package com.element.swing.base;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * 基础表格模型
 *
 * <ul>
 *     <li>重写了{@link #getColumnClass(int)}，优先以第一行数据的类型作为列类型，通过类型的判断，可识别icon、boolean或者date等常用类型</li>
 *     <li>这里加了一个开关，cellEditable，用来判断所有单元格是否可编辑，不过一般情况下也许只是部分单元格可编辑，重写{@link #isCellEditable(int, int)}即可</li>
 * </ul>
 */
public class DefaultTableModel2 extends DefaultTableModel {
	/** 所有单元格是否可以编辑 */
	private boolean cellEditable = false;

	public DefaultTableModel2() {
	}

	public DefaultTableModel2(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}

	public DefaultTableModel2(Vector columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public DefaultTableModel2(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	public DefaultTableModel2(Vector data, Vector columnNames) {
		super(data, columnNames);
	}

	public DefaultTableModel2(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}

	/**
	 * 这里优先选取第一行数据判断类型，但是如果不存在行数据就返回Object.class
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		if (getRowCount() == 0) return Object.class;
		Object o = getValueAt(0, column);
		if (o == null) return Object.class;
		return o.getClass();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return cellEditable;
	}

	public boolean isCellEditable() {
		return cellEditable;
	}

	public void setCellEditable(boolean cellEditable) {
		this.cellEditable = cellEditable;
	}
}
