package com.element.ui.svg.icon.cursor;

import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.radiance.common.api.icon.SvgIconUIResource;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class GrabSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public GrabSvg() {
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
		generalPath.moveTo(8.38f, 12.27f);
		generalPath.curveTo(8.28f, 11.9f, 8.18f, 11.42f, 7.97f, 10.72f);
		generalPath.curveTo(7.76f, 10.02f, 7.63f, 9.86f, 7.5f, 9.49f);
		generalPath.curveTo(7.37f, 9.12f, 7.2f, 8.77f, 7.0f, 8.31f);
		generalPath.curveTo(6.81681f, 7.84005f, 6.66317f, 7.35912f, 6.54f, 6.87f);
		generalPath.curveTo(6.45676f, 6.4578f, 6.55918f, 6.02986f, 6.82f, 5.7f);
		generalPath.curveTo(7.17886f, 5.34982f, 7.69668f, 5.21656f, 8.18f, 5.35f);
		generalPath.curveTo(8.55865f, 5.51559f, 8.87907f, 5.79073f, 9.1f, 6.14f);
		generalPath.curveTo(9.39504f, 6.61167f, 9.63653f, 7.11478f, 9.82f, 7.64f);
		generalPath.curveTo(10.1017f, 8.35963f, 10.3062f, 9.10716f, 10.43f, 9.87f);
		generalPath.lineTo(10.52f, 10.32f);
		generalPath.curveTo(10.52f, 10.32f, 10.52f, 9.2f, 10.52f, 9.16f);
		generalPath.curveTo(10.52f, 8.16f, 10.46f, 7.34f, 10.52f, 6.22f);
		generalPath.curveTo(10.52f, 6.09f, 10.58f, 5.63f, 10.6f, 5.5f);
		generalPath.curveTo(10.6259f, 5.06996f, 10.8821f, 4.68748f, 11.27f, 4.5f);
		generalPath.curveTo(11.7153f, 4.30021f, 12.2247f, 4.30021f, 12.67f, 4.5f);
		generalPath.curveTo(13.0686f, 4.67843f, 13.3347f, 5.06401f, 13.36f, 5.5f);
		generalPath.curveTo(13.36f, 5.61f, 13.45f, 6.5f, 13.45f, 6.61f);
		generalPath.curveTo(13.45f, 7.61f, 13.45f, 8.25f, 13.45f, 8.78f);
		generalPath.curveTo(13.45f, 9.01f, 13.45f, 10.41f, 13.45f, 10.25f);
		generalPath.curveTo(13.4736f, 8.93046f, 13.5871f, 7.61407f, 13.79f, 6.31f);
		generalPath.curveTo(13.9085f, 5.90144f, 14.196f, 5.56301f, 14.58f, 5.38f);
		generalPath.curveTo(15.0549f, 5.19294f, 15.5945f, 5.28543f, 15.98f, 5.62f);
		generalPath.curveTo(16.2682f, 5.93629f, 16.4378f, 6.34269f, 16.46f, 6.77f);
		generalPath.curveTo(16.46f, 7.18f, 16.46f, 7.67f, 16.46f, 8.02f);
		generalPath.curveTo(16.46f, 8.89f, 16.46f, 9.34f, 16.46f, 10.14f);
		generalPath.curveTo(16.46f, 10.14f, 16.46f, 10.44f, 16.46f, 10.32f);
		generalPath.curveTo(16.55f, 10.04f, 16.65f, 9.78f, 16.73f, 9.58f);
		generalPath.curveTo(16.81f, 9.38f, 16.97f, 8.97f, 17.09f, 8.72f);
		generalPath.curveTo(17.2121f, 8.48166f, 17.3491f, 8.25121f, 17.5f, 8.03f);
		generalPath.curveTo(17.6568f, 7.77573f, 17.8919f, 7.57922f, 18.17f, 7.47f);
		generalPath.curveTo(18.4205f, 7.37318f, 18.6993f, 7.38115f, 18.9438f, 7.49213f);
		generalPath.curveTo(19.1884f, 7.6031f, 19.378f, 7.80774f, 19.47f, 8.06f);
		generalPath.curveTo(19.5295f, 8.42425f, 19.5295f, 8.79575f, 19.47f, 9.16f);
		generalPath.curveTo(19.4033f, 9.71943f, 19.2862f, 10.2717f, 19.12f, 10.81f);
		generalPath.curveTo(18.99f, 11.26f, 18.85f, 12.04f, 18.78f, 12.41f);
		generalPath.curveTo(18.71f, 12.78f, 18.55f, 13.79f, 18.42f, 14.23f);
		generalPath.curveTo(18.2275f, 14.7549f, 17.9615f, 15.2498f, 17.63f, 15.7f);
		generalPath.curveTo(17.1448f, 16.2402f, 16.7437f, 16.8504f, 16.44f, 17.51f);
		generalPath.curveTo(16.3653f, 17.8379f, 16.3317f, 18.1738f, 16.34f, 18.51f);
		generalPath.curveTo(16.3384f, 18.8207f, 16.3788f, 19.1301f, 16.46f, 19.43f);
		generalPath.curveTo(16.0512f, 19.4736f, 15.6388f, 19.4736f, 15.23f, 19.43f);
		generalPath.curveTo(14.84f, 19.37f, 14.36f, 18.59f, 14.23f, 18.35f);
		generalPath.curveTo(14.1657f, 18.2211f, 14.034f, 18.1397f, 13.89f, 18.1397f);
		generalPath.curveTo(13.746f, 18.1397f, 13.6143f, 18.2211f, 13.55f, 18.35f);
		generalPath.curveTo(13.32f, 18.73f, 12.84f, 19.42f, 12.5f, 19.46f);
		generalPath.curveTo(11.83f, 19.54f, 10.45f, 19.46f, 9.36f, 19.46f);
		generalPath.curveTo(9.36f, 19.46f, 9.55f, 18.46f, 9.13f, 18.1f);
		generalPath.curveTo(8.71f, 17.74f, 8.3f, 17.32f, 7.99f, 17.04f);
		generalPath.lineTo(7.16f, 16.12f);
		generalPath.curveTo(6.88f, 15.76f, 6.53f, 15.03f, 5.92f, 14.12f);
		generalPath.curveTo(5.57f, 13.62f, 4.92f, 13.03f, 4.64f, 12.54f);
		generalPath.curveTo(4.40493f, 12.1423f, 4.33663f, 11.6678f, 4.45f, 11.22f);
		generalPath.curveTo(4.61981f, 10.6254f, 5.21067f, 10.2545f, 5.82f, 10.36f);
		generalPath.curveTo(6.2835f, 10.3905f, 6.72192f, 10.5815f, 7.06f, 10.9f);
		generalPath.curveTo(7.32771f, 11.1316f, 7.57836f, 11.3823f, 7.81f, 11.65f);
		generalPath.curveTo(7.97f, 11.84f, 8.01f, 11.93f, 8.19f, 12.16f);
		generalPath.curveTo(8.37f, 12.39f, 8.49f, 12.62f, 8.4f, 12.28f);
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(255, 255, 255, 255)) : new Color(255, 255, 255, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_1
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		stroke = new BasicStroke(0.75f, 1, 1, 4.0f, null, 0.0f);
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(8.38f, 12.27f);
		generalPath.curveTo(8.28f, 11.9f, 8.18f, 11.42f, 7.97f, 10.72f);
		generalPath.curveTo(7.76f, 10.02f, 7.63f, 9.86f, 7.5f, 9.49f);
		generalPath.curveTo(7.37f, 9.12f, 7.2f, 8.77f, 7.0f, 8.31f);
		generalPath.curveTo(6.81681f, 7.84005f, 6.66317f, 7.35912f, 6.54f, 6.87f);
		generalPath.curveTo(6.45676f, 6.4578f, 6.55918f, 6.02986f, 6.82f, 5.7f);
		generalPath.curveTo(7.17886f, 5.34982f, 7.69668f, 5.21656f, 8.18f, 5.35f);
		generalPath.curveTo(8.55865f, 5.51559f, 8.87907f, 5.79073f, 9.1f, 6.14f);
		generalPath.curveTo(9.39504f, 6.61167f, 9.63653f, 7.11478f, 9.82f, 7.64f);
		generalPath.curveTo(10.1017f, 8.35963f, 10.3062f, 9.10716f, 10.43f, 9.87f);
		generalPath.lineTo(10.52f, 10.32f);
		generalPath.curveTo(10.52f, 10.32f, 10.52f, 9.2f, 10.52f, 9.16f);
		generalPath.curveTo(10.52f, 8.16f, 10.46f, 7.34f, 10.52f, 6.22f);
		generalPath.curveTo(10.52f, 6.09f, 10.58f, 5.63f, 10.6f, 5.5f);
		generalPath.curveTo(10.6259f, 5.06996f, 10.8821f, 4.68748f, 11.27f, 4.5f);
		generalPath.curveTo(11.7153f, 4.30021f, 12.2247f, 4.30021f, 12.67f, 4.5f);
		generalPath.curveTo(13.0686f, 4.67843f, 13.3347f, 5.06401f, 13.36f, 5.5f);
		generalPath.curveTo(13.36f, 5.61f, 13.45f, 6.5f, 13.45f, 6.61f);
		generalPath.curveTo(13.45f, 7.61f, 13.45f, 8.25f, 13.45f, 8.78f);
		generalPath.curveTo(13.45f, 9.01f, 13.45f, 10.41f, 13.45f, 10.25f);
		generalPath.curveTo(13.4736f, 8.93046f, 13.5871f, 7.61407f, 13.79f, 6.31f);
		generalPath.curveTo(13.9085f, 5.90144f, 14.196f, 5.56301f, 14.58f, 5.38f);
		generalPath.curveTo(15.0549f, 5.19294f, 15.5945f, 5.28543f, 15.98f, 5.62f);
		generalPath.curveTo(16.2682f, 5.93629f, 16.4378f, 6.34269f, 16.46f, 6.77f);
		generalPath.curveTo(16.46f, 7.18f, 16.46f, 7.67f, 16.46f, 8.02f);
		generalPath.curveTo(16.46f, 8.89f, 16.46f, 9.34f, 16.46f, 10.14f);
		generalPath.curveTo(16.46f, 10.14f, 16.46f, 10.44f, 16.46f, 10.32f);
		generalPath.curveTo(16.55f, 10.04f, 16.65f, 9.78f, 16.73f, 9.58f);
		generalPath.curveTo(16.81f, 9.38f, 16.97f, 8.97f, 17.09f, 8.72f);
		generalPath.curveTo(17.2121f, 8.48166f, 17.3491f, 8.25121f, 17.5f, 8.03f);
		generalPath.curveTo(17.6568f, 7.77573f, 17.8919f, 7.57922f, 18.17f, 7.47f);
		generalPath.curveTo(18.4205f, 7.37318f, 18.6993f, 7.38115f, 18.9438f, 7.49213f);
		generalPath.curveTo(19.1884f, 7.6031f, 19.378f, 7.80774f, 19.47f, 8.06f);
		generalPath.curveTo(19.5295f, 8.42425f, 19.5295f, 8.79575f, 19.47f, 9.16f);
		generalPath.curveTo(19.4033f, 9.71943f, 19.2862f, 10.2717f, 19.12f, 10.81f);
		generalPath.curveTo(18.99f, 11.26f, 18.85f, 12.04f, 18.78f, 12.41f);
		generalPath.curveTo(18.71f, 12.78f, 18.55f, 13.79f, 18.42f, 14.23f);
		generalPath.curveTo(18.2275f, 14.7549f, 17.9615f, 15.2498f, 17.63f, 15.7f);
		generalPath.curveTo(17.1448f, 16.2402f, 16.7437f, 16.8504f, 16.44f, 17.51f);
		generalPath.curveTo(16.3653f, 17.8379f, 16.3317f, 18.1738f, 16.34f, 18.51f);
		generalPath.curveTo(16.3384f, 18.8207f, 16.3788f, 19.1301f, 16.46f, 19.43f);
		generalPath.curveTo(16.0512f, 19.4736f, 15.6388f, 19.4736f, 15.23f, 19.43f);
		generalPath.curveTo(14.84f, 19.37f, 14.36f, 18.59f, 14.23f, 18.35f);
		generalPath.curveTo(14.1657f, 18.2211f, 14.034f, 18.1397f, 13.89f, 18.1397f);
		generalPath.curveTo(13.746f, 18.1397f, 13.6143f, 18.2211f, 13.55f, 18.35f);
		generalPath.curveTo(13.32f, 18.73f, 12.84f, 19.42f, 12.5f, 19.46f);
		generalPath.curveTo(11.83f, 19.54f, 10.45f, 19.46f, 9.36f, 19.46f);
		generalPath.curveTo(9.36f, 19.46f, 9.55f, 18.46f, 9.13f, 18.1f);
		generalPath.curveTo(8.71f, 17.74f, 8.3f, 17.32f, 7.99f, 17.04f);
		generalPath.lineTo(7.16f, 16.12f);
		generalPath.curveTo(6.88f, 15.76f, 6.53f, 15.03f, 5.92f, 14.12f);
		generalPath.curveTo(5.57f, 13.62f, 4.92f, 13.03f, 4.64f, 12.54f);
		generalPath.curveTo(4.40493f, 12.1423f, 4.33663f, 11.6678f, 4.45f, 11.22f);
		generalPath.curveTo(4.61981f, 10.6254f, 5.21067f, 10.2545f, 5.82f, 10.36f);
		generalPath.curveTo(6.2835f, 10.3905f, 6.72192f, 10.5815f, 7.06f, 10.9f);
		generalPath.curveTo(7.32771f, 11.1316f, 7.57836f, 11.3823f, 7.81f, 11.65f);
		generalPath.curveTo(7.97f, 11.84f, 8.01f, 11.93f, 8.19f, 12.16f);
		generalPath.curveTo(8.37f, 12.39f, 8.49f, 12.62f, 8.4f, 12.28f);
		shape = generalPath;
		g.setPaint(paint);
		g.setStroke(stroke);
		g.draw(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_2
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(15.75f, 16.4309f);
		generalPath.lineTo(15.75f, 12.9791f);
		generalPath.curveTo(15.75f, 12.7725f, 15.5821f, 12.605f, 15.375f, 12.605f);
		generalPath.curveTo(15.1679f, 12.605f, 15.0f, 12.7725f, 15.0f, 12.9791f);
		generalPath.lineTo(15.0f, 16.4309f);
		generalPath.curveTo(15.0f, 16.6375f, 15.1679f, 16.805f, 15.375f, 16.805f);
		generalPath.curveTo(15.5821f, 16.805f, 15.75f, 16.6375f, 15.75f, 16.4309f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_3
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(13.76f, 16.4307f);
		generalPath.lineTo(13.75f, 12.9771f);
		generalPath.curveTo(13.7494f, 12.771f, 13.581f, 12.6044f, 13.3739f, 12.605f);
		generalPath.curveTo(13.1668f, 12.6056f, 12.9994f, 12.7732f, 13.0f, 12.9793f);
		generalPath.lineTo(13.01f, 16.4328f);
		generalPath.curveTo(13.0106f, 16.639f, 13.179f, 16.8056f, 13.3861f, 16.805f);
		generalPath.curveTo(13.5932f, 16.8044f, 13.7606f, 16.6368f, 13.76f, 16.4307f);
		generalPath.closePath();
		shape = generalPath;
		paint = (colorFilter != null) ? colorFilter.filter(new Color(0, 0, 0, 255)) : new Color(0, 0, 0, 255);
		g.setPaint(paint);
		g.fill(shape);
		g.setComposite(AlphaComposite.getInstance(3, 1.0f * origAlpha));
// _0_0_4
		if (generalPath == null) {
			generalPath = new GeneralPath();
		} else {
			generalPath.reset();
		}
		generalPath.moveTo(11.005f, 12.9799f);
		generalPath.lineTo(11.025f, 16.4245f);
		generalPath.curveTo(11.0262f, 16.6331f, 11.1951f, 16.8012f, 11.4022f, 16.8f);
		generalPath.curveTo(11.6093f, 16.7988f, 11.7762f, 16.6287f, 11.775f, 16.4201f);
		generalPath.lineTo(11.755f, 12.9755f);
		generalPath.curveTo(11.7538f, 12.7669f, 11.5849f, 12.5988f, 11.3778f, 12.6f);
		generalPath.curveTo(11.1707f, 12.6012f, 11.0038f, 12.7713f, 11.005f, 12.9799f);
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
		return 3.221479892730713;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 3.9751598834991455;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 17.46820068359375;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 17.69540023803711;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		GrabSvg base = new GrabSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		GrabSvg base = new GrabSvg();
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
		return GrabSvg::new;
	}
}

