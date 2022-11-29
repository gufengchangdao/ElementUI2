package com.element.ui.label;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.list.StyledListCellRenderer;
import com.element.ui.tree.StyledTreeCellRenderer;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

public class StyledLabelTest extends AbstractDemo {

	@Override
	public String getName() {
		return "StyledLabel Demo";
	}

	@Override
	public String getDescription() {
		return "StyledLabel is an enhanced JLabel to display the text in different colors and font and can decorate with all kinds of line styles.\n" +
				"Demoed classes:\n" +
				"com.jidesoft.swing.StyledLabel";
	}

	@Override
	public Component getDemoPanel() {
		JPanel pane = new JPanel(new GridLayout(0, 1, 5, 5));
		pane.add(createLabelsPanel());
		pane.add(createTreePanel());
		pane.add(createTablePanel());
		pane.add(createListPanel());
		return pane;
	}

	private static final String SEVERE = "Severe Warnings";
	private static final String REGULAR = "Regular Warnings";
	private static final String MESSAGES = "Messages";
	private static final String ERROR_STRING = "Error";
	private static final String UNDERLINED = "Underlined";
	private static final String STRIKE_THROUGH = "Strikethrough";

	private JComponent createTreePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");
		DefaultMutableTreeNode severe = new DefaultMutableTreeNode(SEVERE + " (10 warnings)");
		severe.add(new DefaultMutableTreeNode(ERROR_STRING));
		severe.add(new DefaultMutableTreeNode(UNDERLINED));
		severe.add(new DefaultMutableTreeNode(STRIKE_THROUGH));
		root.add(severe);
		DefaultMutableTreeNode regular = new DefaultMutableTreeNode(REGULAR + " (2 warnings)");
		regular.add(new DefaultMutableTreeNode(ERROR_STRING));
		regular.add(new DefaultMutableTreeNode(UNDERLINED));
		regular.add(new DefaultMutableTreeNode(STRIKE_THROUGH));
		root.add(regular);
		DefaultMutableTreeNode messages = new DefaultMutableTreeNode(MESSAGES + " (5 messages)");
		messages.add(new DefaultMutableTreeNode(ERROR_STRING));
		messages.add(new DefaultMutableTreeNode(UNDERLINED));
		messages.add(new DefaultMutableTreeNode(STRIKE_THROUGH));
		root.add(messages);
		DefaultTreeModel model = new DefaultTreeModel(root);
		JTree tree = new JTree(model) {
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(300, 100);
			}
		};
		tree.setCellRenderer(new StyledTreeCellRenderer() {
			@Override
			protected void customizeStyledLabel(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				super.customizeStyledLabel(tree, value, sel, expanded, leaf, row, hasFocus);
				String text = getText();
				setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
				setIcon(null);
				StyledLabelTest.this.customizeStyledLabel(this, text);
			}
		});

		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		panel.add(new JScrollPane(tree));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.gray, 1, true),
						" StyledLabel used as TreeCellRenderer ",
						TitledBorder.CENTER, TitledBorder.CENTER),
				BorderFactory.createEmptyBorder(6, 4, 4, 4)));
		return panel;
	}

	private JComponent createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		DefaultTableModel model = new DefaultTableModel(0, 2) {
			private static final long serialVersionUID = 922268373343622904L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}
		};
		model.addRow(new String[]{SEVERE + " (10 warnings)", ERROR_STRING});
		model.addRow(new String[]{REGULAR + " (2 warnings)", UNDERLINED});
		model.addRow(new String[]{MESSAGES + " (5 messages)", STRIKE_THROUGH});
		final JTable table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {
			final StyledLabel l = new StyledLabel();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				if (value != null) {
					l.setText((String) value);
					StyledLabelTest.this.customizeStyledLabel(l, (String) value);
				}

				return l;
			}
		});
		panel.add(new JScrollPane(table));
		table.setPreferredScrollableViewportSize(new Dimension(300, 100));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.gray, 1, true),
						" StyledLabel used as TableCellRenderer ",
						TitledBorder.CENTER, TitledBorder.CENTER),
				BorderFactory.createEmptyBorder(6, 4, 4, 4)));
		return panel;
	}

	private JComponent createListPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		DefaultListModel<String> listModel = new DefaultListModel<>();
		listModel.addElement(SEVERE + " (10 warnings)");
		listModel.addElement(REGULAR + " (2 warnings)");
		listModel.addElement(MESSAGES + " (5 warnings)");
		listModel.addElement(ERROR_STRING);
		listModel.addElement(UNDERLINED);
		listModel.addElement(STRIKE_THROUGH);
		JList<String> list = new JList<>(listModel) {
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(300, 100);
			}
		};
		list.setCellRenderer(new StyledListCellRenderer() {
			@Override
			protected void customizeStyledLabel(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.customizeStyledLabel(list, value, index, isSelected, cellHasFocus);
				String text = getText();
				setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
				setIcon(null);
				StyledLabelTest.this.customizeStyledLabel(this, text);
			}
		});
		panel.add(new JScrollPane(list));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.gray, 1, true),
						" StyledLabel used as  ListCellRenderer ",
						TitledBorder.CENTER, TitledBorder.CENTER),
				BorderFactory.createEmptyBorder(6, 4, 4, 4)));
		return panel;
	}

	private JComponent createLabelsPanel() {
		StyledLabel javaTM = new StyledLabel("JavaTM");
		javaTM.addStyleRange(new StyleRange(4, 2, Font.PLAIN, StyleRange.STYLE_SUPERSCRIPT));

		StyledLabel co2 = new StyledLabel("CO2");
		co2.addStyleRange(new StyleRange(0, 2, Font.PLAIN, Color.BLUE));
		co2.addStyleRange(new StyleRange(2, -1, Font.PLAIN, Color.BLUE, StyleRange.STYLE_SUBSCRIPT));

		StyledLabel bold = new StyledLabel("Bold");
		bold.addStyleRange(new StyleRange(Font.BOLD, Color.BLACK));

		StyledLabel italic = new StyledLabel("Italic");
		italic.addStyleRange(new StyleRange(Font.ITALIC, Color.GRAY));

		StyledLabel error = new StyledLabel("Error");
		error.addStyleRange(new StyleRange(Font.PLAIN, Color.BLACK, StyleRange.STYLE_WAVED, Color.RED));

		StyledLabel background = new StyledLabel("Background");
		background.addStyleRange(new StyleRange(0, 4, Font.PLAIN, Color.BLACK, Color.YELLOW, StyleRange.STYLE_WAVED, Color.RED));
		background.addStyleRange(new StyleRange(4, 6, Font.PLAIN, Color.BLACK, Color.CYAN, StyleRange.STYLE_UNDERLINED, Color.BLUE));

		StyledLabel dotted = new StyledLabel("Dotted");
		dotted.addStyleRange(new StyleRange(Font.PLAIN, Color.BLACK, StyleRange.STYLE_DOTTED, Color.RED));

		StyledLabel strikethrough = new StyledLabel("Strikethrough");
		strikethrough.addStyleRange(new StyleRange(Font.PLAIN, Color.BLUE, StyleRange.STYLE_STRIKE_THROUGH, Color.BLACK));

		StyledLabel underlined = new StyledLabel("Underlined");
		underlined.addStyleRange(new StyleRange(Font.PLAIN, Color.BLUE, StyleRange.STYLE_UNDERLINED, Color.BLACK));

		StyledLabel doubleStrikethrough = new StyledLabel("Double Strikethrough");
		doubleStrikethrough.addStyleRange(new StyleRange(Font.PLAIN, Color.BLUE, StyleRange.STYLE_DOUBLE_STRIKE_THROUGH, Color.BLACK));

		StyledLabel twoStyles = new StyledLabel("Strikethrough and Underlined");
		twoStyles.addStyleRange(new StyleRange(Font.PLAIN, Color.RED, StyleRange.STYLE_STRIKE_THROUGH | StyleRange.STYLE_UNDERLINED, Color.BLACK));

		StyledLabel customizedUnderlined = new StyledLabel("Customized Underlined");
		customizedUnderlined.addStyleRange(new StyleRange(Font.PLAIN, Color.BLACK, StyleRange.STYLE_UNDERLINED, Color.BLACK,
				new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 1.0f, new float[]{6, 3, 0, 3}, 0)));

		StyledLabel mixed = new StyledLabel("Mixed Underlined Strikethrough Styles");
		mixed.addStyleRange(new StyleRange(0, 5, Font.BOLD, Color.BLUE));
		mixed.addStyleRange(new StyleRange(6, 10, Font.PLAIN, Color.BLACK, StyleRange.STYLE_UNDERLINED));
		mixed.addStyleRange(new StyleRange(17, 13, Font.PLAIN, Color.RED, StyleRange.STYLE_STRIKE_THROUGH));

		StyledLabel disabledMixed = new StyledLabel("Mixed Underlined Strikethrough (Disabled)");
		disabledMixed.addStyleRange(new StyleRange(0, 5, Font.BOLD, Color.BLUE));
		disabledMixed.addStyleRange(new StyleRange(6, 10, Font.PLAIN, Color.BLACK, StyleRange.STYLE_UNDERLINED));
		disabledMixed.setEnabled(false);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 10, 10));
		panel.add(javaTM);
		panel.add(co2);
		panel.add(bold);
		panel.add(italic);
		panel.add(error);
		panel.add(background);
		panel.add(dotted);
		panel.add(underlined);
		panel.add(strikethrough);
		panel.add(doubleStrikethrough);
		panel.add(twoStyles);
		panel.add(customizedUnderlined);
		panel.add(mixed);
		panel.add(disabledMixed);
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.gray, 1, true),
						" StyledLabel Examples ",
						TitledBorder.CENTER, TitledBorder.CENTER),
				BorderFactory.createEmptyBorder(6, 4, 4, 4)));
		return panel;
	}

	private void customizeStyledLabel(StyledLabel label, String text) {
		if (text.startsWith(SEVERE)) {
			label.addStyleRange(new StyleRange(0, SEVERE.length(), Font.BOLD, Color.RED));
			label.addStyleRange(new StyleRange(SEVERE.length(), -1, Font.ITALIC | Font.BOLD, Color.GRAY));
		} else if (text.startsWith(REGULAR)) {
			label.addStyleRange(new StyleRange(0, REGULAR.length(), Font.BOLD));
			label.addStyleRange(new StyleRange(REGULAR.length(), -1, Font.ITALIC | Font.BOLD, Color.GRAY));
		} else if (text.startsWith(MESSAGES)) {
			label.addStyleRange(new StyleRange(0, MESSAGES.length(), Font.PLAIN));
			label.addStyleRange(new StyleRange(MESSAGES.length(), -1, Font.ITALIC, Color.GRAY));
		} else if (text.startsWith(ERROR_STRING)) {
			label.addStyleRange(new StyleRange(Font.PLAIN, Color.RED, StyleRange.STYLE_WAVED, Color.RED));
		} else if (text.startsWith(UNDERLINED)) {
			label.addStyleRange(new StyleRange(Font.PLAIN, StyleRange.STYLE_UNDERLINED));
		} else if (text.startsWith(STRIKE_THROUGH)) {
			label.addStyleRange(new StyleRange(Font.PLAIN, Color.GRAY, StyleRange.STYLE_STRIKE_THROUGH, Color.RED));
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new StyledLabelTest());
		});
	}
}