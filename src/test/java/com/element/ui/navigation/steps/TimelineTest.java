package com.element.ui.navigation.steps;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TimelineTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());

		ArrayList<String> text = new ArrayList<>();
		text.add("创建成功");
		text.add("通过审核");
		text.add("活动按期开始");
		ArrayList<String> date = new ArrayList<>();
		date.add("2018-04-11");
		date.add("2018-04-13");
		date.add("2018-04-15");

		ArrayList<StepInfo> items = StepsComponent.createItems(text, SwordSvg.class, date,
				100, false);
		// 正序，对列表排序
		items.sort(Comparator.comparing(o -> o.getText().getText()));
		StepsComponent c = new StepsComponent(items, 0, ColorUtil.SUCCESS, false);

		JButton b1 = new JButton("正序");
		JButton b2 = new JButton("倒叙");
		b1.addActionListener(e -> {
			java.util.List<StepInfo> i = c.getItems();
			i.sort(Comparator.comparing(o -> o.getDescription().getText()));
			c.setItems(i); //虽然列表没变，但是该方法会重新布局
		});
		b2.addActionListener(e -> {
			List<StepInfo> i = c.getItems();
			i.sort((o1, o2) -> o2.getDescription().getText().compareTo(o1.getDescription().getText()));
			c.setItems(i); //虽然列表没变，但是该方法会重新布局
		});

		p.add(c);
		p.add(b1);
		p.add(b2);
		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TimelineTest());
		});
	}
}