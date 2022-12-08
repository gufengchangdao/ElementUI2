package com.element.ui.tabs.listener;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.tabs.hoverclosetab.HoverCloseButtonTabbedPaneTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class TabTitleEditListenerTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		String help = String.join("\n",
				" Start editing: Double-Click, Enter-Key",
				" Commit rename: field-focusLost, Enter-Key",
				"Cancel editing: Esc-Key, title.isEmpty"
		);
		JTabbedPane tabs = new JTabbedPane() {
			private transient TabTitleEditListener listener;

			@Override
			public void updateUI() {
				removeChangeListener(listener);
				removeMouseListener(listener);
				super.updateUI();
				listener = new TabTitleEditListener(this);
				addChangeListener(listener);
				addMouseListener(listener);
			}
		};
		tabs.addTab("Shortcuts", new JTextArea(help));
		tabs.addTab("JLabel", new JLabel("JLabel"));
		tabs.addTab("JTree", new JScrollPane(new JTree()));
		tabs.addTab("JSplitPane", new JSplitPane());
		tabs.setPreferredSize(new Dimension(400,300));
		return tabs;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TabTitleEditListenerTest());
		});
	}
}