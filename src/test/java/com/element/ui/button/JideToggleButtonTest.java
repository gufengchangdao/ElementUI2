package com.element.ui.button;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class JideToggleButtonTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		JideToggleButton icon1 = new JideToggleButton(SwordSvg.of(24, 24));
		p.add(icon1);

		JideToggleButton icon2 = new JideToggleButton("状态按钮");
		p.add(icon2);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new JideToggleButtonTest());
		});
	}
}