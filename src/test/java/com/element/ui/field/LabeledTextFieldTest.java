package com.element.ui.field;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.radiance.common.api.icon.SvgIcon;
import com.element.ui.button.ButtonFactory;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.BrowserLauncherUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class LabeledTextFieldTest extends AbstractDemo {

	@Override
	public String getDescription() {
		return """
				LabeledTextField可以直接使用，直接使用支持左侧label和输入框，其他功能默认不开启。
				因此建议实现该类的一些定制方法，LabeledTextField会为开发者定制内容放到合适的位置。""";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		SvgIcon icon = IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY);
		MyLabeledTextField field = new MyLabeledTextField(icon, "URL");
		field.setText("https://wallhaven.cc/");
		p.add(field);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new LabeledTextFieldTest());
		});
	}

	static class MyLabeledTextField extends LabeledTextField {
		public MyLabeledTextField(Icon icon, String labelText) {
			super(icon, labelText);
		}

		// 定制右侧按钮
		@Override
		protected AbstractButton createButton() {
			JButton b = ButtonFactory.createDefaultButton("访问", ColorUtil.PRIMARY);
			b.addActionListener(e -> BrowserLauncherUtil.openUrl(getText()));
			return b;
		}

		// 定制提示文本
		@Override
		public String getHintText() {
			return "输出正确的URL";
		}

		// 定制菜单
		@Override
		protected void customizePopupMenu(JPopupMenu menu) {
			menu.add(new JMenuItem("复制"));
			menu.add(new JMenuItem("粘贴"));
			menu.add(new JMenuItem("剪切"));
		}

		// 结合输入框定制菜单
		@Override
		public PopupMenuCustomizer getPopupMenuCustomizer() {
			return (field, menu) -> {

			};
		}
	}
}