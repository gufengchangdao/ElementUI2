/*
 * @(#)AutoCompletionComboBox.java 7/24/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.combobox;

import com.element.converter.ObjectConverter;
import com.element.converter.ObjectConverterManager;
import com.element.swing.search.ComboBoxSearchable;

import javax.swing.*;
import java.util.Vector;

/**
 * 自动完成组合框。它使用{@link AutoCompletion}使组合框自动完成。您可以直接使用{@link AutoCompletion}来使任何组合框自动完成。如果你只
 * 需要一个自动完成的组合框，这个类只是一个方便的类。
 * <p>
 * 由于自动完成必须听取关键用户类型，因此它必须是可编辑的。如果您想将用户限制在组合框模型中可用的列表中，您可以调用
 * {@link #setStrict(boolean)}并将其设置为 true。
 * <p>
 * 注意，对于非String的下拉列表需要做特殊处理，应该先注册类型转换器
 * {@link ObjectConverterManager#registerConverter(Class, ObjectConverter)}，并且设置渲染器
 *
 * @param <E> 数据类型
 */
public class AutoCompletionComboBox<E> extends JComboBox<E> {
	protected AutoCompletion _autoCompletion;
	private boolean _preventActionEvent = false;

	public AutoCompletionComboBox() {
		initComponents();
	}

	public AutoCompletionComboBox(Vector<E> items) {
		super(items);
		initComponents();
	}

	public AutoCompletionComboBox(final E[] items) {
		super(items);
		initComponents();
	}

	public AutoCompletionComboBox(ComboBoxModel<E> aModel) {
		super(aModel);
		initComponents();
	}

	protected void initComponents() {
		setEditable(true);
		setSelectedIndex(-1);
		_autoCompletion = createAutoCompletion();
	}

	/**
	 * Creates the <code>AutoCompletion</code>.
	 *
	 * @return the <code>AutoCompletion</code>.
	 */
	protected AutoCompletion createAutoCompletion() {
		return new AutoCompletion(this, new ComboBoxSearchable<>(this) {
			@Override
			public void setSelectedIndex(int index, boolean incremental) {
				Object property = AutoCompletionComboBox.this.getClientProperty("JComboBox.isTableCellEditor");
				if (property instanceof Boolean && (Boolean) property) {
					_preventActionEvent = true;
				}
				try {
					super.setSelectedIndex(index, incremental);
				} finally {
					_preventActionEvent = false;
				}
			}

			@Override
			public String convertElementToString(Object object) {
				if (object instanceof String) return (String) object;
				return ObjectConverterManager.toString(object);
			}
		});
	}

	/**
	 * Gets the strict property.
	 *
	 * @return the value of strict property.
	 */
	public boolean isStrict() {
		return _autoCompletion.isStrict();
	}

	/**
	 * Sets the strict property. If true, it will not allow user to type in anything that is not in the known item list.
	 * If false, user can type in whatever he/she wants. If the text can match with a item in the known item list, it
	 * will still auto-complete.
	 *
	 * @param strict true or false.
	 */
	public void setStrict(boolean strict) {
		_autoCompletion.setStrict(strict);
	}

	/**
	 * Gets the strict completion property.
	 *
	 * @return the value of strict completion property.
	 * @see #setStrictCompletion(boolean)
	 */
	public boolean isStrictCompletion() {
		return _autoCompletion.isStrictCompletion();
	}

	/**
	 * Sets the strict completion property. If true, in case insensitive searching, it will always use the exact item in
	 * the Searchable to replace whatever user types. For example, when Searchable has an item "Arial" and user types in
	 * "AR", if this flag is true, it will auto-completed as "Arial". If false, it will be auto-completed as "ARial". Of
	 * course, this flag will only make a difference if Searchable is case insensitive.
	 *
	 * @param strictCompletion
	 */
	public void setStrictCompletion(boolean strictCompletion) {
		_autoCompletion.setStrictCompletion(strictCompletion);
	}

	/**
	 * Gets the underlying AutoCompletion class.
	 *
	 * @return the underlying AutoCompletion.
	 */
	public AutoCompletion getAutoCompletion() {
		return _autoCompletion;
	}

	@Override
	protected void fireActionEvent() {
		if (!_preventActionEvent) {
			super.fireActionEvent();
		}
	}
}
