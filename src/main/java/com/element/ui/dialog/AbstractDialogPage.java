package com.element.ui.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * AbstractDialogPage is an abstract base class extends AbstractPage. In addition to AbstractPage, this class has some
 * new properties so that it can be used in dialog. <BR> For example, it can support ButtonEvent which is used by
 * ButtonPanel. In addition, it has title, icon, description and parent attribute.
 */
public abstract class AbstractDialogPage extends AbstractPage {
	// 属性
	public static final String TITLE_PROPERTY = "title";
	public static final String ICON_PROPERTY = "icon";
	public static final String PAGE_ENABLED_PROPERTY = "pageEnabled";
	public static final String DESCRIPTION_PROPERTY = "description";
	protected transient ButtonEvent _buttonEvent = null;

	protected String title;
	protected String description;
	protected Icon icon;
	protected boolean pageEnabled = true;
	protected AbstractDialogPage parentPage;

	private Component _defaultFocusComponent;

	/**
	 * Creates an AbstractDialogPage.
	 */
	protected AbstractDialogPage() {
	}

	/**
	 * Creates an AbstractDialogPage with title.
	 *
	 * @param title the title of the page
	 */
	public AbstractDialogPage(String title) {
		this.title = title;
	}

	/**
	 * Creates an AbstractDialogPage with title and icon.
	 *
	 * @param title       the title of the page
	 * @param description the description for the page
	 */
	public AbstractDialogPage(String title, String description) {
		this.title = title;
		this.description = description;
	}

	/**
	 * Creates an AbstractDialogPage with title and icon.
	 *
	 * @param title the title of the page
	 * @param icon  the icon of the page
	 */
	public AbstractDialogPage(String title, Icon icon) {
		this.title = title;
		this.icon = icon;
	}

	/**
	 * Creates an AbstractDialogPage with title, icon and description.
	 *
	 * @param title       the title of the page
	 * @param icon        the icon of the page
	 * @param description the description for the page
	 */
	public AbstractDialogPage(String title, String description, Icon icon) {
		this.title = title;
		this.icon = icon;
		this.description = description;
	}

	/**
	 * /** Creates an AbstractDialogPage with title, icon, description and its parent.
	 *
	 * @param title       the title of the page
	 * @param icon        the icon of the page
	 * @param description the description for the page
	 * @param parentPage  the parent of the page
	 */
	public AbstractDialogPage(String title, String description, Icon icon, AbstractDialogPage parentPage) {
		this.title = title;
		this.icon = icon;
		this.description = description;
		this.parentPage = parentPage;
	}

	/**
	 * 延迟加载有性能优势，但是需要注意组件的布局如果放在该方法中设置，就需要手动设置对话框大小，否则对话框无法第一时间拿到内容的首选大小，
	 * 因此想要保持首选大小的话建议还是在构造器中对组件布局
	 *
	 * @see Laziness#lazyInitialize()
	 */
	@Override
	public abstract void lazyInitialize();

	/**
	 * Adds a <code>ButtonListener</code> to the page.
	 *
	 * @param l the <code>ButtonListener</code> to be added
	 */
	public void addButtonListener(ButtonListener l) {
		listenerList.add(ButtonListener.class, l);
	}

	/**
	 * Removes a <code>ButtonListener</code> from the page.
	 *
	 * @param l the <code>ButtonListener</code> to be removed
	 */
	public void removeButtonListener(ButtonListener l) {
		listenerList.remove(ButtonListener.class, l);
	}

	/**
	 * Returns an array of all the <code>ButtonListener</code>s added to this <code>Page</code> with
	 * <code>ButtonListener</code>.
	 *
	 * @return all of the <code>ButtonListener</code>s added, or an empty array if no listeners have been added
	 * @since 1.4
	 */
	public ButtonListener[] getButtonListeners() {
		return listenerList.getListeners(ButtonListener.class);
	}

	/**
	 * Fire button event with id. The only event that doesn't take a button name as parameter is the {@link
	 * ButtonEvent#CLEAR_DEFAULT_BUTTON} event.
	 *
	 * @param id
	 */
	public void fireButtonEvent(int id) {
		fireButtonEvent(id, null, null);
	}

	/**
	 * Fire button event with id and button name.
	 *
	 * @param id
	 * @param buttonName
	 */
	public void fireButtonEvent(int id, String buttonName) {
		fireButtonEvent(id, buttonName, null);
	}

	/**
	 * Fire button event with id, button name and user object if needed.
	 *
	 * @param id
	 * @param buttonName
	 * @param userObject
	 */
	public void fireButtonEvent(int id, String buttonName, String userObject) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ButtonListener.class) {
				if (_buttonEvent == null) {
					_buttonEvent = new ButtonEvent(this, id, buttonName, userObject);
				} else {
					_buttonEvent.setID(id);
					_buttonEvent.setButtonName(buttonName);
					_buttonEvent.setUserObject(userObject);
				}
				((ButtonListener) listeners[i + 1]).buttonStateChangeListener(_buttonEvent);
			}
		}
	}

	/**
	 * Gets the title of the page.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the page.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		String old = this.title;
		this.title = title;
		firePropertyChange(TITLE_PROPERTY, old, this.title);
	}

	/**
	 * Gets the icon of the page.
	 *
	 * @return the icon of the page.
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Sets the icon of the page.
	 *
	 * @param icon the new icon
	 */
	public void setIcon(Icon icon) {
		Icon old = this.icon;
		this.icon = icon;
		firePropertyChange(ICON_PROPERTY, old, this.icon);
	}


	/**
	 * Checks if the page is enabled.
	 *
	 * @return true if the page is enabled. Otherwise false.
	 */
	public boolean isPageEnabled() {
		return pageEnabled;
	}

	/**
	 * Sets page enabled or disabled. The only place this flag is used right now is in MultiplePageDialog ICON_STYLE and
	 * TAB_STYLE. Disabled page will have a disabled icon or tab as indicator.
	 *
	 * @param pageEnabled
	 */
	public void setPageEnabled(boolean pageEnabled) {
		if (this.pageEnabled != pageEnabled) {
			Boolean oldValue = this.pageEnabled ? Boolean.TRUE : Boolean.FALSE;
			Boolean newValue = pageEnabled ? Boolean.TRUE : Boolean.FALSE;
			this.pageEnabled = pageEnabled;
			firePropertyChange(PAGE_ENABLED_PROPERTY, oldValue, newValue);
		}
	}

	/**
	 * Gets the description of the page.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the page.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		String old = this.description;
		this.description = description;
		firePropertyChange(DESCRIPTION_PROPERTY, old, this.description);
	}

	/**
	 * Gets the parent page.
	 *
	 * @return the parent page
	 */
	public AbstractDialogPage getParentPage() {
		return parentPage;
	}

	/**
	 * Sets the parent page.
	 *
	 * @param parentPage the parent page
	 */
	public void setParentPage(AbstractDialogPage parentPage) {
		this.parentPage = parentPage;
	}

	/**
	 * Gets the full title. It is basically a concat of the titles of all its parent with "." in between.
	 *
	 * @return the full qualified title
	 */
	public String getFullTitle() {
		StringBuilder b = new StringBuilder(getTitle());
		AbstractDialogPage page = this;
		while (page.getParentPage() != null) {
			AbstractDialogPage parent = page.getParentPage();
			b.insert(0, ".");
			b.insert(0, parent.getTitle());
			page = parent;
		}
		return new String(b);
	}

	/**
	 * Gets the default focus component. The default focus component will gain focus when page is shown.
	 *
	 * @return the default focus component.
	 */
	public Component getDefaultFocusComponent() {
		return _defaultFocusComponent;
	}

	/**
	 * Sets the default focus component. The default focus component will gain focus when page is shown.
	 *
	 * @param defaultFocusComponent a component inside the page.
	 */
	public void setDefaultFocusComponent(Component defaultFocusComponent) {
		_defaultFocusComponent = defaultFocusComponent;
	}

	/**
	 * Focus the default focus component if not null.
	 */
	public void focusDefaultFocusComponent() {
		final Component focusComponent = getDefaultFocusComponent();
		if (focusComponent != null) {
			Runnable runnable = focusComponent::requestFocusInWindow;
			SwingUtilities.invokeLater(runnable);
		}
	}
}

