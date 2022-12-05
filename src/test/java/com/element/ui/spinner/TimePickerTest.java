package com.element.ui.spinner;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class TimePickerTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, 2022);
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, 2023);
		Calendar c3 = Calendar.getInstance();
		c3.set(Calendar.YEAR, 2024);
		TimePicker timePicker = new TimePicker(new Calendar[]{c1, c2, c3});

		p.add(timePicker);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TimePickerTest());
		});
	}
}