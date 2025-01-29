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

  @Override
  public Object part1() {
    Map<Integer, Integer> scanners = new HashMap<>();
    
    // Parse input
    for (String line : dayStrings()) {
      String[] parts = line.split(": ");
      scanners.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
    
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
    return "";
  }
}
