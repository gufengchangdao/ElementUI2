package com.element.ui.dialog;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.button.ButtonFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ScrollableButtonPanelTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("fill"));

		ScrollableButtonPanel buttonPanel = new ScrollableButtonPanel(SwingConstants.VERTICAL);
		buttonPanel.addButton(ButtonFactory.createDefaultButton(ButtonNames.CANCEL, ColorUtil.PRIMARY),ButtonPanel.CANCEL);
		buttonPanel.addButton(ButtonFactory.createDefaultButton(ButtonNames.HELP, ColorUtil.PRIMARY),ButtonPanel.HELP);
		for (int i = 0; i < 10; i++) {
			buttonPanel.addButton(ButtonFactory.createDefaultButton("按钮" + i, ColorUtil.PRIMARY),ButtonPanel.OTHER_BUTTON);
		}

		p.add(new JScrollPane(buttonPanel),"growx, growy");
		p.setPreferredSize(new Dimension(400,300));
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ScrollableButtonPanelTest());
		});
	}
}