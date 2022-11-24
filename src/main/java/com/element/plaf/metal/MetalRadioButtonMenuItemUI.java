/*
 * @(#)${NAME}
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.metal;

import com.element.plaf.vsnet.VsnetMenuItemUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

/**
 * RadioButtonMenuItem UI implementation
 */
public class MetalRadioButtonMenuItemUI extends VsnetMenuItemUI {
	public static ComponentUI createUI(JComponent b) {
		return new MetalRadioButtonMenuItemUI();
	}

	@Override
	protected String getPropertyPrefix() {
		return "RadioButtonMenuItem";
	}
}

