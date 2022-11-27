/*
 * @(#)SelectAllFocusListener.java 8/30/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.element.util;

import com.element.util.handle.Handler;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * SelectAllUtils是一个实用程序类，<strong>用于在组件第一次获得焦点时选择文本组件中的所有文本</strong>。它非常容易使用。
 * <pre><code>
 * JTextField field = new JTextField();
 * SelectAllUtils.install(field);
 * </code></pre>
 * <p>
 * 您传入的组件可以是 JTextComponent 或任何包含一个或多个 JTextComponent 的容器。所有 JTextComponents 都将安装这样一个焦点侦听器，以
 * 便在第一次获得焦点时选择所有。例如，您可以将它安装到一个可编辑的 JComboBox 中。
 * <pre><code>
 * JComboBox comboBox = new JComboBox();
 * comboBox.setEditable(true);
 * SelectAllUtils.install(comboBox);
 * </code></pre>
 * <p>
 * 尽管 JComboBox 不是 JTextComponent，但它包含一个 JTextField，因此它仍然可以工作。但是请确保在调用 comboBox.setEditable(true)
 * 之后调用它。否则它将不起作用，因为在调用 setEditable(true) 之前不会创建 JTextField。
 */
public class SelectAllUtil {
	/**
	 * 客户属性。如果设置为 Boolean.TRUE，我们只会在组件获得焦点时第一次选择所有文本。如果是一直有效，不需要设置 Boolean.FALSE
	 */
	public static final String CLIENT_PROPERTY_ONLYONCE = "SelectAll.onlyOnce";

	private static final FocusListener SELECT_ALL = new FocusAdapter() {
		@Override
		public void focusGained(final FocusEvent e) {
			SwingUtilities.invokeLater(() -> {
				Object object = e.getSource();
				if (object instanceof JTextComponent c) {
					c.selectAll();
					Object clientProperty = c.getClientProperty(CLIENT_PROPERTY_ONLYONCE);
					// 移除监听器和客户端属性
					if (Boolean.TRUE.equals(clientProperty)) {
						c.removeFocusListener(SELECT_ALL);
						c.putClientProperty(CLIENT_PROPERTY_ONLYONCE, null);
					}
				} else if (object instanceof Component) {
					UIUtil.setRecursively((Component) object, new Handler() {
						public boolean condition(Component c) {
							return c instanceof JTextComponent;
						}

						public void action(Component component) {
							JTextComponent c = (JTextComponent) component;
							c.selectAll();
							Object clientProperty = c.getClientProperty(CLIENT_PROPERTY_ONLYONCE);
							// 移除监听器和客户端属性
							if (Boolean.TRUE.equals(clientProperty)) {
								c.removeFocusListener(SELECT_ALL);
								c.putClientProperty(CLIENT_PROPERTY_ONLYONCE, null);
							}
						}

						public void postAction(Component c) {
						}
					});
				}
			});
		}
	};

	/**
	 * Installs focus listener to all text components inside the component. This focus listener
	 * will select all the text when it gets focus.
	 *
	 * @param component the component to make it select all when having focus. The component could be a JTextComponent or could be
	 *                  a container that contains one or more JTextComponents. This install method will make all JTextComponents
	 *                  to have this select all feature.
	 */
	public static void install(final Component component) {
		install(component, true);
	}

	/**
	 * Installs focus listener to all text components inside the component. This focus listener
	 * will select all the text when it gets focus.
	 *
	 * @param component the component to make it select all when having focus. The component could be a JTextComponent or could be
	 *                  a container that contains one or more JTextComponents. This install method will make all JTextComponents
	 *                  to have this select all feature.
	 * @param onlyOnce  if true, we will only select all the text when the component has focus for the first time. Otherwise, it will
	 *                  always select all the text whenever the component receives focus.
	 */
	public static void install(final Component component, final boolean onlyOnce) {
		if (component instanceof JTextComponent c) {
			if (onlyOnce) c.putClientProperty(CLIENT_PROPERTY_ONLYONCE, Boolean.TRUE);
			component.addFocusListener(SELECT_ALL);
		} else {
			// 递归处理所有子组件
			UIUtil.setRecursively(component, new Handler() {
				public boolean condition(Component c) {
					return c instanceof JTextComponent;
				}

				public void action(Component c) {
					if (onlyOnce) ((JTextComponent) c).putClientProperty(CLIENT_PROPERTY_ONLYONCE, Boolean.TRUE);
					c.addFocusListener(SELECT_ALL);
				}

				public void postAction(Component c) {
				}
			});
		}
	}

	/**
	 * Uninstalls focus listener to all text components inside the component.
	 *
	 * @param component the component which {@link #install(Component)} is called.
	 */
	public static void uninstall(Component component) {
		if (component instanceof JTextComponent c) {
			c.removeFocusListener(SELECT_ALL);
			c.putClientProperty(CLIENT_PROPERTY_ONLYONCE, null);
		} else {
			UIUtil.setRecursively(component, new Handler() {
				public boolean condition(Component c) {
					return c instanceof JTextComponent;
				}

				public void action(Component c) {
					c.removeFocusListener(SELECT_ALL);
					((JTextComponent) c).putClientProperty(CLIENT_PROPERTY_ONLYONCE, null);
				}

				public void postAction(Component c) {
				}
			});
		}
	}
}
