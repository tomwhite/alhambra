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
 * I am an implementation of UI for SimpleTile that draws a coloured-in polygon with a black outline.
 */
public class PolygonTileUI implements UI, Serializable {

	public static final float TILE_EDGE_WIDTH = 0.04f; // should be proportional to tile extent

	private static Color defaultBackgroundColor = Colours.LIGHT_ALHAMBRA_ORANGE;

	/**
	 * @serial the transform that is carried out on all elements of
	 * the tile to give the tile's absolute position.
	 */
	protected AffineTransform preTransformation;

	/**
	 * @serial the fill colour of the tile.
	 */
	private Color backgroundColor;
	
	private transient GeneralPath polygonalTilePath;

	public PolygonTileUI(AffineTransform preTransformation) {
		this(defaultBackgroundColor, preTransformation);
	}

	public PolygonTileUI(Color backgroundColor, AffineTransform preTransformation) {
		this.backgroundColor = backgroundColor;
		this.preTransformation = preTransformation;
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

	protected void setPolygonalTilePath(GeneralPath polygonalTilePath) {
		this.polygonalTilePath = polygonalTilePath;
	}

	protected GeneralPath getPolygonalTilePath() {
		return polygonalTilePath;
	}

	public Rectangle2D getBounds2D() {
		return polygonalTilePath.getBounds2D();
	}

	public Object clone() {
		try {
			PolygonTileUI polygonalTileUI = (PolygonTileUI) super.clone();
			
			polygonalTileUI.preTransformation = (AffineTransform) preTransformation.clone();
			
			// don't clone backgroundColour since Color is immutable (and not Cloneable anyway)

			polygonalTileUI.polygonalTilePath = (GeneralPath) polygonalTilePath.clone();
			
			return polygonalTileUI;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}}