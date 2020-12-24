package com.sbaars.adventofcode.common;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.Math.abs;

public enum HexDirection {
	NORTHWEST(1, "nw"), EAST(4, "e"), SOUTHEAST(2, "se"), WEST(3, "w"),
	NORTHEAST(4, "ne"), SOUTHWEST(5, "sw");

	public final int num;
	public final String code;

	HexDirection(int num, String code) {
		this.num = num;
		this.code = code;
	}

	public static Optional<HexDirection> get(String code) {
		return Arrays.stream(values()).filter(e -> e.code.equals(code)).findAny();
	}
	
	public Point move(Point currentLocation, int amount) {
		switch (this) {
			case EAST: return new Point(currentLocation.x+(amount*2), currentLocation.y);
			case WEST: return new Point(currentLocation.x-(amount*2), currentLocation.y);
			case SOUTHWEST: return new Point(currentLocation.x-amount, currentLocation.y+amount);
			case NORTHEAST: return new Point(currentLocation.x+amount, currentLocation.y-amount);
			case SOUTHEAST: return new Point(currentLocation.x+amount, currentLocation.y+amount);
			case NORTHWEST: return new Point(currentLocation.x-amount, currentLocation.y-amount);
		}
		throw new IllegalStateException("Non-existent Direction: "+this);
	}

	public Point move(Point currentLocation) {
		return move(currentLocation, 1);
	}

	public HexDirection opposite() {
		switch (this) {
			case EAST: return WEST;
			case WEST: return EAST;
			case NORTHEAST: return SOUTHWEST;
			case SOUTHWEST: return NORTHEAST;
			case SOUTHEAST: return NORTHWEST;
			case NORTHWEST: return SOUTHEAST;
		}
		throw new IllegalStateException("Non-existent Direction: "+this);
	}
}