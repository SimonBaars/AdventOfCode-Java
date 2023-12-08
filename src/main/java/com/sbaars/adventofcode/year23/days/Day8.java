package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.year23.Day2023;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static com.sbaars.adventofcode.year19.days.Day12.lcm;

public class Day8 extends Day2023 {

  public Day8() {
    super(8);
  }

  public static void main(String[] args) {
    new Day8().printParts();
  }

  public record Instruction(String from, String left, String right) {}

  @Override
  public Object part1() {
    String[] input = day().split("\n\n");
    Map<String, Instruction> inst = Arrays.stream(input[1].split("\n")).map(s -> readString(s, "%s = (%s, %s)", Instruction.class)).collect(Collectors.toMap(e -> e.from, e -> e));
    return walk(input, inst, "AAA", "ZZZ");
  }

  @Override
  public Object part2() {
    String[] input = day().split("\n\n");
    Map<String, Instruction> inst = Arrays.stream(input[1].split("\n")).map(s -> readString(s, "%s = (%s, %s)", Instruction.class)).collect(Collectors.toMap(e -> e.from, e -> e));
    return lcm(inst.keySet().stream().filter(e -> e.endsWith("A")).mapToLong(e -> walk(input, inst, e, "")).toArray());
  }

  private static int walk(String[] input, Map<String, Instruction> inst, String start, String end) {
    int i = 0;
    String current = start;
    while(true) {
      char dir = input[0].charAt(i % input[0].length());
      if(dir == 'L') {
        current = inst.get(current).left;
      } else {
        current = inst.get(current).right;
      }
      i++;
      if(end.isEmpty() ? current.endsWith("Z") : current.equals(end)) {
        return i;
      }
    }
  }
}
