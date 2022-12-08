package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class BottomLeftSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public BottomLeftSvg() {
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
		generalPath.moveTo(13.0f, 18.0f);
		generalPath.curveTo(12.0807f, 18.0f, 11.1705f, 17.8189f, 10.3212f, 17.4672f);
		generalPath.curveTo(9.47194f, 17.1154f, 8.70026f, 16.5998f, 8.05025f, 15.9497f);
		generalPath.curveTo(7.40024f, 15.2997f, 6.88463f, 14.5281f, 6.53284f, 13.6788f);
		generalPath.curveTo(6.18106f, 12.8295f, 6.0f, 11.9193f, 6.0f, 11.0f);
		generalPath.lineTo(6.0f, 8.0f);
		generalPath.lineTo(2.0f, 8.0f);
		generalPath.lineTo(8.0f, 2.0f);
		generalPath.lineTo(14.0f, 8.0f);
		generalPath.lineTo(10.0f, 8.0f);
		generalPath.lineTo(10.0f, 11.0f);
		generalPath.curveTo(10.0f, 11.394f, 10.0776f, 11.7841f, 10.2284f, 12.1481f);
		generalPath.curveTo(10.3791f, 12.512f, 10.6001f, 12.8427f, 10.8787f, 13.1213f);
		generalPath.curveTo(11.1573f, 13.3999f, 11.488f, 13.6209f, 11.8519f, 13.7716f);
		generalPath.curveTo(12.2159f, 13.9224f, 12.606f, 14.0f, 13.0f, 14.0f);
		generalPath.lineTo(16.0f, 14.0f);
		generalPath.lineTo(16.0f, 10.0f);
		generalPath.lineTo(22.0f, 16.0f);
		generalPath.lineTo(16.0f, 22.0f);
		generalPath.lineTo(16.0f, 18.0f);
		generalPath.lineTo(13.0f, 18.0f);
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
		generalPath.moveTo(13.0f, 15.0f);
		generalPath.lineTo(17.0f, 15.0f);
		generalPath.lineTo(17.0f, 12.5f);
		generalPath.lineTo(20.5f, 16.0f);
		generalPath.lineTo(17.0f, 19.5f);
		generalPath.lineTo(17.0f, 17.0f);
		generalPath.lineTo(13.0f, 17.0f);
		generalPath.curveTo(12.2121f, 17.0f, 11.4318f, 16.8448f, 10.7039f, 16.5433f);
		generalPath.curveTo(9.97594f, 16.2418f, 9.31451f, 15.7998f, 8.75736f, 15.2426f);
		generalPath.curveTo(8.2002f, 14.6855f, 7.75825f, 14.0241f, 7.45672f, 13.2961f);
		generalPath.curveTo(7.15519f, 12.5681f, 6.99999f, 11.7879f, 6.99999f, 11.0f);
		generalPath.lineTo(6.99999f, 7.0f);
		generalPath.lineTo(4.49999f, 7.0f);
		generalPath.lineTo(7.99999f, 3.5f);
		generalPath.lineTo(11.5f, 7.0f);
		generalPath.lineTo(8.99999f, 7.0f);
		generalPath.lineTo(8.99999f, 11.0f);
		generalPath.curveTo(8.99999f, 11.5253f, 9.10346f, 12.0454f, 9.30448f, 12.5307f);
		generalPath.curveTo(9.5055f, 13.016f, 9.80013f, 13.457f, 10.1716f, 13.8284f);
		generalPath.curveTo(10.543f, 14.1999f, 10.984f, 14.4945f, 11.4693f, 14.6955f);
		generalPath.curveTo(11.9546f, 14.8965f, 12.4747f, 15.0f, 13.0f, 15.0f);
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
		BottomLeftSvg base = new BottomLeftSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		BottomLeftSvg base = new BottomLeftSvg();
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
		return BottomLeftSvg::new;
	}
}

