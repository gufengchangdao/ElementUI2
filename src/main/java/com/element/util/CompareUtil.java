package com.element.util;

import java.lang.reflect.Array;
import java.util.List;

public class CompareUtil {
	/**
	 * Checks if the two objects equal. If both are null, they are equal. If o1 and o2 both are Comparable, we will use
	 * compareTo method to see if it equals 0. At last, we will use <code>o1.equals(o2)</code> to compare. If none of
	 * the above conditions match, we return false.
	 *
	 * @param o1 the first object to compare
	 * @param o2 the second object to compare
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2) {
		return equals(o1, o2, false);
	}

	/**
	 * Checks if the two objects equal. If both are the same instance, they are equal. If both are null, they are equal.
	 * If o1 and o2 both are Comparable, we will use compareTo method to see if it equals 0. If considerArrayOrList is
	 * true and o1 and o2 are both array, we will compare each element in the array. At last, we will use
	 * <code>o1.equals(o2)</code> to compare. If none of the above conditions match, we return false.
	 *
	 * @param o1                  the first object to compare
	 * @param o2                  the second object to compare
	 * @param considerArrayOrList If true, and if o1 and o2 are both array, we will compare each element in the array
	 *                            instead of just compare the two array objects.
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2, boolean considerArrayOrList) {

		return equals(o1, o2, considerArrayOrList, true);
	}

	/**
	 * Checks if the two objects equal. If both are the same instance, they are equal. If both are null, they are equal.
	 * If o1 and o2 both are Comparable, we will use compareTo method to see if it equals 0. If considerArrayOrList is
	 * true and o1 and o2 are both array, we will compare each element in the array. At last, we will use
	 * <code>o1.equals(o2)</code> to compare. If none of the above conditions match, we return false.
	 *
	 * @param o1                  the first object to compare
	 * @param o2                  the second object to compare
	 * @param considerArrayOrList If true, and if o1 and o2 are both array, we will compare each element in the array
	 *                            instead of just compare the two array objects.
	 * @param caseSensitive       if the o1 and o2 are CharSequence, we will use this parameter to do a case sensitive
	 *                            or insensitive comparison
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2, boolean considerArrayOrList, boolean caseSensitive) {
		if (o1 == o2) {
			return true;
		} else if (o1 != null && o2 == null) {
			return false;
		} else if (o1 == null) {
			return false;
		} else if (o1 instanceof CharSequence && o2 instanceof CharSequence) {
			return equals((CharSequence) o1, (CharSequence) o2, caseSensitive);
		} else if (o1 instanceof Comparable && o2 instanceof Comparable && o1.getClass().isAssignableFrom(o2.getClass())) {
			return ((Comparable) o1).compareTo(o2) == 0;
		} else if (o1 instanceof Comparable && o2 instanceof Comparable && o2.getClass().isAssignableFrom(o1.getClass())) {
			return ((Comparable) o2).compareTo(o1) == 0;
		} else if (considerArrayOrList && o1 instanceof java.util.List && o2 instanceof java.util.List) {
			int length1 = ((java.util.List<?>) o1).size();
			int length2 = ((java.util.List<?>) o2).size();
			if (length1 != length2) {
				return false;
			}
			for (int i = 0; i < length1; i++) {
				if (!equals(((java.util.List<?>) o1).get(i), ((List<?>) o2).get(i), true)) {
					return false;
				}
			}
			return true;
		} else if (considerArrayOrList && o1.getClass().isArray() && o2.getClass().isArray()) {
			int length1 = Array.getLength(o1);
			int length2 = Array.getLength(o2);
			if (length1 != length2) {
				return false;
			}
			for (int i = 0; i < length1; i++) {
				if (!equals(Array.get(o1, i), Array.get(o2, i), true)) {
					return false;
				}
			}
			return true;
		} else {
			return o1.equals(o2);
		}
	}

	public static boolean equals(CharSequence s1, CharSequence s2, boolean caseSensitive) {
		if (s1 == s2) return true;
		if (s1 == null || s2 == null) return false;

		// Algorithm from String.regionMatches()

		if (s1.length() != s2.length()) return false;
		int to = 0;
		int po = 0;
		int len = s1.length();

		while (len-- > 0) {
			char c1 = s1.charAt(to++);
			char c2 = s2.charAt(po++);
			if (c1 == c2) {
				continue;
			}
			if (!caseSensitive && charsEqualIgnoreCase(c1, c2)) continue;
			return false;
		}

		return true;
	}

	public static boolean charsEqualIgnoreCase(char a, char b) {
		return a == b || Character.toLowerCase(a) == Character.toLowerCase(b);
	}
}
