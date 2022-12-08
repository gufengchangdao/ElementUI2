package com.element.ui.others.tag;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.button.IconButton;
import com.element.ui.svg.icon.regular.XCircleSvg;
import com.element.ui.table.FixedColumnTablePanelTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.NORTH;
import static org.junit.Assert.*;

public class TagTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());
		SvgIcon icon1 = XCircleSvg.of(16, 16);
		SvgIcon icon2 = XCircleSvg.of(16, 16);
		SvgIcon.ColorFilter filter1 = color -> ColorUtil.PRIMARY;
		icon1.setColorFilter(filter1);
		icon2.setColorFilter(filter1);
		SvgIcon.ColorFilter filter2 = color -> ColorUtil.blend(ColorUtil.PRIMARY, Color.WHITE, 0.3f);
		IconButton closeButton = new IconButton(icon1, icon2, filter2);

		Tag tag = new Tag("标签一",
				ColorUtil.PRIMARY, ColorUtil.blend(ColorUtil.PRIMARY, Color.WHITE, 0.8f),
				closeButton, BorderLayout.EAST);
		Tag tag2 = new Tag("标签一",
				ColorUtil.PRIMARY, ColorUtil.changeAlpha(ColorUtil.blend(ColorUtil.PRIMARY, Color.WHITE, 0.6f), .5f),
				null, NORTH);

		JButton button = new JButton("修改");
		button.addActionListener(e -> {
			tag.setFont(tag.getFont().deriveFont(30f));
			tag2.setFg(Color.RED);
			SwingTestUtil.getFrame().getContentPane().validate();
		});

		p.add(tag);
		p.add(tag2);
		p.add(button);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TagTest());
		});
	}
}