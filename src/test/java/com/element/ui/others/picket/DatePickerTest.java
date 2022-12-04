package com.element.ui.others.picket;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXMonthView;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class DatePickerTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		DatePicker view = new DatePicker(10, "选择日期");
		JXMonthView monthView = view.getMonthView();
		// 是否可遍历
		monthView.setTraversable(true);

		JButton button = new JButton("获取");
		button.addActionListener(e -> {
			System.out.println(view.getText());
			Calendar c = view.getCalendar();
			if (c != null)
				System.out.println(view.getCalendar().get(Calendar.DAY_OF_MONTH));
		});

		p.add(view);
		p.add(button);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new DatePickerTest());
		});
	}
}