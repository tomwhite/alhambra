package org.tiling.alhambra.tile.polygon;

import org.tiling.alhambra.Vertex;

/**
 * Construct a polygon with equal length sides by specifying
 * the internal angles (in degrees).
 */
public class PolygonConstructor {
	
	private static final double SIDE_LENGTH = 2.0;

	private PolygonConstructor() {
	}

	public static Polygon createPolygon(String name, double[] angles) {

		Vertex[] vertices = new Vertex[angles.length];
		vertices[0] = new Vertex(0.0, 0.0);
		vertices[1] = new Vertex(SIDE_LENGTH, 0.0);
		
		for (int i = 2; i < vertices.length; i++) {
			double theta = Math.PI * angles[i - 2] / 180.0;
			double phi = Math.atan2((vertices[i - 1].y - vertices[i - 2].y),
							(vertices[i - 1].x - vertices[i - 2].x));
			double relativeAngle = theta - phi;
			vertices[i] = new Vertex(vertices[i - 1].x - SIDE_LENGTH * Math.cos(relativeAngle),
						vertices[i - 1].y + SIDE_LENGTH * Math.sin(relativeAngle));
		}
		return new Polygon(name, vertices);
	}

}