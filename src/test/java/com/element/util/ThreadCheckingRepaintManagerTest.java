package com.element.util;

import javax.swing.*;
import java.awt.*;

public class ThreadCheckingRepaintManagerTest {
	public static void main(String[] args) {
		RepaintManager.setCurrentManager(new ThreadCheckingRepaintManager());
		// 这里会打印异常信息
		JFrame f = new JFrame();
		EventQueue.invokeLater(() -> {
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			f.setSize(200, 200);
			JButton b = new JButton("按钮");
			b.addActionListener(e -> new Thread(() -> {
				// repaint方法不会打印
				b.repaint();
				// 在非EDT中更新组件，这里也会打印异常信息
				b.setText("新值");
			}).start());
			f.getContentPane().add(b);
			f.setVisible(true);
		});
	}
}