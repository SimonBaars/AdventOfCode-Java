package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.map.ListMap;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.stream.IntStream.range;

public class Day15 extends Day2023 {
  public Day15() {
    super(15);
  }

  public static void main(String[] args) {
    new Day15().printParts();
  }

  @Override
  public Object part1() {
    return dayStream(",").map(String::strip).mapToLong(this::hash).sum();
  }

  private int hash(String s) {
    return s.chars().reduce(0, (acc, c) -> ((acc + c) * 17) % 256);
  }

  public record Lens(String label, boolean dash, int value) {
  }

  @Override
  public Object part2() {
    ListMap<Integer, Lens> lenses = new ListMap<>();
    dayStream(",")
        .map(String::strip)
        .map(s -> s.contains("-") ? new Lens(s.replace("-", ""), true, 0) : new Lens(s.split("=")[0], false, parseInt(s.split("=")[1])))
        .forEach(lens -> registerLenses(lens, lenses));
    return lenses
        .entrySet()
        .stream()
        .flatMapToLong(e -> range(0, e.getValue().size()).mapToLong(i -> (e.getKey() + 1L) * (i + 1L) * e.getValue().get(i).value))
        .sum();
  }

  private void registerLenses(Lens lens, ListMap<Integer, Lens> lensMap) {
    int hash = hash(lens.label);
    List<Lens> lenses = lensMap.get(hash);
    if (lens.dash) {
      lenses.removeIf(l -> l.label.equals(lens.label));
    } else {
      lenses
          .stream()
          .filter(l -> l.label.equals(lens.label))
          .findFirst()
          .ifPresentOrElse(
              l -> lenses.set(lenses.indexOf(l), lens),
              () -> lensMap.addTo(hash, lens)
          );
    }
  }
}
