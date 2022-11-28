package com.element.ui.border;

import com.element.plaf.LookAndFeelFactory;
import demo.AbstractDemo;
import demo.SwingTestUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class PartialEtchedBorderTest extends AbstractDemo {
	@Override
	public String getName() {
		return "PartialEtchedBorderTest";
	}

	@Override
	public String getDescription() {
		return "在原EtchedBorder的基础上可以指定边框的绘制边";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		JLabel l1 = new JLabel("全方向的边框");
		l1.setBorder(new PartialEtchedBorder());
		p.add(l1);

		JLabel l2 = new JLabel("北方的边框");
		l2.setBorder(new PartialEtchedBorder(PartialEtchedBorder.NORTH));
		p.add(l2);

		JLabel l3 = new JLabel("水平的边框");
		l3.setBorder(new PartialEtchedBorder(PartialEtchedBorder.HORIZONTAL));
		p.add(l3);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new PartialEtchedBorderTest());
		});
	}
}