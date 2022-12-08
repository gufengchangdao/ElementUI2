/*
 * @(#)AquaJideUtils.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.aqua;

import com.element.plaf.ExtWindowsDesktopProperty;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.vsnet.VsnetLookAndFeelExtension;
import com.element.ui.tabs.JideTabbedPane;
import com.element.util.UIUtil;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

/**
 * AquaJideUtils to add Jide extension to AquaLookAndFeel
 */
public class AquaJideUtils extends VsnetLookAndFeelExtension {
	/**
	 * Initializes class defaults.
	 *
	 * @param table
	 */
	public static void initClassDefaults(UIDefaults table) {
		VsnetLookAndFeelExtension.initClassDefaults(table);

		final String aquaPackageName = "com.element.plaf.aqua.";

		table.put("JideSplitButtonUI", aquaPackageName + "AquaJideSplitButtonUI");
		table.put("JidePopupMenuUI", aquaPackageName + "AquaJidePopupMenuUI");
		table.put("JideTabbedPaneUI", aquaPackageName + "AquaJideTabbedPaneUI");
		table.put("GripperUI", aquaPackageName + "AquaGripperUI");
		table.put("RangeSliderUI", aquaPackageName + "AquaRangeSliderUI");
	}

	/**
	 * Initializes components defaults.
	 *
	 * @param table
	 */
	public static void initComponentDefaults(UIDefaults table) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		Object defaultTextColor = UIDefaultsLookup.get("controlText");

		Object defaultBackgroundColor = UIDefaultsLookup.get("Panel.background"); // AquaImageFactory.getWindowBackgroundColorUIResource();

		Object defaultLightColor = UIDefaultsLookup.get("controlHighlight");
		Object defaultHighlightColor = UIDefaultsLookup.get("controlLtHighlight");
		Object defaultShadowColor = UIDefaultsLookup.get("controlShadow");
		Object defaultDarkShadowColor = UIDefaultsLookup.get("controlDkShadow");

		Object mdiBackgroundColor = UIDefaultsLookup.get("Panel.background"); // AquaImageFactory.getWindowBackgroundColorUIResource();

		Object controlFont = UIDefaultsLookup.get("Button.font"); // new UIDefaults.ProxyLazyValue("apple.laf.AquaLookAndFeel", "getControlTextFont");

		Object controlSmallFont = UIDefaultsLookup.get("TabbedPane.smallFont"); // new UIDefaults.ProxyLazyValue("apple.laf.AquaLookAndFeel", "getControlTextSmallFont");

		Object boldFont = UIDefaultsLookup.get("Button.font"); // new UIDefaults.ProxyLazyValue("apple.laf.AquaLookAndFeel", "getControlTextFont");

		Object resizeBorder = BorderFactory.createLineBorder(new Color(230, 230, 230), 2);

		Object defaultFormBackground = new ExtWindowsDesktopProperty(// Not exactly right
				new String[]{"win.3d.shadowColor"}, new Object[]{UIDefaultsLookup.get("control")}, toolkit, obj -> obj[0]);

		Object inactiveTabForeground = new ExtWindowsDesktopProperty(// Not exactly right
				new String[]{"win.3d.shadowColor"}, new Object[]{UIDefaultsLookup.get("controlShadow")}, toolkit, obj -> ((Color) obj[0]).darker());

		Object focusedButtonColor = UIDefaultsLookup.get("Menu.selectionBackground"); // AquaImageFactory.getMenuSelectionBackgroundColorUIResource();

		Object selectedAndFocusedButtonColor = UIDefaultsLookup.get("Menu.selectionBackground"); // AquaImageFactory.getMenuSelectionBackgroundColorUIResource();

		Object selectedButtonColor = UIDefaultsLookup.get("Menu.selectionBackground"); // AquaImageFactory.getMenuSelectionBackgroundColorUIResource();

		Object selectionBackgroundColor = UIDefaultsLookup.get("TextField.selectionBackground"); // AquaImageFactory.getTextSelectionBackgroundColorUIResource();

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
				"JideButton.light", defaultLightColor,
				"JideButton.highlight", defaultHighlightColor,

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

				"TristateCheckBox.icon", null,
				"TristateCheckBox.setMixed.clientProperty", new Object[]{"JButton.selectedState", "indeterminate"},
				"TristateCheckBox.clearMixed.clientProperty", new Object[]{"JButton.selectedState", null},

				"JideSplitPane.dividerSize", 3,
				"JideSplitPaneDivider.border", new BorderUIResource(BorderFactory.createEmptyBorder()),
				"JideSplitPaneDivider.background", defaultBackgroundColor,

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

				"JideTabbedPane.border", new BorderUIResource(BorderFactory.createEmptyBorder(0, 0, 0, 0)),
				"JideTabbedPane.background", defaultFormBackground,
				"JideTabbedPane.foreground", defaultTextColor,
				"JideTabbedPane.light", defaultLightColor,
				"JideTabbedPane.highlight", defaultHighlightColor,
				"JideTabbedPane.shadow", defaultShadowColor,
				"JideTabbedPane.tabInsets", new InsetsUIResource(1, 4, 1, 4),
				"JideTabbedPane.contentBorderInsets", new InsetsUIResource(2, 2, 2, 2),
				"JideTabbedPane.ignoreContentBorderInsetsIfNoTabs", Boolean.FALSE,
				"JideTabbedPane.tabAreaInsets", new InsetsUIResource(2, 4, 0, 4),
				"JideTabbedPane.tabAreaBackground", defaultFormBackground,
				"JideTabbedPane.tabAreaBackgroundLt", defaultHighlightColor,
				"JideTabbedPane.tabAreaBackgroundDk", defaultBackgroundColor,
				"JideTabbedPane.tabRunOverlay", 2,
				"JideTabbedPane.font", controlSmallFont,
				"JideTabbedPane.selectedTabFont", controlSmallFont,
				"JideTabbedPane.darkShadow", defaultTextColor,
				"JideTabbedPane.selectedTabTextForeground", defaultTextColor,
				"JideTabbedPane.unselectedTabTextForeground", inactiveTabForeground,
				"JideTabbedPane.selectedTabBackground", defaultBackgroundColor,
				"JideTabbedPane.selectedTabBackgroundLt", new ColorUIResource(230, 139, 44),
				"JideTabbedPane.selectedTabBackgroundDk", new ColorUIResource(255, 199, 60),
				"JideTabbedPane.tabListBackground", UIDefaultsLookup.getColor("List.background"),
				"JideTabbedPane.textIconGap", 4,
				"JideTabbedPane.showIconOnTab", Boolean.TRUE,
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

				"Resizable.resizeBorder", resizeBorder,

				"MeterProgressBar.border", new BorderUIResource(BorderFactory.createLineBorder(Color.BLACK)),
				"MeterProgressBar.background", new ColorUIResource(Color.BLACK),
				"MeterProgressBar.foreground", new ColorUIResource(Color.GREEN),
				"MeterProgressBar.cellForeground", new ColorUIResource(Color.GREEN),
				"MeterProgressBar.cellBackground", new ColorUIResource(0x008000),
				"MeterProgressBar.cellLength", 2,
				"MeterProgressBar.cellSpacing", 2,

				"ButtonPanel.order", "CA",
				"ButtonPanel.oppositeOrder", "HO",
				"ButtonPanel.buttonGap", 6,
				"ButtonPanel.groupGap", 12,
				"ButtonPanel.minButtonWidth", 69,

				"Contour.color", new ColorUIResource(136, 136, 136),
				"Contour.thickness", 4,

				"Gripper.size", 8,
				"Gripper.foreground", defaultShadowColor,

				"Icon.floating", Boolean.FALSE,

				"JideScrollPane.border", table.getBorder("ScrollPane.border"),

				"JideSplitButton.font", controlFont,
				"JideSplitButton.background", defaultBackgroundColor,
				"JideSplitButton.foreground", defaultTextColor,
				"JideSplitButton.margin", new InsetsUIResource(3, 3, 3, 7),
				"JideSplitButton.border", buttonBorder,
				"JideSplitButton.borderPainted", Boolean.FALSE,
				"JideSplitButton.textIconGap", 4,
				"JideSplitButton.focusInputMap", new UIDefaults.LazyInputMap(new Object[]{
				"SPACE", "pressed",
				"released SPACE", "released",
				"ENTER", "pressed",
				"released ENTER", "released",
				"DOWN", "downPressed",
				"released DOWN", "downReleased"
		})
		};
		table.putDefaults(uiDefaults);

		UIDefaultsLookup.put(table, "Theme.painter", AquaPainter.getInstance());
	}

	public static boolean isGraphite() {
		String appleAquaColorVariant = AquaPreferences.getString("AppleAquaColorVariant");
		return "6".equals(appleAquaColorVariant);
	}

	// HALF buttons have to colours
	// lighter upper half
	// darker  lower half
	public static final Color[] HALF_LIGHT = {
			new Color(251, 251, 251),
			new Color(237, 237, 237)
	};

	public static final Color[] HALF_DARK = {
			new Color(133, 133, 133),
			new Color(125, 125, 125)
	};

	// AQUA gradients consist of two gradients for each half
	// light upper half
	// dark  upper half
	// light lower half
	// dark  lower half
	public static final Color[] AQUA_WHITE = {
			new Color(252, 252, 252),
			new Color(236, 236, 236),
			new Color(225, 225, 225),
			new Color(255, 255, 255)
	};

	public static final Color[] AQUA_BLUE = {
			new Color(221, 225, 244),
			new Color(139, 187, 238),
			new Color(100, 168, 242),
			new Color(187, 255, 255)
	};

	public static final Color[] AQUA_GRAPHITE = {
			new Color(231, 233, 235),
			new Color(182, 188, 198),
			new Color(158, 158, 180),
			new Color(231, 241, 255)
	};


	public static final Color[] AQUA_BANNER_WHITE = {
			new Color(255, 255, 255),
			new Color(248, 248, 248),
			new Color(228, 228, 228),
			new Color(239, 239, 239)
	};

	public static final Color[] AQUA_BANNER_BLUE = {
			new Color(103, 159, 254),
			new Color(73, 132, 253),
			new Color(51, 132, 253),
			new Color(84, 170, 254)
	};

	public static Color[] reverse(Color[] colors) {
		Color[] reverse = new Color[colors.length];
		for (int i = 0; i < colors.length; i++) {
			reverse[i] = colors[colors.length - i - 1];
		}
		return reverse;
	}

	public static void fillAquaGradientHorizontal(Graphics g, final Shape shape, final Color[] colors) {
		Color[] c = colors;
		if (c == null || c.length != 4) {
			c = AQUA_WHITE;
		}

		Graphics2D g2d = (Graphics2D) g;
// cause icon and text not showing when close button is on tab.
//        Shape oldClipShape = g2d.getClip();
//        g2d.setClip(shape);
		Rectangle rect = shape.getBounds();
		Rectangle r2 = new Rectangle(rect.x, rect.y + rect.height / 2, rect.width, rect.height / 2);
		Rectangle r1 = new Rectangle(rect.x, rect.y, rect.width, rect.height / 2);
		UIUtil.fillGradient(g2d, r1, c[0], c[1], true);
		UIUtil.fillGradient(g2d, r2, c[2], c[3], true);
//        g2d.setClip(oldClipShape);
	}

	public static void fillAquaGradientVertical(Graphics g, final Shape shape, final Color[] colors) {
		Color[] c = colors;
		if (c == null || c.length != 4) {
			c = AQUA_WHITE;
		}

		Graphics2D g2d = (Graphics2D) g;
//        Shape oldClipShape = g2d.getClip();
//        g2d.setClip(shape);
		Rectangle rect = shape.getBounds();
		Rectangle r2 = new Rectangle(rect.x + rect.width / 2, rect.y, rect.width / 2, rect.height);
		Rectangle r1 = new Rectangle(rect.x, rect.y, rect.width / 2, rect.height);
		UIUtil.fillGradient(g2d, r1, c[0], c[1], false);
		UIUtil.fillGradient(g2d, r2, c[2], c[3], false);
//        g.setClip(oldClipShape);
	}

	public static void fillSquareButtonHorizontal(Graphics g, Shape shape, final Color[] colors) {
		Color[] c = colors;
		if (c == null || c.length != 2) {
			c = HALF_LIGHT;
		}

		Graphics2D g2d = (Graphics2D) g;
//        Shape oldClipShape = g2d.getClip();
//        g2d.setClip(shape);
		Rectangle rect = shape.getBounds();
		g2d.setColor(c[0]);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height / 2);
		g2d.setColor(c[1]);
		g2d.fillRect(rect.x, rect.y + rect.height / 2, rect.width, rect.height / 2);

//        g2d.setClip(oldClipShape);
	}

	public static void fillSquareButtonVertical(Graphics g, Shape shape, final Color[] colors) {
		Color[] c = colors;
		if (c == null || c.length != 2) {
			c = HALF_LIGHT;
		}

		Graphics2D g2d = (Graphics2D) g;
//        Shape oldClipShape = g2d.getClip();
//        g2d.setClip(shape);
		Rectangle rect = shape.getBounds();
		g.setColor(c[0]);
		g.fillRect(rect.x, rect.y, rect.width / 2, rect.height);
		g.setColor(c[1]);
		g.fillRect(rect.x + rect.width / 2, rect.y, rect.width / 2, rect.height);
//        g2d.setClip(oldClipShape);
	}


	public static void antialiasShape(Graphics g, boolean onoff) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				onoff ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);

	}

	public static void antialiasText(Graphics g, boolean onoff) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				onoff ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

	}

}
