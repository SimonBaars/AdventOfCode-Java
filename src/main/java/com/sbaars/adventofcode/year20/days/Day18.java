package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.HasRecursion;
import com.sbaars.adventofcode.year20.Day2020;
import org.apache.commons.lang3.tuple.Pair;

import static java.util.Arrays.stream;

public class Day18 extends Day2020 implements HasRecursion {
  public Day18() {
    super(18);
  }

  public static void main(String[] args) {
    new Day18().printParts();
  }

  @Override
  public Object part1() {
    return getSolution(true);
  }

  @Override
  public Object part2() {
    return getSolution(false);
  }

  public long resolveExpression(StringBuilder s, boolean part1) {
    var a = solve(s, part1);
    return getSolution(a.getRight(), s, a.getLeft(), part1);
  }

  private long getSolution(boolean part1) {
    return stream(dayStrings()).mapToLong(i -> resolveExpression(new StringBuilder(i), part1)).sum();
  }

  private Pair<Long, Integer> solve(StringBuilder s, boolean part1) {
    long leftHand;
    int i = s.length() - 2;
    if (s.charAt(s.length() - 1) == ')') {
      for (int nBrackets = 1; nBrackets > 0; i--) {
        if (s.charAt(i) == '(') nBrackets--;
        else if (s.charAt(i) == ')') nBrackets++;
      }
      i++;
      leftHand = resolveExpression(new StringBuilder(s.substring(i + 1, s.length() - 1)), part1);
    } else {
      leftHand = Long.parseLong(s.substring(s.length() - 1, s.length()));
      i = s.length() - 1;
    }
    return Pair.of(leftHand, i);
  }

  private long getSolution(int i, StringBuilder s, long leftHand, boolean part1) {
    if (i > 0) {
      char operator = s.charAt(i - 2);
      StringBuilder leftSide = new StringBuilder(s.substring(0, i - 3));
      if (operator == '*') {
        return resolveExpression(new StringBuilder(s.substring(0, i - 3)), part1) * leftHand;
      } else if (operator == '+') {
        if (part1) {
          return resolveExpression(new StringBuilder(s.substring(0, i - 3)), true) + leftHand;
        } else {
          var sol = solve(leftSide, false);
          return getSolution(sol.getRight(), leftSide, sol.getKey() + leftHand, false);
        }
      }
    } else if (i == 0) {
      return leftHand;
    }
    throw new IllegalStateException();
  }
}
