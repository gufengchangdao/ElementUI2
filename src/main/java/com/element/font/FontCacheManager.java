/*
 * @(#)FontUtils.java 9/8/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 *
 */

package com.element.font;

import java.awt.*;
import java.util.Map;

/**
 * 这是一个全局类，用于记录Font，以便我们在各种场景下，如StyledLabel，提高性能和内存占用。
 * <p>
 * 在这个类中，我们有字体和派生字体的全局映射。运行很长时间后，它可能会很大。
 * 在这种情况下，您需要使用 {@link #clearDerivedFontCache()} 显式清除此类中的字体缓存。
 */
public class FontCacheManager {
	private static class FontAttribute {
		private final Font _font;
		private final int _style;
		private final float _size;

		FontAttribute(Font font, int style, float size) {
			_font = font;
			_style = style;
			_size = size;
		}

		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof FontAttribute that)) return false;
			if (Float.compare(that._size, _size) != 0) return false;
			if (_style != that._style) return false;
			return _font != null && _font.equals(that._font);
		}

		public int hashCode() {
			int result;
			result = _font.hashCode();
			result = 31 * result + _style;
			result = 31 * result + (_size != 0.0f ? Float.floatToIntBits(_size) : 0);
			return result;
		}
	}

	private static Map<FontAttribute, Font> _fontCache;

	/**
	 * Gets the derived font cache size.
	 *
	 * @return the derived font cache size.
	 */
	public static int getDerivedFontCacheSize() {
		return _fontCache != null ? _fontCache.size() : 0;
	}

	/**
	 * Clear cache whenever needed.
	 */
	public static void clearDerivedFontCache() {
		if (_fontCache != null) {
			_fontCache.clear();
			_fontCache = null;
		}
	}

	/**
	 * Get derived font by font, style and size. At first it will get the derived font from cache. If it cannot hit the
	 * derived font, it will invoke font.deriveFont to derive a font.
	 *
	 * @param font  the original font
	 * @param style the font style
	 * @param size  the font size
	 * @return the derived font.
	 */
	public static Font getCachedDerivedFont(Font font, int style, int size) {
		if (_fontCache == null) {
			_fontCache = new SoftHashMap<>();
		}
		FontAttribute attribute = new FontAttribute(font, style, size);
		Font derivedFont = _fontCache.get(attribute);
		if (derivedFont == null) {
			derivedFont = font.deriveFont(style, size);
			_fontCache.put(attribute, derivedFont);
		}
		return derivedFont;
	}
}
