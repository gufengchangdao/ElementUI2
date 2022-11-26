package com.element.ui.others.timeline;

import com.element.ui.navigation.steps.StepInfo;
import com.element.ui.navigation.steps.StepsComponent;

import java.awt.*;
import java.util.List;

/**
 * 时间线
 * <p>
 * 可视化地呈现时间流信息。这里用垂直步骤条实现
 * <p>
 * 由于排序可以是标题也可以是描述字符，比较灵活，所有我这里只给了示例，并未直接直接创建一个对应的组件。
 * 原理就是对列表重新排序，然后重新布局、绘制
 */
public class TimelineTest extends StepsComponent {
	public TimelineTest(List<StepInfo> items, int currentStep, Color achievedColor, boolean isHorizontal) {
		super(items, currentStep, achievedColor, isHorizontal);
	}
}
