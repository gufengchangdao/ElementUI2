/*
 * @(#)VsnetWindowsUtils.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.vsnet;

import com.element.plaf.ExtWindowsDesktopProperty;
import com.element.plaf.LookAndFeelFactory;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.WindowsDesktopProperty;
import com.element.plaf.basic.BasicPainter;
import com.element.plaf.basic.Painter;
import com.element.plaf.basic.ThemePainter;
import com.element.ui.button.JideButton;
import com.element.ui.font.FontUtil;
import com.element.ui.icons.IconsFactory;
import com.element.swing.JideIconsFactory;
import com.element.ui.icons.MenuCheckIcon;
import com.element.ui.tabs.JideTabbedPane;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.beans.Beans;

/**
 * Initialize the uiClassID to BasicComponentUI mapping for JIDE components using Vsnet style for WindowsLookAndFeel.
 */
public class VsnetWindowsUtils extends VsnetLookAndFeelExtension {

	/**
	 * Initializes class defaults with menu components UIDefaults.
	 *
	 * @param table UIDefaults table
	 */
	public static void initClassDefaultsWithMenu(UIDefaults table) {
		VsnetLookAndFeelExtension.initClassDefaultsWithMenu(table);
		initClassDefaults(table);
	}

	/**
	 * Initializes class defaults.
	 *
	 * @param table UIDefaults table
	 */
	public static void initClassDefaults(UIDefaults table) {
		VsnetLookAndFeelExtension.initClassDefaults(table);
	}

	/**
	 * Initializes components defaults.
	 *
	 * @param table UIDefaults table
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
		WindowsDesktopProperty mdiBackgroundColor = new WindowsDesktopProperty("win.mdi.backgroundColor", UIDefaultsLookup.get("controlShadow"), toolkit);

		WindowsDesktopProperty menuTextColor = new WindowsDesktopProperty("win.menu.textColor", UIDefaultsLookup.get("control"), toolkit);

		WindowsDesktopProperty highContrast = new WindowsDesktopProperty("win.highContrast.on", UIDefaultsLookup.get("highContrast"), toolkit);

		Object controlFont = FontUtil.getControlFont(toolkit, table);
		Object toolbarFont = FontUtil.getMenuFont(toolkit, table);
		Object boldFont = FontUtil.getBoldFont(toolkit, table);

		Object resizeBorder = new ExtWindowsDesktopProperty(new String[]{"win.3d.lightColor", "win.3d.highlightColor", "win.3d.shadowColor", "win.3d.darkShadowColor"},
				new Object[]{UIDefaultsLookup.get("control"), UIDefaultsLookup.get("controlLtHighlight"), UIDefaultsLookup.get("controlShadow"), UIDefaultsLookup.get("controlDkShadow")}, toolkit, obj -> new ResizeFrameBorder((Color) obj[0], (Color) obj[1], (Color) obj[2], (Color) obj[3],
				new Insets(4, 4, 4, 4)));

		Object defaultFormBackground = new ExtWindowsDesktopProperty(new String[]{"win.3d.backgroundColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> VsnetUtils.getDefaultBackgroundColor((Color) obj[0]));

		Object focusedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getRolloverButtonColor((Color) obj[0])));

		Object selectedAndFocusedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getSelectedAndRolloverButtonColor((Color) obj[0])));

		Object selectedButtonColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("textHighlight")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getSelectedButtonColor((Color) obj[0])));

		Object singleLineBorder = new ExtWindowsDesktopProperty(new String[]{"win.3d.shadowColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new BorderUIResource(BorderFactory.createLineBorder((Color) obj[0])));

		Object borderColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getButtonBorderColor((Color) obj[0])));

		Object gripperForeground = new ExtWindowsDesktopProperty(new String[]{"win.3d.backgroundColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getGripperForegroundColor((Color) obj[0])));

		Painter gripperPainter = (c, g, rect, orientation, state) -> {
			Object p = UIDefaultsLookup.get("Theme.painter");
			if (p instanceof ThemePainter) {
				((ThemePainter) p).paintGripper(c, g, rect, orientation, state);
			} else {
				BasicPainter.getInstance().paintGripper(c, g, rect, orientation, state);
			}
		};

		Object buttonBorder = new BasicBorders.MarginBorder();

		Object[] uiDefaults = new Object[]{
				// common
				"Theme.highContrast", highContrast,
				"Content.background", defaultBackgroundColor,

				"JideLabel.font", controlFont,
				"JideLabel.background", defaultBackgroundColor,
				"JideLabel.foreground", defaultTextColor,

				"JideButton.selectedAndFocusedBackground", selectedAndFocusedButtonColor,
				"JideButton.focusedBackground", focusedButtonColor,
				"JideButton.selectedBackground", selectedButtonColor,
				"JideButton.borderColor", borderColor,

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

				"JideSplitPane.dividerSize", 4,
				"JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()),
				"JideSplitPaneDivider.background", defaultBackgroundColor,
				"JideSplitPaneDivider.gripperPainter", gripperPainter,

				"JideTabbedPane.defaultTabShape", JideTabbedPane.SHAPE_VSNET,
				"JideTabbedPane.defaultResizeMode", JideTabbedPane.RESIZE_MODE_NONE,
				"JideTabbedPane.defaultTabColorTheme", JideTabbedPane.COLOR_THEME_VSNET,

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
				"JideTabbedPane.background", defaultFormBackground,
				"JideTabbedPane.foreground", defaultTextColor,
				"JideTabbedPane.light", Color.BLACK.equals(defaultBackgroundColor.createValue(table)) ? defaultBackgroundColor : defaultHighlightColor,
				"JideTabbedPane.highlight", defaultLtHighlightColor,
				"JideTabbedPane.shadow", defaultShadowColor,
				"JideTabbedPane.darkShadow", defaultTextColor,
				"JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4),
				"JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2),
				"JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE,
				"JideTabbedPane.tabAreaInsets", new InsetsUIResource(2, 4, 0, 4),
				"JideTabbedPane.tabAreaBackground", defaultFormBackground,
				"JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor,
				"JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor,
				"JideTabbedPane.tabRunOverlay", 2,
				"JideTabbedPane.font", controlFont,
				"JideTabbedPane.selectedTabFont", controlFont,
				"JideTabbedPane.selectedTabTextForeground", defaultTextColor,
				"JideTabbedPane.unselectedTabTextForeground", defaultTextColor,
				"JideTabbedPane.selectedTabBackground", defaultBackgroundColor,
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

				"Gripper.size", 8,
				"Gripper.foreground", gripperForeground,
				"Gripper.painter", gripperPainter,

				"Resizable.resizeBorder", resizeBorder,

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
				"released ENTER", "released",
				"DOWN", "downPressed",
				"released DOWN", "downReleased"
		}),

				"MeterProgressBar.border", new BorderUIResource(BorderFactory.createLineBorder(Color.BLACK)),
				"MeterProgressBar.background", new ColorUIResource(Color.BLACK),
				"MeterProgressBar.foreground", new ColorUIResource(Color.GREEN),
				"MeterProgressBar.cellForeground", new ColorUIResource(Color.GREEN),
				"MeterProgressBar.cellBackground", new ColorUIResource(0x008000),
				"MeterProgressBar.cellLength", 2,
				"MeterProgressBar.cellSpacing", 2,

				"Cursor.hsplit", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.HSPLIT),
				"Cursor.vsplit", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.VSPLIT),

				"Cursor.north", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.NORTH),
				"Cursor.south", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.SOUTH),
				"Cursor.east", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.EAST),
				"Cursor.west", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.WEST),
				"Cursor.tab", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.TAB),
				"Cursor.float", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.FLOAT),
				"Cursor.vertical", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.VERTICAL),
				"Cursor.horizontal", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.HORIZONTAL),
				"Cursor.delete", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.DELETE),
				"Cursor.drag", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.DROP),
				"Cursor.dragStop", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.NODROP),
				"Cursor.dragText", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.DROP_TEXT),
				"Cursor.dragTextStop", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.NODROP_TEXT),
				"Cursor.percentage", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.PERCENTAGE),
				"Cursor.moveEast", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.MOVE_EAST),
				"Cursor.moveWest", JideIconsFactory.getImageIcon(JideIconsFactory.Cursor.MOVE_WEST),

				"HeaderBox.background", defaultBackgroundColor,

				"Icon.floating", Boolean.TRUE,

				"JideScrollPane.border", singleLineBorder,

				"TextArea.font", controlFont,
		};
		table.putDefaults(uiDefaults);

		// make the spinner has the same font as text field
		table.put("Spinner.font", UIDefaultsLookup.get("TextField.font"));
		table.put("FormattedTextField.font", UIDefaultsLookup.get("TextField.font"));

		UIDefaultsLookup.put(table, "Theme.painter", BasicPainter.getInstance());
	}

	/**
	 * Initializes components defaults with menu components UIDefaults.
	 *
	 * @param table UIDefaults table
	 */
	public static void initComponentDefaultsWithMenu(UIDefaults table) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		initComponentDefaults(table);

		if (!Beans.isDesignTime()) {
			Object defaultLightColor = new ExtWindowsDesktopProperty(
					new String[]{"win.3d.lightColor"}, new Object[]{UIDefaultsLookup.get("controlHighlight")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getLighterColor((Color) obj[0])));
			Object borderColor = new ExtWindowsDesktopProperty(new String[]{"win.item.highlightColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getButtonBorderColor((Color) obj[0])));
			WindowsDesktopProperty menuTextColor = new WindowsDesktopProperty("win.menu.textColor", UIDefaultsLookup.get("control"), toolkit);

			Object menuFont = FontUtil.getMenuFont(toolkit, table);

			Object menuSelectionBackground = new ExtWindowsDesktopProperty(//Actual color 182, 189, 210
					new String[]{"win.3d.lightColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getMenuSelectionColor((Color) obj[0])));

			Object menuBackground = new ExtWindowsDesktopProperty(//Actual color 249, 248, 247
					new String[]{"win.3d.backgroundColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> new ColorUIResource(VsnetUtils.getMenuBackgroundColor((Color) obj[0])));

			Object separatorColor = new ExtWindowsDesktopProperty(// Not exactly right
					new String[]{"win.3d.shadowColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> new ColorUIResource(((Color) obj[0]).brighter()));

			Object[] uiDefaults = {
					"PopupMenuSeparator.foreground", separatorColor,
					"PopupMenuSeparator.background", menuBackground,

					"CheckBoxMenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon(JideIconsFactory.MENU_CHECKBOX_VSNET)),
					"CheckBoxMenuItem.selectionBackground", menuSelectionBackground,
					"CheckBoxMenuItem.selectionForeground", menuTextColor,
					"CheckBoxMenuItem.acceleratorSelectionForeground", menuTextColor,
					"CheckBoxMenuItem.mouseHoverBackground", menuSelectionBackground,
					"CheckBoxMenuItem.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))),
					"CheckBoxMenuItem.margin", new InsetsUIResource(3, 0, 3, 0),
					"CheckBoxMenuItem.font", menuFont,
					"CheckBoxMenuItem.acceleratorFont", menuFont,
					"CheckBoxMenuItem.textIconGap", 8,

					"RadioButtonMenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon(JideIconsFactory.MENU_CHECKBOX_VSNET)),
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

					"Menu.selectionBackground", menuSelectionBackground,
					"Menu.selectionForeground", menuTextColor,
					"Menu.mouseHoverBackground", menuSelectionBackground,
					"Menu.mouseHoverBorder", new BorderUIResource(BorderFactory.createLineBorder(new Color(10, 36, 106))),
					"Menu.margin", new InsetsUIResource(3, 7, 2, 7),
					"Menu.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon(JideIconsFactory.MENU_CHECKBOX_VSNET)),
					"Menu.textIconGap", 2,
					"Menu.font", menuFont,
					"Menu.acceleratorFont", menuFont,
					"Menu.submenuPopupOffsetX", 0,
					"Menu.submenuPopupOffsetY", 0,

					"MenuItem.checkIcon", new MenuCheckIcon(JideIconsFactory.getImageIcon(JideIconsFactory.MENU_CHECKBOX_VSNET)),
					"MenuItem.selectionBackground", menuSelectionBackground,
					"MenuItem.selectionForeground", menuTextColor,
					"MenuItem.acceleratorSelectionForeground", menuTextColor,
					"MenuItem.background", menuBackground,
					"MenuItem.selectionBorderColor", borderColor,
					"MenuItem.shadowWidth", 24,
					"MenuItem.shadowColor", defaultLightColor, // TODO: not exactly. The actual one a little bit brighter than it
					"MenuItem.textIconGap", 8,
					"MenuItem.accelEndGap", 18,
					"MenuItem.margin", new InsetsUIResource(3, 0, 3, 0),
					"MenuItem.font", menuFont,
					"MenuItem.acceleratorFont", menuFont,
			};
			table.putDefaults(uiDefaults);
		}

		// since it used BasicPainter, make sure it is after Theme.Painter is set first.
		Object popupMenuBorder = new ExtWindowsDesktopProperty(new String[]{"null"}, new Object[]{((ThemePainter) UIDefaultsLookup.get("Theme.painter")).getMenuItemBorderColor()}, toolkit, obj -> new BorderUIResource(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder((Color) obj[0]), BorderFactory.createEmptyBorder(1, 1, 1, 1))));
		table.put("PopupMenu.border", popupMenuBorder);
	}
}
