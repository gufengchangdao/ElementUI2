package com.element.ui.dialog;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.icons.IconsFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
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
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		String title = "阿波卡利斯如是说";
		String content = "……德莉莎，我的那些「老朋友」们，赤鸢仙人、理之律者……他们是真正的好人，一定会帮助你走出一条属于自己的道路。";
		ImageIcon icon = IconsFactory.getImageIcon(BannerPanelTest.class, "/com/element/ui/tabs/icons/calendar.gif");

		BannerPanel bannerPanel = new BannerPanel(title, content);
		bannerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ColorUtil.PRIMARY), "标题和内容"));
		p.add(bannerPanel);

		BannerPanel bannerPanel2 = new BannerPanel(title, content, icon);
		bannerPanel2.setBackgroundPaint(new LinearGradientPaint(0, 0, bannerPanel2.getPreferredSize().width, 0,
				new float[]{0, .5f}, new Color[]{ColorUtil.PRIMARY, ColorUtil.BORDER_LEVEL1}));
		bannerPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(ColorUtil.PRIMARY), "标题、内容和图标"));
		p.add(bannerPanel2);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new BannerPanelTest());
		});
	}
}