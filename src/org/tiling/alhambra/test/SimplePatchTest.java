package org.tiling.alhambra.test;

import junit.framework.*;

import org.tiling.alhambra.*;

import java.util.List;import java.awt.geom.AffineTransform;public class SimplePatchTest extends SerializationTestCase {

	SimplePatch patch;
	Tile tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9;
	Tile halfTile1;
	Tile quarterTile1, quarterTile2;
	Tile tile1markedA, tile2markedA, tile3markedA;
	Tile tile1markedB, tile2markedB, tile3markedB;
	Tile tile1directed, tile2directed, tile3directed;

	public SimplePatchTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(SimplePatchTest.class);
	}

	public void setUp() {
		patch = new SimplePatch();

		// 789
		// 456
		// 123
		tile1 = new SimpleTile(
			new Vertex[] {new Vertex(0, 0), new Vertex(1, 0), new Vertex(1, 1), new Vertex(0, 1),}
			);
		tile2 = new SimpleTile(
			new Vertex[] {new Vertex(1, 0), new Vertex(2, 0), new Vertex(2, 1), new Vertex(1, 1),}
			);
		tile3 = new SimpleTile(
			new Vertex[] {new Vertex(2, 0), new Vertex(3, 0), new Vertex(3, 1), new Vertex(2, 1),}
			);
		tile4 = new SimpleTile(
			new Vertex[] {new Vertex(0, 1), new Vertex(1, 1), new Vertex(1, 2), new Vertex(0, 2),}
			);
		tile5 = new SimpleTile(
			new Vertex[] {new Vertex(1, 1), new Vertex(2, 1), new Vertex(2, 2), new Vertex(1, 2),}
			);
		tile6 = new SimpleTile(
			new Vertex[] {new Vertex(2, 1), new Vertex(3, 1), new Vertex(3, 2), new Vertex(2, 2),}
			);
		tile7 = new SimpleTile(
			new Vertex[] {new Vertex(0, 2), new Vertex(1, 2), new Vertex(1, 3), new Vertex(0, 3),}
			);
		tile8 = new SimpleTile(
			new Vertex[] {new Vertex(1, 2), new Vertex(2, 2), new Vertex(2, 3), new Vertex(1, 3),}
			);
		tile9 = new SimpleTile(
			new Vertex[] {new Vertex(2, 2), new Vertex(3, 2), new Vertex(3, 3), new Vertex(2, 3),}
			);

		halfTile1 = new SimpleTile(
			new Vertex[] {new Vertex(0, 1), new Vertex(1, 1), new Vertex(1, 1.5), new Vertex(0, 1.5),}
			);

		quarterTile1 = new SimpleTile(
			new Vertex[] {new Vertex(0, 0), new Vertex(0.5, 0), new Vertex(0.5, 0.5), new Vertex(0, 0.5),}
			);
		quarterTile2 = new SimpleTile(
			new Vertex[] {new Vertex(1, 0), new Vertex(1.5, 0), new Vertex(1.5, 0.5), new Vertex(1, 0.5),}
			);

		tile1markedA = new SimpleTile(
			new Vertex[] {new Vertex(0, 0, 1), new Vertex(1, 0, 2), new Vertex(1, 1, 1), new Vertex(0, 1, 2),}
			);
		tile2markedA = new SimpleTile(
			new Vertex[] {new Vertex(1, 0, 2), new Vertex(2, 0, 1), new Vertex(2, 1, 2), new Vertex(1, 1, 1),}
			);
		tile3markedA = new SimpleTile(
			new Vertex[] {new Vertex(2, 0, 2), new Vertex(3, 0, 1), new Vertex(3, 1, 2), new Vertex(2, 1, 1),}
			);

		tile1markedB = new SimpleTile(
			new Edge[] {
				new Edge(new Vertex(0, 0, 1), new Vertex(1, 0, 2)),
				new Edge(new Vertex(1, 0, 2), new Vertex(1, 1, 1)),
				new Edge(new Vertex(1, 1, 1), new Vertex(0, 1, 2)),
				new Edge(new Vertex(0, 1, 2), new Vertex(0, 0, 1)),
			});
		tile2markedB = new SimpleTile(
			new Edge[] {
				new Edge(new Vertex(1, 0, 2), new Vertex(2, 0, 1)),
				new Edge(new Vertex(2, 0, 1), new Vertex(2, 1, 2)),
				new Edge(new Vertex(2, 1, 2), new Vertex(1, 1, 1)),
				new Edge(new Vertex(1, 1, 1), new Vertex(1, 0, 2)),
			});
		tile3markedB = new SimpleTile(
			new Edge[] {
				new Edge(new Vertex(2, 0, 2), new Vertex(3, 0, 1)),
				new Edge(new Vertex(3, 0, 1), new Vertex(3, 1, 2)),
				new Edge(new Vertex(3, 1, 2), new Vertex(2, 1, 1)),
				new Edge(new Vertex(2, 1, 1), new Vertex(2, 0, 2)),
			});

		tile1directed = new SimpleTile(
			new Edge[] {
				new DirectedEdge(new Vertex(0, 1), new Vertex(1, 1)),
				new DirectedEdge(new Vertex(1, 1), new Vertex(1, 2)),
				new DirectedEdge(new Vertex(1, 2), new Vertex(0, 2)),
				new DirectedEdge(new Vertex(0, 2), new Vertex(0, 1)),
			});
		tile2directed = new SimpleTile(
			new Edge[] {
				new DirectedEdge(new Vertex(1, 1), new Vertex(2, 1)),
				new DirectedEdge(new Vertex(2, 1), new Vertex(2, 2)),
				new DirectedEdge(new Vertex(2, 2), new Vertex(1, 2)),
				new DirectedEdge(new Vertex(1, 2), new Vertex(1, 1)),
			});
		tile3directed = new SimpleTile(
			new Edge[] {
				new DirectedEdge(new Vertex(2, 1), new Vertex(3, 1), false),
				new DirectedEdge(new Vertex(3, 1), new Vertex(3, 2), false),
				new DirectedEdge(new Vertex(3, 2), new Vertex(2, 2), false),
				new DirectedEdge(new Vertex(2, 2), new Vertex(2, 1), false),
			});

	}

	private boolean add(Tile tile) {
		boolean ok = patch.add(tile) != null;
		checkConsistency();
		return ok;
	}

	private boolean remove(Tile tile) {
		boolean ok = patch.remove(tile) != null;
		checkConsistency();
		return ok;
	}

	private void checkConsistency() {
		assertNotNull("tiles not null", patch.getTiles());
		assertNotNull("triangles not null", patch.triangulate());
		assertNotNull("sides not null", patch.getSides());
		assertNotNull("corners not null", patch.getCorners());
		assertTrue("invariant", (patch.getTiles().size() * 2 == patch.triangulate().size())
				&& (patch.getSides().size() == patch.getCorners().size()));
	}

	public void testEquals() {
		patch.add(tile1);
		patch.add(tile2);
		patch.add(tile3);

		SimplePatch patch2 = new SimplePatch();
		patch2.add(tile1);
		patch2.add(tile2);
		patch2.add(tile3);
		assertEquals("patch2", patch, patch2);

		SimplePatch patch3 = new SimplePatch();
		patch3.add(tile1);
		patch3.add(tile3);
		patch3.add(tile2);
		assertTrue("patch3", !patch.equals(patch3));
	}

	public void testAddAll() {
		assertTrue("tile1", add(tile1));
		assertTrue("tile2", add(tile2));
		assertTrue("tile3", add(tile3));
		assertTrue("tile4", add(tile4));
		assertTrue("tile5", add(tile5));
		assertTrue("tile6", add(tile6));
		assertTrue("tile7", add(tile7));
		assertTrue("tile8", add(tile8));
		assertTrue("tile9", add(tile9));
		assertTrue("area = 9", 9.0 == patch.getArea());
		assertTrue("perimeter = 12", 12.0 == patch.getPerimeter());		
	}

	public void testAddInOrder() {
		assertTrue("tile1", add(tile1));
		assertTrue("tile2", add(tile2));
		assertTrue("tile3", add(tile3));
		assertTrue("area = 3", 3.0 == patch.getArea());
		assertTrue("perimeter = 8", 8.0 == patch.getPerimeter());			
	}

	public void testAddNoIntersection() {
		assertTrue("tile1", add(tile1));
		assertTrue("tile3", !add(tile3));
	}

	public void testAddOverlap() {
		assertTrue("tile1", add(tile1));
		assertTrue("quarterTile1", !add(quarterTile1));
	}

	public void testAddOverlap2() {
		assertTrue("quarterTile1", add(quarterTile1));
		assertTrue("tile1", !add(tile1));
	}

	public void testAddEdgeToEdge() {
		assertTrue("tile1", add(tile1));
		assertTrue("quarterTile2", !add(quarterTile2));
	}

	public void testAddEdgeToEdge2() {
		assertTrue("quarterTile2", add(quarterTile2));
		assertTrue("tile1", !add(tile1));
	}

	public void testAddEdgeToEdge3() {
		assertTrue("tile1", add(tile1));
		assertTrue("tile2", add(tile2));
		assertTrue("tile5", add(tile5));
		assertTrue("halfTile1", !add(halfTile1));
	}

	public void testAddWithHole() {
		// shouldn't be allowed to add a tile that produces a patch with a hole
		assertTrue("tile1", add(tile1));
		assertTrue("tile2", add(tile2));
		assertTrue("tile3", add(tile3));
		assertTrue("tile4", add(tile4));
		// no centre tile!
		assertTrue("tile6", add(tile6));
		assertTrue("tile7", add(tile7));
		assertTrue("canAdd tile8", !patch.canAdd(tile8));
		assertTrue("tile9", add(tile9));
		assertTrue("tile8", !add(tile8));
	}

	public void testAddAndRemoveInOrder() {
		assertTrue("tile1", add(tile1));
		assertTrue("tile2", add(tile2));
		assertTrue("tile3", add(tile3));		
		assertTrue("remove tile3", remove(tile3));
		assertTrue("area = 2", 2.0 == patch.getArea());
		assertTrue("perimeter = 6", 6.0 == patch.getPerimeter());		
		assertTrue("remove tile2", remove(tile2));
		assertTrue("area = 1", 1.0 == patch.getArea());
		assertTrue("perimeter = 4", 4.0 == patch.getPerimeter());		
		assertTrue("remove tile1", remove(tile1));
		assertTrue("area = 0", 0.0 == patch.getArea());
		assertTrue("perimeter = 0", 0.0 == patch.getPerimeter());		
	}
	
	public void testRemoveMiddleTile() {
		assertTrue("tile1", add(tile1));
		assertTrue("tile2", add(tile2));		
		assertTrue("tile3", add(tile3));
		assertTrue("remove tile2", !remove(tile2));		
	}
	
	public void testAddMarkedATiles() {
		assertTrue("tile1markedA", add(tile1markedA));
		assertTrue("tile2markedA", add(tile2markedA));
		assertTrue("tile3markedA", !add(tile3markedA));
	}

	public void testAddMarkedBTiles() {
		assertTrue("tile1markedB", add(tile1markedB));
		assertTrue("tile2markedB", add(tile2markedB));
		assertTrue("tile3markedB", !add(tile3markedB));
	}

	public void testAddOppositelyDirectedTiles() {
		assertTrue("tile1directed", add(tile1directed));
		assertTrue("tile2directed", !add(tile2directed));
	}

	public void testAddSameDirectedTiles() {
		assertTrue("tile2directed", add(tile2directed));
		assertTrue("tile3directed", add(tile3directed));
	}

	public void testAddAndRemove() {
		SymmetryGroup g = new SymmetryGroup(4, true);
		Prototile p = new Prototile(new Wedge(), g);
		Patch patch = new SimplePatch();
		SymbolicTransform st = new SymbolicTransform(g);
		Tile t = p.getTransformedTile(st);
		assertNotNull("Can add tile 1", patch.add(t));
		st.rotate(3);		
		t = p.getTransformedTile(st);
		assertNotNull("Can add tile 2", patch.add(t));
		assertTrue("Can remove last tile", patch.canRemove(t));
	}	public void testAddWedge1() {
		SymmetryGroup g = new SymmetryGroup(4, true);
		Prototile p = new Prototile(new Wedge(), g);
		Patch patch = new SimplePatch();
		SymbolicTransform st = new SymbolicTransform(g);
		st.rotate(1); // why is this not 3?
		Tile t = p.getTransformedTile(st).transform(AffineTransform.getTranslateInstance(0, 1));
		assertNotNull("Can add tile 1", patch.add(t));
		st = new SymbolicTransform(g);
		st.reflect(1);		
		t = p.getTransformedTile(st);
		assertNotNull("Can add tile 2", patch.add(t));
	}	public void testAddWedge2() {
		SymmetryGroup g = new SymmetryGroup(4, true);
		Prototile p = new Prototile(new Wedge(), g);
		Patch patch = new SimplePatch();
		SymbolicTransform st = new SymbolicTransform(g);
		st.rotate(1); // why is this not 3?
		Tile t = p.getTransformedTile(st);
		assertNotNull("Can add tile 1", patch.add(t));
		st = new SymbolicTransform(g);
		st.reflect(1);		
		t = p.getTransformedTile(st).transform(AffineTransform.getTranslateInstance(0, -1));
		assertNotNull("Can add tile 2", patch.add(t));
	}	public void testClone() {
		patch.add(tile1);
		patch.add(tile2);
		patch.add(tile3);

		SimplePatch clone = (SimplePatch) patch.clone();
		assertTrue("Identity", patch != clone);
		assertEquals("Equality", patch, clone);

		List tiles = clone.getTiles();

		assertTrue("1 id", tile1 != tiles.get(0));
		assertEquals("1 eq", tile1, tiles.get(0));
		assertTrue("2 id", tile2 != tiles.get(1));
		assertEquals("2 eq", tile2, tiles.get(1));
		assertTrue("3 id", tile3 != tiles.get(2));
		assertEquals("3 eq", tile3, tiles.get(2));
	}	public void testGetAdjacents() {
		List adjacents;
		
		assertTrue("tile1", add(tile1));
		adjacents = patch.getAdjacents(tile1);
		assertTrue("(Tile 1) Tile 1 has no adjacents", adjacents.isEmpty());
		adjacents = patch.getAdjacents(tile2);
		assertNull("(Tile 1) Tile 2 not in patch", adjacents);
		
		assertTrue("tile2", add(tile2));
		adjacents = patch.getAdjacents(tile1);
		assertEquals("(Tile 2) Tile 1 has one adjacent", 1, adjacents.size());
		assertSame("(Tile 2) Tile 1 is adjacent to tile 2", tile2, adjacents.get(0));
		adjacents = patch.getAdjacents(tile2);
		assertEquals("(Tile 2) Tile 2 has one adjacent", 1, adjacents.size());
		assertSame("(Tile 2) Tile 2 is adjacent to tile 1", tile1, adjacents.get(0));
		
		assertTrue("tile5", add(tile5));
		adjacents = patch.getAdjacents(tile1);
		assertEquals("(Tile 5) Tile 1 has one adjacent", 1, adjacents.size());
		assertSame("(Tile 5) Tile 1 is adjacent to tile 2", tile2, adjacents.get(0));
		adjacents = patch.getAdjacents(tile2);
		assertEquals("(Tile 5) Tile 2 has two adjacents", 2, adjacents.size());
		assertSame("(Tile 5) Tile 2 is adjacent to tile 1", tile1, adjacents.get(0));
		assertSame("(Tile 5) Tile 2 is adjacent to tile 5", tile5, adjacents.get(1));
		adjacents = patch.getAdjacents(tile5);
		assertEquals("(Tile 5) Tile 5 has one adjacent", 1, adjacents.size());
		assertSame("(Tile 5) Tile 5 is adjacent to tile 2", tile2, adjacents.get(0));

		assertTrue("tile4", add(tile4));
		adjacents = patch.getAdjacents(tile1);
		assertEquals("(Tile 4) Tile 1 has two adjacents", 2, adjacents.size());
		assertSame("(Tile 4) Tile 1 is adjacent to tile 2", tile2, adjacents.get(0));
		assertSame("(Tile 4) Tile 1 is adjacent to tile 4", tile4, adjacents.get(1));
		adjacents = patch.getAdjacents(tile2);
		assertEquals("(Tile 4) Tile 2 has two adjacents", 2, adjacents.size());
		assertSame("(Tile 4) Tile 2 is adjacent to tile 1", tile1, adjacents.get(0));
		assertSame("(Tile 4) Tile 2 is adjacent to tile 5", tile5, adjacents.get(1));
		adjacents = patch.getAdjacents(tile5);
		assertEquals("(Tile 4) Tile 5 has two adjacents", 2, adjacents.size());
		assertSame("(Tile 4) Tile 5 is adjacent to tile 2", tile2, adjacents.get(0));
		assertSame("(Tile 4) Tile 5 is adjacent to tile 4", tile4, adjacents.get(1));
		adjacents = patch.getAdjacents(tile4);
		assertEquals("(Tile 4) Tile 4 has two adjacents", 2, adjacents.size());
		assertSame("(Tile 4) Tile 4 is adjacent to tile 1", tile1, adjacents.get(0));
		assertSame("(Tile 4) Tile 4 is adjacent to tile 5", tile5, adjacents.get(1));

		// .. and then remove them in reverse order

		assertNotNull("remove tile4", patch.remove(tile4));
		adjacents = patch.getAdjacents(tile1);
		assertEquals("(remove tile4) Tile 1 has one adjacent", 1, adjacents.size());
		assertSame("(remove tile4) Tile 1 is adjacent to tile 2", tile2, adjacents.get(0));
		adjacents = patch.getAdjacents(tile2);
		assertEquals("(remove tile4) Tile 2 has two adjacents", 2, adjacents.size());
		assertSame("(remove tile4) Tile 2 is adjacent to tile 1", tile1, adjacents.get(0));
		assertSame("(remove tile4) Tile 2 is adjacent to tile 5", tile5, adjacents.get(1));
		adjacents = patch.getAdjacents(tile5);
		assertEquals("(remove tile4) Tile 5 has one adjacent", 1, adjacents.size());
		assertSame("(remove tile4) Tile 5 is adjacent to tile 2", tile2, adjacents.get(0));
		assertNull("(remove tile4) Tile 4 not in patch", patch.getAdjacents(tile4));

		assertNotNull("remove tile5", patch.remove(tile5));
		adjacents = patch.getAdjacents(tile1);
		assertEquals("(remove tile5) Tile 1 has one adjacent", 1, adjacents.size());
		assertSame("(remove tile5) Tile 1 is adjacent to tile 2", tile2, adjacents.get(0));
		adjacents = patch.getAdjacents(tile2);
		assertEquals("(remove tile5) Tile 2 has one adjacent", 1, adjacents.size());
		assertSame("(remove tile5) Tile 2 is adjacent to tile 1", tile1, adjacents.get(0));
		assertNull("(remove tile5) Tile 5 not in patch", patch.getAdjacents(tile5));
		assertNull("(remove tile5) Tile 4 not in patch", patch.getAdjacents(tile4));

		assertNotNull("remove tile2", patch.remove(tile2));
		adjacents = patch.getAdjacents(tile1);
		assertTrue("(remove tile2) Tile 1 has no adjacents", adjacents.isEmpty());
		assertNull("(remove tile2) Tile 2 not in patch", patch.getAdjacents(tile2));
		assertNull("(remove tile2) Tile 5 not in patch", patch.getAdjacents(tile5));
		assertNull("(remove tile2) Tile 4 not in patch", patch.getAdjacents(tile4));

		assertNotNull("remove tile1", patch.remove(tile1));
		assertNull("(remove tile1) Tile 1 not in patch", patch.getAdjacents(tile1));
		assertNull("(remove tile1) Tile 2 not in patch", patch.getAdjacents(tile2));
		assertNull("(remove tile1) Tile 5 not in patch", patch.getAdjacents(tile5));
		assertNull("(remove tile1) Tile 4 not in patch", patch.getAdjacents(tile4));
	}	public void testSerializable() {
		patch.add(tile1);
		patch.add(tile2);
		patch.add(tile3);

		SimplePatch copy = (SimplePatch) testPersist(patch);
		assertTrue("Identity", patch != copy);
		assertEquals("Equality", patch, copy);

		List tiles = copy.getTiles();

		assertTrue("1 id", tile1 != tiles.get(0));
		assertEquals("1 eq", tile1, tiles.get(0));
		assertTrue("2 id", tile2 != tiles.get(1));
		assertEquals("2 eq", tile2, tiles.get(1));
		assertTrue("3 id", tile3 != tiles.get(2));
		assertEquals("3 eq", tile3, tiles.get(2));
	}	public void testTransform() {
		assertTrue("tile1", add(tile1));
		assertTrue("tile2", add(tile2));		
		assertTrue("tile3", add(tile3));
		assertTrue("tile4", add(tile4));
		assertTrue("tile5", add(tile5));
		assertTrue("tile6", add(tile6));
		assertTrue("remove tile2", remove(tile2));

		SimplePatch patch2 = new SimplePatch();
		patch2.add(tile4);
		patch2.add(tile5);
		patch2.add(tile6);
		patch2.add(tile7);
		patch2.add(tile8);
		patch2.add(tile9);
		patch2.remove(tile5);
		
		Patch translatedPatch = (Patch) patch.transform(AffineTransform.getTranslateInstance(0.0, 1.0));
		assertEquals("equal", patch2, translatedPatch);
	}}