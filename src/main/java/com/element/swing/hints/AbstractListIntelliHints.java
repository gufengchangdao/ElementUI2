/*
 * @(#)AbstractListIntelliHints.java 7/24/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.swing.hints;

import com.element.swing.Sticky;
import com.element.ui.pane.JideScrollPane;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Vector;

/**
 * <code>AbstractListIntelliHints</code> extends AbstractIntelliHints and further implement most of the methods in
 * interface {@link IntelliHints}. In this class, it assumes the hints can be represented as a JList,
 * so it used JList in the hints popup.
 *
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 * @author JIDE Software, Inc.
 */
public abstract class AbstractListIntelliHints<E> extends AbstractIntelliHints {
	private JList<E> _list;
	protected KeyStroke[] _keyStrokes;
	private JideScrollPane _scroll;

	/**
	 * Creates a Completion for JTextComponent
	 *
	 * @param textComponent
	 */
	public AbstractListIntelliHints(JTextComponent textComponent) {
		super(textComponent);
	}

	public JComponent createHintsComponent() {
		JPanel panel = new JPanel(new BorderLayout());

		_list = createList();
		new Sticky(_list);
		_scroll = new JideScrollPane(getList());
		getList().setFocusable(false);

		_scroll.setHorizontalScrollBarPolicy(JideScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		_scroll.setBorder(BorderFactory.createEmptyBorder());
		_scroll.getVerticalScrollBar().setFocusable(false);
		_scroll.getHorizontalScrollBar().setFocusable(false);

		panel.add(_scroll, BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Creates the list to display the hints. By default, we create a JList using the code below.
	 * <code><pre>
	 * return new JList() {
	 *     public int getVisibleRowCount() {
	 *         int size = getModel().getSize();
	 *         return size < super.getVisibleRowCount() ? size : super.getVisibleRowCount();
	 *     }
	 * <p/>
	 *     public Dimension getPreferredScrollableViewportSize() {
	 *         if (getModel().getSize() == 0) {
	 *             return new Dimension(0, 0);
	 *         }
	 *         else {
	 *             return super.getPreferredScrollableViewportSize();
	 *         }
	 *     }
	 * };
	 * </pre></code>
	 *
	 * @return the list.
	 */
	protected JList<E> createList() {
		return new JList<E>() {
			@Override
			public int getVisibleRowCount() {
				int size = getModel().getSize();
				return Math.min(size, super.getVisibleRowCount());
			}

			@Override
			public Dimension getPreferredScrollableViewportSize() {
				if (getModel().getSize() == 0) {
					return new Dimension(0, 0);
				} else {
					return super.getPreferredScrollableViewportSize();
				}
			}
		};
	}

	/**
	 * Gets the list.
	 *
	 * @return the list.
	 */
	protected JList<E> getList() {
		return _list;
	}

	/**
	 * Sets the list data.
	 *
	 * @param objects
	 */
	protected void setListData(E[] objects) {
		if (getList() == null) {
			return;
		}
		resetSelection();
		getList().setListData(objects);

		// update the view so that isViewSizeSet flag in JViewport is reset to false
		if (_scroll != null) {
			_scroll.setViewportView(getList());
		}

	}

	/**
	 * Sets the list data.
	 *
	 * @param objects
	 */
	protected void setListData(Vector<E> objects) {
		if (getList() == null) {
			return;
		}
		resetSelection();
		getList().setListData(objects);
		// update the view so that isViewSizeSet flag in JViewport is reset to false
		if (_scroll != null) {
			_scroll.setViewportView(getList());
		}
	}

	private void resetSelection() {
		if (getList() == null) {
			return;
		}
		getList().getSelectionModel().setAnchorSelectionIndex(-1); // has to call setAnchor first
		getList().getSelectionModel().setLeadSelectionIndex(-1);
		getList().getSelectionModel().clearSelection();
	}

	public E getSelectedHint() {
		return getList() == null ? null : getList().getSelectedValue();
	}

	@Override
	public JComponent getDelegateComponent() {
		return getList();
	}

	/**
	 * Gets the delegate keystrokes. Since we know the hints popup is a JList, we return eight keystrokes so that they
	 * can be delegate to the JList. Those keystrokes are DOWN, UP, PAGE_DOWN, PAGE_UP, HOME and END.
	 *
	 * @return the keystrokes that will be delegated to the JList when hints popup is visible.
	 */
	@Override
	public KeyStroke[] getDelegateKeyStrokes() {
		if (_keyStrokes == null) {
			_keyStrokes = new KeyStroke[8];
			_keyStrokes[0] = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
			_keyStrokes[1] = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
			_keyStrokes[2] = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0);
			_keyStrokes[3] = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0);
			_keyStrokes[4] = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, KeyEvent.CTRL_DOWN_MASK);
			_keyStrokes[5] = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, KeyEvent.CTRL_DOWN_MASK);
			_keyStrokes[6] = KeyStroke.getKeyStroke(KeyEvent.VK_HOME, KeyEvent.CTRL_DOWN_MASK);
			_keyStrokes[7] = KeyStroke.getKeyStroke(KeyEvent.VK_END, KeyEvent.CTRL_DOWN_MASK);
		}
		return _keyStrokes;
	}
}