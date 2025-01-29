package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.HashMap;
import java.util.Map;

public class Day13 extends Day2017 {
  public Day13() {
    super(13);
  }

  public static void main(String[] args) {
    new Day13().printParts();
  }
  
  private Map<Integer, Integer> parseScanners() {
    Map<Integer, Integer> scanners = new HashMap<>();
    for (String line : dayStrings()) {
      String[] parts = line.split(": ");
      scanners.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
    return scanners;
  }
  
  private boolean isCaught(Map<Integer, Integer> scanners, int delay) {
    for (Map.Entry<Integer, Integer> entry : scanners.entrySet()) {
      int depth = entry.getKey();
      int range = entry.getValue();
      // Scanner position at time = depth + delay
      if ((depth + delay) % (2 * (range - 1)) == 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Object part1() {
    Map<Integer, Integer> scanners = parseScanners();
    int severity = 0;
    int maxDepth = scanners.keySet().stream().mapToInt(Integer::intValue).max().orElse(0);
    
    // For each layer
    for (int depth = 0; depth <= maxDepth; depth++) {
      if (scanners.containsKey(depth)) {
        int range = scanners.get(depth);
        // Scanner position at time = depth is depth % (2 * (range - 1))
        // If position is 0, we get caught
        if (depth % (2 * (range - 1)) == 0) {
          severity += depth * range;
        }
      }
    }
    
    return severity;
  }

  @Override
  public Object part2() {
    Map<Integer, Integer> scanners = parseScanners();
    int delay = 0;
    
    while (isCaught(scanners, delay)) {
      delay++;
    }
    
    return delay;
  }
}
