/*
 * @(#) FontConverter.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter.impl;

import com.element.converter.ConverterContext;
import com.element.converter.ObjectConverter;
import com.element.converter.Resource;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Converter which converts Font to String and converts it back.
 */
public class FontConverter extends ObjectConverter {
	public String toString(Object object, ConverterContext context) {
		if (object instanceof Font font) {
			return font.getName() + ", " + getResourceString(font.getStyle()) + ", " + font.getSize();
		} else {
			return null;
		}
	}

	protected String getResourceString(int style) {
		final ResourceBundle resourceBundle = Resource.getResourceBundle(Locale.getDefault());
		return switch (style) {
			case Font.PLAIN -> resourceBundle.getString("Font.plain");
			case Font.BOLD -> resourceBundle.getString("Font.bold");
			case Font.ITALIC -> resourceBundle.getString("Font.italic");
			case Font.BOLD | Font.ITALIC -> resourceBundle.getString("Font.boldItalic");
			default -> "";
		};
	}

	protected int getStyleValue(String style) {
		final ResourceBundle resourceBundle = Resource.getResourceBundle(Locale.getDefault());
		if (resourceBundle.getString("Font.italic").equalsIgnoreCase(style)) {
			return Font.ITALIC;
		} else if (resourceBundle.getString("Font.bold").equalsIgnoreCase(style)) {
			return Font.BOLD;
		} else if (resourceBundle.getString("Font.boldItalic").equalsIgnoreCase(style)) {
			return Font.BOLD | Font.ITALIC;
		} else /*if("PLAIN".equals(style))*/ {
			return Font.PLAIN;
		}
	}

	public Object fromString(String string, ConverterContext context) {
		if (string == null || string.length() == 0) {
			return null;
		} else {
			String fontFace = null;
			int style = Font.PLAIN;
			int size = 10;

			String[] strings = string.split(",");
			if (strings.length > 0) {
				fontFace = strings[0].trim();
			}
			if (strings.length > 1) {
				style = getStyleValue(strings[1].trim());
			}
			if (strings.length > 2) {
				try {
					double s = Double.parseDouble(strings[2].trim());
					size = (int) s;
				} catch (NumberFormatException e) {
					// ignore
				}
			}

			if (fontFace != null) {
				return new Font(fontFace, style, size);
			} else {
				return null;
			}
		}
	}
}
