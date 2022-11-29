package com.element.ui.pane;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.layout.JideBoxLayout;
import com.element.ui.tabs.JideTabbedPane;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class JideSplitPaneTest extends AbstractDemo {
	private static JideSplitPane _jideSplitPane;
	private static JSplitPane _jSplitPane;

	@Override
	public String getName() {
		return "JideSplitPane Demo";
	}

	@Override
	public String getDescription() {
		return """
				JideSplitPane 是一个支持多次拆分的拆分窗格。正如您在右侧所见，有一个 JideSplitPane 分成三个窗格。
				事实上，您可以根据需要将其拆分为多个窗格。

				Demoed classes:
				com.jidesoft.swing.JideSplitPane
				""";
	}

	@Override
	public Component getDemoPanel() {
		JideTabbedPane tabbedPane = new JideTabbedPane();
		tabbedPane.setTabShape(JideTabbedPane.SHAPE_BOX);
		tabbedPane.addTab("JideSplitPane", createSplitPane());
		tabbedPane.addTab("JSplitPane (for comparison)", createJSplitPane());
		return tabbedPane;
	}

	public Component getOptionsPanel() {
		JPanel switchPanel = new JPanel(new GridLayout(0, 1, 3, 3));

		final JCheckBox option1 = new JCheckBox("显示折叠/展开按钮");
		switchPanel.add(option1);
		option1.addItemListener(e -> {
			_jideSplitPane.setOneTouchExpandable(option1.isSelected());
			_jSplitPane.setOneTouchExpandable(option1.isSelected());
		});

		final JCheckBox option2 = new JCheckBox("设置连续布局");
		switchPanel.add(option2);
		option2.addItemListener(e -> {
			_jideSplitPane.setContinuousLayout(option2.isSelected());
			_jSplitPane.setContinuousLayout(option2.isSelected());
		});

		final JCheckBox option3 = new JCheckBox("显示 gripper");
		switchPanel.add(option3);
		option3.addItemListener(e -> _jideSplitPane.setShowGripper(option3.isSelected()));

		final JCheckBox option4 = new JCheckBox("可拖拽");
		switchPanel.add(option4);
		option4.addItemListener(e -> _jideSplitPane.setDragResizable(option4.isSelected()));
		option4.setSelected(_jideSplitPane.isDragResizable());

		return switchPanel;
	}

	@Override
	public String getDemoFolder() {
		return "B5.JideSplitPane";
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new JideSplitPaneTest());
		});

	}

	private static JideSplitPane createSplitPane() {
		JTree tree = new JTree();
		JTable table = new JTable(new DefaultTableModel(10, 3));
		JList list = new JList(new Object[]{"A", "B", "C", "D", "E", "F", "G", "H", "I",}) {
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				Dimension size = super.getPreferredScrollableViewportSize();
				size.width = 100;
				return size;
			}
		};

		_jideSplitPane = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
		_jideSplitPane.setProportionalLayout(true);
		_jideSplitPane.add(new JScrollPane(tree), JideBoxLayout.FLEXIBLE);
		_jideSplitPane.add(new JScrollPane(table), JideBoxLayout.VARY);
		_jideSplitPane.add(new JScrollPane(list), JideBoxLayout.FLEXIBLE);
		return _jideSplitPane;
	}

	private static JSplitPane createJSplitPane() {
		JTree tree = new JTree();
		JTable table = new JTable(new DefaultTableModel(10, 3));

		_jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		_jSplitPane.setLeftComponent(new JScrollPane(tree));
		_jSplitPane.setRightComponent(new JScrollPane(table));
		return _jSplitPane;
	}
}