package org.tiling.alhambra.geom;

import java.util.List;

/**
 * I am a collection of tools for performing common operations in 2D computational
 * geometry. I am largely based on Leen Ammeraal's code in 'Computer Graphics for
 * Java Programmers'.
 */
public class Tools2D {

	// All methods are static - do not allow construction
	private Tools2D() {
	}

	/**
	 * Rounding error tolerance.
	 */
	public static double eps = 1e-5;

	// Point methods

	/**
	 * @return true if points P and Q are equal (modulo rounding errors)
	 */
	public static boolean coincident(Point2D P, Point2D Q) {
		return (P.x == Q.x && P.y == Q.y || distance2(P, Q) < eps);
	}

	// Vector methods

	/**
	 * @return true if vectors V and W are equal (modulo rounding errors)
	 */
	public static boolean equals(Vector2D V, Vector2D W) {
		if (V == W) {
			return true;
		}
		return (Math.abs(V.x - W.x) < eps && Math.abs(V.y - W.y) < eps);
	}

	/**
	 * @return true if vectors V and W are equal in magnitude and in reversed orientation (modulo rounding errors)
	 */
	public static boolean reverseEquals(Vector2D V, Vector2D W) {
		if (V == W) {
			return false;
		}
		return (Math.abs(V.x + W.x) < eps && Math.abs(V.y + W.y) < eps);
	}

	// Line methods

	/**
	 * @return the square distance between points P and Q
	 */
	public static double distance2(Point2D P, Point2D Q) {
		double dx = P.x - Q.x,
		      dy = P.y - Q.y;
		return dx * dx + dy * dy;
	}

	/**
	 * @return the square distance between P and the (infinite) line through A and B
	 */
	public static double distance2PointToLine(Point2D P, Point2D A, Point2D B) {
		double area2 = area2(A, B, P); // NB area times 2
		double distance2 = distance2(A, B);
		return area2 * area2 / distance2;
	}

	/**
	 * @return the square distance between P and the line segment AB
	 */
	public static double distance2PointToLineSegment(Point2D P, Point2D A, Point2D B) {
		int projection = projectionOnLineSegment(P, A, B);
		if (projection == -1) {
			return distance2(P, A);
		} else if (projection == 1) {
			return distance2(P, B);
		}
		// projection == 0
		return distance2PointToLine(P, A, B);
	}

	/**
	 * Let P' be the point P projected onto the infinite line through A and B.
	 * @return 0 if P' lies on the open segment AB,
	 * -1 if P' coincides with A or lies off AB, but nearest to A, or
	 * 1 if P' coincides with B or lies off AB, but nearest to B.
	 */
	public static int projectionOnLineSegment(Point2D P, Point2D A, Point2D B) {
		double ux = B.x - A.x;
		double uy = B.y - A.y;
		double len2 = ux * ux + uy * uy;
		double innerProduct = ux * (P.x - A.x) + uy * (P.y - A.y); // AB.AP
		if (innerProduct > eps) {
			return innerProduct < len2 - eps ? 0 : 1;
		} else {	
			return -1;
		}
	}

	/**
	 * @return true if P lies on the closed line segment AB (modulo rounding errors) 
	 */
	public static boolean onLineSegment(Point2D P, Point2D A, Point2D B) {
		return (
			Math.abs(A.x - B.x) > eps && (A.x - P.x > -eps && P.x - B.x > -eps || B.x - P.x > -eps && P.x - A.x > -eps)
			||
			Math.abs(A.y - B.y) > eps && (A.y - P.y > -eps && P.y - B.y > -eps || B.y - P.y > -eps && P.y - A.y > -eps)
			)
			&&
			Math.abs(area2(P, A, B)) < eps;
	}

	/**
	 * The line segments (p1, q1) and (p2, q2) are taken to be closed intervals so we include endpoints.
	 * @return true iff line segment (p1, q1) intersects line segment (p2, q2)
	 */
	public static boolean intersects(Point2D p1, Point2D q1, Point2D p2, Point2D q2) {
		int p1q1p2 = orientation(p1, q1, p2),
		    p1q1q2 = orientation(p1, q1, q2),
		    p2q2p1 = orientation(p2, q2, p1),
		    p2q2q1 = orientation(p2, q2, q1);

		// All collinear.
		if (p1q1p2 == 0 && p1q1q2 == 0 && p2q2p1 == 0 && p2q2q1 == 0) {
			if (	(// The x-projections of both segments intersect
					(p2.x - p1.x > -eps && q1.x - p2.x > -eps) ||
					(p2.x - q1.x > -eps && p1.x - p2.x > -eps) ||
					(q2.x - p1.x > -eps && q1.x - q2.x > -eps) ||
					(q2.x - q1.x > -eps && p1.x - q2.x > -eps) 
				)
				&&
				(// The y-projections of both segments intersect
					(p2.y - p1.y > -eps && q1.y - p2.y > -eps) ||
					(p2.y - q1.y > -eps && p1.y - p2.y > -eps) ||
					(q2.y - p1.y > -eps && q1.y - q2.y > -eps) ||
					(q2.y - q1.y > -eps && p1.y - q2.y > -eps)
				)
			  )
			{
				return true;
			}
		}

		if (p1q1p2 != p1q1q2 && p2q2p1 != p2q2q1) {
			return true;
		}

		return false;
	}

	/** 
	 * The line segments (p1, q1) and (p2, q2) are taken to be open intervals so we exclude endpoints. 
	 * @return true iff line segment (p1, q1) intersects line segment (p2, q2) and no triple is collinear
	 */ 
	public static boolean nonCollinearIntersects(Point2D p1, Point2D q1, Point2D p2, Point2D q2) { 
		int p1q1p2 = orientation(p1, q1, p2), 
		    p1q1q2 = orientation(p1, q1, q2), 
		    p2q2p1 = orientation(p2, q2, p1), 
		    p2q2q1 = orientation(p2, q2, q1); 
 
		// If any triple is collinear then there is no intersection 
		if (p1q1p2 == 0 || p1q1q2 == 0 || p2q2p1 == 0 || p2q2q1 == 0) { 
			return false; 
		} 
 
		return (p1q1p2 == -p1q1q2 && p2q2p1 == -p2q2q1); 
	} 

	/**
	 * @return point R lying on PQ such that PR * (1 - lambda) = lambda * RQ
	 * note that 0 <= lambda <= 1.
	 */
	public static Point2D pointOnLine(Point2D P, Point2D Q, double lambda) {
		Vector2D v = new Vector2D(P, Q);
		return new Point2D(P.x + v.x * lambda, P.y + v.y * lambda);
	}


	// Triangle methods

	/**
	 * @return true if triangles T and U are equal (modulo rounding errors in all 3 ccw combinations)
	 */
	public static boolean equals(Triangle2D T, Triangle2D U) {
		if (T == U) {
			return true;
		}
		// if vertices equal to within a small fraction, in all 3 (ccw) combos
		return (coincident(T.A, U.A) && coincident(T.B, U.B) && coincident(T.C, U.C) 
			|| coincident(T.A, U.B) && coincident(T.B, U.C) && coincident(T.C, U.A)
			|| coincident(T.A, U.C) && coincident(T.B, U.A) && coincident(T.C, U.B));
	}

	/**
	 * @return the area of triangle ABC multiplied by 2, or by -2 if ABC are clockwise
	 */
	public static double area2(Point2D A, Point2D B, Point2D C) {
		return (A.x - C.x) * (B.y - C.y) - (A.y - C.y) * (B.x - C.x);
	} 

	/**
	 * @return the centroid of triangle ABC
	 */
	public static Point2D centroid(Point2D A, Point2D B, Point2D C) {
		return new Point2D((A.x + B.x + C.x) / 3.0, (A.y + B.y + C.y) / 3.0);
	}

	/**
	 * @return the centroid of triangle T
	 */
	public static Point2D centroid(Triangle2D T) {
		return centroid(T.A, T.B, T.C);
	}

	/**
	 * @return -1, 0, 1 according to points A, B, and C being clockwise, collinear, counter-clockwise,
	 * respectively. This is called the <i>orientation</i> of ABC.
	 */
	public static int orientation(Point2D A, Point2D B, Point2D C) {
		double area2 = area2(A, B, C);
		if (area2 > eps) {
			return 1;
		} else if (area2 < -eps) {
			return -1;
		} else {
			return 0;
		}
	}


	/**
	 * Points ABC must be ccw.
	 * @return true only if P is inside ABC (modulo rounding errors)
	 */
	public static boolean insideTriangle(Point2D A, Point2D B, Point2D C, Point2D P) {
		return area2(A, B, P) > -eps && area2(B, C, P) > -eps && area2(C, A, P) > -eps;
	}

	/**
	 * Points ABC must be ccw.
	 * @return true only if P is strictly inside ABC (modulo rounding errors)
	 */
	public static boolean strictlyInsideTriangle(Point2D A, Point2D B, Point2D C, Point2D P) {
		return area2(A, B, P) > eps && area2(B, C, P) > eps && area2(C, A, P) > eps;
	}
	// there is an alternative way of doing this...

	// Overloaded versions.

	/**
	 * Triangle T must be ccw.
	 * @return true only if P is inside T (modulo rounding errors)
	 */
	public static boolean insideTriangle(Triangle2D T, Point2D P) {
		return insideTriangle(T.A, T.B, T.C, P);
	}
	/**
	 * Triangle T must be ccw.
	 * @return true only if P is strictly inside T (modulo rounding errors)
	 */
	public static boolean strictlyInsideTriangle(Triangle2D T, Point2D P) {
		return strictlyInsideTriangle(T.A, T.B, T.C, P);
	}

	/**
	 * Triangles T and U must both be ccw.
	 * @return true iff triangle T overlaps triangle U
	 */
	public static boolean overlaps(Triangle2D T, Triangle2D U) {

		if (equals(T, U)) {
			return true;
		}

		if (strictlyInsideTriangle(T, U.A) || strictlyInsideTriangle(T, U.B) || strictlyInsideTriangle(T, U.C) ||
			strictlyInsideTriangle(U, T.A) || strictlyInsideTriangle(U, T.B) || strictlyInsideTriangle(U, T.C)) {
			return true;
		}

		if (nonCollinearIntersects(T.A, T.B, U.A, U.B) || nonCollinearIntersects(T.A, T.B, U.B, U.C)  || nonCollinearIntersects(T.A, T.B, U.C, U.A) ||
			nonCollinearIntersects(T.B, T.C, U.A, U.B) || nonCollinearIntersects(T.B, T.C, U.B, U.C)  || nonCollinearIntersects(T.B, T.C, U.C, U.A) ||
			nonCollinearIntersects(T.C, T.A, U.A, U.B) || nonCollinearIntersects(T.C, T.A, U.B, U.C)  || nonCollinearIntersects(T.C, T.A, U.C, U.A)) {

			return true;
		}

		// Covers sticky case of two sides of T being contained in two sides of U:
		//		U |\		
		//		  | \
		//		T |\ \
		//		  | \ \
		//		  |__\_\
		if (strictlyInsideTriangle(T, centroid(U)) || strictlyInsideTriangle(U, centroid(T))) {
			return true;
		}

		return false;
	}


	// Polygon methods

	/**
	 * @return area of polygon multiplied by 2
	 */
	public static double area2(Point2D[] polygon) {

		int n = polygon.length;
		int j = n - 1;
		double a = 0;

		for (int i = 0; i < n; i++) {
			// i == j + 1, (or j = n - 1 and i = 0)
			a += polygon[j].x * polygon[i].y - polygon[j].y * polygon[i].x;
			j = i;
		}

		return a;

	}

	/**
	 * @param polygon a list of ccw points defining the polygon to be triangulated
	 * @param triangles the collection of triangles built from triangulating polygon
	 */
	public static void triangulate(Point2D[] polygon, List triangles) {
		int n = polygon.length,
		    j = n - 1,
		    iA = 0, iB, iC;
		int[] next = new int[n];

		for (int i = 0; i < n; i++) {
			next[j] = i;
			j = i;
		}

		for (int k = 0; k < n - 2; k++) {
			// Find a suitable triangle, consisting of two edges and
			// an internal diagonal
			Point2D A, B, C;
			boolean triaFound = false;
			int count = 0;
			while (!triaFound && ++count < n) {
				iB = next[iA]; iC = next[iB];
				A = polygon[iA]; B = polygon[iB]; C = polygon[iC];
				if (orientation(A, B, C) != -1) {
					// Edges AB and BC; diagonal AC.
					// Test to see if no other polygon vertex
					// lies within triangle ABC
					j = next[iC];
					while(j != iA && !insideTriangle(A, B, C, polygon[j])) {
						j = next[j];
					}
					if (j == iA) {
						// Triangle2D ABC contains no other vertex
						triangles.add(new Triangle2D(A, B, C));
						next[iA] = iC;
						triaFound = true;
					}
				}
				iA = next[iA];
			}
			if (count == n) {
				System.err.println("Not a simple polygon / vertices not ccw.");
				Thread.dumpStack();
				System.exit(1);
			}
		}
	}
}