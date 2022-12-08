/*
 * @(#)WindowsTabbedPaneUI.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.aqua;

import com.element.plaf.basic.BasicJideTabbedPaneUI;
import com.element.plaf.vsnet.VsnetJideTabbedPaneUI;
import com.element.ui.tabs.TabColorProvider;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;


/**
 * JideTabbedPane UI implementation
 */
public class AquaJideTabbedPaneUI extends VsnetJideTabbedPaneUI {
	public static ComponentUI createUI(JComponent c) {
		return new AquaJideTabbedPaneUI();
	}


	@Override
	protected void paintTabBackground(Graphics g, int tabPlacement,
	                                  int tabIndex,
	                                  int x, int y, int w, int h,
	                                  boolean isSelected) {
		super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);

		if (_tabPane.getTabColorProvider() != null && _tabPane.getTabColorProvider().getBackgroundAt(tabIndex) != null) {
			return;
		}

		if (tabIndex >= 0 && tabIndex < _tabPane.getTabCount()) {
			Component component = _tabPane.getComponentAt(tabIndex);
			if (component instanceof TabColorProvider && ((TabColorProvider) component).getTabBackground() != null) {
				return;
			}
		}

		if (!PAINT_TAB_BACKGROUND) {
			return;
		}

		if (!isSelected) {
			return;
		}

		Color[] color = AquaJideUtils.isGraphite() ? AquaJideUtils.AQUA_GRAPHITE : AquaJideUtils.AQUA_BLUE;

		if (tabRegion != null) {
			Graphics2D g2d = (Graphics2D) g;
			switch (tabPlacement) {
				case SwingConstants.LEFT, SwingConstants.RIGHT ->
						AquaJideUtils.fillAquaGradientVertical(g2d, tabRegion, color);
				default -> AquaJideUtils.fillAquaGradientHorizontal(g2d, tabRegion, color);
			}

		}
	}


	@Override
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
		// no focus rect here
	}

	@Override
	protected boolean isRoundedCorner() {
		return true;
	}

	protected boolean isShading() {
		return true;
	}

	@Override
	protected Color getBorderEdgeColor() {
		return _shadow;
	}

	private static final Color COLOR1 = new Color(130, 130, 130);
	private static final Color COLOR2 = new Color(86, 86, 86);
	private static final Color COLOR3 = new Color(252, 252, 252);

	@Override
	protected void prepareEditor(BasicJideTabbedPaneUI.TabEditor e, int tabIndex) {
		e.setOpaque(true);
		super.prepareEditor(e, tabIndex);
	}

}



