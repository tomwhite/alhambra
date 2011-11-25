package org.tiling.alhambra.ga;



import org.tiling.alhambra.*;
import org.tiling.ga.FixedLengthChromosome;

/**
 * I am a Chromosome whose fitness is computed by attempting to tile the plane.
 * @see GATiler
 */
public class GAChromosome extends FixedLengthChromosome {


	
	protected static final char FIRST_ALLELE = 'A';

	

	


	public void computeFitness() {
		if (stringModified) {
			GATiler tiler = new GATiler(this);
			fitness = tiler.run(100);
			stringModified = false;
		}
	}
	
	private GAEncoder encoder;	private PrototileSet prototileSet;	public GAChromosome(PrototileSet prototileSet, char[] allAlleles, double crossoverProbability, double mutationProbability, GAEncoder encoder) {
		super(allAlleles, encoder.getChromosomeLength(), crossoverProbability, mutationProbability);
		this.prototileSet = prototileSet;
		this.encoder = encoder;
	}	public GAEncoder getGAEncoder() {
		return encoder;
	}	public PrototileSet getPrototileSet() {
		return prototileSet;		
	}	protected int getRandomCrossoverLocus() {
		return 2 * r.nextInt(string.length() / 2); // only at even numbered alleles
	}}