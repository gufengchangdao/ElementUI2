package com.element.ui.area;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.layout.JideBoxLayout;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class AutoResizingTextAreaTest extends AbstractDemo {

	@Override
	public String getName() {
		return "Autoresizing TextArea Demo";
	}

	@Override
	public String getDescription() {
		return """
				AutoResizingTextArea is a special text area which automatically resizes itself vertically. This is an\s
				 ideal candidate when you want to show a smaller text area initially. When user starts to type in a \s
				 lot of text, it automatically increases so that more text can be read.

				You can set minimum rows and maximum rows allowed.\s

				Demoed classes:
				com.jidesoft.swing.AutoResizingTextArea""";
	}

	public Component getDemoPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 400));
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS));
		panel.add(new JScrollPane(new AutoResizingTextArea("在此处输入新行以查看文本区域自动增长“+”。" +
				"最少 2 行，最多 10 行", 2, 10, 20)));
		panel.add(Box.createGlue(), JideBoxLayout.VARY);
		panel.add(new JScrollPane(new JTextArea("普通文本域初始就是20列，固定不变", 10, 20)));
		return panel;
	}

	static public void main(String[] s) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AutoResizingTextAreaTest());
		});
	}
}