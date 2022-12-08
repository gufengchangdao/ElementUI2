package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class LoadingSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public LoadingSvg() {
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
		generalPath.moveTo(23.0f, 25.0f);
		generalPath.curveTo(23.0f, 29.971f, 18.971f, 34.0f, 14.0f, 34.0f);
		generalPath.lineTo(14.0f, 25.0f);
		generalPath.lineTo(5.0f, 25.0f);
		generalPath.curveTo(5.0f, 20.029f, 9.029f, 16.0f, 14.0f, 16.0f);
		generalPath.lineTo(14.0f, 25.0f);
		generalPath.lineTo(23.0f, 25.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(76, 197, 250, 255)) : new Color(76, 197, 250, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_1
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(5.0f, 25.0f);
		generalPath.lineTo(14.0f, 25.0f);
		generalPath.lineTo(14.0f, 16.0f);
		generalPath.curveTo(18.971f, 16.0f, 23.0f, 20.029f, 23.0f, 25.0f);
		generalPath.lineTo(14.0f, 25.0f);
		generalPath.lineTo(14.0f, 34.0f);
		generalPath.curveTo(9.029f, 34.0f, 5.0f, 29.971f, 5.0f, 25.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(52, 130, 229, 255)) : new Color(52, 130, 229, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_2
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(4.0f, 18.5f);
		generalPath.lineTo(4.0f, 2.5f);
		generalPath.lineTo(15.6f, 14.1081f);
		generalPath.lineTo(8.55353f, 14.1081f);
		generalPath.lineTo(8.40242f, 14.232f);
		generalPath.lineTo(4.0f, 18.5f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_3
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(5.0f, 4.79999f);
		generalPath.lineTo(5.0f, 16.0f);
		generalPath.lineTo(7.969f, 13.1309f);
		generalPath.lineTo(8.129f, 12.9918f);
		generalPath.lineTo(13.165f, 13.0f);
		generalPath.lineTo(5.0f, 4.79999f);
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
		return 1.7000000476837158;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 22.600000381469727;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 35.099998474121094;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		LoadingSvg base = new LoadingSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		LoadingSvg base = new LoadingSvg();
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
		return LoadingSvg::new;
	}
}

