package org.tiling.alhambra.tile.penrose;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

public class LB extends PenroseTile {

	public LB() {
		this(new AffineTransform());
	}

	protected LB(AffineTransform t) {

		// Vertex markings are {0, (0), 0, 0}
		super(new Vertex[] {
				new Vertex(0.0, 0.0, 0),
				new Vertex(PHI + 1.0, 0, 0), // dummy
				new Vertex(2 * PHI + 1.0, 0, 0),
				new Vertex((PHI + 1.0) * Math.cos(Math.PI / 5.0),
							 (PHI + 1.0) * Math.sin(Math.PI / 5.0), 0),
			}, t);

		setUI(new LBUI(vertices, t)); 
 
	}

	public Tile transform(AffineTransform t) {
		t = new AffineTransform(t);
		t.concatenate(preTransformation);
		LB lb = new LB(t);
		LBUI lbUI = (LBUI) getUI();
		((LBUI) lb.getUI()).setBackground(lbUI.getBackground());
		return lb;
	}

	public List triangulate() { 
		if (triangles == null) { 
			triangles = new ArrayList(); 
			if (preTransformation.getDeterminant() < 0.0) { 
				triangles.add(new Triangle2D(vertices[0], vertices[1], vertices[2])); 
			} else {
				triangles.add(new Triangle2D(vertices[0], vertices[2], vertices[3])); 
			}
		} 
		return triangles; 
	}

}