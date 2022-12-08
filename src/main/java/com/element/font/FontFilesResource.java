/*
 * @(#)FontFilesResource.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.font;

import java.util.Locale;
import java.util.ResourceBundle;

public class FontFilesResource {
	static final String BASENAME = FontFilesResource.class.getPackageName() + ".fontfiles";

	static final ResourceBundle RB = ResourceBundle.getBundle(BASENAME);

	public static ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundle.getBundle(BASENAME, locale);
	}
}
