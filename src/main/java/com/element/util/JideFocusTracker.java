package com.element.util;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 仅供内部使用
 *
 * <p>名称:       JideFocusTracker</p>
 * <p>说明: 此类用于管理焦点。它将<b>为 highestComponent 及其任何子组件注册焦点侦听器</b>。这允许将 focusListeners 绑定到此适配器，然后所有焦点事件都可以通过它进行路由。</p>
 */
public class JideFocusTracker {
	protected Component compHighest;
	protected FocusListener focusListener;
	protected ContainerListener containerListener;
	protected transient FocusListener multiCastListener;
	protected boolean repeat;

	// protected transient Component lastFocus;

	public JideFocusTracker() {
		repeat = true;
		focusListener = new MainFocusListener();
		containerListener = new MainContainerListener();
	}

	public JideFocusTracker(Component compHighest) {
		this();
		setHighestComponent(compHighest);
	}

	/** 为给定组件添加监听器，该操作也会移除原有组件的监听器 */
	public void setHighestComponent(Component compHighest) {
		Component oldValue = this.compHighest;

		if (oldValue != null) {
			synchronized (oldValue.getTreeLock()) {
				removeInternalListeners(oldValue);
			}
		}

		if (compHighest != null) {
			synchronized (compHighest.getTreeLock()) {
				addInternalListeners(compHighest);
			}
		}

		this.compHighest = compHighest;
		// note - I would fire an event here if I thought anyone would care.
	}

	public Component getHighestComponent() {
		return compHighest;
	}

	/**
	 * This allows you to set whether focus lost or focus gained will be
	 * fired if the event is for the same component as a previous event.
	 * The default is true.
	 */
	public boolean isRepeating() {
		return this.repeat;
	}

	/**
	 * @see #isRepeating
	 */
	public void setRepeating(boolean repeat) {
		this.repeat = repeat;
	}

	public synchronized void addFocusListener(FocusListener l) {
		multiCastListener = AWTEventMulticaster.add(multiCastListener, l);
	}

	public synchronized void removeFocusListener(FocusListener l) {
		multiCastListener = AWTEventMulticaster.remove(multiCastListener, l);
	}

	//递归地为该组件及子组件添加listenerFocus 和 listenerContainer
	protected void addInternalListeners(Component component) {
		if (!isExcludedComponent(component)) {
			component.addFocusListener(focusListener);
			if (component instanceof Container container) {
				container.addContainerListener(containerListener);
				for (int i = 0; i < container.getComponentCount(); i++) {
					addInternalListeners(container.getComponent(i));
				}
			}
		}
	}

	protected boolean isExcludedComponent(Component component) {
		return component instanceof CellRendererPane;
	}

	protected void removeInternalListeners(Component component) {
		if (!isExcludedComponent(component)) {
			component.removeFocusListener(focusListener);
			if (component instanceof Container container) {
				container.removeContainerListener(containerListener);
				for (int i = 0; i < container.getComponentCount(); i++) {
					removeInternalListeners(container.getComponent(i));
				}
			}
		}
	}

	/** 当容器添加和移除组件时，添加或移除该组件的监听器 */
	class MainContainerListener implements ContainerListener {
		public void componentAdded(ContainerEvent e) {
			synchronized (e.getChild().getTreeLock()) {
				addInternalListeners(e.getChild());
			}
		}

		public void componentRemoved(ContainerEvent e) {
			synchronized (e.getChild().getTreeLock()) {
				removeInternalListeners(e.getChild());
			}
		}
	}

	class MainFocusListener implements FocusListener {
		public void focusGained(FocusEvent e) {
			if (multiCastListener != null)
				if (isRepeating()) multiCastListener.focusGained(e);
		}

		public void focusLost(FocusEvent e) {
			if (multiCastListener != null)
				if (isRepeating()) multiCastListener.focusLost(e);
		}
	}
}
