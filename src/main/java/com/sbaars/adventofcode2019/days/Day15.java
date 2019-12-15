package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.common.Direction;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

public class Day15 implements Day {

	public static void main(String[] args) throws IOException {
		new Day15().printParts();
	}

	@Override
	public Object part1() throws IOException {
		IntcodeComputer ic = new IntcodeComputer(15);
		List<Point> walls = new ArrayList<>();
		Point pos = new Point(50,50);
		int[][] grid = new int[100][100];
		for(int[] row : grid) Arrays.fill(row, -1);
		grid[pos.y][pos.x] = 0;
		Direction dir  = Direction.NORTH;
		while(true) {
			ic.run();
		}
	}
	
	@Override
	public Object part2() throws IOException {
		return 0;
	}
}
