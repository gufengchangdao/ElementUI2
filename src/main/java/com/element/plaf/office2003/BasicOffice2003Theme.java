/*
 * @(#)BasicOffice2003Theme.java 3/17/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.office2003;

import com.element.color.ColorUtil;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * <tt>BasicOffice2003Theme</tt> is a special Office2003 theme that uses a base color to calculate
 * all other colors used by JIDE components. This is done regardless what current XP theme you are using.
 */
public class BasicOffice2003Theme extends Office2003Theme {
	private Color _baseColor;

	public BasicOffice2003Theme(String themeName) {
		super(themeName);
	}

	/**
	 * Sets the base color. The Office2003 theme will use this color as base color and calculate
	 * all other colors that will be used by JIDE components.
	 *
	 * @param color                 the base color.
	 * @param derivedSelectionColor if deriving selection colors from the base color. Selection color is used in
	 *                              places like JideButton's background and DockableFrame's title pane background.
	 * @param prefix                the prefix is for the expand/collapse icon on collapsible pane. Available prefixes are
	 *                              "default", "blue", "homestead", "metallic", and "gray". You can decide which one to use depending
	 *                              on what base color is. In most cases, "default" works just fine. But if your base color has more blue,
	 *                              using "blue" will produce a better result. If green, you can use "homestead".
	 */
	public void setBaseColor(Color color, boolean derivedSelectionColor, String prefix) {
		_baseColor = color;
		float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
		Color selectionColor = Color.getHSBColor(hsb[0], (hsb[1] > 0.01) ? 0.45f : hsb[0], 0.90f);

		Object[] uiDefaults = {
				"control", ColorUtil.getDerivedColor(color, 0.9f),
				"controlLt", ColorUtil.getDerivedColor(color, 0.95f),
				"controlDk", ColorUtil.getDerivedColor(color, 0.8f),
				"controlShadow", ColorUtil.getDerivedColor(color, 0.4f),

				"TabbedPane.selectDk", ColorUtil.getDerivedColor(selectionColor, 0.40f),
				"TabbedPane.selectLt", ColorUtil.getDerivedColor(selectionColor, 0.55f),

				"OptionPane.bannerLt", ColorUtil.getDerivedColor(color, 0.5f),
				"OptionPane.bannerDk", ColorUtil.getDerivedColor(color, 0.3f),
				"OptionPane.bannerForeground", new ColorUIResource(255, 255, 255),

				"Separator.foreground", ColorUtil.getDerivedColor(color, 0.4f),
				"Separator.foregroundLt", ColorUtil.getDerivedColor(color, 1.0f),

				"Gripper.foreground", ColorUtil.getDerivedColor(color, 0.6f),
				"Gripper.foregroundLt", ColorUtil.getDerivedColor(color, 0.92f),

				"Chevron.backgroundLt", ColorUtil.getDerivedColor(color, 0.85f),
				"Chevron.backgroundDk", ColorUtil.getDerivedColor(color, 0.75f),

				"Divider.backgroundLt", ColorUtil.getDerivedColor(color, 0.9f),
				"Divider.backgroundDk", ColorUtil.getDerivedColor(color, 0.97f),

				"backgroundLt", ColorUtil.getDerivedColor(color, 0.95f),
				"backgroundDk", ColorUtil.getDerivedColor(color, 0.9f),

				"CommandBar.titleBarBackground", ColorUtil.getDerivedColor(color, 0.6f),
				"MenuItem.background", ColorUtil.getDerivedColor(color, 0.95f),

				"DockableFrameTitlePane.backgroundLt", ColorUtil.getDerivedColor(color, 0.92f),
				"DockableFrameTitlePane.backgroundDk", ColorUtil.getDerivedColor(color, 0.85f),
				"DockableFrameTitlePane.activeForeground", new ColorUIResource(0, 0, 0),
				"DockableFrameTitlePane.inactiveForeground", new ColorUIResource(0, 0, 0),
				"DockableFrame.backgroundLt", ColorUtil.getDerivedColor(color, 0.92f),
				"DockableFrame.backgroundDk", ColorUtil.getDerivedColor(color, 0.89f),

				"selection.border", ColorUtil.getDerivedColor(color, 0.5f)
		};

		putDefaults(uiDefaults);

		if (derivedSelectionColor) {
			Object[] uiDefaultsSelection = new Object[]{
					"selection.Rollover", selectionColor,
					"selection.RolloverLt", ColorUtil.getDerivedColor(selectionColor, 0.55f),
					"selection.RolloverDk", ColorUtil.getDerivedColor(selectionColor, 0.45f),

					"selection.Selected", ColorUtil.getDerivedColor(selectionColor, 0.45f),
					"selection.SelectedLt", ColorUtil.getDerivedColor(selectionColor, 0.55f),
					"selection.SelectedDk", ColorUtil.getDerivedColor(selectionColor, 0.50f),

					"selection.Pressed", ColorUtil.getDerivedColor(selectionColor, 0.4f), // focused and selected;
					"selection.PressedLt", ColorUtil.getDerivedColor(selectionColor, 0.45f),
					"selection.PressedDk", ColorUtil.getDerivedColor(selectionColor, 0.35f)
			};
			putDefaults(uiDefaultsSelection);
		}

	}

	/**
	 * Gets the base color for this theme. It is set using
	 * {@link #setBaseColor(Color, boolean, String)} method.
	 *
	 * @return the base color.
	 */
	public Color getBaseColor() {
		return _baseColor;
	}
}
