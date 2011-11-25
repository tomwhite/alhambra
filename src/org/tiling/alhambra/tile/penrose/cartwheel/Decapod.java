package org.tiling.alhambra.tile.penrose.cartwheel;

import java.util.*;

import org.tiling.alhambra.*;
import org.tiling.alhambra.tile.penrose.*;

public class Decapod {

	public static final Decapod ASTERIX = new Decapod("1001100100");
	public static final Decapod BATMAN = new Decapod("0010011011");
	public static final Decapod CIRCULAR_SAW = new Decapod("0000000000");
	public static final Decapod STARFISH = new Decapod("1010101010");
	public static final Decapod ARTICHOKE = new Decapod("1111100000");
	public static final Decapod BEETLE = new Decapod("1000011110");

	private String orientations;

	public Decapod(String orientations) {
		this.orientations = orientations;
	}

	public Decapod(int bits) {
		orientations = "";
		for (int i = 0; i < 10; i++) {
			if ((bits >> i & 1) == 1) {
				orientations = "1" + orientations;
			} else {
				orientations = "0" + orientations;
			}
		}
	}

	public Decapod[] getEquivalenceClass() {
		HashSet equivalents = new HashSet();
		String reflectedOrientations = reflect().toString();
		for (int i = 0; i < 10; i++) {
			equivalents.add(new Decapod(orientations.substring(i, 10) + orientations.substring(0, i)));
			equivalents.add(new Decapod(reflectedOrientations.substring(i, 10) + reflectedOrientations.substring(0, i)));
		}
		return (Decapod[]) equivalents.toArray(new Decapod[equivalents.size()]);
	}

	public static Decapod[] getEquivalenceClassRepresentatives() {
		ArrayList representatives = new ArrayList();
		HashMap classes = new HashMap();
		for (int i = 0; i < 1024; i++) {
			Decapod decapod = new Decapod(i);
			if (!classes.containsKey(decapod)) {
				Decapod[] equivalents = decapod.getEquivalenceClass();
				for (int j = 0; j < equivalents.length; j++) {
					classes.put(equivalents[j], decapod);
				}
				representatives.add(decapod);
			}
		}
		return (Decapod[]) representatives.toArray(new Decapod[representatives.size()]);
	}

	public Decapod reflect() {
		String invertedString = orientations.replace('0', 'o').replace('1', '0').replace('o', '1');
		return new Decapod((new StringBuffer(invertedString)).reverse().toString());
	}

	public int getOrder() {
		int order = 0;
		for (int i = 0; i < 10; i++) {
			if (orientations.charAt(i) == '1') {
				order++;
			}
		}
		return order;
	}

	public String toPartitionString() {
		if (orientations.charAt(9) != '1') {
			return "()";
		}
		String partition = "(";
		int gap = 0;
		for (int i = 8; i >= 0; i--) {
			if (orientations.charAt(i) == '1') {
				partition += gap + " ";
				gap = 0;
			} else {
				gap++;
			}
		}
		return partition + gap + ")";
	}

	public int toInt() {
		return Integer.parseInt(orientations, 2);
	}

	public Tile getTile() {
		Prototile lb = new ProtoLB();
		Patch patch = new SimplePatch();
		for (int i = 0; i < 10; i++) {
			Tile tile;
			SymbolicTransform st = new SymbolicTransform(lb.getSymmetryGroup());
			if (orientations.charAt(i) == '0') {
				st.rotate(2 * i);
			} else {
				st.reflect(1);
				st.rotate(2 * i + 3);
			}
			tile = lb.getTransformedTile(st);
			patch.add(tile);
		}
		return patch;
	}

	public boolean equals(Object object) {
		if (object instanceof Decapod) {
			Decapod decapod = (Decapod) object;
			return orientations.equals(decapod.orientations);
		}
		return false;
	}

	public int hashCode() {
		return toInt();
	}

	public String toString() {
		return orientations;
	}

}