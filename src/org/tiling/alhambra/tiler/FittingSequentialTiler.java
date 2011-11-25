package org.tiling.alhambra.tiler;

import java.text.NumberFormat;

import org.tiling.alhambra.Edge;
import org.tiling.alhambra.Patch;
import org.tiling.alhambra.PrototileSet;
import org.tiling.alhambra.Tile;
import org.tiling.alhambra.TileJoin;

public class FittingSequentialTiler extends ManualTiler {
	private FittingTiler fittingTiler;
	private Edge currentEdge;
	private Tile currentTile;
	public FittingSequentialTiler(Patch patch, PrototileSet prototileSet) {
		super(patch, prototileSet);
		fittingTiler = new FittingTiler(patch, prototileSet);
	}
	public TileJoin addTile(Edge edge) {
		// and if edge is null?
		currentEdge = edge;
		currentTiles = fittingTiler.getFittingTiles(edge);
		if (currentTiles.isEmpty()) {
			currentTilesIndex = -1;
			setMessage("Can't add any tile here!");
			return null;
		}
		currentTilesIndex = 0;
		currentTile = (Tile) currentTiles.get(currentTilesIndex);
		setMessage("Tile 1 of " + currentTiles.size());
		return patch.add(currentTile);
	}
	private String getStats() {
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMaximumFractionDigits(2);
		return formatter.format(patch.getPackingEfficiency());
	}
	public TileJoin nextTile() {
		if (currentTilesIndex == -1) {
			setMessage("Can't add any tile here!");
			return null;	
		}
		
		currentTilesIndex++;
		currentTilesIndex %= currentTiles.size();

		patch.remove(currentTile);
		currentTile = (Tile) currentTiles.get(currentTilesIndex);
		setMessage("Tile " + (currentTilesIndex + 1) + " of " + currentTiles.size());
		return patch.add(currentTile);
	}
	public TileJoin replaceTile(Tile tile) {
		if (currentTilesIndex == -1) {
			setMessage("Can't add any tile here!");
			return null;
		}
		
		int index = currentTiles.indexOf(tile);
		if (index == -1) {
			setMessage("No such tile on edge!");
			return null;
		}

		currentTilesIndex = index;
		
		patch.remove(currentTile);
		currentTile = (Tile) currentTiles.get(currentTilesIndex);
		setMessage("Tile " + (currentTilesIndex + 1) + " of " + currentTiles.size());
		return patch.add(currentTile);
	}
	protected void setMessage(String message) {
		super.setMessage(message + " (Packing Efficiency: " + getStats() + ")");
	}
}
