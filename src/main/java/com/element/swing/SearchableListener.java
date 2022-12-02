/*
 * @(#)SearchableListener.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */

package com.element.swing;

import java.util.EventListener;

/**
 * The listener interface for receiving searchable events.
 */
public interface SearchableListener extends EventListener {
	void searchableEventFired(SearchableEvent e);
}
