package org.tiling.alhambra.tile.goodmanstrauss;

import org.tiling.alhambra.AbstractPrototileFactory;
import org.tiling.alhambra.Prototile;

public class GoodmanStraussFactory extends AbstractPrototileFactory {

	private static GoodmanStraussFactory instance = new GoodmanStraussFactory();

	private GoodmanStraussFactory() {
	}

	public static GoodmanStraussFactory getInstance() {
		return instance;
	}

	public Prototile createPrototile(String name) {

		if (name.equalsIgnoreCase("Trilobite"))
			return new ProtoTrilobite();
		if (name.equalsIgnoreCase("Cross"))
			return new ProtoCross();
		return null;

	}

	private static final String[] mnemonics = new String[] {
		"Trilobite",
		"Cross",
	};

	public String[] getPrototileNames() {
		return mnemonics;
	}

}