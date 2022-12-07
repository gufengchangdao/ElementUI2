/*
 * @(#)WindowsJidePopupMenuUI.java 12/13/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.windows;

import com.element.plaf.basic.BasicJidePopupMenuUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.PopupMenuUI;

public class WindowsJidePopupMenuUI extends PopupMenuUI {
	public WindowsJidePopupMenuUI() {
	}

	public static ComponentUI createUI(JComponent c) {
		return new WindowsJidePopupMenuUI();
	}

	@Override
	public Popup getPopup(JPopupMenu popupMenu, int x, int y) {
		Popup popup = BasicJidePopupMenuUI.addScrollPaneIfNecessary(popupMenu, x, y);
		return popup == null ? super.getPopup(popupMenu, x, y) : popup;
	}
}
