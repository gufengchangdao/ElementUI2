package com.element.ui.navigation;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class NavigationComponentHelperTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		JList<String> list = new JList<>(DemoData.NAMES) {
			final NavigationComponentHelper helper = new NavigationComponentHelper(this) {
				@Override
				protected Rectangle getRowBounds(int row) {
					return getCellBounds(row, row);
				}

				@Override
				protected int rowAtPoint(Point p) {
					return locationToIndex(p);
				}

				@Override
				protected int[] getSelectedRows() {
					return getSelectedIndices();
				}
			};

			@Override
			public void paint(Graphics g) {
				super.paintComponent(g);
				helper.paint(g);
			}
		};

		p.add(list);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new NavigationComponentHelperTest());
		});
	}

}