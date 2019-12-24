package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

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
			for(int y = 0; y<grid.length; y++)
				for(int x = 0; x<grid[0].length; x++)
					simulate(grid, newGrid, y, x);
			grid = newGrid;
		}
	}

	private void simulate(char[][] grid, char[][] newGrid, int y, int x) {
		int adj = nAdjecent(grid, new Point(x,y));
		if(grid[y][x] == '#' && adj != 1) {
			newGrid[y][x] = '.';
		} else if(grid[y][x] == '.' && (adj == 1 || adj == 2)) {
			newGrid[y][x] = '#';
		}
	}
	
	@EqualsAndHashCode @AllArgsConstructor @Data class Hello{
		char[][] grid;
	}
	
	private long calcRes(char[][] grid) {
		//	Arrays.stream(grid).forEach(e -> System.out.println(Arrays.toString(e)));
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
		if(x<0 || y<0 || x>=grid.length || y>=grid.length) return 0;
		return grid[y][x] == '#' ? 1 : 0;
	}
	
	Map<Integer, char[][]> layers = new HashMap<>();

	@Override
	public Object part2() throws IOException {
		char[][] grid = Arrays.stream(readDay(24).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
		for(int i = -200; i<=200; i++)
			layers.put(i, fill(new char[grid.length][grid[0].length], '.'));
		layers.put(0, grid);
		for(int i =0; i<200; i++) {
			final Map<Integer, char[][]> layers2 = new HashMap<>();
			for(int layer = -200; layer<=200; layer++) {
				char[][] newGrid = copy(layers.get(layer));
				for(int y = 0; y<grid.length; y++)
					for(int x = 0; x<grid[0].length; x++)
						if(x != 2 || y!=2)
						simulate(layer, newGrid, y, x);
				layers2.put(layer, newGrid);
			}
			layers = layers2;
		}
		for(int layer = -200; layer<=200; layer++) {
			System.out.println("Layer "+layer);
			Arrays.stream(layers.get(layer)).forEach(e -> System.out.println(Arrays.toString(e)));
		}
		return layers.values().stream().mapToInt(e -> count(e, '#')).sum();
	}
	
	private int count(char[][] grid, char c) {
		int total = 0;
		for(int y = 0; y<grid.length; y++)
			for(int x = 0; x<grid[0].length; x++)
				if(grid[y][x] == '#')
					total++;
		return total;
	}

	private void simulate(int layer, char[][] newGrid, int y, int x) {
		int adj = nAdjecent(layer, new Point(x,y));
		if(layers.get(layer)[y][x] == '#' && adj != 1) {
			newGrid[y][x] = '.';
		} else if(layers.get(layer)[y][x] == '.' && (adj == 1 || adj == 2)) {
			newGrid[y][x] = '#';
		}
	}
	
	public int nAdjecent(int layer, Point pos) {
		int res = 0;
		if(layers.containsKey(layer+1)) {
			if(pos.y == 0) {
				res+=num(layers.get(layer + 1), 2 , 1);
			}
			if(pos.x == 0) {
				res+=num(layers.get(layer + 1), 1 , 2);
			}
			if(pos.y == 4) {
				res+=num(layers.get(layer + 1), 2 , 3);
			}
			if(pos.x == 4) {
				res+=num(layers.get(layer + 1), 3 , 2);
			}
		}
		if(layers.containsKey(layer-1)) {
			if(pos.equals(new Point(2, 1))) {
				res+=IntStream.range(0,5).map(i -> num(layers.get(layer-1), i, 0)).sum();
			}
			if(pos.equals(new Point(1, 2))) {
				res+=IntStream.range(0,5).map(i -> num(layers.get(layer-1), 0, i)).sum();
			}
			if(pos.equals(new Point(2, 3))) {
				res+=IntStream.range(0,5).map(i -> num(layers.get(layer-1), i, 4)).sum();
			}
			if(pos.equals(new Point(3, 2))) {
				res+=IntStream.range(0,5).map(i -> num(layers.get(layer-1), 4, i)).sum();
			}
		}
		return res + nAdjecent(layers.get(layer), pos);
	}
	
	public char[][] fill(char[][] arr, char el) {
		for (char[] row: arr) Arrays.fill(row, el);
		return arr;
	}
}
