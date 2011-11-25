package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class KiteUI extends SimpleTileUI {

	private static final Color defaultBackgroundColor = Colours.LILAC;

	private boolean conwayLineRendering = false;
	private transient Arc2D arc1, arc2;

	private boolean ammannBarRendering = true;
	private transient GeneralPath ammannBars;

	public KiteUI(Vertex[] vertices, AffineTransform t) {

		super(vertices, defaultBackgroundColor, t);

	}

	public void setConwayLineRendering(boolean conwayLineRendering) {
		this.conwayLineRendering = conwayLineRendering;
	}
	public boolean isConwayLineRendering() {
		return conwayLineRendering;
	}

	public void setAmmannBarRendering(boolean ammannBarRendering) {
		this.ammannBarRendering = ammannBarRendering;
	}
	public boolean isAmmannBarRendering() {
		return ammannBarRendering;
	}

	protected void initialiseGraphics() {
		super.initialiseGraphics();
		arc1 = new Arc2D.Double();
		arc1.setArcByCenter(vertices[0].x, vertices[0].y,
						PenroseTile.PHI, 0, 72, Arc2D.OPEN);
		// JDK 1.2.2
		arc1.setAngleStart(new Point2D.Double(vertices[3].x, vertices[3].y));
		// JDK 1.2RC1
//		arc1.setAngleStart(new Point2D.Double(vertices[1].x, vertices[1].y));
//		arc1.setAngleStart(360 - arc1.getAngleStart() - 72); // fix for reflection problem!

		arc2 = new Arc2D.Double();
		arc2.setArcByCenter(vertices[2].x, vertices[2].y,
						1.0, 0, 144, Arc2D.OPEN);
		// JDK 1.2.2
		arc2.setAngleStart(new Point2D.Double(vertices[1].x, vertices[1].y));
		// JDK 1.2RC1
//		arc2.setAngleStart(new Point2D.Double(vertices[3].x, vertices[3].y));
//		arc2.setAngleStart(360 - arc2.getAngleStart() - 144); // fix for reflection problem!

		ammannBars = new GeneralPath();
		org.tiling.alhambra.geom.Point2D point = Tools2D.pointOnLine(vertices[2], vertices[3], 0.25);
		ammannBars.moveTo((float) point.x, (float) point.y);
		point = Tools2D.pointOnLine(vertices[0], vertices[1], 1 / (2 * (1 + PenroseTile.PHI)));
		ammannBars.lineTo((float) point.x, (float) point.y);
		point = Tools2D.pointOnLine(vertices[0], vertices[3], 1 / (2 * (1 + PenroseTile.PHI)));

		ammannBars.lineTo((float) point.x, (float) point.y);
		point = Tools2D.pointOnLine(vertices[2], vertices[1], 0.25);
		ammannBars.lineTo((float) point.x, (float) point.y);

	}

	public void paint(Graphics2D g2) {
		paintBackground(g2);
		paintPattern(g2);
		paintEdges(g2);
	}

	protected void paintPattern(Graphics2D g2) {
		if (conwayLineRendering) {
			g2.setColor(Color.white);
			g2.draw(arc1);
			g2.draw(arc2);
		}
		if (ammannBarRendering) {
			g2.setColor(Color.blue);
			g2.draw(ammannBars);
		}
	}

}