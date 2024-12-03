package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day3 extends Day2024 {
  public Day3() {
    super(3);
  }

  public static void main(String[] args) {
    new Day3().printParts();
  }

  @Override
  public Object part1() {
    return solve(day(), true);
  }

  @Override
  public Object part2() {
    return solve(day(), false);
  }

  private int solve(String input, boolean ignoreConditions) {
    Pattern pattern = Pattern.compile("(mul\\((\\d+),(\\d+)\\))|(do\\(\\))|(don't\\(\\))");
    Matcher matcher = pattern.matcher(input);
    AtomicBoolean isEnabled = new AtomicBoolean(true);

    return matcher.results()
        .mapToInt(result -> {
          if (result.group(1) != null && (ignoreConditions || isEnabled.get())) {
            return parseInt(result.group(2)) * parseInt(result.group(3));
          } else if (!ignoreConditions) {
            if (result.group(4) != null) {
              isEnabled.set(true);
            } else if (result.group(5) != null) {
              isEnabled.set(false);
            }
          }
          return 0;
        })
        .sum();
  }
}
