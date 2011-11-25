package org.tiling.alhambra.geom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * I am a java.awt.geom.Point2D.Double that is Serializable.
 */
public class Point2D extends java.awt.geom.Point2D.Double implements Cloneable, Serializable {

	static final long serialVersionUID = -5598739799092667694L;

	public Point2D(double x, double y) {
		super(x, y);
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}


	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeDouble(x);
		out.writeDouble(y);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		x = in.readDouble();
		y = in.readDouble();
	}

}