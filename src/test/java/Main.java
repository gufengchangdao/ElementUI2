import com.element.util.JideSwingUtilities;
import com.element.util.SwingTestUtil;

import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JPanel p = SwingTestUtil.init(new FlowLayout());

			JPanel panel = new JPanel();
			// JideSwingUtilities.setOpaqueRecursively(panel, true);
			JButton b = new JButton("改变");
			b.addActionListener(e -> {
				System.out.println(panel.isOpaque());
				panel.setOpaque(false);
				System.out.println(panel.isOpaque());
			});
			p.add(panel);
			p.add(b);

			SwingTestUtil.test();
		});
	}
}