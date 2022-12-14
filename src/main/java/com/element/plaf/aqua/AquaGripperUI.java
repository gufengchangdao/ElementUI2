package com.element.plaf.aqua;

import com.element.plaf.basic.BasicGripperUI;
import com.element.swing.Gripper;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

/**
 * @author olifink
 */
public class AquaGripperUI extends BasicGripperUI {
	public static ComponentUI createUI(JComponent c) {
		return new AquaGripperUI();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		Gripper gripper = (Gripper) c;
		paintBackground(g, gripper);
		getPainter().paintGripper(c, g, new Rectangle(0, 0, c.getWidth(), c.getHeight()), gripper.getOrientation(), 0);
	}
}