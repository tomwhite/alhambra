package org.tiling.alhambra;

import org.tiling.alhambra.geom.*;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * I represent the symmetries and matching of a curved edge E of a tiling.
 * I am immutable, so I do not need to be Cloneable.
 * (All implementations should be immutable.)
 * <p>
 * An <i>S-curve</i> is a centrally-symmetric arc whose centre of symmetry is the midpoint of edge E.
 * <p>
 * A <i>C-curve</i> is an arc for which the perpendicular bisector of the edge is a line of reflective symmetry.
 * <p>
 * A <i>J-curve</i> is an arc which has no non-trivial symmetry.
 * @see LabelledEdge
 */
public abstract class Curve implements Serializable {

	public static final Curve C_CURVE = new CCurve();
	public static final Curve S_CURVE = new SCurve();
	public static final Curve J_CURVE = new JCurve();

	public abstract boolean matches(LabelledEdge edge1, LabelledEdge edge2);
	public abstract int getInitialDirection();

	private static class CCurve extends Curve {
		public boolean matches(LabelledEdge edge1, LabelledEdge edge2) {
			return 
				(edge1.getMark() ==  edge2.getMark() && edge1.getV1().equals(edge2.getV1()) && edge1.getV2().equals(edge2.getV2())) ||
				(edge1.getMark() == -edge2.getMark() && edge1.getV2().equals(edge2.getV1()) && edge1.getV1().equals(edge2.getV2()));
		}
		public int getInitialDirection() {
			return LabelledEdge.UNDIRECTED;
		}
		public String toString() {
			return "C-curve";
		}
	}

	private static class SCurve extends Curve {
		public boolean matches(LabelledEdge edge1, LabelledEdge edge2) {
			return 
				(edge1.getMark() == edge2.getMark() && edge1.getV1().equals(edge2.getV1()) && edge1.getV2().equals(edge2.getV2())) ||
				(edge1.getMark() == edge2.getMark() && edge1.getV2().equals(edge2.getV1()) && edge1.getV1().equals(edge2.getV2()));
		}
		public int getInitialDirection() {
			return LabelledEdge.UNDIRECTED;
		}
		public boolean reverseMark() {
			return true;
		}
		public String toString() {
			return "S-curve";
		}
	}

	private static class JCurve extends Curve {
		public boolean matches(LabelledEdge edge1, LabelledEdge edge2) {
			return 
				(edge1.getMark() ==  edge2.getMark() && edge1.getV1().equals(edge2.getV1()) && edge1.getV2().equals(edge2.getV2()) && edge1.getDirection() ==  edge2.getDirection()) ||
				(edge1.getMark() == -edge2.getMark() && edge1.getV2().equals(edge2.getV1()) && edge1.getV1().equals(edge2.getV2()) && edge1.getDirection() == -edge2.getDirection());
		}
		public int getInitialDirection() {
			return LabelledEdge.FORWARD_DIRECTION;
		}
		public String toString() {
			return "J-curve";
		}
	}

	public boolean reverseMark() {
		return false;
	}}