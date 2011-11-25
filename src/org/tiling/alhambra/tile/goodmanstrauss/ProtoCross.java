package org.tiling.alhambra.tile.goodmanstrauss;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoCross extends Prototile {

	public ProtoCross() {
		super(new Cross(), new SymmetryGroup(4, false));
	}

}