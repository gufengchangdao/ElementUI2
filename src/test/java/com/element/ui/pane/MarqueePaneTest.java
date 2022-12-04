package com.element.ui.pane;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.border.PartialEtchedBorder;
import com.element.ui.border.PartialSide;
import com.element.ui.label.MultilineLabel;
import com.element.ui.label.StyleRange;
import com.element.ui.label.StyledLabel;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MarqueePaneTest extends AbstractDemo {
	MarqueePane _horizonMarqueeLeft;
	MarqueePane _verticalMarqueeUp;
	MarqueePane _verticalMarqueeDown;

	@Override
	public String getName() {
		return "MarqueePane Demo";
	}

	@Override
	public String getDescription() {
		return """
				MarqueePane is a subclass of JScrollPane to display components with a limited space by rolling it left and right, up and down.
				Demoed classes:
				com.jidesoft.swing.MarqueePane""";
	}

	@Override
	public Component getOptionsPanel() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
		JCheckBox freezeCheckBox = new JCheckBox("Freeze Auto Scrolling");
		freezeCheckBox.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				_horizonMarqueeLeft.stopAutoScrolling();
				_verticalMarqueeUp.stopAutoScrolling();
				_verticalMarqueeDown.stopAutoScrolling();
			} else {
				_horizonMarqueeLeft.startAutoScrolling();
				_verticalMarqueeUp.startAutoScrolling();
				_verticalMarqueeDown.startAutoScrolling();
			}
		});
		panel.add(freezeCheckBox);
		return panel;
	}

	public Component getDemoPanel() {
		StyledLabel styledLabel = new StyledLabel();
		customizeStyledLabel(styledLabel);

		MarqueePane horizonMarqueeLeft = new MarqueePane(styledLabel);
		horizonMarqueeLeft.setPreferredSize(new Dimension(250, 40));
		horizonMarqueeLeft.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Scroll Left", TitledBorder.LEADING, TitledBorder.ABOVE_TOP),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		JPanel demoPanel = new JPanel();
		demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));
		demoPanel.add(horizonMarqueeLeft, BorderLayout.BEFORE_FIRST_LINE);

		JTable table = new JTable(new String[][]{
				{"A1", "B1", "C1", "D1"}, {"A2", "B2", "C2", "D2"}, {"A3", "B3", "C3", "D3"}, {"A4", "B4", "C4", "D4"},
				{"A5", "B5", "C5", "D5"}, {"A6", "B6", "C6", "D6"}, {"A7", "B7", "C7", "D7"}, {"A8", "B8", "C8", "D8"},
				{"A9", "B9", "C9", "D9"}
		}, new String[]{"name", "age", "salary", "brith"});

		MultilineLabel textArea = new MultilineLabel();
		textArea.setText("""
				Obama welcomes bill to regulate tobacco\s
				Fake Rockefeller found guilty of kidnapping\s
				Al Qaeda fighters relocating, officials say\s
				Navarrette: Haters looking for scapegoats\s
				Avlon: 'Wingnuts' spread hate of Obama, Jews\s
				Ticker: Palin knocks 'perverted' Letterman\s
				Spokesman: Chastity Bono changing gender\s
				iReport.com: Share stories of gender change\s
				Robin Meade: Packing for presidential skydive\s
				WLUK: Girl gets excuse note from Obama\s
				Woman gives up home, car to help kids\s
				9-month-old snatched from home \s
				WPLG: Cat killings becoming more violent\s
				Cargo containers become beautiful homes\s
				Fortune: Dare you ask for a raise now?\s
				Truck loses load of cash, causes car jam \s
				Flying fish smack boater in head  \s
				Dog eats bag of pot, gets high""");

		System.out.println(textArea.getFont().getSize());
		MarqueePane verticalMarqueeUp = new MarqueePane(textArea);
		verticalMarqueeUp.setScrollDirection(MarqueePane.SCROLL_DIRECTION_UP);
		verticalMarqueeUp.setPreferredSize(new Dimension((int) horizonMarqueeLeft.getPreferredSize().getWidth(), 30));
		verticalMarqueeUp.setScrollAmount(1);
		verticalMarqueeUp.setStayPosition(17);
		verticalMarqueeUp.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Scroll Up", TitledBorder.LEADING, TitledBorder.ABOVE_TOP),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		MarqueePane verticalMarqueeDown = new MarqueePane(table);
		verticalMarqueeDown.setScrollDirection(MarqueePane.SCROLL_DIRECTION_DOWN);
		verticalMarqueeDown.setScrollDelay(200);
		verticalMarqueeDown.setStayDelay(1000);
		verticalMarqueeDown.setPreferredSize(new Dimension((int) horizonMarqueeLeft.getPreferredSize().getWidth(), 150));
		verticalMarqueeDown.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Scroll Down", TitledBorder.LEADING, TitledBorder.ABOVE_TOP),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));

		demoPanel.add(verticalMarqueeUp, BorderLayout.CENTER);
		demoPanel.add(verticalMarqueeDown, BorderLayout.AFTER_LAST_LINE);
		_horizonMarqueeLeft = horizonMarqueeLeft;
		_verticalMarqueeUp = verticalMarqueeUp;
		_verticalMarqueeDown = verticalMarqueeDown;
		return demoPanel;
	}

	private void customizeStyledLabel(StyledLabel styledLabel) {
		styledLabel.setText("GOOG   429.11   -6.51          DIA   87.64   -0.1          FXI   39.19   +1.12          GLD   93.62   -0.21          USO   39   +0.81          MSFT   22.25   +0.17");
		styledLabel.setForeground(Color.WHITE);
		int[] steps = new int[]{16, 5, 24, 4, 24, 5, 24, 5, 21, 5, 25, 5};
		int index = 0;
		for (int i = 0; i < steps.length; i++) {
			if (i % 2 == 0) {
				styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.WHITE, Color.BLACK, 0, Color.WHITE));
			} else {
				if (styledLabel.getText().charAt(index) == '-') {
					styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.RED, Color.BLACK, 0, Color.WHITE));
				} else {
					styledLabel.addStyleRange(new StyleRange(index, steps[i], Font.PLAIN, Color.GREEN, Color.BLACK, 0, Color.WHITE));
				}
			}
			index += steps[i];
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new MarqueePaneTest());
		});
	}
}