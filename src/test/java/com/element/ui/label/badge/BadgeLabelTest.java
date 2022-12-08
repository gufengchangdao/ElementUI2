package com.element.ui.label.badge;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.overlay.DefaultOverlayable;
import com.element.swing.overlay.OverlayTextArea;
import com.element.ui.label.BadgeLabel;
import com.element.ui.others.badge.BadgePanelGroup;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;

public class BadgeLabelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1", "grow"));

		final JTextArea textArea = new OverlayTextArea();
		textArea.setColumns(50);
		textArea.setRows(10);
		DefaultOverlayable overlayTextArea = new DefaultOverlayable(new JScrollPane(textArea));
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				if (textArea.getDocument().getLength() > 0) {
					// 覆盖组件不可见
					overlayTextArea.setOverlayVisible(false);
				}
			}

			public void removeUpdate(DocumentEvent e) {
				if (textArea.getDocument().getLength() == 0)
					overlayTextArea.setOverlayVisible(true);
			}

			public void changedUpdate(DocumentEvent e) {
			}
		});
		textArea.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				overlayTextArea.setOverlayVisible(false);
			}

			public void focusLost(FocusEvent e) {
				overlayTextArea.setOverlayVisible(textArea.getDocument().getLength() == 0);
			}
		});
		// 添加覆盖组件
		BadgeLabel badgeLabel = new BadgeLabel("测试标签", ColorUtil.changeAlpha(ColorUtil.DANGER, .5f), Color.WHITE);
		overlayTextArea.addOverlayComponent(badgeLabel);
		p.add(overlayTextArea, "growx");

		p.add(new JXTextField("移开焦点"), "growx");
		p.add(Box.createVerticalStrut(20));


		JPanel panel = new JPanel(new MigLayout("wrap 1"));
		JButton b1 = new JButton("添加");
		b1.setPreferredSize(new Dimension(150, 25));
		JButton b2 = new JButton("移除");
		JButton b3 = new JButton("修改");
		panel.add(b1);
		panel.add(b2);
		panel.add(b3);
		// panel.getLayout().layoutContainer(panel);
		// System.out.println(SwingUtilities.convertPoint(panel, 0, 0, b1));
		BadgePanelGroup badgePanelGroup = new BadgePanelGroup(panel,
				Arrays.asList(b2, b3),
				Arrays.asList(new BadgeLabel(null, ColorUtil.PRIMARY, Color.WHITE),
						new BadgeLabel("56", ColorUtil.SUCCESS, Color.WHITE)),
				12
		);

		BadgeLabel newLabel = new BadgeLabel("新添加超级长的标签", ColorUtil.PRIMARY, Color.WHITE);
		badgePanelGroup.addBadge(b1, newLabel);
		badgePanelGroup.repaint(SwingTestUtil.getFrame().getContentPane(), newLabel);
		p.add(badgePanelGroup.getLayeredPane());
		//注意，只有在pack()之后才能获取到组件所在容器中的位置
		badgePanelGroup.setAllBadgeLocation();

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new BadgeLabelTest());
		});
	}
}