package org.tiling.alhambra.tile.penrose;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

public abstract class PenroseTile extends SimpleTile {

	/**
	 * The golden ratio:
	 * <pre>(1 + sqrt(5))/2</pre>
	 */
	public static final double PHI = (1.0 + Math.sqrt(5)) / 2.0;

	/**
	 * <pre>Math.PI / 5.0</pre>
	 */
	public static final double THETA = Math.PI / 5.0;

	public PenroseTile(Vertex[] vertices, AffineTransform t) {
		super(vertices, t);
	}

	public PenroseTile(Edge[] edges, AffineTransform t) {
		super(edges, t);
	}

}