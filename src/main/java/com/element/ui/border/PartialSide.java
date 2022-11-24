package com.element.ui.border;

/**
 *
 */
public interface PartialSide {
	int NORTH = 1;
	int SOUTH = 2;
	int EAST = 4;
	int WEST = 8;
	int HORIZONTAL = NORTH | SOUTH;
	int VERTICAL = EAST | WEST;
	int ALL = VERTICAL | HORIZONTAL;
}
