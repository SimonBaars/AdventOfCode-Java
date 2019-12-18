package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.pathfinding.CharGrid2d;

public class Day18 implements Day {
	
	char[][] grid;
	CharGrid2d charGrid;
	
	public Day18() throws IOException {
		grid = Arrays.stream(readDay(18).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
		charGrid = new CharGrid2d(grid, false);
	}	

	public static void main(String[] args) throws IOException {
		new Day18().printParts();
	}
	
	class Route {
		Point start;
		Point end;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((end == null) ? 0 : end.hashCode());
			result = prime * result + ((start == null) ? 0 : start.hashCode());
			return result;
		}
		public Route(Point start, Point end) {
			super();
			this.start = start;
			this.end = end;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Route other = (Route) obj;
			if (end == null) {
				if (other.end != null)
					return false;
			} else if (!end.equals(other.end))
				return false;
			if (start == null) {
				if (other.start != null)
					return false;
			} else if (!start.equals(other.start))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Route [start=" + start + ", end=" + end + "]";
		}
		
		
	}
	
	class Robot{
		Point pos;
		int num;
	}

	@Override
	public Object part1() throws IOException {
		List<Point> robots = findPos('@');
		
		//List<Character> collectedKeys = new ArrayList<>();
		//int keysToCollect = findPos('a', 'z').size();
		/*List<Point> keys = findPos('a', 'z');
		Map<Route, List<Point>> routes = new HashMap<>();
		List<Point> requiredRoutes = new ArrayList<>(keys);
		requiredRoutes.add(me);
		for(int i = 0; i<requiredRoutes.size(); i++) {
			for(int j = i+1; j<requiredRoutes.size(); j++) {
				List<Point> r = charGrid.findPath(requiredRoutes.get(i), requiredRoutes.get(j));
				//System.out.println(r.size()+", "+new Route(requiredRoutes.get(i), requiredRoutes.get(j)));
				routes.put(new Route(requiredRoutes.get(i), requiredRoutes.get(j)), r);
			}
		}*/
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
		return fSteps(robots, new ArrayList<>());
		//return findSteps(0, me, new ArrayList<>(), keys, routes);
	}
	
	private Object fSteps(List<Point> robots, List<Character> collectedKeys) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<List<Point>> reachableKeys(List<Point> robots, List<Character> collectedKeys){
		
	}

	public List<Point> getRoute(Map<Route, List<Point>> routes, Point p1, Point p2){
		List<Point> p = routes.get(new Route(p1, p2));
		if(p != null)
			return p;
		else return routes.get(new Route(p2, p1));
	}
	
	public boolean canTakeRoute(List<Point> route, List<Character> keys) {
		for(Point p : route) {
			if(grid[p.y][p.x]>='A' && grid[p.y][p.x]<='Z' && !keys.contains(grid[p.y][p.x])) {
				return false;
			}
		}
		return true;
	}
	
	//int lowest = Integer.MAX_VALUE;
	public int findSteps(int currentSteps, Point me, List<Character> collectedKeys, List<Point> keys, Map<Route, List<Point>> routes) {
		/*if(keys.isEmpty() && currentSteps<lowest) {
			lowest = currentSteps;
			System.out.println(lowest);
		} else if(currentSteps>=lowest) {
			return currentSteps;
		}*/
		
		List<List<Point>> possibleMoves = keys.stream().map(e -> getRoute(routes, me, e)).filter(e -> canTakeRoute(e, collectedKeys)).collect(Collectors.toList());
		//System.out.println("moves "+possibleMoves.size());
		//possibleMoves.addAll(doors.stream().filter(e -> collectedKeys.contains(grid[e.y][e.x])).map(e -> charGrid.findPath(meNow, e)).filter(e -> !e.isEmpty()).collect(Collectors.toList()));
		//List<Point> takenMove = possibleMoves.stream().reduce((a, b) -> a.size()<b.size() ? a : b).get();
		//System.out.println(possibleMoves.size()+", "+collectedKeys.size());
		List<Integer> nSteps = new ArrayList<>();
		for(List<Point> takenMove : possibleMoves) {
		//List<Point> takenMove = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
			//grid[me.y][me.x] = '.';
			//me = takenMove.get(takenMove.size()-1);
			List<Character> myKeys = new ArrayList<>(collectedKeys);
			List<Point> keyLocs = new ArrayList<>(keys);
			Point newLoc = me.equals(takenMove.get(0)) ? takenMove.get(takenMove.size()-1) : takenMove.get(0);
			char collected = grid[newLoc.y][newLoc.x];
			//System.out.println((takenMove.size()-1)+" steps to "+collected);
			myKeys.add(Character.toUpperCase(collected));
				//doors.stream().filter(e -> grid[e.y][e.x] == Character.toUpperCase(collected)).forEach(e -> grid[e.y][e.x] = '.');
				//doors.removeIf(e -> grid[e.y][e.x] == Character.toUpperCase(collected));
			keyLocs.remove(newLoc);
			//System.out.println(collected);
			//grid[newLoc.y][newLoc.x] = '@';
			nSteps.add(findSteps(currentSteps + takenMove.size()-1, newLoc, myKeys, keyLocs, routes));
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
