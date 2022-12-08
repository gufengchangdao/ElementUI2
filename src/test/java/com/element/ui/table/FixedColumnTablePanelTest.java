package com.element.ui.table;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.base.DefaultTableModel2;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.stream.IntStream;

public class FixedColumnTablePanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel();
		String ES = "";
		Object[][] data = {
				{1, 11, "A", ES, ES, ES, ES, ES},
				{2, 22, ES, "B", ES, ES, ES, ES},
				{3, 33, ES, ES, "C", ES, ES, ES},
				{4, 1, ES, ES, ES, "D", ES, ES},
				{5, 55, ES, ES, ES, ES, "E", ES},
				{6, 66, ES, ES, ES, ES, ES, "F"}
		};
		String[] columnNames = {"fixed 1", "fixed 2", "A", "B", "C", "D", "E", "F"};
		DefaultTableModel model = new DefaultTableModel2(data, columnNames);

		JTable table = new JTable(model);
		FixedColumnTablePanel panel = new FixedColumnTablePanel(table, 2);

		JButton addButton = new JButton("add");
		addButton.addActionListener(e -> {
			IntStream.range(0, 100)
					.mapToObj(i -> new Object[]{i, i + 1, "A" + i, "B" + i})
					.forEach(model::addRow);
		});
		p.add(panel, "wrap");
		p.add(addButton);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new FixedColumnTablePanelTest());
		});
	}
}