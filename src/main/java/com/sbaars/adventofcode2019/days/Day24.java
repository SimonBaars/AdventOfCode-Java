package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.sbaars.adventofcode2019.common.Day;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class Day24 implements Day {
	
	Set<Hello> grids = new HashSet<>();

	public static void main(String[] args) throws IOException {
		new Day24().printParts();
	}

	@Override
	public Object part1() throws IOException {
		char[][] grid = Arrays.stream(readDay(24).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
		while(true) {
			//Arrays.stream(grid).forEach(e -> System.out.println(Arrays.toString(e)));
			if(!grids.add(new Hello(grid))) {
				return calcRes(grid);
			}
			char[][] newGrid = copy(grid);
			for(int y = 0; y<grid.length; y++) {
				for(int x = 0; x<grid[0].length; x++) {
					int adj = nAdjecent(grid, new Point(x,y));
					if(grid[y][x] == '#' && adj != 1) {
						newGrid[y][x] = '.';
					} else if(grid[y][x] == '.' && (adj == 1 || adj == 2)) {
						newGrid[y][x] = '#';
					}
				}
			}
			grid = newGrid;
		}
	}
	
	@EqualsAndHashCode @AllArgsConstructor @Data class Hello{
		char[][] grid;
	}
	
	private long calcRes(char[][] grid) {
		//Arrays.stream(grid).forEach(e -> System.out.println(Arrays.toString(e)));
		long res = 0;
		long multiplier = 1;
		for(int y = 0; y<grid.length; y++) {
			for(int x = 0; x<grid[0].length; x++) {
				if(grid[y][x] == '#') {
					res+=multiplier;	
				}
				multiplier*=2;
			}
		}
		return res;
	}

	public char[][] copy(char[][] grid){
		char[][] g2 = new char[grid.length][];
		for(int i = 0; i<grid.length; i++) {
			g2[i] = Arrays.copyOf(grid[i], grid[i].length);
		}
		return g2;
	}
	
	public int nAdjecent(char[][] grid, Point pos) {
		return num(grid, pos.x, pos.y-1) + num(grid, pos.x, pos.y+1) + num(grid, pos.x-1, pos.y) + num(grid, pos.x+1, pos.y);
	}
	
	private int num(char[][] grid, int x, int y) {
		if(x<0 || y<0 || x>=grid.length || y>=grid.length)
			return 0;
		return grid[y][x] == '#' ? 1 : 0;
	}

	@Override
	public Object part2() throws IOException {
		return 0;
	}
}
