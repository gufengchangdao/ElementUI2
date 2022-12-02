package com.element.util;

import org.jdesktop.swingx.util.GraphicsUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import static java.awt.image.ImageObserver.*;

/**
 * ImageUtil包含一组工具，可以轻松执行常见的图形操作。这些操作时基于{@link GraphicsUtilities} 所没有的其他操作，这些操作分为几个主题，如下所列。
 * <h3>缩放图像</h3>
 * 提供了三种其他的图像缩放方法
 * <h3>图像处理</h3>
 * 使图片支持反射、阴影、不透明度、支持GIF等功能
 */
public class ImageUtil {
	// ---------------------------------------------------------------------
	// 缩放
	// ---------------------------------------------------------------------

	/**
	 * 对图像进行缩放，如果是放大将返回放大后的兼容图像，缩小则使用双线式图像缩放
	 * <p>
	 * 双线性缩放原理：
	 * 缩放原理：对于缩小倍率较大的图像，使用一次drawImage()缩放并且当缩小比例大于 50% 时，图像质量会发生严重的问题，
	 * 因此这里采用多次缩小的方法，每次最多缩小50%，在效率和质量上均衡。该方法要优于getScaledImage() (时间上优于)
	 *
	 * @param image     源图像
	 * @param newWidth  新图像宽度
	 * @param newHeight 新图像高度
	 * @return 缩放后兼容BufferedImage
	 */
	public static BufferedImage createScaledCompositeInstance(BufferedImage image, int newWidth, int newHeight) {
		if (newWidth >= image.getWidth() ||
				newHeight >= image.getHeight()) {
			// 不是两边都缩小，进行普通缩放
			BufferedImage newImage = GraphicsUtilities.createCompatibleImage(image, newWidth, newHeight);
			newImage.getGraphics().drawImage(image,
					0, 0, newWidth, newHeight,
					0, 0, image.getWidth(), image.getHeight(),
					null);
			return newImage;
		}

		// 双线性缩放
		return GraphicsUtilities.createThumbnail(image, newWidth, newHeight);
	}

	/**
	 * 双线式图像缩放，按图像比例缩放
	 *
	 * @param image     源图像
	 * @param newSize   新的图像大小
	 * @param isByShort newSize 是否为新图像的最短边
	 * @return 最长边(或最短边)为 newSize 的新图像
	 */
	public static BufferedImage createScaledCompositeInstance(BufferedImage image, int newSize, boolean isByShort) {
		int width = image.getWidth();
		int height = image.getHeight();
		int newWidth, newHeight;

		if (width >= height) {
			if (isByShort) {
				newWidth = newSize * width / height;
				newHeight = newSize;
			} else {
				newWidth = newSize;
				newHeight = newSize * height / width;
			}
		} else {
			if (isByShort) {
				newWidth = newSize;
				newHeight = newSize * height / width;
			} else {
				newWidth = newSize * width / height;
				newHeight = newSize;
			}
		}
		return ImageUtil.createScaledCompositeInstance(image, newWidth, newHeight);
	}

	/**
	 * 使用双线式图像缩放，返回指定宽度的缩放图像，图像高度按照宽度缩放倍率进行缩放
	 *
	 * @param image     源图像
	 * @param newWidth  新图像宽度
	 * @param maxHeight 缩放图像的最大高度，也就是说不能完全依靠图像的长宽比进行缩放
	 * @return 包含image缩放图的新兼容BufferedImage
	 */
	public static BufferedImage createFixedWidthScaledCompositeInstance(BufferedImage image, int newWidth, int maxHeight) {
		return ImageUtil.createScaledCompositeInstance(image, newWidth, Math.min(image.getHeight() * newWidth / image.getWidth(), maxHeight));
	}

	// ---------------------------------------------------------------------
	// 图像处理
	// ---------------------------------------------------------------------

	/**
	 * 返回一个具有给定图像反射效果的新图像。
	 * 推荐使用swingx包下的ReflectionRenderer，反射功能更多
	 *
	 * @param image              源图像
	 * @param reflectHeightRatio 反射图像的高度相对于源图像的比例，不知道填什么就试试 0.5f 吧
	 * @param beginOpacity       反射的起始不透明度，即反射图像最上面的不透明度
	 * @return 高度为源图像两倍的具有反射效果的新图像
	 */
	public static BufferedImage createReflection(BufferedImage image, float reflectHeightRatio, float beginOpacity) {
		int height = image.getHeight();
		if (reflectHeightRatio > 1) reflectHeightRatio = 1;
		if (reflectHeightRatio < 0) reflectHeightRatio = 0;

		// 创建一个空的，半透明的，高度为原始图像两倍的BufferedImage，以便这个反射可以放到原始图像下方
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight() * 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) result.getGraphics();

		// 绘制原图像
		g2.drawImage(image, 0, 0, null);

		// 绘制镜像图像
		g2.scale(1.0, -1.0); //使用一个负的缩放，设置图像反转
		g2.drawImage(image, 0, -height - height, null); //找到对称位置
		g2.scale(1.0, -1.0); //设置回正常

		// 为了接下来操作容易，把原点设置为副本位置
		g2.translate(0, height);
		// 利用DstIn的合成规则，这里只需要一个alpha值逐渐减小的渐变即可，源颜色无所谓
		GradientPaint mask = new GradientPaint(0, 0, new Color(1f, 1f, 1f, beginOpacity), 0, height * reflectHeightRatio, new Color(1f, 1f, 1f, 0f));
		g2.setPaint(mask);
		g2.setComposite(AlphaComposite.DstIn);
		// 与镜像图像合成为一个只有一半且逐渐透明的静态图像
		g2.fillRect(0, 0, image.getWidth(), height);

		g2.dispose();
		return result;
	}

	/**
	 * 返回一个具有给定图像反射效果的新图像
	 *
	 * @param image              源图像
	 * @param reflectHeightRatio 反射图像的高度相对于源图像的比例，不知道填什么就试试 0.5f 吧
	 * @return 高度为源图像两倍的具有反射效果的新图像
	 */
	public static BufferedImage createReflection(BufferedImage image, float reflectHeightRatio) {
		return createReflection(image, reflectHeightRatio, .5f);
	}

	/**
	 * 绘制圆角图像
	 *
	 * @param srcImage 源图像
	 * @param angle    圆角大小
	 * @return 设置圆角并且缩放后的图像
	 */
	public static BufferedImage createRoundImage(BufferedImage srcImage, int angle) {
		BufferedImage result = GraphicsUtilities.createCompatibleTranslucentImage(srcImage.getWidth(), srcImage.getHeight());
		Graphics2D g2 = (Graphics2D) result.getGraphics();

		// 绘制圆角图像
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fillRoundRect(0, 0, srcImage.getWidth(), srcImage.getHeight(), angle, angle);
		g2.setComposite(AlphaComposite.SrcIn);
		g2.drawImage(srcImage, 0, 0, null);
		g2.dispose();

		return result;
	}

	/**
	 * 创建具有一定偏移的半透明图像为阴影的圆角图像
	 *
	 * @param srcImage 源图像
	 * @param offset   图像阴影的偏移大小
	 * @return 具有图像阴影的圆角图像
	 */
	public static BufferedImage createLayerImage(BufferedImage srcImage, int offset) {
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();

		// 绘制图像与阴影
		BufferedImage result = new BufferedImage(width + offset, height + offset, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) result.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
		g2.drawImage(srcImage, offset, offset, width, height, null);
		g2.setComposite(((AlphaComposite) g2.getComposite()).derive(1f));
		g2.drawImage(srcImage, 0, 0, width, height, null);
		g2.dispose();

		return result;
	}

	/**
	 * 将两张图像合成为新图像，其中上面的图像具体指定不透明度
	 *
	 * @param frontImage  前景图
	 * @param behindImage 背景图
	 * @param hint        渲染暗示，从以下选择一个(绘制效果递增)：
	 *                    VALUE_INTERPOLATION_NEAREST_NEIGHBOR,
	 *                    RenderingHints.VALUE_INTERPOLATION_BILINEAR,
	 *                    VALUE_INTERPOLATION_BICUBIC，
	 *                    如果为null，默认选择 BILINEAR
	 * @param offsetX     前景图的水平偏移量
	 * @param offsetY     前景图的垂直偏移量
	 * @param opacity     前景图的不透明度
	 * @return 合成后的新图像
	 */
	public static BufferedImage createLayerImage(BufferedImage frontImage, BufferedImage behindImage, Object hint, int offsetX, int offsetY, float opacity) {
		if (hint == null) hint = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
		BufferedImage result = new BufferedImage(Math.max(frontImage.getWidth(), behindImage.getWidth()), Math.max(frontImage.getHeight(), behindImage.getHeight()),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) result.getGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);

		g2.drawImage(behindImage, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2.drawImage(frontImage, offsetX, offsetY, null);
		g2.dispose();

		return result;
	}

	/**
	 * 将给定图像转换为灰色
	 *
	 * @param image 源图像
	 * @return 源图像的灰色版本
	 */
	public static BufferedImage createGrayCompositeImage(BufferedImage image) {
		ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(colorSpace, null);
		image = op.filter(image, null);
		// 对一个图像执行这样的一个转换可能会使得它与您的图形显示硬件不相容，在这里转换为兼容图像
		return GraphicsUtilities.toCompatibleImage(image);
	}

	/**
	 * 创建给定图像卷积处理后的新图像。
	 * 该方法会根据卷积内核大小而适当的增大图像尺寸，用来解决图像边缘出现黑边的问题
	 *
	 * @param image        源图像
	 * @param sharpen      卷积内核
	 * @param kernelWidth  内核矩阵宽度(列数)
	 * @param kernelHeight 内核矩阵高度(行数)
	 * @return 卷积处理后的新图像
	 */
	public static BufferedImage createKernelImage(BufferedImage image, float[] sharpen, int kernelWidth, int kernelHeight) {
		int offsetX = (kernelWidth - 1) / 2;
		int offsetY = (kernelHeight - 1) / 2;

		// 处理黑边问题，需要透明通道图像并且边缘条件设置为 EDGE_NO_OP
		BufferedImage result = new BufferedImage(image.getWidth() + kernelWidth - 1, image.getHeight() + kernelHeight - 1, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = result.getGraphics();
		graphics.drawImage(image, offsetX, offsetY, null);
		graphics.dispose();

		Kernel kernel = new Kernel(kernelWidth, kernelHeight, sharpen);
		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		return op.filter(result, null);
	}

	/**
	 * 组件绘制在图片上并返回。
	 * <p>
	 * 图片清晰度不高，但可以用来作为弹窗的遮罩
	 *
	 * @param c 要绘制的组件
	 */
	public static BufferedImage componentToImage(JComponent c) {
		Dimension size = c.getPreferredSize();
		// paint()会用到这两个方法，这里得设值
		if (!(c.getWidth() > 0 && c.getHeight() > 0)) c.setSize(size);

		BufferedImage img = GraphicsUtilities.createCompatibleImage(c.getWidth(), c.getHeight());
		Graphics2D g = img.createGraphics();
		c.paint(g);
		g.dispose();
		return img;
	}

	/**
	 * 允许 JComboBox 展示GIF动画。
	 * <p>
	 * 不要求调用方法前icon已经添加进JComboBox，但必须保证 row 是正确的
	 *
	 * @param icon  GIF图标
	 * @param combo 创建好的JComboBox
	 * @param row   GIF图片所在下拉列表行索引
	 * @return 原icon对象
	 */
	public static ImageIcon makeAnimatedIcon(ImageIcon icon, JComboBox<?> combo, int row) {
		// Wastefulness: icon.setImageObserver(combo);
		icon.setImageObserver((img, infoflags, x, y, w, h) -> {
			if (combo.isShowing() && (infoflags & (FRAMEBITS | ALLBITS)) != 0) {
				// 重绘gif图片所在行
				UIUtil.repaint(combo, row);
			}
			return (infoflags & (ALLBITS | ABORT)) == 0;
		});
		return icon;
	}

	/**
	 * 允许 JTable 展示GIF动画。
	 *
	 * @param icon  GIF图标
	 * @param table 表格
	 * @param row   模型数据中图标所在行
	 * @param col   模型数据中图标所在列
	 * @return 原icon对象
	 */
	public static ImageIcon makeImageIcon(ImageIcon icon, JTable table, int row, int col) {
		// Wastefulness: icon.setImageObserver((ImageObserver) table);
		icon.setImageObserver((img, infoflags, x, y, w, h) -> {
			if (!table.isShowing()) {
				return false;
			}
			if ((infoflags & (FRAMEBITS | ALLBITS)) != 0) {
				UIUtil.repaint(table, row, col);
			}
			return (infoflags & (ALLBITS | ABORT)) == 0;
		});
		return icon;
	}
}
