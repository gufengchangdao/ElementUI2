/*
 * @(#)JideCursors.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.swing;

import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.cursor.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 苹果的鼠标光标
 */
public enum MacOSCursors {
	ARROW_CURSOR("Cursor.ArrowSvg", IconsFactory.getSvgIcon(ArrowSvg.class, 32, 32)),
	BOTTOM_LEFT_CURSOR("Cursor.BottomLeftSvg", IconsFactory.getSvgIcon(BottomLeftSvg.class, 32, 32)),
	BOTTOM_RIGHT_CURSOR("Cursor.BottomRightSvg", IconsFactory.getSvgIcon(BottomRightSvg.class, 32, 32)),
	CELL_CURSOR("Cursor.CellSvg", IconsFactory.getSvgIcon(CellSvg.class, 32, 32)),
	COPY_CURSOR("Cursor.CopySvg", IconsFactory.getSvgIcon(CopySvg.class, 32, 32)),
	CROSS_CURSOR("Cursor.CrossSvg", IconsFactory.getSvgIcon(CrossSvg.class, 32, 32)),
	DOWN_CURSOR("Cursor.DownSvg", IconsFactory.getSvgIcon(DownSvg.class, 32, 32)),
	EAST_CURSOR("Cursor.EastSvg", IconsFactory.getSvgIcon(EastSvg.class, 32, 32)),
	EAST_WEST_CURSOR("Cursor.EastWestSvg", IconsFactory.getSvgIcon(EastWestSvg.class, 32, 32)),
	GRABBED_CURSOR("Cursor.GrabbedSvg", IconsFactory.getSvgIcon(GrabbedSvg.class, 32, 32)),
	GRAB_CURSOR("Cursor.GrabSvg", IconsFactory.getSvgIcon(GrabSvg.class, 32, 32)),
	HELP_CURSOR("Cursor.HelpSvg", IconsFactory.getSvgIcon(HelpSvg.class, 32, 32)),
	LEFT_RIGHT_CURSOR("Cursor.LeftRightSvg", IconsFactory.getSvgIcon(LeftRightSvg.class, 32, 32)),
	LEFT_CURSOR("Cursor.LeftSvg", IconsFactory.getSvgIcon(LeftSvg.class, 32, 32)),
	LOADING_CURSOR("Cursor.LoadingSvg", IconsFactory.getSvgIcon(LoadingSvg.class, 32, 32)),
	MAKE_ALIAS_CURSOR("Cursor.MakeAliasSvg", IconsFactory.getSvgIcon(MakeAliasSvg.class, 32, 32)),
	MOVE_CURSOR("Cursor.MoveSvg", IconsFactory.getSvgIcon(MoveSvg.class, 32, 32)),
	NORTH_SOUTH_CURSOR("Cursor.NorthSouthSvg", IconsFactory.getSvgIcon(NorthSouthSvg.class, 32, 32)),
	NORTH_EAST_SOUTH_WEST_CURSOR("Cursor.NorthEastSouthWestSvg", IconsFactory.getSvgIcon(NorthEastSouthWestSvg.class, 32, 32)),
	NORTH_EAST_CURSOR("Cursor.NorthEastSvg", IconsFactory.getSvgIcon(NorthEastSvg.class, 32, 32)),
	NORTH_CURSOR("Cursor.NorthSvg", IconsFactory.getSvgIcon(NorthSvg.class, 32, 32)),
	NORTH_WEST_SOUTH_EAST_CURSOR("Cursor.NorthWestSouthEastSvg", IconsFactory.getSvgIcon(NorthWestSouthEastSvg.class, 32, 32)),
	NORTH_WEST_CURSOR("Cursor.NorthWestSvg", IconsFactory.getSvgIcon(NorthWestSvg.class, 32, 32)),
	NOT_ALLOWED_CURSOR("Cursor.NotAllowedSvg", IconsFactory.getSvgIcon(NotAllowedSvg.class, 32, 32)),
	POINTER_CURSOR("Cursor.PointerSvg", IconsFactory.getSvgIcon(PointerSvg.class, 32, 32)),
	POOF_CURSOR("Cursor.PoofSvg", IconsFactory.getSvgIcon(PoofSvg.class, 32, 32)),
	RIGHT_CURSOR("Cursor.RightSvg", IconsFactory.getSvgIcon(RightSvg.class, 32, 32)),
	SCREENSHOT_SELECT_CURSOR("Cursor.ScreenshotSelectSvg", IconsFactory.getSvgIcon(ScreenshotSelectSvg.class, 32, 32)),
	SCREENSHOT_CURSOR("Cursor.ScreenshotSvg", IconsFactory.getSvgIcon(ScreenshotSvg.class, 32, 32)),
	SOUTH_EAST_CURSOR("Cursor.SouthEastSvg", IconsFactory.getSvgIcon(SouthEastSvg.class, 32, 32)),
	SOUTH_CURSOR("Cursor.SouthSvg", IconsFactory.getSvgIcon(SouthSvg.class, 32, 32)),
	SOUTH_WEST_CURSOR("Cursor.SouthWestSvg", IconsFactory.getSvgIcon(SouthWestSvg.class, 32, 32)),
	TEXT_CURSOR("Cursor.TextSvg", IconsFactory.getSvgIcon(TextSvg.class, 32, 32)),
	TEXT_VERTICAL_CURSOR("Cursor.TextVerticalSvg", IconsFactory.getSvgIcon(TextVerticalSvg.class, 32, 32)),
	TOP_LEFT_CURSOR("Cursor.TopLeftSvg", IconsFactory.getSvgIcon(TopLeftSvg.class, 32, 32)),
	TOP_RIGHT_CURSOR("Cursor.TopRightSvg", IconsFactory.getSvgIcon(TopRightSvg.class, 32, 32)),
	TYPING_CURSOR("Cursor.TypingSvg", IconsFactory.getSvgIcon(TypingSvg.class, 32, 32)),
	UP_DOWN_CURSOR("Cursor.UpDownSvg", IconsFactory.getSvgIcon(UpDownSvg.class, 32, 32)),
	UP_CURSOR("Cursor.UpSvg", IconsFactory.getSvgIcon(UpSvg.class, 32, 32)),
	WEST_CURSOR("Cursor.WestSvg", IconsFactory.getSvgIcon(WestSvg.class, 32, 32)),
	ZOOM_IN_CURSOR("Cursor.ZoomInSvg", IconsFactory.getSvgIcon(ZoomInSvg.class, 32, 32)),
	ZOOM_OUT_CURSOR("Cursor.ZoomOutSvg", IconsFactory.getSvgIcon(ZoomOutSvg.class, 32, 32));

	MacOSCursors(String NAME, SvgIcon icon) {
		this.NAME = NAME;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		CURSOR = toolkit.createCustomCursor(icon.toImage(1), new Point(17, 12), NAME.substring(7, NAME.lastIndexOf("Svg")));
	}

	public final String NAME;
	public final Cursor CURSOR;

	/**
	 * 获取所有光标name到cursor的映射
	 */
	public static Map<String, Cursor> getCursors() {
		HashMap<String, Cursor> m = new HashMap<>();
		for (MacOSCursors c : MacOSCursors.values()) {
			m.put(c.NAME, c.CURSOR);
		}
		return m;
	}
}