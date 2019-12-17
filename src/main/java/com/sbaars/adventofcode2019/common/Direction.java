package com.sbaars.adventofcode2019.common;

import java.awt.Point;
import java.util.Arrays;

public enum Direction {
	NORTH(1, 'U'), EAST(4, 'R'), SOUTH(2, 'D'), WEST(3, 'L');
	
	public final int num;
	public final int code;
	
	private Direction(int num, char code) {
		this.num = num;
		this.code = code;
	}
	
	public static Direction getByDirCode(char code) {
		return Arrays.stream(values()).filter(e -> e.code == code).findAny().get();
	}
	
	public Direction turn(boolean right) {
		int cur = ordinal() + (right ? 1 : -1);
		if(cur == Direction.values().length) cur = 0;
		else if(cur == -1) cur = 3;
		return Direction.values()[cur];
	}
	
	public Point move(Point currentLocation, int amount) {
		switch (this) {
			case SOUTH: return new Point(currentLocation.x, currentLocation.y+amount);
			case NORTH: return new Point(currentLocation.x, currentLocation.y-amount);
			case EAST: return new Point(currentLocation.x+amount, currentLocation.y);
			case WEST: return new Point(currentLocation.x-amount, currentLocation.y);
		}
		throw new IllegalStateException("Non-existent Direction: "+this);
	}
	
	public Point move(Point currentLocation) {
		return move(currentLocation, 1);
	}

	public Direction opposite() {
		switch (this) {
			case NORTH: return SOUTH;
			case SOUTH: return NORTH;
			case EAST: return WEST;
			case WEST: return EAST;
		}
		throw new IllegalStateException("Non-existent Direction: "+this);
	}

	public static Direction getByMove(Point from, Point to) {
		if(to.x > from.x) return EAST;
		else if(to.x < from.x) return WEST;
		else if(to.y > from.y) return SOUTH;
		else if(to.y < from.y) return NORTH;
		throw new IllegalStateException("From and to location are the same: "+from+", "+to);
	}

	public boolean leftOf(Direction robotDir) {
		int n = this.ordinal()-1;
		if(n == -1) n = values().length-1;
		return robotDir.ordinal() == n;
	}
}