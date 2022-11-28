/*
 * @(#) AbstractContext.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.converter;

import java.io.Serializable;
import java.util.Objects;

/**
 * <code>AbstractContext</code> is a generic context class. It has two fields: name and userObject. The name is just the
 * name of the context. You can use a meaningful string to name it. The userObject is customizable portion of Context.
 * You can set whatever you want as userObject. It's just a convention between whoever set it and whoever use it. For
 * example, in <code>ConverterContext</code>, we sometimes used it to pass in a <code>Format</code>.
 */
abstract public class AbstractContext implements Serializable {
	private String name;

	private Object userObject;

	/**
	 * Creates a named <code>AbstractContext</code>.
	 *
	 * @param name the name of the <code>AbstractContext</code>.
	 */
	public AbstractContext(String name) {
		this.name = name;
	}

	/**
	 * Creates an abstract context with a name and an object.
	 *
	 * @param name   the name of the <code>AbstractContext</code>.
	 * @param object the user object. It can be used any object to pass information along.
	 */
	public AbstractContext(String name, Object object) {
		this.name = name;
		userObject = object;
	}

	/**
	 * Gets the name of the abstract context.
	 *
	 * @return the name of the abstract context
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the abstract context.
	 *
	 * @param name the name of the abstract context
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the user object.
	 *
	 * @return the user object
	 */
	public Object getUserObject() {
		return userObject;
	}

	/**
	 * Sets the user object.
	 *
	 * @param userObject the user object.
	 */
	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	/**
	 * Override equals. Two abstract context equals as long as the name is the same.
	 *
	 * @param o object to compare.
	 * @return if two objects equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AbstractContext abstractContext)) return false;

		return Objects.equals(name, abstractContext.name);
	}

	@Override
	public int hashCode() {
		return (name != null ? name.hashCode() : 0);
	}

	@Override
	public String toString() {
		return getName();
	}
}
