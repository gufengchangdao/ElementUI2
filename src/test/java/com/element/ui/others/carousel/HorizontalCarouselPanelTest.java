package com.element.ui.others.carousel;

import com.element.plaf.LookAndFeelFactory;
import com.element.util.ImageUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class HorizontalCarouselPanelTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		ArrayList<JLabel> list = new ArrayList<>();
		for (int i = 1; i <= 4; i++) {
			try (InputStream inputStream =
					     HorizontalCarouselPanelTest.class.getResourceAsStream("/com/element/ui/layout/" + i + ".jpg")) {
				BufferedImage image = ImageIO.read(inputStream);
				System.out.println(image.getWidth());
				image = ImageUtil.createScaledCompositeInstance(image, 500, 400);
				JLabel label = new JLabel(new ImageIcon(image));
				list.add(label);
			}
		}

		HorizontalCarouselPanel<JLabel> c = new HorizontalCarouselPanel<>(list, System.out::println);
		c.setBorder(BorderFactory.createLineBorder(Color.RED));

		return c;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new HorizontalCarouselPanelTest());
		});
	}
}