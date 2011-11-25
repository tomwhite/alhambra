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
 * I am an implementation of Patch that is fixed, that is, calls to the {@link #transform} method
 * have no effect.
 * @see Patch
 * @see SimplePatch
 * @see Tile
 */
public class FixedPatch implements Patch, Serializable {

	static final long serialVersionUID = 7416533693743634569L;
	
	private static final double FOUR_PI = 4 * Math.PI;

	/**
	 * @serial a collection of the tiles in the patch (in the order in which
	 * they were added).
	 */
	private List tiles = new ArrayList();

	/**
	 * @serial a non-ordered collection of all the triangles in the patch.
	 */
	private List triangles = new ArrayList();

	/**
	 * @serial an ordered collection of the outer corners
	 *(<code>Vertex</code>s) in the patch.
	 */
	private List corners = new ArrayList();

	/**
	 * @serial an ordered collection of the outer sides
	 *(<code>Vertex</code>s) in the patch.
	 */
	private List sides = new ArrayList();
	
	/**
	 * @serial the total area of the patch
	 */
	private double area;
	
	/**
	 * @serial the perimeter of the patch
	 */
	private double perimeter;

	private double maxTileDiameter;

	/**
	 * @serial
	 */
	private SimplePatchUI patchUI = new SimplePatchUI();

	public FixedPatch() {
	}

	public FixedPatch(Tile tile) { 
		add(tile); 
	} 
 
 
 
	public List getTiles() {
		return tiles;
	}

	public Tile getTileAt(Point2D p) {
		for (Iterator i = tiles.iterator(); i.hasNext(); ) {
			Tile t = (Tile) i.next();
			if (t.contains(p)) {
				return t;
			}
		}
		return null;
	}

	public Vertex getClosestCorner(Point2D p) {
		Vector2D pAsVector2D = new Vector2D(p);
		double oldNorm = Double.MAX_VALUE;
		Vertex closestVertex = null;
		for (Iterator i = corners.iterator(); i.hasNext(); ) {
			Vertex v = (Vertex) i.next();
			double norm = new Vector2D(v).subtract(pAsVector2D).norm();
			if (norm < oldNorm) {
				closestVertex = v;
				oldNorm = norm;
			}
		}
		return closestVertex;
	}

	public Edge getClosestSide(Point2D p) {
		double oldNorm = Double.MAX_VALUE;
		Edge closestEdge = null;
		for (Iterator i = sides.iterator(); i.hasNext(); ) { // should be edges
			Edge e = (Edge) i.next();
			double norm = Tools2D.distance2PointToLineSegment(p, e.getV1(), e.getV2());
			if (norm < oldNorm) {
				closestEdge = e;
				oldNorm = norm;
			}
		}
		return closestEdge;	
	}

	public TileJoin add(Tile tile) {
		synchronized(tiles) {
		try{
			// A tile T can be added to the patch P iff
			// 1. P is empty OR
			// 2.i. T does not overlap P AND
			// 2.ii. T and P share at least one common edge AND
			// 2.iii. the common edges shared by T and P are contiguous AND
			// 2.iv. all edges of T that are not common to P do not intersect any edges of P
			//
			// Alternative formulation:
			// 2.iv. all edges of T that intersect edges of P are equal
			//
			// 2.iv. implies the tiling is edge-to-edge

			SimpleTileJoin joiner = new SimpleTileJoin(sides, tile);
			
			if (tiles.isEmpty()) {
				tiles.add(tile);
				patchUI.add(tile.getUI());
				sides.addAll(tile.getSides());
				corners = Edge.getVertices((Edge[]) sides.toArray(new Edge[0]));
				triangles.addAll(tile.triangulate());
				area = tile.getArea();
				perimeter = tile.getPerimeter();
				maxTileDiameter = tile.getDiameter();
				adjacents.add(tile);
				return joiner;
			}

			List common = joiner.getCommonEdges();
// System.out.println(overlaps(tile));
// System.out.println(common.isEmpty());
// System.out.println(joiner.commonEdgesContiguous());
// System.out.println(newEdgesIsCoincidentWithSides(joiner.getNewEdges()));
			if (!overlaps(tile) && !common.isEmpty() &&
				joiner.commonEdgesContiguous() && !Edge.newEdgesIsCoincidentWithSides(joiner.getNewEdges(), sides)) {

				// update tiles
				tiles.add(tile);
				patchUI.add(tile.getUI());

				// update sides and corners
				int insertionIndex = 0;
				for (ListIterator i = sides.listIterator(); i.hasNext(); ) {
					Edge edge = (Edge) i.next();
					if (common.contains(edge)) {
						i.remove();
						insertionIndex = i.nextIndex();
						perimeter -= edge.getLength();
					}
				}
				sideIndex = insertionIndex;
				for (Iterator i = joiner.getNewEdges().iterator(); i.hasNext(); ) {
					Edge edge = (Edge) i.next();
					sides.add(insertionIndex++, edge);
					perimeter += edge.getLength();
				}
				// corners is calculated from scratch. Inefficient!
				corners = Edge.getVertices((Edge[]) sides.toArray(new Edge[0]));
	
				// update triangles
				triangles.addAll(tile.triangulate());
				
				// update area
				area += tile.getArea();

				// update maximum tile diameter
				if (tile.getDiameter() > maxTileDiameter) {
					maxTileDiameter = tile.getDiameter();
				}

				// update adjacents
				adjacents.add(tile);

				return joiner;
			}
			return null;
		} finally {
			checkConsistency();
		}
		}
	}

	public boolean canAdd(Tile tile) {
		SimpleTileJoin joiner = new SimpleTileJoin(sides, tile);
		return (tiles.isEmpty() ||
			(!overlaps(tile) && !joiner.getCommonEdges().isEmpty() &&
				joiner.commonEdgesContiguous() && !Edge.newEdgesIsCoincidentWithSides(joiner.getNewEdges(), sides)));
	}

 
 
	public TileJoin remove(Tile tile) {
	
		synchronized(tiles) {
		try {
			// A tile T can be removed from the patch P iff
			// 1. T is the only tile in P OR
			// 2.i. P contains T AND
			// 2.ii. T and P share at least one common edge AND
			// 2.iii. the common edges shared by T and P are contiguous AND

			SimpleTileJoin joiner = new SimpleTileJoin(sides, tile);
						
			if (tiles.size() == 1 && tiles.contains(tile)) {
				tiles.clear();
				patchUI.remove(tile.getUI());
				sides.clear();
				corners.clear();
				triangles.clear();
				sideIndex = 0;
				area = 0.0;
				perimeter = 0.0;
				adjacents.remove(tile);
				return joiner;
			}

			List common = joiner.getCommonEdges();
			if (tiles.contains(tile) && !common.isEmpty() && joiner.commonEdgesContiguous()) {

				// update tiles
				tiles.remove(tile);
				patchUI.remove(tile.getUI());

				// update sides and corners
				int insertionIndex = 0;
				for (ListIterator i = sides.listIterator(); i.hasNext(); ) {
					Edge edge = (Edge) i.next();
					if (common.contains(edge)) {
						i.remove();
						insertionIndex = i.nextIndex();
						perimeter -= edge.getLength();
					}
				}
				sideIndex = insertionIndex;
				List newEdges = joiner.getNewEdges();
				for (int i = newEdges.size() - 1; i >= 0; i--) {
					Edge newEdge = (Edge) newEdges.get(i);
					for (Iterator j = tiles.iterator(); j.hasNext(); ) {
						List tileEdges = ((Tile) j.next()).getSides();
						if (tileEdges.contains(newEdge)) {
							Edge edge = (Edge) tileEdges.get(tileEdges.indexOf(newEdge));
							sides.add(insertionIndex++, edge);
							perimeter += edge.getLength();
							break;
						}
					}
				}
				// corners is calculated from scratch. Inefficient!
				corners = Edge.getVertices((Edge[]) sides.toArray(new Edge[0]));
				
				// update triangles
				triangles.removeAll(tile.triangulate());

				// update area
				area -= tile.getArea();

				// update adjacents
				adjacents.remove(tile);

				return joiner;
			}
			return null;
		} finally {
			checkConsistency();
		}
		}
	}

	public boolean canRemove(Tile tile) {
		SimpleTileJoin joiner = new SimpleTileJoin(sides, tile);
		return (tiles.size() == 1 && tiles.contains(tile) ||
			tiles.contains(tile) && !joiner.getCommonEdges().isEmpty() && joiner.commonEdgesContiguous());
	}

	private void checkConsistency() {
		if (getTiles() == null) {
			System.err.println("tiles null");
		} else if (triangulate() == null) {
			System.err.println("triangles null");
		} else if (getSides() == null) {
			System.err.println("sides null");
		} else if (getCorners() == null) {
			System.err.println("corners null");
		} else if (getSides().size() != getCorners().size()) {
			System.err.println("inconsistent");
			System.err.print("#sides: " + getSides().size());
			System.err.println(", #corners: " + getCorners().size());
		} else {
			return;
		}
/*
		// Used to catch malformed tilings for investigation!
		org.tiling.util.Serializer.serialize(this, new java.io.File("inconsistentPatch.ser"));
		System.exit(1);
*/
	}

	public double getPackingEfficiency() {
		return FOUR_PI * area / (perimeter * perimeter);
	}
	
	public UI getUI() {
		return patchUI;
	}

	public void setUI(UI patchUI) {
//		this.patchUI = patchUI;
	}

	private List getNeighbourhood(Point2D p) {
		double d = 4 * maxTileDiameter * maxTileDiameter;
		List neighbourhood = new ArrayList();
		for (Iterator i = tiles.iterator(); i.hasNext(); ) {
			Tile tile = (Tile) i.next();
			if (Tools2D.distance2(p, (Point2D) tile.getCorners().get(0)) < d + Tools2D.eps) {
				neighbourhood.add(tile);
			}
		}
		return neighbourhood;
	}

	public List getCorners() {
		return corners;
	}

	public List getSides() {
		return sides;
	}

	public List triangulate() {
		return triangles;
	}
	
	public boolean contains(Point2D p) {
		for (Iterator i = triangles.iterator(); i.hasNext(); ) {
			if (Tools2D.insideTriangle((Triangle2D) i.next(), p)) {
				return true;
			}
		}
		return false;
	}

	public boolean overlaps(Tile tile) {
		List neighbourhood = getNeighbourhood(tile);
		for (Iterator i = neighbourhood.iterator(); i.hasNext(); ) {
			Tile neighbourhoodTile = (Tile) i.next();
			if (tile.overlaps(neighbourhoodTile)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * I do nothing but return a clone of myself. That is, I ignore the
	 * given <code>AffineTransform</code>.
	 * @see SimplePatch
	 */
	public Tile transform(AffineTransform t) {
		return (Tile) clone();
	}
	
	public double getArea() {
		return area;
	}
	
	public double getPerimeter() {
		return perimeter;
	}

	public double getDiameter() {
		double diameter = 0.0;
		if (!corners.isEmpty()) {
			Extent extent = new Extent((Vertex) corners.get(0)); 
			for (Iterator i = corners.iterator(); i.hasNext(); ) { 
				extent.add((Vertex) i.next()); 
			}
			diameter = extent.getDiameter(); 	
		}
		return diameter;	
	}

	public Type getType() {
		return new Type("Patch");
	}

	public boolean equals(Object obj) {

		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			FixedPatch patch = (FixedPatch) obj;
			return tiles.equals(patch.tiles);
		}
		return false;

	}

	public int hashCode() {
		return tiles.hashCode();
 	}  

	public String toString() {
		return getClass().getName() + " containing " + tiles.size() + " tiles.";
	}
	private Adjacents adjacents = new Adjacents();	private int sideIndex = 0;	class Adjacents implements Serializable {
		private Map tilesToAdjacents = new HashMap();
		public boolean areAdjacent(Tile tile1, Tile tile2) {
			for (Iterator i = tile1.getSides().iterator(); i.hasNext(); ) {
				Edge tile1Edge = (Edge) i.next();
				for (Iterator j = tile2.getSides().iterator(); j.hasNext(); ) {
					Edge tile2Edge = (Edge) j.next();
					if (tile1Edge.equals(tile2Edge)) {
						return true;
					}
				}
			}
			return false;
		}
		public void add(Tile tile) {
			List adjacents = (List) tilesToAdjacents.get(tile);
			if (adjacents == null) {
				adjacents = new ArrayList();
			}
			List neighbourhood = getNeighbourhood(tile);
			for (Iterator i = neighbourhood.iterator(); i.hasNext(); ) {
				Tile neighbourhoodTile = (Tile) i.next();
				if (!tile.equals(neighbourhoodTile) && areAdjacent(tile, neighbourhoodTile)) {
					add(adjacents, neighbourhoodTile);
					List neighbourhoodTileAdjacents = (List) tilesToAdjacents.get(neighbourhoodTile);
					add(neighbourhoodTileAdjacents, tile);
					tilesToAdjacents.put(neighbourhoodTile, neighbourhoodTileAdjacents);
				}
			}
			tilesToAdjacents.put(tile, adjacents);
		}
		private void add(List adjacents, Tile tile) {
			if (!adjacents.contains(tile)) {
				adjacents.add(tile);
			}
		}
		public void remove(Tile tile) {
			List neighbourhood = getNeighbourhood(tile);
			for (Iterator i = neighbourhood.iterator(); i.hasNext(); ) {
				Tile neighbourhoodTile = (Tile) i.next();
				List neighbourhoodTileAdjacents = (List) tilesToAdjacents.get(neighbourhoodTile);
				if (neighbourhoodTileAdjacents.remove(tile)) {
					tilesToAdjacents.put(neighbourhoodTile, neighbourhoodTileAdjacents);
				}
			}
			tilesToAdjacents.remove(tile);
		}
		public List getAdjacents(Tile tile) {
			return (List) tilesToAdjacents.get(tile);
		}
	}	public Object clone() {
		try {
			FixedPatch patch = (FixedPatch) super.clone();
			
			if (tiles != null) {
				patch.tiles = new ArrayList();
				for (int i = 0; i < tiles.size(); i++) {
					patch.tiles.add(((Tile) tiles.get(i)).clone());
				}
			}

			if (triangles != null) {
				patch.triangles = new ArrayList();
				for (int i = 0; i < triangles.size(); i++) {
					patch.triangles.add(((Triangle2D) triangles.get(i)).clone());
				}
			}

			if (corners != null) {
				patch.corners = new ArrayList();
				for (int i = 0; i < corners.size(); i++) {
					patch.corners.add(((Vertex) corners.get(i)).clone());
				}
			}
			
			if (sides != null) {
				patch.sides = new ArrayList();
				for (int i = 0; i < sides.size(); i++) {
					patch.sides.add(((Edge) sides.get(i)).clone());
				}
			}
	
			patch.patchUI = (SimplePatchUI) patchUI.clone();
			
			return patch;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}	public List getAdjacents(Tile tile) {
		return adjacents.getAdjacents(tile);
	}	private List getNeighbourhood(Tile tile) {
		return getNeighbourhood((Vertex) tile.getCorners().get(0));
	}	/**
	 * I return the index of the first new side after the last tile addition or removal, or 0 if the patch is empty.
	 */
	public int getSideIndex() {
		return sideIndex;
	}}