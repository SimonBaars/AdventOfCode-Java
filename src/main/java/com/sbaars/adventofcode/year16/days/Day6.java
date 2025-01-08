package com.sbaars.adventofcode.year16.days;

import com.sbaars.adventofcode.year16.Day2016;
import java.util.*;
import java.util.stream.Collectors;

public class Day6 extends Day2016 {
  public Day6() {
    super(6);
  }

  public static void main(String[] args) {
    new Day6().printParts();
  }

  private String decodeMessage(boolean mostCommon) {
    List<String> messages = dayStream().collect(Collectors.toList());
    int messageLength = messages.get(0).length();
    StringBuilder result = new StringBuilder();

    for (int pos = 0; pos < messageLength; pos++) {
      Map<Character, Integer> freq = new HashMap<>();
      for (String message : messages) {
        char c = message.charAt(pos);
        freq.merge(c, 1, Integer::sum);
      }

      char selectedChar = freq.entrySet().stream()
          .sorted((a, b) -> {
            int comp = mostCommon ? b.getValue().compareTo(a.getValue()) : a.getValue().compareTo(b.getValue());
            return comp != 0 ? comp : a.getKey().compareTo(b.getKey());
          })
          .map(Map.Entry::getKey)
          .findFirst()
          .orElseThrow();

      result.append(selectedChar);
    }

    return result.toString();
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
