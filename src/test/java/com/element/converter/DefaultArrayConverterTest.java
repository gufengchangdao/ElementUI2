package com.element.converter;

import com.element.converter.impl.DefaultArrayConverter;
import com.element.converter.impl.HexColorConverter;

import java.awt.*;

public class DefaultArrayConverterTest {
	public static void main(String[] args) {
		System.out.println(new DefaultArrayConverter(";", int.class).toString(new int[]{2, 3, 2, 4}, null));
		System.out.println(new DefaultArrayConverter(";", int.class).fromString("2;3;2;4", null));

		System.out.println(new DefaultArrayConverter(";", Color.class)
				.toString(new Color[]{Color.RED, Color.YELLOW, Color.GREEN}, HexColorConverter.CONTEXT_HEX));
		System.out.println(new DefaultArrayConverter(";", Color.class)
				.fromString("#FF0000;#FFFF00;#00FF00", HexColorConverter.CONTEXT_HEX));
		System.out.println(new DefaultArrayConverter(";", Object.class)
				.fromString("#FF0000;#FFFF00;#00FF00", null));
	}
}