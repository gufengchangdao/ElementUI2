/*
 * @(#)CheckBoxTreeCellRenderer.java 8/11/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

package com.jidesoft.swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * <strong>This class is deprecated and provided to support compatibility with {@link LegacyTristateCheckBox}. We will not provide support
 * for this class. You likely want to use {@link CheckBoxTreeCellRenderer}.</strong>
 * <p/>
 * Renderers an item in a tree using JCheckBox.
 *
 * @deprecated Use {@link CheckBoxTreeCellRenderer}.
 */
public class LegacyCheckBoxTreeCellRenderer extends JPanel implements TreeCellRenderer, Serializable {
	/**
	 * The checkbox that is used to paint the check box in cell renderer
	 */
	protected LegacyTristateCheckBox _checkBox = null;
	protected JComponent _emptyBox = null;
	protected JCheckBox _protoType;

	/**
	 * The label which appears after the check box.
	 */
	protected TreeCellRenderer _actualTreeRenderer;

	/**
	 * Constructs a default renderer object for an item in a list.
	 */
	public LegacyCheckBoxTreeCellRenderer() {
		this(null);
	}

	public LegacyCheckBoxTreeCellRenderer(TreeCellRenderer renderer) {
		this(renderer, null);
	}

	public LegacyCheckBoxTreeCellRenderer(TreeCellRenderer renderer, LegacyTristateCheckBox checkBox) {
		_protoType = new LegacyTristateCheckBox();
		if (checkBox == null) {
			_checkBox = createCheckBox();
		} else {
			_checkBox = checkBox;
		}
		_emptyBox = (JComponent) Box.createHorizontalStrut(_protoType.getPreferredSize().width);
		setLayout(new BorderLayout(0, 0));
		setOpaque(false);
		_actualTreeRenderer = renderer;
	}

	/**
	 * Create the check box in the cell.
	 * <p>
	 * By default, it creates a LegacyTristateCheckBox and set opaque to false.
	 *
	 * @return the check box instance.
	 */
	protected LegacyTristateCheckBox createCheckBox() {
		LegacyTristateCheckBox checkBox = new LegacyTristateCheckBox();
		checkBox.setOpaque(false);
		return checkBox;
	}

	public TreeCellRenderer getActualTreeRenderer() {
		return _actualTreeRenderer;
	}

	public void setActualTreeRenderer(TreeCellRenderer actualTreeRenderer) {
		_actualTreeRenderer = actualTreeRenderer;
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		removeAll();
		_checkBox.setPreferredSize(new Dimension(_protoType.getPreferredSize().width, 0));
		_emptyBox.setPreferredSize(new Dimension(_protoType.getPreferredSize().width, 0));
		applyComponentOrientation(tree.getComponentOrientation());

		TreePath path = tree.getPathForRow(row);
		if (path != null && tree instanceof LegacyCheckBoxTree) {
			LegacyCheckBoxTreeSelectionModel selectionModel = ((LegacyCheckBoxTree) tree).getCheckBoxTreeSelectionModel();
			if (selectionModel != null) {
				boolean enabled = tree.isEnabled() && ((LegacyCheckBoxTree) tree).isCheckBoxEnabled() && ((LegacyCheckBoxTree) tree).isCheckBoxEnabled(path);
				if (!enabled && !selected) {
					if (getBackground() != null) {
						setForeground(getBackground().darker());
					}
				}
				_checkBox.setEnabled(enabled);
				updateCheckBoxState(_checkBox, path, selectionModel);
			}
		}

		if (_actualTreeRenderer != null) {
			JComponent treeCellRendererComponent = (JComponent) _actualTreeRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			Border border = treeCellRendererComponent.getBorder();
			setBorder(border);
			treeCellRendererComponent.setBorder(BorderFactory.createEmptyBorder());
			if (path == null || !(tree instanceof LegacyCheckBoxTree) || ((LegacyCheckBoxTree) tree).isCheckBoxVisible(path)) {
				remove(_emptyBox);
				add(_checkBox, BorderLayout.BEFORE_LINE_BEGINS);
			} else {
				remove(_checkBox);
				add(_emptyBox, BorderLayout.AFTER_LINE_ENDS); // expand the tree node size to be the same as the one with check box.
			}
			add(treeCellRendererComponent);
		}

		return this;
	}

	/**
	 * Updates the check box state based on the selection in the selection model. By default, we check if the path is
	 * selected. If yes, we mark the check box as TristateCheckBox.SELECTED. If not, we will check if the path is
	 * partially selected, if yes, we set the check box as null or TristateCheckBox.DONT_CARE to indicate the path is
	 * partially selected. Otherwise, we set it to TristateCheckBox.NOT_SELECTED.
	 *
	 * @param checkBox       the TristateCheckBox for the particular tree path.
	 * @param path           the tree path.
	 * @param selectionModel the CheckBoxTreeSelectionModel.
	 */
	protected void updateCheckBoxState(LegacyTristateCheckBox checkBox, TreePath path, LegacyCheckBoxTreeSelectionModel selectionModel) {
		if (selectionModel.isPathSelected(path, selectionModel.isDigIn()))
			checkBox.setState(LegacyTristateCheckBox.SELECTED);
		else
			checkBox.setState(selectionModel.isDigIn() && selectionModel.isPartiallySelected(path) ? null : LegacyTristateCheckBox.NOT_SELECTED);
	}

	@Override
	public String getToolTipText(MouseEvent event) {
		if (_actualTreeRenderer instanceof JComponent) {
			Point p = event.getPoint();
			p.translate(-_checkBox.getWidth(), 0);
			MouseEvent newEvent = new MouseEvent(((JComponent) _actualTreeRenderer), event.getID(),
					event.getWhen(),
					event.getModifiers(),
					p.x, p.y, event.getClickCount(),
					event.isPopupTrigger());

			String tip = ((JComponent) _actualTreeRenderer).getToolTipText(
					newEvent);

			if (tip != null) {
				return tip;
			}
		}
		return super.getToolTipText(event);
	}

}
