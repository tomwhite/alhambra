package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoFatRhomb extends Prototile {

	public ProtoFatRhomb() {
		super(new FatRhomb(), new SymmetryGroup(5, true));
	}

}