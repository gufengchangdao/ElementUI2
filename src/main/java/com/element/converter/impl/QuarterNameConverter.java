/*
 * @(#)QuarterNameConverter.java 5/8/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.element.converter.impl;

import com.element.converter.ConverterContext;
import com.element.converter.ObjectConverter;
import com.element.converter.Resource;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Converter which converts quarter to String and converts it back.
 */
public class QuarterNameConverter extends ObjectConverter {

	/**
	 * Default ConverterContext for MonthConverter.
	 */
	public static ConverterContext CONTEXT = new ConverterContext("QuarterName");
	private static String _quarterNamePattern;

	/**
	 * Creates a new CalendarConverter.
	 */
	public QuarterNameConverter() {
	}

	public String toString(Object object, ConverterContext context) {
		if (!(object instanceof Number)) {
			return "";
		} else {
			int qty = ((Number) object).intValue();
			if (qty >= 0 && qty < 4) {
				return MessageFormat.format(getQuarterNamePattern(), (qty + 1));
			} else {
				return "";
			}
		}
	}

	public Object fromString(String string, ConverterContext context) {
		String quarterNamePattern = getQuarterNamePattern();
		try {
			Object[] values = new MessageFormat(quarterNamePattern).parse(string);
			if (values.length > 0) {
				return Integer.parseInt("" + values[0]) - 1;
			}
		} catch (ParseException e) {
			// ignore
		}
		return 0;
	}

	/**
	 * Gets the quarter name pattern when converting from an int to a String. For example, if the int is 0, it will
	 * converted to "Qtr 1" if the quarter name pattern is "Qtr {0}".
	 *
	 * @return the prefix.
	 */
	public String getQuarterNamePattern() {
		if (_quarterNamePattern == null) {
			return getResourceString("Quarter.quarter");
		}
		return _quarterNamePattern;
	}

	/**
	 * Sets the quarter name pattern. For example, if the int is 0, it will converted to "Qtr 1" if the pattern is "Qtr
	 * {0}".
	 *
	 * @param quarterName
	 */
	public void setQuarterNamePattern(String quarterName) {
		_quarterNamePattern = quarterName;
	}

	protected String getResourceString(String key) {
		final ResourceBundle resourceBundle = Resource.getResourceBundle(Locale.getDefault());
		return resourceBundle.getString(key);
	}

}
