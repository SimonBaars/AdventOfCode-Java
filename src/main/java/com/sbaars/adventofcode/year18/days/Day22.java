package com.sbaars.adventofcode.year18.days;

import com.sbaars.adventofcode.year18.Day2018;
import java.util.*;

public class Day22 extends Day2018 {
  private static final int ROCKY = 0;
  private static final int WET = 1;
  private static final int NARROW = 2;
  
  private static final int TORCH = 0;
  private static final int CLIMBING = 1;
  private static final int NEITHER = 2;

  public Day22() {
    super(22);
  }

  public static void main(String[] args) {
    new Day22().printParts();
  }

  private int[][] calculateErosionLevels(int depth, int targetX, int targetY, int extraSpace) {
    int width = targetX + 1 + extraSpace;
    int height = targetY + 1 + extraSpace;
    int[][] erosionLevels = new int[width][height];
    
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        long geologicIndex;
        if ((x == 0 && y == 0) || (x == targetX && y == targetY)) {
          geologicIndex = 0;
        } else if (y == 0) {
          geologicIndex = x * 16807L;
        } else if (x == 0) {
          geologicIndex = y * 48271L;
        } else {
          geologicIndex = (long) erosionLevels[x-1][y] * erosionLevels[x][y-1];
        }
        erosionLevels[x][y] = (int) ((geologicIndex + depth) % 20183);
      }
    }
    return erosionLevels;
  }

  private int getRegionType(int erosionLevel) {
    return erosionLevel % 3;
  }

  private boolean isToolValidForRegion(int tool, int regionType) {
    switch (regionType) {
      case ROCKY: return tool == TORCH || tool == CLIMBING;
      case WET: return tool == CLIMBING || tool == NEITHER;
      case NARROW: return tool == TORCH || tool == NEITHER;
      default: return false;
    }
  }

  @Override
  public Object part1() {
    String[] lines = day().split("\n");
    int depth = Integer.parseInt(lines[0].split(": ")[1]);
    String[] targetParts = lines[1].split(": ")[1].split(",");
    int targetX = Integer.parseInt(targetParts[0]);
    int targetY = Integer.parseInt(targetParts[1]);

    int[][] erosionLevels = calculateErosionLevels(depth, targetX, targetY, 0);
    
    int riskLevel = 0;
    for (int y = 0; y <= targetY; y++) {
      for (int x = 0; x <= targetX; x++) {
        riskLevel += getRegionType(erosionLevels[x][y]);
      }
    }
    return riskLevel;
  }

  private record State(int x, int y, int tool, int minutes) implements Comparable<State> {
    
    @Override
    public int compareTo(State other) {
      return Integer.compare(this.minutes, other.minutes);
    }
    
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof State)) return false;
      State other = (State) o;
      return x == other.x && y == other.y && tool == other.tool;
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(x, y, tool);
    }
  }

  @Override
  public Object part2() {
    String[] lines = day().split("\n");
    int depth = Integer.parseInt(lines[0].split(": ")[1]);
    String[] targetParts = lines[1].split(": ")[1].split(",");
    int targetX = Integer.parseInt(targetParts[0]);
    int targetY = Integer.parseInt(targetParts[1]);
    
    // Add extra space for potential better paths
    int extraSpace = 50;
    int[][] erosionLevels = calculateErosionLevels(depth, targetX, targetY, extraSpace);
    
    PriorityQueue<State> queue = new PriorityQueue<>();
    Set<State> visited = new HashSet<>();
    
    // Start at 0,0 with torch
    queue.add(new State(0, 0, TORCH, 0));
    
    int[] dx = {0, 1, 0, -1};
    int[] dy = {-1, 0, 1, 0};
    
    while (!queue.isEmpty()) {
      State current = queue.poll();
      
      if (current.x == targetX && current.y == targetY) {
        if (current.tool == TORCH) {
          return current.minutes;
        } else {
          // Need to switch to torch at target
          return current.minutes + 7;
        }
      }
      
      if (visited.contains(current)) continue;
      visited.add(current);
      
      // Try moving in each direction
      for (int i = 0; i < 4; i++) {
        int newX = current.x + dx[i];
        int newY = current.y + dy[i];
        
        if (newX < 0 || newY < 0 || newX >= erosionLevels.length || newY >= erosionLevels[0].length) continue;
        
        int newRegionType = getRegionType(erosionLevels[newX][newY]);
        
        // Try moving with current tool
        if (isToolValidForRegion(current.tool, newRegionType)) {
          queue.add(new State(newX, newY, current.tool, current.minutes + 1));
        }
      }
      
      // Try switching tools
      int currentRegionType = getRegionType(erosionLevels[current.x][current.y]);
      for (int newTool = 0; newTool < 3; newTool++) {
        if (newTool != current.tool && isToolValidForRegion(newTool, currentRegionType)) {
          queue.add(new State(current.x, current.y, newTool, current.minutes + 7));
        }
      }
    }
    
    return -1; // No path found
  }
}
