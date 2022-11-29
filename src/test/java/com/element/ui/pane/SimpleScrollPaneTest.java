package com.element.ui.pane;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.tabs.JideTabbedPane;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class SimpleScrollPaneTest extends AbstractDemo {
	private SimpleScrollPane _pane;

	public String getName() {
		return "SimpleScrollPane Demo";
	}

	@Override
	public String getDescription() {
		return """
				SimpleScrollPaneDemo is a simplied version of JScrollPane. It simply adds four buttons on each side to do the scrolling.
				Demoed classes:
				com.jidesoft.swing.SimpleScrollPane""";
	}

	public Component getDemoPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(700, 400));
		panel.setLayout(new BorderLayout());
		panel.add(createTabbedPane(), BorderLayout.CENTER);
		return panel;
	}

	private Component createTabbedPane() {
		JideTabbedPane tabbedPane = new JideTabbedPane();
		tabbedPane.setTabShape(JideTabbedPane.SHAPE_BOX);
		tabbedPane.addTab("SimpleScrollPane", createFlatScrollPane());
		return tabbedPane;
	}


	@Override
	public Component getOptionsPanel() {
		final JComboBox<String> h = new JComboBox<>(new String[]{"Show as needed", "Never shown", "Always show"});
		h.setSelectedIndex(_pane.getHorizontalScrollBarPolicy() - SimpleScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		h.addItemListener(e -> _pane.setHorizontalScrollBarPolicy(SimpleScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED + h.getSelectedIndex()));

		final JComboBox<String> v = new JComboBox<>(new String[]{"Show as needed", "Never shown", "Always show"});
		v.setSelectedIndex(_pane.getVerticalScrollBarPolicy() - SimpleScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		v.addItemListener(e -> _pane.setVerticalScrollBarPolicy(SimpleScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED + v.getSelectedIndex()));

		final JCheckBox rollover = new JCheckBox("Scroll on Rollover");
		rollover.setSelected(_pane.isScrollOnRollover());
		rollover.addItemListener(e -> _pane.setScrollOnRollover(rollover.isSelected()));

		JPanel panel = new JPanel(new GridLayout(0, 1, 2, 2));
		panel.add(new JLabel("Horizontal Scrolling:"));
		panel.add(h);
		panel.add(new JLabel("Vertical Scrolling:"));
		panel.add(v);
		panel.add(rollover);
		return panel;
	}

	static class DummyTableModel extends AbstractTableModel {
		public int getRowCount() {
			return 30;
		}

		public int getColumnCount() {
			return 12;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return "" + rowIndex + "," + columnIndex;
		}
	}

	private SimpleScrollPane createFlatScrollPane() {
		_pane = new SimpleScrollPane();
		_pane.setHorizontalScrollBarPolicy(SimpleScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		_pane.setVerticalScrollBarPolicy(SimpleScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// you can add a border around viewport.
		//        _pane.setViewportBorder(BorderFactory.createLineBorder(Color.RED, 6));

		JTable table = new JTable(new DummyTableModel());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		_pane.setViewportView(table);

		return _pane;
	}

	static public void main(String[] s) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new SimpleScrollPaneTest());
		});

	}
}