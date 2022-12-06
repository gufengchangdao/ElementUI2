/*
 * @(#)TopLevelMenuContainer.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.menu;

/**
 * 指示这是顶级菜单或命令栏的标记界面。原始的Swing代码使用JMenuBar来判断它是否是TopLeveMenu。但是，由于我们引入了 CommandBar，此标准不再
 * 正确。新条件是如果容器实现 TopLevelMenuContainer，则该容器中的子项是顶级菜单。如果 isMenuBar 返回 true，则表示该容器确实是一个菜单栏，
 * 就像 JMenuBar 一样。
 *
 * @deprecated JIDE定义的接口，我不知道这个接口如何使用，并且尚未发现为什么问题，未来可能会删除
 */
public interface TopLevelMenuContainer {
	/**
	 * Checks if the TopLevelMenuContainer is used as JMenuBar.
	 *
	 * @return true if the TopLevelMenuContainer is used as JMenuBar.
	 */
	boolean isMenuBar();
}
