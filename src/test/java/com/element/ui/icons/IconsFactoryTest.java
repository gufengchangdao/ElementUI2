package com.element.ui.icons;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class IconsFactoryTest extends AbstractDemo {

	@Override
	public String getName() {
		return "IconsFactoryTest";
	}

	@Override
	public Component getDemoPanel() {
		// 这里应该使用图片测试，使用SvgIcon导致有些方法失效了

		JPanel p = new JPanel(new MigLayout("wrap 1", "fill, 300::"));

		SvgIcon imageIcon = IconsFactory.getSvgIcon(SwordSvg.class, 50, 50, ColorUtil.PRIMARY);
		JLabel l1 = new JLabel(imageIcon);
		l1.setBorder(BorderFactory.createTitledBorder("getImageIcon"));
		p.add(l1);

		JLabel l2 = new JLabel(IconsFactory.getSvgIcon(SwordSvg.class, 50, 50, ColorUtil.PRIMARY));
		l2.setBorder(BorderFactory.createTitledBorder("getDisabledImageIcon"));
		p.add(l2);

		JLabel l3 = new JLabel(IconsFactory.getSvgIcon(SwordSvg.class, 50, 50, ColorUtil.PRIMARY));
		l3.setBorder(BorderFactory.createTitledBorder("getBrighterImageIcon"));
		p.add(l3);

		JLabel l4 = new JLabel(IconsFactory.getSvgIcon(SwordSvg.class, 50, 50, ColorUtil.PRIMARY));
		l4.setBorder(BorderFactory.createTitledBorder("getTintedImageIcon"));
		p.add(l4);

		JLabel l5 = new JLabel(IconsFactory.createRotatedImage(null, imageIcon, 90));
		l5.setBorder(BorderFactory.createTitledBorder("createRotatedImage"));
		p.add(l5);

		JLabel l6 = new JLabel(IconsFactory.getSvgIcon(SwordSvg.class, 50, 50, ColorUtil.PRIMARY));
		l6.setBorder(BorderFactory.createTitledBorder("getSvgIcon"));
		p.add(l6);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new IconsFactoryTest());
		});
	}
}