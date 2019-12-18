package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.pathfinding.CharGrid2d;

public class Day18 implements Day {
	
	char[][] grid;
	CharGrid2d charGrid;
	
	public Day18() throws IOException {
		grid = Arrays.stream(readDay(19).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
		charGrid = new CharGrid2d(grid, false);
	}	

	public static void main(String[] args) throws IOException {
		new Day18().printParts();
	}

	@Override
	public Object part1() throws IOException {
		Point me = findPos('@').get(0);
		//List<Character> collectedKeys = new ArrayList<>();
		//int keysToCollect = findPos('a', 'z').size();
		List<Point> keys = findPos('a', 'z');
		//List<Point> doors = findPos('A', 'Z');
		/*int steps = 0;
		//while(collectedKeys.size()<keysToCollect) {
			//System.out.println("Keys = "+Arrays.toString(collectedKeys.toArray()));
			//Arrays.stream(grid).map(e -> new String(e)).forEach(System.out::println);
			final Point meNow = me;
			List<List<Point>> possibleMoves = keys.stream().map(e -> charGrid.findPath(meNow, e)).filter(e -> !e.isEmpty()).collect(Collectors.toList());
			//possibleMoves.addAll(doors.stream().filter(e -> collectedKeys.contains(grid[e.y][e.x])).map(e -> charGrid.findPath(meNow, e)).filter(e -> !e.isEmpty()).collect(Collectors.toList()));
			//List<Point> takenMove = possibleMoves.stream().reduce((a, b) -> a.size()<b.size() ? a : b).get();
			List<Point> takenMove = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
			steps += takenMove.size()-1;
			grid[me.y][me.x] = '.';
			me = takenMove.get(takenMove.size()-1);
			char collected = grid[me.y][me.x];
			//System.out.println((takenMove.size()-1)+" steps to "+collected);
			if(collected >= 'a' && collected <= 'z') {
				collectedKeys.add(Character.toUpperCase(collected));
				doors.stream().filter(e -> grid[e.y][e.x] == Character.toUpperCase(collected)).forEach(e -> grid[e.y][e.x] = '.');
				doors.removeIf(e -> grid[e.y][e.x] == Character.toUpperCase(collected));
				keys.remove(me);
			} 
			grid[me.y][me.x] = '@';
		//}
		if(steps<5992)
		System.out.println(steps);
		}*/
		//System.out.println(Arrays.toString(reachableKeys.toArray()));
		return findSteps(me, new ArrayList<>(), keys);
	}
	
	public int findSteps(Point me, List<Character> collectedKeys, List<Point> keys) {
		List<List<Point>> possibleMoves = keys.stream().map(e -> charGrid.findPath(me, e, collectedKeys)).filter(e -> !e.isEmpty()).collect(Collectors.toList());
		
		//possibleMoves.addAll(doors.stream().filter(e -> collectedKeys.contains(grid[e.y][e.x])).map(e -> charGrid.findPath(meNow, e)).filter(e -> !e.isEmpty()).collect(Collectors.toList()));
		//List<Point> takenMove = possibleMoves.stream().reduce((a, b) -> a.size()<b.size() ? a : b).get();
		List<Integer> nSteps = new ArrayList<>();
		for(List<Point> takenMove : possibleMoves) {
		//List<Point> takenMove = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
			//grid[me.y][me.x] = '.';
			//me = takenMove.get(takenMove.size()-1);
			Point newLoc = takenMove.get(takenMove.size()-1);
			char collected = grid[me.y][me.x];
			//System.out.println((takenMove.size()-1)+" steps to "+collected);
			if(collected >= 'a' && collected <= 'z') {
				collectedKeys.add(Character.toUpperCase(collected));
				//doors.stream().filter(e -> grid[e.y][e.x] == Character.toUpperCase(collected)).forEach(e -> grid[e.y][e.x] = '.');
				//doors.removeIf(e -> grid[e.y][e.x] == Character.toUpperCase(collected));
				keys.remove(newLoc);
			} 
			//grid[newLoc.y][newLoc.x] = '@';
			nSteps.add(findSteps(newLoc, new ArrayList<>(collectedKeys), new ArrayList<>(keys))+takenMove.size()-1);
		}
		return nSteps.stream().mapToInt(e -> e).min().orElse(0);
	}
	
	private List<Point> findPos(char tile) {
		List<Point> positions = new ArrayList<>();
		for(int y = 0; y<grid.length; y++) {
			for(int x = 0; x<grid[y].length; x++) {
				if(grid[y][x] == tile)
					positions.add(new Point(x, y));
			}
		}
		return positions;
	}
	
	private List<Point> findPos(char tile, char tile2) {
		List<Point> positions = new ArrayList<>();
		for(int y = 0; y<grid.length; y++) {
			for(int x = 0; x<grid[y].length; x++) {
				if(grid[y][x] >= tile && grid[y][x] <= tile2)
					positions.add(new Point(x, y));
			}
		}
		return positions;
	}
	
	@Override
	public Object part2() throws IOException {
		return 0;
	}
}
