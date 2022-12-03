/*
 * @(#)DelegateAction.java 10/2/2006
 *
 * Copyright 2002 - 2006 JIDE Software Inc. All rights reserved.
 */

package com.element.swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DelegateAction 是一个特殊的 AbstractAction，它可以做一些事情，然后根据{@link #delegateActionPerformed(ActionEvent)}的返回值委
 * 托给另一个动作。它有两种用法。首先，您可以使用{@link #replaceAction(JComponent, int, KeyStroke, DelegateAction)}将与指定击键关
 * 联的操作替换为 DelegateAction。按下击键时将触发 DelegateAction。 DelegateAction 完成后，它可以返回 true 或 false。如果为 false，
 * 则与击键关联的原始操作也将被触发。这解决了JComponent.registerKeyboardAction(ActionListener, String, KeyStroke, int)会替换原来
 * 的动作，使原来的动作永远不会被触发的问题。
 * <p>
 * 使用 DelegateAction 的第二种方法是使用{@link JComponent#registerKeyboardAction(ActionListener, String, KeyStroke, int)}
 * 将操作从一个组件委托给另一个组件。在这种情况下，第一个组件参数上的击键将触发 DelegateAction。如果 DelegateAction 返回 false，将触发
 * 在第二个组件参数上注册的操作。如果你传入{@link PassthroughDelegateAction} ，第二个组件上的注册动作将始终被触发。
 * <p>
 * 请注意，如果您使用相同的击键在同一个组件上多次调用 replaceAction，它将形成一个 DelegateActions 链。在这种情况下，第一次调用将是第一个
 * DelegateAction。换句话说，第一个将具有最高优先级并且将首先被触发。理想情况下，我们应该为每个 DelegateAction 分配一个优先级。但是为了
 * 简单起见，我们决定暂时不这样做。因此，此类尚未准备好用作公共 API。我们必须将其公开，因为 JIDE 中的不同包需要使用它。如果要使用，请谨慎使
 * 用。我们不保证我们不会更改此类的公共方法。
 */
abstract public class DelegateAction extends AbstractAction {
	private Action _action;
	private JComponent _target;

	public DelegateAction() {
	}

	public DelegateAction(Action action) {
		_action = action;
	}

	public DelegateAction(Action action, JComponent target) {
		_action = action;
		_target = target;
	}

	/**
	 * Returns true if either delegateIsEnabled or the action is enabled.
	 * <p/>
	 * {@inheritDoc}
	 */
	// Should be final like actionPerformed but not done for backward compatibility.
	@Override
	public boolean isEnabled() {
		return isDelegateEnabled() || (_action != null && _action.isEnabled());
	}

	final public void actionPerformed(ActionEvent e) {
		if (!delegateActionPerformed(e)) {
			if (_action != null) {
				if (_target == null) {
					_action.actionPerformed(e);
				} else {
					_action.actionPerformed(new ActionEvent(getTarget(), e.getID(), e.getActionCommand(), e.getWhen(), e.getModifiers()));
				}
			}
		}
	}

	/**
	 * Checks if an action can be performed. Returns true if delegateActionPerformed would perform an action. Otherwise
	 * returns false.
	 *
	 * @return <code>true</code> if the action can be performed.
	 */
	// Should be abstract like delegateActionPerformed but not done for backward compatibility.
	public boolean isDelegateEnabled() {
		return super.isEnabled();
	}

	/**
	 * Performs an action. Returns true if no further action should be taken for this keystroke. Otherwise, returns
	 * false.
	 *
	 * @param e the action event.
	 * @return true if no further action should be taken for this keystroke. Otherwise, returns false.
	 */
	abstract public boolean delegateActionPerformed(ActionEvent e);

	public static class PassthroughDelegateAction extends DelegateAction {
		@Override
		public boolean delegateActionPerformed(ActionEvent e) {
			return false;
		}

		@Override
		public boolean isDelegateEnabled() {
			return false;
		}
	}

	public static void replaceAction(JComponent component, int condition, KeyStroke keyStroke, DelegateAction delegateAction) {
		replaceAction(component, condition, component, condition, keyStroke, delegateAction);
	}

	public static void replaceAction(JComponent component, int condition, KeyStroke keyStroke, DelegateAction delegateAction, boolean first) {
		replaceAction(component, condition, component, condition, keyStroke, delegateAction, first);
	}

	public static void replaceAction(JComponent component, int condition, JComponent target, int targetCondition,
	                                 KeyStroke keyStroke) {
		replaceAction(component, condition, target, targetCondition, keyStroke, new PassthroughDelegateAction(), false);
	}

	public static void replaceAction(JComponent component, int condition, JComponent target, int targetCondition,
	                                 KeyStroke keyStroke, DelegateAction delegateAction) {
		replaceAction(component, condition, target, targetCondition, keyStroke, delegateAction, false);
	}

	public static void replaceAction(JComponent component, int condition, JComponent target, int targetCondition,
	                                 KeyStroke keyStroke, DelegateAction delegateAction, boolean first) {
		ActionListener action = component.getActionForKeyStroke(keyStroke);
		if (action != delegateAction && action instanceof Action) {
			if (!first && action instanceof DelegateAction d) {
				Action childAction = d.getAction();

				// 判断是否已经有该delegateAction了，有的话直接退出
				while (childAction != null) {
					if (childAction == delegateAction) {
						return;
					}
					if (childAction instanceof DelegateAction) {
						childAction = ((DelegateAction) childAction).getAction();
					} else {
						childAction = null;
					}
				}
				delegateAction.setAction(d.getAction());
				d.setAction(delegateAction);
				delegateAction = d;
			} else {
				delegateAction.setAction((Action) action);
			}
		}

		if (target != component) {
			delegateAction.setTarget(target);
			replaceAction(component, condition, keyStroke, delegateAction);
		} else {
			Object actionCommand = target.getInputMap(targetCondition).get(keyStroke);
			if (actionCommand == null) {
				component.registerKeyboardAction(delegateAction, keyStroke, condition);
			} else {
				component.getActionMap().put(actionCommand, delegateAction);
			}
		}
	}

	public static void restoreAction(JComponent component, int condition, KeyStroke keyStroke) {
		if (component == null) {
			return;
		}
		ActionListener action = component.getActionForKeyStroke(keyStroke);
		if (action instanceof DelegateAction) {
			Action actualAction = ((DelegateAction) action).getAction();
			if (actualAction != null) {
				component.registerKeyboardAction(actualAction, keyStroke, condition);
			} else {
				component.unregisterKeyboardAction(keyStroke);
			}
		}
	}

	public static void restoreAction(JComponent component, int condition, KeyStroke keyStroke, Class<?> actionClass) {
		ActionListener action = component.getActionForKeyStroke(keyStroke);
		ActionListener parent = action;
		ActionListener top = action;
		while (action instanceof DelegateAction) {
			if (actionClass.isAssignableFrom(action.getClass())) {
				if (top == action) {
					Action a = ((DelegateAction) action).getAction();
					if (a == null) {
						component.unregisterKeyboardAction(keyStroke);
					} else {
						component.registerKeyboardAction(a, keyStroke, condition);
					}
				} else {
					((DelegateAction) parent).setAction(((DelegateAction) action).getAction());
				}
				break;
			}
			parent = action;
			action = ((DelegateAction) action).getAction();
		}
	}

	public static void restoreAction(JComponent component, int condition, KeyStroke keyStroke, Action actionToBeRemoved) {
		ActionListener action = component.getActionForKeyStroke(keyStroke);
		ActionListener parent = action;
		ActionListener top = action;
		while (action instanceof DelegateAction) {
			if (actionToBeRemoved == action) {
				if (top == action) {
					Action oldAction = ((DelegateAction) action).getAction();
					if (oldAction != null) {
						Object name = component.getInputMap().get(keyStroke);
						if (name != null) {
							component.getActionMap().remove(name);
							component.getActionMap().remove(action);
						}
						component.registerKeyboardAction(oldAction, keyStroke, condition);
					} else {
						component.unregisterKeyboardAction(keyStroke);
					}
				} else {
					((DelegateAction) parent).setAction(((DelegateAction) action).getAction());
				}
				break;
			}
			parent = action;
			action = ((DelegateAction) action).getAction();
		}
	}

	protected Action getAction() {
		return _action;
	}

	protected void setAction(Action action) {
		_action = action;
	}

	protected JComponent getTarget() {
		return _target;
	}

	protected void setTarget(JComponent target) {
		_target = target;
	}
}
