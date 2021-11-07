package com.sbaars.adventofcode.year19.days;

import com.sbaars.adventofcode.year19.Day2019;
import com.sbaars.adventofcode.year19.pathfinding.CharGrid2d;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Day18 extends Day2019 {

  private final char[][] grid;
  private final CharGrid2d charGrid;
  private final Map<State, Integer> cachedResult = new HashMap<>();
  private static final char[][] CHANGE_GRID = {
      {'@', '#', '@'},
      {'#', '#', '#'},
      {'@', '#', '@'}
  };
  private final Point middle;

  public Day18() {
    super(18);
    grid = dayStream().map(String::toCharArray).toArray(char[][]::new);
    charGrid = new CharGrid2d(grid, false);
    middle = findPos('@').get(0);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  record Route(Point start, Point end) {
  }

  record State(List<Point> me, TreeSet<Character> keys) {
  }

  @Override
  public Object part1() {
    List<Point> me = new ArrayList<>();
    me.add(middle);
    return findRoutes(me);
  }

  private Object findRoutes(List<Point> me) {
    List<Point> keys = findPos('a', 'z');
    Map<Route, List<Point>> routes = new HashMap<>();
    List<Point> requiredRoutes = new ArrayList<>(keys);
    requiredRoutes.addAll(me);
    for (int i = 0; i < requiredRoutes.size(); i++) {
      for (int j = i + 1; j < requiredRoutes.size(); j++) {
        List<Point> r = charGrid.findPath(requiredRoutes.get(i), requiredRoutes.get(j));
        if (!r.isEmpty())
          routes.put(new Route(requiredRoutes.get(i), requiredRoutes.get(j)), r);
      }
    }
    return findSteps(me, new TreeSet<>(), keys, routes);
  }

  public List<Point> getRoute(Map<Route, List<Point>> routes, Point p1, Point p2) {
    List<Point> p = routes.get(new Route(p1, p2));
    if (p != null)
      return p;
    else return routes.get(new Route(p2, p1));
  }

  public boolean canTakeRoute(List<Point> route, TreeSet<Character> keys) {
    for (Point p : route) {
      if (grid[p.y][p.x] >= 'A' && grid[p.y][p.x] <= 'Z' && !keys.contains(grid[p.y][p.x])) {
        return false;
      }
    }
    return true;
  }

  public int findSteps(List<Point> me, TreeSet<Character> collectedKeys, List<Point> keys, Map<Route, List<Point>> routes) {
    Integer cachedRes = cachedResult.get(new State(me, collectedKeys));
    if (cachedRes != null) return cachedRes;
    var possibleMoves = me.stream().flatMap(m -> keys.stream().map(p -> getRoute(routes, m, p))).filter(Objects::nonNull).filter(e -> canTakeRoute(e, collectedKeys)).collect(Collectors.toList());
    List<Integer> nSteps = new ArrayList<>();
    for (List<Point> takenMove : possibleMoves) {
      var myKeys = new TreeSet<>(collectedKeys);
      var keyLocs = new ArrayList<>(keys);
      Point newLoc = me.contains(takenMove.get(0)) ? takenMove.get(takenMove.size() - 1) : takenMove.get(0);
      Point oldLoc = me.contains(takenMove.get(0)) ? takenMove.get(0) : takenMove.get(takenMove.size() - 1);
      char collected = grid[newLoc.y][newLoc.x];
      myKeys.add(Character.toUpperCase(collected));
      keyLocs.remove(newLoc);
      var me2 = new ArrayList<>(me);
      me2.set(me.indexOf(oldLoc), newLoc);
      nSteps.add(findSteps(me2, myKeys, keyLocs, routes) + takenMove.size() - 1);
    }
    int res = nSteps.stream().mapToInt(e -> e).min().orElse(0);
    cachedResult.put(new State(me, collectedKeys), res);
    return res;
  }

  private List<Point> findPos(char tile) {
    List<Point> positions = new ArrayList<>();
    for (int y = 0; y < grid.length; y++) {
      for (int x = 0; x < grid[y].length; x++) {
        if (grid[y][x] == tile)
          positions.add(new Point(x, y));
      }
    }
    return positions;
  }

  private List<Point> findPos(char tile, char tile2) {
    List<Point> positions = new ArrayList<>();
    for (int y = 0; y < grid.length; y++) {
      for (int x = 0; x < grid[y].length; x++) {
        if (grid[y][x] >= tile && grid[y][x] <= tile2)
          positions.add(new Point(x, y));
      }
    }
    return positions;
  }

  @Override
  public Object part2() {
    for (int y = 0; y < CHANGE_GRID.length; y++) {
      for (int x = 0; x < CHANGE_GRID[y].length; x++) {
        grid[middle.y - 1 + y][middle.x - 1 + x] = CHANGE_GRID[y][x];
      }
    }
    cachedResult.clear();
    return findRoutes(findPos('@'));
  }
}
