package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.stream.Collectors;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.util.AOCUtils.allPairs;
import static java.util.stream.IntStream.range;

public class Day16 extends Day2022 {

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    new Day16().printParts();
  }

  public record Valve(String name, long flow, String others) {}
  public record State(Map<String, Long> open, Valve valve, long totalFlow) {}
  public record State2(Map<String, Long> open, int myIndex, int elephantIndex, long totalFlow) {}

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
    List<Valve> in = dayStream().map(s -> {
      try {
        return readString(s, "Valve %s has flow rate=%n; tunnels lead to valves %s", Valve.class);
      } catch (IllegalStateException e) {
        return readString(s, "Valve %s has flow rate=%n; tunnel leads to valve %s", Valve.class);
      }
    }).toList();
    Set<String> openable = in.stream().filter(s -> s.flow > 0).map(Valve::name).collect(Collectors.toSet());
    Map<String, Integer> indices = range(0, in.size()).boxed().collect(Collectors.toMap(i -> in.get(i).name, i -> i));
    Set<State2> states = new HashSet<>();
    states.add(new State2(new HashMap<>(), range(0, in.size()).filter(i -> in.get(i).name.equals("AA")).findFirst().getAsInt(), range(0, in.size()).filter(i -> in.get(i).name.equals("AA")).findFirst().getAsInt(), 0));
//    Map<Integer, Long> kpis = Map.of(5, 41L, 10, 78L, 15, 81L, 20, 81L, 25, 81L); // Example KPIs
    Map<Integer, Long> kpis = Map.of(5, 25L, 10, 50L, 15, 100L, 20, 140L, 25, 160L);
    for(int minutes = 0; minutes<26; minutes++) {
      Set<State2> newStates = new HashSet<>();
      for(State2 s : states) {

        Valve myValve = in.get(s.myIndex);
        Valve eleValve = in.get(s.elephantIndex);
        long flow = s.open.values().stream().mapToLong(e -> e).sum() + s.totalFlow;
        if(s.open.size() == openable.size()) { // All valves are open, time to chill
          newStates.add(new State2(s.open, 0, 0, flow));
        }
        boolean couldOpen = false;
        if(myValve.flow > 0 && !s.open.containsKey(myValve.name)) {
          Map<String, Long> newOpen = new HashMap<>(s.open);
          newOpen.put(myValve.name, myValve.flow);
          Arrays.stream(eleValve.others.split(", ")).forEach(name -> newStates.add(new State2(newOpen, s.myIndex, indices.get(name), flow)));
          couldOpen = true;
        }
        if(eleValve.flow > 0 && !s.open.containsKey(eleValve.name)) {
          Map<String, Long> newOpen = new HashMap<>(s.open);
          newOpen.put(eleValve.name, eleValve.flow);
          Arrays.stream(myValve.others.split(", ")).forEach(name -> newStates.add(new State2(newOpen, indices.get(name), s.elephantIndex, flow)));
          couldOpen = true;
        }
        if(myValve.flow > 0 && !s.open.containsKey(myValve.name) && eleValve.flow > 0 && !s.open.containsKey(eleValve.name)) {
          Map<String, Long> newOpen = new HashMap<>(s.open);
          newOpen.put(myValve.name, myValve.flow);
          newOpen.put(eleValve.name, eleValve.flow);
          newStates.add(new State2(newOpen, s.myIndex, s.elephantIndex, flow));
          couldOpen = true;
        }
        if(!couldOpen) allPairs(List.of(myValve.others.split(", ")), List.of(eleValve.others.split(", "))).forEach(p -> newStates.add(new State2(s.open, indices.get(p.a()), indices.get(p.b()), flow)));
      }
      states = newStates;
      if(kpis.containsKey(minutes)){
        long kpi = kpis.get(minutes);
        states = states.stream().filter(e -> e.open.values().stream().mapToLong(f -> f).sum()>=kpi).collect(Collectors.toSet());
        System.out.println("Apply KPI");
      }
      System.out.println(minutes+", "+states.size());
    }
    for(State2 s : states) {
      if(s.totalFlow == 1706) {
        System.out.println(s);
      }
    }
    return states.stream().mapToLong(State2::totalFlow).max().getAsLong();
  }
}
