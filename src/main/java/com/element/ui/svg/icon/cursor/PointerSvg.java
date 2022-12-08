package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class PointerSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public PointerSvg() {
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
		generalPath.moveTo(8.27f, 16.28f);
		generalPath.curveTo(7.99f, 15.92f, 7.64f, 15.19f, 7.03f, 14.28f);
		generalPath.curveTo(6.68f, 13.78f, 5.82f, 12.83f, 5.56f, 12.34f);
		generalPath.curveTo(5.37257f, 12.0422f, 5.31819f, 11.6797f, 5.41f, 11.34f);
		generalPath.curveTo(5.56696f, 10.6942f, 6.17956f, 10.2658f, 6.84f, 10.34f);
		generalPath.curveTo(7.3508f, 10.4426f, 7.82022f, 10.693f, 8.19f, 11.06f);
		generalPath.curveTo(8.44818f, 11.3032f, 8.68567f, 11.5674f, 8.9f, 11.85f);
		generalPath.curveTo(9.06f, 12.05f, 9.1f, 12.13f, 9.28f, 12.36f);
		generalPath.curveTo(9.46f, 12.59f, 9.58f, 12.82f, 9.49f, 12.48f);
		generalPath.curveTo(9.42f, 11.98f, 9.3f, 11.14f, 9.13f, 10.39f);
		generalPath.curveTo(9.0f, 9.82f, 8.97f, 9.73f, 8.85f, 9.3f);
		generalPath.curveTo(8.73f, 8.87f, 8.66f, 8.51f, 8.53f, 8.02f);
		generalPath.curveTo(8.41117f, 7.53858f, 8.31771f, 7.05124f, 8.25f, 6.56f);
		generalPath.curveTo(8.12395f, 5.93171f, 8.21566f, 5.27922f, 8.51f, 4.71f);
		generalPath.curveTo(8.8594f, 4.38137f, 9.37193f, 4.29464f, 9.81f, 4.49f);
		generalPath.curveTo(10.2506f, 4.81534f, 10.5791f, 5.26966f, 10.75f, 5.79f);
		generalPath.curveTo(11.0121f, 6.43039f, 11.187f, 7.10307f, 11.27f, 7.79f);
		generalPath.curveTo(11.43f, 8.79f, 11.74f, 10.25f, 11.75f, 10.55f);
		generalPath.curveTo(11.75f, 10.18f, 11.68f, 9.4f, 11.75f, 9.05f);
		generalPath.curveTo(11.8194f, 8.68513f, 12.073f, 8.38232f, 12.42f, 8.25f);
		generalPath.curveTo(12.7178f, 8.15863f, 13.0328f, 8.13808f, 13.34f, 8.19f);
		generalPath.curveTo(13.65f, 8.25482f, 13.9247f, 8.43315f, 14.11f, 8.69f);
		generalPath.curveTo(14.3417f, 9.2734f, 14.4703f, 9.8926f, 14.49f, 10.52f);
		generalPath.curveTo(14.5168f, 9.97059f, 14.6108f, 9.42653f, 14.77f, 8.9f);
		generalPath.curveTo(14.9371f, 8.66455f, 15.1811f, 8.49479f, 15.46f, 8.42f);
		generalPath.curveTo(15.7906f, 8.35956f, 16.1294f, 8.35956f, 16.46f, 8.42f);
		generalPath.curveTo(16.7311f, 8.51063f, 16.9682f, 8.68152f, 17.14f, 8.91f);
		generalPath.curveTo(17.3518f, 9.44035f, 17.48f, 10.0003f, 17.52f, 10.57f);
		generalPath.curveTo(17.52f, 10.71f, 17.59f, 10.18f, 17.81f, 9.83f);
		generalPath.curveTo(17.9243f, 9.4906f, 18.211f, 9.23797f, 18.5621f, 9.16728f);
		generalPath.curveTo(18.9132f, 9.09659f, 19.2754f, 9.21857f, 19.5121f, 9.48728f);
		generalPath.curveTo(19.7489f, 9.75599f, 19.8243f, 10.1306f, 19.71f, 10.47f);
		generalPath.curveTo(19.71f, 11.12f, 19.71f, 11.09f, 19.71f, 11.53f);
		generalPath.curveTo(19.71f, 11.97f, 19.71f, 12.36f, 19.71f, 12.73f);
		generalPath.curveTo(19.6736f, 13.3152f, 19.5933f, 13.8968f, 19.47f, 14.47f);
		generalPath.curveTo(19.296f, 14.9771f, 19.0538f, 15.4582f, 18.75f, 15.9f);
		generalPath.curveTo(18.2645f, 16.44f, 17.8633f, 17.0502f, 17.56f, 17.71f);
		generalPath.curveTo(17.4848f, 18.0378f, 17.4512f, 18.3738f, 17.46f, 18.71f);
		generalPath.curveTo(17.459f, 19.0206f, 17.4994f, 19.33f, 17.58f, 19.63f);
		generalPath.curveTo(17.1711f, 19.6732f, 16.7589f, 19.6732f, 16.35f, 19.63f);
		generalPath.curveTo(15.96f, 19.57f, 15.48f, 18.79f, 15.35f, 18.55f);
		generalPath.curveTo(15.2857f, 18.4211f, 15.154f, 18.3397f, 15.01f, 18.3397f);
		generalPath.curveTo(14.866f, 18.3397f, 14.7343f, 18.4211f, 14.67f, 18.55f);
		generalPath.curveTo(14.45f, 18.93f, 13.96f, 19.62f, 13.62f, 19.66f);
		generalPath.curveTo(12.95f, 19.74f, 11.57f, 19.66f, 10.48f, 19.66f);
		generalPath.curveTo(10.48f, 19.66f, 10.66f, 18.66f, 10.25f, 18.3f);
		generalPath.curveTo(9.84f, 17.94f, 9.42f, 17.52f, 9.11f, 17.24f);
		generalPath.lineTo(8.27f, 16.28f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_1
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(0.75f, 1, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(8.27f, 16.28f);
		generalPath.curveTo(7.99f, 15.92f, 7.64f, 15.19f, 7.03f, 14.28f);
		generalPath.curveTo(6.68f, 13.78f, 5.82f, 12.83f, 5.56f, 12.34f);
		generalPath.curveTo(5.37257f, 12.0422f, 5.31819f, 11.6797f, 5.41f, 11.34f);
		generalPath.curveTo(5.56696f, 10.6942f, 6.17956f, 10.2658f, 6.84f, 10.34f);
		generalPath.curveTo(7.3508f, 10.4426f, 7.82022f, 10.693f, 8.19f, 11.06f);
		generalPath.curveTo(8.44818f, 11.3032f, 8.68567f, 11.5674f, 8.9f, 11.85f);
		generalPath.curveTo(9.06f, 12.05f, 9.1f, 12.13f, 9.28f, 12.36f);
		generalPath.curveTo(9.46f, 12.59f, 9.58f, 12.82f, 9.49f, 12.48f);
		generalPath.curveTo(9.42f, 11.98f, 9.3f, 11.14f, 9.13f, 10.39f);
		generalPath.curveTo(9.0f, 9.82f, 8.97f, 9.73f, 8.85f, 9.3f);
		generalPath.curveTo(8.73f, 8.87f, 8.66f, 8.51f, 8.53f, 8.02f);
		generalPath.curveTo(8.41117f, 7.53858f, 8.31771f, 7.05124f, 8.25f, 6.56f);
		generalPath.curveTo(8.12395f, 5.93171f, 8.21566f, 5.27922f, 8.51f, 4.71f);
		generalPath.curveTo(8.85939f, 4.38137f, 9.37193f, 4.29464f, 9.81f, 4.49f);
		generalPath.curveTo(10.2506f, 4.81534f, 10.5791f, 5.26966f, 10.75f, 5.79f);
		generalPath.curveTo(11.0121f, 6.43039f, 11.187f, 7.10307f, 11.27f, 7.79f);
		generalPath.curveTo(11.43f, 8.79f, 11.74f, 10.25f, 11.75f, 10.55f);
		generalPath.curveTo(11.75f, 10.18f, 11.68f, 9.4f, 11.75f, 9.05f);
		generalPath.curveTo(11.8194f, 8.68513f, 12.073f, 8.38232f, 12.42f, 8.25f);
		generalPath.curveTo(12.7178f, 8.15863f, 13.0328f, 8.13808f, 13.34f, 8.19f);
		generalPath.curveTo(13.65f, 8.25482f, 13.9247f, 8.43315f, 14.11f, 8.69f);
		generalPath.curveTo(14.3417f, 9.2734f, 14.4703f, 9.8926f, 14.49f, 10.52f);
		generalPath.curveTo(14.5168f, 9.97059f, 14.6108f, 9.42653f, 14.77f, 8.9f);
		generalPath.curveTo(14.9371f, 8.66455f, 15.1811f, 8.49479f, 15.46f, 8.42f);
		generalPath.curveTo(15.7906f, 8.35956f, 16.1294f, 8.35956f, 16.46f, 8.42f);
		generalPath.curveTo(16.7311f, 8.51063f, 16.9682f, 8.68152f, 17.14f, 8.91f);
		generalPath.curveTo(17.3518f, 9.44035f, 17.48f, 10.0003f, 17.52f, 10.57f);
		generalPath.curveTo(17.52f, 10.71f, 17.59f, 10.18f, 17.81f, 9.83f);
		generalPath.curveTo(17.9243f, 9.4906f, 18.211f, 9.23797f, 18.5621f, 9.16728f);
		generalPath.curveTo(18.9132f, 9.09659f, 19.2754f, 9.21857f, 19.5121f, 9.48728f);
		generalPath.curveTo(19.7489f, 9.75599f, 19.8243f, 10.1306f, 19.71f, 10.47f);
		generalPath.curveTo(19.71f, 11.12f, 19.71f, 11.09f, 19.71f, 11.53f);
		generalPath.curveTo(19.71f, 11.97f, 19.71f, 12.36f, 19.71f, 12.73f);
		generalPath.curveTo(19.6736f, 13.3152f, 19.5933f, 13.8968f, 19.47f, 14.47f);
		generalPath.curveTo(19.296f, 14.9771f, 19.0538f, 15.4582f, 18.75f, 15.9f);
		generalPath.curveTo(18.2644f, 16.44f, 17.8633f, 17.0502f, 17.56f, 17.71f);
		generalPath.curveTo(17.4848f, 18.0378f, 17.4512f, 18.3738f, 17.46f, 18.71f);
		generalPath.curveTo(17.459f, 19.0206f, 17.4994f, 19.33f, 17.58f, 19.63f);
		generalPath.curveTo(17.1711f, 19.6732f, 16.7589f, 19.6732f, 16.35f, 19.63f);
		generalPath.curveTo(15.96f, 19.57f, 15.48f, 18.79f, 15.35f, 18.55f);
		generalPath.curveTo(15.2857f, 18.4211f, 15.154f, 18.3397f, 15.01f, 18.3397f);
		generalPath.curveTo(14.866f, 18.3397f, 14.7343f, 18.4211f, 14.67f, 18.55f);
		generalPath.curveTo(14.45f, 18.93f, 13.96f, 19.62f, 13.62f, 19.66f);
		generalPath.curveTo(12.95f, 19.74f, 11.57f, 19.66f, 10.48f, 19.66f);
		generalPath.curveTo(10.48f, 19.66f, 10.66f, 18.66f, 10.25f, 18.3f);
		generalPath.curveTo(9.84f, 17.94f, 9.42f, 17.52f, 9.11f, 17.24f);
		generalPath.lineTo(8.27f, 16.28f);
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
		generalPath.moveTo(16.75f, 16.8259f);
		generalPath.lineTo(16.75f, 13.3741f);
		generalPath.curveTo(16.75f, 13.1675f, 16.5821f, 13.0f, 16.375f, 13.0f);
		generalPath.curveTo(16.1679f, 13.0f, 16.0f, 13.1675f, 16.0f, 13.3741f);
		generalPath.lineTo(16.0f, 16.8259f);
		generalPath.curveTo(16.0f, 17.0325f, 16.1679f, 17.2f, 16.375f, 17.2f);
		generalPath.curveTo(16.5821f, 17.2f, 16.75f, 17.0325f, 16.75f, 16.8259f);
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
		generalPath.moveTo(14.77f, 16.8246f);
		generalPath.lineTo(14.75f, 13.3711f);
		generalPath.curveTo(14.7488f, 13.1649f, 14.5799f, 12.9988f, 14.3728f, 13.0f);
		generalPath.curveTo(14.1657f, 13.0012f, 13.9988f, 13.1693f, 14.0f, 13.3754f);
		generalPath.lineTo(14.02f, 16.8289f);
		generalPath.curveTo(14.0212f, 17.035f, 14.1901f, 17.2012f, 14.3972f, 17.2f);
		generalPath.curveTo(14.6043f, 17.1988f, 14.7712f, 17.0307f, 14.77f, 16.8246f);
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
		generalPath.moveTo(12.0f, 13.379f);
		generalPath.lineTo(12.02f, 16.8254f);
		generalPath.curveTo(12.0212f, 17.0335f, 12.1901f, 17.2012f, 12.3972f, 17.2f);
		generalPath.curveTo(12.6043f, 17.1988f, 12.7712f, 17.0291f, 12.77f, 16.821f);
		generalPath.lineTo(12.75f, 13.3746f);
		generalPath.curveTo(12.7488f, 13.1665f, 12.5799f, 12.9988f, 12.3728f, 13.0f);
		generalPath.curveTo(12.1657f, 13.0012f, 11.9988f, 13.1709f, 12.0f, 13.379f);
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
		return 4.19133996963501;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 4.01177978515625;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 16.7460994720459;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 17.858800888061523;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		PointerSvg base = new PointerSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		PointerSvg base = new PointerSvg();
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
		return PointerSvg::new;
	}
}

