package org.tiling.alhambra;

import java.awt.geom.AffineTransform;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.tiling.UI;
import org.tiling.alhambra.geom.Extent; 
import org.tiling.alhambra.geom.Point2D; 
import org.tiling.alhambra.geom.Tools2D;
import org.tiling.alhambra.geom.Triangle2D;
import org.tiling.alhambra.geom.Vector2D;
import org.tiling.types.Type; 

import java.util.HashMap;import java.util.Map;/**
 * I am simple implementation of Patch.
 * @see Patch
 * @see FixedPatch
 * @see Tile
 */
public class SimplePatch implements Patch, Serializable {

	static final long serialVersionUID = 7416533693743634569L;
	









	

	






	public SimplePatch() {
	}

	public SimplePatch(Tile tile) {
		add(tile); 
	} 
 
 
 
	public List getTiles() {
		return patch.getTiles();
	}

	public Tile getTileAt(Point2D p) {
		return patch.getTileAt(p);
	}

	public Vertex getClosestCorner(Point2D p) {
		return patch.getClosestCorner(p);
	}

	public Edge getClosestSide(Point2D p) {
		return patch.getClosestSide(p);
	}

	public TileJoin add(Tile tile) {
		return execute(new TileOperation.AddTileOperation(patch, tile));
	}

	public boolean canAdd(Tile tile) {
		return patch.canAdd(tile);
	}

 
 
	public TileJoin remove(Tile tile) {
		return execute(new TileOperation.RemoveTileOperation(patch, tile));
	}

	public boolean canRemove(Tile tile) {
		return patch.canRemove(tile);
	}



	public double getPackingEfficiency() {
		return patch.getPackingEfficiency();
	}
	
	public UI getUI() {
		return patch.getUI();
	}

	public void setUI(UI patchUI) {
//		this.patchUI = patchUI;
	}



	public List getCorners() {
		return patch.getCorners();
	}

	public List getSides() {
		return patch.getSides();
	}

	public List triangulate() {
		return patch.triangulate();
	}
	
	public boolean contains(Point2D p) {
		return patch.contains(p);
	}

	public boolean overlaps(Tile tile) {
		return patch.overlaps(tile);
	}

	public Tile transform(AffineTransform t) {
		SimplePatch simplePatch = new SimplePatch();
		for (Iterator i = operations.iterator(); i.hasNext(); ) {
			TileOperation operation = (TileOperation) i.next();
			if (operation instanceof TileOperation.AddTileOperation) {
				operation = new TileOperation.AddTileOperation(simplePatch, operation.getTile().transform(t));
			} else if (operation instanceof TileOperation.RemoveTileOperation) {
				operation = new TileOperation.RemoveTileOperation(simplePatch, operation.getTile().transform(t));
			} else {
				throw new IllegalStateException("Unrecognised operation: " + operation.getClass());
			}
			simplePatch.execute(operation);
		}
		return simplePatch;
	}
	
	public double getArea() {
		return patch.getArea();
	}
	
	public double getPerimeter() {
		return patch.getPerimeter();
	}

	public double getDiameter() {
		return patch.getDiameter();
	}

	public Type getType() {
		return new Type("Patch");
	}

	public boolean equals(Object obj) {

		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			SimplePatch simplePatch = (SimplePatch) obj;
			return patch.equals(simplePatch.patch);
		}
		return false;

	}

	public int hashCode() {
		return patch.hashCode();
 	}  

	public String toString() {
		return getClass().getName() + " containing " + getTiles().size() + " tiles.";
	}
	private List operations = new ArrayList();	private FixedPatch patch = new FixedPatch();	public Object clone() {
		try {
			SimplePatch simplePatch = (SimplePatch) super.clone();
			
			if (patch != null) {
				simplePatch.patch = (FixedPatch) patch.clone();
			}

			if (operations != null) {
				simplePatch.operations = new ArrayList();
				for (int i = 0; i < operations.size(); i++) {
//					simplePatch.operations.add(((TileOperation) operations.get(i)).clone());
				}
			}
			return simplePatch;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}	private TileJoin execute(TileOperation operation) {
		operations.add(operation);
		TileJoin tileJoin = operation.execute();
		if (tileJoin == null) {
			((SimplePatchUI) patch.getUI()).addError(operation.getTile().getUI());
		}
		return tileJoin;
	}	public List getAdjacents(Tile tile) {
		return patch.getAdjacents(tile);
	}	/**
	 * I return the index of the first new side after the last tile addition or removal, or 0 if the patch is empty.
	 */
	public int getSideIndex() {
		return patch.getSideIndex();
	}}