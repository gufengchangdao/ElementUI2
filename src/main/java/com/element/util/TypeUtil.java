package com.element.util;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Utils methods for data type.
 */
public class TypeUtil {
	// indexes referring to columns in the PRIMITIVE_ARRAY_TYPES table.
	private static final int WRAPPER_TYPE_INDEX = 0;
	private static final int PRIMITIVE_TYPE_INDEX = 1;

	private static final Object[][] PRIMITIVE_ARRAY_TYPES = {
			{Boolean.class, boolean.class},
			{Character.class, char.class},
			{Byte.class, byte.class},
			{Short.class, short.class},
			{Integer.class, int.class},
			{Long.class, long.class},
			{Float.class, float.class},
			{Double.class, double.class}
	};

	/** 是否是基本数据类型类对象 */
	public static boolean isPrimitive(Class<?> primitive) {
		for (Object[] primitiveArrayType : PRIMITIVE_ARRAY_TYPES) {
			if (primitiveArrayType[PRIMITIVE_TYPE_INDEX] == primitive) {
				return true;
			}
		}
		return false;
	}

	/** 是否是基本类型包装类对象 */
	public static boolean isPrimitiveWrapper(Class<?> wrapperType) {
		for (Object[] primitiveArrayType : PRIMITIVE_ARRAY_TYPES) {
			if (primitiveArrayType[WRAPPER_TYPE_INDEX] == wrapperType) {
				return true;
			}
		}
		return false;
	}

	/** 基本类型类对象转其包装类对象，不是基本类型类对象的话返回参数 */
	public static Class<?> convertPrimitiveToWrapperType(Class<?> primitive) {
		for (Object[] primitiveArrayType : PRIMITIVE_ARRAY_TYPES) {
			if (primitiveArrayType[PRIMITIVE_TYPE_INDEX] == primitive) {
				return (Class<?>) primitiveArrayType[WRAPPER_TYPE_INDEX];
			}
		}
		return primitive;
	}

	/** 包装类对象转其基本类型类对象，不是包装类对象的话返回参数 */
	public static Class<?> convertWrapperToPrimitiveType(Class<?> wrapperType) {
		for (Object[] primitiveArrayType : PRIMITIVE_ARRAY_TYPES) {
			if (primitiveArrayType[WRAPPER_TYPE_INDEX] == wrapperType) {
				return (Class<?>) primitiveArrayType[PRIMITIVE_TYPE_INDEX];
			}
		}
		return wrapperType;
	}

	/**
	 * Checks if the type is a numeric type.
	 *
	 * @param type the data type.
	 * @return true if it is numeric type including all subclasses of Number, double, int, float, short and long.
	 */
	public static boolean isNumericType(Class<?> type) {
		return type != null && (Number.class.isAssignableFrom(type)
				|| type == double.class
				|| type == int.class
				|| type == float.class
				|| type == short.class
				|| type == long.class);
	}

	/**
	 * Checks if the type is an integer type.
	 *
	 * @param type the data type.
	 * @return true if it is numeric type including all subclasses of Integer or int.
	 */
	public static boolean isIntegerType(Class<?> type) {
		return type != null && (Integer.class.isAssignableFrom(type) || type == int.class);
	}

	/**
	 * Checks if the type is a long type.
	 *
	 * @param type the data type.
	 * @return true if it is numeric type including all subclasses of Long or long.
	 */
	public static boolean isLongType(Class<?> type) {
		return type != null && (Long.class.isAssignableFrom(type) || type == long.class);
	}

	/**
	 * Checks if the type is a BigDecimal type.
	 *
	 * @param type the data type.
	 * @return true if it is numeric type including all subclasses of BigDecimal.
	 */
	public static boolean isBigDecimalType(Class<?> type) {
		return type != null && BigDecimal.class.isAssignableFrom(type);
	}

	/**
	 * Checks if the type is a string type.
	 *
	 * @param type the data type.
	 * @return true if it is numeric type including all subclasses of String.
	 */
	public static boolean isStringType(Class<?> type) {
		return type != null && (String.class.isAssignableFrom(type));
	}

	/**
	 * Checks if the type is a boolean type.
	 *
	 * @param type the data type.
	 * @return true if it is a boolean
	 */
	public static boolean isBooleanType(Class<?> type) {
		return type != null && (Boolean.class.isAssignableFrom(type) || type == boolean.class);
	}

	/**
	 * Checks if the type is a temporal type such as Date, Calendar, long or double that can be used to represent date
	 * or time.
	 *
	 * @param type the data type.
	 * @return true if it is temporal type including all subclasses.
	 */
	public static boolean isTemporalType(Class<?> type) {
		return type != null && (Date.class.isAssignableFrom(type) || Calendar.class.isAssignableFrom(type)
				|| type == double.class
				|| type == long.class);
	}

	/**
	 * Checks if the type is a type that can be visualized such as color, image or icon.
	 *
	 * @param type the data type.
	 * @return true if it is visual type including all subclasses.
	 */
	public static boolean isVisualType(Class<?> type) {
		return type != null && (Color.class.isAssignableFrom(type) || Icon.class.isAssignableFrom(type) || Image.class.isAssignableFrom(type));
	}

}
