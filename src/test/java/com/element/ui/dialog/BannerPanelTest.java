package com.element.ui.dialog;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.JideIconsFactory;
import com.element.ui.layout.JideBoxLayout;
import com.element.util.SwingTestUtil;
import com.element.util.WrapperUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class BannerPanelTest extends AbstractDemo {

	@Override
	public String getName() {
		return "BannerPanelTest";
	}

	@Override
	public String getDescription() {
		return """
				横幅面板对于显示标题、描述和图标非常有用。
				它可以在对话框中显示一些帮助信息或以很好的方式显示产品标识。你也可以设置横幅面板的背景使用画笔。
				""";
	}

	@Override
	public Component getDemoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 10));

		BannerPanel headerPanel0 = new BannerPanel("This is also a BannerPanel with no subtitle or icon");
		headerPanel0.setBackground(new Color(0, 0, 128));
		headerPanel0.setForeground(Color.WHITE);
		headerPanel0.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.lightGray, Color.lightGray, Color.gray));

		BannerPanel headerPanel1 = new BannerPanel("This is a BannerPanel",
				"BannerPanel is very useful to display a title, a description and an icon. It can be used in " +
						"dialog to show some help information or display a product logo in a nice way.",
				JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32));
		headerPanel1.setBackground(Color.WHITE);
		headerPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		BannerPanel headerPanel2 = new BannerPanel("This is a BannerPanel",
				"BannerPanel is very useful to display a title, a description and an icon. It can be used in " +
						"dialog to show some help information or display a product logo in a nice way.",
				JideIconsFactory.getImageIcon(JideIconsFactory.JIDE32));
		headerPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		headerPanel2.setTitleIconLocation(SwingConstants.LEADING);
		// this method will use JideSwingUtilities fast gradient paint to do the painting.
		headerPanel2.setGradientPaint(Color.WHITE, new Color(0, 0, 128), false);
		//  you can use Paint such as GradientPaint or TexturePaint
		//        headerPanel2.setBackgroundPaint(new GradientPaint(0, 0, new Color(0, 0, 128), 500, 0, Color.WHITE));

		BannerPanel headerPanel3 = new BannerPanel("This is a BannerPanel",
				"The place for the title icon of BannerPanel actually can be any JComponent. Here is an example " +
						"to use a JComboBox instead of an icon. The component can be placed at the left or right.",
				WrapperUtil.createCenterPanel(new JComboBox<>(new String[]{"Any Component", "Just a Demo"})));

		headerPanel3.setFont(headerPanel0.getFont().deriveFont(11f));
		headerPanel3.setBackground(Color.WHITE);
		headerPanel3.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		panel.add(headerPanel1, JideBoxLayout.FLEXIBLE);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
		panel.add(headerPanel2, JideBoxLayout.FLEXIBLE);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
		panel.add(headerPanel0, JideBoxLayout.FLEXIBLE);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
		panel.add(headerPanel3, JideBoxLayout.FLEXIBLE);
		panel.add(Box.createGlue(), JideBoxLayout.VARY);

		panel.setPreferredSize(new Dimension(500, 400));

		return panel;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new BannerPanelTest());
		});
	}
}