package org.tiling.alhambra;

import org.tiling.alhambra.geom.*;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.util.Iterator;/**
 * I am a pair of Vertex objects used to represent the side of a Tile.
 * I can also have an associated mark to allow matching requirements
 * to be specified.
 * @see Vertex
 */
public class Edge implements Cloneable, Serializable {

	static final long serialVersionUID = 6710123000954284408L;

	/**
	 * @serial the end vertices of the edge.
	 */
	protected Vertex v1, v2;



	/**
	 * @serial an integer used to mark the edge for matching purposes.
	 */
	protected int mark;
	
	/**
	 * @serial the length of the edge
	 */
	private double length;

	public Edge(Vertex v1, Vertex v2) {
		this(v1, v2, 0);
	}

	public Edge(Vertex v1, Vertex v2, int mark) {
		this.v1 = v1;
		this.v2 = v2;
		this.mark = mark;
		length = Math.sqrt(getVector2D().norm());
	}

	public Vertex getV1() {
		return v1;
	}

	public Vertex getV2() {
		return v2;
	}

	public Vector2D getVector2D() {
		return new Vector2D(v2.x - v1.x, v2.y - v1.y);
	}

	public int getMark() {
		return mark;
	}
	
	public double getLength() {
		return length;	
	}

	public Edge transform(AffineTransform t) {
		Vertex v1Transformed = (Vertex) v1.clone();
		Vertex v2Transformed = (Vertex) v2.clone();
		t.transform(v1, v1Transformed);
		t.transform(v2, v2Transformed);
		return new Edge(v1Transformed, v2Transformed, mark);
	}

	public Edge reverse() {
		Vertex temp = v1;
		v1 = v2;
		v2 = temp;
		return this;
//		return new Edge(v2, v1, mark); // Immutable, but doesn't affect equals???
	}


	/**
	 * Compares the specified object with this edge for equality.
	 * Returns true if the specified object is also an edge, and the two edges have
	 * equal vertices <em>taken in either order</em>.
	 * Note that edge markings are not significant - use the matches method.
	 * @return true if the specified object is equal to this edge.
	 * @see #matches
	 */
	public boolean equals(Object obj) {

//		if (obj != null && (obj.getClass().equals(this.getClass()))) {
		if (obj instanceof Edge) {
			Edge e = (Edge) obj;
			return ((v1.equals(e.v1) && v2.equals(e.v2))
				|| (v1.equals(e.v2) && v2.equals(e.v1)));
		}
		return false;

	}

	/**
	 * Returns true if the specified edge is also an edge, the two edges have
	 * equal vertices <em>taken in either order</em>, and the two edges have equal
	 * mark fields.
	 * @return true if the specified edge matches this edge.
	 * @see #equals
	 */
	public boolean matches(Edge edge) {

		if (edge != null && (edge.getClass().equals(this.getClass()))) {
			return edge.mark == mark && ((v1.equals(edge.v1) && v2.equals(edge.v2))
				|| (v1.equals(edge.v2) && v2.equals(edge.v1)));
//			return edge.mark == mark && (v1.equals(edge.v2) && v2.equals(edge.v1));
		}
		return false;

	}

	public int hashCode() {
		int bits = mark;
		bits = bits * 31 ^ v1.hashCode();
		bits = bits * 31 ^ v2.hashCode();
		return bits;
 	} 

	public String toString() {
		return "Edge[(" + v1.x + ", " + v1.y + ")-(" + v2.x + ", " + v2.y + ")]";
	}


	public Object clone() {
		try {
			Edge edge = (Edge) super.clone();
			edge.v1 = (Vertex) v1.clone();
			edge.v2 = (Vertex) v2.clone();
			return edge;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}

	/**
	 * @param polygon an array of Vertex objects of length at least 2
	 * @return a list of unmarked Edge objects constructed from polygon, taking
	 * vertices in pairs and joining the final vertex to the first (if there are
	 * more than 2 vertices)
	 */
	public static final List getEdges(Vertex[] polygon) {
		List edges = new ArrayList();
		if (polygon.length == 2) {
			edges.add(new Edge(polygon[0], polygon[1]));
		} else if (polygon.length > 2) {
			for (int i = 0; i < polygon.length - 1; i++) {
				edges.add(new Edge(polygon[i], polygon[i + 1]));
			}
			edges.add(new Edge(polygon[polygon.length - 1], polygon[0]));
		}
		return edges;
	}

	/**
	 * The following constraints must be followed (otherwise null is returned).
	 * If polygon.length is 2 then marks.length must be 1 and
	 * marks[0] is the mark for the edge polygon[0] to polygon[1];
	 * <br>
	 * otherwise polygon.length must equal marks.length,
	 * marks[i] is the mark for the edge polygon[i] to polygon[i + 1], 0 <= i < polygon.length - 1, and
	 * marks[polygon.length - 1] is the mark for the edge polygon[polygon.length - 1] to polygon[0].
	 * @param polygon an array of Vertex objects of length at least 2
	 * @param marks an array of Vertex objects of length at least 2
	 * @return a list of marked Edge objects constructed from polygon, taking
	 * vertices in pairs and joining the final vertex to the first (if there are
	 * more than 2 vertices)
	 */
	public static final List getEdges(Vertex[] polygon, int[] marks) {
		List edges = new ArrayList();
		if (polygon.length == 2 && marks.length == 1) {
			edges.add(new Edge(polygon[0], polygon[1], marks[0]));
		} else if (polygon.length > 2 && polygon.length == marks.length) {
			for (int i = 0; i < polygon.length - 1; i++) {
				edges.add(new Edge(polygon[i], polygon[i + 1], marks[i]));
			}
			edges.add(new Edge(polygon[polygon.length - 1], polygon[0], marks[marks.length - 1]));
		} else {
			return null;
		}
		return edges;
	}

	/**
	 * @param edges an array of Edge objects
	 * @return a list of unmarked Vertex objects constructed from edges, or null
	 * if edges has discontinuous or 'twisted' (reverse) edges
	 */
	public static final List getVertices(Edge[] edges) {

		List vertices = new ArrayList();
		if (edges.length == 0) {
			return vertices;
		}
		vertices.add(edges[0].v1);
		vertices.add(edges[0].v2);
		for (int i = 1; i < edges.length - 1; i++) {
			Vertex lastVertex = (Vertex) vertices.get(vertices.size() - 1);
			if (lastVertex.equals(edges[i].v1)) {
				if (i != edges.length - 1 || !edges[0].v1.equals(edges[i].v2) ) {
					vertices.add(edges[i].v2);
				}
			} else {
				// throw an exception?
//for (int j =0; j<edges.length; j++) {System.out.println(edges[j]);}
//new Throwable().printStackTrace();
				return null; // error: cannot have discontinuous or 'twisted' (reverse) edges
			}
		}
		return vertices;
	}

	/** 
	 * @param a an array of Edge objects (note that these objects are changed)
	 * @return an array of reversed Edge objects in reverse order to the input a
	 */
	public static Edge[] reverse(Edge[] a) { 
		Edge[] reversed = (Edge[]) a.clone(); 
		for (int i = 0; i < a.length; i++) { 
			reversed[i] = a[a.length - i - 1].reverse(); 
		} 
		a = reversed; 
		return reversed; 
	} 

	public static boolean newEdgesIsCoincidentWithSides(List newEdges, List sides) {
		for (Iterator i = newEdges.iterator(); i.hasNext(); ) {
			Edge newEdge = (Edge) i.next();
			if (i.hasNext()) {
				Vertex v = newEdge.getV2();
				for (Iterator j = sides.iterator(); j.hasNext(); ) {
					Edge side = (Edge) j.next();
					if (Tools2D.onLineSegment(v, side.getV1(), side.getV2())) {
						return true;
					}
				}
			}
		}
		return false;
	}}