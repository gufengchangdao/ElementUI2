package com.element.ui.others.select.removable;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.checkbox.RemoveButtonComboBox;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class RemoveButtonComboBoxTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		ComboBoxModel<String> m = new DefaultComboBoxModel<>(
				new String[]{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "bbb", "ccc"});
		JComboBox<String> comboBox = new RemoveButtonComboBox<>(m);
		p.add(comboBox, "w :300:");

		JComboBox<String> comboBox2 = new RemoveButtonComboBox<>(m);
		comboBox2.setEditable(true);
		p.add(comboBox2, "w :300:");

		return p;
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new RemoveButtonComboBoxTest());
		});
	}

}