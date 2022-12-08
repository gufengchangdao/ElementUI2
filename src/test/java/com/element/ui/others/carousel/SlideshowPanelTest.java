package com.element.ui.others.carousel;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.notice.message.SystemMessageFactoryTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import org.jdesktop.swingx.JXTextArea;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.junit.Assert.*;

public class SlideshowPanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(900, 200));
		// p.add(new SlideshowPanel(850, 180, List.of(
		// 		new JLabel("你好，这里是测试频道1你好，这里是测试频道1你好，这里是测试频道1你好，这里是测试频道1你好，这里是测试频道1你好，这里是测试频道1你好，这里是测试频道1你好，这里是测试频道1",SwingConstants.LEFT),
		// 		new JLabel("你好，这里是测试频道2"), new JLabel("你好，这里是测试频道3")),
		// 		integer -> System.out.println("点击了第 " + (integer + 1) + "个label")));
		p.add(new SlideshowPanel(850, 180, 1500L, Arrays.asList(
				new JXTextArea("测试文档1"),
				new JXTextArea("测试文档2"),
				new JXTextArea("测试文档3"),
				new JXTextArea("测试文档4")
		),
				integer -> System.out.println("点击了第 " + (integer + 1) + "个label")));
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new SlideshowPanelTest());
		});
	}
}