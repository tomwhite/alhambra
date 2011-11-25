package org.tiling.alhambra.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection; 
import java.util.Collections; 
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.Border;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.*;
import org.tiling.util.JarManager;

/**
 * An internal frame that displays all known prototiles in a grid.
 * @see TileView
 */
public class PrototileMenuViewer extends JInternalFrame {







	/**
	 * @serial
	 */
	public PrototileView allView;
	private JPanel allViewContainer;
	private JScrollPane allScrollPane;



	public PrototileMenuViewer() {
		super("Prototile Menu", true, false, false, true);

		allView = new PrototileView(false, 32, true);
		
		Set urls = JarManager.getInstance().getJarURLs();
		for (Iterator i = urls.iterator(); i.hasNext(); ) {
			String url = ((URL) i.next()).toString();
			Set factoryNames = JarManager.getInstance().find(url, AbstractPrototileFactory.class);
		
			for (Iterator j = factoryNames.iterator(); j.hasNext(); ) {
				String factoryName = (String) j.next();
				AbstractPrototileFactory factory = AbstractPrototileFactory.getFactory(factoryName);
				String[] familyNames = factory.getPrototileFamilyNames();
				for (int k = 0; k < familyNames.length; k++) {
					allView.add(factory.createAllPrototiles(familyNames[k]));
				}
			}
		
		}

		allView.setToolTipText("All Prototiles - click to add to current selection");

		allScrollPane = new JScrollPane(allView);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(allScrollPane, BorderLayout.SOUTH);
		getContentPane().setBackground(Color.white);

		setVisible(true);

		setRootPaneCheckingEnabled(false); // needed for Serialization

	}

	public void add(Prototile prototile) { 
		add(Collections.singleton(prototile));
	} 
	 
	public void add(Collection morePrototiles) {
		allView.add(morePrototiles);
		allScrollPane.repaint(); // ?
	}
	
	public List getSelectedPrototiles() {
		return allView.getSelectedPrototiles();	
	}

	public void setPrototiles(Collection prototiles) {
		if (getSelectedPrototiles().isEmpty()) {
			allView.setSelectedPrototiles(prototiles);
		}
	}



	public void addTileSelectionListener(TileSelectionListener listener) {
		allView.addTileSelectionListener(listener);
	}}