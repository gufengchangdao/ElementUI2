package com.element.ui.others.select;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.select.removable.RemoveButtonComboBoxTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

import static org.junit.Assert.*;

public class MultiSelectorTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		Vector<String> vector = new Vector<>();
		vector.add("香蕉");
		vector.add("苹果");
		vector.add("艾希");
		MultiSelector field = new MultiSelector(vector);
		// field.setmSize(new Dimension(100, 40));
		// vector.remove(0);
		JButton button = new JButton("获取");
		button.addActionListener(e -> {
			System.out.println(field.getSelectItem());
		});
		p.add(field);
		p.add(button);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new MultiSelectorTest());
		});
	}
}