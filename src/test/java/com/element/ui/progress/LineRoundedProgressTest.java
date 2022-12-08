package com.element.ui.progress;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class LineRoundedProgressTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		Container p = new JPanel(new MigLayout("wrap 2", "grow"));

		LineRoundedProgress pgbar = new LineRoundedProgress();
		pgbar.setStringPainted(true);

		new Thread(() -> {
			try {
				for (int i = 0; i < 101; i++) {
					pgbar.setValue(i);
					TimeUnit.MILLISECONDS.sleep(30);
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).start();
		p.add(pgbar, "center");

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new LineRoundedProgressTest());
		});
	}
}