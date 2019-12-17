package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.common.Direction;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

public class Day17 implements Day {

	public static void main(String[] args) throws IOException {
		new Day17().printParts();
	}

	@Override
	public Object part1() throws IOException {
		char[][] grid = new char[48][48];
		readDay(17);
		IntcodeComputer ic = new IntcodeComputer(17, 1);
		long res;
		int x = 0, y =0;
		int result = 0;
		while((res = ic.run()) != IntcodeComputer.STOP_CODE) {
			System.out.print((char)res);
			if(res == 10) {
				y++;
				x= 0;
			} else {
				grid[y][x] = (char)res;
				x++;
			}
		}
		for(y = 1; y<grid.length-1; y++) {
			for(x = 1; x<grid.length-1; x++) {
				if(hasAdjecent(grid, new Point(x,y), '#')) {
					result+=x*y;
				}
			}
		}
		//System.out.println(y);
		return result;
	}
	
	private boolean hasAdjecent(char[][] grid, Point pos, char tile) {
		return grid[pos.y+1][pos.x] == tile && grid[pos.y][pos.x+1] == tile && grid[pos.y-1][pos.x] == tile && grid[pos.y][pos.x-1] == tile && grid[pos.y][pos.x] == tile;
	}
	
	@Override
	public Object part2() throws IOException {
		char[][] grid = new char[48][48];
		readDay(17);
		IntcodeComputer ic = new IntcodeComputer(17, 1);
		long res;
		int x = 0, y = 0;
		while((res = ic.run()) != IntcodeComputer.STOP_CODE) {
			if(res == 10) {
				y++;
				x= 0;
			} else {
				grid[y][x] = (char)res;
				x++;
			}
		}
		//StringBuilder input = new StringBuilder();
		Point pos = findPos(grid, '^').get(0);
		List<Instruction> instructions = new ArrayList<>();
		List<Point> traversed = new ArrayList<>();
		Direction dir;
		Direction robotDir = Direction.NORTH;
		while((dir = directionContainsSomethingAndUntraversed(grid, pos, traversed)) != null) {
			int n;
			for(n = 1; getGrid(grid, dir.move(pos, n)) == '#'; n++) traversed.add(dir.move(pos, n));
			pos = dir.move(pos, n-1);
			instructions.add(new Instruction(n-1, dir.leftOf(robotDir) ? Dir.R : Dir.L));
			robotDir = dir;
		}
		System.out.println(Arrays.toString(instructions.toArray()));
		List<String> good = new ArrayList<>();
		int start = 0;
		for(int i = 1; i<instructions.size(); i++) {
			List<Instruction> is = instructions.subList(start, i);
			if(toString(is).length()>20) {
				good.add(toString(instructions.subList(start, i-1)));
				start = i-1;
			}
		}
		good.add(toString(instructions.subList(start, instructions.size())));
		good.stream().forEach(System.out::println);
		String stuff = "A,A,B,C,B,A,C,B,C,A\nL,6,R,12,L,6,L,8,L,8\nL,6,R,12,R,8,L,8\nL,4,L,4,L,6\nn\n";
		System.out.println(stuff);
		long[] asciis = stuff.chars().mapToLong(e -> e).toArray();
		ic = new IntcodeComputer(17, 2);
		ic.setInput(asciis);
		System.out.println(Arrays.toString(asciis));
		while(true) {
			if((res = ic.run())>255L)
				return res;
		}
	}

	public String toString(List<Instruction> i) {
		return i.stream().map(Instruction::toString).collect(Collectors.joining(","));
	}
	
	private char getGrid(char[][] grid, Point p) {
		if(p.x < 0 || p.y < 0 || p.x>=grid[0].length || p.y>=grid.length) return '.';
		return grid[p.y][p.x];
	}
	
	private Direction directionContainsSomethingAndUntraversed(char[][] grid, Point pos, List<Point> traversed) {
		Direction dir = Direction.NORTH;
		for(int i = 0; i<4; i++) {
			Point p = dir.move(pos);
			if(getGrid(grid, p) == '#' && !traversed.contains(p)) {
				return dir;
			}
			dir = dir.turn(true);
		}
		return null;
	}

	private List<Point> findPos(char[][] grid, char tile) {
		List<Point> positions = new ArrayList<>();
		for(int y = 0; y<grid.length; y++) {
			for(int x = 0; x<grid[y].length; x++) {
				if(grid[y][x] == tile)
					positions.add(new Point(x, y));
			}
		}
		return positions;
	}
	
	enum Dir{L,R}
	class Instruction{
		int amount;
		Dir dir;
		
		public Instruction(int amount, Dir dir) {
			super();
			this.amount = amount;
			this.dir = dir;
		}

		@Override
		public String toString() {
			return dir.name()+","+amount;
		}
	}
}
