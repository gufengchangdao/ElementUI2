package com.element.converter;

import java.util.Locale;
import java.util.ResourceBundle;

public class Resource {
	static final String BASENAME = Resource.class.getPackageName()+".converter";

	static final ResourceBundle RB = ResourceBundle.getBundle(BASENAME);

	public static ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundle.getBundle(BASENAME, locale);
	}
}
