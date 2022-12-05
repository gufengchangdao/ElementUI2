package com.element.ui.label;

import com.element.color.ColorUtil;
import com.element.plaf.LookAndFeelFactory;
import com.element.ui.icons.IconsFactory;
import com.element.ui.layout.JideBoxLayout;
import com.element.ui.svg.icon.fill.SwordSvg;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

public class StyledLabelBuilderTest extends AbstractDemo {
	protected StyledLabel _label;
	private final String INITIAL_TEXT = "{Preview:bold} of {StyledLabel:f:red}";

	@Override
	public String getDescription() {
		return """
				StyledLabelBuilder is a helper class for StyledLabel.
				In this demo, we will show you how to use one annotated string to create StyledLabel with the help of StyledLabelBuilder.

				Demoed classes:"""
				+ StyledLabelBuilder.class.getName() + "\n" +
				StyledLabel.class.getName();
	}

	@Override
	public Component getOptionsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS, 2));

		final JComboBox<String> horizontalAlignment = new JComboBox<>(new String[]{"Left", "Center", "Right", "Leading", "Trailing"});
		horizontalAlignment.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Object item = horizontalAlignment.getSelectedItem();
				if ("Left".equals(item)) {
					_label.setHorizontalAlignment(SwingConstants.LEFT);
				} else if ("Center".equals(item)) {
					_label.setHorizontalAlignment(SwingConstants.CENTER);
				} else if ("Right".equals(item)) {
					_label.setHorizontalAlignment(SwingConstants.RIGHT);
				} else if ("Leading".equals(item)) {
					_label.setHorizontalAlignment(SwingConstants.LEADING);
				} else if ("Trailing".equals(item)) {
					_label.setHorizontalAlignment(SwingConstants.TRAILING);
				}
			}
		});
		horizontalAlignment.setSelectedIndex(1);
		panel.add(new JLabel("Horizontal Alignment"));
		panel.add(horizontalAlignment);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		final JComboBox<String> verticalAlignment = new JComboBox<>(new String[]{"Top", "Center", "Bottom"});
		verticalAlignment.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Object item = verticalAlignment.getSelectedItem();
				if ("Top".equals(item)) {
					_label.setVerticalAlignment(SwingConstants.TOP);
				} else if ("Center".equals(item)) {
					_label.setVerticalAlignment(SwingConstants.CENTER);
				} else if ("Bottom".equals(item)) {
					_label.setVerticalAlignment(SwingConstants.BOTTOM);
				}
			}
		});
		panel.add(new JLabel("Vertical Alignment"));
		verticalAlignment.setSelectedIndex(1);
		panel.add(verticalAlignment);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		final JComboBox<String> horizontalTextPosition = new JComboBox<>(new String[]{"Left", "Center", "Right", "Leading", "Trailing"});
		horizontalTextPosition.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Object item = horizontalTextPosition.getSelectedItem();
				if ("Left".equals(item)) {
					_label.setHorizontalTextPosition(SwingConstants.LEFT);
				} else if ("Center".equals(item)) {
					_label.setHorizontalTextPosition(SwingConstants.CENTER);
				} else if ("Right".equals(item)) {
					_label.setHorizontalTextPosition(SwingConstants.RIGHT);
				} else if ("Leading".equals(item)) {
					_label.setHorizontalTextPosition(SwingConstants.LEADING);
				} else if ("Trailing".equals(item)) {
					_label.setHorizontalTextPosition(SwingConstants.TRAILING);
				}
			}
		});
		horizontalTextPosition.setSelectedItem("Right");
		panel.add(new JLabel("Horizontal Text Position"));
		panel.add(horizontalTextPosition);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		final JComboBox<String> verticalTextPosition = new JComboBox<>(new String[]{"Top", "Center", "Bottom"});
		verticalTextPosition.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				Object item = verticalTextPosition.getSelectedItem();
				if ("Top".equals(item)) {
					_label.setVerticalTextPosition(SwingConstants.TOP);
				} else if ("Center".equals(item)) {
					_label.setVerticalTextPosition(SwingConstants.CENTER);
				} else if ("Bottom".equals(item)) {
					_label.setVerticalTextPosition(SwingConstants.BOTTOM);
				}
			}
		});
		panel.add(new JLabel("Vertical Text Position"));
		panel.add(verticalTextPosition);
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);

		final JCheckBox checkBox = new JCheckBox("Show Icon");
		checkBox.setMnemonic('S');
		checkBox.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				_label.setIcon(IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
			} else {
				_label.setIcon(null);
			}
		});
		checkBox.setSelected(true);
		panel.add(checkBox);

		return panel;
	}

	public Component getDemoPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(createLabelsPanel());
		panel.add(Box.createVerticalStrut(6));
		panel.add(createInputPanel());
		panel.add(Box.createVerticalStrut(6), JideBoxLayout.VARY);
		panel.add(createHelpPanel());
		panel.add(Box.createGlue(), JideBoxLayout.VARY);
		return panel;
	}

	private JComponent createLabelsPanel() {
		_label = new StyledLabel();
		_label.setIcon(IconsFactory.getSvgIcon(SwordSvg.class, 16, 16, ColorUtil.PRIMARY));
		StyledLabelBuilder.setStyledText(_label, INITIAL_TEXT);
		_label.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1, 10, 10));
		panel.add(_label);
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(
						BorderFactory.createLineBorder(Color.gray, 1, true),
						" Preview ", TitledBorder.CENTER, TitledBorder.CENTER),
				BorderFactory.createEmptyBorder(2, 4, 8, 4)));

		panel.setPreferredSize(new Dimension(200, 100));
		return panel;
	}

	private JComponent createHelpPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.gray, 1, true), " Help ",
				TitledBorder.CENTER, TitledBorder.CENTER), BorderFactory.createEmptyBorder(2, 4, 8, 4)));

		StyledLabel titleLabel = StyledLabelBuilder.createStyledLabel("{Font styles\\::b, f:blue}");
		titleLabel.setFont(titleLabel.getFont().deriveFont(12.0f));
		panel.add(titleLabel);
		panel.add(StyledLabelBuilder.createStyledLabel("   - {plain:b} or {p:b}, i.e. \\{plain text:p} => {plain text:p}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {bold:b} or {b:b}, i.e. \\{bold text:b} => {bold text:b}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {italic:b} or {i:b}, i.e. \\{italic text:i} => {italic text:i}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {bolditalic:b} or {bi:b}, i.e. \\{bold and italic text:bi} => {bold and italic:bi}"));

		titleLabel = StyledLabelBuilder.createStyledLabel("{Line styles\\::b,f:blue}");
		titleLabel.setFont(titleLabel.getFont().deriveFont(12.0f));
		panel.add(titleLabel);
		panel.add(StyledLabelBuilder.createStyledLabel("   - {strike:b} or {s:b}, i.e. \\{strikethrough:s} => {strikethrough:s}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {doublestrike:b} or {ds:b}, i.e. \\{double strikethrough:ds} => {double strikethrough:ds}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {waved:b} or {w:b}, i.e. \\{waved:w} => {waved:w}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {underlined:b} or {u:b}, i.e. \\{underlined:u} => {underlined:u}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {dotted:b} or {d:b}, i.e. \\{dotted:d} => {dotted:d}"));
		titleLabel = StyledLabelBuilder.createStyledLabel("{Text Position\\::b,f:blue}");
		titleLabel.setFont(titleLabel.getFont().deriveFont(12.0f));
		panel.add(titleLabel);
		panel.add(StyledLabelBuilder.createStyledLabel("   - {superscript:b} or {sp:b}, i.e. Java\\{TM:sp} => Java{TM:sp}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {subscript:b} or {sb:b}, i.e. CO\\{2:sb} => CO{2:sb}"));

		titleLabel = StyledLabelBuilder.createStyledLabel("{Using Colors\\::b,f:blue} using {f:b} for font color, {l:b} for line color and {b:b} for background color");
		titleLabel.setFont(titleLabel.getFont().deriveFont(12.0f));
		panel.add(titleLabel);
		panel.add(StyledLabelBuilder.createStyledLabel("   - {f\\::b} plus color name defined in class Color, i.e. \\{red text:f:red} => {red text:f:red}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {l\\::b}: plus color name defined in class Color, i.e. \\{red underline:u, l:red} => {red underline:u, l:red}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {b\\::b}: plus color name defined in class Color, i.e. \\{red background:b:red} => {red background:b:red}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {f\\::b} or {l\\::b} or {b\\::b}: plus #RRGGBB, i.e. \\{any color:f:#00AA55} => {any color:f:#00AA55}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {f\\::b} or {l\\::b} or {b\\::b}: plus #RGB as in CSS, i.e. \\{any color:f:#0A5} => {any color:f:#0A5}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - {f\\::b} or {l\\::b}or {b\\::b}: plus (R, G, B), i.e. \\{any line color:s, l:(0, 220, 128)}" +
				" or \\{any background color:b:(0, 120, 128)} => {any line color:s, l:(0, 220, 128)} or {any background color:b:(0, 120, 128)}"));

		titleLabel = StyledLabelBuilder.createStyledLabel("{Special characters\\::b, f:blue}");
		titleLabel.setFont(titleLabel.getFont().deriveFont(12.0f));
		panel.add(titleLabel);
		panel.add(StyledLabelBuilder.createStyledLabel("   - Special annotation characters {\\{:b} {\\}:b} {\\(:b} {\\):b} {\\#:b} {\\::b} {\\,:b} should be escaped by \"\\\\\" when they are used as regular text"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - i.e. \\{\\\\\\{brace\\\\\\}:b} => {\\{brace\\}:b}"));
		panel.add(StyledLabelBuilder.createStyledLabel("   - If you need multiple styles connect them with {,:b} (comma), i.e. \\{bold red text:f:red, b} => {bold red text:f:red, b}"));

		titleLabel = StyledLabelBuilder.createStyledLabel("{Global flags\\::b, f:blue}");
		titleLabel.setFont(titleLabel.getFont().deriveFont(12.0f));
		panel.add(titleLabel);
		panel.add(StyledLabelBuilder.createStyledLabel("   - The rows: you can use \"rows\", \"row\", or \"r\", followed by up to three parameters separated by \":\" which are preferred row count, minimum row count and maximum row counts respectively."));
		panel.add(StyledLabelBuilder.createStyledLabel("   - The preferred width: You can use \"preferredWidth\", \"width\", or \"w\""));
		panel.add(StyledLabelBuilder.createStyledLabel("   - Use \"@\" at the end of the string to start definition of global flags. i.e. \"this is a long text@rows\", \"this is a long text@r:2:1:4\". "));
		panel.add(StyledLabelBuilder.createStyledLabel("   - We may add more global flags in the future"));

// the following code builder to registered new styles and use it as annotation.
//        StyledLabelBuilder styledLabelBuilder = new
//                StyledLabelBuilder().register("NORMAL_STYLE", Font.PLAIN, Color.RED).
//                register("ERROR_STYLE", Font.PLAIN, Color.RED).
//                register("UNDERLINE_STYLE", Font.PLAIN, Color.BLUE,
//                        StyleRange.STYLE_UNDERLINED, Color.BLUE).
//                register("UNDERLINE_STYLE2", "UNDERLINE_STYLE,f:yellow").
//                register("STRIKE_THROUGH_STYLE", Font.PLAIN, Color.GRAY,
//                        StyleRange.STYLE_STRIKE_THROUGH, Color.RED);
//        StyledLabel label = new StyledLabel();
//        styledLabelBuilder.configure(label, "{text:UNDERLINE_STYLE} {text:UNDERLINE_STYLE2}");
//        panel.add(label);
		return panel;
	}

	private JComponent createInputPanel() {
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		panel.add(new JLabel("Annotated String: "), BorderLayout.BEFORE_LINE_BEGINS);
		final JTextField textField = new JTextField(INITIAL_TEXT);
		textField.setColumns(50);
		panel.add(textField, BorderLayout.CENTER);
		AbstractAction action = new AbstractAction("Update") {
			public void actionPerformed(ActionEvent e) {
				_label.clearStyleRanges();
				StyledLabelBuilder.setStyledText(_label, textField.getText());
			}
		};

		final JButton button = new JButton(action);
		button.setMnemonic('U');

		textField.addActionListener(e -> button.doClick());

		panel.add(button, BorderLayout.AFTER_LINE_ENDS);

		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.gray, 1, true),
				" Type in an annotated string and press enter to see the result ",
				TitledBorder.CENTER, TitledBorder.CENTER), BorderFactory.createEmptyBorder(2, 4, 8, 4)));
		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new StyledLabelBuilderTest());
		});
	}
}