package com.element.ui.alert;

import com.element.color.ColorUtil;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.swing.template.X2Component;
import com.element.ui.svg.icon.fill.CheckCircleSvg;
import com.element.ui.svg.icon.fill.WarningCircleSvg;
import com.element.ui.svg.icon.fill.XCircleSvg;

import java.awt.*;

/**
 * 警告工厂类
 */
public class AlertFactory {
	public static SvgIcon getSuccessIcon(int width, int height) {
		SvgIcon icon = CheckCircleSvg.of(width, height);
		icon.setColorFilter(color -> ColorUtil.SUCCESS);
		return icon;
	}

	public static SvgIcon getWarningIcon(int width, int height) {
		SvgIcon icon = WarningCircleSvg.of(width, height);
		icon.setColorFilter(color -> ColorUtil.WARNING);
		return icon;
	}

	public static SvgIcon getDangerIcon(int width, int height) {
		SvgIcon icon = CheckCircleSvg.of(width, height);
		icon.setColorFilter(color -> ColorUtil.DANGER);
		return icon;
	}

	public static SvgIcon getInfoIcon(int width, int height) {
		SvgIcon icon = CheckCircleSvg.of(width, height);
		icon.setColorFilter(color -> ColorUtil.INFO);
		return icon;
	}


	public static AlertComponent createSuccessAlert(String text, boolean isContainIcon, boolean closeable,
	                                                X2Component.GrowStyle style) {
		return new AlertComponent(isContainIcon ? CheckCircleSvg.of(16, 16) : null,
				text, ColorUtil.SUCCESS, closeable, style, new Insets(8, 16, 8, 16));
	}

	public static AlertComponent createWarningAlert(String text, boolean isContainIcon, boolean closeable,
	                                                X2Component.GrowStyle style) {
		return new AlertComponent(isContainIcon ? WarningCircleSvg.of(16, 16) : null,
				text, ColorUtil.WARNING, closeable, style, new Insets(8, 16, 8, 16));
	}

	public static AlertComponent createDangerAlert(String text, boolean isContainIcon, boolean closeable,
	                                               X2Component.GrowStyle style) {
		return new AlertComponent(isContainIcon ? XCircleSvg.of(16, 16) : null,
				text, ColorUtil.DANGER, closeable, style, new Insets(8, 16, 8, 16));
	}

	public static AlertComponent createInfoAlert(String text, boolean isContainIcon, boolean closeable,
	                                             X2Component.GrowStyle style) {
		return new AlertComponent(isContainIcon ? WarningCircleSvg.of(16, 16) : null,
				text, ColorUtil.INFO, closeable, style, new Insets(8, 16, 8, 16));
	}
}
