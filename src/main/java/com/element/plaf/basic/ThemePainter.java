/*
 * @(#)ThemePainter.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.basic;

import javax.swing.*;
import java.awt.*;

/**
 * 定义用于绘制 UI 的方法列表的接口。
 *
 * 请注意，此接口仍处于开发模式。如果您现在使用它，未来的版本可能会破坏您的构建。
 */
public interface ThemePainter {
	int STATE_DEFAULT = 0;
	int STATE_PRESSED = 1;
	int STATE_ROLLOVER = 2;
	int STATE_SELECTED = 3;
	int STATE_DISABLE = 4;
	int STATE_DISABLE_SELECTED = 5;
	int STATE_DISABLE_ROLLOVER = 6;
	// this is only used by JideSplitButton. When the button part is rollover, the drop down part will be inactive rollover. And vice versa.
	int STATE_INACTIVE_ROLLOVER = 7;

	void paintSelectedMenu(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintButtonBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder);

	void paintMenuItemBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintMenuItemBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, boolean showBorder);

	void paintChevronBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintDividerBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintFloatingCommandBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintMenuShadow(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintGripper(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintChevronMore(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintChevronOption(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintFloatingChevronOption(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintContentBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintStatusBarBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCommandBarTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintDockableFrameBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintDockableFrameTitlePane(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCollapsiblePaneTitlePaneBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCollapsiblePaneTitlePaneBackgroundEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCollapsiblePanesBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCollapsiblePaneTitlePaneBackgroundPlainEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCollapsiblePaneTitlePaneBackgroundPlain(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCollapsiblePaneTitlePaneBackgroundSeparatorEmphasized(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintCollapsiblePaneTitlePaneBackgroundSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintTabAreaBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintTabBackground(JComponent c, Graphics g, Shape region, Color[] colors, int orientation, int state);

	void paintSidePaneItemBackground(JComponent c, Graphics g, Rectangle rect, Color[] colors, int orientation, int state);

	void paintTabContentBorder(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintHeaderBoxBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintToolBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintStatusBarSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	void paintPopupMenuSeparator(JComponent c, Graphics g, Rectangle rect, int orientation, int state);

	Insets getSortableTableHeaderColumnCellDecoratorInsets(JComponent c, Graphics g, Rectangle rect, int orientation, int state, int sortOrder, Icon sortIcon, int orderIndex, Color indexColor, boolean paintIndex);

	void paintSortableTableHeaderColumn(JComponent c, Graphics g, Rectangle rect, int orientation, int state, int sortOrder, Icon sortIcon, int orderIndex, Color indexColor, boolean paintIndex);

	void fillBackground(JComponent c, Graphics g, Rectangle rect, int orientation, int state, Color color);

	Color getMenuItemBorderColor();

	Color getGripperForeground();

	Color getGripperForegroundLt();

	Color getSeparatorForeground();

	Color getSeparatorForegroundLt();

	Color getCollapsiblePaneContentBackground();

	Color getCollapsiblePaneTitleForeground();

	Color getCollapsiblePaneTitleForegroundEmphasized();

	Color getCollapsiblePaneFocusTitleForeground();

	Color getCollapsiblePaneFocusTitleForegroundEmphasized();

	ImageIcon getCollapsiblePaneUpIcon();

	ImageIcon getCollapsiblePaneDownIcon();

	ImageIcon getCollapsiblePaneUpIconEmphasized();

	ImageIcon getCollapsiblePaneDownIconEmphasized();

	ImageIcon getCollapsiblePaneTitleButtonBackground();

	ImageIcon getCollapsiblePaneTitleButtonBackgroundEmphasized();

	ImageIcon getCollapsiblePaneUpMask();

	ImageIcon getCollapsiblePaneDownMask();

	Color getBackgroundDk();

	Color getBackgroundLt();

	Color getSelectionSelectedDk();

	Color getSelectionSelectedLt();

	Color getMenuItemBackground();

	Color getCommandBarTitleBarBackground();

	Color getColor(Object key);

	Color getControl();

	Color getControlLt();

	Color getControlDk();

	Color getControlShadow();

	Color getDockableFrameTitleBarActiveForeground();

	Color getDockableFrameTitleBarInactiveForeground();

	Color getTitleBarBackground();

	Color getOptionPaneBannerDk();

	Color getOptionPaneBannerLt();

	Color getOptionPaneBannerForeground();

	Color getTabbedPaneSelectDk();

	Color getTabbedPaneSelectLt();

	Color getTabAreaBackgroundDk();

	Color getTabAreaBackgroundLt();
}
