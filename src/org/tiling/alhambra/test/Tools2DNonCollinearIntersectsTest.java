package org.tiling.alhambra.test;

import junit.framework.*;

import org.tiling.alhambra.geom.*;

public class Tools2DNonCollinearIntersectsTest extends TestCase {

	public Tools2DNonCollinearIntersectsTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(Tools2DNonCollinearIntersectsTest.class);
	}

	private void checkIntersects(String message, Point2D p1, Point2D p2, Point2D q1, Point2D q2) {
		Point2D p1r = new Point2D(p1.y, p1.x);
		Point2D p2r = new Point2D(p2.y, p2.x);
		Point2D q1r = new Point2D(q1.y, q1.x);
		Point2D q2r = new Point2D(q2.y, q2.x);
		assertTrue(message + " [1]", Tools2D.nonCollinearIntersects(p1, p2, q1, q2));
		assertTrue(message + " [2]", Tools2D.nonCollinearIntersects(p1, p2, q2, q1));
		assertTrue(message + " [3]", Tools2D.nonCollinearIntersects(p2, p1, q1, q2));
		assertTrue(message + " [4]", Tools2D.nonCollinearIntersects(p2, p1, q2, q1));
		assertTrue(message + " [1r]", Tools2D.nonCollinearIntersects(p1r, p2r, q1r, q2r));
		assertTrue(message + " [2r]", Tools2D.nonCollinearIntersects(p1r, p2r, q2r, q1r));
		assertTrue(message + " [3r]", Tools2D.nonCollinearIntersects(p2r, p1r, q1r, q2r));
		assertTrue(message + " [4r]", Tools2D.nonCollinearIntersects(p2r, p1r, q2r, q1r));
	}

	private void checkNotIntersects(String message, Point2D p1, Point2D p2, Point2D q1, Point2D q2) {
		Point2D p1r = new Point2D(p1.y, p1.x);
		Point2D p2r = new Point2D(p2.y, p2.x);
		Point2D q1r = new Point2D(q1.y, q1.x);
		Point2D q2r = new Point2D(q2.y, q2.x);
		assertTrue(message + " [1]", !Tools2D.nonCollinearIntersects(p1, p2, q1, q2));
		assertTrue(message + " [2]", !Tools2D.nonCollinearIntersects(p1, p2, q2, q1));
		assertTrue(message + " [3]", !Tools2D.nonCollinearIntersects(p2, p1, q1, q2));
		assertTrue(message + " [4]", !Tools2D.nonCollinearIntersects(p2, p1, q2, q1));
		assertTrue(message + " [1r]", !Tools2D.nonCollinearIntersects(p1r, p2r, q1r, q2r));
		assertTrue(message + " [2r]", !Tools2D.nonCollinearIntersects(p1r, p2r, q2r, q1r));
		assertTrue(message + " [3r]", !Tools2D.nonCollinearIntersects(p2r, p1r, q1r, q2r));
		assertTrue(message + " [4r]", !Tools2D.nonCollinearIntersects(p2r, p1r, q2r, q1r));
	}

	public void testIntersects1() {
		checkIntersects("1", new Point2D(0, 0), new Point2D(3, 0), new Point2D(0, -2), new Point2D(3, 1));
	}

	public void testIntersects2() {
		checkNotIntersects("2", new Point2D(0, 0), new Point2D(3, 0), new Point2D(0, -2), new Point2D(1, -1));
	}

	public void testIntersects3() {
		checkNotIntersects("3", new Point2D(0, 0), new Point2D(3, 0), new Point2D(-2, -2), new Point2D(1, 1));
	}

	public void testIntersects4() {
		checkNotIntersects("4", new Point2D(0, 0), new Point2D(3, 0), new Point2D(-2, -2), new Point2D(-1, -1));
	}

	public void testIntersects5() {
		checkNotIntersects("5", new Point2D(0, 0), new Point2D(3, 0), new Point2D(-2, -2), new Point2D(0, 0));
	}

	// Collinear

	public void testIntersects6() {
		checkNotIntersects("6", new Point2D(0, 0), new Point2D(3, 0), new Point2D(-2, 0), new Point2D(-1, 0));
	}

	public void testIntersects7() {
		checkNotIntersects("7", new Point2D(0, 0), new Point2D(3, 0), new Point2D(0, 0), new Point2D(2, 0));
	}

	public void testIntersects8() {
		checkNotIntersects("8", new Point2D(0, 0), new Point2D(3, 0), new Point2D(1, 0), new Point2D(2, 0));
	}

	public void testIntersects9() {
		checkNotIntersects("9", new Point2D(0, 0), new Point2D(3, 0), new Point2D(-1, 0), new Point2D(0, 0));
	}

	public void testIntersects10() {
		checkNotIntersects("10", new Point2D(0, 0), new Point2D(3, 0), new Point2D(-1, 0), new Point2D(1, 0));
	}

	// Collinear sloping

	public void testIntersects11() {
		checkNotIntersects("11", new Point2D(0, 0), new Point2D(3, 3), new Point2D(-2, -2), new Point2D(-1, -1));
	}

	public void testIntersects12() {
		checkNotIntersects("12", new Point2D(0, 0), new Point2D(3, 3), new Point2D(0, 0), new Point2D(2, 2));
	}

	public void testIntersects13() {
		checkNotIntersects("13", new Point2D(0, 0), new Point2D(3, 3), new Point2D(1, 1), new Point2D(2, 2));
	}

	public void testIntersects14() {
		checkNotIntersects("14", new Point2D(0, 0), new Point2D(3, 3), new Point2D(-1, -1), new Point2D(0, 0));
	}

	public void testIntersects15() {
		checkNotIntersects("15", new Point2D(0, 0), new Point2D(3, 3), new Point2D(-1, -1), new Point2D(1, 1));
	}



}