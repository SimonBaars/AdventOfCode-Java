package com.sbaars.adventofcode.year20.days;

import com.sbaars.adventofcode.common.ListMap;
import com.sbaars.adventofcode.common.SetMap;
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
    SetMap<String, String> allergens = getAllergens(rules);
    return rules.stream().map(Rule::ingredients).flatMap(List::stream).filter(i -> allergens.get(i).isEmpty()).count();
  }

  private List<Rule> input() {
    return dayStream().map(s -> readString(s, "%ls (contains %ls)", Rule.class, " ", ", ")).toList();
  }

  private SetMap<String, String> getAllergens(List<Rule> input) {
    SetMap<String, String> allergens = new SetMap<>();
    Set<String> allAllergens = input.stream().flatMap(r -> r.allergens.stream()).collect(Collectors.toSet());;
    input.stream().flatMap(r -> r.ingredients.stream()).distinct().forEach(i -> allergens.addTo(i, allAllergens));
    allergens.forEach((i, a) -> input.stream().filter(r -> r.ingredients.contains(i)).forEach(r -> a.retainAll(r.allergens)));
    return allergens;
  }

  @Override
  public Object part2() {
    List<Rule> rules = input();
    SetMap<String, String> allergens = getAllergens(rules);
    return allergens.entrySet().stream().sorted(comparingByKey()).map(e -> e.getValue().stream().findAny().get()).collect(Collectors.joining(","));
  }

  public record Rule(List<String> ingredients, List<String> allergens) {}
}
