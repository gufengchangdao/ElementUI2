package com.element.ui.notice.notification;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.svg.icon.fill.XCircleSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

import static com.element.ui.notice.notification.NotificationFactory.openNotification;

public class NotificationFactoryTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JComponent glassPane = (JComponent) getFrame().getGlassPane();
		glassPane.setLayout(null);

		JButton button = new JButton("弹出");
		button.addActionListener(e -> {
			// Popup popup = getPopup(SwingTestUtil.getFrame().getContentPane(), "提示",
			// 		new JLabel("这是一条成功的提示消息"), 4500, true, true,
			// 		SwingConstants.NORTH_EAST, 10);
			// popup.show();
			glassPane.setVisible(true);
			SvgIcon icon = XCircleSvg.of(16, 16);
			icon.setColorFilter(color -> ColorUtil.SUCCESS);
			NotificationComponent c = openNotification(glassPane, "提示", icon, new JLabel("这是一条成功的提示消息"),
					true, true, SwingConstants.NORTH_EAST, 10, 4500);
		});

		return button;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			SwingTestUtil.setDefaultTimingSource();
			showAsFrame(new NotificationFactoryTest());
		});
	}
}