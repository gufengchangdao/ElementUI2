/*
 * @(#)PortingUtils.java 4/12/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */
package com.element.util;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.util.Arrays;

/**
 * 保留所有 1.4/1.3 不同内容的类。
 */
public class PortingUtil {
	private static final GraphicsEnvironment GRAPHICS_ENVIRONMENT =
			GraphicsEnvironment.getLocalGraphicsEnvironment();

	/**
	 * To make sure the rectangle is within the screen bounds.
	 *
	 * @param invoker          the invoker component
	 * @param rect             the rectangle
	 * @param useInvokerDevice the flag to return invoker device or not
	 * @return the rectangle that is in the screen bounds.
	 * @since 3.4.1
	 */
	public static Rectangle containsInScreenBounds(Component invoker, Rectangle rect, boolean useInvokerDevice) {
		Rectangle screenBounds = getScreenBounds(invoker, useInvokerDevice);
		Point p = rect.getLocation();
		if (p.x + rect.width > screenBounds.x + screenBounds.width) {
			p.x = screenBounds.x + screenBounds.width - rect.width;
		}
		if (p.y + rect.height > screenBounds.y + screenBounds.height) {
			p.y = screenBounds.y + screenBounds.height - rect.height;
		}
		if (p.x < screenBounds.x) {
			p.x = screenBounds.x;
		}
		if (p.y < screenBounds.y) {
			p.y = screenBounds.y;
		}
		return new Rectangle(p, rect.getSize());
	}

	/**
	 * To make sure the rectangle has overlap with the screen bounds.
	 *
	 * @param invoker the invoker component
	 * @param rect    the rectangle
	 * @return the rectangle that has overlap with the screen bounds.
	 */
	public static Rectangle overlapWithScreenBounds(Component invoker, Rectangle rect) {
		Rectangle screenBounds = getScreenBounds(invoker);
		Point p = rect.getLocation();
		if (p.x > screenBounds.x + screenBounds.width) {
			p.x = screenBounds.x + screenBounds.width - rect.width;
		}
		if (p.y > screenBounds.y + screenBounds.height) {
			p.y = screenBounds.y + screenBounds.height - rect.height;
		}
		if (p.x + rect.width < screenBounds.x) {
			p.x = screenBounds.x;
		}
		if (p.y + rect.height < screenBounds.y) {
			p.y = screenBounds.y;
		}
		return new Rectangle(p, rect.getSize());
	}

	/**
	 * Gets the screen size. In JDK1.4+, the returned size will exclude task bar area on Windows OS.
	 *
	 * @param invoker the invoker component
	 * @return the screen size.
	 */
	public static Dimension getScreenSize(Component invoker) {
		// to handle multi-display case
		Dimension screenSize = getScreenBounds().getSize();

		// jdk1.4 only
		if (invoker != null && invoker.getGraphicsConfiguration() != null) {
			Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(invoker.getGraphicsConfiguration());
			screenSize.width -= insets.left + insets.right;
			screenSize.height -= insets.top + insets.bottom;
		}

		return screenSize;
	}

	/**
	 * Gets the screen size. In JDK1.4+, the returned size will exclude task bar area on Windows OS.
	 *
	 * @param invoker the invoker component
	 * @return the screen size.
	 */
	public static Dimension getLocalScreenSize(Component invoker) {
//      ensureScreenBounds();

		// jdk1.4 only
		if (invoker != null && !(invoker instanceof JApplet) && invoker.getGraphicsConfiguration() != null) {
			// to handle multi-display case
			GraphicsConfiguration gc = invoker.getGraphicsConfiguration();
			Rectangle bounds = gc.getBounds();
			Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);
			bounds.width -= insets.left + insets.right;
			bounds.height -= insets.top + insets.bottom;
			return bounds.getSize();
		} else {
			return getScreenSize(invoker);
		}
	}

	/**
	 * Gets the screen bounds. In JDK1.4+, the returned bounds will exclude task bar area on Windows OS. If the invoker
	 * is null, the whole screen bounds including all display devices will be returned. If the invoker is not null and
	 * the useInvokeDevice flag is true, the screen of the display device for the invoker will be returned.
	 *
	 * @param invoker          the invoker component
	 * @param useInvokerDevice the flag to return invoker device or not
	 * @return the screen bounds.
	 */
	public static Rectangle getScreenBounds(Component invoker, boolean useInvokerDevice) {
		// to handle multi-display case
		Rectangle bounds = (!useInvokerDevice || invoker == null || invoker.getGraphicsConfiguration() == null)
				? new Rectangle(getScreenBounds())
				: invoker.getGraphicsConfiguration().getBounds();

		// jdk1.4 only
		if (invoker != null && invoker.getGraphicsConfiguration() != null) {
			Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(invoker.getGraphicsConfiguration());
			bounds.x += insets.left;
			bounds.y += insets.top;
			bounds.width -= insets.left + insets.right;
			bounds.height -= insets.top + insets.bottom;
		}

		return bounds;
	}

	/**
	 * Gets the screen bounds. In JDK1.4+, the returned bounds will exclude task bar area on Windows OS.
	 * <p/>
	 * By default, it will not use invoker graphic device automatically.
	 *
	 * @param invoker the invoker component
	 * @return the screen bounds.
	 * @see #getScreenBounds(Component, boolean)
	 */
	public static Rectangle getScreenBounds(Component invoker) {
		return getScreenBounds(invoker, false);
	}

	/**
	 * Gets the local monitor's screen bounds.
	 *
	 * @return the screen bounds.
	 */
	public static Rectangle getLocalScreenBounds() {
		Rectangle bounds;
		try {
			// use this because it takes into account areas
			// like the taskbar, but it can throw
			// a "Window must not be zero" if there are 3 monitors
			// on Linux with some newer Java versions, see
			// https://github.com/lbalazscs/Pixelitor/issues/15
			bounds = GRAPHICS_ENVIRONMENT.getMaximumWindowBounds();
		} catch (Exception e) {
			return new Rectangle(new Point(0, 0), Toolkit.getDefaultToolkit().getScreenSize());
		}

		return bounds;
	}

	private static Rectangle getScreenBounds() {
		Rectangle SCREEN_BOUNDS = new Rectangle();
		GraphicsDevice[] gs = GRAPHICS_ENVIRONMENT.getScreenDevices();
		for (GraphicsDevice gd : gs) {
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			SCREEN_BOUNDS = SCREEN_BOUNDS.union(gc.getBounds());
		}
		return SCREEN_BOUNDS;
	}

	/**
	 * Ensures the rectangle is visible on the screen.
	 *
	 * @param invoker the invoking component
	 * @param bounds  the input bounds
	 * @return the modified bounds.
	 */
	public static Rectangle ensureVisible(Component invoker, Rectangle bounds) {
		// this is fast. Only if it is outside this bounds, we try the more expensive one.
		Rectangle mainScreenBounds = PortingUtil.getLocalScreenBounds();
		if (!mainScreenBounds.contains(bounds.getLocation())) {
			Rectangle screenBounds = PortingUtil.getScreenBounds(invoker, false);
			if (bounds.x > screenBounds.x + screenBounds.width || bounds.x < screenBounds.x) {
				bounds.x = screenBounds.x;
			}
			if (bounds.y > screenBounds.y + screenBounds.height || bounds.y < screenBounds.y) {
				bounds.y = screenBounds.y;
			}
		}
		return bounds;
	}

	/**
	 * Modifies the position of rect so that it is completely on screen if that is possible. By default, it will allow
	 * the rect to cross two screens. You can call {@link #ensureOnScreen(Rectangle, boolean)} and set the
	 * second parameter to false if you don't want to allow that case.
	 *
	 * @param rect The rectangle to be moved to a single screen
	 * @return rect after its position has been modified
	 */
	public static Rectangle ensureOnScreen(Rectangle rect) {
		return ensureOnScreen(rect, true);
	}

	/**
	 * Modifies the position of rect so that it is completely on screen if that is possible.
	 *
	 * @param rect             The rectangle to be moved to a single screen
	 * @param allowCrossScreen a flag to allow or disallow when the rect is cross two screens.
	 * @return rect after its position has been modified
	 */
	public static Rectangle ensureOnScreen(Rectangle rect, boolean allowCrossScreen) {
		// optimize it so that it is faster for most cases
		Rectangle localScreenBounds = getLocalScreenBounds();
		if (localScreenBounds.contains(rect)) {
			return rect;
		}

		final Rectangle[] SCREENS = getScreens();

		// check if rect is total on screen
		if (allowCrossScreen && getScreenArea().contains(rect)) return rect;
		// see if the top left is on any of the screens
		Rectangle containingScreen = null;
		Point rectPos = rect.getLocation();
		for (Rectangle screenBounds : SCREENS) {
			if (screenBounds.contains(rectPos)) {
				containingScreen = screenBounds;
				break;
			}
		}
		// if not see if rect partial on any screen
		for (Rectangle screenBounds : SCREENS) {
			if (screenBounds.intersects(rect)) {
				containingScreen = screenBounds;
				break;
			}
		}
		// check if it was on any screen
		if (containingScreen == null) {
			// it was not on any of the screens so center it on the first screen
			rect.x = (SCREENS[0].width - rect.width) / 2;
			rect.y = (SCREENS[0].height - rect.height) / 2;
		} else {
			// move rect so it is completely on a single screen
			// check X
			int rectRight = rect.x + rect.width;
			int screenRight = containingScreen.x + containingScreen.width;
			if (rectRight > screenRight) {
				rect.x = screenRight - rect.width;
			}
			if (rect.x < containingScreen.x) rect.x = containingScreen.x;
			// check Y
			int rectBottom = rect.y + rect.height;
			int screenBottom = containingScreen.y + containingScreen.height;
			if (rectBottom > screenBottom) {
				rect.y = screenBottom - rect.height;
			}
			if (rect.y < containingScreen.y) rect.y = containingScreen.y;
			// return corrected rect
		}
		return rect;
	}

	/**
	 * Gets the screen bounds that contains the rect. The screen bounds consider the screen insets if any.
	 *
	 * @param rect           the rect of the component.
	 * @param considerInsets if consider the insets. The insets is for thing like Windows Task Bar.
	 * @return the screen bounds that contains the rect.
	 */
	public static Rectangle getContainingScreenBounds(Rectangle rect, boolean considerInsets) {
		// check if rect is total on screen
//        if (SCREEN_AREA.contains(rect)) return SCREEN_AREA;

		final Insets[] INSETS = getInsets();
		final Rectangle[] SCREENS = getScreens();

		// see if the top left is on any of the screens
		Rectangle containingScreen = null;
		Insets insets = null;
		Point rectPos = rect.getLocation();
		for (int i = 0; i < SCREENS.length; i++) {
			Rectangle screenBounds = SCREENS[i];
			if (screenBounds.contains(rectPos)) {
				containingScreen = screenBounds;
				insets = INSETS[i];
				break;
			}
		}
		// if not see if rect partial on any screen
		for (int i = 0; i < SCREENS.length; i++) {
			Rectangle screenBounds = SCREENS[i];
			if (screenBounds.intersects(rect)) {
				containingScreen = screenBounds;
				insets = INSETS[i];
				break;
			}
		}

		// fall back to the first screen
		if (containingScreen == null) {
			containingScreen = SCREENS[0];
			insets = INSETS[0];
		}

		Rectangle bounds = new Rectangle(containingScreen);
		if (considerInsets && insets != null) {
			bounds.x += insets.left;
			bounds.y += insets.top;
			bounds.width -= insets.left + insets.right;
			bounds.height -= insets.top + insets.bottom;
		}
		return bounds;
	}


	/**
	 * Get screen area of all monitors.
	 *
	 * @return Union of all screens
	 */
	public static Area getScreenArea() {
		return Arrays.stream(GRAPHICS_ENVIRONMENT.getScreenDevices()).reduce(new Area(), (area, device) -> {
					GraphicsConfiguration configuration = device.getDefaultConfiguration();
					Rectangle screenBounds = configuration.getBounds();
					return new Area(screenBounds);
				}, (area, area2) -> {
					area.add(area2);
					return area;
				}
		);
	}

	private static Rectangle[] getScreens() {
		return Arrays.stream(GRAPHICS_ENVIRONMENT.getScreenDevices()).map(device -> {
			GraphicsConfiguration configuration = device.getDefaultConfiguration();
			return configuration.getBounds();
		}).toArray(Rectangle[]::new);
	}

	private static Insets[] getInsets() {
		return Arrays.stream(GRAPHICS_ENVIRONMENT.getScreenDevices()).map(device -> {
			GraphicsConfiguration configuration = device.getDefaultConfiguration();
			return Toolkit.getDefaultToolkit().getScreenInsets(configuration);
		}).toArray(Insets[]::new);
	}


	/**
	 * Notifies user something is wrong. We use Toolkit beep method by default.
	 */
	public static void notifyUser() {
		notifyUser(null);
	}

	/**
	 * Notifies user something is wrong. We use Toolkit beep method by default.
	 *
	 * @param component the component that has the error or null if the error is not associated with any component.
	 */
	public static void notifyUser(Component component) {
		String beep = SecurityUtils.getProperty("jide.beepNotifyUser", "true");
		if ("true".equals(beep)) {
			UIManager.getLookAndFeel().provideErrorFeedback(component);
		}
	}

	/**
	 * Checks the prerequisite needed by JIDE demos. If the prerequisite doesn't meet, it will prompt a message box and
	 * exit.
	 */
	public static void prerequisiteChecking() {
		if (SystemInfo.isMacOSX()) { // set special properties for Mac OS X
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("apple.awt.brushMetalLook", "true");
		}
	}
}