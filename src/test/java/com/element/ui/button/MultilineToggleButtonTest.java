package com.element.ui.button;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MultilineToggleButtonTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		MultilineToggleButton chkCertA = new MultilineToggleButton(MultilineToggleButton.CHECKBOX_TYPE, "Very Long Text Goes Here");
		MultilineToggleButton radioButton = new MultilineToggleButton(MultilineToggleButton.RADIOBUTTON_TYPE, "Very Long Text");
		p.add(chkCertA);
		p.add(radioButton);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new MultilineToggleButtonTest());
		});
	}
}