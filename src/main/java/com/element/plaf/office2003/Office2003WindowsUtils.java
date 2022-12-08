/**
 * @(#)Office2003WindowsUtils.java Copyright 2002 - 2004 JIDE Software. All rights reserved.
 */
package com.element.plaf.office2003;

import com.element.plaf.ExtWindowsDesktopProperty;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.WindowsDesktopProperty;
import com.element.plaf.basic.Painter;
import com.element.plaf.basic.ThemePainter;
import com.element.plaf.vsnet.VsnetWindowsUtils;
import com.element.font.FontUtil;
import com.element.ui.tabs.JideTabbedPane;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;

/**
 * WindowsLookAndFeel with Office2003 extension
 */
public class Office2003WindowsUtils extends VsnetWindowsUtils {

	/**
	 * Initializes class defaults.
	 *
	 * @param table
	 * @param withMenu
	 */
	public static void initClassDefaults(UIDefaults table, boolean withMenu) {
		if (withMenu) {
			VsnetWindowsUtils.initClassDefaultsWithMenu(table);
		} else {
			VsnetWindowsUtils.initClassDefaults(table);
		}


		table.put("JideTabbedPaneUI", "com.element.plaf.office2003.Office2003JideTabbedPaneUI");
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

		WindowsDesktopProperty defaultTextColor = new WindowsDesktopProperty("win.button.textColor", UIDefaultsLookup.get("controlText"), toolkit);
		WindowsDesktopProperty defaultBackgroundColor = new WindowsDesktopProperty("win.3d.backgroundColor", UIDefaultsLookup.get("control"), toolkit);
		WindowsDesktopProperty defaultLightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
		WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
		WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
		WindowsDesktopProperty defaultDarkShadowColor = new WindowsDesktopProperty("win.3d.darkShadowColor", UIDefaultsLookup.get("controlDkShadow"), toolkit);

		Object toolbarFont = FontUtil.getMenuFont(toolkit, table);
		Object boldFont = FontUtil.getBoldFont(toolkit, table);

		Painter gripperPainter = (c, g, rect, orientation, state) -> {
			Object p = UIDefaultsLookup.get("Theme.painter");
			if (p instanceof ThemePainter) {
				((ThemePainter) p).paintGripper(c, g, rect, orientation, state);
			} else {
				Office2003Painter.getInstance().paintGripper(c, g, rect, orientation, state);
			}
		};

		Object[] uiDefaults = new Object[]{
				"JideTabbedPane.defaultTabShape", JideTabbedPane.SHAPE_OFFICE2003,
				"JideTabbedPane.defaultTabColorTheme", JideTabbedPane.COLOR_THEME_OFFICE2003,
				"JideTabbedPane.contentBorderInsets", new InsetsUIResource(3, 3, 3, 3),

				"JideTabbedPane.gripperPainter", gripperPainter,
				"JideTabbedPane.alwaysShowLineBorder", Boolean.FALSE,
				"JideTabbedPane.showFocusIndicator", Boolean.TRUE,

				"JideSplitPaneDivider.gripperPainter", gripperPainter,

				"Gripper.size", 8,
				"Gripper.painter", gripperPainter,
				"Icon.floating", Boolean.FALSE,

				"JideScrollPane.border", UIDefaultsLookup.getBorder("ScrollPane.border"),

				"Menu.margin", new InsetsUIResource(2, 7, 3, 7),

				"Menu.submenuPopupOffsetX", 1,
				"Menu.submenuPopupOffsetY", 0,
				"MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 2, 1, 2)),

				"PopupMenu.background", (UIDefaults.ActiveValue) table12 -> Office2003Painter.getInstance().getMenuItemBackground(),
		};
		table.putDefaults(uiDefaults);

		UIDefaultsLookup.put(table, "Theme.painter", Office2003Painter.getInstance());

		// since it used BasicPainter, make sure it is after Theme.Painter is set first.
		Object popupMenuBorder = new ExtWindowsDesktopProperty(new String[]{"null"}, new Object[]{((ThemePainter) UIDefaultsLookup.get("Theme.painter")).getMenuItemBorderColor()}, toolkit, obj -> new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color) obj[0]), BorderFactory.createEmptyBorder(1, 1, 1, 1))));
		table.put("PopupMenu.border", popupMenuBorder);
	}
}
