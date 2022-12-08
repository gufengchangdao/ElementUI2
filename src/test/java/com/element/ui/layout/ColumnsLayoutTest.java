package com.element.ui.layout;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.button.RoundButtonTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class ColumnsLayoutTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		ColumnsLayout layout = new ColumnsLayout(600, 5, 5);
		JPanel panel = new JPanel(layout);
		panel.add(new JButton(" "), (Integer) 24);
		panel.add(new JButton(" "), (Integer) 12);
		panel.add(new JButton(" "), (Integer) 12);
		panel.add(new JButton(" "), (Integer) 8);
		panel.add(new JButton(" "), (Integer) 8);
		panel.add(new JButton(" "), (Integer) 8);
		panel.add(new JButton(" "), (Integer) 6);
		panel.add(new JButton(" "), (Integer) 6);
		panel.add(new JButton(" "), (Integer) 6);
		panel.add(new JButton(" "), (Integer) 6);
		panel.add(new JButton(" "), (Integer) 4);
		panel.add(new JButton(" "), (Integer) 4);
		panel.add(new JButton(" "), (Integer) 4);
		panel.add(new JButton(" "), (Integer) 4);
		panel.add(new JButton(" "), (Integer) 4);
		panel.add(new JButton(" "), (Integer) 4);
		panel.setBorder(BorderFactory.createLineBorder(ColorUtil.INFO));

		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ColumnsLayoutTest());
		});
	}
}