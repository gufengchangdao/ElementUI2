package com.element.ui.button;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class SwitchButtonTest extends AbstractDemo {

	@Override
	public String getDescription() {
		return "该类相对于一代进行了升级，一代继承的是JButton，而二代继承了JToggleButton，按钮的状态交给JToggleButton来维护，只重" +
				"写关于绘制方面的方法";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		SwitchButton b = new SwitchButton();
		p.add(b);
		b.addActionListener(e -> System.out.println(b.isSelected()));

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new SwitchButtonTest());
		});
	}
}