/*
 * @(#)JideMenu.java
 *
 * Copyright 2002 - 2004 JIDE Software Inc. All rights reserved.
 */
package com.element.ui.menu;

import com.element.plaf.UIDefaultsLookup;
import com.element.swing.Alignable;
import com.element.util.SystemInfo;
import com.element.util.UIUtil;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JMenu 的特殊实现。它用于替换 JMenu 以便与 CommandBar 一起使用。 它有两个特殊功能。首先，它有一个用于惰性菜单创建的
 * PopupMenuCustomizer。您可以使用 PopupMenuCustomizer 创建它，而不是预先创建可能非常昂贵的菜单。在菜单设置为可见之前调用
 * PopupMenuCustomizer。请注意，当您使用 PopupMenuCustomizer 时，您需要删除之前使用 PopupMenuCustomizer 添加的旧菜单项。否则，您会
 * 看到一个菜单，当您显示它时它会变得越来越长。请参阅下面的示例。
 * <pre><code>
 *   JideMenu jideMenu = new JideMenu("Dynamic");
 *   jideMenu.setPopupMenuCustomizer(new JideMenu.PopupMenuCustomizer(){
 *       public void customize(JPopupMenu menu) {
 *           // 删除所有菜单或设置PopupMenuCustomizer为null都可以解决问题
 *           b1.setPopupMenuCustomizer(null);
 *           // b1.removeAll();
 *           menu.add("item 1");
 *           menu.add("item 2");
 *           menu.add("item 3");
 *       }
 *   });
 * </code></pre>
 */
public class JideMenu extends JMenu implements Alignable {
	private int _preferredPopupHorizontalAlignment = LEFT;
	private int _preferredPopupVerticalAlignment = BOTTOM;

	/** Menu定制器 */
	private PopupMenuCustomizer _customizer;

	/** 菜单弹出位置计算器 */
	private PopupMenuOriginCalculator _originCalculator;

	public static int DELAY = 400;

	private int _orientation;

	public JideMenu() {
		initMenu();
	}

	public JideMenu(String s) {
		super(s);
		initMenu();
	}

	public JideMenu(Action a) {
		super(a);
		initMenu();
	}

	public JideMenu(String s, boolean b) {
		super(s, b);
		initMenu();
	}

	protected void initMenu() {
//        setDelay(DELAY);
		addMenuListener(new MenuListener() {
			public void menuSelected(MenuEvent e) {
				PopupMenuCustomizer menuCreator;
				if ((menuCreator = getPopupMenuCustomizer()) != null) {
					menuCreator.customize(getPopupMenu());
					if (getPopupMenu().getComponentCount() == 0) {
						return;
					}
				}

				PopupMenuCustomizer customizer;
				if ((customizer = getPopupMenuCustomizer()) != null) {
					customizer.customize(getPopupMenu());
				}
			}

			public void menuDeselected(MenuEvent e) {
			}

			public void menuCanceled(MenuEvent e) {
			}
		});
	}

	/**
	 * Checks if the menu is added to a top level menu container. It will be consider as top level menu when <br> 1.
	 * getParent() equals null, or <br> 2. getParent() is not an instance of JPopupMenu <br> Please note, the definition
	 * of topLevelMenu is different from that of JMenu.
	 *
	 * @return true if it's top level menu.
	 */
	@Override
	public boolean isTopLevelMenu() {
		return getParent() == null || !(getParent() instanceof JPopupMenu);//TopLevelMenuContainer || getParent() instanceof JMenuBar;
	}

	/**
	 * 自定义弹出菜单。每次在弹出菜单设置为可见之前都会调用此方法。
	 * <p>
	 * 因此你需要像下面这样实现：
	 * <pre>
	 *  // 删除所有菜单或设置PopupMenuCustomizer为null都可以防止重复添加MenuItem
	 *  b1.setPopupMenuCustomizer(null);
	 *  // b1.removeAll();
	 *  // 添加MenuItem...
	 * </pre>
	 */
	public interface PopupMenuCustomizer {
		void customize(JPopupMenu menu);
	}

	/**
	 * 如果指定，计算弹出菜单的原点。
	 */
	public interface PopupMenuOriginCalculator {
		Point getPopupMenuOrigin(JideMenu menu);
	}

	/**
	 * Gets the PopupMenuOriginCalculator or <code>null</code>, if none has been specified.
	 *
	 * @return the calculator
	 */
	public PopupMenuOriginCalculator getOriginCalculator() {
		return _originCalculator;
	}

	/**
	 * Sets the PopupMenuOriginCalculator that will be used to determine the popup menu origin.
	 *
	 * @param originCalculator the calculator
	 */
	public void setOriginCalculator(PopupMenuOriginCalculator originCalculator) {
		this._originCalculator = originCalculator;
	}

	/**
	 * Gets the PopupMenuCustomizer.
	 *
	 * @return the PopupMenuCustomizer.
	 */
	public PopupMenuCustomizer getPopupMenuCustomizer() {
		return _customizer;
	}

	/**
	 * Sets the PopupMenuCustomizer. PopupMenuCustomizer can be used to do lazy menu creation. If you put code in the
	 * MenuCreator, it won't be called until before the menu is set visible.
	 * <p/>
	 * PopupMenuCustomizer has a customize method. The popup menu of this menu will be passed in. You can
	 * add/remove/change the menu items in customize method. For example, instead of
	 * <code><pre>
	 * JideMenu menu = new JideMenu();
	 * menu.add(new JMenuItem("..."));
	 * menu.add(new JMenuItem("..."));
	 * </pre></code>
	 * You can do
	 * <code><pre>
	 * JideMenu menu = new JideMenu();
	 * menu.setPopupMenuCustomzier(new JideMenu.PopupMenuCustomizer() {
	 *     void customize(JPopupMenu popupMenu) {
	 *         poupMenu.removeAll();
	 *         popupMenu.add(new JMenuItem("..."));
	 *         popupMenu.add(new JMenuItem("..."));
	 *     }
	 * }
	 * </pre></code>
	 * If the menu is never used, the two add methods will never be called thus improve the performance.
	 *
	 * @param customizer the popup menu customizer
	 */
	public void setPopupMenuCustomizer(PopupMenuCustomizer customizer) {
		_customizer = customizer;
	}

	@Override
	protected Point getPopupMenuOrigin() {
		if (_originCalculator != null) {
			return _originCalculator.getPopupMenuOrigin(this);
		}
		int x;
		int y;
		JPopupMenu pm = getPopupMenu();

		// Figure out the sizes needed to calculate the menu position
		Dimension s = getSize();
		Dimension pmSize = pm.getPreferredSize();

		Point position = getLocationOnScreen();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		GraphicsConfiguration gc = getGraphicsConfiguration();
		Rectangle screenBounds = new Rectangle(toolkit.getScreenSize());
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gds = ge.getScreenDevices();
		for (GraphicsDevice gd : gds) {
			if (gd.getType() == GraphicsDevice.TYPE_RASTER_SCREEN) {
				GraphicsConfiguration dgc = gd.getDefaultConfiguration();
				if (dgc.getBounds().contains(position)) {
					gc = dgc;
					break;
				}
			}
		}

		if (gc != null) {
			screenBounds = gc.getBounds();
			// take screen insets (e.g. task bar) into account
			Insets screenInsets = toolkit.getScreenInsets(gc);

			screenBounds.width -= Math.abs(screenInsets.left + screenInsets.right);
			screenBounds.height -= Math.abs(screenInsets.top + screenInsets.bottom);
			position.x -= Math.abs(screenInsets.left);
			position.y -= Math.abs(screenInsets.top);
		}

		Container parent = getParent();
		if (parent instanceof JPopupMenu) {
			// We are a sub-menu (pull-right)
			int xOffset = UIDefaultsLookup.getInt("Menu.submenuPopupOffsetX");
			int yOffset = UIDefaultsLookup.getInt("Menu.submenuPopupOffsetY");

			if (this.getComponentOrientation().isLeftToRight()) {
				if (UIUtil.getOrientationOf(this) == HORIZONTAL) {
					// First determine x:
					x = s.width + xOffset;   // Prefer placement to the right
					if (position.x + x + pmSize.width >= screenBounds.width + screenBounds.x &&
							// popup doesn't fit - place it wherever there's more room
							screenBounds.width - s.width < 2 * (position.x - screenBounds.x)) {

						x = -xOffset - pmSize.width;
					}
				} else {
					// First determine x:
					x = s.width + xOffset;   // Prefer placement to the right
					if (position.x + x + pmSize.width >= screenBounds.width + screenBounds.x &&
							// popup doesn't fit - place it wherever there's more room
							screenBounds.width - s.width < 2 * (position.x - screenBounds.x)) {

						x = -xOffset - pmSize.width;
					}
				}
			} else {
				// First determine x:
				x = -xOffset - pmSize.width; // Prefer placement to the left
				if (position.x + x < screenBounds.x &&
						// popup doesn't fit - place it wherever there's more room
						screenBounds.width - s.width > 2 * (position.x -
								screenBounds.x)) {

					x = s.width + xOffset;
				}
			}
			// Then the y:
			y = yOffset;                     // Prefer dropping down
			if (position.y + y + pmSize.height >= screenBounds.height + screenBounds.y &&
					// popup doesn't fit - place it wherever there's more room
					screenBounds.height - s.height < 2 * (position.y - screenBounds.y)) {

				y = s.height - yOffset - pmSize.height;
			}
		} else {
			// We are a top level menu (pull-down)
			int xOffset = UIDefaultsLookup.getInt("Menu.menuPopupOffsetX");
			int yOffset = UIDefaultsLookup.getInt("Menu.menuPopupOffsetY");

			if (this.getComponentOrientation().isLeftToRight()) {
				if (UIUtil.getOrientationOf(this) == HORIZONTAL) {
					// First determine the x:
					if (getPreferredPopupHorizontalAlignment() == LEFT) {
						x = xOffset;                   // Extend to the right
						if (position.x + x + pmSize.width >= screenBounds.width
								+ screenBounds.x &&
								// popup doesn't fit - place it wherever there's more room
								screenBounds.width - s.width < 2 * (position.x
										- screenBounds.x)) {

							x = s.width - xOffset - pmSize.width;
						}
					} else {
						x = -pmSize.width + xOffset + s.width;                   // align right
						if (position.x + x < screenBounds.x) {
							x = screenBounds.x - position.x;
						}
					}
				} else {
					// First determine the x:
					x = 1 - xOffset - pmSize.width; // Extend to the left
					if (position.x + x < screenBounds.x &&
							// popup doesn't fit - place it wherever there's more room
							screenBounds.width - s.width > 2 * (position.x
									- screenBounds.x)) {

						x = s.width + xOffset - 1;
					}
				}
			} else {
				// First determine the x:
				if (getPreferredPopupHorizontalAlignment() == LEFT) {
					x = s.width - xOffset - pmSize.width; // Extend to the left
					if (position.x + x < screenBounds.x &&
							// popup doesn't fit - place it wherever there's more room
							screenBounds.width - s.width > 2 * (position.x
									- screenBounds.x)) {

						x = xOffset;
					}
				} else {
					x = xOffset;
				}
			}

			// Then the y:
			if (UIUtil.getOrientationOf(this) == HORIZONTAL) {
				y = s.height + yOffset - 1;    // Prefer dropping down
				if (getPreferredPopupVerticalAlignment() == TOP || // If forced to be on TOP
						(position.y + y + pmSize.height >= screenBounds.height &&
								// ...or popup doesn't fit - place it wherever there's more room
								screenBounds.height - s.height < 2 * (position.y
										- screenBounds.y))) {

					y = 1 - yOffset - pmSize.height;   // Otherwise drop 'up'
				}
			} else {
				y = -yOffset;    // Prefer dropping up
				if (position.y + y + pmSize.height >= screenBounds.height &&
						// popup doesn't fit - place it wherever there's more room
						screenBounds.height - s.height < 2 * (position.y
								- screenBounds.y)) {

					y = -yOffset - pmSize.height;   // Otherwise drop 'up'
				}
			}
		}

		return new Point(x, y);
	}

	/**
	 * Checks if the
	 *
	 * @return false if it's top level menu. Otherwise, it will return what super.isOpaque().
	 */
	@Override
	public boolean isOpaque() {
		return SystemInfo.isMacOSX() && isSelected() ? super.isOpaque() : !isTopLevelMenu() && super.isOpaque();
	}

	public boolean originalIsOpaque() {
		return super.isOpaque();
	}

	protected void hideMenu() {
		MenuSelectionManager msm = MenuSelectionManager.defaultManager();
		msm.clearSelectedPath();
	}

	public int getPreferredPopupHorizontalAlignment() {
		return _preferredPopupHorizontalAlignment;
	}

	public void setPreferredPopupHorizontalAlignment(int preferredPopupHorizontalAlignment) {
		_preferredPopupHorizontalAlignment = preferredPopupHorizontalAlignment;
	}

	public int getPreferredPopupVerticalAlignment() {
		return _preferredPopupVerticalAlignment;
	}

	public void setPreferredPopupVerticalAlignment(int preferredPopupVerticalAlignment) {
		_preferredPopupVerticalAlignment = preferredPopupVerticalAlignment;
	}

	public boolean supportVerticalOrientation() {
		return true;
	}

	public boolean supportHorizontalOrientation() {
		return true;
	}

	public void setOrientation(int orientation) {
		int old = _orientation;
		if (old != orientation) {
			_orientation = orientation;
			firePropertyChange(PROPERTY_ORIENTATION, old, orientation);
		}
	}

	public int getOrientation() {
		return _orientation;
	}

	private static JideMenu _pendingMenu;

	private static HideTimer _timer;

	// use this flag to disable the hide timer as there are quite a few bugs on it that we don't know how to solve.
	private static final boolean DISABLE_TIMER = true;

	@Override
	public void setPopupMenuVisible(boolean b) {
		PopupMenuCustomizer menuCreator;
		if (b && (menuCreator = getPopupMenuCustomizer()) != null) {
			menuCreator.customize(getPopupMenu());
		}

		PopupMenuCustomizer customizer;
		if (b && (customizer = getPopupMenuCustomizer()) != null) {
			customizer.customize(getPopupMenu());
			if (shouldHidePopupMenu()) {
				return;
			}
		} else if (b && shouldHidePopupMenu()) {
			return;
		}


		if (!DISABLE_TIMER) {
			if (isTopLevelMenu()) {
				setPopupMenuVisibleImmediately(b);
			} else {
				if (b) {
//                    System.out.println("show new menu");
					stopTimer();
					setPopupMenuVisibleImmediately(b);
				} else {
					// HACK: check if the calling stack has clearSelectedPath method.
					StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
					for (StackTraceElement stackTraceElement : stackTraceElements) {
						if (stackTraceElement.getMethodName().equals("clearSelectedPath")) {
							setPopupMenuVisibleImmediately(b);
							return;
						}
					}
					startTimer();
				}
			}
		} else {
			setPopupMenuVisibleImmediately(b);
		}
	}

	/**
	 * Check if the popup menu should stay hidden although {@link #setPopupMenuVisible(boolean)} is invoked.
	 * <p/>
	 * The default implementation is to check if it contains any menu items. You could override this method to change the
	 * default behavior.
	 *
	 * @return true if the popup menu should stay invisible. Otherwise false.
	 */
	protected boolean shouldHidePopupMenu() {
		return getPopupMenu().getComponentCount() == 0;
	}

	void setPopupMenuVisibleImmediately(boolean b) {
		super.setPopupMenuVisible(b);
	}

	private class HideTimer extends Timer implements ActionListener {
		public HideTimer() {
			super(DELAY + 300, null);
			addActionListener(this);
			setRepeats(false);
		}

		public void actionPerformed(ActionEvent e) {
			stopTimer();
		}
	}

	private void startTimer() {
//        System.out.println("timer started");
		if (_timer != null) {
			stopTimer();
		}
		_pendingMenu = this;
		_timer = new HideTimer();
		_timer.start();
	}

	private void stopTimer() {
		if (_timer != null) {
//            System.out.println("timer stopped");
			if (_pendingMenu != null) {
//                System.out.println("hidding pending menu");
				_pendingMenu.setPopupMenuVisibleImmediately(false);
				_pendingMenu = null;
			}
			_timer.stop();
			_timer = null;
		}
	}

}
