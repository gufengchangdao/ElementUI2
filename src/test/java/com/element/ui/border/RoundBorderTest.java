package com.element.ui.border;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.base.BaseInputField;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.regular.MagnifyingGlassSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.prompt.BuddySupport;

import javax.swing.*;
import java.awt.*;

public class RoundBorderTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());
		JXTextField field = new BaseInputField("请输入关键词");
		field.setColumns(20);
		field.setBorder(new RoundBorder(field,ColorUtil.BORDER_LEVEL1, 10, new Insets(5, 10, 5, 10)));
		JButton searchButton = new JButton(IconsFactory.getSvgIcon(MagnifyingGlassSvg.class, 16, 16, ColorUtil.PRIMARY));
		searchButton.addActionListener(e -> System.out.println("搜素结果"));
		searchButton.setBorderPainted(false);
		searchButton.setFocusPainted(false);

		field.addBuddy(searchButton, BuddySupport.Position.RIGHT);

		p.add(field);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new RoundBorderTest());
		});
	}
}