package com.element.ui.border;

import com.element.swing.SwingPosition;
import com.element.swing.base.AngleComponent;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * 切角边框
 * <p>
 * 该边框一般不单独使用，因为含有切角的组件一般都设置了背景色，但是背景色会覆盖边框的绘制，建议使用{@link AngleComponent}
 */
public class AngleBorder implements Border, SwingPosition {
	private final GeneralPath generalPath = new GeneralPath();
	/**
	 * 展示方式。
	 * 可选{@link #NORTH}，{@link #NORTH_EAST}，{@link #NORTH_WEST}，
	 * {@link #SOUTH}，{@link #SOUTH_EAST}，{@link #SOUTH_WEST}，
	 * {@link #EAST}，{@link #EAST_NORTH}，{@link #EAST_SOUTH}，
	 * {@link #WEST}，{@link #WEST_NORTH}，{@link #WEST_SOUTH}
	 */
	private int position;
	/** 三角形背景色 */
	private Color color;
	/** 等腰三角形的高度，也是边框的边距 */
	private int size = 10;
	/** 圆角大小 */
	private int angle = 2;
	/** 三角形相对默认位置的偏移 */
	private Point offset = new Point(0, 0);

	/**
	 * @param position 可选{@link #NORTH}，{@link #NORTH_EAST}，{@link #NORTH_WEST}，
	 *                 {@link #SOUTH}，{@link #SOUTH_EAST}，{@link #SOUTH_WEST}，
	 *                 {@link #EAST}，{@link #EAST_NORTH}，{@link #EAST_SOUTH}，
	 *                 {@link #WEST}，{@link #WEST_NORTH}，{@link #WEST_SOUTH}
	 */
	public AngleBorder(int position, Color color) {
		this.position = position;
		this.color = color;
	}

	/**
	 * @param position 可选{@link #NORTH}，{@link #NORTH_EAST}，{@link #NORTH_WEST}，
	 *                 {@link #SOUTH}，{@link #SOUTH_EAST}，{@link #SOUTH_WEST}，
	 *                 {@link #EAST}，{@link #EAST_NORTH}，{@link #EAST_SOUTH}，
	 *                 {@link #WEST}，{@link #WEST_NORTH}，{@link #WEST_SOUTH}
	 */
	public AngleBorder(int position, Color color, int size) {
		this.position = position;
		this.color = color;
		this.size = size;
	}

	/**
	 * @param position 可选{@link #NORTH}，{@link #NORTH_EAST}，{@link #NORTH_WEST}，
	 *                 {@link #SOUTH}，{@link #SOUTH_EAST}，{@link #SOUTH_WEST}，
	 *                 {@link #EAST}，{@link #EAST_NORTH}，{@link #EAST_SOUTH}，
	 *                 {@link #WEST}，{@link #WEST_NORTH}，{@link #WEST_SOUTH}
	 */
	public AngleBorder(int position, Color color, int size, int angle) {
		this.position = position;
		this.color = color;
		this.size = size;
		this.angle = angle;
	}

	/**
	 * @param position 可选{@link #NORTH}，{@link #NORTH_EAST}，{@link #NORTH_WEST}，
	 *                 {@link #SOUTH}，{@link #SOUTH_EAST}，{@link #SOUTH_WEST}，
	 *                 {@link #EAST}，{@link #EAST_NORTH}，{@link #EAST_SOUTH}，
	 *                 {@link #WEST}，{@link #WEST_NORTH}，{@link #WEST_SOUTH}
	 */
	public AngleBorder(int position, Color color, int size, int angle, Point offset) {
		this.position = position;
		this.color = color;
		this.size = size;
		this.angle = angle;
		this.offset = offset;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		// 定位坐标原点
		double tx = 0, ty = 0;
		switch (position) {
			case NORTH -> { // 上边
				tx = width / 2.0 - size;
			}
			case NORTH_WEST -> { // 上左
				tx = width / 4.0 - size;
			}
			case NORTH_EAST -> { // 上右
				tx = width * 3.0 / 4 - size;
			}
			case SOUTH, SOUTH_WEST -> { // 下边
				tx = width / 2.0 - size;
				ty = height - size;
			}// 下左
			case SOUTH_EAST -> { // 下右
				tx = width * 3.0 / 4 - size;
				ty = height - size;
			}
			case WEST -> { // 左边
				tx = 0.3;
				ty = height / 2.0 - size;
			}
			case WEST_NORTH -> {
				tx = 0.3;
				ty = height / 4.0 - size;
			}
			case WEST_SOUTH -> {
				tx = 0.3;
				ty = height * 3.0 / 4 - size;
			}
			case EAST -> {
				tx = width - size * 2 - 0.3; //这是为了消除边框与组件可能存在的缝隙
				ty = height / 2.0 - size;
			}
			case EAST_NORTH -> {
				tx = width - size * 2 - 0.4;
				ty = height / 4.0 - size;
			}
			case EAST_SOUTH -> {
				tx = width - size * 2 - 0.4;
				ty = height * 3.0 / 4 - size;
			}
		}

		tx += offset.x;
		ty += offset.y;
		g2d.translate(tx, ty);

		// 绘图
		generalPath.reset();

		// 向上
		generalPath.moveTo(0, size);
		generalPath.lineTo(size - angle, angle);
		generalPath.curveTo(size - angle, angle, size, 0, size + angle, angle);
		generalPath.lineTo(size * 2, size);

		switch (position) {
			case SOUTH, SOUTH_WEST, SOUTH_EAST -> g2d.rotate(Math.toRadians(180), size, size / 2.0);
			case WEST, WEST_NORTH, WEST_SOUTH -> g2d.rotate(Math.toRadians(-90), size, size);
			case EAST, EAST_NORTH, EAST_SOUTH -> g2d.rotate(Math.toRadians(90), size, size);
		}

		generalPath.closePath();
		g2d.setPaint(color);
		g2d.fill(generalPath);
		// g2d.draw(generalPath); //以后可以做一个只有边缘才有颜色的切角边框
		g2d.dispose();
	}

	@Override
	public Insets getBorderInsets(Component c) {
		Insets insets = new Insets(0, 0, 0, 0);
		switch (position) {
			case NORTH, NORTH_EAST, NORTH_WEST -> insets.top = size;
			case SOUTH, SOUTH_WEST, SOUTH_EAST -> insets.bottom = size;
			case WEST, WEST_NORTH, WEST_SOUTH -> insets.left = size;
			case EAST, EAST_NORTH, EAST_SOUTH -> insets.right = size;
		}
		return insets;
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}
}
