package org.tiling.alhambra.tiler;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.*; 

import java.awt.*;
import java.util.Collection; 
import java.util.Collections; 
import java.util.List;

/**
 * I am an AutoTiler that is useful for debugging generation of patches. 
 * @see AutoTiler 
 */
public abstract class DebuggingTiler extends AutoTiler {

	boolean stuck = false;

 

	protected abstract Tile nextTile();

	public TileJoin addTile() {
		if (stuck) {
			return null;
		}
		Tile tile = nextTile();
		if (tile == null) {
			return null;
		}
		TileJoin join = patch.add(tile);
		if (join == null) {
			tile.getUI().setBackground(Color.red);
			((SimplePatchUI) patch.getUI()).add(tile.getUI());
			System.out.println(patch.getCorners());
			System.out.println(tile);
			stuck = true;
		}
		return join;
	}

	public DebuggingTiler(Patch patch, PrototileSet prototileSet) {
		super(patch, prototileSet);
	}}