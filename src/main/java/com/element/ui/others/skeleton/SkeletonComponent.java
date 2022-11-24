package com.element.ui.others.skeleton;

import com.element.color.ColorUtil;
import com.element.ui.base.BaseComponent;
import com.element.util.UIUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 骨架屏
 * <p>
 * 在需要等待加载内容的位置设置一个骨架屏，某些场景下比 Loading 的视觉效果更好。
 */
public class SkeletonComponent extends BaseComponent {
	public static JComponent createHorizontalStrut(int width) {
		SkeletonComponent c = new SkeletonComponent();
		c.setMinimumSize(new Dimension(width, 0));
		c.setPreferredSize(new Dimension(width, 0));
		c.setMaximumSize(new Dimension(width, Short.MAX_VALUE));
		c.setFocusable(false);
		c.setBackground(ColorUtil.BORDER_LEVEL2);

		return c;
	}

	public static JComponent createVerticalStrut(int height) {
		SkeletonComponent c = new SkeletonComponent();
		c.setMinimumSize(new Dimension(0, height));
		c.setPreferredSize(new Dimension(0, height));
		c.setMaximumSize(new Dimension(Short.MAX_VALUE, height));
		c.setFocusable(false);
		c.setBackground(ColorUtil.BORDER_LEVEL2);
		return c;
	}

	public SkeletonComponent() {
	}

	public SkeletonComponent(Dimension size) {
		setPreferredSize(size);
		setBackground(ColorUtil.BORDER_LEVEL2);
		setFocusable(false);
	}

	public SkeletonComponent(Dimension size, Color bg) {
		setPreferredSize(size);
		setBackground(bg);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Color bg = getBackground();
		g.setColor(bg);
		Object[] oldRender = UIUtil.setRenderingHints(g);
		g.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
		UIUtil.resetRenderingHints(g, oldRender);
	}
}
