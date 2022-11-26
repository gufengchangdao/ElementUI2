package com.element.util.handle;

import java.awt.*;

/**
 * {@link com.element.util.UIUtil#setRecursively}使用的简单处理程序。
 * 类似于将Predicate<Boolean>和两个Consumer<Component>组合在一起了。
 *
 * <pre>
 *  if ( condition() ) {
 *      action();
 *  }
 *  postAction();
 * </pre>
 */
public interface Handler {
	/**
	 * If true, it will call {@link #action(Component)} on this component.
	 *
	 * @param c the component
	 * @return true or false.
	 */
	boolean condition(Component c);

	/**
	 * The action you want to perform on this component. This method will only be called if {@link
	 * #condition(Component)} returns true.
	 *
	 * @param c the component
	 */
	void action(Component c);

	/**
	 * The action you want to perform to any components. If action(c) is called, this action is after it.
	 *
	 * @param c the component.
	 */
	void postAction(Component c);
}
