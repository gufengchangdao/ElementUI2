package com.element.ui.base;

import com.element.radiance.common.api.icon.SvgIcon;

import java.awt.*;

/**
 * 以图标为背景的组件
 * <p>
 * 该组件可以
 * <ul>
 *     <li>设置背景图标，且图标随着背景大小改变自适应</li>
 *     <li>设置背景图标的内边距，背景大小也会因为内边距的设置而改变</li>
 * <ul/>
 */
public class BackgroundComponent extends BaseComponent {
	/** 背景 */
	private SvgIcon icon;
	private int rate = 1;

	public BackgroundComponent(SvgIcon icon) {
		this.icon = icon;
	}

	public BackgroundComponent(SvgIcon icon, int rate) {
		this.icon = icon;
		setRate(rate);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// 图标居中
		icon.paintIcon(this, g, (getWidth() - icon.getIconWidth()) / 2,
				(getHeight() - icon.getIconHeight()) / 2
				// -25

		);
		super.paintComponent(g);
	}

	/** 根据组件缩放比例缩放图标 */
	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		icon.setDimension(new Dimension(preferredSize.width * rate, preferredSize.height * rate));
	}

	public SvgIcon getIcon() {
		return icon;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
		Dimension size = getPreferredSize();
		icon.setDimension(new Dimension(size.width * rate, size.height * rate));
	}
}
