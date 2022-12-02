/*
 * @(#) DefaultObjectConverter.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter.impl;

import com.element.converter.ConverterContext;
import com.element.converter.ObjectConverter;

import javax.swing.*;
import java.text.ParseException;


/**
 * Default object converter. It converts an object to a String using either toString()
 * or the AbstractFormatter specified in the ConverterContex's userObject.
 * <p/>
 * For example,
 * <code><pre>
 *  MaskFormatter mask = null;
 *  try {
 *      mask = new MaskFormatter("###-##-####");
 *  }
 *  catch (ParseException e) {
 *      e.printStackTrace();
 *  }
 *  ConverterContext ssnConverterContext = new ConverterContext("SSN", mask);
 * </pre></code>
 * If so, it will use the MaskFormatter's stringToValue and valueToString methods to do the conversion.
 */
public class DefaultObjectConverter extends ObjectConverter {
	public DefaultObjectConverter() {
	}

	public String toString(Object object, ConverterContext context) {
		if (context != null && context.getUserObject() instanceof JFormattedTextField.AbstractFormatter o) {
			try {
				return o.valueToString(object);
			} catch (ParseException ignored) {
			}
		}
		return object == null ? "" : object.toString();
	}

	public Object fromString(String string, ConverterContext context) {
		if (context != null && context.getUserObject() instanceof JFormattedTextField.AbstractFormatter o) {
			try {
				return o.stringToValue(string);
			} catch (ParseException ignored) {
			}
		}
		return string;
	}

}
