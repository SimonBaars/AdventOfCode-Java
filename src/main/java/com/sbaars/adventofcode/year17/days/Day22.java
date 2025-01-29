package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;

public class Day22 extends Day2017 {
    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int CLEAN = 0;
    private static final int WEAKENED = 1;
    private static final int INFECTED = 2;
    private static final int FLAGGED = 3;

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public Day22() {
        super(22);
    }

    public static void main(String[] args) {
        new Day22().printParts();
    }

    @Override
    public Object part1() {
        String[] grid = dayStrings();
        Set<Point> infected = new HashSet<>();

        // Parse initial grid
        int centerY = grid.length / 2;
        int centerX = grid[0].length() / 2;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length(); x++) {
                if (grid[y].charAt(x) == '#') {
                    infected.add(new Point(x - centerX, y - centerY));
                }
            }
        }

        // Start virus carrier at center facing up
        Point carrier = new Point(0, 0);
        int direction = UP;
        int infections = 0;

        // Simulate 10000 bursts
        for (int burst = 0; burst < 10000; burst++) {
            // Turn based on current node
            if (infected.contains(carrier)) {
                direction = (direction + 1) % 4;  // Turn right
            } else {
                direction = (direction + 3) % 4;  // Turn left
            }

            // Toggle infection state
            if (infected.contains(carrier)) {
                infected.remove(carrier);
            } else {
                infected.add(new Point(carrier.x, carrier.y));
                infections++;
            }

            // Move forward
            switch (direction) {
                case UP -> carrier.y--;
                case RIGHT -> carrier.x++;
                case DOWN -> carrier.y++;
                case LEFT -> carrier.x--;
            }
        }

        return infections;
    }

    @Override
    public Object part2() {
        String[] grid = dayStrings();
        Map<Point, Integer> nodes = new HashMap<>();

        // Parse initial grid
        int centerY = grid.length / 2;
        int centerX = grid[0].length() / 2;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length(); x++) {
                if (grid[y].charAt(x) == '#') {
                    nodes.put(new Point(x - centerX, y - centerY), INFECTED);
                }
            }
        }

        // Start virus carrier at center facing up
        Point carrier = new Point(0, 0);
        int direction = UP;
        int infections = 0;

        // Simulate 10000000 bursts
        for (int burst = 0; burst < 10000000; burst++) {
            int currentState = nodes.getOrDefault(carrier, CLEAN);

            // Turn based on current node state
            switch (currentState) {
                case CLEAN -> direction = (direction + 3) % 4;  // Turn left
                case INFECTED -> direction = (direction + 1) % 4;  // Turn right
                case FLAGGED -> direction = (direction + 2) % 4;  // Reverse
                // WEAKENED: Don't turn
            }

            // Update node state
            int newState = (currentState + 1) % 4;
            if (newState == INFECTED) {
                infections++;
            }
            if (newState == CLEAN) {
                nodes.remove(carrier);
            } else {
                nodes.put(new Point(carrier.x, carrier.y), newState);
            }

            // Move forward
            switch (direction) {
                case UP -> carrier.y--;
                case RIGHT -> carrier.x++;
                case DOWN -> carrier.y++;
                case LEFT -> carrier.x--;
            }
        }

        return infections;
    }
}
