package com.element.ui.border;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class ComponentTitledBorderTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());

		JTree tree = new JTree();
		JCheckBox c = new JCheckBox("CheckBox", true);
		c.addActionListener(e -> tree.setEnabled(c.isSelected()));
		c.setFocusPainted(false);
		JScrollPane l1 = new JScrollPane(tree);
		l1.setBorder(new ComponentTitledBorder(c, l1, BorderFactory.createEtchedBorder()));

		JLabel icon = new JLabel(UIManager.getIcon("FileChooser.detailsViewIcon"));
		JLabel l2 = new JLabel("<html>ComponentTitledBorder<br>+ JLabel + Icon");
		l2.setBorder(new ComponentTitledBorder(icon, l2, BorderFactory.createEtchedBorder()));

		JButton b = new JButton("Button");
		b.setFocusPainted(false);
		JLabel l3 = new JLabel("ComponentTitledBorder + JButton");
		l3.setBorder(new ComponentTitledBorder(b, l3, BorderFactory.createEtchedBorder()));

		p.add(l1, "cell 0 0 1 2");
		p.add(l2, "wrap");
		p.add(l3, "wrap");

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ComponentTitledBorderTest());
		});
	}
}