package org.tiling.alhambra.tile.polygon;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoPolygon extends Prototile {
	public ProtoPolygon(Polygon polygon, int rotationalSymmetry, AffineTransform reflection) {
		super(polygon, new SymmetryGroup(rotationalSymmetry, reflection));
	}
}