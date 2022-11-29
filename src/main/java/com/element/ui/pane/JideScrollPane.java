/*
 * @(#)JideScrollPane.java
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.pane;

import com.element.util.ListenerUtil;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;

/**
 * JideScrollPane是JScrollPane的增强版。在JScrollPane中，你可以有 rowHeader 和 columnHeader。但是，您不能有 rowFooter 和
 * columnFooter。然而 rowFooter 和 columnFooter 在表格中非常有用。例如，它们可用于显示“总计”或“摘要”类型的信息。
 * <p>
 * 添加了与rowFooter和columnFooter相关的几个方法，例如{@link #setRowFooter(JViewport)}和{@link #setColumnFooter(JViewport)} ，
 * 它们将视口分别设置为rowFooter 和columnFooter 区域。这些方法的用法与{@link JScrollPane#setRowHeader(JViewport)}完全相同。
 * <p>
 * 为了充分利用 JideScrollPane 的强大功能，我们还创建了一个名为TableScrollPane的类，它是 JIDE Grids 包的一部分。它将允许您轻松创建带有
 * 行标题、行脚注和列脚注的表格。JideScrollPane还提供了对滚动条角的支持。您可以使用{@link #setScrollBarCorner(String, Component)}设
 * 置它们。滚动条角的可用键在{@link JideScrollPaneConstants}中定义，可以从JideScrollPane访问。
 *
 * <b>Credit:</b> This implementation of scroll bar corner is based on work from Santhosh Kumar -
 * santhosh@in.fiorano.com.
 */
public class JideScrollPane extends JScrollPane implements JideScrollPaneConstants {

	/**
	 * The row footer child.  Default is <code>null</code>.
	 *
	 * @see #setRowFooter(JViewport)
	 */
	protected JViewport _rowFooter;


	/**
	 * The component under column header.  Default is <code>null</code>.
	 *
	 * @see #setSubColumnHeader(JViewport)
	 */
	protected JViewport _subColumnHeader;
	/**
	 * The component under upper left corner.  Default is <code>null</code>.
	 *
	 * @see #setCorner(String, Component)
	 */
	protected Component _subUpperLeft;
	/**
	 * The component under upper right corner.  Default is <code>null</code>.
	 *
	 * @see #setCorner(String, Component)
	 */
	protected Component _subUpperRight;
	/**
	 * The column footer child.  Default is <code>null</code>.
	 *
	 * @see #setColumnFooter(JViewport)
	 */
	protected JViewport _columnFooter;

	/**
	 * The component to the left of horizontal scroll bar.
	 */
	protected Component _hLeft;
	/**
	 * The component to the right of horizontal scroll bar.
	 */
	protected Component _hRight;

	/**
	 * The component to the top of vertical scroll bar.
	 */
	protected Component _vTop;

	/**
	 * The component to the bottom of vertical scroll bar.
	 */
	protected Component _vBottom;

	private boolean _keepCornerVisible = false;

	private boolean _horizontalScrollBarCoversWholeWidth;
	private boolean _verticalScrollBarCoversWholeHeight;
	private boolean _flatLayout = false;

	public static final String PROPERTY_HORIZONTAL_SCROLL_BAR_COVERS_WHOLE_WIDTH = "horizontalScrollBarCoversWholeWidth";
	public static final String PROPERTY_VERTICAL_SCROLL_BAR_COVERS_WHOLE_HEIGHT = "verticalScrollBarCoversWholeHeight";
	public static final String PROPERTY_KEEP_CORNER_VISIBLE = "keepCornerVisible";
	public static final String PROPERTY_FLAT_LAYOUT = "flatLayout";

	private boolean _columnHeadersHeightUnified;
	private boolean _columnFootersHeightUnified;
	public static final String PROPERTY_COLUMN_HEADERS_HEIGHT_UNIFIED = "columnHeadersHeightUnified";
	public static final String PROPERTY_COLUMN_FOOTERS_HEIGHT_UNIFIED = "columnFootersHeightUnified";

	public static final String CLIENT_PROPERTY_SLAVE_VIEWPORT = "synchronizeViewSlaveViewport";
	public static final String CLIENT_PROPERTY_MASTER_VIEWPORT = "synchronizeViewMasterViewport";

	/**
	 * Creates a <code>JideScrollPane</code> that displays the view component in a viewport whose view position can be
	 * controlled with a pair of scrollbars. The scrollbar policies specify when the scrollbars are displayed, For
	 * example, if <code>vsbPolicy</code> is <code>VERTICAL_SCROLLBAR_AS_NEEDED</code> then the vertical scrollbar only
	 * appears if the view doesn't fit vertically. The available policy settings are listed at {@link
	 * #setVerticalScrollBarPolicy(int)} and {@link #setHorizontalScrollBarPolicy(int)}.
	 *
	 * @param view      the component to display in the scrollpanes viewport
	 * @param vsbPolicy an integer that specifies the vertical scrollbar policy
	 * @param hsbPolicy an integer that specifies the horizontal scrollbar policy
	 * @see #setViewportView(Component)
	 */
	public JideScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		setLayout(new JideScrollPaneLayout.UIResource());
		setVerticalScrollBarPolicy(vsbPolicy);
		setHorizontalScrollBarPolicy(hsbPolicy);
		setViewport(createViewport());
		setVerticalScrollBar(createVerticalScrollBar());
		setHorizontalScrollBar(createHorizontalScrollBar());
		if (null != view) {
			setViewportView(view);
		}
		setOpaque(true);
		updateUI();

		if (!getComponentOrientation().isLeftToRight()) {
			viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
		}
	}


	/**
	 * Creates a <code>JideScrollPane</code> that displays the contents of the specified component, where both
	 * horizontal and vertical scrollbars appear whenever the component's contents are larger than the view.
	 *
	 * @param view the component to display in the scrollpane's viewport
	 * @see #setViewportView(Component)
	 */
	public JideScrollPane(Component view) {
		this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}


	/**
	 * Creates an empty (no viewport view) <code>JideScrollPane</code> with specified scrollbar policies. The available
	 * policy settings are listed at {@link #setVerticalScrollBarPolicy(int)} and {@link
	 * #setHorizontalScrollBarPolicy(int)}.
	 *
	 * @param vsbPolicy an integer that specifies the vertical scrollbar policy
	 * @param hsbPolicy an integer that specifies the horizontal scrollbar policy
	 * @see #setViewportView(Component)
	 */
	public JideScrollPane(int vsbPolicy, int hsbPolicy) {
		this(null, vsbPolicy, hsbPolicy);
	}


	/**
	 * Creates an empty (no viewport view) <code>JideScrollPane</code> where both horizontal and vertical scrollbars
	 * appear when needed.
	 */
	public JideScrollPane() {
		this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	@Override
	public void setViewport(JViewport viewport) {
		JViewport old = getViewport();
		super.setViewport(viewport);
		if (old != null) {
			if (rowHeader != null) {
				ListenerUtil.unsynchronizeView(rowHeader, old);
			}
			if (_rowFooter != null) {
				ListenerUtil.unsynchronizeView(_rowFooter, old);
				ListenerUtil.unsynchronizeView(old, _rowFooter);
			}
			if (_columnFooter != null) {
				ListenerUtil.unsynchronizeView(_columnFooter, old);
				ListenerUtil.unsynchronizeView(old, _columnFooter);
			}
			if (columnHeader != null) {
				ListenerUtil.unsynchronizeView(columnHeader, old);
			}
			if (_subColumnHeader != null) {
				ListenerUtil.unsynchronizeView(_subColumnHeader, old);
				ListenerUtil.unsynchronizeView(old, _subColumnHeader);
			}
		}
		if (viewport != null) {
			if (rowHeader != null) {
				ListenerUtil.synchronizeView(rowHeader, getViewport(), SwingConstants.VERTICAL);
			}
			if (_rowFooter != null) {
				ListenerUtil.synchronizeView(_rowFooter, getViewport(), SwingConstants.VERTICAL);
				ListenerUtil.synchronizeView(getViewport(), _rowFooter, SwingConstants.VERTICAL);
			}
			if (_columnFooter != null) {
				ListenerUtil.synchronizeView(_columnFooter, getViewport(), SwingConstants.HORIZONTAL);
				ListenerUtil.synchronizeView(getViewport(), _columnFooter, SwingConstants.HORIZONTAL);
			}
			if (columnHeader != null) {
				ListenerUtil.synchronizeView(columnHeader, getViewport(), SwingConstants.HORIZONTAL);
			}
			if (_subColumnHeader != null) {
				ListenerUtil.synchronizeView(_subColumnHeader, getViewport(), SwingConstants.HORIZONTAL);
				ListenerUtil.synchronizeView(getViewport(), _subColumnHeader, SwingConstants.HORIZONTAL);
			}
		}
	}

	/**
	 * Returns the row footer.
	 *
	 * @return the <code>rowFooter</code> property
	 * @see #setRowFooter(JViewport)
	 */
	public JViewport getRowFooter() {
		return _rowFooter;
	}


	/**
	 * Removes the old rowFooter, if it exists.  If the new rowFooter isn't <code>null</code>, syncs the y coordinate of
	 * its viewPosition with the viewport (if there is one) and then adds it to the scrollpane.
	 *
	 * @param rowFooter the new row footer to be used; if <code>null</code> the old row footer is still removed and the
	 *                  new rowFooter is set to <code>null</code>
	 * @see #getRowFooter
	 * @see #setRowFooterView(Component)
	 */
	public void setRowFooter(JViewport rowFooter) {
		JViewport old = getRowFooter();
		_rowFooter = rowFooter;
		if (null != rowFooter) {
			add(rowFooter, ROW_FOOTER);
		} else if (null != old) {
			remove(old);
		}
		firePropertyChange("rowFooter", old, rowFooter);
		revalidate();
		repaint();
		if (old != null) {
			ListenerUtil.unsynchronizeView(old, getViewport());
			ListenerUtil.unsynchronizeView(getViewport(), old);
		}
		if (rowFooter != null) {
			ListenerUtil.synchronizeView(rowFooter, getViewport(), SwingConstants.VERTICAL);
			ListenerUtil.synchronizeView(getViewport(), rowFooter, SwingConstants.VERTICAL);
		}
	}

	/**
	 * Override setRowHeader method in JScrollPane and synchronize the view with the main viewport. Swing tried to
	 * implement this feature but it will break if the view position changes starts from rowHeader.
	 *
	 * @param rowHeader the new row header
	 */
	@Override
	public void setRowHeader(JViewport rowHeader) {
		JViewport old = getRowHeader();
		super.setRowHeader(rowHeader);
		if (old != null) {
			ListenerUtil.unsynchronizeView(old, getViewport());
			ListenerUtil.unsynchronizeView(getViewport(), old);
		}
		if (getRowHeader() != null) {
			ListenerUtil.synchronizeView(getRowHeader(), getViewport(), SwingConstants.VERTICAL);
			ListenerUtil.synchronizeView(getViewport(), getRowHeader(), SwingConstants.VERTICAL);
		}
	}

	/**
	 * Creates a row-footer viewport if necessary, sets its view and then adds the row-footer viewport to the
	 * scrollpane.  For example:
	 * <pre>
	 * JScrollPane scrollpane = new JideScrollPane();
	 * scrollpane.setViewportView(myBigComponentToScroll);
	 * scrollpane.setRowFooterView(myBigComponentsRowFooter);
	 * </pre>
	 *
	 * @param view the component to display as the row footer
	 * @see #setRowFooter(JViewport)
	 * @see JViewport#setView(Component)
	 */
	public void setRowFooterView(Component view) {
		if (null == getRowFooter()) {
			setRowFooter(createViewport());
		}
		getRowFooter().setView(view);
	}


	/**
	 * Returns the column footer.
	 *
	 * @return the <code>columnFooter</code> property
	 * @see #setColumnFooter(JViewport)
	 */
	public JViewport getColumnFooter() {
		return _columnFooter;
	}


	/**
	 * Removes the old columnFooter, if it exists.  If the new columnFooter isn't <code>null</code>, sync the x
	 * coordinate of the its viewPosition with the viewport (if there is one) and then add it to the scrollpane.
	 *
	 * @param columnFooter the new column footer to be used; if <code>null</code> the old column footer is still removed
	 *                     and the new columnFooter is set to <code>null</code>
	 * @see #getColumnFooter
	 * @see #setColumnFooterView(Component)
	 */
	public void setColumnFooter(JViewport columnFooter) {
		JViewport old = getColumnFooter();
		_columnFooter = columnFooter;
		if (null != columnFooter) {
			add(columnFooter, COLUMN_FOOTER);
		} else if (null != old) {
			remove(old);
		}
		firePropertyChange("columnFooter", old, columnFooter);

		revalidate();
		repaint();

		if (old != null) {
			ListenerUtil.unsynchronizeView(old, getViewport());
			ListenerUtil.unsynchronizeView(getViewport(), old);
		}
		if (_columnFooter != null) {
			ListenerUtil.synchronizeView(_columnFooter, getViewport(), SwingConstants.HORIZONTAL);
			ListenerUtil.synchronizeView(getViewport(), _columnFooter, SwingConstants.HORIZONTAL);
		}
	}

	/**
	 * Overrides to make column header viewport synchronizing with the main viewport.
	 *
	 * @param columnHeader the column header
	 */
	@Override
	public void setColumnHeader(JViewport columnHeader) {
		JViewport old = getColumnHeader();
		super.setColumnHeader(columnHeader);
		if (old != null) {
			ListenerUtil.unsynchronizeView(old, getViewport());
		}
		if (getColumnHeader() != null) {
			ListenerUtil.synchronizeView(getColumnHeader(), getViewport(), SwingConstants.HORIZONTAL);
		}
	}

	/**
	 * Returns the sub column header.
	 *
	 * @return the <code>rowSubColumnHeader</code> property
	 * @see #setSubColumnHeader(JViewport)
	 */
	public JViewport getSubColumnHeader() {
		return _subColumnHeader;
	}

	/**
	 * Removes the old sub column header, if it exists.  If the new sub column header isn't <code>null</code>, sync the
	 * x coordinate of the its viewPosition with the viewport (if there is one) and then add it to the scroll pane.
	 *
	 * @param subColumnHeader the new sub column header to be used; if <code>null</code> the old sub column header is
	 *                        still removed and the new sub column header is set to <code>null</code>
	 * @see #getSubColumnHeader()
	 */
	public void setSubColumnHeader(JViewport subColumnHeader) {
		JViewport old = getSubColumnHeader();
		_subColumnHeader = subColumnHeader;
		if (null != subColumnHeader) {
			add(subColumnHeader, SUB_COLUMN_HEADER);
		} else if (null != old) {
			remove(old);
		}
		firePropertyChange("subColumnHeader", old, subColumnHeader);

		revalidate();
		repaint();

		if (old != null) {
			ListenerUtil.unsynchronizeView(old, getViewport());
			ListenerUtil.unsynchronizeView(getViewport(), old);
		}
		if (_subColumnHeader != null) {
			ListenerUtil.synchronizeView(_subColumnHeader, getViewport(), SwingConstants.HORIZONTAL);
			ListenerUtil.synchronizeView(getViewport(), _subColumnHeader, SwingConstants.HORIZONTAL);
		}
	}

	/**
	 * Creates a column-footer viewport if necessary, sets its view, and then adds the column-footer viewport to the
	 * scrollpane.  For example:
	 * <pre>
	 * JScrollPane scrollpane = new JideScrollPane();
	 * scrollpane.setViewportView(myBigComponentToScroll);
	 * scrollpane.setColumnFooterView(myBigComponentsColumnFooter);
	 * </pre>
	 *
	 * @param view the component to display as the column footer
	 * @see #setColumnFooter(JViewport)
	 * @see JViewport#setView(Component)
	 */
	public void setColumnFooterView(Component view) {
		if (null == getColumnFooter()) {
			setColumnFooter(createViewport());
		}
		getColumnFooter().setView(view);
	}

	/**
	 * Creates a sub-column-header viewport if necessary, sets its view, and then adds the sub-column-header viewport to
	 * the scrollpane.
	 *
	 * @param view the component to display as the sub column header
	 * @see #setSubColumnHeader(JViewport)
	 * @see JViewport#setView(Component)
	 */
	public void setSubColumnHeaderView(Component view) {
		if (null == getSubColumnHeader()) {
			setSubColumnHeader(createViewport());
		}
		getSubColumnHeader().setView(view);
	}

	@Override
	public Component getCorner(String key) {
		if (key == null) {
			return null;
		}
		if (key.equals(SUB_UPPER_LEFT)) {
			return _subUpperLeft;
		} else if (key.equals(SUB_UPPER_RIGHT)) {
			return _subUpperRight;
		}
		return super.getCorner(key);
	}

	@Override
	public void setCorner(String key, Component corner) {
		if (key == null) {
			return;
		}
		if (key.equals(SUB_UPPER_LEFT) || key.equals(SUB_UPPER_RIGHT)) {
			Component old;
			if (key.equals(SUB_UPPER_LEFT)) {
				old = _subUpperLeft;
				_subUpperLeft = corner;
			} else {
				old = _subUpperRight;
				_subUpperRight = corner;
			}
			if (old != null) {
				remove(old);
			}
			if (corner != null) {
				add(corner, key);
			}
			firePropertyChange(key, old, corner);
			revalidate();
			repaint();
			return;
		}
		super.setCorner(key, corner);
	}

	/**
	 * Returns the component at the specified scroll bar corner. The <code>key</code> value specifying the corner is one
	 * of: <ul> <li>{@link JideScrollPane#HORIZONTAL_LEFT} <li>{@link JideScrollPane#HORIZONTAL_RIGHT} <li>{@link
	 * JideScrollPane#VERTICAL_TOP} <li>{@link JideScrollPane#VERTICAL_BOTTOM} <li>{@link
	 * JideScrollPane#HORIZONTAL_LEADING} <li>{@link JideScrollPane#HORIZONTAL_TRAILING} </ul>
	 *
	 * @param key one of the values as shown above
	 * @return one of the components listed below or <code>null</code> if <code>key</code> is invalid: <ul>
	 * <li>lowerLeft <li>lowerRight <li>upperLeft <li>upperRight </ul>
	 * @see #setCorner(String, Component)
	 */
	public Component getScrollBarCorner(String key) {
		boolean isLeftToRight = getComponentOrientation().isLeftToRight();
		if (key.equals(HORIZONTAL_LEADING)) {
			key = isLeftToRight ? HORIZONTAL_LEFT : HORIZONTAL_RIGHT;
		} else if (key.equals(HORIZONTAL_TRAILING)) {
			key = isLeftToRight ? HORIZONTAL_RIGHT : HORIZONTAL_LEFT;
		}

		return switch (key) {
			case HORIZONTAL_LEFT -> _hLeft;
			case HORIZONTAL_RIGHT -> _hRight;
			case VERTICAL_BOTTOM -> _vBottom;
			case VERTICAL_TOP -> _vTop;
			default -> null;
		};
	}


	/**
	 * Adds a child that will appear in one of the scroll bars corners. Scroll bar will make room to show the corner
	 * component. Legal values for the <b>key</b> are: <ul> <li>{@link JideScrollPane#HORIZONTAL_LEFT} <li>{@link
	 * JideScrollPane#HORIZONTAL_RIGHT} <li>{@link JideScrollPane#VERTICAL_TOP} <li>{@link
	 * JideScrollPane#VERTICAL_BOTTOM} <li>{@link JideScrollPane#HORIZONTAL_LEADING} <li>{@link
	 * JideScrollPane#HORIZONTAL_TRAILING} </ul>
	 * <p/>
	 * Although "corner" doesn't match any beans property signature, <code>PropertyChange</code> events are generated
	 * with the property name set to the corner key.
	 *
	 * @param key    identifies which corner the component will appear in
	 * @param corner one of the following components: <ul> <li>lowerLeft <li>lowerRight <li>upperLeft <li>upperRight
	 *               </ul>
	 * @throws IllegalArgumentException if corner key is invalid
	 */
	public void setScrollBarCorner(String key, Component corner) {
		Component old;
		boolean isLeftToRight = getComponentOrientation().isLeftToRight();
		if (key.equals(HORIZONTAL_LEADING)) {
			key = isLeftToRight ? HORIZONTAL_LEFT : HORIZONTAL_RIGHT;
		} else if (key.equals(HORIZONTAL_TRAILING)) {
			key = isLeftToRight ? HORIZONTAL_RIGHT : HORIZONTAL_LEFT;
		}

		switch (key) {
			case HORIZONTAL_LEFT -> {
				old = _hLeft;
				_hLeft = corner;
			}
			case HORIZONTAL_RIGHT -> {
				old = _hRight;
				_hRight = corner;
			}
			case VERTICAL_TOP -> {
				old = _vTop;
				_vTop = corner;
			}
			case VERTICAL_BOTTOM -> {
				old = _vBottom;
				_vBottom = corner;
			}
			default -> throw new IllegalArgumentException("invalid scroll bar corner key");
		}

		if (null != old) {
			remove(old);
		}
		if (null != corner) {
			add(corner, key);
		}
		if (corner != null) corner.setComponentOrientation(getComponentOrientation());
		firePropertyChange(key, old, corner);
		revalidate();
		repaint();
	}

	@Override
	public void updateUI() {
		super.updateUI();
		setLayout(new JideScrollPaneLayout.UIResource());
		if (getBorder() instanceof UIResource) {
			LookAndFeel.installBorder(this, "JideScrollPane.border");
		}
	}

	@Override
	public void setLayout(LayoutManager layout) {
		if (!(layout instanceof JideScrollPaneLayout)) {
			super.setLayout(new JideScrollPaneLayout.UIResource());
		} else {
			super.setLayout(layout);
		}
	}

	public boolean isVerticalScrollBarCoversWholeHeight() {
		return _verticalScrollBarCoversWholeHeight;
	}

	public void setHorizontalScrollBarCoversWholeWidth(boolean horizontalScrollBarCoversWholeWidth) {
		boolean old = _horizontalScrollBarCoversWholeWidth;
		if (old != horizontalScrollBarCoversWholeWidth) {
			_horizontalScrollBarCoversWholeWidth = horizontalScrollBarCoversWholeWidth;
			firePropertyChange(PROPERTY_HORIZONTAL_SCROLL_BAR_COVERS_WHOLE_WIDTH, old, _horizontalScrollBarCoversWholeWidth);
			invalidate();
			doLayout();
			if (getHorizontalScrollBar() != null) {
				getHorizontalScrollBar().doLayout();
			}
		}
	}

	public boolean isHorizontalScrollBarCoversWholeWidth() {
		return _horizontalScrollBarCoversWholeWidth;
	}

	public void setVerticalScrollBarCoversWholeHeight(boolean verticalScrollBarCoversWholeHeight) {
		boolean old = _verticalScrollBarCoversWholeHeight;
		if (old != verticalScrollBarCoversWholeHeight) {
			_verticalScrollBarCoversWholeHeight = verticalScrollBarCoversWholeHeight;
			firePropertyChange(PROPERTY_VERTICAL_SCROLL_BAR_COVERS_WHOLE_HEIGHT, old, _verticalScrollBarCoversWholeHeight);
			invalidate();
			doLayout();
			if (getVerticalScrollBar() != null) {
				getVerticalScrollBar().doLayout();
			}
		}
	}

	/**
	 * If true, the top-right, top-left corners the column header will have the same height. If false, three of them
	 * will keep their own preferred height.
	 *
	 * @return true or false.
	 */
	public boolean isColumnHeadersHeightUnified() {
		return _columnHeadersHeightUnified;
	}

	/**
	 * Sets the flag if the top-right, top-left corner and the column header will have the same height or different
	 * heights.
	 *
	 * @param columnHeadersHeightUnified true or false.
	 */
	public void setColumnHeadersHeightUnified(boolean columnHeadersHeightUnified) {
		boolean old = _columnHeadersHeightUnified;
		if (old != columnHeadersHeightUnified) {
			_columnHeadersHeightUnified = columnHeadersHeightUnified;
			firePropertyChange(PROPERTY_COLUMN_HEADERS_HEIGHT_UNIFIED, old, _horizontalScrollBarCoversWholeWidth);
			invalidate();
			doLayout();
		}
	}

	/**
	 * If true, the bottom-right, bottom-left corners the column footer will have the same height. If false, three of
	 * them will keep their own preferred height.
	 *
	 * @return true or false.
	 */
	public boolean isColumnFootersHeightUnified() {
		return _columnFootersHeightUnified;
	}

	/**
	 * Sets the flag if the bottom-right, bottom-left corner and the column footer will have the same height or
	 * different heights.
	 *
	 * @param columnFootersHeightUnified true or false.
	 */
	public void setColumnFootersHeightUnified(boolean columnFootersHeightUnified) {
		boolean old = _columnFootersHeightUnified;
		if (old != columnFootersHeightUnified) {
			_columnFootersHeightUnified = columnFootersHeightUnified;
			firePropertyChange(PROPERTY_COLUMN_FOOTERS_HEIGHT_UNIFIED, old, _horizontalScrollBarCoversWholeWidth);
			invalidate();
			doLayout();
		}
	}

	/**
	 * Get the flag indicating if JideScrollPane should keep the corner visible when it has corner components defined
	 * even when the scroll bar is not visible.
	 * <p/>
	 * This flag will take effect only when the scroll bar policy is <code>HORIZONTAL_SCROLLBAR_AS_NEEDED</code> or
	 * <code>VERTICAL_SCROLLBAR_AS_NEEDED</code>
	 * <p/>
	 * The default value of this flag is false.
	 *
	 * @return the flag.
	 */
	public boolean isKeepCornerVisible() {
		return _keepCornerVisible;
	}

	/**
	 * Set the flag indicating if JideScrollPane should keep the corner visible when it has corner components defined
	 * even when the scroll bar is not visible.
	 *
	 * @param keepCornerVisible the flag
	 */
	public void setKeepCornerVisible(boolean keepCornerVisible) {
		if (_keepCornerVisible != keepCornerVisible) {
			boolean old = _keepCornerVisible;
			_keepCornerVisible = keepCornerVisible;
			firePropertyChange(PROPERTY_KEEP_CORNER_VISIBLE, old, _keepCornerVisible);
			invalidate();
			doLayout();
		}
	}

	/**
	 * Gets the flag indicating if the JideScrollPane will layout its view flat without scroll bars.
	 *
	 * @return true if flat layout. Otherwise false.
	 * @see #setFlatLayout(boolean)
	 * @since 3.3.3
	 */
	public boolean isFlatLayout() {
		return _flatLayout;
	}

	/**
	 * Sets the flag indicating if the JideScrollPane will layout its view flat without scroll bars.
	 * <p/>
	 * By default, the value is false to keep normal behavior.
	 *
	 * @param flatLayout the flag
	 * @since 3.3.3
	 */
	public void setFlatLayout(boolean flatLayout) {
		if (_flatLayout != flatLayout) {
			boolean old = _flatLayout;
			_flatLayout = flatLayout;
			firePropertyChange(PROPERTY_FLAT_LAYOUT, old, _flatLayout);
			invalidate();
			doLayout();
		}
	}
}
