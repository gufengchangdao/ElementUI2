/*
 * @(#)RegistrationEvent.java 11/28/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

package com.element.event;

import java.awt.*;
import java.util.EventObject;

/**
 * 添加对注册对象作为事件源的支持的AWTEvent 。
 */
public class RegistrationEvent extends EventObject {
	/**
	 * 用于DockableFrame事件的 ID 范围中的第一个数字。
	 */
	public static final int REGISTRATION_EVENT_FIRST = AWTEvent.RESERVED_ID_MAX + 1400;

	/**
	 * 用于DockableFrame事件的 ID 范围中的最后一个数字。
	 */
	public static final int REGISTRATION_EVENT_LAST = REGISTRATION_EVENT_FIRST + 3;

	/**
	 * 注册新对象时会传递此事件。
	 */
	public static final int REGISTRATION_ADDED = REGISTRATION_EVENT_FIRST;

	/**
	 * 当已注册的对象被删除时传递此事件。
	 */
	public static final int REGISTRATION_REMOVED = 1 + REGISTRATION_EVENT_FIRST;

	/**
	 * 清除整个注册时发送此事件
	 */
	public static final int REGISTRATION_CLEARED = 2 + REGISTRATION_EVENT_FIRST;

	private final int _id;
	private Object _object;
	private Object _context;
	private Object _key;

	/**
	 * 创建一个 REGISTRATION_CLEARED 事件。
	 *
	 * @param source 这里是{@link CacheMap}对象
	 * @param id     must be equal to REGISTRATION_CLEARED.
	 */
	public RegistrationEvent(Object source, int id) {
		super(source);
		if (id != REGISTRATION_CLEARED) {
			throw new IllegalArgumentException("This constructor is only for REGISTRATION_CLEARED event.");
		}
		_id = id;
	}

	/**
	 * 构造一个RegistrationEvent对象。
	 *
	 * @param source 发起事件的Registration对象，这里是{@link CacheMap}对象
	 * @param id     表示事件类型的整数
	 */
	public RegistrationEvent(Object source, int id, Object object, Object key, Object context) {
		super(source);
		_id = id;
		_object = object;
		_context = context;
		_key = key;
	}

	public Object getKey() {
		return _key;
	}

	public Object getContext() {
		return _context;
	}

	public Object getObject() {
		return _object;
	}

	public int getId() {
		return _id;
	}

	@Override
	public String toString() {
		String action = switch (getId()) {
			case REGISTRATION_ADDED -> "ADDED ";
			case REGISTRATION_REMOVED -> "REMOVED ";
			case REGISTRATION_CLEARED -> "CLEARED ";
			default -> "UNKNOWN " + getId() + " ";
		};
		return action + "{key = " + getKey() + "; context = " + getContext() + "; object = " + getObject();
	}
}
