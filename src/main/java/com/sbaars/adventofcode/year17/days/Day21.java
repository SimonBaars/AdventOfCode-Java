package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import com.sbaars.adventofcode.common.grid.CharGrid;
import java.util.*;

public class Day21 extends Day2017 {
  private static final String INITIAL_PATTERN = ".#./..#/###";
  
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts();
  }

  private CharGrid stringToGrid(String pattern) {
    return new CharGrid(pattern.replace("/", "\n"));
  }

  private String gridToString(CharGrid grid) {
    return grid.toString().replace("\n", "/").trim();
  }

  private List<String> getAllPatternVariations(String pattern) {
    CharGrid grid = stringToGrid(pattern);
    Set<String> variations = new HashSet<>();
    
    // Original pattern
    variations.add(gridToString(grid));
    
    // Rotations
    for (int i = 0; i < 3; i++) {
      char[][] rotated = new char[grid.grid[0].length][grid.grid.length];
      for (int y = 0; y < grid.grid.length; y++) {
        for (int x = 0; x < grid.grid[0].length; x++) {
          rotated[x][grid.grid.length - 1 - y] = grid.grid[y][x];
        }
      }
      grid = new CharGrid(rotated);
      variations.add(gridToString(grid));
    }
    
    // Flips
    char[][] flipped = new char[grid.grid.length][grid.grid[0].length];
    for (int y = 0; y < grid.grid.length; y++) {
      for (int x = 0; x < grid.grid[0].length; x++) {
        flipped[y][grid.grid[0].length - 1 - x] = grid.grid[y][x];
      }
    }
    grid = new CharGrid(flipped);
    variations.add(gridToString(grid));
    
    // Rotations of flipped
    for (int i = 0; i < 3; i++) {
      char[][] rotated = new char[grid.grid[0].length][grid.grid.length];
      for (int y = 0; y < grid.grid.length; y++) {
        for (int x = 0; x < grid.grid[0].length; x++) {
          rotated[x][grid.grid.length - 1 - y] = grid.grid[y][x];
        }
      }
      grid = new CharGrid(rotated);
      variations.add(gridToString(grid));
    }
    
    return new ArrayList<>(variations);
  }

  private Map<String, String> parseRules(String input) {
    Map<String, String> rules = new HashMap<>();
    for (String line : input.split("\n")) {
      String[] parts = line.split(" => ");
      String pattern = parts[0];
      String result = parts[1];
      
      for (String variation : getAllPatternVariations(pattern)) {
        rules.put(variation, result);
      }
    }
    return rules;
  }

  private CharGrid enhance(CharGrid input, Map<String, String> rules) {
    int size = input.grid.length;
    int step = (size % 2 == 0) ? 2 : 3;
    int newStep = step + 1;
    int newSize = size / step * newStep;
    
    CharGrid result = new CharGrid(new char[newSize][newSize]);
    
    for (int y = 0; y < size; y += step) {
      for (int x = 0; x < size; x += step) {
        // Extract subgrid
        char[][] subgrid = new char[step][step];
        for (int i = 0; i < step; i++) {
          for (int j = 0; j < step; j++) {
            subgrid[i][j] = input.grid[y + i][x + j];
          }
        }
        
        // Find matching rule and apply transformation
        String pattern = gridToString(new CharGrid(subgrid));
        String transformed = rules.get(pattern);
        CharGrid newSubgrid = stringToGrid(transformed);
        
        // Place transformed subgrid in result
        int newY = y / step * newStep;
        int newX = x / step * newStep;
        for (int i = 0; i < newStep; i++) {
          for (int j = 0; j < newStep; j++) {
            result.grid[newY + i][newX + j] = newSubgrid.grid[i][j];
          }
        }
      }
    }
    
    return result;
  }

  private long solve(int iterations) {
    Map<String, String> rules = parseRules(day());
    CharGrid grid = stringToGrid(INITIAL_PATTERN);
    
    for (int i = 0; i < iterations; i++) {
      grid = enhance(grid, rules);
    }
    
    return grid.countChar('#');
  }

  @Override
  public Object part1() {
    return solve(5);
  }

  @Override
  public Object part2() {
    return solve(18);
  }
}
