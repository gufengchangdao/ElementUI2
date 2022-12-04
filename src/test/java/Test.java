import com.element.color.ColorUtil;
import com.element.util.SwingTestUtil;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXButton;

import javax.swing.*;
import java.awt.*;

public class Test {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JPanel p = SwingTestUtil.init(new MigLayout());


			SwingTestUtil.test();
		});
	}
}
