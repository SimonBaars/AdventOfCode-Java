package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 extends Day2025 {
  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  record Range(long start, long end) {
    boolean contains(long id) {
      return id >= start && id <= end;
    }
  }

  @Override
  public Object part1() {
    String[] parts = day().split("\n\n");
    
    List<Range> ranges = Arrays.stream(parts[0].split("\n"))
        .map(line -> {
          String[] nums = line.split("-");
          return new Range(Long.parseLong(nums[0]), Long.parseLong(nums[1]));
        })
        .collect(Collectors.toList());
    
    return Arrays.stream(parts[1].split("\n"))
        .map(Long::parseLong)
        .filter(id -> ranges.stream().anyMatch(range -> range.contains(id)))
        .count();
  }

  @Override
  public Object part2() {
    String[] parts = day().split("\n\n");
    
    List<Range> ranges = Arrays.stream(parts[0].split("\n"))
        .map(line -> {
          String[] nums = line.split("-");
          return new Range(Long.parseLong(nums[0]), Long.parseLong(nums[1]));
        })
        .collect(Collectors.toList());
    
    // Merge overlapping ranges and count total IDs
    ranges.sort(Comparator.comparingLong(Range::start));
    
    long totalFresh = 0;
    long currentStart = ranges.get(0).start;
    long currentEnd = ranges.get(0).end;
    
    for (int i = 1; i < ranges.size(); i++) {
      Range r = ranges.get(i);
      if (r.start <= currentEnd + 1) {
        // Overlapping or adjacent, merge
        currentEnd = Math.max(currentEnd, r.end);
      } else {
        // Non-overlapping, count current range and start new one
        totalFresh += (currentEnd - currentStart + 1);
        currentStart = r.start;
        currentEnd = r.end;
      }
    }
    
    // Add the last range
    totalFresh += (currentEnd - currentStart + 1);
    
    return totalFresh;
  }
}
