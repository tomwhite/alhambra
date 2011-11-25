package org.tiling.alhambra.test; 

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.*;

class Wedge extends SimpleTile {
/*
	static Edge[] edges = new Edge[] {
		new LabelledEdge(new Vertex(0, 0), new Vertex(1, 0)),
		new LabelledEdge(new Vertex(1, 0), new Vertex(0, 1)),
		new LabelledEdge(new Vertex(0, 1), new Vertex(0, 0)),
	};
*/
	static Edge[] edges = new Edge[] {
		new LabelledEdge(new Vertex(0, 0), new Vertex(1, 0), 1, Curve.C_CURVE),
		new LabelledEdge(new Vertex(1, 0), new Vertex(0, 1)),
		new LabelledEdge(new Vertex(0, 1), new Vertex(0, 0), -1, Curve.C_CURVE),
	};
	public Wedge() {
		this(new AffineTransform());
	}
	public Wedge(AffineTransform t) {
		super(edges, t);
	}
	public Tile transform(AffineTransform t) {
//t = new AffineTransform(t); // Has been known to cause problems leaving this out. BUT should be allowed to miss it out!
		t.concatenate(preTransformation);
		return new Wedge(t);
	}
}