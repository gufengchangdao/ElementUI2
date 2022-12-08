package com.element.ui.svg.icon.fill;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class NewSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public NewSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}


	private void _paint0(Graphics2D g, float origAlpha) {
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
		generalPath.moveTo(918.673f, 883.0f);
		generalPath.lineTo(104.327f, 883.0f);
		generalPath.curveTo(82.578f, 883.0f, 65.0f, 867.368f, 65.0f, 848.027f);
		generalPath.lineTo(65.0f, 276.973f);
		generalPath.curveTo(65.0f, 257.632f, 82.578f, 242.0f, 104.327f, 242.0f);
		generalPath.lineTo(918.67303f, 242.0f);
		generalPath.curveTo(940.422f, 242.0f, 958.0f, 257.632f, 958.0f, 276.973f);
		generalPath.lineTo(958.0f, 848.027f);
		generalPath.curveTo(958.0f, 867.28f, 940.323f, 883.0f, 918.673f, 883.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 233, 180, 255)) : new Color(255, 233, 180, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_1
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(512.0f, 411.0f);
		generalPath.lineTo(65.0f, 411.0f);
		generalPath.lineTo(65.0f, 210.37f);
		generalPath.curveTo(65.0f, 188.597f, 82.598f, 171.0f, 104.371f, 171.0f);
		generalPath.lineTo(410.29102f, 171.0f);
		generalPath.curveTo(427.691f, 171.0f, 443.001f, 182.334f, 447.97202f, 199.036f);
		generalPath.lineTo(512.0f, 411.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 176, 44, 255)) : new Color(255, 176, 44, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_2
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(918.673f, 883.0f);
		generalPath.lineTo(104.327f, 883.0f);
		generalPath.curveTo(82.578f, 883.0f, 65.0f, 865.42f, 65.0f, 843.668f);
		generalPath.lineTo(65.0f, 335.332f);
		generalPath.curveTo(65.0f, 313.58f, 82.578f, 296.0f, 104.327f, 296.0f);
		generalPath.lineTo(918.67303f, 296.0f);
		generalPath.curveTo(940.422f, 296.0f, 958.0f, 313.58f, 958.0f, 335.332f);
		generalPath.lineTo(958.0f, 843.66797f);
		generalPath.curveTo(958.0f, 865.32f, 940.323f, 883.0f, 918.673f, 883.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 202, 40, 255)) : new Color(255, 202, 40, 255);
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
		return 1.015625;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 2.671875;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 13.953125;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 11.125;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		NewSvg base = new NewSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		NewSvg base = new NewSvg();
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
		return NewSvg::new;
	}
}

