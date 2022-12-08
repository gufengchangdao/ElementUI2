package demo;

/*
 * @(#)Demo.java 2/11/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import java.awt.*;
import java.io.Serializable;

/**
 * 演示组件使用的接口
 */
public interface Demo extends Serializable {
	/**
	 * 获取此演示的名称。
	 */
	String getName();

	/**
	 * 获取此演示的说明。
	 */
	String getDescription();

	/**
	 * 获取主演示面板。
	 */
	Component getDemoPanel() throws Exception;

	/**
	 * 获取演示的源代码。
	 */
	Class<?>[] getDemoSource();

	/**
	 * 处理演示。演示关闭时将调用它。
	 */
	void dispose();

	/**
	 * 获取面板，用户可以在其中设置演示组件的选项。
	 */
	Component getOptionsPanel();

	/**
	 * 检查公共选项面板是否可见。常用选项是设置语言环境或切换从左到右或从右到左，这些对所有组件都是通用的。
	 *
	 * @return true or false.
	 */
	boolean isCommonOptionsPaneVisible();
}

