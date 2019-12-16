package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.common.Direction;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;
import com.sbaars.adventofcode2019.pathfinding.Grid2d;

public class Day15 implements Day {
	
	private static final int UNEXPLORED = 3;
	private static final int WALL = 0;
	private static final int PATH = 1;
	private static final int FINISH = 2;
	private static final int BOARD_SIZE = 60;
	private static final Point START_POINT = new Point(BOARD_SIZE/2,BOARD_SIZE/2);

	public static void main(String[] args) throws IOException {
		new Day15().printParts();
	}

	@Override
	public Object part1() throws IOException {
		IntcodeComputer ic = new IntcodeComputer(15);
		Point pos = START_POINT;
		int[][] grid = new int[BOARD_SIZE][BOARD_SIZE];
		for(int[] row : grid) Arrays.fill(row, UNEXPLORED);
		grid[pos.y][pos.x] = 0;
		while(true) {
			System.out.println("Current pos = "+pos);
			Arrays.stream(grid).map(e -> Arrays.toString(e)).forEach(System.out::println);
			explore(grid, pos, ic);
			pos = moveToUnexploredPlace(grid, pos, ic);
			if(pos == null) {
				Grid2d map2d = new Grid2d(grid, false);
				System.out.println(Arrays.deepToString(grid));
				return map2d.findPath(START_POINT, findPos(grid, FINISH).get(0)).size();
			}
		}
	}
	
	private Point moveToUnexploredPlace(int[][] grid, Point pos, IntcodeComputer ic) {
		List<Point> corridorSpaces = findPos(grid, PATH);
		for(Point p : corridorSpaces) {
			if(hasAdjecent(grid, p, UNEXPLORED)) {
				Grid2d map2d = new Grid2d(grid, false);
				List<Point> route = map2d.findPath(pos, p);
				traverseRoute(ic, pos, route.subList(1, route.size()));
				return p;
			}
		}
		return null;
	}
	
	private void traverseRoute(IntcodeComputer ic, Point pos, List<Point> route) {
		for(Point p : route) {
			ic.run(Direction.getByMove(pos, p).num);
		}
	}

	private boolean hasAdjecent(int[][] grid, Point pos, int tile) {
		return grid[pos.y+1][pos.x] == tile || grid[pos.y][pos.x+1] == tile || grid[pos.y-1][pos.x] == tile || grid[pos.y][pos.x-1] == tile;
	}

	private List<Point> findPos(int[][] grid, int tile) {
		List<Point> positions = new ArrayList<>();
		for(int y = 0; y<grid.length; y++) {
			for(int x = 0; x<grid[y].length; x++) {
				if(grid[y][x] == tile)
					positions.add(new Point(x, y));
			}
		}
		return positions;
	}

	private void explore(int[][] grid, Point pos, IntcodeComputer ic) {
		Direction dir  = Direction.NORTH;
		for(int i = 0; i<4;i++) {
			Point move = dir.move(pos);
			if(grid[move.y][move.x] == UNEXPLORED) {
				grid[move.y][move.x] = Math.toIntExact(ic.run(dir.num));
				if(grid[move.y][move.x] != WALL) {
					ic.run(dir.opposite().num); // Move back
				}
			}
			dir = dir.turn(true);
		}
	}
	
	@Override
	public Object part2() throws IOException {
		return 0;
	}
}
