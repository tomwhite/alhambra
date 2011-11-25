package org.tiling.alhambra.test;

import java.util.*;

import junit.framework.*;

import org.tiling.alhambra.*;

import org.tiling.alhambra.geom.Vector2D;import org.tiling.alhambra.geom.Tools2D;public class EdgeTest extends TestCase {

	Vertex v1, v2, v3, v4;
	
	public EdgeTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(EdgeTest.class);
	}
	
	public void setUp() {
		v1 = new Vertex(0, 0);
		v2 = new Vertex(1, 0);
		v3 = new Vertex(1, 1);
		v4 = new Vertex(0, 1);		
	}

	public void testEquals() {
		Edge edge1 = new Edge(v1, v2);
		Edge edge2 = new Edge(v2, v1);
		assertEquals(edge1, edge2);
	}

	public void testMatches() {
		Edge edge1 = new Edge(v1, v2, 0);
		Edge edge2 = new Edge(v1, v2, 1);
		Edge edge3 = new Edge(v1, v2, 1);
		assertTrue(!edge1.matches(edge2));
		assertTrue(edge2.matches(edge3)); //??
	}
	
	public void testLength() {
		Edge edge1 = new Edge(v1, v2);
		Edge edge2 = new Edge(v2, v1);		
		assertTrue(1.0 == edge1.getLength());	
		assertTrue(1.0 == edge2.getLength());
	}
	
	public void testGetNoEdges() {
		Vertex[] vertices = new Vertex[] {v1};
		List edges = Edge.getEdges(vertices);
		assertEquals(0, edges.size());
	}
	
	public void testGetEdge() {
		Vertex[] vertices = new Vertex[] {v1, v2};
		List edges = Edge.getEdges(vertices);
		assertEquals(1, edges.size());
		assertEquals(new Edge(v1, v2), edges.get(0));
	}
	
	public void testGetEdges() {
		Vertex[] vertices = new Vertex[] {v1, v2, v3, v4};
		List edges = Edge.getEdges(vertices);
		assertEquals(4, edges.size());
		assertEquals(new Edge(v1, v2), edges.get(0));
		assertEquals(new Edge(v2, v3), edges.get(1));
		assertEquals(new Edge(v3, v4), edges.get(2));
		assertEquals(new Edge(v4, v1), edges.get(3));		
	}
	
	public void testGetVertices() {
		Edge[] edges = new Edge[] {new Edge(v1, v2), new Edge(v2, v3),
						new Edge(v3, v4), new Edge(v4, v1)};
		List vertices = Edge.getVertices(edges);
		assertEquals(4, vertices.size());
		assertEquals(v1, vertices.get(0));
		assertEquals(v2, vertices.get(1));
		assertEquals(v3, vertices.get(2));
		assertEquals(v4, vertices.get(3));		
	}
	
	public void testGetTwistedVertices() {
		Edge[] edges = new Edge[] {new Edge(v1, v2), new Edge(v3, v2),
						new Edge(v3, v4), new Edge(v4, v1)};
		List vertices = Edge.getVertices(edges);
		assertNull(vertices);	
	}
	
	public void testGetDiscontinuousVertices() {
		Edge[] edges = new Edge[] {new Edge(v1, v2),
						new Edge(v3, v4), new Edge(v4, v1)};
		List vertices = Edge.getVertices(edges);
		assertNull(vertices);	
	}

	public void testReverse() {
		Vertex v0 = new Vertex(0, 0); 
		Vertex v1 = new Vertex(1, 0); 
		Vertex v2 = new Vertex(1, 1); 
		Edge e0 = new Edge(v0, v1); 
		Edge e1 = new Edge(v1, v2); 
		Edge e2 = new Edge(v2, v0); 
		Edge[] edges = new Edge[] {e0, e1, e2}; 
		edges = Edge.reverse(edges); 
		assertTrue("e0", edges[0].equals(new Edge(v0, v2))); 
		assertTrue("e1", edges[1].equals(new Edge(v2, v1))); 
		assertTrue("e2", edges[2].equals(new Edge(v1, v0))); 
	}		

	public void testClone() {
		Edge edge = new Edge(v1, v2);
		Edge clone = (Edge) edge.clone();
		assertTrue("Identity", edge != clone);
		assertEquals("Equality", edge, clone);
		assertTrue("Identity v1", edge.getV1() != clone.getV1());
		assertTrue("Identity v2", edge.getV2() != clone.getV2());
	}	public void testGetVector2D() {
		Edge edge = new Edge(v1, v2);
		Vector2D v1 = edge.getVector2D();
		edge.reverse();
		Vector2D v2 = edge.getVector2D();
		assertTrue(Tools2D.reverseEquals(v1, v2));
	}}