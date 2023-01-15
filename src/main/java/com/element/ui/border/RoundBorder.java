package com.element.ui.border;

import com.element.color.ColorUtil;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * 圆角边框
 * <p>
 * 支持修改边框属性
 */
public class RoundBorder extends AbstractBorder {
	private Color color = ColorUtil.PRIMARY;
	/** 圆角大小，为-1时取最大值 */
	private int arcSize;
	/** 边框的内边距，注意这个不是组件的内边距 */
	private Insets borderInset = new Insets(3, 7, 3, 7);
	/** 设置边框的组件，修改属性后需要调用其重绘方法 */
	private final JComponent c;

	/**
	 * @param color   边框颜色
	 * @param arcSize 圆角大小，为-1时取最大值
	 */
	public RoundBorder(JComponent c, Color color, int arcSize) {
		this.c = c;
		this.color = color;
		this.arcSize = arcSize;
	}

	public RoundBorder(JComponent c) {
		this.c = c;
	}

	public RoundBorder(JComponent c, int arcSize) {
		this.c = c;
		this.arcSize = arcSize;
	}

	public RoundBorder(JComponent c, int arcSize, Insets borderInset) {
		this.c = c;
		this.arcSize = arcSize;
		this.borderInset = borderInset;
	}

	public RoundBorder(JComponent c, Color color, int arcSize, Insets borderInset) {
		this.c = c;
		this.color = color;
		this.arcSize = arcSize;
		this.borderInset = borderInset;
	}

	// 实现Border（父类）方法
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(color);
		g2.drawRoundRect(1, 1, c.getWidth() - 2, c.getHeight() - 2,
				arcSize < 0 ? height : arcSize, arcSize < 0 ? height : arcSize);
		g2.dispose();
	}

	public Color getColor() {
		return color;
	}

	public int getArcSize() {
		return arcSize;
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.top += borderInset.top;
		insets.bottom += borderInset.bottom;
		insets.left += borderInset.left;
		insets.right += borderInset.right;
		return insets;
	}

	public Insets getBorderInset() {
		return borderInset;
	}

	public void setColor(Color color) {
		this.color = color;
		c.repaint();
	}

	public void setArcSize(int arcSize) {
		this.arcSize = arcSize;
		c.repaint();
	}
}
