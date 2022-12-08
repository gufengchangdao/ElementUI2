package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class HelpSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public HelpSvg() {
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
		generalPath.moveTo(8.67513f, 5.09927f);
		generalPath.lineTo(8.72211f, 5.06389f);
		generalPath.curveTo(9.67069f, 4.3922f, 10.7995f, 4.02155f, 11.9145f, 4.00215f);
		generalPath.curveTo(13.3262f, 3.90947f, 14.7077f, 4.43907f, 15.6246f, 5.38552f);
		generalPath.curveTo(16.5731f, 6.18527f, 17.0767f, 7.39399f, 16.98f, 8.54668f);
		generalPath.curveTo(16.9829f, 9.41672f, 16.702f, 10.264f, 16.1544f, 10.9927f);
		generalPath.curveTo(15.7611f, 11.4741f, 15.304f, 11.8997f, 14.85f, 12.2166f);
		generalPath.lineTo(14.3062f, 12.6576f);
		generalPath.curveTo(14.1798f, 12.7565f, 14.0832f, 12.8886f, 14.0438f, 12.9903f);
		generalPath.curveTo(13.9841f, 13.1349f, 13.9911f, 13.3701f, 14.0f, 13.5644f);
		generalPath.lineTo(14.0f, 14.61f);
		generalPath.lineTo(10.0f, 14.61f);
		generalPath.lineTo(9.98227f, 13.6774f);
		generalPath.curveTo(9.93588f, 12.9906f, 9.974f, 12.3007f, 10.1112f, 11.5522f);
		generalPath.curveTo(10.2091f, 11.1694f, 10.4087f, 10.8488f, 10.7013f, 10.5452f);
		generalPath.curveTo(10.8999f, 10.3391f, 11.0477f, 10.2165f, 11.5204f, 9.84512f);
		generalPath.lineTo(12.1174f, 9.37448f);
		generalPath.curveTo(12.2766f, 9.25739f, 12.4204f, 9.12069f, 12.4973f, 9.03145f);
		generalPath.curveTo(12.5996f, 8.88355f, 12.66f, 8.71082f, 12.6703f, 8.57533f);
		generalPath.curveTo(12.6734f, 8.44954f, 12.6317f, 8.32675f, 12.5103f, 8.1728f);
		generalPath.curveTo(12.3901f, 8.00077f, 12.185f, 7.90871f, 11.9153f, 7.93848f);
		generalPath.curveTo(11.6606f, 7.95258f, 11.4209f, 8.06347f, 11.273f, 8.21798f);
		generalPath.curveTo(11.0911f, 8.42625f, 10.9873f, 8.69139f, 10.9796f, 8.96785f);
		generalPath.lineTo(10.9525f, 9.94001f);
		generalPath.lineTo(7.05819f, 9.94001f);
		generalPath.lineTo(6.9833f, 9.02124f);
		generalPath.curveTo(6.86045f, 7.514f, 7.49448f, 6.04422f, 8.67513f, 5.09927f);
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
		generalPath.moveTo(9.29999f, 5.88001f);
		generalPath.curveTo(10.0847f, 5.32432f, 11.0186f, 5.01769f, 11.98f, 5.00001f);
		generalPath.curveTo(13.1f, 4.92647f, 14.1961f, 5.34665f, 14.98f, 6.15001f);
		generalPath.curveTo(15.6815f, 6.74149f, 16.0539f, 7.63544f, 15.98f, 8.55001f);
		generalPath.curveTo(15.9822f, 9.20254f, 15.7715f, 9.83798f, 15.38f, 10.36f);
		generalPath.curveTo(15.0442f, 10.771f, 14.6539f, 11.1344f, 14.22f, 11.44f);
		generalPath.lineTo(13.69f, 11.87f);
		generalPath.curveTo(13.4181f, 12.0827f, 13.2105f, 12.3665f, 13.09f, 12.69f);
		generalPath.curveTo(13.0264f, 12.8799f, 12.9858f, 13.2987f, 13.0f, 13.61f);
		generalPath.lineTo(11.0f, 13.61f);
		generalPath.curveTo(10.9591f, 13.0049f, 10.9727f, 12.397f, 11.08f, 11.8f);
		generalPath.curveTo(11.19f, 11.37f, 11.58f, 11.07f, 12.14f, 10.63f);
		generalPath.lineTo(12.71f, 10.18f);
		generalPath.curveTo(12.9369f, 10.0131f, 13.1419f, 9.81822f, 13.32f, 9.60001f);
		generalPath.curveTo(13.5244f, 9.3042f, 13.6453f, 8.95874f, 13.67f, 8.60001f);
		generalPath.curveTo(13.679f, 8.23688f, 13.5584f, 7.88241f, 13.33f, 7.60001f);
		generalPath.curveTo(12.9989f, 7.12615f, 12.4341f, 6.8726f, 11.86f, 6.94001f);
		generalPath.curveTo(11.3506f, 6.9682f, 10.8712f, 7.18999f, 10.52f, 7.56001f);
		generalPath.curveTo(10.1851f, 7.94328f, 9.99416f, 8.43123f, 9.97999f, 8.94001f);
		generalPath.lineTo(7.97999f, 8.94001f);
		generalPath.curveTo(7.88757f, 7.79679f, 8.35317f, 6.67936f, 9.22999f, 5.94001f);
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
		generalPath.moveTo(14.0f, 14.0f);
		generalPath.lineTo(14.0f, 18.0f);
		generalPath.lineTo(10.0f, 18.0f);
		generalPath.lineTo(10.0f, 14.0f);
		generalPath.lineTo(14.0f, 14.0f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_3
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(13.0f, 15.0f);
		generalPath.lineTo(11.0f, 15.0f);
		generalPath.lineTo(11.0f, 17.0f);
		generalPath.lineTo(13.0f, 17.0f);
		generalPath.lineTo(13.0f, 15.0f);
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
		return 5.168230056762695;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 3.1917099952697754;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 13.623499870300293;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 17.608299255371094;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		HelpSvg base = new HelpSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		HelpSvg base = new HelpSvg();
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
		return HelpSvg::new;
	}
}

