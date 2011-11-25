package org.tiling.alhambra;

import java.util.List;

import org.tiling.alhambra.geom.Point2D;
import org.tiling.UI;

/**
 * I am a finite number of Tiles whose union is a
 * topological disk. I can also be viewed as a single Tile.
 * <p>
 * All Patch implementations are mutable since tiles can be
 * added (and removed).
 * @see Tile
 */
public interface Patch extends Tile {
	// Possible future additions:
	//public List getNeighbours(Tile tile);
	//public int getValence(Vertex v);
	/**
	 * @return a List of all the tiles in the patch in the order
	 * they were added
	 */	
	public List getTiles();

	/**
	 * If there is a tile in this patch located at point p, then
	 * this tile is returned. The behaviour is undefined for points
	 * which are vertices or lie on an edge.
	 * @return the tile that contains p, or <code>null</code> if there
	 * is no such tile.
	 */
	public Tile getTileAt(Point2D p);

	/**
	 * @return the closest (outside) corner of the patch to p
	 */	
	public Vertex getClosestCorner(Point2D p);

	/**
	 * @return the closest (outside) side of the patch to p
	 */	
	public Edge getClosestSide(Point2D p);

	/**
	 * Adds a tile to the patch. The tile is only added if the patch
	 * remains a topological disk.
	 * @return a TileJoin object if the tile was successfully added,
	 * null otherwise.
	 * @see #canAdd(Tile)
	 * @see TileJoin	 
	 */
	public TileJoin add(Tile tile);

	/**
	 * Tests whether a tile can be added to the patch. The tile can 
	 * only be added if the patch remains a topological disk.
	 * Doesn't actually add the tile!
	 * @return <code>true</code> if the tile can be added.
	 * @see #add(Tile)
	 */
	public boolean canAdd(Tile tile);

	/**
	 * Removes a tile from the patch. The tile is only removed if it
	 * is already in the patch, and removing it causes the patch to
	 * remain a topological disk.
	 * @return a TileJoin object if the tile was successfully removed,
	 * null otherwise.
	 * @see #canRemove(Tile)
	 * @see TileJoin	 
	 */
	public TileJoin remove(Tile tile);

	/**
	 * Tests whether a tile can be removed from the patch. The tile
	 * can only be removed if it is already in the patch, and removing
	 * it causes the patch to remain a topological disk.
	 * @return <code>true</code> if the tile can be removed.
	 * @see #remove(Tile)
	 */
	public boolean canRemove(Tile tile);

	/**
	 * The packing efficiency is a number measuring how well the tiles
	 * in the patch are packed together. It lies between 0 and 1, where
	 * 0 is inefficient, and 1 is perfect. A circle has a packing
	 * efficiency of 1. More precisely, the packing efficiency is
	 * <pre>
	   4 * PI * area / (perimeter ^ 2)
	 * </pre>
	 * @return the packing efficiency
	 */	
	public double getPackingEfficiency();

	public UI getUI();

	public void setUI(UI patchUI);

	/**
	 * @return a List of the tiles in the patch which are
	 * <i>adjacent</i> to <code>tile</code>, or null if <code>tile</code>
	 * is not in this patch.
	 * <p>
	 * Two tiles are <i>adjacent</i> if they have an edge in common.
	 * This implementation cannot handle tilings which are not edge-to-edge,
	 * so necessarily this means that the (polygon) tiles have a side in common.
	 */	
	public List getAdjacents(Tile tile);}