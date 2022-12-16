package com.sbaars.adventofcode.year22.days;

import com.sbaars.adventofcode.common.Day;
import com.sbaars.adventofcode.year22.Day2022;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sbaars.adventofcode.common.ReadsFormattedString.readString;

public class Day16 extends Day2022 {

  public Day16() {
    super(16);
  }

  public static void main(String[] args) {
    Day d = new Day16();
    d.downloadIfNotDownloaded();
    d.downloadExample();
    d.printParts(0);
//    System.in.read();
//    d.submitPart1();
//    d.submitPart2();
  }

  public record Valve(String name, long flow, String others) {}

  public static final class State {
    private final Map<String, Long> open;
    private final int index;
    private final long totalFlow;
    private final List<String> path;

    public State(Map<String, Long> open, int index, long totalFlow, List<String> path) {
      this.open = open;
      this.index = index;
      this.totalFlow = totalFlow;
      this.path = path;
    }

    public Map<String, Long> open() {
      return open;
    }

    public int index() {
      return index;
    }

    public long totalFlow() {
      return totalFlow;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj == null || obj.getClass() != this.getClass()) return false;
      var that = (State) obj;
      return Objects.equals(this.open, that.open) &&
              this.index == that.index &&
              this.totalFlow == that.totalFlow;
    }

    @Override
    public int hashCode() {
      return Objects.hash(open, index, totalFlow);
    }

    @Override
    public String toString() {
      return "State[" +
              "open=" + open + ", " +
              "index=" + index + ", " +
              "totalFlow=" + totalFlow + ']';
    }
  }

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
    states.add(new State(new HashMap<>(), 0, 0, new ArrayList<>()));
    for(int minutes = 0; minutes<31; minutes++) {
      Set<State> newStates = new HashSet<>();
      for(State s : states) {
        Valve v = in.get(s.index);
        long flow = s.open.values().stream().mapToLong(e -> e).sum() + s.totalFlow;
        List<String> newPath = new ArrayList<>(s.path);
        newPath.add(v.name);
        if(v.flow > 0 && !s.open.containsKey(v.name)) {
          Map<String, Long> newOpen = new HashMap<>(s.open);
          newOpen.put(v.name, v.flow);
          newStates.add(new State(newOpen, s.index, flow, newPath));
        }
        Arrays.stream(v.others.split(", ")).forEach(name -> newStates.add(new State(s.open, indices.get(name), flow, newPath)));
      }
      states = newStates;
    }
    for(State s : states){
      if(s.totalFlow == 1906) {
        System.out.println(s);
        System.out.println(Arrays.toString(s.path.toArray()));
      }
    }
    return states.stream().mapToLong(State::totalFlow).max().getAsLong();
  }

  @Override
  public Object part2() {
    return "";
  }
}
