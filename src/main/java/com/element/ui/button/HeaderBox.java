// /*
//  * @(#)HeaderBox.java 4/27/2006
//  *
//  * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
//  */
//
// package com.element.ui.button;
//
// import com.element.plaf.LookAndFeelFactory;
// import com.element.plaf.UIDefaultsLookup;
//
// import javax.swing.*;
//
// /**
//  * HeaderBox 是 JIDE Pivot Grid 产品中使用的一个特殊组件，用于模拟具有表头样式的按钮。
//  */
// public class HeaderBox extends JideButton {
// 	private static final String uiClassID = "HeaderBoxUI";
// 	public static final String CLIENT_PROPERTY_TABLE_CELL_EDITOR = "HeaderBox.isTableCellEditor";
//
// 	/**
// 	 * Creates a button with no set text or icon.
// 	 */
// 	public HeaderBox() {
// 		setOpaque(false);
// 		setRolloverEnabled(true);
// 		setRequestFocusEnabled(true);
// 	}
//
// 	/**
// 	 * Resets the UI property to a value from the current look and feel.
// 	 *
// 	 * @see JComponent#updateUI
// 	 */
// 	@Override
// 	public void updateUI() {
// 		if (UIDefaultsLookup.get(uiClassID) == null) {
// 			LookAndFeelFactory.installJideExtension();
// 		}
// 		setUI(UIManager.getUI(this));
// 	}
//
//
// 	/**
// 	 * Returns a string that specifies the name of the L&F class that renders this component.
// 	 *
// 	 * @return the string "HeaderBoxUI"
// 	 */
// 	@Override
// 	public String getUIClassID() {
// 		return uiClassID;
// 	}
// }
