package com.element.ui.notice.message;

import com.element.animator.popup.PopupAnimatorGroup;
import com.element.animator.popup.PopupAnimatorTask;
import com.element.ui.alert.AlertComponent;
import com.element.ui.alert.AlertFactory;
import com.element.ui.template.X2Component;

import javax.swing.*;
import java.awt.*;

/**
 * 消息提示简化操作
 */
public class MessageFactory {
	private JComponent container;
	private PopupAnimatorGroup<AlertComponent> group;

	public MessageFactory(JComponent container) {
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
		// 坐标属性
		int x = (container.getRootPane().getPreferredSize().width - alert.getPreferredSize().width) / 2;

		PopupAnimatorTask<AlertComponent> task = group.createTask(alert, new Point(x, beginY), new Point(x, beginY + 100));
		if (!isAutoClose) task.setDurationTime(0);

		// 添加组件并启动动画
		group.startAnimator(task);
		return task;
	}
}
