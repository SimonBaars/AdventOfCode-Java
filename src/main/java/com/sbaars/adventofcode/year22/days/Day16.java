package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day16 extends Day2022 {

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    Day d = new Day16();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts(1);
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Valve(String name, long flow, String others) {}
  public record State(Map<String, Long> open, List<String> path, int mins, int index) {}

  @Override
  public Object part1() {
    List<Valve> in = dayStream().map(s -> {
      try {
        return readString(s, "Valve %s has flow rate=%n; tunnels lead to valves %s", Valve.class);
      } catch (IllegalStateException e) {
        return readString(s, "Valve %s has flow rate=%n; tunnel leads to valve %s", Valve.class);
      }
    }).toList();
    Map<String, Integer> indices = IntStream.range(0, in.size()).boxed().collect(Collectors.toMap(i -> this.in.get(i).name, i -> i));
    List<State> states =
    return walk(0, 0, new HashMap<>(), new HashSet<>());
  }

  private long walk(int mins, int index, Map<String, Long> open, Set<String> visited) {
    long flow = open.values().stream().mapToLong(e -> e).sum();
    if(mins == 30){
//      System.out.println(Arrays.toString(open.keySet().toArray()));
      return flow;
    }
    Valve v = in.get(index);
//    if(!visited.add(v.name)) return 0;
    List<Long> options = new ArrayList<>();
    if(v.flow > 0 && !open.containsKey(v.name)) {
      open.put(v.name, v.flow);
      options.add(walk(mins + 1, index, new HashMap<>(open), new HashSet<>(visited)));
    }
    return flow + LongStream.concat(options.stream().mapToLong(e -> e), Arrays.stream(v.others.split(", ")).mapToLong(s -> walk(mins + 1, indices.get(s), new HashMap<>(open), new HashSet<>(visited)))).max().orElse(0);
  }

  @Override
  public Object part2() {
    return "";
  }
}
