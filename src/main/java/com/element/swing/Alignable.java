/*
 * @(#)Alignable.java
 *
 * Copyright 2002 - 2004 JIDE Software. All rights reserved.
 */
package com.element.swing;

import java.awt.*;

/**
 * Alignable是一个接口，可以被任何组件实现，以提供信息，例如如何设置方向和检查组件是否支持垂直方向或水平方向。
 * <p>
 * 一些组件支持垂直方向和水平方向。例如，一个只有图标的 JideButton。它可以放在垂直工具栏或普通水平工具栏上。然而，大多数组件并不支持两者。
 * 例如，组合框。很难想象将组合框放在垂直工具栏上。
 * <p>
 * 通过实现这个接口，组件可以选择支持垂直方向还是水平方向。但是，如果将未实现此接口的组件添加到工具栏，默认情况下，它将被视
 * 为 supportHorizontalOrientation() 返回 true 和 supportVerticalOrientation() 返回 false。
 */
public interface Alignable {
	/**
	 * Property name to indicate the orientation is changed.
	 */
	String PROPERTY_ORIENTATION = "orientation";

	/**
	 * Checks if the component support vertical orientation. doesn't consider the component orientation, it should
	 * return false.
	 *
	 * @return true if it supports vertical orientation
	 */
	boolean supportVerticalOrientation();

	/**
	 * Checks if the component support horizontal orientation.
	 *
	 * @return true if it supports horizontal orientation
	 */
	boolean supportHorizontalOrientation();

	/**
	 * Changes the orientation. If the component is a Swing component, the default implementation is this.
	 * <br><code>JideSwingUtilities.setOrientationOf(this, orientation);<code>
	 *
	 * @param orientation the new orientation
	 */
	void setOrientation(int orientation);

	/**
	 * Gets the orientation. If the component is a Swing component, the default implementation is
	 * {@link com.element.util.JideSwingUtilities#getOrientationOf(Component)}. <br>
	 *
	 * @return orientation
	 */
	int getOrientation();
}
