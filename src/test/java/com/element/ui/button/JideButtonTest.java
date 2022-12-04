package com.element.ui.button;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class JideButtonTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		JideButton b1 = new JideButton("按钮");
		p.add(b1);

		JideButton b2 = new JideButton("按钮2", SwordSvg.of(16, 16));
		p.add(b2);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new JideButtonTest());
		});
	}
}