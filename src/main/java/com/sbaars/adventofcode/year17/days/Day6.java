package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day6 extends Day2017 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    int[] banks = Arrays.stream(day().trim().split("\t")).mapToInt(Integer::parseInt).toArray();
    Set<String> seen = new HashSet<>();
    int cycles = 0;
    
    while (true) {
      String state = Arrays.toString(banks);
      if (!seen.add(state)) {
        return cycles;
      }
      
      // Find bank with most blocks
      int maxIndex = 0;
      for (int i = 1; i < banks.length; i++) {
        if (banks[i] > banks[maxIndex]) {
          maxIndex = i;
        }
      }
      
      // Redistribute blocks
      int blocks = banks[maxIndex];
      banks[maxIndex] = 0;
      for (int i = 0; i < blocks; i++) {
        banks[(maxIndex + 1 + i) % banks.length]++;
      }
      
      cycles++;
    }
  }

  @Override
  public Object part2() {
    int[] banks = Arrays.stream(day().trim().split("\t")).mapToInt(Integer::parseInt).toArray();
    Set<String> seen = new HashSet<>();
    int cycles = 0;
    String firstRepeat = null;
    
    while (true) {
      String state = Arrays.toString(banks);
      if (state.equals(firstRepeat)) {
        return cycles;
      }
      if (!seen.add(state)) {
        firstRepeat = state;
        cycles = 0;
      }
      
      // Find bank with most blocks
      int maxIndex = 0;
      for (int i = 1; i < banks.length; i++) {
        if (banks[i] > banks[maxIndex]) {
          maxIndex = i;
        }
      }
      
      // Redistribute blocks
      int blocks = banks[maxIndex];
      banks[maxIndex] = 0;
      for (int i = 0; i < blocks; i++) {
        banks[(maxIndex + 1 + i) % banks.length]++;
      }
      
      cycles++;
    }
  }
}
