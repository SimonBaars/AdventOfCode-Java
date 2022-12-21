package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.TopUniqueElements;
import com.sbaars.adventofcode.year19.util.LongCountMap;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.util.DataMapper.readString;
import static com.sbaars.adventofcode.util.AOCUtils.zip;
import static java.util.Comparator.comparing;
import static java.util.stream.LongStream.rangeClosed;

public class Day19 extends Day2022 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    new Day19().printParts();
  }

  public record Input(long n, long oreCost, long clayCost, long obsidianOre, long obsidianClay, long geodeOre, long geodeObsidian) {}
  public record State(LongCountMap<String> inventory, LongCountMap<String> perTurn, String target) {}

  @Override
  public Object part1() {
    return zip(getQuality(Integer.MAX_VALUE, 24, 100000), rangeClosed(1, 999).boxed(), Pair::new)
            .mapToLong(p -> p.a() * p.b())
            .sum();
  }

  @Override
  public Object part2() {
    return getQuality(3, 32, 500000)
            .mapToLong(e -> e)
            .reduce((a, b) -> a * b)
            .getAsLong();
  }

  private Stream<Long> getQuality(int limit, int minutes, int capacity) {
    List<Input> l = dayStream().map(s -> readString(s, "Blueprint %n: Each ore robot costs %n ore. Each clay robot costs %n ore. Each obsidian robot costs %n ore and %n clay. Each geode robot costs %n ore and %n obsidian.", " ", Input.class)).limit(limit).toList();
    return l.parallelStream().map(b -> {
      Collection<State> states = getStates(new LongCountMap<>(), new LongCountMap<>(), "ore", "clay")
              .peek(s -> s.perTurn.increment("ore"))
              .toList();
      for (int i = 0; i < minutes; i++) {
        TopUniqueElements<State> newStates = new TopUniqueElements<>(capacity, comparing(state -> state.inventory.sumValues()));
        for (State s : states) {
          LongCountMap<String> perTurn = new LongCountMap<>(s.perTurn);
          boolean buildGeode = s.inventory.get("ore") >= b.geodeOre && s.inventory.get("obsidian") >= b.geodeObsidian;
          boolean buildObsidian = s.inventory.get("ore") >= b.obsidianOre && s.inventory.get("clay") >= b.obsidianClay;
          boolean buildOre = s.target.equals("ore") && s.inventory.get("ore") >= b.oreCost;
          boolean buildClay = s.target.equals("clay") && s.inventory.get("ore") >= b.clayCost;
          perTurn.forEach(s.inventory::increment);
          if (!buildGeode && !buildObsidian && buildOre) {
            s.inventory.increment("ore", -b.oreCost);
            s.perTurn.increment("ore");
            getStates(s.inventory, s.perTurn, "ore", "clay").forEach(newStates::add);
          } else if (!buildGeode && !buildObsidian && buildClay) {
            s.inventory.increment("ore", -b.clayCost);
            s.perTurn.increment("clay");
            getStates(s.inventory, s.perTurn, "ore", "clay", "obsidian").forEach(newStates::add);
          } else if (!buildGeode && buildObsidian) {
            s.inventory.increment("ore", -b.obsidianOre);
            s.inventory.increment("clay", -b.obsidianClay);
            s.perTurn.increment("obsidian");
            getStates(s.inventory, s.perTurn, "ore", "clay", "obsidian", "geode").forEach(newStates::add);
          } else if (buildGeode) {
            s.inventory.increment("ore", -b.geodeOre);
            s.inventory.increment("obsidian", -b.geodeObsidian);
            s.perTurn.increment("geode");
            getStates(s.inventory, s.perTurn, "ore", "clay", "obsidian", "geode").forEach(newStates::add);
          } else {
            newStates.add(s);
          }

        }
        states = newStates;
      }
      return states.stream().mapToLong(e -> e.inventory.get("geode")).max().orElse(0L);
    });
  }

  private static Stream<State> getStates(LongCountMap<String> inventory, LongCountMap<String> perTurn, String...ores) {
    return Arrays.stream(ores).map(ore -> new State(new LongCountMap<>(inventory), new LongCountMap<>(perTurn), ore));
  }
}
