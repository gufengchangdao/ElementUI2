package com.element.ui.label;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;

import javax.swing.*;
import java.awt.*;

public class DropCapLabelTest extends AbstractDemo {
	public static final String TEXT = "他如今已经抵达了旅途的尽头——他所要完成的、所要见证的、所要救赎的……它们已经在虚数之树中生根发芽，" +
			"只等待着那迷路的信使，将最后的消息在一切都结束前送达。那一刻，不会太早，也不会太晚，它会成为跨越死亡的镇魂曲，" +
			"它会成为奇迹降临的赞美诗。世界将在那一刻只为了一个人而转动……让那被强加的罪孽烟消云散，让那被终结的意志继续向前。";
	@Override
	public Component getDemoPanel() throws Exception {
		DropCapLabel label = new DropCapLabel(TEXT);
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		label.setPreferredSize(new Dimension(320, 240));
		label.setBorder(BorderFactory.createLineBorder(new Color(0x64_64_C8_C8, true), 10));

		return label;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new DropCapLabelTest());
		});
	}
}