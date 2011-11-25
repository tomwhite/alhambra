package org.tiling.alhambra;

import java.awt.geom.AffineTransform;
import java.util.List;

import org.tiling.UI;
import org.tiling.alhambra.geom.*;
import org.tiling.types.Typed; 


/**
 * I represent a tile - that is, a topological disk with a single simple closed curve
 * boundary. (But note this is not the most general definition - although it is
 * the most workable.) A Tile is defined by its corners and sides.
 * <p>
 * All <code>Tile</code> implementations should be immutable and
 * <code>Serializable</code>.
 */
public interface Tile extends Cloneable, Typed {
	/**
	 * @return a list of Vertex objects in ccw order
	 * @see Vertex
	 */
	public List getCorners();

	/**
	 * @return a list of Edge objects in ccw order
	 * @see Edge
	 */
	public List getSides();

	/**
	 * @return a list of Triangle2D objects that cover the tile
	 * @see org.tiling.alhambra.geom.Triangle2D
	 */
	public List triangulate();
	
	/**
	 * @return <code>true</code> if this tile contains the point p, or p lies on an
	 * edge.
	 */
	public boolean contains(Point2D p);

	/**
	 * @return <code>true</code> if this tile has a non-zero area of
	 * intersection with the tile t.
	 */	
	public boolean overlaps(Tile t);
	
	/**
	 * @return a transformed clone of this tile.
	 */	
	public Tile transform(AffineTransform t);

	/**
	 * @return the area of this tile.
	 */		
	public double getArea();

	/** 
	 * @return the perimeter of this tile. 
	 */ 
	 public double getPerimeter(); 
	 
	/** 
	 * @return the diameter of this tile. The tile should fit in a circle of
	 * the diameter returned. Note that this is an upper bound.
	 */ 
	 public double getDiameter(); 
	 
	public UI getUI();

	public void setUI(UI tileUI);

	public Object clone();}