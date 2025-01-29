package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day18 extends Day2015 {

    private static final int GRID_SIZE = 100;
    private static final int STEPS = 100;
    private boolean[][] grid;

    public Day18() {
        super(18);
        parseInput();
    }

    public static void main(String[] args) {
        Day18 day = new Day18();
        day.printParts();
        new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 18, 1);
        new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 18, 2);
    }

    private void parseInput() {
        grid = new boolean[GRID_SIZE][GRID_SIZE];
        String[] lines = day().split("\n");
        for (int i = 0; i < GRID_SIZE; i++) {
            String line = lines[i].trim();
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = line.charAt(j) == '#';
            }
        }
    }

    @Override
    public Object part1() {
        boolean[][] currentGrid = copyGrid(grid);
        for (int step = 0; step < STEPS; step++) {
            currentGrid = nextStep(currentGrid);
        }
        return countLights(currentGrid);
    }

    @Override
    public Object part2() {
        boolean[][] currentGrid = copyGrid(grid);
        turnOnCorners(currentGrid);
        for (int step = 0; step < STEPS; step++) {
            currentGrid = nextStepWithStuckCorners(currentGrid);
        }
        return countLights(currentGrid);
    }

    private boolean[][] nextStep(boolean[][] currentGrid) {
        boolean[][] newGrid = new boolean[GRID_SIZE][GRID_SIZE];
        
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int neighbors = countNeighbors(currentGrid, i, j);
                if (currentGrid[i][j]) {
                    newGrid[i][j] = neighbors == 2 || neighbors == 3;
                } else {
                    newGrid[i][j] = neighbors == 3;
                }
            }
        }
        
        return newGrid;
    }

    private int countNeighbors(boolean[][] grid, int row, int col) {
        return (int) IntStream.rangeClosed(row - 1, row + 1)
            .filter(r -> r >= 0 && r < GRID_SIZE)
            .flatMap(r -> IntStream.rangeClosed(col - 1, col + 1)
                .filter(c -> c >= 0 && c < GRID_SIZE)
                .filter(c -> !(r == row && c == col))
                .filter(c -> grid[r][c]))
            .count();
    }

    private boolean[][] copyGrid(boolean[][] original) {
        boolean[][] copy = new boolean[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            copy[i] = Arrays.copyOf(original[i], GRID_SIZE);
        }
        return copy;
    }

    private long countLights(boolean[][] grid) {
        return Arrays.stream(grid)
            .flatMap(row -> IntStream.range(0, row.length).mapToObj(i -> row[i]))
            .filter(light -> light)
            .count();
    }

    private void turnOnCorners(boolean[][] grid) {
        grid[0][0] = true;
        grid[0][GRID_SIZE - 1] = true;
        grid[GRID_SIZE - 1][0] = true;
        grid[GRID_SIZE - 1][GRID_SIZE - 1] = true;
    }

    private boolean[][] nextStepWithStuckCorners(boolean[][] currentGrid) {
        boolean[][] newGrid = nextStep(currentGrid);
        turnOnCorners(newGrid);
        return newGrid;
    }
}
