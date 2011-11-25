package org.tiling.alhambra;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap; 
import java.util.Iterator; 
import java.util.List; 
import java.util.Map; 

import org.tiling.alhambra.geom.*;

/**
 * I am an encapsulation of a Tile and its symmetries in a tiling up to translation.
 * Prototiles are immutable.
 * @see Tile
 * @see SymmetryGroup
 * @see PrototileSet
 */
public class Prototile implements Serializable {

	static final long serialVersionUID = -5630855790771385869L;

	/**
	 * @serial a tile used to define the prototile.
	 */
	private Tile tile;

	/**
	 * @serial the symmetry used to define the prototile.
	 */
	private SymmetryGroup symmetry;

	/** 
	 * @serial the transformed tiles. 
	 */ 
	private List transformedTiles; 
 
	/** 
	 * @serial the transformed tiles keyed by a SymbolicTransform series. 
	 */ 
	private Map transformedTilesMap; 
 
	/**
	 * Constructs a prototile that is the unit square.
	 */
	public Prototile() {
		this(new SimpleTile(
					new Vertex[] {
						new Vertex(0.0, 0.0),
						new Vertex(1.0, 0.0),
						new Vertex(1.0, 1.0),
						new Vertex(0.0, 1.0),
					}
				),
			SymmetryGroup.E);
	}

	// If no vertex of tile is the origin tile will be translated so one (arbitrary) vertex is the origin.
	// (This is not currently implemented.)
	public Prototile(Tile tile, SymmetryGroup symmetry) {
		this.tile = tile;
		this.symmetry = symmetry;

		transformedTiles = new ArrayList();
		transformedTilesMap = new HashMap();

		SymbolicTransform[] symbolicTransforms = symmetry.getSymbolicTransforms();
		for (int i = 0; i < symbolicTransforms.length; i++) { 
			Tile newTile = tile.transform(symbolicTransforms[i].getAffineTransform()); 
			transformedTiles.add(newTile);
			transformedTilesMap.put(symbolicTransforms[i].getCanonicalSeries(), newTile);
		} 
	}

	public Tile getTile() {
		return tile;
	}

	public SymmetryGroup getSymmetryGroup() {
		return symmetry;
	}

	// Returns a List of transformations of tile, including tile itself.
	public List getTransformedTiles() {
		return Collections.unmodifiableList(transformedTiles);
	}

	public Tile getTransformedTile(SymbolicTransform symbolicTransform) { 
		return (Tile) transformedTilesMap.get(symbolicTransform.getCanonicalSeries()); 
	} 

	/**
	 * @return true if tile is amongst the transformed tiles of this prototile
	 */
	public boolean matches(Tile tile) {
		return transformedTiles.contains(tile);
	}

	public boolean equals(Object obj) {

		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			Prototile p = (Prototile) obj;
			return p.getTile().equals(tile) && p.getSymmetryGroup().equals(symmetry);
		}
		return false;

	}

	public int hashCode() {
		int bits = tile.hashCode();
		bits = bits * 31 ^ symmetry.hashCode();
		return bits;
 	} 

	public String toString() {
		return getClass().getName() + "[" + tile.getClass().getName() + ", " + symmetry.toString() + "]";
	}

}