package com.element.ui.spinner;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import com.element.util.WrapperUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class DateSpinnerTest extends AbstractDemo {
	@Override
	public String getDescription() {
		return """
				This is a demo of DateSpinner. It can be used to display and edit Date type using a DateFormat string\
				 that can representa date or time.
				Demoed classes:
				com.jidesoft.spinner.DateSpinner""";
	}

	@Override
	public Component getDemoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1, 10, 10));

		DateSpinner date = new DateSpinner("MM/dd/yyyy");
		panel.add(date);

		DateSpinner time = new DateSpinner("hh:mm:ssa", Calendar.getInstance().getTime());
		panel.add(time);

		return WrapperUtil.createTopPanel(panel);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new DateSpinnerTest());
		});
	}

}