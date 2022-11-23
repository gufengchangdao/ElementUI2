package com.jidesoft.demo;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 测试资源
 */
public class Resource {
	static final String BASENAME = "com.component.jidesoft.demo.demo";

	static final ResourceBundle RB = ResourceBundle.getBundle(BASENAME);

	public static ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundle.getBundle(BASENAME, locale);
	}
}
