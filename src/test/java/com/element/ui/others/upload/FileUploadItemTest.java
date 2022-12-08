package com.element.ui.others.upload;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.select.MultiSelectorTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;

public class FileUploadItemTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		JList<File> list = new JList<>(new File[]{new File("file1.png"), new File("file2.png"), new File("file3.png")});
		list.setCellRenderer(new FileUploadItem());
		JButton b = new JButton("添加");
		b.addActionListener(e -> {
		});
		p.add(list);
		p.add(b);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new FileUploadItemTest());
		});
	}
}