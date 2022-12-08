package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class ZoomInSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public ZoomInSvg() {
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
		generalPath.moveTo(14.8887f, 12.4794f);
		generalPath.lineTo(20.0045f, 17.5952f);
		generalPath.lineTo(18.5903f, 19.0094f);
		generalPath.lineTo(13.4737f, 13.8928f);
		generalPath.curveTo(12.4934f, 14.59f, 11.2946f, 15.0f, 10.0f, 15.0f);
		generalPath.curveTo(6.68629f, 15.0f, 4.0f, 12.3137f, 4.0f, 9.0f);
		generalPath.curveTo(4.0f, 5.68629f, 6.68629f, 3.0f, 10.0f, 3.0f);
		generalPath.curveTo(13.3137f, 3.0f, 16.0f, 5.68629f, 16.0f, 9.0f);
		generalPath.curveTo(16.0f, 10.2971f, 15.5884f, 11.4981f, 14.8887f, 12.4794f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_1
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(10.0f, 14.0f);
		generalPath.curveTo(12.7614f, 14.0f, 15.0f, 11.7614f, 15.0f, 9.0f);
		generalPath.curveTo(15.0f, 6.23858f, 12.7614f, 4.0f, 10.0f, 4.0f);
		generalPath.curveTo(7.23858f, 4.0f, 5.0f, 6.23858f, 5.0f, 9.0f);
		generalPath.curveTo(5.0f, 11.7614f, 7.23858f, 14.0f, 10.0f, 14.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_2
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(13.0f, 8.0f);
		generalPath.lineTo(11.0f, 8.0f);
		generalPath.lineTo(11.0f, 6.0f);
		generalPath.lineTo(9.0f, 6.0f);
		generalPath.lineTo(9.0f, 8.0f);
		generalPath.lineTo(7.0f, 8.0f);
		generalPath.lineTo(7.0f, 9.98f);
		generalPath.lineTo(9.0f, 9.98f);
		generalPath.lineTo(9.0f, 12.0f);
		generalPath.lineTo(11.0f, 12.0f);
		generalPath.lineTo(11.0f, 9.98f);
		generalPath.lineTo(13.0f, 9.98f);
		generalPath.lineTo(13.0f, 8.0f);
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
		return 2.200000047683716;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 19.60449981689453;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 19.609399795532227;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		ZoomInSvg base = new ZoomInSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		ZoomInSvg base = new ZoomInSvg();
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
		return ZoomInSvg::new;
	}
}

