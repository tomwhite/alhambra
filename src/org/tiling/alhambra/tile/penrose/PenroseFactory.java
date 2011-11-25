package org.tiling.alhambra.tile.penrose;

import org.tiling.alhambra.AbstractPrototileFactory;
import org.tiling.alhambra.Prototile;

public class PenroseFactory extends AbstractPrototileFactory {

	private static PenroseFactory instance = new PenroseFactory();

	private PenroseFactory() {
	}

	public static PenroseFactory getInstance() {
		return instance;
	}

	public Prototile createPrototile(String name) {

		if (name.equalsIgnoreCase("Kite"))
			return new ProtoKite();
		if (name.equalsIgnoreCase("Dart"))
			return new ProtoDart();
		if (name.equalsIgnoreCase("ThinRhomb"))
			return new ProtoThinRhomb();
		if (name.equalsIgnoreCase("FatRhomb"))
			return new ProtoFatRhomb();
		if (name.equalsIgnoreCase("LB"))
			return new ProtoLB();
		return null;

	}

	private static final String[] mnemonics = new String[] {
		"Kite",
		"Dart",
		"ThinRhomb",
		"FatRhomb",
		"LB",
	};

	public String[] getPrototileNames() {
		return mnemonics;
	}

}