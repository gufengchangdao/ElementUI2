package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class GrabbedSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public GrabbedSvg() {
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
		generalPath.moveTo(8.0f, 7.15f);
		generalPath.curveTo(8.48f, 6.97f, 9.43f, 7.08f, 9.68f, 7.62f);
		generalPath.curveTo(9.93f, 8.16f, 10.08f, 8.86f, 10.09f, 8.69f);
		generalPath.curveTo(10.0709f, 8.17329f, 10.1145f, 7.65618f, 10.22f, 7.15f);
		generalPath.curveTo(10.3312f, 6.82581f, 10.5858f, 6.57115f, 10.91f, 6.46f);
		generalPath.curveTo(11.2073f, 6.36597f, 11.523f, 6.34538f, 11.83f, 6.4f);
		generalPath.curveTo(12.1404f, 6.46389f, 12.4154f, 6.64242f, 12.6f, 6.9f);
		generalPath.curveTo(12.834f, 7.48313f, 12.9659f, 8.10215f, 12.99f, 8.73f);
		generalPath.curveTo(13.0149f, 8.19425f, 13.1056f, 7.6636f, 13.26f, 7.15f);
		generalPath.curveTo(13.4271f, 6.91455f, 13.6711f, 6.74479f, 13.95f, 6.67f);
		generalPath.curveTo(14.2806f, 6.60955f, 14.6194f, 6.60955f, 14.95f, 6.67f);
		generalPath.curveTo(15.2214f, 6.76005f, 15.4587f, 6.93105f, 15.63f, 7.16f);
		generalPath.curveTo(15.8424f, 7.69015f, 15.9706f, 8.25024f, 16.01f, 8.82f);
		generalPath.curveTo(16.01f, 8.96f, 16.08f, 8.43f, 16.3f, 8.08f);
		generalPath.curveTo(16.4767f, 7.55533f, 17.0453f, 7.27327f, 17.57f, 7.45f);
		generalPath.curveTo(18.0947f, 7.62673f, 18.3767f, 8.19533f, 18.2f, 8.72f);
		generalPath.curveTo(18.2f, 9.37f, 18.2f, 9.34f, 18.2f, 9.78f);
		generalPath.curveTo(18.2f, 10.22f, 18.2f, 10.61f, 18.2f, 10.98f);
		generalPath.curveTo(18.164f, 11.5652f, 18.0837f, 12.1469f, 17.96f, 12.72f);
		generalPath.curveTo(17.7864f, 13.2273f, 17.5442f, 13.7084f, 17.24f, 14.15f);
		generalPath.curveTo(16.7546f, 14.6901f, 16.3534f, 15.3003f, 16.05f, 15.96f);
		generalPath.curveTo(15.976f, 16.288f, 15.9424f, 16.6238f, 15.95f, 16.96f);
		generalPath.curveTo(15.949f, 17.2706f, 15.9894f, 17.58f, 16.07f, 17.88f);
		generalPath.curveTo(15.6612f, 17.9237f, 15.2488f, 17.9237f, 14.84f, 17.88f);
		generalPath.curveTo(14.45f, 17.82f, 13.97f, 17.04f, 13.84f, 16.8f);
		generalPath.curveTo(13.7757f, 16.6711f, 13.644f, 16.5897f, 13.5f, 16.5897f);
		generalPath.curveTo(13.356f, 16.5897f, 13.2243f, 16.6711f, 13.16f, 16.8f);
		generalPath.curveTo(12.94f, 17.18f, 12.45f, 17.87f, 12.16f, 17.91f);
		generalPath.curveTo(11.49f, 17.99f, 10.1f, 17.91f, 9.02f, 17.91f);
		generalPath.curveTo(9.02f, 17.91f, 9.21f, 16.91f, 8.79f, 16.55f);
		generalPath.curveTo(8.37f, 16.19f, 7.96f, 15.77f, 7.65f, 15.49f);
		generalPath.lineTo(6.82f, 14.57f);
		generalPath.curveTo(6.23469f, 14.0267f, 5.80638f, 13.3359f, 5.58f, 12.57f);
		generalPath.curveTo(5.37f, 11.63f, 5.39f, 11.18f, 5.58f, 10.8f);
		generalPath.curveTo(5.77379f, 10.4862f, 6.07639f, 10.2548f, 6.43f, 10.15f);
		generalPath.curveTo(6.72377f, 10.0967f, 7.02618f, 10.1173f, 7.31f, 10.21f);
		generalPath.curveTo(7.50627f, 10.2922f, 7.67589f, 10.4272f, 7.8f, 10.6f);
		generalPath.curveTo(8.03f, 10.91f, 8.11f, 11.06f, 8.01f, 10.72f);
		generalPath.curveTo(7.91f, 10.38f, 7.69f, 10.13f, 7.58f, 9.72f);
		generalPath.curveTo(7.36585f, 9.23579f, 7.23728f, 8.71813f, 7.2f, 8.19f);
		generalPath.curveTo(7.24098f, 7.71616f, 7.57182f, 7.31756f, 8.03f, 7.19f);
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_1
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(0.75f, 0, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(8.0f, 7.15f);
		generalPath.curveTo(8.48f, 6.97f, 9.43f, 7.08f, 9.68f, 7.62f);
		generalPath.curveTo(9.93f, 8.16f, 10.08f, 8.86f, 10.09f, 8.69f);
		generalPath.curveTo(10.0709f, 8.17329f, 10.1145f, 7.65618f, 10.22f, 7.15f);
		generalPath.curveTo(10.3312f, 6.82581f, 10.5858f, 6.57115f, 10.91f, 6.46f);
		generalPath.curveTo(11.2073f, 6.36597f, 11.523f, 6.34538f, 11.83f, 6.4f);
		generalPath.curveTo(12.1404f, 6.46389f, 12.4154f, 6.64242f, 12.6f, 6.9f);
		generalPath.curveTo(12.834f, 7.48313f, 12.9659f, 8.10215f, 12.99f, 8.73f);
		generalPath.curveTo(13.0149f, 8.19425f, 13.1056f, 7.6636f, 13.26f, 7.15f);
		generalPath.curveTo(13.4271f, 6.91455f, 13.6711f, 6.74479f, 13.95f, 6.67f);
		generalPath.curveTo(14.2806f, 6.60955f, 14.6194f, 6.60955f, 14.95f, 6.67f);
		generalPath.curveTo(15.2214f, 6.76005f, 15.4587f, 6.93105f, 15.63f, 7.16f);
		generalPath.curveTo(15.8424f, 7.69015f, 15.9706f, 8.25024f, 16.01f, 8.82f);
		generalPath.curveTo(16.01f, 8.96f, 16.08f, 8.43f, 16.3f, 8.08f);
		generalPath.curveTo(16.4767f, 7.55533f, 17.0453f, 7.27327f, 17.57f, 7.45f);
		generalPath.curveTo(18.0947f, 7.62673f, 18.3767f, 8.19533f, 18.2f, 8.72f);
		generalPath.curveTo(18.2f, 9.37f, 18.2f, 9.34f, 18.2f, 9.78f);
		generalPath.curveTo(18.2f, 10.22f, 18.2f, 10.61f, 18.2f, 10.98f);
		generalPath.curveTo(18.164f, 11.5652f, 18.0837f, 12.1469f, 17.96f, 12.72f);
		generalPath.curveTo(17.7864f, 13.2273f, 17.5442f, 13.7084f, 17.24f, 14.15f);
		generalPath.curveTo(16.7546f, 14.6901f, 16.3534f, 15.3003f, 16.05f, 15.96f);
		generalPath.curveTo(15.976f, 16.288f, 15.9424f, 16.6238f, 15.95f, 16.96f);
		generalPath.curveTo(15.949f, 17.2706f, 15.9894f, 17.58f, 16.07f, 17.88f);
		generalPath.curveTo(15.6612f, 17.9237f, 15.2488f, 17.9237f, 14.84f, 17.88f);
		generalPath.curveTo(14.45f, 17.82f, 13.97f, 17.04f, 13.84f, 16.8f);
		generalPath.curveTo(13.7757f, 16.6711f, 13.644f, 16.5897f, 13.5f, 16.5897f);
		generalPath.curveTo(13.356f, 16.5897f, 13.2243f, 16.6711f, 13.16f, 16.8f);
		generalPath.curveTo(12.94f, 17.18f, 12.45f, 17.87f, 12.16f, 17.91f);
		generalPath.curveTo(11.49f, 17.99f, 10.1f, 17.91f, 9.02f, 17.91f);
		generalPath.curveTo(9.02f, 17.91f, 9.21f, 16.91f, 8.79f, 16.55f);
		generalPath.curveTo(8.37f, 16.19f, 7.96f, 15.77f, 7.65f, 15.49f);
		generalPath.lineTo(6.82f, 14.57f);
		generalPath.curveTo(6.23469f, 14.0267f, 5.80638f, 13.3359f, 5.58f, 12.57f);
		generalPath.curveTo(5.37f, 11.63f, 5.39f, 11.18f, 5.58f, 10.8f);
		generalPath.curveTo(5.77379f, 10.4862f, 6.07639f, 10.2548f, 6.43f, 10.15f);
		generalPath.curveTo(6.72377f, 10.0967f, 7.02618f, 10.1173f, 7.31f, 10.21f);
		generalPath.curveTo(7.50627f, 10.2922f, 7.67589f, 10.4272f, 7.8f, 10.6f);
		generalPath.curveTo(8.03f, 10.91f, 8.11f, 11.06f, 8.01f, 10.72f);
		generalPath.curveTo(7.91f, 10.38f, 7.69f, 10.13f, 7.58f, 9.72f);
		generalPath.curveTo(7.36585f, 9.23579f, 7.23728f, 8.71813f, 7.2f, 8.19f);
		generalPath.curveTo(7.22045f, 7.70923f, 7.54057f, 7.29308f, 8.0f, 7.15f);
		generalPath.closePath();
		shape = generalPath;
		g.setPaint(paint);
		g.setStroke(stroke);
		g.draw(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_2
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(15.75f, 14.8259f);
		generalPath.lineTo(15.75f, 11.3741f);
		generalPath.curveTo(15.75f, 11.1675f, 15.5821f, 11.0f, 15.375f, 11.0f);
		generalPath.curveTo(15.1679f, 11.0f, 15.0f, 11.1675f, 15.0f, 11.3741f);
		generalPath.lineTo(15.0f, 14.8259f);
		generalPath.curveTo(15.0f, 15.0325f, 15.1679f, 15.2f, 15.375f, 15.2f);
		generalPath.curveTo(15.5821f, 15.2f, 15.75f, 15.0325f, 15.75f, 14.8259f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_3
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(13.77f, 14.8246f);
		generalPath.lineTo(13.75f, 11.3711f);
		generalPath.curveTo(13.7488f, 11.165f, 13.5799f, 10.9988f, 13.3728f, 11.0f);
		generalPath.curveTo(13.1657f, 11.0012f, 12.9988f, 11.1693f, 13.0f, 11.3754f);
		generalPath.lineTo(13.02f, 14.8289f);
		generalPath.curveTo(13.0212f, 15.035f, 13.1901f, 15.2012f, 13.3972f, 15.2f);
		generalPath.curveTo(13.6043f, 15.1988f, 13.7712f, 15.0307f, 13.77f, 14.8246f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_4
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(11.0f, 11.3799f);
		generalPath.lineTo(11.02f, 14.8245f);
		generalPath.curveTo(11.0212f, 15.0331f, 11.1901f, 15.2012f, 11.3972f, 15.2f);
		generalPath.curveTo(11.6043f, 15.1988f, 11.7712f, 15.0287f, 11.77f, 14.8201f);
		generalPath.lineTo(11.75f, 11.3755f);
		generalPath.curveTo(11.7488f, 11.1669f, 11.5799f, 10.9988f, 11.3728f, 11.0f);
		generalPath.curveTo(11.1657f, 11.0012f, 10.9988f, 11.1713f, 11.0f, 11.3799f);
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
		return 4.254899978637695;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 5.995160102844238;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 15.172900199890137;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 14.125399589538574;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		GrabbedSvg base = new GrabbedSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		GrabbedSvg base = new GrabbedSvg();
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
		return GrabbedSvg::new;
	}
}

