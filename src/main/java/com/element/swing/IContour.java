/*
 * @(#)Contour.java
 *
 * Copyright 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Contour是一种轻量级组件，它仅在拖动时绘制组件的轮廓。它还用作拖动过程中某些信息的占位符。
 * <p>
 * 通常将Contour添加到{@link RootPaneContainer}的{@link JLayeredPane}中，以便它看起来像浮动在其他窗口之上。
 * <p>
 * 注意：此类必须是公共的，以便 JIDE 可以在不同的包中使用它，而不是作为公共 API 发布给最终用户。 JIDE 不保证类会保持原样。
 */
public interface IContour {

	Rectangle getBounds();

	boolean isLightweight();

	void setBounds(Rectangle r);

	void setBounds(int x, int y, int width, int height);

	int getTabHeight();

	/**
	 * Sets the tab height.
	 *
	 * @param tabHeight
	 */
	void setTabHeight(int tabHeight);

	/**
	 * Returns true if the contour is in tab-dock mode.
	 *
	 * @return true if tab-docking; false otherwise
	 */
	boolean isTabDocking();

	/**
	 * Sets the tab-docking mode.
	 *
	 * @param tabDocking new mode
	 */
	void setTabDocking(boolean tabDocking);

	/**
	 * Gets the side of the tab.
	 *
	 * @return the side of the tab
	 */
	int getTabSide();

	/**
	 * Sets the side of the tab.
	 *
	 * @param tabSide
	 */
	void setTabSide(int tabSide);

	/**
	 * Returns true if the contour is in floating mode.
	 *
	 * @return true if floating; false otherwise
	 */
	boolean isFloating();

	/**
	 * Sets the floating mode.
	 *
	 * @param floating new mode
	 */
	void setFloating(boolean floating);

	/**
	 * Gets the attached component of this contour.
	 *
	 * @return the attached component
	 */
	Component getAttachedComponent();

	/**
	 * Sets the attached components.
	 *
	 * @param attachedComponent attached component to be set
	 */
	void setAttachedComponent(Component attachedComponent);

	/**
	 * Gets the side of the attached component which the contour is attached to.
	 *
	 * @return side the attached side
	 */
	int getAttachedSide();

	/**
	 * Sets the side of the attached component which the contour is attached to.
	 *
	 * @param attachedSide the new attached side to be set
	 */
	void setAttachedSide(int attachedSide);

	/**
	 * When you dragged a component, several other components could be dragged. For example, if user drags on title bar
	 * of FrameContainer, all components in the FrameContainer are considered as dragged. If user drags on tab, only
	 * selected one is dragged.
	 *
	 * @return <code>true</code> if all dragged components are affected; <code>false</code> otherwise.
	 */
	boolean isSingle();

	/**
	 * Sets the value of single.
	 *
	 * @param single <code>true</code> if all dragged components are affected; <code>false</code> otherwise.
	 */
	void setSingle(boolean single);

	/**
	 * Checks if docking is allowed.
	 *
	 * @return <code>true</code> if docking is allowed; <code>false</code> otherwise.
	 */
	boolean isAllowDocking();

	/**
	 * Sets the value of docking.
	 *
	 * @param allowDocking <code>true</code> if docking is allowed; <code>false</code> otherwise.
	 */
	void setAllowDocking(boolean allowDocking);

	Container getRelativeContainer();

	void setRelativeContainer(Container relativeContainer);

	/**
	 * Gets saved X position of contour before it's hidden.
	 *
	 * @return saved X position
	 */
	int getSaveX();

	/**
	 * Gets saved Y position of contour before it's hidden.
	 *
	 * @return saved Y position
	 */
	int getSaveY();

	/**
	 * Gets saved mouse modifier before the contour is hidden.
	 *
	 * @return saved mouse modifier
	 */
	int getSaveMouseModifier();

	/**
	 * Gets saved dragged component before the contour is hidden.
	 *
	 * @return saved dragged component
	 */
	JComponent getSaveDraggedComponent();

	/**
	 * Stores information before the contour is hidden. Those information will be used to restore when the contour is
	 * set visible again.
	 *
	 * @param comp              the dragged component
	 * @param saveX             X position of the contour
	 * @param saveY             Y position of the contour
	 * @param saveMouseModifier mouse modifier in the MouseEvent
	 */
	void setDraggingInformation(JComponent comp, int saveX, int saveY, int saveMouseModifier);

	void cleanup();

//    private Screen _screen;
//    private Container _savedContainer;

	/**
	 * Makes the component visible or invisible. Overrides <code>Component.setVisible</code>.
	 *
	 * @param aFlag true to make the component visible; false to make it invisible
	 */
	void setVisible(boolean aFlag);

	/**
	 * Determines whether this component should be visible when its parent is visible. Components are initially visible,
	 * with the exception of top level components such as <code>Frame</code> objects.
	 *
	 * @return <code>true</code> if the component is visible, <code>false</code> otherwise
	 * @see #setVisible
	 * @since JDK1.0
	 */
	boolean isVisible();

	void setGlassPane(Component glassPane);

	Component getGlassPane();

	void setChangeCursor(boolean changeCursor);
}
