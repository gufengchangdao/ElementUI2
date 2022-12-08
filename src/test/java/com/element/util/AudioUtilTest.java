package com.element.util;

import com.element.plaf.LookAndFeelFactory;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class AudioUtilTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		JButton b1 = new JButton("默认提示音");
		b1.addActionListener(e -> {
			// Toolkit.getDefaultToolkit().beep();
			UIManager.getLookAndFeel().provideErrorFeedback(b1);
			JOptionPane.showMessageDialog(p, "默认提示音");
		});
		p.add(b1);

		JButton b2 = new JButton("自定义提示音");
		b2.addActionListener(e -> {
			AudioUtil.playAudio(b2,
					AudioUtilTest.class.getResourceAsStream("notice2.wav"),
					() -> JOptionPane.showConfirmDialog(p, "自定义提示音"));
		});
		p.add(b2);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AudioUtilTest());
		});
	}
}