/*
 * @(#)JIdeIconsFactory.java
 *
 * Copyright 2002-2003 JIDE Software Inc. All rights reserved.
 */
package com.element.swing;

import com.element.ui.icons.IconsFactory;

import javax.swing.*;

/**
 * 包含 JIDE 产品演示图标的辅助类。这些图标的版权归 JIDE Software, Inc. 所有。
 */
public class JideIconsFactory {
	public static class FileType {
		public static final String TEXT = "icons/jide/file_text.png";
		public static final String JAVA = "icons/jide/file_java.png";
		public static final String HTML = "icons/jide/file_html.png";
	}

	public static class View {
		public static final String HTML = "icons/jide/view_html.gif";
		public static final String DESIGN = "icons/jide/view_design.gif";
	}

	public static class DockableFrame {
		public static final String BLANK = "icons/jide/dockableframe_blank.gif";
		public static final String FRAME1 = "icons/jide/dockableframe_1.gif";
		public static final String FRAME2 = "icons/jide/dockableframe_2.gif";
		public static final String FRAME3 = "icons/jide/dockableframe_3.gif";
		public static final String FRAME4 = "icons/jide/dockableframe_4.gif";
		public static final String FRAME5 = "icons/jide/dockableframe_5.gif";
		public static final String FRAME6 = "icons/jide/dockableframe_6.gif";
		public static final String FRAME7 = "icons/jide/dockableframe_7.gif";
		public static final String FRAME8 = "icons/jide/dockableframe_8.gif";
		public static final String FRAME9 = "icons/jide/dockableframe_9.gif";
		public static final String FRAME10 = "icons/jide/dockableframe_10.gif";
		public static final String FRAME11 = "icons/jide/dockableframe_11.gif";
		public static final String FRAME12 = "icons/jide/dockableframe_12.gif";
		public static final String FRAME13 = "icons/jide/dockableframe_13.gif";
		public static final String FRAME14 = "icons/jide/dockableframe_14.gif";
		public static final String FRAME15 = "icons/jide/dockableframe_15.gif";
		public static final String FRAME16 = "icons/jide/dockableframe_16.gif";
		public static final String FRAME17 = "icons/jide/dockableframe_17.gif";
		public static final String FRAME18 = "icons/jide/dockableframe_18.gif";
		public static final String FRAME19 = "icons/jide/dockableframe_19.gif";
		public static final String FRAME20 = "icons/jide/dockableframe_20.gif";
	}

	public static class Cursor {
		public static final String HSPLIT = "icons/jide/cursor_h_split.gif";
		public static final String VSPLIT = "icons/jide/cursor_v_split.gif";

		public static final String NORTH = "icons/jide/cursor_north.gif";
		public static final String SOUTH = "icons/jide/cursor_south.gif";
		public static final String EAST = "icons/jide/cursor_east.gif";
		public static final String WEST = "icons/jide/cursor_west.gif";
		public static final String TAB = "icons/jide/cursor_tab.gif";
		public static final String FLOAT = "icons/jide/cursor_float.gif";
		public static final String VERTICAL = "icons/jide/cursor_vertical.gif";
		public static final String HORIZONTAL = "icons/jide/cursor_horizontal.gif";

		public static final String DROP = "icons/jide/cursor_drag.gif";
		public static final String NODROP = "icons/jide/cursor_drag_stop.gif";
		public static final String DELETE = "icons/jide/cursor_delete.gif";

		public static final String DROP_TEXT = "icons/jide/cursor_drag_text.gif";
		public static final String NODROP_TEXT = "icons/jide/cursor_drag_text_stop.gif";

		public static final String PERCENTAGE = "icons/jide/cursor_percentage.gif";
		public static final String MOVE_EAST = "icons/jide/cursor_move_east.gif";
		public static final String MOVE_WEST = "icons/jide/cursor_move_west.gif";
	}

	public static class WindowMenu {
		public static final String NEW_HORIZONTAL_TAB = "icons/jide/windows_new_horizontal_tab_group.png";
		public static final String NEW_VERTICAL_TAB = "icons/jide/windows_new_vertical_tab_group.png";
	}

	public static class Arrow {
		public static final String DOWN = "icons/jide/direction_down.gif";
		public static final String UP = "icons/jide/direction_up.gif";
		public static final String LEFT = "icons/jide/direction_left.gif";
		public static final String RIGHT = "icons/jide/direction_right.gif";
		public static final String DOT = "icons/jide/direction_dot.gif";
	}

	public static final String MENU_CHECKBOX_VSNET = "icons/jide/menu_checkbox_vsnet.gif";
	public static final String MENU_CHECKBOX_ECLIPSE = "icons/jide/menu_checkbox_eclipse.gif";
	public static final String MENU_RADIOBUTTON_VSNET = "icons/jide/menu_radiobutton_vsnet.gif";
	public static final String MENU_RADIOBUTTON_ECLIPSE = "icons/jide/menu_radiobutton_eclipse.gif";
	public static final String SAVE = "icons/jide/save.png";

	public static final String JIDE32 = "icons/jide/jide32.png";
	public static final String JIDE50 = "icons/jide/jide50.png";
	public static final String JIDELOGO = "icons/jide/jide_logo_small.png";

	public static ImageIcon getImageIcon(String name) {
		if (name != null) return IconsFactory.getImageIcon(JideIconsFactory.class, name);
		else return null;
	}

	public static void main(String[] argv) {
		IconsFactory.generateHTML(JideIconsFactory.class);
	}
}
