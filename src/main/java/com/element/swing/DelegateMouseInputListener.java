/*
 * @(#)DelegateMouseInputListener.java 10/20/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.element.swing;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

/**
 * 此鼠标输入侦听器可以将鼠标事件委托给另一个鼠标输入侦听器。
 * 当您需要在有鼠标事件时执行一些额外的步骤但您仍然希望在特定条件下调用原始鼠标输入侦听器时，可以使用它。
 */
public class DelegateMouseInputListener implements MouseInputListener {
	private MouseInputListener _listener;

	public DelegateMouseInputListener(MouseInputListener listener) {
		_listener = listener;
	}

	public void mouseClicked(MouseEvent e) {
		if (_listener != null) {
			_listener.mouseClicked(e);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (_listener != null) {
			_listener.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (_listener != null) {
			_listener.mouseReleased(e);
		}
	}

	public void mouseEntered(MouseEvent e) {
		if (_listener != null) {
			_listener.mouseEntered(e);
		}
	}

	public void mouseExited(MouseEvent e) {
		if (_listener != null) {
			_listener.mouseExited(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (_listener != null) {
			_listener.mouseDragged(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (_listener != null) {
			_listener.mouseMoved(e);
		}
	}
}
