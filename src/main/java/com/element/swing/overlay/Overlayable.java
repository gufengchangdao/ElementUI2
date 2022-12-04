/*
 * @(#)Overlay.java 3/2/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */
package com.element.swing.overlay;

import javax.swing.*;
import java.awt.*;

/**
 * Overlayable提供了一种在另一个组件之上添加多个组件作为叠加组件的方法。
 * 通常我们会制作一个实现 Overlayable 接口的组件，尽管这不是必需的。该界面将允许用户添加/删除其他组件作为叠加组件并独立设置它们的位置。
 * <p>
 * 原理是将原组件和叠加在它上面的组件放到创建的面板中进行维护，{@link DefaultOverlayable}就是这样一个面板，提供了许多方法来修改样式。
 * 除此之外原组件必须重写repaint方法，以便在原组件重绘时提醒它上面的组件也进行重绘，因为RepaintManager并没有提供给我们这样一个钩子。像下面
 * 这样：
 * <pre>
 * public void repaint(long tm, int x, int y, int width, int height) {
 *     super.repaint(tm, x, y, width, height);
 *     OverlayableUtil.repaintOverlayable(this);
 * }
 * </pre>
 * 需要注意的是，repaintOverlayable会查找该组件的父组件，一直到找到Overlayable实现类，使其重绘，或者一直找到null或非JComponent才停止，
 * 因为我不喜欢这种递归的方式，所以没有让BaseXXX系列组件重写repaint
 *
 * <p>
 * 优点：
 * <ol>
 *     <li>API易用性-最少的代码更改来添加一个覆盖组件</li>
 *     <li>API易于理解</li>
 *     <li>覆盖组件是一个真实的组件，而不仅仅是一个绘制的图像，以便用户可以添加鼠标侦听器或设置工具提示等。</li>
 *     <li>可以放置在组件边界之外</li>
 *     <li>很好的处理滚动窗格</li>
 *     <li>支持任何没有额外代码的外观和感觉。</li>
 *     <li>可以向任何组件添加覆盖组件</li>
 *     <li>可以使用任何组件作为覆盖组件</li>
 * </ol>
 * <p>
 * 我们知道实现这个特性的许多不同的方法。然而，在我们看了上述标准之后，我们排除了许多替代方案。由于第5项，JLayeredPane/GlassPane排除在外。
 * 由于第3和4项，排除了覆盖paint方法。由于第6和7项，排除了扩展或多重L&F方法。最后，我们提出了这个设计。我想指出的是，尽管它几乎满足了所有的
 * 标准，但它仍然并不完美，特别是我们仍然必须覆盖Repaint方法。解决这个问题的一种方法是提供我们自己的RepaintManager，但它可能会使API更难理
 * 解。如果Swing提供一个钩子，那将是完美的。总之，如果我们给这个设计的评分从1到5,5是最好的，我们会给第3项5分，给第8项3分，给第1项2分。这两
 * 项方面还有改进的空间。
 */
public interface Overlayable extends SwingConstants {
	/**
	 * Client property. If a component has this property, the property will be an Overlayable. The component is the
	 * actual component of the Overlayable.
	 */
	String CLIENT_PROPERTY_OVERLAYABLE = "Overlayable.overlayable";

	/**
	 * Adds an overlay component to the center.
	 *
	 * @param component the overlay component.
	 */
	void addOverlayComponent(JComponent component);

	/**
	 * Adds an overlay component at the specified location. The location could be one of the following values.
	 * <ul>
	 *     <li>{@link SwingConstants#CENTER}
	 *     <li>{@link SwingConstants#SOUTH}
	 *     <li>{@link SwingConstants#NORTH}
	 *     <li>{@link SwingConstants#WEST}
	 *     <li>{@link SwingConstants#EAST}
	 *     <li>{@link SwingConstants#NORTH_EAST}
	 *     <li>{@link SwingConstants#NORTH_WEST}
	 *     <li>{@link SwingConstants#SOUTH_EAST}
	 *     <li>{@link SwingConstants#SOUTH_WEST}
	 * </ul>
	 *
	 * @param component the overlay component.
	 * @param location  the overlay location.
	 */
	void addOverlayComponent(JComponent component, int location);

	/**
	 * Adds an overlay component at the specified location. The location could be one of the following values.
	 * <ul>
	 *     <li>{@link SwingConstants#CENTER}
	 *     <li>{@link SwingConstants#SOUTH}
	 *     <li>{@link SwingConstants#NORTH}
	 *     <li>{@link SwingConstants#WEST}
	 *     <li>{@link SwingConstants#EAST}
	 *     <li>{@link SwingConstants#NORTH_EAST}
	 *     <li>{@link SwingConstants#NORTH_WEST}
	 *     <li>{@link SwingConstants#SOUTH_EAST}
	 *     <li>{@link SwingConstants#SOUTH_WEST}
	 * </ul>
	 *
	 * @param component the overlay component.
	 * @param location  the overlay location.
	 * @param index     the overlay index. 0 means the first overlay component. -1 means the last overlay component.
	 */
	void addOverlayComponent(JComponent component, int location, int index);

	/**
	 * Removes an overlay component that was added before.
	 *
	 * @param component
	 */
	void removeOverlayComponent(JComponent component);

	/**
	 * Gets the overlay component.
	 *
	 * @return the overlay component.
	 */
	JComponent[] getOverlayComponents();

	/**
	 * Sets the overlay component location. The valid values are defined in SwingConstants. They are
	 * <ul>
	 *     <li>{@link SwingConstants#CENTER}
	 *     <li>{@link SwingConstants#SOUTH}
	 *     <li>{@link SwingConstants#NORTH}
	 *     <li>{@link SwingConstants#WEST}
	 *     <li>{@link SwingConstants#EAST}
	 *     <li>{@link SwingConstants#NORTH_EAST}
	 *     <li>{@link SwingConstants#NORTH_WEST}
	 *     <li>{@link SwingConstants#SOUTH_EAST}
	 *     <li>{@link SwingConstants#SOUTH_WEST}
	 * </ul>
	 *
	 * @param location the overlay component location.
	 */
	void setOverlayLocation(JComponent component, int location);

	/**
	 * Gets the overlay component location. If -1, it means the component doesn't exit.
	 *
	 * @return the overlay component location.
	 */
	int getOverlayLocation(JComponent component);

	/**
	 * Gets the insets of the overlay component relative to the border of the component. This will affect the actual
	 * location of the overlay component except CENTER. If an edge of the insets is greater than 0, it will move the
	 * overlay component outwards on that edge. On the opposite, if the value is negative, it will move inward.
	 *
	 * @return the insets of the overlay component relative to the border of the component.
	 */
	Insets getOverlayLocationInsets();

	/**
	 * Sets the insets of the overlay component relative to the border of the component.
	 *
	 * @param insets the insets of the overlay component relative to the border of the component.
	 */
	void setOverlayLocationInsets(Insets insets);

	/**
	 * Sets all the overlay components visible or invisible. If you want to set one overlay component visible/invisible,
	 * you just need to call setVisible of that component.
	 *
	 * @param visible true to set it visible. False to invisible.
	 */
	void setOverlayVisible(boolean visible);
}
