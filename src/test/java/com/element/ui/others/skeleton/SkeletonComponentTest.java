package com.element.ui.others.skeleton;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class SkeletonComponentTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		SkeletonComponent c = new SkeletonComponent(new Dimension(100, 30));

		return c;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new SkeletonComponentTest());
		});
	}
}