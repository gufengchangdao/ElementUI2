package com.element.ui.list;

import com.element.converter.ObjectConverterManager;
import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class CalendarListCellRendererTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		Calendar c1 = Calendar.getInstance();
		c1.set(Calendar.YEAR, 2022);
		Calendar c2 = Calendar.getInstance();
		c2.set(Calendar.YEAR, 2023);
		Calendar c3 = Calendar.getInstance();
		c3.set(Calendar.YEAR, 2024);
		JList<Calendar> list = new JList<>(new Calendar[]{c1, c2, c3});
		// list.setCellRenderer(new CalendarListCellRenderer(calendar ->
		// 		String.format("%d年%d月%d日", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
		// 				calendar.get(Calendar.DAY_OF_MONTH))));

		ObjectConverterManager.initDefaultConverter();
		list.setCellRenderer(new CalendarListCellRenderer(ObjectConverterManager::toString));

		p.add(list);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CalendarListCellRendererTest());
		});

	}
}