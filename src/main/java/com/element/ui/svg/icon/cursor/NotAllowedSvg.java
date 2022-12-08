package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class NotAllowedSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public NotAllowedSvg() {
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
		generalPath.moveTo(14.0f, 15.5f);
		generalPath.curveTo(8.75329f, 15.5f, 4.5f, 19.7533f, 4.5f, 25.0f);
		generalPath.curveTo(4.5f, 30.2467f, 8.75329f, 34.5f, 14.0f, 34.5f);
		generalPath.curveTo(19.2467f, 34.5f, 23.5f, 30.2467f, 23.5f, 25.0f);
		generalPath.curveTo(23.4945f, 19.7556f, 19.2444f, 15.5055f, 14.0f, 15.5f);
		generalPath.closePath();
		generalPath.moveTo(10.61f, 19.5f);
		generalPath.curveTo(11.6242f, 18.8561f, 12.7987f, 18.5096f, 14.0f, 18.5f);
		generalPath.curveTo(17.5876f, 18.5055f, 20.4945f, 21.4124f, 20.5f, 25.0f);
		generalPath.curveTo(20.4904f, 26.2013f, 20.1439f, 27.3758f, 19.5f, 28.39f);
		generalPath.lineTo(10.61f, 19.5f);
		generalPath.closePath();
		generalPath.moveTo(14.0f, 31.5f);
		generalPath.curveTo(10.4124f, 31.4945f, 7.50551f, 28.5876f, 7.5f, 25.0f);
		generalPath.curveTo(7.50396f, 23.7885f, 7.85065f, 22.6028f, 8.5f, 21.58f);
		generalPath.lineTo(17.44f, 30.52f);
		generalPath.curveTo(16.4172f, 31.1694f, 15.2315f, 31.516f, 14.02f, 31.52f);
		generalPath.lineTo(14.0f, 31.5f);
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
		generalPath.moveTo(5.0f, 25.0f);
		generalPath.curveTo(5.0f, 29.97f, 9.03f, 34.0f, 14.0f, 34.0f);
		generalPath.curveTo(18.97f, 34.0f, 23.0f, 29.97f, 23.0f, 25.0f);
		generalPath.curveTo(23.0f, 20.029f, 18.97f, 16.0f, 14.0f, 16.0f);
		generalPath.curveTo(9.03f, 16.0f, 5.0f, 20.029f, 5.0f, 25.0f);
		generalPath.closePath();
		generalPath.moveTo(9.826f, 19.39f);
		generalPath.curveTo(10.993f, 18.521f, 12.434f, 18.0f, 14.0f, 18.0f);
		generalPath.curveTo(17.866f, 18.0f, 21.0f, 21.134f, 21.0f, 25.0f);
		generalPath.curveTo(21.0f, 26.567f, 20.48f, 28.008f, 19.61f, 29.174f);
		generalPath.lineTo(9.826f, 19.39f);
		generalPath.closePath();
		generalPath.moveTo(7.0f, 25.0f);
		generalPath.curveTo(7.0f, 23.422f, 7.529f, 21.971f, 8.409f, 20.801f);
		generalPath.lineTo(18.199f, 30.591f);
		generalPath.curveTo(17.028f, 31.472f, 15.577f, 32.0f, 14.0f, 32.0f);
		generalPath.curveTo(10.134f, 32.0f, 7.0f, 28.866f, 7.0f, 25.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = new LinearGradientPaint(new Point2D.Double(14.0, 16.0), new Point2D.Double(14.0, 34.0), new float[]{0.0f, 1.0f}, new Color[]{((colorFilter != null) ? colorFilter.filter(new Color(241, 241, 241, 255)) : new Color(241, 241, 241, 255)), ((colorFilter != null) ? colorFilter.filter(new Color(214, 214, 214, 255)) : new Color(214, 214, 214, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
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
		return 23.100000381469727;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 35.599998474121094;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		NotAllowedSvg base = new NotAllowedSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		NotAllowedSvg base = new NotAllowedSvg();
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
		return NotAllowedSvg::new;
	}
}

