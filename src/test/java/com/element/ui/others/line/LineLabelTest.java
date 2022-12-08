package com.element.ui.others.line;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import java.awt.*;

public class LineLabelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		LineLabel lineLabel = new LineLabel(3);
		lineLabel.setForeground(ColorUtil.PRIMARY);
		lineLabel.setPreferredSize(new Dimension(200, 200));

		return lineLabel;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new LineLabelTest());
		});
	}
}