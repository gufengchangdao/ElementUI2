package com.element.ui.alert;

import com.element.animator.popup.PopupAnimatorTask;
import com.element.animator.popup.listener.CloseMouseListener;
import com.element.color.ColorUtil;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.swing.template.X2Component;
import com.element.ui.button.IconButton;
import com.element.ui.others.tag.TagFactory;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Arrays;

/**
 * 警告组件
 * <p>
 * 用于页面中展示重要的提示信息
 * <p>
 * 支持功能：
 * <ul>
 *     <li>左侧图标、文本、按钮、颜色、边距</li>
 *     <li>内部组件大小自适应</li>
 *     <li>点击关闭按钮移除组件</li>
 * </ul>
 */
public class AlertComponent extends X2Component<JLabel, IconButton> {
	private boolean closable = false;
	private MouseListener closeListener;

	/**
	 * @param icon     左侧图标，可以为 null，不需要设置颜色过滤器，构造器中会设置为 c
	 * @param text     文本，可以使用html标签，不过获取时得手动解析
	 * @param c        主题色，字体色、背景色、图标色参考这个颜色
	 * @param closable 是否可关闭(右边是否含有关闭按钮)。需要注意的是这里只是决定是否绘制关闭按钮，并不设置关闭逻辑
	 * @param style    增量类型，默认为 {@link GrowStyle#CONSTANT}
	 * @param insets   外边距
	 */
	public AlertComponent(@Nullable SvgIcon icon,
	                      String text, Color c,
	                      boolean closable,
	                      GrowStyle style, Insets insets) {
		JLabel label;
		IconButton button = null;
		// 左侧标签
		if (icon != null) {
			icon.setColorFilter(color -> c);
			label = new JLabel(text, icon, JLabel.LEFT);
		} else {
			label = new JLabel(text, JLabel.LEFT);
		}
		label.setForeground(c);

		// 右侧按钮
		if (closable) {
			this.closable = true;
			button = TagFactory.createCloseButton(ColorUtil.PLACEHOLDER_TEXT);
		}

		setProperty(label, button, style, insets);
		Color bg = ColorUtil.blend(c, Color.WHITE, .8f);
		setBorderColor(bg);
		setBackground(bg);
		setInsets(insets);

		init(20);
	}

	public JLabel getLabel() {
		return getLeftC();
	}

	public IconButton getButton() {
		return getRightC();
	}

	public boolean isClosable() {
		return closable;
	}

	/**
	 * 注册关闭按钮。注册按钮逻辑，点击按钮即会移除组件
	 *
	 * @param task 执行动画的任务对象，如果 task 不为null，则可以在关闭时播放淡出动画，否则组件直接移除
	 */
	public void registerClose(PopupAnimatorTask<?> task) {
		// 已经注册了不再注册
		if (closeListener != null
				&& Arrays.stream(getRightC().getMouseListeners()).anyMatch(mouseListener -> mouseListener == closeListener))
			return;

		if (!this.closable) {
			this.closable = true;
			IconButton button = TagFactory.createCloseButton(ColorUtil.PLACEHOLDER_TEXT);
			setRightC(button);
			add(button);
		}

		closeListener = new CloseMouseListener(this, task);
		getRightC().addMouseListener(closeListener);
	}

	/**
	 * 注销关闭按钮的关闭逻辑
	 */
	public void unregisterClose() {
		if (closeListener == null) return;
		Arrays.stream(getRightC().getMouseListeners())
				.filter(mouseListener -> mouseListener == closeListener)
				.findFirst()
				.ifPresent(getRightC()::removeMouseListener);
	}
}
