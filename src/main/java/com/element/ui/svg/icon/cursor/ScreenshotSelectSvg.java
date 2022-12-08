package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class ScreenshotSelectSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public ScreenshotSelectSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}


	private void _paint0(Graphics2D g, float origAlpha) {
// 
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0
		g.setComposite(AlphaComposite.getInstance(3, 0.72f * origAlpha));
// _0_0
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(13.0f, 2.0f);
		generalPath.lineTo(14.0f, 2.0f);
		generalPath.lineTo(14.0f, 13.0f);
		generalPath.lineTo(13.0f, 13.0f);
		generalPath.lineTo(13.0f, 2.0f);
		generalPath.closePath();
		generalPath.moveTo(13.0f, 14.0f);
		generalPath.lineTo(14.0f, 14.0f);
		generalPath.lineTo(14.0f, 25.0f);
		generalPath.lineTo(13.0f, 25.0f);
		generalPath.lineTo(13.0f, 14.0f);
		generalPath.closePath();
		generalPath.moveTo(2.0f, 13.0f);
		generalPath.lineTo(13.0f, 13.0f);
		generalPath.lineTo(13.0f, 14.0f);
		generalPath.lineTo(2.0f, 14.0f);
		generalPath.lineTo(2.0f, 13.0f);
		generalPath.closePath();
		generalPath.moveTo(14.0f, 13.0f);
		generalPath.lineTo(25.0f, 13.0f);
		generalPath.lineTo(25.0f, 14.0f);
		generalPath.lineTo(14.0f, 14.0f);
		generalPath.lineTo(14.0f, 13.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(35, 31, 32, 255)) : new Color(35, 31, 32, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 0.15f * origAlpha));
// _0_1
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(19.0f, 13.5f);
		generalPath.curveTo(19.0f, 16.5376f, 16.5376f, 19.0f, 13.5f, 19.0f);
		generalPath.curveTo(10.4624f, 19.0f, 8.0f, 16.5376f, 8.0f, 13.5f);
		generalPath.curveTo(8.0f, 10.4624f, 10.4624f, 8.0f, 13.5f, 8.0f);
		generalPath.curveTo(16.5376f, 8.0f, 19.0f, 10.4624f, 19.0f, 13.5f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(35, 31, 32, 255)) : new Color(35, 31, 32, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 0.3f * origAlpha));
// _0_2
		paint = (colorFilter != null) ? colorFilter.filter(new Color(35, 31, 32, 255)) : new Color(35, 31, 32, 255);
		stroke = new BasicStroke(1.0f, 0, 0, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(13.5f, 19.0f);
		generalPath.curveTo(16.5376f, 19.0f, 19.0f, 16.5376f, 19.0f, 13.5f);
		generalPath.curveTo(19.0f, 10.4624f, 16.5376f, 8.0f, 13.5f, 8.0f);
		generalPath.curveTo(10.4624f, 8.0f, 8.0f, 10.4624f, 8.0f, 13.5f);
		generalPath.curveTo(8.0f, 16.5376f, 10.4624f, 19.0f, 13.5f, 19.0f);
		generalPath.closePath();
		shape = generalPath;
		g.setPaint(paint);
		g.setStroke(stroke);
		g.draw(shape);

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
		return 2.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 2.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 23.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 23.0;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		ScreenshotSelectSvg base = new ScreenshotSelectSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		ScreenshotSelectSvg base = new ScreenshotSelectSvg();
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
		return ScreenshotSelectSvg::new;
	}
}

