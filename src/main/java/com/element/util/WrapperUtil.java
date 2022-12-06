package com.element.util;

import com.element.ui.layout.JideBorderLayout;
import com.element.swing.nullc.NullPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 组件包装工具类
 */
public class WrapperUtil {
	/**
	 * 组件水平居中
	 * <p>
	 * 当布局为Y轴排列并组件放大时，组件并不会居中布局，可以用此方法将组件嵌套一层，这样可实现居中效果
	 */
	public static Box horizontalCenter(JComponent c) {
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(c);
		b.add(Box.createHorizontalGlue());
		return b;
	}

	/** 靠左 */
	public static Box left(JComponent c) {
		Box b = Box.createHorizontalBox();
		b.add(c);
		b.add(Box.createHorizontalGlue());
		return b;
	}

	/** 靠右 */
	public static Box right(JComponent c) {
		Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(c);
		return b;
	}

	/** 两边对齐，可以有一个为 null */
	public static Box horizontalAlign(JComponent leftC, JComponent rightC) {
		Box b = Box.createHorizontalBox();
		if (leftC != null) {
			b.add(leftC);
			if (rightC != null) b.add(Box.createHorizontalGlue());
		}
		if (rightC != null) b.add(rightC);
		return b;
	}

	/** 组件垂直居中 */
	public static Box verticalCenter(JComponent c) {
		Box b = Box.createVerticalBox();
		b.add(Box.createVerticalGlue());
		b.add(c);
		b.add(Box.createVerticalGlue());
		return b;
	}


	/**
	 * Create a Panel around a component so that component aligns to left.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createLeftPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.LINE_START);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to right.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createRightPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.LINE_END);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to top.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createTopPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.PAGE_START);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to bottom.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createBottomPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.PAGE_END);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component is right in the middle.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createCenterPanel(Component object) {
		JPanel ret = new NullPanel(new GridBagLayout());
		ret.setOpaque(false);
		ret.add(object, new GridBagConstraints());
		return ret;
	}

	/**
	 * Creates a container which a label for the component.
	 *
	 * @param title      the label
	 * @param component  the component
	 * @param constraint the constraint as in BorderLayout. You can use all the constraints as in BorderLayout except
	 *                   CENTER.
	 * @return the container which has both the label and the component.
	 */
	public static JPanel createLabeledComponent(JLabel title, Component component, Object constraint) {
		JPanel ret = new NullPanel(new JideBorderLayout(3, 3));
		ret.setOpaque(false);
		ret.add(title, constraint);
		title.setLabelFor(component);
		ret.add(component);
		return ret;
	}
}
