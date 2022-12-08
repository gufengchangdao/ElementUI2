package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class NorthWestSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public NorthWestSvg() {
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
		generalPath.moveTo(6.0f, 6.0f);
		generalPath.lineTo(6.0f, 14.48f);
		generalPath.lineTo(8.83f, 11.66f);
		generalPath.lineTo(13.78f, 16.61f);
		generalPath.lineTo(16.61f, 13.78f);
		generalPath.lineTo(11.66f, 8.83f);
		generalPath.lineTo(14.49f, 6.0f);
		generalPath.lineTo(6.0f, 6.0f);
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
		generalPath.moveTo(13.78f, 15.19f);
		generalPath.lineTo(8.83f, 10.24f);
		generalPath.lineTo(7.0f, 12.07f);
		generalPath.lineTo(7.0f, 7.0f);
		generalPath.lineTo(12.07f, 7.0f);
		generalPath.lineTo(10.24f, 8.83f);
		generalPath.lineTo(15.19f, 13.78f);
		generalPath.lineTo(13.78f, 15.19f);
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
		return 4.199999809265137;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 5.199999809265137;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 14.210000038146973;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 14.210000038146973;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		NorthWestSvg base = new NorthWestSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		NorthWestSvg base = new NorthWestSvg();
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
		return NorthWestSvg::new;
	}
}

