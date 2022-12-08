package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class EastWestSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public EastWestSvg() {
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
		generalPath.moveTo(5.41f, 12.0f);
		generalPath.lineTo(9.0f, 8.41f);
		generalPath.lineTo(9.0f, 11.0f);
		generalPath.lineTo(15.0f, 11.0f);
		generalPath.lineTo(15.0f, 8.42f);
		generalPath.lineTo(18.58f, 12.0f);
		generalPath.lineTo(15.0f, 15.59f);
		generalPath.lineTo(15.0f, 13.0f);
		generalPath.lineTo(9.0f, 13.0f);
		generalPath.lineTo(9.0f, 15.59f);
		generalPath.lineTo(5.41f, 12.0f);
		generalPath.closePath();
		generalPath.moveTo(4.0f, 12.0f);
		generalPath.lineTo(10.0f, 18.0f);
		generalPath.lineTo(10.0f, 14.0f);
		generalPath.lineTo(14.0f, 14.0f);
		generalPath.lineTo(14.0f, 18.0f);
		generalPath.lineTo(20.0f, 12.0f);
		generalPath.lineTo(14.0f, 6.0f);
		generalPath.lineTo(14.0f, 10.0f);
		generalPath.lineTo(10.0f, 10.0f);
		generalPath.lineTo(10.0f, 6.0f);
		generalPath.lineTo(4.0f, 12.0f);
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
		generalPath.moveTo(12.5f, 13.0f);
		generalPath.lineTo(15.02f, 13.0f);
		generalPath.lineTo(15.02f, 15.59f);
		generalPath.lineTo(18.58f, 12.0f);
		generalPath.lineTo(15.02f, 8.42001f);
		generalPath.lineTo(15.02f, 11.02f);
		generalPath.lineTo(12.5f, 11.02f);
		generalPath.lineTo(9.0f, 11.02f);
		generalPath.lineTo(9.0f, 8.42001f);
		generalPath.lineTo(5.41f, 12.0f);
		generalPath.lineTo(9.0f, 15.59f);
		generalPath.lineTo(9.0f, 13.0f);
		generalPath.lineTo(12.5f, 13.0f);
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
		return 2.200000047683716;
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
		return 19.600000381469727;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 15.600000381469727;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		EastWestSvg base = new EastWestSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		EastWestSvg base = new EastWestSvg();
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
		return EastWestSvg::new;
	}
}

