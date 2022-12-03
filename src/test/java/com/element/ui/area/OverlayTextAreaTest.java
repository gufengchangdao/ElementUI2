package com.element.ui.area;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class OverlayTextAreaTest extends AbstractDemo {

	@Override
	public String getDescription() {
		return "这里只演示光标所在行背景高亮显示";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("fill"));
		p.setPreferredSize(new Dimension(300, 300));

		OverlayTextArea area = new OverlayTextArea();
		area.setLineHighlight(true);
		p.add(new JScrollPane(area), "growx, growy");

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new OverlayTextAreaTest());
		});
	}
}