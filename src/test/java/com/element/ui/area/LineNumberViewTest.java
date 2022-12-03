package com.element.ui.area;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class LineNumberViewTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("fill"));

		JTextArea textArea = new JTextArea("""
				不能播放请刷新一次!刷新一次!或切换线路,不同线路播放源不同,百度APP无法播放,建议QQ浏览器或
				Edge浏览器免解析线路的广告,
				全部不能观看推荐更换其他浏览器""");
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setRowHeaderView(new LineNumberView(textArea));
		p.add(scroll,"growx,growy");

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new LineNumberViewTest());
		});
	}
}