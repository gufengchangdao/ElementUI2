/*
 * @(#)${NAME}.java
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.pane;

import javax.swing.*;

/**
 * Constants used with the JideScrollPane component.
 */
public interface JideScrollPaneConstants extends ScrollPaneConstants {
	/**
	 * Identifies the area along the left side of the viewport between the
	 * upper right corner and the lower right corner.
	 */
	String ROW_FOOTER = "ROW_FOOTER";
	/**
	 * Identifies the area at the bottom where the viewport is between the
	 * lower left corner and the lower right corner.
	 */
	String COLUMN_FOOTER = "COLUMN_FOOTER";
	/**
	 * Identifies the area at the top where the viewport is between the
	 * column header and main viewport.
	 */
	String SUB_COLUMN_HEADER = "SUB_COLUMN_HEADER";

	String HORIZONTAL_LEFT = "HORIZONTAL_LEFT";      //NOI18N
	String HORIZONTAL_RIGHT = "HORIZONTAL_RIGHT";    //NOI18N
	String HORIZONTAL_LEADING = "HORIZONTAL_LEADING";      //NOI18N
	String HORIZONTAL_TRAILING = "HORIZONTAL_TRAILING";      //NOI18N
	String VERTICAL_TOP = "VERTICAL_TOP";            //NOI18N
	String VERTICAL_BOTTOM = "VERTICAL_BOTTOM";      //NOI18N
	String SUB_UPPER_LEFT = "SUB_UPPER_LEFT";       //NOI18N
	String SUB_UPPER_RIGHT = "SUB_UPPER_RIGHT";      //NOI18N
}
