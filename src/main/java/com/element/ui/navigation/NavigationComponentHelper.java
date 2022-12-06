/*
 * @(#)NavigationHelper.java 11/5/2011
 *
 * Copyright 2002 - 2011 JIDE Software Inc. All rights reserved.
 */

package com.element.ui.navigation;

import com.element.color.ColorUtil;
import com.element.util.UIUtil;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;

/**
 * NavigationComponentHelper是在 JTree、JList 和 JTable（或 JIDE 表子类）上实现的辅助类，因此它们可用于导航目的。为了使组件适合导航，
 * 我们希望选择效果很容易被注意到并覆盖行（而不是分别在 JTable 和 JTree 的情况下仅一个单元格或一个节点）。我们还希望鼠标悬停在一行上时具有翻
 * 转效果。此外，当组件获得焦点时，选择应该有不同的颜色，以便在使用多个导航组件时，我们可以分辨出哪个是活动的。一些 L&F 已经默认执行此操作，但
 * 大多数 L&F 不执行此操作。该类提供了一些通用代码，使实现变得容易。
 * <p>
 * 要使用这个类，你需要实现{@link #getRowBounds(int)}、{@link #rowAtPoint(Point)}和{@link #getSelectedRows()}三个方法，这三个方
 * 法是用来获取需要高亮的单元格(行)区域，此外你还需要重写组件的paint()，调用{@link #paint(Graphics)}进行绘制高亮块的绘制
 */
abstract public class NavigationComponentHelper extends MouseInputAdapter implements FocusListener {
	private final JComponent c;
	private int _rolloverRow = -1;
	private Point _mousePosition = null;

	public NavigationComponentHelper(JComponent c) {
		if (c == null)
			throw new IllegalArgumentException("c cannot is null");
		this.c = c;
		c.addMouseMotionListener(this);
		c.addMouseListener(this);
		c.addFocusListener(this);
	}

	/**
	 * Gets the bounds of the row.
	 *
	 * @param row the bounds of the specific row.
	 * @return the bounds of the row. Or null if there is no row at all or the specified row doesn't exist.
	 */
	protected abstract Rectangle getRowBounds(int row);

	protected abstract int rowAtPoint(Point p);

	protected abstract int[] getSelectedRows();

	@Override
	public void mouseMoved(MouseEvent e) {
		int row = rowAtPoint(e.getPoint());
		if (row != -1) {
			Rectangle bounds = getRowBounds(row);
			System.out.println(bounds);
			if (c instanceof JTree) {
				int maxIconSize = bounds != null ? bounds.height : ((JTree) c).getRowHeight();
				if (_mousePosition != null) {
					c.repaint(new Rectangle(_mousePosition.x - maxIconSize, _mousePosition.y - maxIconSize, 2 * maxIconSize, 2 * maxIconSize));
				}
				_mousePosition = e.getPoint();
				c.repaint(new Rectangle(_mousePosition.x - maxIconSize, _mousePosition.y - maxIconSize, 2 * maxIconSize, 2 * maxIconSize));
			}
			if (_rolloverRow != row) {
				int old = _rolloverRow;
				_rolloverRow = row;
				if (old != -1) {
					Rectangle oldBounds = getRowBounds(old);
					if (oldBounds != null) {
						c.repaint(oldBounds);
					}
				}
				if (bounds != null) {
					c.repaint(bounds);
				}
			}
		} else {
			int old = _rolloverRow;
			_rolloverRow = -1;
			if (old != -1) {
				Rectangle bounds = getRowBounds(old);
				if (bounds != null) {
					c.repaint(bounds);
				}
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		_mousePosition = null;
		int old = _rolloverRow;
		_rolloverRow = -1;
		if (old != -1) {
			Rectangle bounds = getRowBounds(old);
			if (bounds != null) {
				c.repaint(bounds);
			}
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		repaintSelections();
	}

	@Override
	public void focusLost(FocusEvent e) {
		repaintSelections();
	}

	/**
	 * Paints the rollover row and selection rows.
	 *
	 * @param g the Graphics
	 */
	public void paint(Graphics g) {
		if (_rolloverRow != -1) {
			paintRolloverRow(g, _rolloverRow);
		}
		int[] rows = getSelectedRows();
		if (rows != null) {
			for (int row : rows) {
				paintSelectedRow(g, row);
			}
		}
	}

	/**
	 * Paints the selected row. This method is called after the tree is painted. It will paint over the content of the
	 * tree. In order to not cover the content, the painting code must be semi-transparent. By default, we paint it
	 * using the selection color which can be retrieved from UIDefault "Tree.selectionBackground"  but with an alpha
	 * between 70 to 100 to create a gradient effect.
	 *
	 * @param g   the Graphics
	 * @param row the row index
	 */
	protected void paintSelectedRow(Graphics g, int row) {
		Color selectedColor = getSelectionColor();
		Rectangle bounds = getRowBounds(row);
		if (bounds != null) {
			bounds.width -= 1;
			bounds.height -= 1;
			paintRow(g, row, bounds, selectedColor, 30, 70, 50, 128);
		}
	}

	/**
	 * Gets the color to paint the selected rows.
	 *
	 * @return the selection color.
	 * @since 3.4.6
	 */
	protected Color getSelectionColor() {
		Color selectedColor = UIManager.getColor("NavigationComponent.selectionBackground");
		if (selectedColor == null) {
			selectedColor = UIManager.getColor("Tree.selectionBackground");
		}
		if (!c.hasFocus()) {
			selectedColor = ColorUtil.toGrayscale(selectedColor).brighter();
			if (Color.WHITE.equals(selectedColor)) {
				selectedColor = new Color(202, 202, 202);
			}
		}
		return selectedColor;
	}

	/**
	 * Paints the rollover row. This method is called after the tree is painted. It will paint over the content of the
	 * tree. In order to not cover the content, the painting code must be semi-transparent. By default, we paint it
	 * using the selection color which can be retrieved from UIDefault "Tree.selectionBackground"  but with an alpha
	 * between 10 to 40 to create a gradient effect.
	 *
	 * @param g   the Graphics
	 * @param row the row index
	 */
	@SuppressWarnings({"UnusedParameters"})
	protected void paintRolloverRow(Graphics g, int row) {
		Color selectedColor = UIManager.getColor("Tree.selectionBackground");
		Rectangle bounds = getRowBounds(row);
		if (bounds != null) {
			bounds.width -= 1;
			bounds.height -= 1;
			paintRow(g, row, bounds, selectedColor, 10, 40, 20, 100);
		}
	}

	@SuppressWarnings({"UnusedParameters"})
	private void paintRow(Graphics g, int row, Rectangle bounds, Color color, int a1, int a2, int a3, int a4) {
		Object o = UIUtil.setupShapeAntialiasing(g);
		((Graphics2D) g).setPaint(new LinearGradientPaint(bounds.x, bounds.y, bounds.x, bounds.y + bounds.height, new float[]{0.5f, 0.95f, 1f}, new Color[]{
				new Color(color.getRed(), color.getGreen(), color.getBlue(), a1),
				new Color(color.getRed(), color.getGreen(), color.getBlue(), a2),
				new Color(color.getRed(), color.getGreen(), color.getBlue(), a3)
		}, MultipleGradientPaint.CycleMethod.NO_CYCLE));
		int cornerSize = 5;
		g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, cornerSize, cornerSize);
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), a4));
		g.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, cornerSize, cornerSize);
		UIUtil.restoreShapeAntialiasing(g, o);
	}

	public void repaintSelections() {
		int[] rows = getSelectedRows();
		if (rows != null) {
			for (int row : rows) {
				Rectangle bounds = getRowBounds(row);
				if (bounds != null) {
					bounds.x = 0;
					bounds.width = c.getWidth();
					c.repaint(bounds);
				}
			}
		}
		if (_rolloverRow != -1) {
			Rectangle bounds = getRowBounds(_rolloverRow);
			if (bounds != null) {
				c.repaint(bounds);
			}
		}
	}

	/**
	 * Gets the rollover row that currently has rollover effect.
	 *
	 * @return the row that has the rollover effect.
	 */
	public int getRolloverRow() {
		return _rolloverRow;
	}

	/**
	 * Sets the rollover row.
	 *
	 * @param rolloverRow the row to show the rollover effect.
	 */
	public void setRolloverRow(int rolloverRow) {
		_rolloverRow = rolloverRow;
	}

	public Point getMousePosition() {
		return _mousePosition;
	}
}
