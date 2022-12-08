package com.element.ui.button;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.svg.icon.fill.CircleWavyCheckSvg;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.ui.svg.icon.fill.XCircleSvg;
import com.element.ui.svg.icon.regular.CrosshairSvg;
import com.element.ui.svg.icon.regular.QuestionSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class IconButtonTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		SvgIcon icon = XCircleSvg.of(32, 32);
		icon.setColorFilter(color -> ColorUtil.WARNING);
		p.add(new IconButton(icon, color -> ColorUtil.PRIMARY));
		p.add(new IconButton(CircleWavyCheckSvg.of(32, 32), color -> ColorUtil.PRIMARY));
		p.add(new IconButton(CrosshairSvg.of(32, 32), color -> ColorUtil.PRIMARY));
		p.add(new IconButton(QuestionSvg.of(32, 32), color -> ColorUtil.PRIMARY));
		p.add(new IconButton(SwordSvg.of(32, 32), color -> ColorUtil.PRIMARY));

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new IconButtonTest());
		});
	}
}