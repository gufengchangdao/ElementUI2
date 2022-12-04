package com.element.ui.button;

import com.element.color.ColorUtil;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.PropertySetter;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * 圆角Switch开关，该类继承JToggleButton实现
 */
public class SwitchButton extends JToggleButton implements ActionListener {
	/** 当前背景色，动画使用 */
	private Color closeColor = ColorUtil.DANGER;
	private Color openColor = ColorUtil.SUCCESS;
	private Animator animator;
	/** 白色按钮位移动画 */
	private TimingTargetAdapter target;
	/** 白色球体x坐标，动画使用 */
	private int xPosition;

	public SwitchButton() {
		super();
		init();
	}


	public SwitchButton(boolean isSelected) {
		super();
		setSelected(isSelected);
		init();
	}

	public SwitchButton(boolean isSelected, Color closeColor, Color openColor) {
		super();
		setSelected(isSelected);
		this.closeColor = closeColor;
		this.openColor = openColor;
		init();
	}

	private void init() {
		setBackground(isSelected() ? openColor : closeColor);

		animator = new Animator.Builder()
				.setDuration(200, TimeUnit.MILLISECONDS)
				.addTarget(PropertySetter.getTarget(this, "background", closeColor, openColor))
				.build();
		addActionListener(this);

		// 这里面有设置小球位移路径的动画
		setPreferredSize(new Dimension(50, 25));
		setBorderPainted(false);
		setFocusable(false);
	}

	@Override
	@SuppressWarnings("all")
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Dimension size = getPreferredSize();

		boolean enabled = isEnabled();
		// 绘制背景
		if (enabled) g2.setColor(getBackground());
		else
			g2.setColor(new Color(getBackground().getRed(), getBackground().getGreen(), getBackground().getBlue(), 120));
		g2.fillArc(0, 0, size.height, size.height, 90, 180);
		g2.fillRect(size.height / 2 - 1, 0, Math.max(size.width - size.height + 2, 0), size.height);
		g2.fillArc(size.width - size.height, 0, size.height, size.height, -90, 180);

		// 绘制圆点
		g2.setColor(Color.WHITE);
		int d = (int) (size.height * 0.8); //直径
		g2.fillOval(xPosition, (size.height - d) / 2, d, d);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (animator.isRunning()) return;
		if (isSelected()) animator.start();
		else animator.restartReverse();
		repaint();
	}

	public Color getCloseColor() {
		return closeColor;
	}

	public void setCloseColor(Color closeColor) {
		this.closeColor = closeColor;
	}

	public Color getOpenColor() {
		return openColor;
	}

	public void setOpenColor(Color openColor) {
		this.openColor = openColor;
	}

	public int getXPosition() {
		return xPosition;
	}

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
		repaint();
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		// 修改白色小球移动路径
		animator.removeTarget(target);
		Dimension size = getPreferredSize();
		int d = (int) (size.height * 0.8); //直径
		xPosition = isSelected() ? (size.width - (size.height + d) / 2) : ((size.height - d) / 2);
		target = PropertySetter.getTarget(this, "xPosition",
				(size.height - d) / 2, size.width - (size.height + d) / 2);
		animator.addTarget(target);
	}
}
