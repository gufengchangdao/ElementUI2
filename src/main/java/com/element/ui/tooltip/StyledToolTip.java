/*
 * @(#)StyledToolTip.java 1/12/2012
 *
 * Copyright 2002 - 2012 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.tooltip;

import com.element.ui.label.StyledLabel;
import com.element.ui.label.StyledLabelBuilder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * 可以接收 StyledLabel 注释的工具提示。
 * <p>
 * 使用方式例如：
 * <pre>
 * JButton b = new JButton("按钮") {
 *   public JToolTip createToolTip() {
 *     return new StyledToolTip();
 *   }
 * };
 * </pre>
 */
public class StyledToolTip extends JToolTip {
	private final StyledLabel label = new StyledLabel();

	public StyledToolTip() {
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.CENTER);

		setLayout(new BorderLayout());
		add(label);
	}

	@Override
	public void setBorder(Border border) {
		if (border == null) {
			super.setBorder(null);
		} else {
			super.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		}
	}

	@Override
	public Dimension getPreferredSize() {
		if (getTipText() != null) {
			Insets insets = getInsets();
			Dimension size = label.getPreferredSize();
			size.width += insets.left + insets.right;
			size.height += insets.top + insets.bottom;
			return size;
		}
		return super.getPreferredSize();
	}

	@Override
	public void setTipText(String tipText) {
		super.setTipText(tipText != null ? "" : null);

		StyledLabelBuilder.setStyledText(label, tipText != null ? tipText : "");
		revalidate();
	}
}