package com.element.ui.others.line;

import com.element.plaf.LookAndFeelFactory;
import demo.AbstractDemo;
import demo.SwingTestUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TitledSeparatorTest extends AbstractDemo {

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1", "grow"));
		p.setPreferredSize(new Dimension(400,500));

		p.add(new TitledSeparator("TitledBorder", 2, TitledBorder.DEFAULT_POSITION), "growx");
		p.add(new JCheckBox("JCheckBox 0"));
		p.add(new JCheckBox("JCheckBox 1"));
		p.add(Box.createVerticalStrut(10));

		Color color = new Color(0x64_B4_C8);
		p.add(new TitledSeparator("TitledBorder ABOVE TOP", color, 2, TitledBorder.ABOVE_TOP), "growx");
		p.add(new JCheckBox("JCheckBox 2"));
		p.add(new JCheckBox("JCheckBox 3"));
		p.add(Box.createVerticalStrut(10));

		p.add(new JSeparator(), "growx");
		p.add(new JCheckBox("JCheckBox 4"));
		p.add(new JCheckBox("JCheckBox 5"));

		TitledSeparator s1 = new TitledSeparator("TitledBorder ABOVE TOP", color, Color.RED, 20, TitledBorder.BOTTOM);
		s1.setDoubleDraw(false);
		p.add(s1, "growx");
		p.add(new JCheckBox("JCheckBox 4"));
		p.add(new JCheckBox("JCheckBox 5"));

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TitledSeparatorTest());
		});
	}
}