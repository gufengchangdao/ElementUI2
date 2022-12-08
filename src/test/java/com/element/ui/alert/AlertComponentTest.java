package com.element.ui.alert;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.template.X2Component;
import com.element.ui.svg.icon.fill.CheckCircleSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class AlertComponentTest extends AbstractDemo {
	@Override
	public String getName() {
		return "AlertComponentTest";
	}

	@Override
	public String getDescription() {
		return """
				<HTML>
				警告组件
								
				用于页面中展示重要的提示信息
								
				支持功能：
				1. 左侧图标、文本、按钮、颜色、边距
				2. 内部组件大小自适应
				3. 点击关闭按钮移除组件
				""";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		AlertComponent alert = new AlertComponent(CheckCircleSvg.of(16, 16),
				"成功提示的文案",
				// """
				// 		<html>
				// 		<b>带辅助性文字介绍</b>
				// 		<p>这是一句绕口令：黑灰化肥会挥发发灰黑化肥挥发；灰黑化肥会挥发发黑灰化肥发挥。 黑灰化肥会挥发发灰黑化肥黑灰挥发化为灰……</p>
				// 		</html>""",
				ColorUtil.SUCCESS,
				true,
				X2Component.GrowStyle.LEFT_GROW,
				new Insets(8, 16, 8, 16));
		alert.registerClose(null);
		// alert.unregisterClose();
		p.add(alert);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AlertComponentTest());
		});
	}
}