/*
 * @(#)TextStyle.java 9/6/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.label;

import java.awt.*;

/**
 * 数据结构表示一系列文本的样式。目前支持两类样式。一种是字体样式和颜色，包括粗体、斜体、上标、下标以及文本的颜色。另一个是线条颜色和样式。线条
 * 样式可以是直线、点线、波浪线或任何使用 Stroke 自定义的样式。该线可用作下划线或删除线。
 * <p>
 * StyleRange 的名字来源于SWT 的StyleRange。我们从中借鉴了一些设计理念。 StyledLabel 实际上与 SWT 的 StyledText 非常相似。话虽如此，
 * 这两个组件的功能并不完全相同，因为这两个组件的用途完全不同。
 */
public class StyleRange {
	public static final int STYLE_STRIKE_THROUGH = 0x1;
	public static final int STYLE_DOUBLE_STRIKE_THROUGH = STYLE_STRIKE_THROUGH << 1;
	public static final int STYLE_WAVED = STYLE_DOUBLE_STRIKE_THROUGH << 1;
	public static final int STYLE_UNDERLINED = STYLE_WAVED << 1;
	public static final int STYLE_DOTTED = STYLE_UNDERLINED << 1;
	public static final int STYLE_SUPERSCRIPT = STYLE_DOTTED << 1;
	public static final int STYLE_SUBSCRIPT = STYLE_SUPERSCRIPT << 1;
	/** 字体样式。有效值为Font.PLAIN, Font.BOLD, Font.ITALIC, or Font.BOLD | Font.ITALIC */
	private final int _fontStyle;
	private final Color _fontColor;

	private final Color _backgroundColor;

	private final Color _lineColor;
	/** 线条描边，如果在附加样式中存在线条，则将使用线条笔画来绘制线条。 */
	private final Stroke _lineStroke;
	/**
	 * 额外的风格。这是您设置为获得各种样式的属性。有效值为
	 * <ul>
	 *     <li>STYLE_STRIKE_THROUGH</li>
	 *     <li>STYLE_DOUBLE_STRIKE_THROUGH</li>
	 *     <li>STYLE_WAVED</li>
	 *     <li>STYLE_UNDERLINED</li>
	 *     <li>STYLE_DOTTED</li>
	 *     <li>STYLE_SUPERSCRIPT</li>
	 *     <li>STYLE_SUBSCRIPT</li>
	 * </ul>
	 * <p>
	 * 它们在样式范围中定义为常数。你甚至可以通过使用“|”来使用几种风格的组合
	 */
	private final int _additionalStyle;
	/** 该范围的起始索引 */
	private int _start;
	/** 该范围的长度 */
	private int _length;

	private final float _fontShrinkRatio;

	/**
	 * Creates a style range with a specified font style.
	 *
	 * @param fontStyle Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 */
	public StyleRange(int fontStyle) {
		this(0, -1, fontStyle, null, 0, null, null);
	}

	/**
	 * Creates a style range with a specified font color.
	 *
	 * @param fontColor the color of the text
	 */
	public StyleRange(Color fontColor) {
		this(0, -1, -1, fontColor, 0, null, null);
	}

	/**
	 * Creates a style range with a specified font style and font color.
	 *
	 * @param fontStyle Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor the color of the text
	 */
	public StyleRange(int fontStyle, Color fontColor) {
		this(0, -1, fontStyle, fontColor, 0, null, null);
	}

	/**
	 * Creates a style range with a specified font style and additional style.
	 *
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to connect two or more styles as long as it makes
	 *                        sense.
	 */
	public StyleRange(int fontStyle, int additionalStyle) {
		this(0, -1, fontStyle, null, additionalStyle, null, null);
	}

	/**
	 * Creates a style range with a specified font style and additional style.
	 *
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 * @param fontShrinkRatio the ratio that regular font size divides by subscript or superscript font size.
	 */
	public StyleRange(int fontStyle, int additionalStyle, float fontShrinkRatio) {
		this(0, -1, fontStyle, null, additionalStyle, null, null, fontShrinkRatio);
	}

	/**
	 * Creates a style range with a specified font style and a range.
	 *
	 * @param start     the start index of the range in a string
	 * @param length    the length of the range.
	 * @param fontStyle Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 */
	public StyleRange(int start, int length, int fontStyle) {
		this(start, length, fontStyle, null, 0, null, null);
	}

	/**
	 * Creates a style range with a specified font style, font color and a range.
	 *
	 * @param start     the start index of the range in a string
	 * @param length    the length of the range.
	 * @param fontStyle Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor the color of the text.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor) {
		this(start, length, fontStyle, fontColor, 0, null, null);
	}

	/**
	 * Creates a style range with a specified font color and a range.
	 *
	 * @param start     the start index of the range in a string
	 * @param length    the length of the range.
	 * @param fontColor the color of the text.
	 */
	public StyleRange(int start, int length, Color fontColor) {
		this(start, length, Font.PLAIN, fontColor, 0, null, null);
	}

	/**
	 * Creates a style range with a specified font style, additional style and a range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 */
	public StyleRange(int start, int length, int fontStyle, int additionalStyle) {
		this(start, length, fontStyle, null, additionalStyle, null, null);
	}

	/**
	 * Creates a style range with a specified font style, additional style and a range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 * @param fontShrinkRatio the ratio that regular font size divides by subscript or superscript font size.
	 */
	public StyleRange(int start, int length, int fontStyle, int additionalStyle, float fontShrinkRatio) {
		this(start, length, fontStyle, null, additionalStyle, null, null, fontShrinkRatio);
	}

	/**
	 * Creates a style range with a specified font style, font color, and additional style.
	 *
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 */
	public StyleRange(int fontStyle, Color fontColor, int additionalStyle, Color lineColor) {
		this(0, -1, fontStyle, fontColor, additionalStyle, lineColor, null);
	}

	/**
	 * Creates a style range with a specified font style, font color, and additional style.
	 *
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param backgroundColor the background color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 */
	public StyleRange(int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor) {
		this(0, -1, fontStyle, fontColor, backgroundColor, additionalStyle, lineColor, null);
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style and a range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor, int additionalStyle) {
		this(start, length, fontStyle, fontColor, additionalStyle, null, null);
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style and a range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param backgroundColor the background color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle) {
		this(start, length, fontStyle, fontColor, backgroundColor, additionalStyle, null, null);
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style, and line color.
	 *
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 * @param lineColor       the color of the line.
	 */
	public StyleRange(int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke) {
		this(0, -1, fontStyle, fontColor, additionalStyle, lineColor, lineStroke);
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style, line color and a range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 * @param lineColor       the color of the line.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor, int additionalStyle, Color lineColor) {
		this(start, length, fontStyle, fontColor, additionalStyle, lineColor, null);
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style, line color and a range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param backgroundColor the background color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 * @param lineColor       the color of the line.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor) {
		this(start, length, fontStyle, fontColor, backgroundColor, additionalStyle, lineColor, null);
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style, line color, line stroke and a
	 * range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 * @param lineColor       the color of the line.
	 * @param lineStroke      the stroke of the line.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke) {
		this(start, length, fontStyle, fontColor, additionalStyle, lineColor, lineStroke, 1.5f);
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style, line color, line stroke and a
	 * range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param backgroundColor the background color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 * @param lineColor       the color of the line.
	 * @param lineStroke      the stroke of the line.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor, Stroke lineStroke) {
		this(start, length, fontStyle, fontColor, backgroundColor, additionalStyle, lineColor, lineStroke, 1.5f);
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style, line color, line stroke and a
	 * range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use any | to concat two or more styles as long as it makes
	 *                        sense.
	 * @param lineColor       the color of the line.
	 * @param lineStroke      the stroke of the line.
	 * @param fontShrinkRatio the ratio that regular font size divides by subscript or superscript font size.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke, float fontShrinkRatio) {
		this(start, length, fontStyle, fontColor, null, additionalStyle, lineColor, lineStroke, fontShrinkRatio);
	}

	/**
	 * Creates a style range exactly the same with the existing range.
	 *
	 * @param range the old range
	 * @since 3.2.1
	 */
	public StyleRange(StyleRange range) {
		this(range.getStart(), range.getLength(), range.getFontStyle(), range.getFontColor(), range.getBackgroundColor(), range.getAdditionalStyle(), range.getLineColor(), range.getLineStroke(), range.getFontShrinkRatio());
	}

	/**
	 * Creates a style range with a specified font style, font color, additional style, line color, line stroke and a
	 * range.
	 *
	 * @param start           the start index of the range in a string
	 * @param length          the length of the range.
	 * @param fontStyle       Valid values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 * @param fontColor       the color of the text.
	 * @param backgroundColor the background color of the text.
	 * @param additionalStyle Valid additional styles are defined as constants in {@link StyleRange}. The names begin
	 *                        with STYLE_. You can also use bitwise OR "|" to concat any two or more styles as long as
	 *                        it makes sense.
	 * @param lineColor       the color of the line.
	 * @param lineStroke      the stroke of the line.
	 * @param fontShrinkRatio the ratio that regular font size divides by subscript or superscript font size.
	 */
	public StyleRange(int start, int length, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor, Stroke lineStroke, float fontShrinkRatio) {
		if (length == 0) {
			throw new IllegalArgumentException("The length of StyleRange cannot be 0.");
		}

		_start = start;
		_length = length;
		_fontColor = fontColor;
		_fontStyle = fontStyle;
		_backgroundColor = backgroundColor;
		_lineColor = lineColor;
		_lineStroke = lineStroke;
		_additionalStyle = additionalStyle;
		_fontShrinkRatio = fontShrinkRatio;
	}

	/**
	 * Gets the start index of the range.
	 *
	 * @return the start index of the range.
	 */
	public int getStart() {
		return _start;
	}

	/**
	 * Sets the start index of the range.
	 *
	 * @param start the start index of the range
	 */
	public void setStart(int start) {
		_start = start;
	}

	/**
	 * Gets the length of the range.
	 *
	 * @return the length of the range.
	 */
	public int getLength() {
		return _length;
	}

	/**
	 * Sets the length of the range.
	 *
	 * @param length the length of the range
	 */
	public void setLength(int length) {
		_length = length;
	}

	/**
	 * Gets the font style. Possible values are Font.PLAIN, Font.ITALIC, Font.BOLD or Font.BOLD | Font.ITALIC.
	 *
	 * @return the font style.
	 */
	public int getFontStyle() {
		return _fontStyle;
	}

	/**
	 * Gets the font color.
	 *
	 * @return the font color.
	 */
	public Color getFontColor() {
		return _fontColor;
	}

	/**
	 * Gets the background color.
	 *
	 * @return the background color.
	 */
	public Color getBackgroundColor() {
		return _backgroundColor;
	}

	/**
	 * Gets the additional style. Possible additional styles are defined as constants in {@link StyleRange}. The names
	 * begin with STYLE_. The value could also be two or more styles concatenated by | as long as it makes sense. It
	 * could be more convenient to use methods {@link #isStrikethrough()}, {@link #isDoublestrikethrough()}, {@link
	 * #isDotted()}, {@link #isWaved()}, {@link #isUnderlined()}, {@link #isSubscript()}, {@link #isSuperscript()} to
	 * see what's the additional style.
	 *
	 * @return the additional style.
	 */
	public int getAdditionalStyle() {
		return _additionalStyle;
	}

	/**
	 * Gets the line color.
	 *
	 * @return the line color.
	 */
	public Color getLineColor() {
		return _lineColor;
	}

	/**
	 * Gets the line stroke.
	 *
	 * @return the line stroke.
	 */
	public Stroke getLineStroke() {
		return _lineStroke;
	}

	/**
	 * Checks if the text has strike through style.
	 *
	 * @return true if the text has strike through style.
	 */
	public boolean isStrikethrough() {
		return (_additionalStyle & STYLE_STRIKE_THROUGH) != 0;
	}

	/**
	 * Checks if the text has double strike through style.
	 *
	 * @return true if the text has double strike through style.
	 */
	public boolean isDoublestrikethrough() {
		return (_additionalStyle & STYLE_DOUBLE_STRIKE_THROUGH) != 0;
	}

	/**
	 * Checks if the line has waved style.
	 *
	 * @return true if the line has waved style.
	 */
	public boolean isWaved() {
		return (_additionalStyle & STYLE_WAVED) != 0;
	}

	/**
	 * Checks if the text has underlined style.
	 *
	 * @return true if the text has underlined style.
	 */
	public boolean isUnderlined() {
		return (_additionalStyle & STYLE_UNDERLINED) != 0;
	}

	/**
	 * Checks if the line has dotted style.
	 *
	 * @return true if the line has dotted style.
	 */
	public boolean isDotted() {
		return (_additionalStyle & STYLE_DOTTED) != 0;
	}

	/**
	 * Checks if the text is superscript.
	 *
	 * @return true if the text is superscript.
	 */
	public boolean isSuperscript() {
		return (_additionalStyle & STYLE_SUPERSCRIPT) != 0;
	}

	/**
	 * Checks if the text is subscript.
	 *
	 * @return true if the text is subscript.
	 */
	public boolean isSubscript() {
		return (_additionalStyle & STYLE_SUBSCRIPT) != 0;
	}

	/**
	 * Gets the font shrink ratio for superscript and subscript.
	 *
	 * @return the shrink ratio.
	 */
	public float getFontShrinkRatio() {
		return _fontShrinkRatio;
	}

	@Override
	public String toString() {
		return "StyleRange{" +
				"fontStyle=" + _fontStyle +
				", fontColor=" + _fontColor +
				", backgroundColor=" + _backgroundColor +
				", lineColor=" + _lineColor +
				", lineStroke=" + _lineStroke +
				", additionalStyle=" + _additionalStyle +
				", start=" + _start +
				", length=" + _length +
				", fontShrinkRatio=" + _fontShrinkRatio +
				'}';
	}
}
