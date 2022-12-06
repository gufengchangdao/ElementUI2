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

public class DesktopSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public DesktopSvg() {
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
generalPath.moveTo(170.66667f, 213.33333f);
generalPath.lineTo(874.6667f, 213.33333f);
generalPath.lineTo(874.6667f, 725.3333f);
generalPath.lineTo(170.66667f, 725.3333f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(217, 236, 255, 255)) : new Color(217, 236, 255, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(853.3333f, 234.66667f);
generalPath.lineTo(853.3333f, 704.0f);
generalPath.lineTo(192.0f, 704.0f);
generalPath.lineTo(192.0f, 234.66667f);
generalPath.lineTo(853.3333f, 234.66667f);
generalPath.moveTo(874.6666f, 192.0f);
generalPath.lineTo(170.66667f, 192.0f);
generalPath.curveTo(158.8846f, 192.0f, 149.33334f, 201.55125f, 149.33334f, 213.33333f);
generalPath.lineTo(149.33334f, 725.3333f);
generalPath.curveTo(149.33336f, 737.11536f, 158.88461f, 746.6666f, 170.66667f, 746.6666f);
generalPath.lineTo(874.6667f, 746.6666f);
generalPath.curveTo(886.4487f, 746.6666f, 896.0f, 737.11536f, 896.0f, 725.3333f);
generalPath.lineTo(896.0f, 213.33333f);
generalPath.curveTo(896.0f, 201.55127f, 886.4487f, 192.00002f, 874.6667f, 192.0f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(64, 158, 255, 255)) : new Color(64, 158, 255, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_2
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(170.66667f, 597.3333f);
generalPath.lineTo(874.6667f, 597.3333f);
generalPath.lineTo(874.6667f, 725.3333f);
generalPath.lineTo(170.66667f, 725.3333f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_3
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(853.3333f, 618.6667f);
generalPath.lineTo(853.3333f, 704.0f);
generalPath.lineTo(192.0f, 704.0f);
generalPath.lineTo(192.0f, 618.6667f);
generalPath.lineTo(853.3333f, 618.6667f);
generalPath.moveTo(874.6666f, 576.0f);
generalPath.lineTo(170.66667f, 576.0f);
generalPath.curveTo(158.88461f, 576.0f, 149.33336f, 585.5513f, 149.33334f, 597.3333f);
generalPath.lineTo(149.33334f, 725.3333f);
generalPath.curveTo(149.33336f, 737.11536f, 158.88461f, 746.6666f, 170.66667f, 746.6666f);
generalPath.lineTo(874.6667f, 746.6666f);
generalPath.curveTo(886.4487f, 746.6666f, 896.0f, 737.11536f, 896.0f, 725.3333f);
generalPath.lineTo(896.0f, 597.3333f);
generalPath.curveTo(896.0f, 585.5513f, 886.4487f, 576.0f, 874.6667f, 576.0f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(64, 158, 255, 255)) : new Color(64, 158, 255, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_4
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(298.6667f, 810.6667f);
generalPath.lineTo(768.0f, 810.6667f);
generalPath.quadTo(789.3333f, 810.6667f, 789.3333f, 832.0f);
generalPath.lineTo(789.3333f, 832.0f);
generalPath.quadTo(789.3333f, 853.3333f, 768.0f, 853.3333f);
generalPath.lineTo(298.66666f, 853.3333f);
generalPath.quadTo(277.3333f, 853.3333f, 277.3333f, 832.0f);
generalPath.lineTo(277.3333f, 832.0f);
generalPath.quadTo(277.3333f, 810.6667f, 298.66666f, 810.6667f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(230, 162, 60, 255)) : new Color(230, 162, 60, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_5
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(768.0f, 810.6667f);
generalPath.lineTo(298.66666f, 810.6667f);
generalPath.curveTo(286.88458f, 810.6667f, 277.3333f, 820.21796f, 277.3333f, 832.0f);
generalPath.curveTo(277.3333f, 843.7821f, 286.88458f, 853.3334f, 298.66666f, 853.3334f);
generalPath.lineTo(768.0f, 853.3334f);
generalPath.curveTo(779.7821f, 853.3334f, 789.3334f, 843.7821f, 789.3334f, 832.0f);
generalPath.curveTo(789.3334f, 820.21796f, 779.7821f, 810.6667f, 768.0f, 810.6667f);
generalPath.closePath();
generalPath.moveTo(554.6667f, 746.6667f);
generalPath.lineTo(512.0f, 746.6667f);
generalPath.lineTo(512.0f, 810.6667f);
generalPath.lineTo(554.6667f, 810.6667f);
generalPath.lineTo(554.6667f, 746.6667f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(230, 162, 60, 255)) : new Color(230, 162, 60, 255);
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
		return 2.3333334922790527;
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
		return 11.666666030883789;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 10.333333969116211;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		DesktopSvg base = new DesktopSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		DesktopSvg base = new DesktopSvg();
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
		return DesktopSvg::new;
	}
}

