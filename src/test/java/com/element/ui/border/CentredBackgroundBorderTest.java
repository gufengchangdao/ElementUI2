package com.element.ui.border;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class CentredBackgroundBorderTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JTextArea area = new JTextArea();
		area.setForeground(Color.WHITE);
		area.setBackground(new Color(0x0, true)); // Nimbus
		area.setLineWrap(true);
		area.setOpaque(false);
		area.setText(String.join("\n",
				"private static void createAndShowGui() {",
				"  final JFrame frame = new JFrame();",
				"  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);",
				"  frame.getContentPane().add(new MainPanel());",
				"  frame.pack();",
				"  frame.setLocationRelativeTo(null);",
				"  frame.setVisible(true);",
				"}"
		));

		JScrollPane scroll = new JScrollPane(area);
		scroll.getViewport().setOpaque(false);
		scroll.setViewportBorder(new CentredBackgroundBorder(
				ImageIO.read(CentredBackgroundBorderTest.class.getResourceAsStream("/img/beauty.jpg"))
		));
		scroll.getVerticalScrollBar().setUnitIncrement(25);
		scroll.setPreferredSize(new Dimension(400, 500));

		return scroll;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CentredBackgroundBorderTest());
		});
	}
}