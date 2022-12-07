package com.element.ui.tabs.hoverclosetab;

import com.element.color.ColorUtil;
import com.element.ui.icons.IconsFactory;
import com.element.ui.others.tag.TagFactory;
import com.element.ui.svg.icon.fill.XCircleSvg;
import com.element.ui.svg.icon.regular.XSvg;

import javax.swing.*;
import java.awt.*;

/**
 * 标签的面板，含有文本和图标
 */
class TabPanel extends JPanel {
	private final JButton button;

	public TabPanel(JTabbedPane pane, String title, Component content) {
		this(pane, title, content, new CloseTabIcon());
		// this(pane, title, content, IconsFactory.getSvgIcon(XSvg.class,12,12,ColorUtil.DANGER));
	}

	public TabPanel(JTabbedPane pane, String title, Component content, Icon icon) {
		super(new BorderLayout());
		setOpaque(false);

		button = new JButton(icon);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusable(false);
		button.setVisible(false);

		JLabel label = new JLabel() {
			@Override
			public Dimension getPreferredSize() {
				Dimension dim = super.getPreferredSize();
				// 按钮可见时，减去按钮的宽度
				int bw = button.isVisible() ? button.getPreferredSize().width : 0;
				return new Dimension(dim.width - bw, dim.height);
			}
		};
		label.setText(title);
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 1));

		button.addActionListener(e -> {
			int idx = pane.indexOfComponent(content);
			pane.removeTabAt(idx);
			int count = pane.getTabCount();
			if (count > idx) {
				Component c = pane.getTabComponentAt(idx);
				if (c instanceof TabPanel) {
					((TabPanel) c).setButtonVisible(true);
				}
			}
		});
		add(label);
		add(button, BorderLayout.EAST);
	}

	public void setButtonVisible(boolean flag) {
		button.setVisible(flag);
	}
}
