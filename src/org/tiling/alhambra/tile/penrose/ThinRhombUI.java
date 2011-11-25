package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

public class ThinRhombUI extends SimpleTileUI {

	private static final Color defaultBackgroundColor = Color.white; 
 
	private static double SHORT_HALF_DIAMETER = PenroseTile.PHI * Math.sin(PenroseTile.THETA / 2.0);

	private transient Arc2D arc1, arc2;

	public ThinRhombUI(Vertex[] vertices, AffineTransform t) {
 		super(vertices, defaultBackgroundColor, t); 
	}

	protected void initialiseGraphics() {
		super.initialiseGraphics();
		arc1 = new Arc2D.Double();
		arc1.setArcByCenter(vertices[1].x, vertices[1].y,
						SHORT_HALF_DIAMETER, 0, 144, Arc2D.PIE);
		// JDK 1.2.2
		arc1.setAngleStart(new Point2D.Double(vertices[0].x, vertices[0].y));

		arc2 = new Arc2D.Double();
		arc2.setArcByCenter(vertices[3].x, vertices[3].y,
						SHORT_HALF_DIAMETER, 0, 144, Arc2D.PIE);
		// JDK 1.2.2
		arc2.setAngleStart(new Point2D.Double(vertices[2].x, vertices[2].y));
	}

	public void paint(Graphics2D g2) {
		paintBackground(g2);
		paintPattern(g2);
		paintEdges(g2);
	}

	protected void paintPattern(Graphics2D g2) {
		g2.setColor(Colours.LILAC);
		g2.fill(arc1);
		g2.setColor(Colours.DARK_PURPLE);
		g2.fill(arc2);
	}

}