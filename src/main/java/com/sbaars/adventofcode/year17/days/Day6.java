package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.*;

public class Day6 extends Day2017 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  private String getState(int[] banks) {
    StringBuilder sb = new StringBuilder();
    for (int bank : banks) {
      sb.append(bank).append(',');
    }
    return sb.toString();
  }

  private int[] redistribute(int[] banks) {
    int[] newBanks = banks.clone();
    int maxIndex = 0;
    int maxValue = newBanks[0];
    
    // Find bank with most blocks
    for (int i = 1; i < newBanks.length; i++) {
      if (newBanks[i] > maxValue) {
        maxValue = newBanks[i];
        maxIndex = i;
      }
    }
    
    // Redistribute blocks
    newBanks[maxIndex] = 0;
    int index = (maxIndex + 1) % newBanks.length;
    while (maxValue > 0) {
      newBanks[index]++;
      maxValue--;
      index = (index + 1) % newBanks.length;
    }
    
    return newBanks;
  }

  @Override
  public Object part1() {
    int[] banks = Arrays.stream(day().trim().split("\t")).mapToInt(Integer::parseInt).toArray();
    Set<String> seen = new HashSet<>();
    int cycles = 0;
    
    while (seen.add(getState(banks))) {
      banks = redistribute(banks);
      cycles++;
    }
    
    return cycles;
  }

  @Override
  public Object part2() {
    int[] banks = Arrays.stream(day().trim().split("\t")).mapToInt(Integer::parseInt).toArray();
    Map<String, Integer> seen = new HashMap<>();
    int cycles = 0;
    
    while (true) {
      String state = getState(banks);
      if (seen.containsKey(state)) {
        return cycles - seen.get(state);
      }
      seen.put(state, cycles);
      banks = redistribute(banks);
      cycles++;
    }
  }
}
