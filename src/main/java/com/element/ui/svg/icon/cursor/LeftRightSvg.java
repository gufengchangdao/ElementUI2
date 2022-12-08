package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class LeftRightSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public LeftRightSvg() {
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
		generalPath.moveTo(10.0f, 4.0f);
		generalPath.lineTo(10.0f, 10.01f);
		generalPath.lineTo(8.98f, 10.01f);
		generalPath.lineTo(8.98f, 6.54f);
		generalPath.lineTo(3.0f, 12.01f);
		generalPath.lineTo(8.98f, 17.47f);
		generalPath.lineTo(8.98f, 13.99f);
		generalPath.lineTo(10.0f, 13.99f);
		generalPath.lineTo(10.0f, 20.0f);
		generalPath.lineTo(10.02f, 20.0f);
		generalPath.lineTo(13.98f, 20.0f);
		generalPath.lineTo(13.98f, 13.99f);
		generalPath.lineTo(15.04f, 13.99f);
		generalPath.lineTo(15.04f, 17.46f);
		generalPath.lineTo(21.0f, 11.99f);
		generalPath.lineTo(15.04f, 6.53f);
		generalPath.lineTo(15.04f, 10.0f);
		generalPath.lineTo(13.98f, 10.0f);
		generalPath.lineTo(13.98f, 4.0f);
		generalPath.lineTo(10.0f, 4.0f);
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
		generalPath.moveTo(19.34f, 11.99f);
		generalPath.lineTo(16.02f, 8.76999f);
		generalPath.lineTo(16.02f, 11.02f);
		generalPath.lineTo(13.0f, 11.02f);
		generalPath.lineTo(13.0f, 5.05999f);
		generalPath.lineTo(12.98f, 5.05999f);
		generalPath.lineTo(12.98f, 5.03999f);
		generalPath.lineTo(11.04f, 5.03999f);
		generalPath.lineTo(11.04f, 11.04f);
		generalPath.lineTo(7.98f, 11.04f);
		generalPath.lineTo(7.98f, 8.75999f);
		generalPath.lineTo(4.66f, 12.01f);
		generalPath.lineTo(7.98f, 15.23f);
		generalPath.lineTo(7.98f, 12.98f);
		generalPath.lineTo(11.04f, 12.98f);
		generalPath.lineTo(11.04f, 18.98f);
		generalPath.lineTo(11.06f, 18.98f);
		generalPath.lineTo(12.98f, 18.98f);
		generalPath.lineTo(13.0f, 18.98f);
		generalPath.lineTo(13.0f, 12.98f);
		generalPath.lineTo(16.02f, 12.98f);
		generalPath.lineTo(16.02f, 15.24f);
		generalPath.lineTo(19.34f, 11.99f);
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
		return 1.2000000476837158;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 3.200000047683716;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 21.600000381469727;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 19.600000381469727;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		LeftRightSvg base = new LeftRightSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		LeftRightSvg base = new LeftRightSvg();
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
		return LeftRightSvg::new;
	}
}

