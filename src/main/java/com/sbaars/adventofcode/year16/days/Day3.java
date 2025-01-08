package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 extends Day2016 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  private boolean isValidTriangle(int a, int b, int c) {
    return a + b > c && b + c > a && a + c > b;
  }

  @Override
  public Object part1() {
    return dayStream()
        .map(line -> Arrays.stream(line.trim().split("\\s+"))
            .mapToInt(Integer::parseInt)
            .toArray())
        .filter(sides -> isValidTriangle(sides[0], sides[1], sides[2]))
        .count();
  }

  @Override
  public Object part2() {
    List<String> lines = dayStream().collect(Collectors.toList());
    int validTriangles = 0;
    
    for (int i = 0; i < lines.size(); i += 3) {
        int[][] triangles = new int[3][3];
        
        // Read three rows
        for (int row = 0; row < 3; row++) {
            String[] nums = lines.get(i + row).trim().split("\\s+");
            for (int col = 0; col < 3; col++) {
                triangles[row][col] = Integer.parseInt(nums[col]);
            }
        }
        
        // Check each column as a triangle
        for (int col = 0; col < 3; col++) {
            if (isValidTriangle(triangles[0][col], triangles[1][col], triangles[2][col])) {
                validTriangles++;
            }
        }
    }
    
    return validTriangles;
  }
}
