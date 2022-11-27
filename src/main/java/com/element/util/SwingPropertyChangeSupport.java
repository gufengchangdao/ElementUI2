/*
 * $Id: SwingPropertyChangeSupport.java,v 1.1 2005/06/18 21:27:14 idk Exp $
 *
 * Copyright (c) 1995, 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.element.util;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * java.beans.PropertyChangeSupport的这个子类在功能上几乎相同。唯一的区别是，如果使用
 * SwingPropertyChangeSupport(sourceBean, true)构造，它确保侦听器只会在Event Dispatch Thread上收到通知。
 * 使用方式如下：
 * <pre>
 * 		SwingPropertyChangeSupport pcs = new SwingPropertyChangeSupport(someObject, true);
 * 		pcs.addPropertyChangeListener("text", evt -> {
 * 			System.out.println(evt.getPropertyName() + " : old: " + evt.getOldValue() + ", new: " + evt.getNewValue());
 * 			// 在EDT中执行一些更新...
 *                });
 * 		// ...
 * 		pcs.firePropertyChange("text", oldVal, newVal);
 * </pre>
 * <p>
 * 如果是监听组件的属性变化则不大需要这个类，因为组件属性的修改大多都在EDT中执行，fire方法可直接接后面，但是非组件类的属性监听就有些作用，
 * firePropertyChange相当于以下代码：
 * <pre>
 *     EventQueue.invokeLater(() -> pcs.firePropertyChange("text", oldVal, newVal));
 * </pre>
 */
public final class SwingPropertyChangeSupport extends PropertyChangeSupport {

	/**
	 * whether to notify listeners on EDT
	 *
	 * @serial
	 * @since 1.6
	 */
	private final boolean notifyOnEDT;

	/**
	 * Returns {@code notifyOnEDT} property.
	 *
	 * @return {@code notifyOnEDT} property
	 * @since 1.6
	 */
	public boolean isNotifyOnEDT() {
		return notifyOnEDT;
	}

	/**
	 * Constructs a SwingPropertyChangeSupport object.
	 *
	 * @param sourceBean  the bean to be given as the source for any events
	 * @param notifyOnEDT whether to notify listeners on the <i>Event Dispatch Thread</i> only
	 * @throws NullPointerException if {@code sourceBean} is {@code null}
	 * @since 1.6
	 */
	public SwingPropertyChangeSupport(Object sourceBean, boolean notifyOnEDT) {
		super(sourceBean);
		this.notifyOnEDT = notifyOnEDT;
	}

	/**
	 * {@inheritDoc}
	 * <p/>
	 * <p/>
	 * If {@see #isNotifyOnEDT} is {@code true} and called off the <i>Event Dispatch Thread</i> this implementation uses
	 * {@code SwingUtilities.invokeLater} to send out the notification on the <i>Event Dispatch Thread</i>. This ensures
	 * listeners are only ever notified on the <i>Event Dispatch Thread</i>.
	 *
	 * @throws NullPointerException if {@code evt} is {@code null}
	 * @since 1.6
	 */
	@Override
	public void firePropertyChange(final PropertyChangeEvent evt) {
		if (evt == null)
			throw new NullPointerException();
		if (!notifyOnEDT || SwingUtilities.isEventDispatchThread()) {
			super.firePropertyChange(evt);
		} else {
			EventQueue.invokeLater(() -> super.firePropertyChange(evt));
		}
	}
}
