package com.element.ui.dialog;

import com.element.plaf.LookAndFeelFactory;
import com.element.font.FontUtil;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class MultiplePageDialogTest extends AbstractDemo {

	@Override
	public String getDescription() {
		return "展示MultiplePageDialog的部分功能演示，其他功能演示之后会补上";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout());
		MultiplePageDialog dialog = new MultiplePageDialog(getFrame(), "测试对话框", true);
		AbstractDialogPage[] contentPanel = getContentPanel();
		PageList pageList = new PageList();
		pageList.addAll(Arrays.stream(contentPanel).toList());
		dialog.setPageList(pageList);

		JButton b = new JButton("弹出");
		b.addActionListener(e -> {
			dialog.pack();
			// 由于布局在延迟方法中进行的，因此对话框无法拿到内容真正的首选大小，需要手动设置一下大小
			Dimension size = dialog.getPreferredSize();
			size.width += 300;
			dialog.setSize(size);
			// 居中
			dialog.setLocationRelativeTo(null);
			// 可见
			dialog.setVisible(true);
		});
		p.add(b);

		return p;
	}

	public static AbstractDialogPage[] getContentPanel() {
		return IntStream.rangeClosed(1, 4).mapToObj(i -> new AbstractDialogPage("组件" + i,
				"崩坏3是米哈游旗下的一款手游", SwordSvg.of(16, 16)) {
			@Override
			public void lazyInitialize() {
				System.out.println("面板 " + i + " 初始化");
				setLayout(new MigLayout("wrap 1", "grow"));
				JLabel titleLabel = new JLabel(getTitle(), getIcon(), SwingConstants.LEFT);
				titleLabel.setFont(titleLabel.getFont().deriveFont((float) FontUtil.TITLE));
				add(titleLabel);

				JLabel descriptionLabel = new JLabel(getDescription());
				descriptionLabel.setFont(descriptionLabel.getFont().deriveFont((float) FontUtil.TITLE));
				add(descriptionLabel);

				JTextArea area = new JTextArea();
				area.setLineWrap(true);
				Arrays.stream(DemoData.NAMES).reduce((s, s2) -> s + ", " + s2).ifPresent(area::setText);
				add(new JScrollPane(area), "growx, h 200::");

				JButton b = new JButton("使当前tab不可用");
				b.addActionListener(e -> {
					setPageEnabled(false);
				});
				add(b,BorderLayout.SOUTH);
			}
		}).toArray(AbstractDialogPage[]::new);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new MultiplePageDialogTest());
		});
	}
}