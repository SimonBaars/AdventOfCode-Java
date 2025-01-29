package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;

public class Day3 extends Day2017 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    int input = (int)dayNumber();
    if (input == 1) return 0;
    
    // Find the ring number and side length
    int ring = 1;
    int sideLength = 1;
    while (sideLength * sideLength < input) {
      ring++;
      sideLength = 2 * ring - 1;
    }
    
    // Find position within the ring
    int maxInRing = sideLength * sideLength;
    int minInRing = (sideLength - 2) * (sideLength - 2) + 1;
    int stepsFromCenter = ring - 1;
    
    // Find the closest middle point of any side
    int[] midPoints = new int[4];
    for (int i = 0; i < 4; i++) {
      midPoints[i] = maxInRing - (sideLength - 1) * (i + 1) / 2;
    }
    
    // Find minimum distance to any middle point
    int minDist = Integer.MAX_VALUE;
    for (int mid : midPoints) {
      minDist = Math.min(minDist, Math.abs(input - mid));
    }
    
    return stepsFromCenter + minDist;
  }

  @Override
  public Object part2() {
    return "";
  }
}
