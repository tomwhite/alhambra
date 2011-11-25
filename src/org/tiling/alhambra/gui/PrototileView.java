package org.tiling.alhambra.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.tiling.alhambra.Prototile; 
import org.tiling.alhambra.Tile;

import java.util.Collections;/**
 * I lay out Prototiles on a regular grid on a canvas. Useful for viewing collections
 * of individual tiles rather than tilings.
 */
public class PrototileView extends TileView {

	/**
	 * @serial
	 */
	private List prototiles = new ArrayList(); // a List of Lists (of Prototiles)

	/**
	 * @serial
	 */
	private boolean expand;

	/**
	 * Constructs a PrototileView of a collection of prototiles.
	 * @param expand if true then show all symmetries of the prototile
	 */
	public PrototileView(boolean expand, int gridSize) {
		super(gridSize);
		this.expand = expand;
	}

	/**
	 * Constructs a PrototileView of a collection of prototiles, with selection.
	 * @param expand if true then show all symmetries of the prototile
	 */
	public PrototileView(boolean expand, int gridSize, boolean multipleSelection) {
		super(gridSize, multipleSelection);
		this.expand = expand;
	}

	public void add(Collection morePrototiles) {
		if (expand) {
			for (Iterator i = morePrototiles.iterator(); i.hasNext(); ) {
				super.add(((Prototile) i.next()).getTransformedTiles());
			}
		} else {
			List allTiles = new ArrayList();
			List allPrototiles = new ArrayList();
			for (Iterator i = morePrototiles.iterator(); i.hasNext(); ) {
				Prototile prototile = (Prototile) i.next();
				allPrototiles.add(prototile);
				allTiles.add(prototile.getTile());
			}
			prototiles.add(allPrototiles);
			super.add(allTiles);
		}
		repaint();
	}
	
	/**
	 * @return a List of prototiles corresponding to the selection
	 */
	public List getSelectedPrototiles() {
		List selectedPrototiles = new ArrayList();
		
		int row = 0;
		int column = 0;
		for (Iterator i = selectedTiles.iterator(); i.hasNext(); ) {
			column = 0;
			for (Iterator j = ((Collection) i.next()).iterator(); j.hasNext(); ) {
				if (((Boolean) j.next()).booleanValue()) {
					selectedPrototiles.add(((List) prototiles.get(row)).get(column));
				}
				column++;
			}
			row++;
		}
		return selectedPrototiles;
	}
	
	/**
	 * @param a Collection of prototiles to mark as selected
	 */
	public void setSelectedPrototiles(Collection selectedPrototiles) {
		int row = 0;
		int column = 0;
		for (Iterator i = prototiles.iterator(); i.hasNext(); ) {
			List selectedRow = (List) selectedTiles.get(row);
			column = 0;
			for (Iterator j = ((Collection) i.next()).iterator(); j.hasNext(); ) {
				if (selectedPrototiles.contains(j.next())) {
					selectedRow.set(column, Boolean.TRUE);
				}
				column++;
			}
			row++;
		}
		repaint();
	}

	/**
	 * @return the prototile that matches tile, or null if there is no such prototile
	 */
	public Prototile getPrototile(Tile tile) {
		for (Iterator i = prototiles.iterator(); i.hasNext(); ) {
			List row = (List) i.next();
			for (Iterator j = row.iterator(); j.hasNext(); ) {
				Prototile prototile = (Prototile) j.next();
				if (prototile.matches(tile)) {
					return prototile;
				}
			}
		}
		return null;
	}
	public void add(Prototile prototile) { 
		add(Collections.singleton(prototile));
	}}