/*
 * @(#) ObjectConverterManager.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter;

import com.element.converter.impl.*;
import com.element.swing.RegistrationListener;
import com.element.util.TypeUtil;

import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * ObjectConverterManager是使用数据类型和可选的 ConverterContext 注册 ObjectConverter 的中心空间。当您需要针对同一数据类型的多个不
 * 同转换器时，ConverterContext 是一个开关。如果您只对特定数据类型使用一个 ObjectConverter，则可以使用 null 作为 ConverterContext。
 * 也就是说要是使用的转换器类型由类对象和上下文共同决定。
 * <p>
 * ObjectConverterManager在 项目的许多地方都有使用，尤其是在 Grids 中，其中ContextSensitiveTableModel为表模型中的每个单元格添加了
 * getCellClassAt（数据类型）和 getConverterContextAt。我们使用这两个值作为从ObjectConverterManager查找ObjectConverter的键。
 */
public class ObjectConverterManager {
	private static final CacheMap<ObjectConverter, ConverterContext> _cache =
			new CacheMap<>(ConverterContext.DEFAULT_CONTEXT);

	private static final ObjectConverter _defaultConverter = new DefaultObjectConverter();

	private static boolean _inited = false;
	private static boolean _initing = false;
	private static boolean _autoInit = true;

	/**
	 * Registers a converter with the type specified as class and a converter context specified as context.
	 *
	 * @param clazz     the type of which the converter will registered. You can use superclass if several subclasses
	 *                  share the same superclass and the same converter can be used. If you register converters for
	 *                  both subclass and superclass, the one registered with the subclass will be in effect.
	 * @param converter the converter to be registered
	 * @param context   the converter context.
	 */
	public static <T> void registerConverter(Class<T> clazz, ObjectConverter<T> converter, ConverterContext context) {
		if (clazz == null) {
			throw new IllegalArgumentException("Parameter class cannot be null");
		}

		if (context == null) {
			context = ConverterContext.DEFAULT_CONTEXT;
		}

		if (_autoInit && !_inited && !_initing) {
			initDefaultConverter();
		}

		_cache.register(clazz, converter, context);
	}

	/**
	 * Registers as the default converter with type specified as clazz.
	 *
	 * @param clazz     the type of which the converter will registered. You can use superclass if several subclasses
	 *                  share the same superclass and the same converter can be used. If you register converters for
	 *                  both subclass and superclass, the one registered with the subclass will be in effect.
	 * @param converter the converter to be registered
	 */
	public static <T> void registerConverter(Class<T> clazz, ObjectConverter<T> converter) {
		registerConverter(clazz, converter, ConverterContext.DEFAULT_CONTEXT);
	}

	/**
	 * Unregisters converter associated with clazz and context.
	 *
	 * @param clazz   the type of which the converter will be unregistered.
	 * @param context the converter context.
	 */
	public static void unregisterConverter(Class<?> clazz, ConverterContext context) {
		if (context == null) {
			context = ConverterContext.DEFAULT_CONTEXT;
		}

		if (isAutoInit() && !_inited && !_initing) {
			initDefaultConverter();
		}

		_cache.unregister(clazz, context);
	}

	/**
	 * Unregisters converter associated with the class.
	 *
	 * @param clazz the type of which the converter will be unregistered.
	 */
	public static void unregisterConverter(Class<?> clazz) {
		unregisterConverter(clazz, ConverterContext.DEFAULT_CONTEXT);
	}

	/**
	 * Unregisters all converters associated with the class.
	 *
	 * @param clazz the type of which the converter will be unregistered.
	 */
	public static void unregisterAllConverters(Class<?> clazz) {
		_cache.remove(clazz);
	}

	/**
	 * Unregisters all the converters which registered before.
	 */
	public static void unregisterAllConverters() {
		_cache.clear();
	}

	/**
	 * Gets the registered converter associated with class and context.
	 *
	 * @param clazz   the type of which the converter will be registered.
	 * @param context the converter context.
	 * @return the registered converter.
	 */
	public static<T> ObjectConverter<T> getConverter(Class<T> clazz, ConverterContext context) {
		if (isAutoInit() && !_inited && !_initing) {
			initDefaultConverter();
		}

		if (context == null) {
			context = ConverterContext.DEFAULT_CONTEXT;
		}

		ObjectConverter<T> converter = _cache.getRegisteredObject(clazz, context);
		if (converter != null) {
			return converter;
		} else {
			if (clazz != null && clazz.isArray()) {
				DefaultArrayConverter defaultArrayConverter = new DefaultArrayConverter("; ", clazz.getComponentType());
				registerConverter(clazz, defaultArrayConverter);
				return defaultArrayConverter;
			}
			return _defaultConverter;
		}
	}

	/**
	 * Gets the converter associated with the type.
	 *
	 * @param clazz type
	 * @return the converter
	 */
	public static ObjectConverter getConverter(Class<?> clazz) {
		return getConverter(clazz, ConverterContext.DEFAULT_CONTEXT);
	}

	/**
	 * Converts an object to string using default converter context.
	 *
	 * @param object object to be converted.
	 * @return the string
	 */
	public static String toString(Object object) {
		if (object != null)
			return toString(object, object.getClass(), ConverterContext.DEFAULT_CONTEXT);
		else
			return "";
	}

	/**
	 * Converts an object to string using default converter context.
	 *
	 * @param object object to be converted.
	 * @param clazz  type of the object
	 * @return the string
	 */
	public static String toString(Object object, Class<?> clazz) {
		return toString(object, clazz, ConverterContext.DEFAULT_CONTEXT);
	}

	/**
	 * Converts an object to string using converter context specified.
	 *
	 * @param object  object to be converted.
	 * @param clazz   type of the object
	 * @param context converter context
	 * @return the string converted from object
	 */
	public static String toString(Object object, Class<?> clazz, ConverterContext context) {
		ObjectConverter converter = getConverter(clazz, context);
		if (converter != null && converter.supportToString(object, context)) {
			return converter.toString(object, context);
		} else if (object == null) {
			return "";
		} else {
			return object.toString();
		}
	}

	/**
	 * Converts from a string to an object with type class.
	 *
	 * @param string the string to be converted
	 * @param clazz  the type to be converted to
	 * @return the object of type class which is converted from string
	 */
	public static Object fromString(String string, Class<?> clazz) {
		return fromString(string, clazz, ConverterContext.DEFAULT_CONTEXT);
	}

	/**
	 * Converts from a string to an object with type class using the converter context.
	 *
	 * @param string  the string to be converted
	 * @param clazz   the type to be converted to
	 * @param context converter context to be used
	 * @return the object of type class which is converted from string
	 */
	public static Object fromString(String string, Class<?> clazz, ConverterContext context) {
		ObjectConverter converter = getConverter(clazz, context);
		if (converter != null && converter.supportFromString(string, context)) {
			Object value = converter.fromString(string, context);
			if (value != null && clazz != null && !clazz.isAssignableFrom(value.getClass())) {
				if (TypeUtil.isNumericType(clazz) && value instanceof Number) {
					clazz = TypeUtil.convertPrimitiveToWrapperType(clazz);
					if (clazz == Double.class) {
						return ((Number) value).doubleValue();
					}
					if (clazz == Byte.class) {
						return ((Number) value).byteValue();
					}
					if (clazz == Short.class) {
						return ((Number) value).shortValue();
					}
					if (clazz == Integer.class) {
						return ((Number) value).intValue();
					}
					if (clazz == Long.class) {
						return ((Number) value).longValue();
					}
					if (clazz == Float.class) {
						return ((Number) value).floatValue();
					}
				}
			}
			return value;
		} else {
			return null;
		}
	}

	/**
	 * Checks the value of autoInit.
	 *
	 * @return true or false.
	 * @see #setAutoInit(boolean)
	 */
	public static boolean isAutoInit() {
		return _autoInit;
	}

	/**
	 * Sets autoInit to true or false. If autoInit is true, whenever someone tries to call methods like as toString or
	 * fromString, {@link #initDefaultConverter()} will be called if it has never be called. By default, autoInit is
	 * true.
	 * <p/>
	 * This might affect the behavior if users provide their own converters and want to overwrite default converters. In
	 * this case, instead of depending on autoInit to initialize default converters, you should call {@link
	 * #initDefaultConverter()} first, then call registerConverter to add your own converters.
	 *
	 * @param autoInit true or false.
	 */
	public static void setAutoInit(boolean autoInit) {
		_autoInit = autoInit;
	}

	/**
	 * Adds a listener to the list that's notified each time a change to the manager occurs.
	 *
	 * @param l the RegistrationListener
	 */
	public static void addRegistrationListener(RegistrationListener l) {
		_cache.addRegistrationListener(l);
	}

	/**
	 * Removes a listener from the list that's notified each time a change to the manager occurs.
	 *
	 * @param l the RegistrationListener
	 */
	public static void removeRegistrationListener(RegistrationListener l) {
		_cache.removeRegistrationListener(l);
	}

	/**
	 * Returns an array of all the registration listeners registered on this manager.
	 *
	 * @return all of this registration's <code>RegistrationListener</code>s or an empty array if no registration
	 * listeners are currently registered
	 * @see #addRegistrationListener
	 * @see #removeRegistrationListener
	 */
	public static RegistrationListener[] getRegistrationListeners() {
		return _cache.getRegistrationListeners();
	}

	/**
	 * Gets the available ConverterContexts registered with the class.
	 *
	 * @param clazz the class.
	 * @return the available ConverterContexts.
	 */
	public static ConverterContext[] getConverterContexts(Class<?> clazz) {
		return _cache.getKeys(clazz, new ConverterContext[0]);
	}

	/**
	 * 初始化默认转换器。请确保在使用任何与转换器相关的类之前调用此方法。默认情况下，我们注册以下转换器。
	 * <ul>
	 *     <li> registerConverter(String.class, new DefaultObjectConverter());
	 *     <li> registerConverter(Integer.class, new IntegerConverter());
	 *     <li> registerConverter(int.class, new IntegerConverter());
	 *     <li> registerConverter(Integer.class, new NaturalNumberConverter(), NaturalNumberConverter.CONTEXT);
	 *     <li> registerConverter(int.class, new NaturalNumberConverter(), NaturalNumberConverter.CONTEXT);
	 *     <li> registerConverter(Long.class, new LongConverter());
	 *     <li> registerConverter(long.class, new LongConverter());
	 *     <li> registerConverter(Double.class, new DoubleConverter());
	 *     <li> registerConverter(double.class, new DoubleConverter());
	 *     <li> registerConverter(Float.class, new FloatConverter());
	 *     <li> registerConverter(float.class, new FloatConverter());
	 *     <li> registerConverter(Short.class, new ShortConverter());
	 *     <li> registerConverter(short.class, new ShortConverter());
	 *     <li> registerConverter(Rectangle.class, new RectangleConverter());
	 *     <li> registerConverter(Point.class, new PointConverter());
	 *     <li> registerConverter(Insets.class, new InsetsConverter());
	 *     <li> registerConverter(Dimension.class, new DimensionConverter());
	 *     <li> registerConverter(Boolean.class, new BooleanConverter());
	 *     <li> registerConverter(boolean.class, new BooleanConverter());
	 *     <li> registerConverter(File.class, new FileConverter());
	 *     <li> registerConverter(String.class, new FontNameConverter(), FontNameConverter.CONTEXT);
	 *     <li> registerConverter(Date.class, new DateConverter());
	 *     <li> registerConverter(Calendar.class, new CalendarConverter());
	 *     <li> registerConverter(Calendar.class, new MonthConverter(), MonthConverter.CONTEXT_MONTH);
	 *     <li> registerConverter(Color.class, new RgbColorConverter());
	 *     <li> registerConverter(Color.class, new HexColorConverter(), ColorConverter.CONTEXT_HEX);
	 *     <li> registerConverter(String[].class, new StringArrayConverter());
	 * </ul>
	 */
	@SuppressWarnings("unchecked")
	public static void initDefaultConverter() {
		if (_inited) {
			return;
		}
		_initing = true;
		try {
			registerConverter(String.class, new DefaultObjectConverter());
			registerConverter(char[].class, new PasswordConverter(), PasswordConverter.CONTEXT);

			DoubleConverter fractionConverter = new DoubleConverter();
			fractionConverter.setFractionDigits(2, 2);
			registerConverter(Number.class, fractionConverter, NumberConverter.CONTEXT_FRACTION_NUMBER);

			IntegerConverter integerConverter = new IntegerConverter();
			registerConverter(Integer.class, integerConverter);
			registerConverter(int.class, integerConverter);

			NaturalNumberConverter naturalNumberConverter = new NaturalNumberConverter();
			registerConverter(Integer.class, naturalNumberConverter, NaturalNumberConverter.CONTEXT);
			registerConverter(int.class, naturalNumberConverter, NaturalNumberConverter.CONTEXT);

			LongConverter longConverter = new LongConverter();
			registerConverter(Long.class, longConverter);
			registerConverter(long.class, longConverter);

			DoubleConverter doubleConverter = new DoubleConverter();
			registerConverter(Double.class, doubleConverter);
			registerConverter(double.class, doubleConverter);

			FloatConverter floatConverter = new FloatConverter();
			registerConverter(Float.class, floatConverter);
			registerConverter(float.class, floatConverter);

			ShortConverter shortConverter = new ShortConverter();
			registerConverter(Short.class, shortConverter);
			registerConverter(short.class, shortConverter);

			ByteConverter byteConverter = new ByteConverter();
			registerConverter(Byte.class, byteConverter);
			registerConverter(byte.class, byteConverter);

			registerConverter(Rectangle.class, new RectangleConverter());
			registerConverter(Point.class, new PointConverter());
			registerConverter(Insets.class, new InsetsConverter());
			registerConverter(Dimension.class, new DimensionConverter());

			BooleanConverter booleanConverter = new BooleanConverter();
			registerConverter(Boolean.class, booleanConverter);
			registerConverter(boolean.class, booleanConverter);

			registerConverter(File.class, new FileConverter());
			registerConverter(String.class, new FontNameConverter(), FontNameConverter.CONTEXT);

			DateConverter dateConverter = new DateConverter();
			registerConverter(Date.class, dateConverter);
			registerConverter(Date.class, dateConverter, DateConverter.DATETIME_CONTEXT);
			registerConverter(Date.class, dateConverter, DateConverter.TIME_CONTEXT);

			CalendarConverter calendarConverter = new CalendarConverter();
			registerConverter(Calendar.class, calendarConverter);
			registerConverter(Calendar.class, calendarConverter, DateConverter.DATETIME_CONTEXT);
			registerConverter(Calendar.class, calendarConverter, DateConverter.TIME_CONTEXT);

			registerConverter(Calendar.class, new MonthConverter(), MonthConverter.CONTEXT_MONTH);
			registerConverter(Color.class, new RgbColorConverter());
			registerConverter(Color.class, new HexColorConverter(), ColorConverter.CONTEXT_HEX);
			registerConverter(Color.class, new RgbColorConverter(true), ColorConverter.CONTEXT_RGBA);
			registerConverter(Color.class, new HexColorConverter(true), ColorConverter.CONTEXT_HEX_WITH_ALPHA);

			registerConverter(String[].class, new StringArrayConverter());

			QuarterNameConverter quarterNameConverter = new QuarterNameConverter();
			registerConverter(int.class, quarterNameConverter, QuarterNameConverter.CONTEXT);
			registerConverter(Integer.class, quarterNameConverter, QuarterNameConverter.CONTEXT);

			registerConverter(Font.class, new FontConverter());
			registerConverter(String.class, new MultilineStringConverter(), MultilineStringConverter.CONTEXT);

			CurrencyConverter currencyConverter = new CurrencyConverter();
			registerConverter(Float.class, currencyConverter, CurrencyConverter.CONTEXT);
			registerConverter(float.class, currencyConverter, CurrencyConverter.CONTEXT);
			registerConverter(Double.class, currencyConverter, CurrencyConverter.CONTEXT);
			registerConverter(double.class, currencyConverter, CurrencyConverter.CONTEXT);

			PercentConverter percentConverter = new PercentConverter();
			registerConverter(Float.class, percentConverter, PercentConverter.CONTEXT);
			registerConverter(float.class, percentConverter, PercentConverter.CONTEXT);
			registerConverter(Double.class, percentConverter, PercentConverter.CONTEXT);
			registerConverter(double.class, percentConverter, PercentConverter.CONTEXT);

			MonthNameConverter monthNameConverter = new MonthNameConverter();
			registerConverter(Integer.class, monthNameConverter, MonthNameConverter.CONTEXT);
			registerConverter(int.class, monthNameConverter, MonthNameConverter.CONTEXT);

			YearNameConverter yearNameConverter = new YearNameConverter();
			registerConverter(Integer.class, yearNameConverter, YearNameConverter.CONTEXT);
			registerConverter(int.class, yearNameConverter, YearNameConverter.CONTEXT);

			ObjectConverterManager.registerConverter(int[].class, new DefaultArrayConverter("; ", int.class));
			registerConverter(Object[].class, new DefaultArrayConverter("; ", Object.class));
			registerConverter(Enum[].class, new DefaultArrayConverter("; ", Enum.class));
			registerConverter(String[].class, new DefaultArrayConverter("; ", String.class));
			registerConverter(Date[].class, new DefaultArrayConverter("; ", Date.class));
			registerConverter(Calendar[].class, new DefaultArrayConverter("; ", Calendar.class));
			registerConverter(Number[].class, new DefaultArrayConverter("; ", Number.class));
			registerConverter(Integer[].class, new DefaultArrayConverter("; ", Integer.class));
			registerConverter(Float[].class, new DefaultArrayConverter("; ", Float.class));
			registerConverter(Double[].class, new DefaultArrayConverter("; ", Double.class));
			registerConverter(Long[].class, new DefaultArrayConverter("; ", Long.class));
			registerConverter(Short[].class, new DefaultArrayConverter("; ", Short.class));
			registerConverter(int[].class, new DefaultArrayConverter("; ", int.class));
			registerConverter(float[].class, new DefaultArrayConverter("; ", float.class));
			registerConverter(double[].class, new DefaultArrayConverter("; ", double.class));
			registerConverter(long[].class, new DefaultArrayConverter("; ", long.class));
			registerConverter(short[].class, new DefaultArrayConverter("; ", short.class));

			registerConverter(BigDecimal.class, new BigDecimalConverter());
		} finally {
			_initing = false;
			_inited = true;
		}
	}

	/**
	 * If {@link #initDefaultConverter()} is called once, calling it again will have no effect because an internal flag
	 * is set. This method will reset the internal flag so that you can call  {@link #initDefaultConverter()} in case
	 * you unregister all converters using {@link #unregisterAllConverters()}.
	 */
	public static void resetInit() {
		_inited = false;
	}

	public static void clear() {
		resetInit();
		_cache.clear();
	}
}
