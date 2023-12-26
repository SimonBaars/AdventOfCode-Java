package com.sbaars.adventofcode.year23.days;

import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.common.map.ListMap;
import com.sbaars.adventofcode.year23.Day2023;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sbaars.adventofcode.common.map.ListMap.toListMap;
import static com.sbaars.adventofcode.util.DataMapper.readString;
import static java.util.Comparator.reverseOrder;

public class Day25 extends Day2023 {
  public Day25() {
    super(25);
  }

  public static void main(String[] args) {
    new Day25().printParts();
  }

  public record Wiring(String comp, List<String> connectedTo) {
  }

  @Override
  public Object part1() {
    ListMap<String, String> connections = dayStream().map(s -> readString(s, "%s: %ls", " ", Wiring.class)).flatMap(w -> w.connectedTo.stream().map(c -> new Pair<>(w.comp, c))).flatMap(p -> Stream.of(p, p.flip())).collect(toListMap(Pair::a, Pair::b));
    String randomNode = connections.keySet().stream().findFirst().orElseThrow();
    var totalSize = dijkstra(connections, randomNode).size();
    var allNodes = connections.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    record PathComponent(String label, AtomicInteger counter) implements Comparable<PathComponent> {
      @Override
      public int compareTo(PathComponent o) {
        var c = Integer.compare(counter.get(), o.counter.get());
        return c != 0 ? c : label.compareTo(o.label);
      }
    }
    Map<String, PathComponent> pathComponents = new HashMap<>();
    allNodes.forEach(s -> dijkstra(connections, s).forEach((k, v) -> {
      var label = k.compareTo(v.a()) < 0 ? k + " " + v.a() : v.a() + " " + k;
      pathComponents.computeIfAbsent(label, t -> new PathComponent(t, new AtomicInteger())).counter.incrementAndGet();
    }));
    pathComponents.values().stream().sorted(reverseOrder()).limit(3L).map(PathComponent::label).map(s -> s.split(" ")).forEach(s -> {
      connections.removeFrom(s[0], s[1]);
      connections.removeFrom(s[1], s[0]);
    });
    var reducedSize = dijkstra(connections, randomNode).size();
    return reducedSize * (totalSize - reducedSize);
  }

  record ND(String node, int distance) implements Comparable<ND> {

    @Override
    public int compareTo(ND o) {
      int d = Integer.compare(distance, o.distance);
      return d != 0 ? d : node.compareTo(o.node);
    }
  }


  @Override
  public Object part2() {
    return "That's all!";
  }

  public Map<String, Pair<T, Integer>> dijkstra(ListMap<T, T> nodes, T node) {
    var queue = new PriorityQueue<Pair<T, Integer>>();
    Set<T> visited = new HashSet<>();
    Map<T, Pair<T, Integer>> distanceMap = new HashMap<>();
    queue.add(new Pair<>(node, 0));
    while (!queue.isEmpty()) {
      var current = queue.remove();
      visited.add(current.a());
      nodes.get(current.a()).stream()
          .filter(s -> !visited.contains(s))
          .filter(s -> !distanceMap.containsKey(s) || distanceMap.get(s).b() > current.b() + 1)
          .forEach(s -> {
            distanceMap.put(s, new Pair<>(current.a(), current.b() + 1));
            queue.add(new Pair<>(s, current.b() + 1));
          });
    }
    return distanceMap;
  }
}
