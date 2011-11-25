package org.tiling.alhambra.tile.penrose;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

public class ThinRhomb extends PenroseTile {

	private static double LONG_DIAMETER = 2.0 * PHI * Math.sin(2.0 * THETA);

	// Vertex markings are {1, 2, 1, 1}
	private static Vertex[] baseVertices = new Vertex[] {
				new Vertex(0.0, 0.0, 1),
				new Vertex(PHI, 0, 2),
				new Vertex(LONG_DIAMETER * Math.cos(THETA / 2.0),
							 LONG_DIAMETER * Math.sin(THETA / 2.0), 1),
				new Vertex(PHI * Math.cos(THETA),
							 PHI * Math.sin(THETA), 1),
			};

	public ThinRhomb() {
		this(new AffineTransform());
	}

	protected ThinRhomb(AffineTransform t) {

		super(new Edge[] {
				new Edge(baseVertices[0], baseVertices[1]),
				new Edge(baseVertices[1], baseVertices[2]),
				new DirectedEdge(baseVertices[2], baseVertices[3], true),
				new DirectedEdge(baseVertices[3], baseVertices[0], false),
			}, t);

		setUI(new ThinRhombUI(vertices, t));

	}

	public Tile transform(AffineTransform t) {
		t = new AffineTransform(t);
		t.concatenate(preTransformation);
		return new ThinRhomb(t);
	}

}