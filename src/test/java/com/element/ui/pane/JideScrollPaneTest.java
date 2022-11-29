package com.element.ui.pane;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.button.JideButton;
import com.element.ui.button.JideToggleButton;
import com.element.ui.icons.IconsFactory;
import com.element.ui.tabs.JideTabbedPane;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;

import javax.swing.*;
import java.awt.*;

public class JideScrollPaneTest extends AbstractDemo {
	private JideScrollPane _pane;

	@Override
	public String getName() {
		return "JideScrollPane Demo";
	}

	@Override
	public String getDescription() {
		return """
				JideScrollPane is an extended version of JScrollPane. It adds the support for row footer, column footer,
				 scroll bar corner etc. components to JScrollPane. In the demo, the red, gray and blue areas are supported by only JideScrollPane.

				Demoed classes:
				com.jidesoft.swing.JideScrollPane""";
	}


	@Override
	public Component getDemoPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(700, 400));
		panel.setLayout(new BorderLayout());
		panel.add(createTabbedPane(), BorderLayout.CENTER);
		return panel;
	}

	private Component createTabbedPane() {
		JideTabbedPane tabbedPane = new JideTabbedPane();
		tabbedPane.setTabShape(JideTabbedPane.SHAPE_BOX);
		tabbedPane.addTab("JideScrollPane", createJideScrollPane());
		tabbedPane.addTab("Example", createExampleScrollPane());
		tabbedPane.addTab("JScrollPane (for comparison)", createScrollPane());
		return tabbedPane;

	}

	private JideScrollPane createJideScrollPane() {
		_pane = new JideScrollPane();
		_pane.setHorizontalScrollBarPolicy(JideScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		_pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel center = new JPanel(new BorderLayout());
		center.setBackground(Color.YELLOW);
		center.setPreferredSize(new Dimension(600, 400));
		center.add(new CenterLabel("Center"));
		_pane.setViewportView(center);

		JPanel rowHeader = new JPanel(new BorderLayout());
		rowHeader.setBackground(Color.GREEN);
		rowHeader.setPreferredSize(new Dimension(100, 400));
		rowHeader.add(new CenterLabel("Row Header"));
		_pane.setRowHeaderView(rowHeader);

		JPanel rowFooter = new JPanel(new BorderLayout());
		rowFooter.setBackground(new Color(255, 128, 128));
		rowFooter.setPreferredSize(new Dimension(100, 400));
		rowFooter.add(new CenterLabel("<HTML>Row Footer<BR>(only in JideScrollPane)</HTML>"));
		_pane.setRowFooterView(rowFooter);

		JPanel columnHeader = new JPanel(new BorderLayout());
		columnHeader.setBackground(Color.GREEN);
		columnHeader.setPreferredSize(new Dimension(600, 50));
		columnHeader.add(new CenterLabel("Column Header"));
		_pane.setColumnHeaderView(columnHeader);

		JPanel columnFooter = new JPanel(new BorderLayout());
		columnFooter.setBackground(new Color(255, 128, 128));
		columnFooter.setPreferredSize(new Dimension(600, 50));
		columnFooter.add(new CenterLabel("<HTML>Column Footer<BR>(only in JideScrollPane)</HTML>"));
		_pane.setColumnFooterView(columnFooter);

		JPanel upperLeft = new JPanel(new BorderLayout());
		upperLeft.setBackground(Color.YELLOW);
		upperLeft.setPreferredSize(new Dimension(100, 50));
		upperLeft.add(new CenterLabel("Upper Left"));
		_pane.setCorner(JideScrollPane.UPPER_LEFT_CORNER, upperLeft);

		JPanel subColHead = new JPanel(new BorderLayout());
		subColHead.setBackground(Color.GRAY);
		subColHead.setPreferredSize(new Dimension(600, 20));
		subColHead.add(new CenterLabel("Sub Column Header"));
		_pane.setSubColumnHeaderView(subColHead);

		JPanel subUpperLeft = new JPanel(new BorderLayout());
		subUpperLeft.setBackground(Color.GRAY);
		subUpperLeft.setPreferredSize(new Dimension(100, 20));
		subUpperLeft.add(new CenterLabel("Sub Upper Left"));
		_pane.setCorner(JideScrollPane.SUB_UPPER_LEFT, subUpperLeft);

		JPanel subUpperRight = new JPanel(new BorderLayout());
		subUpperRight.setBackground(Color.GRAY);
		subUpperRight.setPreferredSize(new Dimension(100, 20));
		subUpperRight.add(new CenterLabel("Sub Upper Right"));
		_pane.setCorner(JideScrollPane.SUB_UPPER_RIGHT, subUpperRight);

		JPanel upperRight = new JPanel(new BorderLayout());
		upperRight.setBackground(Color.YELLOW);
		upperRight.setPreferredSize(new Dimension(100, 50));
		upperRight.add(new CenterLabel("Upper Right"));
		_pane.setCorner(JideScrollPane.UPPER_RIGHT_CORNER, upperRight);

		JPanel lowerLeft = new JPanel(new BorderLayout());
		lowerLeft.setBackground(Color.YELLOW);
		lowerLeft.setPreferredSize(new Dimension(100, 50));
		lowerLeft.add(new CenterLabel("Lower Left"));
		_pane.setCorner(JideScrollPane.LOWER_LEFT_CORNER, lowerLeft);

		JPanel lowerRight = new JPanel(new BorderLayout());
		lowerRight.setBackground(Color.YELLOW);
		lowerRight.setPreferredSize(new Dimension(100, 50));
		lowerRight.add(new CenterLabel("Lower Right"));
		_pane.setCorner(JideScrollPane.LOWER_RIGHT_CORNER, lowerRight);

		Color purple = new Color(128, 128, 255);

		JPanel hLeft = new JPanel(new BorderLayout());
		hLeft.setBackground(purple);
		hLeft.setPreferredSize(new Dimension(50, 50));
		hLeft.add(new CenterLabel("<HTML>Leading</HTML>"));
		_pane.setScrollBarCorner(JideScrollPane.HORIZONTAL_LEADING, hLeft);

		JPanel hRight = new JPanel(new BorderLayout());
		hRight.setBackground(purple);
		hRight.setPreferredSize(new Dimension(50, 50));
		hRight.add(new CenterLabel("<HTML>Trailing</HTML>"));
		_pane.setScrollBarCorner(JideScrollPane.HORIZONTAL_TRAILING, hRight);

		JPanel vTop = new JPanel(new BorderLayout());
		vTop.setBackground(purple);
		vTop.setPreferredSize(new Dimension(50, 50));
		vTop.add(new CenterLabel("<HTML>Top</HTML>"));
		_pane.setScrollBarCorner(JideScrollPane.VERTICAL_TOP, vTop);

		JPanel vBottom = new JPanel(new BorderLayout());
		vBottom.setBackground(purple);
		vBottom.setPreferredSize(new Dimension(50, 50));
		vBottom.add(new CenterLabel("<HTML>Bottom</HTML>"));
		_pane.setScrollBarCorner(JideScrollPane.VERTICAL_BOTTOM, vBottom);

		return _pane;
	}

	private JideScrollPane createExampleScrollPane() {
		JideScrollPane pane = new JideScrollPane();
		pane.setHorizontalScrollBarPolicy(JideScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pane.setVerticalScrollBarPolicy(JideScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel center = new JPanel(new BorderLayout());
		center.setBackground(Color.WHITE);
		center.setPreferredSize(new Dimension(800, 400));
		center.add(new CenterLabel(""));
		pane.setViewportView(center);

		JPanel leadingPanel = new JPanel(new GridLayout(1, 5));
		ButtonGroup buttonGroup = new ButtonGroup();
		JideButton button1 = createJideToggleButton(IconsFactory.getImageIcon(getClass(), "icons/view_normal.png"), "Normal View");
		leadingPanel.add(button1);
		buttonGroup.add(button1);
		JideButton button2 = createJideToggleButton(IconsFactory.getImageIcon(getClass(), "icons/view_web_layout.png"), "Web Layout View");
		leadingPanel.add(button2);
		buttonGroup.add(button2);
		JideButton button3 = createJideToggleButton(IconsFactory.getImageIcon(getClass(), "icons/view_print_layout.png"), "Print Layout View");
		leadingPanel.add(button3);
		buttonGroup.add(button3);
		button3.setSelected(true);
		buttonGroup.add(button3);
		JideButton button4 = createJideToggleButton(IconsFactory.getImageIcon(getClass(), "icons/view_outline.png"), "Outline View");
		leadingPanel.add(button4);
		buttonGroup.add(button4);
		JideButton button5 = createJideToggleButton(IconsFactory.getImageIcon(getClass(), "icons/view_reading_layout.png"), "Reading Layout View");
		leadingPanel.add(button5);
		buttonGroup.add(button5);
		JPanel hLeft = new JPanel(new BorderLayout());
		hLeft.add(leadingPanel);
		pane.setScrollBarCorner(JideScrollPane.HORIZONTAL_LEADING, hLeft);

		JPanel bottomPanel = new JPanel(new GridLayout(3, 1));
		bottomPanel.add(createJideButton(IconsFactory.getImageIcon(getClass(), "icons/scroll_up.png"), "Previous Page"));
		bottomPanel.add(createJideButton(IconsFactory.getImageIcon(getClass(), "icons/scroll_select.png"), "Select Browse Object"));
		bottomPanel.add(createJideButton(IconsFactory.getImageIcon(getClass(), "icons/scroll_down.png"), "Next Page"));

		JPanel vBottom = new JPanel(new BorderLayout());
		vBottom.add(bottomPanel);
		pane.setScrollBarCorner(JideScrollPane.VERTICAL_BOTTOM, vBottom);

		return pane;
	}

	private JideButton createJideButton(Icon icon, String toolTip) {
		JideButton button = new JideButton(icon);
		button.setPreferredSize(new Dimension(18, 18));
		button.setToolTipText(toolTip);
		button.setRequestFocusEnabled(false);
		button.setFocusable(false);
		return button;
	}

	private JideButton createJideToggleButton(Icon icon, String toolTip) {
		JideButton button = new JideToggleButton(icon);
		button.setPreferredSize(new Dimension(18, 18));
		button.setToolTipText(toolTip);
		button.setRequestFocusEnabled(false);
		button.setFocusable(false);
		return button;
	}

	private JScrollPane createScrollPane() {
		JScrollPane pane = new JScrollPane();

		JPanel center = new JPanel(new BorderLayout());
		center.setBackground(Color.YELLOW);
		center.setPreferredSize(new Dimension(600, 400));
		center.add(new CenterLabel("Center"));
		pane.setViewportView(center);

		JPanel rowHeader = new JPanel(new BorderLayout());
		rowHeader.setBackground(Color.GREEN);
		rowHeader.setPreferredSize(new Dimension(100, 400));
		rowHeader.add(new CenterLabel("Row Header"));
		pane.setRowHeaderView(rowHeader);

		JPanel columnHeader = new JPanel(new BorderLayout());
		columnHeader.setBackground(Color.GREEN);
		columnHeader.setPreferredSize(new Dimension(600, 50));
		columnHeader.add(new CenterLabel("Column Header"));
		pane.setColumnHeaderView(columnHeader);

		JPanel upperLeft = new JPanel(new BorderLayout());
		upperLeft.setBackground(Color.YELLOW);
		upperLeft.setPreferredSize(new Dimension(100, 50));
		upperLeft.add(new CenterLabel("Upper Left"));
		pane.setCorner(JideScrollPane.UPPER_LEFT_CORNER, upperLeft);

		JPanel upperRight = new JPanel(new BorderLayout());
		upperRight.setBackground(Color.YELLOW);
		upperRight.setPreferredSize(new Dimension(100, 50));
		upperRight.add(new CenterLabel("Upper Right"));
		pane.setCorner(JideScrollPane.UPPER_RIGHT_CORNER, upperRight);

		JPanel lowerLeft = new JPanel(new BorderLayout());
		lowerLeft.setBackground(Color.YELLOW);
		lowerLeft.setPreferredSize(new Dimension(100, 50));
		lowerLeft.add(new CenterLabel("Lower Left"));
		pane.setCorner(JideScrollPane.LOWER_LEFT_CORNER, lowerLeft);

		JPanel lowerRight = new JPanel(new BorderLayout());
		lowerRight.setBackground(Color.YELLOW);
		lowerRight.setPreferredSize(new Dimension(100, 50));
		lowerRight.add(new CenterLabel("Lower Right"));
		pane.setCorner(JideScrollPane.LOWER_RIGHT_CORNER, lowerRight);

		return pane;
	}

	static class CenterLabel extends JLabel {
		public CenterLabel(String text) {
			super(text);
			setToolTipText(text);
			setHorizontalAlignment(SwingConstants.HORIZONTAL);
			setVerticalAlignment(SwingConstants.CENTER);
		}
	}

	private Component _lr = null;
	private Component _ur = null;
	private Component _ll = null;
	private Component _ul = null;
	private Component _rf = null;
	private Component _cf = null;
	private Component _rh = null;
	private Component _ch = null;

	@Override
	public Component getOptionsPanel() {
		final JCheckBox lr = new JCheckBox("Show LowerRight");
		lr.setSelected(true);
		lr.addItemListener(e -> {
			if (!lr.isSelected()) {
				_lr = _pane.getCorner(JideScrollPane.LOWER_RIGHT_CORNER);
				_pane.setCorner(JideScrollPane.LOWER_RIGHT_CORNER, null);
			} else {
				_pane.setCorner(JideScrollPane.LOWER_RIGHT_CORNER, _lr);
			}
		});

		final JCheckBox ur = new JCheckBox("Show UpperRight");
		ur.setSelected(true);
		ur.addItemListener(e -> {
			if (!ur.isSelected()) {
				_ur = _pane.getCorner(JideScrollPane.UPPER_RIGHT_CORNER);
				_pane.setCorner(JideScrollPane.UPPER_RIGHT_CORNER, null);
			} else {
				_pane.setCorner(JideScrollPane.UPPER_RIGHT_CORNER, _ur);
			}
		});

		final JCheckBox ll = new JCheckBox("Show LowerLeft");
		ll.setSelected(true);
		ll.addItemListener(e -> {
			if (!ll.isSelected()) {
				_ll = _pane.getCorner(JideScrollPane.LOWER_LEFT_CORNER);
				_pane.setCorner(JideScrollPane.LOWER_LEFT_CORNER, null);
			} else {
				_pane.setCorner(JideScrollPane.LOWER_LEFT_CORNER, _ll);
			}
		});

		final JCheckBox ul = new JCheckBox("Show UpperLeft");
		ul.setSelected(true);
		ul.addItemListener(e -> {
			if (!ul.isSelected()) {
				_ul = _pane.getCorner(JideScrollPane.UPPER_LEFT_CORNER);
				_pane.setCorner(JideScrollPane.UPPER_LEFT_CORNER, null);
			} else {
				_pane.setCorner(JideScrollPane.UPPER_LEFT_CORNER, _ul);
			}
		});

		final JCheckBox rh = new JCheckBox("Show RowHeader");
		rh.setSelected(true);
		rh.addItemListener(e -> {
			if (!rh.isSelected()) {
				_rh = _pane.getRowHeader().getView();
				_pane.setRowHeaderView(null);
			} else {
				_pane.setRowHeaderView(_rh);
			}
		});

		final JCheckBox ch = new JCheckBox("Show ColumnHeader");
		ch.setSelected(true);
		ch.addItemListener(e -> {
			if (!ch.isSelected()) {
				_ch = _pane.getColumnHeader().getView();
				_pane.setColumnHeaderView(null);
			} else {
				_pane.setColumnHeaderView(_ch);
			}
		});

		final JCheckBox rf = new JCheckBox("Show RowFooter");
		rf.setSelected(true);
		rf.addItemListener(e -> {
			if (!rf.isSelected()) {
				_rf = _pane.getRowFooter().getView();
				_pane.setRowFooterView(null);
			} else {
				_pane.setRowFooterView(_rf);
			}
		});

		final JCheckBox cf = new JCheckBox("Show ColumnFooter");
		cf.setSelected(true);
		cf.addItemListener(e -> {
			if (!cf.isSelected()) {
				_cf = _pane.getColumnFooter().getView();
				_pane.setColumnFooterView(null);
			} else {
				_pane.setColumnFooterView(_cf);
			}
		});

		final JCheckBox h = new JCheckBox("Stretch Horizontal ScrollBar");
		h.addItemListener(e -> _pane.setHorizontalScrollBarCoversWholeWidth(h.isSelected()));
		h.setSelected(_pane.isHorizontalScrollBarCoversWholeWidth());

		final JCheckBox v = new JCheckBox("Stretch Vertical ScrollBar");
		v.addItemListener(e -> _pane.setVerticalScrollBarCoversWholeHeight(v.isSelected()));
		v.setSelected(_pane.isVerticalScrollBarCoversWholeHeight());

		JPanel panel = new JPanel(new GridLayout(0, 1, 2, 2));
		panel.add(new JLabel("Stretch scroll bars:"));
		panel.add(h);
		panel.add(v);
		panel.add(new JLabel("Show/hide child components:"));
		panel.add(rh);
		panel.add(ch);
		panel.add(rf);
		panel.add(cf);
		panel.add(ur);
		panel.add(lr);
		panel.add(ul);
		panel.add(ll);
		return panel;
	}

	static public void main(String[] s) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new JideScrollPaneTest());
		});
	}
}