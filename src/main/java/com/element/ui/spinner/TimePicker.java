package com.element.ui.spinner;

import com.element.color.ColorUtil;
import com.element.converter.ObjectConverterManager;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.border.IconBorder;
import com.element.ui.combobox.AutoCompletionComboBox;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.regular.ClockSvg;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Calendar;
import java.util.Vector;

/**
 * 时间选择器，用于选择或输入时间。
 * <p>
 * 使用默认的类型转换器将Calendar转为String，如果需要自定义格式化，参考{@link ObjectConverterManager}
 */
public class TimePicker extends AutoCompletionComboBox<Calendar> {
	private Icon icon;
	private Border oldBorder;

	public TimePicker() {
		init();
	}

	public TimePicker(Vector<Calendar> items) {
		super(items);
		init();
	}

	public TimePicker(Calendar[] items) {
		super(items);
		init();
	}

	public TimePicker(ComboBoxModel<Calendar> aModel) {
		super(aModel);
		init();
	}

	private void init() {
		oldBorder = getBorder();

		int size = (int) (getPreferredSize().height * 0.75);
		icon = IconsFactory.getSvgIcon(ClockSvg.class, size, size, ColorUtil.PRIMARY);
		setIcon(icon);

		// 初始化默认转换器，如果已经初始化了方法里不会再初始化的
		ObjectConverterManager.initDefaultConverter();

		// 设置render，修改显示的字符串
		ListCellRenderer<? super Calendar> oldRender = getRenderer();
		setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			Component c = oldRender.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (c instanceof JLabel)
				((JLabel) c).setText(ObjectConverterManager.toString(value));
			return c;
		});

		// 设置Item监听器，监听Item的选择
		addItemListener(e -> {
			if (getEditor().getEditorComponent() instanceof JTextField f)
				EventQueue.invokeLater(() -> f.setText(ObjectConverterManager.toString(e.getItem())));
		});
	}

	/**
	 * 设置图标
	 *
	 * @param icon 左侧图标
	 */
	public void setIcon(Icon icon) {
		IconBorder newBorder = new IconBorder(0, icon.getIconWidth() + 4, 0, 0, icon);
		newBorder.setVerticalIconAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createCompoundBorder(oldBorder, newBorder));
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		// 图标大小自适应
		if (icon instanceof SvgIcon s) {
			int size = (int) (preferredSize.height * 0.75);
			s.setDimension(new Dimension(size, size));
		}
	}
}
