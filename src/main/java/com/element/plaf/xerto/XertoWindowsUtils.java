/*
 * @(#)XertoWindowsUtils.java 6/13/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.xerto;

import com.element.plaf.ExtWindowsDesktopProperty;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.WindowsDesktopProperty;
import com.element.plaf.basic.Painter;
import com.element.plaf.basic.ThemePainter;
import com.element.plaf.office2003.Office2003WindowsUtils;
import com.element.plaf.vsnet.VsnetLookAndFeelExtension;
import com.element.font.FontUtil;
import com.element.ui.icons.IconsFactory;
import com.element.ui.icons.MenuCheckIcon;
import com.element.ui.svg.icon.regular.CheckSvg;
import com.element.ui.tabs.JideTabbedPane;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

/**
 * Initialize the uiClassID to BasicComponentUI mapping for JIDE components using Xerto style for WindowsLookAndFeel.
 * Xerto Style is designed by Xerto at http://www.xerto.com.
 */
public class XertoWindowsUtils extends Office2003WindowsUtils {

	/**
	 * Initializes class defaults with menu components UIDefaults.
	 *
	 * @param table ui default table
	 */
	public static void initClassDefaultsWithMenu(UIDefaults table) {
		VsnetLookAndFeelExtension.initClassDefaultsWithMenu(table);
		initClassDefaults(table);
	}

	/**
	 * Initializes class defaults with menu components UIDefaults.
	 *
	 * @param table ui default table
	 */
	public static void initClassDefaults(UIDefaults table) {
		Office2003WindowsUtils.initClassDefaults(table, false);
		initClassDefaultsForXerto(table);
	}

	private static void initClassDefaultsForXerto(UIDefaults table) {
		final String xertoPackageName = "com.element.plaf.xerto.";
	}

	/**
	 * Initializes components defaults.
	 *
	 * @param table ui default table
	 */
	public static void initComponentDefaultsWithMenu(UIDefaults table) {
		/// always want shading
		System.setProperty("shadingtheme", "true");

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
		WindowsDesktopProperty selectionBackgroundColor = new WindowsDesktopProperty("win.item.highlightColor", UIDefaultsLookup.get("controlShadow"), toolkit);
		WindowsDesktopProperty menuTextColor = new WindowsDesktopProperty("win.menu.textColor", UIDefaultsLookup.get("control"), toolkit);

		Object menuFont = FontUtil.getMenuFont(toolkit, table);

		Object menuSelectionBackground = new ExtWindowsDesktopProperty(//Actual color 182, 189, 210
				new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new ColorUIResource(XertoUtils.getMenuSelectionColor((Color) obj[0])));

		Object menuBackground = new ExtWindowsDesktopProperty(//Actual color 249, 248, 247
				new String[]{"win.3d.backgroundColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> new ColorUIResource(XertoUtils.getMenuBackgroundColor((Color) obj[0])));

		Object separatorColor = new ExtWindowsDesktopProperty(// Not exactly right
				new String[]{"win.3d.shadowColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new ColorUIResource(((Color) obj[0]).brighter()));

		Object[] uiDefaults = {
				"PopupMenuSeparator.foreground", separatorColor,
				"PopupMenuSeparator.background", menuBackground,

				"CheckBoxMenuItem.checkIcon", new MenuCheckIcon(IconsFactory.getSvgIcon(CheckSvg.class, 20, 20)),
				"CheckBoxMenuItem.selectionBackground", menuSelectionBackground,
				"CheckBoxMenuItem.selectionForeground", menuTextColor,
				"CheckBoxMenuItem.acceleratorSelectionForeground", menuTextColor,
				"CheckBoxMenuItem.mouseHoverBackground", menuSelectionBackground,
				"CheckBoxMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))),
				"CheckBoxMenuItem.margin", new InsetsUIResource(3, 0, 3, 0),
				"CheckBoxMenuItem.font", menuFont,
				"CheckBoxMenuItem.acceleratorFont", menuFont,
				"CheckBoxMenuItem.textIconGap", 8,

				"RadioButtonMenuItem.checkIcon", new MenuCheckIcon(IconsFactory.getSvgIcon(CheckSvg.class, 20, 20)),
				"RadioButtonMenuItem.selectionBackground", menuSelectionBackground,
				"RadioButtonMenuItem.selectionForeground", menuTextColor,
				"RadioButtonMenuItem.acceleratorSelectionForeground", menuTextColor,
				"RadioButtonMenuItem.mouseHoverBackground", menuSelectionBackground,
				"RadioButtonMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))),
				"RadioButtonMenuItem.margin", new InsetsUIResource(3, 0, 3, 0),
				"RadioButtonMenuItem.font", menuFont,
				"RadioButtonMenuItem.acceleratorFont", menuFont,
				"RadioButtonMenuItem.textIconGap", 8,

				"MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)),
//            "MenuBar.border", new BorderUIResource(BorderFactory.createCompoundBorder(
//                    new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.SOUTH),
//                    BorderFactory.createEmptyBorder(2, 2, 2, 2))),

				"Menu.selectionBackground", menuSelectionBackground,
				"Menu.selectionForeground", menuTextColor,
				"Menu.mouseHoverBackground", menuSelectionBackground,
				"Menu.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))),
				"Menu.margin", new InsetsUIResource(2, 7, 1, 7),
				"Menu.checkIcon", new MenuCheckIcon(IconsFactory.getSvgIcon(CheckSvg.class, 20, 20)),
				"Menu.textIconGap", 2,
				"Menu.font", menuFont,
				"Menu.acceleratorFont", menuFont,
				"Menu.submenuPopupOffsetX", 0,
				"Menu.submenuPopupOffsetY", 0,

				"PopupMenu.border", new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(102, 102, 102)), BorderFactory.createEmptyBorder(1, 1, 1, 1))),

				"MenuItem.checkIcon", new MenuCheckIcon(IconsFactory.getSvgIcon(CheckSvg.class, 20, 20)),
				"MenuItem.selectionBackground", menuSelectionBackground,
				"MenuItem.selectionForeground", menuTextColor,
				"MenuItem.acceleratorSelectionForeground", menuTextColor,
				"MenuItem.background", menuBackground,
				"MenuItem.selectionBorderColor", selectionBackgroundColor,
				"MenuItem.shadowWidth", 24,
				"MenuItem.shadowColor", defaultHighlightColor, // TODO: not exactly. The actual one a little bit brighter than it
				"MenuItem.textIconGap", 8,
				"MenuItem.accelEndGap", 18,
				"MenuItem.margin", new InsetsUIResource(4, 0, 3, 0),
				"MenuItem.font", menuFont,
				"MenuItem.acceleratorFont", menuFont
		};
		table.putDefaults(uiDefaults);
		initComponentDefaults(table);

		UIDefaultsLookup.put(table, "Theme.painter", XertoPainter.getInstance());

		// since it used BasicPainter, make sure it is after Theme.Painter is set first.
		Object popupMenuBorder = new ExtWindowsDesktopProperty(new String[]{"null"}, new Object[]{((ThemePainter) UIDefaultsLookup.get("Theme.painter")).getMenuItemBorderColor()}, toolkit, obj -> new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color) obj[0]), BorderFactory.createEmptyBorder(1, 1, 1, 1))));
		table.put("PopupMenu.border", popupMenuBorder);
	}

	/**
	 * Initializes components defaults with menu components UIDefaults.
	 *
	 * @param table ui default table
	 */
	public static void initComponentDefaults(UIDefaults table) {
		/// always want shading
		System.setProperty("shadingtheme", "true");

		Toolkit toolkit = Toolkit.getDefaultToolkit();

		WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
		WindowsDesktopProperty defaultLtHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
		WindowsDesktopProperty selectionBackgroundColor = new WindowsDesktopProperty("win.item.highlightColor", UIDefaultsLookup.get("controlShadow"), toolkit);
		WindowsDesktopProperty mdiBackgroundColor = new WindowsDesktopProperty("win.mdi.backgroundColor", UIDefaultsLookup.get("controlShadow"), toolkit);
		WindowsDesktopProperty menuTextColor = new WindowsDesktopProperty("win.menu.textColor", UIDefaultsLookup.get("controlText"), toolkit);
		WindowsDesktopProperty defaultTextColor = new WindowsDesktopProperty("win.button.textColor", UIDefaultsLookup.get("controlText"), toolkit);
		WindowsDesktopProperty defaultBackgroundColor = new WindowsDesktopProperty("win.3d.backgroundColor", UIDefaultsLookup.get("control"), toolkit);
		WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
		WindowsDesktopProperty defaultDarkShadowColor = new WindowsDesktopProperty("win.3d.darkShadowColor", UIDefaultsLookup.get("controlDkShadow"), toolkit);
		WindowsDesktopProperty activeTitleBackgroundColor = new WindowsDesktopProperty("win.frame.activeCaptionColor", UIDefaultsLookup.get("activeCaption"), toolkit);
		WindowsDesktopProperty activeTitleTextColor = new WindowsDesktopProperty("win.frame.captionTextColor", UIDefaultsLookup.get("activeCaptionText"), toolkit);

		WindowsDesktopProperty highContrast = new WindowsDesktopProperty("win.highContrast.on", UIDefaultsLookup.get("highContrast"), toolkit);

		Object singleLineBorder = new ExtWindowsDesktopProperty(new String[]{"win.3d.shadowColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new BorderUIResource(BorderFactory.createLineBorder((Color) obj[0])));

		Object controlFont = FontUtil.getControlFont(toolkit, table);
		Object toolbarFont = FontUtil.getMenuFont(toolkit, table);
		Object boldFont = FontUtil.getBoldFont(toolkit, table);

		Object resizeBorder = new ExtWindowsDesktopProperty(new String[]{"win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor"},
				new Object[]{UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow")}, toolkit, obj -> new XertoFrameBorder(new Insets(4, 4, 4, 4)));


		Object defaultFormBackground = new ExtWindowsDesktopProperty(new String[]{"win.3d.backgroundColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> new ColorUIResource(XertoUtils.getDefaultBackgroundColor((Color) obj[0])));

		Object inactiveTabForground = new ExtWindowsDesktopProperty(// Not exactly right
				new String[]{"win.3d.shadowColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new ColorUIResource(((Color) obj[0]).darker()));

		Object focusedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(XertoUtils.getFocusedButtonColor((Color) obj[0])));

		Object selectedAndFocusedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(XertoUtils.getSelectedAndFocusedButtonColor((Color) obj[0])));

		Object selectedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(XertoUtils.getSelectedButtonColor((Color) obj[0])));


		Object gripperForeground = new ExtWindowsDesktopProperty(new String[]{"win.3d.backgroundColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> new ColorUIResource(XertoUtils.getGripperForegroundColor((Color) obj[0])));

		Object commandBarBackground = new ExtWindowsDesktopProperty(new String[]{"win.3d.backgroundColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> new ColorUIResource(XertoUtils.getToolBarBackgroundColor((Color) obj[0])));

		Painter gripperPainter = (c, g, rect, orientation, state) -> {
			Object p = UIDefaultsLookup.get("Theme.painter");
			if (p instanceof ThemePainter) {
				((ThemePainter) p).paintGripper(c, g, rect, orientation, state);
			} else {
				XertoPainter.getInstance().paintGripper(c, g, rect, orientation, state);
			}
		};

		Object buttonBorder = new BasicBorders.MarginBorder();

		Object[] uiDefaults = new Object[]{
				"Theme.highContrast", highContrast,
				"Content.background", defaultBackgroundColor,

				"JideScrollPane.border", singleLineBorder,

				"JideButton.selectedAndFocusedBackground", selectedAndFocusedButtonColor,
				"JideButton.focusedBackground", focusedButtonColor,
				"JideButton.selectedBackground", selectedButtonColor,
				"JideButton.borderColor", selectionBackgroundColor,

				"JideButton.font", controlFont,
				"JideButton.background", defaultBackgroundColor,
				"JideButton.foreground", defaultTextColor,
				"JideButton.shadow", defaultShadowColor,
				"JideButton.darkShadow", defaultDarkShadowColor,
				"JideButton.light", defaultHighlightColor,
				"JideButton.highlight", defaultLtHighlightColor,
				"JideButton.border", buttonBorder,
				"JideButton.margin", new InsetsUIResource(3, 3, 3, 3),
				"JideButton.textIconGap", 4,
				"JideButton.textShiftOffset", 0,
				"JideButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[]{
				"SPACE", "pressed",
				"released SPACE", "released",
				"ENTER", "pressed",
				"released ENTER", "released"   // no last two for metal
		}),

				"JideSplitPane.dividerSize", 3,
				"JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()),
				"JideSplitPaneDivider.background", defaultBackgroundColor,
				"JideSplitPaneDivider.gripperPainter", gripperPainter,

				"JideTabbedPane.defaultTabShape", JideTabbedPane.SHAPE_ROUNDED_VSNET,
				"JideTabbedPane.defaultResizeMode", JideTabbedPane.RESIZE_MODE_NONE,
				"JideTabbedPane.defaultTabColorTheme", JideTabbedPane.COLOR_THEME_OFFICE2003,

				"JideTabbedPane.tabRectPadding", 2,
				"JideTabbedPane.closeButtonMarginHorizonal", 3,
				"JideTabbedPane.closeButtonMarginVertical", 3,
				"JideTabbedPane.textMarginVertical", 4,
				"JideTabbedPane.noIconMargin", 2,
				"JideTabbedPane.iconMargin", 5,
				"JideTabbedPane.textPadding", 6,
				"JideTabbedPane.buttonSize", 18,
				"JideTabbedPane.buttonMargin", 5,
				"JideTabbedPane.fitStyleBoundSize", 8,
				"JideTabbedPane.fitStyleFirstTabMargin", 4,
				"JideTabbedPane.fitStyleIconMinWidth", 24,
				"JideTabbedPane.fitStyleTextMinWidth", 16,
				"JideTabbedPane.compressedStyleNoIconRectSize", 24,
				"JideTabbedPane.compressedStyleIconMargin", 12,
				"JideTabbedPane.compressedStyleCloseButtonMarginHorizontal", 0,
				"JideTabbedPane.compressedStyleCloseButtonMarginVertical", 0,
				"JideTabbedPane.fixedStyleRectSize", 60,
				"JideTabbedPane.closeButtonMargin", 2,
				"JideTabbedPane.gripLeftMargin", 4,
				"JideTabbedPane.closeButtonMarginSize", 6,
				"JideTabbedPane.closeButtonLeftMargin", 2,
				"JideTabbedPane.closeButtonRightMargin", 2,

				"JideTabbedPane.defaultTabBorderShadowColor", new ColorUIResource(115, 109, 99),

				"JideTabbedPane.gripperPainter", gripperPainter,
				"JideTabbedPane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)),
				"JideTabbedPane.background", new ColorUIResource(XertoUtils.getControlColor()),
				"JideTabbedPane.foreground", new ColorUIResource(XertoUtils.getTabForgroundColor()),
				"JideTabbedPane.light", defaultHighlightColor,
				"JideTabbedPane.highlight", defaultLtHighlightColor,
				"JideTabbedPane.shadow", defaultShadowColor,
				"JideTabbedPane.darkShadow", new ColorUIResource(Color.GRAY),
				"JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4),
				"JideTabbedPane.contentBorderInsets", new InsetsUIResource(1, 1, 1, 1),
				"JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE,
				"JideTabbedPane.tabAreaInsets", new InsetsUIResource(2, 4, 0, 4),
				"JideTabbedPane.tabAreaBackground", new ColorUIResource(XertoUtils.getApplicationFrameBackgroundColor()),
				"JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor,
				"JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor,
				"JideTabbedPane.tabRunOverlay", 2,
				"JideTabbedPane.font", controlFont,
				"JideTabbedPane.selectedTabFont", controlFont,
				"JideTabbedPane.selectedTabTextForeground", new ColorUIResource(XertoUtils.getTabForgroundColor()),
				"JideTabbedPane.unselectedTabTextForeground", inactiveTabForground,
				"JideTabbedPane.selectedTabBackground", new ColorUIResource(XertoUtils.getSelectedTabBackgroundColor()),
				"JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44),
				"JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60),
				"JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"),
				"JideTabbedPane.textIconGap", 4,
				"JideTabbedPane.showIconOnTab", Boolean.TRUE,
				"JideTabbedPane.showCloseButtonOnTab", Boolean.FALSE,
				"JideTabbedPane.closeButtonAlignment", SwingConstants.TRAILING,
				"JideTabbedPane.focusInputMap",
				new UIDefaults.LazyInputMap(new Object[]{
						"RIGHT", "navigateRight",
						"KP_RIGHT", "navigateRight",
						"LEFT", "navigateLeft",
						"KP_LEFT", "navigateLeft",
						"UP", "navigateUp",
						"KP_UP", "navigateUp",
						"DOWN", "navigateDown",
						"KP_DOWN", "navigateDown",
						"ctrl DOWN", "requestFocusForVisibleComponent",
						"ctrl KP_DOWN", "requestFocusForVisibleComponent",
				}),
				"JideTabbedPane.ancestorInputMap",
				new UIDefaults.LazyInputMap(new Object[]{
						"ctrl PAGE_DOWN", "navigatePageDown",
						"ctrl PAGE_UP", "navigatePageUp",
						"ctrl UP", "requestFocus",
						"ctrl KP_UP", "requestFocus",
				}),

				"ButtonPanel.order", "ACO",
				"ButtonPanel.oppositeOrder", "H",
				"ButtonPanel.buttonGap", 6,
				"ButtonPanel.groupGap", 6,
				"ButtonPanel.minButtonWidth", 75,

				"JideSplitButton.font", controlFont,
				"JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7),
				"JideSplitButton.border", buttonBorder,
				"JideSplitButton.borderPainted", Boolean.FALSE,
				"JideSplitButton.textIconGap", 4,
				"JideSplitButton.selectionForeground", menuTextColor,
				"JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[]{
				"SPACE", "pressed",
				"released SPACE", "released",
				"ENTER", "pressed",
				"released ENTER", "released", // no these two for metal
				"DOWN", "downPressed",
				"released DOWN", "downReleased",
		}),

				"Gripper.size", 8,
				"Gripper.foreground", gripperForeground,
				"Gripper.painter", gripperPainter,

				"HeaderBox.background", defaultBackgroundColor,

				"Icon.floating", Boolean.FALSE,

				"Resizable.resizeBorder", resizeBorder,

				"TextArea.font", controlFont,
		};
		table.putDefaults(uiDefaults);

		UIDefaultsLookup.put(table, "Theme.painter", XertoPainter.getInstance());
	}
}
