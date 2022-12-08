package com.element.ui.others.result;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.button.ButtonFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ResultFactoryTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		Container p = new JPanel(new MigLayout("wrap 4", "grow, center"));

		p.add(ResultFactory.createSuccessResult("请根据提示进行操作",
				ButtonFactory.createDefaultButton("返回", ColorUtil.PRIMARY)));
		p.add(ResultFactory.createWarningResult("请根据提示进行操作",
				ButtonFactory.createDefaultButton("返回", ColorUtil.PRIMARY)));
		p.add(ResultFactory.createDangerResult("请根据提示进行操作",
				ButtonFactory.createDefaultButton("返回", ColorUtil.PRIMARY)));
		p.add(ResultFactory.createInfoResult("请根据提示进行操作",
				ButtonFactory.createDefaultButton("返回", ColorUtil.PRIMARY)));

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new ResultFactoryTest());
		});
	}
}