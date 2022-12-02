/*
 * @(#)MultilineStringConverter.java 10/24/2008
 *
 * Copyright 2002 - 2008 JIDE Software Inc. All rights reserved.
 *
 */
package com.element.converter.impl;

import com.element.converter.ConverterContext;
import com.element.converter.ObjectConverter;

/**
 * Converter which converts a String with new lines to String and convert the new lines to "\n" so that it can be
 * displayed in the UI.
 */
public class MultilineStringConverter extends ObjectConverter {
	public static final ConverterContext CONTEXT = new ConverterContext("MultilineString");

	public String toString(Object object, ConverterContext context) {
		if (object instanceof String) {
			return ((String) object).replaceAll("\\\\n", "\\\\\\\\n").replaceAll("\\\\r", "\\\\\\\\r").replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r");
		} else if (object == null) {
			return "";
		} else {
			return "" + object;
		}
	}

	public Object fromString(String string, ConverterContext context) {
		if (string != null) {
			return string.replaceAll("\\\\\\\\n", "\\\\n").replaceAll("\\\\\\\\r", "\\\\r");
		} else {
			return null;
		}
	}
}