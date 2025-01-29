package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.ArrayList;
import java.util.List;

public class Day17 extends Day2017 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    new Day17().printParts();
  }

  @Override
  public Object part1() {
    int steps = Integer.parseInt(day().trim());
    List<Integer> buffer = new ArrayList<>();
    buffer.add(0);
    int currentPos = 0;

    for (int i = 1; i <= 2017; i++) {
      currentPos = ((currentPos + steps) % buffer.size()) + 1;
      buffer.add(currentPos, i);
    }

    // Find the value after 2017
    int index2017 = buffer.indexOf(2017);
    return buffer.get((index2017 + 1) % buffer.size());
  }

  @Override
  public Object part2() {
    int steps = Integer.parseInt(day().trim());
    int currentPos = 0;
    int valueAfterZero = 0;
    int bufferSize = 1;

    for (int i = 1; i <= 50_000_000; i++) {
      currentPos = ((currentPos + steps) % bufferSize) + 1;
      
      // If we're inserting at position 1 (after 0), update valueAfterZero
      if (currentPos == 1) {
        valueAfterZero = i;
      }
      
      bufferSize++;
    }

    return valueAfterZero;
  }
}
