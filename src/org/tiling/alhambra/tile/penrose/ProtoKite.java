package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.Prototile;
import org.tiling.alhambra.SymmetryGroup;

public class ProtoKite extends Prototile {

	public ProtoKite() {
		super(new Kite(), new SymmetryGroup(5, true));
	}

}