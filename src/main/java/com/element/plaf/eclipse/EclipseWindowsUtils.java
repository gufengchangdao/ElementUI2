/*
 * @(#)EclipseWindowsUtils.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.eclipse;

import com.element.plaf.ExtWindowsDesktopProperty;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.WindowsDesktopProperty;
import com.element.plaf.basic.Painter;
import com.element.plaf.basic.ThemePainter;
import com.element.font.FontUtil;
import com.element.ui.icons.IconsFactory;
import com.element.ui.icons.MenuCheckIcon;
import com.element.ui.svg.icon.bold.CheckSvg;
import com.element.ui.svg.icon.fill.RecordSvg;
import com.element.ui.tabs.JideTabbedPane;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.beans.Beans;

/**
 * Utility Class for WindowsLookAndFeel to add Eclipse related LookAndFeel style
 */
public class EclipseWindowsUtils extends EclipseLookAndFeelExtension {

	/**
	 * Initializes class defaults with menu components UIDefaults.
	 *
	 * @param table ui default table
	 */
	public static void initClassDefaultsWithMenu(UIDefaults table) {
		EclipseLookAndFeelExtension.initClassDefaultsWithMenu(table);
		initClassDefaults(table);
	}

	/**
	 * Initializes class defaults.
	 *
	 * @param table ui default table
	 */
	public static void initClassDefaults(UIDefaults table) {
		EclipseLookAndFeelExtension.initClassDefaults(table);
	}

	/**
	 * Initializes components defaults.
	 *
	 * @param table ui default table
	 */
	public static void initComponentDefaults(UIDefaults table) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		WindowsDesktopProperty defaultTextColor = new WindowsDesktopProperty("win.button.textColor", UIDefaultsLookup.get("controlText"), toolkit);
		WindowsDesktopProperty defaultBackgroundColor = new WindowsDesktopProperty("win.3d.backgroundColor", UIDefaultsLookup.get("control"), toolkit);
		WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
		WindowsDesktopProperty defaultLtHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
		WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
		WindowsDesktopProperty defaultDarkShadowColor = new WindowsDesktopProperty("win.3d.darkShadowColor", UIDefaultsLookup.get("controlDkShadow"), toolkit);
		WindowsDesktopProperty activeTitleTextColor = new WindowsDesktopProperty("win.frame.captionTextColor", UIDefaultsLookup.get("activeCaptionText"), toolkit);
		WindowsDesktopProperty activeTitleBackgroundColor = new WindowsDesktopProperty("win.frame.activeCaptionColor", UIDefaultsLookup.get("activeCaption"), toolkit);
		WindowsDesktopProperty activeTitleBarGradientColor = new WindowsDesktopProperty("win.frame.activeCaptionGradientColor", UIDefaultsLookup.get("activeCaption"), toolkit);
		WindowsDesktopProperty inactiveTitleTextColor = new WindowsDesktopProperty("win.frame.inactiveCaptionTextColor", UIDefaultsLookup.get("controlText"), toolkit);
		WindowsDesktopProperty inactiveTitleBackgroundColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);
		WindowsDesktopProperty mdiBackgroundColor = new WindowsDesktopProperty("win.mdi.backgroundColor", UIDefaultsLookup.get("controlShadow"), toolkit);

		WindowsDesktopProperty highContrast = new WindowsDesktopProperty("win.highContrast.on", UIDefaultsLookup.get("highContrast"), toolkit);

		Object controlFont = FontUtil.getControlFont(toolkit, table);
		Object toolbarFont = FontUtil.getMenuFont(toolkit, table);
		Object boldFont = FontUtil.getBoldFont(toolkit, table);

		Border shadowBorder = BorderFactory.createCompoundBorder(new ShadowBorder(null, null, new Color(171, 168, 165), new Color(143, 141, 138), new Insets(0, 0, 2, 2)),
				BorderFactory.createLineBorder(Color.gray));

		Object sunkenBorder = new ExtWindowsDesktopProperty(new String[]{"win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor"},
				new Object[]{UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow")}, toolkit, obj -> new SunkenBorder((Color) obj[0], (Color) obj[1], (Color) obj[2], (Color) obj[3],
				new Insets(1, 1, 2, 1)));

		Object focusedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(EclipseUtils.getFocusedButtonColor((Color) obj[0])));

		Object selectedAndFocusedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(EclipseUtils.getSelectedAndFocusedButtonColor((Color) obj[0])));

		Object selectedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(EclipseUtils.getSelectedButtonColor((Color) obj[0])));

		WindowsDesktopProperty selectionBackgroundColor = new WindowsDesktopProperty("win.item.highlightColor", UIDefaultsLookup.get("controlShadow"), toolkit);

		Painter gripperPainter = (c, g, rect, orientation, state) -> {
			Object p = UIDefaultsLookup.get("Theme.painter");
			if (p instanceof ThemePainter) {
				((ThemePainter) p).paintGripper(c, g, rect, orientation, state);
			} else {
				EclipsePainter.getInstance().paintGripper(c, g, rect, orientation, state);
			}
		};

		Object buttonBorder = new BasicBorders.MarginBorder();

		Object[] uiDefaults = {
				// common
				"Theme.highContrast", highContrast,
				"Content.background", defaultBackgroundColor,

				"JideLabel.font", controlFont,
				"JideLabel.background", defaultBackgroundColor,
				"JideLabel.foreground", defaultTextColor,

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
				"released ENTER", "released"
		}),

				"JideSplitPane.dividerSize", 3,
				"JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()),
				"JideSplitPaneDivider.background", defaultBackgroundColor,
				"JideSplitPaneDivider.gripperPainter", gripperPainter,

				"JideTabbedPane.defaultTabShape", JideTabbedPane.SHAPE_ECLIPSE,
				"JideTabbedPane.defaultTabColorTheme", JideTabbedPane.COLOR_THEME_WIN2K,

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
				"JideTabbedPane.fitStyleFirstTabMargin", 0,
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
				"JideTabbedPane.showFocusIndicator", Boolean.TRUE,

				"JideTabbedPane.gripperPainter", gripperPainter,
				"JideTabbedPane.border", new BorderUIResource(shadowBorder),
				"JideTabbedPane.background", defaultBackgroundColor,
				"JideTabbedPane.foreground", defaultTextColor,
				"JideTabbedPane.light", defaultHighlightColor,
				"JideTabbedPane.highlight", defaultLtHighlightColor,
				"JideTabbedPane.shadow", defaultShadowColor,
				"JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4),
				"JideTabbedPane.contentBorderInsets", new InsetsUIResource(1, 0, 0, 0),
				"JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE,
				"JideTabbedPane.tabAreaInsets", new InsetsUIResource(0, 0, 0, 0),
				"JideTabbedPane.tabAreaBackground", defaultBackgroundColor,
				"JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor,
				"JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor,
				"JideTabbedPane.tabRunOverlay", 2,
				"JideTabbedPane.font", controlFont,
				"JideTabbedPane.selectedTabFont", controlFont,
				"JideTabbedPane.darkShadow", defaultTextColor,
				"JideTabbedPane.selectedTabTextForeground", activeTitleTextColor,
				"JideTabbedPane.unselectedTabTextForeground", defaultTextColor,
				"JideTabbedPane.selectedTabBackground", defaultBackgroundColor,
				"JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44),
				"JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60),
				"JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"),
				"JideTabbedPane.textIconGap", 4,
				"JideTabbedPane.showIconOnTab", Boolean.FALSE,
				"JideTabbedPane.showCloseButtonOnTab", Boolean.TRUE,
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

				// this two colors are used by JideTabbedPane so moved it here
				"DockableFrame.activeTitleBackground2", activeTitleBarGradientColor, //EclipseUtils.getLighterColor((Color)windowTitleBackground),
				"DockableFrame.activeTitleBackground", activeTitleBackgroundColor,

				"Gripper.size", 8,
				"Gripper.painter", gripperPainter,

				"Resizable.resizeBorder", new BorderUIResource(shadowBorder),

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
				"JideSplitButton.selectionBackground", selectionBackgroundColor,
				"JideSplitButton.selectionForeground", defaultTextColor,
				"JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[]{
				"SPACE", "pressed",
				"released SPACE", "released",
				"ENTER", "pressed",
				"released ENTER", "released",
				"DOWN", "downPressed",
				"released DOWN", "downReleased"
		}),
				"HeaderBox.background", defaultBackgroundColor,

				"Icon.floating", Boolean.FALSE,

				"JideScrollPane.border", new BorderUIResource(BorderFactory.createEmptyBorder()),
				"MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)),

				"TextArea.font", controlFont,
		};
		table.putDefaults(uiDefaults);

		UIDefaultsLookup.put(table, "Theme.painter", EclipsePainter.getInstance());
	}

	/**
	 * Initializes components defaults with menu components UIDefaults.
	 *
	 * @param table ui default table
	 */
	public static void initComponentDefaultsWithMenu(UIDefaults table) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		initComponentDefaults(table);

		if (!Beans.isDesignTime()) {
			WindowsDesktopProperty defaultLightColor = new WindowsDesktopProperty("win.3d.lightColor", UIDefaultsLookup.get("controlHighlight"), toolkit);
			WindowsDesktopProperty defaultHighlightColor = new WindowsDesktopProperty("win.3d.highlightColor", UIDefaultsLookup.get("controlLtHighlight"), toolkit);
			WindowsDesktopProperty selectionTextColor = new WindowsDesktopProperty("win.item.highlightTextColor", UIDefaultsLookup.get("textHighlightText"), toolkit);
			WindowsDesktopProperty selectionBackgroundColor = new WindowsDesktopProperty("win.item.highlightColor", UIDefaultsLookup.get("controlShadow"), toolkit);

			WindowsDesktopProperty defaultShadowColor = new WindowsDesktopProperty("win.3d.shadowColor", UIDefaultsLookup.get("controlShadow"), toolkit);

			Object menuBorder = new ExtWindowsDesktopProperty(new String[]{"win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor"},
					new Object[]{UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow")}, toolkit, obj -> new RaisedBorder((Color) obj[0], (Color) obj[1], (Color) obj[2], (Color) obj[3],
					new Insets(2, 2, 2, 2)));

			Object menuFont = FontUtil.getMenuFont(toolkit, table);

			Object[] uiDefaults = {
					"PopupMenuSeparator.foreground", defaultHighlightColor,
					"PopupMenuSeparator.background", defaultShadowColor,

					"CheckBoxMenuItem.checkIcon", new MenuCheckIcon(IconsFactory.getSvgIcon(CheckSvg.class, 20, 20)),
					"CheckBoxMenuItem.selectionBackground", selectionBackgroundColor,
					"CheckBoxMenuItem.selectionForeground", selectionTextColor,
					"CheckBoxMenuItem.acceleratorSelectionForeground", selectionTextColor,
					"CheckBoxMenuItem.mouseHoverBackground", defaultHighlightColor,
					"CheckBoxMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))),
					"CheckBoxMenuItem.margin", new InsetsUIResource(2, 2, 2, 2),
					"CheckBoxMenuItem.font", menuFont,
					"CheckBoxMenuItem.acceleratorFont", menuFont,

					"RadioButtonMenuItem.checkIcon", new MenuCheckIcon(IconsFactory.getSvgIcon(RecordSvg.class, 20, 20)),
					"RadioButtonMenuItem.selectionBackground", selectionBackgroundColor,
					"RadioButtonMenuItem.selectionForeground", selectionTextColor,
					"RadioButtonMenuItem.acceleratorSelectionForeground", selectionTextColor,
					"RadioButtonMenuItem.mouseHoverBackground", defaultHighlightColor,
					"RadioButtonMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))),
					"RadioButtonMenuItem.margin", new InsetsUIResource(2, 2, 2, 2),
					"RadioButtonMenuItem.font", menuFont,
					"RadioButtonMenuItem.acceleratorFont", menuFont,

					"MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 4, 2)),

					"Menu.selectionBackground", selectionBackgroundColor,
					"Menu.selectionForeground", selectionTextColor,
					"Menu.mouseHoverBackground", selectionBackgroundColor,
					"Menu.mouseHoverBorder", new BorderUIResource(BorderFactory.createEmptyBorder()),
					"Menu.mouseSelectedBorder", new BorderUIResource(BorderFactory.createEmptyBorder()),
					"Menu.margin", new InsetsUIResource(4, 6, 2, 6),
					"Menu.textIconGap", 4,
					"Menu.checkIcon", new MenuCheckIcon(IconsFactory.getSvgIcon(com.element.ui.svg.icon.regular.CheckSvg.class, 20, 20)),
					"Menu.font", menuFont,
					"Menu.acceleratorFont", menuFont,

					"PopupMenu.border", menuBorder,

					"MenuItem.checkIcon", new MenuCheckIcon(IconsFactory.getSvgIcon(com.element.ui.svg.icon.regular.CheckSvg.class, 20, 20)),
					"MenuItem.selectionBackground", selectionBackgroundColor,
					"MenuItem.selectionForeground", selectionTextColor,
					"MenuItem.acceleratorSelectionForeground", selectionTextColor,
//            "MenuItem.background", menuBackground,
					"MenuItem.selectionBorderColor", selectionBackgroundColor,
					"MenuItem.shadowWidth", 24,
					"MenuItem.shadowColor", defaultLightColor, // TODO: not exactly. The actual one a little bit brighter than it
					"MenuItem.textIconGap", 4,
					"MenuItem.accelEndGap", 18,
					"MenuItem.margin", new InsetsUIResource(2, 2, 2, 2),
					"MenuItem.font", menuFont,
					"MenuItem.acceleratorFont", menuFont,
			};
			table.putDefaults(uiDefaults);
		}
	}
}
