package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.pathfinding.CharGrid2d;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;

public class Day20 implements Day {
	char[][] grid;
	CharGrid2d charGrid;
	private final List<Portal> outerRing = new ArrayList<>();
	private final List<Portal> innerRing = new ArrayList<>();
	private Portal entry;
	private Portal exit;
	
	public Day20() throws IOException {
		grid = Arrays.stream(readDay(20).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
		charGrid = new CharGrid2d(grid, false);
		
		int[] rows = {2, 26, 80, 104};
		for(int row : rows) {
			boolean addPortal = row == rows[0] || row == rows[rows.length];
			for(int i = 2; i<grid.length-2; i++) {
				//System.out.println(row+", "+i+", "+grid[i][row]+", "+grid[i][row-1]);
				if(grid[i][row] == '.') {
					if(Character.isAlphabetic(grid[i][row-1])) {
						addPortal(new Portal(""+grid[i][row-2]+grid[i][row-1], new Point(row, i)), addPortal);
					} else if (Character.isAlphabetic(grid[i][row+1])) {
						addPortal(new Portal(""+grid[i][row+2]+grid[i][row+1], new Point(row, i)), addPortal);
					}
				}
				if(grid[row][i] == '.') {
					if(Character.isAlphabetic(grid[row-1][i])) {
						addPortal(new Portal(""+grid[row-2][i]+grid[row-1][i], new Point(i, row)), addPortal);
					} else if (Character.isAlphabetic(grid[row+1][i])) {
						addPortal(new Portal(""+grid[row+1][i]+grid[row+2][i], new Point(i, row)), addPortal);
					}
				}
			}
		}
	}
	
	public void addPortal(Portal p, boolean outerRing) {
		if(p.value.equals("AA")) this.entry = p;
		else if(p.value.equals("AA")) this.exit = p;
		else if(outerRing) this.outerRing.add(p);
		else this.innerRing.add(p);
	}

	public static void main(String[] args) throws IOException {
		new Day20().printParts();
	}
	
	@Data @AllArgsConstructor class Portal {
		String value;
		Point location;
	}

	@Override
	public Object part1() throws IOException {
		//Arrays.stream(grid).forEach(e -> System.out.println(new String(e)));
		return findRoutes(getPortal(portals, "AA").stream().map(e -> e.location).findAny().get(), portals);
		//System.out.println(Arrays.toString(portals.toArray()));
		//return 0;
	}
	
	@Override
	public Object part2() throws IOException {
		return 0;
	}
	
	@Data @AllArgsConstructor class Route {
		Point start;
		Point end;
		List<Point> route;
		
		public int steps() {
			return route.size();
		}
	}
	
	@Data @AllArgsConstructor class State{
		Point me;
		int depth;
	}
	
	private Object findRoutes(Point me, List<Portal> portals) {
		Map<Route, List<Point>> routes = new HashMap<>();
		List<Point> requiredRoutes = new ArrayList<>(portals.stream().map(e -> e.location).collect(Collectors.toList()));
		requiredRoutes.add(me);
		for(int i = 0; i<requiredRoutes.size(); i++) {
			for(int j = i+1; j<requiredRoutes.size(); j++) {
				List<Point> r = charGrid.findPath(requiredRoutes.get(i), requiredRoutes.get(j));
				if(!r.isEmpty())
					routes.put(new Route(requiredRoutes.get(i), requiredRoutes.get(j)), r);
			}
		}
		removePortal(portals, "AA");
		return findSteps(me, new TreeSet<>(), portals, routes);
	}
	
	public List<Point> getRoute(Map<Route, List<Point>> routes, Point p1, Point p2){
		List<Point> p = routes.get(new Route(p1, p2));
		if(p != null) return p;
		else return routes.get(new Route(p2, p1));
	}
	
	public boolean canTakeRoute(List<Point> route, TreeSet<Character> keys) {
		for(Point p : route) {
			if(grid[p.y][p.x]>='A' && grid[p.y][p.x]<='Z' && !keys.contains(grid[p.y][p.x])) {
				return false;
			}
		}
		return true;
	}
	
	Map<State, Integer> cachedResult = new HashMap<>();
	public int findSteps(Point me, TreeSet<String> collectedKeys, List<Portal> portals, Map<Route, List<Point>> routes) {
		Integer cachedRes = cachedResult.get(new State(me, collectedKeys));
		if(cachedRes!=null) return cachedRes;
		
		var possibleMoves = portals.stream().filter(p -> !collectedKeys.contains(p.value)).map(p -> getRoute(routes, me, p.location)).filter(Objects::nonNull).collect(Collectors.toList());
		removeDuplicates(me, portals, possibleMoves);
		if(collectedKeys.size()>3) possibleMoves.clear();
		System.out.println(Arrays.toString(collectedKeys.toArray())+possibleMoves.size());
		
		List<Integer> nSteps = new ArrayList<>();
		for(List<Point> takenMove : possibleMoves) {
			Point moveTo = me.equals(takenMove.get(0)) ? takenMove.get(takenMove.size()-1) : takenMove.get(0);
			Portal thisPortal = getPortal(portals, moveTo);
			if(thisPortal.value.equals("ZZ")) {
				nSteps.add(takenMove.size()-1);
				continue;
			}
			
			List<Portal> teleport = getPortal(portals, thisPortal.value);
			Point newLoc = null;
			for(Portal portal : teleport) {
				if(!portal.location.equals(moveTo)) {
					newLoc = portal.getLocation();
				}
			}
			val myKeys = new TreeSet<>(collectedKeys);
			val keyLocs = new ArrayList<>(portals);
			//Point newLoc = me.contains(takenMove.get(0)) ? moveTo : takenMove.get(0);
			//Point oldLoc = me.equals(takenMove.get(0)) ? takenMove.get(0) : moveTo;
			myKeys.add(thisPortal.value);
			keyLocs.removeAll(teleport);
			nSteps.add(findSteps(newLoc, myKeys, keyLocs, routes) + takenMove.size()-1);
		}
		int res = nSteps.stream().mapToInt(e -> e).min().orElse(Integer.MAX_VALUE-10000);
		cachedResult.put(new State(me, collectedKeys), res);
		return res;
	}
	
	private void removeDuplicates(Point me, List<Portal> portals, List<List<Point>> possibleMoves) {
		Map<Point, Integer> pointIndices = new HashMap<>();
		for(int i = 0; i<possibleMoves.size(); i++) {
			Point moveTo = me.equals(possibleMoves.get(i).get(0)) ? possibleMoves.get(i).get(possibleMoves.get(i).size()-1) : possibleMoves.get(i).get(0);
			Portal p = getPortal(portals, moveTo);
			List<Portal> ps = getPortal(portals, p.value);
			for(Portal por : ps) {
				Point portal = por.location;
				if(!portal.equals(moveTo) && pointIndices.containsKey(portal)) {
					int moves = possibleMoves.get(i).size();
					int otherMoves = possibleMoves.get(pointIndices.get(portal)).size();
					if(moves > otherMoves)
						possibleMoves.remove(i);
					else possibleMoves.remove(pointIndices.get(portal).intValue());
				}
			}
			pointIndices.put(moveTo, 0);
		}
		
	}

	public List<Portal> getPortal(List<Portal> portals, String key){
		return portals.stream().filter(e -> e.value.equals(key)).collect(Collectors.toList());
	}
	
	public boolean removePortal(List<Portal> portals, String key){
		return portals.removeIf(e -> e.value.equals(key));
	}
	
	public Portal getPortal(List<Portal> portals, Point key){
		return portals.stream().filter(e -> e.location.equals(key)).findAny().get();
	}
}
