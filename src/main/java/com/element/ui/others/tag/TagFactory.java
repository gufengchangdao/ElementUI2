package com.element.ui.others.tag;

import com.element.color.ColorUtil;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.button.IconButton;
import com.element.ui.svg.icon.regular.XCircleSvg;

import java.awt.*;

/**
 * 生成常用标签
 */
public class TagFactory {
	/**
	 * 基础标签
	 *
	 * @param text 标签文本
	 * @param fg   主题色
	 * @return 初始化后的标签
	 */
	public static Tag createDefaultTag(String text, Color fg) {
		return new Tag(text, fg, ColorUtil.blend(fg, Color.WHITE, 0.85f),
				null, null);
	}

	/**
	 * 带有关闭图标的标签
	 *
	 * @param text 标签文本
	 * @param fg   主题色
	 * @return 初始化后的标签
	 */
	public static Tag createIconTag(String text, Color fg) {
		return new Tag(text, fg, ColorUtil.blend(fg, Color.WHITE, 0.85f),
				createCloseButton(fg), BorderLayout.EAST);
	}

	/**
	 * 根据提供的图标色创建关闭图标
	 *
	 * @param fg 图标色
	 */
	public static IconButton createCloseButton(Color fg) {
		SvgIcon icon1 = XCircleSvg.of(16, 16);
		SvgIcon icon2 = com.element.ui.svg.icon.fill.XCircleSvg.of(16, 16);
		SvgIcon.ColorFilter filter1 = color -> fg;
		icon1.setColorFilter(filter1);
		icon2.setColorFilter(filter1);
		SvgIcon.ColorFilter filter2 = color ->
				ColorUtil.blend(fg, Color.WHITE, 0.3f);
		return new IconButton(icon1, icon2, filter2);
	}
}
