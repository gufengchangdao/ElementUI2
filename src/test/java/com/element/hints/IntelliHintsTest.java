package com.element.hints;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.hints.AbstractListIntelliHints;
import com.element.swing.hints.FileIntelliHints;
import com.element.swing.hints.ListDataIntelliHints;
import com.element.ui.layout.JideBoxLayout;
import com.element.util.SelectAllUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.util.List;
import java.util.Vector;


public class IntelliHintsTest extends AbstractDemo {
	private boolean _applyFileFilter;

	@Override
	public String getName() {
		return "IntelliHints Demo";
	}

	@Override
	public String getDescription() {
		return "This is a demo of IntelliHints components. IntelliHints can display a hint popup in a text field or text area " +
				"so that user can pick a hint directly while typing.\n" +
				"\nYou can start to type in those text fields or text area to see how it works. " +
				"At any time, if you want to see whether there are hints available, you can press DOWN key " +
				"in text field or CTRL+SPACE in text area.\n" +
				"\n" +
				"Demoed classes:\n" +
				"com.jidesoft.hints.IntelliHints\n" +
				"com.jidesoft.hints.AbstractIntelliHints\n" +
				"com.jidesoft.hints.AbstractListIntelliHints\n" +
				"com.jidesoft.hints.FileIntelliHints\n" +
				"com.jidesoft.hints.ListDataIntelliHints";
	}

	@Override
	public Component getOptionsPanel() {
		JPanel panel = new JPanel(new GridLayout());
		final JCheckBox applyFileFilter = new JCheckBox("Show \"Program\" Folders/Files Only");
		applyFileFilter.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				_applyFileFilter = applyFileFilter.isSelected();
			}
		});
		panel.add(applyFileFilter);
		return panel;
	}

	public Component getDemoPanel() {
		final String[] fontNames = {"Agency FB", "Aharoni", "Algerian", "Andalus", "Angsana New", "AngsanaUPC",
				"Aparajita", "Arabic Typesetting", "Arial", "Arial Black", "Arial Narrow", "Arial Rounded MT Bold",
				"Arial Unicode MS", "Baskerville Old Face"};
		// create file text field
		List<String> urls = List.of("https://www.bilibili.com/", "https://pan-yz.chaoxing.com/computerFiles/check_key/",
				"https://co-browsing.net/", "https://github.com/");

		JTextField urlTextField = new JTextField("https://");
		urlTextField.setName("URL IntelliHint");
		SelectAllUtil.install(urlTextField);
		ListDataIntelliHints<String> intelliHints = new ListDataIntelliHints<>(urlTextField, urls);
		intelliHints.setCaseSensitive(false);

		JTextField pathTextField = new JTextField("C://");
		SelectAllUtil.install(pathTextField);
		FileIntelliHints fileIntelliHints = new FileIntelliHints(pathTextField);
		fileIntelliHints.setFilter((dir, name) -> !_applyFileFilter || dir.getAbsolutePath().contains("Program") || name.contains("Program"));
		fileIntelliHints.setFolderOnly(false);
		fileIntelliHints.setFollowCaret(true);
		fileIntelliHints.setShowFullPath(false);

		// create file text field
		JTextField fileTextField = new JTextField("C:/");
		fileTextField.setName("File IntelliHint");
		SelectAllUtil.install(fileTextField);
		new FileIntelliHints(fileTextField);

		// create file text field
		JTextArea fileTextArea = new JTextArea();
		new FileIntelliHints(fileTextArea);
		fileTextArea.setRows(4);

		// create file text field
		JTextField fontTextField = new JTextField();
		fontTextField.setName("Font IntelliHint");
		SelectAllUtil.install(fontTextField);
		ListDataIntelliHints<String> fontIntelliHints = new ListDataIntelliHints<>(fontTextField, fontNames);
		fontIntelliHints.setCaseSensitive(false);

		JTextField textField = new JTextField();
		SelectAllUtil.install(textField);
		//noinspection UnusedDeclaration
		new AbstractListIntelliHints<Long>(textField) {
			private JLabel _messageLabel;

			// 自定义提示面板，在顶部加一个label用来提示
			@Override
			public JComponent createHintsComponent() {
				JPanel panel = (JPanel) super.createHintsComponent();
				_messageLabel = new JLabel();
				panel.add(_messageLabel, BorderLayout.BEFORE_FIRST_LINE);
				return panel;
			}

			// update list model depending on the data in textfield
			public boolean updateHints(Object value) {
				if (value == null) {
					return false;
				}
				String s = value.toString();
				s = s.trim();
				if (s.length() == 0) {
					return false;
				}
				try {
					long l = Long.parseLong(s);
					boolean prime = isProbablePrime(l);
					_messageLabel.setText("");
					if (prime) {
						return false;
					} else {
						Vector<Long> list = new Vector<>();
						long nextPrime = l;
						for (int i = 0; i < 10; i++) {
							nextPrime = nextPrime(nextPrime);
							list.add(nextPrime);
						}
						setListData(list);
						_messageLabel.setText("Next 10 prime numbers:");
						_messageLabel.setForeground(Color.DARK_GRAY);
						return true;
					}
				} catch (NumberFormatException e) {
					_messageLabel.setText("Invalid long number");
					_messageLabel.setForeground(Color.RED);
					return true;
				}
			}
		};

		DefaultTableModel model = new DefaultTableModel(0, 1) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}

			@Override
			public String getColumnName(int column) {
				return "Font";
			}
		};
		model.addRow(new Object[]{"Arial"});
		model.addRow(new Object[]{"Tahoma"});
		JXTable table = new JXTable(model);
		// 给表格单元格编辑用的输入框注册代码提示
		JTextField f = new JTextField();
		ListDataIntelliHints<String> fontIntellihints = new ListDataIntelliHints<>(f, fontNames);
		fontIntellihints.setCaseSensitive(false);
		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(f));

		table.setPreferredScrollableViewportSize(new Dimension(100, 100));

		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 3));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panel.add(new JLabel("ListDataIntelliHints TextField for URLs: "));
		panel.add(urlTextField);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		// create path text field
		panel.add(new JLabel("FileIntelliHints TextField for paths (folders only, show partial path):"));
		panel.add(pathTextField);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		panel.add(new JLabel("FileIntelliHints TextField for files (files and folders, show full path):"));
		panel.add(fileTextField);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		panel.add(new JLabel("FileIntelliHints TextArea for files (each line is for a new file):"));
		panel.add(new JScrollPane(fileTextArea));
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		panel.add(new JLabel("IntelliHints TextField to choose a font:"));
		panel.add(fontTextField);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		panel.add(new JLabel("A custom IntelliHints for prime numbers: "));
		panel.add(textField);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		panel.add(new JLabel("Using IntelliHint in JTable's cell editor"));
		panel.add(new JScrollPane(table), JideBoxLayout.FLEXIBLE);

		panel.add(Box.createGlue(), JideBoxLayout.VARY);

		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new IntelliHintsTest());
		});
	}

	public static boolean isProbablePrime(long number) {
		return new BigInteger("" + number).isProbablePrime(500);
	}

	public static long nextPrime(long lastPrime) {
		long testPrime;
		testPrime = lastPrime + 1;
		while (!isProbablePrime(testPrime)) testPrime += (testPrime % 2 == 0) ? 1 : 2;

		return testPrime;
	}
}