package org.tiling.alhambra.test;

import junit.framework.*;

import org.tiling.alhambra.geom.*;

public class Triangle2DTest extends TestCase {

	Triangle2D basic;

	public Triangle2DTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(Triangle2DTest.class);
	}

	public void setUp() {
		basic = new Triangle2D(new Point2D(0, 0), new Point2D(4, 3), new Point2D(0, 6));
	}

	public void checkNotOverlaps(String message, Triangle2D T, Triangle2D U) {
		checkNotOverlaps0(message, T, U);
		checkNotOverlaps0(message + " (r)", U, T);
	}

	public void checkNotOverlaps0(String message, Triangle2D T, Triangle2D U) {
		Triangle2D T1 = T;
		Triangle2D T2 = new Triangle2D(T.B, T.C, T.A);
		Triangle2D T3 = new Triangle2D(T.C, T.A, T.B);
		Triangle2D U1 = U;
		Triangle2D U2 = new Triangle2D(U.B, U.C, U.A);
		Triangle2D U3 = new Triangle2D(U.C, U.A, U.B);
		assertTrue(message + " [11]", !Tools2D.overlaps(T1, U1));
		assertTrue(message + " [12]", !Tools2D.overlaps(T1, U2));
		assertTrue(message + " [13]", !Tools2D.overlaps(T1, U3));
		assertTrue(message + " [21]", !Tools2D.overlaps(T2, U1));
		assertTrue(message + " [22]", !Tools2D.overlaps(T2, U2));
		assertTrue(message + " [23]", !Tools2D.overlaps(T2, U3));
		assertTrue(message + " [31]", !Tools2D.overlaps(T3, U1));
		assertTrue(message + " [32]", !Tools2D.overlaps(T3, U2));
		assertTrue(message + " [33]", !Tools2D.overlaps(T3, U3));
	}
 
	public void checkOverlaps(String message, Triangle2D T, Triangle2D U) {
		checkOverlaps0(message, T, U);
		checkOverlaps0(message + " (r)", U, T);
	}

	public void checkOverlaps0(String message, Triangle2D T, Triangle2D U) {
		Triangle2D T1 = T;
		Triangle2D T2 = new Triangle2D(T.B, T.C, T.A);
		Triangle2D T3 = new Triangle2D(T.C, T.A, T.B);
		Triangle2D U1 = U;
		Triangle2D U2 = new Triangle2D(U.B, U.C, U.A);
		Triangle2D U3 = new Triangle2D(U.C, U.A, U.B);
		assertTrue(message + " [11]", Tools2D.overlaps(T1, U1));
		assertTrue(message + " [12]", Tools2D.overlaps(T1, U2));
		assertTrue(message + " [13]", Tools2D.overlaps(T1, U3));
		assertTrue(message + " [21]", Tools2D.overlaps(T2, U1));
		assertTrue(message + " [22]", Tools2D.overlaps(T2, U2));
		assertTrue(message + " [23]", Tools2D.overlaps(T2, U3));
		assertTrue(message + " [31]", Tools2D.overlaps(T3, U1));
		assertTrue(message + " [32]", Tools2D.overlaps(T3, U2));
		assertTrue(message + " [33]", Tools2D.overlaps(T3, U3));
	}
 
	public void testNoTouch1() {
		Triangle2D notouch1 = new Triangle2D(new Point2D(10, 0), new Point2D(14, 3), new Point2D(10, 6));
		checkNotOverlaps("NoTouch1", basic, notouch1);
	}

	public void testTouch1() {
		Triangle2D touch1 = new Triangle2D(new Point2D(-4, 0), new Point2D(0, 3), new Point2D(-4, 6));
		checkNotOverlaps("Touch1", basic, touch1);
	}

	public void testTouch2() {
		Triangle2D touch2 = new Triangle2D(new Point2D(0, 0), new Point2D(-4, 3), new Point2D(0, 6));
		checkNotOverlaps("Touch2", basic, touch2);
	}

	public void testTouch3() {
		Triangle2D touch3 = new Triangle2D(new Point2D(0, 1), new Point2D(-4, 3), new Point2D(0, 6));
		checkNotOverlaps("Touch2", basic, touch3);
	}

	public void testTouch4() { 
		Triangle2D touch4 = new Triangle2D(new Point2D(0, 1), new Point2D(-4, 3), new Point2D(0, 5)); 
		checkNotOverlaps("Touch4", basic, touch4);
	} 
 
	public void testTouch5() { 
		Triangle2D touch5a = new Triangle2D(new Point2D(0, 0), new Point2D(2, 0), new Point2D(1, 1));
		Triangle2D touch5b = new Triangle2D(new Point2D(0, 0), new Point2D(2, 2), new Point2D(0, 2));
		checkNotOverlaps("Touch2", touch5a, touch5b);
	} 
 
	public void testOverlaps1() { 
		Triangle2D overlaps1 = basic; 
		checkOverlaps("Overlaps1", basic, overlaps1);
	} 
 
	public void testOverlaps2() { 
		Triangle2D overlaps2 = new Triangle2D(new Point2D(0, 0), new Point2D(3, 3), new Point2D(0, 6)); 
		checkOverlaps("Overlaps2", basic, overlaps2);
	} 
 
	public void testOverlaps3() { 
		Triangle2D overlaps3 = new Triangle2D(new Point2D(0, 0), new Point2D(3, 6), new Point2D(0, 6)); 
		checkOverlaps("Overlaps3", basic, overlaps3);
	} 

	public void testOverlaps4() { 
		Triangle2D overlaps4 = new Triangle2D(new Point2D(1, 1), new Point2D(3, 3), new Point2D(1, 5)); 
		checkOverlaps("Overlaps4", basic, overlaps4);
	} 
 
	public void testOverlaps5() { 
		Triangle2D overlaps5 = new Triangle2D(new Point2D(2, 0), new Point2D(4, 0), new Point2D(3, 4)); 
		checkOverlaps("Overlaps5", basic, overlaps5);
	} 
 
	public void testOverlaps6() { 
		Triangle2D overlaps6 = new Triangle2D(new Point2D(0, 0), new Point2D(2, 1.5), new Point2D(0, 3)); 
		checkOverlaps("Overlaps6", basic, overlaps6);
	} 
 
	public void testOverlaps7() {
		double PHI = (1.0 + Math.sqrt(5)) / 2.0;
		Triangle2D overlaps7a = new Triangle2D(new Point2D(0, 0),
							new Point2D(2 * PHI + 1.0, 0),
							new Point2D((PHI + 1.0) * Math.cos(Math.PI / 5.0), (PHI + 1.0) * Math.sin(Math.PI / 5.0))); 
		Triangle2D overlaps7b = new Triangle2D(new Point2D(0, 0),
							new Point2D(PHI + 1.0, 0),
							new Point2D((2 * PHI + 1.0) * Math.cos(Math.PI / 5.0), (2 * PHI + 1.0) * Math.sin(Math.PI / 5.0))); 
		checkOverlaps("Overlaps7", overlaps7a, overlaps7b);
	} 
 
 
	public void testClone() {
		Triangle2D clone = (Triangle2D) basic.clone();
		assertTrue("Identity", basic != clone);
		assertEquals("Equality", basic, clone);
		assertTrue("Identity A", basic.A != clone.A);
		assertTrue("Identity B", basic.B != clone.B);
		assertTrue("Identity C", basic.C != clone.C);
	}}