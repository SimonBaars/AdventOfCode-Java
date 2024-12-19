package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.map.LongCountMap;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.util.DataMapper.readString;

import java.util.*;

public class Day19 extends Day2024 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  public record Input(List<String> patterns, List<String> designs) {}

  @Override
  public Object part1() {
    Input input = input();
    return input.designs().stream().filter(d -> solve(d, input.patterns()) > 0).count();
  }

  @Override
  public Object part2() {
    Input input = input();
    return input.designs().stream().mapToLong(d -> solve(d, input.patterns())).sum();
  }

  private Input input() {
    return readString(day(), "%ls\n\n%ls", new String[]{", ", "\n"}, Input.class);
  }

  private long solve(String design, List<String> patterns) {
    var lcm = new LongCountMap<Integer>(Map.of(0, 1L));
    for (int i = 0; i < design.length(); i++) {
      for (String pattern : patterns) {
        int end = i + pattern.length();
        if (end <= design.length() && design.startsWith(pattern, i)) {
          lcm.increment(end, lcm.get(i));
        }
      }
    }
    return lcm.get(design.length());
  }
}
