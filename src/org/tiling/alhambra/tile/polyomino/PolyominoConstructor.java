package org.tiling.alhambra.tile.polyomino;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.*;

/**
 * Construct a Polyomino by specifying the constituent unit squares 	
 * (identified by the bottom left hand corner). E.g.
 * <pre>
 * createPolyomino(new int{
 *					        {1, 2},
 *					{0, 1}, {1, 1}, {2, 1},
 *					        {1, 0},
 *				});
 * </pre>
 * constructs a '+' Polyomino
 */
public class PolyominoConstructor {

	private PolyominoConstructor() {
	}

	// this is used only to construct the Polyomino
	private static SimplePatch tile;

	public static Polyomino createPolyomino(String name, int[][] squares) { 

		tile = new SimplePatch();

		boolean[][] pattern;

		// 1. Transform into grid of booleans

		int hBound = 0, vBound = 0;

		// find maxima
		for (int i = 0; i < squares.length; i++) {
			if (squares[i][0] > hBound)
				hBound = squares[i][0];	
			if (squares[i][1] > vBound)
				vBound = squares[i][1];	
		}

		hBound++;
		vBound++;

		pattern = new boolean[hBound][vBound];
		for (int i = 0; i < squares.length; i++) {
			pattern[squares[i][0]][squares[i][1]] = true;
		}

		// 2. Build by adding lots of squares together in a Patch

		boolean[][] scanned = new boolean[hBound][vBound];

		out:
		for (int hPos = 0; hPos < hBound; hPos++) {
			for (int vPos = 0; vPos < vBound; vPos++) {
				if (pattern[hPos][vPos]) {
					glueSquare(hPos, vPos, hBound, vBound, scanned, pattern);
					break out;
				}
			}
		}

		// 3. Finally get the boundary

		Vertex[] vertices = (Vertex[]) tile.getCorners().toArray(new Vertex[0]);

		return new Polyomino(vertices, pattern, name);
	}


	// A recursive method that ensures squares are added in a valid order (so the tile remains in once piece)
	private static void glueSquare(int hPos, int vPos, int hBound, int vBound, boolean[][] scanned, boolean[][] pattern) {
		if (scanned[hPos][vPos])
			return;

		AffineTransform translation = new AffineTransform(new double[]{1, 0, 0, 1, hPos, vPos});

		Tile unitSquare = new SimpleTile(
								new Vertex[] {
									new Vertex(0.0, 0.0),
									new Vertex(1.0, 0.0),
									new Vertex(1.0, 1.0),
									new Vertex(0.0, 1.0),
								}
							);


		tile.add(unitSquare.transform(translation));
		scanned[hPos][vPos] = true;

		if (0 < hPos && pattern[hPos - 1][vPos])
			glueSquare(hPos - 1, vPos, hBound, vBound, scanned, pattern);
		if (hPos < hBound - 1 && pattern[hPos + 1][vPos])
			glueSquare(hPos + 1, vPos, hBound, vBound, scanned, pattern);
		if (0 < vPos && pattern[hPos][vPos - 1])
			glueSquare(hPos, vPos - 1, hBound, vBound, scanned, pattern);
		if (vPos < vBound - 1 && pattern[hPos][vPos + 1])
			glueSquare(hPos, vPos + 1, hBound, vBound, scanned, pattern);

	}

}