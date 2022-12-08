package com.element.ui.others.pagination;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class PaginationListTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));
		// page.add("长文本测试");
		PaginationList list = new PaginationList(10);
		// 绘制背景
		list.getCellRenderer().setBackgroundPainted(true);

		JButton button1 = new JButton("增加5个");
		button1.addActionListener(e -> {
			// int oldCount = list.getModel().getSize();
			// list.getModel().addData(5);
			// list.firePropertyChange("visibleRowCount", oldCount, list.getModel().getSize());

			list.getModel().addData(5);
			list.updateUI();
		});
		JButton button2 = new JButton("减少5个");
		button2.addActionListener(e -> {
			list.getModel().removeData(5);
			// list.updateUI();
		});

		p.add(list);
		p.add(button1);
		p.add(button2);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new PaginationListTest());
		});
	}
}