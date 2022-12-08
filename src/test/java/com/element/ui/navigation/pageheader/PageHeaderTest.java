package com.element.ui.navigation.pageheader;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.navigation.breadcrumb.BreadcrumbTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class PageHeaderTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		return new PageHeader("详情页面");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new PageHeaderTest());
		});
	}
}