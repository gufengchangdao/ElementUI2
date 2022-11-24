package com.element.util;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * For internal usage only.
 *
 * <p>Title:       JideFocusTracker</p>
 * <p>Description: This class is used to manage focus. It will register focus
 * listeners for the highestComponent and any of its children.
 * This allows for focusListeners to be tied to this adapter,
 * and then all focus events can be routed through this.</p>
 */
public class JideFocusTracker {

	protected Component compHighest;
	protected FocusListener listenerFocus = null;
	protected ContainerListener listenerContainer = null;
	protected transient FocusListener listenerMultiCast;
	protected boolean repeat;

	protected transient Component lastFocus;

	public JideFocusTracker() {
		lastFocus = null;
		setRepeating(true);
		listenerFocus = new MainFocusListener();
		listenerContainer = new MainContainerListener();
	}

	public JideFocusTracker(Component compHighest) {
		this();
		//System.out.println("constructing focus tracker for comp " + compHighest);
		setHighestComponent(compHighest);
	}

////////////////////////////////////////////////////////////////////////////////
// Public Methods
////////////////////////////////////////////////////////////////////////////////

	public void setHighestComponent(Component compHighest) {
		Component OldValue = this.compHighest;

		if (OldValue != null) {
			synchronized (OldValue.getTreeLock()) {
				removeInternalListeners(OldValue);
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
		listenerMultiCast = AWTEventMulticaster.add(listenerMultiCast, l);
	}

	public synchronized void removeFocusListener(FocusListener l) {
		listenerMultiCast = AWTEventMulticaster.remove(listenerMultiCast, l);
	}

////////////////////////////////////////////////////////////////////////////////
// Protected Methods
////////////////////////////////////////////////////////////////////////////////

	protected void addInternalListeners(Component component) {
		if (!isExcludedComponent(component)) {
			component.addFocusListener(listenerFocus);
			if (component instanceof Container container) {
				container.addContainerListener(listenerContainer);
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
			component.removeFocusListener(listenerFocus);
			if (component instanceof Container container) {
				container.removeContainerListener(listenerContainer);
				for (int i = 0; i < container.getComponentCount(); i++) {
					removeInternalListeners(container.getComponent(i));
				}
			}
		}
	}

	class MainContainerListener implements ContainerListener {
		public void componentAdded(ContainerEvent e) {
			//System.out.println(e.getChild().getClass().getName() + " add to container = " + e.getContainer().getClass().getName());
			synchronized (e.getChild().getTreeLock()) {
				addInternalListeners(e.getChild());
			}
		}

		public void componentRemoved(ContainerEvent e) {
			//System.out.println(e.getChild().getClass().getName() + " removed from container = " + e.getContainer().getClass().getName());
			synchronized (e.getChild().getTreeLock()) {
				removeInternalListeners(e.getChild());
			}
		}
	}

	class MainFocusListener implements FocusListener {
		public void focusGained(FocusEvent e) {
//      System.out.println("focusGained " + e.getSource());
			if (listenerMultiCast != null)
				if ((e.getSource() != lastFocus) || (isRepeating()))
					listenerMultiCast.focusGained(e);
		}

		public void focusLost(FocusEvent e) {
//      System.out.println(this  + " is firing");
			if (listenerMultiCast != null)
				if ((e.getSource() != lastFocus) || (isRepeating()))
					listenerMultiCast.focusLost(e);
		}
	}
}
