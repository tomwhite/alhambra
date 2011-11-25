package org.tiling.alhambra.test;

import java.awt.geom.AffineTransform;

import junit.framework.*;

import org.tiling.alhambra.geom.*;

public class Tools2DOnLineSegmentTest extends TestCase {

	private Point2D A, B, C, D;
	private AffineTransform t;

	public Tools2DOnLineSegmentTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(Tools2DOnLineSegmentTest.class);
	}
	
	public void setUp() {
		A = new Point2D(1, 0);
		B = new Point2D(2, 0);
		double piby3 = Math.PI / 3;
		t = new AffineTransform(new double[] {Math.cos(piby3), -Math.sin(piby3), Math.cos(piby3), Math.sin(piby3)});
	}

	private void checkOnLineSegment(String message, Point2D P, Point2D A, Point2D B) {
		Point2D Pr = new Point2D(P.y, P.x);
		Point2D Ar = new Point2D(A.y, A.x);
		Point2D Br = new Point2D(B.y, B.x);
		Point2D Prot = new Point2D(0, 0);
		t.transform(P, Prot);
		Point2D Arot = new Point2D(0, 0);
		t.transform(A, Arot);
		Point2D Brot = new Point2D(0, 0);
		t.transform(B, Brot);
		assertTrue(message + " [1]", Tools2D.onLineSegment(P, A, B));
		assertTrue(message + " [2]", Tools2D.onLineSegment(P, B, A));
		assertTrue(message + " [1r]", Tools2D.onLineSegment(Pr, Ar, Br));
		assertTrue(message + " [2r]", Tools2D.onLineSegment(Pr, Br, Ar));
		assertTrue(message + " [1rot]", Tools2D.onLineSegment(Prot, Arot, Brot));
		assertTrue(message + " [2rot]", Tools2D.onLineSegment(Prot, Brot, Arot));
	}

	private void checkNotOnLineSegment(String message, Point2D P, Point2D A, Point2D B) {
		Point2D Pr = new Point2D(P.y, P.x);
		Point2D Ar = new Point2D(A.y, A.x);
		Point2D Br = new Point2D(B.y, B.x);
		Point2D Prot = new Point2D(0, 0);
		t.transform(P, Prot);
		Point2D Arot = new Point2D(0, 0);
		t.transform(A, Arot);
		Point2D Brot = new Point2D(0, 0);
		t.transform(B, Brot);
		assertTrue(message + " [1]", !Tools2D.onLineSegment(P, A, B));
		assertTrue(message + " [2]", !Tools2D.onLineSegment(P, B, A));
		assertTrue(message + " [1r]", !Tools2D.onLineSegment(Pr, Ar, Br));
		assertTrue(message + " [2r]", !Tools2D.onLineSegment(Pr, Br, Ar));
		assertTrue(message + " [1rot]", !Tools2D.onLineSegment(Prot, Arot, Brot));
		assertTrue(message + " [2rot]", !Tools2D.onLineSegment(Prot, Brot, Arot));
	}

	public void testOnLineSegment1() {
		checkNotOnLineSegment("1", new Point2D(0, 0), A, B);
	}

	public void testOnLineSegment2() {
		checkNotOnLineSegment("2", new Point2D(1 - 2 * Tools2D.eps, 0), A, B);
	}

	public void testOnLineSegment2a() {
		checkOnLineSegment("2a", new Point2D(1 - Tools2D.eps / 2, 0), A, B);
	}

	public void testOnLineSegment3() {
		checkOnLineSegment("3", new Point2D(1, 0), A, B);
	}

	public void testOnLineSegment4() {
		checkOnLineSegment("4", new Point2D(1 + Tools2D.eps, 0), A, B);
	}

	public void testOnLineSegment5() {
		checkOnLineSegment("5", new Point2D(2 - Tools2D.eps, 0), A, B);
	}

	public void testOnLineSegment6() {
		checkOnLineSegment("6", new Point2D(2, 0), A, B);
	}

	public void testOnLineSegment6a() {
		checkOnLineSegment("6a", new Point2D(2 + Tools2D.eps / 2, 0), A, B);
	}

	public void testOnLineSegment7() {
		checkNotOnLineSegment("7", new Point2D(2 + 2 * Tools2D.eps, 0), A, B);
	}

	public void testOnLineSegment8() {
		checkNotOnLineSegment("8", new Point2D(3, 0), A, B);
	}

	public void testOnLineSegment9() {
		checkNotOnLineSegment("9", new Point2D(1.5, 2 * Tools2D.eps), A, B);
	}

	public void testOnLineSegment9a() {
		checkOnLineSegment("9a", new Point2D(1.5, Tools2D.eps / 2), A, B);
	}




}