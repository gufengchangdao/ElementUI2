package com.element.ui.button;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneOverviewTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		JTextArea area = new JTextArea(DemoData.LONG_TEXT);
		JScrollPane scrollPane = new JScrollPane(area);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		JButton b = new JButton("预览");
		ScrollPaneOverview scrollPaneOverview = new ScrollPaneOverview(scrollPane, b);
		b.addActionListener(e -> {
			scrollPaneOverview.display();
		});
		p.add(scrollPane);
		p.add(b);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ScrollPaneOverviewTest());
		});
	}
}