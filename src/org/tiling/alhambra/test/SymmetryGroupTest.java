package org.tiling.alhambra.test;

import junit.framework.*;

import org.tiling.alhambra.*;

public class SymmetryGroupTest extends TestCase {
	
	public SymmetryGroupTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(SymmetryGroupTest.class);
	}

	public void testE() {
		SymmetryGroup sg = new SymmetryGroup("e");
		assertEquals("e", sg.toString());
		assertEquals(1, sg.getSymbolicTransforms().length);
	}

	public void testC2() {
		SymmetryGroup sg = new SymmetryGroup("c2");
		assertEquals("c2", sg.toString());
		assertEquals(2, sg.getSymbolicTransforms().length);
	}

	public void testD2() {
		SymmetryGroup sg = new SymmetryGroup("d2");
		assertEquals("d2", sg.toString());
		assertEquals(4, sg.getSymbolicTransforms().length);
	}
}