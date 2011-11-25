package org.tiling.alhambra.tile.polygon;

import java.awt.geom.AffineTransform;
import java.util.Arrays;

import org.tiling.alhambra.AbstractPrototileFactory;
import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class RegularPolygonFactory extends AbstractPrototileFactory {

	private static RegularPolygonFactory instance = new RegularPolygonFactory();

	private RegularPolygonFactory() {
	}

	public static RegularPolygonFactory getInstance() {
		return instance;
	}

	public Prototile createPrototile(String name) {

		if (name.startsWith("regular_") && name.endsWith("-gon")) {
			try {
				String sides = name.substring(name.indexOf('_') + 1, name.indexOf("-gon"));
				int n = Integer.parseInt(sides);
				if (n < 3) {
					return null;
				}
				double[] interiorAngles = new double[n];
				Arrays.fill(interiorAngles, 180 * (n - 2) / n);
				return createPolygon(name, interiorAngles, (n % 2) + 1, null);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		return null;

	}

	private static final String[] mnemonics;

	static {
		int max = 12;
		mnemonics = new String[max - 2];
		for (int i = 3; i <= max; i++) {
			mnemonics[i - 3] = "regular_" + i + "-gon";
		}
	}

	public String[] getPrototileNames() {
		return mnemonics;
	}
	
	private Prototile createPolygon(String name, double[] angles, int rotationalSymmetry, AffineTransform reflection) {
		return new ProtoPolygon(PolygonConstructor.createPolygon(name, angles),
					rotationalSymmetry, reflection);
	}

}