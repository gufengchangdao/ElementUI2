package com.element.swing.base;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.SwingPosition;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class AngleComponentTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JLabel label = new JLabel("这是文本");
		label.setPreferredSize(new Dimension(200, 200));
		label.setOpaque(true);
		label.setBackground(ColorUtil.SUCCESS.brighter());

		return new AngleComponent(label, SwingPosition.WEST,
				label.getBackground(), 20, 3, new Point(0, 0));
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AngleComponentTest());
		});
	}
}