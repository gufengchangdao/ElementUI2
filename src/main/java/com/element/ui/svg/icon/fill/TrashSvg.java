package com.element.ui.svg.icon.fill;

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
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(27.0f, 6.0f);
		generalPath.lineTo(22.0f, 6.0f);
		generalPath.lineTo(22.0f, 5.0f);
		generalPath.curveTo(21.9967f, 4.20536f, 21.6796f, 3.44421f, 21.1177f, 2.88231f);
		generalPath.curveTo(20.5558f, 2.32042f, 19.7946f, 2.00329f, 19.0f, 2.0f);
		generalPath.lineTo(13.0f, 2.0f);
		generalPath.curveTo(12.2054f, 2.00329f, 11.4442f, 2.32042f, 10.8823f, 2.88231f);
		generalPath.curveTo(10.3204f, 3.44421f, 10.0033f, 4.20536f, 10.0f, 5.0f);
		generalPath.lineTo(10.0f, 6.0f);
		generalPath.lineTo(5.0f, 6.0f);
		generalPath.curveTo(4.73478f, 6.0f, 4.48043f, 6.10536f, 4.29289f, 6.29289f);
		generalPath.curveTo(4.10536f, 6.48043f, 4.0f, 6.73478f, 4.0f, 7.0f);
		generalPath.curveTo(4.0f, 7.26522f, 4.10536f, 7.51957f, 4.29289f, 7.70711f);
		generalPath.curveTo(4.48043f, 7.89464f, 4.73478f, 8.0f, 5.0f, 8.0f);
		generalPath.lineTo(6.0f, 8.0f);
		generalPath.lineTo(6.0f, 26.0f);
		generalPath.curveTo(6.0f, 26.5304f, 6.21071f, 27.0391f, 6.58579f, 27.4142f);
		generalPath.curveTo(6.96086f, 27.7893f, 7.46957f, 28.0f, 8.0f, 28.0f);
		generalPath.lineTo(24.0f, 28.0f);
		generalPath.curveTo(24.5304f, 28.0f, 25.0391f, 27.7893f, 25.4142f, 27.4142f);
		generalPath.curveTo(25.7893f, 27.0391f, 26.0f, 26.5304f, 26.0f, 26.0f);
		generalPath.lineTo(26.0f, 8.0f);
		generalPath.lineTo(27.0f, 8.0f);
		generalPath.curveTo(27.2652f, 8.0f, 27.5196f, 7.89464f, 27.7071f, 7.70711f);
		generalPath.curveTo(27.8946f, 7.51957f, 28.0f, 7.26522f, 28.0f, 7.0f);
		generalPath.curveTo(28.0f, 6.73478f, 27.8946f, 6.48043f, 27.7071f, 6.29289f);
		generalPath.curveTo(27.5196f, 6.10536f, 27.2652f, 6.0f, 27.0f, 6.0f);
		generalPath.closePath();
		generalPath.moveTo(14.0f, 21.0f);
		generalPath.curveTo(14.0f, 21.2652f, 13.8946f, 21.5196f, 13.7071f, 21.7071f);
		generalPath.curveTo(13.5196f, 21.8946f, 13.2652f, 22.0f, 13.0f, 22.0f);
		generalPath.curveTo(12.7348f, 22.0f, 12.4804f, 21.8946f, 12.2929f, 21.7071f);
		generalPath.curveTo(12.1054f, 21.5196f, 12.0f, 21.2652f, 12.0f, 21.0f);
		generalPath.lineTo(12.0f, 13.0f);
		generalPath.curveTo(12.0f, 12.7348f, 12.1054f, 12.4804f, 12.2929f, 12.2929f);
		generalPath.curveTo(12.4804f, 12.1054f, 12.7348f, 12.0f, 13.0f, 12.0f);
		generalPath.curveTo(13.2652f, 12.0f, 13.5196f, 12.1054f, 13.7071f, 12.2929f);
		generalPath.curveTo(13.8946f, 12.4804f, 14.0f, 12.7348f, 14.0f, 13.0f);
		generalPath.lineTo(14.0f, 21.0f);
		generalPath.closePath();
		generalPath.moveTo(20.0f, 21.0f);
		generalPath.curveTo(20.0f, 21.2652f, 19.8946f, 21.5196f, 19.7071f, 21.7071f);
		generalPath.curveTo(19.5196f, 21.8946f, 19.2652f, 22.0f, 19.0f, 22.0f);
		generalPath.curveTo(18.7348f, 22.0f, 18.4804f, 21.8946f, 18.2929f, 21.7071f);
		generalPath.curveTo(18.1054f, 21.5196f, 18.0f, 21.2652f, 18.0f, 21.0f);
		generalPath.lineTo(18.0f, 13.0f);
		generalPath.curveTo(18.0f, 12.7348f, 18.1054f, 12.4804f, 18.2929f, 12.2929f);
		generalPath.curveTo(18.4804f, 12.1054f, 18.7348f, 12.0f, 19.0f, 12.0f);
		generalPath.curveTo(19.2652f, 12.0f, 19.5196f, 12.1054f, 19.7071f, 12.2929f);
		generalPath.curveTo(19.8946f, 12.4804f, 20.0f, 12.7348f, 20.0f, 13.0f);
		generalPath.lineTo(20.0f, 21.0f);
		generalPath.closePath();
		generalPath.moveTo(20.0f, 6.0f);
		generalPath.lineTo(12.0f, 6.0f);
		generalPath.lineTo(12.0f, 5.0f);
		generalPath.curveTo(12.0f, 4.73478f, 12.1054f, 4.48043f, 12.2929f, 4.29289f);
		generalPath.curveTo(12.4804f, 4.10536f, 12.7348f, 4.0f, 13.0f, 4.0f);
		generalPath.lineTo(19.0f, 4.0f);
		generalPath.curveTo(19.2652f, 4.0f, 19.5196f, 4.10536f, 19.7071f, 4.29289f);
		generalPath.curveTo(19.8946f, 4.48043f, 20.0f, 4.73478f, 20.0f, 5.0f);
		generalPath.lineTo(20.0f, 6.0f);
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

