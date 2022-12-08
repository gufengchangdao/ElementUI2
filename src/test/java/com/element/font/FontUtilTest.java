package com.element.font;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class FontUtilTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		FontUtil.registerGlobalFont(FontUtil.getFont("ZhanKuXiaoLOGOTi-2.otf"));

		JTextArea c2 = new JTextArea("如果概括奥托·阿波卡利斯的一生，我想，用「卡莲·卡斯兰娜」再精确不过了\n" +
				"他的确称不上是一个好人，但他绝对是天命迄今为止最为伟大的主教，也是一个真正的男人");

		return c2;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			// LookAndFeelFactory.installJideExtension();
			showAsFrame(new FontUtilTest());
		});
	}
}