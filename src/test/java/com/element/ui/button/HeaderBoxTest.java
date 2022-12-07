package com.element.ui.button;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class HeaderBoxTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		HeaderBox headerBox = new HeaderBox();
		headerBox.setPreferredSize(new Dimension(100,40));
		return headerBox;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new HeaderBoxTest());
		});
	}
}