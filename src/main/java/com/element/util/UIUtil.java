/*
 * Copyright 2019 FormDev Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.element.util;

import com.element.plaf.UIDefaultsLookup;
import com.element.plaf.basic.ThemePainter;
import com.element.swing.Alignable;
import com.element.swing.FastGradientPainter;
import com.element.ui.button.JideSplitButton;
import com.element.ui.button.SplitButtonModel;
import com.element.ui.layout.JideBorderLayout;
import com.element.ui.nullc.NullPanel;
import com.element.ui.pane.JideScrollPane;
import com.element.util.handle.ConditionHandler;
import com.element.util.handle.GetHandler;
import com.element.util.handle.Handler;
import org.apache.batik.ext.awt.geom.Polygon2D;

import javax.accessibility.Accessible;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.List;
import java.util.*;
import java.util.function.Supplier;

/**
 * Utility methods for UI delegates.
 *
 * @author Karl Tauber
 */
public class UIUtil implements SwingConstants {
	public static final boolean MAC_USE_QUARTZ = Boolean.getBoolean("apple.awt.graphics.UseQuartz");
	public static final boolean JETBRAINS_JRE = System.getProperty("java.vendor").toLowerCase().contains("jetbrains");

	public static final Double cachedScaleFactor = 1D;
	private static final boolean useSharedUIs = true;
	private static final WeakHashMap<LookAndFeel, IdentityHashMap<Object, ComponentUI>> sharedUIinstances = new WeakHashMap<>();
	/**
	 * 文本是否被抗锯齿绘制。这仅在AA_TEXT_DEFINED为真时使用。
	 */
	private static final boolean AA_TEXT;

	/**
	 * 是否定义了系统属性“swing.aatext”。
	 */
	private static final boolean AA_TEXT_DEFINED;

	/**
	 * 客户端属性中用于指示组件是否应使用文本的键。
	 */
	public static final Object AA_TEXT_PROPERTY_KEY = new StringBuilder("AATextPropertyKey");

	static {
		String aa = SecurityUtils.getProperty("swing.aatext");
		AA_TEXT_DEFINED = (aa != null);
		AA_TEXT = "true".equals(aa);
	}

	// ---------------------------------------------------------------------
	// 渲染提示
	// ---------------------------------------------------------------------

	static Map<Object, Object> renderingHints;

	static {
		Toolkit tk = Toolkit.getDefaultToolkit();
		renderingHints = (Map) (tk.getDesktopProperty("awt.font.desktophints"));
		tk.addPropertyChangeListener("awt.font.desktophints", evt -> {
			if (evt.getNewValue() instanceof RenderingHints) {
				renderingHints = (RenderingHints) evt.getNewValue();
			}
		});
	}

	/**
	 * 设置形状反锯齿
	 *
	 * @param g 绘图图形上下文
	 * @return 原有的渲染提示{KEY_ANTIALIASING, KEY_STROKE_CONTROL}
	 */
	public static Object[] setRenderingHints(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Object[] oldRenderingHints = {
				g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING),
				g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL),
		};

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				MAC_USE_QUARTZ ? RenderingHints.VALUE_STROKE_PURE : RenderingHints.VALUE_STROKE_NORMALIZE);

		return oldRenderingHints;
	}

	/**
	 * 重置先前用{@link #setRenderingHints}设置的渲染提示
	 */
	public static void resetRenderingHints(Graphics g, Object[] oldRenderingHints) {
		Graphics2D g2 = (Graphics2D) g;
		if (oldRenderingHints[0] != null)
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldRenderingHints[0]);
		if (oldRenderingHints[1] != null)
			g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, oldRenderingHints[1]);
	}

	/**
	 * 临时重置使用setRenderingHints设置的呈现提示，并运行给定的可运行程序。这是为了在设置渲染提示时绘制文本。
	 * <p>
	 * 如果禁用了文本反锯齿(在OS系统设置中或通过-Dawt.useSystemAAFontSettings=off)，但是启用了一般的反锯齿，那么文本仍然使用某种“灰度”反锯齿绘制，这可能会使文本看起来粗体(取决于字体和字体大小)。
	 * 为了避免这种情况，请暂时禁用一般的反锯齿。如果启用了文本反锯齿(通常为默认值)，这不会影响文本呈现。
	 *
	 * @param g                 画笔
	 * @param oldRenderingHints 运行 runnable 之间使用的渲染提示
	 * @param runnable          在应用了给定渲染提示后被调用，然后在调用完毕后会将画笔重新设置为原先提示
	 */
	public static void runWithoutRenderingHints(Graphics g, Object[] oldRenderingHints, Runnable runnable) {
		if (oldRenderingHints == null) {
			runnable.run();
			return;
		}

		Graphics2D g2 = (Graphics2D) g;
		Object[] oldRenderingHints2 = {
				g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING),
				g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL),
		};

		resetRenderingHints(g2, oldRenderingHints);
		runnable.run();
		resetRenderingHints(g2, oldRenderingHints2);
	}

	/**
	 * Returns whether or not text should be drawn anti-aliased.
	 *
	 * @param c JComponent to test.
	 * @return Whether or not text should be drawn anti-aliased for the specified component.
	 */
	private static boolean drawTextAntiAliased(Component c) {
		if (!AA_TEXT_DEFINED) {
			if (c != null) {
				// Check if the component wants aa text
				if (c instanceof JComponent) {
					Boolean aaProperty = (Boolean) ((JComponent) c).getClientProperty(AA_TEXT_PROPERTY_KEY);
					return aaProperty != null ? aaProperty : false;
				} else {
					return false;
				}
			}
			// No component, assume aa is off
			return false;
		}
		// 'swing.aatext' was defined, use its value.
		return AA_TEXT;
	}

	/**
	 * 返回文本是否应该被抗锯齿绘制。
	 *
	 * @param aaText 没有定义系统属性“swing.aatext”时返回的默认值
	 * @return 返回文本是否应该被抗锯齿绘制
	 */
	public static boolean drawTextAntiAliased(boolean aaText) {
		if (!AA_TEXT_DEFINED) {
			// 'swing.aatext' wasn't defined, use the components aa text value.
			return aaText;
		}
		// 'swing.aatext' was defined, use its value.
		return AA_TEXT;
	}

	/**
	 * 从 Graphics 实例获取渲染提示。
	 *
	 * @param g2          绘制上下文，获取渲染提示使用
	 * @param hintsToSave 需要获取的渲染提示键值的列表
	 * @return 获取到的渲染提示键值的映射
	 */
	private static RenderingHints getRenderingHints(Graphics2D g2, Set<Object> hintsToSave) {
		RenderingHints savedHints = new RenderingHints(null);
		if (hintsToSave == null || hintsToSave.size() == 0) {
			return savedHints;
		}

		for (Object o : hintsToSave) {
			RenderingHints.Key key = (RenderingHints.Key) o;
			Object value = g2.getRenderingHint(key);
			if (value != null) {
				savedHints.put(o, value);
			}
		}
		return savedHints;
	}

	/**
	 * Setups the graphics to draw text using anti-alias.
	 * <p/>
	 * Under JDK1.4 and JDK5, this method will use a system property "swing.aatext" to determine if anti-alias is used.
	 * Under JDK6, we will read the system setting. For example, on Windows XP, there is a check box to turn on clear
	 * type anti-alias. We will use the same settings.
	 *
	 * @param c the component
	 * @param g the Graphics instance
	 * @return the old hints. You will need this value as the third parameter in {@link
	 * #restoreAntialiasing(Component, Graphics, Object)}.
	 */
	public static Object setupAntialiasing(Component c, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Object oldHints;
		oldHints = getRenderingHints(g2d, renderingHints.keySet());
		if (renderingHints != null) {
			g2d.addRenderingHints(renderingHints);
		}
		return oldHints;
	}

	/**
	 * Restores the old setting for text anti-alias.
	 *
	 * @param c
	 * @param g
	 * @param oldHints the value returned from {@link #setupAntialiasing(Component, Graphics)}.
	 */
	public static void restoreAntialiasing(Component c, Graphics g, Object oldHints) {
		Graphics2D g2d = (Graphics2D) g;
		if (oldHints instanceof RenderingHints) {
			g2d.addRenderingHints((RenderingHints) oldHints);
		}
	}

	/**
	 * Setups the graphics to draw shape using anti-alias.
	 *
	 * @param g
	 * @return the old hints. You will need this value as the third parameter in {@link
	 * #restoreShapeAntialiasing(Graphics, Object)}.
	 */
	public static Object setupShapeAntialiasing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Object oldHints = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		return oldHints;
	}

	/**
	 * Restores the old setting for shape anti-alias.
	 *
	 * @param g
	 * @param oldHints the value returned from {@link #setupShapeAntialiasing(Graphics)}.
	 */
	public static void restoreShapeAntialiasing(Graphics g, Object oldHints) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldHints);
	}

	/** 根据设备决定是否开启反锯齿，几何渲染的提示 */
	public static void withFractionalAntiAliasing(Graphics g, Runnable r) {
		withFractionalAntiAliasing(g, RenderingHints.VALUE_ANTIALIAS_ON, r);
	}

	/** 根据设备决定是否关闭反锯齿，几何渲染的提示 */
	public static void withoutFractionalAntiAliasing(Graphics g, Runnable r) {
		withFractionalAntiAliasing(g, RenderingHints.VALUE_ANTIALIAS_OFF, r);
	}

	public static double getScaleFactor(Graphics2D g) {
		if (g == null) {
			throw new NullPointerException("graphics is null");
		}
		GraphicsConfiguration deviceConfiguration = g.getDeviceConfiguration();
		if (deviceConfiguration == null) {
			throw new NullPointerException("deviceConfiguration is null");
		}
		double scale = deviceConfiguration.getDefaultTransform().getScaleX();
		if (SystemInfo.isMacOSX() && scale == 1f && !JETBRAINS_JRE) {
			return cachedScaleFactor;
		}

		return scale;
	}

	public static boolean isIntegerScaleFactor(Graphics2D g) {
		double scaleFactor = getScaleFactor(g);
		return Math.floor(scaleFactor) == scaleFactor;
	}

	public static void withFractionalAntiAliasing(Graphics g, Object value, Runnable r) {
		Graphics2D g2 = (Graphics2D) g;
		boolean fractionalScale = !isIntegerScaleFactor(g2);
		Object oldAntiAliasingHint = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		if (fractionalScale) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, value);
		}
		try {
			r.run();
		} finally {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAntiAliasingHint);
		}
	}

	// ---------------------------------------------------------------------
	// 图形绘制
	// ---------------------------------------------------------------------

	/**
	 * Paints an outline at the given bounds using the given line width.
	 * Depending on the given arc, a rectangle, rounded rectangle or circle (if w == h) is painted.
	 *
	 * @param g         the graphics context used for painting
	 * @param x         the x coordinate of the outline
	 * @param y         the y coordinate of the outline
	 * @param w         the width of the outline
	 * @param h         the height of the outline
	 * @param lineWidth the width of the outline
	 * @param arc       the arc diameter used for the outside shape of the outline
	 * @since 2
	 */
	public static void paintOutline(Graphics2D g, float x, float y, float w, float h,
	                                float lineWidth, float arc) {
		paintOutline(g, x, y, w, h, lineWidth, arc, arc - (lineWidth * 2));
	}

	/**
	 * 使用给定的线宽在给定的边界绘制轮廓。根据给定的圆弧，绘制矩形、圆角矩形或圆形(如果w == h)。
	 *
	 * @param g         用于绘画的图形上下文
	 * @param x         轮廓的x坐标
	 * @param y         轮廓的y坐标
	 * @param w         轮廓的宽度
	 * @param h         轮廓的高度
	 * @param lineWidth 线宽
	 * @param arc       用于外轮廓的圆弧直径
	 * @param innerArc  用于轮廓内部形状的圆弧直径
	 * @since 2
	 */
	public static void paintOutline(Graphics2D g, float x, float y, float w, float h,
	                                float lineWidth, float arc, float innerArc) {
		if (lineWidth == 0 || w <= 0 || h <= 0)
			return;

		float t = lineWidth;
		float t2x = t * 2;

		Path2D border = new Path2D.Float(Path2D.WIND_EVEN_ODD);
		border.append(createComponentRectangle(x, y, w, h, arc), false);
		border.append(createComponentRectangle(x + t, y + t, w - t2x, h - t2x, innerArc), false);
		g.fill(border);
	}

	/**
	 * Creates a (rounded) rectangle used to paint components (border, background, etc).
	 * The given arc diameter is limited to min(width,height).
	 */
	public static Shape createComponentRectangle(float x, float y, float w, float h, float arc) {
		if (arc <= 0)
			return new Rectangle2D.Float(x, y, w, h);

		if (w == h && arc >= w)
			return new Ellipse2D.Float(x, y, w, h);

		arc = Math.min(arc, Math.min(w, h));
		return new RoundRectangle2D.Float(x, y, w, h, arc, arc);
	}

	/**
	 * Find the first parent that is opaque.
	 */
	private static Container findOpaqueParent(Container c) {
		while ((c = c.getParent()) != null) {
			if (c.isOpaque())
				return c;
		}
		return null;
	}

	/**
	 * 创建具有给定线宽的非填充矩形形状
	 */
	public static Path2D createRectangle(float x, float y, float width, float height, float lineWidth) {
		Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
		path.append(new Rectangle2D.Float(x, y, width, height), false);
		path.append(new Rectangle2D.Float(x + lineWidth, y + lineWidth,
				width - (lineWidth * 2), height - (lineWidth * 2)), false);
		return path;
	}

	/**
	 * 创建一个未填充的圆角矩形形状，并允许指定线宽和半径或每个角
	 */
	public static Path2D createRoundRectangle(float x, float y, float width, float height,
	                                          float lineWidth, float arcTopLeft, float arcTopRight, float arcBottomLeft, float arcBottomRight) {
		Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
		path.append(createRoundRectanglePath(x, y, width, height, arcTopLeft, arcTopRight, arcBottomLeft, arcBottomRight), false);
		path.append(createRoundRectanglePath(x + lineWidth, y + lineWidth, width - (lineWidth * 2), height - (lineWidth * 2),
				arcTopLeft - lineWidth, arcTopRight - lineWidth, arcBottomLeft - lineWidth, arcBottomRight - lineWidth), false);
		return path;
	}

	/**
	 * 创建填充圆角矩形形状，并允许指定每个角的半径。
	 */
	public static Shape createRoundRectanglePath(float x, float y, float width, float height,
	                                             float arcTopLeft, float arcTopRight, float arcBottomLeft, float arcBottomRight) {
		if (arcTopLeft <= 0 && arcTopRight <= 0 && arcBottomLeft <= 0 && arcBottomRight <= 0)
			return new Rectangle2D.Float(x, y, width, height);

		// limit arcs to min(width,height)
		float maxArc = Math.min(width, height) / 2;
		arcTopLeft = (arcTopLeft > 0) ? Math.min(arcTopLeft, maxArc) : 0;
		arcTopRight = (arcTopRight > 0) ? Math.min(arcTopRight, maxArc) : 0;
		arcBottomLeft = (arcBottomLeft > 0) ? Math.min(arcBottomLeft, maxArc) : 0;
		arcBottomRight = (arcBottomRight > 0) ? Math.min(arcBottomRight, maxArc) : 0;

		float x2 = x + width;
		float y2 = y + height;

		// same constant as in java.awt.geom.EllipseIterator.CtrlVal used to paint circles
		double c = 0.5522847498307933;
		double ci = 1. - c;
		double ciTopLeft = arcTopLeft * ci;
		double ciTopRight = arcTopRight * ci;
		double ciBottomLeft = arcBottomLeft * ci;
		double ciBottomRight = arcBottomRight * ci;

		Path2D rect = new Path2D.Float();
		rect.moveTo(x2 - arcTopRight, y);
		rect.curveTo(x2 - ciTopRight, y,
				x2, y + ciTopRight,
				x2, y + arcTopRight);
		rect.lineTo(x2, y2 - arcBottomRight);
		rect.curveTo(x2, y2 - ciBottomRight,
				x2 - ciBottomRight, y2,
				x2 - arcBottomRight, y2);
		rect.lineTo(x + arcBottomLeft, y2);
		rect.curveTo(x + ciBottomLeft, y2,
				x, y2 - ciBottomLeft,
				x, y2 - arcBottomLeft);
		rect.lineTo(x, y + arcTopLeft);
		rect.curveTo(x, y + ciTopLeft,
				x + ciTopLeft, y,
				x + arcTopLeft, y);
		rect.closePath();

		return rect;
	}

	/**
	 * Creates a chevron or triangle arrow shape for the given direction and size.
	 * <p>
	 * The chevron shape is an open path that can be painted with {@link Graphics2D#draw(Shape)}.
	 * The triangle shape is a close path that can be painted with {@link Graphics2D#fill(Shape)}.
	 *
	 * @param direction the arrow direction ({@link SwingConstants#NORTH}, {@link SwingConstants#SOUTH}
	 *                  {@link SwingConstants#WEST} or {@link SwingConstants#EAST})
	 * @param chevron   {@code true} for chevron arrow, {@code false} for triangle arrow
	 * @param w         the width of the returned shape
	 * @param h         the height of the returned shape
	 * @since 1.1
	 */
	public static Shape createArrowShape(int direction, boolean chevron, float w, float h) {
		return switch (direction) {
			case SwingConstants.NORTH -> createPath(!chevron, 0, h, (w / 2f), 0, w, h);
			case SwingConstants.SOUTH -> createPath(!chevron, 0, 0, (w / 2f), h, w, 0);
			case SwingConstants.WEST -> createPath(!chevron, w, 0, 0, (h / 2f), w, h);
			case SwingConstants.EAST -> createPath(!chevron, 0, 0, w, (h / 2f), 0, h);
			default -> new Path2D.Float();
		};
	}

	/**
	 * 为给定的点创建封闭路径。
	 */
	public static Path2D createPath(double... points) {
		return createPath(true, points);
	}

	/**
	 * 为给定的点创建打开或关闭的路径。
	 */
	public static Path2D createPath(boolean close, double... points) {
		Path2D path = new Path2D.Float();
		path.moveTo(points[0], points[1]);
		for (int i = 2; i < points.length; i += 2)
			path.lineTo(points[i], points[i + 1]);
		if (close)
			path.closePath();
		return path;
	}

	/**
	 * 绘制箭头形状，是一个等腰直角三角形，只有朝下和朝右两个方向
	 *
	 * @param g           the graphics instance
	 * @param color       color
	 * @param startX      start X
	 * @param startY      start Y
	 * @param width       width
	 * @param orientation horizontal or vertical
	 */
	public static void paintArrow(Graphics g, Color color, int startX, int startY, int width, int orientation) {
		Graphics2D g2 = (Graphics2D) g;
		Color oldColor = g2.getColor();
		g2.setColor(color);
		if (orientation == HORIZONTAL) { //朝下
			g2.fill(new Polygon2D(
					new float[]{startX, startX + width, (startX * 2 + width) / 2f},
					new float[]{startY, startY, startY + (width + 1) / 2f},
					3
			));
		} else if (orientation == VERTICAL) { //朝右边
			g2.fill(new Polygon2D(
					new float[]{startX, startX, startX + width / 2f},
					new float[]{startY, startY + width, startY + width / 2f},
					3
			));
		}
		g2.setColor(oldColor);
	}

	/**
	 * 绘制箭头形状，如果组件是从右到左。绘制的三角形将朝向左边。支持方向：下左右
	 *
	 * @param c           the component
	 * @param g           the graphics instance
	 * @param color       color
	 * @param startX      start X
	 * @param startY      start Y
	 * @param width       width
	 * @param orientation horizontal or vertical
	 */
	public static void paintArrow(JComponent c, Graphics g, Color color, int startX, int startY, int width, int orientation) {
		if (!c.getComponentOrientation().isLeftToRight()) {
			Color oldColor = g.getColor();
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(color);
			g2.fill(new Polygon2D(
					new float[]{startX + width, startX + width, startX + width / 2f},
					new float[]{startY, startY + width, startY + width / 2f},
					3
			));
			g2.setColor(oldColor);
			return;
		}

		paintArrow(g, color, startX, startY, width, orientation);
	}

	/**
	 * 绘制矩形背景及边框
	 *
	 * @param g      绘制上下文
	 * @param rect   绘制区域，一般为组件大小
	 * @param border 边框色
	 * @param bk     背景色
	 */
	public static void paintBackground(Graphics g, Rectangle rect, Color border, Color bk) {
		Color old = g.getColor();
		g.setColor(bk);
		g.fillRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
		g.setColor(border);
		g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
		g.setColor(old);
	}

	/**
	 * 绘制矩形背景及边框
	 *
	 * @param g2d    绘制上下文
	 * @param rect   绘制区域，一般为组件大小
	 * @param border 边框色
	 * @param paint  背景色，为Paint类型，也就是说可以设置渐变色
	 */
	public static void paintBackground(Graphics2D g2d, Rectangle rect, Color border, Paint paint) {
		Color old = g2d.getColor();
		g2d.setPaint(paint);
		g2d.fillRect(rect.x + 1, rect.y + 1, rect.width - 2, rect.height - 2);
		g2d.setColor(border);
		g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
		g2d.setColor(old);
	}

	/** 在指定位置绘制文本 */
	public static void drawString(Graphics g, String text, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		Map oldHints = null;
		if (renderingHints != null) {
			oldHints = getRenderingHints(g2, renderingHints.keySet());
			g2.addRenderingHints(renderingHints);
		}
		g2.drawString(text, x, y);
		if (oldHints != null) {
			g2.addRenderingHints(oldHints);
		}
	}

	/**
	 * 在指定位置绘制文本并添加下划线
	 *
	 * @param c               绘制文本的组件
	 * @param g               绘制上下文
	 * @param text            文本内容
	 * @param underlinedIndex 要绘制下划线的字符索引
	 * @param x               x
	 * @param y               y
	 */
	public static void drawStringUnderlineCharAt(JComponent c, Graphics g, String text, int underlinedIndex, int x, int y) {
		drawString(g, text, x, y);

		if (underlinedIndex >= 0 && underlinedIndex < text.length()) {
			FontMetrics fm = g.getFontMetrics();
			int underlineRectX = x + fm.stringWidth(text.substring(0, underlinedIndex));
			int underlineRectY = y;
			int underlineRectWidth = fm.charWidth(text.charAt(underlinedIndex));
			int underlineRectHeight = 1;
			g.fillRect(underlineRectX, underlineRectY + fm.getDescent() - 1,
					underlineRectWidth, underlineRectHeight);
		}
	}

	/** 用规律分布的点填充矩形区域 */
	public static void drawGrip(Graphics g, Rectangle rectangle, int maxLength, int maxThickness) {
		drawGrip(g, rectangle, maxLength, maxThickness, true);
	}

	public static void drawGrip(Graphics g, Rectangle rectangle, int maxLength, int maxThickness, boolean isSelected) {
		int count = maxLength;
		if (rectangle.width > rectangle.height) {
			if (maxLength * 3 > rectangle.width) {
				count = rectangle.width / 3;
			}
			int startX = rectangle.x + ((rectangle.width - (count * 3)) >> 1);
			int startY = rectangle.y + ((rectangle.height - (maxThickness * 3)) >> 1);
			for (int i = 0; i < maxThickness; i++) {
				for (int j = 0; j < count; j++) {
					if (isSelected) {
						g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
						g.drawLine(startX + j * 3, startY + i * 3, startX + j * 3, startY + i * 3);
					}
					g.setColor(UIDefaultsLookup.getColor("controlShadow"));
					g.drawLine(startX + j * 3 + 1, startY + i * 3 + 1, startX + j * 3 + 1, startY + i * 3 + 1);
				}
			}
		} else {
			if (maxLength * 3 > rectangle.height) {
				count = rectangle.height / 3;
			}
			int startX = rectangle.x + ((rectangle.width - (maxThickness * 3)) >> 1);
			int startY = rectangle.y + ((rectangle.height - (count * 3)) >> 1);
			for (int i = 0; i < maxThickness; i++) {
				for (int j = 0; j < count; j++) {
					if (isSelected) {
						g.setColor(UIDefaultsLookup.getColor("controlLtHighlight"));
						g.drawLine(startX + i * 3, startY + j * 3, startX + i * 3, startY + j * 3);
					}
					g.setColor(UIDefaultsLookup.getColor("controlShadow"));
					g.drawLine(startX + i * 3 + 1, startY + j * 3 + 1, startX + i * 3 + 1, startY + j * 3 + 1);
				}
			}
		}
	}

	/**
	 * 使用指定的 startColor 和 endColor 填充渐变。这是填充渐变的快速版本，它不仅可以利用硬件加速，还可以缓存 GradientPaint 并重用它。
	 * <p>
	 * 我们还保留了使用普通 GradientPaint 绘制渐变的选项。为此，只需将系统属性“normalGradientPaint”设置为“false”。
	 *
	 * @param g2         绘制上下文
	 * @param s          绘制区域
	 * @param startColor 开始颜色
	 * @param endColor   结束颜色
	 * @param isVertical 是否为垂直方向
	 */
	public static void fillGradient(Graphics2D g2, Shape s, Color startColor, Color endColor, boolean isVertical) {
		if ("true".equals(SecurityUtils.getProperty("normalGradientPaint", "false"))) {
			fillNormalGradient(g2, s, startColor, endColor, isVertical);
		} else {
			FastGradientPainter.drawGradient(g2, s, startColor, endColor, isVertical);
		}
	}

	/**
	 * 用白色到淡灰色的渐变色填充在矩形区域
	 *
	 * @param g           绘制上下文
	 * @param rect        绘制区域
	 * @param orientation 颜色的相同的方向，注意这与颜色过渡方向垂直，值为{@link #HORIZONTAL} 或{@link #VERTICAL}
	 */
	public static void fillGradient(Graphics g, Rectangle rect, int orientation) {
		Graphics2D g2d = (Graphics2D) g;
		// paint upper gradient
		Color col1 = new Color(255, 255, 255, 0);
		Color col2 = new Color(255, 255, 255, 48);
		Color col3 = new Color(0, 0, 0, 0);
		Color col4 = new Color(0, 0, 0, 32);

		if (orientation == SwingConstants.HORIZONTAL) {
			// paint upper gradient
			fillGradient(g2d, new Rectangle2D.Float(rect.x, rect.y, rect.width, rect.height >> 1), col2, col1, true);

			// paint lower gradient
			fillGradient(g2d, new Rectangle2D.Float(rect.x, rect.y + (rect.height >> 1), rect.width, rect.height >> 1), col3, col4, true);
		} else {
			// paint left gradient
			fillGradient(g2d, new Rectangle2D.Float(rect.x, rect.y, rect.width >> 1, rect.height), col2, col1, false);

			// paint right gradient
			fillGradient(g2d, new Rectangle2D.Float(rect.x + (rect.width >> 1), rect.y, rect.width >> 1, rect.height), col3, col4, false);
		}
	}

	/** 使用半透明白色到透明的渐变色填充在矩形区域 */
	public static void fillSingleGradient(Graphics g, Rectangle rect, int orientation) {
		fillSingleGradient(g, rect, orientation, 127);
	}

	/**
	 * 使用白色到透明的渐变色填充在矩形区域
	 *
	 * @param g           绘制上下文
	 * @param rect        绘制区域
	 * @param orientation 渐变方向，值为{@link #SOUTH}、{@link #NORTH}、{@link #EAST}、{@link #WEST}
	 * @param level       绘制起始位置的alpha值，最大值为255
	 */
	public static void fillSingleGradient(Graphics g, Rectangle rect, int orientation, int level) {
		Graphics2D g2d = (Graphics2D) g;
		Color col1 = new Color(255, 255, 255, 0);
		Color col2 = new Color(255, 255, 255, level);

		if (orientation == SwingConstants.SOUTH) {
			fillGradient(g2d, new Rectangle2D.Float(rect.x, rect.y, rect.width, rect.height), col2, col1, true);
		} else if (orientation == SwingConstants.NORTH) {
			fillGradient(g2d, new Rectangle2D.Float(rect.x, rect.y, rect.width, rect.height), col1, col2, true);
		} else if (orientation == SwingConstants.EAST) {
			fillGradient(g2d, new Rectangle2D.Float(rect.x, rect.y, rect.width, rect.height), col2, col1, false);
		} else if (orientation == SwingConstants.WEST) {
			fillGradient(g2d, new Rectangle2D.Float(rect.x, rect.y, rect.width, rect.height), col1, col2, false);
		}
	}

	public static void fillNormalGradient(Graphics2D g2, Shape s, Color startColor, Color endColor, boolean isVertical) {
		Rectangle rect = s.getBounds();
		GradientPaint paint;
		if (isVertical) {
			// turn cyclic to true will be faster
			paint = new GradientPaint(rect.x, rect.y, startColor, rect.x, rect.height + rect.y, endColor, true);
		} else {
			// turn cyclic to true will be faster
			paint = new GradientPaint(rect.x, rect.y, startColor, rect.width + rect.x, rect.y, endColor, true);
		}
		Paint old = g2.getPaint();
		g2.setPaint(paint);
		g2.fill(s);
		g2.setPaint(old);
	}

	/**
	 * Clears the gradient cache used for fast gradient painting
	 */
	public static void clearGradientCache() {
		FastGradientPainter.clearGradientCache();
	}


	/**
	 * 绘制带有禁用笔画正常化的给定形状。形状的x/y坐标转换了半个像素。
	 *
	 * @since 2.1
	 */
	public static void drawShapePure(Graphics2D g, Shape shape) {
		Object oldStrokeControl = g.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		g.translate(0.5, 0.5);
		g.draw(shape);
		g.translate(-0.5, -0.5);

		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, oldStrokeControl);
	}

	public static boolean hasOpaqueBeenExplicitlySet(JComponent c) {
		boolean oldOpaque = c.isOpaque();
		LookAndFeel.installProperty(c, "opaque", !oldOpaque);
		boolean explicitlySet = c.isOpaque() == oldOpaque;
		LookAndFeel.installProperty(c, "opaque", oldOpaque);
		return explicitlySet;
	}

	/**
	 * 返回是否使用共享UI委托。
	 *
	 * @since 1.6
	 */
	public static boolean isUseSharedUIs() {
		return useSharedUIs;
	}

	/**
	 * 为给定的键和当前Laf创建一个共享组件UI。每个Laf实例都有自己的共享组件UI实例。
	 * <p>
	 * 这是为支持Laf切换并可能同时使用多个Laf实例的GUI构建器准备的。
	 */
	public static ComponentUI createSharedUI(Object key, Supplier<ComponentUI> newInstanceSupplier) {
		if (!useSharedUIs)
			return newInstanceSupplier.get();

		return sharedUIinstances
				.computeIfAbsent(UIManager.getLookAndFeel(), k -> new IdentityHashMap<>())
				.computeIfAbsent(key, k -> newInstanceSupplier.get());
	}

	/**
	 * 九宫格图片拉伸。根据图像绘制边框。图像可以分为九个不同的区域。每个区域的大小由插图决定。
	 *
	 * @param g          绘制上下文
	 * @param img        图标
	 * @param rect       绘制区域(包含内边距)，一般为组件的大小
	 * @param ins        内边距，边距的大小也会影响图片的拉伸
	 * @param drawCenter 是否用图片填充中间的区域
	 */
	public static void drawImageBorder(Graphics g, ImageIcon img, Rectangle rect, Insets ins, boolean drawCenter) {
		int left = ins.left;
		int right = ins.right;
		int top = ins.top;
		int bottom = ins.bottom;
		int x = rect.x;
		int y = rect.y;
		int w = rect.width;
		int h = rect.height;

		// top
		g.drawImage(img.getImage(), x, y, x + left, y + top,
				0, 0, left, top, null);
		g.drawImage(img.getImage(), x + left, y, x + w - right, y + top,
				left, 0, img.getIconWidth() - right, top, null);
		g.drawImage(img.getImage(), x + w - right, y, x + w, y + top,
				img.getIconWidth() - right, 0, img.getIconWidth(), top, null);

		// middle
		g.drawImage(img.getImage(), x, y + top, x + left, y + h - bottom,
				0, top, left, img.getIconHeight() - bottom, null);
		g.drawImage(img.getImage(), x + w - right, y + top, x + w, y + h - bottom,
				img.getIconWidth() - right, top, img.getIconWidth(), img.getIconHeight() - bottom, null);

		// bottom
		g.drawImage(img.getImage(), x, y + h - bottom, x + left, y + h,
				0, img.getIconHeight() - bottom, left, img.getIconHeight(), null);
		g.drawImage(img.getImage(), x + left, y + h - bottom, x + w - right, y + h,
				left, img.getIconHeight() - bottom, img.getIconWidth() - right, img.getIconHeight(), null);
		g.drawImage(img.getImage(), x + w - right, y + h - bottom, x + w, y + h,
				img.getIconWidth() - right, img.getIconHeight() - bottom, img.getIconWidth(), img.getIconHeight(), null);

		if (drawCenter) {
			g.drawImage(img.getImage(), x + left, y + top, x + w - right, y + h - bottom,
					left, top, img.getIconWidth() - right, img.getIconHeight() - bottom, null);
		}
	}

	/**
	 * 九宫格图片拉伸。根据图像绘制边框。图像可以分为九个不同的区域。每个区域的大小由插图决定。
	 *
	 * @param g          绘制上下文
	 * @param img        图像
	 * @param rect       绘制区域(包含内边距)，一般为组件的大小
	 * @param ins        内边距，边距的大小也会影响图片的拉伸
	 * @param drawCenter 是否用图片填充中间的区域
	 */
	public static void drawImageBorder(Graphics g, Image img, Rectangle rect, Insets ins, boolean drawCenter) {
		drawImageBorder(g, new ImageIcon(img), rect, ins, drawCenter);
	}

	// ---------------------------------------------------------------------
	// 重绘
	// ---------------------------------------------------------------------

	/**
	 * 重绘 JComboBox 指定行，如果该行已选中，则按钮也会被重绘
	 *
	 * @param combo JComboBox
	 * @param row   要重绘的行索引
	 */
	public static void repaint(JComboBox<?> combo, int row) {
		// 如果已选中就重绘按钮
		if (combo.getSelectedIndex() == row) {
			combo.repaint();
		}
		// 如果下拉列表已展开就重绘列表指定行
		Accessible a = combo.getAccessibleContext().getAccessibleChild(0);
		if (a instanceof ComboPopup) {
			JList<?> list = ((ComboPopup) a).getList();
			if (list.isShowing()) {
				list.repaint(list.getCellBounds(row, row));
			}
		}
	}

	/**
	 * 重绘表格指定单元格
	 *
	 * @param table 表格
	 * @param row   表格模型中所在行
	 * @param col   表格模型中所在列
	 */
	public static void repaint(JTable table, int row, int col) {
		int vr = table.convertRowIndexToView(row); // JDK 1.6.0
		int vc = table.convertColumnIndexToView(col);
		table.repaint(table.getCellRect(vr, vc, false));
	}

	// ---------------------------------------------------------------------
	// 包装组件
	// ---------------------------------------------------------------------

	/**
	 * Create a Panel around a component so that component aligns to left.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createLeftPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.LINE_START);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to right.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createRightPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.LINE_END);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to top.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createTopPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.PAGE_START);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component aligns to bottom.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createBottomPanel(Component object) {
		JPanel ret = new NullPanel(new BorderLayout());
		ret.setOpaque(false);
		ret.add(object, BorderLayout.PAGE_END);
		return ret;
	}

	/**
	 * Create a Panel around a component so that component is right in the middle.
	 *
	 * @param object the component
	 * @return a Panel
	 */
	public static JPanel createCenterPanel(Component object) {
		JPanel ret = new NullPanel(new GridBagLayout());
		ret.setOpaque(false);
		ret.add(object, new GridBagConstraints());
		return ret;
	}

	/**
	 * Creates a container which a label for the component.
	 *
	 * @param title      the label
	 * @param component  the component
	 * @param constraint the constraint as in BorderLayout. You can use all the constraints as in BorderLayout except
	 *                   CENTER.
	 * @return the container which has both the label and the component.
	 */
	public static JPanel createLabeledComponent(JLabel title, Component component, Object constraint) {
		JPanel ret = new NullPanel(new JideBorderLayout(3, 3));
		ret.setOpaque(false);
		ret.add(title, constraint);
		title.setLabelFor(component);
		ret.add(component);
		return ret;
	}

	/**
	 * Center the component to it's parent window.
	 *
	 * @param childToCenter the parent window
	 */
	public static void centerWindow(Window childToCenter) {
		childToCenter.setLocationRelativeTo(childToCenter.getParent());
	}

	// ---------------------------------------------------------------------
	// 比较
	// ---------------------------------------------------------------------

	/**
	 * Checks if the two objects equal. If both are null, they are equal. If o1 and o2 both are Comparable, we will use
	 * compareTo method to see if it equals 0. At last, we will use <code>o1.equals(o2)</code> to compare. If none of
	 * the above conditions match, we return false.
	 *
	 * @param o1 the first object to compare
	 * @param o2 the second object to compare
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2) {
		return equals(o1, o2, false);
	}

	/**
	 * Checks if the two objects equal. If both are the same instance, they are equal. If both are null, they are equal.
	 * If o1 and o2 both are Comparable, we will use compareTo method to see if it equals 0. If considerArrayOrList is
	 * true and o1 and o2 are both array, we will compare each element in the array. At last, we will use
	 * <code>o1.equals(o2)</code> to compare. If none of the above conditions match, we return false.
	 *
	 * @param o1                  the first object to compare
	 * @param o2                  the second object to compare
	 * @param considerArrayOrList If true, and if o1 and o2 are both array, we will compare each element in the array
	 *                            instead of just compare the two array objects.
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2, boolean considerArrayOrList) {
		return equals(o1, o2, considerArrayOrList, true);
	}

	/**
	 * Checks if the two objects equal. If both are the same instance, they are equal. If both are null, they are equal.
	 * If o1 and o2 both are Comparable, we will use compareTo method to see if it equals 0. If considerArrayOrList is
	 * true and o1 and o2 are both array, we will compare each element in the array. At last, we will use
	 * <code>o1.equals(o2)</code> to compare. If none of the above conditions match, we return false.
	 *
	 * @param o1                  the first object to compare
	 * @param o2                  the second object to compare
	 * @param considerArrayOrList If true, and if o1 and o2 are both array, we will compare each element in the array
	 *                            instead of just compare the two array objects.
	 * @param caseSensitive       if the o1 and o2 are CharSequence, we will use this parameter to do a case sensitive
	 *                            or insensitive comparison
	 * @return true if the two objects are equal. Otherwise false.
	 */
	public static boolean equals(Object o1, Object o2, boolean considerArrayOrList, boolean caseSensitive) {
		if (o1 == o2) {
			return true;
		} else if (o1 != null && o2 == null) {
			return false;
		} else if (o1 == null) {
			return false;
		} else if (o1 instanceof CharSequence && o2 instanceof CharSequence) {
			return equals((CharSequence) o1, (CharSequence) o2, caseSensitive);
		} else if (o1 instanceof Comparable && o2 instanceof Comparable && o1.getClass().isAssignableFrom(o2.getClass())) {
			return ((Comparable) o1).compareTo(o2) == 0;
		} else if (o1 instanceof Comparable && o2 instanceof Comparable && o2.getClass().isAssignableFrom(o1.getClass())) {
			return ((Comparable) o2).compareTo(o1) == 0;
		} else if (considerArrayOrList && o1 instanceof java.util.List && o2 instanceof java.util.List) {
			int length1 = ((java.util.List<?>) o1).size();
			int length2 = ((java.util.List<?>) o2).size();
			if (length1 != length2) {
				return false;
			}
			for (int i = 0; i < length1; i++) {
				if (!equals(((java.util.List<?>) o1).get(i), ((List<?>) o2).get(i), true)) {
					return false;
				}
			}
			return true;
		} else if (considerArrayOrList && o1.getClass().isArray() && o2.getClass().isArray()) {
			int length1 = Array.getLength(o1);
			int length2 = Array.getLength(o2);
			if (length1 != length2) {
				return false;
			}
			for (int i = 0; i < length1; i++) {
				if (!equals(Array.get(o1, i), Array.get(o2, i), true)) {
					return false;
				}
			}
			return true;
		} else {
			return o1.equals(o2);
		}
	}

	public static boolean equals(CharSequence s1, CharSequence s2, boolean caseSensitive) {
		if (s1 == s2) return true;
		if (s1 == null || s2 == null) return false;

		// Algorithm from String.regionMatches()

		if (s1.length() != s2.length()) return false;
		int to = 0;
		int po = 0;
		int len = s1.length();

		while (len-- > 0) {
			char c1 = s1.charAt(to++);
			char c2 = s2.charAt(po++);
			if (c1 == c2) {
				continue;
			}
			if (!caseSensitive && charsEqualIgnoreCase(c1, c2)) continue;
			return false;
		}

		return true;
	}

	public static boolean charsEqualIgnoreCase(char a, char b) {
		return a == b || Character.toLowerCase(a) == Character.toLowerCase(b);
	}

	// ---------------------------------------------------------------------
	// 监听器
	// ---------------------------------------------------------------------

	/** 组件是否是永久焦点所有者 */
	public static boolean componentIsPermanentFocusOwner(Component comp) {
		return ((comp != null) && (KeyboardFocusManager.getCurrentKeyboardFocusManager().
				getPermanentFocusOwner() == comp));
	}

	/**
	 * 查找容器中可以获取焦点的第一个组件并尝试使其获取焦点
	 * <p>
	 * 请注意，这并没有做一些聪明的事情，比如尝试在每个级别水平地遍历层次结构，以便聚焦的子组件尽可能高。相反，它是深度遍历的。它只是一个安全阀，因此可以在某处请求焦点而不是丢失。
	 *
	 * @param container 要获取焦点容器
	 * @return 一个可聚焦的子组件，可能是容器本身，或者是容器的子组件，找不到就返回null
	 */
	public static Component findSomethingFocusable(Container container) {
		if (passesFocusabilityTest(container)) {
			container.requestFocusInWindow();
			return container;
		}
		Component[] comps = container.getComponents();
		for (Component comp1 : comps) {
			if (passesFocusabilityTest(comp1)) {
				container.requestFocusInWindow();
				return container;
			} else if (comp1 instanceof Container) {
				Component comp = findSomethingFocusable((Container) (comp1));
				if (comp != null) {
					return comp;
				}
			}
		}
		return null;
	}

	/**
	 * There are four standard tests which determine if Swing will be able to request focus for a component. Test them.
	 *
	 * @param comp
	 * @return does the specified component pass the four focusability tests
	 */
	public static boolean passesFocusabilityTest(Component comp) {
		return ((comp != null) && comp.isEnabled() && comp.isFocusable() && comp.isShowing());
	}

	/**
	 * Copied from BasicLookAndFeel as the method is package local.
	 *
	 * @param component
	 * @return if request focus is success or not.
	 */
	public static boolean compositeRequestFocus(Component component) {
		if (component instanceof Container container) {
			if (container.isFocusCycleRoot()) {
				FocusTraversalPolicy policy = container.getFocusTraversalPolicy();
				Component comp = policy.getDefaultComponent(container);

				if ((comp != null) && comp.isShowing() && container.getComponentCount() > 0) {
					return comp.requestFocusInWindow();
				}
			}
			Container rootAncestor = container.getFocusCycleRootAncestor();
			if (rootAncestor != null) {
				FocusTraversalPolicy policy = rootAncestor.getFocusTraversalPolicy();
				Component comp = null;
				try {
					comp = policy.getComponentAfter(rootAncestor, container);
				} catch (Exception e) {
					// ClassCastException when docking frames on Solaris
					// http://jidesoft.com/forum/viewtopic.php?p=32569
				}

				if (comp != null && SwingUtilities.isDescendingFrom(comp, container)) {
					return comp.requestFocusInWindow();
				}
			}
		}
		if (!passesFocusabilityTest(component)) {
			return false;
		}

		return component.requestFocusInWindow();
	}

	public static boolean isAncestorOfFocusOwner(Component component) {
		boolean hasFocus = false;
		Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		if (component == focusOwner || (component instanceof Container && ((Container) component).isAncestorOf(focusOwner))) {
			hasFocus = true;
		}
		return hasFocus;
	}

	/**
	 * Checks if the key listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isKeyListenerRegistered(Component component, KeyListener l) {
		return Arrays.stream(component.getKeyListeners()).anyMatch(keyListener -> keyListener == l);
	}

	/**
	 * Inserts the key listener at the particular index in the listeners' chain.
	 *
	 * @param component
	 * @param l
	 * @param index
	 */
	public static void insertKeyListener(Component component, KeyListener l, int index) {
		KeyListener[] listeners = component.getKeyListeners();
		for (KeyListener listener : listeners) {
			component.removeKeyListener(listener);
		}
		for (int i = 0; i < listeners.length; i++) {
			KeyListener listener = listeners[i];
			if (index == i) {
				component.addKeyListener(l);
			}
			component.addKeyListener(listener);
		}
		// index is too large, add to the end.
		if (index > listeners.length - 1) {
			component.addKeyListener(l);
		}
	}

	/**
	 * Inserts the table model listener at the particular index in the listeners' chain. The listeners are fired in
	 * reverse order. So the listener at index 0 will be fired at last.
	 *
	 * @param model the AbstractTableModel
	 * @param l     the TableModelListener to be inserted
	 * @param index the index.
	 */
	public static void insertTableModelListener(TableModel model, TableModelListener l, int index) {
		if (!(model instanceof AbstractTableModel)) {
			model.addTableModelListener(l);
			return;
		}
		TableModelListener[] listeners = ((AbstractTableModel) model).getTableModelListeners();
		for (TableModelListener listener : listeners) {
			model.removeTableModelListener(listener);
		}
		for (int i = 0; i < listeners.length; i++) {
			TableModelListener listener = listeners[i];
			if (index == i) {
				model.addTableModelListener(l);
			}
			model.addTableModelListener(listener);
		}
		// index is too large, add to the end.
		if (index < 0 || index > listeners.length - 1) {
			model.addTableModelListener(l);
		}
	}

	/**
	 * Inserts the property change listener at the particular index in the listeners' chain.
	 *
	 * @param component    the component where the listener will be inserted.
	 * @param l            the listener to be inserted
	 * @param propertyName the name of the property. Could be null.
	 * @param index        the index to be inserted
	 */
	public static void insertPropertyChangeListener(Component component, PropertyChangeListener l, String propertyName, int index) {
		PropertyChangeListener[] listeners = propertyName == null ? component.getPropertyChangeListeners() : component.getPropertyChangeListeners(propertyName);
		for (PropertyChangeListener listener : listeners) {
			if (propertyName == null) {
				component.removePropertyChangeListener(listener);
			} else {
				component.removePropertyChangeListener(propertyName, listener);
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			PropertyChangeListener listener = listeners[i];
			if (index == i) {
				if (propertyName == null) {
					component.addPropertyChangeListener(l);
				} else {
					component.addPropertyChangeListener(propertyName, l);
				}
			}
			if (propertyName == null) {
				component.addPropertyChangeListener(listener);
			} else {
				component.addPropertyChangeListener(propertyName, listener);
			}
		}
		// index is too large, add to the end.
		if (index > listeners.length - 1) {
			if (propertyName == null) {
				component.addPropertyChangeListener(l);
			} else {
				component.addPropertyChangeListener(propertyName, l);
			}
		}
	}

	/**
	 * Inserts the property change listener at the particular index in the listeners' chain.
	 *
	 * @param manager      the KeyboardFocusManager where the listener will be inserted.
	 * @param l            the listener to be inserted
	 * @param propertyName the name of the property. Could be null.
	 * @param index        the index to be inserted
	 */
	public static void insertPropertyChangeListener(KeyboardFocusManager manager, PropertyChangeListener l, String propertyName, int index) {
		PropertyChangeListener[] listeners = propertyName == null ? manager.getPropertyChangeListeners() : manager.getPropertyChangeListeners(propertyName);
		for (PropertyChangeListener listener : listeners) {
			if (propertyName == null) {
				manager.removePropertyChangeListener(listener);
			} else {
				manager.removePropertyChangeListener(propertyName, listener);
			}
		}
		for (int i = 0; i < listeners.length; i++) {
			PropertyChangeListener listener = listeners[i];
			if (index == i) {
				if (propertyName == null) {
					manager.addPropertyChangeListener(l);
				} else {
					manager.addPropertyChangeListener(propertyName, l);
				}
			}
			if (propertyName == null) {
				manager.addPropertyChangeListener(listener);
			} else {
				manager.addPropertyChangeListener(propertyName, listener);
			}
		}
		// index is too large, add to the end.
		if (index > listeners.length - 1) {
			if (propertyName == null) {
				manager.addPropertyChangeListener(l);
			} else {
				manager.addPropertyChangeListener(propertyName, l);
			}
		}
	}

	/**
	 * Inserts the mouse listener at the particular index in the listeners' chain.
	 *
	 * @param component
	 * @param l
	 * @param index
	 */
	public static void insertMouseListener(Component component, MouseListener l, int index) {
		MouseListener[] listeners = component.getMouseListeners();
		for (MouseListener listener : listeners) {
			component.removeMouseListener(listener);
		}
		for (int i = 0; i < listeners.length; i++) {
			MouseListener listener = listeners[i];
			if (index == i) {
				component.addMouseListener(l);
			}
			component.addMouseListener(listener);
		}
		// index is too large, add to the end.
		if (index < 0 || index > listeners.length - 1) {
			component.addMouseListener(l);
		}
	}

	/**
	 * Inserts the mouse motion listener at the particular index in the listeners' chain.
	 *
	 * @param component
	 * @param l
	 * @param index
	 */
	public static void insertMouseMotionListener(Component component, MouseMotionListener l, int index) {
		MouseMotionListener[] listeners = component.getMouseMotionListeners();
		for (MouseMotionListener listener : listeners) {
			component.removeMouseMotionListener(listener);
		}
		for (int i = 0; i < listeners.length; i++) {
			MouseMotionListener listener = listeners[i];
			if (index == i) {
				component.addMouseMotionListener(l);
			}
			component.addMouseMotionListener(listener);
		}
		// index is too large, add to the end.
		if (index < 0 || index > listeners.length - 1) {
			component.addMouseMotionListener(l);
		}
	}

	/**
	 * Checks if the property change listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isPropertyChangeListenerRegistered(Component component, PropertyChangeListener l) {
		return Arrays.stream(component.getPropertyChangeListeners()).anyMatch(propertyChangeListener -> propertyChangeListener == l);
	}

	/**
	 * Checks if the property change listener is already registered on the component.
	 *
	 * @param component    the component
	 * @param propertyName the property name
	 * @param l            the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isPropertyChangeListenerRegistered(Component component, String propertyName, PropertyChangeListener l) {
		if (propertyName == null) return isPropertyChangeListenerRegistered(component, l);
		return Arrays.stream(component.getPropertyChangeListeners(propertyName)).anyMatch(propertyChangeListener -> propertyChangeListener == l);
	}

	/**
	 * Checks if the mouse listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isMouseListenerRegistered(Component component, MouseListener l) {
		return Arrays.stream(component.getMouseListeners()).anyMatch(mouseListener -> mouseListener == l);
	}


	/**
	 * Checks if the mouse motion listener is already registered on the component.
	 *
	 * @param component the component
	 * @param l         the listener
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isMouseMotionListenerRegistered(Component component, MouseMotionListener l) {
		return Arrays.stream(component.getMouseMotionListeners()).anyMatch(mouseMotionListener -> mouseMotionListener == l);
	}

	/**
	 * Checks if the listener is always registered to the EventListenerList to avoid duplicated registration of the same
	 * listener
	 *
	 * @param list the EventListenerList to register the listener.
	 * @param t    the type of the EventListener.
	 * @param l    the listener.
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isListenerRegistered(EventListenerList list, Class t, EventListener l) {
		Object[] objects = list.getListenerList();
		return isListenerRegistered(objects, t, l);
	}

	/**
	 * Checks if the listener is always registered to the Component to avoid duplicated registration of the same
	 * listener
	 *
	 * @param component the component that you want to register the listener.
	 * @param t         the type of the EventListener.
	 * @param l         the listener.
	 * @return true if already registered. Otherwise false.
	 */
	public static boolean isListenerRegistered(Component component, Class t, EventListener l) {
		Object[] objects = component.getListeners(t);
		return isListenerRegistered(objects, t, l);
	}

	private static boolean isListenerRegistered(Object[] objects, Class t, EventListener l) {
		// TODO 为什么这么遍历？百思不得其解
		for (int i = objects.length - 2; i >= 0; i -= 2) {
			if ((objects[i] == t) && (objects[i + 1].equals(l))) {
				return true;
			}
		}
		return false;
	}

	public static void retargetMouseEvent(int id, MouseEvent e, Component target) {
		if (target == null || (target == e.getSource() && id == e.getID())) {
			return;
		}
		if (e.isConsumed()) {
			return;
		}

		// fix for bug #4202966 -- hania
		// When re-targeting a mouse event, we need to translate
		// the event's coordinates relative to the target.

		Point p = SwingUtilities.convertPoint((Component) e.getSource(),
				e.getX(), e.getY(),
				target);
		MouseEvent retargeted = new MouseEvent(target,
				id,
				e.getWhen(),
				e.getModifiersEx() | e.getModifiersEx(),
				p.x, p.y,
				e.getClickCount(),
				e.isPopupTrigger(),
				e.getButton());
		target.dispatchEvent(retargeted);
	}

	/**
	 * Checks if the ctrl key is pressed. On Mac oS X, it will be command key.
	 *
	 * @param event the InputEvent.
	 * @return true or false.
	 */
	public static boolean isMenuShortcutKeyDown(InputEvent event) {
		return (event.getModifiersEx() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()) != 0;
	}

	/**
	 * Checks if the ctrl key is pressed. On Mac oS X, it will be command key.
	 *
	 * @param event the InputEvent.
	 * @return true or false.
	 */
	public static boolean isMenuShortcutKeyDown(ActionEvent event) {
		return (event.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()) != 0;
	}

	private static ChangeListener _viewportSyncListener;

	/** 获取视口同步更改侦听器 */
	public static ChangeListener getViewportSynchronizationChangeListener() {
		if (_viewportSyncListener == null) {
			_viewportSyncListener = new ViewportSynchronizationChangeListener();
		}
		return _viewportSyncListener;
	}

	/**
	 * 同步两个视口。主视口的视口位置发生变化，从视口的视口位置也会发生变化。
	 * 一般来说，如果你想让两个视口在垂直方向上同步，它们应该有相同的高度。如果水平就应该宽度相同。
	 * <p>
	 * 如果您使用相同的主视口和从视口重复调用此方法，则可以。它不会导致触发多个事件。
	 *
	 * @param masterViewport 主视口
	 * @param slaveViewport  从视口
	 * @param orientation    它可以是 {@link SwingConstants#HORIZONTAL} 或 {@link SwingConstants#VERTICAL}
	 */
	public static void synchronizeView(final JViewport masterViewport, final JViewport slaveViewport, final int orientation) {
		if (masterViewport == null || slaveViewport == null) {
			return;
		}
		ChangeListener[] changeListeners = masterViewport.getChangeListeners();
		// 添加视口改变监听器，但如果已经添加了就不再添加
		int i = 0;
		for (; i < changeListeners.length; i++) {
			if (changeListeners[i] == getViewportSynchronizationChangeListener()) break;
		}
		if (i >= changeListeners.length) {
			masterViewport.addChangeListener(getViewportSynchronizationChangeListener());
		}

		// 将从视口添加到客户端属性中
		Object property = masterViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT);
		if (!(property instanceof Map)) {
			property = new HashMap<JViewport, Integer>();
		}
		Map<JViewport, Integer> slaveViewportMap = (Map) property;
		slaveViewportMap.put(slaveViewport, orientation);
		masterViewport.putClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT, slaveViewportMap);

		// 将主视口添加到客户端属性中
		property = slaveViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_MASTER_VIEWPORT);
		if (!(property instanceof Map)) {
			property = new HashMap<JViewport, Integer>();
		}
		Map<JViewport, Integer> masterViewportMap = (Map) property;
		masterViewportMap.put(masterViewport, orientation);
		slaveViewport.putClientProperty(JideScrollPane.CLIENT_PROPERTY_MASTER_VIEWPORT, masterViewportMap);
	}

	/**
	 * 取消同步两个视口
	 *
	 * @param masterViewport 主视口
	 * @param slaveViewport  从视口
	 */
	public static void unsynchronizeView(final JViewport masterViewport, final JViewport slaveViewport) {
		if (masterViewport == null || slaveViewport == null) {
			return;
		}
		Object property = masterViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT);
		if (property instanceof Map slaveViewportMap) {
			slaveViewportMap.remove(slaveViewport);
			if (slaveViewportMap.isEmpty()) {
				slaveViewportMap = null;
				masterViewport.removeChangeListener(getViewportSynchronizationChangeListener());
			}
			masterViewport.putClientProperty(JideScrollPane.CLIENT_PROPERTY_SLAVE_VIEWPORT, slaveViewportMap);
		}

		property = slaveViewport.getClientProperty(JideScrollPane.CLIENT_PROPERTY_MASTER_VIEWPORT);
		if (property instanceof Map masterViewportMap) {
			masterViewportMap.remove(masterViewport);
			if (masterViewportMap.isEmpty()) {
				masterViewportMap = null;
			}
			slaveViewport.putClientProperty(JideScrollPane.CLIENT_PROPERTY_MASTER_VIEWPORT, masterViewportMap);
		}
	}

	// ---------------------------------------------------------------------
	// 递归处理组件
	// ---------------------------------------------------------------------

	/**
	 * 在组件上递归调用处理程序。
	 *
	 * @param c       component
	 * @param handler handler to be called
	 */
	public static void setRecursively(final Component c, final Handler handler) {
		setRecursively0(c, handler);
		handler.postAction(c);
	}

	private static void setRecursively0(final Component c, final Handler handler) {
		if (handler.condition(c)) {
			handler.action(c);
		}

		if (handler instanceof ConditionHandler && ((ConditionHandler) handler).stopCondition(c)) {
			return;
		}

		Component[] children = null;

		if (c instanceof JMenu) {
			children = ((JMenu) c).getMenuComponents();
		} else if (c instanceof JTabbedPane tabbedPane) {
			children = new Component[tabbedPane.getTabCount()];
			for (int i = 0; i < children.length; i++) {
				children[i] = tabbedPane.getComponentAt(i);
			}
		} else if (c instanceof Container) {
			children = ((Container) c).getComponents();
		}
		if (children != null) {
			for (Component child : children) {
				setRecursively0(child, handler);
			}
		}
	}

	/**
	 * Gets to a child of a component recursively based on certain condition.
	 *
	 * @param c       component
	 * @param handler handler to be called
	 * @return the component that matches the condition specified in GetHandler.
	 */
	public static Component getRecursively(final Component c, final GetHandler handler) {
		return getRecursively0(c, handler);
	}

	private static Component getRecursively0(final Component c, final GetHandler handler) {
		if (handler.condition(c)) {
			return handler.action(c);
		}

		Component[] children = null;

		if (c instanceof JMenu) {
			children = ((JMenu) c).getMenuComponents();
		} else if (c instanceof Container) {
			children = ((Container) c).getComponents();
		}

		if (children != null) {
			for (Component child : children) {
				Component result = getRecursively0(child, handler);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the first component inside the specified container that has the specified name.
	 *
	 * @param c    the container
	 * @param name the name of the component
	 * @return the component. Null if not found.
	 */
	public static Component findFirstComponentByName(final Container c, final String name) {
		if (name != null && name.trim().length() != 0) {
			return getRecursively(c, new GetHandler() {
				@Override
				public boolean condition(Component c) {
					return name.equals(c.getName());
				}

				@Override
				public Component action(Component c) {
					return c;
				}
			});
		} else {
			return null;
		}
	}

	/**
	 * Gets the first component inside the specified container that has the specified class.
	 *
	 * @param c     the container
	 * @param clazz the class of the component
	 * @return the component. Null if not found.
	 */
	public static Component findFirstComponentByClass(final Container c, final Class<?> clazz) {
		if (clazz != null) {
			return getRecursively(c, new GetHandler() {
				@Override
				public boolean condition(Component c) {
					return c.getClass().isAssignableFrom(clazz);
				}

				@Override
				public Component action(Component c) {
					return c;
				}
			});
		} else {
			return null;
		}
	}

	/**
	 * Calls setEnabled method recursively on component. <code>Component</code> c is usually a <code>Container</code>
	 *
	 * @param c       component
	 * @param enabled true if enable; false otherwise
	 */
	public static void setEnabledRecursively(final Component c, final boolean enabled) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return true;
			}

			public void action(Component c) {
				c.setEnabled(enabled);
			}

			public void postAction(Component c) {
			}
		});
	}

	/**
	 * Calls putClientProperty method recursively on component and its child components as long as it is JComponent.
	 *
	 * @param c              component
	 * @param clientProperty the client property name
	 * @param value          the value for the client property
	 */
	public static void putClientPropertyRecursively(final Component c, final String clientProperty, final Object value) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return c instanceof JComponent;
			}

			public void action(Component c) {
				((JComponent) c).putClientProperty(clientProperty, value);
			}

			public void postAction(Component c) {
			}
		});
	}

	/**
	 * Calls setRequestFocusEnabled method recursively on component. <code>Component</code> c is usually a
	 * <code>Container</code>
	 *
	 * @param c       component
	 * @param enabled true if setRequestFocusEnabled to true; false otherwise
	 */
	public static void setRequestFocusEnabledRecursively(final Component c, final boolean enabled) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return true;
			}

			public void action(Component c) {
				if (c instanceof JComponent)
					((JComponent) c).setRequestFocusEnabled(enabled);
			}

			public void postAction(Component c) {
			}
		});
	}

	private static PropertyChangeListener _setOpaqueTrueListener;
	private static PropertyChangeListener _setOpaqueFalseListener;

	private static final String OPAQUE_LISTENER = "setOpaqueRecursively.opaqueListener";

	/**
	 * setOpaqueRecursively method will make all child components opaque true or false. But if you call
	 * jcomponent.putClientProperty(SET_OPAQUE_RECURSIVELY_EXCLUDED, Boolean.TRUE), we will not touch this particular
	 * component when setOpaqueRecursively.
	 */
	public static final String SET_OPAQUE_RECURSIVELY_EXCLUDED = "setOpaqueRecursively.excluded";

	/**
	 * 在除 JButton、JComboBox 和 JTextComponent 之外的每个组件上递归调用 setOpaque 方法。 Component c 通常是一个Container。该方
	 * 法添加的监听器监听opaque属性改变的时候会把值改回来，也就是用该方法设置了opaque就不会因外界调用setOpaque而改变了。
	 * <p>
	 * 如果您希望某个子组件不受此调用的影响，您可以在调用此方法之前调用
	 * <pre>
	 *     jcomponent.putClientProperty(SET_OPAQUE_RECURSIVELY_EXCLUDED, Boolean.TRUE)。
	 * </pre>
	 *
	 * @param c      component
	 * @param opaque true if setOpaque to true; false otherwise
	 */
	public static void setOpaqueRecursively(final Component c, final boolean opaque) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				if (c instanceof JComboBox || c instanceof JButton || c instanceof JTextComponent ||
						c instanceof ListCellRenderer || c instanceof TreeCellRenderer || c instanceof TableCellRenderer || c instanceof CellEditor)
					return false;

				if (!(c instanceof JComponent jc)) return false;
				if (Boolean.TRUE.equals(jc.getClientProperty(SET_OPAQUE_RECURSIVELY_EXCLUDED))) return false;

				return true;
			}

			public void action(Component c) {
				JComponent jc = (JComponent) c;

				Object clientProperty = jc.getClientProperty(OPAQUE_LISTENER);
				if (clientProperty != null) {
					jc.removePropertyChangeListener("opaque", (PropertyChangeListener) clientProperty);
					jc.putClientProperty(OPAQUE_LISTENER, null);
				}
				jc.setOpaque(opaque);

				if (opaque) {
					if (_setOpaqueTrueListener == null) {
						_setOpaqueTrueListener = new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {
								// opaque修改后会再改回true
								if (evt.getSource() instanceof JComponent) {
									Component component = (Component) evt.getSource();
									component.removePropertyChangeListener("opaque", this);
									if (component instanceof JComponent)
										((JComponent) component).setOpaque(true);
									component.addPropertyChangeListener("opaque", this);
								}
							}
						};
					}
					jc.addPropertyChangeListener("opaque", _setOpaqueTrueListener);
					jc.putClientProperty(OPAQUE_LISTENER, _setOpaqueTrueListener);
				} else {
					if (_setOpaqueFalseListener == null) {
						_setOpaqueFalseListener = new PropertyChangeListener() {
							public void propertyChange(PropertyChangeEvent evt) {
								// opaque修改后会再改回false
								if (evt.getSource() instanceof JComponent) {
									Component component = (Component) evt.getSource();
									component.removePropertyChangeListener("opaque", this);
									if (component instanceof JComponent)
										((JComponent) component).setOpaque(false);
									component.addPropertyChangeListener("opaque", this);
								}
							}
						};
					}
					jc.addPropertyChangeListener("opaque", _setOpaqueFalseListener);
					jc.putClientProperty(OPAQUE_LISTENER, _setOpaqueFalseListener);
				}
			}

			public void postAction(Component c) {
			}
		});
	}

	/**
	 * 禁用组件及其子组件的双缓冲标志。返回映射包含双缓冲的组件。在此调用之后，您可以使用从该方法返回的映射使用
	 * {@link #restoreDoubleBuffered(Component, Map)}恢复双缓冲标志。
	 *
	 * @param c 父容器
	 * @return 包含所有双缓冲组件的映射，键为组件及子组件，值为是否使用了双缓冲
	 */
	public static Map<Component, Boolean> disableDoubleBuffered(final Component c) {
		final Map<Component, Boolean> map = new HashMap<>();
		if (c instanceof JComponent) {
			setRecursively(c, new Handler() {
				public boolean condition(Component c) {
					return c instanceof JComponent && c.isDoubleBuffered();
				}

				public void action(Component c) {
					map.put(c, Boolean.TRUE);
					((JComponent) c).setDoubleBuffered(false);
				}

				public void postAction(Component c) {
				}
			});
		}
		return map;
	}

	/**
	 * Enables the double buffered flag of the component and its children. The return map contains the components that
	 * weren't double buffered. After this call, you can then restore the double buffered flag using {@link
	 * #restoreDoubleBuffered(Component, Map)} using the map that is returned from this method.
	 *
	 * @param c the parent container.
	 * @return the map that contains all components that weren't double buffered.
	 */
	public static Map<Component, Boolean> enableDoubleBuffered(final Component c) {
		final Map<Component, Boolean> map = new HashMap<>();
		if (c instanceof JComponent) {
			setRecursively(c, new Handler() {
				public boolean condition(Component c) {
					return c instanceof JComponent && !c.isDoubleBuffered();
				}

				public void action(Component c) {
					map.put(c, Boolean.FALSE);
					((JComponent) c).setDoubleBuffered(true);
				}

				public void postAction(Component c) {

				}
			});
		}
		return map;
	}

	/**
	 * Restores the double buffered flag of the component and its children. Only components that are in the map will be
	 * changed.
	 *
	 * @param c   the parent container.
	 * @param map a map maps from component to a boolean. If the boolean is true, it means the component was double
	 *            buffered bore. Otherwise, not double buffered.
	 */
	public static void restoreDoubleBuffered(final Component c, final Map<Component, Boolean> map) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return c instanceof JComponent;
			}

			public void action(Component c) {
				Boolean value = map.get(c);
				if (value != null) {
					((JComponent) c).setDoubleBuffered(Boolean.TRUE.equals(value));
				}
			}

			public void postAction(Component c) {
			}
		});
	}

	/**
	 * Sets the locale recursively on the component and all its child components if any.
	 *
	 * @param c      the component
	 * @param locale the new locales.
	 */
	public static void setLocaleRecursively(final Component c, final Locale locale) {
		setRecursively(c, new Handler() {
			public boolean condition(Component c) {
				return true;
			}

			public void action(Component c) {
				c.setLocale(locale);
			}

			public void postAction(Component c) {
			}
		});
	}

	/**
	 * Invalidate and doLayout on the component and all its child components if any.
	 *
	 * @param c the component
	 */
	public static void invalidateRecursively(final Component c) {
		if (c instanceof JComponent) {
			setRecursively(c, new Handler() {
				public boolean condition(Component c) {
					return true;
				}

				public void action(Component c) {
					if (c instanceof JComponent) c.revalidate();
					c.invalidate();
				}

				public void postAction(Component c) {
				}
			});
		}
		c.doLayout();
		c.repaint();
	}

	/**
	 * Gets the first JComponent from the RootPaneContainer.
	 *
	 * @param rootPaneContainer a rootPaneContainer
	 * @return the first JComponent from the rootPaneContainer's content pane.
	 */
	public static JComponent getFirstJComponent(RootPaneContainer rootPaneContainer) {
		return (JComponent) getRecursively(rootPaneContainer.getContentPane(), new GetHandler() {
			public boolean condition(Component c) {
				return c instanceof JComponent;
			}

			public Component action(Component c) {
				return c;
			}
		});
	}

	// ---------------------------------------------------------------------
	// 组件属性
	// ---------------------------------------------------------------------


	/**
	 * Toggles between RTL and LTR.
	 *
	 * @param topContainer the component
	 */
	public static void toggleRTLnLTR(Component topContainer) {
		ComponentOrientation co = topContainer.getComponentOrientation();
		if (co == ComponentOrientation.RIGHT_TO_LEFT)
			co = ComponentOrientation.LEFT_TO_RIGHT;
		else
			co = ComponentOrientation.RIGHT_TO_LEFT;
		topContainer.applyComponentOrientation(co);
	}

	/**
	 * 获取按钮此时状态，返回值为 {@link ThemePainter} 内的常量
	 *
	 * @param b 按钮
	 * @return {@link ThemePainter} 内的常量，表示按钮此时的状态
	 */
	public static int getButtonState(AbstractButton b) {
		ButtonModel model = b.getModel();
		if (!model.isEnabled()) {
			if (model.isSelected()) return ThemePainter.STATE_DISABLE_SELECTED;
			else return ThemePainter.STATE_DISABLE;
		} else if (model.isPressed() && model.isArmed()) {
			if (model.isRollover()) return ThemePainter.STATE_PRESSED;
			else if (model.isSelected()) return ThemePainter.STATE_SELECTED;
		} else if (b.isRolloverEnabled() && model.isRollover()) {
			if (model.isSelected()) return ThemePainter.STATE_PRESSED; // should be rollover selected
			else return ThemePainter.STATE_ROLLOVER;
		} else if (model.isSelected()) return ThemePainter.STATE_SELECTED;
		else if (b.hasFocus() && b.isFocusPainted()) {
			if (model.isSelected()) return ThemePainter.STATE_PRESSED;
			else return ThemePainter.STATE_ROLLOVER;
		}
		return ThemePainter.STATE_DEFAULT;
	}

	/**
	 * 返回按钮此时的状态数组
	 */
	public static int[] getButtonState(JideSplitButton b) {
		int[] states = new int[2];
		SplitButtonModel model = (SplitButtonModel) b.getModel();
		if (!model.isEnabled()) {
			if (model.isButtonSelected()) {
				states[0] = ThemePainter.STATE_DISABLE_SELECTED;
			} else {
				states[0] = ThemePainter.STATE_DISABLE;
			}
		} else if (b.hasFocus() && b.isFocusPainted()) {
			if (model.isButtonSelected()) {
				states[0] = ThemePainter.STATE_SELECTED;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			} else if (model.isSelected()) {
				states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
				states[1] = ThemePainter.STATE_SELECTED;
			} else {
				states[0] = ThemePainter.STATE_ROLLOVER;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			}
		} else if (model.isPressed() && model.isArmed()) {
			if (model.isButtonRollover()) {
				states[0] = ThemePainter.STATE_PRESSED;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			} else if (model.isRollover()) {
				states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
				states[1] = ThemePainter.STATE_ROLLOVER;
			}
		} else if (b.isRolloverEnabled() && model.isButtonRollover()) {
			if (model.isButtonSelected()) {
				states[0] = ThemePainter.STATE_PRESSED;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			} else if (model.isSelected()) {
				states[0] = ThemePainter.STATE_ROLLOVER;
				states[1] = ThemePainter.STATE_PRESSED;
			} else {
				states[0] = ThemePainter.STATE_ROLLOVER;
				states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			}
		} else if (b.isRolloverEnabled() && model.isRollover()) {
			if (model.isButtonSelected()) {
				states[0] = ThemePainter.STATE_PRESSED;
				states[1] = ThemePainter.STATE_ROLLOVER;
			} else if (model.isSelected()) {
				states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
				states[1] = ThemePainter.STATE_PRESSED;
			} else {
				states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
				states[1] = ThemePainter.STATE_ROLLOVER;
			}
		} else if (model.isButtonSelected()) {
			states[0] = ThemePainter.STATE_SELECTED;
			states[1] = ThemePainter.STATE_INACTIVE_ROLLOVER;
		} else if (model.isSelected()) {
			states[0] = ThemePainter.STATE_INACTIVE_ROLLOVER;
			states[1] = ThemePainter.STATE_SELECTED;
		} else {
			states[0] = ThemePainter.STATE_DEFAULT;
			states[1] = ThemePainter.STATE_DEFAULT;
		}
		return states;
	}


	public static Dimension getPreferredButtonSize(AbstractButton b, int textIconGap) {
		if (b.getComponentCount() > 0) {
			return null;
		}

		Icon icon = b.getIcon();
		String text = b.getText();

		Font font = b.getFont();
		FontMetrics fm = b.getFontMetrics(font);

		Rectangle iconR = new Rectangle();
		Rectangle textR = new Rectangle();
		Rectangle viewR = new Rectangle(Short.MAX_VALUE, Short.MAX_VALUE);

		SwingUtilities.layoutCompoundLabel(b, fm, text, icon,
				b.getVerticalAlignment(), b.getHorizontalAlignment(),
				b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
				viewR, iconR, textR, (text == null ? 0 : textIconGap));
		/* The preferred size of the button is the size of
		 * the text and icon rectangles plus the buttons insets.
		 */

		Rectangle r = iconR.union(textR);

		Insets insets = b.getInsets();
		r.width += insets.left + insets.right;
		r.height += insets.top + insets.bottom;

		return r.getSize();
	}

	public static int getOrientationOf(Component component) {
		if (component instanceof Alignable) {
			return ((Alignable) component).getOrientation();
		} else if (component instanceof JComponent) {
			Integer value = (Integer) ((JComponent) component).getClientProperty(Alignable.PROPERTY_ORIENTATION);
			if (value != null)
				return value;
		}
		return HORIZONTAL;
	}

	/**
	 * 设置组件的 orientation 属性，如果组件实现{@link Alignable}接口则直接调用方法，否则以客户端属性的方式记录orientation属性值
	 *
	 * @param component   组件
	 * @param orientation 方向
	 */
	public static void setOrientationOf(Component component, int orientation) {
		int old = getOrientationOf(component);
		if (orientation != old) {
			if (component instanceof Alignable) {
				((Alignable) component).setOrientation(orientation);
			} else if (component instanceof JComponent) {
				((JComponent) component).putClientProperty(Alignable.PROPERTY_ORIENTATION, orientation);
			}
		}
	}

	public static void setChildrenOrientationOf(Container c, int orientation) {
		Component[] components = c.getComponents();
		for (Component component : components) {
			setOrientationOf(component, orientation);
		}
	}

	/**
	 * 获取当前窗口的顶部模态对话框
	 *
	 * @param w
	 * @return 当前窗口的顶部模态对话框
	 */
	public static Window getTopModalDialog(Window w) {
		Window[] ws = w.getOwnedWindows();
		for (Window w1 : ws) {
			if (w1.isVisible() && w1 instanceof Dialog && ((Dialog) w1).getModalityType() != Dialog.ModalityType.MODELESS) {
				return getTopModalDialog(w1);
			}
		}
		return w;
	}

	public static void runGCAndPrintFreeMemory() {
		java.text.DecimalFormat memFormatter = new java.text.DecimalFormat("###,###,##0.####");
		String memFree = memFormatter.format(Runtime.getRuntime().freeMemory() / 1024);
		String memTotal = memFormatter.format(Runtime.getRuntime().totalMemory() / 1024);
		String memUsed = memFormatter.format((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
		System.out.println("before gc: (Total [" + memTotal + "k] - Free [" + memFree + "k]) = Used [" + memUsed + "k]");
		System.runFinalization();
		System.gc();
		try {
			// give the gc time.
			Thread.sleep(100);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		memFree = memFormatter.format(Runtime.getRuntime().freeMemory() / 1024);
		memTotal = memFormatter.format(Runtime.getRuntime().totalMemory() / 1024);
		memUsed = memFormatter.format((Runtime.getRuntime().totalMemory() - Runtime
				.getRuntime().freeMemory()) / 1024);
		System.out.println("after gc: (Total [" + memTotal + "k] - Free ["
				+ memFree + "k]) = Used [" + memUsed + "k]");
	}

	/**
	 * Gets the scroll pane around the component.
	 *
	 * @param innerComponent
	 * @return the scroll pane. Null if the component is not in any JScrollPane.
	 */
	public static Component getScrollPane(Component innerComponent) {
		if (innerComponent instanceof JScrollPane) {
			return innerComponent;
		}
		if (innerComponent.getParent() != null
				&& innerComponent.getParent().getParent() != null
				&& innerComponent.getParent().getParent() instanceof JScrollPane) {
			return innerComponent.getParent().getParent();
		} else {
			return null;
		}
	}

	/**
	 * Get the index of the component in the container. It will return -1 if c's parent is not container.
	 *
	 * @param container the container
	 * @param c         the component
	 * @return the index
	 */
	public static int getComponentIndex(Container container, Component c) {
		if (c.getParent() != container) {
			return -1;
		}
		Component[] children = container.getComponents();
		for (int i = 0; i < children.length; i++) {
			if (children[i] == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 确保该行可见。如果表格的水平滚动条可见，则该方法不会更改水平滚动条的位置。
	 *
	 * @param table 表格
	 * @param row   希望可见的行
	 */
	public static void ensureRowVisible(JTable table, int row) {
		Rectangle r = table.getVisibleRect();
		// Hack! make above and below visible if necessary
		// TODO: how to center it or make it the first?
		Rectangle rMid = table.getCellRect(row, 0, true);
		Rectangle rBefore = null, rAfter = null;
		if (row < table.getModel().getRowCount() - 1)
			rAfter = table.getCellRect(row + 1, 0, true);
		if (row > 0)
			rBefore = table.getCellRect(row - 1, 0, true);

		int yLow = (int) rMid.getMinY();
		int yHi = (int) rMid.getMaxY();
		int xLow = r.x;
		int xHi = r.x + r.width;

		if (rBefore != null)
			yLow = (int) rBefore.getMinY();

		if (rAfter != null) {
			yHi = (int) rAfter.getMaxY();
		}

		Rectangle rScrollTo = new Rectangle(xLow, yLow, xHi - xLow, yHi - yLow);
		if (!r.contains(rScrollTo) && rScrollTo.height != 0) {
			table.scrollRectToVisible(rScrollTo);
		}
	}

	/**
	 * If c is a JRootPane descendant return its outermost JRootPane ancestor. If c is a RootPaneContainer then return
	 * its JRootPane.
	 *
	 * @param c the component.
	 * @return the outermost JRootPane for Component c or {@code null}.
	 */
	public static JRootPane getOutermostRootPane(Component c) {
		if (c instanceof RootPaneContainer && c.getParent() == null) {
			return ((RootPaneContainer) c).getRootPane();
		}
		JRootPane lastRootPane;
		for (; c != null; c = SwingUtilities.getRootPane(c)) {
			if (c instanceof JRootPane) {
				lastRootPane = (JRootPane) c;
				if (c.getParent().getParent() == null) {
					return lastRootPane;
				}
				if (c.getParent() instanceof JDialog || c.getParent() instanceof JWindow
						|| c.getParent() instanceof JFrame) {
					return lastRootPane;
				}
				c = c.getParent().getParent();
			}
		}
		return null;
	}

	/**
	 * Checks if the font specified by the font name is fixed width font. Fixed width font means all chars have the
	 * exact same width.
	 *
	 * @param fontName  the font name
	 * @param component the component where the font will be displayed.
	 * @return true if the font is fixed width. Otherwise false.
	 */
	public static boolean isFixedWidthFont(String fontName, Component component) {
		if (fontName.endsWith(" Bold") || fontName.endsWith(" ITC") || fontName.endsWith(" MT") || fontName.endsWith(" LET")
				|| fontName.endsWith(".bold") || fontName.endsWith(".italic"))
			return false;
		try {
			Font font = new Font(fontName, Font.PLAIN, 12);
			if (!font.canDisplay('W'))
				return false;
			Font boldFont = font.deriveFont(Font.BOLD);
			FontMetrics fm = component.getFontMetrics(font);
			FontMetrics fmBold = component.getFontMetrics(boldFont);
			int l1 = fm.charWidth('l');
			int l2 = fmBold.charWidth('l');
			if (l1 == l2) {
				int w1 = fm.charWidth('W');
				int w2 = fmBold.charWidth('W');
				if (w1 == w2 && l1 == w1) {
					int s1 = fm.charWidth(' ');
					int s2 = fmBold.charWidth(' ');
					if (s1 == s2) {
						return true;
					}
				}
			}
		} catch (Throwable throwable) {
			// ignore it and return false
		}
		return false;
	}

	/**
	 * Sets the bounds. If the container orientation is from right to left, this method will adjust the x to the
	 * opposite.
	 *
	 * @param container the container. It is usually the parent of the component.
	 * @param component the component to set bounds
	 * @param bounds    the bounds.
	 */
	public static void setBounds(Container container, Component component, Rectangle bounds) {
		if (container.getComponentOrientation().isLeftToRight()) {
			component.setBounds(bounds);
		} else {
			Rectangle r = new Rectangle(bounds);
			int w = container.getWidth();
			r.x = w - (bounds.x + bounds.width);
			component.setBounds(r);
		}
	}

	/**
	 * Sets the bounds. If the container orientation is from right to left, this method will adjust the x to the
	 * opposite.
	 *
	 * @param container the container. It is usually the parent of the component.
	 * @param component the component to set bounds
	 * @param x         the x of the bounds
	 * @param y         the y of the bounds
	 * @param width     the the height of the bounds. of the bounds.
	 * @param height    the height of the bounds.
	 */
	public static void setBounds(Container container, Component component, int x, int y, int width, int height) {
		if (container.getComponentOrientation().isLeftToRight()) {
			component.setBounds(x, y, width, height);
		} else {
			int w = container.getWidth();
			component.setBounds(w - x - width, y, width, height);
		}
	}

	/**
	 * Registers all actions registered on the source component and registered them on the target component at the
	 * specified condition.
	 *
	 * @param sourceComponent the source component.
	 * @param targetComponent the target component.
	 * @param keyStrokes      the keystrokes
	 * @param condition       the condition which will be used in {@link JComponent#registerKeyboardAction(ActionListener,
	 *                        KeyStroke, int)} as the last parameter.
	 */
	public static void synchronizeKeyboardActions(JComponent sourceComponent, JComponent targetComponent, KeyStroke[] keyStrokes, int condition) {
		for (KeyStroke keyStroke : keyStrokes) {
			ActionListener actionListener = sourceComponent.getActionForKeyStroke(keyStroke);
			if (actionListener != null) {
				targetComponent.registerKeyboardAction(actionListener, keyStroke, condition);
			}
		}
	}

	/**
	 * The semantics in AWT of hiding a component, removing a component, and reparenting a component are inconsistent
	 * with respect to focus. By calling this function before any of the operations above focus is guaranteed a
	 * consistent degregation.
	 *
	 * @param component
	 */
	public static void removeFromParentWithFocusTransfer(Component component) {
		boolean wasVisible = component.isVisible();
		component.setVisible(false);
		if (component.getParent() != null) {
			component.getParent().remove(component);
		}
		component.setVisible(wasVisible);
	}

	/**
	 * 获取组件字体的行高
	 *
	 * @param c             组件
	 * @param defaultHeight 如果指定组件上的字体为 null，则为默认高度
	 * @return 组件字体的行高（如果指定组件上的字体为空，则传入默认值）
	 */
	public static float getLineHeight(Component c, int defaultHeight) {
		Font f = c == null ? null : c.getFont();
		if (f == null) {
			return defaultHeight;
		}
		FontMetrics fm = c.getFontMetrics(f);
		float h = fm.getHeight();

		h += fm.getDescent();

		return h;
	}

	/**
	 * Adds a separator to the popup menu if there are menu items on it already.
	 *
	 * @param popup the popup menu.
	 */
	public static void addSeparatorIfNecessary(JPopupMenu popup) {
		int count = popup.getComponentCount();
		if (count > 0 && !(popup.getComponent(count - 1) instanceof JSeparator)) {
			popup.addSeparator();
		}
	}

	/**
	 * Removes extra separators, if any. This can be used when you remove some menu items and leave extra separators on
	 * the UI.
	 *
	 * @param popup the popup menu.
	 */
	public static void removeExtraSeparators(JPopupMenu popup) {
		Component[] components = popup.getComponents();
		if (components.length <= 1) {
			return;
		}
		for (int i = 0; i < components.length; i++) {
			Component component = components[i];
			if (component instanceof JSeparator) {
				if (i == 0 || i == components.length - 1) { // if the separator is the first one or the last one, remove it because the separator is not necessary here
					popup.remove(component);
				} else if (components[i - 1] instanceof JSeparator) {
					popup.remove(component);
				}
			}
		}
	}

	/**
	 * Sets the text component transparent. It will call setOpaque(false) and also set client property for certain L&Fs
	 * in case the L&F doesn't respect the opaque flag.
	 *
	 * @param component the text component to be set to transparent.
	 */
	public static void setComponentTransparent(JComponent component) {
		component.setOpaque(false);

		// add this for the Synthetica
		component.putClientProperty("Synthetica.opaque", false);
		// add this for Nimbus to disable all the painting of a component in Nimbus
		component.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
		component.putClientProperty("Nimbus.Overrides", new UIDefaults());
	}

	/**
	 * Shows the popup menu with the consideration of the invoker's orientation.
	 *
	 * @param popup   the popup menu
	 * @param invoker the invoker for the popup menu
	 * @param x       the x, usually the x of the mouse clicked position
	 * @param y       the y, usually the y of the mouse clicked position
	 */
	public static void showPopupMenu(JPopupMenu popup, Component invoker, int x, int y) {
		popup.applyComponentOrientation(invoker.getComponentOrientation());
		if (popup.getComponentOrientation().isLeftToRight()) {
			popup.show(invoker, x, y);
		} else {
			popup.show(invoker, x - popup.getPreferredSize().width, y);
		}
	}
}
