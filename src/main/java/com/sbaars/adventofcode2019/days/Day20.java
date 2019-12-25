package com.sbaars.adventofcode2019.days;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.sbaars.adventofcode2019.common.Day;
import com.sbaars.adventofcode2019.pathfinding.CharGrid2d;
import com.sbaars.adventofcode2019.util.ListMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class Day20 implements Day {
	char[][] grid;
	CharGrid2d charGrid;
	private final Map<String, Portal[]> portals = new HashMap<>();
	private final Map<Portal, String> portalLabel = new HashMap<>();
	private final ListMap<Portal, Route> routes = new ListMap<>();
	private final List<Portal> portalsToTake = new ArrayList<>();
	private Portal entry;
	private Portal exit;

	@Data @AllArgsConstructor class Portal {
		Point pos;
		boolean isOuter;
	}

	@Data @AllArgsConstructor class Route {
		Portal goal;
		int distance;
	}

	@EqualsAndHashCode(callSuper = true) @ToString(callSuper = true) @AllArgsConstructor class State extends Visited {
		int totalSteps;

		public State(int totalSteps, Visited vis) {
			super(vis.pos, vis.level);
			this.totalSteps = totalSteps;
		}
	}

	@Data @AllArgsConstructor @NoArgsConstructor class Visited {
		Portal pos;
		int level;
	}

	public Day20() throws IOException {
		grid = Arrays.stream(readDay(20).split(System.lineSeparator())).map(e -> e.toCharArray()).toArray(char[][]::new);
		charGrid = new CharGrid2d(grid, false);

		int[] rows = {2, 26, 80, 104};
		for(int row : rows) {
			boolean addPortal = row == rows[0] || row == rows[rows.length-1];
			for(int i = 2; i<grid.length-2; i++) {
				if(grid[i][row] == '.') {
					if(Character.isAlphabetic(grid[i][row-1])) {
						addPortal(""+grid[i][row-2]+grid[i][row-1], new Point(row, i), addPortal);
						grid[i][row-1]='#';
					} else if (Character.isAlphabetic(grid[i][row+1])) {
						addPortal(""+grid[i][row+1]+grid[i][row+2], new Point(row, i), addPortal);
						grid[i][row+1]='#';
					}
				}
				if(grid[row][i] == '.') {
					if(Character.isAlphabetic(grid[row-1][i])) {
						addPortal(""+grid[row-2][i]+grid[row-1][i], new Point(i, row), addPortal);
						grid[row-1][i] = '#';
					} else if (Character.isAlphabetic(grid[row+1][i])) {
						addPortal(""+grid[row+1][i]+grid[row+2][i], new Point(i, row), addPortal);
						grid[row+1][i] = '#';
					}
				}
			}
		}
		portalsToTake.addAll(portalLabel.keySet());
		portalsToTake.add(exit);
	}

	public void addPortal(String label, Point pos, boolean outerRing) {
		Portal p = new Portal(pos, outerRing);
		if(label.equals("AA")) this.entry = p;
		else if(label.equals("ZZ")) this.exit = p;
		else {
			Portal[] portal = portals.get(label);
			if(portal == null) {
				portals.put(label, new Portal[] {p, null});
			} else portal[1] = p;
			portalLabel.put(p, label);
		}

	}

	public static void main(String[] args) throws IOException {
		new Day20().printParts();
	}

	@Override
	public Object part1() throws IOException {
		return findRoutes(false);
	}

	private int findRoutes(boolean b) {
		final Queue<State> queue = new ArrayDeque<>();
		final Set<Visited> visited = new HashSet<>();
		queue.add(new State(-1, new Visited(entry, 0)));
		while(true) {
			State s = queue.poll();
			if(!routes.containsKey(s.pos)) determineRoutes(s.pos);
			for(Route route : routes.get(s.pos)) {
				int level = s.level;
				if(level == 0 && route.goal.equals(exit)) return route.distance + s.totalSteps;
				else if(route.goal.equals(exit)) continue;
				if(b) level+=route.goal.isOuter ? 1 : -1;
				if(s.level < 0) continue;
				Visited vis = new Visited(route.goal, level);
				if(!visited.contains(vis)) {
					visited.add(vis);
					queue.add(new State(s.totalSteps + route.distance, vis));
				}
			}
		}
	}

	private void determineRoutes(Portal p) {
		for(Portal portal : portalsToTake) {
			if(!portal.pos.equals(p.pos)) {
				List<Point> route = charGrid.findPath(p.pos, portal.pos);
				if(!route.isEmpty()) routes.addTo(p, new Route(teleport(portal), route.size()));
			}
		}
	}

	private Portal teleport(Portal portal) {
		Portal[] thisPortal = portals.get(portalLabel.get(portal));
		if(portal.equals(exit)) return exit;
		if(portal.equals(thisPortal[0])) return thisPortal[1];
		return thisPortal[0];
	}

	@Override
	public Object part2() throws IOException {
		return findRoutes(true);
	}
}
