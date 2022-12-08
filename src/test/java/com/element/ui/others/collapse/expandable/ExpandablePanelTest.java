package com.element.ui.others.collapse.expandable;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.notice.message.SystemMessageFactoryTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ExpandablePanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {

		JButton b1 = new JButton("面板一");
		JButton b2 = new JButton("面板二");
		JButton b3 = new JButton("面板三");

		JPanel p1 = new JPanel();
		p1.add(new JButton("艾希"));
		JPanel p2 = new JPanel();
		p2.add(new JTree());
		JPanel p3 = new JPanel();
		p3.add(new JTextField("输入框"));
		ExpandablePanel expandablePanel = new ExpandablePanel(
				Arrays.asList(b1, b2, b3),
				Arrays.asList(p1, p2, p3)
		);
		expandablePanel.setPreferredSize(new Dimension(300,400));
		return expandablePanel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ExpandablePanelTest());
		});
	}
}