/*
 * @(#)ConvertListener.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.vsnet;


import com.element.plaf.ExtWindowsDesktopProperty;

/**
 * Convert multiple objects into one object. Used by ExtDesktopProperty.
 *
 * @see ExtWindowsDesktopProperty
 */
public interface ConvertListener {
	Object convert(Object[] obj);
}
