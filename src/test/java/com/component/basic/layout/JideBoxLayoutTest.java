package com.component.basic.layout;

import com.component.util.SwingTestUtil;
import com.jidesoft.swing.JideBoxLayout;

import javax.swing.*;
import java.awt.*;

public class JideBoxLayoutTest {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JPanel p = SwingTestUtil.init(new BorderLayout());
			JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			splitPane.setTopComponent(useBoxLayout());
			splitPane.setBottomComponent(useJideBoxLayout());
			p.add(splitPane);
			SwingTestUtil.test();
		});
	}

	public static JPanel useBoxLayout() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JButton button = new JButton("FIX");
		button.setPreferredSize(new Dimension(60, 60));
		panel.add(button);

		button = new JButton("FLEX");
		button.setPreferredSize(new Dimension(120, 60));
		panel.add(button);

		button = new JButton("VARY");
		button.setPreferredSize(new Dimension(120, 60));
		panel.add(button);
		panel.setBorder(BorderFactory.createLineBorder(Color.RED));
		return panel;
	}

	public static JPanel useJideBoxLayout() {
		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, 0, 6));

		JButton button = new JButton("FIX");
		button.setPreferredSize(new Dimension(60, 60));
		panel.add(button, JideBoxLayout.FIX);

		button = new JButton("FLEX");
		button.setPreferredSize(new Dimension(120, 60));
		panel.add(button, JideBoxLayout.FLEXIBLE);

		button = new JButton("VARY");
		button.setPreferredSize(new Dimension(120, 60));
		panel.add(button, JideBoxLayout.VARY);
		panel.setBorder(BorderFactory.createLineBorder(Color.RED));
		return panel;
	}
}