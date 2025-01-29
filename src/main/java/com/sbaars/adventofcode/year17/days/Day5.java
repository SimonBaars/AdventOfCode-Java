package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;

public class Day5 extends Day2017 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  @Override
  public Object part1() {
    int[] jumps = dayIntStream().toArray();
    int pos = 0;
    int steps = 0;
    
    while (pos >= 0 && pos < jumps.length) {
      int jump = jumps[pos];
      jumps[pos]++;
      pos += jump;
      steps++;
    }
    
    return steps;
  }

  @Override
  public Object part2() {
    return "";
  }
}
