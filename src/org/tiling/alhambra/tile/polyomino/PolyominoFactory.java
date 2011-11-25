package org.tiling.alhambra.tile.polyomino;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import org.tiling.alhambra.*;
import org.tiling.alhambra.geom.*;

public class PolyominoFactory extends AbstractPrototileFactory {

	private static PolyominoFactory instance = new PolyominoFactory();

	private PolyominoFactory() {
	}

	public static PolyominoFactory getInstance() {
		return instance;
	}

	public Prototile createPrototile(String name) {
		return createPolyomino(name);
	}

	private static final String[] mnemonics = new String[] {
		"monomino",
		"domino",
		"tromino_straight", "tromino_right",
		"tetromino_straight", "tetromino_square", "tetromino_T", "tetromino_skew", "tetromino_L",
		"pentomino_T", "pentomino_U", "pentomino_V", "pentomino_W", "pentomino_X", "pentomino_Y", "pentomino_Z", 
			"pentomino_F", "pentomino_I", "pentomino_L", "pentomino_P", "pentomino_N",
		"Ammann1", "Ammann2", "Ammann3",
	};

	public String[] getPrototileNames() {
		return mnemonics;
	}

	private static final String[] familyNames = new String[] {
		"monomino",
		"domino",
		"tromino",
		"tetromino",
		"pentomino",
		"Ammann",
	};

	public String[] getPrototileFamilyNames() {
		return familyNames;
	}

	public String[] getPrototileNames(String family) {
		List l = new ArrayList();
		for (int i = 0; i < mnemonics.length; i++) {
			if (mnemonics[i].startsWith(family)) {
				l.add(mnemonics[i]);
			}
		}
		return (String[]) l.toArray(new String[0]);
	}


	private Prototile createPolyomino(String name, int[][] squares, int rotationalSymmetry, AffineTransform reflection) {
		return new ProtoPolyomino(PolyominoConstructor.createPolyomino(name, squares),
					rotationalSymmetry, reflection);
	}

	public Prototile createPolyomino(String mnemonic) {

		if (mnemonic.equalsIgnoreCase("monomino"))
			return createPolyomino(
					mnemonic,
					new int[][]{
							{0, 0},
					},
					1, null
			);

		if (mnemonic.equalsIgnoreCase("domino"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 0}, {1, 0},
					},
					1, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);

		if (mnemonic.equalsIgnoreCase("tromino_straight"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 0}, {1, 0}, {2, 0},
					},
					1, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);

		if (mnemonic.equalsIgnoreCase("tromino_right"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 1}, {1, 1},
							{0, 0},
					},
					4, null
			);

		if (mnemonic.equalsIgnoreCase("tetromino_straight"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 0}, {1, 0}, {2, 0}, {3, 0},
					},
					1, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);

		if (mnemonic.equalsIgnoreCase("tetromino_square"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 1}, {1, 1},
							{0, 0}, {1, 0},
					},
					1, null
			);

		if (mnemonic.equalsIgnoreCase("tetromino_T"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 1}, {1, 1}, {2, 1},
							        {1, 0},
					},
					4, null
			);

		if (mnemonic.equalsIgnoreCase("tetromino_skew"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 1}, {1, 1},
							        {1, 0}, {2, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_ZERO
			);

		if (mnemonic.equalsIgnoreCase("tetromino_L"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 1}, {1, 1}, {2, 1},
							{0, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);

		if (mnemonic.equalsIgnoreCase("pentomino_T"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 2}, {1, 2}, {2, 2},
							        {1, 1}, 
							        {1, 0},
					},
					4, null
			);
		if (mnemonic.equalsIgnoreCase("pentomino_U"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 1},         {2, 1}, 
							{0, 0}, {1, 0}, {2, 0},
					},
					4, null
			);
		if (mnemonic.equalsIgnoreCase("pentomino_V"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 2},
							{0, 1},
							{0, 0}, {1, 0}, {2, 0},
					},
					4, null
			);
		if (mnemonic.equalsIgnoreCase("pentomino_W"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 2},
							{0, 1}, {1, 1},
							        {1, 0}, {2, 0},
					},
					4, null
			);
		if (mnemonic.equalsIgnoreCase("pentomino_X"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							        {1, 2},
							{0, 1}, {1, 1}, {2, 1},
							        {1, 0},
					},
					1, null
			);
		if (mnemonic.equalsIgnoreCase("pentomino_Y"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							                {2, 1},
							{0, 0}, {1, 0}, {2, 0}, {3, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		if (mnemonic.equalsIgnoreCase("pentomino_Z"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 2}, {1, 2},
							        {1, 1},
							        {1, 0}, {2, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		if (mnemonic.equalsIgnoreCase("pentomino_F"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							        {1, 2}, {2, 2},
							{0, 1}, {1, 1},
							        {1, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		if (mnemonic.equalsIgnoreCase("pentomino_I"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 4},
							{0, 3},
							{0, 2},
							{0, 1},
							{0, 0},
					},
					1, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		if (mnemonic.equalsIgnoreCase("pentomino_L"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 3},
							{0, 2},
							{0, 1},
							{0, 0}, {1, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		if (mnemonic.equalsIgnoreCase("pentomino_P"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 2}, {1, 2},
							{0, 1}, {1, 1},
							{0, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		if (mnemonic.equalsIgnoreCase("pentomino_N"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 1}, {1, 1},
							        {1, 0}, {2, 0}, {3, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		if (mnemonic.equalsIgnoreCase("Ammann1"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							{0, 3}, {1, 3}, {2, 3}, {3, 3},
							        {1, 2}, {2, 2},
							{0, 1}, {1, 1}, {2, 1}, {3, 1},
							{0, 0},                 {3, 0},
					},
					4, null
			);
		if (mnemonic.equalsIgnoreCase("Ammann2"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
							        {1, 7}, {2, 7}, {3, 7}, {4, 7},
					      		          {2, 6},
							{0, 5}, {1, 5}, {2, 5}, {3, 5},
							{0, 4}, {1, 4}, {2, 4},
							{0, 3}, {1, 3}, {2, 3},         {4, 3},
					      		          {2, 2}, {3, 2}, {4, 2},
							                {2, 1},         {4, 1},
							                                {4, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		if (mnemonic.equalsIgnoreCase("Ammann3"))
			return createPolyomino(
					mnemonic, 
					new int[][]{
						        {1, 7}, {2, 7}, {3, 7}, {4, 7},
						                {2, 6}, {3, 6}, {4, 6},         {6, 6},
						{0, 5}, {1, 5}, {2, 5}, {3, 5}, {4, 5}, {5, 5}, {6, 5},
						{0, 4}, {1, 4}, {2, 4}, {3, 4},                 {6, 4},
						{0, 3}, {1, 3}, {2, 3}, {3, 3}, {4, 3},
						                {2, 2}, {3, 2}, {4, 2},
						        {1, 1}, {2, 1}, {3, 1},
						                        {3, 0}, {4, 0}, {5, 0},
					},
					4, SymmetryGroup.REFLECTION_IN_X_EQUALS_Y
			);
		return null;
	}

}