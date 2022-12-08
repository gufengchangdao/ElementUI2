package com.element.ui.notice.message;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.ui.tabs.CloseableTabTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class SystemMessageFactoryTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());

		MenuItem exitItem = new MenuItem("EXIT");
		exitItem.addActionListener(e -> {
			Container c = p.getTopLevelAncestor();
			if (c instanceof Window) {
				((Window) c).dispose();
			}
			//其实会自动删除的
			// SystemTray tray = SystemTray.getSystemTray();
			// for (TrayIcon icon : tray.getTrayIcons()) {
			// 	tray.remove(icon);
			// }
		});
		PopupMenu popup = new PopupMenu();
		popup.add(exitItem);

		// 托盘图标
		SvgIcon ic = SwordSvg.of(16, 16);
		ic.setColorFilter(color -> ColorUtil.PRIMARY);
		TrayIcon icon = new TrayIcon(ic.toImage(1), "这是系统托盘", popup);
		try {
			// 托盘图标添加到系统托盘中
			SystemTray.getSystemTray().add(icon);
		} catch (AWTException ex) {
			throw new IllegalStateException(ex);
		}

		JComboBox<TrayIcon.MessageType> messageType = new JComboBox<>(TrayIcon.MessageType.values());

		JButton messageButton = new JButton("弹出消息");
		messageButton.addActionListener(e -> {
			SystemMessageFactory.openMessage("标题", "正文",
					messageType.getItemAt(messageType.getSelectedIndex()));
		});
		p.add(messageType);
		p.add(messageButton);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new SystemMessageFactoryTest());
		});
	}
}