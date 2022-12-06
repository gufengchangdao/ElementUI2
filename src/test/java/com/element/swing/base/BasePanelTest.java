package com.element.swing.base;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.util.SwingTestUtil;
import com.element.util.UIUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class BasePanelTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1"));

		BasePanel basePanel1 = new BasePanel();
		basePanel1.setGradientPaint(ColorUtil.DANGER,ColorUtil.SUCCESS,false);

		basePanel1.setPreferredSize(new Dimension(200, 200));
		p.add(basePanel1);

		BasePanel basePanel2 = new BasePanel();
		basePanel2.setBackgroundPainter((g, object, width, height) -> {
			Dimension s = basePanel2.getPreferredSize();
			g.setPaint(new LinearGradientPaint(0, 0, s.width, 0,
					new float[]{0, .5f}, new Color[]{ColorUtil.DANGER, ColorUtil.SUCCESS}));
			Object[] oldRender = UIUtil.setRenderingHints(g);
			g.fillRoundRect(0, 0, s.width, s.height, 8, 8);
			UIUtil.resetRenderingHints(g,oldRender);
		});

		basePanel2.setPreferredSize(new Dimension(200, 200));
		p.add(basePanel2);

		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new BasePanelTest());
		});
	}
}