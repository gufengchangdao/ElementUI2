package com.element.ui.table.renderer;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.base.DefaultTableModel2;
import com.element.ui.table.listener.DragScrollingListenerTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

import static org.junit.Assert.*;

public class TableHeaderPopupRendererTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		String[] columnNames = {"Boolean", "Integer", "String"};
		Object[][] data = {
				{true, 1, "BBB"}, {false, 12, "AAA"}, {true, 2, "DDD"}, {false, 5, "CCC"},
				{true, 3, "EEE"}, {false, 6, "GGG"}, {true, 4, "FFF"}, {false, 7, "HHH"}
		};
		DefaultTableModel2 model = new DefaultTableModel2(data, columnNames);
		model.setCellEditable(true);

		JTable table = new JTable(model) {
			// 复选框单元格选中时，单元格的背景色变化会慢与所在行其他单元格，这里进行设置
			@Override
			public Component prepareEditor(TableCellEditor editor, int row, int column) {
				Component c = super.prepareEditor(editor, row, column);
				if (c instanceof JCheckBox b) {
					b.setBackground(getSelectionBackground());
					b.setBorderPainted(true);
				}
				return c;
			}
		};

		JPopupMenu pop = new JPopupMenu();
		pop.add("000");
		pop.add("11111");
		pop.add("2222222");

		TableHeaderPopupRenderer r = new TableHeaderPopupRenderer(table.getTableHeader(), pop);
		table.getColumnModel().getColumn(0).setHeaderRenderer(r);
		table.getColumnModel().getColumn(1).setHeaderRenderer(r);
		table.getColumnModel().getColumn(2).setHeaderRenderer(r);

		return new JScrollPane(table);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TableHeaderPopupRendererTest());
		});
	}
}