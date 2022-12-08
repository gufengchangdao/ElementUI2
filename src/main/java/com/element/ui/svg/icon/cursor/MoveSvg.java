package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class MoveSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public MoveSvg() {
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
		generalPath.moveTo(12.0f, 4.0f);
		generalPath.lineTo(4.0f, 12.0f);
		generalPath.lineTo(9.22f, 17.22f);
		generalPath.lineTo(12.0f, 20.0f);
		generalPath.lineTo(20.0f, 12.0f);
		generalPath.lineTo(12.0f, 4.0f);
		generalPath.closePath();
		generalPath.moveTo(10.0f, 15.0f);
		generalPath.lineTo(9.0f, 15.0f);
		generalPath.lineTo(9.0f, 14.0f);
		generalPath.lineTo(10.0f, 14.0f);
		generalPath.lineTo(10.0f, 15.0f);
		generalPath.closePath();
		generalPath.moveTo(10.0f, 10.0f);
		generalPath.lineTo(9.0f, 10.0f);
		generalPath.lineTo(9.0f, 9.0f);
		generalPath.lineTo(10.0f, 9.0f);
		generalPath.lineTo(10.0f, 10.0f);
		generalPath.closePath();
		generalPath.moveTo(15.0f, 15.0f);
		generalPath.lineTo(14.0f, 15.0f);
		generalPath.lineTo(14.0f, 14.0f);
		generalPath.lineTo(15.0f, 14.0f);
		generalPath.lineTo(15.0f, 15.0f);
		generalPath.closePath();
		generalPath.moveTo(14.0f, 9.0f);
		generalPath.lineTo(15.0f, 9.0f);
		generalPath.lineTo(15.0f, 10.0f);
		generalPath.lineTo(14.0f, 10.0f);
		generalPath.lineTo(14.0f, 9.0f);
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
		generalPath.moveTo(18.55f, 11.98f);
		generalPath.lineTo(15.99f, 9.17f);
		generalPath.lineTo(15.99f, 11.0f);
		generalPath.lineTo(11.99f, 11.0f);
		generalPath.lineTo(7.98f, 11.0f);
		generalPath.lineTo(7.98f, 9.17f);
		generalPath.lineTo(5.41f, 11.98f);
		generalPath.lineTo(7.98f, 14.79f);
		generalPath.lineTo(7.98f, 12.98f);
		generalPath.lineTo(11.99f, 12.98f);
		generalPath.lineTo(15.99f, 12.98f);
		generalPath.lineTo(15.99f, 14.79f);
		generalPath.lineTo(18.55f, 11.98f);
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
		generalPath.moveTo(12.97f, 11.99f);
		generalPath.lineTo(12.98f, 11.99f);
		generalPath.lineTo(12.98f, 7.99f);
		generalPath.lineTo(14.79f, 7.99f);
		generalPath.lineTo(11.99f, 5.42f);
		generalPath.lineTo(9.18f, 7.99f);
		generalPath.lineTo(10.99f, 7.99f);
		generalPath.lineTo(10.99f, 11.99f);
		generalPath.lineTo(10.99f, 15.99f);
		generalPath.lineTo(9.17f, 15.99f);
		generalPath.lineTo(11.97f, 18.56f);
		generalPath.lineTo(14.78f, 15.99f);
		generalPath.lineTo(12.97f, 15.99f);
		generalPath.lineTo(12.97f, 11.99f);
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
		return 3.200000047683716;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 19.600000381469727;
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
		MoveSvg base = new MoveSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		MoveSvg base = new MoveSvg();
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
		return MoveSvg::new;
	}
}

