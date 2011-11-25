package org.tiling.alhambra.ga;

import java.io.File;


import org.tiling.ga.*; 
 
 

import java.text.NumberFormat;import java.util.Arrays;import java.io.IOException;import org.tiling.util.Archiver;import java.beans.Beans;/**
 * I am an application that performs a specified number of runs of breeding
 * Populations.
 * @ deprecated - see org.tiling.ga.Breeder.
 */
public class Breeder implements Runnable {



	private Population population;
	

	private int runLength = 50;
	

	

	

	
	public static void main(String[] args) {
		
		if (args.length == 0) {
			printUsage();
			System.exit(1);
		}

		Breeder breeder = new Breeder();

		Population population = new FixedSizePopulation();
		if (args.length > 0) {
			try {
				breeder.setPopulationSize(Integer.parseInt(args[0]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.exit(1);
			}			
		}
		
		if (args.length > 1) {
			try {
				breeder.setRunLength(Integer.parseInt(args[1]));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.exit(1);
			}			
		}

		if (args.length > 2) {
			try {
				population.setSelection((Selection) Beans.instantiate(null, args[2]));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}			
		}

		breeder.setPopulation(population);
		
		int edgeNeighbourhoodSize = 3;
		double crossoverProbability = Chromosome.CROSSOVER_PROBABILITY;
		double mutationProbability = Chromosome.MUTATION_PROBABILITY;

		if (args.length > 3) {
			try {
				edgeNeighbourhoodSize = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.exit(1);
			}			
		}

		if (args.length > 4) {
			try {
				crossoverProbability = Double.parseDouble(args[4]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.exit(1);
			}			
		}

		if (args.length > 5) {
			try {
				mutationProbability = Double.parseDouble(args[5]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				System.exit(1);
			}			
		}
		GAChromosomeFactory chromosomeFactory = new GAChromosomeFactory();
		chromosomeFactory.setEdgeNeighbourhoodSize(edgeNeighbourhoodSize);
		chromosomeFactory.setCrossoverProbability(crossoverProbability);
		chromosomeFactory.setMutationProbability(mutationProbability);
		breeder.setChromosomeFactory(chromosomeFactory);

		System.out.println("Population size: " + breeder.getPopulationSize()); 
		System.out.println("Run length: " + breeder.getRunLength());
		System.out.println("Selection: " + breeder.getPopulation().getSelection().getClass().getName());
		System.out.println("Edge neighbourhood size: " + edgeNeighbourhoodSize);
		System.out.println("Crossover probability: " + crossoverProbability);
		System.out.println("Mutation probability: " + mutationProbability);
		
		breeder.run();

	}

	private static void printUsage() {
		System.err.println("Usage: Breeder population_size run_length [selection_bean [edge_neighbourhood_size [crossover_probability mutation_probability]]]");
	}
	private ChromosomeFactory chromosomeFactory;	private Chromosome fittestEver;	private int generationFittestEverFound;	private int populationSize = 30;	public Breeder() {
	}	public ChromosomeFactory getChromosomeFactory() {
		return chromosomeFactory;
	}	public Population getPopulation() {
		return population;
	}	public int getPopulationSize() {
		return populationSize;
	}	public int getRunLength() {
		return runLength;
	}	private Chromosome report(Population p, int generation) {
		Chromosome[] individuals = (Chromosome[]) p.getIndividuals().clone();
		Chromosome fittest = individuals[0];
		double maxFitness = individuals[0].getFitness();
		for (int i = 1; i < individuals.length; i++) {
			double fitness = individuals[i].getFitness();
			if (fitness > maxFitness) {
				fittest = individuals[i];
				maxFitness = fitness;
			}
		}

		if (fittestEver == null || maxFitness > fittestEver.getFitness()) {
			fittestEver = fittest;
			generationFittestEverFound = generation;
		}

		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		System.out.println(generation + ". Best: " + numberFormat.format(fittestEver.getFitness()) +
							" (gen. " + generationFittestEverFound + ")" +
							" Max: " + numberFormat.format(maxFitness) +
							" Mean: " + numberFormat.format(p.getMeanFitness()) +
							" Var: " + numberFormat.format(p.getFitnessVariance()));
		return fittest;
	}	public void run() {

		// Create initial, random population
		Chromosome[] individuals = new Chromosome[populationSize];
		for (int i = 0; i < individuals.length; i++) {
			individuals[i] = chromosomeFactory.create();
		}
		population.setIndividuals(individuals);

		// Iterate
		Chromosome fittest = null;
		for (int generation = 0; generation < runLength; generation++) {
			population.run(1);
			fittest = report(population, generation);
		}

		// archive fittest string and whole population
		try {
			Archiver.getInstance().store("fittest", fittest);
			Archiver.getInstance().store("population", population);
			System.out.println("Archived");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("Fittest:");
		System.out.println(fittest);

	}	public void setChromosomeFactory(ChromosomeFactory chromosomeFactory) {
		this.chromosomeFactory = chromosomeFactory;
	}	public void setPopulation(Population population) {
		this.population = population;
	}	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}	public void setRunLength(int runLength) {
		this.runLength = runLength;
	}}