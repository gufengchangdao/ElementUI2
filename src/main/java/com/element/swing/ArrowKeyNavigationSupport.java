package com.element.swing;

import com.element.util.JideSwingUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个 util 类，支持在任何容器中使用四个箭头键导航。要使用它，您可以致电
 * <code><pre>
 * new ArrowKeyNavigationSupport().install(container);
 * </pre></code>
 * <p>
 * 容器可以是任何容器。一个典型的用例是按钮面板。默认情况下，我们在ButtonPanel类中使用它来启用左/右/上/下键。
 * <p>
 * 默认情况下，所有组件都可以在容器中导航，但您可以使用构造函数进一步定义哪些组件可以导航
 * <code><pre>
 * new ArrowKeyNavigationSupport(Class[] componentTypes)
 * </pre></code>
 * <p>
 * 其中 componentTypes 是您希望可导航的组件类的列表。例如，
 * <code><pre>
 * new ArrowKeyNavigationSupport(new Class[]{ AbstractButton.class }).install(container);
 * </pre></code>
 * <p>
 * 只允许任何按钮（JButton、JideButton、JCheckBox、JRadioButton）等。
 * <p>
 * 您还可以允许只使用某些键。例如。
 * <code><pre>
 * new ArrowKeyNavigationSupport(new int[]{ KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT}).install(container);
 * </pre></code>
 * <p>
 * 只有左右键在您的容器中导航才有意义。
 */
public class ArrowKeyNavigationSupport {
	private int[] _keyCode = new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN};
	private Class<Component>[] _componentTypes;
	public static final String CLIENT_PROPERTY_ARROWKEY_NAVIGATION_SUPPORT = "ArrowKeyNavigationSupport.previousAction";

	public ArrowKeyNavigationSupport() {
	}

	public ArrowKeyNavigationSupport(Class<Component>[] componentTypes) {
		_componentTypes = componentTypes;
	}

	public ArrowKeyNavigationSupport(int[] keyCodes) {
		_keyCode = keyCodes;
	}

	public ArrowKeyNavigationSupport(Class<Component>[] componentTypes, int[] keyCode) {
		_keyCode = keyCode;
		_componentTypes = componentTypes;
	}

	/**
	 * Installs the actions for arrow keys to allow user to navigate components using arrow keys.
	 *
	 * @param container the container such as ButtonPanel, JPanel etc.
	 */
	public void install(JComponent container) {
		for (int keyCode : _keyCode) {
			InputMap inputMap = container.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
			KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, 0);
			Object actionName = inputMap.get(keyStroke);
			if (actionName != null) {
				container.putClientProperty(CLIENT_PROPERTY_ARROWKEY_NAVIGATION_SUPPORT, actionName);
			}
			container.registerKeyboardAction(new NavigationAction(container, keyCode), "ArrowKeyNavigation " + keyCode, keyStroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		}
	}

	/**
	 * Uninstalls the actions for arrow keys.
	 *
	 * @param container the container such as ButtonPanel, JPanel etc.
	 */
	public void uninstall(JComponent container) {
		for (int keyCode : _keyCode) {
			Object actionName = container.getClientProperty(CLIENT_PROPERTY_ARROWKEY_NAVIGATION_SUPPORT);
			if (actionName != null) {
				InputMap inputMap = container.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
				KeyStroke keyStroke = KeyStroke.getKeyStroke(keyCode, 0);
				inputMap.put(keyStroke, actionName);
			} else {
				container.unregisterKeyboardAction(KeyStroke.getKeyStroke(keyCode, 0));
			}
		}
	}

	private class NavigationAction implements ActionListener {
		private final JComponent _parent;
		private final int _keyCode;

		public NavigationAction(JComponent c, int key) {
			_parent = c;
			_keyCode = key;
		}

		public void actionPerformed(ActionEvent e) {
			final List<Rectangle> rects = new ArrayList<>();
			final List<Component> components = new ArrayList<>();
			JideSwingUtilities.setRecursively(_parent, new JideSwingUtilities.Handler() {
				public void postAction(Component c) {
				}

				public void action(Component c) {
					if (_componentTypes != null) {
						boolean allowed = false;
						for (Class<?> allowedType : _componentTypes) {
							if (allowedType.isAssignableFrom(c.getClass())) {
								allowed = true;
								break;
							}
						}
						if (!allowed) return;
					}
					Rectangle bounds = c.getBounds();
					rects.add(SwingUtilities.convertRectangle(c, bounds, _parent));
					components.add(c);
				}

				public boolean condition(Component c) {
					return (c.isVisible() && c.isDisplayable() && c.isFocusable() && c.isEnabled());
				}
			});
			Component owner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
			Component c = switch (_keyCode) {
				case KeyEvent.VK_RIGHT -> findComponentToRight(owner, rects, components);
				case KeyEvent.VK_LEFT -> findComponentToLeft(owner, rects, components);
				case KeyEvent.VK_UP -> findComponentToAbove(owner, rects, components);
				case KeyEvent.VK_DOWN -> findComponentToBelow(owner, rects, components);
				default -> null;
			};
			if (c != null) c.requestFocusInWindow();
		}

		private Component findComponentToRight(Component c, List<Rectangle> rects, List<Component> components) {
			int max = Integer.MAX_VALUE;
			Component found = null;
			Rectangle src = SwingUtilities.convertRectangle(c, c.getBounds(), _parent);
			for (int i = 0; i < rects.size(); i++) {
				Rectangle dst = rects.get(i);
				if (dst.x <= src.x + src.width) { // not on the left
					continue;
				} else if (dst.y + dst.height < src.y) { // on top
					continue;
				} else if (dst.y > src.y + src.height) { // on bottom
					continue;
				}

				int dist = dst.x - src.x - src.width;
				if (dist < max) {
					max = dist;
					found = components.get(i);
				}
			}

			return found;
		}

		private Component findComponentToBelow(Component c, List<Rectangle> rects, List<Component> components) {
			int max = Integer.MAX_VALUE;
			Component found = null;
			Rectangle src = SwingUtilities.convertRectangle(c, c.getBounds(), _parent);
			for (int i = 0; i < rects.size(); i++) {
				Rectangle dst = rects.get(i);
				if (dst.y <= src.y + src.height) { // not on the left
					continue;
				} else if (dst.x + dst.width < src.x) { // on top
					continue;
				} else if (dst.x > src.x + src.width) { // on bottom
					continue;
				}

				int dist = dst.y - src.y - src.height;
				if (dist < max) {
					max = dist;
					found = components.get(i);
				}
			}

			return found;
		}

		private Component findComponentToLeft(Component c, List<Rectangle> rects, List<Component> components) {
			int max = Integer.MAX_VALUE;
			Component found = null;
			Rectangle src = SwingUtilities.convertRectangle(c, c.getBounds(), _parent);
			for (int i = 0; i < rects.size(); i++) {
				Rectangle dst = rects.get(i);
				if (dst.x + dst.width >= src.x) { // not on the right
					continue;
				} else if (dst.y + dst.height < src.y) { // on top
					continue;
				} else if (dst.y > src.y + src.height) { // on bottom
					continue;
				}

				int dist = src.x - dst.x - dst.width;
				if (dist < max) {
					max = dist;
					found = components.get(i);
				}
			}

			return found;
		}

		private Component findComponentToAbove(Component c, List<Rectangle> rects, List<Component> components) {
			int max = Integer.MAX_VALUE;
			Component found = null;
			Rectangle src = SwingUtilities.convertRectangle(c, c.getBounds(), _parent);
			for (int i = 0; i < rects.size(); i++) {
				Rectangle dst = rects.get(i);
				if (dst.y + dst.height >= src.y) { // not on the above
					continue;
				} else if (dst.x + dst.width < src.x) { // on left
					continue;
				} else if (dst.x > src.x + src.width) { // on right
					continue;
				}

				int dist = src.y - dst.y - dst.height;
				if (dist < max) {
					max = dist;
					found = components.get(i);
				}
			}

			return found;
		}

	}
}
