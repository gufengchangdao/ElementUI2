package com.element.ui.scrollbar;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class TranslucentScrollTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());

		p.add(ScrollBarOnHoverScroll.getLayer(makeList()));
		p.add(TranslucentScroll.getLayer(makeList()));

		return p;
	}

	private static Component makeList() {
		DefaultListModel<String> m = new DefaultListModel<>();
		IntStream.range(0, 50)
				.mapToObj(i -> String.format("%05d: %s", i, LocalDateTime.now(ZoneId.systemDefault())))
				.forEach(m::addElement);
		return new JList<>(m);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TranslucentScrollTest());
		});
	}
}