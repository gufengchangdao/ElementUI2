/*
 * @(#)MutableTreeNodeEx.java 12/16/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.dialog;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This is a subclass of DefaultMutableTreeNode to have disable feature.
 */
class MutableTreeNodeEx extends DefaultMutableTreeNode {
	protected boolean _enabled = true;

	public MutableTreeNodeEx() {
		this(null, true, true);
	}

	public MutableTreeNodeEx(Object userObject) {
		this(userObject, true, true);
	}

	public MutableTreeNodeEx(Object userObject, boolean allowsChildren) {
		this(userObject, allowsChildren, true);
	}

	public MutableTreeNodeEx(Object userObject, boolean allowsChildren, boolean enabled) {
		super(userObject, allowsChildren);
		setEnabled(enabled);
	}

	/**
	 * Override so that disabled node looks have no child.
	 *
	 * @return the count of the node's children.
	 */
	public int getChildCount() {
		if (isEnabled()) {
			return super.getChildCount();
		} else {
			return 0;
		}
	}

	/**
	 * Set the flag indicating if the node is enabled.
	 * <p/>
	 * By default, the value is true just like the other normal DefaultMutableTreeNode.
	 *
	 * @param enabled the flag
	 */
	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	/**
	 * Get the flag indicating if the node is enabled.
	 *
	 * @return true if the node is enabled. Otherwise false.
	 * @see #setEnabled(boolean)
	 */
	public boolean isEnabled() {
		return _enabled;
	}
}
