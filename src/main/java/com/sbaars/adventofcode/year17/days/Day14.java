package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.common.Day;

public class Day14 extends Day {
    private static final int GRID_SIZE = 128;
    
    public Day14() {
        super(14, 2017);
    }

    public static void main(String[] args) {
        new Day14().printParts();
    }
    
    private boolean[][] createGrid(String input) {
        boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
        
        for (int row = 0; row < GRID_SIZE; row++) {
            String rowInput = input + "-" + row;
            String hash = knotHash(rowInput);
            
            // Convert hash to binary and fill grid
            int col = 0;
            for (char c : hash.toCharArray()) {
                int value = Character.digit(c, 16);
                for (int bit = 3; bit >= 0; bit--) {
                    grid[row][col] = ((value >> bit) & 1) == 1;
                    col++;
                }
            }
        }
        
        return grid;
    }
    
    private void dfs(boolean[][] grid, boolean[][] visited, int row, int col) {
        if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE || 
            !grid[row][col] || visited[row][col]) {
            return;
        }
        
        visited[row][col] = true;
        
        // Visit all adjacent squares
        dfs(grid, visited, row - 1, col); // up
        dfs(grid, visited, row + 1, col); // down
        dfs(grid, visited, row, col - 1); // left
        dfs(grid, visited, row, col + 1); // right
    }

    @Override
    public Object part1() {
        String input = "hxtvlmkl";
        int usedSquares = 0;
        
        for (int i = 0; i < GRID_SIZE; i++) {
            String rowInput = input + "-" + i;
            String hash = knotHash(rowInput);
            usedSquares += countBits(hash);
        }
        
        return usedSquares;
    }

    private String knotHash(String input) {
        int[] list = new int[256];
        for (int i = 0; i < 256; i++) {
            list[i] = i;
        }
        
        int[] lengths = new int[input.length() + 5];
        for (int i = 0; i < input.length(); i++) {
            lengths[i] = input.charAt(i);
        }
        lengths[input.length()] = 17;
        lengths[input.length() + 1] = 31;
        lengths[input.length() + 2] = 73;
        lengths[input.length() + 3] = 47;
        lengths[input.length() + 4] = 23;
        
        int pos = 0;
        int skip = 0;
        
        for (int round = 0; round < 64; round++) {
            for (int length : lengths) {
                // Reverse the sublist
                for (int i = 0; i < length / 2; i++) {
                    int a = (pos + i) % 256;
                    int b = (pos + length - 1 - i) % 256;
                    int temp = list[a];
                    list[a] = list[b];
                    list[b] = temp;
                }
                pos = (pos + length + skip) % 256;
                skip++;
            }
        }
        
        // Calculate dense hash
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            int xor = 0;
            for (int j = 0; j < 16; j++) {
                xor ^= list[i * 16 + j];
            }
            result.append(String.format("%02x", xor));
        }
        
        return result.toString();
    }

    private int countBits(String hash) {
        int count = 0;
        for (char c : hash.toCharArray()) {
            int value = Character.digit(c, 16);
            count += Integer.bitCount(value);
        }
        return count;
    }

    @Override
    public Object part2() {
        String input = "hxtvlmkl";
        boolean[][] grid = createGrid(input);
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        int regions = 0;
        
        // Count regions using DFS
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] && !visited[row][col]) {
                    dfs(grid, visited, row, col);
                    regions++;
                }
            }
        }
        
        return regions;
    }
}
