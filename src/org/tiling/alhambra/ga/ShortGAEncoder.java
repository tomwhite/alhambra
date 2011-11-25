package org.tiling.alhambra.ga;

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
public class ShortGAEncoder extends GAEncoder {
	private static final Vector2D[] BASE_VECTORS = new Vector2D[] {
		new Vector2D(1, 0),
		new Vector2D(0, 1),
		new Vector2D(-1, 0),
		new Vector2D(0, -1),
	};

	private static final int BASE = BASE_VECTORS.length;
	public ShortGAEncoder(PrototileSet prototileSet, int edgeNeighbourhoodSize) {
		super(prototileSet, edgeNeighbourhoodSize);

		chromosomeLength = 2;
		for (int i = 0; i < edgeNeighbourhoodSize - 1; i++){
			chromosomeLength *= (BASE - 1);
		}

	}
	public int encodeEdgeNeighbourhood(List edgeNeighbourhood) {
		if (edgeNeighbourhood.size() < 2) {
			throw new IllegalArgumentException("Cannot have edge neighbourhood of size less than 2.");
		}
		
		int encoding = 0;
		int multiplier = 1;
		Edge previousEdge = (Edge) edgeNeighbourhood.get(0);
		for (int i = 1; i < edgeNeighbourhood.size(); i++) {
			Edge edge = (Edge) edgeNeighbourhood.get(i);
			int orientation = Tools2D.orientation(previousEdge.getV1(), previousEdge.getV2(), edge.getV2());
			encoding += (orientation + 1) * multiplier;
			multiplier *= BASE - 1;
			previousEdge = edge;
		}
		return encoding;	
	}
	protected TileEdgePair getCandidateTileEdgePair(String chromosomeString, List edgeNeighbourhood) {

		// encode edge neighbourhood
		int edgeNeighbourhoodEncoding = encodeEdgeNeighbourhood(edgeNeighbourhood);
		
		// lookup tile and edge from chromosome
		char prototileBit = chromosomeString.charAt(2 * edgeNeighbourhoodEncoding);
		char prototileEdgeBit = chromosomeString.charAt(2 * edgeNeighbourhoodEncoding + 1);
		
		int prototileNumber = prototileBit - GAChromosome.FIRST_ALLELE;
		int prototileEdgeNumber = prototileEdgeBit - GAChromosome.FIRST_ALLELE;
		
		List allTiles = prototileSet.getAllTiles();
		prototileNumber %= allTiles.size();

		Tile tile = (Tile) allTiles.get(prototileNumber);
		for (int i = 0; i < BASE_VECTORS.length; i++) {
			Edge patchEdge = (Edge) edgeNeighbourhood.get(0);
			if (Tools2D.equals(BASE_VECTORS[i], patchEdge.getVector2D())) {
				int index = (6 - i) % 4;
				tile = tile.transform(prototile.getSymmetryGroup().getRotationalTransform(index));
			}
		}
		
		prototileEdgeNumber %= tile.getSides().size();

		return new TileEdgePair(tile, prototileEdgeNumber);
	}
}
