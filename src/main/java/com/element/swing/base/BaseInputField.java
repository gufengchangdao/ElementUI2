package com.element.swing.base;

import com.element.ui.border.RoundBorder;
import com.element.util.UIUtil;
import org.jdesktop.swingx.JXTextField;
import org.jdesktop.swingx.plaf.BuddyLayoutAndBorder;

import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * 基础输入框
 * <p>
 * 功能有
 * <ul>
 *     <li>JXTextField的提示文本，添加左右组件</li>
 *     <li>圆角，可配合{@link com.element.ui.border.RoundBorder}添加圆角</li>
 * </ul>
 */
public class BaseInputField extends JXTextField {
	private int roundBorderArc = -1;

	public BaseInputField() {
	}

	public BaseInputField(String promptText) {
		super(promptText);
	}

	public BaseInputField(String promptText, Color promptForeground) {
		super(promptText, promptForeground);
	}

	public BaseInputField(String promptText, Color promptForeground, Color promptBackground) {
		super(promptText, promptForeground, promptBackground);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (roundBorderArc != -1) {
			// 裁剪背景，不能让背景绘制在圆角边框的外面
			RoundRectangle2D.Float round = new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, roundBorderArc, roundBorderArc);
			Object[] oldHints = UIUtil.setRenderingHints(g); //浅色背景的话，不设置渲染提示也看不出来锯齿的
			g.setClip(round);
			UIUtil.resetRenderingHints(g, oldHints);
		}
		super.paintComponent(g);
	}

	@Override
	public void setBorder(Border border) {
		if (border instanceof RoundBorder) {
			roundBorderArc = ((RoundBorder) border).getArcSize();
		} else if (!(border instanceof BuddyLayoutAndBorder)) {
			// BuddyLayoutAndBorder边框是JXTextField默认设置的边框，如果是除BuddyLayoutAndBorder和RoundBorder以外的边框，则认为不是圆角边框
			roundBorderArc = -1;
		}
		super.setBorder(border);
	}
}
