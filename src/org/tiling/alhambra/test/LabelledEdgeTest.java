package org.tiling.alhambra.test;

import java.util.*;

import junit.framework.*;

import org.tiling.alhambra.*;

public class LabelledEdgeTest extends TestCase {

	Vertex v1, v2, v3, v4, v5, v6;
	
	public LabelledEdgeTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(LabelledEdgeTest.class);
	}
	
	public void setUp() {
		v1 = new Vertex(0, 0);
		v2 = new Vertex(1, 0);
		v3 = new Vertex(2, 0);
		v4 = new Vertex(2, 1);		
		v5 = new Vertex(1, 1); 
		v6 = new Vertex(0, 1); 
	}

	private void checkMatches(String message, Edge edge1, Edge edge2) {
		assertTrue(message + " (edge1, edge2)", edge1.matches(edge2));
		assertTrue(message + " (edge2, edge1)", edge2.matches(edge1));
		edge1.reverse();
		edge2.reverse();
		assertTrue(message + " (edge1, edge2) reversed", edge1.matches(edge2));
		assertTrue(message + " (edge2, edge1) reversed", edge2.matches(edge1));
	}
 
	private void checkNotMatches(String message, Edge edge1, Edge edge2) {
		assertTrue(message + " (edge1, edge2)", !edge1.matches(edge2));
		assertTrue(message + " (edge2, edge1)", !edge2.matches(edge1));
		edge1.reverse();
		edge2.reverse();
		assertTrue(message + " (edge1, edge2) reversed", !edge1.matches(edge2));
		assertTrue(message + " (edge2, edge1) reversed", !edge2.matches(edge1));
	}
 
	public void testCCurveMatches1() { 
		Edge edge1 = new LabelledEdge(v2, v5, 0, Curve.C_CURVE);
		Edge edge2 = new LabelledEdge(v5, v2, 0, Curve.C_CURVE);
		checkMatches("1 matches 1", edge1, edge1);
		checkMatches("1 matches 2", edge1, edge2);
		checkMatches("2 matches 2", edge2, edge2);
	} 
 
	public void testCCurveMatches2() { 
		Edge edge1 = new LabelledEdge(v2, v5, 1, Curve.C_CURVE);
		Edge edge2 = new LabelledEdge(v5, v2, -1, Curve.C_CURVE);
		checkMatches("1 matches 1", edge1, edge1);
		checkMatches("1 matches 2", edge1, edge2);
		checkMatches("2 matches 2", edge2, edge2);
	} 
 
	public void testCCurveMatches3() { 
		Edge edge1 = new LabelledEdge(v2, v5, 1, Curve.C_CURVE);
		Edge edge2 = new LabelledEdge(v5, v2, 1, Curve.C_CURVE);
		checkMatches("1 matches 1", edge1, edge1);
		checkNotMatches("1 does not match 2", edge1, edge2);
		checkMatches("2 matches 2", edge2, edge2);
	} 
 
	public void testCCurveMatches4() { 
		Edge edge1 = new LabelledEdge(v2, v5, 1, Curve.C_CURVE);
		Edge edge2 = new LabelledEdge(v5, v2, 2, Curve.C_CURVE);
		checkMatches("1 matches 1", edge1, edge1);
		checkNotMatches("1 does not match 2", edge1, edge2);
		checkMatches("2 matches 2", edge2, edge2);
	} 
 
	public void testSCurveMatches1() { 
		Edge edge1 = new LabelledEdge(v2, v5, 0, Curve.S_CURVE);
		Edge edge2 = new LabelledEdge(v5, v2, 0, Curve.S_CURVE);
		checkMatches("1 matches 1", edge1, edge1);
		checkMatches("1 matches 2", edge1, edge2);
		checkMatches("2 matches 2", edge2, edge2);
	} 
 
	public void testSCurveMatches2() { 
		Edge edge1 = new LabelledEdge(v2, v5, 1, Curve.S_CURVE);
		Edge edge2 = new LabelledEdge(v5, v2, 1, Curve.S_CURVE);
		checkMatches("1 matches 1", edge1, edge1);
		checkMatches("1 matches 2", edge1, edge2);
		checkMatches("2 matches 2", edge2, edge2);
	} 
 
	public void testSCurveMatches3() { 
		Edge edge1 = new LabelledEdge(v2, v5, 1, Curve.S_CURVE);
		Edge edge2 = new LabelledEdge(v5, v2, -1, Curve.S_CURVE);
		checkMatches("1 matches 1", edge1, edge1);
		checkNotMatches("1 does not match 2", edge1, edge2);
		checkMatches("2 matches 2", edge2, edge2);
	} 
 
	public void testSCurveMatches4() { 
		Edge edge1 = new LabelledEdge(v2, v5, 1, Curve.S_CURVE);
		Edge edge2 = new LabelledEdge(v5, v2, 2, Curve.S_CURVE);
		checkMatches("1 matches 1", edge1, edge1);
		checkNotMatches("1 does not match 2", edge1, edge2);
		checkMatches("2 matches 2", edge2, edge2);
	} 
 
}