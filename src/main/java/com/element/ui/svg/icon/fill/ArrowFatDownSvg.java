package com.element.ui.svg.icon.fill;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class ArrowFatDownSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public ArrowFatDownSvg() {
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
		generalPath.moveTo(28.7125f, 17.7125f);
		generalPath.lineTo(16.7125f, 29.7125f);
		generalPath.curveTo(16.5229f, 29.9003f, 16.2669f, 30.0056f, 16.0f, 30.0056f);
		generalPath.curveTo(15.7332f, 30.0056f, 15.4771f, 29.9003f, 15.2875f, 29.7125f);
		generalPath.lineTo(3.28751f, 17.7125f);
		generalPath.curveTo(3.15185f, 17.5679f, 3.0599f, 17.3879f, 3.0223f, 17.1932f);
		generalPath.curveTo(2.98469f, 16.9985f, 3.00297f, 16.7972f, 3.07501f, 16.6125f);
		generalPath.curveTo(3.15151f, 16.4305f, 3.28018f, 16.2752f, 3.44479f, 16.1662f);
		generalPath.curveTo(3.6094f, 16.0572f, 3.80259f, 15.9994f, 4.00001f, 16.0f);
		generalPath.lineTo(9.00001f, 16.0f);
		generalPath.lineTo(9.00001f, 6.0f);
		generalPath.curveTo(9.00001f, 5.46957f, 9.21073f, 4.96086f, 9.5858f, 4.58579f);
		generalPath.curveTo(9.96087f, 4.21071f, 10.4696f, 4.0f, 11.0f, 4.0f);
		generalPath.lineTo(21.0f, 4.0f);
		generalPath.curveTo(21.5304f, 4.0f, 22.0392f, 4.21071f, 22.4142f, 4.58579f);
		generalPath.curveTo(22.7893f, 4.96086f, 23.0f, 5.46957f, 23.0f, 6.0f);
		generalPath.lineTo(23.0f, 16.0f);
		generalPath.lineTo(28.0f, 16.0f);
		generalPath.curveTo(28.1974f, 15.9994f, 28.3906f, 16.0572f, 28.5552f, 16.1662f);
		generalPath.curveTo(28.7198f, 16.2752f, 28.8485f, 16.4305f, 28.925f, 16.6125f);
		generalPath.curveTo(28.9971f, 16.7972f, 29.0153f, 16.9985f, 28.9777f, 17.1932f);
		generalPath.curveTo(28.9401f, 17.3879f, 28.8482f, 17.5679f, 28.7125f, 17.7125f);
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
		return 4.0;
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
		return 26.005599975585938;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		ArrowFatDownSvg base = new ArrowFatDownSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		ArrowFatDownSvg base = new ArrowFatDownSvg();
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
		return ArrowFatDownSvg::new;
	}
}

