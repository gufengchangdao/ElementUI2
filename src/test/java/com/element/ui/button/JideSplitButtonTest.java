package com.element.ui.button;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import com.element.util.WrapperUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class JideSplitButtonTest extends AbstractDemo {
	private JideSplitButton[] _buttons;

	@Override
	public String getName() {
		return "JideSplitButtonTest";
	}

	@Override
	public String getDescription() {
		return "JideSplitButton是按钮和菜单的组合。按钮中间有一条线将按钮分成两部分。该位置之前的部分是一个按钮。\n" +
				"用户可以点击它并触发一个动作。该位置之后的部分是菜单。用户可以点击它来显示一个普通的菜单。";
	}

	public Component getDemoPanel() {
		int numberOfButtons = 8;
		JPanel panel = new JPanel(new GridLayout(numberOfButtons, 1, 2, 2));
		_buttons = new JideSplitButton[numberOfButtons];
		int i = 0;
		_buttons[i++] = createJideSplitButton("Copy the text", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		// 嵌套一个试试
		_buttons[0].add(createJideSplitButton("Cut the text", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY)));

		_buttons[i++] = createJideSplitButton("Paste the text", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		_buttons[i++] = createJideSplitButton("Delete the text", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		_buttons[i++] = createJideSplitButton("Refresh the content", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		_buttons[i++] = createJideSplitButton("Undo the action", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		_buttons[i++] = createJideSplitButton("Redo the action", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		// 两种状态的按钮
		_buttons[i++] = createJideToggleSplitButton("Redo the action", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		_buttons[i] = createJideSplitButton("Action history", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));

		for (JideSplitButton button : _buttons) {
			panel.add(button);
		}

		return WrapperUtil.createTopPanel(panel);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new JideSplitButtonTest());
		});
	}

	@Override
	public Component getOptionsPanel() {
		JPanel switchPanel = new JPanel(new GridLayout(0, 1, 2, 2));

		final JRadioButton style1 = new JRadioButton("Toolbar Style");
		final JRadioButton style2 = new JRadioButton("Toolbox Style");
		final JRadioButton style3 = new JRadioButton("Flat Style");

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(style1);
		buttonGroup.add(style2);
		buttonGroup.add(style3);

		switchPanel.add(new JLabel("Styles:"));
		switchPanel.add(style1);
		switchPanel.add(style2);
		switchPanel.add(style3);
		style1.setSelected(true);

		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (style1.isSelected()) {
					for (JideSplitButton button : _buttons) {
						button.setButtonStyle(JideSplitButton.TOOLBAR_STYLE);
					}
				} else if (style2.isSelected()) {
					for (JideSplitButton button : _buttons) {
						button.setButtonStyle(JideSplitButton.TOOLBOX_STYLE);
					}
				} else if (style3.isSelected()) {
					for (JideSplitButton button : _buttons) {
						button.setButtonStyle(JideSplitButton.FLAT_STYLE);
					}
				}
			}
		};
		style1.addItemListener(itemListener);
		style2.addItemListener(itemListener);
		style3.addItemListener(itemListener);

		switchPanel.add(new JLabel("Options: "));

		final JCheckBox option1 = new JCheckBox("Button part enabled");
		final JCheckBox option2 = new JCheckBox("Both parts enabled");
		final JCheckBox option3 = new JCheckBox("Button part selected");
		final JCheckBox option4 = new JCheckBox("Always Dropdown");

		switchPanel.add(option1);
		switchPanel.add(option2);
		switchPanel.add(option3);
		switchPanel.add(option4);

		option1.setSelected(true);
		option1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				for (JideSplitButton button : _buttons) {
					button.setButtonEnabled(option1.isSelected());
				}
			}
		});

		option2.setSelected(true);
		option2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				for (JideSplitButton button : _buttons) {
					button.setEnabled(option2.isSelected());
				}
			}
		});

		option3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				for (JideSplitButton button : _buttons) {
					button.setButtonSelected(option3.isSelected());
				}
			}
		});

		option4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				for (JideSplitButton button : _buttons) {
					button.setAlwaysDropdown(option4.isSelected());
				}
			}
		});

		return switchPanel;
	}

	static JideSplitButton createJideSplitButton(String name, Icon icon) {
		final JideSplitButton button = new JideSplitButton(name);
		button.setIcon(icon);
		button.add(new AbstractAction("Sample Menu Item") {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("button is clicked");
			}
		});
		button.getPopupMenu().addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				System.out.println("menu is clicked");
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
			}
		});
		button.setFocusable(false);
		return button;
	}

	static JideSplitButton createJideToggleSplitButton(String name, Icon icon) {
		final JideSplitButton button = new JideToggleSplitButton(name);
		button.setIcon(icon);
		button.add(new AbstractAction("Sample Menu Item") {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.setFocusable(false);
		return button;
	}
}