/*
 * @(#)PointFormatter.java 4/26/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.spinner;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import java.awt.*;
import java.text.ParseException;

/**
 * @author Nako Ruru
 */
public class PointFormatter extends DefaultFormatter {
	private static JFormattedTextField.AbstractFormatter formatter;
	public synchronized static JFormattedTextField.AbstractFormatter getInstance() {
		if (formatter == null) {
			formatter = new PointFormatter();
		}
		return formatter;
	}

	private PointFormatter() {
		super();
	}

	@Override
	public Object stringToValue(String text) throws ParseException {
		text = text.trim();
		if (text.startsWith("(") && text.endsWith(")")) {
			text = text.substring(1, text.length() - 1);
		}
		try {
			String[] splition = text.split(",");
			return new Point(Integer.parseInt(splition[0].trim()), Integer.parseInt(splition[1].trim()));
		} catch (Exception e) {
			throw new ParseException(text, 0);
		}
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value instanceof Point point) {
			return "(" + point.x + ", " + point.y + ")";
		} else {
			return super.valueToString(value);
		}
	}
}
