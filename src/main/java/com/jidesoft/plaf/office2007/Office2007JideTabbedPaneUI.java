/*
 * @(#)Office2007JideTabbedPaneUI.java 7/8/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.plaf.office2007;

import com.jidesoft.plaf.office2003.Office2003JideTabbedPaneUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

/**
 * JideTabbedPane UI implementation
 */
public class Office2007JideTabbedPaneUI extends Office2003JideTabbedPaneUI {
	public static ComponentUI createUI(JComponent c) {
		return new Office2007JideTabbedPaneUI();
	}
}