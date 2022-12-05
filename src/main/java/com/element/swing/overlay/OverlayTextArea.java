/*
 * @(#)OverlayTextArea.java 8/14/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.element.swing.overlay;

import com.element.util.OverlayableUtil;

import javax.swing.*;
import javax.swing.text.Document;

/**
 * 支持功能：
 * <ul>
 *     <li>可覆盖组件</li>
 * </ul>
 */
public class OverlayTextArea extends JTextArea {

	public OverlayTextArea() {
	}

	public OverlayTextArea(String text) {
		super(text);
	}

	public OverlayTextArea(int rows, int columns) {
		super(rows, columns);
	}

	public OverlayTextArea(String text, int rows, int columns) {
		super(text, rows, columns);
	}

	public OverlayTextArea(Document doc) {
		super(doc);
	}

	public OverlayTextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
	}

	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
		super.repaint(tm, x, y, width, height);
		OverlayableUtil.repaintOverlayable(this);
	}
}
