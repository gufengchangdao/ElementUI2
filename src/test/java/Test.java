import com.element.color.ColorUtil;
import com.element.converter.ObjectConverterManager;
import com.element.ui.combobox.AutoCompletionComboBox;
import com.element.util.SwingTestUtil;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class Test {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JPanel p = SwingTestUtil.init(new MigLayout());
			ObjectConverterManager.initDefaultConverter();

			Color[] colors = {ColorUtil.WARNING, ColorUtil.PRIMARY, ColorUtil.INFO, ColorUtil.BORDER_LEVEL1};
			AutoCompletionComboBox<Color> autoCompletionComboBox = new AutoCompletionComboBox<>(colors);

			p.add(autoCompletionComboBox);
			SwingTestUtil.test();
		});
	}
}
