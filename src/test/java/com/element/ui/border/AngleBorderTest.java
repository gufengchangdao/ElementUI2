package com.element.ui.border;

import com.element.animator.popup.PopupAnimatorGroupTest;
import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.swing.SwingPosition;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class AngleBorderTest extends AbstractDemo {
	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());

		JLabel label = new JLabel("这是文本");
		label.setPreferredSize(new Dimension(200, 200));
		label.setOpaque(true);
		label.setBackground(ColorUtil.SUCCESS.brighter());

		AngleBorder border = new AngleBorder(SwingConstants.SOUTH_EAST, ColorUtil.SUCCESS, 20, 3, new Point(0, 0));

		JComponent c = new JComponent() {
			{
				setLayout(null);
				Dimension s = label.getPreferredSize();
				Insets insets = border.getBorderInsets(this);

				// 用于展示，这里写死
				// label.setBounds(insets.left, insets.top, s.width, s.height);
				label.setBounds(20, 20, s.width, s.height);
				add(label);

				// s.width += insets.left + insets.right;
				// s.height += insets.top + insets.bottom;
				s.width += 40;
				s.height += 40;
				setPreferredSize(s);
				setBorder(border);
			}
		};

		ThreadLocalRandom random = ThreadLocalRandom.current();
		JButton b = new JButton("改变方位");
		b.addActionListener(e -> {
			int i = random.nextInt(1, 12);
			System.out.println("方向为 " + i);
			c.setBorder(new AngleBorder(i,
					ColorUtil.SUCCESS, 20, 3, new Point(0, 0)));
		});

		p.add(c);
		p.add(b);

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AngleBorderTest());
		});
	}
}