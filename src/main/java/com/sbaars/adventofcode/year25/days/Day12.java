package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day12 extends Day2025 {
    public Day12() {
        super(12);
    }

    public static void main(String[] args) throws IOException {
        new Day12().printParts();
    }

    record Shape(Set<int[]> cells, int width, int height) {
        @Override
        public boolean equals(Object o) {
            return this == o;
        }
        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }
    }
    
    record Region(int width, int height, int[] quantities) {}

    @Override
    public Object part1() {
        String input = day().trim();
        
        // Find the line that separates shapes from regions (first line with "x")
        String[] lines = input.split("\n");
        int splitIndex = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("x") && lines[i].contains(":")) {
                splitIndex = i;
                break;
            }
        }
        
        // Parse shapes
        List<Set<Set<int[]>>> allShapes = new ArrayList<>();
        List<Integer> shapeSizes = new ArrayList<>();
        StringBuilder shapePart = new StringBuilder();
        for (int i = 0; i < splitIndex; i++) {
            shapePart.append(lines[i]).append("\n");
        }
        
        String[] shapeSections = shapePart.toString().trim().split("\n\n");
        for (String shapeSection : shapeSections) {
            String[] shapeLines = shapeSection.split("\n");
            // First line is "N:"
            Set<int[]> baseShape = new HashSet<>();
            for (int y = 1; y < shapeLines.length; y++) {
                for (int x = 0; x < shapeLines[y].length(); x++) {
                    if (shapeLines[y].charAt(x) == '#') {
                        baseShape.add(new int[]{x, y - 1});
                    }
                }
            }
            shapeSizes.add(baseShape.size());
            // Generate all rotations and flips
            allShapes.add(generateAllOrientations(baseShape));
        }
        
        // Parse regions
        List<Region> regions = new ArrayList<>();
        for (int i = splitIndex; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(": ");
            String[] dims = parts[0].split("x");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);
            int[] quantities = Arrays.stream(parts[1].split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
            regions.add(new Region(width, height, quantities));
        }
        
        // Check each region
        int count = 0;
        for (Region region : regions) {
            if (canFit(region, allShapes, shapeSizes)) {
                count++;
            }
        }
        return count;
    }
    
    private Set<Set<int[]>> generateAllOrientations(Set<int[]> shape) {
        Set<Set<int[]>> orientations = new HashSet<>();
        Set<int[]> current = shape;
        
        for (int flip = 0; flip < 2; flip++) {
            for (int rot = 0; rot < 4; rot++) {
                orientations.add(normalize(current));
                current = rotate90(current);
            }
            current = flipHorizontal(shape);
        }
        
        return orientations;
    }
    
    private Set<int[]> normalize(Set<int[]> shape) {
        int minX = shape.stream().mapToInt(c -> c[0]).min().orElse(0);
        int minY = shape.stream().mapToInt(c -> c[1]).min().orElse(0);
        return shape.stream()
            .map(c -> new int[]{c[0] - minX, c[1] - minY})
            .collect(Collectors.toSet());
    }
    
    private Set<int[]> rotate90(Set<int[]> shape) {
        // Rotate 90 degrees clockwise: (x, y) -> (y, -x)
        return shape.stream()
            .map(c -> new int[]{c[1], -c[0]})
            .collect(Collectors.toSet());
    }
    
    private Set<int[]> flipHorizontal(Set<int[]> shape) {
        // Flip horizontally: (x, y) -> (-x, y)
        return shape.stream()
            .map(c -> new int[]{-c[0], c[1]})
            .collect(Collectors.toSet());
    }
    
    private boolean canFit(Region region, List<Set<Set<int[]>>> allShapes, List<Integer> shapeSizes) {
        // First check: total area of shapes must be <= region area  
        int regionArea = region.width * region.height;
        int shapeArea = 0;
        for (int i = 0; i < region.quantities.length; i++) {
            shapeArea += region.quantities[i] * shapeSizes.get(i);
        }
        
        // For a packing problem, shapeArea must be <= regionArea
        return shapeArea <= regionArea;
    }
    
    private boolean solve(boolean[][] grid, List<Set<Set<int[]>>> pieces, int pieceIdx, int width, int height) {
        if (pieceIdx == pieces.size()) {
            return true;
        }
        
        Set<Set<int[]>> orientations = pieces.get(pieceIdx);
        
        // Find first empty cell to try placing
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!grid[y][x]) {
                    // Try each orientation
                    for (Set<int[]> orientation : orientations) {
                        if (canPlace(grid, orientation, x, y, width, height)) {
                            place(grid, orientation, x, y, true);
                            if (solve(grid, pieces, pieceIdx + 1, width, height)) {
                                return true;
                            }
                            place(grid, orientation, x, y, false);
                        }
                    }
                    return false; // Must fill this cell, no orientation worked
                }
            }
        }
        return true;
    }
    
    private boolean canPlace(boolean[][] grid, Set<int[]> shape, int offsetX, int offsetY, int width, int height) {
        for (int[] cell : shape) {
            int x = cell[0] + offsetX;
            int y = cell[1] + offsetY;
            if (x < 0 || x >= width || y < 0 || y >= height || grid[y][x]) {
                return false;
            }
        }
        return true;
    }
    
    private void place(boolean[][] grid, Set<int[]> shape, int offsetX, int offsetY, boolean value) {
        for (int[] cell : shape) {
            int x = cell[0] + offsetX;
            int y = cell[1] + offsetY;
            grid[y][x] = value;
        }
    }

    @Override
    public Object part2() {
        return 0;
    }
}
