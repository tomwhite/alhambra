package org.tiling.alhambra.test;

import junit.framework.*;

import org.tiling.alhambra.geom.*;

public class Point2DTest extends SerializationTestCase {

	public Point2DTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(Point2DTest.class);
	}

	public void testCoincidence() {
		Point2D p = new Point2D(4, -3);
		Point2D q = new Point2D(4 + Tools2D.eps / 2, -3 + Tools2D.eps / 2);
		assertTrue("identity", Tools2D.coincident(p, p));
		assertTrue("clone", Tools2D.coincident(p, (Point2D) p.clone()));
		assertTrue("close", Tools2D.coincident(p, q));
	}

	public void testPointOnLine() {
		Point2D p = new Point2D(1, 1);
		Point2D q = new Point2D(0, 2);
		Point2D mid = new Point2D(0.5, 1.5);
		assertEquals(mid, Tools2D.pointOnLine(p, q, 0.5));
	}
	public void testClone() {
		Point2D p = new Point2D(4, -3);
		Point2D clone = (Point2D) p.clone();
		assertTrue("Identity", p != clone);
		assertEquals("Equality", p, clone);
	}	public void testSerializable() {
		Point2D p = new Point2D(4, -3);
		Point2D copy = (Point2D) testPersist(p);
		assertTrue("Identity", p != copy);
		assertEquals("Equality", p, copy);
	}}