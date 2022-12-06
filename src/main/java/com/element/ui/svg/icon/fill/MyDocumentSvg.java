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

public class MyDocumentSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public MyDocumentSvg() {
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
generalPath.moveTo(931.6071f, 176.85179f);
generalPath.lineTo(459.2772f, 176.85179f);
generalPath.lineTo(445.9685f, 132.8308f);
generalPath.curveTo(440.8498f, 115.68308f, 425.1097f, 103.91003f, 407.1942f, 103.91003f);
generalPath.lineTo(92.13697f, 103.91003f);
generalPath.curveTo(69.74257f, 103.91003f, 51.57111f, 121.953514f, 51.57111f, 144.34792f);
generalPath.lineTo(51.57111f, 879.2682f);
generalPath.curveTo(51.57111f, 901.6626f, 69.6146f, 919.83405f, 92.009f, 919.83405f);
generalPath.lineTo(931.6071f, 919.83405f);
generalPath.curveTo(954.0015f, 919.83405f, 972.045f, 901.6626f, 972.173f, 879.2682f);
generalPath.lineTo(972.173f, 217.28967f);
generalPath.curveTo(972.173f, 195.02324f, 954.0015f, 176.85178f, 931.6071f, 176.85178f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 160, 0, 255)) : new Color(255, 160, 0, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_1
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(867.879f, 869.4147f);
generalPath.lineTo(155.86504f, 869.4147f);
generalPath.curveTo(133.47064f, 869.4147f, 115.29918f, 851.37115f, 115.29918f, 828.9768f);
generalPath.lineTo(115.29918f, 279.2262f);
generalPath.curveTo(115.29918f, 256.8318f, 133.34267f, 238.66034f, 155.73706f, 238.66034f);
generalPath.lineTo(867.879f, 238.66034f);
generalPath.curveTo(890.27344f, 238.66034f, 908.4449f, 256.70383f, 908.4449f, 279.09824f);
generalPath.lineTo(908.4449f, 828.97675f);
generalPath.curveTo(908.4449f, 851.37115f, 890.27344f, 869.5426f, 867.879f, 869.4146f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_2
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(931.6071f, 919.83405f);
generalPath.lineTo(92.13697f, 919.83405f);
generalPath.curveTo(69.74257f, 919.83405f, 51.57111f, 901.7905f, 51.57111f, 879.3962f);
generalPath.lineTo(51.57111f, 355.36716f);
generalPath.curveTo(51.57111f, 332.97275f, 69.6146f, 314.8013f, 92.009f, 314.8013f);
generalPath.lineTo(931.6071f, 314.8013f);
generalPath.curveTo(954.0015f, 314.8013f, 972.173f, 332.8448f, 972.173f, 355.2392f);
generalPath.lineTo(972.173f, 879.2682f);
generalPath.curveTo(972.045f, 901.6626f, 954.0015f, 919.83405f, 931.6071f, 919.83405f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 202, 40, 255)) : new Color(255, 202, 40, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_3
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(379.5531f, 482.31143f);
generalPath.lineTo(156.7608f, 482.31143f);
generalPath.curveTo(145.6276f, 482.31143f, 136.54185f, 473.2257f, 136.54185f, 461.9645f);
generalPath.curveTo(136.54185f, 450.8313f, 145.62758f, 441.74557f, 156.7608f, 441.74557f);
generalPath.lineTo(379.6811f, 441.74557f);
generalPath.curveTo(390.8143f, 441.74557f, 399.90002f, 450.8313f, 400.028f, 461.9645f);
generalPath.curveTo(399.90005f, 473.2257f, 390.81433f, 482.31143f, 379.55313f, 482.31143f);
generalPath.curveTo(379.6811f, 482.31143f, 379.55313f, 482.31143f, 379.55313f, 482.31143f);
generalPath.closePath();
generalPath.moveTo(379.5531f, 620.005f);
generalPath.lineTo(156.7608f, 620.005f);
generalPath.curveTo(145.6276f, 620.005f, 136.54185f, 610.91925f, 136.54185f, 599.6581f);
generalPath.curveTo(136.54185f, 588.52484f, 145.62758f, 579.43915f, 156.7608f, 579.43915f);
generalPath.lineTo(379.6811f, 579.43915f);
generalPath.curveTo(390.8143f, 579.43915f, 399.90002f, 588.5249f, 400.028f, 599.6581f);
generalPath.curveTo(399.90005f, 610.91925f, 390.81433f, 620.005f, 379.55313f, 620.005f);
generalPath.curveTo(379.6811f, 620.005f, 379.55313f, 620.005f, 379.55313f, 620.005f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
g.setPaint(paint);
g.fill(shape);
g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_4
if (generalPath == null) {
   generalPath = new GeneralPath();
} else {
   generalPath.reset();
}
generalPath.moveTo(844.9728f, 768.7038f);
generalPath.lineTo(735.4322f, 696.40186f);
generalPath.curveTo(732.10504f, 694.09845f, 727.62616f, 694.09845f, 724.29895f, 696.40186f);
generalPath.lineTo(614.75836f, 768.8317f);
generalPath.curveTo(610.02356f, 771.90295f, 603.7531f, 770.6233f, 600.6819f, 766.0164f);
generalPath.curveTo(599.53015f, 764.35284f, 599.0183f, 762.4333f, 599.0183f, 760.3858f);
generalPath.lineTo(599.0183f, 313.39365f);
generalPath.curveTo(599.0183f, 302.26044f, 607.72015f, 293.1747f, 618.3415f, 293.1747f);
generalPath.lineTo(841.2618f, 293.1747f);
generalPath.curveTo(851.8831f, 293.1747f, 860.58496f, 302.26044f, 860.58496f, 313.39365f);
generalPath.lineTo(860.58496f, 760.25793f);
generalPath.curveTo(860.71295f, 765.76056f, 856.2341f, 770.36743f, 850.60345f, 770.49536f);
generalPath.curveTo(848.68396f, 770.49536f, 846.6365f, 769.8555f, 844.97284f, 768.7038f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(244, 67, 54, 255)) : new Color(244, 67, 54, 255);
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
		return 0.8057985901832581;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 1.6235941648483276;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 14.384404182434082;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 12.748812675476074;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		MyDocumentSvg base = new MyDocumentSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		MyDocumentSvg base = new MyDocumentSvg();
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
		return MyDocumentSvg::new;
	}
}

