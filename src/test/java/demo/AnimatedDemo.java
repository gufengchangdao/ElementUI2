package demo;

/*
 * @(#)AnimatedDemo.java 8/24/2009
 *
 * Copyright 2002 - 2009 JIDE Software Inc. All rights reserved.
 */

/**
 * 引入此接口后，如果演示使用动画，则演示仅在演示窗口处于活动状态时运行。否则它会不必要地耗尽 CPU 周期。
 */
public interface AnimatedDemo extends Demo {
	void startAnimation();

	void stopAnimation();
}
