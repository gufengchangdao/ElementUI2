/*
 * @(#)SidePaneListener.java
 *
 * Copyright 2004 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.pane;

import com.element.ui.pane.SidePaneEvent;

import java.util.EventListener;

/**
 * The listener interface for receiving side pane events.
 */
public interface SidePaneListener extends EventListener {

	/**
	 * Invoked when a tab is selected in the <code>SidePaneGroup</code>.
	 *
	 * @param e SidePaneEvent
	 */
	void sidePaneTabSelected(SidePaneEvent e);

	/**
	 * Invoked when a tab is deselected in the <code>SidePaneGroup</code>.
	 *
	 * @param e SidePaneEvent
	 */
	void sidePaneTabDeselected(SidePaneEvent e);

}
