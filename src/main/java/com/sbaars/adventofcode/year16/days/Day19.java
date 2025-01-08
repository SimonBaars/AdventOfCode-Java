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

  private int findWinningElfPart1(int numElves) {
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

  private int findWinningElfPart2(int numElves) {
    // Pattern: For n = 3^k + l where l < 3^k
    // Winner = l if l ≤ 3^k/2
    // Winner = 2l - 3^k if 3^k/2 < l ≤ 3^k
    int pow = 1;
    while (pow * 3 <= numElves) {
      pow *= 3;
    }
    
    int l = numElves - pow;
    if (l <= pow) {
      return l;
    } else {
      return 2 * l - pow;
    }
  }

  @Override
  public Object part1() {
    return findWinningElfPart1(Integer.parseInt(day().trim()));
  }

  @Override
  public Object part2() {
    return findWinningElfPart2(Integer.parseInt(day().trim()));
  }
}
