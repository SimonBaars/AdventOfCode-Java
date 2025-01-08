package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.LinkedList;

public class Day19 extends Day2016 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  private int findWinningElf(int numElves) {
    LinkedList<Integer> elves = new LinkedList<>();
    for (int i = 1; i <= numElves; i++) {
      elves.add(i);
    }

    while (elves.size() > 1) {
      // Move first elf to the end (they keep their presents)
      elves.addLast(elves.removeFirst());
      // Remove the next elf (they lose their presents)
      elves.removeFirst();
    }

    return elves.getFirst();
  }

  @Override
  public Object part1() {
    return findWinningElf(Integer.parseInt(day().trim()));
  }

  @Override
  public Object part2() {
    return "";
  }
}
