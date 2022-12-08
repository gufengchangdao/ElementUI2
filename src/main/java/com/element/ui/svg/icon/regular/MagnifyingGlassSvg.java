package com.element.ui.svg.icon.regular;

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

public class MagnifyingGlassSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public MagnifyingGlassSvg() {
		this.width = (int) getOrigWidth();
		this.height = (int) getOrigHeight();
	}

	

	private void _paint0(Graphics2D g,float origAlpha) {
// 
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
stroke = new BasicStroke(2.0f,1,1,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(14.5f, 25.0f);
generalPath.curveTo(20.299f, 25.0f, 25.0f, 20.299f, 25.0f, 14.5f);
generalPath.curveTo(25.0f, 8.70101f, 20.299f, 4.0f, 14.5f, 4.0f);
generalPath.curveTo(8.70101f, 4.0f, 4.0f, 8.70101f, 4.0f, 14.5f);
generalPath.curveTo(4.0f, 20.299f, 8.70101f, 25.0f, 14.5f, 25.0f);
generalPath.closePath();
shape = generalPath;
g.setPaint(paint);
g.setStroke(stroke);
g.draw(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_1
paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
stroke = new BasicStroke(2.0f,1,1,4.0f,null,0.0f);
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(21.925f, 21.925f);
generalPath.lineTo(28.0f, 28.0f);
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
		return 3.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 3.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 26.097631454467773;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 26.097631454467773;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		MagnifyingGlassSvg base = new MagnifyingGlassSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		MagnifyingGlassSvg base = new MagnifyingGlassSvg();
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
		return MagnifyingGlassSvg::new;
	}
}

