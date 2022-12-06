package com.element.swing;

import org.jdesktop.core.animation.timing.Animator;

import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;

/**
 * 为组件添加监听器，组件当前显示在框架时启动动画，不可显示时(包含被移除、被释放)停止动画。内部使用isShowing()进行判断
 */
public class AnimatorHierarchyListener implements HierarchyListener {
	private final Animator animator;

	public AnimatorHierarchyListener(Animator animator) {
		this.animator = animator;
	}

	@Override
	public void hierarchyChanged(HierarchyEvent e) {
		if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
			if (e.getComponent().isShowing() && !animator.isRunning()) {
				animator.restart();
			} else {
				animator.cancel();
			}
		}
	}
}
