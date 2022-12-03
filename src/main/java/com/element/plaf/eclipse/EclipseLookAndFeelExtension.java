/*
 * @(#)EclipseLookAndFeelExtension.java 4/15/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.eclipse;

import com.element.plaf.LookAndFeelFactory;
import com.element.plaf.basic.BasicLookAndFeelExtension;

import javax.swing.*;
import java.beans.Beans;

/**
 * Utility Class for WindowsLookAndFeel to add Eclipse related LookAndFeel style
 */
public class EclipseLookAndFeelExtension extends BasicLookAndFeelExtension {

	/**
	 * Initializes class defaults with menu components UIDefaults.
	 *
	 * @param table
	 */
	public static void initClassDefaultsWithMenu(UIDefaults table) {
		if (!Beans.isDesignTime()) {
			table.put("PopupMenuSeparatorUI", "com.element.plaf.eclipse.EclipsePopupMenuSeparatorUI");
			table.put("SeparatorUI", "com.element.plaf.eclipse.EclipsePopupMenuSeparatorUI");
			table.put("MenuUI", "com.element.plaf.eclipse.EclipseMenuUI");
			table.put("MenuItemUI", "com.element.plaf.eclipse.EclipseMenuItemUI");
			table.put("CheckBoxMenuItemUI", "com.element.plaf.eclipse.EclipseCheckBoxMenuItemUI");
			table.put("RadioButtonMenuItemUI", "com.element.plaf.eclipse.EclipseRadioButtonMenuItemUI");
		}
	}

	/**
	 * Initializes class defaults.
	 *
	 * @param table
	 */
	public static void initClassDefaults(UIDefaults table) {
		BasicLookAndFeelExtension.initClassDefaults(table);

		final String eclipsePackageName = "com.element.plaf.eclipse.";

		table.put("JideTabbedPaneUI", eclipsePackageName + "EclipseJideTabbedPaneUI");
		table.put("JideSplitButtonUI", eclipsePackageName + "EclipseJideSplitButtonUI");
		table.put("GripperUI", eclipsePackageName + "EclipseGripperUI");
	}
}
