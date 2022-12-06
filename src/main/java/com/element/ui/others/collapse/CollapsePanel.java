package com.element.ui.others.collapse;

import com.element.swing.compo.BaseComponent;
import org.jdesktop.swingx.VerticalLayout;

import java.util.List;

/**
 * 折叠面板
 * <p>
 * 通过折叠面板收纳内容区域
 */
public class CollapsePanel extends BaseComponent {
	private final List<CollapseItem> items;
	// private

	public CollapsePanel(List<CollapseItem> items) {
		this.items = items;
		init();
	}

	private void init() {
		setLayout(new VerticalLayout(5));
		for (CollapseItem item : items) {
			add(item);
		}
	}
}

