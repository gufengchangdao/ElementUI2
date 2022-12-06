package com.element.util;

import com.element.ui.pane.JideScrollPane;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

/**
 * 事件、监听器工具类
 */
public class ListenerUtil {
	/**
	 * Checks if the key listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isKeyListenerRegistered(Component component, KeyListener l) {
		return Arrays.stream(component.getKeyListeners()).anyMatch(keyListener -> keyListener == l);
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
	 * Checks if the property change listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isPropertyChangeListenerRegistered(Component component, PropertyChangeListener l) {
		return Arrays.stream(component.getPropertyChangeListeners()).anyMatch(propertyChangeListener -> propertyChangeListener == l);
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
		if (propertyName == null) return isPropertyChangeListenerRegistered(component, l);
		return Arrays.stream(component.getPropertyChangeListeners(propertyName)).anyMatch(propertyChangeListener -> propertyChangeListener == l);
	}

	/**
	 * Checks if the mouse listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isMouseListenerRegistered(Component component, MouseListener l) {
		return Arrays.stream(component.getMouseListeners()).anyMatch(mouseListener -> mouseListener == l);
	}


	/**
	 * Checks if the mouse motion listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isMouseMotionListenerRegistered(Component component, MouseMotionListener l) {
		return Arrays.stream(component.getMouseMotionListeners()).anyMatch(mouseMotionListener -> mouseMotionListener == l);
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
				p.x, p.y,
				e.getClickCount(),
				e.isPopupTrigger(),
				e.getButton());
		target.dispatchEvent(retargeted);
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
}
