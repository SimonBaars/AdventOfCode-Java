package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.common.Graph;
import com.sbaars.adventofcode.common.Pair;
import com.sbaars.adventofcode.util.DataMapper;
import com.sbaars.adventofcode.year24.Day2024;

import static com.sbaars.adventofcode.common.Graph.toGraph;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

import java.util.*;

public class Day23 extends Day2024 {
  public Day23() {
    super(23);
  }

  public static void main(String[] args) {
    new Day23().printParts();
  }

  @Override
  public Object part1() {
    Graph<String> g = input();
    return g.edgeStream()
      .flatMapToObj((a, b) -> g.getData(b).stream().map(c -> Arrays.asList(a, b, c)))
      .filter(t -> g.getData(t.get(2)).contains(t.get(0)))
      .map(l -> new HashSet<>(l))
      .distinct()
      .filter(t -> t.stream().anyMatch(name -> name.toLowerCase().startsWith("t")))
      .count();
  }

  @Override
  public Object part2() {
    Graph<String> g = input();
    return findCliques(g, new HashSet<>(), new HashSet<>(g.keySet())).stream()
      .max(comparingInt(Set::size))
      .map(largestClique -> largestClique.stream().sorted().reduce((a, b) -> a + "," + b).orElse(""))
      .orElse("");
  }

  private Graph<String> input() {
    return dayStream().map(s -> (Pair<String, String>) DataMapper.readString(s, "%s-%s", Pair.class)).flatMap(p -> of(p, p.flip())).collect(toGraph());
  }

  private List<Set<String>> findCliques(Graph<String> g, Set<String> potentialClique, Set<String> candidates) {
    if (candidates.isEmpty()) {
      return List.of(new HashSet<>(potentialClique));
    }
    return new HashSet<>(candidates).stream().flatMap(candidate -> {
      candidates.remove(candidate);
      return findCliques(g, concat(potentialClique.stream(), of(candidate)).collect(toSet()), intersect(candidates, g.getData(candidate))).stream();
    }).toList();
  }

  private Set<String> intersect(Set<String> set1, List<String> set2) {
    Set<String> result = new HashSet<>(set1);
    result.retainAll(set2);
    return result;
  }
}
