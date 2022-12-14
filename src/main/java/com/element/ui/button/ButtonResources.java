package com.element.ui.button;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 定义常用按钮的资源类
 */
public class ButtonResources {
	static final String BASENAME = ButtonResources.class.getPackageName() + ".buttons";

	static final ResourceBundle RB = ResourceBundle.getBundle(BASENAME);

	public static ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundle.getBundle(BASENAME, locale);
	}
}
