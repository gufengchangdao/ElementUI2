package com.element.ui.svg.icon.fill;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class RecordSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public RecordSvg() {
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
		generalPath.moveTo(16.0f, 3.0f);
		generalPath.curveTo(13.4288f, 3.0f, 10.9154f, 3.76244f, 8.77759f, 5.1909f);
		generalPath.curveTo(6.63975f, 6.61935f, 4.97351f, 8.64968f, 3.98957f, 11.0251f);
		generalPath.curveTo(3.00563f, 13.4006f, 2.74819f, 16.0144f, 3.2498f, 18.5362f);
		generalPath.curveTo(3.75141f, 21.0579f, 4.98953f, 23.3743f, 6.80762f, 25.1924f);
		generalPath.curveTo(8.6257f, 27.0105f, 10.9421f, 28.2486f, 13.4638f, 28.7502f);
		generalPath.curveTo(15.9856f, 29.2518f, 18.5995f, 28.9944f, 20.9749f, 28.0104f);
		generalPath.curveTo(23.3503f, 27.0265f, 25.3807f, 25.3603f, 26.8091f, 23.2224f);
		generalPath.curveTo(28.2376f, 21.0846f, 29.0f, 18.5712f, 29.0f, 16.0f);
		generalPath.curveTo(28.9934f, 12.5542f, 27.6216f, 9.25145f, 25.1851f, 6.81491f);
		generalPath.curveTo(22.7486f, 4.37837f, 19.4458f, 3.00661f, 16.0f, 3.0f);
		generalPath.closePath();
		generalPath.moveTo(16.0f, 27.0f);
		generalPath.curveTo(13.8244f, 27.0f, 11.6977f, 26.3549f, 9.88873f, 25.1462f);
		generalPath.curveTo(8.07979f, 23.9375f, 6.66989f, 22.2195f, 5.83733f, 20.2095f);
		generalPath.curveTo(5.00477f, 18.1995f, 4.78693f, 15.9878f, 5.21137f, 13.854f);
		generalPath.curveTo(5.63581f, 11.7202f, 6.68345f, 9.7602f, 8.22183f, 8.22183f);
		generalPath.curveTo(9.76021f, 6.68345f, 11.7202f, 5.6358f, 13.854f, 5.21136f);
		generalPath.curveTo(15.9878f, 4.78692f, 18.1995f, 5.00476f, 20.2095f, 5.83733f);
		generalPath.curveTo(22.2195f, 6.66989f, 23.9375f, 8.07979f, 25.1462f, 9.88873f);
		generalPath.curveTo(26.3549f, 11.6977f, 27.0f, 13.8244f, 27.0f, 16.0f);
		generalPath.curveTo(26.9967f, 18.9164f, 25.8367f, 21.7123f, 23.7745f, 23.7745f);
		generalPath.curveTo(21.7123f, 25.8367f, 18.9164f, 26.9967f, 16.0f, 27.0f);
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
		generalPath.moveTo(16.0f, 25.0f);
		generalPath.curveTo(20.9706f, 25.0f, 25.0f, 20.9706f, 25.0f, 16.0f);
		generalPath.curveTo(25.0f, 11.0294f, 20.9706f, 7.0f, 16.0f, 7.0f);
		generalPath.curveTo(11.0294f, 7.0f, 7.0f, 11.0294f, 7.0f, 16.0f);
		generalPath.curveTo(7.0f, 20.9706f, 11.0294f, 25.0f, 16.0f, 25.0f);
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
		return 2.748189926147461;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 3.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 26.25181007385254;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 26.251800537109375;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		RecordSvg base = new RecordSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		RecordSvg base = new RecordSvg();
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
		return RecordSvg::new;
	}
}

