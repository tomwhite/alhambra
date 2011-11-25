package org.tiling.alhambra;

import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * I represent an operation on a tiling.
 */
public abstract class TileOperation implements Serializable {
	protected Patch patch;
	protected Tile tile;
	public static class AddTileOperation extends TileOperation {
		public AddTileOperation(Patch patch, Tile tile) {
			super(patch, tile);
		}
		public TileJoin execute() {
			return patch.add(tile);
		}
	}
	public static class RemoveTileOperation extends TileOperation {
		public RemoveTileOperation(Patch patch, Tile tile) {
			super(patch, tile);
		}
		public TileJoin execute() {
			return patch.remove(tile);
		}
	}
	public TileOperation(Patch patch, Tile tile) {
		this.patch = patch;
		this.tile = tile;
	}
	public abstract TileJoin execute();
	public Tile getTile() {
		return tile;
	}
}
