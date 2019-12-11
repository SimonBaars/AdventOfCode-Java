package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;
import com.sbaars.adventofcode2019.util.DoesFileOperations;

public class Day11 implements Day, DoesFileOperations {
	
	enum Direction { UP, RIGHT, DOWN, LEFT }
	
	public Direction turn(Direction dir) {
		int cur = dir.ordinal()+1;
		if(cur == Direction.values().length)
			cur = 0;
		return Direction.values()[cur];
	}

	public static void main(String[] args) throws IOException {
		new Day11().printParts();
	}

	@Override
	public int part1() throws IOException {
		return robotWalk(false).size();
	}

	private Set<Point> robotWalk(boolean startWhite) throws IOException {
		IntcodeComputer c = new IntcodeComputer(11);
		Point currentLocation = new Point(0,0);
		int steps = 0;
		Direction dir = Direction.UP;
		Set<Point> paintedOnce = new HashSet<>();
		Set<Point> whitePlaces = new HashSet<>();
		if(startWhite)
			whitePlaces.add(currentLocation);
		while(true) {
			c.setInput(whitePlaces.contains(currentLocation) ? 1 : 0);
			int paintColor = c.runInt();
			if(paintColor == -2)
				break;
			int turn = c.runInt();
			if(turn == -2)
				break;
			System.out.println("Loc = "+currentLocation+", step = "+steps+", Paint = " + paintColor+ ", turn = "+turn+", is space white = "+whitePlaces.contains(currentLocation));
			paintedOnce.add(currentLocation);
			if(paintColor == 1) {
				whitePlaces.add(currentLocation);
			} else if(paintColor == 0) {
				whitePlaces.remove(currentLocation);
			}
			
			if(turn == 1) dir = turn(dir);
			currentLocation = move(currentLocation, dir);
			steps++;
		}
		return paintedOnce;
	}
	
	private Point move(Point currentLocation, Direction dir2) {
		switch (dir2) {
			case UP: return new Point(currentLocation.x, currentLocation.y+1);
			case DOWN: return new Point(currentLocation.x, currentLocation.y-1);
			case RIGHT: return new Point(currentLocation.x+1, currentLocation.y);
			case LEFT: return new Point(currentLocation.x-1, currentLocation.y);
		}
		return null;
	}

	@Override
	public int part2() throws IOException {
		robotWalk(true);
		return 0;
	}
}
