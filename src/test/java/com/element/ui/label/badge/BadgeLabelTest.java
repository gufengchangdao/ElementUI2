package com.element.ui.label.badge;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.overlay.DefaultOverlayable;
import com.element.swing.overlay.OverlayTextArea;
import com.element.ui.label.BadgeLabel;
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