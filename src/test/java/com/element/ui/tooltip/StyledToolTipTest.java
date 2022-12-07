package com.element.ui.tooltip;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class StyledToolTipTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());
		JButton b = new JButton("按钮") {
			@Override
			public JToolTip createToolTip() {
				return new StyledToolTip();
			}
		};
		b.setToolTipText("这是一段{按钮的提示文本:b,f:(64, 158, 255)}");
		p.add(b);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new StyledToolTipTest());
		});
	}

}