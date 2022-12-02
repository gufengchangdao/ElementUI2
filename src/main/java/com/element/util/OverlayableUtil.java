package com.element.util;

import com.element.swing.Overlayable;
import com.element.swing.DefaultOverlayable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This util class has several methods related to {@link Overlayable}.
 */
public class OverlayableUtil {
	/**
	 * Gets the overlayable associated with this component and its parents. This method will find the first overlayable
	 * that contains the component or its parents.
	 *
	 * @param component the component.
	 * @return the overlayable.
	 */
	public static Overlayable getOverlayable(JComponent component) {
		Container parent = component;
		do {
			Object o = ((JComponent) parent).getClientProperty(DefaultOverlayable.CLIENT_PROPERTY_OVERLAYABLE);
			if (o instanceof Overlayable) return (Overlayable) o;
			parent = parent.getParent();
		} while (parent instanceof JComponent);
		return null;
	}

	/**
	 * Gets all overlayables associated with this component and its parents. Different from
	 * {@link #getOverlayable(JComponent)}, this method will find the all overlayables that contain the component
	 * or its parents.
	 *
	 * @param component the component
	 * @return all the overlayables.
	 */
	public static List<Overlayable> getAllOverlayables(JComponent component) {
		List<Overlayable> list = new ArrayList<>();
		Container parent = component;
		do {
			Object o = ((JComponent) parent).getClientProperty(DefaultOverlayable.CLIENT_PROPERTY_OVERLAYABLE);
			if (o instanceof Overlayable) {
				if (!list.contains(o)) {
					list.add((Overlayable) o);
				}
			}
			parent = parent.getParent();
		} while (parent != null);
		return list;
	}

	/**
	 * Repaints the overlayable component associated with component. Because the overlay component is shown above the
	 * component and its child components, if any of the components repaint, the overlay component will be covered if
	 * the overlay component doesn't know to repaint immediately. Due to way Swing repaintManager works, there isn't any
	 * other better way to solve the issue other than the component has code to trigger the repaint of the overlay
	 * component. That's one reason we provide this way to repaint the overlay component easily.
	 * <p/>
	 * See below for an example of how to prepare the component to be ready for the overlayable.
	 * <pre><code>
	 * public OverlayTextField() {
	 * ...
	 * public void repaint(long tm, int x, int y, int width, int height) {
	 *     super.repaint(tm, x, y, width, height);
	 *     OverlayableUtils.repaintOverlayable(this);
	 * }
	 * </code></pre>
	 *
	 * @param component the component that has an overlayable.
	 */
	public static void repaintOverlayable(JComponent component) {
		Overlayable overlayable = getOverlayable(component);
		if (overlayable instanceof Component) {
			((Component) overlayable).repaint();
		}
	}

	/**
	 * Repaints all the overlayables associated with the component or its parents.
	 *
	 * @param component the component.
	 */
	public static void repaintAllOverlayables(JComponent component) {
		List<Overlayable> overlayables = getAllOverlayables(component);
		for (Overlayable overlayable : overlayables) {
			if (overlayable instanceof Component) {
				((Component) overlayable).repaint();
			}
		}
	}
}
