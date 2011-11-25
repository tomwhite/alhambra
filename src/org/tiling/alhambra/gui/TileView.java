package org.tiling.alhambra.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.tiling.alhambra.Colours;
import org.tiling.alhambra.SimpleTileUI;
import org.tiling.alhambra.Tile;
import org.tiling.alhambra.Vertex;
import org.tiling.alhambra.geom.Extent;
import org.tiling.alhambra.geom.Point2D;
import org.tiling.gui.*;

/**
 * I lay out Tile objects on a regular grid on a canvas. Useful for viewing collections
 * of individual tiles rather than tilings.
 */
public class TileView extends Canvas2D {

	/**
	 * @serial
	 */
	private List tiles = new ArrayList(); // a List of Lists (of Tiles)

	/**
	 * @serial
	 */
	private List allTiles = new ArrayList(); // a List of Tiles

	/**
	 * @serial
	 */
	private List displayTiles = new ArrayList(); // a List of transformed Tiles

	/**
	 * @serial
	 */
	protected List selectedTiles = new ArrayList(); // a List of Lists (of Booleans)
	
	/**
	 * @serial
	 */
	private boolean multipleSelection;

	/**
	 * @serial
	 */
	private int gridSize;

	/**
	 * Constructs an empty TileView with no selection.
	 */
	public TileView(int gridSize) {
		this.gridSize = gridSize;
		setBackground(Color.white);
	}
	
	/**
	 * Constructs an empty TileView with either single or multiple selection.
	 */
	public TileView(int gridSize, boolean multipleSelection) {
		this.gridSize = gridSize;
		this.multipleSelection = multipleSelection;
		setBackground(Color.white);
		addMouseListener(new MouseClickListener());
	}

	/**
	 * @param tile the tile to add to this view
	 */
	public void add(Tile tile) {
		add(Collections.singleton(tile));
	}

	/**
	 * @param moreTiles the collection of tiles to add to this view
	 */
	public void add(Collection moreTiles) {
		tiles.add(moreTiles);
		allTiles.addAll(moreTiles);
		
		List selected = new ArrayList();
		selected.addAll(Collections.nCopies(moreTiles.size(), Boolean.FALSE));
		selectedTiles.add(selected);

		calculateLayout();
	}

	public void clear() {
		tiles.clear();
		allTiles.clear();
		displayTiles.clear();
		selectedTiles.clear();
	}

	// shouldn't really be public!
	public void calculateLayout() {

		displayTiles.clear();

		if (allTiles.isEmpty()) {
			return;	
		}
		
		// Find box size
		Extent maxExtent = new Extent(new Point2D(0.0, 0.0));
		List tileCentres = new ArrayList();
		for (Iterator i = allTiles.iterator(); i.hasNext(); ) {
			Tile tile = (Tile) i.next();
			Iterator j = tile.getCorners().iterator();
			if (j.hasNext()) {
				Extent extent = new Extent((Vertex) j.next());
				while (j.hasNext()) {
					extent.add((Vertex) j.next());
				}
				maxExtent.add(new Point2D(extent.getWidth(), extent.getHeight()));
				tileCentres.add(extent.getCentre());
			} else {
				tileCentres.add(new Point2D(0.0, 0.0));
			}
		}
		double paddingFactor = 1.1;
		double boxHalfWidth = maxExtent.getMaxExtent() * paddingFactor / 2;
		double boxHalfHeight = maxExtent.getMaxExtent() * paddingFactor / 2;

		// Transform each tile to centre of a new box
		
		int count = 0;
		int row = 0;
		int column = 0;
		int maxColumn = 0;
		for (Iterator i = tiles.iterator(); i.hasNext(); ) {
			column = 0;
			for (Iterator j = ((Collection) i.next()).iterator(); j.hasNext(); ) {
				Tile tile = (Tile) j.next();
				Point2D centreOfTile = (Point2D) tileCentres.get(count);
				Point2D centreOfBox = new Point2D((2 * column + 1) * boxHalfWidth, (2 * row + 1) * boxHalfHeight);
				AffineTransform t = new AffineTransform(
					new double[] {1, 0, 0, 1, centreOfBox.x - centreOfTile.x, centreOfBox.y - centreOfTile.y});
				Tile transformedTile = tile.transform(t);
				displayTiles.add(transformedTile);
				column++;
				count++;
			}
			row++;
			if (column > maxColumn) {
				maxColumn = column;
			}
		}

		double factor = gridSize / (maxExtent.getMaxExtent() * paddingFactor * getAffineTransform().getScaleX());
		getAffineTransform().scale(factor, factor);

		Dimension size = new Dimension(maxColumn * gridSize, row * gridSize);
		setPreferredSize(size);
		revalidate();

	}

	protected void selectTileAt(int column, int row) {
		if (row < selectedTiles.size()) {
			List selectedRow = (List) selectedTiles.get(row);
			if (column < selectedRow.size()) {
				if (!multipleSelection) {  // for single selection set this row to all false
					int size = selectedRow.size();
					selectedRow.clear();
					selectedRow.addAll(Collections.nCopies(size, Boolean.FALSE));
				}
				boolean selected = ((Boolean) selectedRow.get(column)).booleanValue();
				selectedRow.set(column, new Boolean(!selected));
			}
		}
		repaint();
	}
	
	/**
	 * @return a List of tiles corresponding to the selection
	 */
	public List getSelectedTiles() {
		List selected = new ArrayList();
		
		int row = 0;
		int column = 0;
		for (Iterator i = selectedTiles.iterator(); i.hasNext(); ) {
			column = 0;

			for (Iterator j = ((Collection) i.next()).iterator(); j.hasNext(); ) {
				if (((Boolean) j.next()).booleanValue()) {
					selected.add(((List) tiles.get(row)).get(column));
				}
				column++;
			}
			row++;
		}
		return selected;
	}
	
	TileSelectionListener listener; // just one for now
	public void addTileSelectionListener(TileSelectionListener listener) {
		this.listener = listener;
	}
	
	private void fireTileSelectionEvent(List selectedTiles) {
		if (listener != null) {
			listener.valueChanged(new TileSelectionEvent(this, selectedTiles));
		}
	}

	public Tile getTileAt(Point2D p) {
		for (int i = 0; i < displayTiles.size(); i++) {
			Tile t = (Tile) displayTiles.get(i);
			if (t.contains(p)) {
				return (Tile) allTiles.get(i);
			}
		}
		return null;
	}
	
	public void paint(Graphics g) {

		//Important
		Graphics2D g2 = (Graphics2D) g;

		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
											RenderingHints.VALUE_ANTIALIAS_ON);

		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);

		Stroke stroke = new BasicStroke(SimpleTileUI.TILE_EDGE_WIDTH);
		g2.setStroke(stroke);

		g.setColor(Colours.LILAC);
		int row = 0;
		int column = 0;
		for (Iterator i = selectedTiles.iterator(); i.hasNext(); ) {
			column = 0;
			for (Iterator j = ((Collection) i.next()).iterator(); j.hasNext(); ) {
				if (((Boolean) j.next()).booleanValue()) {
					g2.fillRect(column * gridSize, row * gridSize, gridSize, gridSize);
				}
				column++;
			}
			row++;
		}

		g2.transform(getAffineTransform());
		
		for (Iterator i = displayTiles.iterator(); i.hasNext(); ) {
			((Tile) i.next()).getUI().paint(g2);
		}

		super.paintBorder(g);
	}
	
	protected class MouseClickListener extends MouseAdapter implements Serializable {

		public void mouseClicked(MouseEvent e) {
			selectTileAt(e.getX() / gridSize, e.getY() / gridSize);
			fireTileSelectionEvent(getSelectedTiles());
		}
		
	}

}