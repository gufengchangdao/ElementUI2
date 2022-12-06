/*
 * @(#)FileSystemIconsFactory.java 9/12/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.plaf.basic;

import com.element.color.ColorUtil;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.fill.*;
import com.element.ui.svg.icon.regular.XCircleSvg;

/**
 * A helper class to contain icons for demo of JIDE products. Those icons are copyrighted by JIDE Software, Inc.
 */
public class BasicFolderChooserIconsFactory {
	public static class ToolBar {
		public static final SvgIcon NEW = IconsFactory.getSvgIcon(NewSvg.class, 16, 16);
		public static final SvgIcon DELETE = IconsFactory.getSvgIcon(TrashSvg.class, 16, 16, ColorUtil.DANGER);
		public static final SvgIcon HOME = IconsFactory.getSvgIcon(HomeSvg.class, 16, 16);
		public static final SvgIcon MY_DOCUMENT = IconsFactory.getSvgIcon(MyDocumentSvg.class, 16, 16);
		public static final SvgIcon DESKTOP = IconsFactory.getSvgIcon(DesktopSvg.class, 16, 16);
		public static final SvgIcon REFRESH = IconsFactory.getSvgIcon(RefreshSvg.class, 16, 16);
	}
}
