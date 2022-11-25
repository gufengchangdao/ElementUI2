/*
 * @(#)JideSwingUtilities.java
 *
 * Copyright 2002 JIDE Software. All rights reserved.
 */
package com.element.util;

import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.WindowsDesktopProperty;
import com.element.plaf.basic.ThemePainter;
import com.element.swing.Alignable;
import com.element.swing.FastGradientPainter;
import com.element.swing.ShadowFactory;
import com.element.ui.button.JideSplitButton;
import com.element.ui.button.SplitButtonModel;
import com.element.ui.dialog.ButtonPanel;
import com.element.ui.dialog.ButtonResources;
import com.element.ui.layout.JideBorderLayout;
import com.element.ui.nullc.NullPanel;
import com.element.ui.pane.JideScrollPane;
import com.element.util.handle.ConditionHandler;
import com.element.util.handle.GetHandler;
import com.element.util.handle.Handler;
import org.apache.batik.ext.awt.geom.Polygon2D;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.*;
import java.util.List;
import java.util.*;

/**
 * Swing 的实用程序类。
 */
public class JideSwingUtilities implements SwingConstants {
	/**
	 * 文本是否被抗锯齿绘制。这仅在AA_TEXT_DEFINED为真时使用。
	 */
	private static final boolean AA_TEXT;

	/**
	 * 是否定义了系统属性“swing.aatext”。
	 */
	private static final boolean AA_TEXT_DEFINED;

	/**
	 * 客户端属性中用于指示组件是否应使用文本的键。
	 */
	public static final Object AA_TEXT_PROPERTY_KEY = new StringBuilder("AATextPropertyKey");

	static {
		String aa = SecurityUtils.getProperty("swing.aatext");
		AA_TEXT_DEFINED = (aa != null);
		AA_TEXT = "true".equals(aa);
	}

	// ---------------------------------------------------------------------
	// 包装组件
	// ---------------------------------------------------------------------

	/**
	 * Create a Panel around a component so that component aligns to left.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createLeftPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.LINE_START);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to right.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createRightPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.LINE_END);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to top.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createTopPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.PAGE_START);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to bottom.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createBottomPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.PAGE_END);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component is right in the middle.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createCenterPanel(Component object) {
		JPanel ret = new NullPanel(new GridBagLayout());
		ret.setOpaque(false);
		ret.add(object, new GridBagConstraints());
		return ret;
	}

	/**
	 * Creates a container which a label for the component.
	 *
	 * @param title      the label
	 * @param component  the component
	 * @param constraint the constraint as in BorderLayout. You can use all the constraints as in BorderLayout except
	 *                   CENTER.
	 * @return the container which has both the label and the component.
	 */
	public static JPanel createLabeledComponent(JLabel title, Component component, Object constraint) {
		JPanel ret = new NullPanel(new JideBorderLayout(3, 3));
		ret.setOpaque(false);
		ret.add(title, constraint);
		title.setLabelFor(component);
		ret.add(component);
		return ret;
	}

	/**
	 * Center the component to it's parent window.
	 *
	 * @param childToCenter the parent window
	 */
	public static void centerWindow(Window childToCenter) {
		childToCenter.setLocationRelativeTo(childToCenter.getParent());
	}

	// ---------------------------------------------------------------------
	// 图形绘制
	// ---------------------------------------------------------------------

	/**
	 * 绘制箭头形状，是一个等腰直角三角形，只有朝下和朝右两个方向
	 *
	 * @param g           the graphics instance
	 * @param color       color
	 * @param startX      start X
	 * @param startY      start Y
	 * @param width       width
	 * @param orientation horizontal or vertical
	 */
	public static void paintArrow(Graphics g, Color color, int startX, int startY, int width, int orientation) {
		Graphics2D g2 = (Graphics2D) g;
		Color oldColor = g2.getColor();
		g2.setColor(color);
		if (orientation == HORIZONTAL) { //朝下
			g2.fill(new Polygon2D(
					new float[]{startX, startX + width, (startX * 2 + width) / 2f},
					new float[]{startY, startY, startY + (width + 1) / 2f},
					3
			));
		} else if (orientation == VERTICAL) { //朝右边
			g2.fill(new Polygon2D(
					new float[]{startX, startX, startX + width / 2f},
					new float[]{startY, startY + width, startY + width / 2f},
					3
			));
		}
		g2.setColor(oldColor);
	}

	/**
	 * 绘制箭头形状，如果组件是从右到左。绘制的三角形将朝向左边。支持方向：下左右
	 *
	 * @param c           the component
	 * @param g           the graphics instance
	 * @param color       color
	 * @param startX      start X
	 * @param startY      start Y
	 * @param width       width
	 * @param orientation horizontal or vertical
	 */
	public static void paintArrow(JComponent c, Graphics g, Color color, int startX, int startY, int width, int orientation) {
		if (!c.getComponentOrientation().isLeftToRight()) {
			Color oldColor = g.getColor();
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(color);
			g2.fill(new Polygon2D(
					new float[]{startX + width, startX + width, startX + width / 2f},
					new float[]{startY, startY + width, startY + width / 2f},
					3
			));
			g2.setColor(oldColor);
			return;
		}

		paintArrow(g, color, startX, startY, width, orientation);
	}

	// ---------------------------------------------------------------------
	// 其他设置
	// ---------------------------------------------------------------------

	/**
	 * Toggles between RTL and LTR.
	 *
	 * @param topContainer the component
	 */
	public static void toggleRTLnLTR(Component topContainer) {
		ComponentOrientation co = topContainer.getComponentOrientation();
		if (co == ComponentOrientation.RIGHT_TO_LEFT)
			co = ComponentOrientation.LEFT_TO_RIGHT;
		else
			co = ComponentOrientation.RIGHT_TO_LEFT;
		topContainer.applyComponentOrientation(co);
	}

	/**
	 * 同步两个视口。主视口的视口位置发生变化，从视口的视口位置也会发生变化。
	 * 一般来说，如果你想让两个视口在垂直方向上同步，它们应该有相同的高度。如果水平就应该宽度相同。
	 * <p>
	 * 如果您使用相同的主视口和从视口重复调用此方法，则可以。它不会导致触发多个事件。
	 *
	 * @param masterViewport 主视口
	 * @param slaveViewport  从视口
	 * @param orientation    它可以是 {@link SwingConstants#HORIZONTAL} 或 {@link SwingConstants#VERTICAL}
	 */
	public static void synchronizeView(final JViewport masterViewport, final JViewport slaveViewport, final int orientation) {
		if (masterViewport == null || slaveViewport == null) {
			return;
		}
		ChangeListener[] changeListeners = masterViewport.getChangeListeners();
		// 添加视口改变监听器，但如果已经添加了就不再添加
		int i = 0;
		for (; i < changeListeners.length; i++) {
			if (changeListeners[i] == getViewportSynchronizationChangeListener()) break;
		}
		if (i >= changeListeners.length) {
			masterViewport.addChangeListener(getViewportSynchronizationChangeListener());
		}

		// 将从视口添加到客户端属性中
		Object property = masterViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT);
		if (!(property instanceof Map)) {
			property = new HashMap<JViewport, Integer>();
		}
		Map<JViewport, Integer> slaveViewportMap = (Map) property;
		slaveViewportMap.put(slaveViewport, orientation);
		masterViewport.putClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT, slaveViewportMap);

		// 将主视口添加到客户端属性中
		property = slaveViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_MASTER_VIEWPORT);
		if (!(property instanceof Map)) {
			property = new HashMap<JViewport, Integer>();
		}
		Map<JViewport, Integer> masterViewportMap = (Map) property;
		masterViewportMap.put(masterViewport, orientation);
		slaveViewport.putClientProperty(JideScrollPane.CLIENT_PROPERTY_MASTER_VIEWPORT, masterViewportMap);
	}

	/**
	 * 取消同步两个视口
	 *
	 * @param masterViewport 主视口
	 * @param slaveViewport  从视口
	 */
	public static void unsynchronizeView(final JViewport masterViewport, final JViewport slaveViewport) {
		if (masterViewport == null || slaveViewport == null) {
			return;
		}
		Object property = masterViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT);
		if (property instanceof Map slaveViewportMap) {
			slaveViewportMap.remove(slaveViewport);
			if (slaveViewportMap.isEmpty()) {
				slaveViewportMap = null;
				masterViewport.removeChangeListener(getViewportSynchronizationChangeListener());
			}
			masterViewport.putClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT, slaveViewportMap);
		}

		property = slaveViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_MASTER_VIEWPORT);
		if (property instanceof Map masterViewportMap) {
			masterViewportMap.remove(masterViewport);
			if (masterViewportMap.isEmpty()) {
				masterViewportMap = null;
			}
			slaveViewport.putClientProperty(JideScrollPane.CLIENT_PROPERTY_MASTER_VIEWPORT, masterViewportMap);
		}
	}

	/**
	 * 获取按钮此时状态，返回值为 {@link ThemePainter} 内的常量
	 *
	 * @param b 按钮
	 * @return {@link ThemePainter} 内的常量，表示按钮此时的状态
	 */
	public static int getButtonState(AbstractButton b) {
		ButtonModel model = b.getModel();
		if (!model.isEnabled()) {
			if (model.isSelected()) return ThemePainter.STATE_DISABLE_SELECTED;
			else return ThemePainter.STATE_DISABLE;
		} else if (model.isPressed() && model.isArmed()) {
			if (model.isRollover()) return ThemePainter.STATE_PRESSED;
			else if (model.isSelected()) return ThemePainter.STATE_SELECTED;
		} else if (b.isRolloverEnabled() && model.isRollover()) {
			if (model.isSelected()) return ThemePainter.STATE_PRESSED; // should be rollover selected
			else return ThemePainter.STATE_ROLLOVER;
		} else if (model.isSelected()) return ThemePainter.STATE_SELECTED;
		else if (b.hasFocus() && b.isFocusPainted()) {
			if (model.isSelected()) return ThemePainter.STATE_PRESSED;
			else return ThemePainter.STATE_ROLLOVER;
		}
		return ThemePainter.STATE_DEFAULT;
	}

	/**
	 * 返回按钮此时的状态数组
	 */
	public static int[] getButtonState(JideSplitButton b) {
		int[] states = new int[2];
		SplitButtonModel model = (SplitButtonModel) b.getModel();
		if (!model.isEnabled()) {
			if (model.isButtonSelected()) {
				states[0] = ThemePainter.STATE_DISABLE_SELECTED;
			} else {
				states[0] = ThemePainter.STATE_DISABLE;
			}
		} else if (b.hasFocus() && b.isFocusPainted()) {
			if (model.isButtonSelected()) {
				states[0] = ThemePainter.STATE_SELECTED;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			} else if (model.isSelected()) {
				states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
				states[1] = ThemePainter.STATE_SELECTED;
			} else {
				states[0] = ThemePainter.STATE_ROLLOVER;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			}
		} else if (model.isPressed() && model.isArmed()) {
			if (model.isButtonRollover()) {
				states[0] = ThemePainter.STATE_PRESSED;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			} else if (model.isRollover()) {
				states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
				states[1] = ThemePainter.STATE_ROLLOVER;
			}
		} else if (b.isRolloverEnabled() && model.isButtonRollover()) {
			if (model.isButtonSelected()) {
				states[0] = ThemePainter.STATE_PRESSED;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			} else if (model.isSelected()) {
				states[0] = ThemePainter.STATE_ROLLOVER;
				states[1] = ThemePainter.STATE_PRESSED;
			} else {
				states[0] = ThemePainter.STATE_ROLLOVER;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			}
		} else if (b.isRolloverEnabled() && model.isRollover()) {
			if (model.isButtonSelected()) {
				states[0] = ThemePainter.STATE_PRESSED;
				states[1] = ThemePainter.STATE_ROLLOVER;
			} else if (model.isSelected()) {
				states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
				states[1] = ThemePainter.STATE_PRESSED;
			} else {
				states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
				states[1] = ThemePainter.STATE_ROLLOVER;
			}
		} else if (model.isButtonSelected()) {
			states[0] = ThemePainter.STATE_SELECTED;
			states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
		} else if (model.isSelected()) {
			states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			states[1] = ThemePainter.STATE_SELECTED;
		} else {
			states[0] = ThemePainter.STATE_DEFAULT;
			states[1] = ThemePainter.STATE_DEFAULT;
		}
		return states;
	}

	// ---------------------------------------------------------------------
	// 比较
	// ---------------------------------------------------------------------

	/**
	 * Checks if the two objects equal. If both are null, they are equal. If o1 and o2 both are Comparable, we will use
	 * compareTo method to see if it equals 0. At last, we will use <code>o1.equals(o2)</code> to compare. If none of
	 * the above conditions match, we return false.
	 *
	 * @param o1 the first object to compare
	 * @param o2 the second object to compare
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2) {
		return equals(o1, o2, false);
	}

	/**
	 * Checks if the two objects equal. If both are the same instance, they are equal. If both are null, they are equal.
	 * If o1 and o2 both are Comparable, we will use compareTo method to see if it equals 0. If considerArrayOrList is
	 * true and o1 and o2 are both array, we will compare each element in the array. At last, we will use
	 * <code>o1.equals(o2)</code> to compare. If none of the above conditions match, we return false.
	 *
	 * @param o1                  the first object to compare
	 * @param o2                  the second object to compare
	 * @param considerArrayOrList If true, and if o1 and o2 are both array, we will compare each element in the array
	 *                            instead of just compare the two array objects.
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2, boolean considerArrayOrList) {
		return equals(o1, o2, considerArrayOrList, true);
	}

	/**
	 * Checks if the two objects equal. If both are the same instance, they are equal. If both are null, they are equal.
	 * If o1 and o2 both are Comparable, we will use compareTo method to see if it equals 0. If considerArrayOrList is
	 * true and o1 and o2 are both array, we will compare each element in the array. At last, we will use
	 * <code>o1.equals(o2)</code> to compare. If none of the above conditions match, we return false.
	 *
	 * @param o1                  the first object to compare
	 * @param o2                  the second object to compare
	 * @param considerArrayOrList If true, and if o1 and o2 are both array, we will compare each element in the array
	 *                            instead of just compare the two array objects.
	 * @param caseSensitive       if the o1 and o2 are CharSequence, we will use this parameter to do a case sensitive
	 *                            or insensitive comparison
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2, boolean considerArrayOrList, boolean caseSensitive) {
		if (o1 == o2) {
			return true;
		} else if (o1 != null && o2 == null) {
			return false;
		} else if (o1 == null) {
			return false;
		} else if (o1 instanceof CharSequence && o2 instanceof CharSequence) {
			return equals((CharSequence) o1, (CharSequence) o2, caseSensitive);
		} else if (o1 instanceof Comparable && o2 instanceof Comparable && o1.getClass().isAssignableFrom(o2.getClass())) {
			return ((Comparable) o1).compareTo(o2) == 0;
		} else if (o1 instanceof Comparable && o2 instanceof Comparable && o2.getClass().isAssignableFrom(o1.getClass())) {
			return ((Comparable) o2).compareTo(o1) == 0;
		} else if (considerArrayOrList && o1 instanceof List && o2 instanceof List) {
			int length1 = ((List<?>) o1).size();
			int length2 = ((List<?>) o2).size();
			if (length1 != length2) {
				return false;
			}
			for (int i = 0; i < length1; i++) {
				if (!equals(((List<?>) o1).get(i), ((List<?>) o2).get(i), true)) {
					return false;
				}
			}
			return true;
		} else if (considerArrayOrList && o1.getClass().isArray() && o2.getClass().isArray()) {
			int length1 = Array.getLength(o1);
			int length2 = Array.getLength(o2);
			if (length1 != length2) {
				return false;
			}
			for (int i = 0; i < length1; i++) {
				if (!equals(Array.get(o1, i), Array.get(o2, i), true)) {
					return false;
				}
			}
			return true;
		} else {
			return o1.equals(o2);
		}
	}

	public static boolean equals(CharSequence s1, CharSequence s2, boolean caseSensitive) {
		if (s1 == s2) return true;
		if (s1 == null || s2 == null) return false;

		// Algorithm from String.regionMatches()

		if (s1.length() != s2.length()) return false;
		int to = 0;
		int po = 0;
		int len = s1.length();

		while (len-- > 0) {
			char c1 = s1.charAt(to++);
			char c2 = s2.charAt(po++);
			if (c1 == c2) {
				continue;
			}
			if (!caseSensitive && charsEqualIgnoreCase(c1, c2)) continue;
			return false;
		}

		return true;
	}

	public static boolean charsEqualIgnoreCase(char a, char b) {
		return a == b || Character.toLowerCase(a) == Character.toLowerCase(b);
	}

	// ---------------------------------------------------------------------
	// 递归处理组件
	// ---------------------------------------------------------------------

	/**
	 * 在组件上递归调用处理程序。
	 *
	 * @param c       component
	 * @param handler handler to be called
	 */
	public static void setRecursively(final Component c, final Handler handler) {
		setRecursively0(c, handler);
		handler.postAction(c);
	}

	private static void setRecursively0(final Component c, final Handler handler) {
		if (handler.condition(c)) {
			handler.action(c);
		}

		if (handler instanceof ConditionHandler && ((ConditionHandler) handler).stopCondition(c)) {
			return;
		}

		Component[] children = null;

		if (c instanceof JMenu) {
			children = ((JMenu) c).getMenuComponents();
		} else if (c instanceof JTabbedPane tabbedPane) {
			children = new Component[tabbedPane.getTabCount()];
			for (int i = 0; i < children.length; i++) {
				children[i] = tabbedPane.getComponentAt(i);
			}
		} else if (c instanceof Container) {
			children = ((Container) c).getComponents();
		}
		if (children != null) {
			for (Component child : children) {
				setRecursively0(child, handler);
			}
		}
	}

	/**
	 * Gets to a child of a component recursively based on certain condition.
	 *
	 * @param c       component
	 * @param handler handler to be called
	 * @return the component that matches the condition specified in GetHandler.
	 */
	public static Component getRecursively(final Component c, final GetHandler handler) {
		return getRecursively0(c, handler);
	}

	private static Component getRecursively0(final Component c, final GetHandler handler) {
		if (handler.condition(c)) {
			return handler.action(c);
		}

		Component[] children = null;

		if (c instanceof JMenu) {
			children = ((JMenu) c).getMenuComponents();
		} else if (c instanceof Container) {
			children = ((Container) c).getComponents();
		}

		if (children != null) {
			for (Component child : children) {
				Component result = getRecursively0(child, handler);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the first component inside the specified container that has the specified name.
	 *
	 * @param c    the container
	 * @param name the name of the component
	 * @return the component. Null if not found.
	 */
	public static Component findFirstComponentByName(final Container c, final String name) {
		if (name != null && name.trim().length() != 0) {
			return getRecursively(c, new GetHandler() {
				@Override
				public boolean condition(Component c) {
					return name.equals(c.getName());
				}

				@Override
				public Component action(Component c) {
					return c;
				}
			});
		} else {
			return null;
		}
	}

	/**
	 * Gets the first component inside the specified container that has the specified class.
	 *
	 * @param c     the container
	 * @param clazz the class of the component
	 * @return the component. Null if not found.
	 */
	public static Component findFirstComponentByClass(final Container c, final Class<?> clazz) {
		if (clazz != null) {
			return getRecursively(c, new GetHandler() {
				@Override
				public boolean condition(Component c) {
					return c.getClass().isAssignableFrom(clazz);
				}

				@Override
				public Component action(Component c) {
					return c;
				}
			});
		} else {
			return null;
		}
	}

	/**
	 * Calls setEnabled method recursively on component. <code>Component</code> c is usually a <code>Container</code>
	 *
	 * @param c       component
	 * @param enabled true if enable; false otherwise
	 */
	public static void setEnabledRecursively(final Component c, final boolean enabled) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return true;
			}

			public void action(Component c) {
				c.setEnabled(enabled);
			}

			public void postAction(Component c) {
			}
		});
	}

	/**
	 * Calls putClientProperty method recursively on component and its child components as long as it is JComponent.
	 *
	 * @param c              component
	 * @param clientProperty the client property name
	 * @param value          the value for the client property
	 */
	public static void putClientPropertyRecursively(final Component c, final String clientProperty, final Object value) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return c instanceof JComponent;
			}

			public void action(Component c) {
				((JComponent) c).putClientProperty(clientProperty, value);
			}

			public void postAction(Component c) {
			}
		});
	}

	/**
	 * Calls setRequestFocusEnabled method recursively on component. <code>Component</code> c is usually a
	 * <code>Container</code>
	 *
	 * @param c       component
	 * @param enabled true if setRequestFocusEnabled to true; false otherwise
	 */
	public static void setRequestFocusEnabledRecursively(final Component c, final boolean enabled) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return true;
			}

			public void action(Component c) {
				if (c instanceof JComponent)
					((JComponent) c).setRequestFocusEnabled(enabled);
			}

			public void postAction(Component c) {
			}
		});
	}

	private static PropertyChangeListener _setOpaqueTrueListener;
	private static PropertyChangeListener _setOpaqueFalseListener;

	private static final String OPAQUE_LISTENER = "setOpaqueRecursively.opaqueListener";

	/**
	 * setOpaqueRecursively method will make all child components opaque true or false. But if you call
	 * jcomponent.putClientProperty(SET_OPAQUE_RECURSIVELY_EXCLUDED, Boolean.TRUE), we will not touch this particular
	 * component when setOpaqueRecursively.
	 */
	public static final String SET_OPAQUE_RECURSIVELY_EXCLUDED = "setOpaqueRecursively.excluded";

	/**
	 * 在除 JButton、JComboBox 和 JTextComponent 之外的每个组件上递归调用 setOpaque 方法。 Component c 通常是一个Container。该方
	 * 法添加的监听器监听opaque属性改变的时候会把值改回来，也就是用该方法设置了opaque就不会因外界调用setOpaque而改变了。
	 * <p>
	 * 如果您希望某个子组件不受此调用的影响，您可以在调用此方法之前调用
	 * <pre>
	 *     jcomponent.putClientProperty(SET_OPAQUE_RECURSIVELY_EXCLUDED, Boolean.TRUE)。
	 * </pre>
	 *
	 * @param c      component
	 * @param opaque true if setOpaque to true; false otherwise
	 */
	public static void setOpaqueRecursively(final Component c, final boolean opaque) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				if (c instanceof JComboBox || c instanceof JButton || c instanceof JTextComponent ||
						c instanceof ListCellRenderer || c instanceof TreeCellRenderer || c instanceof TableCellRenderer || c instanceof CellEditor)
					return false;

				if (!(c instanceof JComponent jc)) return false;
				if (Boolean.TRUE.equals(jc.getClientProperty(SET_OPAQUE_RECURSIVELY_EXCLUDED))) return false;

				return true;
			}

			public void action(Component c) {
				JComponent jc = (JComponent) c;

				Object clientProperty = jc.getClientProperty(OPAQUE_LISTENER);
				if (clientProperty != null) {
					jc.removePropertyChangeListener("opaque", (PropertyChangeListener) clientProperty);
					jc.putClientProperty(OPAQUE_LISTENER, null);
				}
				jc.setOpaque(opaque);

				if (opaque) {
					if (_setOpaqueTrueListener == null) {
						_setOpaqueTrueListener = new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {
								// opaque修改后会再改回true
								if (evt.getSource() instanceof JComponent) {
									Component component = (Component) evt.getSource();
									component.removePropertyChangeListener("opaque", this);
									if (component instanceof JComponent)
										((JComponent) component).setOpaque(true);
									component.addPropertyChangeListener("opaque", this);
								}
							}
						};
					}
					jc.addPropertyChangeListener("opaque", _setOpaqueTrueListener);
					jc.putClientProperty(OPAQUE_LISTENER, _setOpaqueTrueListener);
				} else {
					if (_setOpaqueFalseListener == null) {
						_setOpaqueFalseListener = new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {
								// opaque修改后会再改回false
								if (evt.getSource() instanceof JComponent) {
									Component component = (Component) evt.getSource();
									component.removePropertyChangeListener("opaque", this);
									if (component instanceof JComponent)
										((JComponent) component).setOpaque(false);
									component.addPropertyChangeListener("opaque", this);
								}
							}
						};
					}
					jc.addPropertyChangeListener("opaque", _setOpaqueFalseListener);
					jc.putClientProperty(OPAQUE_LISTENER, _setOpaqueFalseListener);
				}
			}

			public void postAction(Component c) {
			}
		});
	}


	public static Dimension getPreferredButtonSize(AbstractButton b, int textIconGap) {
		if (b.getComponentCount() > 0) {
			return null;
		}

		Icon icon = b.getIcon();
		String text = b.getText();

		Font font = b.getFont();
		FontMetrics fm = b.getFontMetrics(font);

		Rectangle iconR = new Rectangle();
		Rectangle textR = new Rectangle();
		Rectangle viewR = new Rectangle(Short.MAX_VALUE, Short.MAX_VALUE);

		SwingUtilities.layoutCompoundLabel(b, fm, text, icon,
				b.getVerticalAlignment(), b.getHorizontalAlignment(),
				b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
				viewR, iconR, textR, (text == null ? 0 : textIconGap));
		/* The preferred size of the button is the size of
		 * the text and icon rectangles plus the buttons insets.
		 */

		Rectangle r = iconR.union(textR);

		Insets insets = b.getInsets();
		r.width += insets.left + insets.right;
		r.height += insets.top + insets.bottom;

		return r.getSize();
	}

	public static int getOrientationOf(Component component) {
		if (component instanceof Alignable) {
			return ((Alignable) component).getOrientation();
		} else if (component instanceof JComponent) {
			Integer value = (Integer) ((JComponent) component).getClientProperty(Alignable.PROPERTY_ORIENTATION);
			if (value != null)
				return value;
		}
		return HORIZONTAL;
	}

	/**
	 * 设置组件的 orientation 属性，如果组件实现{@link Alignable}接口则直接调用方法，否则以客户端属性的方式记录orientation属性值
	 *
	 * @param component   组件
	 * @param orientation 方向
	 */
	public static void setOrientationOf(Component component, int orientation) {
		int old = getOrientationOf(component);
		if (orientation != old) {
			if (component instanceof Alignable) {
				((Alignable) component).setOrientation(orientation);
			} else if (component instanceof JComponent) {
				((JComponent) component).putClientProperty(Alignable.PROPERTY_ORIENTATION, orientation);
			}
		}
	}

	public static void setChildrenOrientationOf(Container c, int orientation) {
		Component[] components = c.getComponents();
		for (Component component : components) {
			setOrientationOf(component, orientation);
		}
	}

	/**
	 * 禁用组件及其子组件的双缓冲标志。返回映射包含双缓冲的组件。在此调用之后，您可以使用从该方法返回的映射使用
	 * {@link #restoreDoubleBuffered(Component, Map)}恢复双缓冲标志。
	 *
	 * @param c 父容器
	 * @return 包含所有双缓冲组件的映射，键为组件及子组件，值为是否使用了双缓冲
	 */
	public static Map<Component, Boolean> disableDoubleBuffered(final Component c) {
		final Map<Component, Boolean> map = new HashMap<>();
		if (c instanceof JComponent) {
			setRecursively(c, new Handler() {
				public boolean condition(Component c) {
					return c instanceof JComponent && c.isDoubleBuffered();
				}

				public void action(Component c) {
					map.put(c, Boolean.TRUE);
					((JComponent) c).setDoubleBuffered(false);
				}

				public void postAction(Component c) {
				}
			});
		}
		return map;
	}

	/**
	 * Enables the double buffered flag of the component and its children. The return map contains the components that
	 * weren't double buffered. After this call, you can then restore the double buffered flag using {@link
	 * #restoreDoubleBuffered(Component, Map)} using the map that is returned from this method.
	 *
	 * @param c the parent container.
	 * @return the map that contains all components that weren't double buffered.
	 */
	public static Map<Component, Boolean> enableDoubleBuffered(final Component c) {
		final Map<Component, Boolean> map = new HashMap<>();
		if (c instanceof JComponent) {
			setRecursively(c, new Handler() {
				public boolean condition(Component c) {
					return c instanceof JComponent && !c.isDoubleBuffered();
				}

				public void action(Component c) {
					map.put(c, Boolean.FALSE);
					((JComponent) c).setDoubleBuffered(true);
				}

				public void postAction(Component c) {

				}
			});
		}
		return map;
	}

	/**
	 * Restores the double buffered flag of the component and its children. Only components that are in the map will be
	 * changed.
	 *
	 * @param c   the parent container.
	 * @param map a map maps from component to a boolean. If the boolean is true, it means the component was double
	 *            buffered bore. Otherwise, not double buffered.
	 */
	public static void restoreDoubleBuffered(final Component c, final Map<Component, Boolean> map) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return c instanceof JComponent;
			}

			public void action(Component c) {
				Boolean value = map.get(c);
				if (value != null) {
					((JComponent) c).setDoubleBuffered(Boolean.TRUE.equals(value));
				}
			}

			public void postAction(Component c) {
			}
		});
	}

	/**
	 * 绘制矩形背景及边框
	 *
	 * @param g      绘制上下文
	 * @param rect   绘制区域，一般为组件大小
	 * @param border 边框色
	 * @param bk     背景色
	 */
	public static void paintBackground(Graphics g, Rectangle rect, Color border, Color bk) {
		Color old = g.getColor();
		g.setColor(bk);
		g.fillRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
		g.setColor(border);
		g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
		g.setColor(old);
	}

	/**
	 * 绘制矩形背景及边框
	 *
	 * @param g2d    绘制上下文
	 * @param rect   绘制区域，一般为组件大小
	 * @param border 边框色
	 * @param paint  背景色，为Paint类型，也就是说可以设置渐变色
	 */
	public static void paintBackground(Graphics2D g2d, Rectangle rect, Color border, Paint paint) {
		Color old = g2d.getColor();
		g2d.setPaint(paint);
		g2d.fillRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
		g2d.setColor(border);
		g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
		g2d.setColor(old);
	}

	/**
	 * Returns whether or not text should be drawn anti-aliased.
	 *
	 * @param c JComponent to test.
	 * @return Whether or not text should be drawn anti-aliased for the specified component.
	 */
	private static boolean drawTextAntiAliased(Component c) {
		if (!AA_TEXT_DEFINED) {
			if (c != null) {
				// Check if the component wants aa text
				if (c instanceof JComponent) {
					Boolean aaProperty = (Boolean) ((JComponent) c).getClientProperty(AA_TEXT_PROPERTY_KEY);
					return aaProperty != null ? aaProperty : false;
				} else {
					return false;
				}
			}
			// No component, assume aa is off
			return false;
		}
		// 'swing.aatext' was defined, use its value.
		return AA_TEXT;
	}

	/**
	 * 返回文本是否应该被抗锯齿绘制。
	 *
	 * @param aaText 没有定义系统属性“swing.aatext”时返回的默认值
	 * @return 返回文本是否应该被抗锯齿绘制
	 */
	public static boolean drawTextAntiAliased(boolean aaText) {
		if (!AA_TEXT_DEFINED) {
			// 'swing.aatext' wasn't defined, use the components aa text value.
			return aaText;
		}
		// 'swing.aatext' was defined, use its value.
		return AA_TEXT;
	}

	public static void drawStringUnderlineCharAt(JComponent c, Graphics g, String text,
	                                             int underlinedIndex, int x, int y) {
		drawString(c, g, text, x, y);

		if (underlinedIndex >= 0 && underlinedIndex < text.length()) {
			FontMetrics fm = g.getFontMetrics();
			int underlineRectX = x + fm.stringWidth(text.substring(0, underlinedIndex));
			int underlineRectY = y;
			int underlineRectWidth = fm.charWidth(text.charAt(underlinedIndex));
			int underlineRectHeight = 1;
			g.fillRect(underlineRectX, underlineRectY + fm.getDescent() - 1,
					underlineRectWidth, underlineRectHeight);
		}
	}

	static Map<Object, Object> renderingHints = null;

	static {
			Toolkit tk = Toolkit.getDefaultToolkit();
			renderingHints = (Map) (tk.getDesktopProperty("awt.font.desktophints"));
			tk.addPropertyChangeListener("awt.font.desktophints", evt -> {
				if (evt.getNewValue() instanceof RenderingHints) {
					renderingHints = (RenderingHints) evt.getNewValue();
				}
			});
	}

	/**
	 * Get rendering hints from a Graphics instance. "hintsToSave" is a Map of RenderingHint key-values. For each hint
	 * key present in that map, the value of that hint is obtained from the Graphics and stored as the value for the key
	 * in savedHints.
	 */
	private static Map getRenderingHints(Graphics2D g2d,
	                                     Map hintsToSave,
	                                     Map savedHints) {
		if (savedHints == null) {
			savedHints = new RenderingHints(null);
		} else {
			savedHints.clear();
		}
		if (hintsToSave == null || hintsToSave.size() == 0) {
			return savedHints;
		}
		/* RenderingHints.keySet() returns Set*/
		Set objects = hintsToSave.keySet();
		for (Object o : objects) {
			RenderingHints.Key key = (RenderingHints.Key) o;
			Object value = g2d.getRenderingHint(key);
			if (value != null) {
				savedHints.put(key, value);
			}
		}

		return savedHints;
	}

	public static void drawString(JComponent c, Graphics g, String text, int x, int y) {
			Graphics2D g2d = (Graphics2D) g;
			Map oldHints = null;
			if (renderingHints != null) {
				oldHints = getRenderingHints(g2d, renderingHints, null);
				g2d.addRenderingHints(renderingHints);
			}
			g2d.drawString(text, x, y);
			if (oldHints != null) {
				g2d.addRenderingHints(oldHints);
			}
	}

	/**
	 * Setups the graphics to draw text using anti-alias.
	 * <p/>
	 * Under JDK1.4 and JDK5, this method will use a system property "swing.aatext" to determine if anti-alias is used.
	 * Under JDK6, we will read the system setting. For example, on Windows XP, there is a check box to turn on clear
	 * type anti-alias. We will use the same settings.
	 *
	 * @param c the component
	 * @param g the Graphics instance
	 * @return the old hints. You will need this value as the third parameter in {@link
	 * #restoreAntialiasing(Component, Graphics, Object)}.
	 */
	public static Object setupAntialiasing(Component c, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Object oldHints;
			oldHints = getRenderingHints(g2d, renderingHints, null);
			if (renderingHints != null) {
				g2d.addRenderingHints(renderingHints);
			}
		return oldHints;
	}

	/**
	 * Restores the old setting for text anti-alias.
	 *
	 * @param c
	 * @param g
	 * @param oldHints the value returned from {@link #setupAntialiasing(Component, Graphics)}.
	 */
	public static void restoreAntialiasing(Component c, Graphics g, Object oldHints) {
		Graphics2D g2d = (Graphics2D) g;
		if (oldHints instanceof RenderingHints) {
			g2d.addRenderingHints((RenderingHints) oldHints);
		}
	}

	/**
	 * Setups the graphics to draw shape using anti-alias.
	 *
	 * @param g
	 * @return the old hints. You will need this value as the third parameter in {@link
	 * #restoreShapeAntialiasing(Graphics, Object)}.
	 */
	public static Object setupShapeAntialiasing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Object oldHints = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		return oldHints;
	}

	/**
	 * Restores the old setting for shape anti-alias.
	 *
	 * @param g
	 * @param oldHints the value returned from {@link #setupShapeAntialiasing(Graphics)}.
	 */
	public static void restoreShapeAntialiasing(Graphics g, Object oldHints) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldHints);
	}

	public static void drawGrip(Graphics g, Rectangle rectangle, int maxLength, int maxThickness) {
		drawGrip(g, rectangle, maxLength, maxThickness, true);
	}

	public static void drawGrip(Graphics g, Rectangle rectangle, int maxLength, int maxThickness, boolean isSelected) {
		if (rectangle.width > rectangle.height) {
			int count = maxLength;
			if (maxLength * 3 > rectangle.width) {
				count = rectangle.width / 3;
			}
			int startX = rectangle.x + ((rectangle.width - (count * 3)) >> 1);
			int startY = rectangle.y + ((rectangle.height - (maxThickness * 3)) >> 1);
			for (int i = 0; i < maxThickness; i++) {
				for (int j = 0; j < count; j++) {
					if (isSelected) {
						g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
						g.drawLine(startX + j * 3, startY + i * 3, startX + j * 3, startY + i * 3);
					}
					g.setColor(UIDefaultsLookup.getColor("controlShadow"));
					g.drawLine(startX + j * 3 + 1, startY + i * 3 + 1, startX + j * 3 + 1, startY + i * 3 + 1);
				}
			}
		} else {
			int count = maxLength;
			if (maxLength * 3 > rectangle.height) {
				count = rectangle.height / 3;
			}
			int startX = rectangle.x + ((rectangle.width - (maxThickness * 3)) >> 1);
			int startY = rectangle.y + ((rectangle.height - (count * 3)) >> 1);
			for (int i = 0; i < maxThickness; i++) {
				for (int j = 0; j < count; j++) {
					if (isSelected) {
						g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
						g.drawLine(startX + i * 3, startY + j * 3, startX + i * 3, startY + j * 3);
					}
					g.setColor(UIDefaultsLookup.getColor("controlShadow"));
					g.drawLine(startX + i * 3 + 1, startY + j * 3 + 1, startX + i * 3 + 1, startY + j * 3 + 1);
				}
			}
		}
	}

	/**
	 * Register the tab key with the container.
	 *
	 * @param container
	 */
	public static void registerTabKey(Container container) {
		if (container instanceof JComponent) {
			((JComponent) container).registerKeyboardAction(new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					// JDK 1.3 Porting Hint
					// comment out for now
					DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
				}
			}, KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), JComponent.WHEN_FOCUSED);
		} else {
			for (int i = 0; i < container.getComponentCount(); i++) {
				Component c = container.getComponent(i);
				// JDK 1.3 Porting Hint
				// change to isFocusTraversable()
				if (c instanceof JComponent && c.isFocusable()) {
					((JComponent) container).registerKeyboardAction(new AbstractAction() {
						public void actionPerformed(ActionEvent e) {
							// JDK 1.3 Porting Hint
							// comment out for now
							DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
						}
					}, KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), JComponent.WHEN_FOCUSED);
				}
			}
		}
	}

	public static void fillGradient(Graphics g, Rectangle rect, int orientation) {
		Graphics2D g2d = (Graphics2D) g;
		// paint upper gradient
		Color col1 = new Color(255, 255, 255, 0);
		Color col2 = new Color(255, 255, 255, 48);
		Color col3 = new Color(0, 0, 0, 0);
		Color col4 = new Color(0, 0, 0, 32);

		if (orientation == SwingConstants.HORIZONTAL) {
			// paint upper gradient
			fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height >> 1), col2, col1, true);

			// paint lower gradient
			fillGradient(g2d, new Rectangle(rect.x, rect.y + (rect.height >> 1), rect.width, rect.height >> 1), col3, col4, true);
		} else {
			// paint left gradient
			fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width >> 1, rect.height), col2, col1, false);

			// paint right gradient
			fillGradient(g2d, new Rectangle(rect.x + (rect.width >> 1), rect.y, rect.width >> 1, rect.height), col3, col4, false);
		}
	}

	public static void fillSingleGradient(Graphics g, Rectangle rect, int orientation) {
		fillSingleGradient(g, rect, orientation, 127);
	}

	public static void fillSingleGradient(Graphics g, Rectangle rect, int orientation, int level) {
		Graphics2D g2d = (Graphics2D) g;
		Color col1 = new Color(255, 255, 255, 0);
		Color col2 = new Color(255, 255, 255, level);

		if (orientation == SwingConstants.SOUTH) {
			fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), col2, col1, true);
		} else if (orientation == SwingConstants.NORTH) {
			fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), col1, col2, true);
		} else if (orientation == SwingConstants.EAST) {
			fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), col2, col1, false);
		} else if (orientation == SwingConstants.WEST) {
			fillGradient(g2d, new Rectangle(rect.x, rect.y, rect.width, rect.height), col1, col2, false);
		}
	}

	private static Class<?> _radialGradientPaintClass;
	private static Constructor<?> _radialGradientPaintConstructor1;
	private static Constructor<?> _radialGradientPaintConstructor2;

	/**
	 * Gets the RadialGradientPaint. RadialGradientPaint is added after JDK6. If you are running JDK5 or before, you can
	 * include batik-awt-util.jar which also has a RadialGradientPaint class. This method will use reflection to
	 * determine if the RadialGradientPaint class is in the class path and use the one it can find.
	 */
	public static Paint getRadialGradientPaint(Point2D point, float radius, float[] fractions, Color[] colors) {
		Class<?> radialGradientPaintClass = null;
		try {
			radialGradientPaintClass = Class.forName("java.awt.RadialGradientPaint");
		} catch (ClassNotFoundException e1) {
			// ignore
		}
		if (radialGradientPaintClass != null) {
			try {
				if (_radialGradientPaintConstructor2 == null) {
					_radialGradientPaintConstructor2 = radialGradientPaintClass.getConstructor(Point2D.class, float.class, float[].class, Color[].class);
				}
				final Object radialGradientPaint = _radialGradientPaintConstructor2.newInstance(point, radius, fractions, colors);
				return (Paint) radialGradientPaint;
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
			         InvocationTargetException e) {
				// ignore
			}
		}

		System.err.println("Warning - radial gradients are only supported in Java 6 and higher or use batik-aw-util.jar, using a plain color instead"); //$NON-NLS-1$
		return colors[0];
	}

	/**
	 * Gets the RadialGradientPaint. RadialGradientPaint is added after JDK6. If you are running JDK5 or before, you can
	 * include batik-awt-util.jar which also has a RadialGradientPaint class. This method will use reflection to
	 * determine if the RadialGradientPaint class is in the class path and use the one it can find.
	 */
	public static Paint getRadialGradientPaint(float cx, float cy, float radius, float[] fractions, Color[] colors) {
		if (_radialGradientPaintClass == null) {
			try {
				_radialGradientPaintClass = Class.forName("java.awt.RadialGradientPaint");
			} catch (ClassNotFoundException e1) {
				// ignore
			}
		}
		if (_radialGradientPaintClass != null) {
			try {
				if (_radialGradientPaintConstructor1 == null) {
					_radialGradientPaintConstructor1 = _radialGradientPaintClass.getConstructor(float.class, float.class, float.class, float[].class, Color[].class);
				}
				final Object radialGradientPaint = _radialGradientPaintConstructor1.newInstance(cx, cy, radius, fractions, colors);
				return (Paint) radialGradientPaint;
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
			         InvocationTargetException e) {
				// ignore
			}
		}

		System.err.println("Warning - radial gradients are only supported in Java 6 and higher or use batik-aw-util.jar, using a plain color instead"); //$NON-NLS-1$
		return colors[0];
	}

	private static Class<?> _linearGradientPaintClass;
	private static Constructor<?> _linearGradientPaintConstructor1;
	private static Constructor<?> _linearGradientPaintConstructor2;

	/**
	 * Gets the LinearGradientPaint. LinearGradientPaint is added after JDK6. If you are running JDK5 or before, you can
	 * include batik-awt-util.jar which also has a LinearGradientPaint class. This method will use reflection to
	 * determine if the LinearGradientPaint class is in the class path and use the one it can find.
	 */
	public static Paint getLinearGradientPaint(float startX, float startY, float endX, float endY, float[] fractions, Color[] colors) {
		if (_linearGradientPaintClass == null) {
			try {
				_linearGradientPaintClass = Class.forName("java.awt.LinearGradientPaint");
			} catch (ClassNotFoundException e1) {
				// ignore
			}
		}
		if (_linearGradientPaintClass != null) {
			try {
				if (_linearGradientPaintConstructor1 == null) {
					_linearGradientPaintConstructor1 = _linearGradientPaintClass.getConstructor(float.class, float.class, float.class, float.class, float[].class, Color[].class);
				}
				final Object linearGradientPaint = _linearGradientPaintConstructor1.newInstance(startX, startY, endX, endY, fractions, colors);
				return (Paint) linearGradientPaint;
			} catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
			         InvocationTargetException e) {
				// ignore
			}
		}

		System.err.println("Warning - linear gradients are only supported in Java 6 and higher or use batik-aw-util.jar, using a plain color instead"); //$NON-NLS-1$
		return colors[0];
	}

	/**
	 * containerContainsFocus, does the specified container contain the current focusOwner?
	 *
	 * @param cont the specified container
	 * @return Is the current focusOwner a descendant of the specified container, or the container itself?
	 */

	public static boolean containerContainsFocus(Container cont) {
		Component focusOwner =
				KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		Component permFocusOwner =
				KeyboardFocusManager.getCurrentKeyboardFocusManager().getPermanentFocusOwner();
		boolean focusOwned;
		focusOwned = ((focusOwner != null) && SwingUtilities.isDescendingFrom(focusOwner, cont));
		if (!focusOwned) {
			focusOwned = ((permFocusOwner != null) &&
					SwingUtilities.isDescendingFrom(permFocusOwner, cont));
		}
		return focusOwned;
	}

//<syd_0002>

	public static boolean componentIsPermanentFocusOwner(Component comp) {
		return ((comp != null) && (KeyboardFocusManager.getCurrentKeyboardFocusManager().
				getPermanentFocusOwner() == comp));
	}

//</syd_0002>

	public static void installColorsAndFont(Component c,
	                                        Color background,
	                                        Color foreground,
	                                        Font font) {
		installFont(c, font);
		installColors(c, background, foreground);
	}

	public static void installFont(Component c, Font font) {
		Font f = c.getFont();
		if (f == null || f instanceof UIResource) {
			c.setFont(font);
		}
	}

	public static void installColors(Component c,
	                                 Color background, Color foreground) {
		Color bg = c.getBackground();
		if (background != null && (bg == null || bg instanceof UIResource)) {
			c.setBackground(background);
		}

		Color fg = c.getForeground();
		if (foreground != null && (fg == null || fg instanceof UIResource)) {
			c.setForeground(foreground);
		}
	}

	public static void installBorder(JComponent c, Border defaultBorder) {
		Border border = c.getBorder();
		if (border == null || border instanceof UIResource) {
			c.setBorder(defaultBorder);
		}
	}

	public static void fillNormalGradient(Graphics2D g2d, Shape s, Color startColor, Color endColor, boolean isVertical) {
		Rectangle rect = s.getBounds();
		GradientPaint paint;
		if (isVertical) {
			paint = new GradientPaint(rect.x, rect.y, startColor, rect.x, rect.height + rect.y, endColor, true); // turn cyclic to true will be faster
		} else {
			paint = new GradientPaint(rect.x, rect.y, startColor, rect.width + rect.x, rect.y, endColor, true);  // turn cyclic to true will be faster
		}
		Paint old = g2d.getPaint();
		g2d.setPaint(paint);
		g2d.fill(s);
		g2d.setPaint(old);
	}

	/**
	 * Fills a gradient using the startColor and endColor specified. This is a fast version of fill gradient which will
	 * not only leverage hardware acceleration, but also cache GradientPaint and reuse it.
	 * <p/>
	 * We also leave an option to use the normal GradientPaint to paint the gradient. To do so, just set a system
	 * property "normalGradientPaint" to "false".
	 *
	 * @param g2d
	 * @param s
	 * @param startColor
	 * @param endColor
	 * @param isVertical
	 */
	public static void fillGradient(Graphics2D g2d, Shape s, Color startColor, Color endColor, boolean isVertical) {
		if ("true".equals(SecurityUtils.getProperty("normalGradientPaint", "false"))) {
			fillNormalGradient(g2d, s, startColor, endColor, isVertical);
		} else {
			FastGradientPainter.drawGradient(g2d, s, startColor, endColor, isVertical);
		}
	}

	/**
	 * Clears the gradient cache used for fast gradient painting
	 */
	public static void clearGradientCache() {
		FastGradientPainter.clearGradientCache();
	}

	/**
	 * Gets the top modal dialog of current window.
	 *
	 * @param w
	 * @return the top modal dialog of current window.
	 */
	public static Window getTopModalDialog(Window w) {
		Window[] ws = w.getOwnedWindows();
		for (Window w1 : ws) {
			if (w1.isVisible() && w1 instanceof Dialog && ((Dialog) w1).isModal()) {
				return (getTopModalDialog(w1));
			}
		}
		return w;
	}

	protected static boolean tracingFocus = false;

	/**
	 * For internal usage only.
	 */
	public static void traceFocus() {
		traceFocus(false);
	}

	/**
	 * For internal usage only.
	 */
	public static void traceFocus(final boolean useBorders) {
		if (tracingFocus)
			return;
		PropertyChangeListener listener = evt -> {
			if (useBorders) {
				Component oldValue = (Component) evt.getOldValue();
				if (oldValue instanceof JComponent) {
					Border oldBorder = ((JComponent) oldValue).getBorder();
					if (oldBorder instanceof TraceDebugBorder)
						((JComponent) oldValue).setBorder(((TraceDebugBorder) oldBorder).getInsideBorder());
				}

				Component newValue = (Component) evt.getNewValue();
				if (newValue instanceof JComponent) {
					Border oldBorder = ((JComponent) newValue).getBorder();
					if (oldBorder == null)
						oldBorder = new EmptyBorder(0, 0, 0, 0);
					if (!(oldBorder instanceof TraceDebugBorder))
						((JComponent) newValue).setBorder(new TraceDebugBorder(oldBorder));
				}
			}

			String oldName = evt.getOldValue() == null ? "null" : evt.getOldValue().getClass().getName();
			if (evt.getOldValue() instanceof Component && ((Component) evt.getOldValue()).getName() != null)
				oldName = oldName + "'" + ((Component) evt.getOldValue()).getName() + "'";
			String newName = evt.getNewValue() == null ? "null" : evt.getNewValue().getClass().getName();
			if (evt.getNewValue() instanceof Component && ((Component) evt.getNewValue()).getName() != null)
				newName = newName + "'" + ((Component) evt.getNewValue()).getName() + "'";

			System.out.println(evt.getPropertyName() + ": " + oldName + " ==> " + newName);
		};
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", listener);
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("permanentFocusOwner", listener);
		DefaultKeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("activeWindow", listener);
		tracingFocus = true;
	}

	public static class TraceDebugBorder extends CompoundBorder {
		public TraceDebugBorder(Border insideBorder) {
			super(BorderFactory.createLineBorder(Color.RED, 1), insideBorder);
		}

		public Insets getBorderInsets(Component c) {
			return getInsideBorder().getBorderInsets(c);
		}

		public Insets getBorderInsets(Component c, Insets insets) {
			return getInsideBorder().getBorderInsets(c);
		}
	}


	public static void runGCAndPrintFreeMemory() {
		java.text.DecimalFormat memFormatter = new java.text.DecimalFormat("###,###,##0.####");
		String memFree = memFormatter
				.format(Runtime.getRuntime().freeMemory() / 1024);
		String memTotal = memFormatter
				.format(Runtime.getRuntime().totalMemory() / 1024);
		String memUsed = memFormatter
				.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
						.freeMemory()) / 1024);
		System.out.println("before gc: (Total [" + memTotal + "k] - Free ["
				+ memFree + "k]) = Used [" + memUsed + "k]");
		System.runFinalization();
		System.gc();
		try {
			// give the gc time.
			Thread.sleep(100);
		} catch (InterruptedException ie) {
		}
		memFree = memFormatter.format(Runtime.getRuntime().freeMemory() / 1024);
		memTotal = memFormatter.format(Runtime.getRuntime().totalMemory() / 1024);
		memUsed = memFormatter.format((Runtime.getRuntime().totalMemory() - Runtime
				.getRuntime().freeMemory()) / 1024);
		System.out.println("after gc: (Total [" + memTotal + "k] - Free ["
				+ memFree + "k]) = Used [" + memUsed + "k]");
	}

	/**
	 * For internal usage only.
	 */
	public static JPanel createTableModelModifier(final DefaultTableModel tableModel) {
		JPanel tableModelPanel = new JPanel(new BorderLayout(6, 6));
		final JTable table = new JTable(tableModel);
		tableModelPanel.add(new JScrollPane(table));
		ButtonPanel buttonPanel = new ButtonPanel();

		JButton insert = new JButton("Insert");
		insert.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Vector rowData = tableModel.getDataVector();
				int index = table.getSelectedRow();
				if (index != -1) {
					Vector v = (Vector) rowData.get(index);
					Vector clone = new Vector();
					for (int i = 0; i < v.size(); i++) {
						if (i == 0) {
							clone.add((int) (Math.random() * 10));
						} else {
							clone.add("" + v.get(i));
						}
					}
					tableModel.insertRow(index, clone);
				}
			}
		});

		JButton delete = new JButton("Delete");
		delete.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = table.getSelectedRows();
				for (int i = rows.length - 1; i >= 0; i--) {
					int row = rows[i];
					tableModel.removeRow(row);
				}
			}
		});

		JButton clear = new JButton("Clear");
		clear.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					tableModel.removeRow(0);
				}
			}
		});

		buttonPanel.add(insert);
		buttonPanel.add(delete);
		buttonPanel.add(clear);
		tableModelPanel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
		return tableModelPanel;
	}

	/**
	 * Find some subcomponent of the specified container that will accept focus.
	 * <p/>
	 * Note that this doesn't do something smart like trying to walk the hierarchy horizontally at each level so that
	 * the focused subcomponent is as high as possible. Rather, it drills vertically. It's just a safety valve so that
	 * focus can be requested somewhere rather than being lost.
	 *
	 * @param container
	 * @return a focusable subcomponent
	 */
	public static Component findSomethingFocusable(Container container) {
		if (passesFocusabilityTest(container)) {
			container.requestFocusInWindow();
			return container;
		}
		Component[] comps;
		Component comp;
		comps = container.getComponents();
		for (Component comp1 : comps) {
			if (passesFocusabilityTest(comp1)) {
				container.requestFocusInWindow();
				return container;
			} else if (comp1 instanceof Container) {
				comp = findSomethingFocusable((Container) (comp1));
				if (comp != null) {
					return comp;
				}
			}
		}
		return null;
	}

	/**
	 * There are four standard tests which determine if Swing will be able to request focus for a component. Test them.
	 *
	 * @param comp
	 * @return does the specified component pass the four focusability tests
	 */
	public static boolean passesFocusabilityTest(Component comp) {
		return ((comp != null) &&
				comp.isEnabled() && comp.isDisplayable() &&
				comp.isVisible() && comp.isFocusable() && comp.isShowing());
	}

	/**
	 * Ignore the exception. This method does nothing. However it's a good practice to use this method so that we can
	 * easily find out the place that ignoring exception. In development phase, we can log a message in this method so
	 * that we can verify if it makes sense to ignore.
	 *
	 * @param e
	 */
	public static void ignoreException(Exception e) {
	}

	/**
	 * Prints out the message of the exception.
	 *
	 * @param e
	 */
	public static void printException(Exception e) {
		System.err.println(e.getLocalizedMessage());
	}

	/**
	 * Throws the exception. If the exception is RuntimeException, just throw it. Otherwise, wrap it in RuntimeException
	 * and throw it.
	 *
	 * @param e
	 */
	public static void throwException(Exception e) {
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		} else {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Throws the InvocationTargetException. Usually InvocationTargetException has a nested exception as target
	 * exception. If the target exception is a RuntimeException or Error, we will throw it. Otherwise, we will wrap it
	 * inside RuntimeException and throw it.
	 *
	 * @param e
	 */
	public static void throwInvocationTargetException(InvocationTargetException e) {
		// in most cases, target exception will be RuntimeException
		// but to be on safer side (it may be Error) we explicitly check it
		if (e.getTargetException() instanceof RuntimeException) {
			throw (RuntimeException) e.getTargetException();
		} else if (e.getTargetException() instanceof Error) {
			throw (Error) e.getTargetException();
		} else {
			throw new RuntimeException(e.getTargetException());
		}
	}

	public static int findDisplayedMnemonicIndex(String text, int mnemonic) {
		if (text == null || mnemonic == '\0') {
			return -1;
		}

		char uc = Character.toUpperCase((char) mnemonic);
		char lc = Character.toLowerCase((char) mnemonic);

		int uci = text.indexOf(uc);
		int lci = text.indexOf(lc);

		if (uci == -1) {
			return lci;
		} else if (lci == -1) {
			return uci;
		} else {
			return Math.min(lci, uci);
		}
	}

	/**
	 * Gets the first occurrence of the component with specified type in the container. It used deep-first searching to
	 * find it.
	 *
	 * @param c
	 * @param container
	 * @return the first occurrence of the component with specified type in the container. Null if nothing is found.
	 */
	public static Component getDescendantOfClass(Class c, Container container) {
		if (container == null || c == null)
			return null;

		Component[] components = container.getComponents();

		for (Component component : components) {
			if (c.isInstance(component)) {
				return component;
			}
			if (component instanceof Container) {
				Component found = getDescendantOfClass(c, (Container) component);
				if (found != null) {
					return found;
				}
			}
		}
		return null;
	}

	public static float getDefaultFontSize() {
		// read the font size from system property.
		String fontSize = SecurityUtils.getProperty("jide.fontSize", null);
		float defaultFontSize = -1f;
		try {
			if (fontSize != null) {
				defaultFontSize = Float.parseFloat(fontSize);
			}
		} catch (NumberFormatException e) {
			// ignore
		}

		return defaultFontSize;
	}

	public static Object getMenuFont(Toolkit toolkit, UIDefaults table) {
		Object menuFont = null;
		// read the font size from system property.
		float defaultFontSize = getDefaultFontSize();

		if (defaultFontSize == -1/* || SystemInfo.isCJKLocale()*/) {
			menuFont = table.getFont("ToolBar.font");
		} else {
			menuFont = new WindowsDesktopProperty("win.menu.font", table.getFont("ToolBar.font"), toolkit, defaultFontSize);
		}

		if (menuFont == null) {
			return getControlFont(toolkit, table);
		} else {
			return menuFont;
		}
	}

	public static Object getControlFont(Toolkit toolkit, UIDefaults table, String defaultUIDefault) {
		Object controlFont;
		// read the font size from system property.
		float defaultFontSize = getDefaultFontSize();

		Font font = table.getFont(defaultUIDefault);
		if (font == null) {
			font = new Font("Tahoma", Font.PLAIN, 12); // use default font
		}
		if (defaultFontSize == -1/* || SystemInfo.isCJKLocale()*/) {
			controlFont = font;
		} else {
			controlFont = new WindowsDesktopProperty("win.defaultGUI.font", font, toolkit, defaultFontSize);
		}

		return controlFont;
	}

	public static Object getControlFont(Toolkit toolkit, UIDefaults table) {
		return getControlFont(toolkit, table, "Label.font");
	}

	public static Object getBoldFont(Toolkit toolkit, UIDefaults table) {
		if (SystemInfo.isCJKLocale()) {
			return getControlFont(toolkit, table);
		} else {
			Object boldFont;
			// read the font size from system property.
			float defaultFontSize = getDefaultFontSize();

			Font font = table.getFont("Label.font");
			if (font == null) {
				font = new Font("Tahoma", Font.PLAIN, 12); // use default font
			}
			if (defaultFontSize == -1) {
				boldFont = new FontUIResource(font.deriveFont(Font.BOLD));
			} else {
				boldFont = new WindowsDesktopProperty("win.defaultGUI.font", font, toolkit, defaultFontSize, Font.BOLD);
			}
			return boldFont;
		}
	}

	public static void drawShadow(Graphics g, Component c, int x, int y, int w, int h) {
		if (w <= 0 || h <= 0) {
			return;
		}
		ShadowFactory factory = new ShadowFactory(6, 0.7f, Color.GRAY);
		BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = temp.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, temp.getWidth(), temp.getHeight());
		g2.dispose();
		BufferedImage shadow = factory.createShadow(temp);
		g.drawImage(shadow, x, y, c);
	}

	static {
		Font.getFont("defaultFont");
		Font.getFont("emphasizedFont");
	}

//    public static void drawTileImage(Graphics g, ImageIcon img, Rectangle rect, Insets ins, boolean horizontal) {
//        int left = ins.left;
//        int right = ins.right;
//        int top = ins.top;
//        int bottom = ins.bottom;
//        int x = rect.x;
//        int y = rect.y;
//        int w = rect.width;
//        int h = rect.height;
//        int width = img.getIconWidth();
//        int height = img.getIconHeight();
//
//        if (horizontal) {
//            w += x;
//            while (x < w) {
//                int aw = (w - x) < width ? w - x : width;
//                g.drawImage(img.getImage(), x, y, x + aw, y + top,
//                        0, 0, aw, top, null);
//                g.drawImage(img.getImage(), x, y + top, x + aw, h - bottom,
//                        0, top, aw, height - bottom, null);
//                g.drawImage(img.getImage(), x, h - bottom, x + aw, h,
//                        0, height - bottom, aw, height, null);
//                x += aw;
//            }
//        }
//        else {
//            h += y;
//            while (y < h) {
//                int ah = (h - y) < height ? h - y : height;
//                g.drawImage(img.getImage(), x, y, x + left, y + ah,
//                        0, 0, left, ah, null);
//                g.drawImage(img.getImage(), x + left, y, x + width - right, y + ah,
//                        left, 0, width - right, ah, null);
//                g.drawImage(img.getImage(), x + width - right, y, x + width, y + ah,
//                        width - right, 0, width, ah, null);
//                y += height;
//            }
//        }
//    }

	/**
	 * Draws a border based on an image. The image can be divided into nine different areas. Each area size is
	 * determined by the insets.
	 */
	public static void drawImageBorder(Graphics g, ImageIcon img, Rectangle rect, Insets ins, boolean drawCenter) {
		int left = ins.left;
		int right = ins.right;
		int top = ins.top;
		int bottom = ins.bottom;
		int x = rect.x;
		int y = rect.y;
		int w = rect.width;
		int h = rect.height;

// top
		g.drawImage(img.getImage(), x, y, x + left, y + top,
				0, 0, left, top, null);
		g.drawImage(img.getImage(), x + left, y, x + w - right, y + top,
				left, 0, img.getIconWidth() - right, top, null);
		g.drawImage(img.getImage(), x + w - right, y, x + w, y + top,
				img.getIconWidth() - right, 0, img.getIconWidth(), top, null);

// middle
		g.drawImage(img.getImage(), x, y + top, x + left, y + h - bottom,
				0, top, left, img.getIconHeight() - bottom, null);
		g.drawImage(img.getImage(), x + left, y + top, x + w - right, y + h - bottom,
				left, top, img.getIconWidth() - right, img.getIconHeight() - bottom, null);
		g.drawImage(img.getImage(), x + w - right, y + top, x + w, y + h - bottom,
				img.getIconWidth() - right, top, img.getIconWidth(), img.getIconHeight() - bottom, null);

// bottom
		g.drawImage(img.getImage(), x, y + h - bottom, x + left, y + h,
				0, img.getIconHeight() - bottom, left, img.getIconHeight(), null);
		g.drawImage(img.getImage(), x + left, y + h - bottom, x + w - right, y + h,
				left, img.getIconHeight() - bottom, img.getIconWidth() - right, img.getIconHeight(), null);
		g.drawImage(img.getImage(), x + w - right, y + h - bottom, x + w, y + h,
				img.getIconWidth() - right, img.getIconHeight() - bottom, img.getIconWidth(), img.getIconHeight(), null);

		if (drawCenter) {
			g.drawImage(img.getImage(), x + left, y + top, x + w - right, y + h - bottom,
					left, top, img.getIconWidth() - right, img.getIconHeight() - bottom, null);
		}
	}

	/**
	 * Copied from BasicLookAndFeel as the method is package local.
	 *
	 * @param component
	 * @return if request focus is success or not.
	 */
	public static boolean compositeRequestFocus(Component component) {
		if (component instanceof Container container) {
			if (container.isFocusCycleRoot()) {
				FocusTraversalPolicy policy = container.getFocusTraversalPolicy();
				Component comp = policy.getDefaultComponent(container);

				if ((comp != null) && comp.isShowing() && container.getComponentCount() > 0) {
					return comp.requestFocusInWindow();
				}
			}
			Container rootAncestor = container.getFocusCycleRootAncestor();
			if (rootAncestor != null) {
				FocusTraversalPolicy policy = rootAncestor.getFocusTraversalPolicy();
				Component comp = null;
				try {
					comp = policy.getComponentAfter(rootAncestor, container);
				} catch (Exception e) {
					// ClassCastException when docking frames on Solaris
					// http://jidesoft.com/forum/viewtopic.php?p=32569
				}

				if (comp != null && SwingUtilities.isDescendingFrom(comp, container)) {
					return comp.requestFocusInWindow();
				}
			}
		}
		if (!passesFocusabilityTest(component)) {
			return false;
		}

		return component.requestFocusInWindow();
	}

	public static boolean isAncestorOfFocusOwner(Component component) {
		boolean hasFocus = false;
		Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		if (component == focusOwner || (component instanceof Container && ((Container) component).isAncestorOf(focusOwner))) {
			hasFocus = true;
		}
		return hasFocus;
	}

	/**
	 * Gets the top level Dialog or Frame of the component.
	 *
	 * @param parentComponent
	 * @return the top level Frame or Dialog. Null if we didn't find an ancestor which is instance of Frame.
	 */
	public static Window getWindowForComponent(Component parentComponent)
			throws HeadlessException {
		if (parentComponent == null)
			return JOptionPane.getRootFrame();
		if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
			return (Window) parentComponent;
		return getWindowForComponent(parentComponent.getParent());
	}

	/**
	 * Checks if the key listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isKeyListenerRegistered(Component component, KeyListener l) {
		KeyListener[] listeners = component.getKeyListeners();
		for (KeyListener listener : listeners) {
			if (listener == l) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inserts the key listener at the particular index in the listeners' chain.
	 *
	 * @param component
	 * @param l
	 * @param index
	 */
	public static void insertKeyListener(Component component, KeyListener l, int index) {
		KeyListener[] listeners = component.getKeyListeners();
		for (KeyListener listener : listeners) {
			component.removeKeyListener(listener);
		}
		for (int i = 0; i < listeners.length; i++) {
			KeyListener listener = listeners[i];
			if (index == i) {
				component.addKeyListener(l);
			}
			component.addKeyListener(listener);
		}
		// index is too large, add to the end.
		if (index > listeners.length - 1) {
			component.addKeyListener(l);
		}
	}

	/**
	 * Inserts the table model listener at the particular index in the listeners' chain. The listeners are fired in
	 * reverse order. So the listener at index 0 will be fired at last.
	 *
	 * @param model the AbstractTableModel
	 * @param l     the TableModelListener to be inserted
	 * @param index the index.
	 */
	public static void insertTableModelListener(TableModel model, TableModelListener l, int index) {
		if (!(model instanceof AbstractTableModel)) {
			model.addTableModelListener(l);
			return;
		}
		TableModelListener[] listeners = ((AbstractTableModel) model).getTableModelListeners();
		for (TableModelListener listener : listeners) {
			model.removeTableModelListener(listener);
		}
		for (int i = 0; i < listeners.length; i++) {
			TableModelListener listener = listeners[i];
			if (index == i) {
				model.addTableModelListener(l);
			}
			model.addTableModelListener(listener);
		}
		// index is too large, add to the end.
		if (index < 0 || index > listeners.length - 1) {
			model.addTableModelListener(l);
		}
	}

	/**
	 * Inserts the property change listener at the particular index in the listeners' chain.
	 *
	 * @param component    the component where the listener will be inserted.
	 * @param l            the listener to be inserted
	 * @param propertyName the name of the property. Could be null.
	 * @param index        the index to be inserted
	 */
	public static void insertPropertyChangeListener(Component component, PropertyChangeListener l, String propertyName, int index) {
		PropertyChangeListener[] listeners = propertyName == null ? component.getPropertyChangeListeners() : component.getPropertyChangeListeners(propertyName);
		for (PropertyChangeListener listener : listeners) {
			if (propertyName == null) {
				component.removePropertyChangeListener(listener);
			} else {
				component.removePropertyChangeListener(propertyName, listener);
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			PropertyChangeListener listener = listeners[i];
			if (index == i) {
				if (propertyName == null) {
					component.addPropertyChangeListener(l);
				} else {
					component.addPropertyChangeListener(propertyName, l);
				}
			}
			if (propertyName == null) {
				component.addPropertyChangeListener(listener);
			} else {
				component.addPropertyChangeListener(propertyName, listener);
			}
		}
		// index is too large, add to the end.
		if (index > listeners.length - 1) {
			if (propertyName == null) {
				component.addPropertyChangeListener(l);
			} else {
				component.addPropertyChangeListener(propertyName, l);
			}
		}
	}

	/**
	 * Inserts the property change listener at the particular index in the listeners' chain.
	 *
	 * @param manager      the KeyboardFocusManager where the listener will be inserted.
	 * @param l            the listener to be inserted
	 * @param propertyName the name of the property. Could be null.
	 * @param index        the index to be inserted
	 */
	public static void insertPropertyChangeListener(KeyboardFocusManager manager, PropertyChangeListener l, String propertyName, int index) {
		PropertyChangeListener[] listeners = propertyName == null ? manager.getPropertyChangeListeners() : manager.getPropertyChangeListeners(propertyName);
		for (PropertyChangeListener listener : listeners) {
			if (propertyName == null) {
				manager.removePropertyChangeListener(listener);
			} else {
				manager.removePropertyChangeListener(propertyName, listener);
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			PropertyChangeListener listener = listeners[i];
			if (index == i) {
				if (propertyName == null) {
					manager.addPropertyChangeListener(l);
				} else {
					manager.addPropertyChangeListener(propertyName, l);
				}
			}
			if (propertyName == null) {
				manager.addPropertyChangeListener(listener);
			} else {
				manager.addPropertyChangeListener(propertyName, listener);
			}
		}
		// index is too large, add to the end.
		if (index > listeners.length - 1) {
			if (propertyName == null) {
				manager.addPropertyChangeListener(l);
			} else {
				manager.addPropertyChangeListener(propertyName, l);
			}
		}
	}

	/**
	 * Checks if the property change listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isPropertyChangeListenerRegistered(Component component, PropertyChangeListener l) {
		PropertyChangeListener[] listeners = component.getPropertyChangeListeners();
		for (PropertyChangeListener listener : listeners) {
			if (listener == l) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the property change listener is already registered on the component.
	 *
	 * @param component    the component
	 * @param propertyName the property name
	 * @param l            the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isPropertyChangeListenerRegistered(Component component, String propertyName, PropertyChangeListener l) {
		if (propertyName == null) {
			return isPropertyChangeListenerRegistered(component, l);
		}
		PropertyChangeListener[] listeners = component.getPropertyChangeListeners(propertyName);
		for (PropertyChangeListener listener : listeners) {
			if (listener == l) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the mouse listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isMouseListenerRegistered(Component component, MouseListener l) {
		MouseListener[] listeners = component.getMouseListeners();
		for (MouseListener listener : listeners) {
			if (listener == l) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inserts the mouse listener at the particular index in the listeners' chain.
	 *
	 * @param component
	 * @param l
	 * @param index
	 */
	public static void insertMouseListener(Component component, MouseListener l, int index) {
		MouseListener[] listeners = component.getMouseListeners();
		for (MouseListener listener : listeners) {
			component.removeMouseListener(listener);
		}
		for (int i = 0; i < listeners.length; i++) {
			MouseListener listener = listeners[i];
			if (index == i) {
				component.addMouseListener(l);
			}
			component.addMouseListener(listener);
		}
		// index is too large, add to the end.
		if (index < 0 || index > listeners.length - 1) {
			component.addMouseListener(l);
		}
	}

	/**
	 * Checks if the mouse motion listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isMouseMotionListenerRegistered(Component component, MouseMotionListener l) {
		MouseMotionListener[] listeners = component.getMouseMotionListeners();
		for (MouseMotionListener listener : listeners) {
			if (listener == l) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inserts the mouse motion listener at the particular index in the listeners' chain.
	 *
	 * @param component
	 * @param l
	 * @param index
	 */
	public static void insertMouseMotionListener(Component component, MouseMotionListener l, int index) {
		MouseMotionListener[] listeners = component.getMouseMotionListeners();
		for (MouseMotionListener listener : listeners) {
			component.removeMouseMotionListener(listener);
		}
		for (int i = 0; i < listeners.length; i++) {
			MouseMotionListener listener = listeners[i];
			if (index == i) {
				component.addMouseMotionListener(l);
			}
			component.addMouseMotionListener(listener);
		}
		// index is too large, add to the end.
		if (index < 0 || index > listeners.length - 1) {
			component.addMouseMotionListener(l);
		}
	}

	/**
	 * Gets the scroll pane around the component.
	 *
	 * @param innerComponent
	 * @return the scroll pane. Null if the component is not in any JScrollPane.
	 */
	public static Component getScrollPane(Component innerComponent) {
		Component component = innerComponent;
		if (innerComponent instanceof JScrollPane) {
			return innerComponent;
		}
		if (component.getParent() != null && component.getParent().getParent() != null && component.getParent().getParent() instanceof JScrollPane) {
			component = component.getParent().getParent();
			return component;
		} else {
			return null;
		}
	}

	/**
	 * Checks if the listener is always registered to the EventListenerList to avoid duplicated registration of the same
	 * listener
	 *
	 * @param list the EventListenerList to register the listener.
	 * @param t    the type of the EventListener.
	 * @param l    the listener.
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isListenerRegistered(EventListenerList list, Class t, EventListener l) {
		Object[] objects = list.getListenerList();
		return isListenerRegistered(objects, t, l);
	}

	/**
	 * Checks if the listener is always registered to the Component to avoid duplicated registration of the same
	 * listener
	 *
	 * @param component the component that you want to register the listener.
	 * @param t         the type of the EventListener.
	 * @param l         the listener.
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isListenerRegistered(Component component, Class t, EventListener l) {
		Object[] objects = component.getListeners(t);
		return isListenerRegistered(objects, t, l);
	}

	private static boolean isListenerRegistered(Object[] objects, Class t, EventListener l) {
		for (int i = objects.length - 2; i >= 0; i -= 2) {
			if ((objects[i] == t) && (objects[i + 1].equals(l))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the first child of the component that is the specified type.
	 *
	 * @param clazz the type of the component to look for
	 * @param c     the component
	 * @return the first child of the component that is the specified type.
	 */
	public static Component getFirstChildOf(final Class<?> clazz, Component c) {
		return getRecursively(c, new GetHandler() {
			public boolean condition(Component c) {
				return clazz.isAssignableFrom(c.getClass());
			}

			public Component action(Component c) {
				return c;
			}
		});
	}

	/**
	 * Get the index of the component in the container. It will return -1 if c's parent is not container.
	 *
	 * @param container the container
	 * @param c         the component
	 * @return the index
	 */
	public static int getComponentIndex(Container container, Component c) {
		if (c.getParent() != container) {
			return -1;
		}
		Component[] children = container.getComponents();
		for (int i = 0; i < children.length; i++) {
			if (children[i] == c) {
				return i;
			}
		}
		return -1;
	}


	public static Vector convertDefaultComboBoxModelToVector(DefaultComboBoxModel model) {
		Vector v = new Vector();
		for (int i = 0; i < model.getSize(); i++) {
			v.add(model.getElementAt(i));
		}
		return v;

	}

	/**
	 * To make sure the row is visible. If the table's horizontal scroll bar is visible, the method will not change the
	 * horizontal scroll bar's position.
	 *
	 * @param table
	 * @param row
	 */
	public static void ensureRowVisible(JTable table, int row) {
		Rectangle r = table.getVisibleRect();
// Hack! make above and below visible if necessary
// TODO: how to center it or make it the first?
		Rectangle rMid = table.getCellRect(row, 0, true);
		Rectangle rBefore = null, rAfter = null;
		if (row < table.getModel().getRowCount() - 1)
			rAfter = table.getCellRect(row + 1, 0, true);
		if (row > 0)
			rBefore = table.getCellRect(row - 1, 0, true);

		int yLow = (int) rMid.getMinY();
		int yHi = (int) rMid.getMaxY();
		int xLow = r.x;
		int xHi = r.x + r.width;

		if (rBefore != null)
			yLow = (int) rBefore.getMinY();

		if (rAfter != null) {
			yHi = (int) rAfter.getMaxY();
		}

		Rectangle rScrollTo = new Rectangle(xLow, yLow, xHi - xLow, yHi - yLow);
		if (!r.contains(rScrollTo) && rScrollTo.height != 0) {
			table.scrollRectToVisible(rScrollTo);
		}
	}

	public static void retargetMouseEvent(int id, MouseEvent e, Component target) {
		if (target == null || (target == e.getSource() && id == e.getID())) {
			return;
		}
		if (e.isConsumed()) {
			return;
		}

		// fix for bug #4202966 -- hania
		// When re-targeting a mouse event, we need to translate
		// the event's coordinates relative to the target.

		Point p = SwingUtilities.convertPoint((Component) e.getSource(),
				e.getX(), e.getY(),
				target);
		MouseEvent retargeted = new MouseEvent(target,
				id,
				e.getWhen(),
				e.getModifiersEx() | e.getModifiersEx(),
				p.x,
				p.y,
				e.getClickCount(),
				e.isPopupTrigger(),
				e.getButton());
		target.dispatchEvent(retargeted);
	}

	/**
	 * If c is a JRootPane descendant return its outermost JRootPane ancestor. If c is a RootPaneContainer then return
	 * its JRootPane.
	 *
	 * @param c the component.
	 * @return the outermost JRootPane for Component c or {@code null}.
	 */
	public static JRootPane getOutermostRootPane(Component c) {
		if (c instanceof RootPaneContainer && c.getParent() == null) {
			return ((RootPaneContainer) c).getRootPane();
		}
		JRootPane lastRootPane;
		for (; c != null; c = SwingUtilities.getRootPane(c)) {
			if (c instanceof JRootPane) {
				lastRootPane = (JRootPane) c;
				if (c.getParent().getParent() == null) {
					return lastRootPane;
				}
				if (c.getParent() instanceof JDialog || c.getParent() instanceof JWindow
						|| c.getParent() instanceof JFrame) {
					return lastRootPane;
				}
				c = c.getParent().getParent();
			}
		}
		return null;
	}

	/**
	 * Checks if the font specified by the font name is fixed width font. Fixed width font means all chars have the
	 * exact same width.
	 *
	 * @param fontName  the font name
	 * @param component the component where the font will be displayed.
	 * @return true if the font is fixed width. Otherwise false.
	 */
	public static boolean isFixedWidthFont(String fontName, Component component) {
		if (fontName.endsWith(" Bold") || fontName.endsWith(" ITC") || fontName.endsWith(" MT") || fontName.endsWith(" LET")
				|| fontName.endsWith(".bold") || fontName.endsWith(".italic"))
			return false;
		try {
			Font font = new Font(fontName, 0, 12);
			if (!font.canDisplay('W'))
				return false;
			Font boldFont = font.deriveFont(Font.BOLD);
			FontMetrics fm = component.getFontMetrics(font);
			FontMetrics fmBold = component.getFontMetrics(boldFont);
			int l1 = fm.charWidth('l');
			int l2 = fmBold.charWidth('l');
			if (l1 == l2) {
				int w1 = fm.charWidth('W');
				int w2 = fmBold.charWidth('W');
				if (w1 == w2 && l1 == w1) {
					int s1 = fm.charWidth(' ');
					int s2 = fmBold.charWidth(' ');
					if (s1 == s2) {
						return true;
					}
				}
			}
		} catch (Throwable throwable) {
			// ignore it and return false
		}
		return false;
	}

	/**
	 * Sets the locale recursively on the component and all its child components if any.
	 *
	 * @param c      the component
	 * @param locale the new locales.
	 */
	public static void setLocaleRecursively(final Component c, final Locale locale) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return true;
			}

			public void action(Component c) {
				c.setLocale(locale);
			}

			public void postAction(Component c) {

			}
		});
	}

	/**
	 * Sets the bounds. If the container orientation is from right to left, this method will adjust the x to the
	 * opposite.
	 *
	 * @param container the container. It is usually the parent of the component.
	 * @param component the component to set bounds
	 * @param bounds    the bounds.
	 */
	public static void setBounds(Container container, Component component, Rectangle bounds) {
		if (container.getComponentOrientation().isLeftToRight()) {
			component.setBounds(bounds);
		} else {
			Rectangle r = new Rectangle(bounds);
			int w = container.getWidth();
			r.x = w - (bounds.x + bounds.width);
			component.setBounds(r);
		}
	}

	/**
	 * Sets the bounds. If the container orientation is from right to left, this method will adjust the x to the
	 * opposite.
	 *
	 * @param container the container. It is usually the parent of the component.
	 * @param component the component to set bounds
	 * @param x         the x of the bounds
	 * @param y         the y of the bounds
	 * @param width     the the height of the bounds. of the bounds.
	 * @param height    the height of the bounds.
	 */
	public static void setBounds(Container container, Component component, int x, int y, int width, int height) {
		if (container.getComponentOrientation().isLeftToRight()) {
			component.setBounds(x, y, width, height);
		} else {
			int w = container.getWidth();
			component.setBounds(w - x - width, y, width, height);
		}
	}

	/**
	 * Invalidate and doLayout on the component and all its child components if any.
	 *
	 * @param c the component
	 */
	public static void invalidateRecursively(final Component c) {
		if (c instanceof JComponent) {
			setRecursively(c, new Handler() {
				public boolean condition(Component c) {
					return true;
				}

				public void action(Component c) {
					if (c instanceof JComponent) c.revalidate();
					c.invalidate();
				}

				public void postAction(Component c) {
				}
			});
		}
		c.doLayout();
		c.repaint();
	}

	/**
	 * Registers all actions registered on the source component and registered them on the target component at the
	 * specified condition.
	 *
	 * @param sourceComponent the source component.
	 * @param targetComponent the target component.
	 * @param keyStrokes      the keystrokes
	 * @param condition       the condition which will be used in {@link JComponent#registerKeyboardAction(ActionListener,
	 *                        KeyStroke, int)} as the last parameter.
	 */
	public static void synchronizeKeyboardActions(JComponent sourceComponent, JComponent targetComponent, KeyStroke[] keyStrokes, int condition) {
		for (KeyStroke keyStroke : keyStrokes) {
			ActionListener actionListener = sourceComponent.getActionForKeyStroke(keyStroke);
			if (actionListener != null) {
				targetComponent.registerKeyboardAction(actionListener, keyStroke, condition);
			}
		}
	}

	/**
	 * Gets the first JComponent from the RootPaneContainer.
	 *
	 * @param rootPaneContainer a rootPaneContainer
	 * @return the first JComponent from the rootPaneContainer's content pane.
	 */
	public static JComponent getFirstJComponent(RootPaneContainer rootPaneContainer) {
		return (JComponent) getRecursively(rootPaneContainer.getContentPane(), new GetHandler() {
			public boolean condition(Component c) {
				return c instanceof JComponent;
			}

			public Component action(Component c) {
				return c;
			}
		});
	}


	/**
	 * This method can be used to fix two JDK bugs. One is to fix the row height is wrong when the first element in the
	 * model is null or empty string. The second bug is only on JDK1.4.2 where the vertical scroll bar is shown even all
	 * rows are visible. To use it, you just need to override JList#getPreferredScrollableViewportSize and call this
	 * method.
	 * <pre><code>
	 * public Dimension getPreferredScrollableViewportSize() {
	 *    return JideSwingUtilities.adjustPreferredScrollableViewportSize(this, super.getPreferredScrollableViewportSize());
	 * }
	 * <p/>
	 * </code></pre>
	 *
	 * @param list                the JList
	 * @param defaultViewportSize the default viewport size from JList#getPreferredScrollableViewportSize().
	 * @return the adjusted size.
	 */
	public static Dimension adjustPreferredScrollableViewportSize(JList list, Dimension defaultViewportSize) {
		// workaround the bug that the list is tiny when the first element is empty
		Rectangle cellBonds = list.getCellBounds(0, 0);
		if (cellBonds != null && cellBonds.height < 3) {
			ListCellRenderer renderer = list.getCellRenderer();
			if (renderer != null) {
				Component c = renderer.getListCellRendererComponent(list, "DUMMY STRING", 0, false, false);
				if (c != null) {
					Dimension preferredSize = c.getPreferredSize();
					if (preferredSize != null) {
						int height = preferredSize.height;
						if (height < 3) {
							try {
								height = list.getCellBounds(1, 1).height;
							} catch (Exception e) {
								height = 16;
							}
						}
						list.setFixedCellHeight(height);
					}
				}
			}
		}
		return defaultViewportSize;
	}

	/**
	 * The semantics in AWT of hiding a component, removing a component, and reparenting a component are inconsistent
	 * with respect to focus. By calling this function before any of the operations above focus is guaranteed a
	 * consistent degregation.
	 *
	 * @param component
	 */
	public static void removeFromParentWithFocusTransfer(Component component) {
		boolean wasVisible = component.isVisible();
		component.setVisible(false);
		if (component.getParent() != null) {
			component.getParent().remove(component);
		}
		component.setVisible(wasVisible);
	}

	/**
	 * Gets the line height for the font for the component
	 *
	 * @param c             the component
	 * @param defaultHeight the default height if the font on the specified component is null
	 * @return the line height for the font for the component (or the passed in the default value if the font on the
	 * specified component is null)
	 */
	public static int getLineHeight(Component c, int defaultHeight) {
		Font f = c == null ? null : c.getFont();
		if (f == null) {
			return defaultHeight;
		}
		FontMetrics fm = c.getFontMetrics(f);
		float h = fm.getHeight();

		h += fm.getDescent();

		return (int) h;
	}

	/**
	 * Adds a separator to the popup menu if there are menu items on it already.
	 *
	 * @param popup the popup menu.
	 */
	public static void addSeparatorIfNecessary(JPopupMenu popup) {
		int count = popup.getComponentCount();
		if (count > 0 && !(popup.getComponent(count - 1) instanceof JSeparator)) {
			popup.addSeparator();
		}
	}

	/**
	 * Removes extra separators, if any. This can be used when you remove some menu items and leave extra separators on
	 * the UI.
	 *
	 * @param popup the popup menu.
	 */
	public static void removeExtraSeparators(JPopupMenu popup) {
		Component[] components = popup.getComponents();
		if (components.length <= 1) {
			return;
		}
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			if (component instanceof JSeparator) {
				if (i == 0 || i == components.length - 1) { // if the separator is the first one or the last one, remove it because the separator is not necessary here
					popup.remove(component);
				} else if (components[i - 1] instanceof JSeparator) {
					popup.remove(component);
				}
			}
		}
	}

	/**
	 * Sets the text component transparent. It will call setOpaque(false) and also set client property for certain L&Fs
	 * in case the L&F doesn't respect the opaque flag.
	 *
	 * @param component the text component to be set to transparent.
	 * @deprecated replaced by {@link #setComponentTransparent(JComponent)}.
	 */
	@Deprecated
	public static void setTextComponentTransparent(JComponent component) {
		setComponentTransparent(component);
	}

	/**
	 * Sets the text component transparent. It will call setOpaque(false) and also set client property for certain L&Fs
	 * in case the L&F doesn't respect the opaque flag.
	 *
	 * @param component the text component to be set to transparent.
	 */
	public static void setComponentTransparent(JComponent component) {
		component.setOpaque(false);

// add this for the Synthetica
		component.putClientProperty("Synthetica.opaque", false);
// add this for Nimbus to disable all the painting of a component in Nimbus
		component.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
		component.putClientProperty("Nimbus.Overrides", new UIDefaults());

	}

	/**
	 * Perform a binary search over a sorted list for the given key.
	 *
	 * @param a   the array to search
	 * @param key the key to search for
	 * @return the index of the given key if it exists in the list, otherwise -1 times the index value at the insertion
	 * point that would be used if the key were added to the list.
	 */
	public static <T> int binarySearch(List<T> a, T key) {
		int x1 = 0;
		int x2 = a.size();
		int i = x2 / 2, c;
		while (x1 < x2) {
			if (!(a.get(i) instanceof Comparable)) {
				return i;
			}
			c = ((Comparable) a.get(i)).compareTo(key);
			if (c == 0) {
				return i;
			} else if (c < 0) {
				x1 = i + 1;
			} else {
				x2 = i;
			}
			i = x1 + (x2 - x1) / 2;
		}
		return -1 * i;
	}

	/**
	 * Perform a binary search over a sorted array for the given key.
	 *
	 * @param a   the array to search
	 * @param key the key to search for
	 * @return the index of the given key if it exists in the array, otherwise -1 times the index value at the insertion
	 * point that would be used if the key were added to the array.
	 */
	public static <T> int binarySearch(T[] a, T key) {
		int x1 = 0;
		int x2 = a.length;
		int i = x2 / 2, c;
		while (x1 < x2) {
			if (!(a[i] instanceof Comparable)) {
				return i;
			}
			c = ((Comparable) a[i]).compareTo(key);
			if (c == 0) {
				return i;
			} else if (c < 0) {
				x1 = i + 1;
			} else {
				x2 = i;
			}
			i = x1 + (x2 - x1) / 2;
		}
		return -1 * i;
	}

	/**
	 * Perform a binary search over a sorted array for the given key.
	 *
	 * @param a   the array to search
	 * @param key the key to search for
	 * @return the index of the given key if it exists in the array, otherwise -1 times the index value at the insertion
	 * point that would be used if the key were added to the array.
	 */
	public static int binarySearch(int[] a, int key) {
		return binarySearch(a, key, 0, a.length);
	}

	/**
	 * Perform a binary search over a sorted array for the given key.
	 *
	 * @param a     the array to search
	 * @param key   the key to search for
	 * @param start the start index to search inclusive
	 * @param end   the end index to search exclusive
	 * @return the index of the given key if it exists in the array, otherwise -1 times the index value at the insertion
	 * point that would be used if the key were added to the array.
	 */
	public static int binarySearch(int[] a, int key, int start, int end) {
		int x1 = start;
		int x2 = end;
		int i = x2 / 2;
		while (x1 < x2) {
			if (a[i] == key) {
				return i;
			} else if (a[i] < key) {
				x1 = i + 1;
			} else {
				x2 = i;
			}
			i = x1 + (x2 - x1) / 2;
		}
		return -1 * i;
	}

	/**
	 * Checks if the ctrl key is pressed. On Mac oS X, it will be command key.
	 *
	 * @param event the InputEvent.
	 * @return true or false.
	 */
	public static boolean isMenuShortcutKeyDown(InputEvent event) {
		return (event.getModifiersEx() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()) != 0;
	}

	/**
	 * Checks if the ctrl key is pressed. On Mac oS X, it will be command key.
	 *
	 * @param event the InputEvent.
	 * @return true or false.
	 */
	public static boolean isMenuShortcutKeyDown(ActionEvent event) {
		return (event.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()) != 0;
	}

	private static ChangeListener _viewportSyncListener;

	/** 获取视口同步更改侦听器 */
	public static ChangeListener getViewportSynchronizationChangeListener() {
		if (_viewportSyncListener == null) {
			_viewportSyncListener = new ViewportSynchronizationChangeListener();
		}
		return _viewportSyncListener;
	}


	/**
	 * Sets the Window opaque using AWTUtilities.setWindowOpaque on JDK6u10 and later.
	 *
	 * @param window the Window
	 * @param opaque true or false
	 */
	public static void setWindowOpaque(Window window, boolean opaque) {
		try {
			Class<?> c = Class.forName("com.sun.awt.AWTUtilities");
			Method m = c.getMethod("setWindowOpaque", Window.class, boolean.class);
			m.invoke(null, window, opaque);
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * Sets the Window opacity using AWTUtilities.setWindowOpacity on JDK6u10 and later.
	 *
	 * @param window  the Window
	 * @param opacity the opacity
	 */
	public static void setWindowOpacity(Window window, float opacity) {
		try {
			Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
			Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);
			mSetWindowOpacity.invoke(null, window, opacity);
		} catch (Exception ex) {
			// ignore
		}
	}

	/**
	 * Sets the Window shape using AWTUtilities.setWindowOpacity on JDK6u10 and later.
	 *
	 * @param window the Window
	 * @param shape  the shape
	 */
	public static void setWindowShape(Window window, Shape shape) {
		try {
			Class<?> c = Class.forName("com.sun.awt.AWTUtilities");
			Method m = c.getMethod("setWindowShape", Window.class, Shape.class);
			m.invoke(null, window, shape);
		} catch (Exception e) {
			// ignore
		}
	}

	/**
	 * Gets the string representing OK button.
	 *
	 * @param locale the locale
	 * @return the string.
	 * @since 3.3.8
	 */
	public static String getOKString(Locale locale) {
		String text = UIDefaultsLookup.getString("OptionPane.okButtonText", locale);
		if (text == null || text.length() <= 0) {
			text = UIDefaultsLookup.getString("ColorChooser.okText");
			if (text == null || text.length() <= 0) {
				text = ButtonResources.getResourceBundle(locale).getString("Button.ok");
			}
		}
		return text;
	}

	/**
	 * Gets the string representing Cancel button.
	 *
	 * @param locale the locale
	 * @return the string.
	 * @since 3.3.8
	 */
	public static String getCancelString(Locale locale) {
		String text = UIDefaultsLookup.getString("OptionPane.cancelButtonText", locale);
		if (text == null || text.length() <= 0) {
			text = UIDefaultsLookup.getString("ColorChooser.cancelText");
			if (text == null || text.length() <= 0) {
				text = ButtonResources.getResourceBundle(locale).getString("Button.cancel");
			}
		}
		return text;
	}

	/**
	 * Gets the string representing Yes button.
	 *
	 * @param locale the locale
	 * @return the string.
	 * @since 3.3.8
	 */
	public static String getYesString(Locale locale) {
		String text = UIDefaultsLookup.getString("OptionPane.yesButtonText", locale);
		if (text == null || text.length() <= 0) {
			text = ButtonResources.getResourceBundle(locale).getString("Button.yes");
		}
		return text;
	}

	/**
	 * Gets the string representing No button.
	 *
	 * @param locale the locale
	 * @return the string.
	 * @since 3.3.8
	 */
	public static String getNoString(Locale locale) {
		String text = UIDefaultsLookup.getString("OptionPane.noButtonText", locale);
		if (text == null || text.length() <= 0) {
			text = ButtonResources.getResourceBundle(locale).getString("Button.no");
		}
		return text;
	}

	/**
	 * Copied from JDK's SwingUtilities2.java
	 * <p/>
	 * Returns the FontMetrics for the current Font of the passed in Graphics.  This method is used when a Graphics is
	 * available, typically when painting.  If a Graphics is not available the JComponent method of the same name should
	 * be used.
	 * <p/>
	 * Callers should pass in a non-null JComponent, the exception to this is if a JComponent is not readily available
	 * at the time of painting.
	 * <p/>
	 * This does not necessarily return the FontMetrics from the Graphics.
	 *
	 * @param c JComponent requesting FontMetrics, may be null
	 * @param g Graphics Graphics
	 */
	public static FontMetrics getFontMetrics(JComponent c, Graphics g) {
		return getFontMetrics(c, g, g.getFont());
	}


	/**
	 * Copied from JDK's SwingUtilities2.java
	 * <p/>
	 * Returns the FontMetrics for the specified Font. This method is used when a Graphics is available, typically when
	 * painting.  If a Graphics is not available the JComponent method of the same name should be used.
	 * <p/>
	 * Callers should pass in a non-null JComponent, the exception to this is if a JComponent is not readily available
	 * at the time of painting.
	 * <p/>
	 * This does not necessarily return the FontMetrics from the Graphics.
	 *
	 * @param c    JComponent requesting FontMetrics, may be null
	 * @param c    Graphics Graphics
	 * @param font Font to get FontMetrics for
	 */
	public static FontMetrics getFontMetrics(JComponent c, Graphics g,
	                                         Font font) {
		if (c != null) {
			// Note: We assume that we're using the FontMetrics
			// from the widget to layout out text, otherwise we can get
			// mismatches when printing.
			return c.getFontMetrics(font);
		}
		return Toolkit.getDefaultToolkit().getFontMetrics(font);
	}

	/**
	 * Shows the popup menu with the consideration of the invoker's orientation.
	 *
	 * @param popup   the popup menu
	 * @param invoker the invoker for the popup menu
	 * @param x       the x, usually the x of the mouse clicked position
	 * @param y       the y, usually the y of the mouse clicked position
	 */
	public static void showPopupMenu(JPopupMenu popup, Component invoker, int x, int y) {
		popup.applyComponentOrientation(invoker.getComponentOrientation());
		if (popup.getComponentOrientation().isLeftToRight()) {
			popup.show(invoker, x, y);
		} else {
			popup.show(invoker, x - popup.getPreferredSize().width, y);
		}

	}

	public static final boolean JETBRAINS_JRE = System.getProperty("java.vendor").toLowerCase().contains("jetbrains");

	public static Double cachedScaleFactor = null;

	public static boolean isIntegerScaleFactor(Graphics2D g) {
		double scaleFactor = getScaleFactor(g);
		return Math.floor(scaleFactor) == scaleFactor;
	}

	public static double getScaleFactor(Graphics2D g) {
		if (g == null) {
			throw new NullPointerException("graphics is null");
		}
		GraphicsConfiguration deviceConfiguration = g.getDeviceConfiguration();
		if (deviceConfiguration == null) {
			throw new NullPointerException("deviceConfiguration is null");
		}
		double scale = deviceConfiguration.getDefaultTransform().getScaleX();
		if (SystemInfo.isMacOSX() && scale == 1f && !JETBRAINS_JRE) {
			if (cachedScaleFactor == null) {
				initCachedScaleFactor();
			}
			return cachedScaleFactor;
		}

		return scale;
	}

	private static void initCachedScaleFactor() {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = graphicsEnvironment.getDefaultScreenDevice();
		cachedScaleFactor = 1d;
		try {
			Field field = device.getClass().getDeclaredField("scale");
			if (field != null) {
				field.setAccessible(true);
				Object scaleValue = field.get(device);
				if (scaleValue instanceof Integer && (Integer) scaleValue == 2) {
					cachedScaleFactor = 2d;
				}
			}
		} catch (Exception ignore) {
		}
	}

	public static void withFractionalAntiAliasing(Graphics g, Runnable r) {
		withFractionalAntiAliasing(g, RenderingHints.VALUE_ANTIALIAS_ON, r);
	}

	public static void withoutFractionalAntiAliasing(Graphics g, Runnable r) {
		withFractionalAntiAliasing(g, RenderingHints.VALUE_ANTIALIAS_OFF, r);
	}

	public static void withFractionalAntiAliasing(Graphics g, Object value, Runnable r) {
		Graphics2D g2 = (Graphics2D) g;
		boolean fractionalScale = !isIntegerScaleFactor(g2);
		Object oldAntiAliasingHint = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		if (fractionalScale) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, value);
		}
		try {
			r.run();
		} finally {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntiAliasingHint);
		}
	}
}
