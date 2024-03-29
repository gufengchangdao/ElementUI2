/*
 * @(#)MetroPainter.java 9/1/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.metro;

import com.element.color.ColorUtil;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.basic.BasicJideButtonUI;
import com.element.plaf.basic.BasicPainter;
import com.element.plaf.basic.ComponentStateSupport;
import com.element.plaf.basic.ThemePainter;
import com.element.ui.button.JideSplitButton;
import com.element.ui.icons.IconsFactory;
import com.element.util.UIUtil;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.geom.Area;

/**
 * Painter for Metro L&F.
 * <p/>
 * Please note, this class is an internal class which is meant to be used by other JIDE classes only. Future version
 * might break your build if you use it.
 */
public class MetroPainter extends BasicPainter {

	public static final String IS_MENU_PART_BUTTON = "isMenuPartButton";

	private static MetroPainter _instance;
	private ThemePainter _defaultPainter;

	public static ThemePainter getInstance() {
		if (_instance == null) {
			_instance = new MetroPainter();
		}
		return _instance;
	}

	protected MetroPainter() {
	}

	private Color _activeThemeColor;
	private Color _normalThemeColor;
	private Color _inactiveThemeColor;

	private Color _rolloverThemeColor;
	private Color _focusThemeColor;

	protected ThemePainter createDefaultPainter() {
		return MetroPainter.getInstance();
	}

	public ThemePainter getDefaultPainter() {
		if (_defaultPainter == null) {
			_defaultPainter = createDefaultPainter();
		}
		return _defaultPainter;
	}

	public void installDefaults() {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.installDefaults();
		}
	}

	public void uninstallDefaults() {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.uninstallDefaults();
		}
	}

	public void paintContentBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		Graphics2D g2d = (Graphics2D) g.create();
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		g2d.setColor(highContrast ? UIDefaultsLookup.getColor("Content.background") : MetroUtils.BACKGROUND);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
		g2d.dispose();
	}

	private void paintButtonBorder(Component c, Graphics g, Rectangle rect, int state) {
		int x = rect.x;
		int y = rect.y;
		int w = rect.width;
		int h = rect.height;

		Graphics2D gfx = (Graphics2D) g;
		if (state == STATE_SELECTED || state == STATE_PRESSED) {
			gfx.setColor(new Color(0x8b7654));
			gfx.drawLine(x + 1, y, x + w - 2, y);
			gfx.setPaint(new GradientPaint(x, y + 1, new Color(0x8b7654), x, y + h - 3, new Color(0xc4b9a8)));
			gfx.drawLine(x, y + 1, x, y + h - 3);
			gfx.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 3);
			gfx.setColor(new Color(0xf3ca7a));
			gfx.drawLine(x, y + h - 3, x, y + h - 3);
			gfx.drawLine(x + w - 1, y + h - 3, x + w - 1, y + h - 3);

			if (c instanceof JideSplitButton && BasicJideButtonUI.shouldWrapText(c)) {
				gfx.setColor(new Color(0xd0bf85));
				gfx.drawLine(x, y + h - 30, x + w, y + h - 30);
			}
		} else if (state == STATE_ROLLOVER) {
			if (h != 0) {
				Paint lgp = new LinearGradientPaint(x, y, x, y + h,
						new float[]{.0f, .5f, 1f},
						new Color[]{new Color(0xd8ca96), new Color(0xb9a074), new Color(0xb8a98e)});
				gfx.setPaint(lgp);
			}
			gfx.drawLine(x, y + 1, x, h - 2);
			gfx.setColor(new Color(0xdbce99));
			gfx.drawLine(x + 1, y, x + w - 2, y);
			gfx.setPaint(new GradientPaint(x, y + h, new Color(0xbbae97), x + (w >> 1), y + h, new Color(0xcbc3aa), true));
			gfx.drawLine(x + 1, y + h - 1, x + w - 2, y + h - 1);
			if (h - 1 != 0) {
				gfx.setPaint(new LinearGradientPaint(x + w - 1, y, x + w - 1, y + h - 1,
						new float[]{.0f, .5f, 1f},
						new Color[]{new Color(0xdcce9a), new Color(0xc3ab7a), new Color(0xd2ceb9)}));
			}
			gfx.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);
			gfx.setColor(new Color(0xeae2a8));
			gfx.drawLine(x + 1, y + 1, x + 1, y + 1);
			gfx.drawLine(x + w - 2, y + 1, x + w - 2, y + 1);
			gfx.setColor(new Color(0xf0e3bc));
			gfx.drawLine(x + 1, y + h - 2, x + 1, y + h - 2);
			gfx.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2);

//            if (c instanceof JideSplitButton) {
//                gfx.setColor(new Color(0xd0bf85));
//                if (BasicJideButtonUI.shouldWrapText(c)) {
//                    gfx.drawLine(x, y + h - 30, x + w, y + h - 30);
//                }
//                else {
//                    int arrowSize = ((JMenu) c).isTopLevelMenu() ? 12 : 20;
//                    gfx.drawLine(x + w - arrowSize, y, x + w - arrowSize, y + h);
//                }
//            }
		} else if (state == STATE_DEFAULT) {
			// paint border
			gfx.setColor(new Color(0x7793b9));
			gfx.drawLine(x + 1, y, x + w - 2, y); // top
			gfx.drawLine(x, y + 1, x, y + h - 2); // left
			gfx.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2); // right
			gfx.drawLine(x + 1, y + h - 1, x + w - 2, y + h - 1); // bottom

			// paint dots
			gfx.setColor(new Color(0x9cb4d4));
			gfx.drawLine(x + 1, y + 1, x + 1, y + 1); // top left
			gfx.drawLine(x + 1, y + h - 2, x + 1, y + h - 2); // bottom left
			gfx.drawLine(x + w - 2, y + 1, x + w - 2, y + 1); // top right
			gfx.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2); // bottom right
		}
	}

	public void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder) {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.paintButtonBackground(c, g, rect, orientation, state, showBorder);
			return;
		}

		Color background = null;
		switch (state) {
			case STATE_DEFAULT:
				background = c.getBackground();
				break;
			case STATE_ROLLOVER:
			case STATE_SELECTED:
			case STATE_DISABLE_SELECTED:
			case STATE_PRESSED:
				if (c instanceof ComponentStateSupport) {
					background = ((ComponentStateSupport) c).getBackgroundOfState(state);
				}
				break;
		}
		if (background != null && !(background instanceof UIResource)) {
			getDefaultPainter().paintButtonBackground(c, g, rect, orientation, state, showBorder);
			return;
		}
		int x = rect.x;
		int y = rect.y;
		int width = rect.width;
		int height = rect.height;
		// readjust if we do not paint a border
		if (!showBorder) {
			x -= 1;
			y -= 1;
			width += 2;
			height += 2;
		}

		Graphics2D g2d = (Graphics2D) g.create();

//        ButtonModel model = btn.getModel();
		if (state == STATE_PRESSED) {
			paintShadowedButtonBackground(g2d, rect,
					new Color[]{new Color(0xfdad11), new Color(0xf69c18), new Color(0xf7a427), new Color(0xf0a85c)},
					new Color[]{new Color(0xfda868), new Color(0xfc8f3d), new Color(0xfcb33d)});
		} else if (state == STATE_ROLLOVER) {
			if (c.getClientProperty(IS_MENU_PART_BUTTON) == Boolean.TRUE) {
				if (1 != height - 2) {
					g2d.setPaint(new LinearGradientPaint(x + 1, y + 1, x + 1, y + height - 2,
							new float[]{0f, .5f, .51f, 1f},
							new Color[]{new Color(0xfffee2), new Color(0xffdc73), new Color(0xffd660), new Color(0xffeaa8)}));
				}
				g2d.fillRect(x + 1, y + 1, width - 2, height - 2);
			} else {
				// paint background
				g2d.setPaint(new GradientPaint(x, y, new Color(0xFFFFFF), x, y + height, new Color(0xfff35e)));
				g2d.fillRect(x + 1, y + 1, width - 2, height - 2);
				g2d.setPaint(new GradientPaint(x, y, new Color(0xfff792), x + width >> 1, y, new Color(0xFFFFFF), true));
				g2d.drawLine(x, y + height - 2, x + width, y + height - 2);
				if (2 != height - 4) {
					g2d.setPaint(new LinearGradientPaint(x + 2, y + 2, x + 2, y + height - 4,
							new float[]{0f, .36f, .37f, .38f, 1f},
							new Color[]{new Color(0xfffddf), new Color(0xffe794), new Color(0xfed160), new Color(0xfecd58), new Color(0xffe794)}));
				}
				g2d.fillRect(x + 2, y + 2, width - 4, height - 4);
//                g2d.setPaint(UIUtil.getRadialGradientPaint(x + width >> 1, y + height - 4, (int) (height * .53f),
//                        new float[]{0f, 1f},
//                        new Color[]{new Color(0xFFFFFFFF, true), new Color(0x00FFFFFF, true)}));
//                Composite oldComp = g2d.getComposite();
//                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .36f));
//                g2d.fillRect(x + 2, y + 2, width - 4, height - 4);
//                g2d.setComposite(oldComp);
			}
//            }
		} else if (state == STATE_SELECTED) {
			paintShadowedButtonBackground(g2d, rect,
					new Color[]{new Color(0xF3CFA5), new Color(0xF0B159), new Color(0xF1B151), new Color(0xFBC860)},
					new Color[]{new Color(0xFDCD98), new Color(0xF8B35B), new Color(0xFBD582)});
		} else if (state == STATE_DISABLE_SELECTED) {
			Color[] baseColors = {new Color(0xF3CFA5), new Color(0xF0B159), new Color(0xF1B151), new Color(0xFBC860)};
			Color[] innerBackgroundColors = {new Color(0xFDCD98), new Color(0xF8B35B), new Color(0xFBD582)};
			for (int i = 0, length = baseColors.length; i < length; i++) {
				baseColors[i] = ColorUtil.toGrayscale(baseColors[i]);
			}
			for (int i = 0, length = innerBackgroundColors.length; i < length; i++) {
				innerBackgroundColors[i] = ColorUtil.toGrayscale(innerBackgroundColors[i]);
			}
			paintShadowedButtonBackground(g2d, rect, baseColors, innerBackgroundColors);
		} else if (state == STATE_DEFAULT) {
			if (1 != height - 2) {
				g2d.setPaint(new LinearGradientPaint(x + 1, y + 1, x + 1, y + height - 2,
						new float[]{0f, .5f, .51f, 1f},
						new Color[]{new Color(0xe8f1fc), new Color(0xe8f1fc), new Color(0xd2e1f4), new Color(0xebf3fd)}));
			}
			g2d.fillRect(x + 1, y + 1, width - 2, height - 2);
		}

		if (showBorder) {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP));
			paintButtonBorder(c, g2d, rect, state);
		}

		g2d.dispose();
	}

	static void paintShadowedButtonBackground(Graphics2D gfx, Rectangle rect, Color[] baseColors, Color[] innerBackgroundColors) {
		assert baseColors.length == 4 && innerBackgroundColors.length == 3;

		int x = rect.x;
		int y = rect.y;
		int width = rect.width;
		int height = rect.height;

		// base background
		if (1 != height - 2) {
			gfx.setPaint(new LinearGradientPaint(x + 1, y + 1, x + width - 2, y + height - 2,
					new float[]{0f, .6f, .61f, 1f},
					baseColors));
		}
		//new Color[]{new Color(0xfdad11), new Color(0xf69c18), new Color(0xf7a427), new Color(0xf0a85c)}));
		gfx.fillRect(x + 1, y + 1, width - 2, height - 2);
		// inner background
		Area base = new Area(new Rectangle(x + 2, y + 1, width - 4, height - 3));
		base.subtract(new Area(new Rectangle(x + 2, y + height - 3, 1, 1)));
		base.subtract(new Area(new Rectangle(x + width - 3, y + height - 3, 1, 1)));
		if (2 != height - 4) {
			gfx.setPaint(new LinearGradientPaint(x + 2, y + 2, x + 2, y + height - 4,
					new float[]{.39f, .4f, 1f},
					innerBackgroundColors));
		}
		//new Color[]{new Color(0xfda868), new Color(0xfc8f3d), new Color(0xfcb33d)}));
		gfx.fill(base);
		// highlight
//        int h = (int) (height * .53f);
//        if (h > 0) {
//            gfx.setPaint(UIUtil.getRadialGradientPaint(x + width >> 1, y + height - 4, h,
//                    new float[]{0f, 1f},
//                    new Color[]{new Color(0xFFFFFFFF, true), new Color(0x00FFFFFF, true)}));
//        }
//        Composite oldComp = gfx.getComposite();
//        gfx.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .20f));
//        gfx.fill(base);
//        gfx.setComposite(oldComp);
		// paint "shadow"
//        Area shadow = new Area(new Rectangle(x + 1, y + 1, width - 2, height - 3));
//        shadow.subtract(new Area(new RoundRectangle2D.Double(x + 2, y + 4, width - 4, 5, 3, 3)));
//        gfx.setPaint(new GradientPaint(x, y, new Color(0x4B000000, true), x, y + 6, new Color(0x07000000, true)));
//        gfx.fill(shadow);
	}


	private static final Color[] STATUS_BAR_BG = new Color[]{
			new Color(0x567DB0), // 1 line
			new Color(0xFFFFFF), // 1 line
			new Color(0xD7E6F9),
			new Color(0xC7DCF8), // 0.333
			new Color(0xB3D0F5),
			new Color(0xD7E5F7), // 1
			new Color(0xCDE0F7), // 1 line
			new Color(0xBAD4F7), // 1 line
	};

	private static final Color[] SPECIAL_STATUS_BAR_BG = new Color[]{
			new Color(0x567DB0), // 1 line
			new Color(0xC5DCF8), // 1 line
			new Color(0xC2D9F7),
			new Color(0xA9CAF7), // 0.333
			new Color(0x90B6EA),
			new Color(0x7698C5), // 1
			new Color(0x7495C2), // 1 line
			new Color(0x95ADCE), // 1 line
	};

	public void paintStatusBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		if (c.isOpaque()) {
			Color bg = state == STATE_DEFAULT ? MetroUtils.BLUE : MetroUtils.DARK_BLUE;
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(bg);
			g2d.fillRect(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height);
			g2d.dispose();
		}
	}

	@Override
	public void paintStatusBarSeparator
			(JComponent
					 c, Graphics
					 g, Rectangle
					 rect, int orientation,
			 int state) {
		g.setColor(new Color(0x8DACD5));
		g.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height);
		g.setColor(new Color(0xFFFFFF));
		g.drawLine(rect.x + 1, rect.y, rect.x + 1, rect.y + rect.height);
	}

	public void paintMenuShadow(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		if (c.getClientProperty("vsnet.paintShadow") != Boolean.FALSE) {
			super.paintMenuShadow(c, g, rect, orientation, state);
			g.setColor(new Color(0xC5C5C5));
			g.drawLine(rect.x + rect.width, rect.y, rect.x + rect.width, rect.y + rect.height);
		} else {
			g.setColor(getMenuItemBackground());
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
	}

	public void paintMenuItemBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder) {
		if (c.getClientProperty(IS_MENU_PART_BUTTON) == Boolean.TRUE) {
			paintButtonBackground(c, g, rect, orientation, state, showBorder);
		} else {
			UIUtil.drawImageBorder(g, IconsFactory.getImageIcon(MetroPainter.class, "icons/menu_item_bg.png"), rect, new Insets(2, 2, 2, 2), true);
		}
	}

	@Override
	public void paintPopupMenuSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		super.paintPopupMenuSeparator(c, g, rect, orientation, state);
		g.setColor(new Color(0xC5C5C5));
		g.drawLine(rect.x + 24, rect.y, rect.x + 24, rect.y + rect.height);
	}

	public void paintDropDownIcon(Graphics g, int x, int y) {
		g.setColor(new Color(0x567db1));
		g.drawLine(x, y, x + 5, y);
		g.drawLine(x + 1, y + 1, x + 4, y + 1);
		g.drawLine(x + 2, y + 2, x + 3, y + 2);
	}

	@Override
	public void paintCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.paintCommandBarBackground(c, g, rect, orientation, state);
			return;
		}

		int x = rect.x;
		int y = rect.y;
		int width = rect.width;
		int height = rect.height;

		Graphics2D g2d = (Graphics2D) g.create();
		Color color = MetroUtils.BACKGROUND;
		g2d.setColor(color);
		g2d.fillRect(x, y, width - 1, height - 1);
		g2d.dispose();
	}

	@Override
	public void paintFloatingCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		int x = rect.x;
		int y = rect.y;
		int width = rect.width;
		int height = rect.height;

		Graphics2D g2d = (Graphics2D) g.create();
		Color[] colors = {new Color(0xe8f1fc), new Color(0xe8f1fc), new Color(0xd2e1f4), new Color(0xebf3fd)};
		for (int i = 0; i < colors.length; i++) {
			Color color = colors[i];
			colors[i] = ColorUtil.getDerivedColor(color, .48f);
		}
		if (height != 0) {
			g2d.setPaint(new LinearGradientPaint(x, y, x, y + height,
					new float[]{0f, .5f, .51f, 1f},
					colors));
		}
		g2d.fillRect(x, y, width, height);
		g2d.dispose();
	}

	private static final Color[] DOCKABLE_FRAME_TITLE_BAR_BG = new Color[]{
			new Color(0xe5f4fd), // 0.333
			new Color(0xd8effc),
			new Color(0xbee6fd), // 0.666
			new Color(0xa6d9f4), // 1
	};

	private static final Color[] ACTIVE_DOCKABLE_FRAME_TITLE_BAR_BG = new Color[]{
			new Color(0xffe3a7), // 0.333
			new Color(0xffcf71),
			new Color(0xffc34f), // 0.666
			new Color(0xffc854), // 1
	};

	private static final Color[] COLLAPSIBLE_PANE_TITLE_BAR_BG = new Color[]{
			new Color(0xd7e6f9), // 0.333
			new Color(0xc7dcf8),
			new Color(0xb3d0f5), // 0.666
			new Color(0xd7e5f7), // 1
	};

	private static final Color[] EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_BG = new Color[]{
			new Color(51, 153, 255),
	};

	private static final Color[] COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG = new Color[]{
			new Color(0xc7dcf8),
			new Color(0xd7e6f9), // 0.5
			new Color(0xc7dcf8),
	};

	private static final Color[] EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG = new Color[]{
			new Color(0xb9d3f7),
			new Color(0xe7f0fe), // 0.5
			new Color(0xb9d3f7),
	};

	@Override
	public void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.paintDockableFrameTitlePane(c, g, rect, orientation, state);
			return;
		}

		int x = rect.x;
		int y = rect.y;
		int w = rect.width;
		int h = rect.height;
		if (c.getBorder() != null) {
			Insets insets = c.getBorder().getBorderInsets(c);
			x += insets.left;
			y += insets.top;
			w -= insets.right + insets.left;
			h -= insets.top + insets.bottom;
		}
		boolean active = state == STATE_SELECTED;
		Color color = active ? MetroUtils.BLUE : MetroUtils.SHADOW;
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(color);
		g2d.fillRect(x + 1, y + 1, w - 1, h - 1);
		g2d.dispose();
	}

	@Override
	public void paintCollapsiblePaneTitlePaneBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.paintCollapsiblePaneTitlePaneBackground(c, g, rect, orientation, state);
			return;
		}
		paintCollapsiblePaneTitlePane(c, g, rect, COLLAPSIBLE_PANE_TITLE_BAR_BG, orientation, state);
	}

	@Override
	public void paintCollapsiblePaneTitlePaneBackgroundEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.paintCollapsiblePaneTitlePaneBackgroundEmphasized(c, g, rect, orientation, state);
			return;
		}
		paintCollapsiblePaneTitlePane(c, g, rect, EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_BG, orientation, state);
	}

	@Override
	public void paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(c, g, rect, orientation, state);
			return;
		}
		paintCollapsiblePaneTitlePaneSeparator(c, g, rect, EMPHASIZED_COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG, state);
	}

	@Override
	public void paintCollapsiblePaneTitlePaneBackgroundSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.paintCollapsiblePaneTitlePaneBackgroundSeparator(c, g, rect, orientation, state);
			return;
		}
		paintCollapsiblePaneTitlePaneSeparator(c, g, rect, COLLAPSIBLE_PANE_TITLE_BAR_SEPARATOR_BG, state);
	}

	private void paintCollapsiblePaneTitlePane(JComponent c, Graphics g, Rectangle rect, Color[] colors, int orientation, int state) {
		int x = rect.x;
		int y = rect.y;
		int w = rect.width;
		int h = rect.height;
		if (c.getBorder() != null) {
			Insets insets = c.getBorder().getBorderInsets(c);
			x += insets.left;
			y += insets.top;
			w -= insets.right + insets.left;
			h -= insets.top + insets.bottom;
		}
		if (h != 0) {
			Graphics2D g2d = (Graphics2D) g.create();
			Paint paint = null;
			// TODO 这里不明意义的newColors变量，paint的值并没有更新
			switch (orientation) {
				case SwingConstants.EAST, SwingConstants.WEST, SwingConstants.NORTH, SwingConstants.SOUTH -> {
					if (state == ThemePainter.STATE_ROLLOVER) {
						Color[] newColors = new Color[colors.length];
						for (int i = 0; i < colors.length; i++) {
							Color color = colors[i];
							newColors[i] = ColorUtil.getDerivedColor(color, 0.60f);
						}
					}
					paint = colors[0];
				}
			}
			g2d.setPaint(paint);
			g2d.fillRect(x, y, w, h);
			g2d.dispose();
		}
	}

	private void paintCollapsiblePaneTitlePaneSeparator(JComponent c, Graphics g, Rectangle rect, Color[] colors, int state) {
		int x = rect.x;
		int y = rect.y;
		int w = rect.width;
		int h = rect.height;
		if (c.getBorder() != null) {
			Insets insets = c.getBorder().getBorderInsets(c);
			x += insets.left;
			y += insets.top;
			w -= insets.right + insets.left;
			h -= insets.top + insets.bottom;
		}
		if (h != 0) {
			Graphics2D g2d = (Graphics2D) g.create();
			if (state == ThemePainter.STATE_ROLLOVER) {
				Color[] newColors = new Color[colors.length];
				for (int i = 0; i < colors.length; i++) {
					Color color = colors[i];
					newColors[i] = ColorUtil.getDerivedColor(color, 0.60f);
				}
				g2d.setPaint(new LinearGradientPaint(x, y, x + w, y, new float[]{0f, .5f, 1f}, newColors));
			} else {
				g2d.setPaint(new LinearGradientPaint(x, y, x + w, y, new float[]{0f, .5f, 1f}, colors));
			}
			g2d.fillRect(x, y, w, h);
			g2d.dispose();
		}
	}

	@Override
	public void paintCollapsiblePanesBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		if (!c.isOpaque()) {
			return;
		}
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast) {
			super.paintCollapsiblePanesBackground(c, g, rect, orientation, state);
			return;
		}
		Graphics2D g2d = (Graphics2D) g;
		if (!(c.getBackground() instanceof UIResource)) {
			UIUtil.fillGradient(g2d,
					new Rectangle(rect.x, rect.y, rect.width, rect.height),
					c.getBackground(),
					ColorUtil.getDerivedColor(c.getBackground(), 0.6f),
					orientation == SwingConstants.HORIZONTAL);
		} else {
			UIUtil.fillGradient(g2d,
					new Rectangle(rect.x, rect.y, rect.width, rect.height),
					UIDefaultsLookup.getColor("CollapsiblePanes.backgroundLt"),
					UIDefaultsLookup.getColor("CollapsiblePanes.backgroundDk"),
					orientation == SwingConstants.HORIZONTAL);
		}
	}

	@Override
	public void paintSidePaneItemBackground(JComponent c, Graphics g, Rectangle rect, Color[] colors, int orientation, int state) {
		boolean highContrast = UIManager.getBoolean("Theme.highContrast");
		if (highContrast || (colors != null && colors.length >= 1 && colors[0] != null && !(colors[0] instanceof UIResource))) {
			super.paintSidePaneItemBackground(c, g, rect, colors, orientation, state);
			return;
		}
		Graphics2D g2d = (Graphics2D) g.create();
		switch (orientation) {
			case SwingConstants.WEST, SwingConstants.EAST -> {
				g2d.rotate(-Math.toRadians(90), rect.x, rect.y);
				g2d.translate(-rect.height, rect.y);
				//noinspection SuspiciousNameCombination
				paintButtonBackground(c, g2d, new Rectangle(0, 0, rect.height, rect.width), SwingConstants.HORIZONTAL, state, false);
			}
			case SwingConstants.NORTH, SwingConstants.SOUTH ->
					paintButtonBackground(c, g2d, rect, SwingConstants.HORIZONTAL, state, false);
		}
		g2d.dispose();
	}

	@Override
	public void paintHeaderBoxBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		boolean paintBorder = !(c instanceof AbstractButton) || ((AbstractButton) c).isBorderPainted();
		paintButtonBackground(c, g, rect, orientation, state, paintBorder);
		if (!paintBorder) {
			Color old = g.getColor();
			g.setColor(UIDefaultsLookup.getColor("Table.gridColor"));
			g.drawLine(rect.x + rect.width - 1, rect.y, rect.x + rect.width - 1, rect.y + rect.height - 1);
			g.drawLine(rect.x, rect.y + rect.height - 1, rect.x + rect.width - 1, rect.y + rect.height - 1);
			g.setColor(old);
		}
	}

	@Override
	public void paintGripper(JComponent c, Graphics g, Rectangle rect, int orientation, int state) {
		if (rect.height > rect.width && rect.height > 6) {
			orientation = SwingConstants.VERTICAL;
		} else if (rect.height < rect.width && rect.width > 6) {
			orientation = SwingConstants.HORIZONTAL;
		}

		int longEdge = (orientation == SwingConstants.HORIZONTAL) ? rect.width : rect.height;
		int count = Math.min(9, (longEdge - 2) / 4) + 1;
		int y = rect.y;
		int x = rect.x;

		if (orientation == SwingConstants.HORIZONTAL) {
			y += rect.height / 2;
			x += 2;
		} else {
			x += rect.width / 2;
			y += 2;
		}

		for (int i = 0; i < count; i++) {
			g.setColor(getGripperForeground());
			if (orientation == SwingConstants.HORIZONTAL) {
				g.fillRect(x, y + 2, 1, 1);
				g.fillRect(x, y - 2, 1, 1);
				g.fillRect(x + 2, y, 1, 1);
				x += 4;
			} else {
				g.fillRect(x - 2, y, 1, 1);
				g.fillRect(x + 2, y, 1, 1);
				g.fillRect(x, y + 2, 1, 1);
				y += 4;
			}
		}
	}

	public Color getGripperForegroundLt() {
		return UIDefaultsLookup.getColor("Gripper.light");
	}

	@Override
	public Color getSelectionSelectedDk() {
		return new ColorUIResource(0xf8cb73);
	}

	@Override
	public Color getSelectionSelectedLt() {
		return new ColorUIResource(0xfefbd5);
	}

	public Color getBackgroundDk() {
		return UIDefaultsLookup.getColor("JideTabbedPane.background");
	}

	public Color getBackgroundLt() {
		return UIDefaultsLookup.getColor("JideTabbedPane.background");
	}

	public void fillBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, Color color) {
		Graphics2D g2d = (Graphics2D) g.create();
		if (orientation == SwingConstants.HORIZONTAL) {
			int topHeight = rect.height / 3;
			UIUtil.fillGradient((Graphics2D) g, new Rectangle(rect.x, rect.y, rect.width, topHeight), ColorUtil.getDerivedColor(color, 0.74f),
					ColorUtil.getDerivedColor(color, 0.64f), true);
			UIUtil.fillGradient((Graphics2D) g, new Rectangle(rect.x, rect.y + topHeight, rect.width, rect.height - topHeight), color,
					ColorUtil.getDerivedColor(color, 0.64f), true);
		} else {
			int leftWidth = rect.width / 3;
			UIUtil.fillGradient((Graphics2D) g, new Rectangle(rect.x, rect.y, leftWidth, rect.height), ColorUtil.getDerivedColor(color, 0.74f),
					ColorUtil.getDerivedColor(color, 0.64f), false);
			UIUtil.fillGradient((Graphics2D) g, new Rectangle(rect.x + leftWidth, rect.y, rect.width - leftWidth, rect.height), color,
					ColorUtil.getDerivedColor(color, 0.64f), false);
		}
		g2d.dispose();
	}
}