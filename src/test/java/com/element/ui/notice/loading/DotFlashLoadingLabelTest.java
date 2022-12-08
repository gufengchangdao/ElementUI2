package com.element.ui.notice.loading;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class DotFlashLoadingLabelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		DotFlashLoadingLabel label = new DotFlashLoadingLabel();
		label.startAnimation();
		return label;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new DotFlashLoadingLabelTest());
		});
	}
}