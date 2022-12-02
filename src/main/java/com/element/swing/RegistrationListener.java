/*
 * @(#)ManagerListener.java 11/28/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

package com.element.swing;

import java.util.EventListener;

/**
 * 用于接收注册更改事件的侦听器接口。
 */
public interface RegistrationListener extends EventListener {
	/**
	 * 每当更改注册时调用。
	 *
	 * @param event 要触发的 RegistrationEvent。
	 */
	void registrationChanged(RegistrationEvent event);
}
