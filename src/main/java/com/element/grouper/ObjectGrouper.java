package com.element.grouper;

import com.element.comparator.ComparatorContext;
import com.element.converter.ConverterContext;

/**
 * 一个可以将对象转换为组的接口，以便可以将具有相同组的对象组合在一起。
 * 如果您想创建自己的 ObjectGrouper，我们建议您扩展 {@link AbstractObjectGrouper} ，以防我们因需求更改而向该接口添加新方法。
 */
public interface ObjectGrouper {
	/**
	 * Gets the group value after this value is grouped. If two objects return the same value in this getGroupValue
	 * method, the two objects are considered as one group. We assume all values returned from this method are of the
	 * same type which is returned in {@link #getType()}.
	 *
	 * @param value the value
	 * @return the value after grouped.
	 */
	Object getValue(Object value);

	/**
	 * Gets the group value type. It should be the type of the value that is returned from the getGroupValue.
	 *
	 * @return the group value type.
	 */
	Class<?> getType();

	/**
	 * Gets the name of this object grouper.
	 *
	 * @return the name of this grouper.
	 */
	String getName();

	/**
	 * Gets the converter context for the value returned from this object grouper. This converter context will be used
	 * to find the ObjectConverter that will convert the value returned from {@link #getValue(Object)} method to String
	 * so that it can be displayed somewhere.
	 *
	 * @return the converter context.
	 */
	ConverterContext getConverterContext();

	/**
	 * Gets the comparator context for the value returned from this object grouper. This comparator context will be used
	 * to find the ObjectComparator that will sort the values return from {@link #getValue(Object)} method whenever
	 * sorting is needed.
	 *
	 * @return the converter context.
	 */
	ComparatorContext getComparatorContext();
}
