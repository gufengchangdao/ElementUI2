package com.element.ui.others.pagination;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class IconPaginationListTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		IconPaginationList list1 = new IconPaginationList(35);
		// 绘制背景
		list1.getList().getCellRenderer().setBackgroundPainted(true);
		p.add(list1, "gapleft 30");

		IconPaginationList list2 = new IconPaginationList(40);
		// 绘制背景
		list2.getList().getCellRenderer().setBackgroundPainted(true);
		p.add(list2, "gapleft 30");

		IconPaginationList list3 = new IconPaginationList(100);
		// 绘制背景
		list3.getList().getCellRenderer().setBackgroundPainted(true);
		p.add(list3, "gapleft 30");
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new IconPaginationListTest());
		});
	}
}