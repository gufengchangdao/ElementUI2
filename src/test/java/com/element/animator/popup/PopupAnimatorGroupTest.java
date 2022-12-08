package com.element.animator.popup;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.button.ButtonFactory;
import com.element.ui.others.empty.EmptyComponent;
import com.element.ui.others.result.ResultFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class PopupAnimatorGroupTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() {
		// 设置玻璃窗格
		JPanel panel = new JPanel(null);
		panel.setOpaque(false);
		getFrame().setGlassPane(panel);

		PopupAnimatorGroup<EmptyComponent> group = new PopupAnimatorGroup<>(panel);

		// 设置组件及其task
		EmptyComponent result = ResultFactory.createSuccessResult("你成功了", ButtonFactory.createDefaultButton("返回", ColorUtil.PRIMARY));
		PopupAnimatorTask<EmptyComponent> task = group.createTask(result, new Point(0, 0), new Point(0, 50));
		task.setProcessComponent(emptyComponent -> System.out.println("淡入完成"));
		task.setDispose(emptyComponent -> System.out.println("淡出完成"));

		JButton b = new JButton("弹出");
		b.addActionListener(e -> {
			panel.setVisible(true);
			// 将task中组件添加到面板中并运行动画
			group.startAnimator(task);
		});

		return b;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			SwingTestUtil.setDefaultTimingSource();
			showAsFrame(new PopupAnimatorGroupTest());
		});
	}

}