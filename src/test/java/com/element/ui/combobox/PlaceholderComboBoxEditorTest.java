package com.element.ui.combobox;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.overlay.DefaultOverlayable;
import com.element.swing.overlay.OverlayComboBox;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PlaceholderComboBoxEditorTest extends AbstractDemo {
	@Override
	public String getDescription() {
		return "使用设置编辑器和Overlay两种方式为 JComboBox 添加提示文本";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		JComboBox<String> comboBox = new JComboBox<>(DemoData.NAMES);
		comboBox.setEditable(true);
		comboBox.setSelectedIndex(-1);
		PlaceholderComboBoxEditor c = new PlaceholderComboBoxEditor("提示文本");
		comboBox.setEditor(c);

		OverlayComboBox<String> comboBox2 = new OverlayComboBox<>(DemoData.NAMES);
		comboBox2.setEditable(true);
		comboBox2.setSelectedIndex(-1);
		DefaultOverlayable overlayable = new DefaultOverlayable(comboBox2);
		JLabel hintLabel = new JLabel("提示文本");
		hintLabel.setForeground(ColorUtil.PLACEHOLDER_TEXT);
		overlayable.addOverlayComponent(hintLabel, SwingConstants.WEST);
		overlayable.setOverlayLocationInsets(new Insets(0, -10, 0, 0));
		((JTextField) comboBox2.getEditor().getEditorComponent()).getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (e.getDocument().getLength() > 0) {
					// 覆盖组件不可见
					overlayable.setOverlayVisible(false);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (e.getDocument().getLength() == 0)
					overlayable.setOverlayVisible(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		p.add(comboBox);
		p.add(overlayable);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new PlaceholderComboBoxEditorTest());
		});
	}

}