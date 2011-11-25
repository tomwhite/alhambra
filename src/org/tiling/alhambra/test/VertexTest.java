package org.tiling.alhambra.test;

import junit.framework.*;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.*;

public class VertexTest extends TestCase {

	public VertexTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(VertexTest.class);
	}

	public void testEquals() {
		Vertex p = new Vertex(1.5, -3);
		Vertex q = new Vertex(1.5 + Tools2D.eps / 2, -3 + Tools2D.eps / 2);
		Vertex r = new Vertex(1.5, -3, 1); 
		Vertex s = new Vertex(1.5, -3, 2); 
		assertEquals("identity", p, p);
		assertEquals("clone", p, (Vertex) p.clone());
		assertEquals("close", p, q);
		assertTrue("p matches r", p.equals(r)); 
		assertTrue("p matches s", p.equals(s)); 
		assertTrue("r and s do not match", !r.equals(s)); 
		assertEquals("clone marked", r, (Vertex) r.clone());
	}

	public void testMatches() {
		assertTrue("1 does not match 2", !Vertex.matches(1, 2));
		assertTrue("2 does not match 1", !Vertex.matches(2, 1));
		assertTrue("1 matches itself", Vertex.matches(1, 1));
		assertTrue("0 matches 1", Vertex.matches(0, 1));
		assertTrue("1 matches 0", Vertex.matches(1, 0));
		assertTrue("0 matches itself", Vertex.matches(0, 0));
	}

	public void testReverse() { 
		Vertex v0 = new Vertex(0, 0); 
		Vertex v1 = new Vertex(1, 0); 
		Vertex v2 = new Vertex(1, 1); 
		Vertex[] vertices = new Vertex[] {v0, v1, v2}; 
		vertices = Vertex.reverse(vertices); 
		assertTrue("v0", v0 == vertices[0]); 
		assertTrue("v1", v1 == vertices[2]); 
		assertTrue("v2", v2 == vertices[1]); 
	} 


}