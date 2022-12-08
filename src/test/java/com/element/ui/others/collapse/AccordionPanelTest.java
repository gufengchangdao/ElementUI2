package com.element.ui.others.collapse;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.collapse.expandable.ExpandablePanelTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class AccordionPanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p1 = AccordionContentPanel.getDefaultPanel();
		p1.setLayout(new GridLayout(0, 1));
		Stream.of("1111", "222222")
				.map(JCheckBox::new)
				.forEach(b -> {
					b.setOpaque(false);
					p1.add(b);
				});

		JPanel p2 = AccordionContentPanel.getDefaultPanel();
		p2.setLayout(new GridLayout(0, 1));
		Stream.of("Desktop", "My Network Places", "My Documents", "Shared Documents")
				.map(JLabel::new)
				.forEach(p2::add);

		JPanel p3 = AccordionContentPanel.getDefaultPanel();
		p3.setLayout(new GridLayout(0, 1));
		ButtonGroup bg = new ButtonGroup();
		Stream.of("aaa", "bbb", "ccc", "ddd")
				.map(JRadioButton::new)
				.forEach(b -> {
					b.setSelected(p3.getComponentCount() == 0);
					b.setOpaque(false);
					p3.add(b);
					bg.add(b);
				});

		AccordionPanel c = new AccordionPanel(Arrays.asList(p1, p2, p3),
				Arrays.asList("System Tasks", "Other Places", "Details"),
				Color.WHITE, new Color(0xC8_C8_FF));
		c.setBackground(new Color(0xB4_B4_FF));
		// 设置标题字体色
		// for (ExpansionPanel.Item item : c.getItems()) {
		// 	item.getTitlePanel().setForeground(Color.BLUE);
		// }

		return c.getScrollPane();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AccordionPanelTest());
		});
	}
}