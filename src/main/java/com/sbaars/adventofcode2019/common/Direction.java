package com.sbaars.adventofcode2019.common;

import java.util.Arrays;

public enum Direction {
	NORTH, EAST, SOUTH, WEST;
	
	public char directionCode() {
		return name().charAt(0);
	}
	
	public static Direction getByDirCode(char code) {
		return Arrays.stream(values()).filter(e -> e.directionCode() == code).findAny().get();
	}
	
	public Direction turn(boolean right) {
		int cur = ordinal() + (right ? 1 : -1);
		if(cur == Direction.values().length) cur = 0;
		else if(cur == -1) cur = 3;
		return Direction.values()[cur];
	}
}