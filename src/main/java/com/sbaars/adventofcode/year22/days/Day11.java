package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year19.util.LongCountMap;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day11 extends Day2022 {
  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  public record Monkey(long n, String items, char op, String add, long divisible, long ifTrue, long ifFalse) {}

  @Override
  public Object part1() {
    return solution(20, true);
  }

  private long solution(int cycles, boolean decreasingWorry) {
    List<Monkey> monkeys = Arrays.stream(day().split("\n\n")).map(String::trim).map(s -> readString(s, "Monkey %n:\n" +
            "  Starting items: %s\n" +
            "  Operation: new = old %c %s\n" +
            "  Test: divisible by %n\n" +
            "    If true: throw to monkey %n\n" +
            "    If false: throw to monkey %n", Monkey.class)).toList();
    Map<Long, List<Long>> items = monkeys.stream().collect(Collectors.toMap(m -> m.n, m -> Arrays.stream(m.items.split(", ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayList::new))));
    LongCountMap<Long> times = new LongCountMap<>();
    long gcd = monkeys.stream().mapToLong(e -> e.divisible).reduce((a,b) -> a*b).getAsLong();
    for(int i = 0; i<cycles; i++) {
      for(Monkey m : monkeys) {
        while(!items.get(m.n).isEmpty()) {
          long item = items.get(m.n).remove(0);
          long worryLevel = applyOperator(item, m.op, m.add) / (decreasingWorry ? 3 : 1);
          boolean test = worryLevel % m.divisible == 0;
          items.get(test ? m.ifTrue : m.ifFalse).add(worryLevel % gcd);
          times.increment(m.n);
        }
      }
    }
    long[] sorted = times.values().stream().mapToLong(e -> e).sorted().toArray();
    return sorted[sorted.length-1] * sorted[sorted.length-2];
  }

  private long applyOperator(long item, char op, String add) {
    long itemValue = add.equals("old") ? item : Long.parseLong(add);
    return switch (op) {
      case '*' -> item * itemValue;
      case '+' -> item + itemValue;
      default -> throw new IllegalStateException();
    };
  }

  @Override
  public Object part2() {
    return solution(10000, false);
  }
}
