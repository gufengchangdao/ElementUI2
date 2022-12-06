package com.element.swing.base;

import com.element.plaf.LookAndFeelFactory;
import com.element.plaf.UIDefaultsLookup;

import javax.swing.*;
import java.util.Vector;

/**
 * <code>JideComboBox</code> is a JCombobox used on JToolBar or CommandBar. It has a flat look which matches with that
 * of JideButton and JideSplitButton.
 */
public class BaseComboBox<E> extends JComboBox<E> {
	private static final String uiClassID = "JideComboBoxUI";

	public BaseComboBox(ComboBoxModel<E> aModel) {
		super(aModel);
	}

	public BaseComboBox(final E[] items) {
		super(items);
	}

	public BaseComboBox(Vector<E> items) {
		super(items);
	}

	public BaseComboBox() {
		super();
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 *
	 * @see JComponent#updateUI
	 */
	@Override
	public void updateUI() {
		if (UIDefaultsLookup.get(uiClassID) == null) {
			LookAndFeelFactory.installJideExtension();
		}
		setUI(UIManager.getUI(this));
	}

	/**
	 * Returns a string that specifies the name of the L&F class that renders this component.
	 *
	 * @return the string "JideComboBoxUI"
	 * @see JComponent#getUIClassID
	 * @see UIDefaults#getUI
	 */
	@Override
	public String getUIClassID() {
		return uiClassID;
	}
}