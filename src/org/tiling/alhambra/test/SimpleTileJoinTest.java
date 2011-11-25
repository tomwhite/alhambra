package org.tiling.alhambra.test;

import java.util.*;

import junit.framework.*;

import org.tiling.alhambra.*;

public class SimpleTileJoinTest extends TestCase {

	Tile tile;

	public SimpleTileJoinTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(SimpleTileJoinTest.class);
	}

	public void setUp() {
		tile = new SimpleTile(
			new Vertex[] {new Vertex(1, 0), new Vertex(2, 0), new Vertex(2, 1), new Vertex(1, 1),}
			);
	}


	public void testNoEdges() {
		List edges = new ArrayList();
		SimpleTileJoin tj = new SimpleTileJoin(edges, tile);
		assertTrue("no common", tj.getCommonEdges().isEmpty());
		assertTrue("4 new", tj.getNewEdges().size() == 4);
	}

	public void testNoCommonEdges() {
		List edges = new ArrayList();
		edges.add(new Edge(new Vertex(0, 0), new Vertex(1, 0)));
		SimpleTileJoin tj = new SimpleTileJoin(edges, tile);
		assertTrue("no common", tj.getCommonEdges().isEmpty());
		assertTrue("no new", tj.getNewEdges().isEmpty());
	}

	public void testContiguousEdges1() {
		List edges = new ArrayList();
		edges.add(new Edge(new Vertex(1, 0), new Vertex(1, 1)));
		SimpleTileJoin tj = new SimpleTileJoin(edges, tile);
		assertTrue("contiguous", tj.commonEdgesContiguous());
		assertTrue("1 common", tj.getCommonEdges().size() == 1);
		assertTrue("common", tj.getCommonEdges().contains(new Edge(new Vertex(1, 1), new Vertex(1, 0))));
		assertTrue("3 new", tj.getNewEdges().size() == 3);
		assertEquals("1st new", new Edge(new Vertex(1, 0), new Vertex(2, 0)), tj.getNewEdges().get(0));
		assertEquals("2nd new", new Edge(new Vertex(2, 0), new Vertex(2, 1)), tj.getNewEdges().get(1));
		assertEquals("3rd new", new Edge(new Vertex(2, 1), new Vertex(1, 1)), tj.getNewEdges().get(2));
	}

	public void testContiguousEdges2() {
		List edges = new ArrayList();
//		edges.add(new Edge(new Vertex(2, 0), new Vertex(2, 1)));
//		edges.add(new Edge(new Vertex(2, 1), new Vertex(1, 1)));
		edges.add(new Edge(new Vertex(1, 1), new Vertex(2, 1)));
		edges.add(new Edge(new Vertex(2, 1), new Vertex(2, 0)));
		SimpleTileJoin tj = new SimpleTileJoin(edges, tile);
		assertTrue("contiguous", tj.commonEdgesContiguous());
		assertTrue("2 common", tj.getCommonEdges().size() == 2);
		assertEquals("1st common", new Edge(new Vertex(2, 0), new Vertex(2, 1)), tj.getCommonEdges().get(0));
		assertEquals("2nd common", new Edge(new Vertex(2, 1), new Vertex(1, 1)), tj.getCommonEdges().get(1));
		assertTrue("2 new", tj.getNewEdges().size() == 2);
		assertEquals("1st new", new Edge(new Vertex(1, 1), new Vertex(1, 0)), tj.getNewEdges().get(0));
		assertEquals("2nd new", new Edge(new Vertex(1, 0), new Vertex(2, 0)), tj.getNewEdges().get(1));
	}

	public void testContiguousEdges3() {
		List edges = new ArrayList();
//		edges.add(new Edge(new Vertex(1, 1), new Vertex(1, 0)));
//		edges.add(new Edge(new Vertex(1, 0), new Vertex(2, 0)));
		edges.add(new Edge(new Vertex(2, 0), new Vertex(1, 0)));
		edges.add(new Edge(new Vertex(1, 0), new Vertex(1, 1)));
		SimpleTileJoin tj = new SimpleTileJoin(edges, tile);
		assertTrue("contiguous", tj.commonEdgesContiguous());
		assertTrue("2 common", tj.getCommonEdges().size() == 2);
		assertEquals("1st common", new Edge(new Vertex(1, 1), new Vertex(1, 0)), tj.getCommonEdges().get(0));
		assertEquals("2nd common", new Edge(new Vertex(1, 0), new Vertex(2, 0)), tj.getCommonEdges().get(1));
		assertTrue("2 new", tj.getNewEdges().size() == 2);
		assertEquals("1st new", new Edge(new Vertex(2, 0), new Vertex(2, 1)), tj.getNewEdges().get(0));
		assertEquals("2nd new", new Edge(new Vertex(2, 1), new Vertex(1, 1)), tj.getNewEdges().get(1));
	}

	public void testContiguousNonMatchingEdges() {
		List edges = new ArrayList();
//		edges.add(new Edge(new Vertex(2, 0), new Vertex(2, 1)));
//		edges.add(new Edge(new Vertex(2, 1), new Vertex(1, 1), 1)); // not matching!
		edges.add(new Edge(new Vertex(1, 1), new Vertex(2, 1), 1));
		edges.add(new Edge(new Vertex(2, 1), new Vertex(2, 0))); // not matching!
		SimpleTileJoin tj = new SimpleTileJoin(edges, tile);
		assertTrue("no common", tj.getCommonEdges().isEmpty());
		assertTrue("no new", tj.getNewEdges().isEmpty());
	}

	public void testNonContiguousEdges() {
		List edges = new ArrayList();
//		edges.add(new Edge(new Vertex(1, 0), new Vertex(1, 1)));
//		edges.add(new Edge(new Vertex(2, 0), new Vertex(2, 1)));
		edges.add(new Edge(new Vertex(2, 1), new Vertex(2, 0)));
		edges.add(new Edge(new Vertex(1, 1), new Vertex(1, 0)));
		SimpleTileJoin tj = new SimpleTileJoin(edges, tile);
		assertTrue("not contiguous", !tj.commonEdgesContiguous());
		assertTrue("no common", tj.getCommonEdges().isEmpty());
		assertTrue("no new", tj.getNewEdges().isEmpty());
	}

}