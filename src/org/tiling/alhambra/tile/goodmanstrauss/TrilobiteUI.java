package org.tiling.alhambra.tile.goodmanstrauss;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TrilobiteUI extends SimpleTileUI {

	private static final Color defaultBackgroundColor = Color.white; 
 
	private transient List blackPatterns;
	private transient List greyPatterns;

	public TrilobiteUI(Vertex[] vertices, AffineTransform t) {

		super(vertices, defaultBackgroundColor, t); 
	}

	protected void initialiseGraphics() {
		super.initialiseGraphics();
		blackPatterns = new ArrayList();

		blackPatterns.add(createPath(new Vertex[] {
							new Vertex(0.0, 0.0),
							new Vertex(2.0, 0.0),
							new Vertex(2.0, -1.0),
							new Vertex(1.0, -1.0),
						}, preTransformation)
		);
		blackPatterns.add(createPath(new Vertex[] {
							new Vertex(0.0, 0.0),
							new Vertex(0.0, 2.0),
							new Vertex(-1.0, 2.0),
							new Vertex(-1.0, 1.0),
						}, preTransformation)
		);
		blackPatterns.add(createPath(new Vertex[] {
							new Vertex(0.0, 0.0),
							new Vertex(-1.0, 1.0),
							new Vertex(-2.0, 1.0),
							new Vertex(-2.0, 0.0),
						}, preTransformation)
		);
		blackPatterns.add(createPath(new Vertex[] {
							new Vertex(0.0, 0.0),
							new Vertex(0.0, -2.0),
							new Vertex(1.0, -2.0),
							new Vertex(1.0, -1.0),
						}, preTransformation)
		);

		greyPatterns = new ArrayList();

		greyPatterns.add(createPath(new Vertex[] {
							new Vertex(1.0, 1.0),
							new Vertex(2.0, 1.0),
							new Vertex(3.0, 2.0),
							new Vertex(2.0, 3.0),
							new Vertex(1.0, 2.0),
						}, preTransformation)
		);
		greyPatterns.add(createPath(new Vertex[] {
							new Vertex(-1.0, 1.0),
							new Vertex(-2.0, 1.0),
							new Vertex(-1.0, 2.0),
						}, preTransformation)
		);
		greyPatterns.add(createPath(new Vertex[] {
							new Vertex(1.0, -1.0),
							new Vertex(2.0, -1.0),
							new Vertex(3.0, -2.0),
							new Vertex(2.0, -3.0),
							new Vertex(1.0, -2.0),
						}, preTransformation)
		);
		greyPatterns.add(createPath(new Vertex[] {
							new Vertex(-1.0, -1.0),
							new Vertex(-2.0, -1.0),
							new Vertex(-3.0, -2.0),
							new Vertex(-2.0, -3.0),
							new Vertex(-1.0, -2.0),
						}, preTransformation)
		);

	}

	private GeneralPath createPath(Vertex[] vertices, AffineTransform t) {
		Vertex[] transformedPatternVertices = new Vertex[vertices.length];

		for (int i = 0; i < vertices.length; i++) {
			transformedPatternVertices[i] = (Vertex) vertices[i].clone();
		}
		SimpleTile.transform(t, vertices, transformedPatternVertices);

		GeneralPath patternPath = new GeneralPath();
		patternPath.moveTo((float) transformedPatternVertices[0].x, (float) transformedPatternVertices[0].y);
		for (int i = 1; i < transformedPatternVertices.length; i++) {
			patternPath.lineTo((float) transformedPatternVertices[i].x, (float) transformedPatternVertices[i].y);
		}
		patternPath.closePath();

		return patternPath;
	}

	public void paint(Graphics2D g2) {
		paintBackground(g2);
		paintPattern(g2);
		paintEdges(g2);
	}

	protected void paintPattern(Graphics2D g2) {
		g2.setColor(Color.darkGray);
		for (Iterator i = blackPatterns.iterator(); i.hasNext(); ) {
			g2.fill((GeneralPath) i.next());
		}
		g2.setColor(Color.lightGray);
 		for (Iterator i = greyPatterns.iterator(); i.hasNext(); ) {
			g2.fill((GeneralPath) i.next());
		} 
	}

}