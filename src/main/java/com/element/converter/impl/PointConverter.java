/*
 * @(#) PointConverter.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter.impl;

import com.element.converter.ConverterContext;

import java.awt.*;

/**
 * Converter which converts Point to String and converts it back.
 */
public class PointConverter extends ArrayConverter {
	public PointConverter() {
		super("; ", 2, Integer.class);
	}

	public String toString(Object object, ConverterContext context) {
		if (object instanceof Point point) {
			return arrayToString(new Object[]{
					point.x, point.y
			}, context);
		} else {
			return "";
		}
	}

	public Object fromString(String string, ConverterContext context) {
		if (string == null || string.trim().length() == 0) {
			return null;
		}
		Object[] objects = arrayFromString(string, context);
		int x = 0, y = 0;
		if (objects.length >= 1 && objects[0] instanceof Integer) {
			x = (Integer) objects[0];
		}
		if (objects.length >= 2 && objects[1] instanceof Integer) {
			y = (Integer) objects[1];
		}
		return new Point(x, y);
	}
}
