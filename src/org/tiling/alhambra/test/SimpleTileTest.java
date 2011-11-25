package org.tiling.alhambra.test;

import java.awt.geom.AffineTransform;
import java.util.*;

import junit.framework.*;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.Tools2D;

public class SimpleTileTest extends SerializationTestCase {

	Vertex v1, v2, v3, v4;
	Edge e1, e2, e3, e4;
	SimpleTile tile1, tile2;

	public SimpleTileTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(SimpleTileTest.class);
	}

	public void setUp() {
		v1 = new Vertex(0, 0);
		v2 = new Vertex(1, 0);
		v3 = new Vertex(1, 1);
		v4 = new Vertex(0, 1);
		tile1 = new SimpleTile(new Vertex[] {v1, v2, v3, v4});
		e1 = new Edge(v1, v2);		
		e2 = new Edge(v2, v3);
		e3 = new Edge(v3, v4);
		e4 = new Edge(v4, v1);
		tile2 = new SimpleTile(new Edge[] {e1, e2, e3, e4});
	}

	public void testEquals() {
		assertEquals(tile1, tile2);
		assertEquals(tile1, new SimpleTile(new Vertex[] {v1, v2, v3, v4}));
		assertEquals(tile2, new SimpleTile(new Edge[] {e1, e2, e3, e4}));
	}

	public void testGetArea() {	
		assertTrue(1.0 == tile1.getArea());	
		assertTrue(1.0 == tile2.getArea());	
	}
	
	public void testGetPerimeter() {	
		assertTrue(4.0 == tile1.getPerimeter());	
		assertTrue(4.0 == tile2.getPerimeter());
	}
		
	public void testGetCorners() {
		List corners1 = tile1.getCorners();
		assertEquals(4, corners1.size());
		assertEquals(v1, corners1.get(0));
		assertEquals(v2, corners1.get(1));
		assertEquals(v3, corners1.get(2));
		assertEquals(v4, corners1.get(3));
		List corners2 = tile2.getCorners();
		assertEquals(4, corners2.size());
		assertEquals(v1, corners2.get(0));
		assertEquals(v2, corners2.get(1));
		assertEquals(v3, corners2.get(2));
		assertEquals(v4, corners2.get(3));
	}

	public void testGetSides() {
	}

	public void testTriangulate() {
	}

	public void testContains() {
	}

	public void testOverlaps() {
	}

	public void checkEdge(String message, Edge edge, double v1x, double v1y, double v2x, double v2y, int mark) {
		assertEquals(message, edge.getV1().x, v1x, Tools2D.eps);
		assertEquals(message, edge.getV1().y, v1y, Tools2D.eps);
		assertEquals(message, edge.getV2().x, v2x, Tools2D.eps);
		assertEquals(message, edge.getV2().y, v2y, Tools2D.eps);
		assertEquals(message, edge.getMark(), mark);
	}

	public void testTransform() {
		Wedge tile = new Wedge();

		AffineTransform t = AffineTransform.getRotateInstance(Math.PI / 2);
		Tile tile1 = tile.transform(t);
		List edges1 = tile1.getSides();
		checkEdge("1 Edge 1", (Edge) edges1.get(0), 0, 0, 0, 1, 1);
		checkEdge("1 Edge 2", (Edge) edges1.get(1), 0, 1, -1, 0, 0);
		checkEdge("1 Edge 3", (Edge) edges1.get(2), -1, 0, 0, 0, -1);

		AffineTransform r = new AffineTransform(new double[] {-1, 0, 0, 1, 0, 0});
		Tile tile2 = tile.transform(r);
		List edges2 = tile2.getSides();
		checkEdge("2 Edge 1", (Edge) edges2.get(0), 0, 0, 0, 1, -1);
		checkEdge("2 Edge 2", (Edge) edges2.get(1), 0, 1, -1, 0, 0);
		checkEdge("2 Edge 3", (Edge) edges2.get(2), -1, 0, 0, 0, 1);

		Tile tile3 = tile2.transform(new AffineTransform(t));
		List edges3 = tile3.getSides();
		checkEdge("3 Edge 1", (Edge) edges3.get(0), 0, 0, -1, 0, -1);
		checkEdge("3 Edge 2", (Edge) edges3.get(1), -1, 0, 0, -1, 0);
		checkEdge("3 Edge 3", (Edge) edges3.get(2), 0, -1, 0, 0, 1);
		
		Tile tile4 = tile1.transform(new AffineTransform(r));
		List edges4 = tile4.getSides();
		checkEdge("4 Edge 1", (Edge) edges4.get(0), 0, 0, 1, 0, -1);
		checkEdge("4 Edge 2", (Edge) edges4.get(1), 1, 0, 0, 1, 0);
		checkEdge("4 Edge 3", (Edge) edges4.get(2), 0, 1, 0, 0, 1);

		Tile tile5 = tile1.transform(new AffineTransform(t));
		List edges5 = tile5.getSides();
		checkEdge("5 Edge 1", (Edge) edges5.get(0), 0, 0, -1, 0, 1);
		checkEdge("5 Edge 2", (Edge) edges5.get(1), -1, 0, 0, -1, 0);
		checkEdge("5 Edge 3", (Edge) edges5.get(2), 0, -1, 0, 0, -1);
		
		AffineTransform m = AffineTransform.getTranslateInstance(0, 1);
		Tile tile6 = tile5.transform(m);
		List edges6 = tile6.getSides();
		checkEdge("6 Edge 1", (Edge) edges6.get(0), 0, 1, -1, 1, 1);
		checkEdge("6 Edge 2", (Edge) edges6.get(1), -1, 1, 0, 0, 0);
		checkEdge("6 Edge 3", (Edge) edges6.get(2), 0, 0, 0, 1, -1);

	}

	public void testClone() {
		SimpleTile clone = (SimpleTile) tile1.clone();
		assertTrue("Identity", tile1 != clone);
		assertEquals("Equality", tile1, clone);
		assertTrue("Identity preTransformation", tile1.preTransformation != clone.preTransformation);
		assertEquals("Identity preTransformation", tile1.preTransformation, clone.preTransformation);
		for (int i = 0; i < tile1.getCorners().size(); i++) {
			assertTrue("Corner " + i, tile1.getCorners().get(i) != clone.getCorners().get(i));
			assertEquals("Corner " + i, tile1.getCorners().get(i), clone.getCorners().get(i));
		}
		for (int i = 0; i < tile1.getSides().size(); i++) {
			assertTrue("Side " + i, tile1.getSides().get(i) != clone.getSides().get(i));
			assertEquals("Side " + i, tile1.getSides().get(i), clone.getSides().get(i));
		}
		assertTrue("Identity ui", tile1.getUI() != clone.getUI());
	}	public void testSerializable() {
		SimpleTile copy = (SimpleTile) testPersist(tile1);
		assertTrue("Identity", tile1 != copy);
		assertEquals("Equality", tile1, copy);
	}}