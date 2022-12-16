package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;
import static com.sbaars.adventofcode.util.AOCUtils.allPairs;

public class Day16 extends Day2022 {

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    Day d = new Day16();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts();
//    System.in.read();
    d.submitPart1();
//    d.submitPart2();
  }

  public record Valve(String name, long flow, String others) {}
  public record State(Map<String, Long> open, int index, long totalFlow) {}
  public record State2(Map<String, Long> open, int myIndex, int elephantIndex, long totalFlow) {}

  @Override
  public Object part1() {
    List<Valve> in = dayStream().map(s -> {
      try {
        return readString(s, "Valve %s has flow rate=%n; tunnels lead to valves %s", Valve.class);
      } catch (IllegalStateException e) {
        return readString(s, "Valve %s has flow rate=%n; tunnel leads to valve %s", Valve.class);
      }
    }).toList();
    Map<String, Integer> indices = IntStream.range(0, in.size()).boxed().collect(Collectors.toMap(i -> in.get(i).name, i -> i));
    Set<State> states = new HashSet<>();
    states.add(new State(new HashMap<>(), 0, 0));
    for(int minutes = 0; minutes<30; minutes++) {
      Set<State> newStates = new HashSet<>();
      for(State s : states) {
        Valve v = in.get(s.index);
        long flow = s.open.values().stream().mapToLong(e -> e).sum() + s.totalFlow;
        if(v.flow > 0 && !s.open.containsKey(v.name)) {
          Map<String, Long> newOpen = new HashMap<>(s.open);
          newOpen.put(v.name, v.flow);
          newStates.add(new State(newOpen, s.index, flow));
        }
        Arrays.stream(v.others.split(", ")).forEach(name -> newStates.add(new State(s.open, indices.get(name), flow)));
      }
      states = newStates;
    }
    return states.stream().mapToLong(State::totalFlow).max().getAsLong();
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
    Map<String, Integer> indices = IntStream.range(0, in.size()).boxed().collect(Collectors.toMap(i -> in.get(i).name, i -> i));
    Set<State2> states = new HashSet<>();
    states.add(new State2(new HashMap<>(), 0, 0, 0));
    for(int minutes = 0; minutes<30; minutes++) {
      Set<State2> newStates = new HashSet<>();
      for(State2 s : states) {

        Valve myValve = in.get(s.myIndex);
        Valve eleValve = in.get(s.elephantIndex);
        long flow = s.open.values().stream().mapToLong(e -> e).sum() + s.totalFlow;
        if(s.open.keySet().equals(openable)) { // All valves are open, time to chill
          newStates.add(new State2(s.open, s.myIndex, s.elephantIndex, flow));
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
    }
    return states.stream().mapToLong(State2::totalFlow).max().getAsLong();
  }
}
