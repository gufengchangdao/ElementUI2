package com.element.ui.others.carousel;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.notice.message.SystemMessageFactoryTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class CarouselCardLayoutPanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 2", "grow"));

		CarouselCardLayoutPanel cards = new CarouselCardLayoutPanel();
		cards.add(new JScrollPane(new JTree()));
		cards.add(new JSplitPane());
		cards.add(new JScrollPane(new JTable(9, 3)));
		cards.add(new JButton("JButton"));
		p.add(cards, "span 2, center");

		JButton b1 = new JButton("上一个");
		JButton b2 = new JButton("下一个");
		b1.addActionListener(e -> cards.last());
		b2.addActionListener(e -> cards.next());
		p.add(b1);
		p.add(b2, "right");
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CarouselCardLayoutPanelTest());
		});
	}
}