/*
 * @(#)EclipseGripperUI.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.eclipse;

import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.basic.BasicGripperUI;
import com.element.ui.base.Gripper;
import com.element.util.UIUtil;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

/**
 *
 */
public class EclipseGripperUI extends BasicGripperUI {

	protected Color _shadowColor;
	protected Color _darkShadowColor;
	protected Color _highlight;
	protected Color _lightHighlightColor;

	public static ComponentUI createUI(JComponent c) {
		return new EclipseGripperUI();
	}

	@Override
	protected void installDefaults(Gripper s) {
		_shadowColor = UIDefaultsLookup.getColor("controlShadow");
		_darkShadowColor = UIDefaultsLookup.getColor("controlDkShadow");
		_highlight = UIDefaultsLookup.getColor("controlHighlight");
		_lightHighlightColor = UIDefaultsLookup.getColor("controlLtHighlight");
		super.installDefaults(s);
	}

	@Override
	protected void uninstallDefaults(Gripper s) {
		_shadowColor = null;
		_highlight = null;
		_lightHighlightColor = null;
		_darkShadowColor = null;
		super.uninstallDefaults(s);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		if (_gripperPainter == null) {
			getPainter().paintGripper(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), UIUtil.getOrientationOf(c), 0);
		} else {
			_gripperPainter.paint(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), UIUtil.getOrientationOf(c), 0);
		}
	}
}
