package org.tiling.alhambra.tile.polyomino;

import org.tiling.alhambra.*; 
import org.tiling.alhambra.geom.*;
import org.tiling.types.Type;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Polyomino extends SimpleTile implements Tile, Serializable {

	/** 
	 * @serial stores the pattern of squares in a 2D boolean array. 
	 */ 
	private boolean[][] pattern; 

	/** 
	 * @serial the name of the Polyomino 
	 */ 
	private String name; 

	protected Polyomino(Vertex[] vertices, boolean[][] pattern) { 
		this(vertices, pattern, "Polyomino");
	} 
 
	protected Polyomino(Vertex[] vertices, boolean[][] pattern, String name) { 
		super(vertices); 
		this.pattern = pattern;
		this.name = name;
	} 
  
	// Overridden to take advantage of the ease of triangulating a polyomino
	public List triangulate() {
		if (triangles == null) {
			triangles = new ArrayList();
			// use pattern to construct triangles
			for (int hPos = 0; hPos < pattern.length; hPos++) {
				for (int vPos = 0; vPos < pattern[0].length; vPos++) {
					if (pattern[vPos][hPos]) {
						Point2D A = new Point2D(vPos, hPos);
						Point2D B = new Point2D(vPos + 1, hPos);
						Point2D C = new Point2D(vPos + 1, hPos + 1);
						Point2D D = new Point2D(vPos, hPos + 1);
						triangles.add(new Triangle2D(A, B, C));
						triangles.add(new Triangle2D(C, D, A));
					}
				}
			}
		}
		return triangles;
	}

	public Type getType() { 
		return new Type(name, super.getType()); 
	} 

}