package com.element.ui.button;

import com.element.radiance.common.api.icon.SvgIcon;
import com.element.swing.base.BaseComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 带有三种状态的图标按钮，两个图标分别对应鼠标移出移进的状态
 */
public class IconButton extends BaseComponent implements MouseListener {
	/** 鼠标未悬停状态 */
	private SvgIcon beginIcon;
	/** 鼠标悬停状态 */
	private SvgIcon endIcon;
	private SvgIcon currentIcon;
	/** 鼠标悬停状态的过滤器，保存下来使鼠标松开时能回归原样，因为 endIcon 有可能有设置初始过滤器(组件色) */
	private SvgIcon.ColorFilter endIconFilter;
	/** 鼠标按下状态 */
	private SvgIcon.ColorFilter filter;
	private ActionListener l;

	public IconButton(SvgIcon beginIcon, SvgIcon.ColorFilter filter) {
		this.beginIcon = beginIcon;
		this.endIcon = beginIcon;
		endIconFilter = endIcon.getColorFilter();
		this.filter = filter;
		currentIcon = beginIcon;
		init();
	}

	public IconButton(SvgIcon beginIcon, SvgIcon endIcon) {
		this.beginIcon = beginIcon;
		this.endIcon = endIcon;
		currentIcon = beginIcon;
		init();
	}

	/**
	 * @param beginIcon 默认图标
	 * @param endIcon   鼠标悬停时图标
	 * @param filter    鼠标按下时图标颜色变化
	 */
	public IconButton(SvgIcon beginIcon, SvgIcon endIcon, SvgIcon.ColorFilter filter) {
		this.beginIcon = beginIcon;
		this.endIcon = endIcon;
		endIconFilter = endIcon.getColorFilter();
		this.filter = filter;
		currentIcon = beginIcon;
		init();
	}

	private void init() {
		Dimension size = new Dimension();
		size.width = Math.max(beginIcon.getIconWidth(), endIcon.getIconWidth());
		size.height = Math.max(beginIcon.getIconHeight(), endIcon.getIconHeight());
		super.setPreferredSize(size);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		addMouseListener(this);
	}

	public void addActionListener(ActionListener l) {
		this.l = l;
	}

	public void doClick() {
		Dimension size = getSize();
		mouseEntered(null);
		mousePressed(null);
		paintImmediately(new Rectangle(0, 0, size.width, size.height));
		try {
			Thread.sleep(68);
		} catch (InterruptedException ignored) {
		}
		mouseReleased(null);
		mouseClicked(null);
		mouseExited(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		currentIcon.paintIcon(this, g, (getWidth() - currentIcon.getIconWidth()) / 2,
				(getHeight() - currentIcon.getIconHeight()) / 2);
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		beginIcon.setDimension(preferredSize);
		endIcon.setDimension(preferredSize);
	}

	@Override
	public boolean contains(int x, int y) {
		// 可以重写该方法，获取到icon的shape来判断
		return super.contains(x, y);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		new JButton().doClick();
		if (l != null)
			l.actionPerformed(new ActionEvent(this, 1, null, InputEvent.BUTTON1_DOWN_MASK));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (filter != null) {
			endIcon.setColorFilter(filter);
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (filter != null) {
			endIcon.setColorFilter(endIconFilter);
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		currentIcon = endIcon;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		currentIcon = beginIcon;
		repaint();
	}

	public SvgIcon getBeginIcon() {
		return beginIcon;
	}

	public void setBeginIcon(SvgIcon beginIcon) {
		this.beginIcon = beginIcon;
		currentIcon = beginIcon;
	}

	public SvgIcon getEndIcon() {
		return endIcon;
	}

	public void setEndIcon(SvgIcon endIcon) {
		this.endIcon = endIcon;
	}

	public SvgIcon.ColorFilter getFilter() {
		return filter;
	}

	public void setFilter(SvgIcon.ColorFilter filter) {
		endIconFilter = endIcon.getColorFilter();
		this.filter = filter;
	}

	public ActionListener getActionListener() {
		return l;
	}
}
