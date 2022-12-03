package com.element.radiance.common.api.icon;

import com.element.swing.EmptyGraphics2D;

import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.Stack;

/**
 * 核心图标界面的扩展的抽象类，用于减少生成的代码体积，将固定代码放在该类中。
 * <p>
 * 该类的静态方法不应该被调用，只用于呈现子类的同名实现类的方法注释
 * <p>
 * 子类由 Radiance SVG转码器自动生成
 * <a href="https://github.com/kirill-grouchnikov/radiance">Radiance SVG transcoder</a>
 */
public abstract class AbstractSvgIcon implements SvgIcon {
	protected Shape shape = null;
	protected GeneralPath generalPath = null;
	protected Paint paint = null;
	protected Stroke stroke = null;
	protected Shape clip = null;
	protected ColorFilter colorFilter = null;
	protected Stack<AffineTransform> transformsStack = new Stack<>();

	/** 此图标的当前宽度 */
	protected int width;

	/** 此图标的当前高度 */
	protected int height;

	/**
	 * 创建一个新的转码SVG图像。这被标记为private是为了表明应用程序代码应该使用{@link #of(int, int)}方法来获取预配置的实例。
	 */
	public AbstractSvgIcon() {
	}

	protected float getOrigAlpha(Graphics2D g) {
		float origAlpha = 1.0f;
		Composite origComposite = g.getComposite();
		if (origComposite instanceof AlphaComposite origAlphaComposite) {
			if (origAlphaComposite.getRule() == AlphaComposite.SRC_OVER) {
				origAlpha = origAlphaComposite.getAlpha();
			}
		}
		return origAlpha;
	}

	protected void innerPaint(Graphics2D g) {
		// shape = null;
		generalPath = null;
		paint = null;
		stroke = null;
		clip = null;
		transformsStack.clear();
	}

	@Override
	public ColorFilter getColorFilter() {
		return colorFilter;
	}

	@Override
	public void setColorFilter(ColorFilter colorFilter) {
		this.colorFilter = colorFilter;
	}

	@Override
	public int getIconHeight() {
		return height;
	}

	@Override
	public int getIconWidth() {
		return width;
	}


	@Override
	public synchronized void setDimension(Dimension newDimension) {
		this.width = newDimension.width;
		this.height = newDimension.height;
	}


	/**
	 * Returns a new instance of this icon with specified dimensions.
	 *
	 * @param width  Required width of the icon
	 * @param height Required height of the icon
	 * @return A new instance of this icon with specified dimensions.
	 */
	public static SvgIcon of(int width, int height) {
		return null;
	}

	/**
	 * Returns a new {@link UIResource} instance of this icon with specified dimensions.
	 *
	 * @param width  Required width of the icon
	 * @param height Required height of the icon
	 * @return A new {@link UIResource} instance of this icon with specified dimensions.
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		return null;
	}

	/**
	 * Returns a factory that returns instances of this icon on demand.
	 *
	 * @return Factory that returns instances of this icon on demand.
	 */
	public static Factory factory() {
		return null;
	}


	public Graphics2D paint(Component c, Graphics g, int x, int y,
	                        double origWidth, double origHeight, double origX, double origY) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.translate(x, y);

		double coef1 = width / origWidth;
		double coef2 = height / origHeight;
		double coef = Math.min(coef1, coef2);
		g2d.clipRect(0, 0, width, height);
		// 按照最小的倍率缩小和放大
		g2d.scale(coef, coef);
		// 去除偏移量。缩放后图标相对于左上角的偏移量也会缩小和放大，将绘制起点回到左上角
		g2d.translate(-origX, -origY);
		if (coef1 != coef2) {
			if (coef1 < coef2) {
				// 高度没有缩放到给定的height，这里重新设置起点，使得图标垂直居中
				double extraDy = (height / coef - origHeight) / 2.0;
				g2d.translate(0, extraDy);
			} else {
				double extraDx = (width / coef - origWidth) / 2.0;
				g2d.translate(extraDx, 0);
			}
		}
		Graphics2D g2 = (Graphics2D) g2d.create();
		g2d.dispose();
		return g2;
	}

	/**
	 * 返回原始SVG图像边界框的X。
	 *
	 * @return 原始SVG图像边界框的X
	 */
	public static double getOrigX() {
		return 0;
	}

	/**
	 * 返回原始SVG图像边界框的Y
	 *
	 * @return 原始SVG图像边界框的Y
	 */
	public static double getOrigY() {
		return 0;
	}

	/**
	 * 返回原始SVG图像的边界框的宽度。
	 *
	 * @return 原始SVG图像的边界框的宽度
	 */
	public static double getOrigWidth() {
		return 0;
	}

	/**
	 * 返回原始SVG图像的边界框的高度
	 *
	 * @return 原始SVG图像的边界框的高度
	 */
	public static double getOrigHeight() {
		return 0;
	}

	public Shape getShape() {
		if (shape == null) innerPaint(EmptyGraphics2D.EMPTY_Graphics2D);
		return shape;
	}
}
