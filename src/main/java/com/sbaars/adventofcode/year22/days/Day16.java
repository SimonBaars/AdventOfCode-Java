package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.util.AOCUtils.allPairs;

public class Day16 extends Day2022 {

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    new Day16().printParts();
  }

  public record Valve(String name, long flow, String others) {}
  public record State(Map<String, Long> open, Valve valve, long totalFlow) {}
  public record State2(Map<String, Long> open, Valve me, Valve elephant, long totalFlow) {}

  @Override
  public Object part1() {
    Map<String, Valve> valves = input();
    Set<State> states = new HashSet<>();
    states.add(new State(new HashMap<>(), valves.get("AA"), 0));
    for(int minutes = 0; minutes<30; minutes++) {
      Set<State> newStates = new HashSet<>();
      for(State s : states) {
        long flow = s.open.values().stream().mapToLong(e -> e).sum() + s.totalFlow;
        if(s.valve.flow > 0 && !s.open.containsKey(s.valve.name)) {
          Map<String, Long> newOpen = new HashMap<>(s.open);
          newOpen.put(s.valve.name, s.valve.flow);
          newStates.add(new State(newOpen, s.valve, flow));
        }
        Arrays.stream(s.valve.others.split(", ")).forEach(name -> newStates.add(new State(s.open, valves.get(name), flow)));
      }
      states = newStates;
    }
    return states.stream().mapToLong(State::totalFlow).max().getAsLong();
  }

  private Map<String, Valve> input() {
    return dayStream().map(s -> {
      try {
        return readString(s, "Valve %s has flow rate=%n; tunnels lead to valves %s", Valve.class);
      } catch (IllegalStateException e) {
        return readString(s, "Valve %s has flow rate=%n; tunnel leads to valve %s", Valve.class);
      }
    }).collect(Collectors.toMap(v -> v.name, v -> v));
  }

  @Override
  public Object part2() {
    Map<String, Valve> valves = input();
    Set<String> openable = valves.values().stream().filter(s -> s.flow > 0).map(Valve::name).collect(Collectors.toSet());
    Set<State2> states = new HashSet<>();
    states.add(new State2(new HashMap<>(), valves.get("AA"), valves.get("AA"), 0));
    Map<Integer, Long> kpis = Map.of(5, 25L, 10, 50L, 15, 100L, 20, 140L, 25, 160L);
    for(int minutes = 0; minutes<26; minutes++) {
      Set<State2> newStates = new HashSet<>();
      for(State2 s : states) {
        long flow = s.open.values().stream().mapToLong(e -> e).sum() + s.totalFlow;
        if(s.open.size() == openable.size()) { // All valves are open, time to chill
          newStates.add(new State2(s.open, valves.get("AA"), valves.get("AA"), flow));
        }
        boolean iCanOpen = s.me.flow > 0 && !s.open.containsKey(s.me.name);
        boolean elefriendCanOpen = s.elephant.flow > 0 && !s.open.containsKey(s.elephant.name);
        if(iCanOpen || elefriendCanOpen) {
          Map<String, Long> newOpen = new HashMap<>(s.open);
          if(iCanOpen) newOpen.put(s.me.name, s.me.flow);
          if(elefriendCanOpen) newOpen.put(s.elephant.name, s.elephant.flow);
          if(iCanOpen) Arrays.stream(s.elephant.others.split(", ")).forEach(name -> newStates.add(new State2(newOpen, s.me, valves.get(name), flow)));
          else if(elefriendCanOpen) Arrays.stream(s.me.others.split(", ")).forEach(name -> newStates.add(new State2(newOpen, valves.get(name), s.elephant, flow)));
          else newStates.add(new State2(newOpen, s.me, s.elephant, flow));
        } else allPairs(List.of(s.me.others.split(", ")), List.of(s.elephant.others.split(", "))).forEach(p -> newStates.add(new State2(s.open, valves.get(p.a()), valves.get(p.b()), flow)));
      }
      states = newStates;
      if(kpis.containsKey(minutes)){
        long kpi = kpis.get(minutes);
        states = states.stream().filter(e -> e.open.values().stream().mapToLong(f -> f).sum()>=kpi).collect(Collectors.toSet());
      }
    }
    return states.stream().mapToLong(State2::totalFlow).max().getAsLong();
  }
}
