/*
 * @(#)${NAME}
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.office2003;

import com.element.color.ColorUtil;
import com.element.plaf.ExtWindowsDesktopProperty;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.WindowsDesktopProperty;
import com.element.plaf.XPUtils;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * The default 2003 theme. This theme doesn't define any color of its own but derive all colors from
 * the underlying L&F. If your application used a customized L&F other than Windows L&F, this is
 * the preferred theme as it will consider the colors defined underlying L&F to make it looks consistent
 * than hard-coded themes such as BLUE, HOMESTEAD of METALLIC.
 */
public class DefaultOffice2003Theme extends Office2003Theme {
	private static final double DAKRER_FACTOR = 0.76;
	private static final double FACTOR = 0.85;
	private static final double LIGHTER_FACTOR = 0.95;
	private static final double EVEN_LIGHTER_FACTOR = 0.97;

	private static boolean _useStandardSelectionColor;

	static {
		try {
			_useStandardSelectionColor = XPUtils.isXPStyleOn();
		} catch (UnsupportedOperationException e) {
			_useStandardSelectionColor = false;
		}
	}

	static Color getLighterColor(Color color) {
		if (Color.BLACK.equals(color)) {
			return color;
		} else if (Color.WHITE.equals(color)) {
			return color;
		}
		return ColorUtil.getDerivedColor(color, 0.93f);
	}

	public DefaultOffice2003Theme() {
		super(XPUtils.DEFAULT);

		if (!isUseStandardSelectionColor()) {
			putDerivedSelectionColor();
		}

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		WindowsDesktopProperty control = new WindowsDesktopProperty("win.3d.backgroundColor", UIDefaultsLookup.get("control"), toolkit);
		Object controlDk2 = new ExtWindowsDesktopProperty(new String[]{"win.3d.backgroundColor"},
				new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> darker((Color) obj[0], LIGHTER_FACTOR));
		Object controlDk = new ExtWindowsDesktopProperty(new String[]{"win.3d.backgroundColor"},
				new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> darker((Color) obj[0], DAKRER_FACTOR));
		Object controlLt = new ExtWindowsDesktopProperty(new String[]{"win.3d.highlightColor"},
				new Object[]{UIDefaultsLookup.get("controlLtHighlight")}, toolkit, obj -> brighter((Color) obj[0], FACTOR));
		WindowsDesktopProperty controlShadow = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
		Object controlDkShadow = new ExtWindowsDesktopProperty(new String[]{"win.3d.shadowColor"},
				new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> darker((Color) obj[0], FACTOR));
		WindowsDesktopProperty controlText = new WindowsDesktopProperty("win.button.textColor", UIDefaultsLookup.get("controlText"), toolkit);
		WindowsDesktopProperty controlLtHighlight = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
		WindowsDesktopProperty commandBarCaption = new WindowsDesktopProperty("", UIDefaultsLookup.get("inactiveCaption"), toolkit);
		Object menuItemBackground = new ExtWindowsDesktopProperty(//Actual color 249, 248, 247
				new String[]{"win.3d.backgroundColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> new ColorUIResource(getLighterColor((Color) obj[0])));

		Object[] uiDefaults = {
				"control", control,
				"controlLt", controlLt,
				"controlDk", control,
				"controlShadow", controlShadow,

				"OptionPane.bannerLt", new ColorUIResource(0, 52, 206),
				"OptionPane.bannerDk", new ColorUIResource(45, 96, 249),
				"OptionPane.bannerForeground", new ColorUIResource(255, 255, 255),

				"Separator.foreground", controlShadow,
				"Separator.foregroundLt", controlLt,

				"Gripper.foreground", controlShadow,
				"Gripper.foregroundLt", controlLt,

				"Chevron.backgroundLt", controlDk,
				"Chevron.backgroundDk", controlShadow,

				"Divider.backgroundLt", controlShadow,
				"Divider.backgroundDk", controlDkShadow,

				"backgroundLt", controlLt,
				"backgroundDk", control,

				"selection.border", controlShadow,

				"MenuItem.background", menuItemBackground,

				"DockableFrameTitlePane.backgroundLt", controlLt,
				"DockableFrameTitlePane.backgroundDk", controlDk2,
				"DockableFrameTitlePane.activeForeground", controlText,
				"DockableFrameTitlePane.inactiveForeground", controlText,
				"DockableFrame.backgroundLt", controlLt,
				"DockableFrame.backgroundDk", controlLt,

				"CommandBar.titleBarBackground", commandBarCaption,
		};
		putDefaults(uiDefaults);
	}

	private void putDerivedSelectionColor() {
		Object selectionRollover = (ActiveValue) table -> UIDefaultsLookup.getColor("JideButton.focusedBackground");
		Object selectionRolloverLt = (ActiveValue) table -> brighter(UIDefaultsLookup.getColor("JideButton.focusedBackground"), LIGHTER_FACTOR);
		Object selectionRolloverDk = (ActiveValue) table -> darker(UIDefaultsLookup.getColor("JideButton.focusedBackground"), LIGHTER_FACTOR);

		Object selectionSelected = (ActiveValue) table -> UIDefaultsLookup.getColor("JideButton.selectedBackground");
		Object selectionSelectedLt = (ActiveValue) table -> brighter(UIDefaultsLookup.getColor("JideButton.selectedBackground"), LIGHTER_FACTOR);
		Object selectionSelectedDk = (ActiveValue) table -> darker(UIDefaultsLookup.getColor("JideButton.selectedBackground"), LIGHTER_FACTOR);
		Object selectionSelectedAndFocused = (ActiveValue) table -> UIDefaultsLookup.getColor("JideButton.selectedAndFocusedBackground");
		Object selectionSelectedAndFocusedLt = (ActiveValue) table -> brighter(UIDefaultsLookup.getColor("JideButton.selectedAndFocusedBackground"), LIGHTER_FACTOR);
		Object selectionSelectedAndFocusedDk = (ActiveValue) table -> darker(UIDefaultsLookup.getColor("JideButton.selectedAndFocusedBackground"), LIGHTER_FACTOR);

		Object[] uiDefaultsSelection = new Object[]{
				"selection.Rollover", selectionRollover,
				"selection.RolloverLt", selectionRolloverLt,
				"selection.RolloverDk", selectionRolloverDk,

				"selection.Selected", selectionSelected,
				"selection.SelectedLt", selectionSelectedLt,
				"selection.SelectedDk", selectionSelectedDk,

				"selection.Pressed", selectionSelectedAndFocused, // focused and selected;
				"selection.PressedLt", selectionSelectedAndFocusedLt,
				"selection.PressedDk", selectionSelectedAndFocusedDk
		};
		putDefaults(uiDefaultsSelection);
	}

	private void removeDerivedSelectionColor() {
		Object[] uiDefaultsSelection = new Object[]{
				"selection.Rollover",
				"selection.RolloverLt",
				"selection.RolloverDk",

				"selection.Selected",
				"selection.SelectedLt",
				"selection.SelectedDk",

				"selection.Pressed",
				"selection.PressedLt",
				"selection.PressedDk"
		};
		for (Object o : uiDefaultsSelection) {
			remove(o);
		}
	}

	/**
	 * Creates a new <code>Color</code> that is a brighter version of this
	 * <code>Color</code>.
	 * <p/>
	 * This method applies an arbitrary scale factor to each of the three RGB
	 * components of this <code>Color</code> to create a brighter version
	 * of this <code>Color</code>. Although <code>brighter</code> and
	 * <code>darker</code> are inverse operations, the results of a
	 * series of invocations of these two methods might be inconsistent
	 * because of rounding errors.
	 *
	 * @return a new <code>Color</code> object that is
	 * a brighter version of this <code>Color</code>.
	 * @see Color#darker
	 * @since JDK1.0
	 */
	private Color brighter(Color c, double factor) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();

		/* From 2D group:
		 * 1. black.brighter() should return grey
		 * 2. applying brighter to blue will always return blue, brighter
		 * 3. non pure color (non zero rgb) will eventually return white
		 */
		int i = (int) (1.0 / (1.0 - factor));
		if (r == 0 && g == 0 && b == 0) {
			return new Color(i, i, i);
		}
		if (r > 0 && r < i) r = i;
		if (g > 0 && g < i) g = i;
		if (b > 0 && b < i) b = i;

		return new Color(Math.min((int) (r / factor), 255),
				Math.min((int) (g / factor), 255),
				Math.min((int) (b / factor), 255));
	}

	/**
	 * Creates a new <code>Color</code> that is a darker version of this
	 * <code>Color</code>.
	 * <p/>
	 * This method applies an arbitrary scale factor to each of the three RGB
	 * components of this <code>Color</code> to create a darker version of
	 * this <code>Color</code>.  Although <code>brighter</code> and
	 * <code>darker</code> are inverse operations, the results of a series
	 * of invocations of these two methods might be inconsistent because
	 * of rounding errors.
	 *
	 * @return a new <code>Color</code> object that is
	 * a darker version of this <code>Color</code>.
	 * @see Color#brighter
	 * @since JDK1.0
	 */
	private Color darker(Color c, double factor) {
		return new Color(Math.max((int) (c.getRed() * factor), 0),
				Math.max((int) (c.getGreen() * factor), 0),
				Math.max((int) (c.getBlue() * factor), 0));
	}

	/**
	 * Checks if the standard selection color will be used.
	 *
	 * @return true if the standard selection color will be used.
	 */
	public static boolean isUseStandardSelectionColor() {
		return _useStandardSelectionColor;
	}

	/**
	 * Sets if the standard selection color will be used. The standard selection color (orange) is the color that used in Office 2003
	 * to indicate a button is selected or focused. By default it will be used for all Office 2003 theme including DefayltOffice2003Theme.
	 * If user chooses not to use it, the theme will derive the color from the default selection color of the LookAndFeel.
	 * <p/>
	 * If you want to call this method, make sure you call it before LookAndFeelFactory.installJideExtension(...) is called.
	 *
	 * @param useStandardSelectionColor
	 */
	public static void setUseStandardSelectionColor(boolean useStandardSelectionColor) {
		_useStandardSelectionColor = useStandardSelectionColor;
	}
}
