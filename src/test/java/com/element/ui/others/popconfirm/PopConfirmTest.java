package com.element.ui.others.popconfirm;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.button.ButtonFactory;
import com.element.ui.others.carousel.HorizontalCarouselPanelTest;
import com.element.ui.svg.icon.regular.NotePencilSvg;
import com.element.ui.svg.icon.regular.QuestionSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class PopConfirmTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 2", "grow, center"));

		JButton update = ButtonFactory.createPlainButton("修改", ColorUtil.WARNING);
		SvgIcon icon = NotePencilSvg.of(16, 16);
		icon.setColorFilter(color -> ColorUtil.WARNING);
		PopConfirm popConfirm = new PopConfirm(update, new JLabel("这是一段内容确定修改吗？", icon, SwingConstants.LEFT));
		p.add(update);

		JButton del = ButtonFactory.createPlainButton("删除", ColorUtil.PRIMARY);
		icon = QuestionSvg.of(16, 16);
		icon.setColorFilter(color -> ColorUtil.WARNING);
		PopConfirm popConfirm2 = new PopConfirm(del, new JLabel("这是一段内容确定删除吗？", icon, SwingConstants.LEFT));
		p.add(del);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new PopConfirmTest());
		});
	}
}