package com.sbaars.adventofcode.year19.days;

import com.google.common.collect.ArrayListMultimap;
import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.pathfinding.CharGrid2d;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Day20 extends Day2019 {
  private final Map<String, Portal[]> portals = new HashMap<>();
  private final Map<Portal, String> portalLabel = new HashMap<>();
  private final ArrayListMultimap<Portal, Route> routes = ArrayListMultimap.create();
  private final List<Portal> portalsToTake = new ArrayList<>();
  char[][] grid;
  CharGrid2d charGrid;
  private Portal entry;
  private Portal exit;

  public Day20() {
    super(20);
    grid = dayGrid();
    charGrid = new CharGrid2d(grid, false);

    int[] rows = {2, 26, 80, 104};
    for (int row : rows) {
      boolean addPortal = row == rows[0] || row == rows[rows.length - 1];
      for (int i = 2; i < grid.length - 2; i++) {
        if (grid[i][row] == '.') {
          if (Character.isAlphabetic(grid[i][row - 1])) {
            addPortal("" + grid[i][row - 2] + grid[i][row - 1], new Point(row, i), addPortal);
            grid[i][row - 1] = '#';
          } else if (Character.isAlphabetic(grid[i][row + 1])) {
            addPortal("" + grid[i][row + 1] + grid[i][row + 2], new Point(row, i), addPortal);
            grid[i][row + 1] = '#';
          }
        }
        if (grid[row][i] == '.') {
          if (Character.isAlphabetic(grid[row - 1][i])) {
            addPortal("" + grid[row - 2][i] + grid[row - 1][i], new Point(i, row), addPortal);
            grid[row - 1][i] = '#';
          } else if (Character.isAlphabetic(grid[row + 1][i])) {
            addPortal("" + grid[row + 1][i] + grid[row + 2][i], new Point(i, row), addPortal);
            grid[row + 1][i] = '#';
          }
        }
      }
    }
    portalsToTake.addAll(portalLabel.keySet());
    portalsToTake.add(exit);
  }

  public static void main(String[] args) {
    new Day20().printParts();
  }

  public void addPortal(String label, Point pos, boolean outerRing) {
    Portal p = new Portal(pos, outerRing);
    if (label.equals("AA")) this.entry = p;
    else if (label.equals("ZZ")) this.exit = p;
    else {
      Portal[] portal = portals.get(label);
      if (portal == null) {
        portals.put(label, new Portal[]{p, null});
      } else portal[1] = p;
      portalLabel.put(p, label);
    }

  }

  @Override
  public Object part1() {
    return findRoutes(false);
  }

  private int findRoutes(boolean b) {
    final Queue<State> queue = new ArrayDeque<>();
    queue.add(new State(entry, 0, -1));
    int min = Integer.MAX_VALUE;
    while (!queue.isEmpty()) {
      State s = queue.poll();
      if (!routes.containsKey(s.pos)) determineRoutes(s.pos);
      for (Route route : routes.get(s.pos)) {
        int level = s.level;
        int distance = route.distance + s.totalSteps;
        if (level == 0 && route.goal.equals(exit) && distance < min){
          min = distance;
        } else if(distance > min) {
          break;
        }
        else if (route.goal.equals(exit)) continue;
        if (b) level += route.goal.isOuter ? 1 : -1;
        if (s.level < 0) continue;
        queue.add(new State(route.goal, level, s.totalSteps + route.distance));
      }
    }
    return min;
  }

  private void determineRoutes(Portal p) {
    for (Portal portal : portalsToTake) {
      if (!portal.pos.equals(p.pos)) {
        List<Point> route = charGrid.findPath(p.pos, portal.pos);
        if (!route.isEmpty()) routes.put(p, new Route(teleport(portal), route.size()));
      }
    }
  }

  private Portal teleport(Portal portal) {
    Portal[] thisPortal = portals.get(portalLabel.get(portal));
    if (portal.equals(exit)) return exit;
    if (portal.equals(thisPortal[0])) return thisPortal[1];
    return thisPortal[0];
  }

  @Override
  public Object part2() {
    return findRoutes(true);
  }

  record Portal(Point pos, boolean isOuter) {}

  record Route(Portal goal, int distance) {}

  record State(Portal pos, int level, int totalSteps) {}
}
