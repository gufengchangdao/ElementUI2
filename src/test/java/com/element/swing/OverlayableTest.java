package com.element.swing;

import com.element.plaf.LookAndFeelFactory;
import com.element.swing.overlay.DefaultOverlayable;
import com.element.swing.overlay.Overlayable;
import com.element.swing.overlay.OverlayableIconsFactory;
import com.element.ui.area.OverlayTextArea;
import com.element.ui.button.JideToggleButton;
import com.element.ui.button.OverlayRadioButton;
import com.element.ui.checkbox.OverlayCheckBox;
import com.element.ui.combobox.OverlayComboBox;
import com.element.ui.dialog.ButtonPanel;
import com.element.ui.field.OverlayTextField;
import com.element.ui.label.StyledLabelBuilder;
import com.element.ui.layout.JideBorderLayout;
import com.element.ui.layout.JideBoxLayout;
import com.element.ui.pane.JideSplitPane;
import com.element.ui.panel.InfiniteProgressPanel;
import com.element.ui.popup.JidePopup;
import com.element.util.OverlayableUtil;
import com.element.util.SwingTestUtil;
import com.element.util.WrapperUtil;
import demo.AbstractDemo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class OverlayableTest extends AbstractDemo {
	private JPanel _contentPanel;
	private Thread _thread1;
	private Thread _thread2;

	@Override
	public String getName() {
		return "Overlayable Demo";
	}

	@Override
	public String getDescription() {
		return """
				可叠加特性是将一个组件放在指定位置的另一个组件之上。原理是将原组件和叠加在它上面的组件放到创建的面板中进行维护，
				DefaultOverlayable就是这样一个面板，提供了许多方法来修改样式。除此之外原组件必须重写repaint方法，以便在原组件重绘时提醒
				它上面的组件也进行重绘。
								
				它有很多用途：
				1.提供有关如何使用组件的提示，尤其是当组件内容为空时。
				2. 提供进度指示器。
				3. 在不影响现有布局的情况下，在组件旁边提供状态指示器。
				演示类：
				com.jidesoft.swing.DefaultOverlayable
				com.jidesoft.swing.Overlayable""";
	}

	private final List<Overlayable> overlayables = new ArrayList<>();

	public Component getDemoPanel() {
		// 组件覆盖
		JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
		JideSplitPane pane1 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
		JideSplitPane pane2 = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
		panel.add(pane1);
		panel.add(pane2);

		pane1.add(createTextArea());
		pane1.add(createTable());

		pane2.add(createLoadingTextArea1());
		pane2.add(createLoadingTextArea2());

		JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
		contentPanel.add(panel);

		// 图标覆盖
		contentPanel.add(createMiscPanel(), BorderLayout.AFTER_LAST_LINE);

		_contentPanel = contentPanel;
		return contentPanel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new OverlayableTest());
		});
	}

	private static JPanel createTitledPanel(String title, Component component) {
		JPanel panel = new JPanel(new BorderLayout(4, 4));
		panel.add(component, BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createTitledBorder(title));
		return panel;
	}

	protected JPanel createTextArea() {
		final JTextArea textArea = new OverlayTextArea();
		textArea.setColumns(50);
		textArea.setRows(10);

		final DefaultOverlayable overlayTextArea = new DefaultOverlayable(new JScrollPane(textArea));
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				if (textArea.getDocument().getLength() > 0) {
					// 覆盖组件不可见
					overlayTextArea.setOverlayVisible(false);
				}
			}

			public void removeUpdate(DocumentEvent e) {
				if (textArea.getDocument().getLength() == 0)
					overlayTextArea.setOverlayVisible(true);
			}

			public void changedUpdate(DocumentEvent e) {
			}
		});
		textArea.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				overlayTextArea.setOverlayVisible(false);
			}

			public void focusLost(FocusEvent e) {
				overlayTextArea.setOverlayVisible(textArea.getDocument().getLength() == 0);
			}
		});
		// 添加覆盖组件
		overlayTextArea.addOverlayComponent(StyledLabelBuilder.createStyledLabel("{Enter description here:f:gray}"));
		return createTitledPanel("Overlayable JTextArea:", overlayTextArea);
	}

	protected JPanel createTable() {
		final TableModel tableModel = new DefaultTableModel(new String[]{"Symbol", "Name", "Last", "Change", "Volume"}, 0);
		final JTable table = new JTable(tableModel) {
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(300, 100);
			}
		};

		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);

		final DefaultOverlayable overlayTable = new DefaultOverlayable(new JScrollPane(table));
		table.getParent().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					((DefaultTableModel) table.getModel()).addRow(new String[]{"QQQQ", "QQQQ", "100.00", "10.0"});
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					((DefaultTableModel) table.getModel()).removeRow(table.getSelectedRow());
				}
			}
		});
		table.getModel().addTableModelListener(e -> overlayTable.setOverlayVisible(table.getModel().getRowCount() == 0));
		overlayTable.addOverlayComponent(StyledLabelBuilder.createStyledLabel("{Double click to insert a row:f:gray}"));

		return createTitledPanel("Overlayable JTable:", overlayTable);
	}

	protected JPanel createLoadingTextArea1() {
		final JTextArea textArea = new OverlayTextArea();
		textArea.setColumns(50);
		textArea.setRows(10);
		textArea.setWrapStyleWord(false);
		textArea.setLineWrap(true);

		final DefaultOverlayable overlayTextArea = new DefaultOverlayable(new JScrollPane(textArea));

		final InfiniteProgressPanel progressPanel = new InfiniteProgressPanel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(20, 20);
			}
		};
		overlayTextArea.addOverlayComponent(progressPanel);
		progressPanel.stop();
		overlayTextArea.setOverlayVisible(false);

		ButtonPanel buttonPanel = new ButtonPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		buttonPanel.addButton(new JButton(new AbstractAction("Start") {
			public void actionPerformed(ActionEvent e) {
				if (_thread1 == null || !_thread1.isAlive()) {
					_thread1 = createThread(progressPanel, textArea);
					_thread1.start();
					progressPanel.start();
				}
			}
		}));
		buttonPanel.addButton(new JButton(new AbstractAction("Stop") {
			public void actionPerformed(ActionEvent e) {
				if (_thread1 != null) {
					_thread1.interrupt();
					_thread1 = null;
					progressPanel.stop();
				}
			}
		}));

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(overlayTextArea);
		panel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
		return createTitledPanel("Overlayable JTextArea (Indeterminate Loading Demo):", panel);
	}

	protected JPanel createLoadingTextArea2() {
		final JTextArea textArea = new OverlayTextArea();
		textArea.setColumns(40);
		textArea.setRows(10);
		textArea.setWrapStyleWord(false);
		textArea.setLineWrap(true);

		final DefaultOverlayable overlayTextArea = new DefaultOverlayable(new JScrollPane(textArea));

		final JProgressBar progressBar = new JProgressBar(0, 100);
		overlayTextArea.addOverlayComponent(progressBar);
		overlayTextArea.setOverlayVisible(false);

		ButtonPanel buttonPanel = new ButtonPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		buttonPanel.addButton(new JButton(new AbstractAction("Start") {
			public void actionPerformed(ActionEvent e) {
				if (_thread2 == null || !_thread2.isAlive()) {
					_thread2 = createThread(progressBar, textArea);
					_thread2.start();
					progressBar.setValue(0);
				}
			}
		}));
		buttonPanel.addButton(new JButton(new AbstractAction("Stop") {
			public void actionPerformed(ActionEvent e) {
				if (_thread2 != null) {
					_thread2.interrupt();
					_thread2 = null;
				}
			}
		}));

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(overlayTextArea);
		panel.add(buttonPanel, BorderLayout.AFTER_LAST_LINE);
		return createTitledPanel("Overlayable JTextArea (Determinate Loading Demo):", panel);
	}

	@SuppressWarnings({"UnusedDeclaration"})
	private Thread createThread(final InfiniteProgressPanel progressPanel, final JTextArea textArea) {
		return new Thread(() -> {
			Overlayable overlayable = OverlayableUtil.getOverlayable(textArea);
			if (overlayable != null) {
				overlayable.setOverlayVisible(true);
			}
			while (true) {
				textArea.append("A");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					break;
				}
			}
			if (overlayable != null) {
				overlayable.setOverlayVisible(false);
			}
		});
	}

	private Thread createThread(final JProgressBar bar, final JTextArea textArea) {
		return new Thread(() -> {
			bar.setValue(0);
			Overlayable overlayable = OverlayableUtil.getOverlayable(textArea);
			if (overlayable != null) {
				overlayable.setOverlayVisible(true);
			}
			while (true) {
				textArea.append("A");
				int i = bar.getValue();
				if (i < bar.getMaximum()) {
					i++;
					bar.setValue(i);
				} else {
					break;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					break;
				}
			}
			if (overlayable != null) {
				overlayable.setOverlayVisible(false);
			}
		});
	}

	protected JPanel createMiscPanel() {
		JLabel correctIcon = new JLabel(OverlayableIconsFactory.getImageIcon(OverlayableIconsFactory.CORRECT));
		correctIcon.setToolTipText("Correct ...");
		correctIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		final JLabel infoIcon = new JLabel(OverlayableIconsFactory.getImageIcon(OverlayableIconsFactory.INFO));
		infoIcon.setToolTipText("Click for help ...");
		infoIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		infoIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JidePopup popup = new JidePopup();
				JLabel label = new JLabel("<HTML>This overlay icon allows you to set any help information<BR>with " +
						"another component so that can get help easily.</HTML>");
				label.setOpaque(true);
				label.setBackground(Color.WHITE);
				popup.add(label);
				popup.showPopup(new Insets(-5, 0, -5, 0), infoIcon);
			}
		});
		JLabel questionIcon = new JLabel(OverlayableIconsFactory.getImageIcon(OverlayableIconsFactory.QUESTION));
		questionIcon.setToolTipText("Question ...");
		questionIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		JLabel attentionIcon = new JLabel(OverlayableIconsFactory.getImageIcon(OverlayableIconsFactory.ATTENTION));
		attentionIcon.setToolTipText("Need attention ...");
		attentionIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		DefaultOverlayable overlayableRadioButton = new DefaultOverlayable(new OverlayRadioButton("Radio Button"),
				infoIcon, DefaultOverlayable.SOUTH_EAST);
		overlayables.add(overlayableRadioButton);

		OverlayCheckBox component = new OverlayCheckBox("Check Box");
		DefaultOverlayable overlayableCheckBox = new DefaultOverlayable(component, questionIcon, DefaultOverlayable.SOUTH_EAST);
		overlayables.add(overlayableCheckBox);

		DefaultOverlayable overlayableTextField = new DefaultOverlayable(new OverlayTextField("Text Field", 10),
				correctIcon, DefaultOverlayable.SOUTH_WEST);
		overlayables.add(overlayableTextField);

		OverlayComboBox<String> comboBox = new OverlayComboBox<>(new String[]{"Item 1", "Item 2", "Item 3",});
		comboBox.setPrototypeDisplayValue("AAAAAAAAAA"); // 值是什么无所谓，关键是值的长度
		DefaultOverlayable overlayableComboBox = new DefaultOverlayable(comboBox, attentionIcon, DefaultOverlayable.SOUTH_WEST);
		overlayables.add(overlayableComboBox);

		JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		controlPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		controlPanel.add(overlayableRadioButton);
		controlPanel.add(overlayableCheckBox);
		controlPanel.add(overlayableTextField);
		controlPanel.add(overlayableComboBox);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createOptionPanel(), BorderLayout.BEFORE_LINE_BEGINS);
		panel.add(WrapperUtil.createTopPanel(controlPanel), BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		return createTitledPanel("Customize the Overlayable: ", panel);
	}

	protected JPanel createOptionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new JideBoxLayout(panel, JideBoxLayout.Y_AXIS));
		panel.add(new JLabel("Overlay Location: "));
		panel.add(Box.createVerticalStrut(4));
		// 修改图标的位置
		panel.add(WrapperUtil.createLeftPanel(createLocationPanel()));
		panel.add(Box.createVerticalStrut(6));
		panel.add(new JLabel("Extended Margin"));
		panel.add(Box.createVerticalStrut(4));
		// 修改margin
		panel.add(createMarginPanel());
		return panel;
	}

	protected JComponent createLocationPanel() {
		ButtonGroup group = new ButtonGroup();
		ImageIcon icon = OverlayableIconsFactory.getImageIcon(OverlayableIconsFactory.INFO);
		JideToggleButton center = new JideToggleButton(icon);
		center.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.CENTER);
			}
		});
		JideToggleButton northEast = new JideToggleButton(icon);
		northEast.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.NORTH_EAST);
			}
		});
		JideToggleButton northWest = new JideToggleButton(icon);
		northWest.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.NORTH_WEST);
			}
		});
		JideToggleButton southEast = new JideToggleButton(icon);
		southEast.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.SOUTH_EAST);
			}
		});
		JideToggleButton southWest = new JideToggleButton(icon);
		southWest.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.SOUTH_WEST);
			}
		});
		JideToggleButton north = new JideToggleButton(icon);
		north.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.NORTH);
			}
		});
		JideToggleButton south = new JideToggleButton(icon);
		south.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.SOUTH);
			}
		});
		JideToggleButton west = new JideToggleButton(icon);
		west.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.WEST);
			}
		});
		JideToggleButton east = new JideToggleButton(icon);
		east.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				setLocation(SwingConstants.EAST);
			}
		});
		group.add(center);
		group.add(north);
		group.add(east);
		group.add(south);
		group.add(west);
		group.add(northWest);
		group.add(northEast);
		group.add(southWest);
		group.add(southEast);
		JPanel panel = new JPanel(new GridLayout(3, 3, 2, 2));
		panel.add(northWest);
		panel.add(north);
		panel.add(northEast);
		panel.add(west);
		panel.add(center);
		panel.add(east);
		panel.add(southWest);
		panel.add(south);
		panel.add(southEast);
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY), BorderFactory.createEmptyBorder(2, 2, 2, 2)));
		return panel;
	}

	protected JComponent createMarginPanel() {
		Insets defaultInset = new Insets(5, 5, 5, 5);
		JPanel marginPanel = new JPanel(new JideBorderLayout());
		JSpinner l = new JSpinner(new SpinnerNumberModel(defaultInset.left, 0, 30, 1));
		JSpinner t = new JSpinner(new SpinnerNumberModel(defaultInset.top, 0, 30, 1));
		JSpinner r = new JSpinner(new SpinnerNumberModel(defaultInset.right, 0, 30, 1));
		JSpinner b = new JSpinner(new SpinnerNumberModel(defaultInset.bottom, 0, 30, 1));
		l.addChangeListener(e -> {
			defaultInset.left = (int) l.getValue();
			setExtendedMargin(defaultInset);
			if (_contentPanel != null) {
				_contentPanel.validate();
			}
		});
		r.addChangeListener(e -> {
			defaultInset.right = (int) r.getValue();
			setExtendedMargin(defaultInset);
			if (_contentPanel != null) {
				_contentPanel.validate();
			}
		});
		t.addChangeListener(e -> {
			defaultInset.top = (int) t.getValue();
			setExtendedMargin(defaultInset);
			if (_contentPanel != null) {
				_contentPanel.validate();
			}
		});
		b.addChangeListener(e -> {
			defaultInset.bottom = (int) b.getValue();
			setExtendedMargin(defaultInset);
			if (_contentPanel != null) {
				_contentPanel.validate();
			}
		});
		marginPanel.add(l, BorderLayout.WEST);
		marginPanel.add(t, BorderLayout.NORTH);
		marginPanel.add(r, BorderLayout.EAST);
		marginPanel.add(b, BorderLayout.SOUTH);
		marginPanel.add(Box.createVerticalStrut(l.getPreferredSize().height));
		return marginPanel;
	}

	public void setLocation(int location) {
		for (Overlayable overlayable : overlayables) {
			overlayable.setOverlayLocation(overlayable.getOverlayComponents()[0], location);
		}
	}

	private void setExtendedMargin(Insets insets) {
		for (Overlayable overlayable : overlayables) {
			overlayable.setOverlayLocationInsets(insets);
		}
	}
}