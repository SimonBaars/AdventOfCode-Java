package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day21 extends Day2022 {
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    Day21 d = new Day21();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Monkey (String name, String sum) {}
  public record Sum (String monkey1, char op, String monkey2) {}

  @Override
  public Object part1() {
    Map<String, Object> in = dayStream().map(s -> readString(s, "%s: %s", Monkey.class)).collect(Collectors.toMap(e -> e.name, e -> {
      try {
        return readString(e.sum, "%s %c %s", Sum.class);
      } catch (IllegalStateException s) {
        return Long.parseLong(e.sum);
      }
    }));
    while(in.get("root") instanceof Sum) {
      for (String s : new ArrayList<>(in.keySet())) {
        Object o = in.get(s);
        if(o instanceof Sum) {
          Sum sum = (Sum)o;
          if(in.get(sum.monkey1) instanceof Long && in.get(sum.monkey2) instanceof Long) {
            long m1 = (Long)in.get(sum.monkey1);
            long m2 = (Long)in.get(sum.monkey2);
            in.put(s, switch (sum.op) {
              case '+' -> m1+m2;
              case '-' -> m1-m2;
              case '/' -> m1/m2;
              case '*' -> m1*m2;
              default -> throw new IllegalStateException("Unexpected value: " + sum.op);
            });
          }
        }
      }
    }
    return in.get("root");
  }

  @Override
  public Object part2() {
    return "";
  }
}
