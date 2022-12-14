/*
 * @(#)VsnetRadioButtonMenuItemUI.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.eclipse;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * RadioButtonMenuItem UI implementation
 */
public class EclipseRadioButtonMenuItemUI extends EclipseMenuItemUI {
	public static ComponentUI createUI(JComponent b) {
		return new EclipseRadioButtonMenuItemUI();
	}

	@Override
	protected String getPropertyPrefix() {
		return "RadioButtonMenuItem";
	}

	public void processMouseEvent(JMenuItem item, MouseEvent e, MenuElement[] path, MenuSelectionManager manager) {
		Point p = e.getPoint();
		if (p.x >= 0 && p.x < item.getWidth() &&
				p.y >= 0 && p.y < item.getHeight()) {
			if (e.getID() == MouseEvent.MOUSE_RELEASED) {
				manager.clearSelectedPath();
				item.doClick(0);
				item.setArmed(false);
			} else
				manager.setSelectedPath(path);
		} else if (item.getModel().isArmed()) {
			MenuElement[] newPath = new MenuElement[path.length - 1];
			int i, c;
			for (i = 0, c = path.length - 1; i < c; i++)
				newPath[i] = path[i];
			manager.setSelectedPath(newPath);
		}
	}
}








