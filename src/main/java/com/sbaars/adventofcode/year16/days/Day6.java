package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import com.sbaars.adventofcode.common.map.LongCountMap;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day6 extends Day2016 {
  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  private String decodeMessage(boolean mostCommon) {
    List<String> messages = dayStream().collect(Collectors.toList());
    return IntStream.range(0, messages.get(0).length())
        .mapToObj(pos -> messages.stream()
            .map(msg -> msg.charAt(pos))
            .collect(LongCountMap.toCountMap()))
        .map(freq -> freq.entrySet().stream()
            .sorted((a, b) -> mostCommon ? 
                b.getValue().compareTo(a.getValue()) : 
                a.getValue().compareTo(b.getValue()))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElseThrow())
        .map(String::valueOf)
        .collect(Collectors.joining());
  }

  @Override
  public Object part1() {
    return decodeMessage(true);
  }

  @Override
  public Object part2() {
    return decodeMessage(false);
  }
}
