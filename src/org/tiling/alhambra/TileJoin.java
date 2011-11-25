package org.tiling.alhambra;

import java.util.List;

/**
 * I encapsulate the addition or removal of a Tile from a
 * portion of a Patch.
 * @see Tile
 * @see Patch 
 */
public interface TileJoin {

	/**
	 * @return a List of the common edges of a list of basic edges and a
	 * tile in ccw order.
	 */
	public List getCommonEdges();

	/**
	 * @return a List of the new edges of a tile in ccw order
	 * that are not in a list of the basic edges.
	 */	
	public List getNewEdges();
}