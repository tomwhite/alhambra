package org.tiling.alhambra.geom;

import java.io.Serializable;

import java.awt.geom.Rectangle2D;

/**
 * I enable the maximum extent (width, height) of a number of points to be determined.
 */
public class Extent implements Cloneable, Serializable {
	public double minX, minY, maxX, maxY;

	public Extent(Point2D p) {
		minX = p.x;
		maxX = p.x;
		minY = p.y;
		maxY = p.y;
	}

	public void add(Point2D p) {
		if (p.x < minX)
			minX = p.x;
		else if (p.x > maxX)
			maxX = p.x;

		if (p.y < minY)
			minY = p.y;
		else if (p.y > maxY)
			maxY = p.y;
	}

	public double getWidth() {
		return maxX - minX;
	}

	public double getHeight() {
		return maxY - minY;
	}

	public double getMaxExtent() { 
		return Math.max(getWidth(), getHeight()); 
	} 
 
	public double getDiameter() { 
		return Math.sqrt((maxX - minX) * (maxX - minX) + (maxY - minY) * (maxY - minY)); 
	} 
 
	public Point2D getCentre() {
		return new Point2D((minX + maxX) / 2, (minY + maxY) / 2);
	}

	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
	}

	public void pad(double xPad, double yPad) {
		minX -= xPad;
		maxX += xPad;
		minY -= yPad;
		maxY += yPad;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(); // should never happen
		}
	}}