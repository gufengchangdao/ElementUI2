/*
 * @(#)MouseInputListenerWrapper.java 3/19/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.swing;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

/**
 * 鼠标事件监听器集合，将多个监听器为一体
 */
class MouseInputListeners implements MouseInputListener {
	private final MouseInputListener[] _mouseInputListeners;

	public MouseInputListeners(MouseInputListener[] mouseInputListeners) {
		_mouseInputListeners = mouseInputListeners;
	}

	public void mouseClicked(MouseEvent e) {
		for (MouseInputListener mouseInputListener : _mouseInputListeners) {
			if (e.isConsumed()) {
				break;
			}
			mouseInputListener.mouseClicked(e);
		}
	}

	public void mousePressed(MouseEvent e) {
		for (MouseInputListener mouseInputListener : _mouseInputListeners) {
			if (e.isConsumed()) {
				break;
			}
			mouseInputListener.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		for (MouseInputListener mouseInputListener : _mouseInputListeners) {
			if (e.isConsumed()) {
				break;
			}
			mouseInputListener.mouseReleased(e);
		}
	}

	public void mouseEntered(MouseEvent e) {
		for (MouseInputListener mouseInputListener : _mouseInputListeners) {
			if (e.isConsumed()) {
				break;
			}
			mouseInputListener.mouseEntered(e);
		}
	}

	public void mouseExited(MouseEvent e) {
		for (MouseInputListener mouseInputListener : _mouseInputListeners) {
			if (e.isConsumed()) {
				break;
			}
			mouseInputListener.mouseExited(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		for (MouseInputListener mouseInputListener : _mouseInputListeners) {
			if (e.isConsumed()) {
				break;
			}
			mouseInputListener.mouseDragged(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		for (MouseInputListener mouseInputListener : _mouseInputListeners) {
			if (e.isConsumed()) {
				break;
			}
			mouseInputListener.mouseMoved(e);
		}
	}
}
