package org.tiling.alhambra;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tiling.UI;
import org.tiling.alhambra.geom.*;
import org.tiling.types.Type; 

/**
 * I am a simple implementation of Tile.
 * @see Tile
 */
public class SimpleTile implements Tile, Cloneable, Serializable {

	static final long serialVersionUID = 1930848463375682188L;

	/**
	 * @serial the transform that is carried out on all elements of
	 * the tile to give the tile's absolute position.
	 */
	public AffineTransform preTransformation;

	/**
	 * @serial a marker showing if this tile is a "vertices-oriented" or
	 * a "edges-oriented" tile. Indicates which constructor was used.
	 */
	private boolean vertexOriented;

	/**
	 * @serial an array of the (actual) vertices of the tile.
	 */
	protected Vertex[] vertices;

	/**
	 * @serial an array of the edges of the tile.
	 */
	protected Edge[] edges;

	/**
	 * @serial a collection of the corners (<code>Vertex</code>s) of the tile.
	 */
	protected List corners;

	/**
	 * @serial a collection of the sides (<code>Edge</code>s) of the tile.
	 */
	protected List sides;

	/**
	 * @serial the triangulation of the tile (<code>Triangle2D</code>s).
	 */
	protected List triangles;

	/**
	 * @serial the area of the tile.
	 */
	private double area;

	/** 
	 * @serial the perimeter of the tile. 
	 */ 
	private double perimeter; 
 
	/** 
	 * @serial the diameter of the tile. 
	 */ 
	private double diameter; 
 
	/**
	 * @serial
	 */
	private UI tileUI;

	/**
	 * Subclasses should initialise vertices in the constructor, then
	 * edges and triangles will be calculated automatically when needed.
 	 */
	public SimpleTile(Vertex[] vertices) {
		this(vertices, new AffineTransform());
	}

	public SimpleTile(Vertex[] vertices, AffineTransform t) {
		this(true, vertices, null, t);
	}

	/**
	 * Subclasses should initialise edges in the constructor, then
	 * vertices and triangles will be calculated automatically when needed.
 	 */
	public SimpleTile(Edge[] edges) {
		this(edges, new AffineTransform());
	}

	public SimpleTile(Edge[] edges, AffineTransform t) {
		this(false, null, edges, t);
	}



	private void initializeVertices(Vertex[] vertices, AffineTransform t) {

		// Copy vertices into originalVertices and this.vertices arrays
		Vertex[] originalVertices = new Vertex[vertices.length];
		this.vertices = new Vertex[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			this.vertices[i] = (Vertex) vertices[i].clone();
			originalVertices[i] = (Vertex) vertices[i].clone();
			// ... and transform
			t.transform(originalVertices[i], this.vertices[i]); 
		}

		// If t is not orientation-preserving, then reverse order of this.vertices
		if (t.getDeterminant() < 0.0) {
			this.vertices = Vertex.reverse(this.vertices);
		}
	}

	private void initializeEdges(Edge[] edges, AffineTransform t) {

		// Copy edges into originalEdges and this.edges arrays
		Edge[] originalEdges = new Edge[edges.length];
		this.edges = new Edge[edges.length];
		for (int i = 0; i < edges.length; i++) {
			originalEdges[i] = (Edge) edges[i].clone();
			// ... and transform
			this.edges[i] = originalEdges[i].transform(t);
		}

		// If t is not orientation-preserving, then reverse order of this.edges
		if (t.getDeterminant() < 0.0) {
			this.edges = Edge.reverse(this.edges);
		}
	}

	public static void transform(AffineTransform t, Vertex[] vertexSrc, Vertex[] vertexDst) {
		for (int i = 0; i < vertexDst.length; i++) { 
			t.transform(vertexSrc[i], vertexDst[i]); 
		} 
	}

	public List getCorners() {
		if (corners == null) {
			if (vertices != null) {
				corners = Arrays.asList(vertices);
			}
		}
		return corners;
	}

	public List getSides() {
		if (sides == null) {
			if (edges != null) {
				sides = Arrays.asList(edges);
			}
		}
		return sides;
	}

	public List triangulate() {
		if (triangles == null) {
			triangles = new ArrayList();
			Tools2D.triangulate(vertices, triangles);
		}
		return triangles;
	}

	public boolean contains(Point2D p) {
		// N.B. This is not strict containment (at the moment)
		for (Iterator i = triangulate().iterator(); i.hasNext(); ) {
			if (Tools2D.insideTriangle((Triangle2D) i.next(), p)) {
				return true;
			}
		}
		return false;
	}

	public Tile transform(AffineTransform t) {

		t = new AffineTransform(t);
		Tile tile;
		if (vertexOriented) {
			tile = new SimpleTile(vertices, t);
		} else {
			tile = new SimpleTile(edges, t);
		}

		// Very important to preserve UI properties on the new tile
		tile.getUI().setBackground(getUI().getBackground());

		return tile;
	}

	public boolean overlaps(Tile tile) {

		List tileTriangles = tile.triangulate();
		for (Iterator i = triangulate().iterator(); i.hasNext(); ) {
			Triangle2D triangle = (Triangle2D) i.next();
			for (Iterator j = tileTriangles.iterator(); j.hasNext(); ) {
				if (Tools2D.overlaps(triangle, (Triangle2D) j.next())) {
					return true;
				}			
			}
		}
		return false;
	}
	
	public double getArea() {
		if (area == 0.0) {
			area = 0.5 * Tools2D.area2(vertices);
		}
		return area;	
	}

	public double getPerimeter() {
		if (perimeter == 0.0) {
			for (Iterator i = getSides().iterator(); i.hasNext(); ) {
				perimeter += ((Edge)i.next()).getLength();
			}
		}
		return perimeter;	
	}

	public double getDiameter() {
		if (diameter == 0.0) { 
			Extent extent = new Extent(vertices[0]); 
			for (int i = 1; i < vertices.length; i++) { 
				extent.add(vertices[i]); 
			}
			diameter = extent.getDiameter(); 	
		} 
		return diameter;	
	}

	public UI getUI() {
		return tileUI;
	}

	public void setUI(UI tileUI) {
		this.tileUI = tileUI;
	}
	
	/**
	 * N.B. Order of vertices and edges is significant.
	 */
	public boolean equals(Object obj) {

		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			SimpleTile tile = (SimpleTile) obj;
			return getCorners().equals(tile.getCorners()) && getSides().equals(tile.getSides());
		}
		return false;

	}

	public int hashCode() {
		int bits = getCorners().hashCode();
		bits = bits * 31 ^ getSides().hashCode();
		return bits;
 	} 

	public String toString() {
		StringBuffer sb = new StringBuffer(getClass().getName());
		sb.append(":{");
		for (int i = 0; i < vertices.length; i++) {
			sb.append(vertices[i]);
			sb.append(", ");
		}
		sb.append("}");
		return sb.toString();
	}

	public Type getType() {
		// See SimpleType

		List hierarchy = new ArrayList(); 
		Class clazz = getClass(); 
		while (clazz != null) { 
			hierarchy.add(0, clazz); 
			clazz = clazz.getSuperclass(); 
		} 
 
		// walk down hierarchy constructing types 
		Type type = null; 
		for (Iterator i = hierarchy.iterator(); i.hasNext(); ) { 
			clazz = (Class) i.next(); 
			String name = clazz.getName(); 
			type = new Type(name.substring(name.lastIndexOf('.') + 1), type); 
		} 
		return type; 
	}
	protected SimpleTile(boolean vertexOriented, Vertex[] vertices, Edge[] edges, AffineTransform t) {
		initialize(vertexOriented, vertices, edges, t);
		tileUI = new SimpleTileUI(this.vertices, preTransformation); 
	}	public Object clone() {
		try {
			SimpleTile tile = (SimpleTile) super.clone();
			
			tile.preTransformation = (AffineTransform) preTransformation.clone();

			tile.vertices = (Vertex[]) vertices.clone();	
			for (int i = 0; i < vertices.length; i++) {
				tile.vertices[i] = (Vertex) vertices[i].clone();
			}
			
			tile.edges = (Edge[]) edges.clone();	
			for (int i = 0; i < edges.length; i++) {
				tile.edges[i] = (Edge) edges[i].clone();
			}

			if (corners != null) {
				tile.corners = new ArrayList();
				for (int i = 0; i < corners.size(); i++) {
					tile.corners.add(((Vertex) corners.get(i)).clone());
				}
			}
			
			if (sides != null) {
				tile.sides = new ArrayList();
				for (int i = 0; i < sides.size(); i++) {
					tile.sides.add(((Edge) sides.get(i)).clone());
				}
			}
			
			if (triangles != null) {
				tile.triangles = new ArrayList();
				for (int i = 0; i < triangles.size(); i++) {
					tile.triangles.add(((Triangle2D) triangles.get(i)).clone());
				}
			}

			tile.tileUI = (UI) tileUI.clone();
			
			return tile;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}	private void initialize(boolean vertexOriented, Vertex[] vertices, Edge[] edges, AffineTransform t) {
		this.vertexOriented = vertexOriented;
		if (vertexOriented) {
			initializeVertices(vertices, t);
			this.edges = (Edge[]) Edge.getEdges(this.vertices).toArray(new Edge[this.vertices.length]);
		} else {
			initializeEdges(edges, t);
			this.vertices = (Vertex[]) Edge.getVertices(this.edges).toArray(new Vertex[this.edges.length]);
		}
		preTransformation = t;
	}}