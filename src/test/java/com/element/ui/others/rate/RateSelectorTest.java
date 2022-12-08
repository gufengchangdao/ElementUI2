package com.element.ui.others.rate;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.ui.svg.icon.fill.ThumbsUpSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class RateSelectorTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		Container p = new JPanel(new MigLayout("wrap 1, center"));

		RateSelector r1 = new RateSelector();
		r1.setSelectedCount(5);
		p.add(r1);

		RateSelector r2 = new RateSelector();
		r2.setIcon(ThumbsUpSvg.of(16, 16));
		r2.setSelectedCount(2);
		p.add(r2);

		RateSelector r3 = new RateSelector();
		r3.setIcon(ThumbsUpSvg.of(16, 16));
		r3.setTurnColor(true);
		r3.setSelectedCount(2);
		p.add(r3);

		RateSelector r4 = new RateSelector();
		r4.setIcon(SwordSvg.of(32, 32));
		// r2.setIcon(ThumbsUpSvg.of(16, 16));
		// 设置图标颜色渐变
		r4.setTurnColor(true);
		// 设置初始
		r4.setSelectedCount(2);
		// 设置右侧提示文本，文本宽度依据最长字符串而定
		r4.setTipList(Arrays.asList("0分", "1分", "2分", "3分", "4分", "超级满分结果"));
		p.add(r4);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new RateSelectorTest());
		});
	}
}