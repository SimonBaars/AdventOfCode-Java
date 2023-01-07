package com.sbaars.adventofcode.year20.days;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sbaars.adventofcode.common.ListMap;
import com.sbaars.adventofcode.year20.Day2020;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Map.Entry.comparingByKey;

public class Day21 extends Day2020 {
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts(1);
  }

  private static String[] getSplit(String s) {
    return s.substring(0, s.length() - 1).split(" \\(contains ");
  }

  @Override
  public Object part1() {
    Rule[] input = Arrays.stream(day().split("\n")).map(Rule::new).toArray(Rule[]::new);
    ListMap<String, String> allergens = getAllergens(input);
    return Arrays.stream(input).flatMap(r -> Arrays.stream(r.ingredients)).filter(i -> !allergens.hasValue(i)).count();
  }

  private ListMap<String, String> getAllergens(Rule[] input) {
    ListMap<String, String> allergens = new ListMap<>();
    Set<String> found = new HashSet<>();
    for (Rule r : input) {
      for (String allergen : r.allergens) {
        if (!found.contains(allergen)) {
          allergens.addTo(allergen, asList(r.ingredients));
        } else if (allergens.containsKey(allergen)) {
          for (String s : new HashSet<>(allergens.get(allergen))) {
            if (!asList(r.ingredients).contains(s)) {
              allergens.removeFrom(allergen, s);
            }
          }
        }
        found.add(allergen);
      }
    }
    return allergens;
  }

  @Override
  public Object part2() {
    Rule[] input = Arrays.stream(day().split("\n")).map(Rule::new).toArray(Rule[]::new);
    ListMap<String, String> allergens = getAllergens(input);
    return allergens.entrySet().stream().sorted(comparingByKey()).map(e -> e.getValue().stream().findAny().get()).collect(Collectors.joining(","));
  }

  public static record Rule(String[] ingredients, String[] allergens) {
    public Rule(String s) {
      this(getSplit(s)[0].split(" "), getSplit(s)[1].split(", "));
    }
  }
}
