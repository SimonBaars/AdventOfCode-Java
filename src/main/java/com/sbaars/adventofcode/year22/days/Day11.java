package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.map.LongCountMap;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.List;

import static com.sbaars.adventofcode.util.DataMapper.readString;

public class Day11 extends Day2022 {
  public Day11() {
    super(11);
  }

  public static void main(String[] args) {
    new Day11().printParts();
  }

  public record Monkey(int n, List<Long> items, char op, String add, long divisible, int ifTrue, int ifFalse) {
  }

  @Override
  public Object part1() {
    return solution(20, true);
  }

  private long solution(int cycles, boolean decreasingWorry) {
    List<Monkey> monkeys = dayStream("\n\n").map(String::trim).map(s -> readString(s, """
        Monkey %i:
          Starting items: %ln
          Operation: new = old %c %s
          Test: divisible by %n
            If true: throw to monkey %i
            If false: throw to monkey %i""", Monkey.class)).toList();
    LongCountMap<Integer> times = new LongCountMap<>();
    long gcd = monkeys.stream().mapToLong(e -> e.divisible).reduce((a, b) -> a * b).getAsLong();
    for (int i = 0; i < cycles; i++) {
      for (Monkey m : monkeys) {
        while (!m.items.isEmpty()) {
          long item = m.items.remove(0);
          long worryLevel = applyOperator(item, m.op, m.add) / (decreasingWorry ? 3 : 1);
          boolean test = worryLevel % m.divisible == 0;
          monkeys.get(test ? m.ifTrue : m.ifFalse).items.add(worryLevel % gcd);
          times.increment(m.n);
        }
      }
    }
    long[] sorted = times.values().stream().mapToLong(e -> e).sorted().toArray();
    return sorted[sorted.length - 1] * sorted[sorted.length - 2];
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
