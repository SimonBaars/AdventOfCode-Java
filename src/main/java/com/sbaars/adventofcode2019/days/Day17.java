package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.common.Direction;
import com.sbaars.adventofcode2019.intcode.IntcodeComputer;

import lombok.EqualsAndHashCode;

public class Day17 implements Day {

	char[][] grid = new char[48][48];

	public Day17() throws IOException {
		IntcodeComputer ic = new IntcodeComputer(17, 1);
		long res;
		int x = 0, y =0;
		while((res = ic.run()) != IntcodeComputer.STOP_CODE) {
			if(res == 10) {
				y++;
				x= 0;
			} else {
				grid[y][x] = (char)res;
				x++;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new Day17().printParts();
	}

	@Override
	public Object part1() throws IOException {
		int result = 0;
		for(int y = 1; y<grid.length-1; y++) {
			for(int x = 1; x<grid.length-1; x++) {
				if(hasAdjecent(grid, new Point(x,y), '#')) {
					result+=x*y;
				}
			}
		}
		return result;
	}

	private boolean hasAdjecent(char[][] grid, Point pos, char tile) {
		return grid[pos.y+1][pos.x] == tile && grid[pos.y][pos.x+1] == tile && grid[pos.y-1][pos.x] == tile && grid[pos.y][pos.x-1] == tile && grid[pos.y][pos.x] == tile;
	}

	@Override
	public Object part2() throws IOException {
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
		String patterns = findPatterns(instructions) + "\nn\n";
		long[] asciis = patterns.chars().mapToLong(e -> e).toArray();
		IntcodeComputer ic = new IntcodeComputer(17, 2);
		ic.setInput(asciis);
		while(true) {
			long res = ic.run();
			if(res>255L)
				return res;
		}
	}

	private String findPatterns(List<Instruction> instructions) {
		List<List<Instruction>> patterns = new ArrayList<>();
		String patternString = "";
		int start = 0;
		for(int i = 0; i<instructions.size()-1; i++) {
			List<Instruction> pattern = existing(instructions, patterns, i);
			if(pattern!=null && start == i) {
				start += pattern.size();
				i+=pattern.size()-1;
				patternString += ","+patterns.indexOf(pattern);
				continue;
			} else if(start!=i && (pattern != null || occurrences(instructions, instructions.subList(start, i+1))<3)) {
				patternString += ","+patterns.size();
				patterns.add(instructions.subList(start, i));
				start = i;
				i--;
			}
		}
		return patternString.substring(1).replace("0", "A").replace("1", "B").replace("2", "C")+"\n"+patterns.stream().map(this::toString).collect(Collectors.joining("\n"));
	}

	private List<Instruction> existing(List<Instruction> instructions, List<List<Instruction>> patterns, int i){
		for(List<Instruction> pattern : patterns)
			if(i+pattern.size() <= instructions.size() && instructions.subList(i, i+pattern.size()).equals(pattern))
				return pattern;
		return null;
	}

	private int occurrences(List<Instruction> instructions, List<Instruction> subList) {
		return Math.toIntExact(IntStream.range(0, instructions.size()-subList.size()).filter(i -> toString(instructions.subList(i, i+subList.size())).equals(toString(subList))).count());
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
	@EqualsAndHashCode class Instruction{
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
