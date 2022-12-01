package com.element.ui.button;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.font.FontUtil;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class JideSplitButtonTest extends AbstractDemo {
	@Override
	public String getName() {
		return "JideSplitButtonTest";
	}

	@Override
	public String getDescription() {
		return "JideSplitButton是按钮和菜单的组合。按钮中间有一条线将按钮分成两部分。该位置之前的部分是一个按钮。\n" +
				"用户可以点击它并触发一个动作。该位置之后的部分是菜单。用户可以点击它来显示一个普通的菜单。";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		JideSplitButton b1 = new JideSplitButton("按钮1", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		b1.setButtonStyle(ButtonStyle.FLAT_STYLE);
		JideSplitButton b2 = new JideSplitButton("按钮2", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		b2.setFont((Font) FontUtil.getMenuFont(Toolkit.getDefaultToolkit(), UIManager.getDefaults()));
		b2.add(new JMenuItem("本地历史记录"));

		// 懒加载
		b1.setPopupMenuCustomizer(menu -> {
			// 删除所有菜单或设置PopupMenuCustomizer为null都可以解决问题
			b1.setPopupMenuCustomizer(null);
			// b1.removeAll();
			b1.add(new JMenuItem("显示上下文操作"));
			b1.add(new JMenuItem("复制"));
			b1.add(new JMenuItem("粘贴"));
			b1.add(new JMenuItem("剪切", IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY)));
			b1.add(b2);
		});

		p.add(b1);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new JideSplitButtonTest());
		});
	}
}