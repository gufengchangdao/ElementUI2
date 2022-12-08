package com.element.ui.svg.icon.regular;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class TrashSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public TrashSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}


	private void _paint0(Graphics2D g, float origAlpha) {
// 
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(2.0f, 1, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(27.0f, 7.0f);
		generalPath.lineTo(5.0f, 7.0f);
		shape = generalPath;
		g.setPaint(paint);
		g.setStroke(stroke);
		g.draw(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_1
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(2.0f, 1, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(13.0f, 13.0f);
		generalPath.lineTo(13.0f, 21.0f);
		shape = generalPath;
		g.setPaint(paint);
		g.setStroke(stroke);
		g.draw(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_2
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(2.0f, 1, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(19.0f, 13.0f);
		generalPath.lineTo(19.0f, 21.0f);
		shape = generalPath;
		g.setPaint(paint);
		g.setStroke(stroke);
		g.draw(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_3
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(2.0f, 1, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(25.0f, 7.0f);
		generalPath.lineTo(25.0f, 26.0f);
		generalPath.curveTo(25.0f, 26.2652f, 24.8946f, 26.5196f, 24.7071f, 26.7071f);
		generalPath.curveTo(24.5196f, 26.8946f, 24.2652f, 27.0f, 24.0f, 27.0f);
		generalPath.lineTo(8.0f, 27.0f);
		generalPath.curveTo(7.73478f, 27.0f, 7.48043f, 26.8946f, 7.29289f, 26.7071f);
		generalPath.curveTo(7.10536f, 26.5196f, 7.0f, 26.2652f, 7.0f, 26.0f);
		generalPath.lineTo(7.0f, 7.0f);
		shape = generalPath;
		g.setPaint(paint);
		g.setStroke(stroke);
		g.draw(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_4
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(2.0f, 1, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(21.0f, 7.0f);
		generalPath.lineTo(21.0f, 5.0f);
		generalPath.curveTo(21.0f, 4.46957f, 20.7893f, 3.96086f, 20.4142f, 3.58579f);
		generalPath.curveTo(20.0391f, 3.21071f, 19.5304f, 3.0f, 19.0f, 3.0f);
		generalPath.lineTo(13.0f, 3.0f);
		generalPath.curveTo(12.4696f, 3.0f, 11.9609f, 3.21071f, 11.5858f, 3.58579f);
		generalPath.curveTo(11.2107f, 3.96086f, 11.0f, 4.46957f, 11.0f, 5.0f);
		generalPath.lineTo(11.0f, 7.0f);
		shape = generalPath;
		g.setPaint(paint);
		g.setStroke(stroke);
		g.draw(shape);

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
		return 2.0;
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
		return 26.0;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		TrashSvg base = new TrashSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		TrashSvg base = new TrashSvg();
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
		return TrashSvg::new;
	}
}

