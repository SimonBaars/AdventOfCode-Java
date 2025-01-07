package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.*;

import static com.sbaars.adventofcode.year19.days.Day12.lcm;
import static java.lang.Long.MAX_VALUE;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.LongStream.range;

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
          List.of(in.split(" -> ")[1].split(", ")));
    }
  }

  public record Signal(String from, String to, boolean isHigh) {
  }

  @Override
  public Object part1() {
    var modules = Arrays.stream(dayStrings()).map(Module::new).collect(toMap(m -> m.source, m -> m));
    var flipflopState = modules.keySet().stream().filter(m -> modules.get(m).t == Type.FLIPFLOP).collect(toMap(k -> k, k -> false));
    var conjunctionState = modules.keySet().stream().filter(m -> modules.get(m).t == Type.CONJUNCTION).collect(toMap(k -> k, k -> modules.entrySet().stream().filter(e -> e.getValue().targets.contains(k)).map(Map.Entry::getKey).collect(toMap(k2 -> k2, k2 -> false))));
    return range(0, 1000).mapToObj(i -> sendPulse(modules, flipflopState, conjunctionState, new HashMap<>(), i)).reduce(new Pair<>(0L, 0L), (a, b) -> new Pair<>(a.a() + b.a(), a.b() + b.b())).map((a, b) -> a * b);
  }

  private Pair<Long, Long> sendPulse(Map<String, Module> modules, Map<String, Boolean> state, Map<String, Map<String, Boolean>> conjunctionState, Map<String, Long> lcms, long i) {
    Pair<Long, Long> out = new Pair<>(0L, 0L);
    var pulseQueue = new LinkedList<>(List.of(new Signal("", "broadcaster", false)));
    while (!pulseQueue.isEmpty()) {
      var pulse = pulseQueue.pop();
      var isHigh = pulse.isHigh();
      out = out.map((a, b) -> new Pair<>(a + (isHigh ? 0 : 1), b + (isHigh ? 1 : 0)));
      if (isHigh && lcms.containsKey(pulse.from)) {
        lcms.put(pulse.from, i);
        if (lcms.values().stream().allMatch(e -> e != 0L)) {
          return new Pair<>(lcm(lcms.values().stream().mapToLong(e -> e).toArray()), 0L);
        }
      }
      if (!modules.containsKey(pulse.to)) continue;
      var module = modules.get(pulse.to);
      switch (module.t) {
        case BROADCAST -> module.targets.forEach(t -> pulseQueue.add(new Signal(pulse.to, t, isHigh)));
        case CONJUNCTION -> {
          conjunctionState.get(pulse.to).put(pulse.from, isHigh);
          boolean allHigh = conjunctionState.get(pulse.to).values().stream().allMatch(b -> b);
          module.targets.forEach(t -> pulseQueue.add(new Signal(pulse.to, t, !allHigh)));
        }
        case FLIPFLOP -> {
          if (!isHigh) {
            boolean newState = !state.get(module.source);
            state.put(module.source, newState);
            module.targets.forEach(t -> pulseQueue.add(new Signal(pulse.to, t, newState)));
          }
        }
      }
    }
    return lcms.isEmpty() ? out : new Pair<>(0L, 0L);
  }

  @Override
  public Object part2() {
    var modules = Arrays.stream(dayStrings()).map(Module::new).collect(toMap(m -> m.source, m -> m));
    var rx = modules.keySet().stream().filter(m -> modules.get(m).targets.contains("rx")).findFirst().orElseThrow();
    var flipflopState = modules.keySet().stream().filter(m -> modules.get(m).t == Type.FLIPFLOP).collect(toMap(k -> k, k -> false));
    var conjunctionState = modules.keySet().stream().filter(m -> modules.get(m).t == Type.CONJUNCTION).collect(toMap(k -> k, k -> modules.entrySet().stream().filter(e -> e.getValue().targets.contains(k)).map(Map.Entry::getKey).collect(toMap(k2 -> k2, k2 -> false))));
    var lcms = conjunctionState.get(rx).keySet().stream().collect(toMap(e -> e, e -> 0L));
    return range(1, MAX_VALUE).map(i -> sendPulse(modules, flipflopState, conjunctionState, lcms, i).a()).filter(e -> e != 0L).findFirst().orElseThrow();
  }
}
