/*
 * @(#) FileConverter.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter.impl;

import com.element.converter.ConverterContext;
import com.element.converter.ObjectConverter;

import java.io.File;

/**
 * Converter which converts File to String and converts it back.
 */
public class FileConverter extends ObjectConverter {
	public String toString(Object object, ConverterContext context) {
		if (!(object instanceof File)) {
			return null;
		} else {
			return ((File) object).getAbsolutePath();
		}
	}

	public Object fromString(String string, ConverterContext context) {
		if (string == null || string.trim().length() == 0) {
			return null;
		}
		return new File(string);
	}
}
