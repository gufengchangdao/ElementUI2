package com.element.swing;

import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.border.IconBorder;
import com.element.util.UIUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 支持左右图标的组件
 * <p>
 * 该类跟 {@link IconBorder} 的区别在于IconBorder只是边框提供的图标，和应用边框的组件是一体的，
 */
public class IconComponent<E extends JComponent> extends BaseComponent {
	private SvgIcon leftIcon;
	private JButton leftButton;
	private SvgIcon rightIcon;
	private JButton rightButton;
	private E component;

	/** 供子类使用 */
	public IconComponent() {
	}

	public IconComponent(E component) {
		this.component = component;
		init();
	}

	/**
	 * 构造器中调用。供子类使用
	 */
	protected void init() throws RuntimeException {
		setLayout(new BorderLayout());
		add(component);
		// if (leftButton != null)
		// 	add(leftButton,BorderLayout.WEST);
		// add(rightButton,BorderLayout.EAST);
	}


	/**
	 * 调用该方法跳转组件大小时，不会改变传入组件的大小，但会使左右图标按钮自适应容器大小
	 *
	 * @param preferredSize The new preferred size, or null
	 */
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		if (leftIcon != null)
			leftIcon.setDimension(UIUtil
					.adjustDimensionSize(preferredSize, leftButton.getPreferredSize()));

		if (rightIcon != null)
			rightIcon.setDimension(UIUtil
					.adjustDimensionSize(preferredSize, rightButton.getPreferredSize()));
	}

	public SvgIcon getLeftIcon() {
		return leftIcon;
	}

	public void setLeftIcon(SvgIcon leftIcon) {
		this.leftIcon = leftIcon;
		leftButton = updateIcon(this, BorderLayout.WEST, leftButton, leftIcon);
	}

	private static JButton updateIcon(JComponent container, String layout, JButton button, SvgIcon icon) {
		if (button == null) {
			button = new JButton();
			button.setBorderPainted(false);
			button.setBackground(null);
			container.add(button, layout); //建议先add()再setIcon()，不然布局会有问题
		}
		button.setIcon(icon);
		return button;
	}

	public SvgIcon getRightIcon() {
		return rightIcon;
	}

	public void setRightIcon(SvgIcon rightIcon) {
		this.rightIcon = rightIcon;
		rightButton = updateIcon(this, BorderLayout.EAST, rightButton, rightIcon);
	}

	public E getComponent() {
		return component;
	}

	public void setComponent(E component) {
		if (this.component != null) remove(this.component);
		this.component = component;
		add(this.component);
	}

	public JButton getLeftButton() {
		return leftButton;
	}

	public JButton getRightButton() {
		return rightButton;
	}
}
