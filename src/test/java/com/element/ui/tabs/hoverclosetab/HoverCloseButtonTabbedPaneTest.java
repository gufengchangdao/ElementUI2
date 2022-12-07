package com.element.ui.tabs.hoverclosetab;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class HoverCloseButtonTabbedPaneTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("fill"));
		HoverCloseButtonTabbedPane tabbedPane = new HoverCloseButtonTabbedPane();
		tabbedPane.addTab("JTree", new JScrollPane(new JTree()));
		tabbedPane.addTab("JLabel", new JScrollPane(new JLabel("JLabel")));
		tabbedPane.addTab("JSplitPane", new JSplitPane());

		// 指定图标
		SvgIcon icon = SwordSvg.of(12, 12);
		icon.setColorFilter(color -> ColorUtil.PRIMARY);
		tabbedPane.addTab("宝剑在手", new JSplitPane(), icon);

		p.add(tabbedPane,"growx, growy");
		p.setPreferredSize(new Dimension(300,200));
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new HoverCloseButtonTabbedPaneTest());
		});
	}
}