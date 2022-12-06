package com.element.ui.notice.loading;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class InfiniteProgressPanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		InfiniteProgressPanel progressPanel = new InfiniteProgressPanel(5);
		progressPanel.setPreferredSize(new Dimension(100, 100));
		JButton b = new JButton("开始");
		b.addActionListener(e -> {
			if (progressPanel.isRunning()) progressPanel.stop();
			else progressPanel.start();
		});
		p.add(b);
		p.add(progressPanel);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new InfiniteProgressPanelTest());
		});
	}
}