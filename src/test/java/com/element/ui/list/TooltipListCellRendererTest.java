package com.element.ui.list;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.tooltip.BalloonToolTip;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class TooltipListCellRendererTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		DefaultListModel<String> model = new DefaultListModel<>();
		model.addElement("ABC DEF GHI JKL MNO PQR STU VWX YZ");
		model.addElement("111");
		model.addElement("111222");


		return new JList<>(model) {
			@Override
			public JToolTip createToolTip() {
				// 气泡提示
				JToolTip tip = new BalloonToolTip();
				tip.setComponent(this);
				return tip;
			}

			@Override
			public void updateUI() {
				super.updateUI();
				// 带有提示的渲染器
				setCellRenderer(new TooltipListCellRenderer<>());
			}
		};
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TooltipListCellRendererTest());
		});
	}
}