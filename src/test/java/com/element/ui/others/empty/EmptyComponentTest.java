package com.element.ui.others.empty;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.button.ButtonFactory;
import com.element.ui.svg.empty.EmptyImgGraySvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class EmptyComponentTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());

		p.setBackground(ColorUtil.PRIMARY_TEXT);
		p.add(new EmptyComponent(null, "暂无数据",
				ButtonFactory.createDefaultButton("按钮", ColorUtil.PRIMARY)));
		p.add(new EmptyComponent(new JLabel(EmptyImgGraySvg.of(200, 200)),
				"暂无数据",
				ButtonFactory.createDefaultButton("按钮", ColorUtil.PRIMARY)));

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new EmptyComponentTest());
		});
	}
}