package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class PoofSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public PoofSvg() {
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
		generalPath.curveTo(23.0f, 20.0294f, 18.9706f, 16.0f, 14.0f, 16.0f);
		generalPath.curveTo(9.02944f, 16.0f, 5.0f, 20.0294f, 5.0f, 25.0f);
		generalPath.curveTo(5.0f, 29.9706f, 9.02944f, 34.0f, 14.0f, 34.0f);
		generalPath.curveTo(18.9706f, 34.0f, 23.0f, 29.9706f, 23.0f, 25.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = new LinearGradientPaint(new Point2D.Double(14.0, 16.0), new Point2D.Double(14.0, 34.0), new float[]{0.0f, 1.0f}, new Color[]{((colorFilter != null) ? colorFilter.filter(new Color(241, 241, 241, 255)) : new Color(241, 241, 241, 255)), ((colorFilter != null) ? colorFilter.filter(new Color(214, 214, 214, 255)) : new Color(214, 214, 214, 255))}, MultipleGradientPaint.CycleMethod.NO_CYCLE, MultipleGradientPaint.ColorSpaceType.SRGB, new AffineTransform(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 0.6f * origAlpha));
// _0_0_1
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(15.41f, 25.0306f);
		generalPath.lineTo(17.53f, 22.9106f);
		generalPath.curveTo(17.7837f, 22.6587f, 17.8837f, 22.2907f, 17.7924f, 21.945f);
		generalPath.curveTo(17.7011f, 21.5994f, 17.4323f, 21.3288f, 17.0874f, 21.235f);
		generalPath.curveTo(16.7424f, 21.1413f, 16.3737f, 21.2387f, 16.12f, 21.4906f);
		generalPath.lineTo(14.0f, 23.6206f);
		generalPath.lineTo(11.88f, 21.5006f);
		generalPath.curveTo(11.4879f, 21.1112f, 10.8544f, 21.1135f, 10.465f, 21.5056f);
		generalPath.curveTo(10.0756f, 21.8977f, 10.0779f, 22.5312f, 10.47f, 22.9206f);
		generalPath.lineTo(12.59f, 25.0306f);
		generalPath.lineTo(10.47f, 27.1506f);
		generalPath.curveTo(10.1302f, 27.5474f, 10.1531f, 28.1388f, 10.5224f, 28.5082f);
		generalPath.curveTo(10.8918f, 28.8775f, 11.4832f, 28.9004f, 11.88f, 28.5606f);
		generalPath.lineTo(14.0f, 26.4406f);
		generalPath.lineTo(16.12f, 28.5606f);
		generalPath.curveTo(16.5168f, 28.9004f, 17.1082f, 28.8775f, 17.4776f, 28.5082f);
		generalPath.curveTo(17.8469f, 28.1388f, 17.8698f, 27.5474f, 17.53f, 27.1506f);
		generalPath.lineTo(15.41f, 25.0306f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
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
		PoofSvg base = new PoofSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		PoofSvg base = new PoofSvg();
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
		return PoofSvg::new;
	}
}

