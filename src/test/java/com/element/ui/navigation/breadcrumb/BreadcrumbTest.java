package com.element.ui.navigation.breadcrumb;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.svg.icon.regular.ArrowFatRightSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BreadcrumbTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));
		p.setPreferredSize(new Dimension(400,300));
		ArrayList<String> list = new ArrayList<>();
		list.add("首页");
		list.add("活动管理");
		list.add("活动列表");
		list.add("活动详情");
		Breadcrumb breadcrumb = new Breadcrumb(
				list, 2,
				// "/"
				ArrowFatRightSvg.of(16, 16)
		);
		JButton b1 = new JButton("添加");
		b1.addActionListener(e -> {
			breadcrumb.addItem("新页面");
			SwingTestUtil.getFrame().getContentPane().validate();
		});
		JButton b2 = new JButton("移除新页面");
		b2.addActionListener(e -> {
			breadcrumb.removeItem("新页面");
			SwingTestUtil.getFrame().getContentPane().validate();
		});
		JButton b3 = new JButton("移除第一个标签");
		b3.addActionListener(e -> {
			breadcrumb.removeItemAt(0);
			SwingTestUtil.getFrame().getContentPane().validate();
		});
		JButton b4 = new JButton("移除最后一个标签");
		b4.addActionListener(e -> {
			breadcrumb.removeLastItem();
			SwingTestUtil.getFrame().getContentPane().validate();
		});

		p.add(breadcrumb);
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new BreadcrumbTest());
		});
	}
}