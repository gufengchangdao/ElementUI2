package com.element.swing.base;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.svg.icon.regular.XCircleSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class BackgroundComponentTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		BackgroundComponent c1 = new BackgroundComponent(XCircleSvg.of(100, 100));
		c1.setPreferredSize(new Dimension(100, 100));
		c1.setBorder(BorderFactory.createLineBorder(Color.RED));
		c1.setPreferredSize(new Dimension(200, 200));

		return c1;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new BackgroundComponentTest());
		});
	}
}