package com.element.util.handle;

import java.awt.*;

/**
 * A simple handler used by {@link com.element.util.UIUtil#setRecursively}.
 * <pre>
 *  if ( condition() ) {
 *      action();
 *  }
 *  postAction();
 * </pre>.
 */
public interface ConditionHandler extends Handler {
	/**
	 * If this method returns true, the recursive call will stop at the component and will not call to its
	 * children.
	 *
	 * @param c the component
	 * @return true or false.
	 */
	boolean stopCondition(Component c);
}
