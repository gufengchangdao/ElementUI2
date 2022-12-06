package com.element.ui.list;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TransferListTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		// TransferList list = new TransferList("水果", true, "请输入水果",
		// 		List.of("苹果", "香蕉", "梨", "榴莲"));
		// TransferList list = new TransferList(List.of("1", "2", "3", "4", "5",
		// 		"6", "7", "8", "9", "10", "11", "12"));
		TransferList list = new TransferList("水果", true, "请输入数字",
				Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
		JButton button = new JButton("获取");
		button.addActionListener(e -> {
			if (list.getSearchField() != null)
				System.out.println(list.getSearchField().getText());
			if (list.getListModel() != null)
				System.out.println(list.getListModel().getSize());
			if (list.getList() != null)
				System.out.println(list.getList().getSelectedValuesList());
		});
		p.add(list);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TransferListTest());
		});
	}
}