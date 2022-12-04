package com.element.ui.combobox;

import com.element.color.ColorUtil;
import com.element.converter.ObjectConverterManager;
import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class AutoCompletionComboBoxTest extends AbstractDemo {

	@Override
	public String getDescription() {
		return """
             对于String类型，AutoCompletionComboBox可以很好的运行，但是对于非String类型有两种解决方案：
             1. 使用Converter提前将数据转为String类型，再对获取的String进行转换回来
             2. 同时设置渲染器和Item选择的监听器，前者是为了下拉列表中数据的展示，后者是因为菜单选择会调用元素的toString方法，需要重新设\
             置输入框的值""";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		Color[] colors = {ColorUtil.WARNING, ColorUtil.PRIMARY, ColorUtil.INFO, ColorUtil.BORDER_LEVEL1};
		AutoCompletionComboBox<Color> autoCompletionComboBox = new AutoCompletionComboBox<>(colors);
		// 初始化默认转换器，如果已经初始化了方法里不会再初始化的

		// 设置render，修改显示的字符串
		ListCellRenderer<? super Color> oldRender = autoCompletionComboBox.getRenderer();
		autoCompletionComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			Component c = oldRender.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (c instanceof JLabel)
				((JLabel) c).setText(ObjectConverterManager.toString(value));
			return c;
		});

		// 设置Item监听器，监听Item的选择
		// 我不知道JComboBox是在调用什么方法更新输入框的值的，只能在这里设置监听器，当值更新时重新设置输入框的值了
		autoCompletionComboBox.addItemListener(e -> {
			if (autoCompletionComboBox.getEditor().getEditorComponent() instanceof JTextField f)
				EventQueue.invokeLater(() -> f.setText(ObjectConverterManager.toString(e.getItem())));
		});

		p.add(autoCompletionComboBox);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			ObjectConverterManager.initDefaultConverter();
			showAsFrame(new AutoCompletionComboBoxTest());
		});
	}
}