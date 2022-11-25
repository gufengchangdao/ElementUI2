package com.element.util.handle;

import com.element.util.JideSwingUtilities;

import java.awt.*;

/**
 * A simple handler used by {@link JideSwingUtilities#setRecursively}.
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
