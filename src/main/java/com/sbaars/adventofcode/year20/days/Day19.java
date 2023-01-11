package com.sbaars.adventofcode.year20.days;

import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;

import com.sbaars.adventofcode.common.SetMap;
import com.sbaars.adventofcode.year20.Day2020;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Day19 extends Day2020 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts(5);
  }

  private static Optional<String> getLetter(String rule) {
    return rule.startsWith("\"") ? Optional.of(rule.charAt(1) + "") : Optional.empty();
  }

  private static long[] getRule(String rule, boolean num) {
    if (rule.startsWith("\"")) {
      return new long[]{};
    }
    String[] r = rule.split(" \\| ");
    if (!num || r.length > 1) {
      return Arrays.stream(r[num ? 1 : 0].split(" ")).mapToLong(Long::parseLong).toArray();
    } else {
      return new long[]{};
    }
  }

  @Override
  public Object part1() {
    return getSolution(day());
  }

  private long getSolution(String inputFile) {
    SetMap<Long, String> sol = new SetMap<>();
    String[] input = inputFile.split("\n\n");
    Map<Long, Rule> rules = Arrays.stream(input[0].split("\n"))
        .map(e -> e.split(": "))
        .collect(Collectors.toMap(e -> parseLong(e[0]), e -> new Rule(parseLong(e[0]), e[1])));
    rules.values().forEach(e -> e.getPossibilities(rules, sol));
    return stream(input[1].split("\n")).filter(sol::hasValue).count();
  }

  @Override
  public Object part2() {
    return getSolution(day().replace("8: 42", "8: 42 | 42 8").replace("11: 42 31", "11: 42 31 | 42 11 31"));
  }

  public record Rule(long id, Optional<String> letter, long[] rule1, long[] rule2) {
    public Rule(long id, String rule) {
      this(id, getLetter(rule), getRule(rule, false), getRule(rule, true));
    }

    public Set<String> getPossibilities(Map<Long, Rule> m, SetMap<Long, String> sol) {
      System.out.println(sol.containsKey(id) ? sol.get(id).size() : 0);
      if (sol.containsKey(id)) return sol.get(id);
      if (letter.isEmpty()) {
        Rule[] r = stream(rule1).mapToObj(m::get).toArray(Rule[]::new);
        Rule[] orRule = stream(rule2).mapToObj(m::get).toArray(Rule[]::new);
        Set<String> output = r[0].getPossibilities(m, sol);
        if (sol.containsKey(id)) return sol.get(id);
        for (int i = 1; i < r.length; i++) {
          Set<String> output2 = r[i].getPossibilities(m, sol);
          if (sol.containsKey(id)) return sol.get(id);
          Set<String> newOne = new HashSet<>();
          for (String o : output) {
            for (String o2 : output2) {
              newOne.add(o + o2);
            }
          }
          output = newOne;
        }
        if (orRule.length > 0) {
          Set<String> outputOr = orRule[0].getPossibilities(m, sol);
          if (sol.containsKey(id)) return sol.get(id);
          for (int i = 1; i < orRule.length; i++) {
            Set<String> outputOr2 = orRule[i].getPossibilities(m, sol);
            if (sol.containsKey(id)) return sol.get(id);
            Set<String> newOne = new HashSet<>();
            for (String o : outputOr) {
              for (String o2 : outputOr2) {
                newOne.add(o + o2);
              }
            }
            outputOr = newOne;
          }
          output.addAll(outputOr);
        }
        sol.put(id, output);
        return output;
      }
      return new HashSet<>(singletonList(letter.get()));
    }
  }
}
