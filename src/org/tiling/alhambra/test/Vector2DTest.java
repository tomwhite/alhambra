package org.tiling.alhambra.test;

import junit.framework.*;

import org.tiling.alhambra.geom.*;

public class Vector2DTest extends TestCase {
	public Vector2DTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(Vector2DTest.class);
	}

	public void testTools2DEquals() {
		Vector2D v = new Vector2D(4, -3);
		Vector2D copy = new Vector2D(4, -3);
		Vector2D w = new Vector2D(4 + Tools2D.eps / 2, -3 + Tools2D.eps / 2);
		assertTrue("identity", Tools2D.equals(v, v));
		assertTrue("copy", Tools2D.equals(v, copy));
		assertTrue("close", Tools2D.equals(v, w));
	}

	public void testTools2DReverseEquals() {
		Vector2D v = new Vector2D(4, -3);
		Vector2D minusV = new Vector2D(-4, 3);
		Vector2D w = new Vector2D(4 + Tools2D.eps / 2, -3 + Tools2D.eps / 2);
		Vector2D minusW = new Vector2D(-4 - Tools2D.eps / 2, 3 - Tools2D.eps / 2);
		assertTrue("identity", !Tools2D.reverseEquals(v, v));
		assertTrue("v and minusV", Tools2D.reverseEquals(v, minusV));
		assertTrue("w and minusV", Tools2D.reverseEquals(w, minusV));
		assertTrue("v and minusW", Tools2D.reverseEquals(v, minusW));
		assertTrue("w and minusW", Tools2D.reverseEquals(w, minusW));
	}
	public void testClone() {
		Vector2D v = new Vector2D(4, -3);
		Vector2D clone = (Vector2D) v.clone();
		assertTrue("Identity", v != clone);
		assertEquals("Equality", v, clone);
	}}