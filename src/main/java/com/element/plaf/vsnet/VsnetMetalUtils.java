/*
 * @(#)VsnetMetalUtils.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.vsnet;

import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.basic.Painter;
import com.element.plaf.basic.ThemePainter;
import com.element.plaf.metal.MetalIconFactory;
import com.element.plaf.metal.MetalPainter;
import com.element.font.FontUtil;
import com.element.ui.tabs.JideTabbedPane;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.beans.Beans;

/**
 * Initialize the uiClassID to BasicComponentUI mapping for JIDE components using Vsnet style for MetalLookAndFeel.
 */
public class VsnetMetalUtils extends VsnetLookAndFeelExtension {

	/**
	 * Initializes class defaults.
	 *
	 * @param table the UI defaults
	 */
	public static void initClassDefaultsWithMenu(UIDefaults table) {
		if (!Beans.isDesignTime()) {
			table.put("MenuUI", "com.element.plaf.metal.MetalMenuUI");
		}
		initClassDefaults(table);
	}

	public static void initClassDefaults(UIDefaults table) {
		VsnetLookAndFeelExtension.initClassDefaults(table);

		final String metalPackageName = "com.element.plaf.metal.";

		// common
		table.put("JideSplitButtonUI", metalPackageName + "MetalJideSplitButtonUI");
		table.put("RangeSliderUI", metalPackageName + "MetalRangeSliderUI");
	}

	/**
	 * Initializes components defaults.
	 *
	 * @param table the UI defaults
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
		Object activeTitleBackgroundColor = UIDefaultsLookup.get("activeCaption");
		Object activeTitleBorderColor = UIDefaultsLookup.get("controlDkShadow");
		Object inactiveTitleTextColor = UIDefaultsLookup.get("controlText");
		Object inactiveTitleBackgroundColor = UIDefaultsLookup.get("control");
		Object mdiBackgroundColor = UIDefaultsLookup.get("controlShadow");

		Object controlFont = FontUtil.getControlFont(toolkit, table);
		Object toolbarFont = FontUtil.getMenuFont(toolkit, table);

		Object singleLineBorder = new BorderUIResource(BorderFactory.createLineBorder(UIDefaultsLookup.getColor("controlShadow")));

		Object slidingEastFrameBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"),
				new Insets(0, 4, 0, 0));

		Object slidingWestFrameBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"),
				new Insets(0, 0, 0, 4));

		Object slidingNorthFrameBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"),
				new Insets(0, 0, 4, 0));

		Object slidingSouthFrameBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"),
				new Insets(4, 0, 0, 0));

		Object resizeBorder = new ResizeFrameBorder(UIDefaultsLookup.getColor("control"), UIDefaultsLookup.getColor("controlLtHighlight"), UIDefaultsLookup.getColor("controlShadow"), UIDefaultsLookup.getColor("controlDkShadow"),
				new Insets(4, 4, 4, 4));

		Object defaultFormBackgroundColor = VsnetUtils.getLighterColor((Color) activeTitleBackgroundColor);

		Color highlightColor = UIDefaultsLookup.getColor("textHighlight");

		Object focusedButtonColor =
				new ColorUIResource(VsnetUtils.getRolloverButtonColor(highlightColor));

		Object selectedAndFocusedButtonColor =
				new ColorUIResource(VsnetUtils.getSelectedAndRolloverButtonColor(highlightColor));

		Object selectedButtonColor =
				new ColorUIResource(VsnetUtils.getSelectedButtonColor(highlightColor));

		Painter gripperPainter = (c, g, rect, orientation, state) -> {
			Object p = UIDefaultsLookup.get("Theme.painter");
			if (p instanceof ThemePainter) {
				((ThemePainter) p).paintGripper(c, g, rect, orientation, state);
			} else {
				MetalPainter.getInstance().paintGripper(c, g, rect, orientation, state);
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
				"JideButton.borderColor", Color.black,

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

				"JideScrollPane.border", singleLineBorder,

				"JideSplitPane.dividerSize", 3,
				"JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)),
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
				"JideTabbedPane.background", defaultFormBackgroundColor,
				"JideTabbedPane.foreground", defaultTextColor,
				"JideTabbedPane.light", defaultHighlightColor,
				"JideTabbedPane.highlight", defaultLtHighlightColor,
				"JideTabbedPane.shadow", defaultShadowColor,
				"JideTabbedPane.darkShadow", defaultTextColor,
				"JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4),
				"JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2),
				"JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE,
				"JideTabbedPane.tabAreaInsets", new InsetsUIResource(2, 4, 0, 4),
				"JideTabbedPane.tabAreaBackground", defaultFormBackgroundColor,
				"JideTabbedPane.tabAreaBackgroundLt", defaultLtHighlightColor,
				"JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor,
				"JideTabbedPane.tabRunOverlay", 2,
				"JideTabbedPane.font", controlFont,
				"JideTabbedPane.selectedTabFont", controlFont,
				"JideTabbedPane.selectedTabTextForeground", defaultDarkShadowColor,
				"JideTabbedPane.unselectedTabTextForeground", defaultDarkShadowColor,
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

				"Resizable.resizeBorder", resizeBorder,

				"Gripper.size", 8,
				"Gripper.foreground", defaultBackgroundColor,
				"Gripper.painter", gripperPainter,

				"MenuBar.border", new BorderUIResource(BorderFactory.createEmptyBorder(1, 0, 1, 0)),
				"Icon.floating", Boolean.FALSE,

				"JideSplitButton.font", controlFont,
				"JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7),
				"JideSplitButton.border", buttonBorder,
				"JideSplitButton.borderPainted", Boolean.FALSE,
				"JideSplitButton.textIconGap", 4,
				"JideSplitButton.selectionBackground", UIDefaultsLookup.getColor("MenuItem.selectionBackground"),
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

				"MeterProgressBar.border", new BorderUIResource(BorderFactory.createLineBorder(Color.BLACK)),
				"MeterProgressBar.background", new ColorUIResource(Color.BLACK),
				"MeterProgressBar.foreground", new ColorUIResource(Color.GREEN),
				"MeterProgressBar.cellForeground", new ColorUIResource(Color.GREEN),
				"MeterProgressBar.cellBackground", new ColorUIResource(0x008000),
				"MeterProgressBar.cellLength", 2,
				"MeterProgressBar.cellSpacing", 2,

				"HeaderBox.background", defaultBackgroundColor,
		};
		table.putDefaults(uiDefaults);

		// make the spinner has the same font as text field
		table.put("Spinner.font", UIDefaultsLookup.get("TextField.font"));
		table.put("FormattedTextField.font", UIDefaultsLookup.get("TextField.font"));

		UIDefaultsLookup.put(table, "Theme.painter", MetalPainter.getInstance());
	}
}