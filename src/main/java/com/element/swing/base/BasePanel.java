package com.element.swing.base;

import com.element.util.UIUtil;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.util.GraphicsUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 支持的功能
 *
 * <ul>
 *     <li>该类继承JXPanel，因此支持滚动、alpha设置、{@link Painter}</li>
 *     <li>对于渐变色背景，既可以JXPanel的{@link #setBackgroundPainter(Painter)}，这个方法也更灵活，也可以使用简便方法
 *     {@link #setGradientPaint(Color, Color, boolean)}，该方法只支持两种颜色、矩形背景还有水平竖直两个方向</li>
 * </ul>
 */
public class BasePanel extends JXPanel {
	// 渐变色简单的绘制方法
	protected Color startColor;
	protected Color endColor;
	protected boolean isVertical;

	public BasePanel() {
	}

	public BasePanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public BasePanel(LayoutManager layout) {
		super(layout);
	}

	public BasePanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	/**
	 * 此方法允许您在不使用setBackgroundPaint(Paint)方法的情况下使用渐变背景。您可以使用 {@link #setBackgroundPainter(Painter)}
	 * 来做同样的事情，后者会更加灵活。但是，如果您使用此方法，它将使用 UIUtil 中定义的快速渐变绘制来进行绘制。
	 *
	 * @param startColor 渐变的开始颜色
	 * @param endColor   渐变的结束颜色
	 * @param isVertical 是否为垂直绘制
	 */
	public void setGradientPaint(Color startColor, Color endColor, boolean isVertical) {
		this.startColor = startColor;
		this.endColor = endColor;
		this.isVertical = isVertical;
	}


	public static TexturePaint createTexturePaint(JPanel panel, Image img, int x, int y, int w, int h) {
		BufferedImage bi = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, x, y, Color.white, panel);
		Rectangle r = new Rectangle(0, 0, w, h);
		return new TexturePaint(bi, r);
	}

	@Override
	protected void paintComponent(Graphics g) {
		//放前面防止非不透明的时候被覆盖掉了
		super.paintComponent(g);

		// 如果没有使用Painter并且设置了startColor和endColor才会绘制
		if (getBackgroundPainter() == null && startColor != null && endColor != null) {
			Graphics2D g2 = (Graphics2D) g;
			UIUtil.fillGradient(g2, new Rectangle(0, 0, getWidth(), getHeight()), startColor, endColor, isVertical);
		}
	}

	public Color getStartColor() {
		return startColor;
	}

	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}

	public Color getEndColor() {
		return endColor;
	}

	public void setEndColor(Color endColor) {
		this.endColor = endColor;
	}

	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean vertical) {
		isVertical = vertical;
	}
}
