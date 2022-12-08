package com.element.ui.svg.icon.fill;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class ArrowFatUpSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public ArrowFatUpSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}


	private void _paint0(Graphics2D g, float origAlpha) {
// 
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(28.925f, 15.3875f);
		generalPath.curveTo(28.8485f, 15.5695f, 28.7198f, 15.7248f, 28.5552f, 15.8338f);
		generalPath.curveTo(28.3906f, 15.9428f, 28.1974f, 16.0006f, 28.0f, 16.0f);
		generalPath.lineTo(23.0f, 16.0f);
		generalPath.lineTo(23.0f, 26.0f);
		generalPath.curveTo(23.0f, 26.5304f, 22.7893f, 27.0392f, 22.4142f, 27.4142f);
		generalPath.curveTo(22.0392f, 27.7893f, 21.5304f, 28.0f, 21.0f, 28.0f);
		generalPath.lineTo(11.0f, 28.0f);
		generalPath.curveTo(10.4696f, 28.0f, 9.96087f, 27.7893f, 9.5858f, 27.4142f);
		generalPath.curveTo(9.21073f, 27.0392f, 9.00001f, 26.5304f, 9.00001f, 26.0f);
		generalPath.lineTo(9.00001f, 16.0f);
		generalPath.lineTo(4.00001f, 16.0f);
		generalPath.curveTo(3.80259f, 16.0006f, 3.6094f, 15.9428f, 3.44479f, 15.8338f);
		generalPath.curveTo(3.28018f, 15.7248f, 3.15151f, 15.5695f, 3.07501f, 15.3875f);
		generalPath.curveTo(3.00297f, 15.2028f, 2.98469f, 15.0015f, 3.0223f, 14.8068f);
		generalPath.curveTo(3.0599f, 14.6121f, 3.15185f, 14.4321f, 3.28751f, 14.2875f);
		generalPath.lineTo(15.2875f, 2.28751f);
		generalPath.curveTo(15.4771f, 2.09973f, 15.7332f, 1.99438f, 16.0f, 1.99438f);
		generalPath.curveTo(16.2669f, 1.99438f, 16.5229f, 2.09973f, 16.7125f, 2.28751f);
		generalPath.lineTo(28.7125f, 14.2875f);
		generalPath.curveTo(28.8482f, 14.4321f, 28.9401f, 14.6121f, 28.9777f, 14.8068f);
		generalPath.curveTo(29.0153f, 15.0015f, 28.9971f, 15.2028f, 28.925f, 15.3875f);
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
		return 2.984689950942993;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 1.994379997253418;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 26.030611038208008;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 26.005619049072266;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		ArrowFatUpSvg base = new ArrowFatUpSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		ArrowFatUpSvg base = new ArrowFatUpSvg();
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
		return ArrowFatUpSvg::new;
	}
}

