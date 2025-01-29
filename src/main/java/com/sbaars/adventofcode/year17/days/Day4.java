package com.sbaars.adventofcode.year17.days;

import com.sbaars.adventofcode.year17.Day2017;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Day4 extends Day2017 {

  public Day4() {
    super(4);
  }

  public static void main(String[] args) {
    new Day4().printParts();
  }

  private String sortString(String str) {
    return str.chars()
        .sorted()
        .mapToObj(c -> String.valueOf((char)c))
        .collect(Collectors.joining());
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
    return dayStream()
        .filter(line -> {
          String[] words = line.split(" ");
          HashSet<String> sortedWords = Arrays.stream(words)
              .map(this::sortString)
              .collect(Collectors.toCollection(HashSet::new));
          return sortedWords.size() == words.length;
        })
        .count();
  }
}
