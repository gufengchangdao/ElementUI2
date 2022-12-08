package com.element.ui.field.filter;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.area.LineNumberViewTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Document;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class VerifierTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel();

		JTextField field1 = new JTextField();
		Document doc1 = field1.getDocument();
		if (doc1 instanceof AbstractDocument) {
			((AbstractDocument) doc1).setDocumentFilter(new IntegerDocumentFilter());
		}

		JTextField field2 = new JTextField();
		field2.setInputVerifier(new IntegerInputVerifier(0, Long.MAX_VALUE));

		JFormattedTextField field3 = new JFormattedTextField();
		field3.setFormatterFactory(new NumberFormatterFactory());

		String[] columnNames = {"Default", "DocumentFilter", "InputVerifier", "JFormattedTextField"};
		TableModel model = new DefaultTableModel(columnNames, 10) {
			@Override
			public Class<?> getColumnClass(int column) {
				return Integer.class; //也是一种方式
			}
		};
		JTable table = new JTable(model) {
			@Override
			public Component prepareEditor(TableCellEditor editor, int row, int column) {
				Component c = super.prepareEditor(editor, row, column);
				if (c instanceof JComponent) {
					((JComponent) c).setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				}
				return c;
			}
		};
		table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(field1));
		table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(field2) {
			// 是否允许写入
			@Override
			public boolean stopCellEditing() {
				JComponent editor = (JComponent) getComponent();
				boolean isEditValid = editor.getInputVerifier().verify(editor);
				editor.setBorder(isEditValid ? BorderFactory.createEmptyBorder(1, 1, 1, 1)
						: BorderFactory.createLineBorder(Color.RED));
				return isEditValid && super.stopCellEditing();
			}
		});
		table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(field3) {
			@Override
			public boolean stopCellEditing() {
				JFormattedTextField editor = (JFormattedTextField) getComponent();
				boolean isEditValid = editor.isEditValid();
				editor.setBorder(isEditValid ? BorderFactory.createEmptyBorder(1, 1, 1, 1)
						: BorderFactory.createLineBorder(Color.RED));
				return isEditValid && super.stopCellEditing();
			}
		});

		p.add(new JScrollPane(table));

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new VerifierTest());
		});
	}
}

class NumberFormatterFactory extends DefaultFormatterFactory {
	private static final NumberFormatter FORMATTER = new NumberFormatter();

	static {
		FORMATTER.setValueClass(Integer.class);
		((NumberFormat) FORMATTER.getFormat()).setGroupingUsed(false);
	}

	protected NumberFormatterFactory() {
		super(FORMATTER, FORMATTER, FORMATTER);
	}
}