package com.element.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * AutoRepeatButtonUtils是一个实用类，可以使在按下按钮期间连续自动触发所有动作事件。达到类似JSpinner中按钮的功能
 * 要在任何按钮上启用此功能，只需调用{@link #install(AbstractButton)} 或 {@link #install(AbstractButton, int, int)}
 */
public class AutoRepeatButtonUtil implements ActionListener, MouseListener {
	public static String AUTO_REPEAT = "AutoRepeat";
	public static String CLIENT_PROPERTY_AUTO_REPEAT = "AutoRepeat.AutoRepeatButtonUtils";
	public static int DEFAULT_DELAY = 200;
	public static int DEFAULT_INITIAL_DELAY = 500;

	private Timer _timer = null;
	private AbstractButton _button;

	/**
	 * Enable auto-repeat feature on the button.
	 *
	 * @param button the button.
	 */
	public static void install(AbstractButton button) {
		uninstall(button);
		new AutoRepeatButtonUtil().installListeners(button, DEFAULT_DELAY, DEFAULT_INITIAL_DELAY);
	}

	/**
	 * @param button       按钮
	 * @param delay        动作事件之间的延迟，以毫秒为单位
	 * @param initialDelay 初始延迟，以毫秒为单位。它是从按下鼠标到第一个动作事件
	 */
	public static void install(AbstractButton button, int delay, int initialDelay) {
		uninstall(button);
		new AutoRepeatButtonUtil().installListeners(button, delay, initialDelay);
	}

	/**
	 * Disabled the auto-repeat feature on the button which called install before.
	 *
	 * @param button the button that has auto-repeat feature.
	 */
	public static void uninstall(AbstractButton button) {
		Object clientProperty = button.getClientProperty(CLIENT_PROPERTY_AUTO_REPEAT);
		if (clientProperty instanceof AutoRepeatButtonUtil) {
			((AutoRepeatButtonUtil) clientProperty).uninstallListeners();
		}
	}

	protected void installListeners(AbstractButton button, int delay, int initialDelay) {
		_button = button;
		// 使用客户端属性便于卸载的时候找到该类的实例对象并移除该监听器
		button.putClientProperty(CLIENT_PROPERTY_AUTO_REPEAT, this);
		button.addMouseListener(this);

		_timer = new Timer(delay, this);
		_timer.setInitialDelay(initialDelay);
		_timer.setRepeats(true);
	}

	protected void uninstallListeners() {
		if (_button != null) {
			_button.putClientProperty(CLIENT_PROPERTY_AUTO_REPEAT, null);
			_button.removeMouseListener(this);
			_button = null;
		}
		if (_timer != null) {
			_timer.stop();
			_timer = null;
		}
	}

	public void mousePressed(MouseEvent e) {
		_timer.start();
	}

	public void mouseReleased(MouseEvent e) {
		_timer.stop();
	}

	public void mouseExited(MouseEvent e) {
		_timer.stop();
	}


	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent event) {
		// Guaranteed to return a non-null array
		if (!_button.getModel().isPressed()) {
			return;
		}
		ActionListener[] listeners = _button.getActionListeners();
		ActionEvent e = null;
		// 从后到前处理监听器，通知那些对此事件感兴趣的监听器
		for (int i = listeners.length - 1; i >= 0; i--) {
			ActionListener listener = listeners[i];
			if (e == null) {
				String actionCommand = event.getActionCommand();
				if (actionCommand == null) {
					actionCommand = _button.getActionCommand();
				}
				e = new ActionEvent(_button,
						ActionEvent.ACTION_PERFORMED,
						actionCommand,
						event.getWhen(),
						event.getModifiers());
			}
			listener.actionPerformed(e);
		}
	}
}
