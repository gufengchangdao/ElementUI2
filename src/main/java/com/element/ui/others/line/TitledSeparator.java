package com.element.ui.others.line;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Objects;
import java.util.Optional;

/**
 * 支持渐变和标题的分割符
 * <p>
 * 支持的功能有
 * <ul>
 *     <li>指定标题的位置，标题字体、颜色</li>
 *     <li>指定分割符的渐变色，支持双层绘制</li>
 * </ul>
 */
public class TitledSeparator extends JLabel {
	protected final String title;
	protected Font titleFont;
	protected Color titleColor;

	protected Color startColor;
	/** 第二段渐变色，如果不传入这个值则使用背景色 */
	protected Color endColor;
	protected final int separatorHeight;
	/** 标题相对于分割符位置，为{@link TitledBorder} 中给定的常量 */
	protected final int titlePosition;
	/** 是否使用双层绘制，如果为true则上一半用深色渐变，下一半用浅色渐变，如果为false则使用startColor到endColor的渐变 */
	protected boolean isDoubleDraw = true;

	/**
	 * @param title         分割符附近文本
	 * @param height        线宽
	 * @param titlePosition 标题相对于分割符位置，为{@link TitledBorder} 中给定的常量
	 */
	public TitledSeparator(String title, int height, int titlePosition) {
		this(title, null, height, titlePosition);
	}

	/**
	 * @param title         分割符附近文本
	 * @param startColor    分割符颜色
	 * @param height        线宽
	 * @param titlePosition 标题相对于分割符位置，为{@link TitledBorder} 中给定的常量
	 */
	public TitledSeparator(String title, Color startColor, int height, int titlePosition) {
		super();
		this.title = title;
		this.startColor = startColor;
		this.separatorHeight = height;
		this.titlePosition = titlePosition;
		updateBorder();
	}

	/**
	 * @param title         分割符附近文本
	 * @param startColor    分割符颜色
	 * @param endColor      结束颜色
	 * @param height        线宽
	 * @param titlePosition 标题相对于分割符位置，为{@link TitledBorder} 中给定的常量
	 */
	public TitledSeparator(String title, Color startColor, Color endColor, int height, int titlePosition) {
		super();
		this.title = title;
		this.startColor = startColor;
		this.endColor = endColor;
		this.separatorHeight = height;
		this.titlePosition = titlePosition;
		updateBorder();
	}

	public TitledSeparator(String title, Font titleFont, Color titleColor,
	                          Color startColor, Color endColor, int height, int titlePosition) {
		super();
		this.title = title;
		this.titleFont = titleFont;
		this.titleColor = titleColor;
		this.startColor = startColor;
		this.endColor = endColor;
		this.separatorHeight = height;
		this.titlePosition = titlePosition;
		updateBorder();
	}

	private void updateBorder() {
		Icon icon = new TitledSeparatorIcon();
		MatteBorder b = BorderFactory.createMatteBorder(separatorHeight, 0, 0, 0, icon);
		if (titleFont != null && titleColor != null)
			setBorder(BorderFactory.createTitledBorder(b, title, TitledBorder.DEFAULT_JUSTIFICATION, titlePosition, titleFont, titleColor));
		else
			setBorder(BorderFactory.createTitledBorder(b, title, TitledBorder.DEFAULT_JUSTIFICATION, titlePosition));
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Short.MAX_VALUE, super.getPreferredSize().height);
	}

	@Override
	public void updateUI() {
		super.updateUI();
		updateBorder();
	}

	private class TitledSeparatorIcon implements Icon {
		private int width = -1;
		private int height;
		private Paint painter1;
		private Paint painter2;

		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			int w = c.getWidth();
			if (w != width || Objects.isNull(painter1) || Objects.isNull(painter2)) {
				width = w;
				Point2D start = new Point2D.Float();
				Point2D end = new Point2D.Float(width, 0f);
				float[] dist = {0f, 1f};
				Color bgc = getBackground();
				Color ec = Optional.ofNullable(endColor)
						.orElse(Optional.ofNullable(bgc).orElse(UIManager.getColor("Panel.background")));
				Color sc = Optional.ofNullable(startColor).orElse(ec);
				if (isDoubleDraw) {
					painter1 = new LinearGradientPaint(start, end, dist, new Color[]{sc.darker(), ec});
					painter2 = new LinearGradientPaint(start, end, dist, new Color[]{sc.brighter(), ec});
				} else {
					painter1 = painter2 = new LinearGradientPaint(start, end, dist, new Color[]{sc, ec});
				}
			}
			Graphics2D g2 = (Graphics2D) g.create();
			if (isDoubleDraw) {
				int h = getIconHeight() / 2;
				g2.setPaint(painter1);
				g2.fillRect(x, y, width, getIconHeight());
				g2.setPaint(painter2);
				g2.fillRect(x, y + h, width, getIconHeight() - h);
			} else {
				g2.setPaint(painter1);
				g2.fillRect(x, y, width, getIconHeight());
			}

			g2.dispose();
		}

		@Override
		public int getIconWidth() {
			return 200; // dummy width
		}

		@Override
		public int getIconHeight() {
			return separatorHeight;
		}
	}

	public boolean isDoubleDraw() {
		return isDoubleDraw;
	}

	public void setDoubleDraw(boolean doubleDraw) {
		isDoubleDraw = doubleDraw;
		repaint();
	}
}
