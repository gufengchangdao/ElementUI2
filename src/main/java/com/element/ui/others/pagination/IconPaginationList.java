package com.element.ui.others.pagination;

import com.element.swing.base.IconComponent;
import com.element.ui.others.pagination.model.PageListModel;
import com.element.ui.others.pagination.renderer.PageListCellRenderer;
import com.element.ui.svg.icon.regular.CaretLeftSvg;
import com.element.ui.svg.icon.regular.CaretRightSvg;

/**
 * 在分页组件基础上添加两侧图标
 *
 * @see PaginationList
 */
public class IconPaginationList extends IconComponent<PaginationList> {
	private PaginationList list;

	public IconPaginationList(int count) {
		this.list = new PaginationList(count);
		setComponent(list);
		init();
	}

	@Override
	protected void init() throws RuntimeException {
		super.init();

		setLeftIcon(CaretLeftSvg.of(14, 14));
		setRightIcon(CaretRightSvg.of(14, 14));
		getLeftButton().addActionListener(e -> {
			int i = getModel().lastPage();
			getCellRenderer().setSelectedIndex(i);
			list.setSelectedIndex(i);
			list.updateUI();
		});
		getRightButton().addActionListener(e -> {
			int i = getModel().nextPage();
			getCellRenderer().setSelectedIndex(i);
			list.setSelectedIndex(i);
			list.updateUI();
		});
	}

	public PaginationList getList() {
		return list;
	}

	public void setList(PaginationList list) {
		this.list = list;
	}

	/**
	 * @see PaginationList#getModel()
	 */
	public PageListModel getModel() {
		return list.getModel();
	}

	/**
	 * @see PaginationList#setModel(PageListModel)
	 */
	public void setModel(PageListModel model) {
		list.setModel(model);
	}

	/**
	 * @see PaginationList#getCellRenderer()
	 */
	public PageListCellRenderer getCellRenderer() {
		return list.getCellRenderer();
	}

	/**
	 * @see PaginationList#setCellRenderer(PageListCellRenderer)
	 */
	public void setCellRenderer(PageListCellRenderer cellRenderer) {
		list.setCellRenderer(cellRenderer);
	}
}
