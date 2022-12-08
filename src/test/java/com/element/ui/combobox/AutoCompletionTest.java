package com.element.ui.combobox;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.search.ListSearchable;
import com.element.swing.search.TreeSearchable;
import com.element.ui.border.PartialEtchedBorder;
import com.element.ui.border.PartialSide;
import com.element.ui.layout.JideBoxLayout;
import com.element.util.SelectAllUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class AutoCompletionTest extends AbstractDemo {
	protected String[] _fontNames;
	protected List<String> _fontList;

	@Override
	public String getName() {
		return "AutoCompletion Demo";
	}

	@Override
	public String getDescription() {
		return """
				This is a demo of several AutoCompletion components.\s

				Demoed classes:
				com.jidesoft.swing.AutoCompletion
				com.jidesoft.swing.AutoCompletionComboBox""";
	}

	public Component getDemoPanel() {
		_fontNames = new String[]{
				"Agency FB", "Aharoni", "Algerian", "Andalus", "Angsana New", "AngsanaUPC",
				"Aparajita", "Arabic Typesetting", "Arial", "Arial Black", "Arial Narrow", "Arial Rounded MT Bold",
				"Arial Unicode MS"
		};
		_fontList = Arrays.asList(_fontNames);

		JPanel panel1 = createPanel1();
		JPanel panel2 = createPanel2();

		JPanel panel = new JPanel(new BorderLayout(6, 6));
		panel.add(panel1, BorderLayout.BEFORE_FIRST_LINE);
		panel.add(panel2);
		return panel;
	}

	private JPanel createPanel1() {
		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH),
						"AutoCompletion combo box and text field", TitledBorder.LEADING, TitledBorder.ABOVE_TOP),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		JComboBox<String> autoCompletionComboBox = new AutoCompletionComboBox<>(_fontNames);
		autoCompletionComboBox.setName("AutoCompletion JComboBox (Strict)");
		autoCompletionComboBox.setToolTipText("AutoCompletion JComboBox (Strict)");
		panel.add(new JLabel("AutoCompletion JComboBox (Strict)"));
		panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
		panel.add(autoCompletionComboBox);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		AutoCompletionComboBox<String> autoCompletionComboBoxNotStrict = new AutoCompletionComboBox<>(_fontNames);
		autoCompletionComboBoxNotStrict.setStrict(false);
		autoCompletionComboBoxNotStrict.setName("AutoCompletion JComboBox (Not strict)");
		autoCompletionComboBoxNotStrict.setToolTipText("AutoCompletion JComboBox (Not strict)");
		panel.add(new JLabel("AutoCompletion JComboBox (Not strict)"));
		panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
		panel.add(autoCompletionComboBoxNotStrict);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		// create tree combobox
		final JTextField textField = new JTextField();
		textField.setName("AutoCompletion JTextField with a hidden data");
		SelectAllUtil.install(textField);
		new AutoCompletion(textField, _fontList);
		panel.add(new JLabel("AutoCompletion JTextField with a hidden data"));
		panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
		panel.add(textField);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		return panel;
	}

	private JPanel createPanel2() {
		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH),
						"AutoCompletion with list and tree", TitledBorder.LEADING, TitledBorder.ABOVE_TOP),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		// create tree combobox
		final JTextField treeTextField = new JTextField();
		treeTextField.setName("AutoCompletion JTextField with JTree");
		SelectAllUtil.install(treeTextField);
		final JTree tree = new JTree();
		tree.setVisibleRowCount(10);
		final TreeSearchable searchable = new TreeSearchable(tree);
		searchable.setRecursive(true);
		new AutoCompletion(treeTextField, searchable);
		panel.add(new JLabel("AutoCompletion JTextField with JTree"));
		panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
		panel.add(treeTextField);
		panel.add(Box.createVerticalStrut(2), JideBoxLayout.FIX);
		panel.add(new JScrollPane(tree));
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		// create font name combobox
		final JTextField fontNameTextField = new JTextField();
		fontNameTextField.setName("AutoCompletion JTextField with JList");
		SelectAllUtil.install(fontNameTextField);
		final JList<String> fontNameList = new JList<>(_fontNames);
		fontNameList.setVisibleRowCount(10);
		new AutoCompletion(fontNameTextField, new ListSearchable<>(fontNameList));
		panel.add(new JLabel("AutoCompletion JTextField with JList"));
		panel.add(Box.createVerticalStrut(3), JideBoxLayout.FIX);
		panel.add(fontNameTextField);
		panel.add(Box.createVerticalStrut(2), JideBoxLayout.FIX);
		panel.add(new JScrollPane(fontNameList));
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AutoCompletionTest());
		});
	}
}