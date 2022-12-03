/*
 * @(#)BasicLookAndFeelExtension.java 4/15/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.basic;

import com.element.plaf.ProductNames;

import javax.swing.*;

/**
 * Initialize the uiClassID to BasicComponentUI mapping for JIDE components. The JComponent classes define their own
 * uiClassID constants (see AbstractComponent.getUIClassID).  This table must map those constants to a BasicComponentUI
 * class of the appropriate type.
 */
public class BasicLookAndFeelExtension implements ProductNames {

	/**
	 * Initializes class defaults.
	 *
	 * @param table UIDefaults table
	 */
	public static void initClassDefaults(UIDefaults table) {
		final String basicPackageName = "com.element.plaf.basic.";

		// common
		table.put("JidePopupMenuUI", basicPackageName + "BasicJidePopupMenuUI");
		table.put("HeaderBoxUI", basicPackageName + "BasicHeaderBoxUI");
		table.put("FolderChooserUI", basicPackageName + "BasicFolderChooserUI");
		table.put("StyledLabelUI", basicPackageName + "BasicStyledLabelUI");
		table.put("GripperUI", basicPackageName + "BasicGripperUI");
		table.put("JidePopupUI", basicPackageName + "BasicJidePopupUI");
		table.put("JideTabbedPaneUI", basicPackageName + "BasicJideTabbedPaneUI");
		table.put("JideLabelUI", basicPackageName + "BasicJideLabelUI");
		table.put("JideButtonUI", basicPackageName + "BasicJideButtonUI");
		table.put("JideSplitButtonUI", basicPackageName + "BasicJideSplitButtonUI");
		table.put("JideComboBoxUI", basicPackageName + "BasicJideComboBoxUI");
		table.put("MeterProgressBarUI", basicPackageName + "MeterProgressBarUI");
	}
}
