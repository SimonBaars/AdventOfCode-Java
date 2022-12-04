package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;

public class Day5 extends Day2022 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) throws IOException {
    Day d = new Day5();
    d.downloadIfNotDownloaded();
//    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  @Override
  public Object part1() {
    return dayStream();
  }

  @Override
  public Object part2() {
    return "";
  }
}
