import com.element.util.SwingTestUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class Test {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JPanel p = SwingTestUtil.init(new MigLayout());
			JButton b = new JButton("按钮");

			p.add(b);

			SwingTestUtil.test();
		});
	}

}
