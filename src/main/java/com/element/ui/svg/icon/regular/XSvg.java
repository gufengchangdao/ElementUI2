package com.element.ui.svg.icon.regular;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class XSvg extends AbstractSvgIcon {
	public XSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}


	private void _paint0(Graphics2D g, float origAlpha) {
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(2.0f, 1, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(25.0f, 7.0f);
		generalPath.lineTo(7.0f, 25.0f);
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
		generalPath.moveTo(25.0f, 25.0f);
		generalPath.lineTo(7.0f, 7.0f);
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

	public static double getOrigX() {
		return 5.902369022369385;
	}

	public static double getOrigY() {
		return 5.902369022369385;
	}

	public static double getOrigWidth() {
		return 20.195262908935547;
	}

	public static double getOrigHeight() {
		return 20.195262908935547;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	public static SvgIcon of(int width, int height) {
		XSvg base = new XSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	public static SvgIconUIResource uiResourceOf(int width, int height) {
		XSvg base = new XSvg();
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

	public static Factory factory() {
		return XSvg::new;
	}
}

