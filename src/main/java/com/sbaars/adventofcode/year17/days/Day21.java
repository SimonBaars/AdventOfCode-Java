package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;

public class Day21 extends Day2017 {
    private static final String INITIAL_PATTERN = ".#./..#/###";
    private final Map<String, String> rules = new HashMap<>();

    public Day21() {
        super(21);
    }

    public static void main(String[] args) {
        new Day21().printParts();
    }

    private List<String> getAllVariations(String pattern) {
        List<String> variations = new ArrayList<>();
        String[] rows = pattern.split("/");
        int size = rows.length;
        char[][] grid = new char[size][size];

        // Convert to 2D array
        for (int i = 0; i < size; i++) {
            grid[i] = rows[i].toCharArray();
        }

        // Add all rotations and flips
        for (int flip = 0; flip < 2; flip++) {
            for (int rot = 0; rot < 4; rot++) {
                variations.add(gridToString(grid));
                grid = rotate(grid);
            }
            grid = flip(grid);
        }

        return variations;
    }

    private char[][] rotate(char[][] grid) {
        int n = grid.length;
        char[][] result = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[j][n - 1 - i] = grid[i][j];
            }
        }
        return result;
    }

    private char[][] flip(char[][] grid) {
        int n = grid.length;
        char[][] result = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][n - 1 - j] = grid[i][j];
            }
        }
        return result;
    }

    private String gridToString(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            if (i > 0) sb.append('/');
            sb.append(new String(grid[i]));
        }
        return sb.toString();
    }

    private char[][] stringToGrid(String pattern) {
        String[] rows = pattern.split("/");
        int size = rows.length;
        char[][] grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            grid[i] = rows[i].toCharArray();
        }
        return grid;
    }

    private char[][] enhance(char[][] grid) {
        int size = grid.length;
        int newSize;
        int blockSize;
        int numBlocks;

        if (size % 2 == 0) {
            blockSize = 2;
            numBlocks = size / 2;
            newSize = numBlocks * 3;
        } else {
            blockSize = 3;
            numBlocks = size / 3;
            newSize = numBlocks * 4;
        }

        char[][] result = new char[newSize][newSize];

        for (int blockRow = 0; blockRow < numBlocks; blockRow++) {
            for (int blockCol = 0; blockCol < numBlocks; blockCol++) {
                // Extract block
                StringBuilder blockPattern = new StringBuilder();
                for (int i = 0; i < blockSize; i++) {
                    if (i > 0) blockPattern.append('/');
                    for (int j = 0; j < blockSize; j++) {
                        blockPattern.append(grid[blockRow * blockSize + i][blockCol * blockSize + j]);
                    }
                }

                // Find matching rule and apply enhancement
                String enhanced = rules.get(blockPattern.toString());
                if (enhanced == null) {
                    for (String variation : getAllVariations(blockPattern.toString())) {
                        enhanced = rules.get(variation);
                        if (enhanced != null) break;
                    }
                }

                // Place enhanced block in result
                String[] enhancedRows = enhanced.split("/");
                int enhancedSize = enhancedRows.length;
                for (int i = 0; i < enhancedSize; i++) {
                    for (int j = 0; j < enhancedSize; j++) {
                        result[blockRow * enhancedSize + i][blockCol * enhancedSize + j] = enhancedRows[i].charAt(j);
                    }
                }
            }
        }

        return result;
    }

    private int countPixels(int iterations) {
        // Parse rules
        for (String line : dayStrings()) {
            String[] parts = line.split(" => ");
            rules.put(parts[0], parts[1]);
        }

        // Start with initial pattern
        char[][] grid = stringToGrid(INITIAL_PATTERN);

        // Enhance specified number of times
        for (int i = 0; i < iterations; i++) {
            grid = enhance(grid);
        }

        // Count pixels that are on
        int count = 0;
        for (char[] row : grid) {
            for (char c : row) {
                if (c == '#') count++;
            }
        }

        return count;
    }

    @Override
    public Object part1() {
        return countPixels(5);
    }

    @Override
    public Object part2() {
        return countPixels(18);
    }
}
