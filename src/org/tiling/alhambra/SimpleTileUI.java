package org.tiling.alhambra;

import org.tiling.alhambra.geom.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import org.tiling.UI;

/**
 * I am a simple implementation of UI for SimpleTile.
 */
public class SimpleTileUI implements UI, Serializable {

	static final long serialVersionUID = 8294455733732512253L;

	public static final float TILE_EDGE_WIDTH = 0.04f; // should be proportional to tile extent

	private static Color defaultBackgroundColor = Colours.LIGHT_ALHAMBRA_ORANGE;

	/**
	 * @serial the transform that is carried out on all elements of
	 * the tile to give the tile's absolute position.
	 */
	protected AffineTransform preTransformation;

	/**
	 * @serial an array of the (actual) vertices of the tile.
	 */
	protected Vertex[] vertices;

	/**
	 * @serial the fill colour of the tile.
	 */
	private Color backgroundColor;
	
	private transient GeneralPath polygonalTilePath;

	public SimpleTileUI(Vertex[] vertices, AffineTransform preTransformation) {
		this(vertices, defaultBackgroundColor, preTransformation);
	}

	public SimpleTileUI(Vertex[] vertices, Color backgroundColor, AffineTransform preTransformation) {
		this.vertices = vertices;
		this.backgroundColor = backgroundColor;
		this.preTransformation = preTransformation;
		initialiseGraphics();
	}

	protected void initialiseGraphics() {

		polygonalTilePath = new GeneralPath();
		polygonalTilePath.moveTo((float) vertices[0].x, (float) vertices[0].y);
		for (int i = 1; i < vertices.length; i++) {
			polygonalTilePath.lineTo((float) vertices[i].x, (float) vertices[i].y);
		}
		polygonalTilePath.closePath();

	}

	private void readObject(ObjectInputStream ois) throws IOException {
		try {
			ois.defaultReadObject();
			initialiseGraphics();
		} catch (ClassNotFoundException e) {
			throw new IOException();
		}
	}

	public void paint(Graphics2D g2) {
		paintBackground(g2);
		paintEdges(g2);
	}

	protected void paintBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fill(polygonalTilePath);
	}

	protected void paintEdges(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.draw(polygonalTilePath);
	}

	public void setBackground(Color c) {
		backgroundColor = c;
	}

	public Color getBackground() {
		return backgroundColor;
	}

	public Rectangle2D getBounds2D() {
		Extent extent = new Extent(vertices[0]);
		for (int i = 1; i < vertices.length; i++) {
			extent.add(vertices[i]);
		}
		extent.pad(TILE_EDGE_WIDTH, TILE_EDGE_WIDTH);
		return extent.getBounds2D();
	}

	public Object clone() {
		try {
			SimpleTileUI tileUI = (SimpleTileUI) super.clone();
			
			tileUI.preTransformation = (AffineTransform) preTransformation.clone();
			
			tileUI.vertices = (Vertex[]) vertices.clone();	
			for (int i = 0; i < vertices.length; i++) {
				tileUI.vertices[i] = (Vertex) vertices[i].clone();
			}

			// don't clone backgroundColour since Color is immutable (and not Cloneable anyway)

			tileUI.polygonalTilePath = (GeneralPath) polygonalTilePath.clone();
			
			return tileUI;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}}