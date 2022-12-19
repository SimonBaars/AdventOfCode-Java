package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.TopElements;
import com.sbaars.adventofcode.year19.util.LongCountMap;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.util.AOCUtils.zip;
import static java.util.Comparator.comparing;

public class Day19 extends Day2022 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    System.out.println(new Day19().part2());
  }

  public record Blueprint(long n, List<Robot> robotList) {}
  public record Robot (String name, List<Cost> costs) {}
  public record Cost (long n, String name) {}
  public record Input(long n, long oreCost, long clayCost, long obsidianOre, long obsidianClay, long geodeOre, long geodeObsidian) {}
  public record State(LongCountMap<String> inventory, LongCountMap<String> perTurn, String target) {
    private long score() {
       return inventory.get("ore") + (inventory.get("clay") * 2) + (inventory.get("obsidian") * 3) + (inventory.get("geode") * 4);
    }

    private long score2() {
      return inventory.get("ore") + (inventory.get("clay") * 2) + (inventory.get("obsidian") * 2) + (inventory.get("geode") * 3);
    }
  }

  @Override
  public Object part1() {
    return zip(getQuality(Integer.MAX_VALUE, 24, 100000, State::score), LongStream.rangeClosed(1, 999).boxed(), Pair::new)
            .mapToLong(p -> p.a() * p.b())
            .sum();
  }

  @Override
  public Object part2() {
    return getQuality(3, 32,100000, State::score2)
            .mapToLong(e -> e)
            .reduce((a, b) -> a * b)
            .getAsLong();
  }

  private Stream<Long> getQuality(int limit, int minutes, int capacity, Function<State, Long> compare) {
    List<Input> l = dayStream().map(s -> readString(s, "Blueprint %n: Each ore robot costs %n ore. Each clay robot costs %n ore. Each obsidian robot costs %n ore and %n clay. Each geode robot costs %n ore and %n obsidian.", " ", Input.class)).limit(limit).toList();
    List<Long> quality = new ArrayList<>();
    for(Input b : l) {
      TopElements<State> states = new TopElements<>(capacity, comparing(compare));
      states.add(new State(new LongCountMap<>(), new LongCountMap<>(), "ore"));
      states.add(new State(new LongCountMap<>(), new LongCountMap<>(), "clay"));
      states.forEach(s -> s.perTurn.increment("ore"));

      for (int i = 0; i < minutes; i++) {
        for (State s : new ArrayList<>(states)) {
          LongCountMap<String> perTurn = new LongCountMap<>(s.perTurn);
//          boolean buildGeode = s.inventory.get("ore") >= b.obsidianOre && s.inventory.get("clay") >= b.obsidianClay;
//          boolean buildGeode = s.inventory.get("ore") >= b.obsidianOre && s.inventory.get("clay") >= b.obsidianClay;
          if (s.target.equals("ore") && s.inventory.get("ore") >= b.oreCost) {
            s.inventory.increment("ore", -b.oreCost);
            s.perTurn.increment("ore");
            perTurn.forEach(s.inventory::increment);
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "clay"));
          } else if (s.target.equals("clay") && s.inventory.get("ore") >= b.clayCost) {
            s.inventory.increment("ore", -b.clayCost);
            s.perTurn.increment("clay");
            perTurn.forEach(s.inventory::increment);
//            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "ore"));
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "obsidian"));
          } else if (s.target.equals("obsidian") && s.inventory.get("ore") >= b.obsidianOre && s.inventory.get("clay") >= b.obsidianClay) {
            s.inventory.increment("ore", -b.obsidianOre);
            s.inventory.increment("clay", -b.obsidianClay);
            s.perTurn.increment("obsidian");
            perTurn.forEach(s.inventory::increment);
//            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "ore"));
//            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "clay"));
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "geode"));
          } else if (s.target.equals("geode") && s.inventory.get("ore") >= b.geodeOre && s.inventory.get("obsidian") >= b.geodeObsidian) {
            s.inventory.increment("ore", -b.geodeOre);
            s.inventory.increment("obsidian", -b.geodeObsidian);
            s.perTurn.increment("geode");
            perTurn.forEach(s.inventory::increment);
//            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "ore"));
//            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "clay"));
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "obsidian"));
          } else {
            perTurn.forEach(s.inventory::increment);
          }


        }
        System.out.println("Minute "+(i+1)+": States "+states.size());
      }
      System.out.println("Blueprint "+b.n+" done, states="+states.size()+", maxGeodes="+states.stream().mapToLong(e -> e.inventory.get("geode")).max().getAsLong());
      quality.add(states.stream().mapToLong(e -> e.inventory.get("geode")).max().getAsLong());
    }
    return quality.stream();
  }
}
