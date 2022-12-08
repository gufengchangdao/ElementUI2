package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class NorthEastSouthWestSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public NorthEastSouthWestSvg() {
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
		generalPath.moveTo(14.73f, 12.07f);
		generalPath.lineTo(10.66f, 16.14f);
		generalPath.lineTo(13.49f, 18.97f);
		generalPath.lineTo(5.01f, 18.95f);
		generalPath.lineTo(5.0f, 10.48f);
		generalPath.lineTo(7.84f, 13.32f);
		generalPath.lineTo(11.92f, 9.26f);
		generalPath.lineTo(13.33f, 7.84f);
		generalPath.lineTo(10.49f, 5.0f);
		generalPath.lineTo(18.97f, 5.0f);
		generalPath.lineTo(18.97f, 13.48f);
		generalPath.lineTo(16.14f, 10.66f);
		generalPath.lineTo(14.73f, 12.07f);
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
		generalPath.moveTo(13.67f, 11.72f);
		generalPath.lineTo(9.24f, 16.14f);
		generalPath.lineTo(11.07f, 17.97f);
		generalPath.lineTo(6.01f, 17.95f);
		generalPath.lineTo(6.0f, 12.9f);
		generalPath.lineTo(7.84f, 14.74f);
		generalPath.lineTo(12.27f, 10.32f);
		generalPath.lineTo(14.74f, 7.84f);
		generalPath.lineTo(12.9f, 6.0f);
		generalPath.lineTo(17.97f, 6.0f);
		generalPath.lineTo(17.97f, 11.07f);
		generalPath.lineTo(16.14f, 9.24f);
		generalPath.lineTo(13.67f, 11.72f);
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
		return 3.200000047683716;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 4.199999809265137;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 17.56999969482422;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 17.56999969482422;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		NorthEastSouthWestSvg base = new NorthEastSouthWestSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		NorthEastSouthWestSvg base = new NorthEastSouthWestSvg();
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
		return NorthEastSouthWestSvg::new;
	}
}

