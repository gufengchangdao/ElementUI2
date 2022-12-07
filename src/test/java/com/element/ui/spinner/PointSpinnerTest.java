package com.element.ui.spinner;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import com.element.util.WrapperUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class PointSpinnerTest extends AbstractDemo {
	@Override
	public String getDescription() {
		return """
				This is a demo of PointSpinner. It can be used to display and edit Point type.a date or time.
				Demoed classes:
				com.jidesoft.spinner.PointSpinner""";
	}

	public Component getDemoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1, 10, 10));

		final PointSpinner pointSpinner = new PointSpinner();
		panel.add(pointSpinner);

		return WrapperUtil.createTopPanel(panel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new PointSpinnerTest());
		});
	}

}