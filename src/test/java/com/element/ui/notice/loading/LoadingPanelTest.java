package com.element.ui.notice.loading;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.template.X2Component;
import com.element.ui.alert.AlertFactory;
import com.element.ui.others.result.ResultFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoadingPanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());

		// LoadingPanel<LoadingLabel> panel = new LoadingPanel<>(
		// 		new LoadingLabel(CrosshairSvg.of(48, 48), 700));
		LoadingPanel<CircleLoadingLabel> panel = new LoadingPanel<>(
				new CircleLoadingLabel(ColorUtil.PRIMARY, 3));
		panel.setPreferredSize(new Dimension(400, 400));
		JFrame frame = getFrame();
		frame.setGlassPane(panel);

		JButton b = new JButton("开启加载");
		b.addActionListener(e -> {
			//获取图像得在该面板加入面板之前，不然会把加载动画也印上去
			panel.flushBackground();
			frame.getGlassPane().setVisible(true);
		});
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.getGlassPane().setVisible(false);
			}
		});
		p.add(ResultFactory.createSuccessResult("成功文本", new JButton("返回")));
		p.add(AlertFactory.createSuccessAlert("这是一条警告", true, true, X2Component.GrowStyle.LEFT_GROW));
		p.add(new JTextField("输入框"));
		p.add(b);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new LoadingPanelTest());
		});
	}
}