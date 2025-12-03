package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

public class Day3 extends Day2025 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    return dayStream()
        .mapToInt(line -> {
          int max = 0;
          for (int i = 0; i < line.length() - 1; i++) {
            for (int j = i + 1; j < line.length(); j++) {
              int val = Integer.parseInt("" + line.charAt(i) + line.charAt(j));
              max = Math.max(max, val);
            }
          }
          return max;
        })
        .sum();
  }

  @Override
  public Object part2() {
    return dayStream()
        .mapToLong(line -> {
          // Greedy approach: pick largest digit at each position
          StringBuilder result = new StringBuilder();
          String remaining = line;
          
          for (int i = 0; i < 12 && !remaining.isEmpty(); i++) {
            int maxIdx = 0;
            char maxChar = remaining.charAt(0);
            
            // Find the largest digit in positions that leave enough digits for remaining picks
            int searchEnd = remaining.length() - (12 - i - 1);
            for (int j = 1; j < searchEnd; j++) {
              if (remaining.charAt(j) > maxChar) {
                maxChar = remaining.charAt(j);
                maxIdx = j;
              }
            }
            
            result.append(maxChar);
            remaining = remaining.substring(maxIdx + 1);
          }
          
          return Long.parseLong(result.toString());
        })
        .sum();
  }
}
