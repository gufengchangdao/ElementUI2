package com.element.ui.svg.icon.fill;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Base64;

public class HomeSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public HomeSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}

	

	private void _paint0(Graphics2D g,float origAlpha) {
// 
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
transformsStack.push(g.getTransform());
g.transform(new AffineTransform(0.015625f, 0.0f, 0.0f, 0.015625f, -0.0f, -0.0f));
// _0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(902.268f, 514.39f);
generalPath.lineTo(511.236f, 216.342f);
generalPath.lineTo(120.203f, 514.39f);
generalPath.lineTo(64.037f, 440.70203f);
generalPath.lineTo(511.236f, 99.843f);
generalPath.lineTo(958.43396f, 440.70203f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(244, 86, 86, 255)) : new Color(244, 86, 86, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(513.985f, 329.359f);
generalPath.lineTo(192.0f, 565.269f);
generalPath.lineTo(192.0f, 833.00397f);
generalPath.curveTo(192.0f, 862.683f, 216.589f, 887.0f, 246.268f, 887.0f);
generalPath.lineTo(413.0f, 887.0f);
generalPath.lineTo(413.0f, 623.0f);
generalPath.lineTo(608.0f, 623.0f);
generalPath.lineTo(608.0f, 887.0f);
generalPath.lineTo(776.93f, 887.0f);
generalPath.curveTo(806.609f, 887.0f, 830.0f, 862.683f, 830.0f, 833.004f);
generalPath.lineTo(830.0f, 565.269f);
generalPath.lineTo(513.985f, 329.35898f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(189, 204, 212, 255)) : new Color(189, 204, 212, 255);
g.setPaint(paint);
g.fill(shape);
g.setTransform(transformsStack.pop());

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
		return 1.0005781650543213;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 1.560046911239624;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 13.974952697753906;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 12.299327850341797;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		HomeSvg base = new HomeSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		HomeSvg base = new HomeSvg();
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
		return HomeSvg::new;
	}
}

