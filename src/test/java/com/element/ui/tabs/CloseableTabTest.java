package com.element.ui.tabs;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class CloseableTabTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JTabbedPane tb = new JTabbedPane();
		tb.add("Tab1", new JTextArea("Tab1"));
		tb.add("Tab2", new JTextArea("Tab2"));
		tb.add("Tab3", new JTextArea("Tab3"));
		tb.add("Tab4", new JTextArea("Tab4"));
		tb.add("Tab5", new JTextArea("Tab5"));
		tb.setTabComponentAt(0, new CloseableTab(tb));

		tb.setEnabledAt(1, false);
		tb.setPreferredSize(new Dimension(400, 300));
		return tb;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CloseableTabTest());
		});
	}
}