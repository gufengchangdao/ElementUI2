package com.element.ui.table.listener;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.base.DefaultTableModel2;
import com.element.ui.table.editor.DateSpinnerCellEditorTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class DragScrollingListenerTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		String[] columnNames = {"String", "Integer", "Boolean"};
		DefaultTableModel2 model = new DefaultTableModel2(null, columnNames);
		model.setCellEditable(true);
		IntStream.range(0, 1000)
				.mapToObj(i -> new Object[]{"Java Swing", i, i % 2 == 0})
				.forEach(model::addRow);

		JTable table = new JTable(model) {
			private transient MouseAdapter handler;

			@Override
			public void updateUI() {
				removeMouseMotionListener(handler);
				removeMouseListener(handler);
				super.updateUI();
				handler = new DragScrollingListener(this);
				addMouseMotionListener(handler);
				addMouseListener(handler);
			}
		};
		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		return scroll;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new DragScrollingListenerTest());
		});
	}
}