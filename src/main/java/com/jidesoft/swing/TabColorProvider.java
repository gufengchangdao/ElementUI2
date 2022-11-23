/*
 * @(#)TabColorProvider.java 4/1/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.swing;

import java.awt.*;

/**
 * A Color Provider to provide background and foreground for both {@link JideTabbedPane} and {@link SidePaneItem}.
 * <p/>
 * It has higher priority than {@link JideTabbedPane#getTabColorProvider()}. However, if {@link SidePaneItem#setBackground(Color)}
 * or {@link SidePaneItem#setForeground(Color)} is invoked, the settings in {@link SidePaneItem} will be respected
 * instead of the color returned by this class.
 */
public interface TabColorProvider {
	/**
	 * Gets the background color the tab.
	 *
	 * @return the background color.
	 */
	public Color getTabBackground();

	/**
	 * Gets the foreground color the tab.
	 *
	 * @return the foreground color.
	 */
	public Color getTabForeground();
}
