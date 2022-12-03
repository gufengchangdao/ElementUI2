package com.element.ui.button;

import com.element.ui.label.MultilineLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 一个切换按钮（JCheckBox 或 JRadioButton），可以包装其标签以扩展到多行。使用 CHECKBOX_TYPE 或 RADIOBUTTON_TYPE 指定要创建的切换
 * 按钮的类型。
 *
 * @author Bao Trang
 */
public class MultilineToggleButton extends JPanel {
	private final JToggleButton _button;
	private final MultilineLabel _label;

	public static int CHECKBOX_TYPE = 0;
	public static int RADIOBUTTON_TYPE = 1;

	/**
	 * constructor.
	 *
	 * @param type     the type of toggle button to create
	 * @param labelTxt the label
	 */
	public MultilineToggleButton(int type, String labelTxt) {
		if (type == CHECKBOX_TYPE) {
			_button = new JCheckBox();
		} else if (type == RADIOBUTTON_TYPE) {
			_button = new JRadioButton();
		} else {
			_button = new JToggleButton();
		}
		_label = new MultilineLabel(labelTxt);
		_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				_button.doClick();
			}
		});
		build();
	}

	/**
	 * builds the component
	 */
	private void build() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(_button);
		add(Box.createHorizontalGlue());
		add(_label);
	}

	public void setTopAlignment() {
		_button.setAlignmentY(Component.TOP_ALIGNMENT);
		_label.setAlignmentY(Component.TOP_ALIGNMENT);
	}

	public void setCenterAlignment() {
		_button.setAlignmentY(Component.CENTER_ALIGNMENT);
		_label.setAlignmentY(Component.CENTER_ALIGNMENT);
	}

	/**
	 * get the toggle button
	 *
	 * @return toggle button
	 */
	public JToggleButton getToggleButton() {
		return _button;
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
		_label.setVisible(b);
		_button.setVisible(b);
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		_button.setEnabled(b);
		_label.setEnabled(b);
	}
}

