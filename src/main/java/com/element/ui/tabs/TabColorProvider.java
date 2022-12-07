/*
 * @(#)TabColorProvider.java 4/1/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.tabs;

import java.awt.*;

/**
 * A Color Provider to provide background and foreground for both {@link JideTabbedPane}.
 * <p/>
 * It has higher priority than {@link JideTabbedPane#getTabColorProvider()}.
 */
public interface TabColorProvider {
	/**
	 * Gets the background color the tab.
	 *
	 * @return the background color.
	 */
	Color getTabBackground();

	/**
	 * Gets the foreground color the tab.
	 *
	 * @return the foreground color.
	 */
	Color getTabForeground();
}
