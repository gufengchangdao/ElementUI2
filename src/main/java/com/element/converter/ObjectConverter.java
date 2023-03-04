/*
 * @(#) ObjectConverter.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter;


/**
 * 一个可以将对象转换为字符串以及从字符串转换为对象的抽象类。
 */
public abstract class ObjectConverter<T> {
	/**
	 * 根据当前语言环境从对象转换为字符串。
	 * 如果{@link #supportToString(Object, ConverterContext)}返回常量false，则返回null即可，因为不支持转换该方法一定不会被调用的
	 *
	 * @param object  要转换的对象
	 * @param context 要使用的转换器上下文
	 * @return 字符串，不支持转换返回null
	 */
	public abstract String toString(T object, ConverterContext context);

	/**
	 * 如果它支持 toString 方法。
	 *
	 * @param object  要转换的对象
	 * @param context 要使用的转换器上下文
	 * @return 如果支持 toString 则为真
	 */
	public boolean supportToString(T object, ConverterContext context) {
		return true;
	}

	/**
	 * 从字符串转换为对象。
	 * 如果{@link #supportFromString(String, ConverterContext)}返回常量false，则返回null即可，因为不支持转换该方法一定不会被调用的
	 *
	 * @param string  字符串
	 * @param context 要转换的上下文
	 * @return 从字符串转换的对象，不支持转换返回null
	 */
	public abstract T fromString(String string, ConverterContext context);

	/**
	 * 如果它支持 fromString。
	 *
	 * @param string  字符串
	 * @param context 要转换的上下文
	 * @return 如果支持则为真
	 */
	public boolean supportFromString(String string, ConverterContext context) {
		return true;
	}
}
