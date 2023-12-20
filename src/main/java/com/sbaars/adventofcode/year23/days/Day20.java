package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.range;

public class Day20 extends Day2023 {
  public Day20() {
    super(20);
  }

  public static void main(String[] args) {
    new Day20().printParts();
  }

  public enum Type {BROADCAST, FLIPFLOP, CONJUNCTION}

  public record Module(Type t, String source, List<String> targets) {
    public Module(String in) {
      this(switch (in.charAt(0)) {
            case '%' -> Type.FLIPFLOP;
            case '&' -> Type.CONJUNCTION;
            default -> Type.BROADCAST;
          },
          in.split(" -> ")[0].replaceAll("[&%]", ""),
          Arrays.asList(in.split(" -> ")[1].split(", ")));
    }
  }

  public record Signal(String from, String to, boolean isHigh) {
  }

  @Override
  public Object part1() {
//    this.example = 3;
    var in = dayStrings();
    var modules = Arrays.stream(in).map(Module::new).collect(toMap(m -> m.source, m -> m));
    var flipflopState = modules.keySet().stream().filter(m -> modules.get(m).t == Type.FLIPFLOP).collect(toMap(k -> k, k -> false));
    var conjunctionState = modules.keySet().stream().filter(m -> modules.get(m).t == Type.CONJUNCTION).collect(toMap(k -> k, k -> modules.entrySet().stream().filter(e -> e.getValue().targets.contains(k)).map(Map.Entry::getKey).collect(toMap(k2 -> k2, k2 -> false))));
    return range(0, 1000).mapToObj(i -> sendPulse(modules, flipflopState, conjunctionState)).reduce(new Pair<>(0L, 0L), (a, b) -> new Pair<>(a.a() + b.a(), a.b() + b.b())).map((a, b) -> a * b);
  }

  private Pair<Long, Long> sendPulse(Map<String, Module> modules, Map<String, Boolean> state, Map<String, Map<String, Boolean>> conjunctionState) {
    Pair<Long, Long> out = new Pair<>(0L, 0L);
    var pulseQueue = new LinkedList<Signal>();
    pulseQueue.add(new Signal("", "broadcaster", false));
    while (!pulseQueue.isEmpty()) {
      var pulse = pulseQueue.pop();
      var isHigh = pulse.isHigh();
      out = out.map((a, b) -> new Pair<>(a + (isHigh ? 0 : 1), b + (isHigh ? 1 : 0)));
      if (pulse.to.equals("rx")) {
        if (!isHigh) {
          System.out.println("Received signal: " + pulse.from + " " + pulse.isHigh());
          return new Pair<>(-1L, -1L);
        }
        continue;
      }
      var module = modules.get(pulse.to);
      if (module.t == Type.BROADCAST) {
        module.targets.forEach(t -> pulseQueue.add(new Signal(pulse.to, t, isHigh)));
      } else if (module.t == Type.CONJUNCTION) {
        conjunctionState.get(pulse.to).put(pulse.from, isHigh);
        if (conjunctionState.get(pulse.to).values().stream().allMatch(b -> b)) {
          module.targets.forEach(t -> pulseQueue.add(new Signal(pulse.to, t, false)));
        } else {
          module.targets.forEach(t -> pulseQueue.add(new Signal(pulse.to, t, true)));
        }
      } else {
        if (!isHigh) {
          boolean newState = !state.get(module.source);
          state.put(module.source, newState);
          module.targets.forEach(t -> pulseQueue.add(new Signal(pulse.to, t, newState)));
        }
      }
    }
    return out;
  }

  @Override
  public Object part2() {
    var in = dayStrings();
    var modules = Arrays.stream(in).map(Module::new).collect(toMap(m -> m.source, m -> m));
    var flipflopState = modules.keySet().stream().filter(m -> modules.get(m).t == Type.FLIPFLOP).collect(toMap(k -> k, k -> false));
    var conjunctionState = modules.keySet().stream().filter(m -> modules.get(m).t == Type.CONJUNCTION).collect(toMap(k -> k, k -> modules.entrySet().stream().filter(e -> e.getValue().targets.contains(k)).map(Map.Entry::getKey).collect(toMap(k2 -> k2, k2 -> false))));
    return range(1, Integer.MAX_VALUE).filter(i -> sendPulse(modules, flipflopState, conjunctionState).equals(new Pair<>(-1L, -1L))).findFirst().getAsInt();
  }
}
