package com.element.ui.border;

/**
 * 绘制边的方向，这里使用2的幂次方的数方便位运算
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
