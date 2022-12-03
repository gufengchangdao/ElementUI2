package com.element.swing;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class StickyTest extends AbstractDemo {

	@Override
	public String getName() {
		return "StickyTest";
	}

	@Override
	public String getDescription() {
		return """
				Sticky是一个辅助类，用于在鼠标移动时使 JList 或 JTree 或 JTable 更改选择。要使用它，您只需调用
				new Sticky(component);
				""";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		JList<String> list = new JList<>(new String[]{"1", "2", "3", "4", "5", "6"});
		new Sticky(list);
		p.add(list);

		JTree tree = new JTree();
		new Sticky(tree);
		p.add(tree);

		JTable table = new JTable(
				new String[][]{{"1", "2", "3"}, {"a", "b", "c"}},
				new String[]{"A", "B", "C"});
		new Sticky(table);
		p.add(new JScrollPane(table));

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new StickyTest());
		});
	}
}