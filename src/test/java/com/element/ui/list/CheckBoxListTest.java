package com.element.ui.list;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SearchableUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CheckBoxListTest extends AbstractDemo {
	private CheckBoxList _list;
	private DefaultListModel _model;

	public String getName() {
		return "CheckBoxList Demo";
	}

	@Override
	public String getDescription() {
		return """
				CheckBoxList is a list with check box.

				You can click on the check box to select/unselect the list item. Or you can press SPACE key to toggle the selection.

				Some items can be marked as disable. In this case, user will not be able to toggle the selection status. from UI, they appear as gray color. There are three diabled items (row 3, 6, and 10) in this demo.

				Demoed classes:
				com.jidesoft.list.CheckBoxList""";
	}

	public Component getDemoPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 400));
		panel.setLayout(new BorderLayout(4, 4));
		panel.add(new JLabel("List of countries: "), BorderLayout.BEFORE_FIRST_LINE);
		_list = createCheckBoxList();
		panel.add(new JScrollPane(_list));
		return panel;
	}

	private CheckBoxList createCheckBoxList() {
		_model = new DefaultListModel<>();
		String[] name = {"卡莲", "符华", "德丽莎", "姬子", "布洛妮娅", "芽衣", "琪亚娜", "八重樱", "丽塔", "莉莉娅", "罗莎莉娅",
				"希儿", "幽兰黛尔", "明日香", "菲谢尔", "渡鸦", "爱莉希雅", "梅比乌斯", "卡萝尔", "帕朵菲莉丝", "阿波尼亚", "伊甸",
				"格蕾修", "维尔薇", "李素裳", "玉骑士·月痕", "真我·人之律者", "螺旋·愚戏之匣", "繁星·绘世之卷", "黄金·璀耀之歌",
				"戒律·深罪之槛", "空梦·掠集之兽", "天元骑英", "缭乱星棘", "次生银翼", "薪炎之律者", "甜辣女孩", "粉色妖精小姐"};
		for (String s : name) {
			_model.addElement(s);
		}
		final CheckBoxList list = new CheckBoxList(_model) {
			@Override
			public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
				return -1;
			}

			@Override
			public boolean isCheckBoxEnabled(int index) {
				return !_model.getElementAt(index).equals("Afghanistan")
						&& !_model.getElementAt(index).equals("Albania")
						&& !_model.getElementAt(index).equals("Antarctica");
			}
		};
		list.getCheckBoxListSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// uncomment the lines below to see a customize cell renderer.
//        list.setCellRenderer(new DefaultListCellRenderer() {
//            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//                label.setIcon(JideIconsFactory.getImageIcon(JideIconsFactory.FileType.JAVA));
//                return label;
//            }
//        });
		SearchableUtil.installSearchable(list);
		list.getCheckBoxListSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				//                    int[] selected = list.getCheckBoxListSelectedIndices();
//                    for (int i = 0; i < selected.length; i++) {
//                        int select = selected[i];
//                        System.out.print(select + " ");
//                    }
//                    System.out.println("\n---");
			}
		});
		list.setCheckBoxListSelectedIndices(new int[]{2, 3, 20});
		return list;
	}

	static public void main(String[] s) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CheckBoxListTest());
		});

	}

	@Override
	public Component getOptionsPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 2, 2));
		JButton selectAllButton = new JButton(new AbstractAction("Select All") {
			public void actionPerformed(ActionEvent e) {
				_list.getCheckBoxListSelectionModel().addSelectionInterval(0, _list.getModel().getSize() - 1);
			}
		});
		JButton selectNoneButton = new JButton(new AbstractAction("Select None") {
			public void actionPerformed(ActionEvent e) {
				_list.getCheckBoxListSelectionModel().clearSelection();
			}
		});

		final JCheckBox checkBoxEnabled = new JCheckBox("Enabled Checking");
		checkBoxEnabled.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				_list.setCheckBoxEnabled(checkBoxEnabled.isSelected());
			}
		});
		checkBoxEnabled.setSelected(_list.isCheckBoxEnabled());

		final JCheckBox clickInCheckBoxOnly = new JCheckBox("Check only valid inside CheckBox");
		clickInCheckBoxOnly.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				_list.setClickInCheckBoxOnly(clickInCheckBoxOnly.isSelected());
			}
		});
		clickInCheckBoxOnly.setSelected(_list.isClickInCheckBoxOnly());

		final JCheckBox allowAll = new JCheckBox("Enable (All)");
		allowAll.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				boolean selected = allowAll.isSelected();
				if (selected) {
					_model.insertElementAt(CheckBoxList.ALL_ENTRY, 0);
				} else {
					_model.remove(0);
				}
			}
		});
		allowAll.setSelected(false);

		final JButton removeSelected = new JButton("Remove Selected Row");
		removeSelected.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 3785843307574034034L;

			public void actionPerformed(ActionEvent e) {
				int index = _list.getSelectedIndex();
				if (index != -1) {
					((DefaultListModel) _list.getModel()).remove(index);
				}
			}
		});

		panel.add(selectAllButton);
		panel.add(selectNoneButton);
		panel.add(removeSelected);
		panel.add(checkBoxEnabled);
		panel.add(allowAll);
		panel.add(clickInCheckBoxOnly);
		return panel;
	}
}