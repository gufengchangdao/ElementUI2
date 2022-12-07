package com.element.plaf;

import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class LookAndFeelFactoryTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		return new JButton("按钮");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			// LookAndFeelFactory.registerDefaultInitializer("com.formdev.flatlaf.FlatLightLaf",);

			showAsFrame(new LookAndFeelFactoryTest());
		});
	}
}

class Init{

}