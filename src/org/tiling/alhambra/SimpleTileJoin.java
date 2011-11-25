package org.tiling.alhambra;

import java.util.ArrayList;
import java.util.List;

/**
 * I am a simple implementation of TileJoin.
 * @see Tile
 * @see Patch
 * @see SimplePatch 
 */
public class SimpleTileJoin implements TileJoin {

	private boolean commonEdgesContiguous = true;
	private List commonEdges = new ArrayList();
	private List newEdges = new ArrayList();

	/**
	 * @param basic a list of edges in ccw order
	 * @param newTile the tile to be added or removed from the basic edges
	 */	
	public SimpleTileJoin(List basic, Tile newTile) {
		List newTileEdges = newTile.getSides();
		if (basic.isEmpty()) {
			newEdges.addAll(newTileEdges);
			return;
		}
		
		int firstSwitch = -1;
		int secondSwitch = -1;
		if (basic.contains(newTileEdges.get(0)) != basic.contains(newTileEdges.get(newTileEdges.size() - 1))) {
			firstSwitch = 0;
		}

		boolean previousIsCommon = basic.contains(newTileEdges.get(0));
		for (int i = 1; i < newTileEdges.size(); i++) {
			Edge newTileEdge = (Edge) newTileEdges.get(i);
			boolean isCommon = basic.contains(newTileEdge);
			if (previousIsCommon != isCommon) {
				if (firstSwitch == -1) {
					firstSwitch = i;
				} else if (secondSwitch == -1) {
					secondSwitch = i;
				} else {
					commonEdgesContiguous = false;
					break;
				}
			}
			previousIsCommon = isCommon;
		}

		if (!commonEdgesContiguous || firstSwitch == -1 || secondSwitch == -1) {
			return;
		}

		int size = newTileEdges.size();
		if (basic.contains(newTileEdges.get(firstSwitch))) {
			for (int i = firstSwitch; i < secondSwitch; i++) {
				// If an edge match fails - reset and exit
				Edge tileEdge = (Edge) newTileEdges.get(i);
				if (!((Edge) basic.get(basic.indexOf(tileEdge))).matches(tileEdge)) {
					reset();
					return;
				}
				commonEdges.add(tileEdge);
			}
			for (int i = secondSwitch; i < firstSwitch + size; i++) {
				newEdges.add(newTileEdges.get(i % size));
			}
		} else if (basic.contains(newTileEdges.get(secondSwitch))) {
			for (int i = firstSwitch; i < secondSwitch; i++) {
				newEdges.add(newTileEdges.get(i));
			}
			for (int i = secondSwitch; i < firstSwitch + size; i++) {
				// If an edge match fails - reset and exit
				Edge tileEdge = (Edge) newTileEdges.get(i % size);
				if (!((Edge) basic.get(basic.indexOf(tileEdge))).matches(tileEdge)) {
					reset();
					return;
				}
				commonEdges.add(tileEdge);
			}
		}
	}

	private void reset() {
		commonEdgesContiguous = false;
		commonEdges.clear();
		newEdges.clear();
	}

	/**
	 * @return true if the common edges (and hence the new edges)
	 * are connected in order.
	 */
	public boolean commonEdgesContiguous() {
		return commonEdgesContiguous;
	}

	/**
	 * @return a List of the common edges of the basic edges and the
	 * tile in ccw order.
	 * The list is empty if the common edges are not contiguous.
	 */
	public List getCommonEdges() {
		return commonEdges;
	}

	/**
	 * @return a List of the new edges of the tile that are not in
	 * the basic edges in ccw order.
	 * The list is empty if there are no common edges
	 * or they are not contiguous.
	 */	
	public List getNewEdges() {
		return newEdges;
	}
}