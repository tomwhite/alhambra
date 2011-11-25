package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoThinRhomb extends Prototile {

	public ProtoThinRhomb() {
		super(new ThinRhomb(), new SymmetryGroup(10, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y));
	}

}