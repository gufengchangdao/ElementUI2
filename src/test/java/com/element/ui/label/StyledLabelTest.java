package com.element.ui.label;

import com.element.converter.ObjectConverterManager;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.pane.JideSplitPane;
import demo.AbstractDemo;
import demo.SwingTestUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.*;
import java.awt.*;

/**
 * 对支持设置字体类型和样式的四种组件的性能测试
 */
public class StyledLabelTest extends AbstractDemo {
	private final int COUNT = 100;
	private final double RATIO = 1000000.0;

	public StyledLabelTest() {
		ObjectConverterManager.initDefaultConverter();
	}

	@Override
	public String getName() {
		return "StyledLabel Demo (Performance Test)";
	}

	@Override
	public String getDescription() {
		return """
				StyledLabel 是一种特殊的 JLabel，可以显示不同颜色的文本并混合各种线条装饰。
				StyledLabel 提供的一些功能可以在 JLabel 中使用 html 代码实现。但是，与使用 html JLabel 相比，使用 StyledLabel 有很多优势。最重要的优势之一是性能。
				StyledLabel 非常简单，几乎和普通 JLabel 一样轻，因此根据我们的测试，StyledLabel 的性能比 html JLabel 好 20 到 40 倍。
				该演示旨在向您展示不同之处。它将创建 100 个纯文本 JLabel、100 个 StyledLabel、100 个 html JLabel。
				每个案例所用的时间显示在相应面板的顶部。在我们的测试机上，普通 JLabel 和 StyledLabel 使用的时间几乎相同（7 毫秒），并且都比 html 标签（使用 237 毫秒）快 20 到 40 倍。
				看到结果后，我猜你在代码中使用 html JLabel 时应该更加小心。""";
	}

	@Override
	public Component getDemoPanel() {
		JideSplitPane panel = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);

		new JTextPane(); // another warm up to load classes related to JTextPane

		panel.add(createLabelsPanel());
		panel.add(createHtmlLabelsPanel());
		panel.add(createTextPanesPanel());
		panel.add(createStyledLabelsPanel());
		return panel;
	}

	private JComponent createStyledLabelsPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 1));

		String text = "Bold Italic Underlined";
		new StyledLabel(text);
		// warm up to make sure classes are loaded. Otherwise the first JLabel test case will take longer time to complete as it has to load all the new classes

		// Creates a StyledLabel to warn up so that we don't include class loading time into the performance test.
		// This is the same for all three cases.
		StyledLabel label = new StyledLabel(text);
		// we could pub the creation of StyleRange[] outside the loop.
		// But to make the comparison fair, we kept it inside.
		label.setStyleRanges(new StyleRange[]{
				new StyleRange(0, 4, Font.BOLD),
				new StyleRange(5, 6, Font.ITALIC),
				new StyleRange(12, 10, Font.PLAIN, StyleRange.STYLE_UNDERLINED)
		});

		long start = System.nanoTime();
		for (int i = 0; i < COUNT; i++) {
			label = new StyledLabel(text);
			// we could pub the creation of StyleRange[] outside the loop.
			// But to make the comparison fair, we kept it inside.
			label.setStyleRanges(new StyleRange[]{
					new StyleRange(0, 4, Font.BOLD),
					new StyleRange(5, 6, Font.ITALIC),
					new StyleRange(12, 10, Font.PLAIN, StyleRange.STYLE_UNDERLINED)
			});
			panel.add(label);
		}
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.gray, 1, true),
				" StyledLabel Examples - use " + ObjectConverterManager.toString((System.nanoTime() - start) / RATIO) + " ms ",
				TitledBorder.CENTER, TitledBorder.CENTER, null, Color.RED), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
		return panel;
	}

	private JComponent createHtmlLabelsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 10, 1));

		String text = "<HTML><B>Bold</B> <I>Italic</I> <U>Underlined</U></HTML>";
		new JLabel(text); // another warm up to load classes related to HTML views
		long start = System.nanoTime();
		for (int i = 0; i < COUNT; i++) {
			JLabel label = new JLabel(text);
			panel.add(label);
		}
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.gray, 1, true),
				" JLabel (HTML) Examples - use " + ObjectConverterManager.toString((System.nanoTime() - start) / RATIO) + " ms ",
				TitledBorder.CENTER, TitledBorder.CENTER, null, Color.RED), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
		return panel;
	}

	private JComponent createTextPanesPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 10, 1));

		new JTextPane();

		long start = System.nanoTime();
		for (int i = 0; i < COUNT; i++) {
			StyledDocument document = new DefaultStyledDocument();

			SimpleAttributeSet bold = new SimpleAttributeSet();
			bold.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
			SimpleAttributeSet italic = new SimpleAttributeSet();
			italic.addAttribute(StyleConstants.CharacterConstants.Italic, Boolean.TRUE);
			SimpleAttributeSet underlined = new SimpleAttributeSet();
			underlined.addAttribute(StyleConstants.CharacterConstants.Underline, Boolean.TRUE);

			try {
				document.insertString(document.getLength(), "Bold ", bold);
				document.insertString(document.getLength(), "Italic ", italic);
				document.insertString(document.getLength(), "Underlined", underlined);
			} catch (BadLocationException badLocationException) {
				System.err.println("Bad insert");
			}

			JTextPane textPane = new JTextPane(document);
			textPane.setOpaque(false);
			panel.add(textPane);
		}
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.gray, 1, true),
				" JTextPane Examples - use " + ObjectConverterManager.toString((System.nanoTime() - start) / RATIO) + " ms ",
				TitledBorder.CENTER, TitledBorder.CENTER, null, Color.RED), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
		return panel;
	}

	private JComponent createLabelsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2, 10, 1));

		// Creates a StyledLabel to warn up so that we don't include class loading time into the performance test.
		// This is the same for all three cases.
		new JLabel("Bold Italic Underlined");

		long start = System.nanoTime();
		for (int i = 0; i < COUNT; i++) {
			JLabel label = new JLabel("Bold Italic Underlined");
			panel.add(label);
		}
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.gray, 1, true),
				" JLabel (Plain) Examples - use " + ObjectConverterManager.toString((System.nanoTime() - start) / RATIO) + " ms ",
				TitledBorder.CENTER, TitledBorder.CENTER, null, Color.RED), BorderFactory.createEmptyBorder(6, 4, 4, 4)));
		return panel;
	}

	static public void main(String[] s) {
		SwingUtilities.invokeLater(() -> {
			LookAndFeelFactory.installJideExtension();
			SwingTestUtil.loadSkin();
			showAsFrame(new StyledLabelTest());
		});
	}
}