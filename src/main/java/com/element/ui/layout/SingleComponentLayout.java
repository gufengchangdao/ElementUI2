package com.element.ui.layout;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * 单一组件的布局管理器，可设置外边距
 * <p>
 * 不应该添加第二个组件
 */
public class SingleComponentLayout implements LayoutManager2 {
	private Insets margin = new Insets(0, 0, 0, 0);

	public SingleComponentLayout() {
	}

	public SingleComponentLayout(Insets margin) {
		this.margin = margin;
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {

	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return null;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

	@Override
	public void addLayoutComponent(String name, Component comp) {

	}

	@Override
	public void removeLayoutComponent(Component comp) {

	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return Optional.ofNullable(parent.getComponent(0)).map(c -> {
			Dimension size = c.getPreferredSize();
			Insets insets = parent.getInsets();
			size.width += insets.left + insets.right + margin.left + margin.right;
			size.height += insets.top + insets.bottom + margin.top + margin.bottom;
			return size;
		}).orElseGet(Dimension::new);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	@Override
	public void layoutContainer(Container parent) {
		Component c = parent.getComponent(0);
		c.setSize(c.getPreferredSize());
		if (parent instanceof JComponent) {
			Dimension size = c.getSize();
			Insets insets = parent.getInsets();
			// 不要居中了，改为外边距控制
			// Rectangle r = SwingUtilities.calculateInnerArea((JComponent) container, null);
			// int x = r.x + (r.width - size.width) / 2;
			// int y = r.y + (r.height - size.height) / 2;
			int x = insets.left + margin.left;
			int y = insets.top + margin.top;
			c.setBounds(x, y, size.width, size.height);
		}
	}

	public Insets getMargin() {
		return margin;
	}
}
