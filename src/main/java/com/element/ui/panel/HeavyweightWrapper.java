/*
 * @(#)HeavyweightWrapper.java 10/18/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.panel;

import com.element.util.UIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * HeavyweightWrapper is a special heavyweight Panel that can hold another component.
 */
public class HeavyweightWrapper extends Panel {
	private JComponent _component;
	private boolean _heavyweight;
	private final static Dimension MIN_DIM = new Dimension(0, 0);

	public HeavyweightWrapper(JComponent component) {
		this(component, false);
	}

	public HeavyweightWrapper(JComponent component, boolean heavyweight) {
		_component = component;
		_heavyweight = heavyweight;
		if (_component != null) {
			_component.putClientProperty("HeavyweightWrapper", this);
			_component.addComponentListener(new ComponentListener() {
				public void componentResized(ComponentEvent e) {
				}

				public void componentMoved(ComponentEvent e) {
				}

				public void componentShown(ComponentEvent e) {
					setVisible(true);
				}

				public void componentHidden(ComponentEvent e) {
					setVisible(false);
				}
			});
		}
		setLayout(new BorderLayout());
		setVisible(false);
	}

	public void delegateAdd(Container parent, Object constraints) {
		UIUtil.removeFromParentWithFocusTransfer(_component);

		if (_heavyweight) {
			if (_component.getParent() != this) {
				add(_component);
			}
			if (getParent() != parent) {
				parent.add(this, constraints);
			}
		} else {
			parent.add(_component, constraints);
		}
	}

	public void delegateRemove(Container parent) {
		UIUtil.removeFromParentWithFocusTransfer(_component);

		if (_heavyweight) {
			remove(_component);
			parent.remove(this);
		} else {
			parent.remove(_component);
		}
	}

	public void delegateSetVisible(boolean visible) {
		if (_heavyweight) {
			this.setVisible(visible);
			_component.setVisible(visible);
		} else {
			_component.setVisible(visible);
		}
	}

	public void delegateSetBounds(Rectangle bounds) {
		if (_heavyweight) {
			this.setBounds(bounds);
			_component.setBounds(0, 0, bounds.width, bounds.height);
		} else {
			_component.setBounds(bounds);
		}
	}

	public void delegateSetBounds(int x, int y, int width, int height) {
		if (_heavyweight) {
			this.setBounds(x, y, width, height);
			_component.setBounds(0, 0, width, height);
		} else {
			_component.setBounds(x, y, width, height);
		}
	}

	public void delegateSetLocation(int x, int y) {
		if (_heavyweight) {
			this.setLocation(x, y);
			_component.setLocation(0, 0);
		} else {
			_component.setLocation(x, y);
		}
	}

	public void delegateSetLocation(Point p) {
		if (_heavyweight) {
			this.setLocation(p);
			_component.setLocation(0, 0);
		} else {
			_component.setLocation(p);
		}
	}

	public void delegateSetCursor(Cursor cursor) {
		_component.setCursor(cursor);
	}

	public void delegateSetNull() {
		_component.putClientProperty("HeavyweightWrapper", null);
		_component = null;
	}

	public Container delegateGetParent() {
		if (_heavyweight) {
			return getParent();
		} else {
			return _component.getParent();
		}
	}

	public boolean delegateIsVisible() {
		if (_heavyweight) {
			return isVisible();
		} else {
			return _component.isVisible();
		}
	}

	public Rectangle delegateGetBounds() {
		if (_heavyweight) {
			return getBounds();
		} else {
			return _component.getBounds();
		}
	}

	public void delegateRepaint() {
		if (_heavyweight) {
			repaint();
			_component.repaint();
		} else {
			_component.repaint();
		}
	}

	public JComponent getComponent() {
		return _component;
	}

	public void setComponent(JComponent component) {
		_component = component;
	}

	public boolean isHeavyweight() {
		return _heavyweight;
	}

	public void setHeavyweight(boolean heavyweight) {
		_heavyweight = heavyweight;
	}

	@Override
	public Dimension getMinimumSize() {
		return MIN_DIM;
	}
}
