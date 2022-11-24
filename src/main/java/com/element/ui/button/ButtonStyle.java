/*
 * @(#)ButtonStyle.java 7/1/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.button;

/**
 * 各种按钮样式的定义。这由JideButton和JideSplitButton使用。
 */
public interface ButtonStyle {
	String BUTTON_STYLE_PROPERTY = "buttonStyle";

	int TOOLBAR_STYLE = 0;
	int TOOLBOX_STYLE = 1;
	int FLAT_STYLE = 2;
	int HYPERLINK_STYLE = 3;

	// we used the same definition as Mac OS X.
	// http://developer.apple.com/technotes/tn2007/tn2196.html#JBUTTON_BUTTONTYPE
	String CLIENT_PROPERTY_SEGMENT_POSITION = "JButton.segmentPosition";
	String SEGMENT_POSITION_FIRST = "first";
	String SEGMENT_POSITION_MIDDLE = "middle";
	String SEGMENT_POSITION_LAST = "last";
	String SEGMENT_POSITION_ONLY = "only";

	/**
	 * Gets the button style.
	 *
	 * @return the button style.
	 */
	int getButtonStyle();

	/**
	 * Sets the button style.
	 *
	 * @param buttonStyle the button style.
	 */
	void setButtonStyle(int buttonStyle);
}
