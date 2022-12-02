/*
 * @(#)Resource.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.swing;

import java.util.Locale;
import java.util.ResourceBundle;

public class Resource {
	static final String BASENAME = Resource.class.getPackageName() +".swing";
	public static final ResourceBundle RB = ResourceBundle.getBundle(BASENAME);

	public static ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundle.getBundle(BASENAME, locale);
	}
}
