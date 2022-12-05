/*
 * @(#)MenuCheckIcon.java 4/29/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 *
 */

package com.element.ui.icons;


import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serializable;

/**
 * 检查图标的图标包装类。只有图标绘制在按钮上并且按钮是选中状态才绘制。
 * 这个类唯一做的就是在它真正绘制它的包装图标之前检查选择。我们提供此类的原因是类似的机制存在于 Swing 中，我们无法覆
 * 盖它。因此，如果我们只是用普通图标更新 UI，图标可能会意外显示。使用这个图标包装器类，您可以获得与 Swing 默认图标完全相同的行为。
 */
public class MenuCheckIcon implements Icon, UIResource, Serializable {
	private final Icon _icon;

	public MenuCheckIcon(Icon icon) {
		if (icon == null) {
			throw new IllegalArgumentException("The icon should not be null.");
		}
		_icon = icon;
	}

	public int getIconHeight() {
		return _icon.getIconHeight();
	}

	public int getIconWidth() {
		return _icon.getIconWidth();
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (c instanceof AbstractButton b) {
			if (b.isSelected()) _icon.paintIcon(c, g, x, y);
		}
	}

	private Icon getIcon() {
		return _icon;
	}
}