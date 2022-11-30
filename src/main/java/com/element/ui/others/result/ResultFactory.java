package com.element.ui.others.result;

import com.element.color.ColorUtil;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.others.empty.EmptyComponent;
import com.element.ui.svg.icon.fill.CheckCircleSvg;
import com.element.ui.svg.icon.fill.WarningCircleSvg;
import com.element.ui.svg.icon.fill.XCircleSvg;

import javax.swing.*;

/**
 * 结果工厂类。这里套用了 {@link EmptyComponent} 这个类
 * <p>
 * 用于对用户的操作结果或者异常状态做反馈。
 */
public class ResultFactory {
	public static EmptyComponent createSuccessResult(String text, JButton button) {
		SvgIcon icon = CheckCircleSvg.of(64, 64);
		icon.setColorFilter(color -> ColorUtil.SUCCESS);
		return new EmptyComponent(new JLabel(icon), text, button);
	}

	public static EmptyComponent createWarningResult(String text, JButton button) {
		SvgIcon icon = WarningCircleSvg.of(64, 64);
		icon.setColorFilter(color -> ColorUtil.WARNING);
		return new EmptyComponent(new JLabel(icon), text, button);
	}

	public static EmptyComponent createDangerResult(String text, JButton button) {
		SvgIcon icon = XCircleSvg.of(64, 64);
		icon.setColorFilter(color -> ColorUtil.DANGER);
		return new EmptyComponent(new JLabel(icon), text, button);
	}

	public static EmptyComponent createInfoResult(String text, JButton button) {
		SvgIcon icon = WarningCircleSvg.of(64, 64);
		icon.setColorFilter(color -> ColorUtil.INFO);
		return new EmptyComponent(new JLabel(icon), text, button);
	}
}
