/*
 * @(#) ColorConverter.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter.impl;


import com.element.converter.ConverterContext;
import com.element.converter.ObjectConverter;

/**
 * 将 Color 转换为 String 并将其转换回来的转换器。
 */
abstract public class ColorConverter extends ObjectConverter {

	/**
	 * ConverterContext for color to convert to RGB string.
	 */
	public static ConverterContext CONTEXT_RGB = ConverterContext.DEFAULT_CONTEXT;

	/**
	 * ConverterContext for color to convert to HEX string.
	 */
	public static ConverterContext CONTEXT_HEX = new ConverterContext("Color.Hex");

	/**
	 * ConverterContext for color to convert to RGB and alpha string.
	 */
	public static ConverterContext CONTEXT_RGBA = new ConverterContext("Color.rgba");


	/**
	 * ConverterContext for color to convert to HEX string.
	 */
	public static ConverterContext CONTEXT_HEX_WITH_ALPHA = new ConverterContext("Color.HexWithAlpha");

	/**
	 * Create a default color converter.
	 */
	public ColorConverter() {
	}
}
