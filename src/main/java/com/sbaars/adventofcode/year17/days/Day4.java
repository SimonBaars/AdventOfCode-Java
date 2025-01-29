package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.Arrays;
import java.util.HashSet;

public class Day4 extends Day2017 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  @Override
  public Object part1() {
    return dayStream()
        .filter(line -> {
          String[] words = line.split(" ");
          HashSet<String> uniqueWords = new HashSet<>(Arrays.asList(words));
          return uniqueWords.size() == words.length;
        })
        .count();
  }

  @Override
  public Object part2() {
    return "";
  }
}
