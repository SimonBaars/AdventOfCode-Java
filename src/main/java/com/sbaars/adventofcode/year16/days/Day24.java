package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;

import java.util.*;

public class Day24 extends Day2016 {
    private record Point(int x, int y) {
        public List<Point> neighbors() {
            return List.of(
                new Point(x + 1, y),
                new Point(x - 1, y),
                new Point(x, y + 1),
                new Point(x, y - 1)
            );
        }
    }

    private record State(Point pos, Set<Integer> visited) {}

    public Day24() {
        super(24);
    }

    public static void main(String[] args) {
        new Day24().printParts();
    }

    private char[][] parseGrid() {
        List<String> lines = dayStream().toList();
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    private Map<Integer, Point> findPoints(char[][] grid) {
        Map<Integer, Point> points = new HashMap<>();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (Character.isDigit(grid[y][x])) {
                    points.put(grid[y][x] - '0', new Point(x, y));
                }
            }
        }
        return points;
    }

    private int shortestPath(char[][] grid, Point start, Point end) {
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Integer> distances = new HashMap<>();
        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(end)) {
                return distances.get(current);
            }

            for (Point next : current.neighbors()) {
                if (next.y() >= 0 && next.y() < grid.length &&
                    next.x() >= 0 && next.x() < grid[0].length &&
                    grid[next.y()][next.x()] != '#' &&
                    !distances.containsKey(next)) {
                    queue.add(next);
                    distances.put(next, distances.get(current) + 1);
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    private int[][] buildDistanceMatrix(char[][] grid, Map<Integer, Point> points) {
        int size = points.size();
        int[][] distances = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int dist = shortestPath(grid, points.get(i), points.get(j));
                distances[i][j] = distances[j][i] = dist;
            }
        }
        return distances;
    }

    private int findShortestRoute(int[][] distances, boolean returnToStart) {
        int n = distances.length;
        List<Integer> points = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            points.add(i);
        }

        int minDistance = Integer.MAX_VALUE;
        do {
            int distance = distances[0][points.get(0)];
            for (int i = 0; i < points.size() - 1; i++) {
                distance += distances[points.get(i)][points.get(i + 1)];
            }
            if (returnToStart) {
                distance += distances[points.get(points.size() - 1)][0];
            }
            minDistance = Math.min(minDistance, distance);
        } while (nextPermutation(points));

        return minDistance;
    }

    private boolean nextPermutation(List<Integer> arr) {
        int i = arr.size() - 2;
        while (i >= 0 && arr.get(i) >= arr.get(i + 1)) {
            i--;
        }
        if (i < 0) {
            return false;
        }

        int j = arr.size() - 1;
        while (arr.get(j) <= arr.get(i)) {
            j--;
        }

        Collections.swap(arr, i, j);
        Collections.reverse(arr.subList(i + 1, arr.size()));
        return true;
    }

    @Override
    public Object part1() {
        char[][] grid = parseGrid();
        Map<Integer, Point> points = findPoints(grid);
        int[][] distances = buildDistanceMatrix(grid, points);
        return findShortestRoute(distances, false);
    }

    @Override
    public Object part2() {
        char[][] grid = parseGrid();
        Map<Integer, Point> points = findPoints(grid);
        int[][] distances = buildDistanceMatrix(grid, points);
        return findShortestRoute(distances, true);
    }
}
