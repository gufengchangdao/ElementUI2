/*
 * @(#)JideSidePaneUI.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf;

import com.element.ui.pane.SidePaneGroup;
import com.element.ui.pane.SidePaneItem;

import javax.swing.plaf.PanelUI;
import java.awt.*;

/**
 * ComponentUI for SidePane.
 */
public abstract class SidePaneUI extends PanelUI {

	abstract public int getSelectedItemIndex(Point p);

	abstract public SidePaneGroup getGroupForIndex(int index);

	abstract public SidePaneItem getItemForIndex(int index);
}
