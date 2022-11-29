/*
 * @(#)VsnetLookAndFeelExtension.java 4/15/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.vsnet;

import com.element.plaf.LookAndFeelFactory;
import com.element.plaf.basic.BasicLookAndFeelExtension;

import javax.swing.*;
import java.beans.Beans;

/**
 * Initialize the uiClassID to BasicComponentUI mapping for JIDE components using Vsnet style.
 */
public class VsnetLookAndFeelExtension extends BasicLookAndFeelExtension {

	/**
	 * Initializes class defaults with menu components UIDefaults.
	 *
	 * @param table UIDefaults table
	 */
	public static void initClassDefaultsWithMenu(UIDefaults table) {
		if (!Beans.isDesignTime()) {
			table.put("PopupMenuSeparatorUI", "com.element.plaf.vsnet.VsnetPopupMenuSeparatorUI");
			table.put("MenuUI", "com.element.plaf.vsnet.VsnetMenuUI");
			table.put("MenuItemUI", "com.element.plaf.vsnet.VsnetMenuItemUI");
			table.put("CheckBoxMenuItemUI", "com.element.plaf.vsnet.VsnetCheckBoxMenuItemUI");
			table.put("RadioButtonMenuItemUI", "com.element.plaf.vsnet.VsnetRadioButtonMenuItemUI");
		}
	}

	/**
	 * Initializes class defaults.
	 *
	 * @param table UIDefaults table
	 */
	public static void initClassDefaults(UIDefaults table) {
		BasicLookAndFeelExtension.initClassDefaults(table);

		final String vsnetPackageName = "com.element.plaf.vsnet.";

		// common
		table.put("JideTabbedPaneUI", vsnetPackageName + "VsnetJideTabbedPaneUI");
		table.put("GripperUI", vsnetPackageName + "VsnetGripperUI");

		int products = LookAndFeelFactory.getProductsUsed();

		if ((products & PRODUCT_DOCK) != 0) {
			// dock
			table.put("SidePaneUI", vsnetPackageName + "VsnetSidePaneUI");
			table.put("DockableFrameUI", vsnetPackageName + "VsnetDockableFrameUI");
		}

		if ((products & PRODUCT_COMPONENTS) != 0) {
			// components
			table.put("CollapsiblePaneUI", vsnetPackageName + "VsnetCollapsiblePaneUI");
		}
	}
}
