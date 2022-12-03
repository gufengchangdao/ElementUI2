package com.element.ui.alert;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.icons.IconsFactory;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.util.GraphicsUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LightboxGlassPaneTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		ImageIcon icon = IconsFactory.getImageIcon(LightboxGlassPaneTest.class, "/img/beauty.jpg");
		BufferedImage img = GraphicsUtilities.createThumbnail((BufferedImage) icon.getImage(), 400);

		getFrame().getRootPane().setGlassPane(new LightboxGlassPane(img));
		getFrame().getRootPane().getGlassPane().setVisible(false);

		JButton button = new JButton("Open");
		button.addActionListener(e -> p.getRootPane().getGlassPane().setVisible(true));
		p.add(button);

		p.setPreferredSize(new Dimension(button.getPreferredSize().width + 50, 500));
		return p;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new LightboxGlassPaneTest());
		});
	}
}