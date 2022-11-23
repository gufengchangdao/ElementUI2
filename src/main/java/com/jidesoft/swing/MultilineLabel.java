/*
 * @(#)MultilineLabel.java
 *
 * Copyright 2002 JIDE Software. All rights reserved.
 */
package com.jidesoft.swing;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import javax.swing.text.DefaultCaret;
import java.awt.*;

/**
 * 普通 JLabel 不能有多行。如果你想要多行标签，你可以使用这个类。
 */
public class MultilineLabel extends JTextArea {
	public MultilineLabel() {
		initComponents();
	}

	public MultilineLabel(String s) {
		super(s);
		initComponents();
	}

	private void initComponents() {
		adjustUI();
	}

	/**
	 * Reloads the pluggable UI.  The key used to fetch the new interface is <code>getUIClassID()</code>.  The type of
	 * the UI is <code>TextUI</code>.  <code>invalidate</code> is called after setting the UI.
	 */
	@Override
	public void updateUI() {
		super.updateUI();
		adjustUI();
	}

	/**
	 * Adjusts UI to make sure it looks like a label instead of a text area.
	 */
	protected void adjustUI() {
		setLineWrap(true);
		setWrapStyleWord(true);
		setEditable(false);
		setRequestFocusEnabled(false);
		setFocusable(false);
		JideSwingUtilities.setComponentTransparent(this);

		setCaret(new DefaultCaret() {
			@Override
			protected void adjustVisibility(Rectangle nloc) {
			}
		});

		LookAndFeel.installBorder(this, "Label.border");
		Color fg = getForeground();
		if (fg == null || fg instanceof UIResource) {
			setForeground(UIManager.getColor("Label.foreground"));
		}
		Font f = getFont();
		if (f == null || f instanceof UIResource) {
			setFont(UIManager.getFont("Label.font"));
		}
		setBackground(null);
	}

	/**
	 * Overrides <code>getMinimumSize</code> to return <code>getPreferredSize()</code> instead. We did this because of a
	 * bug at http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4824261.
	 *
	 * @return new Dimension(1, 1).
	 */
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
