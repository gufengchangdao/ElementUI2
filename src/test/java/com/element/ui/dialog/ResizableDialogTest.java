package com.element.ui.dialog;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.resize.ResizableDialog;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ResizableDialogTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		ResizableDialog dialog = new ResizableDialog(getFrame(), "可拉伸对话框", true);
		JScrollPane scrollPane = new JScrollPane(new JTextArea(DemoData.LONG_TEXT));
		scrollPane.setPreferredSize(new Dimension(400, 400));
		dialog.getContentPane().add(scrollPane);
		dialog.pack();

		JButton b = new JButton("弹出");
		b.addActionListener(e -> {
			dialog.setLocationRelativeTo(null);
			dialog.setVisible(true);
		});
		p.add(b);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ResizableDialogTest());
		});
	}
}