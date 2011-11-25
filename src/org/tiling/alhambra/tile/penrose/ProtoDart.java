package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoDart extends Prototile {

	public ProtoDart() {
		super(new Dart(), new SymmetryGroup(5, true));
	}

}