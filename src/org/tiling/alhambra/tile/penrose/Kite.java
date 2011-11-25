package org.tiling.alhambra.tile.penrose;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

public class Kite extends PenroseTile {

	public Kite() {
		this(new AffineTransform());
	}

	protected Kite(AffineTransform t) {

		// Vertex markings are {1, 2, 1, 2}
		super(new Vertex[] {
				new Vertex(0.0, 0.0, 1),
				new Vertex(PHI + 1.0, 0, 2),
				new Vertex((PHI + 1.0) * Math.cos(Math.PI / 5.0),
							 (PHI + 1.0) * Math.sin(Math.PI / 5.0), 1),
				new Vertex((PHI + 1.0) * Math.cos(2 * Math.PI / 5.0),
							 (PHI + 1.0) * Math.sin(2 * Math.PI / 5.0), 2),
			}, t);

		setUI(new KiteUI(vertices, t));

	}

	public Tile transform(AffineTransform t) {
		t = new AffineTransform(t);
		t.concatenate(preTransformation);
		Kite kite = new Kite(t);
		KiteUI kiteUI = (KiteUI) getUI();
		((KiteUI) kite.getUI()).setBackground(kiteUI.getBackground());
		((KiteUI) kite.getUI()).setConwayLineRendering(kiteUI.isConwayLineRendering());
		((KiteUI) kite.getUI()).setAmmannBarRendering(kiteUI.isAmmannBarRendering());
		return kite;
	}

	public double getDiameter() {
		return PHI + 1.0;
	} 

}