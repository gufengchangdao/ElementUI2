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

public class RefreshSvg extends AbstractSvgIcon {
	/**
	 * @see AbstractSvgIcon#AbstractSvgIcon()
	 */
	public RefreshSvg() {
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
generalPath.moveTo(981.31464f, 554.2968f);
generalPath.curveTo(971.6991f, 606.8793f, 955.9285f, 658.1474f, 934.3282f, 707.0432f);
generalPath.quadTo(828.6221f, 937.77747f, 573.3451f, 949.2415f);
generalPath.curveTo(495.63272f, 953.0675f, 417.92697f, 941.56433f, 344.65607f, 915.3876f);
generalPath.lineTo(344.65607f, 914.3649f);
generalPath.lineTo(312.84735f, 994.3442f);
generalPath.curveTo(308.2391f, 1003.61993f, 301.1384f, 1011.42883f, 292.34122f, 1016.8955f);
generalPath.curveTo(285.9563f, 1020.59424f, 278.67355f, 1022.45685f, 271.29688f, 1022.2777f);
generalPath.curveTo(264.50867f, 1021.79944f, 257.8845f, 1019.96954f, 251.81337f, 1016.8955f);
generalPath.curveTo(247.18106f, 1014.8676f, 242.71767f, 1012.47394f, 238.46555f, 1009.7372f);
generalPath.curveTo(236.56876f, 1008.31793f, 234.77055f, 1006.7715f, 233.08336f, 1005.1085f);
generalPath.lineTo(71.67171f, 731.9089f);
generalPath.curveTo(67.87771f, 725.28f, 65.44413f, 717.961f, 64.513405f, 710.3801f);
generalPath.curveTo(63.75999f, 704.55457f, 64.106865f, 698.6394f, 65.53602f, 692.94183f);
generalPath.curveTo(66.94723f, 688.0347f, 69.38747f, 683.4845f, 72.69432f, 679.594f);
generalPath.curveTo(75.777336f, 675.8401f, 79.220406f, 672.39703f, 82.97429f, 669.314f);
generalPath.curveTo(86.43814f, 666.4457f, 90.211044f, 663.97253f, 94.22305f, 661.9404f);
generalPath.curveTo(96.83789f, 660.4986f, 99.57562f, 659.2918f, 102.40398f, 658.33435f);
generalPath.curveTo(103.57789f, 658.0541f, 104.64424f, 657.4367f, 105.471825f, 656.5582f);
generalPath.lineTo(386.47568f, 581.692f);
generalPath.curveTo(398.153f, 578.4626f, 410.35544f, 577.5844f, 422.37485f, 579.1086f);
generalPath.curveTo(429.95172f, 580.5399f, 437.37628f, 582.6844f, 444.54944f, 585.51337f);
generalPath.curveTo(450.21164f, 587.70056f, 454.68747f, 592.17633f, 456.87463f, 597.83856f);
generalPath.curveTo(458.92062f, 602.6192f, 460.47162f, 607.59674f, 461.50333f, 612.6934f);
generalPath.curveTo(462.23007f, 617.456f, 462.23007f, 622.3013f, 461.50333f, 627.06384f);
generalPath.curveTo(461.18048f, 630.71094f, 460.49536f, 634.31683f, 459.4581f, 637.8282f);
generalPath.curveTo(458.90897f, 638.9612f, 458.56284f, 640.1817f, 458.4355f, 641.43427f);
generalPath.lineTo(425.60416f, 721.4135f);
generalPath.curveTo(478.49612f, 741.3841f, 533.6798f, 754.65564f, 589.8684f, 760.91876f);
generalPath.quadTo(667.2642f, 768.6153f, 721.6781f, 757.3127f);
generalPath.curveTo(757.01776f, 750.1557f, 791.0755f, 737.7084f, 822.7017f, 720.39087f);
generalPath.curveTo(849.2631f, 706.44135f, 874.08527f, 689.40546f, 896.65295f, 669.63684f);
generalPath.curveTo(914.7804f, 652.92926f, 931.10596f, 634.3669f, 945.3617f, 614.25415f);
generalPath.curveTo(955.16f, 600.9581f, 964.1466f, 587.08276f, 972.27264f, 572.7037f);
generalPath.curveTo(976.04016f, 565.2224f, 978.94653f, 559.08673f, 980.99176f, 554.2428f);
generalPath.closePath();
generalPath.moveTo(40.885612f, 449.6671f);
generalPath.curveTo(53.893673f, 388.2243f, 75.28151f, 328.85992f, 104.4492f, 273.23914f);
generalPath.quadTo(222.4805f, 60.96579f, 478.7801f, 66.07886f);
generalPath.curveTo(549.71625f, 66.44333f, 619.9653f, 80.004005f, 685.94037f, 106.06848f);
generalPath.lineTo(719.7943f, 27.11184f);
generalPath.curveTo(723.95465f, 20.368029f, 729.14905f, 14.320007f, 735.1874f, 9.189165f);
generalPath.curveTo(739.6029f, 5.2769523f, 744.8755f, 2.456324f, 750.58044f, 0.95442295f);
generalPath.curveTo(755.4615f, -0.10033632f, 760.4907f, -0.282555f, 765.4353f, 0.41620398f);
generalPath.curveTo(770.03644f, 0.9618816f, 774.5332f, 2.1767275f, 778.78314f, 4.0222683f);
generalPath.curveTo(782.6749f, 5.829848f, 786.4498f, 7.879079f, 790.08575f, 10.157959f);
generalPath.curveTo(792.4332f, 11.515697f, 794.66534f, 13.063785f, 796.75964f, 14.786636f);
generalPath.lineTo(799.8275f, 17.85448f);
generalPath.lineTo(954.673f, 294.76788f);
generalPath.curveTo(957.7997f, 302.0044f, 959.8662f, 309.6541f, 960.80865f, 317.4807f);
generalPath.curveTo(961.5608f, 323.3063f, 961.2139f, 329.22125f, 959.786f, 334.91898f);
generalPath.curveTo(958.3351f, 339.92407f, 955.6944f, 344.5037f, 952.0895f, 348.26678f);
generalPath.curveTo(948.85223f, 351.88184f, 945.2443f, 355.147f, 941.32513f, 358.00854f);
generalPath.curveTo(937.72107f, 360.49905f, 933.9439f, 362.72934f, 930.0225f, 364.68246f);
generalPath.lineTo(921.8416f, 368.77292f);
generalPath.curveTo(920.9112f, 369.3451f, 919.8614f, 369.69504f, 918.77374f, 369.79553f);
generalPath.lineTo(635.7247f, 437.34195f);
generalPath.curveTo(620.4452f, 440.20575f, 604.7369f, 439.8575f, 589.59937f, 436.31934f);
generalPath.curveTo(578.02765f, 432.92856f, 570.4388f, 428.2999f, 567.04803f, 422.48712f);
generalPath.curveTo(563.4026f, 416.08057f, 561.5438f, 408.81287f, 561.66583f, 401.44278f);
generalPath.curveTo(561.57196f, 395.60303f, 562.0043f, 389.7667f, 562.9576f, 384.00452f);
generalPath.curveTo(563.473f, 381.09644f, 564.5125f, 378.3062f, 566.02545f, 375.76978f);
generalPath.lineTo(600.63275f, 296.81308f);
generalPath.curveTo(547.9989f, 275.4998f, 492.84756f, 261.0291f, 436.52997f, 253.75562f);
generalPath.quadTo(358.5421f, 244.55208f, 304.72028f, 253.75562f);
generalPath.curveTo(269.41614f, 259.76462f, 235.25015f, 271.18353f, 203.42757f, 287.60956f);
generalPath.curveTo(176.39291f, 300.8849f, 151.08739f, 317.42386f, 128.077f, 336.85654f);
generalPath.curveTo(109.60561f, 353.312f, 92.77057f, 371.51694f, 77.807396f, 391.2166f);
generalPath.curveTo(67.39736f, 404.2867f, 57.799046f, 417.9832f, 49.066532f, 432.22885f);
generalPath.curveTo(45.84832f, 437.79822f, 43.11141f, 443.63217f, 40.885612f, 449.6671f);
generalPath.closePath();
shape = generalPath;
paint = (colorFilter != null) ? colorFilter.filter(new Color(227, 179, 54, 255)) : new Color(227, 179, 54, 255);
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
		return 0.6388376951217651;
	}

	/**
	 * @see AbstractSvgIcon#getOrigY()
	 */
	public static double getOrigY() {
		return 0.0;
	}

	/**
	 * @see AbstractSvgIcon#getOrigWidth()
	 */
	public static double getOrigWidth() {
		return 14.69420337677002;
	}

	/**
	 * @see AbstractSvgIcon#getOrigHeight()
	 */
	public static double getOrigHeight() {
		return 15.9758882522583;
	}

	@Override
	public boolean supportsColorFilter() {
		return true;
	}

	/**
	 * @see AbstractSvgIcon#of(int, int)
	 */
	public static SvgIcon of(int width, int height) {
		RefreshSvg base = new RefreshSvg();
		base.width = width;
		base.height = height;
		return base;
	}

	/**
	 * @see AbstractSvgIcon#uiResourceOf(int, int)
	 */
	public static SvgIconUIResource uiResourceOf(int width, int height) {
		RefreshSvg base = new RefreshSvg();
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
		return RefreshSvg::new;
	}
}

