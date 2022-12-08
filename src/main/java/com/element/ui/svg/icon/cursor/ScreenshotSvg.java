package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class ScreenshotSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public ScreenshotSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}


	private void _paint0(Graphics2D g, float origAlpha) {
// 
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_0
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(6.5f, 21.0f);
		generalPath.curveTo(4.57f, 21.0f, 3.0f, 19.43f, 3.0f, 17.5f);
		generalPath.lineTo(3.0f, 10.5f);
		generalPath.curveTo(3.0f, 8.57f, 4.57f, 7.0f, 6.5f, 7.0f);
		generalPath.lineTo(7.5f, 7.0f);
		generalPath.curveTo(7.966f, 7.0f, 8.387f, 6.49f, 8.605f, 6.053f);
		generalPath.curveTo(9.239f, 4.786f, 10.028f, 4.0f, 12.0f, 4.0f);
		generalPath.lineTo(16.0f, 4.0f);
		generalPath.curveTo(17.972f, 4.0f, 18.761f, 4.786f, 19.395f, 6.053f);
		generalPath.curveTo(19.613f, 6.49f, 20.034f, 7.0f, 20.5f, 7.0f);
		generalPath.lineTo(21.5f, 7.0f);
		generalPath.curveTo(23.43f, 7.0f, 25.0f, 8.57f, 25.0f, 10.5f);
		generalPath.lineTo(25.0f, 17.5f);
		generalPath.curveTo(25.0f, 19.43f, 23.43f, 21.0f, 21.5f, 21.0f);
		generalPath.lineTo(6.5f, 21.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_1
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(14.0f, 18.0f);
		generalPath.curveTo(11.238f, 18.0f, 9.0f, 15.762f, 9.0f, 13.0f);
		generalPath.curveTo(9.0f, 10.238f, 11.238f, 8.0f, 14.0f, 8.0f);
		generalPath.curveTo(16.762f, 8.0f, 19.0f, 10.238f, 19.0f, 13.0f);
		generalPath.curveTo(19.0f, 15.762f, 16.762f, 18.0f, 14.0f, 18.0f);
		generalPath.closePath();
		generalPath.moveTo(21.5f, 8.0f);
		generalPath.lineTo(20.5f, 8.0f);
		generalPath.curveTo(19.5f, 8.0f, 18.816f, 7.132f, 18.5f, 6.5f);
		generalPath.curveTo(18.0f, 5.5f, 17.5f, 5.0f, 16.0f, 5.0f);
		generalPath.lineTo(14.0f, 5.0f);
		generalPath.lineTo(12.0f, 5.0f);
		generalPath.curveTo(10.5f, 5.0f, 10.0f, 5.5f, 9.5f, 6.5f);
		generalPath.curveTo(9.184f, 7.132f, 8.5f, 8.0f, 7.5f, 8.0f);
		generalPath.lineTo(6.5f, 8.0f);
		generalPath.curveTo(5.119f, 8.0f, 4.0f, 9.119f, 4.0f, 10.5f);
		generalPath.lineTo(4.0f, 17.5f);
		generalPath.curveTo(4.0f, 18.881f, 5.119f, 20.0f, 6.5f, 20.0f);
		generalPath.lineTo(21.5f, 20.0f);
		generalPath.curveTo(22.881f, 20.0f, 24.0f, 18.881f, 24.0f, 17.5f);
		generalPath.lineTo(24.0f, 10.5f);
		generalPath.curveTo(24.0f, 9.119f, 22.881f, 8.0f, 21.5f, 8.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_2
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(17.0f, 13.0f);
		generalPath.curveTo(17.0f, 14.656f, 15.656f, 16.0f, 14.0f, 16.0f);
		generalPath.curveTo(12.344f, 16.0f, 11.0f, 14.656f, 11.0f, 13.0f);
		generalPath.curveTo(11.0f, 11.344f, 12.344f, 10.0f, 14.0f, 10.0f);
		generalPath.curveTo(15.656f, 10.0f, 17.0f, 11.344f, 17.0f, 13.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);

	}


	protected void innerPaint(Graphics2D g) {
		float origAlpha = getOrigAlpha(g);

		_paint0(g, origAlpha);


		super.innerPaint(g);
	}

	/**
	 * @see AbstractSvgIcon#getOrigX()
	 */
	public static double getOrigX() {
		return 1.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 3.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 26.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 21.0;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		ScreenshotSvg base = new ScreenshotSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		ScreenshotSvg base = new ScreenshotSvg();
		base.width = width;
		base.height = height;
		return new SvgIconUIResource(base);
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = paint(c, g, x, y, getOrigWidth(), getOrigHeight(), getOrigX(), getOrigY());
		innerPaint(g2);
		g2.dispose();
	}

	/**
	 * @see AbstractSvgIcon#factory()
	 */
	public static Factory factory() {
		return ScreenshotSvg::new;
	}
}

