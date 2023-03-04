package com.element.ui.table;

import com.element.plaf.LookAndFeelFactory;
import demo.AbstractDemo;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;

public class ComponentTableCellEditorRendererTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() throws Exception {
		JXTable table = new JXTable(
				new Object[][]{
						{"张三", "超能力者", 1},
						{"张三", "超能力者", 2},
						{"张三", "超能力者", 3}
				},
				new String[]{"姓名", "简介", "操作"}
		);
		// ComponentTableCellEditorRenderer editorRenderer = new ComponentTableCellEditorRenderer(
		// 		new JTextField(),
		// 		() -> {
		// 			JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
		// 			JButton b = new JButton("获取ID值");
		// 			b.addActionListener(e -> {
		// 				JOptionPane.showConfirmDialog(getFrame(), p.getClientProperty("cellEditorValue"));
		// 			});
		// 			p.add(b);
		// 			return p;
		// 		});
		// table.getColumn(2).setCellEditor(editorRenderer);
		// table.getColumn(2).setCellRenderer(editorRenderer);

		// 简化操作
		ComponentTableCellEditorRenderer editorRenderer = ComponentTableCellEditorRenderer.registerTableCellEditorRenderer(
				table, 2, () -> {
					JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
					JButton b = new JButton("获取ID值");
					b.addActionListener(e -> JOptionPane.showConfirmDialog(getFrame(),
							p.getClientProperty(ComponentTableCellEditorRenderer.CELL_EDITOR_CLIENT_KEY),
							"提示",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE));
					p.add(b);
					return p;
				});


		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(null);
		return scrollPane;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			// 加载这个皮肤需要在配置中进行下面的自定义，否则进入编辑模式时组件的首选大小会变小，因为单元格添加了padding
			// Table.focusSelectedCellHighlightBorder=0,0,0,0
			// SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ComponentTableCellEditorRendererTest());
		});
	}
}