package com.element.ui.cursor;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class AnimatedCursorTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JButton b = new JButton("悬停");
		b.setPreferredSize(new Dimension(200, 200));
		// 读取图片，这里使用Toolkit.createImage()来读取图片
		// 也可以使用ImageIO读取图片，但是如果是jdk版本低的话图片会有红色背景(jdk8会有)
		Toolkit tk = b.getToolkit();
		new AnimatedCursor(b, Arrays.asList(
				tk.createImage("src/test/resources/lab/cursor/00.png"),
				tk.createImage("src/test/resources/lab/cursor/01.png"),
				tk.createImage("src/test/resources/lab/cursor/02.png")));

		return b;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AnimatedCursorTest());
		});
	}
}