package org.tiling.alhambra.tile.polygon;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.Tile; 
import org.tiling.alhambra.SimpleTile; 
import org.tiling.alhambra.Vertex;
import org.tiling.alhambra.geom.*;
import org.tiling.types.Type;

public class Polygon extends SimpleTile {

	private String name;

	protected Polygon(String name, Vertex[] vertices) { 
		super(vertices); 
		this.name = name;
	}

	protected Polygon(String name, Vertex[] vertices, AffineTransform t) { 
		super(vertices, t); 
		this.name = name;
	}

	public Tile transform(AffineTransform t) {
		Polygon polygon = new Polygon(name, vertices, new AffineTransform(t));
		polygon.getUI().setBackground(getUI().getBackground());
		return polygon;
	} 
 
	public Type getType() { 
		return new Type(name, super.getType()); 
	} 

}