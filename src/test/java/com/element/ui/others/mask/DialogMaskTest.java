package com.element.ui.others.mask;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.line.TitledSeparatorTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class DialogMaskTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 300));
		panel.setBackground(ColorUtil.BORDER_LEVEL1);
		JButton b = new JButton("打开遮罩");
		panel.add(b);

		b.addActionListener(e -> {
			// 遮罩面板
			JPanel m = new JPanel(new BorderLayout());
			m.add(new JLabel("这是遮罩面板"));
			m.setBackground(ColorUtil.changeAlpha(ColorUtil.PRIMARY, .5f));
			DialogMask mask = new DialogMask(panel, m);
			// 定时关闭用Timer或SwingWorker实现
			new Timer(4000, e1 -> mask.closeMask()).start();
			// new SwingWorker<String, Void>() {
			// 	@Override
			// 	protected String doInBackground() throws Exception {
			// 		Thread.sleep(4000);
			// 		return "Done";
			// 	}
			//
			// 	@Override
			// 	protected void done() {
			// 		mask.closeMask();
			// 	}
			// }.execute();
			mask.openMask();
		});

		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TitledSeparatorTest());
		});
	}
}