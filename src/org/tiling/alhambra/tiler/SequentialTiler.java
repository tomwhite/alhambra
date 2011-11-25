package org.tiling.alhambra.tiler;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * I am a Tiler that allows the user to cycle through candidate tiles for any particular edge.
 */
public class SequentialTiler extends FittingTiler {
	
	private Edge currentEdge;
	private Tile currentTile;
	private List currentFittingTiles = new ArrayList();
	private int currentFittingTilesIndex;



	public List getCurrentFittingTiles() {
		return currentFittingTiles;
	}
	
	public int getCurrentFittingTilesIndex() {
		return currentFittingTilesIndex;
	}

	/**
	 * Adds the first tile from the list of fitting tiles to the current edge.
	 * @return String describing the result of adding the first tile
	 */
	public String addFirstTile(Edge edge) {
		// and if edge is null?
		currentEdge = edge;
		currentFittingTiles = getFittingTiles(edge);
		if (currentFittingTiles.isEmpty()) {
			currentFittingTilesIndex = -1;
			return "Can't add any tile here!";
		}
		currentFittingTilesIndex = 0;
		currentTile = (Tile) currentFittingTiles.get(currentFittingTilesIndex);
		patch.add(currentTile);
		return "Tile 1 of " + currentFittingTiles.size();
	}

	/**
	 * Replaces the current tile with the next tile from the list
	 * of fitting tiles.
	 * @return String describing the result of adding the next tile
	 */
	public String replaceWithNextTile() {
//		if (currentEdge == null) {
//			return "Don't know edge!";
//		}
		
		if (currentFittingTilesIndex == -1) {
			return "Can't add any tile here!";		
		}
		
		currentFittingTilesIndex++;
		currentFittingTilesIndex %= currentFittingTiles.size();

		patch.remove(currentTile);
		currentTile = (Tile) currentFittingTiles.get(currentFittingTilesIndex);
		patch.add(currentTile);
		return "Tile " + (currentFittingTilesIndex + 1) + " of " + currentFittingTiles.size();
			
	}

	/**
	 * Replaces the current tile with the fitting tile <code>tile</tile>.
	 * @return String describing the result of adding tile
	 */
	public String replaceWithTile(Tile tile) {
//		if (currentEdge == null) {
//			return "Don't know edge!";
//		}

		if (currentFittingTilesIndex == -1) {
			return "Can't add any tile here!";		
		}
		
		int index = currentFittingTiles.indexOf(tile);
		if (index == -1) {
			return "No such tile on edge!";
		}

		currentFittingTilesIndex = index;
		
		patch.remove(currentTile);
		currentTile = (Tile) currentFittingTiles.get(currentFittingTilesIndex);
		patch.add(currentTile);
		return "Tile " + (currentFittingTilesIndex + 1) + " of " + currentFittingTiles.size();
	}

	public SequentialTiler(Patch patch, PrototileSet prototileSet) {
		super(patch, prototileSet);
	}}