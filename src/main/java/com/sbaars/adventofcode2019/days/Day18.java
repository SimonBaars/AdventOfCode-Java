package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.pathfinding.CharGrid2d;

public class Day18 implements Day {
	
	private final char[][] grid;
	private final CharGrid2d charGrid;
	private static final char[][] CHANGE_GRID = {
			{'@', '#', '@'},
			{'#', '#', '#'},
			{'@', '#', '@'}
	};
	private final Point middle;
	
	public Day18() throws IOException {
		grid = Arrays.stream(readDay(18).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
		charGrid = new CharGrid2d(grid, false);
		middle = findPos('@').get(0);
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
	
	class State{
		List<Point> me;
		TreeSet<Character> keys;
		public State(List<Point> me, TreeSet<Character> keys) {
			super();
			this.me = me;
			this.keys = keys;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((keys == null) ? 0 : keys.hashCode());
			result = prime * result + ((me == null) ? 0 : me.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			if (keys == null) {
				if (other.keys != null)
					return false;
			} else if (!keys.equals(other.keys))
				return false;
			if (me == null) {
				if (other.me != null)
					return false;
			} else if (!me.equals(other.me))
				return false;
			return true;
		}
		
	}

	@Override
	public Object part1() throws IOException {
		List<Point> me = new ArrayList<>();
		me.add(middle);
		//List<Character> collectedKeys = new ArrayList<>();
		//int keysToCollect = findPos('a', 'z').size();
		List<Point> keys = findPos('a', 'z');
		/*Map<Route, List<Point>> routes = new HashMap<>();
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
		return findSteps(me, new TreeSet<>(), keys);
	}
	
	public List<List<Point>> getRoute(List<Point> me, Point p){
		List<List<Point>> points = new ArrayList<>();
		me.forEach(m -> points.add(charGrid.findPath(m, p)));
		return points;
		//List<Point> p = routes.get(new Route(p1, p2));
		//if(p != null)
		//	return p;
		//else return routes.get(new Route(p2, p1));
	}
	
	public boolean canTakeRoute(List<Point> route, TreeSet<Character> keys) {
		for(Point p : route) {
			if(grid[p.y][p.x]>='A' && grid[p.y][p.x]<='Z' && !keys.contains(grid[p.y][p.x])) {
				return false;
			}
		}
		return true;
	}
	
	//int lowest = Integer.MAX_VALUE;
	Map<State, Integer> cachedResult = new HashMap<>();
	public int findSteps(List<Point> me, TreeSet<Character> collectedKeys, List<Point> keys) {
		Integer cachedRes = cachedResult.get(new State(me, collectedKeys));
		if(cachedRes!=null)
			return cachedRes;
		/*if(keys.isEmpty() && currentSteps<lowest) {
			lowest = currentSteps;
			System.out.println(lowest);
		} else if(currentSteps>=lowest) {
			return currentSteps;
		}*/
		List<List<Point>> possibleMoves = me.stream().flatMap(m -> keys.stream().map(p -> charGrid.findPath(m, p))).filter(e -> !e.isEmpty()).collect(Collectors.toList());//keys.stream().map(e -> getRoute(routes, me, e)).filter(e -> canTakeRoute(e, collectedKeys)).collect(Collectors.toList());
		System.out.println(Arrays.toString(collectedKeys.toArray())+possibleMoves.size());
		//System.out.println("moves "+possibleMoves.size());
		//possibleMoves.addAll(doors.stream().filter(e -> collectedKeys.contains(grid[e.y][e.x])).map(e -> charGrid.findPath(meNow, e)).filter(e -> !e.isEmpty()).collect(Collectors.toList()));
		//List<Point> takenMove = possibleMoves.stream().reduce((a, b) -> a.size()<b.size() ? a : b).get();
		//System.out.println(possibleMoves.size()+", "+collectedKeys.size());
		List<Integer> nSteps = new ArrayList<>();
		for(List<Point> takenMove : possibleMoves) {
		//List<Point> takenMove = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
			//grid[me.y][me.x] = '.';
			//me = takenMove.get(takenMove.size()-1);
			TreeSet<Character> myKeys = new TreeSet<>(collectedKeys);
			List<Point> keyLocs = new ArrayList<>(keys);
			Point newLoc = takenMove.get(takenMove.size()-1);
			char collected = grid[newLoc.y][newLoc.x];
			//System.out.println((takenMove.size()-1)+" steps to "+collected);
			myKeys.add(Character.toUpperCase(collected));
				//doors.stream().filter(e -> grid[e.y][e.x] == Character.toUpperCase(collected)).forEach(e -> grid[e.y][e.x] = '.');
				//doors.removeIf(e -> grid[e.y][e.x] == Character.toUpperCase(collected));
			keyLocs.remove(newLoc);
			List<Point> me2 = new ArrayList<>(me);
			me2.set(me.indexOf(takenMove.get(0)), newLoc);
			//System.out.println(collected);
			//grid[newLoc.y][newLoc.x] = '@';
			//System.out.println("Taken move "+collected+" for "+(takenMove.size()-1));
			nSteps.add(findSteps(me2, myKeys, keyLocs) + takenMove.size()-1);
		}
		int res = nSteps.stream().mapToInt(e -> e).min().orElse(0);
		cachedResult.put(new State(me, collectedKeys), res);
		return res;
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
