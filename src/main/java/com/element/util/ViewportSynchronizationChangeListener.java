package com.element.util;

import com.element.ui.pane.JideScrollPane;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.element.util.UIUtil.getViewportSynchronizationChangeListener;

/**
 * 视口同步的监听器，用于从视口位置随主视口位置改变而改变。
 * 该类不适合外部调用，主从视口同步请调用{@link UIUtil#synchronizeView(JViewport, JViewport, int)}
 */
class ViewportSynchronizationChangeListener implements ChangeListener {
	public void stateChanged(ChangeEvent e) {
		if (!(e.getSource() instanceof JViewport masterViewport)) return; //添加监听器的不是视口

		Object property = masterViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT);
		if (!(property instanceof Map)) return; //该视口没有从视口

		Dimension size = masterViewport.getSize();
		if (size.width == 0 || size.height == 0) return;

		Map<JViewport, Integer> slaveViewportMap = (Map) property; //从视口
		// 所有从视口对象映射
		Map<JViewport, Integer> allViewportToSync = new HashMap<>(slaveViewportMap);
		do {
			Map<JViewport, Integer> viewportToAdd = new HashMap<>();
			for (JViewport slaveViewport : allViewportToSync.keySet()) {
				// 从视口的从视口映射
				Object slaveProperty = slaveViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT);
				if (!(slaveProperty instanceof Map)) continue;

				int orientation = allViewportToSync.get(slaveViewport);
				Map<JViewport, Integer> viewportMap = (Map) slaveProperty;
				for (JViewport viewport : viewportMap.keySet()) {
					// 从视口的子视口中，只要不重复的、与从视口同方向的子视口
					if (viewport != masterViewport && !allViewportToSync.containsKey(viewport) && viewportMap.get(viewport) == orientation) {
						viewportToAdd.put(viewport, viewportMap.get(viewport));
					}
				}
			}
			if (viewportToAdd.isEmpty()) break;
			allViewportToSync.putAll(viewportToAdd);
		} while (true);

		// 同步所有从视口
		for (JViewport slaveViewport : allViewportToSync.keySet()) {
			// 在改变从视口的视口位置时移除监听器，防止该视口触发通知，因为需要通知的视口都在映射中，不需要重复触发
			slaveViewport.removeChangeListener(getViewportSynchronizationChangeListener());
			int orientation = allViewportToSync.get(slaveViewport);
			if (orientation == SwingConstants.HORIZONTAL) {
				Point v1 = masterViewport.getViewPosition();
				Point v2 = slaveViewport.getViewPosition();
				if (v1.x != v2.x) {
					slaveViewport.setViewPosition(new Point(v1.x, v2.y));
				}
			} else if (orientation == SwingConstants.VERTICAL) {
				Point v1 = masterViewport.getViewPosition();
				Point v2 = slaveViewport.getViewPosition();
				if (v1.y != v2.y) {
					slaveViewport.setViewPosition(new Point(v2.x, v1.y));
				}
			}
			slaveViewport.addChangeListener(getViewportSynchronizationChangeListener());
		}
	}
}
