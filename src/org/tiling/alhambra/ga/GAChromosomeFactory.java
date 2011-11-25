package org.tiling.alhambra.ga;

import org.tiling.alhambra.AbstractPrototileFactory;
import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.PrototileSet;

import org.tiling.ga.Chromosome;
import org.tiling.ga.ChromosomeFactory;

/**
 * I create GAChromosomes.
 * @see GAChromosome
 */
public class GAChromosomeFactory extends ChromosomeFactory {
	private int edgeNeighbourhoodSize = 3;

	private PrototileSet prototileSet;
	private Prototile prototile; // hack - assume only one prototile
	private GAEncoder encoder;
	private char[] alleles;
	public GAChromosomeFactory() {
		prototile = AbstractPrototileFactory.getFactory("org.tiling.alhambra.tile.polyomino.PolyominoFactory").createPrototile("tetromino_L");
		prototileSet = new PrototileSet(prototile);

		int maxPerimeter = 10;
		
		alleles = new char[Math.max(prototileSet.getAllTiles().size(), maxPerimeter)];
		for (int i = 0; i < alleles.length; i++) {
			alleles[i] = (char) (GAChromosome.FIRST_ALLELE + i);
		}
		
		encoder = new ShortGAEncoder(prototileSet, edgeNeighbourhoodSize);
	}
	public Chromosome create() {
		return new GAChromosome(prototileSet, alleles, getCrossoverProbability(), getMutationProbability(), encoder);
	}
	public void setEdgeNeighbourhoodSize(int edgeNeighbourhoodSize) {
		this.edgeNeighbourhoodSize = edgeNeighbourhoodSize;
		encoder = new ShortGAEncoder(prototileSet, edgeNeighbourhoodSize);
	}
}
