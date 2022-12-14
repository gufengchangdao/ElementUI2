/*
 * Copyright (c) 2005, romain guy (romain.guy@jext.org) and craig wickesser (craig@codecraig.com) and henry story
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <ORGANIZATION> nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.element.ui.notice.loading;

import com.element.swing.base.BaseComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * 线性加载面板，多个矩形绕圆心旋转布局，动画开始后矩形轮流交替变为深色。
 * <p>
 * 该面板不会为组件设置大小，因此你需要手动设置面板大小，加载动画会绘制在面板中间。
 * 例如：
 * <pre>
 * 	 InfiniteProgressPanel progressPanel = new InfiniteProgressPanel(5);
 * 	 progressPanel.setPreferredSize(new Dimension(100, 100));
 * 	 JButton b = new JButton("开始");
 * 	 b.addActionListener(e -> {
 * 	   if (progressPanel.isRunning()) progressPanel.stop();
 * 	   else progressPanel.start();
 *   });
 * </pre>
 */
public class InfiniteProgressPanel extends BaseComponent implements ActionListener {
	private static final int DEFAULT_NUMBER_OF_BARS = 12;

	private int numBars;
	private double dScale = 1.2d;

	private final Area[] bars;
	private Rectangle barsBounds;
	private Rectangle barsScreenBounds = null;
	private AffineTransform centerAndScaleTransform = null;
	private final Timer timer = new Timer(1000 / 16, this);
	private final Color[] colors;
	private int colorOffset = 0;
	private boolean tempHide = false;

	public InfiniteProgressPanel() {
		this(1);
	}

	/**
	 * 创建加载面板
	 *
	 * @param ratio 控制矩形的大小
	 */
	public InfiniteProgressPanel(double ratio) {
		this.numBars = DEFAULT_NUMBER_OF_BARS;

		colors = new Color[numBars * 2];

		// build bars
		bars = buildTicker(numBars, ratio);
		int w = 0, h = 0;
		for (Area bar : bars) {
			Rectangle bounds = bar.getBounds();
			w = Math.max(w, bounds.x + bounds.width);
			h = Math.max(h, bounds.y + bounds.height);
		}
		// calculate bars bounding rectangle
		barsBounds = new Rectangle();
		for (Area bar : bars) {
			barsBounds = barsBounds.union(bar.getBounds());
		}

		// create colors
		for (int i = 0; i < bars.length; i++) {
			int channel = 224 - 128 / (i + 1);
			colors[i] = new Color(channel, channel, channel);
			colors[numBars + i] = colors[i];
		}

		// set opaque
		setOpaque(true);
	}

	/**
	 * Called to animate the rotation of the bar's colors
	 */
	public void actionPerformed(ActionEvent e) {
		// rotate colors
		if (colorOffset == (numBars - 1)) {
			colorOffset = 0;
		} else {
			colorOffset++;
		}
		// repaint
		if (barsScreenBounds != null) {
			repaint(barsScreenBounds);
		} else {
			repaint();
		}
	}

	/**
	 * Show/Hide the pane, starting and stopping the animation as you go
	 */
	@Override
	public void setVisible(boolean i_bIsVisible) {
		setOpaque(false);
		// capture
		if (i_bIsVisible) {
			timer.start();
		} else {
			timer.stop();
		}
		super.setVisible(i_bIsVisible);
	}

	/**
	 * Recalc bars based on changes in size
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		// update centering transform
		centerAndScaleTransform = new AffineTransform();
		centerAndScaleTransform.translate((double) getWidth() / 2d, (double) getHeight() / 2d);
		centerAndScaleTransform.scale(dScale, dScale);
		// calc new bars bounds
		if (barsBounds != null) {
			Area oBounds = new Area(barsBounds);
			oBounds.transform(centerAndScaleTransform);
			barsScreenBounds = oBounds.getBounds();
		}
	}

	/**
	 * paint background dimmed and bars over top
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if (tempHide) return;

		Rectangle oClip = g.getClipBounds();
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(oClip.x, oClip.y, oClip.width, oClip.height);
		}

		// move to center
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.transform(centerAndScaleTransform);
		// draw ticker
		for (int i = 0; i < bars.length; i++) {
			g2.setColor(colors[i + colorOffset]);
			g2.fill(bars[i]);
		}
	}

	/*
	 * Builds the circular shape and returns the result as an array of <code>Area</code>. Each <code>Area</code> is one of the bars composing the shape.
	 */
	private static Area[] buildTicker(int i_iBarCount, double ratio) {
		Area[] ticker = new Area[i_iBarCount];
		Point2D.Double center = new Point2D.Double(0, 0);
		double fixedAngle = 2.0 * Math.PI / ((double) i_iBarCount);

		for (double i = 0.0; i < (double) i_iBarCount; i++) {
			Area primitive = buildPrimitive(ratio);

			AffineTransform toCenter = AffineTransform.getTranslateInstance(center.getX(), center.getY());
			AffineTransform toBorder = AffineTransform.getTranslateInstance(2.0, -0.4);
			AffineTransform toCircle = AffineTransform.getRotateInstance(-i * fixedAngle, center.getX(), center.getY());

			AffineTransform toWheel = new AffineTransform();
			toWheel.concatenate(toCenter);
			toWheel.concatenate(toBorder);

			primitive.transform(toWheel);
			primitive.transform(toCircle);

			ticker[(int) i] = primitive;
		}

		return ticker;
	}

	/*
	 * Builds a bar.
	 */
	private static Area buildPrimitive(double ratio) {
		Rectangle2D.Double body = new Rectangle2D.Double(2 * ratio, 0, 4 * ratio, ratio);
		return new Area(body);
	}

	public void start() {
		setVisible(true);
	}

	public void stop() {
		setVisible(false);
	}

	public boolean isRunning() {
		return timer.isRunning();
	}
}