package org.tiling.alhambra.test;

import junit.framework.*;

import org.tiling.alhambra.*;

public class SymbolicTransformTest extends TestCase {
	
	public SymbolicTransformTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(SymbolicTransformTest.class);
	}

	private void checkCanonicalSeries(String expected, String series) {
		assertEquals("Series " + series, expected, new SymbolicTransform(SymmetryGroup.D3, series).getCanonicalSeries());
	}

	public void test() {
		SymbolicTransform st = new SymbolicTransform(SymmetryGroup.D3);
		assertEquals("I", st.getCanonicalSeries());
		st.rotate(1);
		assertEquals("T", st.getCanonicalSeries());
		st.rotate(3);
		assertEquals("T", st.getCanonicalSeries());
		st.rotate(-1);
		assertEquals("I", st.getCanonicalSeries());
		st.reflect(1);
		assertEquals("R", st.getCanonicalSeries());
		st.reflect(1);
		assertEquals("I", st.getCanonicalSeries());
		st.reflect(-1);
		assertEquals("R", st.getCanonicalSeries());
	}

	public void testIdentity() {
		checkCanonicalSeries("I", "");
		checkCanonicalSeries("I", "illegal characters");
		checkCanonicalSeries("I", "I");
		checkCanonicalSeries("I", "Illegal characters");
		checkCanonicalSeries("I", "II");
	}

	public void testRotations() {
		checkCanonicalSeries("T", "T");
		checkCanonicalSeries("TT", "TT");
		checkCanonicalSeries("I", "TTT");
		checkCanonicalSeries("T", "TTTT");
	}

	public void testReflections() {
		checkCanonicalSeries("R", "R");
		checkCanonicalSeries("I", "RR");
		checkCanonicalSeries("R", "RRR");
		checkCanonicalSeries("I", "RRRR");
	}

	public void testCombinations() {
		checkCanonicalSeries("TR", "TR");
		checkCanonicalSeries("TTR", "TTR");
		checkCanonicalSeries("R", "TTTR");
		checkCanonicalSeries("TR", "TTTTR");

		checkCanonicalSeries("TTR", "RT");
		checkCanonicalSeries("R", "TRT");
		checkCanonicalSeries("TR", "TTRT");
		checkCanonicalSeries("TTR", "TTTRT");

		checkCanonicalSeries("TR", "RTT");
		checkCanonicalSeries("TTR", "TRTT");
		checkCanonicalSeries("R", "TTRTT");

		checkCanonicalSeries("R", "RTTT");
		checkCanonicalSeries("TR", "TRTTT");

		checkCanonicalSeries("TTR", "RTTTT");

	}
}