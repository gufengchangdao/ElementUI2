package lab.component.table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * 单元格中插入按钮
 */
public class JButtonTableExample {

	public JButtonTableExample() {
		JFrame frame = new JFrame("JButtonTable Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][]{{"button 1", "foo"},
				{"button 2", "bar"}}, new Object[]{"Button", "String"});

		JTable table = new JTable(dm);
		table.getColumn("Button").setCellRenderer(new ButtonRenderer());
		table.getColumn("Button").setCellEditor(new ButtonEditor(new JCheckBox()));


		JScrollPane scroll = new JScrollPane(table);

		table.setPreferredScrollableViewportSize(table.getPreferredSize());//thanks mKorbel +1 http://stackoverflow.com/questions/10551995/how-to-set-jscrollpane-layout-to-be-the-same-as-jtable

		table.getColumnModel().getColumn(0).setPreferredWidth(100);//so buttons will fit and not be shown butto..

		frame.add(scroll);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(JButtonTableExample::new);
	}
}

class ButtonRenderer extends JButton implements TableCellRenderer {

	public ButtonRenderer() {
		setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
	                                               boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(UIManager.getColor("Button.background"));
		}
		setText((value == null) ? "" : value.toString());
		return this;
	}
}

class ButtonEditor extends DefaultCellEditor {

	protected JButton button;
	private String label;
	private boolean isPushed;

	public ButtonEditor(JCheckBox checkBox) {
		super(checkBox);
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(e -> fireEditingStopped());
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
	                                             boolean isSelected, int row, int column) {
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		if (isPushed) {
			// 充当ActionListener的作用，不过button好像可以addActionListener()
			JOptionPane.showMessageDialog(button, label + ": Ouch!");
		}
		isPushed = false;
		return label;
	}

	@Override
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}
}