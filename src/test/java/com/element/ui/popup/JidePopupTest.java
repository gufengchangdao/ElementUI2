package com.element.ui.popup;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.layout.JideBoxLayout;
import com.element.ui.menu.JideMenu;
import com.element.util.SearchableUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JidePopupTest extends AbstractDemo {
	private JidePopup _popup;

	@Override
	public String getName() {
		return "PopupDemo";
	}

	@Override
	public String getDescription() {
		return """
				JidePopup is a popup that is detachable, dragable, resizable, autohide when timeout or clicks outside.

				Demoed classes:
				com.jidesoft.popup.JidePopup""";
	}

	public Component getDemoPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		final JButton attachedButton = new JButton();
		_popup = createPopup();
		AbstractAction action = new AbstractAction("Show an attached popup (regular popup)") {
			public void actionPerformed(ActionEvent e) {
				_popup.setOwner(attachedButton);
				_popup.setResizable(true);
				_popup.setMovable(true);
				if (_popup.isPopupVisible()) {
					_popup.hidePopup();
				} else {
					_popup.showPopup();
				}
			}
		};
		attachedButton.setAction(action);
		attachedButton.setName("" + action.getValue(Action.NAME));
		panel.add(attachedButton);

		final JCheckBox snapIn = new JCheckBox("吸附效果(拖动时捕捉到原始位置)");
		snapIn.addItemListener(e -> {
			if (snapIn.isSelected()) {
				_popup.setBackToOriginalInsets(new Insets(10, 10, 10, 10));
			} else {
				_popup.setBackToOriginalInsets(new Insets(0, 0, 0, 0));
			}
		});
		snapIn.setSelected(true);
		panel.add(snapIn);

		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		if ("window".equalsIgnoreCase(System.getProperty("docking.floatingContainerType"))) {
			final JButton attachedDockableFrameButton = new JButton();
			action = new AbstractAction("Show an attached popup (dockable frame type popup)") {
				public void actionPerformed(ActionEvent e) {
					JidePopup popup = createPopup();

					popup.setOwner(attachedDockableFrameButton);
					popup.showPopup();
				}
			};
			attachedDockableFrameButton.setAction(action);
			panel.add(attachedDockableFrameButton);
			panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);
		}

		final JButton detachedButton = new JButton();
		action = new AbstractAction("Show a detached popup") {
			public void actionPerformed(ActionEvent e) {
				JidePopup popup = createPopup();
				popup.setOwner(detachedButton);
				popup.setResizable(true);
				popup.setMovable(true);
				popup.setAttachable(false);
				if (popup.isPopupVisible()) {
					popup.hidePopup();
				} else {
					popup.showPopup(panel);
				}
			}
		};
		detachedButton.setAction(action);
		detachedButton.setName("" + action.getValue(Action.NAME));
		panel.add(detachedButton);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		final JButton ideaButton = new JButton();
		final JidePopup ideaPopup = new JidePopup();
		ideaPopup.setMovable(false);
		JPanel ideaPanel = new JPanel(new BorderLayout());
		ideaPanel.setBorder(BorderFactory.createEmptyBorder(4, 2, 2, 2));
		final JComboBox<String> comboBox = new JComboBox<>(new String[]{"卡莲","符华","德丽莎","姬子","布洛妮娅","芽衣",
				"琪亚娜","八重樱","丽塔","莉莉娅","罗莎莉娅","希儿","幽兰黛尔","明日香"});
		SearchableUtil.installSearchable(comboBox);
		ideaPanel.add(new JLabel("<HTML><B>选择角色名:</B></HTML>"), BorderLayout.BEFORE_FIRST_LINE);
		ideaPanel.add(comboBox);
		ideaPopup.getContentPane().add(ideaPanel);
		action = new AbstractAction("Show IntelliJ IDEA Ctrl-N Popup") {
			public void actionPerformed(ActionEvent e) {
				ideaPopup.setResizable(false);
				ideaPopup.setMovable(false);
				ideaPopup.setDefaultFocusComponent(comboBox);
				ideaPopup.setOwner(ideaButton);
				comboBox.registerKeyboardAction(e1 -> _popup.hidePopupImmediately(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
				if (ideaPopup.isPopupVisible()) {
					ideaPopup.hidePopup();
				} else {
					ideaPopup.showPopup(panel);
				}
			}
		};
		ideaButton.setAction(action);
		ideaButton.setName("" + action.getValue(Action.NAME));

		panel.add(ideaButton);
		panel.add(Box.createVerticalStrut(12), JideBoxLayout.FIX);

		panel.add(Box.createGlue(), JideBoxLayout.VARY);
		return panel;
	}

	private JidePopup createPopup() {
		JidePopup popup = new JidePopup();
		popup.setMovable(true);
		popup.getContentPane().setLayout(new BorderLayout());
		JTextArea view = new JTextArea();
		view.setRows(10);
		view.setColumns(40);
		popup.getContentPane().add(new JScrollPane(view));
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = menuBar.add(new JideMenu("File"));
		menu.add("<< Example >>");
		menuBar.add(new JideMenu("Edit"));
		menuBar.add(new JideMenu("Help"));
		popup.setJMenuBar(menuBar);
		popup.setDefaultFocusComponent(view);
		return popup;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LookAndFeelFactory.installJideExtension();
			SwingTestUtil.loadSkin();
			showAsFrame(new JidePopupTest());
		});
	}
}