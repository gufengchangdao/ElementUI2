/*
 * @(#)StyledLabel.java 9/6/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.label;

import com.element.plaf.LookAndFeelFactory;
import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.basic.BasicStyledLabelUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StyledLabel是一种特殊的 JLabel，可以显示不同样式的文本。它是介于 JLabel 和 JTextPane 之间的组件。
 * <p>
 * 支持的功能：
 * <ul>
 *     <li>线条装饰，包括实线、虚线、波浪线、双实线或任何可以由Java2D的Stroke类定义的任意线条样式。</li>
 *     <li>两个线的位置-有下划线或条纹，或两者都有。</li>
 *     <li>可以换行。您可以指定默认值、最小行数、最大行数和首选宽度。</li>
 *     <li>可以用作JList、JTable或JTree的单元格渲染器。</li>
 *     <li>注释支持使用StyledLabelBuilder，以便您可以使用注释字符串来定义样式标签。</li>
 * </ul>
 * <p>
 * JLabel 简单、快速但功能有限。例如，您不能使用不同的颜色来绘制文本。您可能会争辩说 JLabel 可以使用 HTML 标记来显示不同颜色的文本。然而，
 * 使用 StyledLabel 有两个主要原因。首先，StyledLabel 非常快，几乎与纯文本的 JLabel 一样快。 StyledLabel 文件夹以查看 HTML JLabel
 * 和 StyledLabel 的性能测试。HTML JLabel 也有问题。请参阅http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4373575 上的错
 * 误报告。 Sun 声称它已修复，但并非如另一位用户最后指出的那样。如果您运行原始提交者提供的测试用例，您会立即注意到当您单击树节点时树节点消失
 * 了。这个错误实际上是我们决定创建StyledLabel 的主要原因之一。 JTextPane 功能强大，可以显示不同颜色的文本。但在像单元格渲染器这样的情况
 * 下，JTextPane 显然是一个大材小用。
 * <p>
 * StyledLabel 位于 JLabel 和 JTextPane 之间，提供了一种非常简单快速的方式来显示不同颜色和样式的文本。它还可以支持使用各种线条样式的装饰。
 * <p>
 * JLabel 上的所有方法仍然像以前一样工作。 StyledLabel 中添加的方法是 StyleRange 的几种方法，例如
 * {@link #addStyleRange(StyleRange)}, {@link #setStyleRanges(StyleRange[])}, {@link #clearStyleRange(StyleRange)}, 和
 * {@link #clearStyleRanges()}
 * <p>
 * 这是关于 StyleRange 的一件事，您应该知道，这可以被视为未来的增强项目，即 StyleRanges 不能相互重叠。例如，如果您定义了一个涵盖从索引
 * 0 到索引 3 的 StyleRange，则您不能定义与第一个重叠的任何其他 StyleRange。如果这样做，第二个 StyleRange 将被忽略。
 * <p>
 * 我们在设计StyledLabel时借鉴了SWT的StyledText的一些思想，尤其是StyleRange的概念。话虽如此，这两个组件的功能并不完全相同，因为这两个组
 * 件的用途完全不同。
 */
public class StyledLabel extends JLabel {
	private static final String uiClassID = "StyledLabelUI";

	/**
	 * The list of StyleRanges.
	 */
	private List<StyleRange> _styleRanges;
	private boolean _lineWrap;
	private int _rows;
	private int _maxRows;
	private int _minRows;
	private int _preferredWidth;
	private int _rowGap;
	private boolean _truncated = false;

	private boolean _ignoreColorSettings;

	public static final String PROPERTY_STYLE_RANGE = "styleRange";
	public static final String PROPERTY_IGNORE_COLOR_SETTINGS = "ignoreColorSettings";

	public StyledLabel() {
		setMaximumSize(null);
	}

	public StyledLabel(Icon image) {
		super(image);
		setMaximumSize(null);
	}

	public StyledLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		setMaximumSize(null);
	}

	public StyledLabel(String text) {
		super(text);
		setMaximumSize(null);
	}

	public StyledLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		setMaximumSize(null);
	}

	public StyledLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		setMaximumSize(null);
	}

	/**
	 * Resets the UI property to a value from the current look and feel.
	 *
	 * @see JComponent#updateUI
	 */
	@Override
	public void updateUI() {
		if (UIDefaultsLookup.get(uiClassID) == null) {
			LookAndFeelFactory.installJideExtension();
		}
		setUI(UIManager.getUI(this));
	}


	/**
	 * Returns a string that specifies the name of the L&F class that renders this component.
	 *
	 * @return the string "StyledLabelUI"
	 * @see JComponent#getUIClassID
	 * @see UIDefaults#getUI
	 */
	@Override
	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * Adds a StyleRange into this <code>StyledLabel</code>.
	 *
	 * @param styleRange the new StyleRange.
	 */
	public synchronized void addStyleRange(StyleRange styleRange) {
		if (styleRange == null) {
			throw new IllegalArgumentException("StyleRange cannot be null.");
		}
		List<StyleRange> ranges = internalGetStyleRanges();
		for (int i = ranges.size() - 1; i >= 0; i--) {
			StyleRange range = ranges.get(i);
			if (range.getStart() == styleRange.getStart() && range.getLength() == styleRange.getLength()) {
				ranges.remove(i);
			}
		}
		internalGetStyleRanges().add(styleRange);
		firePropertyChange(PROPERTY_STYLE_RANGE, null, styleRange);
	}

	/**
	 * Clears all the old StyleRanges and adds a list of StyleRanges into this <code>StyledLabel</code>.
	 *
	 * @param styleRanges set the StyleRanges.
	 */
	public synchronized void setStyleRanges(StyleRange[] styleRanges) {
		internalGetStyleRanges().clear();
		addStyleRanges(styleRanges);
	}

	/**
	 * Adds a list of StyleRanges into this <code>StyledLabel</code>.
	 *
	 * @param styleRanges an array of StyleRanges.
	 */
	public synchronized void addStyleRanges(StyleRange[] styleRanges) {
		if (styleRanges != null) {
			for (StyleRange styleRange : styleRanges) {
				internalGetStyleRanges().add(styleRange);
			}
			firePropertyChange(PROPERTY_STYLE_RANGE, null, styleRanges);
		} else {
			firePropertyChange(PROPERTY_STYLE_RANGE, null, null);
		}
	}

	/**
	 * Gets the array of StyledText.
	 *
	 * @return the array of StyledText.
	 */
	public synchronized StyleRange[] getStyleRanges() {
		List<StyleRange> list = internalGetStyleRanges();
		return list.toArray(new StyleRange[0]);
	}

	private List<StyleRange> internalGetStyleRanges() {
		if (_styleRanges == null) {
			_styleRanges = new ArrayList<>();
		}
		return _styleRanges;
	}

	/**
	 * Removes the StyleRange.
	 *
	 * @param styleRange the StyleRange to be removed.
	 */
	public synchronized void clearStyleRange(StyleRange styleRange) {
		if (internalGetStyleRanges().remove(styleRange)) {
			firePropertyChange(PROPERTY_STYLE_RANGE, styleRange, null);
		}
	}

	/**
	 * Clears all the StyleRanges.
	 */
	public synchronized void clearStyleRanges() {
		internalGetStyleRanges().clear();
		firePropertyChange(PROPERTY_STYLE_RANGE, null, null);
	}

	/**
	 * StyleRange could define color for the text and lines. However when StyledLabel is used in cell renderer, the
	 * color could be conflict with selection color. So usually when it is used as cell renderer, the color defined in
	 * StyleRange should be ignored when cell is selected. If so, the foreground is used to paint all text and lines.
	 *
	 * @return true if the color defined by StyleRange should be ignored.
	 */
	public boolean isIgnoreColorSettings() {
		return _ignoreColorSettings;
	}

	/**
	 * Sets if the color defined by StyleRange should be ignored. This flag is used when StyledLabel is used as a
	 * selected cell renderer. Since the selection usually has it own unique selection background and foreground, the
	 * color setting set on this StyledLabel could be unreadable on the selection background, it'd better if we don't
	 * use any color settings in this case.
	 *
	 * @param ignoreColorSettings true or false.
	 */
	public void setIgnoreColorSettings(boolean ignoreColorSettings) {
		boolean old = _ignoreColorSettings;
		if (old != ignoreColorSettings) {
			_ignoreColorSettings = ignoreColorSettings;
			firePropertyChange(PROPERTY_IGNORE_COLOR_SETTINGS, old, ignoreColorSettings);
		}
	}

	@Override
	public Dimension getMinimumSize() {
		return isLineWrap() ? new Dimension(1, 1) : super.getMinimumSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return isLineWrap() ? new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE) : super.getMaximumSize();
	}

	/**
	 * Sets the preferred width of the styled label.
	 *
	 * @param preferredWidth the preferred width
	 * @since 3.2.0
	 */
	public void setPreferredWidth(int preferredWidth) {
		_preferredWidth = preferredWidth;
	}

	/**
	 * Gets the preferred width of the styled label.
	 *
	 * @return the preferred width
	 * @since 3.2.0
	 */
	public int getPreferredWidth() {
		return _preferredWidth;
	}

	/**
	 * Gets the flag indicating if the line should be automatically wrapped when the column width is limited.
	 *
	 * @return true if line wrap is needed. Otherwise false.
	 * @since 3.2.0
	 */
	public boolean isLineWrap() {
		return _lineWrap;
	}

	/**
	 * Sets the flag indicating if the line should be automatically wrapped when the column width is limited.
	 *
	 * @param lineWrap the flag
	 * @since 3.2.0
	 */
	public void setLineWrap(boolean lineWrap) {
		_lineWrap = lineWrap;
	}

	/**
	 * Gets the default row count to wrap the {@link StyledLabel}.
	 *
	 * @return the row count.
	 * @see #setRows(int)
	 * @since 3.2.0
	 */
	public int getRows() {
		return _rows;
	}

	/**
	 * Sets the default row count to wrap the {@link StyledLabel}.
	 * <p/>
	 * By default, the value is 0. Any non-positive value is deemed as not configured.
	 * <p/>
	 * This has lower priority than {@link #setPreferredWidth(int)}. If preferred width is set, this flag does not take
	 * effect.
	 *
	 * @param rows the row count
	 * @since 3.2.0
	 */
	public void setRows(int rows) {
		_rows = rows;
	}

	/**
	 * Gets the gap pixels between rows.
	 *
	 * @return the gap pixels.
	 * @see #setRowGap(int)
	 * @since 3.2.0
	 */
	public int getRowGap() {
		return _rowGap;
	}

	/**
	 * Sets the gap pixels between rows.
	 * <p/>
	 * By default, the value is 0.
	 *
	 * @param rowGap the gap pixels.
	 */
	public void setRowGap(int rowGap) {
		_rowGap = rowGap;
	}

	/**
	 * Gets the maximum rows possible after wrapping.
	 *
	 * @return the maximum rows.
	 * @since 3.2.0
	 */
	public int getMaxRows() {
		return _maxRows;
	}

	/**
	 * Sets the maximum rows possible after wrapping.
	 * <p/>
	 * By default, the value is 0. Any non-positive value is deemed as not configured.
	 *
	 * @param maxRows the maximum rows
	 * @since 3.2.0
	 */
	public void setMaxRows(int maxRows) {
		_maxRows = maxRows;
	}

	/**
	 * Gets the minimum rows possible after wrapping.
	 *
	 * @return the minimum rows.
	 * @since 3.2.0
	 */
	public int getMinRows() {
		return _minRows;
	}

	/**
	 * Sets the minimum rows possible after wrapping.
	 * <p/>
	 * By default, the value is 0. Any non-positive value is deemed as not configured.
	 *
	 * @param minRows the minimum rows
	 * @since 3.2.0
	 */
	public void setMinRows(int minRows) {
		_minRows = minRows;
	}

	/**
	 * Gets the status indicating if the StyledLabel is painted truncated.
	 *
	 * @return true if its truncated. Otherwise false.
	 * @since 3.2.1
	 */
	public boolean isTruncated() {
		return _truncated;
	}

	/**
	 * It will be invoked by {@link BasicStyledLabelUI} each time the StyledLabel is painted.
	 * Please do NOT try to call this method to change the flag and NOT expect the setting could change the behavior of
	 * {@link #isTruncated()}.
	 *
	 * @param truncated the flag
	 * @since 3.2.1
	 */
	public void setTruncated(boolean truncated) {
		_truncated = truncated;
	}
}
