package com.element.ui.progress;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.pagination.IconPaginationListTest;
import com.element.ui.svg.icon.fill.CircleWavyCheckSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class CircleProgressTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		Container p = new JPanel(new MigLayout("wrap 2", "grow"));

		CircleProgress progress = new CircleProgress();

		new Thread(() -> {
			try {
				for (int i = 0; i < 101; i++) {
					progress.setValue(i);
					TimeUnit.MILLISECONDS.sleep(30);
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).start();
		// 图标
		progress.setLabel(new JLabel(CircleWavyCheckSvg.of(32, 32)));
		progress.setLineWidth(20);
		p.add(progress, "center");

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CircleProgressTest());
		});
	}
}