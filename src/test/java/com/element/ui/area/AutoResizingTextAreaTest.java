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
		panel.add(new JScrollPane(new AutoResizingTextArea("Typing in new line here to see the text area growing " +
				"automatically. \nMinimum 2 rows and maximum 10 rows", 2, 10, 20)));
		panel.add(Box.createGlue(), JideBoxLayout.VARY);
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