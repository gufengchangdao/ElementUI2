package com.element.util;

import com.element.ui.dialog.ButtonPanel;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class ArrowKeyNavigationSupportTest extends AbstractDemo {
	@Override
	public String getName() {
		return "ArrowKeyNavigationSupportTest";
	}

	@Override
	public Component getDemoPanel() {
		ButtonPanel demoPanel = new ButtonPanel();
		demoPanel.addButton(new JButton("按钮一"));
		demoPanel.addButton(new JButton("按钮二"));
		demoPanel.addButton(new JButton("按钮三"));
		demoPanel.addButton(new JButton("按钮四"));
		demoPanel.addButton(new JButton("按钮五"));
		new ArrowKeyNavigationSupport().install(demoPanel);

		return demoPanel;
	}

	@Override
	public String getDescription() {
		return "其中一个按钮获取到焦点后，可以使用箭头按键切换焦点所在组件";
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			showAsFrame(new ArrowKeyNavigationSupportTest());
		});
	}
}