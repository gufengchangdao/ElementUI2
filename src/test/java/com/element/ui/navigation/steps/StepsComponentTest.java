package com.element.ui.navigation.steps;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.menu.ReturnJMenuTest;
import com.element.ui.svg.icon.fill.XCircleSvg;
import com.element.util.SwingTestUtil;
import com.element.util.UIUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.junit.Assert.*;

public class StepsComponentTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));
		StepsComponent c = new StepsComponent(Arrays.asList("步骤1", "步骤2", "步骤3", "步骤4", "步骤5"),
				XCircleSvg.class,
				Arrays.asList("描述一", "描述二", "描述三", "描述四", "描述五"),
				2, ColorUtil.SUCCESS, 70, true);

		JButton b1 = new JButton("加一");
		JButton b2 = new JButton("减一");
		JButton b3 = new JButton("修改为3");
		JButton b4 = new JButton("更换列表");
		b1.addActionListener(e -> c.addStep());
		b2.addActionListener(e -> c.reduceStep());
		b3.addActionListener(e -> c.setStep(2));
		b4.addActionListener(e -> {
			c.setItems(c.getItems().subList(0, 2));
			p.validate();
		});

		p.add(c);
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new StepsComponentTest());
		});
	}
}