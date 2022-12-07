package com.element.ui.table.renderer;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.base.BaseTable;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class ETableCellRendererTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() {
		String[][] s1 = {{"张三", "12346", "12"}, {"李四", "234561", "18"}, {"王五", "34561", "22"},
				{"王五", "34561", "22"}, {"王五", "34561", "22"}, {"王五", "34561", "22"},
				{"王五", "34561", "22"}};
		String[] s2 = {"姓名", "学号", "年龄"};
		DefaultTableModel tableModel = new DefaultTableModel(s1, s2);

		ETable table = new ETable(tableModel);
		ETableCellRenderer renderer = table.getCellRenderer();
		renderer.setContainBorder(true);
		renderer.setContainStripe(true);

		renderer.addState(1, ColorUtil.PRIMARY);
		renderer.addState(2, ColorUtil.DANGER);
		renderer.addState(4, ColorUtil.WARNING);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(null);

		return scrollPane;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ETableCellRendererTest());
		});
	}
}

class ETable extends BaseTable {
	private final ETableCellRenderer cellRenderer = new ETableCellRenderer();

	public ETable(TableModel dm) {
		super(dm);
		init();
	}

	private void init() {
		getColumns()
				.forEach(tableColumn -> {
					tableColumn.setCellRenderer(cellRenderer);
					tableColumn.setCellEditor(cellEditor);
				});

		// DefaultTableColumnModel model = new DefaultTableColumnModel();
		// model.addColumn(new TableColumn());
		// JXTableHeader header = new JXTableHeader(model);
		// setTableHeader(header);
	}

	public ETableCellRenderer getCellRenderer() {
		return cellRenderer;
	}
}