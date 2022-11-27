package com.element.util;

import com.element.plaf.UIDefaultsLookup;
import com.element.ui.dialog.ButtonResources;

import java.util.Locale;

public class LocaleUtil {
	/**
	 * Gets the string representing OK button.
	 *
	 * @param locale the locale
	 * @return the string.
	 * @since 3.3.8
	 */
	public static String getOKString(Locale locale) {
		String text = UIDefaultsLookup.getString("OptionPane.okButtonText", locale);
		if (text == null || text.length() == 0) {
			text = UIDefaultsLookup.getString("ColorChooser.okText");
			if (text == null || text.length() == 0) {
				text = ButtonResources.getResourceBundle(locale).getString("Button.ok");
			}
		}
		return text;
	}

	/**
	 * Gets the string representing Cancel button.
	 *
	 * @param locale the locale
	 * @return the string.
	 * @since 3.3.8
	 */
	public static String getCancelString(Locale locale) {
		String text = UIDefaultsLookup.getString("OptionPane.cancelButtonText", locale);
		if (text == null || text.length() <= 0) {
			text = UIDefaultsLookup.getString("ColorChooser.cancelText");
			if (text == null || text.length() <= 0) {
				text = ButtonResources.getResourceBundle(locale).getString("Button.cancel");
			}
		}
		return text;
	}

	/**
	 * Gets the string representing Yes button.
	 *
	 * @param locale the locale
	 * @return the string.
	 * @since 3.3.8
	 */
	public static String getYesString(Locale locale) {
		String text = UIDefaultsLookup.getString("OptionPane.yesButtonText", locale);
		if (text == null || text.length() <= 0) {
			text = ButtonResources.getResourceBundle(locale).getString("Button.yes");
		}
		return text;
	}

	/**
	 * Gets the string representing No button.
	 *
	 * @param locale the locale
	 * @return the string.
	 * @since 3.3.8
	 */
	public static String getNoString(Locale locale) {
		String text = UIDefaultsLookup.getString("OptionPane.noButtonText", locale);
		if (text == null || text.length() <= 0) {
			text = ButtonResources.getResourceBundle(locale).getString("Button.no");
		}
		return text;
	}
}
