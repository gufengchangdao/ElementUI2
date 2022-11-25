package com.element.util.handle;

import com.element.util.JideSwingUtilities;

import java.awt.*;

/**
 * A simple handler used by {@link JideSwingUtilities#getRecursively(Component, GetHandler)}.
 * <code><pre>
 *  if ( condition() ) {
 *      return action();
 *  }
 * </pre></code>.
 * Here is an example to get the first child of the specified type.
 * <code><pre>
 * public static Component getFirstChildOf(final Class clazz, Component c) {
 *     return getRecursively(c, new GetHandler() {
 *         public boolean condition(Component c) {
 *             return clazz.isAssignableFrom(c.getClass());
 *         }
 *         public Component action(Component c) {
 *             return c;
 *         }
 *     });
 * }
 * </pre></code>
 */
public interface GetHandler {
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
	 * @return the component that will be returned from {@link JideSwingUtilities#getRecursively(Component, GetHandler)}
	 */
	Component action(Component c);
}
