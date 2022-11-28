package com.element.hints;

import com.element.plaf.LookAndFeelFactory;
import demo.AbstractDemo;
import demo.SwingTestUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AbstractListIntelliHintsTest extends AbstractDemo {

	@Override
	public String getName() {
		return "AbstractListIntelliHintsTest";
	}

	@Override
	public String getDescription() {
		return "文本组件智能补全功能\n补全功能与组件没有耦合性，JTextComponent都可以使用\n智能提示可以很容易地进行扩展";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		p.add(new JLabel("补全路径"));
		JTextField pathTextField = new JTextField("C://", 30);
		FileIntelliHints intelliHints = new FileIntelliHints(pathTextField);
		intelliHints.setFolderOnly(true);
		p.add(pathTextField);

		p.add(new JLabel("补全url"));
		JTextField urlTextField = new JTextField("https://", 30);
		List<String> urls = List.of(
				"https://www.bilibili.com/", "https://github.com/", "https://www.yxgapp.com/", "https://cloud.tencent.com/"
		);
		ListDataIntelliHints<String> intellihints = new ListDataIntelliHints<>(urlTextField, urls);
		intellihints.setCaseSensitive(false);
		p.add(urlTextField);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AbstractListIntelliHintsTest());
		});
	}
}