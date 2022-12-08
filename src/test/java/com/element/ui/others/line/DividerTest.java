package com.element.ui.others.line;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.collapse.JXTaskPaneTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class DividerTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		JPanel p = new JPanel(new MigLayout());

		Divider c1 = new Divider(new JLabel("少年包青天", SwingConstants.LEFT), 0.25f, false, 300);

		JXLabel label = new JXLabel("MY TEXT");
		label.setTextRotation(3 * Math.PI / 2);
		// label.setTextRotation(Math.PI/2);
		Divider c2 = new Divider(label, 0.25f, false, 300);

		p.add(c1);
		p.add(c2);
		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new DividerTest());
		});
	}
}