package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class BottomRightSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public BottomRightSvg() {
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
		generalPath.moveTo(18.0f, 11.0f);
		generalPath.curveTo(18.0f, 11.9193f, 17.8189f, 12.8295f, 17.4672f, 13.6788f);
		generalPath.curveTo(17.1154f, 14.5281f, 16.5998f, 15.2997f, 15.9497f, 15.9497f);
		generalPath.curveTo(15.2997f, 16.5998f, 14.5281f, 17.1154f, 13.6788f, 17.4672f);
		generalPath.curveTo(12.8295f, 17.8189f, 11.9193f, 18.0f, 11.0f, 18.0f);
		generalPath.lineTo(8.0f, 18.0f);
		generalPath.lineTo(8.0f, 22.0f);
		generalPath.lineTo(2.0f, 16.0f);
		generalPath.lineTo(8.0f, 10.0f);
		generalPath.lineTo(8.0f, 14.0f);
		generalPath.lineTo(11.0f, 14.0f);
		generalPath.curveTo(11.394f, 14.0f, 11.7841f, 13.9224f, 12.148f, 13.7716f);
		generalPath.curveTo(12.512f, 13.6209f, 12.8427f, 13.3999f, 13.1213f, 13.1213f);
		generalPath.curveTo(13.3999f, 12.8427f, 13.6209f, 12.512f, 13.7716f, 12.148f);
		generalPath.curveTo(13.9224f, 11.7841f, 14.0f, 11.394f, 14.0f, 11.0f);
		generalPath.lineTo(14.0f, 8.0f);
		generalPath.lineTo(10.0f, 8.0f);
		generalPath.lineTo(16.0f, 2.0f);
		generalPath.lineTo(22.0f, 8.0f);
		generalPath.lineTo(18.0f, 8.0f);
		generalPath.lineTo(18.0f, 11.0f);
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
		generalPath.moveTo(15.0f, 11.0f);
		generalPath.lineTo(15.0f, 7.0f);
		generalPath.lineTo(12.5f, 7.0f);
		generalPath.lineTo(16.0f, 3.5f);
		generalPath.lineTo(19.5f, 7.0f);
		generalPath.lineTo(17.0f, 7.0f);
		generalPath.lineTo(17.0f, 11.0f);
		generalPath.curveTo(17.0f, 11.7879f, 16.8448f, 12.5682f, 16.5433f, 13.2961f);
		generalPath.curveTo(16.2418f, 14.0241f, 15.7998f, 14.6855f, 15.2426f, 15.2426f);
		generalPath.curveTo(14.6855f, 15.7998f, 14.0241f, 16.2418f, 13.2961f, 16.5433f);
		generalPath.curveTo(12.5681f, 16.8448f, 11.7879f, 17.0f, 11.0f, 17.0f);
		generalPath.lineTo(7.0f, 17.0f);
		generalPath.lineTo(7.0f, 19.5f);
		generalPath.lineTo(3.5f, 16.0f);
		generalPath.lineTo(7.0f, 12.5f);
		generalPath.lineTo(7.0f, 15.0f);
		generalPath.lineTo(11.0f, 15.0f);
		generalPath.curveTo(11.5253f, 15.0f, 12.0454f, 14.8965f, 12.5307f, 14.6955f);
		generalPath.curveTo(13.016f, 14.4945f, 13.457f, 14.1999f, 13.8284f, 13.8284f);
		generalPath.curveTo(14.1999f, 13.457f, 14.4945f, 13.016f, 14.6955f, 12.5307f);
		generalPath.curveTo(14.8965f, 12.0454f, 15.0f, 11.5253f, 15.0f, 11.0f);
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
		BottomRightSvg base = new BottomRightSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		BottomRightSvg base = new BottomRightSvg();
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
		return BottomRightSvg::new;
	}
}

