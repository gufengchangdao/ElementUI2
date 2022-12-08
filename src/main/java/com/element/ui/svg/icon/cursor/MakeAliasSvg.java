package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class MakeAliasSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public MakeAliasSvg() {
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
		generalPath.moveTo(18.0f, 3.0f);
		generalPath.lineTo(8.20001f, 3.0f);
		generalPath.lineTo(11.49f, 6.2f);
		generalPath.lineTo(10.92f, 6.83f);
		generalPath.curveTo(10.5551f, 7.31517f, 10.2211f, 7.82284f, 9.92f, 8.35f);
		generalPath.curveTo(9.62679f, 8.99118f, 9.4022f, 9.66158f, 9.25f, 10.35f);
		generalPath.curveTo(9.1934f, 11.0589f, 9.1934f, 11.7711f, 9.25f, 12.48f);
		generalPath.curveTo(9.28713f, 13.0363f, 9.38778f, 13.5866f, 9.55f, 14.12f);
		generalPath.curveTo(9.70077f, 14.6048f, 9.91246f, 15.0685f, 10.18f, 15.5f);
		generalPath.curveTo(10.4603f, 15.9383f, 10.7965f, 16.3384f, 11.18f, 16.69f);
		generalPath.curveTo(11.8654f, 17.158f, 12.5878f, 17.5694f, 13.34f, 17.92f);
		generalPath.curveTo(12.9665f, 17.371f, 12.6514f, 16.7845f, 12.4f, 16.17f);
		generalPath.curveTo(12.2744f, 15.6664f, 12.1809f, 15.1554f, 12.12f, 14.64f);
		generalPath.curveTo(12.0692f, 14.1116f, 12.0492f, 13.5807f, 12.06f, 13.05f);
		generalPath.curveTo(12.0798f, 12.6673f, 12.1436f, 12.2881f, 12.25f, 11.92f);
		generalPath.curveTo(12.4129f, 11.4492f, 12.6491f, 11.0071f, 12.95f, 10.61f);
		generalPath.curveTo(13.1876f, 10.2823f, 13.4555f, 9.97764f, 13.75f, 9.7f);
		generalPath.curveTo(14.0178f, 9.43911f, 14.3017f, 9.1953f, 14.6f, 8.97f);
		generalPath.lineTo(18.0f, 12.2f);
		generalPath.lineTo(18.0f, 3.0f);
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
		generalPath.moveTo(17.0f, 10.0f);
		generalPath.lineTo(17.0f, 4.0f);
		generalPath.lineTo(11.0f, 4.0f);
		generalPath.lineTo(12.85f, 5.78f);
		generalPath.curveTo(12.71f, 5.93f, 12.52f, 6.12f, 12.41f, 6.26f);
		generalPath.curveTo(12.13f, 6.61f, 11.97f, 6.8f, 11.8f, 6.99f);
		generalPath.curveTo(11.63f, 7.18f, 11.54f, 7.31f, 11.42f, 7.46f);
		generalPath.curveTo(11.2884f, 7.64242f, 11.1682f, 7.83278f, 11.06f, 8.03f);
		generalPath.curveTo(10.95f, 8.23f, 10.83f, 8.49f, 10.69f, 8.78f);
		generalPath.curveTo(10.5434f, 9.09919f, 10.4359f, 9.43497f, 10.37f, 9.78f);
		generalPath.curveTo(10.2905f, 10.109f, 10.2337f, 10.4432f, 10.2f, 10.78f);
		generalPath.curveTo(10.1865f, 11.0332f, 10.1865f, 11.2868f, 10.2f, 11.54f);
		generalPath.curveTo(10.1762f, 11.8729f, 10.1762f, 12.2071f, 10.2f, 12.54f);
		generalPath.curveTo(10.2886f, 13.1309f, 10.4498f, 13.7086f, 10.68f, 14.26f);
		generalPath.lineTo(11.14f, 15.19f);
		generalPath.curveTo(11.0508f, 14.4334f, 11.0107f, 13.6718f, 11.02f, 12.91f);
		generalPath.curveTo(11.0913f, 12.3111f, 11.2567f, 11.7273f, 11.51f, 11.18f);
		generalPath.curveTo(11.5575f, 11.0334f, 11.6212f, 10.8925f, 11.7f, 10.76f);
		generalPath.lineTo(12.06f, 10.19f);
		generalPath.curveTo(12.27f, 9.85f, 12.71f, 9.32f, 12.82f, 9.19f);
		generalPath.curveTo(13.1111f, 8.86142f, 13.4252f, 8.55397f, 13.76f, 8.27f);
		generalPath.lineTo(14.55f, 7.6f);
		generalPath.lineTo(17.0f, 10.0f);
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
		return 6.400010108947754;
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
		return 13.399999618530273;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 18.520000457763672;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		MakeAliasSvg base = new MakeAliasSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		MakeAliasSvg base = new MakeAliasSvg();
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
		return MakeAliasSvg::new;
	}
}

