package com.sbaars.adventofcode.year15.days;

import com.sbaars.adventofcode.year15.Day2015;

public class Day10 extends Day2015 {

  public Day10() {
    super(10);
  }

  public static void main(String[] args) {
    Day10 day = new Day10();
    day.printParts();
    new com.sbaars.adventofcode.network.Submit().submit(day.part1(), 2015, 10, 1);
    new com.sbaars.adventofcode.network.Submit().submit(day.part2(), 2015, 10, 2);
  }

  @Override
  public Object part1() {
    return lookAndSay(day().trim(), 40).length();
  }

  @Override
  public Object part2() {
    return lookAndSay(day().trim(), 50).length();
  }

  private String lookAndSay(String input, int times) {
    String result = input;
    for (int i = 0; i < times; i++) {
      result = lookAndSayOnce(result);
    }
    return result;
  }

  private String lookAndSayOnce(String input) {
    StringBuilder result = new StringBuilder();
    int count = 1;
    char current = input.charAt(0);

    for (int i = 1; i < input.length(); i++) {
      if (input.charAt(i) == current) {
        count++;
      } else {
        result.append(count).append(current);
        current = input.charAt(i);
        count = 1;
      }
    }
    result.append(count).append(current);

    return result.toString();
  }
}
