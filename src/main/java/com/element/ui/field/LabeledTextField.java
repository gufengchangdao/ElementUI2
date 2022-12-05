/*
 * @(#)ShortcutField.java 7/9/2002
 *
 * Copyright 2002 - 2002 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.field;

import com.element.color.ColorUtil;
import com.element.plaf.UIDefaultsLookup;
import com.element.swing.overlay.DefaultOverlayable;
import com.element.swing.overlay.OverlayTextField;
import com.element.swing.overlay.Overlayable;
import com.element.ui.menu.JidePopupMenu;
import com.element.util.SelectAllUtil;
import com.element.util.SystemInfo;
import com.element.util.UIUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * LabeledTextField是一个组合组件，它在前面包含文本字段和一个可选的 JLabel，在末尾包含另一个可选的 AbstractButton。标签支持菜单，输入
 * 框支持提示文本。
 * <p>
 * 直接使用支持左侧label和输入框，其他功能默认不开启。建议使用时继承该类，提供更强大的功能，你可以选择性地重写下面的方法
 * <ul>
 *     <li>{@link #createButton()} 创建右侧按钮</li>
 *     <li>{@link #getHintText()} 输入框提示文本</li>
 *     <li>{@link #customizePopupMenu(JPopupMenu)} 定制菜单内容</li>
 *     <li>{@link #getPopupMenuCustomizer()} 结合输入框定制菜单和输入框</li>
 * </ul>
 * 上面是经常重写的方法，如果有需求其他方法也可以进行重写
 */
public class LabeledTextField extends JPanel {
	protected JTextField _textField;
	protected JLabel _label;
	protected AbstractButton _button;

	protected String _labelText;
	protected Icon _icon;
	protected String _hintText;
	protected boolean _showHintTextWhenFocused = false;
	protected JLabel _hintLabel;
	protected PopupMenuCustomizer _customizer;
	protected KeyStroke _contextMenuKeyStroke;
	private DefaultOverlayable _hintOverlayable;

	/**
	 * The PopupMenuCustomizer for the context menu when clicking on the label/icon before the text field.
	 */
	public interface PopupMenuCustomizer {
		void customize(LabeledTextField field, JPopupMenu menu);
	}

	public LabeledTextField() {
		this(null, null);
	}

	public LabeledTextField(Icon icon) {
		this(icon, null);
	}

	public LabeledTextField(Icon icon, String labelText) {
		super();
		_icon = icon;
		_labelText = labelText;
		initComponent();
	}

	protected void initComponent() {
		_label = createLabel();
		if (_label != null) {
			_label.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					showContextMenu();
				}
			});
		}

		_button = createButton();
		_textField = createTextField();
		initLayout(_label, _textField, _button);
		_contextMenuKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.ALT_DOWN_MASK);
		registerContextMenuKeyStroke(getContextMenuKeyStroke());
		updateUI();
	}

	private void registerContextMenuKeyStroke(KeyStroke keyStroke) {
		if (keyStroke != null) {
			_textField.getInputMap().put(keyStroke, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showContextMenu();
				}
			});
		}
	}

	private void unregisterContextMenuKeyStroke(KeyStroke keyStroke) {
		if (keyStroke != null)
			_textField.getInputMap().remove(keyStroke);
	}

	/**
	 * Shows the context menu.
	 */
	protected void showContextMenu() {
		if (!isEnabled()) return;

		JPopupMenu menu = createContextMenu();
		// 定制menu内容
		customizePopupMenu(menu);
		// 根据输入框内容定制menu
		PopupMenuCustomizer customizer = getPopupMenuCustomizer();
		if (customizer != null && menu != null) {
			customizer.customize(this, menu);
		}
		if (menu != null && menu.getComponentCount() > 0) {
			Point location = calculateContextMenuLocation();
			UIUtil.showPopupMenu(menu, this, location.x, location.y);
		}
	}

	/**
	 * Calculates the locatioin of the context menu.
	 *
	 * @return the upper-left corner location.
	 * @since 3.4.2
	 */
	protected Point calculateContextMenuLocation() {
		Point location = _label.getLocation();
		return new Point(location.x + (_label.getIcon() == null ? 1 : _label.getIcon().getIconWidth() / 2), location.y + _label.getHeight() + 1);
	}

	/**
	 * Customizes the popup menu.
	 *
	 * @param menu the menu to customize
	 * @since 3.4.1
	 */
	protected void customizePopupMenu(JPopupMenu menu) {
	}

	/**
	 * 设置组件的布局。默认情况下，我们使用的边框布局首先是标签，中间是输入框，最后是按钮。该方法也会为输入框添加提示标签
	 *
	 * @param label  左侧标签
	 * @param field  中间输入框
	 * @param button 右侧按钮
	 */
	protected void initLayout(final JLabel label, final JTextField field, final AbstractButton button) {
		setLayout(new BorderLayout(3, 3));
		if (label != null) {
			add(label, BorderLayout.LINE_START);
		}
		_hintLabel = new JLabel(getHintText());
		_hintLabel.setOpaque(false);
		Color foreground = UIDefaultsLookup.getColor("Label.disabledForeground");
		if (foreground == null) {
			foreground = ColorUtil.PLACEHOLDER_TEXT;
		}
		_hintLabel.setForeground(foreground);
		_hintOverlayable = new DefaultOverlayable(field, _hintLabel, DefaultOverlayable.LEADING);
		_hintOverlayable.setOpaque(false);
		field.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				adjustOverlay(field, _hintOverlayable);
			}

			public void focusGained(FocusEvent e) {
				adjustOverlay(field, _hintOverlayable);
			}
		});
		field.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				adjustOverlay(field, _hintOverlayable);
			}

			public void removeUpdate(DocumentEvent e) {
				adjustOverlay(field, _hintOverlayable);
			}

			public void changedUpdate(DocumentEvent e) {
				adjustOverlay(field, _hintOverlayable);
			}
		});
		add(_hintOverlayable);
		if (button != null) {
			add(button, BorderLayout.AFTER_LINE_ENDS);
		}
	}

	/**
	 * Checks if the hint text will still be shown when the text field has focus. By default, the hint text is only
	 * shown when the text field doesn't have focus.
	 *
	 * @return true or false.
	 * @since 3.3.6
	 */
	public boolean isShowHintTextWhenFocused() {
		return _showHintTextWhenFocused;
	}

	/**
	 * Sets the flag if the hint text will still be shown when the text field has focus. By default, the hint text is
	 * only shown when the text field doesn't have focus. If you set it to true, the hint text will always be shown
	 * regardless if the text field has focus.
	 *
	 * @param showHintTextWhenFocused true or false.
	 * @since 3.3.6
	 */
	public void setShowHintTextWhenFocused(boolean showHintTextWhenFocused) {
		_showHintTextWhenFocused = showHintTextWhenFocused;
		if (_textField != null && _hintOverlayable != null) {
			adjustOverlay(_textField, _hintOverlayable);
		}
	}

	private void adjustOverlay(JTextField field, Overlayable overlayable) {
		if (field.hasFocus() && !isShowHintTextWhenFocused()) {
			overlayable.setOverlayVisible(false);
		} else {
			String text = field.getText();
			overlayable.setOverlayVisible(text == null || text.length() == 0);
		}
	}

	/**
	 * Creates a text field. By default it will return a JTextField with opaque set to false. Subclass can override this
	 * method to create their own text field such as JFormattedTextField.
	 *
	 * @return a text field.
	 */
	protected JTextField createTextField() {
		JTextField textField = new OverlayTextField(20);
		SelectAllUtil.install(textField);
		UIUtil.setComponentTransparent(textField);
		return textField;
	}

	/**
	 * Creates a context menu. The context menu will be shown when user clicks on the label.
	 *
	 * @return a context menu.
	 */
	protected JidePopupMenu createContextMenu() {
		return new JidePopupMenu();
	}

	@Override
	public void updateUI() {
		super.updateUI();
		Border textFieldBorder = UIDefaultsLookup.getBorder("TextField.border");
		if (textFieldBorder != null) {
			boolean big = textFieldBorder.getBorderInsets(this).top >= 2;
			if (big)
				setBorder(textFieldBorder);
			else
				setBorder(BorderFactory.createCompoundBorder(textFieldBorder, BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		} else {
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		}

		if (isEnabled()) {
			LookAndFeel.installColors(this, "TextField.background", "TextField.foreground");
		} else {
			LookAndFeel.installColors(this, "TextField.disableBackground", "TextField.inactiveForeground");
		}

		if (textFieldBorder != null && _textField != null) {
			_textField.setBorder(BorderFactory.createEmptyBorder());
		}
	}

	/**
	 * Creates the button that appears after the text field. By default it returns null so there is no button. Subclass
	 * can override it to create their own button. A typical usage of this is to create a browse button to browse a file
	 * or directory.
	 *
	 * @return the button.
	 */
	protected AbstractButton createButton() {
		return null;
	}

	/**
	 * Creates the label that appears before the text field. By default, it only has a search icon.
	 *
	 * @return the label.
	 */
	protected JLabel createLabel() {
		return new JLabel(_labelText, _icon, SwingConstants.CENTER);
	}

	/**
	 * Sets the text that appears before the text field.
	 *
	 * @param text the text that appears before the text field.
	 */
	public void setLabelText(String text) {
		_labelText = text;
		if (_label != null) {
			_label.setText(text);
		}
	}

	/**
	 * Gets the text that appears before the text field.
	 *
	 * @return the text that appears before the text field. By default it's null, meaning no text.
	 */
	public String getLabelText() {
		if (_label != null) {
			return _label.getText();
		} else {
			return _labelText;
		}
	}

	/**
	 * Sets the icon that appears before the text field.
	 *
	 * @param icon the icon that appears before the text field.
	 */
	public void setIcon(Icon icon) {
		_icon = icon;
		if (_label != null) {
			_label.setIcon(icon);
		}
	}

	/**
	 * Gets the icon that appears before the text field.
	 *
	 * @return the icon that appears before the text field.
	 */
	public Icon getIcon() {
		if (_label != null) {
			return _label.getIcon();
		} else {
			return _icon;
		}
	}

	/**
	 * Gets the JLabel that appears before text field.
	 *
	 * @return the JLabel that appears before text field.
	 */
	public JLabel getLabel() {
		return _label;
	}

	/**
	 * Gets the AbstractButton that appears after text field.
	 *
	 * @return the AbstractButton that appears after text field.
	 */
	public AbstractButton getButton() {
		return _button;
	}

	/**
	 * Sets the number of columns in this TextField, and then invalidate the layout.
	 *
	 * @param columns the number of columns for this text field.
	 */
	public void setColumns(int columns) {
		if (_textField != null) {
			_textField.setColumns(columns);
		}
	}

	/**
	 * Sets the text in this TextField.
	 *
	 * @param text the new text in this TextField.
	 */
	public void setText(String text) {
		if (_textField != null) {
			_textField.setText(text);
		}
	}

	/**
	 * Gets the text in this TextField.
	 *
	 * @return the text in this TextField.
	 */
	public String getText() {
		if (_textField != null) {
			return _textField.getText();
		} else {
			return null;
		}
	}

	/**
	 * Gets the actual text field.
	 *
	 * @return the actual text field.
	 */
	public JTextField getTextField() {
		return _textField;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			if (getTextField() != null) {
				getTextField().setEnabled(true);
			}
			if (getLabel() != null) {
				getLabel().setEnabled(true);
			}
			if (getButton() != null) {
				getButton().setEnabled(true);
			}
		} else {
			if (getTextField() != null) {
				getTextField().setEnabled(false);
			}
			if (getLabel() != null) {
				getLabel().setEnabled(false);
			}
			if (getButton() != null) {
				getButton().setEnabled(false);
			}
			setBackground(UIDefaultsLookup.getColor("control"));
		}
		if (_hintLabel != null) {
			boolean textEmpty = true;
			if (getTextField() != null) {
				textEmpty = getTextField().getText() == null || getTextField().getText().length() == 0;
			}
			_hintLabel.setVisible(isEnabled() && textEmpty);
		}
		JTextField textField = getTextField();
		if (textField != null) {
			// this probably won't work with L&F which ignore the background property like GTK L&F
			setBackground(textField.getBackground());
			setForeground(textField.getForeground());
		} else {
			if (enabled) {
				setBackground(UIDefaultsLookup.getColor("TextField.background"));
				setForeground(UIDefaultsLookup.getColor("TextField.foreground"));
			} else {
				Color background = UIDefaultsLookup.getColor("TextField.disabledBackground");
				if (background == null) {
					// TextField.disabledBackground not defined by metal
					background = UIDefaultsLookup.getColor("TextField.inactiveBackground");
					// Nimbus uses TextField[Disabled].backgroundPainter (not a Color)
					// but don't know how to set that for a single panel instance, maybe with a ClientProperty?
				}
				setBackground(background);
				setForeground(UIDefaultsLookup.getColor("TextField.inactiveForeground"));
			}
		}
	}

	public int getBaseline(int width, int height) {
		try {
			Method method = Component.class.getMethod("getBaseline", int.class, int.class);
			Object value = method.invoke(_textField, width, height);
			if (value instanceof Integer) {
				return (Integer) value;
			}
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
		}
		return -1;
	}

	/**
	 * Gets the hint text when the field is empty and not focused.
	 *
	 * @return the hint text.
	 */
	public String getHintText() {
		return _hintText;
	}

	/**
	 * Sets the hint text.
	 *
	 * @param hintText the new hint text.
	 */
	public void setHintText(String hintText) {
		_hintText = hintText;
		if (_hintLabel != null) {
			_hintLabel.setText(_hintText);
		}
	}

	/**
	 * Gets the PopupMenuCustomizer.
	 *
	 * @return the PopupMenuCustomizer.
	 */
	public PopupMenuCustomizer getPopupMenuCustomizer() {
		return _customizer;
	}

	/**
	 * Sets the PopupMenuCustomizer. PopupMenuCustomizer can be used to do customize the popup menu for the
	 * <code>LabeledTextField</code>.
	 * <p/>
	 * PopupMenuCustomizer has a customize method. The popup menu of this menu will be passed in. You can
	 * add/remove/change the menu items in customize method. For example,
	 * <code><pre>
	 * field.setPopupMenuCustomizer(new LabeledTextField.PopupMenuCustomizer() {
	 *     void customize(LabeledTextField field, JPopupMenu menu) {
	 *         menu.removeAll();
	 *         menu.add(new JMenuItem("..."));
	 *         menu.add(new JMenuItem("..."));
	 *     }
	 * }
	 * </pre></code>
	 * If the menu is never used, the two add methods will never be called thus improve the performance.
	 *
	 * @param customizer the PopupMenuCustomizer
	 */
	public void setPopupMenuCustomizer(PopupMenuCustomizer customizer) {
		_customizer = customizer;
	}

	/**
	 * Gets the keystroke that will bring up the context menu. If you never set it before, it will return SHIFT-F10 for
	 * operating systems other than Mac OS X.
	 *
	 * @return the keystroke that will bring up the context menu.
	 */
	public KeyStroke getContextMenuKeyStroke() {
		if (_contextMenuKeyStroke == null) {
			_contextMenuKeyStroke = !SystemInfo.isMacOSX() ? KeyStroke.getKeyStroke(KeyEvent.VK_F10, KeyEvent.SHIFT_DOWN_MASK) : null;
		}
		return _contextMenuKeyStroke;
	}

	/**
	 * Changes the keystroke that brings up the context menu which is normally shown when user clicks on the label icon
	 * before the text field.
	 *
	 * @param contextMenuKeyStroke the new keystroke to bring up the context menu.
	 */
	public void setContextMenuKeyStroke(KeyStroke contextMenuKeyStroke) {
		if (_contextMenuKeyStroke != null) {
			unregisterContextMenuKeyStroke(_contextMenuKeyStroke);
		}
		_contextMenuKeyStroke = contextMenuKeyStroke;
		if (_contextMenuKeyStroke != null) {
			registerContextMenuKeyStroke(_contextMenuKeyStroke);
		}
	}
}
