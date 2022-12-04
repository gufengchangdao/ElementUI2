/*
 * @(#)EclipssMetalUtils.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.eclipse;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.basic.Painter;
import com.element.plaf.basic.ThemePainter;
import com.element.plaf.metal.MetalIconFactory;
import com.element.ui.button.JideButton;
import com.element.ui.font.FontUtil;
import com.element.ui.icons.IconsFactory;
import com.element.swing.JideIconsFactory;
import com.element.ui.tabs.JideTabbedPane;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

/**
 * Utility Class for WindowsLookAndFeel to add Eclipse related LookAndFeel style
 */
public class EclipseMetalUtils extends EclipseLookAndFeelExtension {

	/**
	 * Initializes class defaults.
	 *
	 * @param table
	 */
	public static void initClassDefaults(UIDefaults table) {
		EclipseLookAndFeelExtension.initClassDefaults(table);
	}

	/**
	 * Initializes components defaults.
	 *
	 * @param table
	 */
	public static void initComponentDefaults(UIDefaults table) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Object defaultTextColor = UIDefaultsLookup.get("controlText");
		Object defaultBackgroundColor = UIDefaultsLookup.get("control");
		Object defaultHighlightColor = UIDefaultsLookup.get("controlHighlight");
		Object defaultLtHighlightColor = UIDefaultsLookup.get("controlLtHighlight");
		Object defaultShadowColor = UIDefaultsLookup.get("controlShadow");
		Object defaultDarkShadowColor = UIDefaultsLookup.get("controlDkShadow");
		Object activeTitleTextColor = UIDefaultsLookup.get("activeCaptionText");
		Color activeTitleBackgroundColor = UIDefaultsLookup.getColor("activeCaption");
		Object activeTitleBarGradientColor = activeTitleBackgroundColor != null ? activeTitleBackgroundColor.darker() : Color.gray.darker();
		Object activeTitleBorderColor = UIDefaultsLookup.get("controlDkShadow");
		Object inactiveTitleTextColor = UIDefaultsLookup.get("controlText");
		Object inactiveTitleBackgroundColor = UIDefaultsLookup.get("control");
		Object mdiBackgroundColor = UIDefaultsLookup.get("controlShadow");
		Object selectionBackgroundColor = UIDefaultsLookup.getColor("controlShadow");

		Object controlFont = FontUtil.getControlFont(toolkit, table);
		Object toolbarFont = FontUtil.getMenuFont(toolkit, table);
		Object boldFont = FontUtil.getBoldFont(toolkit, table);

		Border shadowBorder = BorderFactory.createCompoundBorder(new ShadowBorder(null, null, new Color(171, 168, 165), new Color(143, 141, 138), new Insets(0, 0, 2, 2)),
				BorderFactory.createLineBorder(Color.gray));

		Object slidingEastFrameBorder = new FrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 4, 0, 0));

		Object slidingWestFrameBorder = new FrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 0, 0, 4));

		Object slidingNorthFrameBorder = new FrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(0, 0, 4, 0));

		Object slidingSouthFrameBorder = new FrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"), new Insets(4, 0, 0, 0));

		Object focusedButtonColor =
				new ColorUIResource(EclipseUtils.getFocusedButtonColor(UIDefaultsLookup.getColor("textHighlight")));

		Object selectedAndFocusedButtonColor =
				new ColorUIResource(EclipseUtils.getSelectedAndFocusedButtonColor(UIDefaultsLookup.getColor("textHighlight")));

		Object selectedButtonColor =
				new ColorUIResource(EclipseUtils.getSelectedButtonColor(UIDefaultsLookup.getColor("textHighlight")));

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
				"released SPACE", "released"
		}),

				"TristateCheckBox.icon", MetalIconFactory.getCheckBoxIcon(),

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

				"JideTabbedPane.gripperPainter", gripperPainter,
				"JideTabbedPane.border", new BorderUIResource(shadowBorder),
				"JideTabbedPane.background", defaultBackgroundColor,
				"JideTabbedPane.foreground", defaultTextColor,
				"JideTabbedPane.light", defaultHighlightColor,
				"JideTabbedPane.highlight", defaultLtHighlightColor,
				"JideTabbedPane.shadow", defaultShadowColor,
				"JideTabbedPane.darkShadow", defaultDarkShadowColor,
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
				"JideTabbedPane.selectedTabTextForeground", activeTitleTextColor,
				"JideTabbedPane.unselectedTabTextForeground", inactiveTitleTextColor,
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

				"Resizable.resizeBorder", new BorderUIResource(shadowBorder),

				"Gripper.size", 8,
				"Gripper.size", 8,
				"Gripper.painter", gripperPainter,

				"MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(2, 2, 2, 2)),
				"Icon.floating", Boolean.FALSE,

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
				"DOWN", "downPressed",
				"released DOWN", "downReleased"
		}),

				"ButtonPanel.order", "ACO",
				"ButtonPanel.oppositeOrder", "H",
				"ButtonPanel.buttonGap", 5,
				"ButtonPanel.groupGap", 5,
				"ButtonPanel.minButtonWidth", 57,

				"HeaderBox.background", defaultBackgroundColor,

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
		};
		table.putDefaults(uiDefaults);

		UIDefaultsLookup.put(table, "Theme.painter", EclipsePainter.getInstance());
	}
}
