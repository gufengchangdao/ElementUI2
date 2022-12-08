package com.element.font;

import com.element.plaf.WindowsDesktopProperty;
import com.element.util.SystemInfo;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;

public class FontUtil {
	/** 辅助文字 */
	public static final int AUXILIARY_WORDS = 12;
	/** 正文 */
	public static final int NORMAL = 14;
	/** 小标题 */
	public static final int SMALL_TITLE = 16;
	/** 标题 */
	public static final int TITLE = 18;
	/** 主标题 */
	public static final int PRIMARY_TITLE = 20;

	/**
	 * 有抗锯齿和分数度量 FontRenderContext 对象，应用于自定义组件中文本绘制的定位
	 */
	public static final FontRenderContext FRC = new FontRenderContext(new AffineTransform(), true, true);
	public static final Font SMALL_FONT = new Font(Font.SERIF, Font.PLAIN, AUXILIARY_WORDS);
	public static final Font DEFAULT_FONT = new Font(Font.SERIF, Font.PLAIN, NORMAL);

	/**
	 * 替换全局字体名，但保留组件默认字体的类型和大小。
	 */
	public static void registerGlobalFont(Font font) {
		UIDefaults defaults = UIManager.getDefaults();
		for (Map.Entry<Object, Object> entry : defaults.entrySet()) {
			Object key = entry.getKey();
			if (key.toString().endsWith(".font")) {
				Font f = UIManager.getFont(key);
				UIManager.put(key, font.deriveFont(f.getStyle(), f.getSize()));
			}
		}
	}

	/**
	 * 引入外部字体
	 * <p>
	 * 你可以通过引入外部字体充当图标。例如
	 * <pre>
	 * Font f = getFont("/font/fontawesome-webfont.ttf").deriveFont(20f);
	 * JLabel iconLabel = new JLabel(String.valueOf('\uf0f4'));
	 * iconLabel.setFont(f);
	 * </pre>
	 *
	 * @param fontFileName 外部字体所在路径
	 * @return 引入的字体对象。新字体创建时，字号为1，样式为PLAIN。
	 */
	public static Font getFont(String fontFileName) {
		try (InputStream in = FontUtil.class.getResourceAsStream(fontFileName)) {
			return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(in));
		} catch (NullPointerException | IOException | FontFormatException e) {
			throw new RuntimeException(e);
		}
	}

	/** 获取jide的默认字体大小，如果获取失败返回 -1 */
	public static float getDefaultFontSize() {
		// read the font size from system property.
		String fontSize = System.getProperty("jide.fontSize", null);
		float defaultFontSize = -1f;
		try {
			if (fontSize != null) {
				defaultFontSize = Float.parseFloat(fontSize);
			}
		} catch (NumberFormatException ignored) {
		}

		return defaultFontSize;
	}

	public static Object getMenuFont(Toolkit toolkit, UIDefaults table) {
		Object menuFont;
		// read the font size from system property.
		float defaultFontSize = getDefaultFontSize();

		if (defaultFontSize == -1) {
			menuFont = table.getFont("ToolBar.font");
		} else {
			menuFont = new WindowsDesktopProperty("win.menu.font", table.getFont("ToolBar.font"), toolkit, defaultFontSize);
		}

		if (menuFont == null) {
			return getControlFont(toolkit, table);
		} else {
			return menuFont;
		}
	}

	public static Object getControlFont(Toolkit toolkit, UIDefaults table, String defaultUIDefault) {
		Object controlFont;
		// read the font size from system property.
		float defaultFontSize = getDefaultFontSize();

		Font font = table.getFont(defaultUIDefault);
		if (font == null) {
			font = new Font("Tahoma", Font.PLAIN, 12); // use default font
		}
		if (defaultFontSize == -1) {
			controlFont = font;
		} else {
			controlFont = new WindowsDesktopProperty("win.defaultGUI.font", font, toolkit, defaultFontSize);
		}

		return controlFont;
	}

	public static Object getControlFont(Toolkit toolkit, UIDefaults table) {
		return getControlFont(toolkit, table, "Label.font");
	}

	public static Object getBoldFont(Toolkit toolkit, UIDefaults table) {
		if (SystemInfo.isCJKLocale()) {
			return getControlFont(toolkit, table);
		} else {
			Object boldFont;
			// read the font size from system property.
			float defaultFontSize = getDefaultFontSize();

			Font font = table.getFont("Label.font");
			if (font == null) {
				font = new Font("Tahoma", Font.PLAIN, 12); // use default font
			}
			if (defaultFontSize == -1) {
				boldFont = new FontUIResource(font.deriveFont(Font.BOLD));
			} else {
				boldFont = new WindowsDesktopProperty("win.defaultGUI.font", font, toolkit, defaultFontSize, Font.BOLD);
			}
			return boldFont;
		}
	}

	public static final String BOLD = "Bold";
	public static final String ITALIC = "Italic";
	public static final String BOLD_ITALIC = "Bold Italic";

	private static String createFontStrings(String font, int style) {
		String fontString = switch (style) {
			case Font.BOLD -> font + " " + BOLD;
			case Font.ITALIC -> font + " " + ITALIC;
			case Font.BOLD | Font.ITALIC -> font + " " + BOLD_ITALIC;
			default -> font;
		};
		return fontString.replace(' ', '_');
	}

	/**
	 * Creates font. If there is no permission to access font file, it will try to create the font directly from font
	 * file that is bundled as part of jar.
	 *
	 * @param name  the font name.
	 * @param style the font style.
	 * @param size  the font size.
	 * @return the font.
	 */
	public static Font createFont(String name, int style, int size) {
		try {
			return new Font(name, style, size);
		} catch (Exception e) {
			ClassLoader cl = FontUtil.class.getClassLoader();
			try {
				String value = null;
				try {
					value = FontFilesResource.getResourceBundle(Locale.getDefault()).getString(createFontStrings(name, style));
				} catch (MissingResourceException me1) {
					try {
						value = FontFilesResource.getResourceBundle(Locale.getDefault()).getString(name);
					} catch (MissingResourceException ignored) {
					}
				}
				if (value == null) {
					return null;
				} else {
					try (InputStream inputStream = cl.getResourceAsStream(value)) {
						Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
						return font.deriveFont(style, size);
					}
				}
			} catch (IOException | RuntimeException | FontFormatException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public static FontUIResource createFontUIResource(String name, int style, int size) {
		Font font = createFont(name, style, size);
		if (font != null)
			return new FontUIResource(font);
		return null;
	}
}
