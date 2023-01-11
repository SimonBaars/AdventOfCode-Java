package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.ListMap;
import com.sbaars.adventofcode.year20.Day2020;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.Map.Entry.comparingByKey;

public class Day21 extends Day2020 {
  public Day21() {
    super(21);
  }

  public static void main(String[] args) {
    new Day21().printParts(1);
  }

  @Override
  public Object part1() {
    List<Rule> rules = input();
    ListMap<String, String> allergens = getAllergens(rules);
    return rules.stream().map(Rule::ingredients).flatMap(List::stream).filter(i -> !allergens.hasValue(i)).count();
  }

  private List<Rule> input() {
    return dayStream().map(s -> readString(s, "%ls (contains %ls)", Rule.class, " ", ", ")).toList();
  }

  private ListMap<String, String> getAllergens(List<Rule> input) {
    ListMap<String, String> allergens = new ListMap<>();

    Set<String> found = new HashSet<>();
    for (Rule r : input) {
      for (String allergen : r.allergens) {
        if (!found.contains(allergen)) {
          allergens.addTo(allergen, r.ingredients);
        } else if (allergens.containsKey(allergen)) {
          for (String s : new HashSet<>(allergens.get(allergen))) {
            if (!r.ingredients.contains(s)) {
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
    List<Rule> rules = input();
    ListMap<String, String> allergens = getAllergens(rules);
    return allergens.entrySet().stream().sorted(comparingByKey()).map(e -> e.getValue().stream().findAny().get()).collect(Collectors.joining(","));
  }

  public record Rule(List<String> ingredients, List<String> allergens) {}
}