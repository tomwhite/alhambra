package org.tiling.alhambra.ga;


 
import org.tiling.alhambra.tiler.AutoTiler; 

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random; 
import java.util.Set; 

import org.tiling.alhambra.Tile;import org.tiling.alhambra.tiler.FittingTiler;import org.tiling.alhambra.FixedPatch;import org.tiling.alhambra.PrototileSet;import org.tiling.alhambra.Patch;import org.tiling.alhambra.TileJoin;import org.tiling.alhambra.Edge;/**
 * I am an AutoTiler that tiles using the strategy encoded in a chromosome.
 * @see GAChromosome
 */
public class GATiler extends AutoTiler {

	/**
	 * @serial
	 */
	private int nextEdgeIndex;



	/**
	 * @serial
	 */
	private final String chromosomeString;


	
	public TileJoin addTile() {
		return addTile(nextEdgeIndex);	
	}	


	

	
	/**
	 * @param iterations the number of iterations to run
	 * @return the fitness after this run
	 */
	public double run(int iterations) {
		for (int i = 0; i < iterations; i++) {
			addTile();
		}
		return getFitness(iterations);
	}
	
	private double getFitness(int iterations) {
		return patch.getTiles().size() - 1;
//		return getPackingEfficiency() * (getTiles().size() - 1) / iterations;
	}
	

	


	private GAEncoder encoder;	private FittingTiler fittingTiler;	private static Random random = new Random();	private GATiler(String chromosomeString, Patch patch, PrototileSet prototileSet, GAEncoder encoder) {
		super(patch, prototileSet);
		this.chromosomeString = chromosomeString;
		this.encoder = encoder;

		// Add a random initial seed tile
		List allTiles = prototileSet.getAllTiles(); 
		patch.add((Tile) allTiles.get(random.nextInt(allTiles.size())));

		fittingTiler = new FittingTiler(patch, prototileSet);
	}	public GATiler(GAChromosome gaChromosome) {
		this(gaChromosome, new FixedPatch());
	}	public GATiler(GAChromosome gaChromosome, Patch patch) {
		this(gaChromosome.toString(), patch, gaChromosome.getPrototileSet(), gaChromosome.getGAEncoder());
	}	private TileJoin addTile(int edgeIndex) {
		nextEdgeIndex = edgeIndex;
		
		TileJoin join = null;

		List edges = patch.getSides();
		List edgeNeighbourhood = encoder.getEdgeNeighbourhood(edges, nextEdgeIndex);

		TileEdgePair tileEdgePair = encoder.getCandidateTileEdgePair(chromosomeString, edgeNeighbourhood);

		if (tileEdgePair != null) {
			Edge patchEdge = (Edge) edgeNeighbourhood.get((edgeNeighbourhood.size() - 1) / 2);
			Tile transformedTile = fittingTiler.getFittingTile(patchEdge, tileEdgePair.getTile(), tileEdgePair.getEdge());

			if (transformedTile != null) {
				join = patch.add(transformedTile);
			}
		}
		if (join == null) {
			nextEdgeIndex++;
		} else {
			nextEdgeIndex += join.getNewEdges().size();
		}
		nextEdgeIndex %= edges.size();
		return join;
	}	public TileJoin addTile(Edge edge) {
		return addTile(patch.getSides().indexOf(edge));
	}}