package com.sbaars.adventofcode.year20.days;

import static java.lang.Long.parseLong;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonList;

import aima.core.nlp.parsing.CYK;
import aima.core.nlp.parsing.grammars.ProbCNFGrammar;
import aima.core.nlp.parsing.grammars.Rule;
import com.sbaars.adventofcode.common.SetMap;
import com.sbaars.adventofcode.year20.Day2020;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    ProbCNFGrammar grammar = new ProbCNFGrammar();
    Arrays.stream(input[0].split("\n"))
            .map(e -> e.split(": "))
            .flatMap(e -> e[1].contains("|") ? Stream.of(parseRule(e[0], e[1].split(" \\| ")[0]), parseRule(e[0], e[1].split(" \\| ")[1])) : Stream.of(parseRule(e[0], e[1])))
            .forEach(grammar::addRule);
    float[][][] sol2 = new CYK().parse(stream(input[1].split("\n")).toList(), grammar);
    return stream(input[1].split("\n")).filter(sol::hasValue).count();
  }

  public Rule parseRule(String s1, String s2) {
    System.out.println("parseRule("+s1+", "+s2+")");
    return new Rule(List.of(numToString(s1)), stream(s2.split(" ")).map(s -> s.contains("\"") ? s.replace("\"", "") : numToString(s)).toList(), 0.5F);
  }

  public String numToString(String num) {
    return num.chars().mapToObj(c -> ""+((char)(c-'0'+65))).collect(Collectors.joining());
  }

  @Override
  public Object part2() {
    return getSolution(day().replace("8: 42", "8: 42 | 42 8").replace("11: 42 31", "11: 42 31 | 42 11 31"));
  }
}
