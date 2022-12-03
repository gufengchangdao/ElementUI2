package com.element.ui.border;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class IconBorderTest extends AbstractDemo {
	@Override
	public String getDescription() {
		return "conBorder 创建一个边框，在边框的一侧或多侧放置一个图标\n" +
				"在不需要特殊绘画的情况下尝试将图标添加到预先存在的组件时，此边框很有用。例如，在 CellStyle 中使用以用图标装饰单元格渲染器。";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		JLabel l = new JLabel("基于MatteBorder实现的可绘制图标的边框");
		SvgIcon icon = IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY);
		// 给左侧图标留点位置
		IconBorder border = new IconBorder(0, icon.getIconWidth(), 0, 0, icon);
		border.setHorizontalIconAlignment(SwingConstants.LEFT);
		l.setBorder(border);
		p.add(l);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new IconBorderTest());
		});
	}
}