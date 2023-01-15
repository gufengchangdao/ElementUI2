package com.element.ui.tooltip;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.SwingPosition;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class TooltipLabelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		Container p = new JPanel(new MigLayout("", "grow,center"));
		JButton b = new JButton("按钮");
		TooltipLabel tooltipLabel = new TooltipLabel(b, "这是提示文本",
				SwingPosition.NORTH_WEST, ColorUtil.SUCCESS);

		b.addActionListener(e -> {
			tooltipLabel.setText("这是新的提示文本");
		});
		p.add(b);
		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TooltipLabelTest());
		});
	}
}