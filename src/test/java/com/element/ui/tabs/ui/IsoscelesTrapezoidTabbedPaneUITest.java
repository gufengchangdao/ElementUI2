package com.element.ui.tabs.ui;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.tabs.listener.TabTitleEditListenerTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class IsoscelesTrapezoidTabbedPaneUITest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JTabbedPane tabs = new JTabbedPane() {
			@Override
			public void updateUI() {
				super.updateUI();
				// UIManager.put("TabbedPane.highlight", Color.GRAY);
				setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
				setUI(new IsoscelesTrapezoidTabbedPaneUI());
			}
		};
		tabs.addTab("JTextArea", new JScrollPane(new JTextArea()));
		tabs.addTab("JTree", new JScrollPane(new JTree()));
		tabs.addTab("JButton", new JButton("button"));
		tabs.addTab("JSplitPane", new JSplitPane());
		tabs.setPreferredSize(new Dimension(400,300));
		return tabs;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new IsoscelesTrapezoidTabbedPaneUITest());
		});
	}
}