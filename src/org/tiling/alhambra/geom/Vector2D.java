package org.tiling.alhambra.geom;

import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * I am an ordered-pair of doubles, like Point2D but representing a different concept.
 */
public class Vector2D implements Cloneable, Serializable {

	static final long serialVersionUID = 3177429969771592440L;

	/**
	 * @serial a coordinate used to define the vector
	 */
	public double x, y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Vector2D(Point2D p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * @return the vector from p to q
	 */
	public Vector2D(Point2D p, Point2D q) {
		this.x = q.x - p.x;
		this.y = q.y - p.y;
	}

	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}

	public Vector2D subtract(Vector2D v) {
		return new Vector2D(x - v.x, y - v.y);
	}

	public double norm() {
		return x * x + y * y;
	}

	public AffineTransform getTranslation() {
		return new AffineTransform(new double[] {1, 0, 0, 1, x, y});
	}

	public String toString() {
		return getClass().getName() + "(" + x + ", " + y + ")";
	}

	public int hashCode() {
		long bits = Double.doubleToLongBits(x);
		bits ^= Double.doubleToLongBits(y) * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}

	public boolean equals(Object obj) {
		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			Vector2D v = (Vector2D) obj;
			return x == v.x && y == v.y;
		}	
		return false;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(); // should never happen
		}
	}}