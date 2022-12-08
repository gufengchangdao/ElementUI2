package com.element.ui.others.card;

import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CardPanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel();

		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.add(new JButton("操作按钮"));
		buttons.add(new JButton("不操作按钮"));
		CardPanel<String> c = new CardPanel<>(
				"卡片名称",
				buttons,
				new JList<>(new String[]{"列表内容 1", "列表内容 2", "列表内容 3", "列表内容 4", "列表内容 5"}),
				true
		);
		Dimension s = c.getPreferredSize();
		s.width += 100;
		c.setPreferredSize(s);
		p.add(c);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			// LookAndFeelFactory.installJideExtension();
			showAsFrame(new CardPanelTest());
		});
	}
}