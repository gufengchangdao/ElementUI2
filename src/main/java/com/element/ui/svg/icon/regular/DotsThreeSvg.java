package com.element.ui.svg.icon.regular;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class DotsThreeSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public DotsThreeSvg() {
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
		generalPath.moveTo(16.0f, 17.5f);
		generalPath.curveTo(16.8284f, 17.5f, 17.5f, 16.8284f, 17.5f, 16.0f);
		generalPath.curveTo(17.5f, 15.1716f, 16.8284f, 14.5f, 16.0f, 14.5f);
		generalPath.curveTo(15.1716f, 14.5f, 14.5f, 15.1716f, 14.5f, 16.0f);
		generalPath.curveTo(14.5f, 16.8284f, 15.1716f, 17.5f, 16.0f, 17.5f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_1
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(24.0f, 17.5f);
		generalPath.curveTo(24.8284f, 17.5f, 25.5f, 16.8284f, 25.5f, 16.0f);
		generalPath.curveTo(25.5f, 15.1716f, 24.8284f, 14.5f, 24.0f, 14.5f);
		generalPath.curveTo(23.1716f, 14.5f, 22.5f, 15.1716f, 22.5f, 16.0f);
		generalPath.curveTo(22.5f, 16.8284f, 23.1716f, 17.5f, 24.0f, 17.5f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_2
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(8.0f, 17.5f);
		generalPath.curveTo(8.82843f, 17.5f, 9.5f, 16.8284f, 9.5f, 16.0f);
		generalPath.curveTo(9.5f, 15.1716f, 8.82843f, 14.5f, 8.0f, 14.5f);
		generalPath.curveTo(7.17157f, 14.5f, 6.5f, 15.1716f, 6.5f, 16.0f);
		generalPath.curveTo(6.5f, 16.8284f, 7.17157f, 17.5f, 8.0f, 17.5f);
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
		return 6.5;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 14.5;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 19.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 3.0;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		DotsThreeSvg base = new DotsThreeSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		DotsThreeSvg base = new DotsThreeSvg();
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
		return DotsThreeSvg::new;
	}
}

