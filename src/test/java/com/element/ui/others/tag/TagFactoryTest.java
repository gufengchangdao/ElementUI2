package com.element.ui.others.tag;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.others.result.ResultFactoryTest;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static com.element.ui.others.tag.TagFactory.createDefaultTag;
import static com.element.ui.others.tag.TagFactory.createIconTag;

public class TagFactoryTest extends AbstractDemo {

	@Override
	public Component getDemoPanel() throws Exception {
		Container p = new JPanel(new MigLayout("wrap 2", "grow"));

		p.add(createDefaultTag("标签一", ColorUtil.PRIMARY), "right");
		p.add(createIconTag("Tag 组件提供除了默认值以外的三种尺寸", ColorUtil.PRIMARY));
		p.add(createDefaultTag("标签二", ColorUtil.WARNING), "right");
		p.add(createIconTag("Tag 组件提供除了默认值以外的三种尺寸", ColorUtil.WARNING));
		p.add(createDefaultTag("标签三", ColorUtil.SUCCESS), "right");
		p.add(createIconTag("Tag 组件提供除了默认值以外的三种尺寸", ColorUtil.SUCCESS));
		p.add(createDefaultTag("标签四", ColorUtil.DANGER), "right");
		p.add(createIconTag("Tag 组件提供除了默认值以外的三种尺寸", ColorUtil.DANGER));

		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new TagFactoryTest());
		});
	}
}