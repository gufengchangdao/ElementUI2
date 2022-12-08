package com.element.ui.link;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.layout.ColumnsLayoutTest;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class LinkButtonTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		Container p = new JPanel(new MigLayout("wrap 6"));

		p.add(new LinkButton("默认链接", null, ColorUtil.PRIMARY));
		p.add(new LinkButton("主要链接", ColorUtil.PRIMARY, null));
		p.add(new LinkButton("成功链接", ColorUtil.SUCCESS, null));
		p.add(new LinkButton("警告链接", ColorUtil.WARNING, null));
		p.add(new LinkButton("危险链接", ColorUtil.DANGER, null));
		p.add(new LinkButton("信息链接", ColorUtil.INFO, null));

		LinkButton b1 = new LinkButton("默认链接", null, ColorUtil.PRIMARY);
		LinkButton b2 = new LinkButton("默认链接", null, null, ColorUtil.PRIMARY, null, false);

		p.add(b1);
		p.add(b2, "wrap");
		System.out.println(p.getLayout());

		LinkButton b3 = new LinkButton("默认链接", null, ColorUtil.PRIMARY);
		b3.setEnabled(false);
		p.add(b3);

		p.add(new LinkButton("宝剑", SwordSvg.of(16, 16), null, ColorUtil.PRIMARY));

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new LinkButtonTest());
		});
	}
}