package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoLB extends Prototile {

	public ProtoLB() {
		super(new LB(), new SymmetryGroup(20, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y));
	}

}