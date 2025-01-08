package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;

public class Day13 extends Day2016 {
    private static final int TARGET_X = 31;
    private static final int TARGET_Y = 39;
    private static final int MAX_STEPS = 50;
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public Day13() {
        super(13);
    }

    public static void main(String[] args) {
        new Day13().printParts();
    }

    private record Point(int x, int y) {
        boolean isValid() {
            return x >= 0 && y >= 0;
        }
    }

    private boolean isWall(int x, int y, int favoriteNumber) {
        if (x < 0 || y < 0) return true;
        long value = x*x + 3*x + 2*x*y + y + y*y + favoriteNumber;
        return Long.bitCount(value) % 2 == 1;
    }

    private record State(Point pos, int steps) {}

    private Map<Point, Integer> exploreMap(int favoriteNumber, boolean stopAtTarget) {
        Queue<State> queue = new LinkedList<>();
        Map<Point, Integer> distances = new HashMap<>();
        Point start = new Point(1, 1);
        queue.add(new State(start, 0));
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            
            if (stopAtTarget && current.pos.x == TARGET_X && current.pos.y == TARGET_Y) {
                break;
            }

            if (!stopAtTarget && current.steps >= MAX_STEPS) {
                continue;
            }

            for (int[] dir : DIRECTIONS) {
                Point next = new Point(current.pos.x + dir[0], current.pos.y + dir[1]);
                if (next.isValid() && !isWall(next.x, next.y, favoriteNumber) && 
                    (!distances.containsKey(next) || distances.get(next) > current.steps + 1)) {
                    distances.put(next, current.steps + 1);
                    queue.add(new State(next, current.steps + 1));
                }
            }
        }

        return distances;
    }

    @Override
    public Object part1() {
        int favoriteNumber = Integer.parseInt(day().trim());
        Map<Point, Integer> distances = exploreMap(favoriteNumber, true);
        return distances.get(new Point(TARGET_X, TARGET_Y));
    }

    @Override
    public Object part2() {
        int favoriteNumber = Integer.parseInt(day().trim());
        Map<Point, Integer> distances = exploreMap(favoriteNumber, false);
        return distances.values().stream()
            .filter(steps -> steps <= MAX_STEPS)
            .count();
    }
}
