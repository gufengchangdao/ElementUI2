package com.element.ui.button;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class CornerScrollerTest extends AbstractDemo {
	@Override
	public String getDescription() {
		return "CornerScroller提供一个滚动窗格内容的缩略图效果，并且根据鼠标选择位置改变滚动窗格视口内容，跳转到点击的位置";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		JTextArea area = new JTextArea(DemoData.LONG_TEXT);
		JScrollPane scrollPane = new JScrollPane(area);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		CornerScroller b = new CornerScroller(scrollPane);
		p.add(scrollPane);
		p.add(b, "top");

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CornerScrollerTest());
		});
	}
}