package com.element.swing;

import javax.swing.*;

/**
 * SwingConstants的扩展，使得每条边上都有三个方向可以选择
 */
public interface SwingPosition extends SwingConstants {
	int EAST_NORTH = 9;
	int EAST_SOUTH = 10;
	int WEST_SOUTH = 11;
	int WEST_NORTH = 12;
}
