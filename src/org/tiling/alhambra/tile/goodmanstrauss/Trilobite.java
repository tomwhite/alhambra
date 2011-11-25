package org.tiling.alhambra.tile.goodmanstrauss;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

public class Trilobite extends SimpleTile {

	public Trilobite() {
		this(new AffineTransform());
	}

	protected Trilobite(AffineTransform t) {

		// Edge markings:
		// white = 0, grey = 1, black = 2

		super((Edge[]) Edge.getEdges(new Vertex[] {
						new Vertex(2.0, 0.0),
						new Vertex(2.0, 1.0),
						new Vertex(3.0, 2.0),
						new Vertex(2.0, 3.0),
						new Vertex(1.0, 2.0),
						new Vertex(0.0, 2.0),
						new Vertex(-1.0, 2.0),
						new Vertex(-2.0, 1.0),
						new Vertex(-2.0, 0.0),
						new Vertex(-2.0, -1.0),
						new Vertex(-3.0, -2.0),
						new Vertex(-2.0, -3.0),
						new Vertex(-1.0, -2.0),
						new Vertex(0.0, -2.0),
						new Vertex(1.0, -2.0),
						new Vertex(2.0, -3.0),
						new Vertex(3.0, -2.0),
						new Vertex(2.0, -1.0),
						},
					new int[] {0, 1, 1, 1, 0, 2, 1, 2, 0, 1, 1, 1, 0, 2, 1, 1, 1, 2}).toArray(new Edge[0]),
			t);

		setUI(new TrilobiteUI(vertices, t));

	}

	public Tile transform(AffineTransform t) {
		t = new AffineTransform(t);
		t.concatenate(preTransformation);
		return new Trilobite(t); 
	}

}