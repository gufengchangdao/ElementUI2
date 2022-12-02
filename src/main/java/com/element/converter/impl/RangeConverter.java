/*
 * @(#)RangeConverter.java 5/22/2014
 *
 * Copyright 2002 - 2014 JIDE Software Inc. All rights reserved.
 */
package com.element.converter.impl;

import com.element.converter.ConverterContext;
import com.element.converter.ObjectConverter;
import com.element.converter.ObjectConverterManager;
import com.element.converter.Resource;
import com.element.range.Range;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * 由任何转换器扩展的抽象类，这些转换器可以转换为类似数组的格式，例如 1、2、3。示例是 Point。 Point(100, 200) 可以转换为“100, 200”或
 * 从“100, 200”转换 您可以选择分隔符是什么；分隔符是上面 Point 示例中的“,”。
 */
public class RangeConverter extends ObjectConverter {
	private final Class<?> _elementClass;

	public static final ConverterContext CONTEXT_RANGE = new ConverterContext("Range");
	public static final ConverterContext CONTEXT_MULTIPLE = new ConverterContext("Multiple");

	/**
	 * Creates an ArrayConverter.
	 *
	 * @param elementClass class of the array element. Assume all elements have the same class type. If not, use the
	 *                     constructor which takes Class<?>[] as parameter.
	 */
	public RangeConverter(Class<?> elementClass) {
		_elementClass = elementClass;
	}

	/**
	 * Converts from array to string by concating them with separators.
	 *
	 * @param objects an array of objects
	 * @param context converter context
	 * @return string all objects concatenated with separators
	 */
	public String arrayToString(Object[] objects, ConverterContext context) {
		if (objects.length > 1) {
			if (CONTEXT_RANGE.equals(context)) {
				String s = Resource.getResourceBundle(Locale.getDefault()).getString("Range.range");
				return MessageFormat.format(s, toString(0, objects[0], context), toString(1, objects[1], context));
			} else {
				return Resource.getResourceBundle(Locale.getDefault()).getString("Range.multiple");
			}
		} else if (objects.length == 1) {
			return toString(0, objects[0], context);
		}
		return "";
	}

	protected String toString(int i, Object o, ConverterContext context) {
		return ObjectConverterManager.toString(o, _elementClass, context);
	}

	/**
	 * Converts from string to an array of objects, using separator to separate the string.
	 *
	 * @param string  string to be converted
	 * @param context converter context
	 * @return the array
	 */
	public Object[] arrayFromString(String string, ConverterContext context) {
		return null;
	}

	protected Object fromString(int i, String s, ConverterContext context) {
		return ObjectConverterManager.fromString(s, _elementClass, context);
	}

	public String toString(Object object, ConverterContext context) {
		if (object instanceof Range<?> range) {
			if (range.size() == 0) {
				return arrayToString(new Object[]{range.lower()}, context);
			}
			return arrayToString(new Object[]{range.lower(), range.upper()}, context);
		} else {
			return "";
		}
	}

	public Object fromString(String string, ConverterContext context) {
		if (string == null || string.trim().length() == 0) {
			return null;
		}
		Object[] objects = arrayFromString(string, context);
		if (objects == null) return null;
		else if (objects.length == 1) {
			return null;
		} else if (objects.length >= 2) {
			return null;
		}
		return null;
	}

	public boolean supportFromString(String string, ConverterContext context) {
		return false;
	}
}
