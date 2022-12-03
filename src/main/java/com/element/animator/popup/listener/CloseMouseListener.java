package com.element.animator.popup.listener;

import com.element.animator.popup.PopupAnimatorTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CloseMouseListener implements MouseListener {
	private final JComponent c;
	private final PopupAnimatorTask<?> task;

	public CloseMouseListener(JComponent c, PopupAnimatorTask<?> task) {
		this.c = c;
		this.task = task;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (task != null) {
			task.startFadeOutAnimator();
		} else {
			Container container = c.getParent();
			if (container == null) return;
			container.remove(c);
			Dimension size = c.getPreferredSize();
			container.repaint(c.getX(), c.getY(), size.width, size.height);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
