package org.tiling.alhambra.tile.polygon;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.AbstractPrototileFactory;
import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class PolygonFactory extends AbstractPrototileFactory {

	private static PolygonFactory instance = new PolygonFactory();

	private PolygonFactory() {
	}

	public static PolygonFactory getInstance() {
		return instance;
	}

	public Prototile createPrototile(String name) {

		if (name.equalsIgnoreCase("jenkins_pentagon"))
			return createPolygon(name, new double[] {45, 270, 22.5, 112.5, 90},
						8, null);
		if (name.equalsIgnoreCase("hirschhorn_pentagon"))
			return createPolygon(name, new double[] {100.0, 140.0, 60.0, 160.0, 80.0},
						6, SymmetryGroup.REFLECTION_IN_X_EQUALS_ZERO);
		if (name.equalsIgnoreCase("108-36-252-36-108_pentagon"))
			return createPolygon(name, new double[] {108.0, 36.0, 252.0, 36.0, 108.0},
						10, null);
		if (name.equalsIgnoreCase("60-90-150-30-210_pentagon"))
			return createPolygon(name, new double[] {60.0, 90.0, 150.0, 30.0, 210.},
						12, SymmetryGroup.REFLECTION_IN_X_EQUALS_ZERO);

		return null;

	}

	private static final String[] mnemonics = new String[] {
		"jenkins_pentagon", "hirschhorn_pentagon",
		"108-36-252-36-108_pentagon", "60-90-150-30-210_pentagon",
	};

	public String[] getPrototileNames() {
		return mnemonics;
	}
	
	private Prototile createPolygon(String name, double[] angles, int rotationalSymmetry, AffineTransform reflection) {
		return new ProtoPolygon(PolygonConstructor.createPolygon(name, angles),
					rotationalSymmetry, reflection);
	}

}