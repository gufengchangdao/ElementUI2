package com.element.swing.base;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import java.awt.*;

public class BaseComponentTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		BaseComponent c = new BaseComponent();
		c.setLayout(new MigLayout("wrap 1"));

		c.add(new JButton("按钮组件"));
		JXTextField field = new JXTextField("输入框");
		field.setColumns(30);
		field.setOpaque(false);
		c.add(field);
		c.setBackgroundPaint(new LinearGradientPaint(0, 0, c.getPreferredSize().width, 0,
				new float[]{0, .5f}, new Color[]{ColorUtil.PRIMARY, ColorUtil.DANGER}));

		return c;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new BaseComponentTest());
		});
	}
}