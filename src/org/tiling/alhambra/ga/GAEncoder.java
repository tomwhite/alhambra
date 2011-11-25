package org.tiling.alhambra.ga;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tiling.alhambra.Edge;
import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.PrototileSet;
import org.tiling.alhambra.Tile;
import org.tiling.alhambra.geom.Tools2D;
import org.tiling.alhambra.geom.Vector2D;

/**
 * I encode edge neighbourhoods as an integer, then use this to lookup
 * a prototile from the chromosome.
 */
public abstract class GAEncoder implements Serializable {
	protected PrototileSet prototileSet;
	protected Prototile prototile; // hack - assume only one prototile
	protected int edgeNeighbourhoodSize;
	protected int chromosomeLength;
	public GAEncoder(PrototileSet prototileSet, int edgeNeighbourhoodSize) {
		this.prototileSet = prototileSet;
		this.prototile = (Prototile) prototileSet.getPrototiles().get(0);
		this.edgeNeighbourhoodSize = edgeNeighbourhoodSize;
	}
	public abstract int encodeEdgeNeighbourhood(List edgeNeighbourhood);
	protected abstract TileEdgePair getCandidateTileEdgePair(String chromosomeString, List edgeNeighbourhood);
	public int getChromosomeLength() {
		return chromosomeLength;
	}
	public List getEdgeNeighbourhood(List edges, int edgeIndex) {
		int startIndex = edgeIndex - ((edgeNeighbourhoodSize - 1) / 2); // inclusive
		startIndex += edges.size(); // ensure startIndex is positive for following mod operation
		startIndex %= edges.size();
		int endIndex = edgeIndex + (edgeNeighbourhoodSize / 2); // inclusive
		endIndex %= edges.size();
		if (startIndex <= endIndex) {
			return Collections.unmodifiableList(edges.subList(startIndex, endIndex + 1));
		} else {
			List add = new ArrayList();
			add.addAll(edges.subList(startIndex, edges.size()));
			add.addAll(edges.subList(0, endIndex + 1));
			return Collections.unmodifiableList(add);
		}
	}
}
