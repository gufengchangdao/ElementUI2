package com.element.ui.others.pagination;

import com.element.ui.others.pagination.model.PageListModel;
import com.element.ui.others.pagination.renderer.PageListCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeListener;

/**
 * 分页列表
 * <p>
 * 支持的功能有
 * <ul>
 *     <li>根据数据量自动分页</li>
 *     <li>渲染器设置选中、悬停单元格的背景色、字体色、是否绘制背景等</li>
 *     <li>模型设置数据总量、触发折叠的阈值、每页数据数量等</li>
 * </ul>
 * <p>
 * 注意：
 */
public class PaginationList extends JList<Integer>
		implements MouseListener, MouseMotionListener {
	private PageListCellRenderer cellRenderer;
	private PageListModel model;

	public PaginationList(int count) {
		super(new PageListModel(count));
		init();
	}

	public PaginationList(PageListModel model) {
		super(model);
		this.model = model;
		init();
	}

	private void init() {
		// 拿到传入的model
		model = (PageListModel) super.getModel();
		// 水平排布
		setVisibleRowCount(1);
		setLayoutOrientation(HORIZONTAL_WRAP);

		setSelectedIndex(0);
		// 本来使用该监听器监听点击事件，结果修改model后调用updateUI第一次会抛空指针异常
		// 虽然后续不会再报错但仍没有解决空指针异常的问题，改用MouseMotionListener
		// addListSelectionListener(this);

		// 设置单元格渲染
		cellRenderer = new PageListCellRenderer();
		setCellRenderer(cellRenderer);

		addMouseMotionListener(this);
		addMouseListener(this);
		updateUI();
	}

	public static final String SELECTED_CHANGE_PROPERTY_NAME = "selectedChange";

	@Override
	public void mouseClicked(MouseEvent e) {
		int selectedIndex = model.setSelectedIndex(getSelectedIndex());
		setPage(selectedIndex + 1);
	}

	/**
	 * 设置新page，起始值为 1
	 */
	public void setPage(int newPage) {
		int selectedIndex = newPage - 1;
		int oldSelectedIndex = cellRenderer.getSelectedIndex();
		if (getSelectedIndex() != -1 && oldSelectedIndex != selectedIndex) {
			firePropertyChange(SELECTED_CHANGE_PROPERTY_NAME, oldSelectedIndex, selectedIndex);
			cellRenderer.setSelectedIndex(selectedIndex);
			setSelectedIndex(selectedIndex);
			updateUI();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		cellRenderer.setEnter(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		cellRenderer.setEnter(false);
		repaint(); //清除遗留的样式
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// 通过鼠标坐标找到单元格索引
		int w = getCellBounds(0, 0).width;
		cellRenderer.setHoverIndex(e.getX() / w);
		repaint();
	}

	/**
	 * 每次点击都会重置模型的数据，会多次调用监听器方法，不建议使用，建议使用{@link #addListSelectionPropertyChangeListener(PropertyChangeListener)}
	 */
	@Override
	public void addListSelectionListener(ListSelectionListener listener) {
		super.addListSelectionListener(listener);
	}

	public void addListSelectionPropertyChangeListener(PropertyChangeListener listener) {
		addPropertyChangeListener(SELECTED_CHANGE_PROPERTY_NAME, listener);
	}

	@Override
	public PageListModel getModel() {
		return model;
	}

	public void setModel(PageListModel model) {
		this.model = model;
	}

	@Override
	public PageListCellRenderer getCellRenderer() {
		return cellRenderer;
	}

	public void setCellRenderer(PageListCellRenderer cellRenderer) {
		this.cellRenderer = cellRenderer;
	}
}
