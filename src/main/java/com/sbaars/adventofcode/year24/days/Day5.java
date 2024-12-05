package com.sbaars.adventofcode.year24.days;

import com.sbaars.adventofcode.year24.Day2024;
import com.sbaars.adventofcode.common.Graph;
import com.sbaars.adventofcode.common.map.ListMap;
import com.sbaars.adventofcode.common.Pair;

import static com.sbaars.adventofcode.common.map.ListMap.toListMap;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 extends Day2024 {

  public Day5() {
    super(5);
  }

  public static void main(String[] args) {
    new Day5().printParts();
  }

  @Override
  public Object part1() {
    return calculateMiddlePages(true);
  }

  @Override
  public Object part2() {
    return calculateMiddlePages(false);
  }

  private Object calculateMiddlePages(boolean onlyValid) {
    String[] sections = day().split("\n\n");
    List<Pair<Integer, Integer>> orderPairs = Arrays.stream(sections[0].split("\n"))
        .filter(rule -> !rule.isEmpty())
        .map(rule -> {
          String[] parts = rule.split("\\|");
          return new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        })
        .toList();

    return Arrays.stream(sections[1].split("\n"))
        .filter(u -> !u.isEmpty())
        .map(update -> Arrays.stream(update.split(",")).map(Integer::parseInt).toList())
        .filter(pages -> isValidUpdate(pages, orderPairs) == onlyValid)
        .mapToInt(pages -> onlyValid ? pages.get(pages.size() / 2) : calculateMiddlePage(orderPairs, pages))
        .sum();
  }

  private int calculateMiddlePage(List<Pair<Integer, Integer>> orderPairs, List<Integer> pages) {
    Set<Integer> nodes = new HashSet<>(pages);
    var edges = orderPairs.stream()
        .filter(pair -> nodes.contains(pair.a()) && nodes.contains(pair.b()))
        .collect(toListMap(Pair::a, Pair::b));
    return topologicalSort(nodes, edges).get(nodes.size() / 2);
  }

  private boolean isValidUpdate(List<Integer> pages, List<Pair<Integer, Integer>> orderPairs) {
    Map<Integer, Integer> pageToIndex = new HashMap<>();
    for (int i = 0; i < pages.size(); i++) {
      pageToIndex.put(pages.get(i), i);
    }
    return orderPairs.stream().noneMatch(pair -> 
      pageToIndex.containsKey(pair.a()) && pageToIndex.containsKey(pair.b()) &&
      pageToIndex.get(pair.a()) >= pageToIndex.get(pair.b())
    );
  }

  private List<Integer> topologicalSort(Set<Integer> nodes, ListMap<Integer, Integer> edges) {
    Graph<Integer> graph = new Graph<>(edges);
    List<Integer> ordering = new ArrayList<>();
    Queue<Graph.Node<Integer>> queue = graph.stream().filter(node -> node.parents().isEmpty()).collect(Collectors.toCollection(LinkedList::new));

    while (!queue.isEmpty()) {
      Graph.Node<Integer> node = queue.poll();
      ordering.add(node.data());
      node.children().forEach(neighbor -> {
        neighbor.parents().remove(node);
        if (neighbor.parents().isEmpty()) queue.add(neighbor);
      });
    }

    return ordering;
  }
}
