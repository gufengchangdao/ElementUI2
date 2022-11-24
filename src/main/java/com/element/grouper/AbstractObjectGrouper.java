package com.element.grouper;

import com.element.comparator.ComparatorContext;
import com.element.converter.ConverterContext;

/**
 * The abstract implementation of <code>ObjectGrouper</code>. It just implements the {@link #getConverterContext()} and
 * {@link #getComparatorContext()} methods.
 */
abstract public class AbstractObjectGrouper implements ObjectGrouper {
	public ConverterContext getConverterContext() {
		return null;
	}

	public ComparatorContext getComparatorContext() {
		return null;
	}
}
