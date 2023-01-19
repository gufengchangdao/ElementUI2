package com.element.ui.table.groupable;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class GroupableTableTest extends AbstractDemo {
	@Override
	public String getDescription() {
		return "支持行分组的表格";
	}

	@Override
	public Component getDemoPanel() throws Exception {
		JTable table = new JTable();
		table.setAutoCreateColumnsFromModel(false);

		DefaultTableModel tm = new DefaultTableModel(0, 10);

		for (int index = 0; index < 10; index++) {
			Object[] row = new Object[10];
			for (int col = 0; col < 10; col++) {
				row[col] = index + "x" + col;
			}
			tm.addRow(row);
		}
		table.setModel(tm);

		GroupableColumnModel model = new GroupableColumnModel();
		model.addColumn(createColumn("A", 0));
		model.addColumn(createColumn("B", 1));
		model.addColumn(createColumn("C", 2));
		model.addColumn(createColumn("D", 3));
		model.addColumn(createColumn("E", 4));
		model.addColumn(createColumn("F", 5));
		model.addColumn(createColumn("G", 6));
		model.addColumn(createColumn("H", 7));
		model.addColumn(createColumn("I", 8));
		model.addColumn(createColumn("J", 9));

		GroupableColumnModel.IColumnGroup groupA = model.addGroup("Test 01");
		groupA.addColumn(model.getColumn(model.getColumnIndex("A")));
		groupA.addColumn(model.getColumn(model.getColumnIndex("B")));
		groupA.addColumn(model.getColumn(model.getColumnIndex("C")));
		groupA.addColumn(model.getColumn(model.getColumnIndex("D")));

		GroupableColumnModel.IColumnGroup groupB = model.addGroup("Test 02");
		groupB.addColumn(model.getColumn(model.getColumnIndex("F")));
		groupB.addColumn(model.getColumn(model.getColumnIndex("G")));
		groupB.addColumn(model.getColumn(model.getColumnIndex("H")));
		groupB.addColumn(model.getColumn(model.getColumnIndex("I")));

		table.setColumnModel(model);
		table.setTableHeader(new GroupableTableHeader(table.getColumnModel()));

		table.setAutoCreateRowSorter(true);

		return new JScrollPane(table);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new GroupableTableTest());
		});
	}

	protected static TableColumn createColumn(String title, int modelIndex) {
		TableColumn column = new TableColumn();
		column.setHeaderValue(title);
		column.setIdentifier(title);
		column.setModelIndex(modelIndex);
		return column;
	}
}