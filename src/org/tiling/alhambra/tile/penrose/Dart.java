package org.tiling.alhambra.tile.penrose;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

public class Dart extends PenroseTile {

	public Dart() {
		this(new AffineTransform());
	}

	protected Dart(AffineTransform t) {

		// Vertex markings are {2, 1, 2, 1}
		super(new Vertex[] {
				new Vertex(0.0, 0.0, 2),
				new Vertex(PHI + 1.0, 0, 1),
				new Vertex(PHI * Math.cos(Math.PI / 5.0),
 PHI * Math.sin(Math.PI / 5.0), 2),
				new Vertex((PHI + 1.0) * Math.cos(2 * Math.PI / 5.0),
							 (PHI + 1.0) * Math.sin(2 * Math.PI / 5.0), 1),
			}, t);

		setUI(new DartUI(vertices, t));

	}

	public Tile transform(AffineTransform t) {
		t = new AffineTransform(t);
		t.concatenate(preTransformation);
		Dart dart = new Dart(t);
		DartUI dartUI = (DartUI) getUI();
		((DartUI) dart.getUI()).setBackground(dartUI.getBackground());
		((DartUI) dart.getUI()).setConwayLineRendering(dartUI.isConwayLineRendering());
		((DartUI) dart.getUI()).setAmmannBarRendering(dartUI.isAmmannBarRendering());
		return dart;
	}

	public double getDiameter() {
		return PHI + 1.0;
	}

}