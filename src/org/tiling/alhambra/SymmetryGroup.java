package org.tiling.alhambra;

import java.awt.geom.AffineTransform;
import java.io.Serializable;

import org.tiling.alhambra.geom.*;

/**
 * I describe rotational and reflectional symmetries.
 * Typically I am used to describe all the transformations that
 * can be performed on a particular Tile for a given tiling.
 * (Note that this is not the same as describing a particular Tile's
 * own symmetries!)
 * <p>
 * Immutable.
 * @see Prototile
 */
public class SymmetryGroup implements Serializable {

	static final long serialVersionUID = 1680367059931665784L;

	/**
	 * The default reflection transform
	 */
	public final static AffineTransform REFLECTION_IN_X_EQUALS_ZERO =
		new AffineTransform(new double[] {-1, 0, 0, 1, 0, 0});

	/**
	 * A common reflection transform
	 */
	public final static AffineTransform REFLECTION_IN_X_EQUALS_Y =
		new AffineTransform(new double[] {0, 1, 1, 0, 0, 0});

	public final static SymmetryGroup E = new SymmetryGroup("e");
	public final static SymmetryGroup C2 = new SymmetryGroup("c2");
	public final static SymmetryGroup C3 = new SymmetryGroup("c3");
	public final static SymmetryGroup C4 = new SymmetryGroup("c4");
	public final static SymmetryGroup D1 = new SymmetryGroup("d1");
	public final static SymmetryGroup D2 = new SymmetryGroup("d2");
	public final static SymmetryGroup D3 = new SymmetryGroup("d3");
	public final static SymmetryGroup D4 = new SymmetryGroup("d4");

	/**
	 * @serial the rotational symmetry.
	 */
	private int rotationalSymmetry;

	/**
	 * @serial the reflectional symmetry.
	 */
	private boolean reflectionalSymmetry;

	/**
	 * @serial the rotational symmetry transforms (including the identity).
	 */
	private AffineTransform[] rotations;

	/**
	 * @serial the reflectional symmetry transform.
	 */
	private AffineTransform reflection;

	/**
	 * Constructor for a symmetry group, following Gruenbaum and Shephard for notation.
	 * @param groupName a string describing the symmetry group, e.g. "d2".
	 * @exception java.lang.IllegalArgumentException if the group name is not recognised
	 */
	public SymmetryGroup(String groupName) {
		this(getRotationalSymmetry(groupName), getReflectionalSymmetry(groupName));
	}

	private static int getRotationalSymmetry(String groupName) {
		if (groupName.equals("e")) {
			return 1;
		} else if (groupName.startsWith("c") || groupName.startsWith("d")) {
			try {
				int order = Integer.parseInt(groupName.substring(1));
				if (order < 1) {
					throw new IllegalArgumentException("Order must be non-negative " + order);
				}
				return order;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Unrecognised group: " + groupName);
			}
		} 
else {
			throw new IllegalArgumentException("Unrecognised group: " + groupName);
		}
	}

	private static AffineTransform getReflectionalSymmetry(String groupName) {
		return groupName.startsWith("d") ? REFLECTION_IN_X_EQUALS_ZERO : null;
	}

	public SymmetryGroup(int rotationalSymmetry, boolean reflectionalSymmetry) {
		this(rotationalSymmetry, reflectionalSymmetry ? REFLECTION_IN_X_EQUALS_ZERO : null);
	}

	public SymmetryGroup(int rotationalSymmetry, AffineTransform reflection) {
		this.rotationalSymmetry = rotationalSymmetry;

		// Mathematical machinery for effecting the rotations
		final double[] angles = new double[rotationalSymmetry];
		final double[] sines = new double[rotationalSymmetry];
		final double[] cosines = new double[rotationalSymmetry];
		
		for (int i = 0; i < rotationalSymmetry; i++) {
			angles[i] = 2 * i * Math.PI / rotationalSymmetry;
			sines[i] = Math.sin(angles[i]);
			cosines[i] = Math.cos(angles[i]);
		}

		rotations = new AffineTransform[rotationalSymmetry];
		rotations[0] = new AffineTransform();
		for (int i = 1; i < rotationalSymmetry; i++) {
			rotations[i] = new AffineTransform(new double[] {cosines[i], -sines[i], sines[i], cosines[i], 0, 0});
		}
		
		// Reflections
		reflectionalSymmetry = (reflection != null);
		if (reflectionalSymmetry)
			this.reflection = reflection;
	}

	public int getRotationalSymmetry() {
		return rotationalSymmetry;
	}

	public boolean getReflectionalSymmetry() {
		return reflectionalSymmetry;
	}

	public AffineTransform[] getRotationalTransforms() {
		AffineTransform[] newRotations = new AffineTransform[rotations.length];
		for (int i = 0; i < rotations.length; i++) {
			newRotations[i] = new AffineTransform(rotations[i]);
		}
		return newRotations;
	}

	public AffineTransform getRotationalTransform(int index) {
		return new AffineTransform(rotations[index]);
	}

	public AffineTransform getReflectionTransform() {
		if (reflection == null) {
			return null;
		} else {
			return new AffineTransform(reflection);
		}
	}

	public SymbolicTransform[] getSymbolicTransforms() {
		SymbolicTransform[] transforms = new SymbolicTransform[reflectionalSymmetry ? (2 * rotations.length) : rotations.length];
		for (int i = 0; i < rotations.length; i++) {
			transforms[i] = new SymbolicTransform(this).rotate(i);
			if (reflectionalSymmetry) {
				transforms[i + rotations.length] = new SymbolicTransform(this).reflect().rotate(i);
			}
		}
		return transforms; 
	}

	public boolean equals(Object obj) {

		if (obj != null && (obj.getClass().equals(this.getClass()))) {
			SymmetryGroup sg = (SymmetryGroup) obj;
			return sg.getRotationalSymmetry() == rotationalSymmetry &&
					sg.getReflectionalSymmetry() == reflectionalSymmetry;
		}
		return false;

	}

	public int hashCode() {
		int bits = rotationalSymmetry;
		bits = bits * 31 ^ new Boolean(reflectionalSymmetry).hashCode();
		return bits;
 	} 

	public String toString() {
		if (rotationalSymmetry == 1 && !reflectionalSymmetry) {
			return "e";
		} else {
			return (reflectionalSymmetry ? "d" : "c") + rotationalSymmetry;
		}
	}



}