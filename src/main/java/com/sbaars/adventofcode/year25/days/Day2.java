package com.sbaars.adventofcode.year25.days;

import com.sbaars.adventofcode.year25.Day2025;

import java.util.Arrays;

public class Day2 extends Day2025 {
  public Day2() {
    super(2);
  }

  public static void main(String[] args) {
    new Day2().printParts();
  }

  record Range(long start, long end) {
    static Range parse(String s) {
      String[] parts = s.trim().split("-");
      return new Range(Long.parseLong(parts[0].trim()), Long.parseLong(parts[1].trim()));
    }
    
    long sumInvalidIds() {
      long sum = 0;
      for (long id = start; id <= end; id++) {
        if (isInvalidId(id)) {
          sum += id;
        }
      }
      return sum;
    }
    
    boolean isInvalidId(long id) {
      String s = String.valueOf(id);
      int len = s.length();
      
      // Must be even length to be repeated twice
      if (len % 2 != 0) return false;
      
      int half = len / 2;
      String firstHalf = s.substring(0, half);
      String secondHalf = s.substring(half);
      
      return firstHalf.equals(secondHalf);
    }
    
    boolean isInvalidIdPart2(long id) {
      String s = String.valueOf(id);
      int len = s.length();
      
      // Try all possible pattern lengths from 1 to len/2
      for (int patternLen = 1; patternLen <= len / 2; patternLen++) {
        // Check if the length is divisible by pattern length
        if (len % patternLen != 0) continue;
        
        int repetitions = len / patternLen;
        if (repetitions < 2) continue;
        
        String pattern = s.substring(0, patternLen);
        boolean valid = true;
        
        // Check if the entire string is this pattern repeated
        for (int i = 0; i < len; i++) {
          if (s.charAt(i) != pattern.charAt(i % patternLen)) {
            valid = false;
            break;
          }
        }
        
        if (valid) return true;
      }
      
      return false;
    }
    
    long sumInvalidIdsPart2() {
      long sum = 0;
      for (long id = start; id <= end; id++) {
        if (isInvalidIdPart2(id)) {
          sum += id;
        }
      }
      return sum;
    }
  }

  @Override
  public Object part1() {
    return Arrays.stream(day().split(","))
        .filter(s -> !s.trim().isEmpty())
        .map(Range::parse)
        .mapToLong(Range::sumInvalidIds)
        .sum();
  }

  @Override
  public Object part2() {
    return Arrays.stream(day().split(","))
        .filter(s -> !s.trim().isEmpty())
        .map(Range::parse)
        .mapToLong(Range::sumInvalidIdsPart2)
        .sum();
  }
}
