package com.element.color;

import com.element.radiance.common.api.icon.SvgIcon;

import java.awt.*;

import static com.element.color.ColorUtil.blend;

/**
 * 配合图标使用的渐变颜色过滤器，为图标颜色提供渐变的效果
 * <p>
 * 改变颜色需要手动设置 radio
 */
public class GradientColorFilter implements SvgIcon.ColorFilter {
	private Color startColor;
	private Color endColor;
	private float radio;

	public GradientColorFilter(Color startColor, Color endColor) {
		this.startColor = startColor;
		this.endColor = endColor;
	}

	@Override
	public Color filter(Color color) {
		return blend(startColor, endColor, radio);
	}

	public Color getStartColor() {
		return startColor;
	}

	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}

	public Color getEndColor() {
		return endColor;
	}

	public void setEndColor(Color endColor) {
		this.endColor = endColor;
	}

	public float getRadio() {
		return radio;
	}

	public void setRadio(float radio) {
		this.radio = radio;
	}
}
