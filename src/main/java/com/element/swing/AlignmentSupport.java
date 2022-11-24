package com.element.swing;

/**
 * 对齐支持的通用接口。如果JIDE 组件具有以下方法，则所有JIDE 组件都将实现该方法。
 * 在标准的 Swing 包中，AbstractButton、JLabel、JTextField 等也应该实现这一点。
 */
public interface AlignmentSupport {
	/**
	 * Returns the horizontal alignment of the content.
	 * {@code AbstractButton}'s default is {@code SwingConstants.CENTER},
	 * but subclasses such as {@code JCheckBox} may use a different default.
	 *
	 * @return the <code>horizontalAlignment</code> property,
	 * one of the following values:
	 * <ul>
	 * <li>{@code SwingConstants.RIGHT}
	 * <li>{@code SwingConstants.LEFT}
	 * <li>{@code SwingConstants.CENTER}
	 * <li>{@code SwingConstants.LEADING}
	 * <li>{@code SwingConstants.TRAILING}
	 * </ul>
	 */
	int getHorizontalAlignment();

	/**
	 * Sets the horizontal alignment of the content.
	 * {@code AbstractButton}'s default is {@code SwingConstants.CENTER},
	 * but subclasses such as {@code JCheckBox} may use a different default.
	 *
	 * @param alignment the alignment value, one of the following values:
	 *                  <ul>
	 *                  <li>{@code SwingConstants.RIGHT}
	 *                  <li>{@code SwingConstants.LEFT}
	 *                  <li>{@code SwingConstants.CENTER}
	 *                  <li>{@code SwingConstants.LEADING}
	 *                  <li>{@code SwingConstants.TRAILING}
	 *                  </ul>
	 * @throws IllegalArgumentException if the alignment is not one of the
	 *                                  valid values
	 */
	void setHorizontalAlignment(int alignment);

	/**
	 * Returns the vertical alignment of the content.
	 *
	 * @return the <code>verticalAlignment</code> property, one of the
	 * following values:
	 * <ul>
	 * <li>{@code SwingConstants.CENTER} (the default)
	 * <li>{@code SwingConstants.TOP}
	 * <li>{@code SwingConstants.BOTTOM}
	 * </ul>
	 */
	int getVerticalAlignment();

	/**
	 * Sets the vertical alignment of the content.
	 *
	 * @param alignment one of the following values:
	 *                  <ul>
	 *                  <li>{@code SwingConstants.CENTER} (the default)
	 *                  <li>{@code SwingConstants.TOP}
	 *                  <li>{@code SwingConstants.BOTTOM}
	 *                  </ul>
	 * @throws IllegalArgumentException if the alignment is not one of the legal
	 *                                  values listed above
	 */
	void setVerticalAlignment(int alignment);
}
