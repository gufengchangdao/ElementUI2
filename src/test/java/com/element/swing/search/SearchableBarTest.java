package com.element.swing.search;

import com.element.plaf.LookAndFeelFactory;
import com.element.ui.tabs.JideTabbedPane;
import com.element.util.SearchableUtil;
import com.element.util.SwingTestUtil;
import demo.AbstractDemo;
import demo.DemoData;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class SearchableBarTest extends AbstractDemo {
	public SearchableBar _textAreaSearchableBar;
	public SearchableBar _tableSearchableBar;

	@Override
	public String getDescription() {
		return """
				SearchableBar works with Searchable components to provide a full size panel to achieve the searching feature.\s
				Comparing to default Searchable feature, SearchableBar is more appropriate for components such as a\
				 large text area or table.
				1. Press any a specified key stroke to start the search. In the demo, we use CTRL-F or CMD-F to start \
				searching but it could be customized to any key stroke.
				2. Press up/down arrow key to navigation to next or previous matching occurrence
				3. Press Highlights button to select all matching occurrences
				4. A lot of customization options using the API

				Demoed classes:
				com.jidesoft.swing.SearchableBar""";
	}

	@Override
	public Component getOptionsPanel() {
		JPanel switchPanel = new JPanel(new GridLayout(0, 1, 3, 3));

		final JRadioButton style1 = new JRadioButton("Full");
		final JRadioButton style2 = new JRadioButton("Compact");

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(style1);
		buttonGroup.add(style2);

		switchPanel.add(new JLabel("Styles:"));
		switchPanel.add(style1);
		switchPanel.add(style2);
		style1.setSelected(true);

		style1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (style1.isSelected()) {
					_tableSearchableBar.setCompact(false);
					_textAreaSearchableBar.setCompact(false);
				}
			}
		});
		style2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (style2.isSelected()) {
					_tableSearchableBar.setCompact(true);
					_textAreaSearchableBar.setCompact(true);
				}
			}
		});

		switchPanel.add(new JLabel("Options: "));

		final JCheckBox option1 = new JCheckBox("Show close button");
		final JCheckBox option2 = new JCheckBox("Show navigation buttons");
		final JCheckBox option3 = new JCheckBox("Show highlights button");
		final JCheckBox option4 = new JCheckBox("Show match case check box");
		final JCheckBox option5 = new JCheckBox("Show repeats check box");
		final JCheckBox option6 = new JCheckBox("Show status");
		final JCheckBox option7 = new JCheckBox("Show history");
		final JTextField option8 = new JTextField("Searching delay");

		switchPanel.add(option1);
		switchPanel.add(option2);
		switchPanel.add(option3);
		switchPanel.add(option4);
		switchPanel.add(option5);
		switchPanel.add(option6);
		switchPanel.add(option7);
		JPanel panel = new JPanel();
		switchPanel.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(new JLabel(" Searching delay: "));
		panel.add(option8);

		option1.setSelected(true);
		option1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateButtons(option1.isSelected(), SearchableBar.SHOW_CLOSE);
			}
		});

		option2.setSelected(true);
		option2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateButtons(option2.isSelected(), SearchableBar.SHOW_NAVIGATION);
			}
		});

		option3.setSelected(true);
		option3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateButtons(option3.isSelected(), SearchableBar.SHOW_HIGHLIGHTS);
			}
		});

		option4.setSelected(true);
		option4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateButtons(option4.isSelected(), SearchableBar.SHOW_MATCHCASE);
			}
		});

		option5.setSelected(false);
		option5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateButtons(option5.isSelected(), SearchableBar.SHOW_REPEATS);
			}
		});

		option6.setSelected(true);
		option6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateButtons(option6.isSelected(), SearchableBar.SHOW_STATUS);
			}
		});

		option7.setSelected(false);
		option7.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				_textAreaSearchableBar.setMaxHistoryLength(option7.isSelected() ? 10 : 0);
				_tableSearchableBar.setMaxHistoryLength(option7.isSelected() ? 10 : 0);
			}
		});

		option8.setText("" + _tableSearchableBar.getSearchable().getSearchingDelay());
		option8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSearchingDelay(e);
			}
		});
		option8.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				setSearchingDelay(e);
			}
		});

		return switchPanel;
	}

	private void setSearchingDelay(AWTEvent e) {
		try {
			_textAreaSearchableBar.getSearchable().setSearchingDelay(Integer.parseInt(((JTextField) e.getSource()).getText()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void updateButtons(boolean selected, int bit) {
		if (selected) {
			_textAreaSearchableBar.setVisibleButtons(_textAreaSearchableBar.getVisibleButtons() | bit);
			_tableSearchableBar.setVisibleButtons(_textAreaSearchableBar.getVisibleButtons() | bit);
		} else {
			_textAreaSearchableBar.setVisibleButtons(_textAreaSearchableBar.getVisibleButtons() & ~bit);
			_tableSearchableBar.setVisibleButtons(_textAreaSearchableBar.getVisibleButtons() & ~bit);
		}
	}

	public Component getDemoPanel() {
		JideTabbedPane tabbedPane = new JideTabbedPane();
		tabbedPane.addTab("JTextArea with SearchableBar", createSearchableTextArea());
		tabbedPane.addTab("JTable with SearchableBar", createSearchableTable());
		return tabbedPane;
	}

	private JPanel createSearchableTextArea() {
		final JTextComponent textArea = createEditor();
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
		Searchable searchable = SearchableUtil.installSearchable(textArea);
		searchable.setRepeats(true);
		_textAreaSearchableBar = SearchableBar.install(searchable,
				KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK), new SearchableBar.Installer() {
					public void openSearchBar(SearchableBar searchableBar) {
						String selectedText = textArea.getSelectedText();
						if (selectedText != null && selectedText.length() > 0) {
							searchableBar.setSearchingText(selectedText);
						}
						panel.add(searchableBar, BorderLayout.PAGE_END);
						panel.invalidate();
						panel.revalidate();
					}

					public void closeSearchBar(SearchableBar searchableBar) {
						panel.remove(searchableBar);
						panel.invalidate();
						panel.revalidate();
					}
				});
		_textAreaSearchableBar.getInstaller().openSearchBar(_textAreaSearchableBar);
		_textAreaSearchableBar.setShowMatchCount(true);
		return panel;
	}

	private JPanel createSearchableTable() {
		JTable table = new JTable(DemoData.createSongTableModel());
		table.setPreferredScrollableViewportSize(new Dimension(200, 100));
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		Searchable searchable = SearchableUtil.installSearchable(table);
		searchable.setRepeats(true);
		final JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		_tableSearchableBar = SearchableBar.install(searchable,
				KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK), new SearchableBar.Installer() {
					public void openSearchBar(SearchableBar searchableBar) {
						panel.add(searchableBar, BorderLayout.AFTER_LAST_LINE);
						panel.invalidate();
						panel.revalidate();
					}

					public void closeSearchBar(SearchableBar searchableBar) {
						panel.remove(searchableBar);
						panel.invalidate();
						panel.revalidate();
					}
				});
		_tableSearchableBar.setName("TableSearchableBar");
		_tableSearchableBar.setShowMatchCount(true);
		return panel;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SwingTestUtil.loadSkin();
			LookAndFeelFactory.installJideExtension();
			showAsFrame(new SearchableBarTest());
		});
	}

	public static JTextComponent createEditor() {
		JTextComponent textComponent = new JTextArea() {
			@Override
			public Dimension getPreferredScrollableViewportSize() {
				return new Dimension(700, 400);
			}
		};
		// Document doc = new DefaultStyledDocument();
		// try {
		// 	doc.insertString(doc.getLength(), DemoData.getArticle(), null);
		// } catch (BadLocationException e) {
		// 	throw new RuntimeException(e);
		// }
		// textComponent.setDocument(doc);
		textComponent.setText(DemoData.getArticle());
		return textComponent;
	}
}