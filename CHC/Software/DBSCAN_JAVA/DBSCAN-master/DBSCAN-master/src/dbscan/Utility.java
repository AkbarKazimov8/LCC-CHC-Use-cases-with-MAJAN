package dbscan;

import java.util.Iterator;
import java.util.Vector;

public class Utility {

	public static Vector<Point> VisitList = new Vector<Point>();

	/// my modification
	/*static double[][] values = new double[][] { { 0, 3.063, 1.806, 1.851, 2.834, 1.940, 1.510, 2.834, 4.131, 1.445 },
			{ 3.063, 0, 5.483, 1.805, 2.624, 1.076, 2.690, 6.483, 5.781, 1.670 },
			{ 1.806, 5.483, 0, 2.405, 1.363, 0.822, 5.848, 3.322, 6.955, 5.728 },
			{ 1.851, 1.805, 2.405, 0, 2.468, 0.522, 0.559, 2.255, 2.255, 1.851 },
			{ 2.834, 2.624, 1.363, 2.468, 0, 0.972, 5.086, 3.862, 3.194, 4.729 },
			{ 1.940, 1.076, 0.822, 0.522, 0.972, 0, 0.736, 0.833, 1.634, 2.367 },
			{ 1.510, 2.690, 5.848, 0.559, 5.086, 0.736, 0, 6.973, 9.343, 0.562 },
			{ 2.834, 6.483, 3.322, 2.255, 3.862, 0.833, 6.973, 0, 6.471, 3.881 },
			{ 4.131, 5.781, 6.955, 2.255, 3.194, 1.634, 9.343, 6.471, 0, 5.816 },
			{ 1.445, 1.670, 5.728, 1.851, 4.729, 2.367, 0.562, 3.881, 5.816, 0 } };*/

	public static double[][] distanceScores = null;

	public static double getDistance(Point p, Point q) {
		/*
		 * int dx = p.getX()-q.getX();
		 * 
		 * int dy = p.getY()-q.getY();
		 * 
		 * double distance = Math.sqrt (dx * dx + dy * dy);
		 * 
		 * return distance;
		 */

		return distanceScores[p.getId()][q.getId()];
	}

	/**
	 * neighbourhood points of any point p
	 **/

	public static Vector<Point> getNeighbours(Point p) {
		Vector<Point> neigh = new Vector<Point>();
		Iterator<Point> points = dbscan.pointList.iterator();
		while (points.hasNext()) {
			Point q = points.next();
			if (getDistance(p, q) <= dbscan.e) {
				neigh.add(q);
			}
		}
		return neigh;
	}

	public static void Visited(Point d) {
		VisitList.add(d);

	}

	public static boolean isVisited(Point c) {
		if (VisitList.contains(c)) {
			return true;
		} else {
			return false;
		}
	}

	public static Vector<Point> Merge(Vector<Point> a, Vector<Point> b) {

		Iterator<Point> it5 = b.iterator();
		while (it5.hasNext()) {
			Point t = it5.next();
			if (!a.contains(t)) {
				a.add(t);
			}
		}
		return a;
	}

//  Returns PointsList to DBscan.java 

	public static Vector<Point> getList() {

		Vector<Point> newList = new Vector<Point>();
		newList.clear();
		newList.addAll(Gui.hset);
		return newList;
	}

	public static Boolean equalPoints(Point m, Point n) {
		if ((m.getX() == n.getX()) && (m.getY() == n.getY()))
			return true;
		else
			return false;
	}

}
