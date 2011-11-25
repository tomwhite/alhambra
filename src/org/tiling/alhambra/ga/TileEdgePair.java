package org.tiling.alhambra.ga;

import org.tiling.alhambra.Edge;
import org.tiling.alhambra.Tile;

class TileEdgePair {
	private Tile tile;
	private int edgeIndex;
	public TileEdgePair(Tile tile, int edgeIndex) {
		this.tile = tile;
		this.edgeIndex = edgeIndex;
	}
	public Edge getEdge() {
		return (Edge) tile.getSides().get(edgeIndex);
	}
	public Tile getTile() {
		return tile;
	}
}
