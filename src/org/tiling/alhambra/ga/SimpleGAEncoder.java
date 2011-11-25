package org.tiling.alhambra.ga;

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
public class SimpleGAEncoder extends GAEncoder {

	private static final Vector2D[] BASE_VECTORS = new Vector2D[] {
		new Vector2D(1, 0),
		new Vector2D(0, 1),
		new Vector2D(-1, 0),
		new Vector2D(0, -1),
	};

	private static final int BASE = BASE_VECTORS.length;

	public SimpleGAEncoder(PrototileSet prototileSet, int edgeNeighbourhoodSize) {
		super(prototileSet, edgeNeighbourhoodSize);

		chromosomeLength = 2;
		for (int i = 0; i < edgeNeighbourhoodSize; i++){
			chromosomeLength *= BASE;
		}

	}
	public int encodeEdgeNeighbourhood(List edgeNeighbourhood) {
		int encoding = 0;
		int multiplier = 1;
		for (int i = 0; i < edgeNeighbourhood.size(); i++) {
			for (int j = 0; j < BASE_VECTORS.length; j++) {
				Edge edge = (Edge) edgeNeighbourhood.get(i);
				if (Tools2D.equals(edge.getVector2D(), BASE_VECTORS[j])) {	
					encoding += j * multiplier;
					break;
				}
			}
			multiplier *= BASE;
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

		if (prototileNumber >= allTiles.size()) {
			return null;
		}
		
		Tile tile = (Tile) allTiles.get(prototileNumber);
		
		if (prototileEdgeNumber >= tile.getSides().size()) {
			return null;
		}

		return new TileEdgePair(tile, prototileEdgeNumber);
	}
}
