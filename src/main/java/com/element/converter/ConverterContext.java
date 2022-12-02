/*
 * @(#) ConverterContext.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter;

/**
 * ObjectConverter 使用的上下文对象。对于相同的类型，我们可能需要不同的方式来转换它们。
 * 使用此上下文以便用户可以为同一类型注册不同的转换器。
 */
public class ConverterContext extends AbstractContext {
	/**
	 * Default converter context with empty name and no user object.
	 */
	public static ConverterContext DEFAULT_CONTEXT = new ConverterContext("");

	/**
	 * Creates a converter context with a name.
	 *
	 * @param name the name of the converter context
	 */
	public ConverterContext(String name) {
		super(name);
	}

	/**
	 * Creates a converter context with a name and an object.
	 *
	 * @param name   the name of the converter context
	 * @param object the user object. It can be used as any object to pass information along.
	 */
	public ConverterContext(String name, Object object) {
		super(name, object);
	}

	/**
	 * Checks if the context is for an array. By conversion, we put "[]" at the end of the converter context's name if
	 * the context is for an array data type. Please note, this is a conversion only. If developer chooses to not put
	 * "[]" at the end for their own customized context, this method will fail.
	 *
	 * @param context the context.
	 * @return true or false.
	 */
	public static boolean isArrayConverterContext(ConverterContext context) {
		return context != null && context.getName() != null && context.getName().endsWith("[]");
	}

	/**
	 * Gets the converter context which removes the trailing "[]" from the context name.
	 *
	 * @param context the context for an array type.
	 * @return the converter context for the element type of an array.
	 */
	public static ConverterContext getElementConverterContext(ConverterContext context) {
		if (isArrayConverterContext(context)) {
			return new ConverterContext(context.getName().substring(0, context.getName().length() - 2));
		} else {
			return context;
		}
	}

	/**
	 * Gets the converter context which add a trailing "[]" to the context name.
	 *
	 * @param context the context for the element type of an array.
	 * @return the converter context the array of the element type.
	 */
	public static ConverterContext getArrayConverterContext(ConverterContext context) {
		if (!isArrayConverterContext(context)) {
			return new ConverterContext(context.getName() + "[]");
		} else {
			return context;
		}
	}
}
