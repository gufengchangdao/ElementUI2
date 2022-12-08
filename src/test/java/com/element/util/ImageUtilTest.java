package com.element.util;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.base.DefaultTableModel2;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static com.element.util.ImageUtil.makeAnimatedIcon;
import static com.element.util.ImageUtil.makeImageIcon;

public class ImageUtilTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		// 下拉列表
		JComboBox<Icon> combo = new JComboBox<>();
		Toolkit tk = combo.getToolkit();
		ImageIcon icon = new ImageIcon(tk.getImage(ImageUtilTest.class.getResource("do_not_dispose.gif")));
		makeAnimatedIcon(icon, combo, 1);
		Icon[] icons = {new ImageIcon(tk.getImage(ImageUtilTest.class.getResource("00.png"))), icon};
		combo.setModel(new DefaultComboBoxModel<>(icons));
		p.add(combo);

		// 表格
		JTable table = new JTable();
		ImageIcon icon2 = new ImageIcon(tk.getImage(ImageUtilTest.class.getResource("do_not_dispose.gif")));
		ImageIcon icon3 = new ImageIcon(tk.getImage(ImageUtilTest.class.getResource("do_not_dispose.gif")));
		Object[][] data = {
				{"Default ImageIcon", icon2},
				{"ImageIcon#setImageObserver", makeImageIcon(icon3, table, 1, 1)}};
		String[] columnNames = {"String", "ImageIcon"};
		table.setModel(new DefaultTableModel2(data, columnNames));
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(20);
		p.add(new JScrollPane(table));

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ImageUtilTest());
		});
	}
}