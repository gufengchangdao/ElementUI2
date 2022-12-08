package com.element.ui.others.select;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.rate.RateSelectorTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CascaderSelectorTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());
		HashMap<String, Map<String, ?>> childNode2 = new HashMap<>();
		childNode2.put("大苹果", null);
		childNode2.put("中苹果", null);
		childNode2.put("小苹果", null);

		Map<String, Map<String, ?>> childNode1 = new HashMap<>();
		childNode1.put("甜苹果", childNode2);
		childNode1.put("酸苹果", null);
		childNode1.put("青苹果", null);

		HashMap<String, Map<String, ?>> map = new HashMap<>();
		map.put("苹果", childNode1);
		map.put("香蕉", null);
		map.put("梨", null);

		CascaderSelector main = new CascaderSelector(15, "，", map);
		p.add(main);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new CascaderSelectorTest());
		});
	}
}