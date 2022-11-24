package com.element.ui.tabs;

import java.util.EventListener;

/**
 * Defines an object which listens for TabEditingEvent.
 */
public interface TabEditingListener extends EventListener {
	/**
	 * This tells the listeners the tab editing is started
	 */
	void editingStarted(TabEditingEvent e);

	/**
	 * This tells the listeners the tab editing is stopped
	 */
	void editingStopped(TabEditingEvent e);

	/**
	 * This tells the listeners the tab editing is canceled
	 */
	void editingCanceled(TabEditingEvent e);
}
