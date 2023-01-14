/*
 * @(#)IconsFactory.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.icons;

import com.element.radiance.common.api.BufferedImageTranscoder;
import com.element.radiance.common.api.icon.AbstractSvgIcon;
import com.element.radiance.common.api.icon.SvgIcon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import static com.element.util.ImageUtil.createScaledCompositeInstance;

/**
 * IconsFactory提供了一种一致的方式来访问任何应用程序中的图标资源。
 * <p>
 * 如果您设置目录结构如上，您现在可以使用 IconsFactory 来访问这样的图像。
 * <pre><code>
 * ImageIcon icon = IconsFactory.get(Foo.class, "icons/foo.gif");
 * </code></pre>
 * <p>
 * IconsFactory 将为您缓存图标。所以下次如果你得到相同的图标，它将从缓存中获取而不是再次从磁盘读取。
 * IconsFactory 上有几种方法可以创建与原始图标不同的变体。
 * 我们还建议您使用下面的模板在您的应用程序中创建多个 IconsFactory 类。这个想法是，您应该为每个功能区域设置一个，以便可以将所有图像文件分
 * 组到每个功能区域中。该功能区域中使用的所有图像都应放在此 IconsFactory 所在的文件夹下。下面是一个模板。
 * <pre><code>
 * class TemplateIconsFactory {
 *    public static class Group1 {
 *        public static final String IMAGE1 = "icons/image11.png";
 *        public static final String IMAGE2 = "icons/image12.png";
 *        public static final String IMAGE3 = "icons/image13.png";
 *    }
 * <p>
 *    public static class Group2 {
 *        public static final String IMAGE1 = "icons/image21.png";
 *        public static final String IMAGE2 = "icons/image22.png";
 *        public static final String IMAGE3 = "icons/image23.png";
 *    }
 * <p>
 *    public static ImageIcon getImageIcon(String name) {
 *        if (name != null)
 *            return IconsFactory.getImageIcon(TemplateIconsFactory.class, name);
 *        else
 *            return null;
 *    }
 * <p>
 *    public static void main(String[] argv) {
 *        IconsFactory.generateHTML(TemplateIconsFactory.class);
 *    }
 * }
 * </code></pre>
 * <p>
 * 在您自己的 IconsFactory 中，您可以进一步将图像分成不同的组。上面的例子有两组。还有一个方便的方法 getImageIcon() 只接受图标名称。
 * 在模板中，我们将图像名称定义为常量。当你有很多图像时，在编写代码时很难记住所有图像。如果使用上面的 IconsFactory，你可以使用
 * <pre><code>
 * ImageIcon icon = TemplateIconsFactory.getImageIcon(TemplateIconsFactory.Group1.IMAGE1);
 * </code></pre>
 * <p>
 * 不用说实际的图像文件名。借助大多数 Java IDE 中的智能感知（或代码完成）功能，您会发现找到所需的图标要容易得多。
 * 您可能还注意到这是此模板中的一个 main() 方法。你可以运行它。当你运行时，你会看到这样打印的信息。
 * <pre><code>
 * 文件生成在 "D:\...\TemplateIconsFactory.html". 请复制到与 BasicFolderChooserIconsFactory.java 同级目录下(资源文件目录)
 * </code></pre>
 * <p>
 * 如果您按照说明将 html 文件复制到与源代码相同的位置并打开 html，您将看到此 IconsFactory 中定义的所有图像文件都很好地列在页面中。
 * 默认情况下，所有图像文件都使用 ImageIO 加载。但是，如果您将系统属性“jide.useImageIO”设置为“false”，我们将禁用 ImageIO 的使用并使
 * 用 Toolkit.getDefaultToolkit().createImage 方法来创建图像文件。
 */
public class IconsFactory {
	static final double DEGREE_90 = Math.PI * 0.5;
	/** 空图像图标，作为不会抛出异常方法的返回值 */
	public static ImageIcon EMPTY_ICON = new ImageIcon() {
		@Override
		public int getIconHeight() {
			return 16;
		}

		@Override
		public int getIconWidth() {
			return 16;
		}

		@Override
		public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
		}
	};
	static Map<String, ImageIcon> _icons = new HashMap<>();
	static Map<String, ImageIcon> _disableIcons = new HashMap<>();
	static Map<String, ImageIcon> _brighterIcons = new HashMap<>();
	static Map<String, ImageIcon> _tintedIcons = new HashMap<>();

	/**
	 * 读取图像数据，这里使用{@link Toolkit#getDefaultToolkit()}实现，相对于{@link ImageIcon#getImage()}的优点在于在低版本jdk中
	 * ImageIcon读取的JPEG图片可能会出现红色背景(jdk8有该问题，更高版本不会)
	 */
	private static Image readImageIcon(Class<?> clazz, String file, InputStream resource) throws IOException {
		try (BufferedInputStream in = new BufferedInputStream(resource);
		     ByteArrayOutputStream out = new ByteArrayOutputStream(1024)) {
			int n;
			byte[] buffer = new byte[1024];
			while ((n = in.read(buffer)) > 0) {
				out.write(buffer, 0, n);
			}
			out.flush();

			if (out.size() == 0) { //没有读取成功
				Package pkg = clazz.getPackage();
				String pkgName = "";
				if (pkg != null) {
					pkgName = pkg.getName().replace('.', '/');
				}
				throw new IOException("Warning: Resource " + pkgName + "/" + file + " is zero-length");
			}
			return Toolkit.getDefaultToolkit().createImage(out.toByteArray());
		}
	}

	/** 读取本地图片，根据客户端属性判断选择使用{@link ImageIO#read(InputStream)} 还是{@link Toolkit#createImage(byte[])} */
	private static ImageIcon createImageIconWithException(final Class<?> baseClass, final String file) throws IOException {
		try (InputStream resource = baseClass.getResourceAsStream(file)) {
			if (resource == null) {
				throw new FileNotFoundException(file);
			}

			Image image;
			if ("true".equals(System.getProperty("jide.useImageIO", "true"))) {
				image = ImageIO.read(resource);
			} else {
				image = readImageIcon(baseClass, file, resource);
			}
			return new ImageIcon(image);
		}
	}

	private static ImageIcon createImageIcon(final Class<?> baseClass, final String file) {
		try {
			return createImageIconWithException(baseClass, file);
		} catch (IOException e) { //图片加载失败
			System.err.println("Can't find the resource: " +
					((file.startsWith("/") || file.startsWith("\\"))
							? file.replaceAll("/", "\\\\")
							: (baseClass.getPackageName().replaceAll("\\.", "\\\\") + "\\" + file.replaceAll("/", "\\\\"))));
			return null;
		}
	}

	/**
	 * 通过传递类和相对图像文件路径获取 ImageIcon。
	 * 请注意，如果找不到图像，getImageIcon 将向 控制台 打印错误消息。我们这样做的原因是因为我们希望您确保所有图像文件都在您的应用程序中。
	 * 如果您看到错误消息，您应该在发布前更正它。但是，如果您只想测试图像文件是否存在，则不希望打印出任何错误消息。如果是这样，您可以使用
	 * {@link #findImageIcon(Class, String)}方法。当找不到图像时，它将抛出 IOException。
	 * 我们使用这种方法创建了我们在 JIDE 代码中使用的所有图标。如果您想使用自己的图标而不是 JIDE 的默认图标，只需将它放到 UIManager 上即
	 * 可。例如，AutoFilterTableHeader 在表头上使用一个图标。这就是它的名字。
	 * <pre>
	 *     IconsFactory.getImageIcon(AutoFilterTableHeader.class, "icons/filterYes_over.png")
	 * </pre>
	 * 这个图标的关键是"com.jidesoft.grid.AutoFilterTableHeader:icons/filterYes_over.png"。所以你可以调用下面的代码来注册你自己的图标。
	 * <pre>
	 *     UIManager.put("com.jidesoft.grid.AutoFilterTableHeader:icons/filterYes_over.png", your_new_icon);
	 * </pre>
	 * <p>
	 * 如果你不知道使用什么键，就在这个方法下一个断点，运行它来检查下面的 id 变量。
	 *
	 * @param clazz    类
	 * @param fileName 相对文件名
	 * @return 图标类或 null
	 */
	public static ImageIcon getImageIcon(Class<?> clazz, String fileName) {
		// 这个id对应要查找的图标,它首先会先查询UIManager中的值,如果存在就使用,否则会就从文件中去找,并且找到后添加到UIManager中
		String id = clazz.getName() + ":" + fileName;
		Object iconInUIDefaults = UIManager.getDefaults().get(id);
		if (iconInUIDefaults instanceof ImageIcon) {
			return (ImageIcon) iconInUIDefaults;
		}
		ImageIcon saved = _icons.get(id);
		if (saved != null) return saved;

		else {
			ImageIcon icon = createImageIcon(clazz, fileName);
			_icons.put(id, icon);
			return icon;
		}
	}

	/**
	 * 通过传递类和相对图像文件路径获取，与{@link #getImageIcon(Class, String)} 不同，该方法找不到文件时会抛异常
	 *
	 * @param clazz    类
	 * @param fileName 相对文件名
	 * @return 图标类
	 * @throws IOException 当找不到图像文件时
	 */
	public static ImageIcon findImageIcon(Class<?> clazz, String fileName) throws IOException {
		String id = clazz.getName() + ":" + fileName;
		Object iconInUIDefaults = UIManager.getDefaults().get(id);
		if (iconInUIDefaults instanceof ImageIcon) {
			return (ImageIcon) iconInUIDefaults;
		}
		ImageIcon saved = _icons.get(id);
		if (saved != null) return saved;
		else {
			ImageIcon icon = createImageIconWithException(clazz, fileName);
			_icons.put(id, icon);
			return icon;
		}
	}

	// ---------------------------------------------------------------------
	// SVG图标的方法
	// ---------------------------------------------------------------------

	/**
	 * 调用AbstractRadianceIcon子类的of()静态方法创建图标对象
	 *
	 * @param c      AbstractRadianceIcon子类，拥有返回图标对象的of方法
	 * @param width  图标宽度
	 * @param height 图标高度
	 * @return 图标对象
	 */
	public static SvgIcon getSvgIcon(Class<? extends AbstractSvgIcon> c, int width, int height) {
		try {
			Method ofMethod = c.getMethod("of", int.class, int.class);
			return (SvgIcon) ofMethod.invoke(null, width, height);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 反射方法创建Icon对象并设置Icon颜色
	 *
	 * @param c         AbstractRadianceIcon子类，拥有返回图标对象的of方法
	 * @param width     图标宽度
	 * @param height    图标高度
	 * @param iconColor 图标颜色
	 * @return 图标对象
	 */
	public static SvgIcon getSvgIcon(Class<? extends AbstractSvgIcon> c, int width, int height, Color iconColor) {
		SvgIcon icon = getSvgIcon(c, width, height);
		icon.setColorFilter(color -> iconColor);
		return icon;
	}

	/**
	 * Svg转换为BufferedImage
	 *
	 * @see BufferedImageTranscoder#loadImage(Reader, Float, Float)
	 */
	public static BufferedImage getSvgImage(Reader svgFile, Float width, Float height) {
		return BufferedImageTranscoder.loadImage(svgFile, width, height);
	}

	/**
	 * Svg转换为BufferedImage
	 *
	 * @see BufferedImageTranscoder#loadImage(InputStream, Float, Float)
	 */
	public static BufferedImage getSvgImage(InputStream svgFile,  Float width, Float height) {
		return BufferedImageTranscoder.loadImage(svgFile, width, height);
	}

	// ---------------------------------------------------------------------
	// 图像处理
	// ---------------------------------------------------------------------

	/**
	 * 使用合适的方法缩放 ImageIcon对象
	 */
	public static ImageIcon imageIconScale(ImageIcon image, int newWidth, int newHeight) {
		Image img = image.getImage();
		// 这里还判断了一下，虽然我觉得Image都是BufferedImage类型的
		if (img instanceof BufferedImage) img = createScaledCompositeInstance((BufferedImage) img, newWidth, newHeight);
		else img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		return new ImageIcon(img);
	}

	/**
	 * Gets a brighter ImageIcon by passing class and a relative image file path.
	 *
	 * @param clazz    the Class<?>
	 * @param fileName relative file name
	 * @return the ImageIcon
	 */
	public static ImageIcon getBrighterImageIcon(Class<?> clazz, String fileName) {
		String id = clazz.getName() + ":" + fileName + ":" + ColorFilter.getPercent();
		ImageIcon saved = _brighterIcons.get(id);
		if (saved != null)
			return saved;
		else {
			ImageIcon icon = createBrighterImage(getImageIcon(clazz, fileName));
			_brighterIcons.put(id, icon);
			return icon;
		}
	}

	/**
	 * Gets a brighter ImageIcon by passing class, a relative image file path and a percentage of brightness.
	 *
	 * @param clazz    the Class<?>
	 * @param fileName relative file name
	 * @param percent  percentage of brightness
	 * @return the ImageIcon
	 */
	public static ImageIcon getBrighterImageIcon(Class<?> clazz, String fileName, int percent) {
		String id = clazz.getName() + ":" + fileName + ":" + percent;
		ImageIcon saved = _brighterIcons.get(id);
		if (saved != null)
			return saved;
		else {
			ImageIcon icon = createBrighterImage(getImageIcon(clazz, fileName), percent);
			_brighterIcons.put(id, icon);
			return icon;
		}
	}

	/**
	 * Gets a tinted ImageIcon by passing class, a relative image file path and a color.
	 *
	 * @param clazz    the Class<?>
	 * @param fileName relative file name
	 * @param color    the color
	 * @return the ImageIcon
	 */
	public static ImageIcon getTintedImageIcon(Class<?> clazz, String fileName, Color color) {
		String id = clazz.getName() + ":" + fileName + ":" + color.toString();
		ImageIcon saved = _tintedIcons.get(id);
		if (saved != null)
			return saved;
		else {
			ImageIcon icon = createTintedImage(getImageIcon(clazz, fileName), color);
			_tintedIcons.put(id, icon);
			return icon;
		}
	}

	/**
	 * Gets a disabled version of ImageIcon by passing class and a relative image file path.
	 *
	 * @param clazz    the Class<?>
	 * @param fileName relative file name
	 * @return the ImageIcon
	 */
	public static ImageIcon getDisabledImageIcon(Class<?> clazz, String fileName) {
		String id = clazz.getName() + ":" + fileName;
		ImageIcon saved = _disableIcons.get(id);
		if (saved != null)
			return saved;
		else {
			ImageIcon icon = createGrayImage(getImageIcon(clazz, fileName));
			_disableIcons.put(id, icon);
			return icon;
		}
	}

	/**
	 * Creates a gray version from an input image. Usually gray icon indicates disabled. If input image is null, a blank
	 * ImageIcon will be returned.
	 *
	 * @param image image
	 * @return gray version of the image
	 */
	public static ImageIcon createGrayImage(Image image) {
		if (image == null)
			return EMPTY_ICON;
		return new ImageIcon(GrayFilter.createDisabledImage(image));
	}

	/**
	 * Creates a gray version from an input ImageIcon. Usually gray icon indicates disabled. If input icon is null, a
	 * blank ImageIcon will be returned.
	 *
	 * @param icon image
	 * @return gray version of the image
	 */
	private static ImageIcon createGrayImage(ImageIcon icon) {
		if (icon == null)
			return EMPTY_ICON;
		return new ImageIcon(GrayFilter.createDisabledImage(icon.getImage()));
	}

	/**
	 * Creates a gray version from an input image. Usually gray icon indicates disabled. If input icon is null, a blank
	 * ImageIcon will be returned.
	 *
	 * @param c    The component to get properties useful for painting, e.g. the foreground or background color.
	 * @param icon icon
	 * @return gray version of the image
	 */
	public static ImageIcon createGrayImage(Component c, Icon icon) {
		if (icon == null)
			return EMPTY_ICON;

		int w = icon.getIconWidth(), h = icon.getIconHeight();
		if ((w == 0) || (h == 0))
			return EMPTY_ICON;

		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(c, image.getGraphics(), 0, 0);
		return new ImageIcon(GrayFilter.createDisabledImage(image));
	}

	/**
	 * Creates a brighter image from an input image. If input image is null, a blank ImageIcon will be returned.
	 *
	 * @param image image
	 * @return dimmed version of the image
	 */
	public static ImageIcon createBrighterImage(Image image) {
		if (image == null)
			return EMPTY_ICON;
		return new ImageIcon(ColorFilter.createBrighterImage(image));
	}

	/**
	 * Creates a brighter image from an input image with a given percentage of brightness. If input image is null, a
	 * blank ImageIcon will be returned.
	 *
	 * @param image   image
	 * @param percent percentage of brightness
	 * @return dimmed version of the image
	 */
	public static ImageIcon createBrighterImage(Image image, int percent) {
		if (image == null)
			return EMPTY_ICON;
		return new ImageIcon(ColorFilter.createBrighterImage(image, percent));
	}

	/**
	 * Creates a gray version from an input image. Usually gray icon indicates disabled. If input icon is null, a blank
	 * ImageIcon will be returned.
	 *
	 * @param c    The component to get properties useful for painting, e.g. the foreground or background color.
	 * @param icon icon
	 * @return gray version of the image
	 */
	public static ImageIcon createBrighterImage(Component c, Icon icon) {
		if (icon == null)
			return EMPTY_ICON;
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(c, image.getGraphics(), 0, 0);
		return new ImageIcon(ColorFilter.createBrighterImage(image));
	}

	/**
	 * Creates a gray version from an input image with a given percentage of brightness. Usually gray icon indicates
	 * disabled. If input icon is null, a blank ImageIcon will be returned.
	 *
	 * @param c       The component to get properties useful for painting, e.g. the foreground or background color.
	 * @param icon    icon
	 * @param percent percentage of brightness
	 * @return gray version of the image
	 */
	public static ImageIcon createBrighterImage(Component c, Icon icon, int percent) {
		if (icon == null)
			return EMPTY_ICON;
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(c, image.getGraphics(), 0, 0);
		return new ImageIcon(ColorFilter.createBrighterImage(image, percent));
	}

	/**
	 * Creates a brighten version from an input ImageIcon. If input icon is null, a blank ImageIcon will be returned.
	 *
	 * @param icon image
	 * @return dimmed version of the image
	 */
	public static ImageIcon createBrighterImage(ImageIcon icon) {
		if (icon == null)
			return EMPTY_ICON;
		return new ImageIcon(ColorFilter.createBrighterImage(icon.getImage()));
	}

	/**
	 * Creates a brighter image from an input image with a given percentage of brightness. If input image is null, a
	 * blank ImageIcon will be returned.
	 *
	 * @param icon    image
	 * @param percent percentage of brightness
	 * @return dimmed version of the image
	 */
	public static ImageIcon createBrighterImage(ImageIcon icon, int percent) {
		if (icon == null)
			return EMPTY_ICON;
		return new ImageIcon(ColorFilter.createBrighterImage(icon.getImage(), percent));
	}

	/**
	 * Creates a tinted image from an input image with a given color. If input image is null, a blank ImageIcon will be
	 * returned.
	 *
	 * @param icon  image
	 * @param color the color
	 * @return a tinted version of the image
	 */
	public static ImageIcon createTintedImage(ImageIcon icon, Color color) {
		if (icon == null)
			return EMPTY_ICON;
		return new ImageIcon(TintFilter.createTintedImage(icon.getImage(), color, null));
	}

	/**
	 * Creates a gray version from an input image. Usually gray icon indicates disabled. If input image is null, a blank
	 * ImageIcon will be returned.
	 *
	 * @param image image
	 * @return gray version of the image
	 */
	public static ImageIcon createNegativeImage(Image image) {
		if (image == null)
			return EMPTY_ICON;
		return new ImageIcon(MaskFilter.createNegativeImage(image));
	}

	/**
	 * Creates a version from an input image which replaces one color with another color.
	 *
	 * @param c        The component to get properties useful for painting, e.g. the foreground or background color.
	 * @param icon     icon
	 * @param oldColor the old color to be replaced.
	 * @param newColor the new color that will replace the old color.
	 * @return the image after replacing the color.
	 */
	public static ImageIcon createMaskImage(Component c, Icon icon, Color oldColor, Color newColor) {
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(c, image.getGraphics(), 0, 0);
		return new ImageIcon(MaskFilter.createImage(image, oldColor, newColor));
	}

	/**
	 * 创建输入图像的旋转版本。
	 *
	 * @param c            旋转后新图标绘制的组件，如果不需要绘制传入null
	 * @param icon         要旋转的图像
	 * @param rotatedAngle 顺时针旋转的角度，以度为单位。它可以是任何 double，但我们会在使用它之前用 360 对其进行修改。
	 * @return 旋转后的图像
	 */
	public static ImageIcon createRotatedImage(Component c, Icon icon, double rotatedAngle) {
		// convert rotatedAngle to a value from 0 to 360
		double originalAngle = rotatedAngle % 360;
		if (rotatedAngle != 0 && originalAngle == 0) {
			originalAngle = 360.0;
		}

		// convert originalAngle to a value from 0 to 90
		double angle = originalAngle % 90;
		if (originalAngle != 0.0 && angle == 0.0) {
			angle = 90.0;
		}

		double radian = Math.toRadians(angle);

		int iw = icon.getIconWidth();
		int ih = icon.getIconHeight();
		int w;
		int h;

		if ((originalAngle >= 0 && originalAngle <= 90) || (originalAngle > 180 && originalAngle <= 270)) {
			w = (int) Math.round((iw * Math.sin(DEGREE_90 - radian) + ih * Math.sin(radian)));
			h = (int) Math.round((iw * Math.sin(radian) + ih * Math.sin(DEGREE_90 - radian)));
		} else {
			w = (int) (ih * Math.sin(DEGREE_90 - radian) + iw * Math.sin(radian));
			h = (int) (ih * Math.sin(radian) + iw * Math.sin(DEGREE_90 - radian));
		}

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		Graphics2D g2d = (Graphics2D) g.create();

		// calculate the center of the icon.
		int cx = iw / 2;
		int cy = ih / 2;

		// account for images that have a center point in the middle of a pixel.
		// for these images (not divisible by two) we need to account for the
		// "down and to the right" bias of the graphics context.
		int xOffset = iw % 2 != 0 && originalAngle >= 90 && originalAngle <= 180
				? 1 : 0;
		int yOffset = iw % 2 != 0 && originalAngle >= 180 && originalAngle < 360
				? 1 : 0;

		// move the graphics center point to the center of the icon.
		g2d.translate(w / 2 + xOffset, h / 2 + yOffset);

		// rotate the graphics about the center point of the icon
		g2d.rotate(Math.toRadians(originalAngle));

		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		icon.paintIcon(c, g2d, -cx, -cy);

		g2d.dispose();
		return new ImageIcon(image);
	}

	/**
	 * Creates a negative version from an input black image which basically replaces black pixel with white pixel.
	 *
	 * @param c    The component to get properties useful for painting, e.g. the foreground or background color.
	 * @param icon icon
	 * @return the negative version of the image
	 */
	public static ImageIcon createNegativeImage(Component c, Icon icon) {
		BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		icon.paintIcon(c, image.getGraphics(), 0, 0);
		return new ImageIcon(MaskFilter.createNegativeImage(image));
	}

	/**
	 * Gets part of the image from input image icon. It basically takes a snapshot of the input image at {x, y} location
	 * and the size is width x height.
	 *
	 * @param c      the component where the returned icon will be used. The component is used as the ImageObserver. It
	 *               could be null.
	 * @param icon   the original icon. This is the larger icon where a sub-image will be created using this method.
	 * @param x      the x location of the sub-image, relative to the original icon.
	 * @param y      the y location of the sub-image, relative to the original icon.
	 * @param width  the width of the sub-image. It should be less than the width of the original icon.
	 * @param height the height of the sub-image. It should be less than the height of the original icon.
	 * @return an new image icon that was part of the input image icon.
	 */
	public static ImageIcon getIcon(Component c, ImageIcon icon, int x, int y, int width, int height) {
		return getIcon(c, icon, x, y, width, height, width, height);
	}

	/**
	 * Gets part of the image from input image icon. It basically takes a snapshot of the input image at {x, y} location
	 * and the size is width x height, then resize it to a size of destWidth x destHeight.
	 *
	 * @param c          the component where the returned icon will be used. The component is used as the ImageObserver.
	 *                   It could be null.
	 * @param icon       the original icon. This is the larger icon where a sub-image will be created using this
	 *                   method.
	 * @param x          the x location of the sub-image, relative to the original icon.
	 * @param y          the y location of the sub-image, relative to the original icon.
	 * @param width      the width of the sub-image. It should be less than the width of the original icon.
	 * @param height     the height of the sub-image. It should be less than the height of the original icon.
	 * @param destWidth  the width of the returned icon. The sub-image will be resize if the destWidth is not the same
	 *                   as the width.
	 * @param destHeight the height of the returned icon. The sub-image will be resize if the destHeight is not the same
	 *                   as the height.
	 * @return an new image icon that was part of the input image icon.
	 */
	public static ImageIcon getIcon(Component c, ImageIcon icon, int x, int y, int width, int height, int destWidth, int destHeight) {
		return getIcon(c, icon, x, y, width, height, BufferedImage.TYPE_INT_ARGB, destWidth, destHeight);
	}

	/**
	 * Gets part of the image from input image icon. It basically takes a snapshot of the input image at {x, y} location
	 * and the size is width x height.
	 *
	 * @param c         the component where the returned icon will be used. The component is used as the ImageObserver.
	 *                  It could be null.
	 * @param icon      the original icon. This is the larger icon where a small icon will be created using this
	 *                  method.
	 * @param x         the x location of the smaller icon, relative to the original icon.
	 * @param y         the y location of the smaller icon, relative to the original icon.
	 * @param width     the width of the smaller icon. It should be less than the width of the original icon.
	 * @param height    the height of the smaller icon. It should be less than the height of the original icon.
	 * @param imageType image type is defined in {@link BufferedImage}, such as {@link BufferedImage#TYPE_INT_ARGB},
	 *                  {@link BufferedImage#TYPE_INT_RGB} etc.
	 * @return an new image icon that was part of the input image icon.
	 */
	public static ImageIcon getIcon(Component c, ImageIcon icon, int x, int y, int width, int height, int imageType) {
		return getIcon(c, icon, x, y, width, height, imageType, width, height);
	}

	/**
	 * Gets part of the image from input image icon. It basically takes a snapshot of the input image at {x, y} location
	 * and the size is width x height, then resize it to a size of destWidth x destHeight. if the original icon is null
	 * or the specified location is outside the original icon, EMPTY_ICON will be returned.
	 *
	 * @param c          the component where the returned icon will be used. The component is used as the ImageObserver.
	 *                   It could be null.
	 * @param icon       the original icon. This is the larger icon where a sub-image will be created using this
	 *                   method.
	 * @param x          the x location of the sub-image, relative to the original icon.
	 * @param y          the y location of the sub-image, relative to the original icon.
	 * @param width      the width of the sub-image. It should be less than the width of the original icon.
	 * @param height     the height of the sub-image. It should be less than the height of the original icon.
	 * @param imageType  image type is defined in {@link BufferedImage}, such as {@link BufferedImage#TYPE_INT_ARGB},
	 *                   {@link BufferedImage#TYPE_INT_RGB} etc.
	 * @param destWidth  the width of the returned icon. The sub-image will be resize if the destWidth is not the same
	 *                   as the width.
	 * @param destHeight the height of the returned icon. The sub-image will be resize if the destHeight is not the same
	 *                   as the height.
	 * @return an new image icon that was part of the input image icon.
	 */
	public static ImageIcon getIcon(Component c, ImageIcon icon, int x, int y, int width, int height, int imageType, int destWidth, int destHeight) {
		if (icon == null || x < 0 || x + width > icon.getIconWidth() || y < 0 || y + height > icon.getIconHeight()) { // outside the original icon.
			return EMPTY_ICON;
		}
		BufferedImage image = new BufferedImage(destWidth, destHeight, imageType);
		image.getGraphics().drawImage(icon.getImage(),
				0, 0, destWidth, destHeight,
				x, y, x + width, y + height,
				c);
		return new ImageIcon(image);
	}

	/**
	 * Gets a new icon with the overlayIcon paints over the original icon.
	 *
	 * @param c           the component where the returned icon will be used. The component is used as the
	 *                    ImageObserver. It could be null.
	 * @param icon        the original icon
	 * @param overlayIcon the overlay icon.
	 * @param location    the location as defined in SwingConstants - CENTER, NORTH, SOUTH, WEST, EAST, NORTH_EAST,
	 *                    NORTH_WEST, SOUTH_WEST and SOUTH_EAST.
	 * @return the new icon.
	 */
	public static ImageIcon getOverlayIcon(Component c, ImageIcon icon, ImageIcon overlayIcon, int location) {
		return getOverlayIcon(c, icon, overlayIcon, location, new Insets(0, 0, 0, 0));
	}

	/**
	 * Gets a new icon with the overlayIcon paints over the original icon.
	 *
	 * @param c           the component where the returned icon will be used. The component is used as the
	 *                    ImageObserver. It could be null.
	 * @param icon        the original icon
	 * @param overlayIcon the overlay icon.
	 * @param location    the location as defined in SwingConstants - CENTER, NORTH, SOUTH, WEST, EAST, NORTH_EAST,
	 *                    NORTH_WEST, SOUTH_WEST and SOUTH_EAST.
	 * @param insets      the insets to the border. This parameter has no effect if the location is CENTER. For example,
	 *                    if the location is WEST, insets.left will be the gap of the left side of the original icon and
	 *                    the left side of the overlay icon.
	 * @return the new icon.
	 */
	public static ImageIcon getOverlayIcon(Component c, ImageIcon icon, ImageIcon overlayIcon, int location, Insets insets) {
		int x = -1, y = -1;
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		int sw = overlayIcon.getIconWidth();
		int sh = overlayIcon.getIconHeight();
		switch (location) {
			case SwingConstants.CENTER -> {
				x = (w - sw) / 2;
				y = (h - sh) / 2;
			}
			case SwingConstants.NORTH -> {
				x = (w - sw) / 2;
				y = insets.top;
			}
			case SwingConstants.SOUTH -> {
				x = (w - sw) / 2;
				y = h - insets.bottom - sh;
			}
			case SwingConstants.WEST -> {
				x = insets.left;
				y = (h - sh) / 2;
			}
			case SwingConstants.EAST -> {
				x = w - insets.right - sw;
				y = (h - sh) / 2;
			}
			case SwingConstants.NORTH_EAST -> {
				x = w - insets.right - sw;
				y = insets.top;
			}
			case SwingConstants.NORTH_WEST -> {
				x = insets.left;
				y = insets.top;
			}
			case SwingConstants.SOUTH_WEST -> {
				x = insets.left;
				y = h - insets.bottom - sh;
			}
			case SwingConstants.SOUTH_EAST -> {
				x = w - insets.right - sw;
				y = h - insets.bottom - sh;
			}
		}
		return getOverlayIcon(c, icon, overlayIcon, x, y);
	}

	/**
	 * Gets a new icon with the overlayIcon paints over the original icon.
	 *
	 * @param c           the component where the returned icon will be used. The component is used as the
	 *                    ImageObserver. It could be null.
	 * @param icon        the original icon
	 * @param overlayIcon the overlay icon.
	 * @param x           the x location relative to the original icon where the overlayIcon will be pained.
	 * @param y           the y location relative to the original icon where the overlayIcon will be pained.
	 * @return the overlay icon
	 */
	public static ImageIcon getOverlayIcon(Component c, ImageIcon icon, ImageIcon overlayIcon, int x, int y) {
		int w = icon == null ? overlayIcon.getIconWidth() : icon.getIconWidth();
		int h = icon == null ? overlayIcon.getIconHeight() : icon.getIconHeight();
		int sw = overlayIcon.getIconWidth();
		int sh = overlayIcon.getIconHeight();
		if (x != -1 && y != -1) {
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			if (icon != null) {
				image.getGraphics().drawImage(icon.getImage(), 0, 0, w, h, c);
			}
			image.getGraphics().drawImage(overlayIcon.getImage(), x, y, sw, sh, c);
			return new ImageIcon(image);
		} else {
			return icon;
		}
	}

	/**
	 * Gets a new icon with the icon2 painting right or down to the icon1.
	 *
	 * @param c           the component where the returned icon will be used. The component is used as the
	 *                    ImageObserver. It could be null
	 * @param icon1       the left side or up side icon
	 * @param icon2       the right side or down side icon
	 * @param orientation the orientation as defined in SwingConstants - HORIZONTAL, VERTICAL
	 * @param gap         the gap between the two icons
	 * @return the new icon.
	 */
	public static ImageIcon getCombinedIcon(Component c, ImageIcon icon1, ImageIcon icon2, int orientation, int gap) {
		if (icon1 == null) {
			return icon2;
		}
		if (icon2 == null) {
			return icon1;
		}
		int x1, y1, x2, y2, width, height;
		int w1 = icon1.getIconWidth();
		int h1 = icon1.getIconHeight();
		int w2 = icon2.getIconWidth();
		int h2 = icon2.getIconHeight();

		if (orientation == SwingConstants.HORIZONTAL) {
			width = w1 + w2 + gap;
			height = Math.max(h1, h2);
			x1 = 0;
			x2 = w1 + gap;
			y1 = h1 > h2 ? 0 : (h2 - h1) / 2;
			y2 = h1 < h2 ? 0 : (h1 - h2) / 2;
		} else {
			width = Math.max(w1, w2);
			height = h1 + h2 + gap;
			x1 = w1 > w2 ? 0 : (w2 - w1) / 2;
			x2 = w1 < w2 ? 0 : (w1 - w2) / 2;
			y1 = 0;
			y2 = h1 + gap;
		}

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.getGraphics().drawImage(icon1.getImage(), x1, y1, w1, h1, c);
		image.getGraphics().drawImage(icon2.getImage(), x2, y2, w2, h2, c);
		return new ImageIcon(image);
	}

	/**
	 * Writes a GIF image of the supplied component to the given file. In particular, you can use this method to take a
	 * 'screen shot' of a Chart component as a GIF Image.
	 *
	 * @param c    the component to save as an image
	 * @param file the file to save it to
	 * @throws FileNotFoundException if the file exists but is a directory rather than a regular file, does not exist
	 *                               but cannot be created, or cannot be opened for any other reason
	 */
	public static void writeGifToFile(Component c, File file) throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(file);
		writeToStream(c, "gif", fos);
		try {
			fos.close();
		} catch (IOException e) {
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}

	/**
	 * Writes a JPEG image of the supplied component to the given file. In particular, you can use this method to take a
	 * 'screen shot' of a Chart component as a JPEG Image.
	 *
	 * @param c    the component to save as an image
	 * @param file the file to save it to
	 * @throws FileNotFoundException if the file exists but is a directory rather than a regular file, does not exist
	 *                               but cannot be created, or cannot be opened for any other reason
	 */
	public static void writeJpegToFile(Component c, File file) throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(file);
		writeToStream(c, "jpg", fos);
		try {
			fos.close();
		} catch (IOException e) {
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}

	/**
	 * Writes a PNG image of the supplied component to the given file. In particular, you can use this method to take a
	 * 'screen shot' of a Chart component as a PNG Image.
	 *
	 * @param c    the component to save as an image
	 * @param file the file to save it to
	 * @throws FileNotFoundException if the file exists but is a directory rather than a regular file, does not exist
	 *                               but cannot be created, or cannot be opened for any other reason
	 */
	public static void writePngToFile(Component c, File file) throws FileNotFoundException {
		FileOutputStream fos = new FileOutputStream(file);
		writeToStream(c, "png", fos);
		try {
			fos.close();
		} catch (IOException e) {
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}

	/**
	 * Paints the component as a PNG image to the supplied output stream
	 *
	 * @param c      the component to capture as a PNG image
	 * @param stream the stream to write the PNG data to
	 */
	public static void writeToStream(Component c, OutputStream stream) {
		writeToStream(c, "png", stream);
	}

	/**
	 * Paints the component to the supplied output stream
	 *
	 * @param c      the component to capture as an image
	 * @param format the format of the image output data, currently "png", "jpg" or "gif"
	 * @param stream the stream to write the image data to
	 */
	private static void writeToStream(Component c, String format, OutputStream stream) {
		BufferedImage img = createImage(c);
		try {
			ImageIO.write(img, format, stream);
		} catch (IOException e) {
			// could happen if the output format is not supported
			Logger.getAnonymousLogger().severe(e.getMessage());
		}
	}

	/**
	 * Creates a buffered image of type TYPE_INT_RGB from the supplied component.
	 *
	 * @param component the component to draw
	 * @return an image of the component
	 */
	public static BufferedImage createImage(Component component) {
		return createImage(component, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * Creates a buffered image (of the specified type) from the supplied component.
	 *
	 * @param component the component to draw
	 * @param bounds    the area relative to the component where the image will be created.
	 * @param imageType the type of buffered image to draw
	 * @return an image of the component
	 */
	public static BufferedImage createImage(final Component component, Rectangle bounds, int imageType) {
		return createImage(component, bounds, imageType, 1);
	}

	/**
	 * Creates a buffered image (of the specified type) from the supplied component.
	 *
	 * @param component the component to draw
	 * @param imageType the type of buffered image to draw
	 * @return an image of the component
	 */
	public static BufferedImage createImage(final Component component, int imageType) {
		Dimension componentSize = component.getSize();
		return createImage(component, new Rectangle(0, 0, componentSize.width, componentSize.height), imageType, 1);
	}

	/**
	 * Creates a buffered image (of the specified type) from the supplied component.
	 * <p>
	 * This method will consider the scale factor for hi-def display such as retina display.
	 * If the scale factor is 2, the returned image size will double the size of the bounds.
	 * When you paint the returned image, you should call g.scale(0.5, 0.5) or SystemInfo.endScale(g, SystemInfo.getDisplayScale())
	 * to paint the image to its normal size. For performance consideration, you may want to keep the
	 * return value of SystemInfo.getDisplayScale() instead of calling it all the time.
	 *
	 * @param component the component to draw
	 * @param bounds    the area relative to the component where the image will be created.
	 * @param imageType the type of buffered image to draw
	 * @param scale     the scale factor of the display
	 * @return an image of the component
	 */
	public static BufferedImage createImage(final Component component, Rectangle bounds, int imageType, int scale) {
		BufferedImage img = new BufferedImage(bounds.width * scale,
				bounds.height * scale,
				imageType);
		final Graphics2D g = img.createGraphics();
		g.scale(scale, scale);
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		// If we are painting a JComponent then switch off double buffering because it
		// causes a failure when running headlessly on Linux
		if (component instanceof JComponent c) {
			boolean isDoubleBuffered = c.isDoubleBuffered();
			c.setDoubleBuffered(false);
			g.translate(-bounds.x, -bounds.y);
			g.setClip(bounds.x, bounds.y, bounds.width, bounds.height);
			c.paint(g);
			c.setDoubleBuffered(isDoubleBuffered);
		} else {
			component.paint(g);
		}
		g.dispose();
		return img;
	}

	/**
	 * Creates a thumbnail from the supplied component (such as a chart). If you want to display the thumbnail as a
	 * component you can pass the created ImageIcon as a parameter to the constructor of a JLabel.
	 *
	 * @param component the component from which we would like to generate a thumbnail
	 * @param width     the width of the thumbnail
	 * @param height    the height of the thumbnail
	 * @return a thumbnail Image of the supplied component
	 */
	public static Image createThumbnailImage(Component component, int width, int height) {
		BufferedImage image = createImage(component);
		BufferedImage thumbnailImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = thumbnailImage.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		double w = (double) width / image.getWidth();
		double h = (double) height / image.getHeight();
		assert w <= 1.0 : "The thumbnail should be smaller than the original";
		assert h <= 1.0 : "The thumbnail should be smaller than the original";
		AffineTransform transform = AffineTransform.getScaleInstance(w, h);
		g.drawRenderedImage(image, transform);
		return thumbnailImage;
	}

	/**
	 * Creates a thumbnail from the supplied component (such as a chart). If you want to display the thumbnail as a
	 * component you can pass the created ImageIcon as a parameter to the constructor of a JLabel.
	 *
	 * @param component the component from which we would like to generate a thumbnail
	 * @param width     the width of the thumbnail
	 * @param height    the height of the thumbnail
	 * @return an ImageIcon of the supplied component
	 */
	public static ImageIcon createThumbnail(Component component, int width, int height) {
		return new ImageIcon(createThumbnailImage(component, width, height));
	}

	/**
	 * Utility method to create a texture paint from a graphics file
	 *
	 * @param observer the observer to be informed when the texture image has been drawn
	 * @param fileName the name of a file on the classpath, e.g., com/mycompany/project/images/widget.gif
	 * @return a TexturePaint instance
	 */

	public static TexturePaint createTexture(JComponent observer, String fileName) {
		Image image = createImage(fileName);
		MediaTracker tracker = new MediaTracker(observer);
		tracker.addImage(image, 1);
		try {
			tracker.waitForAll();
		} catch (Exception ignored) {
		}
		int w = image.getWidth(observer);
		int h = image.getHeight(observer);
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D big = bi.createGraphics();
		big.drawImage(image, 0, 0, observer);
		return new TexturePaint(bi, new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
	}

	/**
	 * Creates an image from a file on the classpath
	 *
	 * @param path the path to the file
	 * @return an Image object
	 */
	public static Image createImage(String path) {
		ClassLoader loader = IconsFactory.class.getClassLoader();
		if (loader != null) {
			URL url = loader.getResource(path);
			if (url == null) {
				url = loader.getResource("/" + path);
			}
			return Toolkit.getDefaultToolkit().createImage(url);
		}
		return null;
	}

	// ---------------------------------------------------------------------
	// 生成HTML，展示类定义的图标
	// ---------------------------------------------------------------------

	/**
	 * Generates HTML that lists all icons in IconsFactory.
	 *
	 * @param clazz the IconsFactory class
	 */
	public static void generateHTML(Class<?> clazz) {
		String fullClassName = clazz.getName();
		String className = getClassName(fullClassName);
		File file = new File(fullClassName + ".html");

		try (FileWriter writer = new FileWriter(file)) {
			StringBuilder b = new StringBuilder();
			b.append("""
							<!doctype html>
							<html lang="en">
							<head>
							    <meta charset="UTF-8">
							    <title>图标列表</title>
							    <style>
							        body {font-family: "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;background: #E6EAE9;}
							       
							        .mytable {margin: 10px;border-spacing: 0;border-collapse: collapse;}
							       
							        tr {background: #fff;color: #4f6b72;}
							       
							        th {font: bold 18px "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif;color: #fff;
							            border-right: 1px solid #C1DAD7;border-bottom: 1px solid #C1DAD7;border-top: 1px solid #C1DAD7;
							            letter-spacing: 2px;text-align: left;padding: 6px 6px 6px 12px;background: #0098a2 no-repeat;}
							       
							        td {border-right: 1px solid #C1DAD7;border-bottom: 1px solid #C1DAD7;font-size: 16px;padding: 6px 6px 6px 12px;}
							       
							        .tr1 {background-color: #269fa7;color: white;}
							       
							        .tr2 {background-color: white;}
							    </style>
							</head>
							""")
					.append("<body>\n")
					.append("<h1>图标定义在 ").append(fullClassName).append("</h1>\n")
					.append("<ol>\n")
					.append("<li>如果您无法查看此页面中的图像，请确保该文件位于正确的资源目录下</li>\n")
					.append("<li>要在代码中获取特定图标，请调用 <b>JideIconsFactory.getImageIcon(FULL_CONSTANT_NAME)</b>。将 FULL_CONSTANT_NAME 替换为下表中的实际完整常量名称</li>\n")
					.append("</ol>\n");
			generate(clazz, b, className);
			b.append("""
					<script>
					    let aTr = document.getElementsByTagName("tr");
					    for (let i = 1; i < aTr.length; i++) {
					        aTr[i].onmouseover = function () {
					            aTr[i].className = "tr1";
					        }
					        aTr[i].onmouseout = function () {
					            aTr[i].className = "tr2";
					        }
					    }
					</script>
					""");
			b.append("</body>\n</html>");
			writer.write(b.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("文件生成在 \"" + file.getAbsolutePath() + "\". 请复制到与 " + className + ".java 同级目录下(资源文件目录)");
	}

	private static String getClassName(String fullName) {
		int last = fullName.lastIndexOf(".");
		if (last != -1) {
			fullName = fullName.substring(last + 1);
		}
		StringTokenizer tokenizer = new StringTokenizer(fullName, "$");
		StringBuilder buffer = new StringBuilder();
		while (tokenizer.hasMoreTokens()) {
			buffer.append(tokenizer.nextToken());
			buffer.append(".");
		}
		return buffer.substring(0, buffer.length() - 1);
	}

	private static void generate(Class<?> aClass, StringBuilder b, String prefix) throws IOException {
		Class<?>[] classes = aClass.getDeclaredClasses();
		// don't know why but the order is exactly the reverse of the order of definitions.
		for (int i = classes.length - 1; i >= 0; i--) {
			Class<?> clazz = classes[i];
			generate(clazz, b, getClassName(clazz.getName()));
		}

		Field[] fields = aClass.getDeclaredFields();
		b.append("<p><b>").append(prefix).append("</b></p>\n");
		b.append("<table class=\"mytable\">\n");
		b.append("<tr>\n");
		b.append("<th>Name</th>\n");
		b.append("<th>Image</th>\n");
		b.append("<th>File Name</th>\n");
		b.append("<th>Full Constant Name</th>\n");
		b.append("</tr>\n");
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object name = field.getName();
				Object value = field.get(aClass);
				b.append("<tr>\n");
				b.append("<td>").append(name).append("</td>\n");
				b.append("<td><img src=\"").append(value).append("\"></td>\n");
				b.append("<td>").append(value).append("</td>\n");
				b.append("<td>").append(prefix).append(".").append(name).append("</td>\n");
				b.append("</tr>\n");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		b.append("</table><br>\n");
	}
}
