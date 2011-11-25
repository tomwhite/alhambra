package org.tiling.alhambra.tiler;

import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import org.tiling.alhambra.Edge;
import org.tiling.alhambra.Patch;
import org.tiling.alhambra.PrototileSet;
import org.tiling.alhambra.SimpleTileJoin;
import org.tiling.alhambra.Tile;
import org.tiling.alhambra.TileJoin;
import org.tiling.alhambra.geom.Tools2D;
import org.tiling.alhambra.geom.Vector2D;

/**
 * I am a Tiler that can find fitting tiles for any particular edge.
 */
public class FittingTiler extends Tiler {
	public FittingTiler() {
	}
	public FittingTiler(Patch patch, PrototileSet prototileSet) {
		super(patch, prototileSet);
	}
	public int addForcedTiles() {
		int added = 0;
		List edges = patch.getSides();
		boolean started = false;
		int nextEdgeIndex = 0;
		int firstEdgeIndex = nextEdgeIndex;
		while (!started || firstEdgeIndex != nextEdgeIndex) {
			List fitting = getFittingTiles((Edge) edges.get(nextEdgeIndex));
			if (fitting.size() == 1) {
				TileJoin join = patch.add((Tile) fitting.get(0));
				added++;
				edges = patch.getSides();
				started = false; // have to restart!
				nextEdgeIndex += join.getNewEdges().size();
				nextEdgeIndex %= edges.size();
				firstEdgeIndex = nextEdgeIndex;
			} else {
				if (!started) {
					started = true;
				}
				nextEdgeIndex++;
				nextEdgeIndex %= edges.size();
			}
		}
		return added;
	}
	/**
	 * @return a list of the Tile objects with the highest number of 
	 * common edges that can legally be added to edge.
	 * Returns the empty set if there are none,
	 * or all tiles if edge is null.
	 * @see Tile
	 */
	protected List getBestFittingTiles(Edge edge) {
		List bestFittingTiles = new ArrayList();
		int bestFitQuality = 0;

		if (edge == null) {
			return prototileSet.getAllTiles();
		}

		for (Iterator i = prototileSet.getAllTiles().iterator(); i.hasNext(); ) {

			Tile candidate = (Tile) i.next();
			List candidateEdges = candidate.getSides();
			for (Iterator j = candidateEdges.iterator(); j.hasNext(); ) {
				Edge candidateEdge = (Edge) j.next();
				Tile transformedCandidate = getFittingTile(edge, candidate, candidateEdge);
				if (transformedCandidate != null) {
					SimpleTileJoin joiner = new SimpleTileJoin(patch.getSides(), transformedCandidate);
					boolean canAdd = (patch.getTiles().isEmpty() ||
						(!patch.overlaps(transformedCandidate) && !joiner.getCommonEdges().isEmpty() &&
							joiner.commonEdgesContiguous() && !Edge.newEdgesIsCoincidentWithSides(joiner.getNewEdges(), patch.getSides())));
					if (canAdd) {
						int fitQuality = joiner.getCommonEdges().size();
						if (fitQuality == bestFitQuality) {
							bestFittingTiles.add(transformedCandidate);
						} else if (fitQuality > bestFitQuality) {
							bestFittingTiles.clear();
							bestFittingTiles.add(transformedCandidate);
							bestFitQuality = fitQuality;
						}
					}
				}
			}

		}
		return Collections.unmodifiableList(bestFittingTiles);
	}
	/**
	 * @return the transformed tile that fits the edge patchSideNumber of the patch
	 * with the edge tileSideNumber of candidate, or null if 
	 * the edges are not equal under translation.
	 * Note that the tile returned may not necessarily be added to the patch.
	 */
	public Tile getFittingTile(int patchSideNumber, Tile candidate, int tileSideNumber) {
		List patchSides = patch.getSides();
		if (patchSideNumber < patchSides.size()) {
			return getFittingTile((Edge) patchSides.get(patchSideNumber), candidate, tileSideNumber);
		}			
		return null;
	}
	/**
	 * @return the transformed tile that fits candidateEdge with the edge tileSideNumber
	 * of candidate, or null if candidateEdge and patchEdge are not equal under translation.
	 * Note that the tile returned may not necessarily be added to the patch that has
	 * patchEdge as a side.
	 */
	public Tile getFittingTile(Edge patchEdge, Tile candidate, int tileSideNumber) {
		List tileSides = candidate.getSides();
		if (tileSideNumber < tileSides.size()) {
			return getFittingTile(patchEdge, candidate, (Edge) tileSides.get(tileSideNumber));
		}		
		return null;
	}
	/**
	 * @return the transformed tile that fits candidateEdge with patchEdge, or null if 
	 * candidateEdge and patchEdge are not equal under translation.
	 * Note that the tile returned may not necessarily be added to the patch that has
	 * patchEdge as a side.
	 */
	public Tile getFittingTile(Edge patchEdge, Tile candidate, Edge candidateEdge) {
		if (Tools2D.equals(candidateEdge.getVector2D(), patchEdge.getVector2D())) {
			Vector2D t = new Vector2D(candidateEdge.getV1(), patchEdge.getV1());
			return candidate.transform(t.getTranslation());
 		} else if (Tools2D.reverseEquals(candidateEdge.getVector2D(), patchEdge.getVector2D())) {
			Vector2D t = new Vector2D(candidateEdge.getV1(), patchEdge.getV2());
			return candidate.transform(t.getTranslation());
		}
		return null;
	}
	/**
	 * @return a list of all the Tile objects that can legally be added to edge.
	 * Returns the empty set if there are none,
	 * or all tiles if edge is null.
	 * @see Tile
	 */
	public List getFittingTiles(Edge edge) {
		List fittingTiles = new ArrayList();
		List fitQualities = new ArrayList();

		if (edge == null) {
			return prototileSet.getAllTiles();
		}

		for (Iterator i = prototileSet.getAllTiles().iterator(); i.hasNext(); ) {

			Tile candidate = (Tile) i.next();
			List candidateEdges = candidate.getSides();
			for (Iterator j = candidateEdges.iterator(); j.hasNext(); ) {
				Edge candidateEdge = (Edge) j.next();
				Tile transformedCandidate = getFittingTile(edge, candidate, candidateEdge);
				if (transformedCandidate != null) {
					SimpleTileJoin joiner = new SimpleTileJoin(patch.getSides(), transformedCandidate);
					boolean canAdd = (patch.getTiles().isEmpty() || 
						(!patch.overlaps(transformedCandidate) && !joiner.getCommonEdges().isEmpty() &&
							joiner.commonEdgesContiguous() && !Edge.newEdgesIsCoincidentWithSides(joiner.getNewEdges(), patch.getSides()))); 
					if (canAdd) {
						Integer fitQuality = new Integer(-joiner.getCommonEdges().size());
						int index = Collections.binarySearch(fitQualities, fitQuality);
						if (index < 0) { // not in list
							fittingTiles.add(-(index + 1), transformedCandidate);
							fitQualities.add(-(index + 1), fitQuality);
						} else {
							fittingTiles.add(index + 1, transformedCandidate);
							fitQualities.add(index + 1, fitQuality);
						}
					}
				}
			}

		}
		return Collections.unmodifiableList(fittingTiles);
	}
}
