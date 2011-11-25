package org.tiling.alhambra.test;

import junit.framework.*;

public class AllTests extends TestCase {

	public AllTests(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();

		suite.addTest(DecapodTest.suite());
		suite.addTest(EdgeTest.suite()); 
		suite.addTest(FittingTilerTest.suite()); 
		suite.addTest(GAEncoderTest.suite()); 
		suite.addTest(LabelledEdgeTest.suite()); 
		suite.addTest(Point2DTest.suite()); 
		suite.addTest(SerializationTest.suite());
		suite.addTest(SimplePatchTest.suite()); 
		suite.addTest(SimpleTileJoinTest.suite()); 
		suite.addTest(SimpleTileTest.suite()); 
		suite.addTest(SymbolicTransformTest.suite()); 
		suite.addTest(SymmetryGroupTest.suite()); 
		suite.addTest(Tools2DIntersectsTest.suite()); 
		suite.addTest(Tools2DNonCollinearIntersectsTest.suite()); 
		suite.addTest(Tools2DOnLineSegmentTest.suite()); 
		suite.addTest(Triangle2DTest.suite()); 

		suite.addTest(Vector2DTest.suite()); 

		suite.addTest(VertexTest.suite());

		return suite;
	}

}