package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.util.AOCUtils.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

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
    Pair<Long, Long> res = calculateRes(in).get();
    return doSum(((Sum)in.get("root")).op, res.a(), res.b());
  }

  @Override
  public Object part2() {
    Map<String, Object> in = input();
    long[] valid = LongStream.range(0, Long.MAX_VALUE).filter(i -> diff(in, new long[]{}, i) != Long.MAX_VALUE).limit(2).toArray();
    return (binarySearch(i -> diff(in, valid, i), valid[0], Long.MAX_VALUE/(valid[1]-valid[0])) * (valid[1]-valid[0])) + valid[0] /* need to do -2 because apparently solution is not unique :( */;
  }

  private long diff(Map<String, Object> in, long[] valid, long i) {
    if(valid.length == 2) {
      i = safeAdd(safeMultiply(i, valid[1]-valid[0]), valid[0]);
    }
    var newIn = new HashMap<>(in);
    newIn.put("humn", i);
    return calculateRes(newIn).map(d -> d.a() - d.b()).orElse(Long.MAX_VALUE);
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

  public Optional<Pair<Long, Long>> calculateRes(Map<String, Object> in) {
    while(true) {
      for (String s : new ArrayList<>(in.keySet())) {
        Object o = in.get(s);
        if(o instanceof Sum sum) {
          if(in.get(sum.monkey1) instanceof Long m1 && in.get(sum.monkey2) instanceof Long m2) {
            if(sum.op == '/' && m1 % m2 != 0) {
              return empty();
            } else if(s.equals("root")){
              return of(new Pair<>(m1, m2));
            }
            try {
              in.put(s, doSum(sum.op, m1, m2));
            } catch (IllegalStateException e) {
              return empty();
            }
          }
        }
      }
    }
  }

  private static Object doSum(char op, long m1, long m2) {
    return switch (op) {
      case '+' -> safeAdd(m1, m2);
      case '-' -> safeSubtract(m1, m2);
      case '/' -> m1 / m2;
      case '*' -> safeMultiply(m1, m2);
      default -> throw new IllegalStateException("Unexpected value: " + op);
    };
  }
}
