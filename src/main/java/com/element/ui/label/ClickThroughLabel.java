/*
 * @(#)ClickThroughLabel.java 2/28/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.label;

import com.element.util.ListenerUtil;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * lickThroughLabel是一个特殊的 JLabel，它将所有鼠标事件重新定位到指定的目标组件。
 * <p>
 * 例如，您需要在 JComponent 上绘制一些文本。通常你可以调用 Java2D 的 paint text 方法来绘制文本。然而，另一种方法是将 JLabel 添加到
 * JComponent，JLabel 不仅会绘制文本，还会绘制一个更好的可选图标。但是，如果您将鼠标侦听器添加到 JComponent，则当鼠标单击 JLabel 时，鼠
 * 标侦听器将不会接收任何鼠标事件。通过使用此ClickThroughLabel ，鼠标事件将传递到底层 JComponent。
 * <p>
 * 请注意，我们没有传递所有鼠标事件。在大多数情况下，通过 MOUSE_EXITED 和 MOUSE_ENTERED 是没有意义的。但是有些情况下，例如当 JLabel
 * 位于 JComponent 的边界时，您可能期望 JComponent 上发生 MOUSE_ENTERED 事件，但它不会发生。所以请注意这些情况，这样您就不会依赖它来
 * 做出代码中的重要决定。
 */
public class ClickThroughLabel extends JLabel implements MouseInputListener {
	private Component _target;

	public ClickThroughLabel() {
		installListeners();
	}

	public ClickThroughLabel(Icon image) {
		super(image);
		installListeners();
	}

	public ClickThroughLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		installListeners();
	}

	public ClickThroughLabel(String text) {
		super(text);
		installListeners();
	}

	public ClickThroughLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		installListeners();
	}

	public ClickThroughLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		installListeners();
	}

	public Component getTarget() {
		return _target;
	}

	public void setTarget(Component target) {
		_target = target;
	}

	protected void installListeners() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	protected void uninstallListeners() {
		removeMouseListener(this);
		removeMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		ListenerUtil.retargetMouseEvent(e.getID(), e, getTarget());
	}

	public void mousePressed(MouseEvent e) {
		ListenerUtil.retargetMouseEvent(e.getID(), e, getTarget());
	}

	public void mouseReleased(MouseEvent e) {
		ListenerUtil.retargetMouseEvent(e.getID(), e, getTarget());
	}

	public void mouseEntered(MouseEvent e) {
		ListenerUtil.retargetMouseEvent(e.getID(), e, getTarget());
	}

	public void mouseExited(MouseEvent e) {
		ListenerUtil.retargetMouseEvent(e.getID(), e, getTarget());
	}

	public void mouseDragged(MouseEvent e) {
		ListenerUtil.retargetMouseEvent(e.getID(), e, getTarget());
	}

	public void mouseMoved(MouseEvent e) {
		ListenerUtil.retargetMouseEvent(e.getID(), e, getTarget());
	}
}
