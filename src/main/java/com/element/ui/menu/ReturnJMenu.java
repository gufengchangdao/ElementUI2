package com.element.ui.menu;

import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.svg.icon.regular.CaretLeftSvg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 子菜单带有返回按钮的菜单项，一般作为二级或更高级菜单使用
 */
public class ReturnJMenu extends JideMenu implements MouseListener {
	public static final SvgIcon icon = CaretLeftSvg.of(10, 10);

	public ReturnJMenu(String s) {
		super(s);

		setDelay(100_000);
		addMouseListener(this);

		// 返回按钮
		JButton button = new JButton(icon);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBackground(null);
		button.setOpaque(false);
		button.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				e.getComponent().getParent().repaint();
			}
		});
		button.addActionListener(e -> getPopupMenu().setVisible(false));

		JMenuItem titleBar = new JMenuItem();
		titleBar.setOpaque(true);
		titleBar.setEnabled(false);
		titleBar.setFocusable(false);
		titleBar.setLayout(new BorderLayout(0, 0));
		titleBar.add(button, BorderLayout.WEST);
		titleBar.add(new JLabel(s, SwingConstants.CENTER));
		titleBar.add(Box.createHorizontalStrut(button.getPreferredSize().width), BorderLayout.EAST);
		// titleBar.setPreferredSize(new Dimension(200, 24));
		add(titleBar);
	}

	@Override
	public void setPopupMenuVisible(boolean b) {
		JPopupMenu popup = getPopupMenu();
		popup.setPopupSize(getParent().getPreferredSize());
		Point p = getLocation();
		setMenuLocation(-p.x, -p.y);
		super.setPopupMenuVisible(b);
	}

	@Override
	public JMenuItem add(JMenuItem item) {
		item.setMaximumSize(new Dimension(Short.MAX_VALUE, item.getPreferredSize().height));
		return super.add(item);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		((AbstractButton) e.getComponent()).doClick();
		getPopupMenu().setVisible(true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}