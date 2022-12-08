package com.element.ui.scrollbar;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.area.LineNumberView;
import com.element.ui.others.line.TitledSeparatorTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

import static org.junit.Assert.*;

public class ScrollBackToTopLayerUITest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JTextArea textArea = new JTextArea();
		textArea.setText(String.join("\n", Collections.nCopies(2000, "1111111111111")));
		JScrollPane scroll1 = new JScrollPane(textArea);
		scroll1.setRowHeaderView(new LineNumberView(textArea));
		textArea.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
		textArea.setEditable(false);

		JTable table = new JTable(500, 3);
		JScrollPane scroll2 = new JScrollPane(table);
		SwingUtilities.invokeLater(() -> table.scrollRectToVisible(table.getCellRect(500, 0, true)));

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("JTextArea", new JLayer<>(scroll1, new ScrollBackToTopLayerUI()));
		tabbedPane.addTab("JTable", new JLayer<>(scroll2, new ScrollBackToTopLayerUI()));

		tabbedPane.setPreferredSize(new Dimension(300,400));
		return tabbedPane;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ScrollBackToTopLayerUITest());
		});
	}
}