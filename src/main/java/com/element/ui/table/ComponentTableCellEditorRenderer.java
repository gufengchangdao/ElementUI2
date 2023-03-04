package com.element.ui.table;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.function.Supplier;

/**
 * 表格单元格渲染器和编辑器，允许将组件添加到单元格中，一般是放一个包装后的面板。
 * <p>
 * 如果要获取单元格的值，可以调用 createCell 中 返回组件的getClientProperty()，例如：
 * <pre>
 * () -> {
 *  JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
 *  JButton b = new JButton("获取ID值");
 *  b.addActionListener(e -> {
 *  JOptionPane.showConfirmDialog(getFrame(), p.getClientProperty(ComponentTableCellEditorRenderer.CELL_EDITOR_CLIENT_KEY));
 * }
 * </pre>
 */
public class ComponentTableCellEditorRenderer extends AbstractCellEditor
		implements TableCellRenderer, TableCellEditor {
	private final Supplier<JComponent> createCell;
	private JComponent displayCom;
	private JComponent editCom;
	public static final String CELL_EDITOR_CLIENT_KEY = "cellEditorValue";

	/**
	 * 为表格指定列注册组件渲染器和编辑器。
	 * 负责为表格设置渲染器和编辑器，并且为表格设置合适的行高和列高以容纳添加的组件
	 *
	 * @param table      表格
	 * @param col        设置组件的列
	 * @param createCell 单元格显示的组件，注意不要使用成员变量，组件及其子组件都应该是临时创建的
	 */
	public static ComponentTableCellEditorRenderer
	registerTableCellEditorRenderer(JTable table, int col, Supplier<JComponent> createCell) {
		ComponentTableCellEditorRenderer editorRenderer = new ComponentTableCellEditorRenderer(createCell);

		// 设置
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(col).setCellRenderer(editorRenderer);
		columnModel.getColumn(col).setCellEditor(editorRenderer);

		// 为组件设置单元格的首选大小
		int rowHeight = table.getRowHeight();
		Dimension size = editorRenderer.displayCom.getPreferredSize();
		if (size.height > rowHeight)
			table.setRowHeight(size.height);
		columnModel.getColumn(col).setPreferredWidth(size.width);

		return editorRenderer;
	}

	public ComponentTableCellEditorRenderer(Supplier<JComponent> createCell) {
		this.createCell = createCell;
		init();
	}

	private void init() {
		displayCom = createCell.get();
		editCom = createCell.get();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (editCom instanceof JPanel && editCom.isOpaque())
			editCom.setBackground(table.getSelectionBackground());
		editCom.putClientProperty(CELL_EDITOR_CLIENT_KEY, value);
		return editCom;
	}

	@Override
	public Object getCellEditorValue() {
		return editCom.getClientProperty(CELL_EDITOR_CLIENT_KEY);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (displayCom instanceof JPanel && displayCom.isOpaque())
			displayCom.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
		return displayCom;
	}
}
