package com.element.ui.notice.loading;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CircleLoadingLabelTest extends AbstractDemo {
	@Override
	public String getDescription() {
		return "尝试改变窗口大小试试";
	}

	@Override
	public Component getDemoPanel() throws Exception {
		CircleLoadingLabel c = new CircleLoadingLabel(ColorUtil.PRIMARY, 4);
		c.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SwingTestUtil.getFrame().getContentPane().remove(c);
				SwingTestUtil.getFrame().getContentPane().repaint();
			}
		});
		return c;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CircleLoadingLabelTest());
		});
	}
}