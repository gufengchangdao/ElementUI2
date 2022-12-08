package com.element.swing;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class AutoRepeatHandlerTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel();
		JTextField field = new JTextField("0");
		AutoRepeatHandler autoRepeatHandler = new AutoRepeatHandler(1, field);
		JButton b1 = new JButton(String.format("%+d", 1));
		b1.addMouseListener(autoRepeatHandler);
		b1.addActionListener(autoRepeatHandler);

		autoRepeatHandler = new AutoRepeatHandler(-1, field);
		JButton b2 = new JButton(String.format("%+d", -1));
		b2.addMouseListener(autoRepeatHandler);
		b2.addActionListener(autoRepeatHandler);
		p.add(field);
		p.add(b1);
		p.add(b2);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AutoRepeatHandlerTest());
		});
	}
}