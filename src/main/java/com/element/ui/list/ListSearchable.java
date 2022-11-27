/*
 * @(#)${NAME}
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.list;

import com.element.event.SearchableEvent;
import com.element.swing.Searchable;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * <code>ListSearchable</code> is an concrete implementation of {@link Searchable} that enables the search function in
 * JList. <p>It's very simple to use it. Assuming you have a JList, all you need to do is to call
 * <code><pre>
 * JList list = ....;
 * ListSearchable searchable = new ListSearchable(list);
 * </pre></code>
 * Now the JList will have the search function.
 * <p/>
 * There is very little customization you need to do to ListSearchable. The only thing you might need is when the
 * element in the JList needs a special conversion to convert to string. If so, you can overide convertElementToString()
 * to provide you own algorithm to do the conversion.
 * <code><pre>
 * JList list = ....;
 * ListSearchable searchable = new ListSearchable(list) {
 *      protected String convertElementToString(Object object) {
 *          ...
 *      }
 * };
 * </pre></code>
 * <p/>
 * Additional customization can be done on the base Searchable class such as background and foreground color,
 * keystrokes, case sensitivity.
 * <p/>
 * JList actually has a simple searchable feature but has flaws. It will affect our searchable feature. To workaround
 * it, you can override getNextMatch method and always return -1 when you create your JList. <code>
 * <pre>
 * JList list = new JList(...) {
 *     public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
 *         return -1;
 *     }
 * };
 * </pre>
 * </code>
 */
@SuppressWarnings("unchecked") //泛型转换不会出错
public class ListSearchable<E> extends Searchable implements ListDataListener, PropertyChangeListener {
	private boolean _useRendererAsConverter = false;

	public ListSearchable(JList<E> list) {
		super(list);
		list.getModel().addListDataListener(this);
		list.addPropertyChangeListener("model", this);
	}

	@Override
	public void uninstallListeners() {
		super.uninstallListeners();
		if (_component instanceof JList) {
			((JList<E>) _component).getModel().removeListDataListener(this);
		}
		_component.removePropertyChangeListener("model", this);
	}


	@Override
	public void setSelectedIndex(int index, boolean incremental) {
		if (incremental) {
			((JList<E>) _component).addSelectionInterval(index, index);
		} else {
			if (((JList<E>) _component).getSelectedIndex() != index) {
				((JList<E>) _component).setSelectedIndex(index);
			}
		}
		((JList<E>) _component).ensureIndexIsVisible(index);
	}

	@Override
	public int getSelectedIndex() {
		return ((JList<E>) _component).getSelectedIndex();
	}

	@Override
	public E getElementAt(int index) {
		return ((JList<E>) _component).getModel().getElementAt(index);
	}

	@Override
	public int getElementCount() {
		return ((JList<E>) _component).getModel().getSize();
	}

	/**
	 * Converts the element in Jlist to string. The returned value will be the <code>toString()</code> of whatever
	 * element that returned from <code>list.getModel().getElementAt(i)</code>.
	 *
	 * @param object the object to be converted to string
	 * @return the string representing the element in the JList.
	 */
	@Override
	public String convertElementToString(Object object) {
		if (isUseRendererAsConverter()) {
			ListCellRenderer<? super E> renderer = ((JList<E>) _component).getCellRenderer();
			// try to get the string displayed on the list first so we can search exactly on what the customers are looking at
			// if cannot get it, still go object.toString().
			if (renderer != null) {
				Component component = renderer.getListCellRendererComponent((JList<E>) _component, (E) object, 0, false, false);
				if (component != null) {
					if (component instanceof JLabel) {
						return ((JLabel) component).getText();
					} else if (component instanceof CheckBoxListCellRenderer) {
						ListCellRenderer actualRenderer = ((CheckBoxListCellRenderer) component).getActualListRenderer();
						if (actualRenderer != null) {
							Component rendererComponent = actualRenderer.getListCellRendererComponent((JList<E>) _component, object, 0, false, false);
							if (rendererComponent instanceof JLabel) {
								return ((JLabel) rendererComponent).getText();
							}
						}
					}
				}
			}
		}
		if (object != null) {
			return object.toString();
		} else {
			return "";
		}
	}

	public void contentsChanged(ListDataEvent e) {
		if (!isProcessModelChangeEvent()) {
			return;
		}
		if (e.getIndex0() == -1 && e.getIndex1() == -1) {
			return;
		}
		hidePopup();
		fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_MODEL_CHANGE));
	}

	public void intervalAdded(ListDataEvent e) {
		if (!isProcessModelChangeEvent()) {
			return;
		}
		hidePopup();
		fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_MODEL_CHANGE));
	}

	public void intervalRemoved(ListDataEvent e) {
		if (!isProcessModelChangeEvent()) {
			return;
		}
		hidePopup();
		fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_MODEL_CHANGE));
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if ("model".equals(evt.getPropertyName())) {
			hidePopup();
			ListModel<E> oldModel = (ListModel<E>) evt.getOldValue();
			if (oldModel != null) {
				oldModel.removeListDataListener(this);
			}

			ListModel<E> newModel = (ListModel<E>) evt.getNewValue();
			if (newModel != null) {
				newModel.addListDataListener(this);
			}
			fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_MODEL_CHANGE));
		}
	}

	/**
	 * Get the flag if the ListSearchable should use the renderer in the list as its converter.
	 * <p/>
	 * The default value for this field is false so we can get higher performance. For AutoFilterBox, we will set it
	 * to false automatically.
	 *
	 * @return true if you want to use the renderer as its converter. Otherwise false.
	 */
	public boolean isUseRendererAsConverter() {
		return _useRendererAsConverter;
	}

	/**
	 * Set the flag if the ListSearchable should use the renderer in the list as its converter.
	 * <p/>
	 *
	 * @param useRendererAsConverter the flag
	 * @see #isUseRendererAsConverter()
	 */
	public void setUseRendererAsConverter(boolean useRendererAsConverter) {
		_useRendererAsConverter = useRendererAsConverter;
	}
}
