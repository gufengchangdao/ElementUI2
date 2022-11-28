import com.element.hints.FileIntelliHints;
import com.element.plaf.LookAndFeelFactory;
import demo.SwingTestUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			LookAndFeelFactory.installJideExtension();
			JPanel p = SwingTestUtil.init(new MigLayout("wrap 1"));

			JTextField pathTextField = new JTextField(30);
			FileIntelliHints intelliHints = new FileIntelliHints(pathTextField);
			intelliHints.setFolderOnly(true);
			p.add(pathTextField);

			SwingTestUtil.test();
		});
	}
}