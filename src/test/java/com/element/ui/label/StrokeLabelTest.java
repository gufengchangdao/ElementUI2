package com.element.ui.label;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.font.FontUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class StrokeLabelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		p.add(new StrokeLabel("测试标签描边2", FontUtil.DEFAULT_FONT.deriveFont(36f),
				ColorUtil.COMMON_TEXT, 1, ColorUtil.SUCCESS), "span 2, right");
		p.add(new StrokeLabel("测试标签描边2", FontUtil.DEFAULT_FONT.deriveFont(36f),
				ColorUtil.COMMON_TEXT, 2, ColorUtil.WARNING), "span 2");
		p.add(new StrokeLabel("测试标签描边2", FontUtil.DEFAULT_FONT.deriveFont(36f),
				ColorUtil.COMMON_TEXT, 3, ColorUtil.DANGER), "span 2, right");
		p.add(new StrokeLabel("测试标签描边2", FontUtil.DEFAULT_FONT.deriveFont(36f),
				ColorUtil.COMMON_TEXT, 4, ColorUtil.INFO), "span 2");
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new StrokeLabelTest());
		});
	}
}