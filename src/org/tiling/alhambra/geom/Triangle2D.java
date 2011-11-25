package org.tiling.alhambra.geom;

import java.io.Serializable;

/**
 * I am a collection of three points. Typically these must be ccw.
 */
public class Triangle2D implements Cloneable, Serializable {

	static final long serialVersionUID = -3509159361733272833L;

	/**
	 * @serial a point of the triangle
	 */
	public Point2D A, B, C;

	/**
	 * Constructs a Triangle from points A, B and C.
	 */
	public Triangle2D(Point2D A, Point2D B, Point2D C) {
		this.A = A;
		this.B = B;
		this.C = C;
	} 

	public String toString() {
		return getClass().getName() + "[" + A.toString() + "-" + B.toString() + "-" + C.toString() + "]";
	}

	public int hashCode() {
		return 31 * 31 * A.hashCode() ^ 31 * B.hashCode() ^ C.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			Triangle2D t = (Triangle2D) obj;
			return A.equals(t.A) && B.equals(t.B) && C.equals(t.C);
		}	
		return false;
	}
	public Object clone() {
		try {
			Triangle2D t = (Triangle2D) super.clone();
			t.A = (Point2D) A.clone();
			t.B = (Point2D) B.clone();
			t.C = (Point2D) C.clone();
			return t;
		} catch (CloneNotSupportedException e) {
			throw new InternalError(); // should never happen
		}
	}}