/*
 * @(#)OverlayableIconsFactory.java 8/10/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.icons;

import javax.swing.*;

/**
 * A helper class to contain icons for the overlayable components
 * Those icons are copyrighted by JIDE Software, Inc.
 */
public class OverlayableIconsFactory {
	public static final String ATTENTION = "icons/overlay_attention.png";
	public static final String CORRECT = "icons/overlay_correct.png";
	public static final String ERROR = "icons/overlay_error.png";
	public static final String INFO = "icons/overlay_info.png";
	public static final String QUESTION = "icons/overlay_question.png";

	/**
	 * Gets the predefined icon that can be used as the overlay icon for the Swing component. Available icon names are
	 * <ul>
	 *     <li>{@link OverlayableIconsFactory#CORRECT}
	 *     <li>{@link OverlayableIconsFactory#ERROR}
	 *     <li>{@link OverlayableIconsFactory#ATTENTION}
	 *     <li>{@link OverlayableIconsFactory#INFO} <li>{@link OverlayableIconsFactory#QUESTION}
	 * </ul>
	 *
	 * @param name name defined in {@link OverlayableIconsFactory}.
	 * @return the icon
	 */
	public static ImageIcon getImageIcon(String name) {
		if (name != null) return IconsFactory.getImageIcon(OverlayableIconsFactory.class, name);
		else return null;
	}

	public static void main(String[] argv) {
		IconsFactory.generateHTML(OverlayableIconsFactory.class);
	}
}
