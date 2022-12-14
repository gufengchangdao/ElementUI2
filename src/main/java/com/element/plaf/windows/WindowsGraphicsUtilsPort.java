/*
 * @(#)WindowsGraphicsUtilsPort.java 12/15/2017
 *
 * Copyright 2002 - 2017 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.windows;

import com.element.util.SystemInfo;
import com.element.util.UIUtil;

import javax.swing.*;
import java.awt.*;

public class WindowsGraphicsUtilsPort {
	/**
	 * Renders a text String in Windows without the mnemonic.
	 * This is here because the WindowsUI hierarchy doesn't match the Component hierarchy. All
	 * the overriden paintText methods of the ButtonUI delegates will call this static method.
	 *
	 * @param g        Graphics context
	 * @param b        Current button to render
	 * @param textRect Bounding rectangle to render the text.
	 * @param text     String to render
	 */
	public static void paintText(Graphics g, AbstractButton b,
	                             Rectangle textRect, String text,
	                             int textShiftOffset) {
		FontMetrics fm = b.getFontMetrics(b.getFont());

		int mnemIndex = b.getDisplayedMnemonicIndex();
		// W2K Feature: Check to see if the Underscore should be rendered.
		if (SystemInfo.isMnemonicHidden()) {
			mnemIndex = -1;
		}

		paintClassicText(b, g, textRect.x + textShiftOffset,
				textRect.y + fm.getAscent() + textShiftOffset,
				text, mnemIndex);
	}

	static void paintClassicText(AbstractButton b, Graphics g, int x, int y,
	                             String text, int mnemIndex) {
		ButtonModel model = b.getModel();

		/* Draw the Text */
		Color color = b.getForeground();
		if (model.isEnabled()) {
			/* paint the text normally */
			if (!(b instanceof JMenuItem && model.isArmed())
					&& !(b instanceof JMenu && (model.isSelected() || model.isRollover()))) {
				/* We shall not set foreground color for selected menu or
				 * armed menuitem. Foreground must be set in appropriate
				 * Windows* class because these colors passes from
				 * BasicMenuItemUI as protected fields and we can't
				 * reach them from this class */
				g.setColor(b.getForeground());
			}
			UIUtil.drawStringUnderlineCharAt(b, g, text, mnemIndex, x, y);
		} else {        /* paint the text disabled ***/
			color = getDisabledTextColor(b);
			if (color == null) {
				color = UIManager.getColor("Button.shadow");
			}
			Color shadow = UIManager.getColor("Button.disabledShadow");
			if (model.isArmed()) {
				color = UIManager.getColor("Button.disabledForeground");
			} else {
				if (shadow == null) {
					shadow = b.getBackground().darker();
				}
				g.setColor(shadow);
				UIUtil.drawStringUnderlineCharAt(b, g, text, mnemIndex, x + 1, y + 1);
			}
			if (color == null) {
				color = b.getBackground().brighter();
			}
			g.setColor(color);
			UIUtil.drawStringUnderlineCharAt(b, g, text, mnemIndex, x, y);
		}
	}

	private static Color getDisabledTextColor(AbstractButton b) {
		if (b instanceof JCheckBox) {
			return UIManager.getColor("CheckBox.disabledText");
		} else if (b instanceof JRadioButton) {
			return UIManager.getColor("RadioButton.disabledText");
		} else if (b instanceof JToggleButton) {
			return UIManager.getColor("ToggleButton.disabledText");
		} else if (b instanceof JButton) {
			return UIManager.getColor("Button.disabledText");
		}
		return null;
	}

}
