package com.element.converter;

import com.element.converter.impl.MonthNameConverter;

public class MonthNameConverterTest {
	public static void main(String[] args) {
		MonthNameConverter converter = new MonthNameConverter();
		converter.setDefaultFormat(MonthNameConverter.LONG_FORMAT);
		for (int i = 0; i < 12; i++) {
			String str = converter.toString(i, null);
			System.out.println(str);
			System.out.println(converter.fromString(str, null));
		}
	}
}