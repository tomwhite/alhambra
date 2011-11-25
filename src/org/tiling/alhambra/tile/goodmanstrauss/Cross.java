package org.tiling.alhambra.tile.goodmanstrauss;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

public class Cross extends SimpleTile {

	public Cross() {
		this(new AffineTransform());
	}

	protected Cross(AffineTransform t) {

		// Edge markings:
		// white = 0, grey = 1, black = 2

		super((Edge[]) Edge.getEdges(new Vertex[] {
						new Vertex(2.0, 0.0),
						new Vertex(2.0, 1.0),
						new Vertex(1.0, 2.0),
						new Vertex(0.0, 2.0),
						new Vertex(-1.0, 2.0),
						new Vertex(-2.0, 1.0),
						new Vertex(-2.0, 0.0),
						new Vertex(-2.0, -1.0),
						new Vertex(-1.0, -2.0),
						new Vertex(0.0, -2.0),
						new Vertex(1.0, -2.0),
						new Vertex(2.0, -1.0),
						},
					new int[] {2, 1, 0, 2, 1, 2, 0, 1, 2, 0, 1, 0}).toArray(new Edge[0]),
			t);

		setUI(new CrossUI(vertices, t));

	}

	public Tile transform(AffineTransform t) {
		t = new AffineTransform(t);
		t.concatenate(preTransformation);
		return new Cross(t);
	}
	

}