package com.element.ui.table.groupable;

import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author shane
 */
public class GroupableTableHeader extends JTableHeader {
	private GroupableColumnModel.IColumnGroup draggedGroup;

	public GroupableTableHeader(TableColumnModel model) {
		super(model);
		super.setUI(new GroupableTableHeaderUI());
		setReorderingAllowed(true);
	}

	@Override
	public void setUI(TableHeaderUI ui) {
	}

	public void setDraggedGroup(GroupableColumnModel.IColumnGroup columnGroup) {
		draggedGroup = columnGroup;
	}
	public GroupableColumnModel.IColumnGroup getDraggedGroup() {
		return draggedGroup;
	}
}
