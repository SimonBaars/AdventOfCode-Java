package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;

public class Day24 extends Day2016 {
  private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

  public Day24() {
    super(24);
  }

  public static void main(String[] args) {
    new Day24().printParts();
  }

  private static class Point {
    final int x, y;

    Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Point)) return false;
      Point point = (Point) o;
      return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
  }

  private int findShortestPath(char[][] grid, Point start, Point end) {
    Queue<Point> queue = new LinkedList<>();
    Map<Point, Integer> distances = new HashMap<>();
    queue.add(start);
    distances.put(start, 0);

    while (!queue.isEmpty()) {
      Point current = queue.poll();
      if (current.equals(end)) {
        return distances.get(current);
      }

      for (int[] dir : DIRECTIONS) {
        int newX = current.x + dir[0];
        int newY = current.y + dir[1];
        Point next = new Point(newX, newY);

        if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length &&
            grid[newX][newY] != '#' && !distances.containsKey(next)) {
          distances.put(next, distances.get(current) + 1);
          queue.add(next);
        }
      }
    }

    return Integer.MAX_VALUE;
  }

  private int findShortestTotalPath(char[][] grid, Map<Integer, Point> locations) {
    int numLocations = locations.size();
    int[][] distances = new int[numLocations][numLocations];

    // Calculate distances between all pairs of locations
    for (int i = 0; i < numLocations; i++) {
      for (int j = i + 1; j < numLocations; j++) {
        int dist = findShortestPath(grid, locations.get(i), locations.get(j));
        distances[i][j] = distances[j][i] = dist;
      }
    }

    // Try all permutations starting with 0
    return findMinPermutation(distances, numLocations);
  }

  private int findMinPermutation(int[][] distances, int n) {
    List<Integer> remaining = new ArrayList<>();
    for (int i = 1; i < n; i++) {
      remaining.add(i);
    }
    return permute(distances, 0, remaining, 0);
  }

  private int permute(int[][] distances, int current, List<Integer> remaining, int totalDist) {
    if (remaining.isEmpty()) {
      return totalDist;
    }

    int minDist = Integer.MAX_VALUE;
    for (int i = 0; i < remaining.size(); i++) {
      int next = remaining.get(i);
      remaining.remove(i);
      int dist = permute(distances, next, remaining, totalDist + distances[current][next]);
      remaining.add(i, next);
      minDist = Math.min(minDist, dist);
    }
    return minDist;
  }

  @Override
  public Object part1() {
    List<String> lines = dayStream().toList();
    char[][] grid = new char[lines.size()][lines.get(0).length()];
    Map<Integer, Point> locations = new HashMap<>();

    // Parse grid and find numbered locations
    for (int i = 0; i < lines.size(); i++) {
      grid[i] = lines.get(i).toCharArray();
      for (int j = 0; j < grid[i].length; j++) {
        if (Character.isDigit(grid[i][j])) {
          locations.put(grid[i][j] - '0', new Point(i, j));
        }
      }
    }

    return findShortestTotalPath(grid, locations);
  }

  @Override
  public Object part2() {
    return "";
  }
}
