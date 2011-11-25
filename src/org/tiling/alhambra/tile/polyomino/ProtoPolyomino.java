package org.tiling.alhambra.tile.polyomino;

import java.awt.geom.AffineTransform;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoPolyomino extends Prototile {

	public ProtoPolyomino(Polyomino polyomino, int rotationalSymmetry, AffineTransform reflection) {
		super(polyomino, new SymmetryGroup(rotationalSymmetry, reflection));
	}

}