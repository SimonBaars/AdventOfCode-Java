package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 extends Day2016 {

  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  private char getMostFrequentChar(List<String> messages, int position) {
    return messages.stream()
        .map(s -> s.charAt(position))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElseThrow();
  }

  @Override
  public Object part1() {
    List<String> messages = dayStream().toList();
    int messageLength = messages.get(0).length();

    return IntStream.range(0, messageLength)
        .mapToObj(pos -> getMostFrequentChar(messages, pos))
        .map(String::valueOf)
        .collect(Collectors.joining());
  }

  @Override
  public Object part2() {
    return "";
  }
}
