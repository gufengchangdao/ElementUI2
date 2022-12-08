package com.element.ui.others.upload;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.select.CascaderSelectorTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static org.junit.Assert.*;

public class FileUploadPanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		Container p = new JPanel(new MigLayout("wrap 1,center"));

		JList<File> list = new JList<>(new File[]{new File("file1.png"), new File("file2.png"), new File("file3.png")});
		list.setCellRenderer(new FileUploadItem());
		p.add(list);

		p.add(new FileUploadPanel("上传文件", "文件大小不要超过20M"));
		return p;
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new FileUploadPanelTest());
		});
	}
}