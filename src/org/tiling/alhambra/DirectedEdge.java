package org.tiling.alhambra;

import org.tiling.alhambra.geom.*;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * I am a pair of Vertex objects and a direction (forwards
 * or backwards) used to represent the side of a Tile.
 * I can also have an associated mark to allow matching requirements
 * to be specified.
 * @see Edge
 * @see Vertex
 */
public class DirectedEdge extends Edge {

	static final long serialVersionUID = -523505345074075554L;

	/**
	 * @serial the edge direction.
	 */
	private boolean forward;

	public DirectedEdge(Vertex v1, Vertex v2) {
		this(v1, v2, 0, true);
	}

	public DirectedEdge(Vertex v1, Vertex v2, int mark) {
		this(v1, v2, mark, true);
	}

	public DirectedEdge(Vertex v1, Vertex v2, boolean forward) {
		this(v1, v2, 0, forward);
	}

	public DirectedEdge(Vertex v1, Vertex v2, int mark, boolean forward) {
		super(v1, v2, mark);
		this.forward = forward;
	}

	public Edge transform(AffineTransform t) {
		Vertex v1Transformed = (Vertex) v1.clone();
		Vertex v2Transformed = (Vertex) v2.clone();
		t.transform(v1, v1Transformed);
		t.transform(v2, v2Transformed);
		return new DirectedEdge(v1Transformed, v2Transformed, mark, forward);
	}

	public Edge reverse() {
		forward = !forward;
		Vertex temp = v1;
		v1 = v2;
		v2 = temp;
		return this;
//		return new Edge(v2, v1, mark, !forward); // Immutable, but doesn't affect equals???
	}

	/**
	 * Returns true if the specified edge is also a directed edge, the two edges have
	 * equal vertices <em>taken in either order</em> but in opposing directions,
	 * and the two edges have equal
	 * mark fields.
	 * @return true if the specified edge matches this edge.
	 * @see #equals
	 */
	public boolean matches(Edge edge) {

		if (edge != null && (edge.getClass().equals(this.getClass()))) {
			DirectedEdge directedEdge = (DirectedEdge) edge;
			return edge.mark == mark &&
					((v1.equals(edge.v1) && v2.equals(edge.v2) && forward == directedEdge.forward) ||
					(v2.equals(edge.v1) && v1.equals(edge.v2) && forward == !directedEdge.forward));
//			return edge.mark == mark &&
//					(v2.equals(edge.v1) && v1.equals(edge.v2) && forward == !directedEdge.forward);
		}
		return false;

	}

	public String toString() {
		return "DirectedEdge[(" + v1.x + ", " + v1.y + ")-(" + v2.x + ", " + v2.y + ")]";
	}

}