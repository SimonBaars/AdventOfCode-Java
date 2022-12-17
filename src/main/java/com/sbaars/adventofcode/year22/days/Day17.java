package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.common.grid.InfiniteGrid;
import com.sbaars.adventofcode.year22.Day2022;

public class Day17 extends Day2022 {
  public Day17() {
    super(17);
  }

  public static void main(String[] args) {
    Day d = new Day17();
    d.downloadIfNotDownloaded();
//    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Shape(int w, int h, int b, int t, InfiniteGrid g) {}

  @Override
  public Object part1() {
    Shape[] shapes = new Shape[]{
            new Shape(4, 1, 4, 4, new InfiniteGrid(new char[][]{"####".toCharArray()})),
            new Shape(3, 3, 1, 1, new InfiniteGrid(new char[][]{" # ".toCharArray(), "###".toCharArray(), " # ".toCharArray()})),
            new Shape(3, 3, 3, 1),
            new Shape(1, 4, 1, 1),
            new Shape(2, 2, 2, 2)
    };
    char[] chars = day().toCharArray();
    int height = 0;
    int i = 0;
    int shapeIndex = 0;
    int left = 2;
    for(int fallenRocks = 0; fallenRocks<2022; i++) {
      char c = chars[i];
      Shape s = shapes[shapeIndex];
      if(c == '>') {
        left = left + s.w + 1 > 7 ? 7 : left + 1;
      } else if(c == '<') {
        left = left == 0 ? 0 : left - 1;
      }
    }
    return "";
  }

  @Override
  public Object part2() {
    return "";
  }
}
