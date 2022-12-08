package com.element.ui.notice.message;

import com.element.animator.popup.PopupAnimatorTask;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.template.X2Component;
import com.element.ui.alert.AlertComponent;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MessageFactoryTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout("", "grow,center"));
		p.setPreferredSize(new Dimension(200, 400));

		JLayeredPane panel = new JLayeredPane();
		getFrame().setGlassPane(panel);

		MessageFactory messageFactory = new MessageFactory(panel);

		JButton button = new JButton("弹出");
		button.addActionListener(e -> {
			PopupAnimatorTask<AlertComponent> task = messageFactory.openSuccessMessage("你成功了！", true, X2Component.GrowStyle.LEFT_GROW,
					10, true, true);
			messageFactory.openInfoMessage("你成功了！", true, X2Component.GrowStyle.LEFT_GROW,
					50, true, true);
			messageFactory.openDangerMessage("你成功了！", true, X2Component.GrowStyle.LEFT_GROW,
					90, true, true);
			messageFactory.openWarningMessage("你成功了！", true, X2Component.GrowStyle.LEFT_GROW,
					130, true, false);
			panel.setVisible(true);
		});
		p.add(button);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new MessageFactoryTest());
		});
	}
}