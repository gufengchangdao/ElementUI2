package com.element.ui.label;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.font.FontUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class LabelFactoryTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());

		Font font = UIManager.getFont("Button.font").deriveFont((float) FontUtil.AUXILIARY_WORDS);
		p.add(LabelFactory.createLabel("书籍未拆封", font, ColorUtil.SUCCESS));
		p.add(LabelFactory.createLabel("书籍未拆封", font, ColorUtil.WARNING));
		p.add(LabelFactory.createLabel("书籍未拆封", font, ColorUtil.DANGER));
		p.add(LabelFactory.createLabel("书籍未拆封", font, ColorUtil.INFO), "wrap");

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new LabelFactoryTest());
		});
	}
}