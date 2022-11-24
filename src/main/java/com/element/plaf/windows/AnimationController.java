/*
 * @(#)AnimationController.java 4/11/2017
 *
 * Copyright 2002 - 2017 JIDE Software Inc. All rights reserved.
 */

package com.element.plaf.windows;

import sun.awt.AppContext;
import sun.security.action.GetBooleanAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Copied from JDK source code and modified to provide additional integration between JIDE components and native windows
 * L&F.
 */
class AnimationController implements ActionListener, PropertyChangeListener {

	@SuppressWarnings("removal")
	private static final boolean VISTA_ANIMATION_DISABLED =
			AccessController.doPrivileged(new GetBooleanAction("swing.disablevistaanimation"));

	private final static Object ANIMATION_CONTROLLER_KEY =
			new StringBuilder("ANIMATION_CONTROLLER_KEY");

	private final Map<JComponent, Map<TMSchema.Part, AnimationState>> animationStateMap =
			new WeakHashMap<>();

	//this timer is used to cause repaint on animated components
	//30 repaints per second should give smooth animation affect
	private final Timer timer =
			new Timer(1000 / 30, this);

	private static synchronized AnimationController getAnimationController() {
		AppContext appContext = AppContext.getAppContext();
		Object obj = appContext.get(ANIMATION_CONTROLLER_KEY);
		if (obj == null) {
			obj = new AnimationController();
			appContext.put(ANIMATION_CONTROLLER_KEY, obj);
		}
		return (AnimationController) obj;
	}

	private AnimationController() {
		timer.setRepeats(true);
		timer.setCoalesce(true);
		//we need to dispose the controller on l&f change
		UIManager.addPropertyChangeListener(this);
	}

	private static void triggerAnimation(JComponent c,
	                                     TMSchema.Part part, TMSchema.State newState) {
		if (c instanceof JTabbedPane
				|| part == TMSchema.Part.TP_BUTTON) {
			//idk: we can not handle tabs animation because
			//the same (component,part) is used to handle all the tabs
			//and we can not track the states
			//Vista theme might have transition duration for toolbar buttons
			//but native application does not seem to animate them
			return;
		}
		AnimationController controller =
				AnimationController.getAnimationController();
		TMSchema.State oldState = controller.getState(c, part);
		if (oldState != newState) {
			controller.putState(c, part, newState);
			if (newState == TMSchema.State.DEFAULTED) {
				// it seems for DEFAULTED button state Vista does animation from
				// HOT
				oldState = TMSchema.State.HOT;
			}
			if (oldState != null) {
				long duration;
				if (newState == TMSchema.State.DEFAULTED) {
					//Only button might have DEFAULTED state
					//idk: do not know how to get the value from Vista
					//one second seems plausible value
					duration = 1000;
				} else {
					duration = XPStyle.getXP().getThemeTransitionDuration(
							c, part,
							normalizeState(oldState),
							normalizeState(newState),
							TMSchema.Prop.TRANSITIONDURATIONS);
				}
				controller.startAnimation(c, part, oldState, newState, duration);
			}
		}
	}

	// for scrollbar up, down, left and right button pictures are
	// defined by states.  It seems that theme has duration defined
	// only for up button states thus we doing this translation here.
	private static TMSchema.State normalizeState(TMSchema.State state) {
		TMSchema.State rv;
		switch (state) {
			case DOWNPRESSED:
				/* falls through */
			case LEFTPRESSED:
				/* falls through */
			case RIGHTPRESSED:
				rv = TMSchema.State.UPPRESSED;
				break;

			case DOWNDISABLED:
				/* falls through */
			case LEFTDISABLED:
				/* falls through */
			case RIGHTDISABLED:
				rv = TMSchema.State.UPDISABLED;
				break;

			case DOWNHOT:
				/* falls through */
			case LEFTHOT:
				/* falls through */
			case RIGHTHOT:
				rv = TMSchema.State.UPHOT;
				break;

			case DOWNNORMAL:
				/* falls through */
			case LEFTNORMAL:
				/* falls through */
			case RIGHTNORMAL:
				rv = TMSchema.State.UPNORMAL;
				break;

			default:
				rv = state;
				break;
		}
		return rv;
	}

	private synchronized TMSchema.State getState(JComponent component, TMSchema.Part part) {
		TMSchema.State rv = null;
		Object tmpObject =
				component.getClientProperty(PartUIClientPropertyKey.getKey(part));
		if (tmpObject instanceof TMSchema.State) {
			rv = (TMSchema.State) tmpObject;
		}
		return rv;
	}

	private synchronized void putState(JComponent component, TMSchema.Part part,
	                                   TMSchema.State state) {
		component.putClientProperty(PartUIClientPropertyKey.getKey(part),
				state);
	}

	private synchronized void startAnimation(JComponent component,
	                                         TMSchema.Part part,
	                                         TMSchema.State startState,
	                                         TMSchema.State endState,
	                                         long millis) {
		boolean isForwardAndReverse = false;
		if (endState == TMSchema.State.DEFAULTED) {
			isForwardAndReverse = true;
		}
		Map<TMSchema.Part, AnimationState> map = animationStateMap.get(component);
		if (millis <= 0) {
			if (map != null) {
				map.remove(part);
				if (map.size() == 0) {
					animationStateMap.remove(component);
				}
			}
			return;
		}
		if (map == null) {
			map = new EnumMap<>(TMSchema.Part.class);
			animationStateMap.put(component, map);
		}
		map.put(part,
				new AnimationState(startState, millis, isForwardAndReverse));
		if (!timer.isRunning()) {
			timer.start();
		}
	}

	static void paintSkin(JComponent component, XPStyle.Skin skin,
	                      Graphics g, int dx, int dy, int dw, int dh, TMSchema.State state) {
		if (VISTA_ANIMATION_DISABLED) {
			skin.paintSkinRaw(g, dx, dy, dw, dh, state);
			return;
		}
		triggerAnimation(component, skin.part, state);
		AnimationController controller = getAnimationController();
		synchronized (controller) {
			AnimationState animationState = null;
			Map<TMSchema.Part, AnimationState> map =
					controller.animationStateMap.get(component);
			if (map != null) {
				animationState = map.get(skin.part);
			}
			if (animationState != null) {
				animationState.paintSkin(skin, g, dx, dy, dw, dh, state);
			} else {
				skin.paintSkinRaw(g, dx, dy, dw, dh, state);
			}
		}
	}

	/**
	 * As of Java 10, com.sun.java.swing.plaf.windows.WindowsLookAndFeel is no longer available on macOS thus
	 * "instanceof WindowsLookAndFeel" directives will result in a NoClassDefFoundError during runtime. This method
	 * was introduced to avoid this exception.
	 *
	 * @param lnf
	 * @return true if it is a WindowsLookAndFeel.
	 */
	public static boolean isWindowsLookAndFeel(LookAndFeel lnf) {
		if (lnf == null) {
			return false;
		} else {
			try {
				Class c = Class.forName("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				return c.isInstance(lnf);
			} catch (ClassNotFoundException cnfe) {
				// if it is not possible to load the Windows LnF class, the
				// given lnf instance cannot be an instance of the Windows
				// LnF class
				return false;
			}
		}
	}

	public synchronized void propertyChange(PropertyChangeEvent e) {
		if ("lookAndFeel".equals(e.getPropertyName())
				&& !isWindowsLookAndFeel((LookAndFeel) e.getNewValue())) {
			dispose();
		}
	}

	public synchronized void actionPerformed(ActionEvent e) {
		java.util.List<JComponent> componentsToRemove = null;
		java.util.List<TMSchema.Part> partsToRemove = null;
		for (JComponent component : animationStateMap.keySet()) {
			component.repaint();
			if (partsToRemove != null) {
				partsToRemove.clear();
			}
			Map<TMSchema.Part, AnimationState> map = animationStateMap.get(component);
			if (!component.isShowing()
					|| map == null
					|| map.size() == 0) {
				if (componentsToRemove == null) {
					componentsToRemove = new ArrayList<>();
				}
				componentsToRemove.add(component);
				continue;
			}
			for (TMSchema.Part part : map.keySet()) {
				if (map.get(part).isDone()) {
					if (partsToRemove == null) {
						partsToRemove = new ArrayList<>();
					}
					partsToRemove.add(part);
				}
			}
			if (partsToRemove != null) {
				if (partsToRemove.size() == map.size()) {
					//animation is done for the component
					if (componentsToRemove == null) {
						componentsToRemove = new ArrayList<>();
					}
					componentsToRemove.add(component);
				} else {
					for (TMSchema.Part part : partsToRemove) {
						map.remove(part);
					}
				}
			}
		}
		if (componentsToRemove != null) {
			for (JComponent component : componentsToRemove) {
				animationStateMap.remove(component);
			}
		}
		if (animationStateMap.size() == 0) {
			timer.stop();
		}
	}

	private synchronized void dispose() {
		timer.stop();
		UIManager.removePropertyChangeListener(this);
		synchronized (AnimationController.class) {
			AppContext.getAppContext()
					.put(ANIMATION_CONTROLLER_KEY, null);
		}
	}

	private static class AnimationState {
		private final TMSchema.State startState;

		//animation duration in nanoseconds
		private final long duration;

		//animatin start time in nanoseconds
		private long startTime;

		//direction the alpha value is changing
		//forward  - from 0 to 1
		//!forward - from 1 to 0
		private boolean isForward = true;

		//if isForwardAndReverse the animation continually goes
		//forward and reverse. alpha value is changing from 0 to 1 then
		//from 1 to 0 and so forth
		private boolean isForwardAndReverse;

		private float progress;

		AnimationState(final TMSchema.State startState,
		               final long milliseconds,
		               boolean isForwardAndReverse) {
			assert startState != null && milliseconds > 0;
			assert SwingUtilities.isEventDispatchThread();

			this.startState = startState;
			this.duration = milliseconds * 1000000;
			this.startTime = System.nanoTime();
			this.isForwardAndReverse = isForwardAndReverse;
			progress = 0f;
		}

		private void updateProgress() {
			assert SwingUtilities.isEventDispatchThread();

			if (isDone()) {
				return;
			}
			long currentTime = System.nanoTime();

			progress = ((float) (currentTime - startTime))
					/ duration;
			progress = Math.max(progress, 0); //in case time was reset
			if (progress >= 1) {
				progress = 1;
				if (isForwardAndReverse) {
					startTime = currentTime;
					progress = 0;
					isForward = !isForward;
				}
			}
		}

		void paintSkin(XPStyle.Skin skin, Graphics _g,
		               int dx, int dy, int dw, int dh, TMSchema.State state) {
			assert SwingUtilities.isEventDispatchThread();

			updateProgress();
			if (!isDone()) {
				Graphics2D g = (Graphics2D) _g.create();
				skin.paintSkinRaw(g, dx, dy, dw, dh, startState);
				float alpha;
				if (isForward) {
					alpha = progress;
				} else {
					alpha = 1 - progress;
				}
				g.setComposite(AlphaComposite.SrcOver.derive(alpha));
				skin.paintSkinRaw(g, dx, dy, dw, dh, state);
				g.dispose();
			} else {
				skin.paintSkinRaw(_g, dx, dy, dw, dh, state);
			}
		}

		boolean isDone() {
			assert SwingUtilities.isEventDispatchThread();

			return progress >= 1;
		}
	}

	private static class PartUIClientPropertyKey
			implements UIClientPropertyKey {

		private static final Map<TMSchema.Part, PartUIClientPropertyKey> map =
				new EnumMap<>(TMSchema.Part.class);

		static synchronized PartUIClientPropertyKey getKey(TMSchema.Part part) {
			PartUIClientPropertyKey rv = map.get(part);
			if (rv == null) {
				rv = new PartUIClientPropertyKey(part);
				map.put(part, rv);
			}
			return rv;
		}

		private final TMSchema.Part part;

		private PartUIClientPropertyKey(TMSchema.Part part) {
			this.part = part;
		}

		public String toString() {
			return part.toString();
		}
	}
}
