package org.tiling.alhambra.test;

import java.awt.geom.AffineTransform;
import java.util.*;

import junit.framework.*;

import org.tiling.alhambra.*; 
import org.tiling.alhambra.geom.Tools2D; 
import org.tiling.alhambra.tiler.*;
import org.tiling.alhambra.tile.penrose.Dart;
import org.tiling.alhambra.tile.penrose.Kite;

public class FittingTilerTest extends TestCase {
	List penrose;
	public FittingTilerTest(String name) {
		super(name);
	}
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	public void setUp() {
		penrose = new ArrayList();
		penrose.add(AbstractPrototileFactory.getFactory("org.tiling.alhambra.tile.penrose.PenroseFactory").createPrototile("Kite"));
		penrose.add(AbstractPrototileFactory.getFactory("org.tiling.alhambra.tile.penrose.PenroseFactory").createPrototile("Dart"));
		assertEquals(2, penrose.size());
	}
	public static Test suite() {
		return new TestSuite(FittingTilerTest.class);
	}
	public void testAddForcedTiles() {
		Tile dart = (Tile) ((Prototile) penrose.get(1)).getTransformedTiles().get(0);

		Patch patch = new SimplePatch();
		FittingTiler tiler = new FittingTiler(patch, new PrototileSet(penrose));
		
		assertNotNull(patch.add(dart));
		assertEquals("Two forced", 2, tiler.addForcedTiles());
		List tiles = patch.getTiles();
		assertTrue("Kite 1", tiles.get(1) instanceof Kite);
		assertTrue("Kite 2", tiles.get(2) instanceof Kite);
	}
	public void testFittingWedgeTiles() {
		SymmetryGroup g = new SymmetryGroup(4, true);
		Prototile p = new Prototile(new Wedge(), g);
		Patch patch = new SimplePatch();
		FittingTiler tiler = new FittingTiler(patch, new PrototileSet(p));
		Tile t = p.getTransformedTile(new SymbolicTransform(g));
		assertNotNull("Can add tile 1", patch.add(t));
		List fittingTiles = tiler.getFittingTiles((Edge) t.getSides().get(2));
		assertEquals(2, fittingTiles.size());
	}
	public void testGetFittingTile() {
		Tile kite = (Tile) ((Prototile) penrose.get(0)).getTransformedTiles().get(0);
		Tile dart = (Tile) ((Prototile) penrose.get(1)).getTransformedTiles().get(9);

		Patch patch = new SimplePatch();
		FittingTiler tiler = new FittingTiler(patch, new PrototileSet(penrose));

		assertNotNull(patch.add(kite));

		int side = 0;
		int repeats = 200;
		// Add 'repeats' of each tile
		// N.B. this test tests rounding tolerances (Penrose tiles have sides of irrational
		// length so they are especially suitable).

		// For a float-based framework:
		// See Tools2D.eps. Changing its value to 1e-6 will break this test for repeats = 4! 
		// For 1e-5 the value is about 100! 

		// For a double-based framework (as it is currently):
		// See Tools2D.eps. For 1e-5 the value is over 200! 
		for (int i = 0; i < repeats; i++) {
			Tile transformedDart = tiler.getFittingTile(side, dart, 0);
			assertNotNull("transformedDart not null (" + i + ")", transformedDart);
			assertNotNull("add " + i + "th dart", patch.add(transformedDart));
			side += 2;
			Tile transformedKite = tiler.getFittingTile(side, kite, 3);
			assertNotNull("transformedKite not null (" + i + " + 1)", transformedKite);
			assertNotNull("add (" + i + " + 1)th kite", patch.add(transformedKite));
		}

	}
}
