package com.element.ui.checkbox;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.list.TransferListTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class CheckedComboBoxTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		p.add(new JLabel("Default:"));
		p.add(new JComboBox<>(makeModel()));
		p.add(Box.createVerticalStrut(20));
		p.add(new JLabel("CheckedComboBox:"));
		p.add(new CheckedComboBox<>(makeModel()));
		p.add(Box.createVerticalStrut(20));
		p.add(new JLabel("CheckedComboBox(Windows):"));
		p.add(new WindowsCheckedComboBox<>(makeModel()));

		return p;
	}

	private static ComboBoxModel<CheckableItem> makeModel() {
		CheckableItem[] m = {
				new CheckableItem("aaa", false),
				new CheckableItem("bb", true),
				new CheckableItem("111", false),
				new CheckableItem("33333", true),
				new CheckableItem("2222", true),
				new CheckableItem("c", false)
		};
		return new DefaultComboBoxModel<>(m);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CheckedComboBoxTest());
		});
	}
}