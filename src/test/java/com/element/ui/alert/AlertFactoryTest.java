package com.element.ui.alert;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.template.X2Component;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static com.element.ui.alert.AlertFactory.*;

public class AlertFactoryTest extends AbstractDemo {

	@Override
	public String getName() {
		return "AlertFactoryTest";
	}

	@Override
	public String getDescription() {
		return "警告的便捷生成";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		p.add(createSuccessAlert("成功提示的文案", true, true, X2Component.GrowStyle.LEFT_GROW));
		p.add(createInfoAlert("消息提示的文案", true, true, X2Component.GrowStyle.LEFT_GROW));
		p.add(createWarningAlert("警告提示的文案", true, true, X2Component.GrowStyle.LEFT_GROW));
		p.add(createDangerAlert("错误提示的文案", true, true, X2Component.GrowStyle.LEFT_GROW));

		return p;
	}

	@Override
	public Class<?>[] getDemoSource() {
		return new Class[]{
				AlertFactory.class,
				AlertFactoryTest.class
		};
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AlertFactoryTest());
		});
	}
}