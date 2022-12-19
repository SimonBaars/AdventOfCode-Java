package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year19.util.LongCountMap;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day19 extends Day2022 {

  public Day19() {
    super(19);
  }

  public static void main(String[] args) {
    Day19 d = new Day19();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Blueprint(long n, List<Robot> robotList) {}
  public record Robot (String name, List<Cost> costs) {}
  public record Cost (long n, String name) {}
  public record Input(long n, long oreCost, long clayCost, long obsidianOre, long obsidianClay, long geodeOre, long geodeObsidian) {}
  public record State(LongCountMap<String> inventory, LongCountMap<String> perTurn, String target) {}

  @Override
  public Object part1() {
//    dayStream().map(s -> readString(s, "Blueprint %n: %l(Each %s robot costs %l(%n %s)).", " ", Blueprint.class, Robot.class, Cost.class)).toList();
    List<Input> l = dayStream().map(s -> readString(s, "Blueprint %n: Each ore robot costs %n ore. Each clay robot costs %n ore. Each obsidian robot costs %n ore and %n clay. Each geode robot costs %n ore and %n obsidian.", " ", Input.class)).toList();
    long quality = 0;
    for(Input b : l) {
      Set<State> states = new HashSet<>();
      states.add(new State(new LongCountMap<>(), new LongCountMap<>(), "ore"));
      states.add(new State(new LongCountMap<>(), new LongCountMap<>(), "clay"));
      states.forEach(s -> s.perTurn.increment("ore"));

      for (int i = 0; i < 24; i++) {
        for (State s : new ArrayList<>(states)) {
          LongCountMap<String> perTurn = new LongCountMap<>(s.perTurn);

          if (s.target.equals("ore") && s.inventory.get("ore") >= b.oreCost) {
            s.inventory.increment("ore", -b.oreCost);
            s.perTurn.increment("ore");
            perTurn.forEach(s.inventory::increment);
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "clay"));
          } else if (s.target.equals("clay") && s.inventory.get("ore") >= b.clayCost) {
            s.inventory.increment("ore", -b.clayCost);
            s.perTurn.increment("clay");
            perTurn.forEach(s.inventory::increment);
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "ore"));
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "obsidian"));
          } else if (s.target.equals("obsidian") && s.inventory.get("ore") >= b.obsidianOre && s.inventory.get("clay") >= b.obsidianClay) {
            s.inventory.increment("ore", -b.obsidianOre);
            s.inventory.increment("clay", -b.obsidianClay);
            s.perTurn.increment("obsidian");
            perTurn.forEach(s.inventory::increment);
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "ore"));
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "clay"));
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "geode"));
          } else if (s.target.equals("geode") && s.inventory.get("ore") >= b.geodeOre && s.inventory.get("obsidian") >= b.geodeObsidian) {
            s.inventory.increment("ore", -b.geodeOre);
            s.inventory.increment("obsidian", -b.geodeObsidian);
            s.perTurn.increment("geode");
            perTurn.forEach(s.inventory::increment);
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "ore"));
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "clay"));
            states.add(new State(new LongCountMap<>(s.inventory), new LongCountMap<>(s.perTurn), "obsidian"));
          } else {
            perTurn.forEach(s.inventory::increment);
          }


        }
        System.out.println("Minute "+(i+1)+": States "+states.size());
      }
      System.out.println("Blueprint "+b.n+" done, states="+states.size()+", maxGeodes="+states.stream().mapToLong(e -> e.inventory.get("geode")).max().getAsLong());
      quality += states.stream().mapToLong(e -> e.inventory.get("geode")).max().getAsLong() * b.n;
    }
    return quality;
  }

  @Override
  public Object part2() {
    return "";
  }
}
