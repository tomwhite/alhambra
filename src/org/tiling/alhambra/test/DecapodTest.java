package org.tiling.alhambra.test;

import java.util.*;

import junit.framework.*;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.*;
import org.tiling.alhambra.tile.penrose.*;

import org.tiling.alhambra.tile.penrose.cartwheel.*;

import java.awt.geom.AffineTransform;public class DecapodTest extends TestCase {

	public DecapodTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(DecapodTest.class);
	}

	public void testConstructor() {
		assertEquals("0000000000", new Decapod(0).toString());
		assertEquals("0000000001", new Decapod(1).toString());
		assertEquals("1000000000", new Decapod(512).toString());
		assertEquals("1111111111", new Decapod(1023).toString());
	}

	public void testToInt() {
		assertEquals(0, new Decapod(0).toInt());
		assertEquals(1, new Decapod(1).toInt());
		assertEquals(512, new Decapod(512).toInt());
		assertEquals(1023, new Decapod(1023).toInt());
	}

	public void testOrder() {
		assertEquals(0, new Decapod("0000000000").getOrder());
		assertEquals(1, new Decapod("1000000000").getOrder());
		assertEquals(1, new Decapod("0100000000").getOrder());
		assertEquals(1, new Decapod("0010000000").getOrder());
		assertEquals(1, new Decapod("0001000000").getOrder());
		assertEquals(1, new Decapod("0000100000").getOrder());
		assertEquals(1, new Decapod("0000010000").getOrder());
		assertEquals(1, new Decapod("0000001000").getOrder());
		assertEquals(1, new Decapod("0000000100").getOrder());
		assertEquals(1, new Decapod("0000000010").getOrder());
		assertEquals(1, new Decapod("0000000001").getOrder());
		assertEquals(2, new Decapod("1000000001").getOrder());
		assertEquals(10, new Decapod("1111111111").getOrder());
	}

	public void testGetEquivalenceClass() {
		assertEquals("Circular Saw", 2, Decapod.CIRCULAR_SAW.getEquivalenceClass().length);
		assertEquals("Batman", 10, Decapod.BATMAN.getEquivalenceClass().length);
		assertEquals("Asterix", 20, Decapod.ASTERIX.getEquivalenceClass().length);
	}

	public void testGetEquivalenceClassRepresentatives() {
		Decapod[] representatives = Decapod.getEquivalenceClassRepresentatives();

		for (int order = 0; order < 6; order++) {
			int count = 0;
			for (int i = 0; i < representatives.length; i++) {
				if (representatives[i].getOrder() == order) {
					count++;
				}
			}
		}

		assertEquals(62, representatives.length);
	}

	public void testGetTile() {
		Decapod[] representatives = Decapod.getEquivalenceClassRepresentatives();
		for (int i = 0; i < representatives.length; i++) {
			Patch p = (Patch) representatives[i].getTile();
			assertEquals(i + ", " + representatives[i].toString(), 10, p.getTiles().size());
		}
	}

	public void testTransformTile() {
		Decapod zero = new Decapod(0);
		Patch transformed = (Patch) zero.getTile().transform(AffineTransform.getTranslateInstance(1.0, 0.0));
		assertEquals(10, transformed.getTiles().size());
	}}