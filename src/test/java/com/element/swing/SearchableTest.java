package com.element.swing;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.layout.JideBoxLayout;
import com.element.ui.pane.JideSplitPane;
import com.element.ui.table.TableSearchable;
import com.element.ui.tree.TreeSearchable;
import com.element.util.SearchableUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.Position;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;

public class SearchableTest extends AbstractDemo {

	@Override
	public String getName() {
		return "Searchable Demo";
	}

	@Override
	public String getDescription() {
		return """
				Searchable is a collection of several classes that enable quick search feature on JList, JComboBox, JTree, JTable or JTextComponent.
				1. Press any letter key to start the search
				2. Press up/down arrow key to navigation to next or previous matching occurrence
				3. Hold CTRL key while pressing up/down arrow key to to multiple selection
				4. Press CTRL+A to select all matching occurrences
				5. Use '?' to match any character or '*' to match several characters (except in JTextComponent)\s
				6. A lot of customization options using the API

				Demoed classes:
				com.jidesoft.swing.Searchable
				com.jidesoft.swing.TreeSearchable
				com.jidesoft.swing.ListSearchable
				com.jidesoft.swing.ComboBoxSearchable
				com.jidesoft.swing.TableSearchable
				com.jidesoft.swing.TextComponentSearchable
				com.jidesoft.swing.SearchableUtil
				com.jidesoft.swing.Searchable""";
	}

	@Override
	public Component getDemoPanel() {
		JTree tree = new JTree() {
			@Override
			public TreePath getNextMatch(String prefix, int startingRow, Position.Bias bias) {
				return null;
			}
		};
		tree.setVisibleRowCount(15);
		final TreeSearchable treeSearchable = SearchableUtil.installSearchable(tree);

		String[] names = {"卡莲", "符华", "德丽莎", "姬子", "布洛妮娅", "芽衣", "琪亚娜", "八重樱", "丽塔", "莉莉娅", "罗莎莉娅", "希儿",
				"幽兰黛尔", "明日香", "菲谢尔", "渡鸦", "爱莉希雅", "梅比乌斯", "卡萝尔", "帕朵菲莉丝", "阿波尼亚", "伊甸", "格蕾修", "维尔薇",
				"李素裳", "玉骑士·月痕", "真我·人之律者", "螺旋·愚戏之匣", "繁星·绘世之卷", "黄金·璀耀之歌", "戒律·深罪之槛",
				"空梦·掠集之兽", "天元骑英", "缭乱星棘", "次生银翼"};
		JList<String> list = new JList<>(names) {
			@Override
			public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
				return -1;
			}
		};
		list.setVisibleRowCount(15);
		SearchableUtil.installSearchable(list);

		JComboBox<String> comboBox = new JComboBox<>(names);
		comboBox.setEditable(false); // combobox searchable only works when combobox is not editable.
		SearchableUtil.installSearchable(comboBox);

		final TableModel tableModel = new DefaultTableModel(
				new String[][]{{"A1", "A2", "A3"}, {"B1", "B2", "B3"}, {"C1", "C2", "C3"}},
				new String[]{"列1", "列2", "列3"}
		);

		JTable table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(200, 100));
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		TableSearchable tableSearchable = SearchableUtil.installSearchable(table);
		tableSearchable.setMainIndex(-1); // search for all columns

		JTextArea textArea = new JTextArea();
		textArea.setRows(15);
		StringBuilder buf = new StringBuilder();
		for (String name : names) {
			buf.append(name);
			buf.append("\n");
		}
		textArea.setText(buf.toString());
		SearchableUtil.installSearchable(textArea);

		JPanel treePanel = createTitledPanel(new JLabel("Searchable JTree"), 'E', new JScrollPane(tree));
		JCheckBox checkbox = new JCheckBox("Recursive");
		checkbox.setMnemonic('R');
		checkbox.addItemListener(e -> {
			treeSearchable.setRecursive(e.getStateChange() == ItemEvent.SELECTED);
		});
		checkbox.setSelected(treeSearchable.isRecursive());
		treePanel.add(checkbox, BorderLayout.AFTER_LAST_LINE);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new JideBoxLayout(listPanel, JideBoxLayout.Y_AXIS, 6));
		listPanel.add(createTitledPanel(new JLabel("Searchable JList"), 'L', new JScrollPane(list)), JideBoxLayout.VARY);
		listPanel.add(createTitledPanel(new JLabel("Searchable JComboBox"), 'C', comboBox), JideBoxLayout.FIX);

		// add to the parent panel
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		JideSplitPane pane1 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
		JideSplitPane pane2 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
		panel.add(pane1);
		panel.add(pane2);

		pane1.add(treePanel);
		pane2.add(listPanel);
		pane1.add(createTitledPanel(new JLabel("Searchable JTable (Configured to search for all columns.)"), 'T', new JScrollPane(table)));
		pane2.add(createTitledPanel(new JLabel("Searchable JTextArea (CTRL+F to start searching)"), 'S', new JScrollPane(textArea)));

		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new SearchableTest());
		});
	}

	private static JPanel createTitledPanel(JLabel label, char mnemonic, JComponent component) {
		JPanel panel = new JPanel(new BorderLayout(2, 2));
		panel.add(label, BorderLayout.BEFORE_FIRST_LINE);
		label.setDisplayedMnemonic(mnemonic);
		if (component instanceof JScrollPane) {
			label.setLabelFor(((JScrollPane) component).getViewport().getView());
		} else {
			label.setLabelFor(component);
		}
		panel.add(component, BorderLayout.CENTER);
		return panel;
	}
}