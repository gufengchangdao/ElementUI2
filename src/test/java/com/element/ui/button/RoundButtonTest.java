package com.element.ui.button;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class RoundButtonTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		RoundButton button1 = new RoundButton("测试按钮",
				// new ImageIcon("D:\\MyDefault\\desktop\\SchoolBooksTrade\\SBT\\src\\main\\resources\\images\\choiceness.png"),
				null,
				Color.GRAY, 10);
		JButton button2 = new JButton("测试按钮");
		button2.setBackground(Color.GRAY);
		p.add(button1);
		p.add(button2);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new RoundButtonTest());
		});
	}
}