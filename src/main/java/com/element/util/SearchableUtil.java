/*
 * @(#)SearchableUtils.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.util;

import com.element.swing.search.Searchable;
import com.element.swing.search.TextComponentSearchable;
import com.element.ui.combobox.ComboBoxSearchable;
import com.element.ui.list.ListSearchable;
import com.element.ui.table.TableSearchable;
import com.element.ui.tree.TreeSearchable;

import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 * 使组件可搜索的实用程序类。使用这个类非常容易。为了制作一个组件，您需要做的就是调用
 * <code><pre>
 * SearchableUtils.installSearchable(component);
 * </pre></code>
 * <p>
 * 该组件可以是 JList、JTree 或 JTable。如果您需要进一步自定义 Searchable 的一些属性，您可以分配一个从 installSearchable() 返回的变量。
 * <code><pre>
 * Searchable searchable = SearchableUtils.installSearchable(component);
 * // further configure it
 * searchable.setCaseSensitive(true);
 * // ...
 * </pre></code>
 * <p>
 * 通常您不需要从组件中卸载 searchable。但是如果由于某种原因，你需要禁用组件的可搜索特性，你可以调用 uninstallSearchable()。
 * <code><pre>
 * Searchable searchable = SearchableUtils.installSearchable(component);
 * // ...
 * // Now disable it
 * SearchableUtils.uninstallSearchable(searchable);
 * </pre></code>
 * <p>
 * 有一个小技巧你应该知道。 JTree 和 JList 部分实现了快速搜索功能，因此当您输入第一个字符时，它会跳转到第一个出现的地方。此功能有时会与我们
 * 提供的 Searchable 冲突。因此，最好通过覆盖 getNextMatch 方法创建 JTree 和 JList 来禁用 JTree 或 JList 默认功能。见下文
 * <code><pre>
 * JTree tree = new JTree(...) {
 *     public TreePath getNextMatch(String prefix, int startingRow, Position.Bias bias) {
 *         return null;
 *     }
 * };
 *
 * JList list = new JList(...){
 *     public int getNextMatch(String prefix, int startIndex, Position.Bias bias) {
 *         return -1;
 *     }
 * };
 * </pre></code>
 */
public class SearchableUtil {
	/**
	 * Installs the searchable function onto a JTree.
	 *
	 * @param tree the JTree to install searchable
	 * @return A TreeSearchable
	 */
	public static TreeSearchable installSearchable(JTree tree) {
		return new TreeSearchable(tree);
	}

	/**
	 * Installs the searchable function onto a JTable.
	 *
	 * @param table the JTable to install searchable
	 * @return A TableSearchable
	 */
	public static TableSearchable installSearchable(JTable table) {
		return new TableSearchable(table);
	}

	/**
	 * Installs the searchable function onto a JList.
	 *
	 * @param list the JList to install searchable
	 * @return A ListSearchable
	 */
	public static <E> ListSearchable<E> installSearchable(JList<E> list) {
		return new ListSearchable<>(list);
	}

	/**
	 * Installs the searchable function onto a JComboBox.
	 *
	 * @param combobox the combo box to install searchable
	 * @return A ComboBoxSearchable
	 */
	public static <E> ComboBoxSearchable<E> installSearchable(JComboBox<E> combobox) {
		return new ComboBoxSearchable<>(combobox);
	}

	/**
	 * Installs the searchable function onto a JTextComponent.
	 *
	 * @param textComponent the text component to install searchable
	 * @return A TextComponentSearchable
	 */
	public static TextComponentSearchable installSearchable(JTextComponent textComponent) {
		return new TextComponentSearchable(textComponent);
	}

	/**
	 * Uninstall the searchable that was installed to a component
	 *
	 * @param searchable the searchable.
	 */
	public static void uninstallSearchable(Searchable searchable) {
		if (searchable != null) {
			searchable.hidePopup();
			searchable.uninstallListeners();
			if (searchable.getComponent() instanceof JComponent c) {
				Object clientProperty = c.getClientProperty(Searchable.CLIENT_PROPERTY_SEARCHABLE);
				if (clientProperty == searchable) {
					c.putClientProperty(Searchable.CLIENT_PROPERTY_SEARCHABLE, null);
				}
			}
		}
	}

	/**
	 * Uninstall the searchable that was installed to a component
	 *
	 * @param component the component that has a searchable installed.
	 */
	public static void uninstallSearchable(JComponent component) {
		if (component != null) {
			Object clientProperty = component.getClientProperty(Searchable.CLIENT_PROPERTY_SEARCHABLE);
			if (clientProperty instanceof Searchable searchable) {
				searchable.hidePopup();
				searchable.uninstallListeners();
				component.putClientProperty(Searchable.CLIENT_PROPERTY_SEARCHABLE, null);
			}
		}
	}
}
