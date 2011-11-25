package org.tiling.alhambra.tile.penrose;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

public class FatRhomb extends PenroseTile {

	private static double LONG_DIAMETER = 2.0 * PHI * Math.sin(1.5 * THETA);

	// Vertex markings are {2, 1, 1, 1}
	private static Vertex[] baseVertices = new Vertex[] {
				new Vertex(0.0, 0.0, 2),
				new Vertex(PHI, 0, 1),
				new Vertex(LONG_DIAMETER * Math.cos(THETA),
							 LONG_DIAMETER * Math.sin(THETA), 1),
				new Vertex(PHI * Math.cos(2.0 * THETA),
							 PHI * Math.sin(2.0 * THETA), 1),
			};

	public FatRhomb() {
		this(new AffineTransform());
	}

	protected FatRhomb(AffineTransform t) {

		super(new Edge[] {
				new Edge(baseVertices[0], baseVertices[1]),
				new DirectedEdge(baseVertices[1], baseVertices[2], false),
				new DirectedEdge(baseVertices[2], baseVertices[3], true),
				new Edge(baseVertices[3], baseVertices[0]),
			}, t);

		setUI(new FatRhombUI(vertices, t));

	}

	public Tile transform(AffineTransform t) {
		t = new AffineTransform(t);
		t.concatenate(preTransformation);
		return new FatRhomb(t);
	}

}