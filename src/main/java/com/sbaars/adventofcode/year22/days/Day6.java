package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.io.IOException;
import java.util.Set;

public class Day6 extends Day2022 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) throws IOException {
    new Day6().printParts();
  }

  @Override
  public Object part1() {
    return calculateAnswer(4);
  }

  private int calculateAnswer(int size) {
    String s = day();
    for(int i = 0; i<s.length(); i++){
      Set<Integer> chars = Set.copyOf(s.substring(i, i+size).chars().boxed().toList());
      if(chars.size() == size) return i+size;
    }
    return 0;
  }

  @Override
  public Object part2() {
    return calculateAnswer(14);
  }
}
