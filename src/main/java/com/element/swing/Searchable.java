/*
 * @(#)${NAME}
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.swing;

import com.element.plaf.UIDefaultsLookup;
import com.element.regex.DefaultWildcardSupport;
import com.element.regex.WildcardSupport;
import com.element.ui.list.ListSearchable;
import com.element.ui.popup.JidePopup;
import com.element.ui.table.TableSearchable;
import com.element.ui.tree.TreeSearchable;
import com.element.util.CompareUtil;
import com.element.util.ListenerUtil;
import com.element.util.SearchableUtil;
import com.element.util.UIUtil;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * JList、JTable 和JTree 是三个数据丰富的组件。它们可用于显示大量数据，因此搜索功能将是这些组件中非常有用的功能。 Searchable就是这样一个
 * 类，可以让JList、JTable、JTree可搜索。用户可以简单地输入他们想要搜索的任何字符串，并使用箭头键导航到下一个或上一个匹配项。
 * <p>
 * Searchable是一个基础抽象类。 ListSearchable 、 TableSearchable和TreeSearchable是分别使 JList、JTable 和 JTree 可搜索的实现。
 * 对于每个实现，需要实现五个方法。
 * <ul>
 *     <li>{@link #getSelectedIndex()}</li>
 *     <li>{@link #setSelectedIndex(int, boolean)}</li>
 *     <li>{@link #getElementCount()}</li>
 *     <li>{@link #getElementAt(int)}</li>
 *     <li>{@link #convertElementToString(Object)}</li>
 * </ul>
 * 请查看每个方法的 javadoc 以了解更多详细信息。
 * <p>
 * 此类使用的键是完全可定制的。子类可以覆盖
 * {@link #isActivateKey(KeyEvent)}, {@link #isDeactivateKey(KeyEvent)}, {@link #isFindFirstKey(KeyEvent)},
 * {@link #isFindLastKey(KeyEvent)}, {@link #isFindNextKey(KeyEvent)}, {@link #isFindPreviousKey(KeyEvent)}
 * 等方法以提供自己的一组键。
 *
 * <ol>
 *     <li>除了按向上/向下箭头查找特定字符串的下一次出现或上一次出现外，还有其他几个非常方便的功能。</li>
 *     <li>多项选择功能 - 如果您按住 CTRL 键并在按下向上和向下箭头的同时按住它，它将在保留现有选择的同时找到下一个/上一个事件。 </li>
 *     <li>选择所有功能 - 如果您键入搜索文本并按 CTRL+A，将选择该搜索字符串的所有匹配项。这是一个非常方便的功能。例如，您要删除表中名称列
 *     以“old”开头的所有行。所以您可以输入“old”并按 CTRL+A，现在所有以“old”开头的行都将被选中。按删除将删除所有这些。 </li>
 *     <li>基本正则表达式支持 - 它允许“？”匹配任何字母或数字，或“*”匹配多个字母或数字。尽管可以实现完整的正则表达式支持，但我们不想那样做。
 *     原因是正则表达式非常复杂，让用户在一个小的弹出窗口中输入如此复杂的表达式可能不是一个好主意。但是，如果您的用户非常熟悉正则表达式，您可
 *     以将该功能添加到Searchable 。您需要做的就是覆盖{@link #compare(String, String)}方法并自行实现。</li>
 *     <li>递归搜索（仅在树可搜索中）-在树可搜索的情况下，有一个选项称为递归。您可以调用树可搜索的
 *     {@link TreeSearchable#setRecursive(boolean)}来更改它。如果可搜索的树是递归的，它将搜索所有的树节点，包括那些不可见的树节点，以
 *     找到匹配的节点。显然，如果您的树有无限数量的树节点或潜在的大量树节点（例如表示文件系统的树），那么递归属性应该是false。为了避免这种情况
 *     下的这个潜在问题，我们将其默认为false。</li>
 *     <li>由于这是一个抽象类，请参阅{@link ListSearchable},{@link TreeSearchable}, and {@link TableSearchable}的 javadoc
 *     以了解如何分别将其与 JList、JTree 和 JTable 一起使用。</li>
 *     <li>这个组件有一个计时器。如果用户输入速度非常快，它会将它们累积在一起并只生成一个搜索动作。定时器可以通过
 *     {@link #setSearchingDelay(int)}来控制。</li>
 *     <li>默认情况下，为了性能，我们将使用轻量级弹出窗口。但是，如果您使用重量级组件可能会掩盖轻量级弹出窗口，则可以调用
 *     {@link #setHeavyweightComponentEnabled(boolean)}为 true，以便使用重量级弹出窗口。</li>
 *     <li>在组件上安装Searchable时，component.getClientProperty(Searchable.CLIENT_PROPERTY_SEARCHABLE) 将为您提供
 *     Searchable 实例。您也可以使用静态方法{@link #getSearchable(JComponent)}来获取它。</li>
 *     <li>最后但并非最不重要的是，一个组件上只允许一个 Searchable。如果您安装另一个，它将删除第一个，然后安装新的。</li>
 * </ol>
 */
public abstract class Searchable {
	private final PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);

	protected final JComponent _component;

	private SearchPopup _popup;
	private JLayeredPane _layeredPane;

	private boolean _heavyweightComponentEnabled;

	/**
	 * optional SearchableProvider
	 */
	private SearchableProvider _searchableProvider;
	private Pattern _pattern;
	private String _searchText;
	private String _previousSearchText;

	private boolean _fromStart = true;
	private boolean _caseSensitive = false;
	private boolean _repeats = false;
	private boolean _wildcardEnabled = true;
	private boolean _countMatch;
	protected int _matchCount;
	private WildcardSupport _wildcardSupport = null;
	private Color _mismatchForeground;
	private Color _foreground = null;
	private Color _background = null;
	protected ComponentListener _componentListener;
	protected KeyListener _keyListener;
	protected FocusListener _focusListener;
	private SearchableListener _searchableListener;

	public static final String PROPERTY_SEARCH_TEXT = "searchText";

	private int _cursor = -1;

	private String _searchLabel = null;

	/**
	 * The popup location
	 */
	private int _popupLocation = SwingConstants.TOP;

	private int _searchingDelay = 0;
	private int _popupTimeout = 0;
	private Timer _popupTimer;

	private boolean _reverseOrder = false;

	/**
	 * A list of event listeners for this component.
	 */
	protected EventListenerList listenerList = new EventListenerList();

	private Component _popupLocationRelativeTo;

	/**
	 * The client property for Searchable instance. When Searchable is installed on a component, this client property
	 * has the Searchable.
	 */
	public static final String CLIENT_PROPERTY_SEARCHABLE = "Searchable";

	private Set<Integer> _selection;

	private boolean _processModelChangeEvent = true;
	private boolean _hideSearchPopupOnEvent = true;

	/**
	 * Creates a Searchable.
	 *
	 * @param component component where the Searchable will be installed.
	 */
	public Searchable(JComponent component) {
		Searchable searchable = getSearchable(component);
		if (searchable != null) {
			SearchableUtil.uninstallSearchable(searchable);
		}
		_previousSearchText = null;
		_component = component;
		_selection = new HashSet<>();
		installListeners();
		updateClientProperty(_component, this);
	}

	/**
	 * Creates a Searchable.
	 *
	 * @param component          component where the Searchable will be installed.
	 * @param searchableProvider the Searchable Provider.
	 */
	public Searchable(JComponent component, SearchableProvider searchableProvider) {
		Searchable searchable = getSearchable(component);
		if (searchable != null) {
			SearchableUtil.uninstallSearchable(searchable);
		}
		_searchableProvider = searchableProvider;
		_previousSearchText = null;
		_component = component;
		_selection = new HashSet<>();
		installListeners();
		updateClientProperty(_component, this);
	}

	/**
	 * Gets the selected index in the component. The concrete implementation should call methods on the component to
	 * retrieve the current selected index. If the component supports multiple selection, it's OK just return the index
	 * of the first selection. <p>Here are some examples. In the case of JList, the index is the row index. In the case
	 * of JTree, the index is the row index too. In the case of JTable, depending on the selection mode, the index could
	 * be row index (in row selection mode), could be column index (in column selection mode) or could the cell index
	 * (in cell selection mode).
	 *
	 * @return the selected index.
	 */
	public abstract int getSelectedIndex();

	/**
	 * Sets the selected index. The concrete implementation should call methods on the component to select the element
	 * at the specified index. The incremental flag is used to do multiple select. If the flag is true, the element at
	 * the index should be added to current selection. If false, you should clear previous selection and then select the
	 * element.
	 *
	 * @param index       the index to be selected
	 * @param incremental a flag to enable multiple selection. If the flag is true, the element at the index should be
	 *                    added to current selection. If false, you should clear previous selection and then select the
	 *                    element.
	 */
	public abstract void setSelectedIndex(int index, boolean incremental);

	/**
	 * Sets the selected index. The reason we have this method is just for back compatibility. All the method do is just
	 * to invoke {@link #setSelectedIndex(int, boolean)}.
	 * <p/>
	 * Please do NOT try to override this method. Always override {@link #setSelectedIndex(int, boolean)} instead.
	 *
	 * @param index       the index to be selected
	 * @param incremental a flag to enable multiple selection. If the flag is true, the element at the index should be
	 *                    added to current selection. If false, you should clear previous selection and then select the
	 *                    element.
	 */
	public void adjustSelectedIndex(int index, boolean incremental) {
		setSelectedIndex(index, incremental);
	}

	/**
	 * Gets the total element count in the component. Different concrete implementation could have different
	 * interpretation of the count. This is totally OK as long as it's consistent in all the methods. For example, the
	 * index parameter in other methods should be always a valid value within the total count.
	 *
	 * @return the total element count.
	 */
	public abstract int getElementCount();

	/**
	 * Gets the element at the specified index. The element could be any data structure that internally used in the
	 * component. The convertElementToString method will give you a chance to convert the element to string which is
	 * used to compare with the string that user types in.
	 *
	 * @param index the index
	 * @return the element at the specified index.
	 */
	public abstract Object getElementAt(int index);

	/**
	 * Converts the element that returns from getElementAt() to string.
	 *
	 * @param element the element to be converted
	 * @return the string representing the element in the component.
	 */
	public abstract String convertElementToString(Object element);

	/**
	 * Converts the element to String.
	 * <p/>
	 * This method will invoke {@link #convertElementToString(Object)} only. This method is added to provide a public
	 * method for ShrinkSearchSupport without breaking the existing code of the customers.
	 *
	 * @param element the element to be converted
	 * @return the string representing the element in the component.
	 * @since 3.4.5
	 */
	public String convertToString(Object element) {
		return convertElementToString(element);
	}

	/**
	 * Get the flag indicating if the search popup should be hidden on the component's event.
	 * <p/>
	 * By default, the value is true so that the search popup will be hidden anyway when the component get related
	 * events. However, you could set this flag to false if you don't want to hide the search popup in some scenarios.
	 * For example, JIDE ComboBoxShrinkSearchableSupport will set this flag to false temporarily when it tries to shrink
	 * the list.
	 *
	 * @return true if the search popup is hidden on event. Otherwise false.
	 */
	public boolean isHideSearchPopupOnEvent() {
		return _hideSearchPopupOnEvent;
	}

	/**
	 * Set the flag indicating if the search popup should be hidden on the component's event.
	 *
	 * @param hideSearchPopupOnEvent the flag
	 * @see #isHideSearchPopupOnEvent()
	 */
	public void setHideSearchPopupOnEvent(boolean hideSearchPopupOnEvent) {
		_hideSearchPopupOnEvent = hideSearchPopupOnEvent;
	}

	/**
	 * A text field for searching text.
	 */
	protected class SearchField extends JTextField {
		SearchField() {
			UIUtil.setComponentTransparent(this);
		}

		@Override
		public Dimension getPreferredSize() {
			Dimension size = super.getPreferredSize();
			size.width = getFontMetrics(getFont()).stringWidth(getText()) + 4;
			return size;
		}

		@Override
		public void processKeyEvent(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_BACK_SPACE && getDocument().getLength() == 0) {
				e.consume();
				return;
			}
			final boolean isNavigationKey = isNavigationKey(e);
			if (isDeactivateKey(e) && !isNavigationKey) {
				hidePopup();
				if (keyCode == KeyEvent.VK_ESCAPE)
					e.consume();
				return;
			}
			super.processKeyEvent(e);
			if (keyCode == KeyEvent.VK_BACK_SPACE || isNavigationKey)
				e.consume();
			if (isSelectAllKey(e)) {
				e.consume();
			}
		}
	}

	/**
	 * The popup panel for search label and search text field.
	 */
	public class DefaultSearchPopup extends SearchPopup {
		private JLabel _label;
		private JLabel _noMatch;

		public DefaultSearchPopup(String text) {
			initComponents(text);
		}

		private void initComponents(String text) {
			final Color foreground = Searchable.this.getForeground();
			final Color background = Searchable.this.getBackground();

			// setup the label
			_label = new JLabel(getSearchLabel());
			_label.setForeground(foreground);
			_label.setVerticalAlignment(JLabel.BOTTOM);

			_noMatch = new JLabel();
			_noMatch.setForeground(getMismatchForeground());
			_noMatch.setVerticalAlignment(JLabel.BOTTOM);

			//setup text field
			_textField = new SearchField();
			_textField.setFocusable(false);
			_textField.setBorder(BorderFactory.createEmptyBorder());
			_textField.setForeground(foreground);
			_textField.setCursor(getCursor());
			_textField.getDocument().addDocumentListener(new DocumentListener() {
				private Timer timer = new Timer(200, e -> applyText());

				public void insertUpdate(DocumentEvent e) {
					startTimer();
				}

				public void removeUpdate(DocumentEvent e) {
					startTimer();
				}

				public void changedUpdate(DocumentEvent e) {
					startTimer();
				}

				private void applyText() {
					String text = _textField.getText();
					firePropertyChangeEvent(text);
					if (text.length() != 0) {
						int found = findFromCursor(text);
						if (found == -1) {
							_textField.setForeground(getMismatchForeground());
						} else {
							_textField.setForeground(foreground);
						}
						select(found, null, text);
					} else {
						_textField.setForeground(foreground);
						_noMatch.setText("");
						updatePopupBounds();
						hidePopup();
					}
				}

				void startTimer() {
					updatePopupBounds();
					if (getSearchingDelay() > 0) {
						timer.setInitialDelay(getSearchingDelay());
						if (timer.isRunning()) {
							timer.restart();
						} else {
							timer.setRepeats(false);
							timer.start();
						}
					} else {
						applyText();
					}
				}
			});
			_textField.setText(text);

			setBackground(background);
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIDefaultsLookup.getColor("controlShadow"), 1),
					BorderFactory.createEmptyBorder(0, 6, 1, 8)));
			setLayout(new BorderLayout(2, 0));
			Dimension size = _label.getPreferredSize();
			size.height = _textField.getPreferredSize().height;
			_label.setPreferredSize(size);
			add(_label, BorderLayout.BEFORE_LINE_BEGINS);
			add(_textField, BorderLayout.CENTER);
			add(_noMatch, BorderLayout.AFTER_LINE_ENDS);
			setPopupBorder(BorderFactory.createEmptyBorder());
		}

		@Override
		protected void select(int index, KeyEvent e, String searchingText) {
			if (index != -1) {
				boolean incremental = e != null && isIncrementalSelectKey(e);
				setSelectedIndex(index, incremental);
				Searchable.this.setCursor(index, incremental);
				_textField.setForeground(Searchable.this.getForeground());
				_noMatch.setText("");
			} else {
				_textField.setForeground(getMismatchForeground());
				_noMatch.setText(getResourceString("Searchable.noMatch"));
			}
			updatePopupBounds();
			if (index != -1) {
				Object element = getElementAt(index);
				fireSearchableEvent(new SearchableEvent(Searchable.this, SearchableEvent.SEARCHABLE_MATCH, searchingText, element, convertElementToString(element)));
			} else {
				fireSearchableEvent(new SearchableEvent(Searchable.this, SearchableEvent.SEARCHABLE_NOMATCH, searchingText));
			}
		}

		private void updatePopupBounds() {
			if (_popup != null) {
				_textField.invalidate();
				try {
					if (!isHeavyweightComponentEnabled()) {
						Dimension size = _noMatch.getPreferredSize();
						size.width += _label.getPreferredSize().width;
						size.width += new JLabel(_textField.getText()).getPreferredSize().width + 24;
						size.height = _popup.getSize().height;
						_popup.setSize(size);
						_popup.validate();
					} else {
						_popup.packPopup();
					}
				} catch (Exception e) { // catch any potential exception
					// see bug report at http://www.jidesoft.com/forum/viewtopic.php?p=8557#8557
				}
			}
		}
	}

	/**
	 * Hides the popup.
	 */
	public void hidePopup() {
		if (_popup != null) {
			if (isHeavyweightComponentEnabled()) {
				_popup.hidePopupImmediately();
			} else {
				if (_layeredPane != null) {
					_layeredPane.remove(_popup);
					_layeredPane.validate();
					_layeredPane.repaint();
					_layeredPane = null;
				}
			}
			_popup = null;
			_searchableProvider = null;
			_previousSearchText = null;
			fireSearchableEvent(new SearchableEvent(Searchable.this, SearchableEvent.SEARCHABLE_END, "", getCurrentIndex(), _previousSearchText));
		}
		setCursor(-1);
	}

	public SearchableProvider getSearchableProvider() {
		return _searchableProvider;
	}

	public void setSearchableProvider(SearchableProvider searchableProvider) {
		_searchableProvider = searchableProvider;
	}

	/**
	 * Installs necessary listeners to the component. This method will be called automatically when Searchable is
	 * created.
	 */
	public void installListeners() {
		if (_componentListener == null) {
			_componentListener = createComponentListener();
		}
		_component.addComponentListener(_componentListener);
		Component scrollPane = UIUtil.getScrollPane(_component);
		if (scrollPane != null) {
			scrollPane.addComponentListener(_componentListener);
		}

		if (_keyListener == null) {
			_keyListener = createKeyListener();
		}
		ListenerUtil.insertKeyListener(getComponent(), _keyListener, 0);

		if (_focusListener == null) {
			_focusListener = createFocusListener();
		}
		if (_focusListener != null) {
			getComponent().addFocusListener(_focusListener);
		}
		if (_searchableListener == null) {
			_searchableListener = e -> {
				if (e.getID() == SearchableEvent.SEARCHABLE_START) {
					if (getPopupTimeout() > 0) {
						_popupTimer = new Timer(getPopupTimeout(), e1 -> {
							if (isPopupVisible()) {
								hidePopup();
							}
						});
						_popupTimer.setRepeats(false);
						_popupTimer.start();
					}
				} else if (_popupTimer != null) {
					if (e.getID() == SearchableEvent.SEARCHABLE_END) {
						_popupTimer.stop();
					} else {
						_popupTimer.restart();
					}
				}
			};
		}
		addSearchableListener(_searchableListener);
	}

	/**
	 * Creates a component listener that updates the popup when component is hidden, moved or resized.
	 *
	 * @return a ComponentListener.
	 */
	protected ComponentListener createComponentListener() {
		return new ComponentAdapter() {
			@Override
			public void componentHidden(ComponentEvent e) {
				super.componentHidden(e);
				boolean passive = _searchableProvider == null || _searchableProvider.isPassive();
				if (passive) {
					hidePopup();
				}
			}

			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				boolean passive = _searchableProvider == null || _searchableProvider.isPassive();
				if (passive) {
					updateSizeAndLocation();
				}
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				super.componentMoved(e);
				boolean passive = _searchableProvider == null || _searchableProvider.isPassive();
				if (passive) {
					updateSizeAndLocation();
				}
			}
		};
	}

	/**
	 * Creates the KeyListener and listen to key typed in the component.
	 *
	 * @return the KeyListener.
	 */
	protected KeyListener createKeyListener() {
		return new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean passive = _searchableProvider == null || _searchableProvider.isPassive();
				if (passive) {
					keyTypedOrPressed(e);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				boolean passive = _searchableProvider == null || _searchableProvider.isPassive();
				if (passive) {
					keyTypedOrPressed(e);
				}
			}
		};
	}

	/**
	 * Creates a FocusListener. We use it to hide the popup when the component loses focus.
	 *
	 * @return a FocusListener.
	 */
	protected FocusListener createFocusListener() {
		return new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent focusevent) {
				boolean passive = _searchableProvider == null || _searchableProvider.isPassive();
				if (passive) {
					Timer timer = new Timer(1000, e -> hidePopup());
					timer.setRepeats(false);
					timer.start();
				}
			}
		};
	}

	/**
	 * Uninstall the listeners that installed before. This method is never called because we don't have the control of
	 * the life cycle of the component. However you can call this method if you don't want the component to be
	 * searchable any more.
	 */
	public void uninstallListeners() {
		if (_componentListener != null) {
			getComponent().removeComponentListener(_componentListener);
			Component scrollPane = UIUtil.getScrollPane(getComponent());
			if (scrollPane != null) {
				scrollPane.removeComponentListener(_componentListener);
			}
			_componentListener = null;
		}

		if (_keyListener != null) {
			getComponent().removeKeyListener(_keyListener);
			_keyListener = null;
		}

		if (_focusListener != null) {
			getComponent().removeFocusListener(_focusListener);
			_focusListener = null;
		}

		if (_searchableListener != null) {
			removeSearchableListener(_searchableListener);
			_searchableListener = null;
		}
	}

	/**
	 * Adds the property change listener. The only property change event that will be fired is the "searchText" property
	 * which will be fired when user types in a different search text in the popup.
	 *
	 * @param propertychangelistener the listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener propertychangelistener) {
		_propertyChangeSupport.addPropertyChangeListener(propertychangelistener);
	}

	/**
	 * Removes the property change listener.
	 *
	 * @param propertychangelistener the listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener propertychangelistener) {
		_propertyChangeSupport.removePropertyChangeListener(propertychangelistener);
	}

	public void firePropertyChangeEvent(String searchingText) {
		if (!searchingText.equals(_previousSearchText)) {
			_propertyChangeSupport.firePropertyChange(PROPERTY_SEARCH_TEXT, _previousSearchText, searchingText);
			fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_CHANGE, searchingText, getCurrentIndex(), _previousSearchText));
			_previousSearchText = searchingText;
			if (searchingText.length() == 0) {
				searchingTextEmpty();
			}
		}
	}

	/**
	 * Actions to take on searching text empty scenario
	 */
	protected void searchingTextEmpty() {
		// do nothing
	}

	/**
	 * Checks if the element matches the searching text.
	 *
	 * @param element       the element to be checked
	 * @param searchingText the searching text
	 * @return true if matches.
	 */
	protected boolean compare(Object element, String searchingText) {
		String text = convertElementToString(element);
		return text != null && compare(isCaseSensitive() ? text : text.toLowerCase(), searchingText);
	}

	/**
	 * Checks if the element string matches the searching text. Different from {@link #compare(Object, String)}, this
	 * method is after the element has been converted to string using {@link #convertElementToString(Object)}.
	 *
	 * @param text          the text to be checked
	 * @param searchingText the searching text
	 * @return true if matches.
	 */
	protected boolean compare(String text, String searchingText) {
		if (searchingText == null || searchingText.trim().length() == 0) {
			return true;
		}

		if (!isWildcardEnabled()) {
			return searchingText != null &&
					(searchingText.equals(text) || searchingText.length() > 0 && (isFromStart() ? text.startsWith(searchingText) : text.contains(searchingText)));
		} else {
			// use the previous pattern since nothing changed.
			if (_searchText != null && _searchText.equals(searchingText) && _pattern != null) {
				return _pattern.matcher(text).find();
			}

			WildcardSupport wildcardSupport = getWildcardSupport();
			String s = wildcardSupport.convert(searchingText);
			if (searchingText.equals(s)) {
				return isFromStart() ? text.startsWith(searchingText) : text.contains(searchingText);
			}
			_searchText = searchingText;

			try {
				_pattern = Pattern.compile(isFromStart() ? "^" + s : s, isCaseSensitive() ? 0 : Pattern.CASE_INSENSITIVE);
				return _pattern.matcher(text).find();
			} catch (PatternSyntaxException e) {
				return false;
			}
		}
	}


	/**
	 * Gets the cursor which is the index of current location when searching. The value will be used in findNext and
	 * findPrevious.
	 *
	 * @return the current position of the cursor.
	 */
	public int getCursor() {
		return _cursor;
	}

	/**
	 * Sets the cursor which is the index of current location when searching. The value will be used in findNext and
	 * findPrevious.
	 *
	 * @param cursor the new position of the cursor.
	 */
	public void setCursor(int cursor) {
		setCursor(cursor, false);
	}

	/**
	 * Sets the cursor which is the index of current location when searching. The value will be used in findNext and
	 * findPrevious. We will call this method automatically inside this class. However, if you ever call {@link
	 * #setSelectedIndex(int, boolean)} method from your code, you should call this method with the same parameters.
	 *
	 * @param cursor      the new position of the cursor.
	 * @param incremental a flag to enable multiple selection. If the flag is true, the element at the index should be
	 *                    added to current selection. If false, you should clear previous selection and then select the
	 *                    element.
	 */
	public void setCursor(int cursor, boolean incremental) {
		if (!incremental || _cursor < 0) _selection.clear();
		if (_cursor >= 0) _selection.add(cursor);
		_cursor = cursor;
	}

	/**
	 * Highlight all matching cases in the target.
	 * <p/>
	 * In default implementation, it will just search all texts in the target to highlight all. If you have a really
	 * huge text to search, you may want to override this method to have a lazy behavior on visible areas only.
	 */
	public void highlightAll() {
		int firstIndex = -1;
		int index = getSelectedIndex();
		String text = getSearchingText();

		while (index != -1) {
			int newIndex = findNext(text);
			if (index == newIndex) {
				index = -1;
			} else {
				index = newIndex;
			}
			if (index != -1) {
				if (firstIndex == -1) {
					firstIndex = index;
				}
				select(index, text);
			}
		}
		// now select the first one
		if (firstIndex != -1) {
			select(firstIndex, text);
		}
	}

	/**
	 * Cancel highlight all.
	 * <p/>
	 * By default, it does nothing. However, if you want to override {@link #highlightAll()}, you may want to override
	 * this method to notify your Searchable that the highlightAll button is to be released.
	 */
	public void cancelHighlightAll() {

	}

	/**
	 * Select the index for the searching text.
	 *
	 * @param index         the start offset
	 * @param searchingText the searching text presented in the searchable event to be fired here.
	 */
	protected void select(int index, String searchingText) {
		if (index != -1) {
			setSelectedIndex(index, true);
			setCursor(index, true);
			Object element = getElementAt(index);
			fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_MATCH, searchingText, element, convertElementToString(element)));
		} else {
			setSelectedIndex(-1, false);
			fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_NOMATCH, searchingText));
		}
	}

	/**
	 * Finds the next matching index from the cursor.
	 *
	 * @param s the searching text
	 * @return the next index that the element matches the searching text.
	 */
	public int findNext(String s) {
		String str = isCaseSensitive() ? s : s.toLowerCase();
		int count = getElementCount();
		if (count == 0)
			return s.length() > 0 ? -1 : 0;
		int selectedIndex = getCurrentIndex();
		for (int i = selectedIndex + 1; i < count; i++) {
			Object element = getElementAt(i);
			if (compare(element, str))
				return i;
		}

		if (isRepeats()) {
			for (int i = 0; i < selectedIndex; i++) {
				Object element = getElementAt(i);
				if (compare(element, str))
					return i;
			}
		}

		return selectedIndex == -1 ? -1 : (compare(getElementAt(selectedIndex), str) ? selectedIndex : -1);
	}

	public int getCurrentIndex() {
		if (_selection.contains(getSelectedIndex())) {
			return _cursor != -1 ? _cursor : getSelectedIndex();
		} else {
			_selection.clear();
			return getSelectedIndex();
		}
	}

	/**
	 * Finds the previous matching index from the cursor.
	 *
	 * @param s the searching text
	 * @return the previous index that the element matches the searching text.
	 */
	public int findPrevious(String s) {
		String str = isCaseSensitive() ? s : s.toLowerCase();
		int count = getElementCount();
		if (count == 0)
			return s.length() > 0 ? -1 : 0;
		int selectedIndex = getCurrentIndex();
		for (int i = selectedIndex - 1; i >= 0; i--) {
			Object element = getElementAt(i);
			if (compare(element, str))
				return i;
		}

		if (isRepeats()) {
			for (int i = count - 1; i >= selectedIndex; i--) {
				Object element = getElementAt(i);
				if (compare(element, str))
					return i;
			}
		}
		return selectedIndex == -1 ? -1 : (compare(getElementAt(selectedIndex), str) ? selectedIndex : -1);
	}

	/**
	 * Finds the next matching index from the cursor. If it reaches the end, it will restart from the beginning. However
	 * is the reverseOrder flag is true, it will finds the previous matching index from the cursor. If it reaches the
	 * beginning, it will restart from the end.
	 *
	 * @param s the searching text
	 * @return the next index that the element matches the searching text.
	 */
	public int findFromCursor(String s) {
		if (isCountMatch()) {
			boolean reverse = isReverseOrder();
			setReverseOrder(false);
			int selectedIndex = getCurrentIndex();
			if (selectedIndex < 0) {
				selectedIndex = 0;
			}
			int oldIndex;
			int newIndex = -1;
			_matchCount = -1;
			do {
				setSelectedIndex(newIndex, false);
				oldIndex = newIndex;
				newIndex = findNext(s);
				_matchCount++;
			}
			while (newIndex > oldIndex);
			setSelectedIndex(selectedIndex, false);
			setReverseOrder(reverse);
		}

		if (isReverseOrder()) {
			return reverseFindFromCursor(s);
		}

		String str = isCaseSensitive() ? s : s.toLowerCase();
		int selectedIndex = getCurrentIndex();
		if (selectedIndex < 0)
			selectedIndex = 0;
		int count = getElementCount();
		if (count == 0)
			return -1; // no match

		// find from cursor
		for (int i = selectedIndex; i < count; i++) {
			Object element = getElementAt(i);
			if (compare(element, str))
				return i;
		}

		// if not found, start over from the beginning
		for (int i = 0; i < selectedIndex; i++) {
			Object element = getElementAt(i);
			if (compare(element, str))
				return i;
		}

		return -1;
	}

	/**
	 * Finds the previous matching index from the cursor. If it reaches the beginning, it will restart from the end.
	 *
	 * @param s the searching text
	 * @return the next index that the element matches the searching text.
	 */
	public int reverseFindFromCursor(String s) {
		if (!isReverseOrder()) {
			return findFromCursor(s);
		}

		String str = isCaseSensitive() ? s : s.toLowerCase();
		int selectedIndex = getCurrentIndex();
		if (selectedIndex < 0)
			selectedIndex = 0;
		int count = getElementCount();
		if (count == 0)
			return -1; // no match

		// find from cursor to beginning
		for (int i = selectedIndex; i >= 0; i--) {
			Object element = getElementAt(i);
			if (compare(element, str))
				return i;
		}

		// if not found, start over from the end
		for (int i = count - 1; i >= selectedIndex; i--) {
			Object element = getElementAt(i);
			if (compare(element, str))
				return i;
		}

		return -1;
	}

	/**
	 * Finds the first element that matches the searching text.
	 *
	 * @param s the searching text
	 * @return the first element that matches with the searching text.
	 */
	public int findFirst(String s) {
		String str = isCaseSensitive() ? s : s.toLowerCase();
		int count = getElementCount();
		if (count == 0)
			return s.length() > 0 ? -1 : 0;

		for (int i = 0; i < count; i++) {
			int index = getIndex(count, i);
			Object element = getElementAt(index);
			if (compare(element, str))
				return index;
		}

		return -1;
	}

	/**
	 * Finds the last element that matches the searching text.
	 *
	 * @param s the searching text
	 * @return the last element that matches the searching text.
	 */
	public int findLast(String s) {
		String str = isCaseSensitive() ? s : s.toLowerCase();
		int count = getElementCount();
		if (count == 0)
			return s.length() > 0 ? -1 : 0;
		for (int i = count - 1; i >= 0; i--) {
			Object element = getElementAt(i);
			if (compare(element, str))
				return i;
		}
		return -1;
	}

	/**
	 * This method is called when a key is typed or pressed.
	 *
	 * @param e the KeyEvent.
	 */
	protected void keyTypedOrPressed(KeyEvent e) {
		if (_searchableProvider != null && _searchableProvider.isPassive()) {
			_searchableProvider.processKeyEvent(e);
			return;
		}

		if (isActivateKey(e)) {
			String searchingText = "";
			if (e.getID() == KeyEvent.KEY_TYPED) {
				if (ListenerUtil.isMenuShortcutKeyDown(e)) { // if ctrl key is pressed
					return;
				}
				if (e.isAltDown()) {
					return;
				}

				searchingText = String.valueOf(e.getKeyChar());
			}
			showPopup(searchingText);
			if (e.getKeyCode() != KeyEvent.VK_ENTER) {
				e.consume();
			}
		}
	}

	private int getIndex(int count, int index) {
		return isReverseOrder() ? count - index - 1 : index;
	}

	/**
	 * Shows the search popup. By default, the search popup will be visible automatically when user types in the first
	 * key (in the case of JList, JTree, JTable) or types in designated keystroke (in the case of JTextComponent). So
	 * this method is only used when you want to show the popup manually.
	 *
	 * @param searchingText the searching text
	 */
	public void showPopup(String searchingText) {
		if (_searchableProvider == null) {
			fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_START, searchingText));
			showPopup(createSearchPopup(searchingText));
			_searchableProvider = new SearchableProvider() {
				public String getSearchingText() {
					return _popup != null ? _popup.getSearchingText() : "";
				}

				public boolean isPassive() {
					return true;
				}

				public void processKeyEvent(KeyEvent e) {
					if (_popup != null) {
						_popup.processKeyEvent(e);
					}
				}
			};
		}
	}

	/**
	 * Creates the popup to hold the searching text.
	 *
	 * @param searchingText the searching text
	 * @return the searching popup.
	 */
	protected SearchPopup createSearchPopup(String searchingText) {
		return new DefaultSearchPopup(searchingText);
	}

	/**
	 * Gets the searching text.
	 *
	 * @return the searching text.
	 */
	public String getSearchingText() {
		return _searchableProvider != null ? _searchableProvider.getSearchingText() : "";
	}

	private void showPopup(SearchPopup searchpopup) {
		JRootPane rootPane = _component.getRootPane();
		if (rootPane != null)
			_layeredPane = rootPane.getLayeredPane();
		else {
			_layeredPane = null;
		}

		if (_layeredPane == null || isHeavyweightComponentEnabled()) {
			_popup = searchpopup;
			Point location = updateSizeAndLocation();
			if (location != null) {
				searchpopup.showPopup(location.x, location.y);
				_popup.setVisible(true);
			} else {
				_popup = null;
			}
		} else {
			if (_popup != null && _layeredPane != null) {
				_layeredPane.remove(_popup);
				_layeredPane.validate();
				_layeredPane.repaint();
				_layeredPane = null;
			} else if (!_component.isShowing())
				_popup = null;
			else
				_popup = searchpopup;

			if (_popup == null || !_component.isDisplayable())
				return;

			if (_layeredPane == null) {
				System.err.println("Failed to find layeredPane.");
				return;
			}

			_layeredPane.add(_popup, JLayeredPane.POPUP_LAYER);

			updateSizeAndLocation();
			_popup.setVisible(true);
			_popup.validate();
		}

		if (_popup != null) {
			_popup.addPopupMenuListener(new PopupMenuListener() {
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

				}

				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
					// clear up the internal cached values so that a new search popup will be shown after this.
					_popup = null;
					_searchableProvider = null;
				}

				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {

				}
			});
		}
	}

	private Point updateSizeAndLocation() {
		Component component = getPopupLocationRelativeTo();
		if (component == null) {
			component = UIUtil.getScrollPane(_component);
		}
		if (component == null) {
			component = _component;
		}

		Point componentLocation;
		if (_popup != null) {
			Dimension size = _popup.getPreferredSize();
			switch (getPopupLocation()) {
				case SwingConstants.BOTTOM:
					try {
						componentLocation = component.getLocationOnScreen();
						componentLocation.y += component.getHeight();
						if (!isHeavyweightComponentEnabled()) {
							SwingUtilities.convertPointFromScreen(componentLocation, _layeredPane);
							if ((componentLocation.y + size.height > _layeredPane.getHeight())) {
								componentLocation.y = _layeredPane.getHeight() - size.height;
							}
						}
					} catch (IllegalComponentStateException e) {
						return null; // can't get the location so just return.
					}
					break;
				case SwingConstants.TOP:
				default:
					try {
						componentLocation = component.getLocationOnScreen();
						if (!isHeavyweightComponentEnabled()) {
							SwingUtilities.convertPointFromScreen(componentLocation, _layeredPane);
						}
						componentLocation.y -= size.height;
						if ((componentLocation.y < 0)) {
							componentLocation.y = 0;
						}
					} catch (IllegalComponentStateException e) {
						return null; // can't get the location so just return.
					}
					break;
			}
			if (!isHeavyweightComponentEnabled()) {
				_popup.setLocation(componentLocation);
				_popup.setSize(size);
			} else {
				_popup.packPopup();
			}
			return componentLocation;
		} else {
			return null;
		}
	}

	/**
	 * Checks if the key is used as a key to find the first occurrence.
	 *
	 * @param e the key event
	 * @return true if the key in KeyEvent is a key to find the firstoccurrencee. By default, home key is used.
	 */
	protected boolean isFindFirstKey(KeyEvent e) {
		return e.getKeyCode() == KeyEvent.VK_HOME;
	}

	/**
	 * Checks if the key is used as a key to find the last occurrence.
	 *
	 * @param e the key event
	 * @return true if the key in KeyEvent is a key to find the last occurrence. By default, end key is used.
	 */
	protected boolean isFindLastKey(KeyEvent e) {
		return e.getKeyCode() == KeyEvent.VK_END;
	}

	/**
	 * Checks if the key is used as a key to find the previous occurrence.
	 *
	 * @param e the key event
	 * @return true if the key in KeyEvent is a key to find the previous occurrence. By default, up arrow key is used.
	 */
	protected boolean isFindPreviousKey(KeyEvent e) {
		return e.getKeyCode() == KeyEvent.VK_UP;
	}

	/**
	 * Checks if the key is used as a key to find the next occurrence.
	 *
	 * @param e the key event
	 * @return true if the key in KeyEvent is a key to find the next occurrence. By default, down arrow key is used.
	 */
	protected boolean isFindNextKey(KeyEvent e) {
		return e.getKeyCode() == KeyEvent.VK_DOWN;
	}

	/**
	 * Checks if the key is used as a navigation key. Navigation keys are keys which are used to navigate to other
	 * occurrences of the searching string.
	 *
	 * @param e the key event
	 * @return true if the key in KeyEvent is a navigation key.
	 */
	protected boolean isNavigationKey(KeyEvent e) {
		return isFindFirstKey(e) || isFindLastKey(e) || isFindNextKey(e) || isFindPreviousKey(e);
	}

	/**
	 * Checks if the key in KeyEvent should activate the search popup.
	 *
	 * @param e the key event
	 * @return true if the keyChar is visible except space and tab.
	 */
	protected boolean isActivateKey(KeyEvent e) {
		char keyChar = e.getKeyChar();
		return e.getID() == KeyEvent.KEY_TYPED && keyChar > KeyEvent.VK_SPACE && keyChar != KeyEvent.VK_DELETE;
	}

	/**
	 * Checks if the key in KeyEvent should hide the search popup. If this method return true and the key is not used
	 * for navigation purpose ({@link #isNavigationKey(KeyEvent)} return false), the popup will be
	 * hidden.
	 *
	 * @param e the key event
	 * @return true if the keyCode in the KeyEvent is escape key, enter key, or any of the arrow keys such as page up,
	 * page down, home, end, left, right, up and down.
	 */
	protected boolean isDeactivateKey(KeyEvent e) {
		int keyCode = e.getKeyCode();
		return keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_ESCAPE
				|| keyCode == KeyEvent.VK_PAGE_UP || keyCode == KeyEvent.VK_PAGE_DOWN
				|| keyCode == KeyEvent.VK_HOME || keyCode == KeyEvent.VK_END
				|| keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT
				|| keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN;
	}

	/**
	 * Checks if the key will trigger selecting all.
	 *
	 * @param e the key event
	 * @return true if the key in KeyEvent is a key to trigger selecting all.
	 */
	protected boolean isSelectAllKey(KeyEvent e) {
		return ListenerUtil.isMenuShortcutKeyDown(e) && e.getKeyCode() == KeyEvent.VK_A;
	}

	/**
	 * Checks if the key will trigger incremental selection.
	 *
	 * @param e the key event
	 * @return true if the key in KeyEvent is a key to trigger incremental selection. By default, ctrl down key is used.
	 */
	protected boolean isIncrementalSelectKey(KeyEvent e) {
		return ListenerUtil.isMenuShortcutKeyDown(e);
	}

	/**
	 * Gets the foreground color when the searching text doesn't match with any of the elements in the component.
	 *
	 * @return the foreground color for mismatch. If you never call {@link #setMismatchForeground(Color)}. red
	 * color will be used.
	 */
	public Color getMismatchForeground() {
		if (_mismatchForeground == null) {
			return Color.RED;
		} else {
			return _mismatchForeground;
		}
	}

	/**
	 * Sets the foreground for mismatch.
	 *
	 * @param mismatchForeground mismatch forground
	 */
	public void setMismatchForeground(Color mismatchForeground) {
		_mismatchForeground = mismatchForeground;
	}

	/**
	 * Checks if the case is sensitive during searching.
	 *
	 * @return true if the searching is case sensitive.
	 */
	public boolean isCaseSensitive() {
		return _caseSensitive;
	}

	/**
	 * Sets the case sensitive flag. By default, it's false meaning it's a case insensitive search.
	 *
	 * @param caseSensitive the flag if searching is case sensitive
	 */
	public void setCaseSensitive(boolean caseSensitive) {
		_caseSensitive = caseSensitive;
	}

	/**
	 * If it returns a positive number, it will wait for that many ms before doing the search. When the searching is
	 * complex, this flag will be useful to make the searching efficient. In the other words, if user types in several
	 * keys very quickly, there will be only one search. If it returns 0 or negative number, each key will generate a
	 * search.
	 *
	 * @return the number of ms delay before searching starts.
	 */
	public int getSearchingDelay() {
		return _searchingDelay;
	}

	/**
	 * If this flag is set to a positive number, it will wait for that many ms before doing the search. When the
	 * searching is complex, this flag will be useful to make the searching efficient. In the other words, if user types
	 * in several keys very quickly, there will be only one search. If this flag is set to 0 , each key will generate a
	 * search with no delay. If this flag is set to a negative number, there are different behaviors. SearchableBar will
	 * not generate any search while typing, but others will generate a search with no delay as well as it is set to 0.
	 *
	 * @param searchingDelay the number of ms delay before searching start.
	 */
	public void setSearchingDelay(int searchingDelay) {
		_searchingDelay = searchingDelay;
	}

	/**
	 * Checks if restart from the beginning when searching reaches the end or restart from the end when reaches
	 * beginning. Default is false.
	 *
	 * @return true or false.
	 */
	public boolean isRepeats() {
		return _repeats;
	}

	/**
	 * Sets the repeat flag. By default, it's false meaning it will stop searching when reaching the end or reaching the
	 * beginning.
	 *
	 * @param repeats the repeat flag
	 */
	public void setRepeats(boolean repeats) {
		_repeats = repeats;
	}

	/**
	 * Gets the foreground color used inn the search popup.
	 *
	 * @return the foreground. By default it will use the foreground of tooltip.
	 */
	public Color getForeground() {
		if (_foreground == null) {
			return UIDefaultsLookup.getColor("ToolTip.foreground");
		} else {
			return _foreground;
		}
	}

	/**
	 * Sets the foreground color used by popup.
	 *
	 * @param foreground the foreground
	 */
	public void setForeground(Color foreground) {
		_foreground = foreground;
	}

	/**
	 * Gets the background color used inn the search popup.
	 *
	 * @return the background. By default it will use the background of tooltip.
	 */
	public Color getBackground() {
		if (_background == null) {
			return UIDefaultsLookup.getColor("ToolTip.background");
		} else {
			return _background;
		}
	}

	/**
	 * Sets the background color used by popup.
	 *
	 * @param background the background
	 */
	public void setBackground(Color background) {
		_background = background;
	}

	/**
	 * Checks if it supports wildcard in searching text. By default it is true which means user can type in "*" or "?"
	 * to match with any characters or any character. If it's false, it will treat "*" or "?" as a regular character.
	 *
	 * @return true if it supports wildcard.
	 */
	public boolean isWildcardEnabled() {
		return _wildcardEnabled;
	}

	/**
	 * Enable or disable the usage of wildcard.
	 *
	 * @param wildcardEnabled the flag if wildcard is enabled
	 * @see #isWildcardEnabled()
	 */
	public void setWildcardEnabled(boolean wildcardEnabled) {
		_wildcardEnabled = wildcardEnabled;
	}

	/**
	 * Gets the WildcardSupport. If user never sets it, {@link DefaultWildcardSupport} will be used.
	 *
	 * @return the WildcardSupport.
	 */
	public WildcardSupport getWildcardSupport() {
		if (_wildcardSupport == null) {
			_wildcardSupport = new DefaultWildcardSupport();
		}
		return _wildcardSupport;
	}

	/**
	 * Sets the WildcardSupport. This class allows you to define what wildcards to use and how to convert the wildcard
	 * strings to a regular expression string which is eventually used to search.
	 *
	 * @param wildcardSupport the new WildCardSupport.
	 */
	public void setWildcardSupport(WildcardSupport wildcardSupport) {
		_wildcardSupport = wildcardSupport;
	}

	/**
	 * Gets the current text that appears in the search popup. By default it is "Search for: ".
	 *
	 * @return the text that appears in the search popup.
	 */
	public String getSearchLabel() {
		if (_searchLabel == null) {
			return getResourceString("Searchable.searchFor");
		} else {
			return _searchLabel;
		}
	}

	/**
	 * Sets the text that appears in the search popup.
	 *
	 * @param searchLabel the search label
	 */
	public void setSearchLabel(String searchLabel) {
		_searchLabel = searchLabel;
	}

	/**
	 * Adds the specified listener to receive searchable events from this searchable.
	 *
	 * @param l the searchable listener
	 */
	public void addSearchableListener(SearchableListener l) {
		listenerList.add(SearchableListener.class, l);
	}

	/**
	 * Removes the specified searchable listener so that it no longer receives searchable events.
	 *
	 * @param l the searchable listener
	 */
	public void removeSearchableListener(SearchableListener l) {
		listenerList.remove(SearchableListener.class, l);
	}

	/**
	 * Returns an array of all the <code>SearchableListener</code>s added to this <code>SearchableGroup</code> with
	 * <code>addSearchableListener</code>.
	 *
	 * @return all of the <code>SearchableListener</code>s added or an empty array if no listeners have been added
	 * @see #addSearchableListener
	 */
	public SearchableListener[] getSearchableListeners() {
		return listenerList.getListeners(SearchableListener.class);
	}

	/**
	 * Returns if a given listener is already installed.
	 *
	 * @param l the listener
	 * @return true if the listener is already installed. Otherwise false.
	 * @since 3.2.3
	 */
	public boolean isSearchableListenerInstalled(SearchableListener l) {
		SearchableListener[] listeners = getSearchableListeners();
		for (SearchableListener listener : listeners) {
			if (listener == l) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Fires a searchable event.
	 *
	 * @param e the event
	 */
	public void fireSearchableEvent(SearchableEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SearchableListener.class) {
				((SearchableListener) listeners[i + 1]).searchableEventFired(e);
			}
		}
	}

	/**
	 * Gets the actual component which installed this Searchable.
	 *
	 * @return the actual component which installed this Searchable.
	 */
	public Component getComponent() {
		return _component;
	}

	/**
	 * Gets the popup location. It could be either {@link SwingConstants#TOP} or {@link SwingConstants#BOTTOM}.
	 *
	 * @return the popup location.
	 */
	public int getPopupLocation() {
		return _popupLocation;
	}

	/**
	 * Sets the popup location.
	 *
	 * @param popupLocation the popup location. The valid values are either {@link SwingConstants#TOP} or {@link
	 *                      SwingConstants#BOTTOM}.
	 */
	public void setPopupLocation(int popupLocation) {
		_popupLocation = popupLocation;
	}

	public abstract class SearchPopup extends JidePopup {
		protected SearchField _textField;

		@Override
		public void processKeyEvent(KeyEvent e) {
			_textField.processKeyEvent(e);
			if (e.isConsumed()) {
				String text = getSearchingText();
				if (text.length() == 0) {
					return;
				}

				if (isSelectAllKey(e)) {
					selectAll(e, text);
					return;
				}

				int found;
				if (isFindPreviousKey(e)) {
					found = findPrevious(text);
					select(found, e, text);
				} else if (isFindNextKey(e)) {
					found = findNext(text);
					select(found, e, text);
				} else if (isFindFirstKey(e)) {
					found = findFirst(text);
					select(found, e, text);
				} else if (isFindLastKey(e)) {
					found = findLast(text);
					select(found, e, text);
				}
//                else {
//                    found = findFromCursor(text);
//                }
			}
			if (e.getKeyCode() != KeyEvent.VK_ENTER) {
				e.consume();
			}
		}

		private void selectAll(KeyEvent e, String text) {
			boolean oldReverseOrder = isReverseOrder(); // keep the old reverse order and we will set it back.
			if (oldReverseOrder) {
				setReverseOrder(false);
			}

			int index = findFirst(text);
			if (index != -1) {
				setSelectedIndex(index, false); // clear side effect of ctrl-a will select all items
				Searchable.this.setCursor(index); // as setSelectedIndex is used directly, we have to manually set the cursor value.
			}


			boolean oldRepeats = isRepeats(); // set repeats to false and set it back later.
			if (oldRepeats) {
				setRepeats(false);
			}

			while (index != -1) {
				int newIndex = findNext(text);
				if (index == newIndex) {
					index = -1;
				} else {
					index = newIndex;
				}
				if (index == -1) {
					break;
				}
				select(index, e, text);
			}

			if (oldRepeats) {
				setRepeats(oldRepeats);
			}

			if (oldReverseOrder) {
				setReverseOrder(oldReverseOrder);
			}
		}

		public String getSearchingText() {
			return _textField != null ? _textField.getText() : "";
		}

		public JTextField getTextField() {
			return _textField;
		}

		abstract protected void select(int index, KeyEvent e, String searchingText);
	}

	/**
	 * Checks the searching order. By default the searchable starts searching from top to bottom. If this flag is true,
	 * it searches from bottom to top.
	 *
	 * @return the reverseOrder flag.
	 */
	public boolean isReverseOrder() {
		return _reverseOrder;
	}

	/**
	 * Sets the searching order. By default the searchable starts searching from top to bottom. If this flag is true,
	 * it searches from bottom to top.
	 *
	 * @param reverseOrder the flag if searching from top to bottom or from bottom to top
	 */
	public void setReverseOrder(boolean reverseOrder) {
		_reverseOrder = reverseOrder;
	}

	/**
	 * Gets the localized string from resource bundle. Subclass can override it to provide its own string. Available
	 * keys are defined in swing.properties that begin with "Searchable.".
	 *
	 * @param key the resource string key
	 * @return the localized string.
	 */
	protected String getResourceString(String key) {
		return Resource.getResourceBundle(_component != null ? _component.getLocale() : Locale.getDefault()).getString(key);
	}

	/**
	 * Check if the searchable popup is visible.
	 *
	 * @return true if visible. Otherwise, false.
	 */
	public boolean isPopupVisible() {
		return _popup != null;
	}

	public boolean isHeavyweightComponentEnabled() {
		return _heavyweightComponentEnabled;
	}

	public void setHeavyweightComponentEnabled(boolean heavyweightComponentEnabled) {
		_heavyweightComponentEnabled = heavyweightComponentEnabled;
	}


	/**
	 * Gets the component that the location of the popup relative to.
	 *
	 * @return the component that the location of the popup relative to.
	 */
	public Component getPopupLocationRelativeTo() {
		return _popupLocationRelativeTo;
	}

	/**
	 * Sets the location of the popup relative to the specified component. Then based on the value of {@link
	 * #getPopupLocation()}. If you never set, we will use the searchable component or its scroll pane (if exists) as
	 * the popupLocationRelativeTo component.
	 *
	 * @param popupLocationRelativeTo the relative component
	 */
	public void setPopupLocationRelativeTo(Component popupLocationRelativeTo) {
		_popupLocationRelativeTo = popupLocationRelativeTo;
	}

	/**
	 * This is a property of how to compare searching text with the data. If it is true, it will use {@link
	 * String#startsWith(String)} to do the comparison. Otherwise, it will use {@link String#indexOf(String)} to do the
	 * comparison.
	 *
	 * @return true or false.
	 */
	public boolean isFromStart() {
		return _fromStart;
	}

	/**
	 * Sets the fromStart property.
	 *
	 * @param fromStart true if the comparison matches from the start of the text only. Otherwise false. The difference
	 *                  is if true, it will use String's <code>startWith</code> method to match. If false, it will use
	 *                  <code>indedxOf</code> method.
	 */
	public void setFromStart(boolean fromStart) {
		hidePopup();
		_fromStart = fromStart;
	}

	/**
	 * Gets the Searchable installed on the component. Null is no Searchable was installed.
	 *
	 * @param component the component
	 * @return the Searchable installed. Null is no Searchable was installed.
	 */
	public static Searchable getSearchable(JComponent component) {
		Object clientProperty = component.getClientProperty(CLIENT_PROPERTY_SEARCHABLE);
		if (clientProperty instanceof Searchable) {
			return ((Searchable) clientProperty);
		} else {
			return null;
		}
	}

	private void updateClientProperty(JComponent component, Searchable searchable) {
		if (component != null) {
			Object clientProperty = _component.getClientProperty(CLIENT_PROPERTY_SEARCHABLE);
			if (clientProperty instanceof Searchable) {
				((Searchable) clientProperty).uninstallListeners();
			}
			component.putClientProperty(CLIENT_PROPERTY_SEARCHABLE, searchable);
		}
	}

	/**
	 * Get the flag if we should process model change event.
	 * <p/>
	 * By default, the value is true, which means the model change event should be processed.
	 * <p/>
	 * In <code>ListShrinkSearchableSupport</code> case, since we will fire this event while applying filters. This flag
	 * will be switched to false before we fire the event and set it back to true.
	 * <p/>
	 * In normal case, please do not set this flag.
	 *
	 * @return true if we should process model change event. Otherwise false.
	 */
	public boolean isProcessModelChangeEvent() {
		return _processModelChangeEvent;
	}

	/**
	 * Set the flag if we should process model change event.
	 * <p/>
	 * In normal case, please do not set this flag.
	 * <p/>
	 *
	 * @param processModelChangeEvent the flag
	 * @see #isProcessModelChangeEvent()
	 */
	public void setProcessModelChangeEvent(boolean processModelChangeEvent) {
		_processModelChangeEvent = processModelChangeEvent;
	}

	/**
	 * Gets the timeout for showing the popup.
	 *
	 * @return the popup timeout.
	 * @see #setPopupTimeout(int)
	 */
	public int getPopupTimeout() {
		return _popupTimeout;
	}

	/**
	 * Sets the timeout for showing the popup.
	 * <p/>
	 * By default, the timeout value is 0, which means no timeout. You could set it to a positive value to automatically
	 * hide the search popup after an idle time.
	 *
	 * @param popupTimeout the timeout in milliseconds
	 */
	public void setPopupTimeout(int popupTimeout) {
		_popupTimeout = popupTimeout;
	}

	/**
	 * Gets the flag indicating if the Searchable should count all matches for every search.
	 *
	 * @return true if should count all matches. Otherwise false.
	 * @see #setCountMatch(boolean)
	 * @since 3.5.2
	 */
	public boolean isCountMatch() {
		return _countMatch;
	}

	/**
	 * Sets the flag indicating if the Searchable should count all matches for every search.
	 * <p/>
	 * By default, the flag is false to keep performance high.
	 *
	 * @param countMatch the flag
	 * @since 3.5.2
	 */
	public void setCountMatch(boolean countMatch) {
		_countMatch = countMatch;
	}

	public int getMatchCount() {
		return _matchCount;
	}

	/**
	 * <code>findAll</code> uses the Searchable to find all the element indices that match the searching string.
	 *
	 * @param s the searching string.
	 * @return the list of indices.
	 */
	public java.util.List<Integer> findAll(String s) {
		String str = isCaseSensitive() ? s : s.toLowerCase();
		java.util.List<Integer> list = new ArrayList<>();
		for (int i = 0, count = getElementCount(); i < count; i++) {
			Object elementAt = getElementAt(i);
			if (compare(elementAt, str)) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * Gets the element at the specified index as string using {@link #convertElementToString(Object)} method.
	 *
	 * @param index the index.
	 * @return the element at the index converted to string.
	 */
	public String getElementAtAsString(int index) {
		return convertElementToString(getElementAt(index));
	}

	public void textChanged(String text) {
		if (text == null || text.length() == 0) {
			firePropertyChangeEvent("");
			return;
		}
		int found = findFromCursor(text);
		if (found == -1) {
			firePropertyChangeEvent(text);
			fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_NOMATCH, text));
		} else {
			firePropertyChangeEvent(text);
			Object element = getElementAt(found);
			fireSearchableEvent(new SearchableEvent(this, SearchableEvent.SEARCHABLE_MATCH, text, element, convertElementToString(element)));
		}
	}

	/**
	 * Finds the first element that matches the searching text exactly.
	 *
	 * @param s the searching text
	 * @return the first element that matches with the searching text.
	 * @since 3.6.1
	 */
	public int findFirstExactly(String s) {
		String str = isCaseSensitive() ? s : s.toLowerCase();
		int count = getElementCount();
		if (count == 0)
			return s.length() > 0 ? -1 : 0;

		for (int i = 0; i < count; i++) {
			int index = getIndex(count, i);
			Object element = getElementAt(index);
			String text = convertElementToString(element);
			if (CompareUtil.equals(text, str))
				return index;
		}

		return -1;
	}
}
