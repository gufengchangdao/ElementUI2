package com.element.ui.area;

import org.jdesktop.swingx.JXTextArea;

import javax.swing.*;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Objects;

/**
 * 支持功能：
 * <ul>
 *     <li>支持光标所在行背景高亮显示</li>
 *     <li>支持文本提示</li>
 * </ul>
 */
public class BaseTextArea extends JXTextArea {
	/** 高亮行背景色 */
	private static final Color LINE_COLOR = new Color(0xFA_FA_DC);
	/** 组件的内部绘制区域的位置和大小 */
	private final Rectangle rect = new Rectangle();
	/** 要使得能够监听回车重绘高亮行，这里需要重新定义Caret */
	private static final Caret CARET = new DefaultCaret() {
		@Override
		protected synchronized void damage(Rectangle r) {
			if (Objects.nonNull(r)) {
				JTextComponent c = getComponent();
				x = 0;
				y = r.y;
				width = c.getSize().width;
				height = r.height;
				c.repaint();
			}
		}
	};
	/** 原来的光标，留着恢复时使用 */
	private Caret oldCaret;

	static {
		CARET.setBlinkRate(UIManager.getInt("TextArea.caretBlinkRate"));
	}

	/** 光标所在行背景是否高亮显示 */
	private boolean lineHighlight = false;

	public BaseTextArea() {
	}

	public BaseTextArea(String text) {
		super(text);
	}

	public BaseTextArea(String promptText, Color promptForeground) {
		super(promptText, promptForeground);
	}

	public BaseTextArea(String promptText, Color promptForeground, Color promptBackground) {
		super(promptText, promptForeground, promptBackground);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// 鼠标所在行绘制高亮
		Caret c = getCaret();
		if (c instanceof DefaultCaret caret) {
			Graphics2D g2 = (Graphics2D) g.create();
			Rectangle r = SwingUtilities.calculateInnerArea(this, rect);
			r.y = caret.y;
			r.height = caret.height;
			g2.setPaint(LINE_COLOR);
			g2.fill(r);
			g2.dispose();
		}
		super.paintComponent(g);
	}

	public boolean isLineHighlight() {
		return lineHighlight;
	}

	/** 设置光标所在行是否高亮 */
	public void setLineHighlight(boolean lineHighlight) {
		if (this.lineHighlight == lineHighlight) return;
		this.lineHighlight = lineHighlight;
		if (lineHighlight) {
			// 背景是先绘制的，必须为非不透明的，不然会把绘制的高亮遮盖掉
			setOpaque(false);
			oldCaret = getCaret();
			setCaret(CARET);
		} else {
			if (oldCaret != null) setCaret(oldCaret);
		}
		repaint();
	}
}
