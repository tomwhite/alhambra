package org.tiling.alhambra.tile.goodmanstrauss;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoTrilobite extends Prototile {

	public ProtoTrilobite() {
		super(new Trilobite(), new SymmetryGroup(4, false));
	}

}