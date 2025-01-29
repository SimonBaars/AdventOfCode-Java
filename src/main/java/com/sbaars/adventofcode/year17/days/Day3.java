package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;

public class Day3 extends Day2017 {
    private static class Point {
        final int x, y;
        
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
    
    public Day3() {
        super(3);
    }

    public static void main(String[] args) {
        new Day3().printParts();
    }

    @Override
    public Object part1() {
        int input = Integer.parseInt(day().trim());
        
        // Find the layer (ring) containing the input number
        int layer = 0;
        int maxInLayer = 1;
        while (maxInLayer < input) {
            layer++;
            maxInLayer = (2 * layer + 1) * (2 * layer + 1);
        }
        
        if (input == 1) return 0;
        
        // Find the side length of the current square
        int sideLength = 2 * layer + 1;
        
        // Calculate position relative to the middle of each side
        int bottomRight = maxInLayer;
        int bottomLeft = bottomRight - (sideLength - 1);
        int topLeft = bottomLeft - (sideLength - 1);
        int topRight = topLeft - (sideLength - 1);
        
        // Find the closest middle point
        int[] midPoints = {
            bottomRight - layer,
            bottomLeft - layer,
            topLeft - layer,
            topRight - layer
        };
        
        int minDist = Integer.MAX_VALUE;
        for (int mid : midPoints) {
            minDist = Math.min(minDist, Math.abs(input - mid));
        }
        
        return layer + minDist;
    }

    @Override
    public Object part2() {
        int input = Integer.parseInt(day().trim());
        Map<Point, Integer> grid = new HashMap<>();
        Point current = new Point(0, 0);
        grid.put(current, 1);
        
        int[][] directions = {{1,0}, {0,1}, {-1,0}, {0,-1}};
        int direction = 0;
        int steps = 0;
        int stepsInDirection = 1;
        int stepsTaken = 0;
        
        while (true) {
            // Move to next position
            current = new Point(
                current.x + directions[direction][0],
                current.y + directions[direction][1]
            );
            
            // Calculate sum of adjacent values
            int sum = 0;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    Point p = new Point(current.x + dx, current.y + dy);
                    sum += grid.getOrDefault(p, 0);
                }
            }
            
            // Store value and check if it's larger than input
            grid.put(current, sum);
            if (sum > input) {
                return sum;
            }
            
            // Update direction and steps
            stepsTaken++;
            if (stepsTaken == stepsInDirection) {
                stepsTaken = 0;
                direction = (direction + 1) % 4;
                steps++;
                if (steps == 2) {
                    steps = 0;
                    stepsInDirection++;
                }
            }
        }
    }
}
