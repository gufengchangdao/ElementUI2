package com.element.ui.dialog;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.layout.JideBoxLayout;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StandardDialogTest extends AbstractDemo {

	@Override
	public String getName() {
		return "StandardDialogDemo";
	}

	@Override
	public String getDescription() {
		return """
				StandardDialog is a step to standardize the creation of JDialog so that all dialogs in your application look consistent.\s

				There are a list of features we put in StandardDialog which are usually need by any dialogs.
				1. ButtonPanel where you can put several buttons.\s
				2. Handle ENTER and ESC key automatically
				3. Allow you to set initial focused component
				4. Standard layouts to arrange banner, content and buttons
				Demoed classes:
				com.jidesoft.dialog.StandardDialog""";
	}

	public Component getDemoPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JButton button = new JButton();
		AbstractAction action = new AbstractAction("Show StandardDialog 1") {
			public void actionPerformed(ActionEvent e) {
				StandardDialog example = new StandardDialogExample1();
				example.pack();
				example.setLocationRelativeTo(null);
				example.setVisible(true);
			}
		};
		button.setAction(action);
		panel.add(button);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		button = new JButton();
		action = new AbstractAction("Show StandardDialog 2") {
			public void actionPerformed(ActionEvent e) {
				StandardDialog example = new StandardDialogExample2();
				example.pack();
				example.setLocationRelativeTo(null);
				example.setVisible(true);
			}
		};
		button.setAction(action);
		panel.add(button);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		button = new JButton();
		action = new AbstractAction("Show StandardDialog 3") {
			public void actionPerformed(ActionEvent e) {
				StandardDialog example = new StandardDialogExample3();
				example.pack();
				example.setLocationRelativeTo(null);
				example.setVisible(true);
			}
		};
		button.setAction(action);
		panel.add(button);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		button = new JButton();
		action = new AbstractAction("Show StandardDialog 4") {
			public void actionPerformed(ActionEvent e) {
				StandardDialog example = new StandardDialogExample4();
				example.pack();
				example.setLocationRelativeTo(null);
				example.setVisible(true);
			}
		};
		button.setAction(action);
		panel.add(button);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		button = new JButton();
		action = new AbstractAction("Show \"Expandable\" Dialog") {
			public void actionPerformed(ActionEvent e) {
				StandardDialog example = new StandardDialogExample5();
				example.pack();
				example.setLocationRelativeTo(null);
				example.setVisible(true);
			}
		};
		button.setAction(action);
		panel.add(button);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		panel.add(Box.createGlue(), JideBoxLayout.VARY);
		return panel;
	}


	@Override
	public Class<?>[] getDemoSource() {
		return new Class[]{
				StandardDialogTest.class, StandardDialogExample1.class, StandardDialogExample2.class,
				StandardDialogExample3.class, StandardDialogExample4.class, StandardDialogExample5.class
		};
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new StandardDialogTest());
		});
	}
}