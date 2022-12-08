package com.element.ui.button;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.border.RoundBorderTest;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class ButtonFactoryTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));
		p.add(ButtonFactory.createDefaultButton("默认按钮", ColorUtil.PRIMARY_TEXT, Color.WHITE, ColorUtil.SECONDARY_TEXT));
		p.add(ButtonFactory.createDefaultButton("主要按钮", ColorUtil.PRIMARY));
		p.add(ButtonFactory.createDefaultButton("成功按钮", ColorUtil.SUCCESS));
		p.add(ButtonFactory.createDefaultButton("信息按钮", ColorUtil.INFO));
		p.add(ButtonFactory.createDefaultButton("警告按钮", ColorUtil.WARNING));
		p.add(ButtonFactory.createDefaultButton("危险按钮", ColorUtil.DANGER));

		p.add(ButtonFactory.createPlainButton("默认按钮", ColorUtil.PRIMARY_TEXT, Color.WHITE, ColorUtil.SECONDARY_TEXT));
		p.add(ButtonFactory.createPlainButton("主要按钮", ColorUtil.PRIMARY));
		p.add(ButtonFactory.createPlainButton("成功按钮", ColorUtil.SUCCESS));
		p.add(ButtonFactory.createPlainButton("信息按钮", ColorUtil.INFO));
		p.add(ButtonFactory.createPlainButton("警告按钮", ColorUtil.WARNING));
		p.add(ButtonFactory.createPlainButton("危险按钮", ColorUtil.DANGER));

		p.add(ButtonFactory.createRoundButton("默认按钮", ColorUtil.PRIMARY_TEXT, Color.WHITE, null));
		p.add(ButtonFactory.createRoundButton("主要按钮", ColorUtil.PRIMARY));
		p.add(ButtonFactory.createRoundButton("成功按钮", ColorUtil.SUCCESS));
		p.add(ButtonFactory.createRoundButton("信息按钮", ColorUtil.INFO));
		p.add(ButtonFactory.createRoundButton("警告按钮", ColorUtil.WARNING));
		p.add(ButtonFactory.createRoundButton("危险按钮", ColorUtil.DANGER));
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ButtonFactoryTest());
		});
	}
}