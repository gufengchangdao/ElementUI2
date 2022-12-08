/*
 * @(#)StyledLabelBuilder.java 9/7/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.label;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * StyledLabelBuilder是一种快速定义 StyledLabel 的方法。它提供了两种方法来处理 StyleLabel 的创建和修改。
 * <p>
 * 第一种是将其用作构建器（因此得名）。如果您想创建具有特定格式和部分通用内容的 StyledLabel，则首选这种方式。例子：
 * <pre><code>
 * StyledLabel label = new StyledLabelBuilder()
 * 	.add(file.getName())
 * 	.add(" (", Font.BOLD)
 * 	.add(file.getPath(), "italic") // using annotation style - see section two for information about annotations
 * 	.add(")", Font.BOLD)
 * 	.createLabel();
 * 	</code></pre>
 * 此代码将用于创建带有某些样式的标签，如“something.txt (/temp/something.txt)”（大括号为粗体，路径为斜体）。如果您发现自己经常在这样的
 * 标签中重复使用特定样式，您可能会考虑为其创建一个样式。这可以在register方法的帮助下完成。例如，上面的代码可以这样重写（尽管它只有在用于创
 * 建更长的样式时才有用）：
 * <pre><code>
 * StyledLabelBuilder builder = new StyledLabelBuilder()
 * 	.register("OPERATOR", Font.BOLD, new Color(0x000052)) // use parameters
 * 	.register("PATH", "italic, f:#0000CD"); // or style annotations
 * StyledLabel label = builder
 * 	.add(file.getName())
 * 	.add(" (", "OPERATOR")
 * 	.add(file.getPath(), "PATH,underlined") // use a style + style annotation
 * 	.add(")", "OPERATOR")
 * 	.createLabel();
 * 	</code></pre>
 * 请注意，我们这次使用了不同的字体颜色。一旦您想要修改一组特定的文本部分或当您的样式开始变得更加复杂时，它就会得到回报。如果您想使用这些样式，
 * {@link #clear()}方法非常有用。不必每次都重新创建一个新的构建器，您可以使用 clear 方法清除文本的内部缓冲区，而无需删除先前定义的样式。
 * <p>
 * 让我们举个例子（我们将重用上面的代码！）：
 * <pre><code>
 * builder.clear();
 * builder
 * 	.add(file.getName())
 * 	.add(" (", "OPERATOR")
 * 	.add(file.getPath(), "PATH")
 * 	.add(")", "OPERATOR")
 *  .configure(label);
 * </code></pre>
 * <p>
 * 请注意，必要时您需要对文本字符串中的“:”进行转义。例如，“{00:00:00:BOLD}”需要更改为“{00\\:00\\:00:BOLD}。
 * <p>
 * 每个{@link #add} 和 {@link #register}都与直接使用相应的 StyleRange-constructor 相同（除此您不必关心它的开始和长度）。
 * <p>
 * 第二种甚至更高级的使用此类的方法是结合带注释的字符串。使用静态setStyledText或{@link #createStyledLabel}方法，您可以仅从字符串创建完全样式化的
 * 标签。如果您需要字符串是可配置的或特定于区域设置的，这是理想的选择。用法比构建器方法更简单：
 * <pre><code>
 *  StyledLabel label = StyledLabelBuilder.createStyledLabel("I'm your {first:bold} styled {label:italic}!");
 * </code></pre>
 * <p>
 * 在上面的示例中，生成的标签将有一个粗体“first”和一个斜体“label”。每个注解都以“{”开始，以“}”结束。您想要设置相应样式的文本通过“:”与其注
 * 释分开。如果您的文本本身需要包含“:”，则需要使用“\”字符对其进行转义。不应该开始注释的“{”也是如此。您根本不需要转义“}”。如果它在带注释的字
 * 符串中使用，它将被忽略。它仅在注释分隔符（“:”）之后计算。有多个注释可用。每个注释都提供了一个由一个或两个字符组成的快捷方式。例如：我们在
 * 上面的示例中使用了“粗体”和“斜体”，但我们可以使用“b”和“i”来代替。也可以通过用“,”分隔多个样式来组合它们。例如：
 * {This text is bold, italic and blue:b,i,f:blue}
 * 除了写“b,i”你也可以写“bi”或“bolditalic”。这个例子给我们带来了颜色。字体颜色必须以“f”或“font”开头，线条颜色必须以“l”或“line”开头，背
 * 景颜色必须以“b”或“background”开头。有很多方法可以指定颜色。您可以使用它的 HTML 名称（如我在上面的示例中所做的那样）或以下任何名称：
 * f:(0,0,255) f:#00F l:#0000FF l:0x0000FF
 * “#00F”符号就像它在CSS。就好像你写了“#0000FF”一样。您可以使用静态{@link #getColorNamesMap()}方法获取和修改解析器正在使用的颜色名称映射。
 * <p>
 * 你在上面看到了一些样式。这是样式及其快捷方式的完整列表。
 * <p>
 * 字体样式
 * <ul>
 *     <li>plain or p
 *     <li>bold or b
 *     <li>italic or i
 *     <li>bolditalic or bi<b>Additional styles</b>
 *     <li>strike or s
 *     <li>doublestrike or ds
 *     <li>waved or w
 *     <li>underlined or u
 *     <li>dotted or d
 *     <li>superscript or sp
 *     <li>subscript or sb
 * </ul>
 *
 * <b>Global flags</b>: 您可以通过在字符串末尾使用“@”来启用全局标志。
 * rows or row or r: 它最多可以带三个参数分别为: 第一个是首选行，第二个是最小行，最后一个是最大行。
 *
 * @author Patrick Gotthardt
 */
public class StyledLabelBuilder {
	private final StringBuilder builder;
	private final List<StyleRange> ranges;
	private int start;
	private final Map<String, StyleRange> styles;

	public StyledLabelBuilder() {
		builder = new StringBuilder();
		ranges = new ArrayList<>();
		styles = new HashMap<>();
		start = 0;
	}

	public void clear() {
		builder.delete(0, builder.length());
		ranges.clear();
		start = 0;
	}

	public StyledLabelBuilder register(String text, Color fontColor) {
		styles.put(text, new StyleRange(fontColor));
		return this;
	}

	public StyledLabelBuilder register(String text, int fontStyle) {
		styles.put(text, new StyleRange(fontStyle));
		return this;
	}

	public StyledLabelBuilder register(String text, int fontStyle, Color fontColor) {
		styles.put(text, new StyleRange(fontStyle, fontColor));
		return this;
	}

	public StyledLabelBuilder register(String text, int fontStyle, Color fontColor, int additionalStyle) {
		styles.put(text, new StyleRange(start, text.length(), fontStyle, fontColor, additionalStyle));
		return this;
	}

	public StyledLabelBuilder register(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor) {
		styles.put(text, new StyleRange(start, text.length(), fontStyle, fontColor, additionalStyle, lineColor));
		return this;
	}

	public StyledLabelBuilder register(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke) {
		styles.put(text, new StyleRange(start, text.length(), fontStyle, fontColor, additionalStyle, lineColor, lineStroke));
		return this;
	}

	public StyledLabelBuilder register(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke, float fontShrinkRatio) {
		styles.put(text, new StyleRange(start, text.length(), fontStyle, fontColor, additionalStyle, lineColor, lineStroke, fontShrinkRatio));
		return this;
	}

	public StyledLabelBuilder register(String text, int fontStyle, int additionalStyle) {
		styles.put(text, new StyleRange(start, text.length(), fontStyle, additionalStyle));
		return this;
	}

	public StyledLabelBuilder register(String text, int fontStyle, int additionalStyle, float fontShrinkRatio) {
		styles.put(text, new StyleRange(start, text.length(), fontStyle, additionalStyle, fontShrinkRatio));
		return this;
	}

	public StyledLabelBuilder register(String text, String format) {
		ParsedStyleResult result = parseStyleAnnotation(format.toCharArray(), 0, this);
		styles.put(text, new StyleRange(result.fontStyle, result.fontColor, result.backgroundColor,
				result.additionalStyle, result.lineColor));
		return this;
	}

	public StyledLabelBuilder add(String text) {
		builder.append(text);
		start += text.length();
		return this;
	}

	public StyledLabelBuilder add(String text, Color fontColor) {
		ranges.add(new StyleRange(start, text.length(), fontColor));
		return add(text);
	}

	public StyledLabelBuilder add(String text, int fontStyle) {
		ranges.add(new StyleRange(start, text.length(), fontStyle));
		return add(text);
	}

	public StyledLabelBuilder add(String text, int fontStyle, Color fontColor) {
		ranges.add(new StyleRange(start, text.length(), fontStyle, fontColor));
		return add(text);
	}

	public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, int additionalStyle) {
		ranges.add(new StyleRange(start, text.length(), fontStyle, fontColor, additionalStyle));
		return add(text);
	}

	public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor) {
		ranges.add(new StyleRange(start, text.length(), fontStyle, fontColor, additionalStyle, lineColor));
		return add(text);
	}

	public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor) {
		ranges.add(new StyleRange(start, text.length(), fontStyle, fontColor, backgroundColor, additionalStyle, lineColor));
		return add(text);
	}

	public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, Color backgroundColor, int additionalStyle, Color lineColor, Stroke lineStroke) {
		ranges.add(new StyleRange(start, text.length(), fontStyle, fontColor, backgroundColor, additionalStyle, lineColor, lineStroke));
		return add(text);
	}

	public StyledLabelBuilder add(String text, int fontStyle, Color fontColor, int additionalStyle, Color lineColor, Stroke lineStroke, float fontShrinkRatio) {
		ranges.add(new StyleRange(start, text.length(), fontStyle, fontColor, additionalStyle, lineColor, lineStroke, fontShrinkRatio));
		return add(text);
	}

	public StyledLabelBuilder add(String text, String style) {
		StyleRange range = styles.get(style);
		// not a stored style, thus it might be an annotation
		if (range == null) {
			ParsedStyleResult result = parseStyleAnnotation(style.toCharArray(), 0, this);
			return add(text, result.fontStyle, result.fontColor, result.backgroundColor, result.additionalStyle, result.lineColor);
		}
		return add(text, range.getFontStyle(), range.getFontColor(), range.getAdditionalStyle(), range.getLineColor(), range.getLineStroke(), range.getFontShrinkRatio());
	}

	public StyledLabelBuilder add(String text, int fontStyle, int additionalStyle) {
		ranges.add(new StyleRange(start, text.length(), fontStyle, additionalStyle));
		return add(text);
	}

	public StyledLabelBuilder add(String text, int fontStyle, int additionalStyle, float fontShrinkRatio) {
		ranges.add(new StyleRange(start, text.length(), fontStyle, additionalStyle, fontShrinkRatio));
		return add(text);
	}

	public StyledLabel configure(StyledLabel label, String style) {
		StyledLabelBuilder.setStyledText(label, style, this);
		return label;
	}

	public StyledLabel configure(StyledLabel label) {
		label.setText(builder.toString());
		for (StyleRange range : ranges) {
			label.addStyleRange(range);
		}
		return label;
	}

	public StyledLabel createLabel() {
		return configure(new StyledLabel());
	}

	// complex part
	public static StyledLabel createStyledLabel(String text) {
		StyledLabel label = new StyledLabel();
		setStyledText(label, text);
		return label;
	}

	/**
	 * Before your call this method, you need call {link@ #parseToVoidStyledTextConfusion(String)} to make sure the text
	 * will not contain confusion "\" or "{"
	 *
	 * @param label the styledLabel to be set with the text
	 * @param text  the styled text
	 */
	public static void setStyledText(StyledLabel label, String text) {
		setStyledText(label, text.toCharArray());
	}

	private static void setStyledText(StyledLabel label, String text, StyledLabelBuilder builder) {
		setStyledText(label, text.toCharArray(), builder);
	}

	/**
	 * Before your call this method, you need call {link@ #parseToVoidStyledTextConfusion(String)} to make sure the text
	 * will not contain confusion "\" or "{"
	 *
	 * @param label the styledLabel to be set with the text
	 * @param text  the styled text
	 */
	public static void setStyledText(StyledLabel label, char[] text) {
		setStyledText(label, text, null);
	}

	private static void setStyledText(StyledLabel label, char[] text, StyledLabelBuilder builder) {
		StringBuilder labelText = new StringBuilder(text.length);
		boolean escaped = false;
		label.clearStyleRanges();
		label.setLineWrap(false);
		int endOfText = text.length;
		for (int j = text.length - 1; j >= 0; j--) {
			if (text[j] == '@') {
				if (isGlobalConfiguration(label, text, j + 1)) {
					endOfText = j;
				}
				break;
			}
		}
		for (int i = 0; i < endOfText; i++) {
			if (escaped) {
				labelText.append(text[i]);
				escaped = false;
				continue;
			}
			switch (text[i]) {
				case '{' -> {
					ParsedStyleResult result = parseStylePart(text, i + 1, builder);
					if (result == null) {
						labelText.append(text[i]);
						continue;
					}
					int realIndex = labelText.length();
					labelText.append(result.text);
					if (result.text.length() > 0) {
						label.addStyleRange(new StyleRange(
								realIndex, result.text.length(),
								result.fontStyle, result.fontColor, result.backgroundColor,
								result.additionalStyle, result.lineColor));
					}
					i = Math.max(i, result.endOffset);
				}
				case '\\' -> escaped = true;
				default -> labelText.append(text[i]);
			}
		}
		label.setText(labelText.toString());
	}

	private static boolean isGlobalConfiguration(StyledLabel label, char[] text, int offset) {
		String globalString = new String(text, offset, text.length - offset);
		String[] subStringsLevel0 = globalString.split(",");
		if (subStringsLevel0.length == 0 || subStringsLevel0[0] == null) {
			return false;
		}
		int defaultRows = 1;
		int maxRows = 0;
		int minRows = 0;
		int preferredWidth = 0;
		for (String subStringLevel0 : subStringsLevel0) {
			String[] subStrings = subStringLevel0.split(":");
			if (subStrings.length == 0 || subStrings[0] == null) {
				return false;
			}
			String property = subStrings[0].trim().toLowerCase();
			if ("rows".equals(property) || "row".equals(property) || "r".equals(property)) {
				if (subStrings.length > 4) {
					return false;
				}
				if (subStrings.length >= 2 && subStrings[1].trim().length() > 0) {
					try {
						defaultRows = Integer.parseInt(subStrings[1]);
					} catch (NumberFormatException e) {
						return false;
					}
				}
				if (subStrings.length >= 3 && subStrings[2].trim().length() > 0) {
					try {
						minRows = Integer.parseInt(subStrings[2]);
					} catch (NumberFormatException e) {
						return false;
					}
					if (minRows > defaultRows || minRows < 0) {
						if (subStrings[1].trim().length() > 0) {
							minRows = 0;
						} else if (minRows > defaultRows) {
							defaultRows = minRows;
						} else {
							minRows = 0;
						}
					}
				}
				if (subStrings.length == 4 && subStrings[3].trim().length() > 0) {
					try {
						maxRows = Integer.parseInt(subStrings[3]);
					} catch (NumberFormatException e) {
						return false;
					}
					if (maxRows < defaultRows || maxRows < 0) {
						maxRows = 0;
					}
				}
			} else if ("w".equals(property) || "width".equals(property) || "preferredwidth".equals(property)) {
				if (subStrings.length != 2) {
					return false;
				}
				if (subStrings[1].trim().length() > 0) {
					try {
						preferredWidth = Integer.parseInt(subStrings[1]);
					} catch (NumberFormatException e) {
						return false;
					}
				}
			} else {
				return false;
			}
		}
		label.setLineWrap(true);
		label.setRows(defaultRows);
		label.setMaxRows(maxRows);
		label.setMinRows(minRows);
		label.setPreferredWidth(preferredWidth);
		if (defaultRows == 1 && maxRows == 1 && minRows == 1) {
			label.setLineWrap(false);
		}
		return true;
	}

	/**
	 * This method need to be invoked to format your string before you invoke {@link #setStyledText(StyledLabel,
	 * String)} or {@link #setStyledText(StyledLabel, char[])}
	 *
	 * @param originalString the original string.
	 * @return a parsed string with "\" replaced by "\\" and "{" replaced by "\{".
	 */
	public static String parseToVoidStyledTextConfusion(String originalString) {
		String destString = originalString.replaceAll("\\\\", "\\\\\\\\");
		destString = destString.replaceAll("\\{", "\\\\{");
		return destString;
	}

	private static ParsedStyleResult parseStylePart(char[] text, int start, StyledLabelBuilder builder) {
		ParsedStyleResult result = new ParsedStyleResult();
		int findIndex;
		// find end of text first
		findIndex = findNext(text, ':', start);
		int indexMatchingBracket = findMatchingBracket(text, start);
		if (findIndex < 0 || findIndex > indexMatchingBracket) {
			return null;
		}
		result.text = createTrimmedString(text, start, findIndex - 1);
		return parseStyleAnnotation(text, findIndex + 1, builder, result);
	}

	private static int findMatchingBracket(char[] text, int offset) {
		if (text.length == 0)
			return -1;
		if (offset >= text.length)
			return -1;

		// Count is 1 initially because we have already
		// `found' one opening bracket
		int count = 1;

		// Number of characters to check
		int len = text.length - offset;

		boolean escaped = false;
		// Scan forwards
		for (int i = 0; i < len; i++, offset++) {
			if (escaped) {
				escaped = false;
				continue;
			}
			// If text[i] == c, we have found another
			// opening bracket, therefore we will need
			// two closing brackets to complete the
			// match.
			char x = text[offset];
			if (x == '\\') {
				escaped = true;
			} else if (x == '{') {
				count++;
			}

			// If text[i] == cprime, we have found an
			// closing bracket, so we return i if
			// --count == 0
			else if (x == '}') {
				if (--count == 0) {
					return offset;
				}
			}
		}

		// Nothing found
		return -1;
	}

	private static ParsedStyleResult parseStyleAnnotation(char[] text, int start, StyledLabelBuilder builder) {
		ParsedStyleResult result = new ParsedStyleResult();
		return parseStyleAnnotation(text, start, builder, result);
	}

	private static ParsedStyleResult parseStyleAnnotation(char[] text, int findIndex, StyledLabelBuilder builder, ParsedStyleResult result) {
		int i = findIndex;
		char[] importantChars = {',', '}'};
		boolean endOfTag = false;
		while (i < text.length && !endOfTag) {
			findIndex = findNextOf(text, importantChars, i);
			String style;
			if (findIndex == -1 || text[findIndex] == '}') {
				endOfTag = true;
			}
			style = createTrimmedString(text, i, findIndex == -1 ? text.length - 1 : findIndex - 1);
			// start with colors first - they're easiest to guess
			int colonIndex = style.indexOf(':');
			if (colonIndex != -1) {
				String color = style.substring(colonIndex + 1);
				// the (r,g,b)-construct allows "," thus we'll have to handle it here!
				if (color.length() > 1) {
					if (color.charAt(0) == '(') {
						findIndex = findNext(text, ')', i + colonIndex + 1);
						style = createTrimmedString(text, i, findIndex + 1);
						color = style.substring(colonIndex + 1);
						// we need to do some specific checking here
						if (text[findIndex + 1] == '}') {
							endOfTag = true;
						}
						// in any case: the cursor needs to be moved forward by one
						findIndex++;
					}
					if (style.charAt(0) == 'f') {
						result.fontColor = toColor(color);
					} else if (style.charAt(0) == 'b') {
						result.backgroundColor = toColor(color);
					} else {
						result.lineColor = toColor(color);
					}
				}
			} else {
				// no color, now it's getting though
				if (style.equals("plain") || style.equals("p")) {
					result.fontStyle = Font.PLAIN;

				} else if (style.equals("bold") || style.equals("b")) {
					result.fontStyle = Font.BOLD;

				} else if (style.equals("italic") || style.equals("i")) {
					result.fontStyle = Font.ITALIC;

				} else if (style.equals("bolditalic") || style.equals("bi")) {
					result.fontStyle = Font.ITALIC + Font.BOLD;

				} else if (style.equals("strike") || style.equals("s")) {
					result.additionalStyle |= StyleRange.STYLE_STRIKE_THROUGH;

				} else if (style.equals("doublestrike") || style.equals("ds")) {
					result.additionalStyle |= StyleRange.STYLE_DOUBLE_STRIKE_THROUGH;

				} else if (style.equals("waved") || style.equals("w")) {
					result.additionalStyle |= StyleRange.STYLE_WAVED;

				} else if (style.equals("underlined") || style.equals("u")) {
					result.additionalStyle |= StyleRange.STYLE_UNDERLINED;

				} else if (style.equals("dotted") || style.equals("d")) {
					result.additionalStyle |= StyleRange.STYLE_DOTTED;

				} else if (style.equals("superscript") || style.equals("sp")) {
					result.additionalStyle |= StyleRange.STYLE_SUPERSCRIPT;

				} else if (style.equals("subscript") || style.equals("sb")) {
					result.additionalStyle |= StyleRange.STYLE_SUBSCRIPT;
				} else if (builder != null && builder.styles.containsKey(style)) {
					StyleRange range = builder.styles.get(style);
					result.fontStyle = range.getFontStyle();
					result.fontColor = range.getFontColor();
					result.backgroundColor = range.getBackgroundColor();
					result.additionalStyle = range.getAdditionalStyle();
					result.lineColor = range.getLineColor();
				} else if (style.length() > 0) {
//                    System.err.println("Unknown style '" + style + "'");
				}
			}
			i = findIndex + 1;
		}
		result.endOffset = i - 1;
		// done, return
		return result;
	}

	/**
	 * Can be: (255, 0, 0) #FF0000 #F00 0xFF0000 red
	 */
	private static Color toColor(String str) {
		switch (str.charAt(0)) {
			case '(':
				int red, green, blue;
				int index;

				red = nextColorInt(str, 1);

				index = str.indexOf(',');
				green = nextColorInt(str, index + 1);

				index = str.indexOf(',', index + 1);
				blue = nextColorInt(str, index + 1);

				return new Color(red, green, blue);
			case '#':
				// Shorthand?
				if (str.length() == 4) {
					return new Color(
							getShorthandValue(str.charAt(1)),
							getShorthandValue(str.charAt(2)),
							getShorthandValue(str.charAt(3))
					);
				} else {
					return new Color(Integer.parseInt(str.substring(1), 16));
				}
			case '0':
				return new Color(Integer.parseInt(str.substring(2), 16));
			default:
				return colorNamesMap.get(str);
		}
	}

	private static int nextColorInt(String str, int index) {
		// start with adjusting the start index
		while (index < str.length()) {
			char c = str.charAt(index);
			// a digit?
			if ('0' <= c && c <= '9') {
				break;
			} else {
				index++;
			}
		}
		// that's only the maximum limit!
		int colorLength = index;
		for (; colorLength < index + 3; colorLength++) {
			char c = str.charAt(colorLength);
			// not a digit?
			if (c < '0' || '9' < c) {
				break;
			}
		}
		return Integer.parseInt(str.substring(index, colorLength));
	}

	private static int getShorthandValue(char c) {
		c = Character.toUpperCase(c);
		if ('A' <= c && c <= 'F') {
			return colorShorthandTable[c - 'A' + 10];
		}
		return colorShorthandTable[c - '0'];
	}

	private static final int[] colorShorthandTable = {
			0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66,
			0x77, 0x88, 0x99, 0xAA, 0xBB, 0xCC, 0xDD,
			0xEE, 0xFF
	};

	private static final Map<String, Color> colorNamesMap;

	static {
		colorNamesMap = new TreeMap<>();
		colorNamesMap.put("white", new Color(0xFFFFFF));
		colorNamesMap.put("lightGray", new Color(0xC0C0C0));
		colorNamesMap.put("gray", new Color(0x808080));
		colorNamesMap.put("darkGray", new Color(0x404040));
		colorNamesMap.put("black", new Color(0x000000));
		colorNamesMap.put("red", new Color(0xFF0000));
		colorNamesMap.put("pink", new Color(0xFFAFAF));
		colorNamesMap.put("orange", new Color(0xFFC800));
		colorNamesMap.put("yellow", new Color(0xFFFF00));
		colorNamesMap.put("green", new Color(0x00FF00));
		colorNamesMap.put("magenta", new Color(0xFF00FF));
		colorNamesMap.put("cyan", new Color(0x00FFFF));
		colorNamesMap.put("blue", new Color(0x0000FF));
	}

	public static Map<String, Color> getColorNamesMap() {
		return colorNamesMap;
	}

	private static String createTrimmedString(char[] text, int start, int end) {
		for (; start < text.length && (text[start] == ' ' || text[start] == '\t'); start++) ;
		for (; start < end && (text[end] == ' ' || text[end] == '\t'); end--) ;
		// need to remove escape chars
		if (end >= start) {
			StringBuilder b = new StringBuilder(end - start);
			boolean escaped = false;
			for (int i = start; i <= end; i++) {
				if (text[i] == '\\' && !escaped) {
					escaped = true;
				} else {
					b.append(text[i]);
					if (escaped) {
						escaped = false;
					}
				}
			}
			return b.toString();
		} else {
			return "";
		}
	}

	private static int findNextOf(char[] text, char[] chars, int start) {
		boolean escaped = false;
		for (int i = start; i < text.length; i++) {
			if (escaped) {
				escaped = false;
				continue;
			}
			if (text[i] == '\\') {
				escaped = true;
			} else {
				for (char c : chars) {
					if (text[i] == c) {
						return i;
					}
				}
			}
		}
		return -1;
	}

	private static int findNext(char[] text, char c, int start) {
		boolean escaped = false;
		for (int i = start; i < text.length; i++) {
			if (escaped) {
				escaped = false;
				continue;
			}
			if (text[i] == '\\') {
				escaped = true;
			} else if (text[i] == c) {
				return i;
			}
		}
		return -1;
	}

	private static class ParsedStyleResult {
		String text;
		int endOffset;
		int fontStyle = Font.PLAIN;
		Color fontColor = null, lineColor = null, backgroundColor = null;
		int additionalStyle = 0;
	}
}
