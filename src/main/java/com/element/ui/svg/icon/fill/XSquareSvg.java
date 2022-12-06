package com.element.ui.svg.icon.fill;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class XSquareSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public XSquareSvg() {
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
		generalPath.moveTo(26.0f, 4.0f);
		generalPath.lineTo(6.0f, 4.0f);
		generalPath.curveTo(5.46957f, 4.0f, 4.96086f, 4.21071f, 4.58579f, 4.58579f);
		generalPath.curveTo(4.21071f, 4.96086f, 4.0f, 5.46957f, 4.0f, 6.0f);
		generalPath.lineTo(4.0f, 26.0f);
		generalPath.curveTo(4.0f, 26.5304f, 4.21071f, 27.0391f, 4.58579f, 27.4142f);
		generalPath.curveTo(4.96086f, 27.7893f, 5.46957f, 28.0f, 6.0f, 28.0f);
		generalPath.lineTo(26.0f, 28.0f);
		generalPath.curveTo(26.5304f, 28.0f, 27.0391f, 27.7893f, 27.4142f, 27.4142f);
		generalPath.curveTo(27.7893f, 27.0391f, 28.0f, 26.5304f, 28.0f, 26.0f);
		generalPath.lineTo(28.0f, 6.0f);
		generalPath.curveTo(28.0f, 5.46957f, 27.7893f, 4.96086f, 27.4142f, 4.58579f);
		generalPath.curveTo(27.0391f, 4.21071f, 26.5304f, 4.0f, 26.0f, 4.0f);
		generalPath.closePath();
		generalPath.moveTo(20.7125f, 19.2875f);
		generalPath.curveTo(20.9003f, 19.4771f, 21.0056f, 19.7332f, 21.0056f, 20.0f);
		generalPath.curveTo(21.0056f, 20.2668f, 20.9003f, 20.5229f, 20.7125f, 20.7125f);
		generalPath.curveTo(20.5214f, 20.8973f, 20.2659f, 21.0006f, 20.0f, 21.0006f);
		generalPath.curveTo(19.7341f, 21.0006f, 19.4786f, 20.8973f, 19.2875f, 20.7125f);
		generalPath.lineTo(16.0f, 17.4125f);
		generalPath.lineTo(12.7125f, 20.7125f);
		generalPath.curveTo(12.5214f, 20.8973f, 12.2659f, 21.0006f, 12.0f, 21.0006f);
		generalPath.curveTo(11.7341f, 21.0006f, 11.4786f, 20.8973f, 11.2875f, 20.7125f);
		generalPath.curveTo(11.0997f, 20.5229f, 10.9944f, 20.2668f, 10.9944f, 20.0f);
		generalPath.curveTo(10.9944f, 19.7332f, 11.0997f, 19.4771f, 11.2875f, 19.2875f);
		generalPath.lineTo(14.5875f, 16.0f);
		generalPath.lineTo(11.2875f, 12.7125f);
		generalPath.curveTo(11.128f, 12.5182f, 11.0465f, 12.2715f, 11.0589f, 12.0205f);
		generalPath.curveTo(11.0712f, 11.7694f, 11.1765f, 11.5319f, 11.3542f, 11.3542f);
		generalPath.curveTo(11.5319f, 11.1765f, 11.7694f, 11.0712f, 12.0205f, 11.0589f);
		generalPath.curveTo(12.2715f, 11.0465f, 12.5182f, 11.128f, 12.7125f, 11.2875f);
		generalPath.lineTo(16.0f, 14.5875f);
		generalPath.lineTo(19.2875f, 11.2875f);
		generalPath.curveTo(19.4818f, 11.128f, 19.7285f, 11.0465f, 19.9795f, 11.0589f);
		generalPath.curveTo(20.2306f, 11.0712f, 20.4681f, 11.1765f, 20.6458f, 11.3542f);
		generalPath.curveTo(20.8235f, 11.5319f, 20.9288f, 11.7694f, 20.9411f, 12.0205f);
		generalPath.curveTo(20.9535f, 12.2715f, 20.872f, 12.5182f, 20.7125f, 12.7125f);
		generalPath.lineTo(17.4125f, 16.0f);
		generalPath.lineTo(20.7125f, 19.2875f);
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
		return 4.0;
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
		return 24.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 24.0;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		XSquareSvg base = new XSquareSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		XSquareSvg base = new XSquareSvg();
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
		return XSquareSvg::new;
	}
}

