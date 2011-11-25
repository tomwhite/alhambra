package org.tiling.alhambra;

import org.tiling.alhambra.geom.*;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * I am a pair of Vertex objects and a marked, directed curve
 * used to represent the side of a Tile.
 * @see Edge 
 * @see Curve 
 * @see Vertex
 */
public class LabelledEdge extends Edge {

	public static final int FORWARD_DIRECTION = 1;
	public static final int UNDIRECTED = 0;
	public static final int BACKWARD_DIRECTION = -1;

	protected int direction;
	protected Curve curve;

	public LabelledEdge(Vertex v1, Vertex v2) { 
		this(v1, v2, 0, Curve.C_CURVE);
	}

	public LabelledEdge(Vertex v1, Vertex v2, int mark, Curve curve) { 
		super(v1, v2, mark);
		this.direction = curve.getInitialDirection();
		this.curve = curve;
	}

	public int getDirection() {
		return direction;
	}

	public Edge reverse() {
		direction = -direction;
		if (curve.reverseMark()) { // depends on the curve type...
			mark = -mark;
		}
		Vertex temp = v1; 
		v1 = v2; 
		v2 = temp; 
		return this; 
	} 

	public Edge transform(AffineTransform t) { 
		Vertex v1Transformed = (Vertex) v1.clone(); 
		Vertex v2Transformed = (Vertex) v2.clone(); 
		t.transform(v1, v1Transformed); 
		t.transform(v2, v2Transformed); 
		return new LabelledEdge(v1Transformed, v2Transformed, mark, curve); 
	}

	/**
	 * Unlike Edge, markings <i>are</i> important.
	 * Equals is stronger, to make it easier to use Tiles in collections, for example.
	 */
	public boolean equals(Object obj) { 
		if (obj instanceof Edge) {
			return matches((Edge) obj);
		}
		return false;
	}

	public boolean matches(Edge edge) { 
		if (edge != null && (edge.getClass().equals(this.getClass()))) { 
			return curve.matches(this, (LabelledEdge) edge);
		}
		return false;
	}

	public String toString() { 
		return "LabelledEdge[(" + v1.x + ", " + v1.y + ")-(" + v2.x + ", " + v2.y + "), " + mark + ", " + direction + ", " + curve + "]"; 
	}

}