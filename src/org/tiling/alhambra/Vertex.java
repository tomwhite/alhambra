package org.tiling.alhambra;

import java.io.*;

import org.tiling.alhambra.geom.*;

/**
 * I am a point used to represent the corner of a Tile.
 * I can also have an associated mark to allow matching requirements
 * to be specified.
 * @see Edge
 */
public class Vertex extends Point2D implements Cloneable, Serializable {

	static final long serialVersionUID = -1608179193639066747L;

	/**
	 * A Vertex located at the origin.
	 */
	public static final Vertex ORIGIN = new Vertex(0.0, 0.0);

	/**
	 * @serial an integer used to mark the vertex for matching purposes.
	 */
	public int mark;

	/**
	 * Construct a Vertex located at the origin.
	 */
	public Vertex() {
		this(0.0, 0.0);
	}

	public Vertex(double x, double y) {
		this(x, y, 0);
	}

	public Vertex(double x, double y, int mark) {
		super(x, y);
		this.mark = mark;
	}

	public static boolean matches(int label1, int label2) {
		if (label1 == 0 || label2 == 0) {
			return true;
		} else {
			return label1 == label2;
		}
	}

	/**
	 * Compares the specified object with this vertex for equality.
	 * Returns true if the specified object is also a vertex, the two vertices are coincident
	 * <em>modulo rounding errors</em>, and the two vertices have equal mark fields.
	 * @return true if the specified object is equal to this vertex.
	 * @see org.tiling.alhambra.geom.Tools2D#coincident
	 */
	public boolean equals(Object obj) {

		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			Vertex v = (Vertex) obj;
			return matches(v.mark, mark) && Tools2D.coincident(v, this);
		}
		return false;

	}

	public int hashCode() {
		int bits = mark;
		bits = bits * 31 ^ super.hashCode();
		return bits;
 	} 

	public String toString() {
		return getClass().getName() + "(" + x + ", " + y + "; " + mark + ")";
	}

	/** 
	 * @param a an array of Vertex objects (note that the reference to a is changed to be the reversed array)
	 * @return an array of Vertex objects r, such that r[0] == a[0], and r[i] == a[a.length - i] for i > 0.
	 * 
	 */
	public static Vertex[] reverse(Vertex[] a) { 
		Vertex[] reversed = (Vertex[]) a.clone(); 
		reversed[0] = a[0]; 
		for (int i = 1; i < a.length; i++) { 
			reversed[i] = a[a.length - i]; 
		} 
		a = reversed; 
		return reversed; 
	} 



}