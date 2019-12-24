package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import com.sbaars.adventofcode2019.common.Day;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class Day24 implements Day {
	
	private final Set<Grid> grids = new HashSet<>();
	private Map<Integer, char[][]> layers = new HashMap<>();
	private final char[][] initialGrid;
	
	@EqualsAndHashCode @AllArgsConstructor @Data class Grid{
		char[][] grid;
	}
	
	public Day24() throws IOException {
		this.initialGrid = Arrays.stream(readDay(24).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
	}

	public static void main(String[] args) throws IOException {
		new Day24().printParts();
	}

	@Override
	public Object part1() throws IOException {
		char[][] grid = initialGrid;
		while(true) {
			if(!grids.add(new Grid(grid))) return calcRes(grid);
			char[][] newGrid = copy(grid);
			for(int y = 0; y<grid.length; y++)
				for(int x = 0; x<grid[0].length; x++)
					simulate(grid, newGrid, new Point(x, y));
			grid = newGrid;
		}
	}

	private void simulate(char[][] grid, char[][] newGrid, Point p) {
		int adj = nAdjecent(grid, p);
		if(grid[p.y][p.x] == '#' && adj != 1) {
			newGrid[p.y][p.x] = '.';
		} else if(grid[p.y][p.x] == '.' && (adj == 1 || adj == 2)) {
			newGrid[p.y][p.x] = '#';
		}
	}
	
	private long calcRes(char[][] grid) {
		return (long)IntStream.range(0, grid.length).boxed().flatMap(y -> IntStream.range(0, grid[y].length).mapToObj(x -> new Point(x,y))).filter(p -> grid[p.y][p.x] == '#').mapToDouble(p -> Math.pow(2, (p.y*grid.length)+p.x)).sum();
	}

	public char[][] copy(char[][] grid){
		char[][] g2 = new char[grid.length][];
		for(int i = 0; i<grid.length; i++) g2[i] = Arrays.copyOf(grid[i], grid[i].length);
		return g2;
	}
	
	public int nAdjecent(char[][] grid, Point pos) {
		return num(grid, new Point(pos.x, pos.y-1)) + num(grid, new Point(pos.x, pos.y+1)) + num(grid, new Point(pos.x-1, pos.y)) + num(grid, new Point(pos.x+1, pos.y));
	}
	
	private int num(char[][] grid, Point p) {
		if(p.x<0 || p.y<0 || p.x>=grid.length || p.y>=grid.length) return 0;
		return grid[p.y][p.x] == '#' ? 1 : 0;
	}

	@Override
	public Object part2() throws IOException {
		char[][] grid = Arrays.stream(readDay(24).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
		for(int i = -200; i<=200; i++) layers.put(i, fill(new char[grid.length][grid[0].length], '.'));
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
		Point[] surround = new Point[] {new Point(2, 1), new Point(1, 2), new Point(2, 3), new Point(3, 2)};
		if(layers.containsKey(layer+1)) {
			List<Supplier<Boolean>> conditions = Arrays.asList(() -> pos.y == 0, () -> pos.x == 0, () -> pos.y == 4, () -> pos.x == 4);
			res += IntStream.range(0, conditions.size()).filter(i -> conditions.get(i).get()).map(i -> num(layers.get(layer + 1), surround[i])).sum();
		}
		if(layers.containsKey(layer-1)) {
			List<Function<Integer, Point>> fs = Arrays.asList(i -> new Point(i, 0), i -> new Point(0, i), i -> new Point(i, 4), i -> new Point(4, i));
			res += IntStream.range(0, fs.size()).filter(i -> pos.equals(surround[i])).map(i -> nLayer(layer, fs.get(i))).sum();
		}
		return res + nAdjecent(layers.get(layer), pos);
	}
	
	public int nLayer(int layer, Function<Integer, Point> f) {
		return IntStream.range(0,5).map(i -> num(layers.get(layer-1), f.apply(i))).sum();
	}
	
	public char[][] fill(char[][] arr, char el) {
		for (char[] row: arr) Arrays.fill(row, el);
		return arr;
	}
}
