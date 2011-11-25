package org.tiling.alhambra;

import java.awt.geom.AffineTransform;
import java.io.Serializable;

/**
 * I encapsulate a series of isometries to be manipulated symbolically.
 * I work with cyclic groups, and dihedral groups.
 * Call the identity I, the base rotation T (for 'turn') and the
 * base reflection R.
 * 
 * @see SymmetryGroup
 */
public class SymbolicTransform implements Serializable {

	private static final char IDENTITY = 'I';
	private static final char ROTATION = 'T';
	private static final char REFLECTION = 'R';

	private SymmetryGroup group;

	private StringBuffer isometries;
	private StringBuffer canonicalIsometries;

	private int T;
	private int R;

	public SymbolicTransform(SymmetryGroup group) {
		this.group = group;
		this.isometries = new StringBuffer();
	}

	public SymbolicTransform(SymmetryGroup group, String series) {
		this(group);
		for (int i = 0; i < series.length(); i++) {
			char c = series.charAt(i);
			if (c == IDENTITY || c == ROTATION || c == REFLECTION) {
				isometries.append(c);
			} 
		}
	}

	public SymbolicTransform(SymbolicTransform symbolicTransform) {
		this(symbolicTransform.getSymmetryGroup(), symbolicTransform.toString());
	}

 
	public SymmetryGroup getSymmetryGroup() { 
		return group; 
	}

	public SymbolicTransform rotate(int n) {
		n %= group.getRotationalSymmetry();
		if (n < 0) {
			n += group.getRotationalSymmetry();
		}
		canonicalIsometries = null;
		for (int i = 0; i < n; i++) {
			isometries.insert(0, ROTATION);
		}
		return this;
	}

	public SymbolicTransform reflect() {
		return reflect(1);
	}

	public SymbolicTransform reflect(int n) {
		n %= 2;
		if (n < 0) {
			n += 2;
		}
		canonicalIsometries = null;
		for (int i = 0; i < n; i++) {
			isometries.insert(0, REFLECTION);
		}
		return this;
	}

	public synchronized String getCanonicalSeries() {
		if (canonicalIsometries != null) {
			return canonicalIsometries.toString();
		}

		// Use these facts:
		// T^(n) = I
		// R^2 = I
		// R*T = T^(n-1)*R

		// to turn the string into one of the form "(I|T*R?)" using regex notation

		StringBuffer isometriesCopy = new StringBuffer(isometries.toString());
		T = 0;
		R = 0;
		for (int i = 0; i < isometriesCopy.length(); i++) {
			if (isometriesCopy.charAt(i) == ROTATION) {
				T++;
			} else if (isometriesCopy.charAt(i) == REFLECTION) {
				if (i < isometriesCopy.length() - 1) {
					if (isometriesCopy.charAt(i + 1) == REFLECTION) { // R^2 = I
						i++; 
					} else if (isometriesCopy.charAt(i + 1) == ROTATION) { // R*T = T^(n-1)*R
						T += group.getRotationalSymmetry() - 1;
						isometriesCopy.setCharAt(i + 1, REFLECTION);
					}
				} else {
					R++;
					break;
				}
			}
		}
		canonicalIsometries = new StringBuffer();
		T %= group.getRotationalSymmetry();
		for (int i = 0; i < T; i++) {
			canonicalIsometries.append(ROTATION);
		}
		if (R % 2 == 1) {
			canonicalIsometries.append(REFLECTION);
		}
		if (canonicalIsometries.length() == 0) {
			canonicalIsometries.append(IDENTITY);
		}
		return canonicalIsometries.toString();
	}

	public AffineTransform getAffineTransform() {
		getCanonicalSeries(); // normalise
		AffineTransform t = group.getRotationalTransform(T);
		if (R == 1) {
			t.concatenate(group.getReflectionTransform());
		}
		return t;
	}

	public boolean equals(Object object) {
		if (!(object instanceof SymbolicTransform)) {
			return false;
		} else {
			SymbolicTransform st = (SymbolicTransform) object;
			return getCanonicalSeries().equals(st.getCanonicalSeries());
		}
	}

	public int hashCode() {
		return getCanonicalSeries().hashCode();
	}

	public String toString() {
		return isometries.toString();
	}
}