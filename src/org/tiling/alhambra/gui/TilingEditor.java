package org.tiling.alhambra.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.*;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.tiler.*;
import org.tiling.gui.*;
import org.tiling.util.Serializer;

import java.beans.PropertyChangeListener;import java.beans.PropertyChangeEvent;/**
 * I am a specialization of Viewer2D that allows interactive construction 
 * of tilings, using the mouse.
 */
public class TilingEditor extends Viewer2D {

	/**
	 * @serial the tiler used to generate tiles
	 */
	private ManualTiler tiler;



	/**
	 * @serial the main canvas
	 */
	private Canvas2D canvas;

	/**
	 * @serial area for messages
	 */
	private JLabel messageArea;

	/**
	 * @serial small view of available prototiles
	 */
	private PrototileView allPrototilesView;

	/**
	 * @serial small view of fitting tiles
	 */
	private TileView fittingTilesView;
	




	protected void setUpMenus() {

		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(new Saver());
		fileMenu.add(new Printer());
		fileMenu.add(new PostscriptPrinter());
		fileMenu.add(new PrintPreviewerA3());
		fileMenu.add(new PrintPreviewerA4());
		
		JMenu tilingMenu = new JMenu("Tiling");
		tilingMenu.add(new Centrer());
		tilingMenu.add(new Fitter());
		tilingMenu.add(new ForcedTileAdder());

		menuBar.add(fileMenu);
		menuBar.add(tilingMenu);
		setJMenuBar(menuBar);

	}
	
	private void showMessage(String message) {
		messageArea.setText(message);
	}
	
	private String getStats() {
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(2);
		return formatter.format(patch.getPackingEfficiency());
	}
	
	protected class FittingTileSelectionListener implements TileSelectionListener, Serializable {
		public void valueChanged(TileSelectionEvent event) {
			List tiles = event.getSelectedTiles();
			if (!tiles.isEmpty()) {
				replaceCurrentTile((Tile) tiles.get(0));
			}
		}
	}
	
	private void replaceCurrentTile(Tile tile) {
		tiler.replaceTile(tile);
		canvas.repaint();
	}

	private void addTile(Tile tile, Edge edge) {
		String message;
		if (tile == null) {
			tiler.addTile(edge);
			fittingTilesView.clear();
			fittingTilesView.add(tiler.getCurrentTiles());
			fittingTilesView.repaint();
		} else {
			tiler.nextTile();
		}
		canvas.repaint();
		if (!tiler.getCurrentTiles().isEmpty()) {
			fittingTilesView.selectTileAt(tiler.getCurrentTilesIndex(), 0);
		}
	}

	private void removeTile(Tile tile) {
		if (tile != null) {
			if (patch.remove(tile) != null) {
				fittingTilesView.clear();
				fittingTilesView.repaint();				
				canvas.repaint();
				showMessage("Tile removed (Packing Efficiency: " + getStats() + ")");
			}
		}				
	}

	protected class MouseClickListener extends MouseAdapter implements Serializable {

		public void mouseClicked(MouseEvent e) {

			if (e.getSource() != canvas) {
				return;
			}

			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) { // Left button

				Point2D clickPoint = transformToTileSpace(e.getX(), e.getY());
				Tile tile = patch.getTileAt(clickPoint);
				Edge edge = patch.getClosestSide(clickPoint);
				addTile(tile, edge);

			} else if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) { // Right button
				Tile tile = patch.getTileAt(transformToTileSpace(e.getX(), e.getY()));
				removeTile(tile);
			}
		}
	}

	private Point2D transformToTileSpace(int x, int y) {
		return transformToTileSpace(x, y, getCanvas2D());
	}

	private Point2D transformToTileSpace(int x, int y, Canvas2D canvas2D) {
		try {
			java.awt.geom.Point2D p1 = new java.awt.geom.Point2D.Double(x, y);
			java.awt.geom.Point2D p2 = new java.awt.geom.Point2D.Double();
			canvas2D.getFlippedAffineTransform().createInverse().transform(p1, p2);
			return new Point2D(p2.getX(), p2.getY());
		} catch (Exception ex) {}
		return null;
	}

	protected class ForcedTileAdder extends AbstractAction {
		public ForcedTileAdder() {
			super("Add Forced Tiles");
		}
		public void actionPerformed(ActionEvent e) {
			int added = fittingTiler.addForcedTiles();
			showMessage(added + " forced tiles added (Packing Efficiency: " + getStats() + ")");
			canvas.repaint();
			fittingTilesView.clear();
			fittingTilesView.repaint();				
		}
	}

	protected class Saver extends AbstractAction {
		public Saver() {
			super("Save As...");
		}
		public void actionPerformed(ActionEvent e) {
			File file = FileManager.getInstance().chooseFileToSave(TilingEditor.this);
			if (file != null) {
				Serializer.serialize(patch, file);
			}
		}
	}



	protected class ColourClickListener extends MouseAdapter implements Serializable {
		public void mouseClicked(MouseEvent e) {

			if (e.getSource() != allPrototilesView) {
				return;
			}

			Tile tile = allPrototilesView.getTileAt(transformToTileSpace(e.getX(), e.getY(),
						allPrototilesView));
			if (tile != null) {
				Color color = ColourChooser.getInstance().chooseColour(allPrototilesView,
					tile.getUI().getBackground());
				if (color != null) {
					for (Iterator i = patch.getTiles().iterator(); i.hasNext(); ) {
						Tile t = (Tile) i.next();
						if (t.getType().isA((tile).getType())) {
							t.getUI().setBackground(color);
						}
					}
					canvas.repaint(); 
				}
			}
		}		
	}

	private FittingTiler fittingTiler;	/**
	 * @serial the patch
	 */
	private Patch patch;	protected class MessageChangeListener implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			showMessage(event.getNewValue().toString());
		}
	}	public TilingEditor(Patch patch, PrototileSet prototileSet, String title) {
		this(patch, prototileSet, title, new FittingSequentialTiler(patch, prototileSet));
	}	public TilingEditor(Patch patch, PrototileSet prototileSet, String title, ManualTiler manualTiler) {

		super(title);
		this.patch = patch;
		this.tiler = manualTiler;
		this.tiler.addPropertyChangeListener(new MessageChangeListener());

		this.fittingTiler = new FittingTiler(patch, prototileSet);

		this.canvas = new Canvas2D(patch.getUI());
		setCanvas2D(canvas);

		messageArea = new JLabel();
		messageArea.setBorder(BorderFactory.createLineBorder(Colours.DARK_PURPLE));

		allPrototilesView = new PrototileView(false, 24);
		allPrototilesView.add(prototileSet.getPrototiles());
		allPrototilesView.setBorder(BorderFactory.createLineBorder(Colours.DARK_PURPLE));
		allPrototilesView.addMouseListener(new ColourClickListener());
		allPrototilesView.setToolTipText("All prototiles - click to choose colour");
		
		fittingTilesView = new TileView(24, false);
		fittingTilesView.setBorder(BorderFactory.createLineBorder(Colours.DARK_PURPLE));
		fittingTilesView.addTileSelectionListener(new FittingTileSelectionListener());
		fittingTilesView.setToolTipText("Fitting tiles - click to replace");

		JPanel panel = new JPanel(new BorderLayout(2, 2));
		panel.add(messageArea, BorderLayout.CENTER);
		panel.add(allPrototilesView, BorderLayout.EAST);
		panel.add(fittingTilesView, BorderLayout.NORTH);
		panel.setBackground(Color.white);

		getContentPane().add(panel, BorderLayout.SOUTH);

		addMouseListener(new MouseClickListener());

		setVisible(true);

		addTile(null, null); // add first tile

//		recentreCanvas();

	}	public TilingEditor(PrototileSet prototileSet, String title) {
		this(new SimplePatch(), prototileSet, title);
	}}