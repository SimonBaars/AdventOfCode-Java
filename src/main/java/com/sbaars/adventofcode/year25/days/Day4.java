package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

public class Day4 extends Day2025 {
  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  @Override
  public Object part1() {
    char[][] grid = dayGrid();
    int count = 0;
    
    for (int r = 0; r < grid.length; r++) {
      for (int c = 0; c < grid[r].length; c++) {
        if (grid[r][c] == '@') {
          int neighbors = 0;
          // Check all 8 adjacent positions
          for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
              if (dr == 0 && dc == 0) continue;
              int nr = r + dr;
              int nc = c + dc;
              if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[nr].length) {
                if (grid[nr][nc] == '@') {
                  neighbors++;
                }
              }
            }
          }
          if (neighbors < 4) {
            count++;
          }
        }
      }
    }
    
    return count;
  }

  @Override
  public Object part2() {
    char[][] grid = dayGrid();
    int totalRemoved = 0;
    boolean changed = true;
    
    while (changed) {
      changed = false;
      char[][] newGrid = new char[grid.length][];
      for (int i = 0; i < grid.length; i++) {
        newGrid[i] = grid[i].clone();
      }
      
      for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[r].length; c++) {
          if (grid[r][c] == '@') {
            int neighbors = 0;
            // Check all 8 adjacent positions
            for (int dr = -1; dr <= 1; dr++) {
              for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = r + dr;
                int nc = c + dc;
                if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[nr].length) {
                  if (grid[nr][nc] == '@') {
                    neighbors++;
                  }
                }
              }
            }
            if (neighbors < 4) {
              newGrid[r][c] = '.';
              totalRemoved++;
              changed = true;
            }
          }
        }
      }
      
      grid = newGrid;
    }
    
    return totalRemoved;
  }
}
