package com.element.ui.notice.message;

import com.element.animator.popup.PopupAnimatorGroup;
import com.element.animator.popup.PopupAnimatorTask;
import com.element.swing.template.X2Component;
import com.element.ui.alert.AlertComponent;
import com.element.ui.alert.AlertFactory;

import javax.swing.*;
import java.awt.*;

/**
 * 消息提示简化操作
 */
public class MessageFactory {
	private Container container;
	private PopupAnimatorGroup<AlertComponent> group;

	/**
	 * 为指定的容器创建实例，弹出的消息在容器中居中
	 *
	 * @param container 消息需要居中的容器
	 */
	public MessageFactory(Container container) {
		this.container = container;
		group = new PopupAnimatorGroup<>(container);
	}

	public PopupAnimatorTask<AlertComponent> openSuccessMessage(String text, boolean isContainIcon,
	                                                            X2Component.GrowStyle style,
	                                                            int beginY, boolean closeable, boolean isAutoClose) {
		AlertComponent alert = AlertFactory.createSuccessAlert(text, isContainIcon, closeable, style);
		return openMessage(alert, beginY, closeable, isAutoClose);
	}

	public PopupAnimatorTask<AlertComponent> openInfoMessage(String text, boolean isContainIcon,
	                                                         X2Component.GrowStyle style,
	                                                         int beginY, boolean closeable, boolean isAutoClose) {
		AlertComponent alert = AlertFactory.createInfoAlert(text, isContainIcon, closeable, style);
		return openMessage(alert, beginY, closeable, isAutoClose);
	}

	public PopupAnimatorTask<AlertComponent> openDangerMessage(String text, boolean isContainIcon,
	                                                           X2Component.GrowStyle style,
	                                                           int beginY, boolean closeable, boolean isAutoClose) {
		AlertComponent alert = AlertFactory.createDangerAlert(text, isContainIcon, closeable, style);
		return openMessage(alert, beginY, closeable, isAutoClose);
	}

	public PopupAnimatorTask<AlertComponent> openWarningMessage(String text, boolean isContainIcon,
	                                                            X2Component.GrowStyle style,
	                                                            int beginY, boolean closeable, boolean isAutoClose) {
		AlertComponent alert = AlertFactory.createWarningAlert(text, isContainIcon, closeable, style);
		return openMessage(alert, beginY, closeable, isAutoClose);
	}

	public PopupAnimatorTask<AlertComponent> openMessage(AlertComponent alert,
	                                                     int beginY,
	                                                     boolean closeable, boolean isAutoClose) {
		// 计算坐标不使用玻璃窗格，这里找到rootPane计算它的大小
		Container rootPane = SwingUtilities.getAncestorOfClass(JRootPane.class, container);
		if (rootPane == null) return null;

		// 坐标属性
		int x = (rootPane.getPreferredSize().width - alert.getPreferredSize().width) / 2;

		PopupAnimatorTask<AlertComponent> task = group.createTask(alert, new Point(x, beginY), new Point(x, beginY + 100));
		if (!isAutoClose) task.setDurationTime(0);

		// 添加组件并启动动画
		group.startAnimator(task);
		return task;
	}
}
