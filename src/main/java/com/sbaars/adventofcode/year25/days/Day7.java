package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day7 extends Day2025 {
    public Day7() {
        super(7);
    }

    public static void main(String[] args) throws IOException {
        new Day7().printParts();
    }

    record Pos(int row, int col) {}
    record Beam(int row, int col) {}  // Tracks beam position

    @Override
    public Object part1() {
        String[] lines = dayStream().toArray(String[]::new);
        
        // Find the starting position 'S'
        int startCol = -1;
        for (int i = 0; i < lines[0].length(); i++) {
            if (lines[0].charAt(i) == 'S') {
                startCol = i;
                break;
            }
        }
        
        // Track which beams we've processed to avoid infinite loops
        Set<Beam> processedBeams = new HashSet<>();
        
        // Start with the initial beam at column startCol
        return simulateBeam(lines, startCol, 0, processedBeams);
    }
    
    private int simulateBeam(String[] lines, int col, int startRow, Set<Beam> processedBeams) {
        int splitCount = 0;
        
        for (int row = startRow; row < lines.length; row++) {
            Beam currentBeam = new Beam(row, col);
            
            // If we've already processed this beam, stop
            if (processedBeams.contains(currentBeam)) {
                return 0;
            }
            processedBeams.add(currentBeam);
            
            if (col < 0 || col >= lines[row].length()) {
                return splitCount; // Beam exits the manifold
            }
            
            char c = lines[row].charAt(col);
            if (c == '^') {
                // This is a split - count it
                splitCount++;
                
                // Emit beams to left and right
                splitCount += simulateBeam(lines, col - 1, row + 1, processedBeams);
                splitCount += simulateBeam(lines, col + 1, row + 1, processedBeams);
                
                return splitCount; // Original beam stops
            }
        }
        
        return splitCount;
    }

    @Override
    public Object part2() {
        String[] lines = dayStream().toArray(String[]::new);
        
        // Find the starting position 'S'
        int startCol = -1;
        for (int i = 0; i < lines[0].length(); i++) {
            if (lines[0].charAt(i) == 'S') {
                startCol = i;
                break;
            }
        }
        
        // Track particle positions: for each column, how many timelines have a particle there
        Map<Integer, Long> currentRow = new HashMap<>();
        currentRow.put(startCol, 1L);
        
        // Simulate row by row
        for (int row = 1; row < lines.length; row++) {
            Map<Integer, Long> nextRow = new HashMap<>();
            
            for (Map.Entry<Integer, Long> entry : currentRow.entrySet()) {
                int col = entry.getKey();
                long count = entry.getValue();
                
                if (col < 0 || col >= lines[row].length()) {
                    continue; // Beam exits
                }
                
                char c = lines[row].charAt(col);
                if (c == '^') {
                    // Split: particle goes both left and right
                    nextRow.merge(col - 1, count, Long::sum);
                    nextRow.merge(col + 1, count, Long::sum);
                } else {
                    // Continue downward
                    nextRow.merge(col, count, Long::sum);
                }
            }
            
            currentRow = nextRow;
        }
        
        // Sum all timelines
        return currentRow.values().stream().mapToLong(Long::longValue).sum();
    }
}
