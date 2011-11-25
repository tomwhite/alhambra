package org.tiling.alhambra.app;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;


import org.tiling.alhambra.ga.*; 
import org.tiling.alhambra.gui.*; 
 
import org.tiling.alhambra.tiler.*;
import org.tiling.gui.FileManager; 
import org.tiling.util.ExampleFileFilter; 
import org.tiling.util.JarManager; 
import org.tiling.util.Serializer;

import org.tiling.util.Archiver;import org.tiling.alhambra.Prototile;import org.tiling.alhambra.PrototileSet;import org.tiling.alhambra.Patch;import org.tiling.script.JythonInterpreter;/**
 * I am an application that houses tilings.
 */
public class Alhambra implements Serializable {



	/**
	 * @serial
	 */
	private int gaTilingViewerNumber, tilingEditorNumber;

	/**
	 * @serial
	 */
	private JDesktopPane desktop;

	/**
	 * @serial
	 */
	private FileManager fileManager = FileManager.getInstance();

	private Container container;

	public Alhambra(Container container) {

		this.container = container;

		setUpDesktop();
		setUpMenus();

		container.setSize(800, 600);
		container.setVisible(true);

	}

	protected void setVisible(boolean visible) {
		container.setVisible(visible);
	}

	private void setUpDesktop() {
		desktop = new JDesktopPane();
		if (container instanceof JApplet) {
			((JApplet) container).getContentPane().setLayout(new BorderLayout());
			((JApplet) container).getContentPane().add(desktop, BorderLayout.CENTER);
		} else if (container instanceof JFrame) {
			((JFrame) container).getContentPane().setLayout(new BorderLayout());
			((JFrame) container).getContentPane().setBackground(Color.white);

			JPanel panel = new JPanel(new BorderLayout());
			panel.setBackground(Color.white);
			
			prototileMenuView = new PrototileMenuView();
			prototileMenuView.addTileSelectionListener(new TileSelectionUpdater(prototileMenuView));
			JScrollPane allScrollPane = new JScrollPane(prototileMenuView);
			JSplitPane allSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				allScrollPane, desktop);
			allSplitPane.setOneTouchExpandable(true);
			panel.add(allSplitPane, BorderLayout.CENTER);
			
			currentView = new PrototileView(true, 32);
			currentView.setToolTipText("Current Prototiles Selection");
			JScrollPane currentScrollPane = new JScrollPane(currentView);

			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				currentScrollPane, panel);
			splitPane.setOneTouchExpandable(true);
			((JFrame) container).getContentPane().add(splitPane, BorderLayout.CENTER);
		
		}
	}

	private void setUpMenus() {

		JMenuBar menuBar = new JMenuBar();
		JMenu tileMenu = new JMenu("Tile");
		JMenu tilerMenu = new JMenu("Tiler"); 
		scriptMenu = new JMenu("Script"); 
		
		if (container instanceof JApplet) {

			tilerMenu.add(new TilingEditorCreator());
		
			menuBar.add(tilerMenu);
			((JApplet) container).setJMenuBar(menuBar);
		} else if (container instanceof JFrame) {

			tileMenu.add(new PrototileJarLoader());

			tilerMenu.add(new TilingEditorCreator());
			tilerMenu.add(new TilingEditorLoader());
			tilerMenu.add(new GATilingViewerCreator());
			tilerMenu.add(new GATilingEditorCreator());

			scriptMenu.add(new RunJythonScriptAction()); 

			menuBar.add(tileMenu);
			menuBar.add(tilerMenu); 
			menuBar.add(scriptMenu); 
			((JFrame) container).setJMenuBar(menuBar);
		}

	}

	private void setPrototiles(Collection prototiles) {
		prototileMenuView.setPrototiles(prototiles);

		currentView.clear();
		currentView.add(prototiles);
		currentView.repaint();
	}



	public void addJInternalFrame(JInternalFrame internalFrame) {
		internalFrame.pack();
		desktop.add(internalFrame, JLayeredPane.DEFAULT_LAYER);
		desktop.moveToFront(internalFrame);
		try {
			internalFrame.setSelected(true);
		} catch (Exception ex) {
		}
	}



	protected class PrototileJarLoader extends AbstractAction {
		
		public PrototileJarLoader() {
			super("Load Prototiles from Jar");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				File file = FileManager.getInstance().chooseFileToOpen(container);
				URL url = file.toURL();
				Set prototiles = JarManager.getInstance().instantiateFromJar(url.toString(), org.tiling.alhambra.Prototile.class);
				prototileMenuView.add(prototiles);
			} catch (MalformedURLException ex) {
				System.out.println(e.toString());
			}
		}
	}

	protected class TilingEditorCreator extends AbstractAction {
		
		public TilingEditorCreator() {
			super("New Editor");
		}

		public void actionPerformed(ActionEvent e) {
			addJInternalFrame(new TilingEditor(getCurrentPrototileSet(), "Tiling " + ++tilingEditorNumber));
		}
	}

	protected class TilingEditorLoader extends AbstractAction { 
		 
		public TilingEditorLoader() { 
			super("Open Editor..."); 
		} 
 
		public void actionPerformed(ActionEvent e) { 
			File file = FileManager.getInstance().chooseFileToOpen(container); 
			if (file != null) { 
				Patch patch = (Patch) Serializer.deserialize(file); 
				addJInternalFrame(new TilingEditor(patch, getCurrentPrototileSet(), "Tiling " + ++tilingEditorNumber)); 
			} 
		} 
	} 
 
	protected class RunJythonScriptAction extends AbstractAction {

		private File file;
		private boolean addToMenu;
		 
		public RunJythonScriptAction() { 
			super("Run Jython Script...");
			addToMenu = true;
		} 
 
		public RunJythonScriptAction(File file) { 
			super("Re-run " + file.toString() + "...");
			this.file = file; 
		} 
 
		public void actionPerformed(ActionEvent e) {
			if (file == null) {
				FileFilter filter = new ExampleFileFilter("py", "Jython source files");
				file = FileManager.getInstance().chooseFileToOpen(container, RunJythonScriptAction.this, filter);
			}
			if (file != null) {
				try {
					JythonInterpreter interpreter = new JythonInterpreter();
					interpreter.set("alhambra", Alhambra.this);
					interpreter.execute(file);
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					if (addToMenu) {
						Alhambra.this.scriptMenu.add(new RunJythonScriptAction(file));
						file = null;
					}
				}
			}
		} 
	} 
	 
	protected class GATilingViewerCreator extends AbstractAction {
		
		public GATilingViewerCreator() {
			super("New GA Tiler");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				GAChromosome chromosome = (GAChromosome) Archiver.getInstance().retrieve("fittest");
				AutoTiler autoTiler = new GATiler(chromosome);
				addJInternalFrame(new AutoTilerViewer(autoTiler, "GA Tiling " + ++gaTilingViewerNumber));
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}

	/**
	 * @serial
	 */
	private PrototileView currentView;	/**
	 * @serial
	 */
	private PrototileMenuView prototileMenuView;	private JMenu scriptMenu;	protected class GATilingEditorCreator extends AbstractAction {
		
		public GATilingEditorCreator() {
			super("New GA Editor");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				GAChromosome chromosome = (GAChromosome) Archiver.getInstance().retrieve("fittest");
				ManualTiler tiler = new GAManualTiler(chromosome);
				addJInternalFrame(new TilingEditor(tiler.getPatch(), tiler.getPrototileSet(), "GA Tiling Editor", tiler));
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
	}	protected class TileSelectionUpdater implements TileSelectionListener, Serializable {
		private PrototileMenuView prototileMenuView;
		public TileSelectionUpdater(PrototileMenuView prototileMenuView) {
			this.prototileMenuView = prototileMenuView;
		}
		public void valueChanged(TileSelectionEvent event) {
			setPrototiles(prototileMenuView.getSelectedPrototiles());
		}
	}	public PrototileSet getCurrentPrototileSet() {
		return new PrototileSet(prototileMenuView.getSelectedPrototiles());
	}	public PrototileMenuView getPrototileMenuView() {
		return prototileMenuView;
	}	public void setCurrentPrototileSet(PrototileSet prototileSet) {
		setPrototiles(prototileSet.getPrototiles());
	}}