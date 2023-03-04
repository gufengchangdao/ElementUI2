package com.element.ui.dialog;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.font.FontUtil;
import com.element.ui.icons.IconsFactory;
import com.element.ui.svg.icon.fill.ArrowFatLeftSvg;
import com.element.ui.svg.icon.fill.ArrowFatRightSvg;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class AbstractDialogPageTest extends AbstractDemo {

	@Override
	public String getDescription() {
		return """
				延迟组件的初始化，延迟构建意味着它将快速启动。
				例如把这些面板放在CardLayout布局下，只会在调用paint方法时才会进行组件的初始化""";
	}

	@Override
	public Component getDemoPanel() {
		JPanel p = new JPanel(new MigLayout("wrap 1, fill"));

		JPanel contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(400, 400));
		CardLayout cardLayout = new CardLayout();
		contentPanel.setLayout(cardLayout);
		Arrays.stream(getContentPanel()).forEach(contentPanel::add);

		ButtonPanel buttonPanel = new ButtonPanel();
		new JButton("下一个组件");
		JButton next = new JButton(IconsFactory.getSvgIcon(ArrowFatRightSvg.class, 16, 16, ColorUtil.PRIMARY));
		JButton last = new JButton(IconsFactory.getSvgIcon(ArrowFatLeftSvg.class, 16, 16, ColorUtil.PRIMARY));
		last.addActionListener(e -> cardLayout.previous(contentPanel));
		next.addActionListener(e -> cardLayout.next(contentPanel));

		buttonPanel.add(last, ButtonPanel.OTHER_BUTTON);
		buttonPanel.add(next, ButtonPanel.OTHER_BUTTON);
		p.add(contentPanel, "growx");
		p.add(buttonPanel, "growx");
		return p;
	}

	public static JPanel[] getContentPanel() {
		return IntStream.rangeClosed(1, 4).mapToObj(i -> {
			// page.setPreferredSize(new Dimension(400, 400));
			return new AbstractDialogPage("组件" + i,
					"崩坏3是米哈游旗下的一款手游", SwordSvg.of(16, 16)) {
				@Override
				public void lazyInitialize() {
					System.out.println("面板 " + i + " 初始化");
					setLayout(new MigLayout("wrap 1", "grow"));
					JLabel titleLabel = new JLabel(getTitle(), getIcon(), SwingConstants.LEFT);
					titleLabel.setFont(titleLabel.getFont().deriveFont((float) FontUtil.TITLE));
					add(titleLabel);

					JLabel descriptionLabel = new JLabel(getDescription());
					descriptionLabel.setFont(descriptionLabel.getFont().deriveFont((float) FontUtil.NORMAL));
					add(descriptionLabel);

					JTextArea area = new JTextArea();
					area.setLineWrap(true);
					Arrays.stream(DemoData.NAMES).reduce((s, s2) -> s + ", " + s2).ifPresent(area::setText);
					add(new JScrollPane(area), "growx, h 200::");
				}
			};
		}).toArray(AbstractDialogPage[]::new);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			SwingTestUtil.setDefaultTimingSource();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new AbstractDialogPageTest());
		});
	}
}