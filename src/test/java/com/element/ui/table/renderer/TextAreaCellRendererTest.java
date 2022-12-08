package com.element.ui.table.renderer;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class TextAreaCellRendererTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1", "grow"));
		SwingTestUtil.setSize(400, 400);
		String[] columnNames = {"Default", "AutoWrap"};
		Object[][] data = {
				{"123456789012345678901234567890", "123456789012345678901234567890"},
				{"1111", "22222222222222222222222222222222222222222222222222222222"},
				{"3333333", "----------------------------------------------0"},
				{"4444444444444444444", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|"},
		};
		JTable table = new JTable(data, columnNames);
		table.setEnabled(false);
		table.setShowGrid(false);
		TextAreaCellRenderer renderer = new TextAreaCellRenderer();

		// table.getColumnModel().getColumn(0).setCellRenderer(renderer);
		table.getColumnModel().getColumn(1).setCellRenderer(renderer);

		// 虽然放入滚动窗格，但这里禁止滚动条出现
		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		p.add(new JLabel("拖动窗口大小看内容如何变化"));
		p.add(scroll, "growx");

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TextAreaCellRendererTest());
		});
	}
}