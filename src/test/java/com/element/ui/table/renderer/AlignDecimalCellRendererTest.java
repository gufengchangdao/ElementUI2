package com.element.ui.table.renderer;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class AlignDecimalCellRendererTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		String[] columnNames = {"String", "Double", "ALIGN_DECIMAL"};
		Object[][] data = {
				{"aaa", 1.4142, 1.4142}, {"bbb", 98.765, 98.765},
				{"CCC", 1.73, 1.73}, {"DDD", 0d, 0d}
		};
		TableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class<?> getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};
		JTable table = new JTable(model);
		// 对最后一列应用该渲染器
		table.getColumnModel().getColumn(2).setCellRenderer(new AlignDecimalCellRenderer());
		table.setAutoCreateRowSorter(true);
		table.setRowSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.setShowVerticalLines(false);
		table.setShowHorizontalLines(false);
		table.setFocusable(false);
		return new JScrollPane(table);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AlignDecimalCellRendererTest());
		});
	}
}