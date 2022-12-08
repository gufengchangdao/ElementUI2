package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class TopLeftSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public TopLeftSvg() {
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
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_0_0
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(6.0f, 13.0f);
		generalPath.curveTo(6.0f, 12.0807f, 6.18106f, 11.1705f, 6.53284f, 10.3212f);
		generalPath.curveTo(6.88463f, 9.47194f, 7.40024f, 8.70026f, 8.05025f, 8.05025f);
		generalPath.curveTo(8.70026f, 7.40024f, 9.47194f, 6.88463f, 10.3212f, 6.53284f);
		generalPath.curveTo(11.1705f, 6.18106f, 12.0807f, 6.0f, 13.0f, 6.0f);
		generalPath.lineTo(16.0f, 6.0f);
		generalPath.lineTo(16.0f, 2.0f);
		generalPath.lineTo(22.0f, 8.0f);
		generalPath.lineTo(16.0f, 14.0f);
		generalPath.lineTo(16.0f, 10.0f);
		generalPath.lineTo(13.0f, 10.0f);
		generalPath.curveTo(12.606f, 10.0f, 12.2159f, 10.0776f, 11.8519f, 10.2284f);
		generalPath.curveTo(11.488f, 10.3791f, 11.1573f, 10.6001f, 10.8787f, 10.8787f);
		generalPath.curveTo(10.6001f, 11.1573f, 10.3791f, 11.488f, 10.2284f, 11.8519f);
		generalPath.curveTo(10.0776f, 12.2159f, 10.0f, 12.606f, 10.0f, 13.0f);
		generalPath.lineTo(10.0f, 16.0f);
		generalPath.lineTo(14.0f, 16.0f);
		generalPath.lineTo(8.0f, 22.0f);
		generalPath.lineTo(2.0f, 16.0f);
		generalPath.lineTo(6.0f, 16.0f);
		generalPath.lineTo(6.0f, 13.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_0_1
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(9.0f, 13.0f);
		generalPath.lineTo(9.0f, 17.0f);
		generalPath.lineTo(11.5f, 17.0f);
		generalPath.lineTo(8.0f, 20.5f);
		generalPath.lineTo(4.5f, 17.0f);
		generalPath.lineTo(7.0f, 17.0f);
		generalPath.lineTo(7.0f, 13.0f);
		generalPath.curveTo(7.0f, 12.2121f, 7.15519f, 11.4318f, 7.45672f, 10.7039f);
		generalPath.curveTo(7.75825f, 9.97594f, 8.20021f, 9.31451f, 8.75736f, 8.75736f);
		generalPath.curveTo(9.31451f, 8.2002f, 9.97594f, 7.75825f, 10.7039f, 7.45672f);
		generalPath.curveTo(11.4319f, 7.15519f, 12.2121f, 6.99999f, 13.0f, 6.99999f);
		generalPath.lineTo(17.0f, 6.99999f);
		generalPath.lineTo(17.0f, 4.49999f);
		generalPath.lineTo(20.5f, 7.99999f);
		generalPath.lineTo(17.0f, 11.5f);
		generalPath.lineTo(17.0f, 8.99999f);
		generalPath.lineTo(13.0f, 8.99999f);
		generalPath.curveTo(12.4747f, 8.99999f, 11.9546f, 9.10346f, 11.4693f, 9.30448f);
		generalPath.curveTo(10.984f, 9.5055f, 10.543f, 9.80013f, 10.1716f, 10.1716f);
		generalPath.curveTo(9.80014f, 10.543f, 9.5055f, 10.984f, 9.30448f, 11.4693f);
		generalPath.curveTo(9.10346f, 11.9546f, 9.0f, 12.4747f, 9.0f, 13.0f);
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
		return 0.20000000298023224;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 1.2000000476837158;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 23.600000381469727;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 22.799999237060547;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		TopLeftSvg base = new TopLeftSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		TopLeftSvg base = new TopLeftSvg();
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
		return TopLeftSvg::new;
	}
}

