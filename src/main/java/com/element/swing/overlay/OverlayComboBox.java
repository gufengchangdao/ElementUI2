/*
 * @(#)OverlayableComboBox.java 8/10/2007
 *
 * Copyright 2002 - 2007 JIDE Software Inc. All rights reserved.
 */

package com.element.swing.overlay;

import com.element.util.OverlayableUtil;

import javax.swing.*;
import java.util.Vector;

public class OverlayComboBox<E> extends JComboBox<E> {
	public OverlayComboBox() {
	}

	public OverlayComboBox(Vector<E> items) {
		super(items);
	}

	public OverlayComboBox(final E[] items) {
		super(items);
	}

	public OverlayComboBox(ComboBoxModel<E> aModel) {
		super(aModel);
	}

	@Override
	public void repaint(long tm, int x, int y, int width, int height) {
		super.repaint(tm, x, y, width, height);
		OverlayableUtil.repaintOverlayable(this);
	}

}
