package com.element.converter.impl;

import com.element.converter.ConverterContext;
import com.element.converter.ObjectConverterManager;

import java.lang.reflect.Array;

/**
 * Converts an array to a string and converts a string to an array.
 */
public class DefaultArrayConverter extends ArrayConverter {
	public DefaultArrayConverter(String separator, Class<?> elementClass) {
		super(separator, -1, elementClass);
	}

	public String toString(Object object, ConverterContext context) {
		if (object == null) {
			return "";
		} else {
			if (object.getClass().isArray()) {
				Object[] objects;
				if (getElementClass() == Object.class) {
					objects = (Object[]) object;
				} else {
					objects = new Object[Array.getLength(object)];
				}
				for (int i = 0; i < objects.length; i++) {
					objects[i] = Array.get(object, i);
				}
				return arrayToString(objects, context);
			} else {
				return ObjectConverterManager.toString(object, getElementClass(), context);
			}
		}
	}

	public Object fromString(String string, ConverterContext context) {
		if (string == null || "".equals(string)) {
			return new Object[0];
		} else {
			Object[] objects = arrayFromString(string, context);
			if (objects == null) {
				return new Object[0];
			}
			Class<?> elementClass = getElementClass();
			if (elementClass == Object.class) {
				return objects;
			}
			for (Object object : objects) {
				if (object != null && !elementClass.isAssignableFrom(object.getClass())) {
					return new Object[0];
				}
			}
			Object array = Array.newInstance(elementClass, objects.length);
			for (int i = 0; i < objects.length; i++) {
				Object object = objects[i];
				Array.set(array, i, object);
			}
			return array;
		}
	}
}
