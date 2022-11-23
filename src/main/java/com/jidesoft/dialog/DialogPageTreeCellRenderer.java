/*
 * @(#)DialogPageTreeCellRenderer.java
 *
 * Copyright 2002 - 2003 JIDE Software Inc. All rights reserved.
 */
package com.jidesoft.dialog;

import com.jidesoft.plaf.UIDefaultsLookup;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

/**
 * A tree cell renderer for AbstractDialogPage.
 */
public class DialogPageTreeCellRenderer extends JLabel implements TreeCellRenderer {
	/**
	 * text selection color
	 */
	private Color _selectedForeground;
	private Color _nonSelectedForeground;
	private Color _selectedBackground;
	private Color _nonSelectedBackground;
	private Color _selectedBorderColor;
	private Icon _selectedIcon;
	private Icon _blankIcon;
	private Icon _openIcon;
	private Icon _closedIcon;

	private Color _defaultTextSelectionColor;
	private Color _defaultTextNonSelectionColor;
	private Color _defaultBkSelectionColor;
	private Color _defaultBkNonSelectionColor;
	private Color _defaultBorderSelectionColor;
	private Icon _defaultOpenIcon;
	private Icon _defaultClosedIcon;

	private boolean m_selected;

	private static final Icon SELECTED = TreeIconsFactory.getImageIcon(TreeIconsFactory.CellRenderer.SELECTED_B16);
	private static final Icon BLANK = TreeIconsFactory.getImageIcon(TreeIconsFactory.CellRenderer.BLANK_16);

	/**
	 * The constructor.
	 */
	public DialogPageTreeCellRenderer() {
		super();
		_defaultTextSelectionColor = UIDefaultsLookup.getColor("Tree.selectionForeground");
		_defaultTextNonSelectionColor = UIDefaultsLookup.getColor("Tree.textForeground");
		_defaultBkSelectionColor = UIDefaultsLookup.getColor("Tree.selectionBackground");
		_defaultBkNonSelectionColor = UIDefaultsLookup.getColor("Tree.textBackground");
		_defaultBorderSelectionColor = UIDefaultsLookup.getColor("Tree.selectionBorderColor");
		_defaultOpenIcon = UIDefaultsLookup.getIcon("Tree.openIcon");
		_defaultClosedIcon = UIDefaultsLookup.getIcon("Tree.closedIcon");
		setOpaque(false);
	}

	public Component getTreeCellRendererComponent(JTree tree,
	                                              Object value, boolean sel, boolean expanded, boolean leaf,
	                                              int row, boolean hasFocus) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();

		if (obj instanceof Boolean)
			setText("Retrieving data...");

		if (obj instanceof AbstractDialogPage) {
			AbstractDialogPage idata = (AbstractDialogPage) obj;
			setText(idata.getTitle());
		} else {
			setText(obj.toString());
			setIcon(null);
		}

		setFont(tree.getFont());

		boolean treeIsEnabled = tree.isEnabled();
		boolean nodeIsEnabled = !(value instanceof MutableTreeNodeEx) || ((MutableTreeNodeEx) value).isEnabled();
		boolean isEnabled = (treeIsEnabled && nodeIsEnabled);
		setEnabled(isEnabled);
		if (!isEnabled) {
			sel = false;
		}
		setForeground(sel ? getSelectedForeground() : getNonSelectedForeground());
		setBackground(sel ? getSelectedBackground() : getNonSelectedBackground());

		if (leaf) {
			if (sel) {
				setIcon(SELECTED);
			} else {
				setIcon(BLANK);
			}
		} else {
			if (expanded) {
				setIcon(getOpenIcon());
			} else {
				setIcon(getClosedIcon());
			}
		}
		m_selected = sel;
		setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));
		return this;
	}

	@Override
	public void paintComponent(Graphics g) {
		Color bColor = getBackground();
		Icon icon = getIcon();

		g.setColor(bColor);
		int offset = 0;
		if (icon != null && getText() != null)
			offset = (icon.getIconWidth() + getIconTextGap()) - 1;
		g.fillRect(offset, 0, getWidth() - 1 - offset,
				getHeight() - 1);

		if (m_selected) {
			g.setColor(getSelectedBorderColor());
			g.drawRect(offset, 0, getWidth() - offset - 1, getHeight() - 1);
		}
		super.paintComponent(g);
	}

	/**
	 * Get the foreground color on selection. By default, it gets color from UIDefault, "Tree.selectionForeground".
	 *
	 * @return the foreground color on selection.
	 */
	public Color getSelectedForeground() {
		if (_selectedForeground == null) {
			if (_defaultTextSelectionColor == null) {
				_defaultTextSelectionColor = UIDefaultsLookup.getColor("Tree.selectionForeground");
			}
			return _defaultTextSelectionColor;
		}
		return _selectedForeground;
	}

	/**
	 * Set the foreground color on selection.
	 *
	 * @param selectedForeground the foreground color on selection
	 * @see #getSelectedForeground()
	 */
	public void setSelectedForeground(Color selectedForeground) {
		_selectedForeground = selectedForeground;
	}

	/**
	 * Get the foreground color without selection. By default, it gets color from UIDefault, "Tree.textForeground".
	 *
	 * @return the foreground color without selection.
	 */
	public Color getNonSelectedForeground() {
		if (_nonSelectedForeground == null) {
			if (_defaultTextNonSelectionColor == null) {
				_defaultTextNonSelectionColor = UIDefaultsLookup.getColor("Tree.textForeground");
			}
			return _defaultTextNonSelectionColor;
		}
		return _nonSelectedForeground;
	}

	/**
	 * Set the foreground color without selection.
	 *
	 * @param nonSelectedForeground the foreground color without selection
	 * @see #getNonSelectedForeground()
	 */
	public void setNonSelectedForeground(Color nonSelectedForeground) {
		_nonSelectedForeground = nonSelectedForeground;
	}

	/**
	 * Get the background color on selection. By default, it gets color from UIDefault, "Tree.selectionBackground".
	 *
	 * @return the background color on selection.
	 */
	public Color getSelectedBackground() {
		if (_selectedBackground == null) {
			if (_defaultBkSelectionColor == null) {
				_defaultBkSelectionColor = UIDefaultsLookup.getColor("Tree.selectionBackground");
			}
			return _defaultBkSelectionColor;
		}
		return _selectedBackground;
	}

	/**
	 * Set the background color on selection.
	 *
	 * @param selectedBackground the background color on selection
	 * @see #getSelectedBackground()
	 */
	public void setSelectedBackground(Color selectedBackground) {
		_selectedBackground = selectedBackground;
	}

	/**
	 * Get the background color without selection. By default, it gets color from UIDefault, "Tree.textBackground".
	 *
	 * @return the background color without selection.
	 */
	public Color getNonSelectedBackground() {
		if (_nonSelectedBackground == null) {
			if (_defaultBkNonSelectionColor == null) {
				_defaultBkNonSelectionColor = UIDefaultsLookup.getColor("Tree.textBackground");
			}
			return _defaultBkNonSelectionColor;
		}
		return _nonSelectedBackground;
	}

	/**
	 * Set the background color without selection.
	 *
	 * @param nonSelectedBackground the background color without selection
	 * @see #getNonSelectedBackground()
	 */
	public void setNonSelectedBackground(Color nonSelectedBackground) {
		_nonSelectedBackground = nonSelectedBackground;
	}

	/**
	 * Get the border color on selection. By default, it gets color from UIDefault, "Tree.selectionBorderColor".
	 *
	 * @return the border color on selection.
	 */
	public Color getSelectedBorderColor() {
		if (_selectedBorderColor == null) {
			if (_defaultBorderSelectionColor == null) {
				_defaultBorderSelectionColor = UIDefaultsLookup.getColor("Tree.selectionBorderColor");
			}
			return _defaultBorderSelectionColor;
		}
		return _selectedBorderColor;
	}

	/**
	 * Set the border color on selection.
	 *
	 * @param selectedBorderColor the border color on selection
	 * @see #getSelectedBorderColor()
	 */
	public void setSelectedBorderColor(Color selectedBorderColor) {
		_selectedBorderColor = selectedBorderColor;
	}

	/**
	 * Get the selected icon. If it is not configured, JIDE will create a default icon.
	 *
	 * @return the selected icon.
	 */
	public Icon getSelectedIcon() {
		if (_selectedIcon == null) {
			return SELECTED;
		}
		return _selectedIcon;
	}

	/**
	 * Set the selected icon.
	 *
	 * @param selectedIcon the selected icon
	 * @see #getSelectedIcon()
	 */
	public void setSelectedIcon(Icon selectedIcon) {
		_selectedIcon = selectedIcon;
	}

	/**
	 * Get the blank icon. If it is not configured, JIDE will create a default icon.
	 *
	 * @return the blank icon.
	 */
	public Icon getBlankIcon() {
		if (_blankIcon == null) {
			return BLANK;
		}
		return _blankIcon;
	}

	/**
	 * Set the blank icon.
	 *
	 * @param blankIcon the blank icon
	 * @see #getBlankIcon()
	 */
	public void setBlankIcon(Icon blankIcon) {
		_blankIcon = blankIcon;
	}

	/**
	 * Get the open/expand icon. By default, it gets icon from UIDefault, "Tree.openIcon".
	 *
	 * @return the open/expand icon.
	 */
	public Icon getOpenIcon() {
		if (_openIcon == null) {
			if (_defaultOpenIcon == null) {
				_defaultOpenIcon = UIDefaultsLookup.getIcon("Tree.openIcon");
			}
			return _defaultOpenIcon;
		}
		return _openIcon;
	}

	/**
	 * Set the open/expand icon.
	 *
	 * @param openIcon the open/expand icon
	 * @see #getOpenIcon()
	 */
	public void setOpenIcon(Icon openIcon) {
		_openIcon = openIcon;
	}

	/**
	 * Get the closed icon. By default, it gets icon from UIDefault, "Tree.closedIcon".
	 *
	 * @return the closed icon.
	 */
	public Icon getClosedIcon() {
		if (_closedIcon == null) {
			if (_defaultClosedIcon == null) {
				_defaultClosedIcon = UIDefaultsLookup.getIcon("Tree.closedIcon");
			}
			return _defaultClosedIcon;
		}
		return _closedIcon;
	}

	/**
	 * Set the closed icon.
	 *
	 * @param closedIcon the closed icon
	 * @see #getClosedIcon()
	 */
	public void setClosedIcon(Icon closedIcon) {
		_closedIcon = closedIcon;
	}
}
