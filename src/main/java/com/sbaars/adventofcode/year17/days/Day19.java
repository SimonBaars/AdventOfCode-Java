package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;

public class Day19 extends Day2017 {
    private static final int DOWN = 0;
    private static final int UP = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;

    public Day19() {
        super(19);
    }

    public static void main(String[] args) {
        new Day19().printParts();
    }

    private static class PathResult {
        final String letters;
        final int steps;

        PathResult(String letters, int steps) {
            this.letters = letters;
            this.steps = steps;
        }
    }

    private PathResult followPath(String[] grid) {
        int x = grid[0].indexOf('|');  // Starting position
        int y = 0;
        int direction = DOWN;
        StringBuilder letters = new StringBuilder();
        int steps = 0;

        while (true) {
            // Move in current direction
            switch (direction) {
                case DOWN -> y++;
                case UP -> y--;
                case RIGHT -> x++;
                case LEFT -> x--;
            }
            steps++;

            // Check if we're out of bounds or hit a space
            if (y < 0 || y >= grid.length || x < 0 || x >= grid[y].length() || grid[y].charAt(x) == ' ') {
                break;
            }

            char current = grid[y].charAt(x);
            if (Character.isLetter(current)) {
                letters.append(current);
            } else if (current == '+') {
                // Change direction
                if (direction == DOWN || direction == UP) {
                    // Check left and right
                    if (x > 0 && grid[y].charAt(x - 1) != ' ') {
                        direction = LEFT;
                    } else {
                        direction = RIGHT;
                    }
                } else {
                    // Check up and down
                    if (y > 0 && grid[y - 1].charAt(x) != ' ') {
                        direction = UP;
                    } else {
                        direction = DOWN;
                    }
                }
            }
        }

        return new PathResult(letters.toString(), steps);
    }

    @Override
    public Object part1() {
        return followPath(dayStrings()).letters;
    }

    @Override
    public Object part2() {
        return followPath(dayStrings()).steps;
    }
}
