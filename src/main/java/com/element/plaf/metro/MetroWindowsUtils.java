/*
 * @(#)MetroWindowsUtils.java 5/11/2012
 *
 * Copyright 2002 - 2012 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.metro;

import com.element.color.ColorUtil;
import com.element.plaf.ExtWindowsDesktopProperty;
import com.element.plaf.LookAndFeelFactory;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.WindowsDesktopProperty;
import com.element.plaf.basic.BasicPainter;
import com.element.plaf.basic.Painter;
import com.element.plaf.basic.ThemePainter;
import com.element.plaf.office2003.Office2003WindowsUtils;
import com.element.plaf.office2007.Office2007Painter;
import com.element.plaf.vsnet.VsnetWindowsUtils;
import com.element.ui.font.FontUtil;
import com.element.ui.icons.IconsFactory;
import com.element.ui.icons.MenuCheckIcon;
import com.element.ui.tabs.JideTabbedPane;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;

/**
 * WindowsLookAndFeel with Metro extension
 */
public class MetroWindowsUtils extends VsnetWindowsUtils {

	/**
	 * Initializes class defaults.
	 *
	 * @param table
	 * @param withMenu
	 */
	public static void initClassDefaults(UIDefaults table, boolean withMenu) {
		Office2003WindowsUtils.initClassDefaults(table, withMenu);
		table.put("JideTabbedPaneUI", "com.element.plaf.office2007.Office2007JideTabbedPaneUI");
	}

	/**
	 * Initializes class defaults.
	 *
	 * @param table
	 */
	public static void initClassDefaults(UIDefaults table) {
		initClassDefaults(table, true);
	}

	/**
	 * Initializes components defaults.
	 *
	 * @param table
	 */
	public static void initComponentDefaults(UIDefaults table) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		WindowsDesktopProperty defaultTextColor = new WindowsDesktopProperty("win.button.textColor", table.get("controlText"), toolkit);

		WindowsDesktopProperty defaultBackgroundColor = new WindowsDesktopProperty("win.3d.backgroundColor", table.get("control"), toolkit);

		WindowsDesktopProperty defaultLightColor = new WindowsDesktopProperty("win.3d.lightColor", table.get("controlHighlight"), toolkit);
		WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", table.get("controlLtHighlight"), toolkit);
		WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", table.get("controlShadow"), toolkit);
		WindowsDesktopProperty defaultDarkShadowColor = new WindowsDesktopProperty("win.3d.darkShadowColor", table.get("controlDkShadow"), toolkit);

		Color defaultFormBackground = new ColorUIResource(0xBFDBFF);

		Object toolbarFont = FontUtil.getMenuFont(toolkit, table);
		Object boldFont = FontUtil.getBoldFont(toolkit, table);

		Painter gripperPainter = (c, g, rect, orientation, state) -> {
			Object p = UIDefaultsLookup.get("Theme.painter");
			if (p instanceof ThemePainter) {
				((ThemePainter) p).paintGripper(c, g, rect, orientation, state);
			} else {
				BasicPainter.getInstance().paintGripper(c, g, rect, orientation, state);
			}
		};

		Object[] uiDefaults = new Object[]{
				"control", new ColorUIResource(0xF2F1F3),
				"MenuItem.checkIcon", new MenuCheckIcon(IconsFactory.getImageIcon(Office2007Painter.class, "icons/menu_checkbox.png")),
				"MenuItem.shadowColor", new ColorUIResource(0xE9EEEE),

				"PopupMenuSeparator.foreground", new ColorUIResource(0xC5C5C5),

				"JideTabbedPane.gripperPainter", gripperPainter,
				"JideSplitPaneDivider.gripperPainter", gripperPainter,
//                "Gallery.downIcon", IconsFactory.getImageIcon(Office2007Painter.class, "icons/gallery_down_button.png"),
//                "Gallery.downIconR", IconsFactory.getImageIcon(Office2007Painter.class, "icons/gallery_down_button_rollover.png"),

				"JideTabbedPane.defaultTabShape", JideTabbedPane.SHAPE_OFFICE2003,
				"JideTabbedPane.defaultTabColorTheme", JideTabbedPane.COLOR_THEME_OFFICE2003,
				"JideTabbedPane.contentBorderInsets", new InsetsUIResource(3, 3, 3, 3),
				"JideTabbedPane.background", defaultFormBackground,

				"JideButton.margin.vertical", new InsetsUIResource(2, 5, 1, 5),
				"JideButton.margin", new InsetsUIResource(3, 3, 3, 4),
				"JideButton.paintDefaultBorder", false,

				"JideSplitButton.margin.vertical", new InsetsUIResource(2, 5, 1, 5),
				"JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 4),
				"JideSplitButton.nonActiveRolloverAlpha", 40,
				"JideSplitButton.textIconGap", 4,
//                "JideSplitButton.foreground", new Color(0x3e6aaa),
//                "JideButton.foreground", new Color(0x3e6aaa),
//                "JideLabel.foreground", new Color(0x3e6aaa),
				"JideLabel.background", table.get("Label.background"),
				"JideLabel.font", table.get("Label.font"),

				"Gripper.painter", gripperPainter,
				"Gripper.foreground", new ColorUIResource(0x6593cf),
				"Gripper.light", new ColorUIResource(0xFFFFFF),
				"NavigationComponent.selectionBackground", new ColorUIResource(51, 153, 255),
		};
		table.putDefaults(uiDefaults);

		UIDefaultsLookup.put(table, "Theme.painter", MetroPainter.getInstance());

		// since it used BasicPainter, make sure it is after Theme.Painter is set first.
		Object popupMenuBorder = new ExtWindowsDesktopProperty(new String[]{"null"}, new Object[]{((ThemePainter) UIDefaultsLookup.get("Theme.painter")).getMenuItemBorderColor()}, toolkit, obj -> new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color) obj[0]), BorderFactory.createEmptyBorder(1, 1, 1, 1))));
		table.put("PopupMenu.border", popupMenuBorder);
	}
}