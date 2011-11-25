package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class LBUI extends SimpleTileUI {

	private static final Color defaultBackgroundColor = Color.white;

	private transient Line2D line;

	public LBUI(Vertex[] vertices, AffineTransform t) {

		super(vertices, defaultBackgroundColor, t);

	}

	protected void initialiseGraphics() {
		super.initialiseGraphics();

		line = new Line2D.Double(vertices[1].x, vertices[1].y, vertices[3].x, vertices[3].y); 
	}

	public void paint(Graphics2D g2) {
		paintBackground(g2);
		paintPattern(g2);
		paintEdges(g2);
	}

	protected void paintPattern(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.draw(line);
	}

}