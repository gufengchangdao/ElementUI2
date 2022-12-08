package com.element.ui.table.editor;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.base.DefaultTableModel2;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class DateSpinnerCellEditorTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		String[] columnNames = {"Integer", "String", "Date"};
		Object[][] data = {
				{-1, "AAA", new Date()}, {2, "BBB", new Date()},
				{-9, "EEE", new Date()}, {1, "", new Date()},
				{10, "CCC", new Date()}, {7, "FFF", new Date()},
		};
		DefaultTableModel2 model = new DefaultTableModel2(data, columnNames);
		model.setCellEditable(true);
		JTable table = new JTable(model) {
			@Override
			public void updateUI() {
				super.updateUI();
				setSurrendersFocusOnKeystroke(true);

				setDefaultEditor(Date.class, new DateSpinnerCellEditor("yyyy-MM-dd"));
			}
		};

		return new JScrollPane(table);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new DateSpinnerCellEditorTest());
		});
	}
}