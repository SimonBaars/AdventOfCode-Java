package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.util.AOCUtils.binarySearch;

public class Day21 extends Day2022 {
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts();
  }

  public record Monkey (String name, String sum) {}
  public record Sum (String monkey1, char op, String monkey2) {}

  @Override
  public Object part1() {
    Map<String, Object> in = input();
    Pair<Long, Long> res = calculateRes(in);
    return doSum(((Sum)in.get("root")).op, res.a(), res.b());
  }

  @Override
  public Object part2() {
    Map<String, Object> in = input();
    return binarySearch(i -> {
      var newIn = new HashMap<>(in);
      newIn.put("humn", i);
      var res = calculateRes(newIn);
      return res.a() - res.b();
    }, 0, 10000000000000L)-2 /* need to do -2 because apparently solution is not unique :( */;
  }

  private Map<String, Object> input() {
    return dayStream().map(s -> readString(s, "%s: %s", Monkey.class)).collect(Collectors.toMap(e -> e.name, e -> {
      try {
        return readString(e.sum, "%s %c %s", Sum.class);
      } catch (IllegalStateException s) {
        return Long.parseLong(e.sum);
      }
    }));
  }

  public Pair<Long, Long> calculateRes(Map<String, Object> in) {
    while(true) {
      for (String s : new ArrayList<>(in.keySet())) {
        Object o = in.get(s);
        if(o instanceof Sum sum) {
          if(in.get(sum.monkey1) instanceof Long m1 && in.get(sum.monkey2) instanceof Long m2) {
            if(s.equals("root")){
              return new Pair<>(m1, m2);
            }
            in.put(s, doSum(sum.op, m1, m2));
          }
        }
      }
    }
  }

  private static Object doSum(char op, Long m1, Long m2) {
    return switch (op) {
      case '+' -> m1 + m2;
      case '-' -> m1 - m2;
      case '/' -> m1 / m2;
      case '*' -> m1 * m2;
      default -> throw new IllegalStateException("Unexpected value: " + op);
    };
  }
}
