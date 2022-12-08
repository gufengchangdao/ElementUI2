package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class ArrowSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public ArrowSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}


	private void _paint0(Graphics2D g, float origAlpha) {
// 
		g.setComposite(AlphaComposite.getInstance(3, origAlpha));
// _0
		g.setComposite(AlphaComposite.getInstance(3, origAlpha));
// _0_0
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_0
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(15.9231f, 18.0296f);
		generalPath.curveTo(16.0985f, 18.4505f, 15.9299f, 20.0447f, 15.0f, 20.4142f);
		generalPath.curveTo(14.0701f, 20.7837f, 12.882f, 20.4142f, 12.882f, 20.4142f);
		generalPath.lineTo(10.726f, 16.1024f);
		generalPath.lineTo(7.0f, 19.8284f);
		generalPath.lineTo(7.0f, 3.0f);
		generalPath.lineTo(18.4142f, 14.4142f);
		generalPath.lineTo(14.1615f, 14.4142f);
		generalPath.curveTo(14.3702f, 14.8144f, 15.7003f, 17.4948f, 15.9231f, 18.0296f);
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
		generalPath.moveTo(8.0f, 5.41422f);
		generalPath.lineTo(8.0f, 17.4142f);
		generalPath.lineTo(11.0f, 14.4142f);
		generalPath.lineTo(13.5f, 19.4142f);
		generalPath.curveTo(13.5f, 19.4142f, 14.1763f, 19.63f, 14.5f, 19.4142f);
		generalPath.curveTo(14.8237f, 19.1984f, 15.1457f, 18.7638f, 15.0f, 18.4142f);
		generalPath.curveTo(14.3123f, 16.7638f, 12.5f, 13.4142f, 12.5f, 13.4142f);
		generalPath.lineTo(16.0f, 13.4142f);
		generalPath.lineTo(8.0f, 5.41422f);
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
		return 5.199999809265137;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 2.200000047683716;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 15.014200210571289;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 21.17840003967285;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		ArrowSvg base = new ArrowSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		ArrowSvg base = new ArrowSvg();
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
		return ArrowSvg::new;
	}
}

