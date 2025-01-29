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
    int input = Integer.parseInt(day().trim());
    
    // Find the layer (ring) containing the input number
    int layer = 0;
    int maxInLayer = 1;
    while (maxInLayer < input) {
      layer++;
      maxInLayer = (2 * layer + 1) * (2 * layer + 1);
    }
    
    if (input == 1) return 0;
    
    // Find the side length of the current square
    int sideLength = 2 * layer + 1;
    
    // Calculate position relative to the middle of each side
    int bottomRight = maxInLayer;
    int bottomLeft = bottomRight - (sideLength - 1);
    int topLeft = bottomLeft - (sideLength - 1);
    int topRight = topLeft - (sideLength - 1);
    
    // Find the closest middle point
    int[] midPoints = {
      bottomRight - layer,
      bottomLeft - layer,
      topLeft - layer,
      topRight - layer
    };
    
    int minDist = Integer.MAX_VALUE;
    for (int mid : midPoints) {
      minDist = Math.min(minDist, Math.abs(input - mid));
    }
    
    return layer + minDist;
  }

  @Override
  public Object part2() {
    return "";
  }
}
