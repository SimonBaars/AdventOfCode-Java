package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.Set;

public class Day6 extends Day2022 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) throws IOException {
    Day d = new Day6();
    d.downloadIfNotDownloaded();
//    d.downloadExample();
    d.printParts();
    System.in.read();
//    d.submitPart1();
    d.submitPart2();
  }

  @Override
  public Object part1() {
    String s = day();
    for(int i = 0; i<s.length(); i++){
      Set<Integer> chars = Set.copyOf(s.substring(i, i+4).chars().mapToObj(e -> e).toList());
      if(chars.size() == 4) return i+4;
    }
    return "";
  }

  @Override
  public Object part2() {
    String s = day();
    for(int i = 0; i<s.length(); i++){
      Set<Integer> chars = Set.copyOf(s.substring(i, i+14).chars().mapToObj(e -> e).toList());
      if(chars.size() == 14) return i+14;
    }
    return "";
  }
}
