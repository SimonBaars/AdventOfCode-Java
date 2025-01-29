package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day10 extends Day2017 {
  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    new Day10().printParts();
  }

  @Override
  public Object part1() {
    int[] list = IntStream.range(0, 256).toArray();
    int[] lengths = Arrays.stream(day().trim().split(",")).mapToInt(Integer::parseInt).toArray();
    int currentPos = 0;
    int skipSize = 0;
    
    for (int length : lengths) {
      // Reverse the sublist
      for (int i = 0; i < length / 2; i++) {
        int pos1 = (currentPos + i) % list.length;
        int pos2 = (currentPos + length - 1 - i) % list.length;
        int temp = list[pos1];
        list[pos1] = list[pos2];
        list[pos2] = temp;
      }
      
      // Move current position and increase skip size
      currentPos = (currentPos + length + skipSize) % list.length;
      skipSize++;
    }
    
    return list[0] * list[1];
  }

  @Override
  public Object part2() {
    return "";
  }
}
