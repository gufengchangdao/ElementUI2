/*
 * @(#)AbstractPage.java
 *
 * Copyright 2002 - 2003 JIDE Software. All rights reserved.
 */
package com.element.ui.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * AbstractPage 是一个抽象基类，它提供了延迟填充 JPanel 对象直到它真正可见。这在使用具有多个页面的 CardLayout 和选项卡面板视图时非常有
 * 用。延迟构建意味着它将快速启动。
 * <p>
 * 如果子类选择覆盖以下任何方法，则它们有责任确保其覆盖的方法首先调用父类的方法。这些方法是：
 *
 * <ul>
 *     <li>public void paint (Graphics)
 *     <li>public void paintComponents(Graphics)
 *     <li>public void paintAll (Graphics)
 *     <li>public void repaint ()
 *     <li>public void repaint (long)
 *     <li>public void repaint (int, int, int, int)
 *     <li>public void repaint (long, int, int, int, int)
 *     <li>public void update (Graphics)
 * </ul>
 * <p>
 * 默认情况下，如果调用任何方法，面板将被填充。但是，用户可以 setInvokeCondition() 来自定义何时填充面板。有关详细信息，请参阅
 * setInvokeCondition() 的 javadoc。
 */
public abstract class AbstractPage extends JPanel implements Laziness {
	/**
	 * Used by setInvokeCondition(). This value means initialize will be called in all paint/repaint/update methods.
	 */
	public static int INVOKE_ON_ALL = 0xFFFFFFFF;

	/**
	 * Used by setInvokeCondition(). This value means initialize will not be called. You have to call it manually.
	 */
	public static int INVOKE_ON_NONE = 0x0;

	/**
	 * Used by setInvokeCondition(). This value means initialize will be called with paint() is called.
	 */
	public static int INVOKE_ON_PAINT = 0x1;

	/**
	 * Used by setInvokeCondition(). This value means initialize will be called with repaint() is called.
	 */
	public static int INVOKE_ON_REPAINT = 0x2;

	/**
	 * Used by setInvokeCondition(). This value means initialize will be called with update() is called.
	 */
	public static int INVOKE_ON_UPDATE = 0x4;

	/**
	 * Used by setInvokeCondition(). This value means initialize will be called with invalidate(), revalidate() is
	 * called.
	 */
	public static int INVOKE_ON_VALIDATE = 0x8;

	private boolean _allowClosing = true;

	private int _invokeCondition = INVOKE_ON_PAINT | INVOKE_ON_REPAINT | INVOKE_ON_UPDATE;
	/**
	 * Only one <code>DataChangeEvent</code> is needed per model instance since the event's only (read-only) state is
	 * the source property. The source of events generated here is always "this".
	 */
	protected transient PageEvent _pageEvent = null;

	// We want to call the lazyConstructor only once.
	private boolean _lazyConstructorCalled = false;

	// Some versions of Swing called paint() before
	// the components were added to their containers.

	/**
	 * Creates an AbstractPage.
	 */
	protected AbstractPage() {
	}

	@Override
	public void invalidate() {
		if ((_invokeCondition & INVOKE_ON_VALIDATE) != 0) {
			initialize();
		}
		super.invalidate();
	}

	@Override
	public void revalidate() {
		if ((_invokeCondition & INVOKE_ON_VALIDATE) != 0) {
			initialize();
		}
		super.revalidate();
	}

	@Override
	public void paint(Graphics g) {
		if ((_invokeCondition & INVOKE_ON_PAINT) != 0) {
			initialize();
		}
		super.paint(g);
	}

	@Override
	public void paintAll(Graphics g) {
		if ((_invokeCondition & INVOKE_ON_PAINT) != 0) {
			initialize();
		}
		super.paintAll(g);
	}

	@Override
	public void paintComponents(Graphics g) {
		if ((_invokeCondition & INVOKE_ON_PAINT) != 0) {
			initialize();
		}
		super.paintComponents(g);
	}

	@Override
	public void repaint() {
		if ((_invokeCondition & INVOKE_ON_REPAINT) != 0) {
			initialize();
		}
		super.repaint();
	}

	@Override
	public void repaint(long l) {
		if ((_invokeCondition & INVOKE_ON_REPAINT) != 0) {
			initialize();
		}
		super.repaint(l);
	}

	@Override
	public void repaint(int i1, int i2, int i3, int i4) {
		if ((_invokeCondition & INVOKE_ON_REPAINT) != 0) {
			initialize();
		}
		super.repaint(i1, i2, i3, i4);
	}

	@Override
	public void repaint(long l, int i1, int i2, int i3, int i4) {
		if ((_invokeCondition & INVOKE_ON_REPAINT) != 0) {
			initialize();
		}
		super.repaint(l, i1, i2, i3, i4);
	}

	@Override
	public void update(Graphics g) {
		if ((_invokeCondition & INVOKE_ON_UPDATE) != 0) {
			initialize();
		}
		super.update(g);
	}

	/**
	 * Force the lazyInitialize() method implemented in the child class to be called. If this method is called more than
	 * once on a given object, all calls but the first do nothing.
	 */
	public synchronized final void initialize() {
		if (!_lazyConstructorCalled) {
			_lazyConstructorCalled = true;
			lazyInitialize();
			validate();
		}
	}

	/**
	 * Resets the page which will result all child components being removed and the method {@link #initialize()} being
	 * invoked again.
	 *
	 * @since 3.2.2
	 */
	public synchronized void reset() {
		_lazyConstructorCalled = false;
		removeAll();
	}

	/**
	 * Adds a <code>PageListener</code> to the page.
	 *
	 * @param l the <code>PageListener</code> to be added
	 */
	public void addPageListener(PageListener l) {
		listenerList.add(PageListener.class, l);
	}

	/**
	 * Removes a <code>PageListener</code> from the page.
	 *
	 * @param l the <code>PageListener</code> to be removed
	 */
	public void removePageListener(PageListener l) {
		listenerList.remove(PageListener.class, l);
	}

	/**
	 * Returns an array of all the <code>PageListener</code>s added to this <code>Page</code> with
	 * <code>addPageListener</code> .
	 *
	 * @return all of the <code>PageListener</code>s added, or an empty array if no listeners have been added
	 */
	public PageListener[] getPageListeners() {
		return listenerList.getListeners(PageListener.class);
	}

	/**
	 * Runs each <code>PageListener</code>'s <code>pageEventFired</code> method.
	 *
	 * @param id event id.
	 */
	public void firePageEvent(int id) {
		firePageEvent(this, id);
	}

	/**
	 * Runs each <code>PageListener</code>'s <code>pageEventFired</code> method.
	 *
	 * @param source of this event
	 * @param id     event id.
	 */
	public void firePageEvent(Object source, int id) {
		// make sure the page is initialized because user might add page listener in it.
		// If the page is not initialized, what's the point firing event
		initialize();

		if (source == null) { // set the source to this if it's null
			source = this;
		}

		Object[] listeners = listenerList.getListenerList();
		_pageEvent = new PageEvent(source, id);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PageListener.class) {
				((PageListener) listeners[i + 1]).pageEventListener(_pageEvent);
			}
		}
	}

	/**
	 * Sets allow closing. If true, the document cannot be closed. user can change the value in documentClosing() to
	 * prevent document from being closed.
	 *
	 * @param allowClosing true or false.
	 */
	public void setAllowClosing(boolean allowClosing) {
		_allowClosing = allowClosing;
	}

	/**
	 * Allow this document closing. By default it return true. User can override this method to return based on
	 * condition. A typical user case is: add a DocumentComponentListener. In documentComponentClosing, make this method
	 * return to false to prevent it from being closed.
	 *
	 * @return whether allow closing
	 */
	public boolean allowClosing() {
		return _allowClosing;
	}

	/**
	 * Gets the invoke condition. Invoke condition defines how lazy the page is. By default, the lazyInitialize() will
	 * be called on any update, paint or repaint method. However you can change the invoke condition to INVOKE_ON_PAINT.
	 * If so, lazyInitialize() will be called only when paint() method is called. You can even set the invoke condition
	 * to INVOKE_ON_NONE. If so, you will be responsible to call lazyInitialize() since none of those methods methods
	 * mentioned above will call lazyInitialize().
	 *
	 * @return the invocation condition
	 */
	public int getInvokeCondition() {
		return _invokeCondition;
	}

	/**
	 * Sets the invoke condition.
	 *
	 * @param invokeCondition the invoke condition.
	 */
	public void setInvokeCondition(int invokeCondition) {
		_invokeCondition = invokeCondition;
	}

}

