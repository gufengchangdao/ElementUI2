/*
 * @(#)ThreadCheckingRepaintManager.java 4/18/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.element.util;

import javax.swing.*;
import java.util.ArrayList;

/**
 * 该重绘管理器可用于检测swing组件更新的线程违规操作，即必要的操作是否在EDT中执行，对于违规的地方会打印异常，建议开发过程使用该重绘管理器
 * <p>
 * 使用方法，在启动窗口前如下调用：
 * <pre>
 *     RepaintManager.setCurrentManager(new ThreadCheckingRepaintManager());
 * </pre>
 */
public class ThreadCheckingRepaintManager extends RepaintManager {
	/** 是否完整检查，如果为true，则忽视checkIsShowing的值检查所有组件 */
	private boolean completeCheck = true;
	/** 是否仅检查显示在屏幕上的组件 */
	private boolean checkIsShowing = false;

	/**
	 * Creates ThreadCheckingRepaintManager. You can create one and set it using RepaintManager.setCurrentManager(new
	 * ThreadCheckingRepaintManager()).
	 */
	public ThreadCheckingRepaintManager() {
		super();
	}

	/**
	 * Creates ThreadCheckingRepaintManager. You can create one and set it using RepaintManager.setCurrentManager(new
	 * ThreadCheckingRepaintManager()).
	 *
	 * @param checkIsShowing true to only check showing components.
	 */
	public ThreadCheckingRepaintManager(boolean checkIsShowing) {
		super();
		this.checkIsShowing = checkIsShowing;
	}

	/**
	 * Initially there was a rule that it is safe to create and use Swing components until they are realized but this
	 * rule is not valid any more, and now it is recommended to interact with Swing from EDT only.
	 * <p/>
	 * That's why completeCheck flag is used - if you test the old program switch it to false, but new applications
	 * should be tested with completeCheck set to true*
	 *
	 * @return true or false. By default, it is false.
	 */
	public boolean isCompleteCheck() {
		return completeCheck;
	}

	/**
	 * @param completeCheck true or false.
	 * @see #isCompleteCheck()
	 */
	public void setCompleteCheck(boolean completeCheck) {
		this.completeCheck = completeCheck;
	}

	@Override
	public synchronized void addInvalidComponent(JComponent jComponent) {
		checkThreadViolations(jComponent);
		super.addInvalidComponent(jComponent);
	}

	@Override
	public synchronized void addDirtyRegion(JComponent jComponent, int i, int i1, int i2, int i3) {
		checkThreadViolations(jComponent);
		super.addDirtyRegion(jComponent, i, i1, i2, i3);
	}

	/** 上一个打印的堆栈轨迹列表，因为验证方法可能会多次调用，防止重复打印相同的堆栈轨迹，这里消除连续的相同的堆栈轨迹 */
	public static ArrayList<StackTraceElement> LAST_STACK_TRACE = new ArrayList<>();

	/**
	 * 检查是否在非EDT中对组件进行了重绘操作，如果进行了会打印堆栈轨迹，方便找出代码可能存在问题的地方
	 */
	private void checkThreadViolations(JComponent c) {
		if (!SwingUtilities.isEventDispatchThread() && (completeCheck || checkIsShowing(c))) {
			IllegalThreadStateException e = new IllegalThreadStateException(c.getClass().getSimpleName() + " 的操作应该在EDT中执行");
			boolean repaint = false;
			boolean fromSwing = false;
			StackTraceElement[] stackTrace = e.getStackTrace();
			ArrayList<StackTraceElement> traceElements = new ArrayList<>();
			for (StackTraceElement st : stackTrace) {
				if (repaint && st.getClassName().startsWith("javax.swing.")) {
					fromSwing = true;
				}
				if ("repaint".equals(st.getMethodName())) {//repaint方法
					repaint = true;
				}
				// 打印的堆栈只打印用户自己代码的部分，这里用getClassLoaderName是否为null来判断，我知道源码的部分null，不知道用户代码
				// 会不会也有可能返回null
				if (repaint && fromSwing && st.getClassLoaderName() != null) {
					traceElements.add(st);
				}
			}
			//这里没有问题，因为 repaint() 是线程安全的
			if (repaint && !fromSwing) return;
			// 跟上一个堆栈比较，如果跟上一个相同就不重复打印了
			if (LAST_STACK_TRACE.equals(traceElements)) return;

			e.setStackTrace(traceElements.toArray(StackTraceElement[]::new));
			e.printStackTrace();
			LAST_STACK_TRACE = traceElements;
		}
	}

	private boolean checkIsShowing(JComponent c) {
		if (this.checkIsShowing) return c.isShowing();
		else return true;
	}
}
