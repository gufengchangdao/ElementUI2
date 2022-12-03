package com.element.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditor;
import java.lang.ref.WeakReference;

/**
 * 如何使用这个：
 * <code><pre>
 * KeyboardFocusManager focusManager =
 * KeyboardFocusManager.getCurrentKeyboardFocusManager();
 * <p/>
 * // instead of registering directly use weak listener
 * // focusManager.addPropertyChangeListener(focusOwnerListener);
 * <p/>
 * focusManager.addPropertyChangeListener(
 * new WeakPropertyChangeListener(focusOwnerListener, focusManager));
 * </pre></code>
 * <p>
 * 这是如何运作的：
 * <p>
 * 我们没有将 propertyChangeListener 直接注册到 keyboardFocusManager，而是将其包装在 WeakPropertyChangeListener 中，并将这个弱监
 * 听器注册到 keyboardFocusManager。这个弱监听器充当代表。它从 keyboardFocusManager 接收 propertyChangeEvents 并将包装的侦听器委托给它。
 * <p>
 * 这个弱侦听器的有趣部分是，它持有对原始 propertyChangeListener 的弱引用。所以这个委托有资格进行垃圾收集，它不再可以通过引用访问。当它进
 * 行垃圾回收时，weakReference 将指向 null。在来自 keyboardFocusManager 的下一个 propertyChangeEvent 通知中，它发现
 * weakReference 指向 null，并从 keyboardFocusManager 中注销自己。因此，弱侦听器也将有资格在下一个 gc 周期中进行垃圾收集。
 * <p>
 * 这个概念并不新鲜。如果您有查看 swing 源代码的习惯，您会发现 AbstractButton 实际上为其操作添加了一个弱侦听器。用于此的弱侦听器类
 * 是：javax.swing.AbstractActionPropertyChangeListener；此类是包私有的，因此您不会在 javadoc 中找到它。
 * <p>
 * Netbeans OpenAPI 中提供了弱侦听器的完整通用实现：WeakListeners.java。值得一看。
 *
 * @author Santhosh Kumar T - santhosh@in.fiorano.com
 */
public class WeakPropertyChangeListener implements PropertyChangeListener {
	private final WeakReference<PropertyChangeListener> _listenerRef;
	private final PropertyEditor _src;

	/**
	 * 创建属性改变的弱监听器
	 *
	 * @param listener 原监听器
	 * @param src      添加监听器的组件，需要有removePropertyChangeListener方法
	 */
	public WeakPropertyChangeListener(PropertyChangeListener listener, PropertyEditor src) {
		_listenerRef = new WeakReference<>(listener);
		_src = src;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		PropertyChangeListener listener = _listenerRef.get();
		if (listener == null) { //原监听器已经被回收了
			removeListener();
		} else
			listener.propertyChange(evt);
	}

	private void removeListener() {
		_src.removePropertyChangeListener(this);
	}
}

